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
    using Microsoft.Owin.Testing;
    using log4net;
    using Newtonsoft.Json;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.Swagger;

    public class SwaggerJavaProxyGenerator : SwaggerProxyGenerator, IProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

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
                RESTServiceDefinition restServiceDefinition = ProcessSwaggerDocuments(username, password, tenant);
                Log.Debug("processed REST document");
                return restServiceDefinition;
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
                swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();
                SourceStringBuilder = new StringBuilder();
                Log.Debug("requesting REST documents...");
                List<Task> taskList = new List<Task>();
                foreach (IAPIProxySettingsEndpoint endPoint in endpoints)
                {
                    string requestUri = endPoint.GetUrl();
                    Log.DebugFormat("requested: {0}", requestUri);
                    taskList.Add(GetEndpointSwaggerDoc(requestUri, endPoint));
                }

                Log.Debug("waiting for REST documents to complete downloading...");
                Task.WaitAll(taskList.ToArray());
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

        private static StringBuilder CreateInterfaceStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend)
        {
            StringBuilder interfaceStringBuilder = new StringBuilder();
            PrintHeaders(interfaceStringBuilder, endPoint.GetNamespace());
            WriteLine(interfaceStringBuilder, "/*");
            WriteLine(interfaceStringBuilder, string.Format("/// Interface for Web Proxy for {0}", proxy));
            WriteLine(interfaceStringBuilder, "*/");
            string interfaceName = string.Format("I{0}WebProxy", SwaggerParser.FixTypeName(proxy));
            WriteLine(interfaceStringBuilder, string.Format("public interface {0}", interfaceName));
            WriteLine(interfaceStringBuilder, "{");
            var proxy1 = proxy;
            foreach (Operation operationDef in proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxy1)))
            {
                string returnType = string.IsNullOrEmpty(operationDef.ReturnType) ? "void" : string.Format("{0}", Operation.GetCleanJavaReturnType(operationDef.ReturnType));
                IEnumerable<Parameter> enums = operationDef.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
                List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums = new List<XCase.ProxyGenerator.REST.Enum>();
                foreach (Parameter enumParam in enums)
                {
                    enumParam.Type.TypeName = operationDef.OperationId + enumParam.Type.Name;
                    proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParam.Type.TypeName, Values = enumParam.Type.EnumValues });
                }

                string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                string parameters = string.Join(", ", operationDef.Parameters.OrderByDescending(i => i.IsRequired).Select(x =>
                {
                    string parameter = string.Empty;
                    if (x.Type != null)
                    {
                        if (x.IsRequired)
                        {
                            parameter += string.Format("{0} {1}", GetDefaultType(x), GetDefaultTypeName(x));
                        }
                        else
                        {
                            parameter += string.Format("{0} {1}", GetDefaultType(x), GetDefaultTypeName(x));
                        }
                    }

                    return parameter;
                }));
                string methodName = SwaggerParser.FixTypeName(operationDef.OperationId);
                string taskLine = string.Format("{0} {1}{2}({3});", returnType, methodName, methodNameAppend, parameters);
                WriteLine(interfaceStringBuilder, taskLine);
            }

            WriteLine(interfaceStringBuilder, "}");
            WriteLine(interfaceStringBuilder, "");
            StreamWriter streamWriter = new StreamWriter(string.Format("{0}.java", interfaceName));
            streamWriter.WriteLine(interfaceStringBuilder.ToString());
            streamWriter.Close();
            return interfaceStringBuilder;
        }

        private static void WriteOperationToStringBuilder(Operation operation, StringBuilder proxyStringBuilder, List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums, string methodNameAppend)
        {
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", Operation.GetCleanJavaReturnType(operation.ReturnType));
            //string bodyParameterName = "body";
            IEnumerable<Parameter> enums = operation.Parameters.Where(i => (i.Type != null && i.Type.EnumValues != null));
            foreach (Parameter enumParam in enums)
            {
                enumParam.Type.TypeName = operation.OperationId + enumParam.Type.Name;
                proxyParamEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = enumParam.Type.TypeName, Values = enumParam.Type.EnumValues });
            }

            string parameters = string.Empty;
            try
            {
                parameters = string.Join(", ", operation.Parameters.OrderByDescending(i => i.IsRequired).Select(x =>
                {
                    string parameter = string.Empty;
                    if (x.IsRequired == false)
                    {
                        parameter += string.Format("{0} {1}", GetDefaultType(x), GetDefaultTypeName(x));
                    }
                    else
                    {
                        parameter += string.Format("{0} {1}", GetDefaultType(x), GetDefaultTypeName(x));
                    }

                    //bodyParameterName = GetDefaultTypeName(x);
                    return parameter;
                }
                ));
            }
            catch (Exception e)
            {
                Log.Warn("exception setting parameters: " + e.Message);
            }

            WriteLine(proxyStringBuilder, "/// <summary>");
            var summary = (SecurityElement.Escape(operation.Description) ?? "").Replace("\n", "\n///");
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
                    WriteLine(proxyStringBuilder, string.Format("/// <param name=\"{0}\">{1}</param>", parameter.Type.GetCleanJavaTypename(), (SecurityElement.Escape(parameter.Description) ?? "").Replace("\n", "\n///")));
                }
            }

            string methodName = SwaggerParser.FixTypeName(operation.OperationId);
            WriteLine(proxyStringBuilder, string.Format("public {0} {1}{2}({3})", returnType, methodName, methodNameAppend, parameters));
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, string.Format("LOGGER.debug(\"starting {0}()\");", methodName));
            if (operation.Path.StartsWith("/"))
            {
                WriteLine(proxyStringBuilder, string.Format("String url = \"{0}\"", operation.Path.Substring(1)));
            }
            else
            {
                WriteLine(proxyStringBuilder, string.Format("String url = \"{0}\"", operation.Path));
            }

            foreach (Parameter parameter in operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Path))
            {
                if (ToStringIsValid(parameter))
                {
                    WriteLine(proxyStringBuilder, string.Format(".replace(\"{{{0}}}\", URLUtils.encodeUrl({0}.toString()))", parameter.Type.GetCleanJavaTypeName()));
                }
                else
                {
                    WriteLine(proxyStringBuilder, string.Format(".replace(\"{{{0}}}\", URLUtils.encodeUrl(String.valueOf({0})))", parameter.Type.GetCleanJavaTypeName()));
                }
            }

            WriteLine(proxyStringBuilder, ";");
            WriteLine(proxyStringBuilder);
            WriteLine(proxyStringBuilder, "url = _baseUrl + url;");
            List<Parameter> queryParams = operation.Parameters.Where(i => i.ParameterIn == ParameterIn.Query).ToList();
            if (queryParams.Count > 0)
            {
                foreach (Parameter parameter in queryParams)
                {
                    if (parameter.IsRequired == false && (parameter.Type.EnumValues == null || parameter.Type.EnumValues.Any() == false))
                    {
                        WriteNullIfStatementOpening(proxyStringBuilder, parameter.Type.GetCleanJavaTypeName(), parameter.Type.TypeName);
                    }

                    string toString = ToStringIsValid(parameter) ? string.Format("{0}.toString()", parameter.Type.GetCleanJavaTypeName()) : string.Format("String.valueOf({0})", parameter.Type.GetCleanJavaTypeName());
                    if (string.IsNullOrWhiteSpace(parameter.CollectionFormat) == false)
                    {
                        if (parameter.CollectionFormat.Equals("csv", StringComparison.OrdinalIgnoreCase))
                        {
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", StringUtils.join(\",\", {1}));", parameter.Type.Name, parameter.Type.GetCleanJavaTypeName()));
                        }
                        else if (parameter.CollectionFormat.Equals("ssv", StringComparison.OrdinalIgnoreCase))
                        {
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", StringUtils.join(\" \", {1}));", parameter.Type.Name, parameter.Type.GetCleanJavaTypeName()));
                        }
                        else if (parameter.CollectionFormat.Equals("tsv", StringComparison.OrdinalIgnoreCase))
                        {
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", StringUtils.join(\"\t\", {1}));", parameter.Type.Name, parameter.Type.GetCleanJavaTypeName()));
                        }
                        else if (parameter.CollectionFormat.Equals("pipes", StringComparison.OrdinalIgnoreCase))
                        {
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", StringUtils.join(\"\t\", {1}));", parameter.Type.Name, parameter.Type.GetCleanJavaTypeName()));
                        }
                        else if (parameter.CollectionFormat.Equals("multi", StringComparison.OrdinalIgnoreCase))
                        {
                            if (ToStringIsValid(parameter))
                            {
                                toString = "item.toString()";
                            }
                            else
                            {
                                toString = "String.valueOf(item)";
                            }

                            WriteLine(proxyStringBuilder, string.Format("for ({0} item : {1})", GetElementType(parameter), parameter.Type.GetCleanJavaTypeName()));
                            WriteLine(proxyStringBuilder, "{");
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", {1});", parameter.Type.Name, toString));
                            WriteLine(proxyStringBuilder, "}");
                        }
                        else
                        {
                            WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", {1});", parameter.Type.Name, toString));
                        }
                    }
                    else
                    {
                        WriteLine(proxyStringBuilder, string.Format("url = appendQuery(url, \"{0}\", {1});", parameter.Type.Name, toString));
                    }

                    if (parameter.IsRequired == false && (parameter.Type.EnumValues == null || parameter.Type.EnumValues.Any() == false))
                    {
                        WriteNullIfStatementClosing(proxyStringBuilder, parameter.Type.GetCleanJavaTypeName(), parameter.Type.TypeName);
                    }
                }
            }

            WriteLine(proxyStringBuilder);
            WriteLine(proxyStringBuilder, "LOGGER.debug(\"url is \" + url);");
            WriteLine(proxyStringBuilder, "try (CommonHTTPManager apiClient = buildHttpClient())");
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "Header[] headers = setHeaders();");
            string method = operation.Method.ToUpperInvariant();
            Log.DebugFormat("method is {0}", method);
            WriteLine(proxyStringBuilder, string.Format("LOGGER.debug(\"method is " + method + "\");"));
            Log.DebugFormat("about to write method {0}", method);
            WriteMethod(operation, proxyStringBuilder, method);
            Log.DebugFormat("written method {0}", method);
            //WriteLine(classStringBuilder, "EnsureSuccessStatusCodeAsync(response);");
            if (string.IsNullOrWhiteSpace(operation.ReturnType) == false)
            {
                //WriteLine(classStringBuilder, string.Format("response.Content.ReadAsAsync<{0}>().ConfigureAwait(false);", operationDef.ReturnType));
            }

            WriteLine(proxyStringBuilder, "}"); // close up the try using
            WriteLine(proxyStringBuilder, "catch (IOException ioe)");  // catch IOException
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "LOGGER.warn(\"ioexception invoking method: \" + ioe.getMessage());");
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder, "catch (Exception e)"); // catch Exception
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "LOGGER.warn(\"exception invoking method: \" + e.getMessage());");
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            WriteLine(proxyStringBuilder, string.IsNullOrEmpty(operation.ReturnType) ? string.Empty : string.Format("return {0};", DefaultReturnValue(operation.ReturnType)));
            WriteLine(proxyStringBuilder, "}"); // close up the method
            WriteLine(proxyStringBuilder);
        }

        private static void WriteMethod(Operation operation, StringBuilder proxyStringBuilder, string method)
        {
            Log.DebugFormat("starting WriteMethod()");
            string returnType = string.IsNullOrEmpty(operation.ReturnType) ? "void" : string.Format("{0}", Operation.GetCleanJavaReturnType(operation.ReturnType));
            //string bodyParameterTypeName = null;
            Parameter bodyParameter = operation.Parameters.FirstOrDefault(p => p.ParameterIn == ParameterIn.Body);
            if (method != "GET")
            {
                Log.DebugFormat("bodyParameter is not null and method is {0}", method);
                WriteLine(proxyStringBuilder, string.Format("String entityBody = null;"));
                if (bodyParameter != null)
                {
                    string bodyParameterTypeName = GetDefaultTypeName(bodyParameter);
                    Log.DebugFormat("bodyParameterTypeName is {0}", bodyParameterTypeName);
                    WriteLine(proxyStringBuilder, "Gson gson = new GsonBuilder().setDateFormat(\"yyyy-MM-dd' 'HH:mm:ss\").create();");
                    WriteLine(proxyStringBuilder, string.Format("entityBody = gson.toJson({0});", bodyParameterTypeName));
                }

                WriteLine(proxyStringBuilder, "LOGGER.debug(\"entityBody is \" + entityBody);");
            }

            List<Parameter> formData = operation.Parameters.Where(p => p.ParameterIn == ParameterIn.FormData).ToList();
            /* Process form parameters */
            bool hasFileParameters = false;
            WriteLine(proxyStringBuilder, "HashMap<String, byte[]> fileParametersHashMap = new HashMap<String, byte[]>();");
            WriteLine(proxyStringBuilder, "List<NameValuePair> formKeyValuePairs = new ArrayList<NameValuePair>();");
            foreach (Parameter formParameter in formData)
            {
                string formParameterTypeName = formParameter.Type.Name;
                if (formParameter.Type.TypeName != "file")
                {
                    if (formParameter.IsRequired == false)
                    {
                        WriteNullIfStatementOpening(proxyStringBuilder, formParameterTypeName, formParameter.Type.TypeName);
                        WriteLine(proxyStringBuilder, string.Format("formKeyValuePairs.add(new BasicNameValuePair(\"{0}\", {0}));", formParameterTypeName));
                        WriteNullIfStatementClosing(proxyStringBuilder, formParameterTypeName, formParameter.Type.TypeName);
                    }
                    else
                    {
                        WriteLine(proxyStringBuilder, string.Format("formKeyValuePairs.add(new BasicNameValuePair(\"{0}\", {0}));", formParameterTypeName));
                    }
                }
                else
                {
                    Log.Debug("formParameter type is file");
                    hasFileParameters = true;
                    WriteLine(proxyStringBuilder, string.Format("for (String key : {0}.keySet()) {{", formParameterTypeName));
                    WriteLine(proxyStringBuilder, string.Format("fileParametersHashMap.put(key, {0}.get(key));", formParameterTypeName));
                    WriteLine(proxyStringBuilder, string.Format("}}"));
                    WriteLine(proxyStringBuilder, string.Format(""));
                }
            }

            if (!string.IsNullOrEmpty(method))
            {
                switch (method)
                {
                    case "DELETE":
                        WriteLine(proxyStringBuilder, string.Format("JsonElement response = apiClient.doJsonDelete(url, headers, formKeyValuePairs, entityBody);"));
                        break;
                    case "GET":
                        WriteLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonGet(url, headers, formKeyValuePairs);");
                        break;
                    case "PATCH":
                        WriteLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPatch(url, headers, formKeyValuePairs, entityBody);");
                        break;
                    case "POST":
                        if (!hasFileParameters)
                        {
                            WriteLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPost(url, headers, formKeyValuePairs, entityBody);");
                        }
                        else
                        {
                            WriteLine(proxyStringBuilder, "String response = apiClient.doMultipartByteArrayPost(url, fileParametersHashMap, headers, formKeyValuePairs);");
                        }

                        break;
                    case "PUT":
                        WriteLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPut(url, headers, formKeyValuePairs, entityBody);");
                        break;
                    default:
                        Log.WarnFormat("unrecognized method {0}", method);
                        break;
                }

                if (!string.IsNullOrEmpty(operation.ReturnType))
                {
                    WriteLine(proxyStringBuilder, "Gson responseGson = new GsonBuilder().setDateFormat(\"yyyy-MM-dd' 'HH:mm:ss\").create();");
                    WriteLine(proxyStringBuilder, string.Format("{0} entity = responseGson.fromJson(response, {0}.class);", returnType));
                    WriteLine(proxyStringBuilder, "return entity;");
                }
            }
        }

        private static StringBuilder CreateProxyStringBuilderForProxy(IProxyDefinition proxyDefinition, string proxy, IAPIProxySettingsEndpoint endPoint, string methodNameAppend)
        {
            StringBuilder proxyStringBuilder = new StringBuilder();
            PrintHeaders(proxyStringBuilder, endPoint.GetNamespace());
            WriteLine(proxyStringBuilder, "/*");
            WriteLine(proxyStringBuilder, string.Format("/// Web Proxy for {0}", proxy));
            WriteLine(proxyStringBuilder, "*/");
            string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
            WriteLine(proxyStringBuilder, string.Format("public class {0} extends {1} implements I{0}", className, endPoint.GetBaseProxyClass()));
            WriteLine(proxyStringBuilder, "{");
            PrintLogger(proxyStringBuilder, className);
            WriteLine(proxyStringBuilder, string.Format("public {0}(URL baseUrl)", className));
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "super(baseUrl);");
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            WriteLine(proxyStringBuilder, string.Format("public {0}(URL baseUrl, String token)", className));
            WriteLine(proxyStringBuilder, "{");
            WriteLine(proxyStringBuilder, "super(baseUrl, token);");
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            List<XCase.ProxyGenerator.REST.Enum> proxyParamEnums = new List<XCase.ProxyGenerator.REST.Enum>();

            /* Async operations (Web methods) */
            string proxyName = proxy;
            foreach (Operation operation in proxyDefinition.Operations.Where(i => i.ProxyName.Equals(proxyName)))
            {
                WriteOperationToStringBuilder(operation, proxyStringBuilder, proxyParamEnums, methodNameAppend);
            }

            foreach (XCase.ProxyGenerator.REST.Enum proxyParamEnum in proxyParamEnums)
            {
                StringBuilder enumStringBuilder = new StringBuilder();
                PrintHeaders(enumStringBuilder, endPoint.GetNamespace());
                WriteLine(enumStringBuilder, "/*");
                WriteLine(enumStringBuilder, string.Format("/// enum for {0}", proxy));
                WriteLine(enumStringBuilder, "*/");
                WriteLine(enumStringBuilder, string.Format("public enum {0}", SwaggerParser.FixTypeName(proxyParamEnum.Name)));
                WriteLine(enumStringBuilder, "{");
                foreach (var value in proxyParamEnum.Values.Distinct())
                {
                    WriteLine(enumStringBuilder, string.Format("{0},", SwaggerParser.FixTypeName(value)));
                }

                WriteLine(enumStringBuilder, "}");
                WriteLine(enumStringBuilder);
                StreamWriter enumStreamWriter = new StreamWriter(string.Format("{0}.java", SwaggerParser.FixTypeName(proxyParamEnum.Name)));
                enumStreamWriter.WriteLine(enumStringBuilder.ToString());
                enumStreamWriter.Close();
                /* TODO: do we still have to add to sourceStringList?
                sourceStringList.Add(enumStringBuilder.ToString());
                */
            }

            // close class def
            WriteLine(proxyStringBuilder, "}");
            WriteLine(proxyStringBuilder);
            StreamWriter streamWriter = new StreamWriter(string.Format("{0}.java", className));
            streamWriter.WriteLine(proxyStringBuilder.ToString());
            streamWriter.Close();
            return proxyStringBuilder;
        }

        private static StringBuilder WriteClassDefinitionToStringBuilder(ClassDefinition classDefinition, IAPIProxySettingsEndpoint endPoint)
        {
            StringBuilder modelClassStringBuilder = new StringBuilder();
            List<XCase.ProxyGenerator.REST.Enum> modelEnums = new List<XCase.ProxyGenerator.REST.Enum>();
            string modelClassName = classDefinition.GetCleanClassName();
            PrintHeaders(modelClassStringBuilder, endPoint.GetNamespace());
            WriteLine(modelClassStringBuilder, string.Format("public class {0}{1}", modelClassName, string.IsNullOrEmpty(classDefinition.Inherits) ? string.Empty : string.Format(" extends {0}", classDefinition.Inherits)));
            WriteLine(modelClassStringBuilder, "{");
            foreach (TypeDefinition typeDefinition in classDefinition.Properties)
            {
                string typeName = string.IsNullOrWhiteSpace(typeDefinition.GetCleanJavaTypename()) ? "object" : typeDefinition.GetCleanJavaTypename();
                WriteLine(modelClassStringBuilder, string.Format("public {0} {1};", typeName, SwaggerParser.FixJavaTypeName(typeDefinition.Name)));
                if (typeDefinition.EnumValues != null)
                {
                    modelEnums.Add(new XCase.ProxyGenerator.REST.Enum() { Name = typeName, Values = typeDefinition.EnumValues });
                }
            }

            foreach (XCase.ProxyGenerator.REST.Enum modelEnum in modelEnums)
            {
                CSharpCodeProvider csharpCodeProvider = new CSharpCodeProvider();
                WriteLine(modelClassStringBuilder, string.Format("public enum {0}", csharpCodeProvider.CreateValidIdentifier(modelEnum.Name)));
                WriteLine(modelClassStringBuilder, "{");
                foreach (string value in modelEnum.Values.Distinct())
                {
                    WriteLine(modelClassStringBuilder, string.Format("{0}(\"{1}\"),", SwaggerParser.FixTypeName(value), value));
                }

                WriteLine(modelClassStringBuilder, ";");
                WriteLine(modelClassStringBuilder, "private String description;");
                WriteLine(modelClassStringBuilder, string.Format("{0}(String description) {{", csharpCodeProvider.CreateValidIdentifier(modelEnum.Name)));
                WriteLine(modelClassStringBuilder, "this.description = description;");
                WriteLine(modelClassStringBuilder, "}");
                WriteLine(modelClassStringBuilder, "}");
                WriteLine(modelClassStringBuilder);
            }

            WriteLine(modelClassStringBuilder, "}");
            WriteLine(modelClassStringBuilder);
            StreamWriter streamWriter = new StreamWriter(string.Format("{0}.java", modelClassName));
            streamWriter.WriteLine(modelClassStringBuilder.ToString());
            streamWriter.Close();
            return modelClassStringBuilder;
        }

        private static RESTServiceDefinition ProcessSwaggerDocuments(string username, string password, string tenant)
        {
            Log.Debug("starting ProcessSwaggerDocuments()");
            RESTServiceDefinition swaggerServiceDefinition = new RESTServiceDefinition();
            List<string> sourceStringList = new List<string>();
            foreach (KeyValuePair<IAPIProxySettingsEndpoint, string> swaggerDocDictionaryEntry in swaggerDocDictionary.OrderBy(x => x.Key.GetId()))
            {
                IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.Key;
                Log.DebugFormat("processing {0}", endPoint.GetUrl());
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
                    Log.DebugFormat("created interfaceStringBuilder for {0}", proxy);
                    sourceStringList.Add(interfaceStringBuilder.ToString());
                }

                /* Write class for each proxy in proxies */
                foreach (string proxy in proxies)
                {
                    string className = SwaggerParser.FixTypeName(proxy) + "WebProxy";
                    swaggerServiceDefinition.ProxyClasses.Add(className);
                    StringBuilder classStringBuilder = CreateProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
                    Log.DebugFormat("created classStringBuilder for {0}", proxy);
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
            return ProcessSwaggerDocuments("Admin", "1nt@ppC10ud2016", "tenant1");
        }

        private static string DefaultReturnValue(string returnType)
        {
            if (returnType == "int")
            {
                return "-1";
            }

            return "null";
        }

        private static string GetElementType(Parameter parameter)
        {
            return TrimArray(GetDefaultType(parameter));
        }

        private static string TrimArray(string type)
        {
            if (type.EndsWith("[]"))
            {
                return type.Substring(0, type.Length - 2);
            }

            return type;
        }

        private static string GetDefaultTypeName(Parameter x)
        {
            if (x.Type.TypeName.EndsWith("Data") || x.Type.TypeName.EndsWith("Data[]"))
            {
                /* TODO: do we need to do this?
                 return "body";
                */
            }

            return x.Type.GetCleanJavaTypeName();
        }

        private static bool ToStringIsValid(Parameter parameter)
        {
            if (parameter.Type.EnumValues != null)
            {
                return false;
            }

            string type = parameter.Type.GetCleanJavaTypename();
            if (!string.IsNullOrEmpty(type))
            {
                if (type == "boolean")
                {
                    return false;
                }

                if (type == "int")
                {
                    return false;
                }

                if (type == "int[]")
                {
                    return false;
                }

                if (type == "long")
                {
                    return false;
                }
            }

            return true;
        }

        private static void PrintLogger(StringBuilder stringBuilder, String className)
        {
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, string.Format("protected static final Logger LOGGER = LogManager.getLogger({0}.class);", className));
            WriteLine(stringBuilder);
        }

        private static void PrintLogger(StringBuilder stringBuilder)
        {
            WriteLine(stringBuilder);
            WriteLine(stringBuilder, "protected static final Logger LOGGER = LogManager.getLogger();");
            WriteLine(stringBuilder);
        }

        static string GetDefaultType(Parameter parameter)
        {
            if (parameter.Type != null)
            {
                string typeName = parameter.Type.TypeName;
                if (typeName == "file")
                {
                    return "Map<String, byte[]>";
                }

                if (parameter.Type.IsNullableType)
                {
                    //typeName += "?";
                }

                //var string = x.CollectionFormat == "multi" ? string.Format("List<{0}>", typeName) : typeName;
                /* Ugly hack to manage parameters of Array type */
                string output = ((parameter.CollectionFormat == "multi") && !typeName.EndsWith("[]")) ? string.Format("{0}[]", typeName) : typeName;
                //output = output.Replace(".", "__");
                /* Ugly hack and will not work if type is both array and contains [ or ] */
                if (!output.Contains("[]"))
                {
                    output = RESTParser.FixTypeName(output);
                }

                if (output == "bool")
                {
                    output = "boolean";
                }

                if (output == "DateTime")
                {
                    output = "Date";
                }

                if (output == "int[][]")
                {
                    output = "int[]";
                }

                if (output == "long?")
                {
                    output = "long";
                }

                if (output == "string")
                {
                    output = "String";
                }

                if (output == "string[]")
                {
                    output = "String[]";
                }

                if (output == "string[][]")
                {
                    output = "String[]";
                } 

                return output;
            }
            else
            {
                return "String";
            }
        }

        static string GetDefaultValue(Parameter x)
        {
            if (!x.Type.IsNullableType && x.CollectionFormat != "multi" && x.Type.EnumValues != null && x.Type.EnumValues.Any())
            {
                return string.Format("{0}.{1}", GetDefaultType(x), x.Type.EnumValues.FirstOrDefault());
            }

            return "null";
        }

        static void PrintHeaders(StringBuilder stringBuilder, string package)
        {
            WriteLine(stringBuilder, string.Format("package {0};", package));
            WriteLine(stringBuilder);
            WriteImportLine(stringBuilder, "java.io.*");
            WriteImportLine(stringBuilder, "java.net.*");
            WriteImportLine(stringBuilder, "java.util.*");
            WriteImportLine(stringBuilder, "org.apache.commons.lang3.*");
            WriteImportLine(stringBuilder, "org.apache.http.*");
            WriteImportLine(stringBuilder, "org.apache.http.message.*");
            WriteImportLine(stringBuilder, "org.apache.logging.log4j.*");
            WriteImportLine(stringBuilder, "com.google.gson.*");
            WriteImportLine(stringBuilder, "com.xcase.common.impl.simple.core.CommonHTTPManager");
            WriteImportLine(stringBuilder, "com.xcase.common.utils.URLUtils");
            WriteLine(stringBuilder);
        }

        private static void WriteImportLine(StringBuilder stringBuilder, string import)
        {
            WriteLine(stringBuilder, string.Format("import {0};", import));
        }

        static void WriteNullIfStatementOpening(StringBuilder stringBuilder, string parameterName, string typeName)
        {
            if (IsIntrinsicType(typeName))
            {
                WriteLine(stringBuilder, string.Format("if (true){{", parameterName));
            }
            else
            {
                WriteLine(stringBuilder, string.Format("if ({0} != null){{", parameterName));
            }
        }

        static void WriteNullIfStatementClosing(StringBuilder stringBuilder, string parameterName, string typeName)
        {
            WriteLine(stringBuilder, string.Format("}}"));
        }

        static bool IsIntrinsicType(string typeName)
        {
            switch (typeName.ToLowerInvariant())
            {
                case "boolean":
                case "byte":
                case "enum":
                case "int":
                case "long":
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
                var value = streamReader.ReadToEnd();
                return JsonConvert.DeserializeObject<RESTApiProxySettings>(value);
            }
        }
    }
}
