namespace XCase.REST.ProxyGenerator.Generator
{
    using System;
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
    using Microsoft.CSharp;
//    using Microsoft.Owin.Testing;
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Serilog;
    using Serilog.Events;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.OpenAPI;

    public class SwaggerJavaProxyGenerator : JavaProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        //static readonly Serilog.ILogger Log = new LoggerConfiguration().MinimumLevel.Debug().WriteTo.File("XCaseServiceClient.log").WriteTo.Console(restrictedToMinimumLevel: LogEventLevel.Information).CreateLogger();

        #endregion

        public override IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, string swaggerDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            return null; 
        }

        public override IServiceDefinition GenerateSourceString(string swaggerDocument)
        {
            Log.Debug("starting GenerateSourceString()");
            try
            {
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                RESTApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint();
                swaggerApiProxySettingsEndPoint.AppendAsyncToMethodName = false;
                swaggerApiProxySettingsEndPoint.Namespace = "com.xcase.integrate.objects";
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
                Log.Debug("about to process REST document");
                RESTServiceDefinition restServiceDefinition = ProcessSwaggerDocuments(username, password, tenant);
                Log.Debug("processed REST document");
                return restServiceDefinition;
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
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                List<Task<string>> taskList = new List<Task<string>>();
                foreach (IAPIProxySettingsEndpoint endPoint in endpoints)
                {
                    string requestUri = endPoint.GetUrl();
                    Log.Debug("about to add task for {0}", requestUri);
                    Task<string> endpointTask = GetEndpointDoc(requestUri);
                    taskList.Add(endpointTask);
                    Log.Debug("added endpointTask");
                    string swaggerString = endpointTask.Result;
                    Log.Debug("swaggerString is {0}", swaggerString);
                    swaggerDocDictionary.GetOrAdd(endPoint, swaggerString);
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WhenAll(taskList);
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

        private static RESTServiceDefinition ProcessSwaggerDocuments(string username, string password, string tenant)
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            RESTServiceDefinition swaggerServiceDefinition = new RESTServiceDefinition();
            List<string> sourceStringList = new List<string>();
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry in swaggerDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.Key;
                Log.Debug("processing {0}", endPoint.GetUrl());
                string methodNameAppend = string.Empty;
                if (endPoint.GetAppendAsyncToMethodName())
                {
                    methodNameAppend = "Async";
                }

                string result = swaggerDocDictionaryEntry.Value;
                SwaggerParser parser = new SwaggerParser();
                IProxyDefinition proxyDefinition = parser.ParseDoc(result, (RESTApiProxySettingsEndPoint)endPoint);
                string endPointString = string.Format("http://{0}{1}", proxyDefinition.Host, proxyDefinition.BasePath);
                if (!endPointString.EndsWith("/"))
                {
                    endPointString = string.Format(endPointString + "{0}", "/");
                }

                swaggerServiceDefinition.EndPoint = endPointString;
                List<string> proxies = proxyDefinition.Operations.Select(i => i.ProxyName).Distinct().ToList();
                /* Write interface for each proxy in proxies */
                foreach (string proxy in proxies)
                {
                    StringBuilder interfaceStringBuilder = CreateInterfaceStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                    Log.Debug("created interfaceStringBuilder for {0}", proxy);
                    sourceStringList.Add(interfaceStringBuilder.ToString());
                }

                /* Write class for each proxy in proxies */
                foreach (string proxy in proxies)
                {
                    string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                    swaggerServiceDefinition.ProxyClasses.Add(className);
                    StringBuilder classStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                    Log.Debug("created classStringBuilder for {0}", proxy);
                    sourceStringList.Add(classStringBuilder.ToString());
                }

                // Model Classes
                foreach (ClassDefinition classDefinition in proxyDefinition.ClassDefinitions)
                {
                    StringBuilder modelClassStringBuilder = WriteClassDefinitionToStringBuilder(classDefinition, endPoint);
                    sourceStringList.Add(modelClassStringBuilder.ToString());
                }
            }

            swaggerServiceDefinition.SourceStrings = sourceStringList.ToArray<string>();
            return swaggerServiceDefinition;
        }

        private static RESTServiceDefinition ProcessSwaggerDocuments()
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            return ProcessSwaggerDocuments("Admin", "password", "tenant1");
        }

        public static string GetDefaultValue(Parameter x)
        {
            if (!x.Type.IsNullableType && x.CollectionFormat != "multi" && x.Type.EnumValues != null && x.Type.EnumValues.Any())
            {
                return string.Format("{0}.{1}", GetDefaultType(x), x.Type.EnumValues.FirstOrDefault());
            }

            return "null";
        }

        public static RESTApiProxySettings GetSettings(string path)
        {
            using (FileStream settingStream = File.OpenRead(path))
            {
                StreamReader streamReader = new StreamReader(settingStream);
                var value = streamReader.ReadToEnd();
                return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
            }
        }
    }
}
