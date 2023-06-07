namespace XCase.REST.ProxyGenerator.Generator
{
    using System;
    using System.CodeDom;
    using System.Collections.Concurrent;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Net.Http;
    using System.Reflection;
    using System.Security;
    using System.Text;
    using System.Threading.Tasks;
    using System.Web;
    using Microsoft.CSharp;
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.OpenAPI;

    public class SwaggerCSharpProxyGenerator : CSharpProxyGenerator
    {
        #region Logger Setup

        #endregion

        public override IServiceDefinition GenerateSourceString(string swaggerDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                RESTApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint();
                //swaggerApiProxySettingsEndPoint.AppendAsyncToMethodName = false;
                swaggerDocDictionary.GetOrAdd(swaggerApiProxySettingsEndPoint, swaggerDocument);
                Log.Debug("about to process REST document");
                return ProcessSwaggerDocuments();
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

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, string swaggerDocument, string username, string password, string tenant)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                //SwaggerApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new SwaggerApiProxySettingsEndPoint();
                ((RESTApiProxySettingsEndPoint)swaggerApiProxySettingsEndPoint).AppendAsyncToMethodName = false;
                swaggerDocDictionary.GetOrAdd(swaggerApiProxySettingsEndPoint, swaggerDocument);
                Log.Debug("about to process Swagger document");
                return ProcessSwaggerDocuments(username, password, tenant);
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

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, string swaggerDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            return GenerateSourceString(swaggerApiProxySettingsEndPoint, swaggerDocument, "Admin", "password", "domain");
        }

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                List<Task> taskList = new List<Task>();
                foreach (IAPIProxySettingsEndpoint endPoint in endpoints)
                {
                    string requestUri = endPoint.GetUrl();
                    Log.Debug("about to add task for {0}", requestUri);
                    taskList.Add(GetEndpointSwaggerDoc(requestUri, endPoint));
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WaitAll(taskList.ToArray());
                Log.Debug("REST documents completed downloading");
                return ProcessSwaggerDocuments();
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

        private static RESTServiceDefinition ProcessSwaggerDocuments(string username, string password, string domain)
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            RESTServiceDefinition swaggerServiceDefinition = new RESTServiceDefinition();
            List<string> sourceStringList = new List<string>();
            SwaggerParser parser = new SwaggerParser();
            Log.Debug("created SwaggerParser");
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry in swaggerDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                ProcessSwaggerDocDictionaryEntry(swaggerServiceDefinition, swaggerDocDictionaryEntry, sourceStringList, parser, username, password, domain);
            }

            swaggerServiceDefinition.SourceStrings = sourceStringList.ToArray<string>();
            swaggerServiceDefinition.GetProxyClasses().Sort();
            return swaggerServiceDefinition;
        }

        private static void ProcessSwaggerDocDictionaryEntry(RESTServiceDefinition swaggerServiceDefinition, KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry, List<string> sourceStringList, SwaggerParser parser, string username, string password, string domain)
        {
            IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.Key;
            string result = swaggerDocDictionaryEntry.Value;
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
            string scheme = proxyDefinition.Schemes != null ? proxyDefinition.Schemes[0] : schemeFromURL;
            Log.Debug("scheme is {0}", scheme);
            Log.Debug("proxyDefinition.Host is {0}", proxyDefinition.Host);
            Log.Debug("proxyDefinition.BasePath is {0}", proxyDefinition.BasePath);
            string endPointString = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
           // string endPointString = proxyDefinition.BasePath;
            if (!endPointString.EndsWith("/"))
            {
                endPointString = string.Format(endPointString + "{0}", "/");
            }

            Log.Debug("endPointString is {0}", endPointString);
            swaggerServiceDefinition.EndPoint = endPointString;
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
                string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                swaggerServiceDefinition.ProxyClasses.Add(className);
                Log.Debug("added className {0}", className);
                StringBuilder proxyStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend, username, password, domain);
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

        private static RESTServiceDefinition ProcessSwaggerDocuments()
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            return ProcessSwaggerDocuments("Admin", "password", "domain");
        }

        public static RESTApiProxySettings GetSettings(string path)
        {
            using FileStream settingStream = File.OpenRead(path);
            StreamReader streamReader = new StreamReader(settingStream);
            string value = streamReader.ReadToEnd();
            return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
        }

        public static async Task GetEndpointSwaggerDoc(string requestUri, IAPIProxySettingsEndpoint endPoint)
        {
            Log.Debug("starting GetEndpointSwaggerDoc()");
            string swaggerString = null;
            // System.Net.WebRequest webRequest = System.Net.WebRequest.Create(requestUri);
            // Log.Debug("created webRequest for {1}", requestUri);
            // using (WebResponse webResponse = await webRequest.GetResponseAsync().ConfigureAwait(false))
            // {
            //     Log.Debug("got webResponse");
            //     Stream webResponseStream = webResponse.GetResponseStream();
            //     StreamReader webResponseStreamReader = new StreamReader(webResponseStream);
            //     swaggerString = await webResponseStreamReader.ReadToEndAsync().ConfigureAwait(false);
            // }

            HttpClient httpClient = new();
            swaggerString = await httpClient.GetStringAsync(requestUri);
            if (swaggerString == null)
            {
                throw new Exception(string.Format("Error downloading from: {0}", endPoint.GetUrl()));
            }

            Log.Debug("downloaded: {0}", requestUri);
            swaggerDocDictionary.GetOrAdd(endPoint, swaggerString);
            Log.Debug("finishing GetEndpointSwaggerDoc()");
        }
    }
}
