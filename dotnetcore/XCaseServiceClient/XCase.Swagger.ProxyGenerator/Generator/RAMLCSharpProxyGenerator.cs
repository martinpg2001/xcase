using Microsoft.Extensions.Logging;
using Microsoft.CSharp;
using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Security;
using System.Text;
using System.Threading.Tasks;
using XCase.ProxyGenerator;
using XCase.ProxyGenerator.REST;
using XCase.Swagger.ProxyGenerator.OpenAPI;

namespace XCase.REST.ProxyGenerator.Generator
{
    public class RAMLCSharpProxyGenerator : RAMLProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        //private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public override IServiceDefinition GenerateSourceString(string ramlDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                ramlDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                RESTApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint();
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
                List<Task> taskList = new List<Task>();
                foreach (IAPIProxySettingsEndpoint endPoint in endpoints)
                {
                    string requestUri = endPoint.GetUrl();
                    Log.Debug("about to add task for {0}", requestUri);
                    taskList.Add(GetEndpointRAMLDoc(requestUri, endPoint));
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WaitAll(taskList.ToArray());
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
            Log.Debug("starting ProcessRAMLDocuments()");
            RESTServiceDefinition ramlServiceDefinition = new RESTServiceDefinition();
            List<string> sourceStringList = new List<string>();
            OpenApiParser parser = new OpenApiParser();
            Log.Debug("created RAMLParser");
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry in ramlDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                ProcessRAMLDocDictionaryEntry(ramlServiceDefinition, swaggerDocDictionaryEntry, sourceStringList, parser, username, password, tenant);
            }

            ramlServiceDefinition.SourceStrings = sourceStringList.ToArray<string>();
            ramlServiceDefinition.GetProxyClasses().Sort();
            return ramlServiceDefinition;
        }

        private static void ProcessRAMLDocDictionaryEntry(RESTServiceDefinition ramlServiceDefinition, KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry, List<string> sourceStringList, OpenApiParser parser, string username, string password, string tenant)
        {
            IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.Key;
            string result = swaggerDocDictionaryEntry.Value;
            Log.Debug("result is {0}", result);
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
            string endPointString = proxyDefinition.Host;
            Log.Debug("proxyDefinition.Host is {0}", proxyDefinition.Host);
            //string endPointString = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
            if (!endPointString.EndsWith("/"))
            {
                endPointString = string.Format(endPointString + "{0}", "/");
            }

            Log.Debug("endPointString is {0}", endPointString);
            if (endPointString.StartsWith("http"))
            {
                ramlServiceDefinition.EndPoint = endPointString;
            }
            else
            {
                ramlServiceDefinition.EndPoint = "http://localhost:8080/api/v3/";
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
            StreamReader streamReader = new StreamReader(settingStream);
            string value = streamReader.ReadToEnd();
            return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
        }
    }
}
