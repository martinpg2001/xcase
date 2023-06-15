using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XCase.ProxyGenerator;
using XCase.ProxyGenerator.REST;
using XCase.Swagger.ProxyGenerator.OpenAPI;
using Microsoft.Extensions.Configuration;
using Serilog;

namespace XCase.REST.ProxyGenerator.Generator
{
    public class RAMLCSharpProxyGenerator : CSharpProxyGenerator
    {
        #region Logger Setup
        /// <summary>
        /// A log instance.
        /// </summary>
        private static readonly Serilog.ILogger Log = new LoggerConfiguration().Enrich.WithProperty("Class", "RAMLCSharpProxyGenerator").ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();
        #endregion

        public override IServiceDefinition GenerateSourceString(string ramlDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                ramlDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                RESTApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new();
                //swaggerApiProxySettingsEndPoint.AppendAsyncToMethodName = false;
                ramlDocDictionary.GetOrAdd(swaggerApiProxySettingsEndPoint, ramlDocument);
                Log.Debug("about to process RAML document");
                return ProcessRAMLDocuments();
            }
            catch (AggregateException ae)
            {
                Log.Warning("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warning("exception generating source string: " + e.Message);
                throw;
            }
        }

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                ramlDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                List<Task<string>> taskList = new();
                foreach (IAPIProxySettingsEndpoint endPoint in endpoints)
                {
                    string requestUri = endPoint.GetUrl();
                    Log.Debug("about to add task for {0}", requestUri);
                    Task<string> endpointTask = GetEndpointDoc(requestUri);
                    taskList.Add(endpointTask);
                    Log.Debug("added endpointTask");
                    string ramlString = endpointTask.Result;
                    Log.Debug("ramlString is\r\n{0}", ramlString);
                    ramlDocDictionary.GetOrAdd(endPoint, ramlString);
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WhenAll(taskList);
                Log.Debug("REST documents completed downloading");
                return ProcessRAMLDocuments();
            }
            catch (AggregateException ae)
            {
                Log.Warning("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warning("exception generating source string: " + e.Message);
                throw;
            }
        }

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant)
        {
            throw new NotImplementedException();
        }

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document)
        {
            throw new NotImplementedException();
        }

        private static RESTServiceDefinition ProcessRAMLDocuments()
        {
            Log.Debug("starting ProcessRAMLDocuments()");
            return ProcessRAMLDocuments("Admin", "password", "domain");
        }

        private static RESTServiceDefinition ProcessRAMLDocuments(string username, string password, string tenant)
        {
            Log.Debug("starting ProcessRAMLDocuments(string username, string password, string tenant)");
            RESTServiceDefinition ramlServiceDefinition = new();
            Log.Debug("created ramlServiceDefinition");
            List<string> sourceStringList = new();
            OpenApiParser parser = new();
            Log.Debug("created parser");
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> ramlDocDictionaryEntry in ramlDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                Log.Debug("next ramlDocDictionaryEntry");
                ProcessRAMLDocDictionaryEntry(ramlServiceDefinition, ramlDocDictionaryEntry, sourceStringList, parser, username, password, tenant);
            }

            Log.Debug("processed ramlDocDictionary");
            ramlServiceDefinition.SourceStrings = sourceStringList.ToArray<string>();
            ramlServiceDefinition.GetProxyClasses().Sort();
            return ramlServiceDefinition;
        }

        private static void ProcessRAMLDocDictionaryEntry(RESTServiceDefinition ramlServiceDefinition, KeyValuePair<IAPIProxySettingsEndpoint, string> ramlDocDictionaryEntry, List<string> sourceStringList, OpenApiParser parser, string username, string password, string tenant)
        {
            Log.Debug("starting ProcessRAMLDocDictionaryEntry()");
            IAPIProxySettingsEndpoint endPoint = ramlDocDictionaryEntry.Key;
            string result = ramlDocDictionaryEntry.Value;
            Log.Debug("result is\r\n{0}", result);
            /* Process endpoint information */
            string endPointURL = endPoint.GetUrl();
            Log.Debug("endPointURL is {0}", endPointURL);
            string schemeFromURL = endPointURL != null ? endPointURL.Substring(0, endPointURL.IndexOf(":")) : "http";
            Log.Debug("schemeFromURL is {0}", schemeFromURL);
            string methodNameAppend = string.Empty;
            if (endPoint.GetAppendAsyncToMethodName())
            {
                methodNameAppend = "Async";
            }

            /* Parse REST document for endpoint */
            IProxyDefinition proxyDefinition = parser.ParseDoc(result, (RESTApiProxySettingsEndPoint)endPoint);
            /* Use the parsed values of schemes, host, and basepath to build endpoint.
             * Assume that host does NOT include schem or ://, and does not end in /
             * Assume that basepath does start with /
             */
            string scheme = proxyDefinition.Schemes != null ? proxyDefinition.Schemes[0] : schemeFromURL;
            Log.Debug("scheme is {0}", scheme);
            Log.Debug("proxyDefinition.Host is {0}", proxyDefinition.Host);
            Log.Debug("proxyDefinition.BasePath is {0}", proxyDefinition.BasePath);
            string endPointString = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
            Log.Debug("endPointString is {0}", endPointString);
            if (!endPointString.EndsWith("/"))
            {
                endPointString = string.Format(endPointString + "{0}", "/");
            }

            if (endPointString.StartsWith("http"))
            {
                ramlServiceDefinition.EndPoint = endPointString;
            }
            else
            {
                ramlServiceDefinition.EndPoint = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
//                ramlServiceDefinition.EndPoint = "http://localhost:8080/api/v3/";
            }

            List<string> proxies = proxyDefinition.Operations.Select(i => i.ProxyName).Distinct().ToList();
            /* Before writing out the proxy classes, we have to ensure that the operation ids are
             * unique within each proxy class.
             */
            foreach (string proxy in proxies)
            {
                Log.Debug("next proxy {0}", proxy);
                IEnumerable<Operation> operationEnumerable = proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxy));
                Log.Debug("proxy operation count is ", operationEnumerable.Count<Operation>());
                foreach (Operation operation in operationEnumerable)
                {
                    int count = operationEnumerable.Count<Operation>(o => o.OperationId == operation.OperationId);
                    if (count > 1)
                    {
                        operation.OperationId = operation.OperationId + count;
                    }

                    Log.Debug("operation.OperationId is {0}", operation.OperationId);
                }
            }

            /* Interface and implementation for proxy classes */
            foreach (string proxy in proxies)
            {
                Log.Debug("next proxy {0}", proxy);
                StringBuilder interfaceStringBuilder = CreateInterfaceStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                Log.Debug("created interfaceStringBuilder for {0}", proxy);
                sourceStringList.Add(interfaceStringBuilder.ToString());
                Log.Debug("added interface for proxy {0}", proxy);
                string className = OpenApiParser.FixTypeName(proxy) + "WebProxy";
                ramlServiceDefinition.ProxyClasses.Add(className);
                Log.Debug("added className {0}", className);
                StringBuilder proxyStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend, username, password, tenant);
                Log.Debug("created proxyStringBuilder for {0}", proxy);
                sourceStringList.Add(proxyStringBuilder.ToString());
                Log.Debug("finished proxy {0}", proxy);
            }

            /* Model Classes */
            foreach (ClassDefinition classDefinition in proxyDefinition.ClassDefinitions)
            {
                StringBuilder classStringBuilder = WriteClassDefinitionToStringBuilder(classDefinition, endPoint);
                sourceStringList.Add(classStringBuilder.ToString());
            }
        }

        public static RESTApiProxySettings GetSettings(string path)
        {
            using FileStream settingStream = File.OpenRead(path);
            StreamReader streamReader = new(settingStream);
            string value = streamReader.ReadToEnd();
            return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
        }

        //public static async Task GetEndpointRAMLDoc(string requestUri, IAPIProxySettingsEndpoint endPoint)
        //{
        //    Log.Debug("starting GetEndpointRAMLDoc()");
        //    string ramlString = null;
        //    System.Net.WebRequest webRequest = System.Net.WebRequest.Create(requestUri);
        //    Log.Debug("created webRequest");
        //    using (WebResponse webResponse = await webRequest.GetResponseAsync().ConfigureAwait(false))
        //    {
        //        Log.Debug("got webResponse");
        //        Stream webResponseStream = webResponse.GetResponseStream();
        //        StreamReader webResponseStreamReader = new StreamReader(webResponseStream);
        //        ramlString = await webResponseStreamReader.ReadToEndAsync().ConfigureAwait(false);
        //    }

        //    if (ramlString == null)
        //    {
        //        throw new Exception(string.Format("Error downloading from: {0}", endPoint.GetUrl()));
        //    }

        //    Log.Debug("downloaded: {0}", requestUri);
        //    ramlDocDictionary.GetOrAdd(endPoint, ramlString);
        //    Log.Debug("finishing GetEndpointRAMLDoc()");
        //}
    }
}
