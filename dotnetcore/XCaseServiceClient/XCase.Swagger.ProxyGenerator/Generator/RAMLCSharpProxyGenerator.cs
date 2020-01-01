using log4net;
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
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

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
                Log.Warn("aggregate exception generating source string: " + ae.Message);
                throw;
            }
            catch (Exception e)
            {
                Log.Warn("exception generating source string: " + e.Message);
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
                    Log.DebugFormat("about to add task for {0}", requestUri);
                    taskList.Add(GetEndpointRAMLDoc(requestUri, endPoint));
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WaitAll(taskList.ToArray());
                Log.Debug("REST documents completed downloading");
                return ProcessRAMLDocuments();
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
            Log.DebugFormat("created RAMLParser");
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
            string endPointString = proxyDefinition.Host;
            Log.DebugFormat("proxyDefinition.Host is {0}", proxyDefinition.Host);
            //string endPointString = string.Format("{0}://{1}{2}", scheme, proxyDefinition.Host, proxyDefinition.BasePath);
            if (!endPointString.EndsWith("/"))
            {
                endPointString = string.Format(endPointString + "{0}", "/");
            }

            Log.DebugFormat("endPointString is {0}", endPointString);
            if (endPointString.StartsWith("http"))
            {
                ramlServiceDefinition.EndPoint = endPointString;
            }
            else
            {
                ramlServiceDefinition.EndPoint = "http://localhost:8080/api/v3/";
            }

            List<string> proxies = proxyDefinition.Operations.Select(i => i.ProxyName).Distinct().ToList();
            /* Interface for proxy classes */
            foreach (string proxy in proxies)
            {
                Log.DebugFormat("*** next proxy {0} ***", proxy);
                StringBuilder interfaceStringBuilder = CreateInterfaceStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                Log.DebugFormat("created interfaceStringBuilder for {0}", proxy);
                sourceStringList.Add(interfaceStringBuilder.ToString());
                Log.DebugFormat("*** added interface for proxy {0} ***", proxy);
                string className = OpenApiParser.FixTypeName(proxy) + "WebProxy";
                ramlServiceDefinition.ProxyClasses.Add(className);
                //Log.DebugFormat("added className {0}", className);
                //Log.DebugFormat("*** added className for proxy {0} ***", proxy);
                StringBuilder proxyStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend, username, password, tenant);
                Log.DebugFormat("created proxyStringBuilder for {0}", proxy);
                sourceStringList.Add(proxyStringBuilder.ToString());
                Log.DebugFormat("*** finished proxy {0} ***", proxy);
            }

            /* Main proxy classes */
            foreach (string proxy in proxies)
            {

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
                string typeDefinitionName = OpenApiParser.FixTypeName(typeDefinition.Name);
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
                    WriteLine(classStringBuilder, string.Format("{0},", OpenApiParser.FixTypeName(value)));
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
            string className = OpenApiParser.FixTypeName(proxy) + "WebProxy";
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
                Log.DebugFormat("next operation");
                IOrderedEnumerable<Parameter> parameterEnumerable = operation.Parameters.OrderByDescending(i => i.IsRequired);
                foreach (Parameter parameter in parameterEnumerable)
                {
                    Log.DebugFormat("* parameter.Type.TypeName is {0} *", parameter.Type.TypeName);
                    Log.DebugFormat("* default type is {0} *", GetDefaultType(parameter));
                }

                WriteOperationToStringBuilder(operation, proxyStringBuilder, endPoint, proxyParamEnums, methodNameAppend);
                Log.DebugFormat("finished writing operation to proxyStringBuilder");
            }

            Log.DebugFormat("proxyParamEnums Count is {0}", proxyParamEnums.Count);
            foreach (XCase.ProxyGenerator.REST.Enum proxyParamEnum in proxyParamEnums)
            {
                Log.DebugFormat("next proxyParamEnum");
                string proxyParamEnumName = OpenApiParser.FixTypeName(proxyParamEnum.Name);
                Log.DebugFormat("proxyParamEnumName is {0}", proxyParamEnumName);
                WriteLine(proxyStringBuilder, string.Format("public enum {0}", proxyParamEnumName));
                WriteLine(proxyStringBuilder, "{");
                foreach (string enumValue in proxyParamEnum.Values.Distinct())
                {
                    WriteLine(proxyStringBuilder, string.Format("{0},", OpenApiParser.FixTypeName(enumValue)));
                }

                WriteLine(proxyStringBuilder, "}");
                WriteLine(proxyStringBuilder);
                Log.DebugFormat("finished writing proxyParamEnum to proxyStringBuilder");
            }

            // close class def
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            // close namespace
            WriteLine(proxyStringBuilder, "}");
            return proxyStringBuilder;
        }

        private static void WriteOperationToStringBuilder(Operation operation, StringBuilder proxyStringBuilder, IAPIProxySettingsEndpoint endPoint, List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums, string methodNameAppend)
        {
            Log.DebugFormat("starting WriteOperationToStringBuilder()");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
            Log.DebugFormat("returnType is {0}", returnType);
            IEnumerable<Parameter> enumParameters = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
            foreach (Parameter enumParameter in enumParameters)
            {
                Log.DebugFormat("next enumParameter {0}", enumParameter.Type.Name);
                enumParameter.Type.TypeName = operation.OperationId + enumParameter.Type.Name;
                proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParameter.Type.TypeName, Values = enumParameter.Type.EnumValues });
            }

            string parameters = string.Empty;
            IOrderedEnumerable<Parameter> parameterEnumerable = operation.Parameters.OrderByDescending(i => i.IsRequired);
            foreach (Parameter parameter in parameterEnumerable)
            {
                Log.DebugFormat("parameter.Type.TypeName is {0}", parameter.Type.TypeName);
                Log.DebugFormat("default type is {0}", GetDefaultType(parameter));
            }

            try
            {
                parameters = string.Join(", ", operation.Parameters.OrderByDescending(i => i.IsRequired).Select(p => (p.IsRequired == false) ? string.Format("{0} {1} = {2}", GetDefaultType(p), p.Type.GetCleanTypeName(), GetDefaultValue(p)) : string.Format("{0} {1}", GetDefaultType(p), p.Type.GetCleanTypeName())));
            }
            catch (Exception e)
            {
                Log.Warn("exception setting parameters: " + e.Message);
            }

            Log.DebugFormat("parameters is {0}", parameters);
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

            string methodName = OpenApiParser.FixMethodName(operation.OperationId);
            Log.DebugFormat("methodName is {0}", methodName);
            string returnTypeName = OpenApiParser.FixTypeName(returnType);
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
            WriteMethod(operation, proxyStringBuilder, endPoint, method);
            WriteLine(proxyStringBuilder, "}"); // close up the using
            WriteLine(proxyStringBuilder, "}"); // close up the method
            WriteLine(proxyStringBuilder);
        }

        private static void WriteMethod(Operation operation, StringBuilder proxyStringBuilder, IAPIProxySettingsEndpoint endPoint, string method)
        {
            Log.DebugFormat("starting WriteMethod()");
            string httpMethod = "System.Net.Http.HttpMethod.Post";
            if (!string.IsNullOrEmpty(method))
            {
                switch (method)
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
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(\"" + endPoint.GetAccept() + "\"));");
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Authorization = new AuthenticationHeaderValue(\"" + endPoint.GetTokenName() + "\", token);");
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
            string returnTypeName = OpenApiParser.FixTypeName(returnType);
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
            Log.DebugFormat("starting CreateInterfaceStringBuilderForProxy()");
            StringBuilder interfaceStringBuilder = new StringBuilder();
            WriteLine(interfaceStringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(interfaceStringBuilder);
            WriteLine(interfaceStringBuilder, string.Format("public interface {0}", string.Format("I{0}WebProxy", OpenApiParser.FixTypeName(proxy))));
            WriteLine(interfaceStringBuilder, "{");
            Log.DebugFormat("proxy is {0}", proxy);
            string className = OpenApiParser.FixTypeName(proxy) + "WebProxy";
            Log.DebugFormat("className is {0}", className);
            string proxyName = proxy;
            foreach (Operation operation in proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxyName)))
            {
                string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
                /* Sort by required so that non-nullable parameters come first */
                IOrderedEnumerable<Parameter> parameterEnumerable = operation.Parameters.OrderByDescending(i => i.IsRequired);
                foreach (Parameter parameter in parameterEnumerable)
                {
                    Log.DebugFormat("* parameter.Type.TypeName is {0} *", parameter.Type.TypeName);
                    Log.DebugFormat("* default type is {0} *", GetDefaultType(parameter));
                }

                IEnumerable<Parameter> enumParameters = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
                foreach (Parameter enumParameter in enumParameters)
                {
                    Log.DebugFormat("next enumParameter {0}", enumParameter.Type.Name);
                    enumParameter.Type.TypeName = operation.OperationId + enumParameter.Type.Name;
                }

                string parameters = string.Join(", ", parameterEnumerable.Select(p =>
                {
                    /* if parameter is enum include the namespace */
                    string parameter = (p.Type != null && p.Type.EnumValues != null) ? string.Format("{0}.{1}.", endPoint.GetNamespace(), className) : string.Empty;
                    Log.DebugFormat("parameter is {0}", parameter);
                    if (p.Type != null)
                    {
                        Log.DebugFormat("p.Type Name is {0}", p.Type.Name);
                        Log.DebugFormat("p.Type TypeName is {0}", p.Type.TypeName);
                        Log.DebugFormat("p default type is {0}", GetDefaultType(p));
                        if (p.IsRequired)
                        {
                            parameter += string.Format("{0} {1}", GetDefaultType(p), p.Type.GetCleanTypeName());
                        }
                        else
                        {
                            parameter += string.Format("{0} {1} = {2}{3}", GetDefaultType(p), p.Type.GetCleanTypeName(), parameter, GetDefaultValue(p));
                        }
                    }

                    Log.DebugFormat("parameter is {0}", parameter);
                    return parameter;
                }));
                Log.DebugFormat("parameters is {0}", parameters);
                string returnTypeName = OpenApiParser.FixTypeName(returnType);
                string methodLine = string.Format("{0} {1}{2}({3});", returnTypeName, OpenApiParser.FixMethodName(operation.OperationId), methodNameAppend, parameters);
                Log.DebugFormat("methodLine is {0}", methodLine);
                WriteLine(interfaceStringBuilder, methodLine);
            }

            Log.DebugFormat("finished writing method lines for {0} interface", className);
            // close interface
            WriteLine(interfaceStringBuilder, "}");
            // close namespace
            WriteLine(interfaceStringBuilder, "}");
            return interfaceStringBuilder;
        }

        private static void PrintLogger(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "private static readonly new ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);");
            WriteLine(stringBuilder);
        }

        private static string GetDefaultType(Parameter parameter)
        {
            //Log.DebugFormat("starting GetDefaultType(Parameter parameter)");
            if (parameter.Type != null)
            {
                //Log.DebugFormat("parameter.Type is not null");
                string typeName = parameter.Type.TypeName;
                //Log.DebugFormat("typeName is {0}", typeName);
                if (typeName == "file")
                {
                    return "Tuple<string, byte[]>";
                }
                else if (typeName == "integer")
                {
                    return "int";
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

                //Log.DebugFormat("about to return {0}", typeName);
                return typeName;
            }
            else
            {
                Log.DebugFormat("parameter.Type is null");
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
            WriteLine(stringBuilder, "using XCase.REST.ProxyGenerator.Proxy;");
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
            using FileStream settingStream = File.OpenRead(path);
            StreamReader streamReader = new StreamReader(settingStream);
            string value = streamReader.ReadToEnd();
            return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
        }
    }
}
