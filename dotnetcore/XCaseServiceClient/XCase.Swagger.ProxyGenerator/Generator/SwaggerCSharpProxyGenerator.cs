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
    using log4net;
    using Newtonsoft.Json;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.OpenAPI;

    public class SwaggerCSharpProxyGenerator : SwaggerProxyGenerator, IProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

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
                Log.Warn("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warn("exception generating source string: " + e.Message);
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
                Log.Debug("about to process REST document");
                return ProcessSwaggerDocuments(username, password, tenant);
            }
            catch (AggregateException ae)
            {
                Log.Warn("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warn("exception generating source string: " + e.Message);
                throw;
            }
        }

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, string swaggerDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            return GenerateSourceString(swaggerApiProxySettingsEndPoint, swaggerDocument, "Admin", "1nt@ppC10ud2016", "tenant1");
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
                    Log.DebugFormat("about to add task for {0}", requestUri);
                    taskList.Add(GetEndpointSwaggerDoc(requestUri, endPoint));
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WaitAll(taskList.ToArray());
                Log.Debug("REST documents completed downloading");
                return ProcessSwaggerDocuments();
            }
            catch (AggregateException ae)
            {
                Log.Warn("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warn("exception generating source string: " + e.Message);
                throw;
            }
        }

        private static RESTServiceDefinition ProcessSwaggerDocuments(string username, string password, string tenant)
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            RESTServiceDefinition swaggerServiceDefinition = new RESTServiceDefinition();
            List<string> sourceStringList = new List<string>();
            SwaggerParser parser = new SwaggerParser();
            Log.DebugFormat("created SwaggerParser");
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry in swaggerDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                ProcessSwaggerDocDictionaryEntry(swaggerServiceDefinition, swaggerDocDictionaryEntry, sourceStringList, parser, username, password, tenant);
            }

            swaggerServiceDefinition.SourceStrings = sourceStringList.ToArray<string>();
            swaggerServiceDefinition.GetProxyClasses().Sort();
            return swaggerServiceDefinition;
        }

        private static void ProcessSwaggerDocDictionaryEntry(RESTServiceDefinition swaggerServiceDefinition, KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry, List<string> sourceStringList, SwaggerParser parser, string username, string password, string tenant)
        {
            IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.Key;
            string result = swaggerDocDictionaryEntry.Value;
            /* Process endpoint information */
            string endPointURL = endPoint.GetUrl();
            Log.DebugFormat("endPointURL is {0}", endPointURL);
            string schemeFromURL = endPointURL != null ? endPointURL.Substring(0, endPointURL.IndexOf(":")) : "http";
            Log.DebugFormat("schemeFromURL is {0}", schemeFromURL);
            string methodNameAppend = string.Empty;
            if (endPoint.GetAppendAsyncToMethodName())
            {
                methodNameAppend = "Async";
            }

            /* Parse REST document for endpoint */
            IProxyDefinition proxyDefinition = parser.ParseDoc(result, (RESTApiProxySettingsEndPoint)endPoint);
            string scheme = proxyDefinition.Schemes != null ? proxyDefinition.Schemes[0] : schemeFromURL;
            Log.DebugFormat("scheme is {0}", scheme);
            string endPointString = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
            if (!endPointString.EndsWith("/"))
            {
                endPointString = string.Format(endPointString + "{0}", "/");
            }

            swaggerServiceDefinition.EndPoint = endPointString;
            List<string> proxies = proxyDefinition.Operations.Select(i => i.ProxyName).Distinct().ToList();
            /* Interface for proxy classes */
            foreach (string proxy in proxies)
            {
                StringBuilder interfaceStringBuilder = CreateInterfaceStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                Log.DebugFormat("created interfaceStringBuilder for {0}", proxy);
                sourceStringList.Add(interfaceStringBuilder.ToString());
            }

            /* Main proxy classes */
            foreach (string proxy in proxies)
            {
                string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                swaggerServiceDefinition.ProxyClasses.Add(className);
                Log.DebugFormat("added className {0}", className);
                StringBuilder proxyStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend, username, password, tenant);
                Log.DebugFormat("created proxyStringBuilder for {0}", proxy);
                sourceStringList.Add(proxyStringBuilder.ToString());
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
    }
}
