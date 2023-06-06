namespace XCase.REST.ProxyGenerator.Generator
{
    using Microsoft.Extensions.Configuration;
    using Microsoft.Extensions.Logging;
    using Microsoft.CSharp;
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
    using XCase.REST.ProxyGenerator.OpenAPI;
    using XCase.Swagger.ProxyGenerator.OpenAPI;
    using Serilog;
    using Serilog.Core;
    using Serilog.Events;
    using Serilog.Formatting.Json;
    using Serilog.Configuration;
    using Serilog.Settings;

    public abstract class CSharpProxyGenerator : IProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        public static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();

        #endregion

        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        public abstract IServiceDefinition GenerateSourceString(string document);

        public static int TextPadding { get; set; }
        public static StringBuilder SourceStringBuilder { get; set; }
        public static ConcurrentDictionary<IAPIProxySettingsEndpoint, string> swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
        public static ConcurrentDictionary<IAPIProxySettingsEndpoint, string> ramlDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();

        public static StringBuilder WriteClassDefinitionToStringBuilder(ClassDefinition classDefinition, IAPIProxySettingsEndpoint endPoint)
        {
            Log.Debug("starting WriteClassDefinitionToStringBuilder()");
            StringBuilder classStringBuilder = new StringBuilder();
            WriteLine(classStringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(classStringBuilder);
            List<XCase.ProxyGenerator.REST.Enum> modelEnums = new List<XCase.ProxyGenerator.REST.Enum>();
            WriteLine(classStringBuilder, string.Format("public class {0}{1}", classDefinition.GetCleanClassName(), string.IsNullOrEmpty(classDefinition.Inherits) ? string.Empty : string.Format(" : {0}", classDefinition.Inherits)));
            WriteLine(classStringBuilder, "{");
            foreach (TypeDefinition typeDefinition in classDefinition.Properties)
            {
                Log.Debug("typeDefinition Name is {0}", typeDefinition.Name);
                /* enum values need to be submitted as strings */
                if (typeDefinition.EnumValues != null)
                {
                    WriteLine(classStringBuilder, "[JsonConverter(typeof(StringEnumConverter))]");
                }

                string typeDefinitionTypeName = string.IsNullOrWhiteSpace(typeDefinition.TypeName) ? "object" : typeDefinition.TypeName;
                /* Arrays are not nullable */
                typeDefinitionTypeName += (typeDefinition.IsNullableType && !typeDefinitionTypeName.EndsWith("[]")) ? "?" : string.Empty;
                Log.Debug("typeDefinitionTypeName is {0}", typeDefinitionTypeName);
                string typeDefinitionName = SwaggerParser.FixTypeName(typeDefinition.Name);
                Log.Debug("typeDefinitionName is {0}", typeDefinitionName);
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

        public static StringBuilder CreateInterfaceStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend)
        {
            Log.Debug("starting CreateInterfaceStringBuilderForProxy()");
            StringBuilder stringBuilder = new StringBuilder();
            WriteLine(stringBuilder, string.Format("namespace {0} {{", endPoint.GetNamespace()));
            PrintHeaders(stringBuilder);
            WriteLine(stringBuilder, string.Format("public interface {0}", string.Format("I{0}WebProxy", OpenApiParser.FixTypeName(proxy))));
            WriteLine(stringBuilder, "{");
            string proxyName = proxy;
            IEnumerable<Operation> operationEnumerable = proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxyName));
            foreach (Operation operation in operationEnumerable)
            {
                string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
                Log.Debug("returnType is {0}", returnType);
                /* 
                 * Adjust the type names of the enum parameters in order to reference their types
                 * declared in the Proxy class.
                 */
                IEnumerable<Parameter> enumParameterEnumerable = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
                foreach (Parameter enumParameter in enumParameterEnumerable)
                {
                    Log.Debug("next enumParameter {0}", enumParameter.Type.Name);
                    enumParameter.Type.TypeName = operation.OperationId + enumParameter.Type.Name + "Enum";
                }

                string className = OpenApiParser.FixTypeName(proxy) + "WebProxy";
                /* Sort by required so that non-nullable parameters come first */
                IEnumerable<Parameter> parameterEnumerable = operation.Parameters.OrderByDescending(parameter => parameter.IsRequired);
                string parameters = string.Join(", ", parameterEnumerable.Select(p =>
                {
                    /* if parameter is enum include the namespace */
                    string parameter = (p.Type != null && p.Type.EnumValues != null) ? string.Format("{0}.{1}.", endPoint.GetNamespace(), className) : string.Empty;
                    if (p.Type != null)
                    {
                        string defaultType = GetDefaultType(p);
                        string cleanTypeName = p.Type.GetCleanTypeName();
                        string defaultValue = GetDefaultValue(p);
                        if (p.IsRequired)
                        {
                            parameter += string.Format("{0} {1}", defaultType, cleanTypeName);
                        }
                        else
                        {
                            parameter += string.Format("{0} {1} = {2}{3}", defaultType, cleanTypeName, parameter, defaultValue);
                        }
                    }

                    return parameter;
                }));
                string returnTypeName = SwaggerParser.FixTypeName(returnType);
                string methodLine = string.Format("public {0} {1}{2}({3});", returnTypeName, SwaggerParser.FixMethodName(operation.OperationId), methodNameAppend, parameters);
                WriteLine(stringBuilder, methodLine);
            }

            // close interface
            WriteLine(stringBuilder, "}");
            // close namespace
            WriteLine(stringBuilder, "}");
            return stringBuilder;
        }

        public static StringBuilder CreateProxyStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend, string username, string password, string domain)
        {
            Log.Debug("starting CreateProxyStringBuilderForProxy()");
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
            WriteLine(proxyStringBuilder, string.Format("_domain = \"{0}\";", domain));
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums = new List<XCase.ProxyGenerator.REST.Enum>();
            string proxyName = proxy;
            IEnumerable<Operation> operationEnumerable = proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxyName));
            foreach (Operation operation in operationEnumerable)
            {
                WriteOperationToStringBuilder(operation, proxyStringBuilder, endPoint, proxyParamEnums, methodNameAppend);
            }

            /* 
             * We include definitions of each Enum defined in one or more of the operations. We assume that if two Enums 
             * have the same name, then they are the same.
             */
            foreach (XCase.ProxyGenerator.REST.Enum proxyParamEnum in proxyParamEnums.Distinct<XCase.ProxyGenerator.REST.Enum>())
            {
                WriteLine(proxyStringBuilder, string.Format("public enum {0}", SwaggerParser.FixTypeName(proxyParamEnum.Name)));
                WriteLine(proxyStringBuilder, "{");
                foreach (string enumValue in proxyParamEnum.Values.Distinct())
                {
                    WriteLine(proxyStringBuilder, string.Format("{0},", XCase.ProxyGenerator.REST.Enum.FixEnumValue(enumValue)));
                }

                WriteLine(proxyStringBuilder, "}");
                WriteLine(proxyStringBuilder);
            }

            /* Close class definition */
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            /* Close namespace */
            WriteLine(proxyStringBuilder, "}");
            return proxyStringBuilder;
        }

        public static void WriteOperationToStringBuilder(Operation operation, StringBuilder stringBuilder, IAPIProxySettingsEndpoint endPoint, List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums, string methodNameAppend)
        {
            Log.Debug("starting WriteOperationToStringBuilder()");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
            Log.Debug("returnType is {0}", returnType);
            /* 
             * Adjust the type names of the enum parameters in order to reference their types
             * declared in the Proxy class.
             */
            IEnumerable<Parameter> enumParameterEnumerable = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
            foreach (Parameter enumParameter in enumParameterEnumerable)
            {
                Log.Debug("next enumParameter {0}", enumParameter.Type.Name);
                enumParameter.Type.TypeName = operation.OperationId + enumParameter.Type.Name + "Enum";
                proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParameter.Type.TypeName, Values = enumParameter.Type.EnumValues });
            }

            if (operation.SecurityScheme != null)
            {
                /* TODO: we have to decide what do with the operation RESTSecurityScheme if it exists */
            }

            WriteLine(stringBuilder, "/// <summary>");
            string summary = (SecurityElement.Escape(operation.Description) ?? "").Replace("\n", "\n///");
            if (string.IsNullOrWhiteSpace(summary))
            {
                WriteLine(stringBuilder, "///");
            }
            else
            {
                WriteLine(stringBuilder, string.Format("/// {0}", summary));
            }

            WriteLine(stringBuilder, "/// </summary>");
            foreach (Parameter parameter in operation.Parameters.OrderByDescending(parameter => parameter.IsRequired))
            {
                if (parameter.Type != null)
                {
                    WriteLine(stringBuilder, string.Format("/// <param name=\"{0}\">{1}</param>", parameter.Type.Name, (SecurityElement.Escape(parameter.Description) ?? "").Replace("\n", "\n///")));
                }
            }

            string methodName = SwaggerParser.FixMethodName(operation.OperationId);
            Log.Debug("methodName is {0}", methodName);
            string returnTypeName = SwaggerParser.FixTypeName(returnType);
            Log.Debug("returnTypeName is {0}", returnTypeName);
            /* Sort by required so that non-nullable parameters come first */
            IEnumerable<Parameter> parameterEnumerable = operation.Parameters.OrderByDescending(parameter => parameter.IsRequired);
            string parameters = string.Join(", ", parameterEnumerable.Select(p =>
            {
                string parameter = string.Empty;
                if (p.Type != null)
                {
                    string defaultType = GetDefaultType(p);
                    string cleanTypeName = p.Type.GetCleanTypeName();
                    string defaultValue = GetDefaultValue(p);
                    if (p.IsRequired)
                    {
                        parameter += string.Format("{0} {1}", defaultType, cleanTypeName);
                    }
                    else
                    {
                        parameter += string.Format("{0} {1} = {2}{3}", defaultType, cleanTypeName, parameter, defaultValue);
                    }
                }

                return parameter;
            }));
            string methodLine = string.Format("public {0} {1}{2}({3})", returnTypeName, SwaggerParser.FixMethodName(operation.OperationId), methodNameAppend, parameters);
            WriteLine(stringBuilder, methodLine);
            WriteLine(stringBuilder, "{");
            WriteLine(stringBuilder, string.Format("Log.Debug(\"starting {0}()\");", methodName));
            if (operation.Path.StartsWith("/"))
            {
                WriteLine(stringBuilder, string.Format("var url = \"{0}\"", operation.Path.Substring(1)));
            }
            else
            {
                WriteLine(stringBuilder, string.Format("var url = \"{0}\"", operation.Path));
            }

            foreach (Parameter parameter in operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Path))
            {
                WriteLine(stringBuilder, string.Format("\t.Replace(\"{{{0}}}\", EncodeParameter({0}))", parameter.Type.GetCleanTypeName().Substring(1)));
            }

            WriteLine(stringBuilder, ";");
            List<Parameter> queryParams = operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Query).ToList();
            if (queryParams.Count > 0)
            {
                foreach (Parameter parameter in queryParams)
                {
                    WriteParameterToProxyStringBuilder(parameter, stringBuilder);
                }
            }

            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "Log.Debug(\"url is {0}\", url);");
            WriteLine(stringBuilder, "using (HttpClient apiClient = BuildHttpClient())");
            WriteLine(stringBuilder, "{");
            WriteLine(stringBuilder, "Log.Debug(\"about to invoke method using url {0}\", url);");
            string method = operation.Method.ToUpperInvariant();
            Log.Debug("method is {0}", method);
            WriteLine(stringBuilder, string.Format("Log.Debug(\"method is {0}\");", method));
            WriteMethod(operation, stringBuilder, endPoint, method);
            WriteLine(stringBuilder, "}"); // close up the using
            WriteLine(stringBuilder, "}"); // close up the method
            WriteLine(stringBuilder);
        }

        public static void WriteMethod(Operation operation, StringBuilder proxyStringBuilder, IAPIProxySettingsEndpoint endPoint, string method)
        {
            Log.Debug("starting WriteMethod()");
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
                        Log.Warning("unrecognized method {0}", method);
                        break;
                }
            }

            Log.Debug("httpMethod is {0}", httpMethod);
            WriteLine(proxyStringBuilder, "string requestURL = string.Format(\"{0}{1}\", apiClient.BaseAddress, url);");
            WriteLine(proxyStringBuilder, "Log.Debug(\"requestURL is {0}\", requestURL);");
            string httpRequestMessage = string.Format("HttpRequestMessage httpRequestMessage = new HttpRequestMessage() {{RequestUri = new Uri(requestURL), Method = {0}}};", httpMethod);
            Log.Debug("httpRequestMessage is {0}", httpRequestMessage);
            WriteLine(proxyStringBuilder, httpRequestMessage);
            //WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(\"application/json\"));");
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(\"" + endPoint.GetAccept() + "\"));");
            WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Authorization = new AuthenticationHeaderValue(\"" + endPoint.GetTokenName() + "\", token);");
            //WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Add(\"IntegrateAuthenticationToken\", token);");
            List<Parameter> headerParams = operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Header).ToList();
            if (headerParams.Count > 0)
            {
                foreach (Parameter parameter in headerParams)
                {
                    WriteLine(proxyStringBuilder, "httpRequestMessage.Headers.Add(\"" + parameter.Type.Name + "\", " + parameter.Type.GetCleanTypeName() + ");");
                    WriteLine(proxyStringBuilder, "Log.Debug(\"added header is {0}\", \"" + parameter.Type.Name + ":\" + " + parameter.Type.GetCleanTypeName() + ");");
                }
            }

            Parameter bodyParameter = operation.Parameters.FirstOrDefault(p => p.ParameterIn == ParameterIn.Body);
            if (bodyParameter != null && method != "GET")
            {
                Log.Debug("bodyParameter is not null and method is {0}", method);
                WriteLine(proxyStringBuilder, "JsonSerializerSettings jsonSerializerSettings = new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore };");
                string bodyParameterTypeName = bodyParameter.Type.GetCleanTypeName();
                WriteLine(proxyStringBuilder, string.Format("string bodyJson = JsonConvert.SerializeObject({0});", bodyParameterTypeName));
                //WriteLine(proxyStringBuilder, string.Format("string bodyJson = JsonConvert.SerializeObject({0}, settings);", bodyParameterTypeName));
                WriteLine(proxyStringBuilder, "Log.Debug(\"bodyJson is {0}\", bodyJson);");
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
            WriteLine(proxyStringBuilder, "Log.Debug(\"response StatusCode is {0}\", response.StatusCode.ToString());");
            WriteLine(proxyStringBuilder, "Log.Debug(\"response StatusCode is {0}\", (int)response.StatusCode);");
            WriteLine(proxyStringBuilder, "if (response.IsSuccessStatusCode)");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "Log.Debug(\"invoked method successfully\");");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", operation.ReturnType);
            string returnTypeName = SwaggerParser.FixTypeName(returnType);
            if (!string.IsNullOrEmpty(returnType) && returnType != "void")
            {
                WriteLine(proxyStringBuilder, "string content = response.Content.ReadAsStringAsync().Result;");
                WriteLine(proxyStringBuilder, "Log.Debug(\"content is {0}\", content);");
                WriteLine(proxyStringBuilder, string.Format("{0} returnObject = JsonConvert.DeserializeObject<{0}>(content);", returnTypeName));
                WriteLine(proxyStringBuilder, "Log.Debug(\"deserialized object\");");
                WriteLine(proxyStringBuilder, "return returnObject;");
            }

            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder, "else");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "throw new Exception(\"Failure invoking method: \" + response.StatusCode + \":\" + response.Content.ReadAsStringAsync().Result);");
            WriteLine(proxyStringBuilder, "}");
        }

        public static void WriteParameterToProxyStringBuilder(Parameter parameter, StringBuilder proxyStringBuilder)
        {
            Log.Debug("starting WriteParameterToProxyStringBuilder()");
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

        public static void PrintLogger(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "private static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder().SetBasePath(Directory.GetCurrentDirectory()).AddJsonFile(\"appsettings.json\", optional: false, reloadOnChange: true).Build()).CreateLogger();");
            WriteLine(stringBuilder);
        }

        public static string GetDefaultType(Parameter parameter)
        {
            if (parameter.Type != null)
            {
                string typeName = parameter.Type.TypeName;
                if (typeName == "file")
                {
                    return "Tuple<string, byte[]>";
                }
                else if (typeName == "integer")
                {
                    typeName = "int";
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

        public static string GetDefaultValue(Parameter parameter)
        {
            if (!parameter.Type.IsNullableType && parameter.CollectionFormat != "multi" && parameter.Type.EnumValues != null && parameter.Type.EnumValues.Any())
            {
                return string.Format("{0}.{1}", GetDefaultType(parameter), parameter.Type.EnumValues.FirstOrDefault());
            }

            return "null";
        }

        public static void PrintHeaders(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder, "/* This file was generated by XCase.REST.CSharpProxyGenerator */");
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "using System;");
            WriteLine(stringBuilder, "using System.IO;");
            WriteLine(stringBuilder, "using System.Collections.Generic;");
            WriteLine(stringBuilder, "using System.Net;");
            WriteLine(stringBuilder, "using System.Net.Http;");
            WriteLine(stringBuilder, "using System.Net.Http.Headers;");
            WriteLine(stringBuilder, "using System.Reflection;");
            WriteLine(stringBuilder, "using System.Runtime.Serialization;");
            WriteLine(stringBuilder, "using System.Text;");
            WriteLine(stringBuilder, "using System.Threading.Tasks;");
            WriteLine(stringBuilder, "using System.Web;");
            WriteLine(stringBuilder, "using Microsoft.Extensions.Configuration; ");
            WriteLine(stringBuilder, "using Microsoft.Extensions.Logging;");
            WriteLine(stringBuilder, "using Newtonsoft.Json;");
            WriteLine(stringBuilder, "using Newtonsoft.Json.Converters;");
            WriteLine(stringBuilder, "using Newtonsoft.Json.Linq;");
            WriteLine(stringBuilder, "using Serilog;");
            WriteLine(stringBuilder, "using Serilog.Events;");
            WriteLine(stringBuilder, "using XCase.REST.ProxyGenerator;");
            WriteLine(stringBuilder, "using XCase.REST.ProxyGenerator.Proxy;");
            WriteLine(stringBuilder);
        }

        public static void WriteNullIfStatementOpening(StringBuilder stringBuilder, string parameterName, string typeName)
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

        public static bool IsIntrinsicType(string typeName)
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

        public static void WriteLine(StringBuilder stringBuilder)
        {
            stringBuilder.AppendLine(string.Empty);
        }

        public static void WriteLine(StringBuilder stringBuilder, string text)
        {
            if (text == "}" && TextPadding != 0)
            {
                TextPadding--;
            }

            string textPadding = new string(' ', TextPadding * 4);
            stringBuilder.AppendLine(string.Format("{1}{0}", text, textPadding));
            if (text.EndsWith("{"))
            {
                TextPadding++;
            }
        }
    }
}
