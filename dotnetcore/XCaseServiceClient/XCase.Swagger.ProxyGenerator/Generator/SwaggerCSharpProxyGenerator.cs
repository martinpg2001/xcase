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
 //   using Microsoft.Owin.Testing;
    using log4net;
    using Newtonsoft.Json;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.Swagger;

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

        private static StringBuilder WriteClassDefinitionToStringBuilder(ClassDefinition classDefinition, IAPIProxySettingsEndpoint endPoint)
        {
            StringBuilder classStringBuilder = new StringBuilder();
            WriteLine(classStringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(classStringBuilder);
            List<XCase.ProxyGenerator.REST.Enum> modelEnums = new List<XCase.ProxyGenerator.REST.Enum>();
            WriteLine(classStringBuilder, string.Format("public class {0}{1}", classDefinition.GetCleanClassName(), string.IsNullOrEmpty(classDefinition.Inherits) ? string.Empty : string.Format(" : {0}", classDefinition.Inherits)));
            WriteLine(classStringBuilder, "{");
            foreach (TypeDefinition typeDefinition in classDefinition.Properties)
            {
                Log.DebugFormat("typeDefinition Name is {0}", typeDefinition.Name);
                /* enum values need to be submitted as strings */
                if (typeDefinition.EnumValues != null)
                {
                    WriteLine(classStringBuilder, "[JsonConverter(typeof(StringEnumConverter))]");
                }

                string typeDefinitionTypeName = string.IsNullOrWhiteSpace(typeDefinition.TypeName) ? "object" : typeDefinition.TypeName;
                /* Arrays are not nullable */
                typeDefinitionTypeName += (typeDefinition.IsNullableType && !typeDefinitionTypeName.EndsWith("[]")) ? "?" : string.Empty;
                Log.DebugFormat("typeDefinitionTypeName is {0}", typeDefinitionTypeName);
                string typeDefinitionName = SwaggerParser.FixTypeName(typeDefinition.Name);
                Log.DebugFormat("typeDefinitionName is {0}", typeDefinitionName);
                WriteLine(classStringBuilder, string.Format("public {0} @{1} {{ get; set; }}", typeDefinitionTypeName, typeDefinitionName));
                if (typeDefinition.EnumValues != null)
                {
                    modelEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = typeDefinitionTypeName, Values = typeDefinition.EnumValues });
                }
            }

            foreach (XCase.ProxyGenerator.REST.Enum modelEnum in modelEnums)
            {
                CSharpCodeProvider csharpCodeProvider = new CSharpCodeProvider();
                WriteLine(classStringBuilder, string.Format("public enum {0}", csharpCodeProvider.CreateValidIdentifier(modelEnum.Name)));
                WriteLine(classStringBuilder, "{");
                foreach (string value in modelEnum.Values.Distinct())
                {
                    WriteLine(classStringBuilder, string.Format("[EnumMember(Value=\"{0}\")]", value));
                    WriteLine(classStringBuilder, string.Format("{0},", SwaggerParser.FixTypeName(value)));
                }

                WriteLine(classStringBuilder, "}");
                WriteLine(classStringBuilder);
            }

            WriteLine(classStringBuilder, "}");
            WriteLine(classStringBuilder);
            WriteLine(classStringBuilder, "}");
            return classStringBuilder;
        }

        private static StringBuilder CreateProxyStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend, string username, string password, string tenant)
        {
            Log.DebugFormat("starting CreateProxyStringBuilderForProxy()");
            StringBuilder proxyStringBuilder = new StringBuilder();
            WriteLine(proxyStringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(proxyStringBuilder);
            WriteLine(proxyStringBuilder, "/// <summary>");
            WriteLine(proxyStringBuilder, string.Format("/// Web Proxy for {0}", proxy));
            WriteLine(proxyStringBuilder, "/// </summary>");
            string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
            WriteLine(proxyStringBuilder, string.Format("public class {0} : {1}, I{0}", className, endPoint.GetBaseProxyClass()));
            WriteLine(proxyStringBuilder, "{");
            PrintLogger(proxyStringBuilder);
            WriteLine(proxyStringBuilder, string.Format("public {0}{1}", className, endPoint.GetProxyConstructorSuffix()));
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, string.Format("_username = \"{0}\";", username));
            WriteLine(proxyStringBuilder, string.Format("_password = \"{0}\";", password));
            WriteLine(proxyStringBuilder, string.Format("_tenantId = \"{0}\";", tenant));
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums = new List<XCase.ProxyGenerator.REST.Enum>();
            /* Async operations (web methods) */
            string proxyName = proxy;
            foreach (Operation operation in proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxyName)))
            {
                WriteOperationToStringBuilder(operation, proxyStringBuilder, proxyParamEnums, methodNameAppend);
            }

            foreach (XCase.ProxyGenerator.REST.Enum proxyParamEnum in proxyParamEnums)
            {
                WriteLine(proxyStringBuilder, string.Format("public enum {0}", SwaggerParser.FixTypeName(proxyParamEnum.Name)));
                WriteLine(proxyStringBuilder, "{");
                foreach (string enumValue in proxyParamEnum.Values.Distinct())
                {
                    WriteLine(proxyStringBuilder, string.Format("{0},", SwaggerParser.FixTypeName(enumValue)));
                }

                WriteLine(proxyStringBuilder, "}");
                WriteLine(proxyStringBuilder);
            }

            // close class def
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            // close namespace
            WriteLine(proxyStringBuilder, "}");
            return proxyStringBuilder;
        }

        private static void WriteOperationToStringBuilder(Operation operation, StringBuilder proxyStringBuilder, List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums, string methodNameAppend)
        {
            Log.DebugFormat("starting WriteOperationToStringBuilder()");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
            Log.DebugFormat("returnType is {0}", returnType);
            IEnumerable<Parameter> enums = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
            foreach (Parameter enumParam in enums)
            {
                enumParam.Type.TypeName = operation.OperationId + enumParam.Type.Name;
                proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParam.Type.TypeName, Values = enumParam.Type.EnumValues });
            }

            string parameters = string.Empty;
            try
            {
                parameters = string.Join(", ", operation.Parameters.OrderByDescending(i => i.IsRequired).Select(x => (x.IsRequired == false) ? string.Format("{0} {1} = {2}", GetDefaultType(x), x.Type.GetCleanTypeName(), GetDefaultValue(x)) : string.Format("{0} {1}", GetDefaultType(x), x.Type.GetCleanTypeName())));
            }
            catch (Exception e)
            {
                Log.Warn("exception setting parameters: " + e.Message);
            }

            WriteLine(proxyStringBuilder, "/// <summary>");
            string summary = (SecurityElement.Escape(operation.Description) ?? "").Replace("\n", "\n///");
            if (string.IsNullOrWhiteSpace(summary))
            {
                WriteLine(proxyStringBuilder, "///");
            }
            else
            {
                WriteLine(proxyStringBuilder, string.Format("/// {0}", summary));
            }

            WriteLine(proxyStringBuilder, "/// </summary>");
            foreach (Parameter parameter in operation.Parameters)
            {
                if (parameter.Type != null)
                {
                    WriteLine(proxyStringBuilder, string.Format("/// <param name=\"{0}\">{1}</param>", parameter.Type.Name, (SecurityElement.Escape(parameter.Description) ?? "").Replace("\n", "\n///")));
                }
            }

            string methodName = SwaggerParser.FixMethodName(operation.OperationId);
            Log.DebugFormat("methodName is {0}", methodName);
            string returnTypeName = SwaggerParser.FixTypeName(returnType);
            Log.DebugFormat("returnTypeName is {0}", returnTypeName);
            WriteLine(proxyStringBuilder, string.Format("public {0} {1}{2}({3})", returnTypeName, methodName, methodNameAppend, parameters));
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, string.Format("Log.Debug(\"starting {0}()\");", methodName));
            if (operation.Path.StartsWith("/"))
            {
                WriteLine(proxyStringBuilder, string.Format("var url = \"{0}\"", operation.Path.Substring(1)));
            }
            else
            {
                WriteLine(proxyStringBuilder, string.Format("var url = \"{0}\"", operation.Path));
            }

            foreach (Parameter parameter in operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Path))
            {
                WriteLine(proxyStringBuilder, string.Format("\t.Replace(\"{{{0}}}\", EncodeParameter({0}))", parameter.Type.GetCleanTypeName().Substring(1)));
            }

            WriteLine(proxyStringBuilder, ";");
            List<Parameter> queryParams = operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Query).ToList();
            if (queryParams.Count > 0)
            {
                foreach (Parameter parameter in queryParams)
                {
                    WriteParameterToProxyStringBuilder(parameter, proxyStringBuilder);
                }
            }

            WriteLine(proxyStringBuilder);
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"url is {0}\", url);");
            WriteLine(proxyStringBuilder, "using (HttpClient apiClient = BuildHttpClient())");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"about to invoke method using url {0}\", url);");
            string method = operation.Method.ToUpperInvariant();
            Log.DebugFormat("method is {0}", method);
            WriteLine(proxyStringBuilder, string.Format("Log.DebugFormat(\"method is {0}\");", method));
            WriteMethod(operation, proxyStringBuilder, method);
            WriteLine(proxyStringBuilder, "}"); // close up the using
            WriteLine(proxyStringBuilder, "}"); // close up the method
            WriteLine(proxyStringBuilder);
        }

        private static void WriteMethod(Operation operation, StringBuilder proxyStringBuilder, string method)
        {
            Log.DebugFormat("starting WriteMethod()");
            string httpMethod = "System.Net.Http.HttpMethod.Post";
            if (!string.IsNullOrEmpty(method))
            {
                switch(method)
                {
                    case "DELETE":
                        httpMethod = "System.Net.Http.HttpMethod.Delete";
                        break;
                    case "GET":
                        httpMethod = "System.Net.Http.HttpMethod.Get";
                        break;
                    case "PATCH":
                        httpMethod = "new System.Net.Http.HttpMethod(\"PATCH\")";
                        break;
                    case "POST":
                        httpMethod = "System.Net.Http.HttpMethod.Post";
                        break;
                    case "PUT":
                        httpMethod = "System.Net.Http.HttpMethod.Put";
                        break;
                    default:
                        Log.WarnFormat("unrecognized method {0}", method);
                        break;
                }
            }

            Log.DebugFormat("httpMethod is {0}", httpMethod);
            WriteLine(proxyStringBuilder, "string requestURL = string.Format(\"{0}{1}\", apiClient.BaseAddress, url);");
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"requestURL is {0}\", requestURL);");
            string httpRequestMessage = string.Format("HttpRequestMessage httpRequestMessage = new HttpRequestMessage() {{RequestUri = new Uri(requestURL), Method = {0}}};", httpMethod);
            Log.DebugFormat("httpRequestMessage is {0}", httpRequestMessage);
            WriteLine(proxyStringBuilder, httpRequestMessage);
            //WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(\"application/json\"));");
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(\"application/vnd.intapp+json\"));");
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Authorization = new AuthenticationHeaderValue(\"Bearer\", token);");
            //WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Add(\"IntegrateAuthenticationToken\", token);");
            Parameter bodyParameter = operation.Parameters.FirstOrDefault(p => p.ParameterIn == ParameterIn.Body);
            if (bodyParameter != null && method != "GET")
            {
                Log.DebugFormat("bodyParameter is not null and method is {0}", method);
                WriteLine(proxyStringBuilder, "JsonSerializerSettings jsonSerializerSettings = new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore };");
                string bodyParameterTypeName = bodyParameter.Type.GetCleanTypeName();
                WriteLine(proxyStringBuilder, string.Format("string bodyJson = JsonConvert.SerializeObject({0});", bodyParameterTypeName));
                //WriteLine(proxyStringBuilder, string.Format("string bodyJson = JsonConvert.SerializeObject({0}, settings);", bodyParameterTypeName));
                WriteLine(proxyStringBuilder, "Log.DebugFormat(\"bodyJson is {0}\", bodyJson);");
                WriteLine(proxyStringBuilder, "httpRequestMessage.Content = new StringContent(bodyJson, Encoding.UTF8, \"application/json\");");
            }
            else if (operation.Parameters.Any(p => p.ParameterIn == ParameterIn.FormData))
            {
                List<Parameter> formData = operation.Parameters.Where(i => i.ParameterIn == ParameterIn.FormData).ToList();
                WriteLine(proxyStringBuilder, "List<KeyValuePair<string, string>> formKeyValuePairs = new List<KeyValuePair<string, string>>();");
                foreach (Parameter formParam in formData.Where(p => p.Type.TypeName != "file"))
                {
                    if (formParam.IsRequired == false)
                    {
                        WriteNullIfStatementOpening(proxyStringBuilder, formParam.Type.Name, formParam.Type.TypeName);
                    }

                    WriteLine(proxyStringBuilder, string.Format("formKeyValuePairs.Add(new KeyValuePair<string, string>(\"{0}\", {0}));", formParam.Type.Name));
                    if (formParam.IsRequired == false)
                    {
                        WriteLine(proxyStringBuilder, "}");
                    }
                }

                foreach (Parameter formParameter in formData.Where(p => p.Type.TypeName == "file"))
                {
                    WriteLine(proxyStringBuilder, string.Format("ByteArrayContent {0}Content = new ByteArrayContent({0}.Item2);", formParameter.Type.Name));
                    WriteLine(proxyStringBuilder, string.Format("{0}Content.Headers.ContentDisposition = new ContentDispositionHeaderValue(\"attachment\") {{ FileName = {0}.Item1 }};", formParameter.Type.Name));
                }

                WriteLine(proxyStringBuilder, "httpRequestMessage.Content = new FormUrlEncodedContent(formKeyValuePairs);");
            }
            else if (method != "GET")
            {
                WriteLine(proxyStringBuilder, "httpRequestMessage.Content = new StringContent(string.Empty);");
            }

            WriteLine(proxyStringBuilder, "HttpResponseMessage response = apiClient.SendAsync(httpRequestMessage).Result;");
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"response StatusCode is {0}\", response.StatusCode.ToString());");
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"response StatusCode is {0}\", (int)response.StatusCode);");
            WriteLine(proxyStringBuilder, "if (response.IsSuccessStatusCode)");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "Log.DebugFormat(\"invoked method successfully\");");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
            string returnTypeName = SwaggerParser.FixTypeName(returnType);
            if (!string.IsNullOrEmpty(returnType) && returnType != "void")
            {
                WriteLine(proxyStringBuilder, "string content = response.Content.ReadAsStringAsync().Result;");
                WriteLine(proxyStringBuilder, "Log.DebugFormat(\"content is {0}\", content);");
                WriteLine(proxyStringBuilder, string.Format("{0} returnObject = JsonConvert.DeserializeObject<{0}>(content);", returnTypeName));
                WriteLine(proxyStringBuilder, "Log.DebugFormat(\"deserialized object\");");
                WriteLine(proxyStringBuilder, "return returnObject;");
            }

            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder, "else");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "throw new Exception(\"Failure invoking method: \" + response.StatusCode + \":\" + response.Content.ReadAsStringAsync().Result);");
            WriteLine(proxyStringBuilder, "}");
        }

        private static void WriteParameterToProxyStringBuilder(Parameter parameter, StringBuilder proxyStringBuilder)
        {
            Log.DebugFormat("starting WriteParameterToProxyStringBuilder()");
            if (parameter.IsRequired == false && (parameter.Type.EnumValues == null || parameter.Type.EnumValues.Any() == false))
            {
                WriteNullIfStatementOpening(proxyStringBuilder, parameter.Type.GetCleanTypeName(), parameter.Type.TypeName);
            }

            if (string.IsNullOrWhiteSpace(parameter.CollectionFormat) == false)
            {
                if (parameter.CollectionFormat.Equals("csv", StringComparison.OrdinalIgnoreCase))
                {
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", string.Join(\",\", {1}));", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
                }
                else if (parameter.CollectionFormat.Equals("ssv", StringComparison.OrdinalIgnoreCase))
                {
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", string.Join(\" \", {1}));", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
                }
                else if (parameter.CollectionFormat.Equals("tsv", StringComparison.OrdinalIgnoreCase))
                {
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", string.Join(\"\t\", {1}));", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
                }
                else if (parameter.CollectionFormat.Equals("pipes", StringComparison.OrdinalIgnoreCase))
                {
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", string.Join(\"\t\", {1}));", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
                }
                else if (parameter.CollectionFormat.Equals("multi", StringComparison.OrdinalIgnoreCase))
                {
                    WriteLine(proxyStringBuilder, string.Format("foreach(var item in {0})", parameter.Type.GetCleanTypeName()));
                    WriteLine(proxyStringBuilder, "{");
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", item.ToString());", parameter.Type.Name));
                    WriteLine(proxyStringBuilder, "}");
                }
                else
                {
                    WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", {1}.ToString());", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
                }
            }
            else
            {
                WriteLine(proxyStringBuilder, string.Format("url = AppendQuery(url, \"{0}\", EncodeParameter({1}));", parameter.Type.Name, parameter.Type.GetCleanTypeName()));
            }

            if (parameter.IsRequired == false && (parameter.Type.EnumValues == null || parameter.Type.EnumValues.Any() == false))
            {
                WriteLine(proxyStringBuilder, "}");
            }
        }

        private static StringBuilder CreateInterfaceStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend)
        {
            StringBuilder interfaceStringBuilder = new StringBuilder();
            WriteLine(interfaceStringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(interfaceStringBuilder);
            WriteLine(interfaceStringBuilder, string.Format("public interface {0}", string.Format("I{0}WebProxy", SwaggerParser.FixTypeName(proxy))));
            WriteLine(interfaceStringBuilder, "{");
            string proxy1 = proxy;
            foreach (Operation operationDef in proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxy1)))
            {
                string returnType = string.IsNullOrEmpty(operationDef.ReturnType) ? "void" : string.Format("{0}", operationDef.ReturnType);
                IEnumerable<Parameter> enums = operationDef.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
                List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums = new List<XCase.ProxyGenerator.REST.Enum>();
                foreach (Parameter enumParam in enums)
                {
                    enumParam.Type.TypeName = operationDef.OperationId + enumParam.Type.Name;
                    proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParam.Type.TypeName, Values = enumParam.Type.EnumValues });
                }

                string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                /* Sort by required so that non-nullable parameters come first */
                IEnumerable<Parameter> parameterEnumerable = operationDef.Parameters.OrderByDescending(i => i.IsRequired);
                string parameters = string.Join(", ", parameterEnumerable.Select(p =>
                {
                    /* if parameter is enum include the namespace */
                    string parameter = (p.Type != null && p.Type.EnumValues != null) ? string.Format("{0}.{1}.", endPoint.GetNamespace(), className) : string.Empty;
                    if (p.Type != null)
                    {
                        if (p.IsRequired)
                        {
                            parameter += string.Format("{0} {1}", GetDefaultType(p), p.Type.GetCleanTypeName());
                        }
                        else
                        {
                            parameter += string.Format("{0} {1} = {2}{3}", GetDefaultType(p), p.Type.GetCleanTypeName(), parameter, GetDefaultValue(p));
                        }
                    }

                    return parameter;
                }));
                string returnTypeName = SwaggerParser.FixTypeName(returnType);
                string methodLine = string.Format("{0} {1}{2}({3});", returnTypeName, SwaggerParser.FixMethodName(operationDef.OperationId), methodNameAppend, parameters);
                WriteLine(interfaceStringBuilder, methodLine);
            }

            // close interface
            WriteLine(interfaceStringBuilder, "}");
            // close namespace
            WriteLine(interfaceStringBuilder, "}");
            return interfaceStringBuilder;
        }

        private static RESTServiceDefinition ProcessSwaggerDocuments()
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            return ProcessSwaggerDocuments("Admin", "1nt@ppC10ud2016", "tenant1");
        }

        private static void PrintLogger(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "private static readonly new ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);");
            WriteLine(stringBuilder);
        }

        static string GetDefaultType(Parameter parameter)
        {
            if (parameter.Type != null)
            {
                string typeName = parameter.Type.TypeName;
                if (typeName == "file")
                {
                    return "Tuple<string, byte[]>";
                }

                if (!parameter.IsRequired && parameter.Type.IsNullableType)
                {
                    typeName += "?";
                }

                typeName = typeName
                    .Replace(":", "_Colon_")
                    .Replace("-", "_Dash_")
                    .Replace(".", "_Dot_")
                    .Replace("(", "_Left_")
                    .Replace("%", "_Percent_")
                    .Replace(")", "_Right_")
                    .Replace(" ", "_Space_");
                /* Ugly hack and will not work if type is both array and contains [ or ] */
                if (!typeName.Contains("[]"))
                {
                    typeName = typeName.Replace("[", "___").Replace("]", "___");
                }

                return typeName;
            }
            else
            {
                return "string";
            }
        }

        static string GetDefaultValue(Parameter parameter)
        {
            if (!parameter.Type.IsNullableType && parameter.CollectionFormat != "multi" && parameter.Type.EnumValues != null && parameter.Type.EnumValues.Any())
            {
                return string.Format("{0}.{1}", GetDefaultType(parameter), parameter.Type.EnumValues.FirstOrDefault());
            }

            return "null";
        }

        static void PrintHeaders(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder, "/* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */");
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "using System;");
            WriteLine(stringBuilder, "using System.Collections.Generic;");
            WriteLine(stringBuilder, "using System.Threading.Tasks;");
            WriteLine(stringBuilder, "using System.Net;");
            WriteLine(stringBuilder, "using System.Net.Http;");
            WriteLine(stringBuilder, "using System.Net.Http.Formatting;");
            WriteLine(stringBuilder, "using System.Net.Http.Headers;");
            WriteLine(stringBuilder, "using System.Reflection;");
            WriteLine(stringBuilder, "using System.Runtime.Serialization;");
            WriteLine(stringBuilder, "using System.Text;");
            WriteLine(stringBuilder, "using System.Web;");
            WriteLine(stringBuilder, "using log4net;");
            WriteLine(stringBuilder, "using Newtonsoft.Json;");
            WriteLine(stringBuilder, "using Newtonsoft.Json.Converters;");
            WriteLine(stringBuilder, "using Newtonsoft.Json.Linq;");
            WriteLine(stringBuilder, "using XCase.REST.ProxyGenerator;");
            WriteLine(stringBuilder);
        }

        static void WriteNullIfStatementOpening(StringBuilder stringBuilder, string parameterName, string typeName)
        {
            if (IsIntrinsicType(typeName))
            {
                WriteLine(stringBuilder, string.Format("if ({0}.HasValue){{", parameterName));
            }
            else
            {
                WriteLine(stringBuilder, string.Format("if ({0} != null){{", parameterName));
            }
        }

        static bool IsIntrinsicType(string typeName)
        {
            switch (typeName.ToLowerInvariant())
            {
                case "int":
                case "long":
                case "byte":
                case "DateTime":
                case "float":
                case "double":
                    return true;
                default:
                    return false;
            }
        }

        public static RESTApiProxySettings GetSettings(string path)
        {
            using (FileStream settingStream = File.OpenRead(path))
            {
                StreamReader streamReader = new StreamReader(settingStream);
                string value = streamReader.ReadToEnd();
                return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
            }
        }
    }
}
