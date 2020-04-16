namespace XCase.REST.ProxyGenerator.OpenAPI
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json.Linq;
    using XCase.ProxyGenerator;
    using XCase.ProxyGenerator.REST;
    using XCase.REST.ProxyGenerator.Generator;

    public class SwaggerParser : RESTParser
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public override IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        {
            Log.LogDebug("starting ParseDoc()");
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            Log.LogDebug("document is {0}", document);
            JObject jObject = JObject.Parse(document);
            JToken swaggerToken = jObject["swagger"];
            if (swaggerToken != null)
            {
                proxyDefinition.Swagger = swaggerToken.ToString();
            }
            else
            {

            }

            Log.LogDebug("proxyDefinition REST is {0}", proxyDefinition.Swagger);
            JToken infoToken = jObject["info"];
            if (infoToken != null)
            {
                JToken descriptionToken = infoToken["description"];
                proxyDefinition.Description = descriptionToken != null ? descriptionToken.ToString() : null;
                proxyDefinition.Title = infoToken["title"].ToString();
                JToken versionToken = infoToken["version"];
                proxyDefinition.Version = versionToken != null ? versionToken.ToString() : null;
            }

            Log.LogDebug("proxyDefinition Description is {0}", proxyDefinition.Description);
            Log.LogDebug("proxyDefinition Title is {0}", proxyDefinition.Title);
            Log.LogDebug("proxyDefinition Version is {0}", proxyDefinition.Version);
            JToken hostToken = jObject["host"];
            if (hostToken != null)
            {
                proxyDefinition.Host = hostToken.ToString();
            }
            else
            {
                proxyDefinition.Host = endpoint.GetHost();
            }

            Log.LogDebug("proxyDefinition Host is {0}", proxyDefinition.Host);
            JToken basePathToken = jObject["basePath"];
            if (basePathToken != null)
            {
                proxyDefinition.BasePath = basePathToken.ToString();
            }
            else
            {
                proxyDefinition.BasePath = endpoint.GetBasePath();
            }

            Log.LogDebug("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
            JToken schemesToken = jObject["schemes"];
            if (schemesToken != null)
            {
                IEnumerable<JToken> schemeIJEnumerable = schemesToken.Values<JToken>();
            }
            else
            {
                Log.LogDebug("no schemes specified");
            }

            this.ParsePaths(jObject, proxyDefinition, endpoint.GetParseOperationIdForProxyName());
            Log.LogDebug("parsed paths");
            this.ParseDefinitions(jObject, proxyDefinition);
            Log.LogDebug("finishing ParseSwaggerDoc()");
            ParseSecuritySchema(jObject, proxyDefinition);
            return proxyDefinition;
        }

        private void ParseSecuritySchema(JObject jObject, ProxyDefinition proxyDefinition)
        {
            Log.LogDebug("starting ParseSecuritySchema()");
            IDictionary<string, RESTSecurityScheme> restSecuritySchemeDictionary = new Dictionary<string, RESTSecurityScheme>();
            if (jObject["securitySchemes"] != null)
            {
                foreach (JProperty schemeJProperty in jObject["securitySchemes"].Cast<JProperty>())
                {
                    string key = schemeJProperty.Name;
                    JToken schemeJToken = schemeJProperty.Value;
                    RESTSecurityScheme restSecurityScheme = CreateRESTSecuritySchemeFromSchemeJToken(schemeJToken);
                    restSecuritySchemeDictionary.Add(key, restSecurityScheme);
                }
            }
            else
            {
                Log.LogDebug("jObject[\"securitySchemes\"] is null");
            }

            proxyDefinition.SecuritySchemes = restSecuritySchemeDictionary;
        }

        private RESTSecurityScheme CreateRESTSecuritySchemeFromSchemeJToken(JToken schemeJToken)
        {
            Log.LogDebug("starting CreateRESTSecuritySchemeFromSchemeJToken()");
            /* This is not correct, but points to what has to be done to process the scheme tokens */
            RESTSecurityScheme restSecurityScheme = new RESTSecurityScheme();
            restSecurityScheme.Type = schemeJToken["type"].ToString();
            restSecurityScheme.Scheme = schemeJToken["scheme"].ToString();
            restSecurityScheme.ApiKeyLocation = schemeJToken["apikeylocation"].ToString();
            restSecurityScheme.Name = schemeJToken["name"].ToString();
            return restSecurityScheme;
        }

        private void ParsePaths(JObject jObject, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.LogDebug("starting ParsePaths()");
            List<Operation> pathOperationList = CreateOperationListFromJObject(jObject, parseOperationIdForProxyName);
            Log.LogDebug("created pathOperationList");
            proxyDefinition.Operations.AddRange(pathOperationList);
            Log.LogDebug("finishing ParsePaths()");
        }

        private List<Operation> CreateOperationListFromJObject(JObject jObject, bool parseOperationIdForProxyName)
        {
            Log.LogDebug("starting CreateOperationListFromJObject()");
            List<Operation> operationList = new List<Operation>();
            foreach (JProperty pathToken in jObject["paths"].Cast<JProperty>())
            {
                List<Operation> pathOperationList = CreateOperationListFromPathToken(pathToken, parseOperationIdForProxyName);
                operationList.AddRange(pathOperationList);
            }

            return operationList;
        }

        private List<Operation> CreateOperationListFromPathToken(JProperty pathToken, bool parseOperationIdForProxyName)
        {
            Log.LogDebug("starting CreateOperationListFromPathToken()");
            List<Operation> pathOperationList = new List<Operation>();
            string path = pathToken.Name;
            Log.LogDebug("path is {0}", path);
            IEnumerable<JProperty> jPropertyEnumerable = pathToken.First.Cast<JProperty>();
            /* Process common parameters first */
            List<Parameter> parameterList = new List<Parameter>();
            JToken parameterTokens = pathToken.First["parameters"];
            if (parameterTokens != null)
            {
                foreach (JToken paramToken in parameterTokens)
                {
                    Parameter parameter = CreateParameterFromJToken(paramToken);
                    //Log.DebugFormat("created parameter");
                    parameterList.Add(parameter);
                }
            }

            /* Now process methods, and pass common parameters in */
            foreach (JProperty operationToken in jPropertyEnumerable)
            {
                Log.LogDebug("next operationToken");
                if ((new string[] { "delete", "get", "patch", "post", "put" }).Contains(operationToken.Name)) {
                    List<Parameter> operationParameterList = new List<Parameter>(parameterList);
                    Operation operation = CreateOperationFromOperationToken(operationToken, operationParameterList, path, parseOperationIdForProxyName);
                    Log.LogDebug("created operation " + operation.OperationId);
                    pathOperationList.Add(operation);
                }
            }

            Log.LogDebug("finished processing operationTokens for pathToken");
            return pathOperationList;
        }

        private Operation CreateOperationFromOperationToken(JProperty operationToken, List<Parameter> parameterList, string path, bool parseOperationIdForProxyName)
        {
            Log.LogDebug("starting CreateOperationFromOperationToken()");
            string method = operationToken.Name;
            Log.LogDebug("method is {0}", method);
            string unqueriedPath = path.Contains("?") ? path.Substring(0, path.IndexOf("?")) : path;
            Log.LogDebug("unqueriedPath is {0}", unqueriedPath);
            unqueriedPath = unqueriedPath.Replace("/", "").Replace("{", "").Replace("}", "");
            Log.LogDebug("unqueriedPath is {0}", unqueriedPath);
            string proxyName = unqueriedPath;
            Log.LogDebug("proxyName is {0}", proxyName);
            string operationId = method + unqueriedPath;
            if (operationToken.First["operationId"] != null)
            {
                operationId = operationToken.First["operationId"].ToString();
            }

            Log.LogDebug("operationId is {0}", operationId);
            if (parseOperationIdForProxyName)
            {
                if (operationId.Contains("_"))
                {
                    int underscoreLocation = operationId.IndexOf("_", StringComparison.OrdinalIgnoreCase);
                    proxyName = operationId.Substring(0, underscoreLocation);
                    operationId = operationId.Substring(underscoreLocation + 1);
                }
            }

            Log.LogDebug("operationId is {0}", operationId);
            if (string.IsNullOrWhiteSpace(proxyName))
            {
                /* Did not get the proxy name from the operation id, so take the first tag value */
                JToken tagToken = operationToken.First["tags"];
                if (tagToken != null)
                {
                    List<string> tags = tagToken.ToObject<List<string>>();
                    string firstTag = tags.First();
                    proxyName = FixTypeName(firstTag);
                }
            }

            JToken descriptionToken = operationToken.First["description"];
            string description = null;
            if (descriptionToken != null)
            {
                description = descriptionToken.ToString();
            }

            string returnType;
            JToken schema = operationToken.First["responses"]["200"];
            if (schema != null)
            {
                bool dummyNullable;
                returnType = this.GetTypeName(schema, out dummyNullable);
                if (returnType != null && returnType.Equals("Void"))
                {
                    returnType = null;
                }
            }
            else
            {
                returnType = null;
            }

            List<Parameter> parameters = null;
            if (parameterList != null)
            {
                parameters = parameterList;
            }
            else
            {
                parameters = new List<Parameter>();
            }

            JToken paramTokens = operationToken.First["parameters"];
            if (paramTokens != null)
            {
                foreach (JToken paramToken in paramTokens)
                {
                    Parameter parameter = CreateParameterFromJToken(paramToken);
                    Log.LogDebug("created parameter");
                    parameters.Add(parameter);
                }
            }

            Log.LogDebug("processed parameters");
            JToken requestBodyJToken = operationToken.First["requestBody"];
            Log.LogDebug("got requestBodyJToken");
            if (requestBodyJToken != null)
            {
                Log.LogDebug("requestBodyJToken is not null");
                JToken contentJToken = requestBodyJToken["content"];
                if (contentJToken != null)
                {
                    Log.LogDebug("contentJToken is not null");

                    JToken? applicationJsonJToken = contentJToken["application/json"];
                    if (applicationJsonJToken != null)
                    {
                        Log.LogDebug("applicationJsonJToken is not null");
                        bool isNullable = false;
                        string typeName = GetTypeName(applicationJsonJToken, out isNullable);
                        Log.LogDebug("typeName is {0}", typeName);
                        Parameter requestBodyParameter = new Parameter(new TypeDefinition(typeName, "body", null, false), ParameterIn.Body, true, null, null);
                        Log.LogDebug("created requestBodyParameter");
                        parameters.Add(requestBodyParameter);
                    }
                }
            }

            Operation operation = new Operation(returnType, method, path, parameters, operationId, description, proxyName);
            Log.LogDebug("created operation");
            return operation;
        }

        private Parameter CreateParameterFromJToken(JToken paramToken)
        {
            TypeDefinition type = ParseType(paramToken);
            bool isRequired = false;
            if (paramToken["required"] != null)
            {
                isRequired = paramToken["required"].ToObject<bool>();
            }

            ParameterIn parameterIn = ParameterIn.Body;
            if (paramToken["in"] != null)
            {
                if (paramToken["in"].ToString().Equals("path"))
                {
                    parameterIn = ParameterIn.Path;
                }
                else if (paramToken["in"].ToString().Equals("query"))
                {
                    parameterIn = ParameterIn.Query;
                }
                else if (paramToken["in"].ToString().Equals("formData"))
                {
                    parameterIn = ParameterIn.FormData;
                }
            }

            JToken propDescriptionToken = paramToken["description"];
            string propDescription = string.Empty;
            if (propDescriptionToken != null)
            {
                propDescription = propDescriptionToken.ToString();
            }

            string collectionFormat = string.Empty;
            JToken collectionFormatToken = paramToken["collectionFormat"];
            if (collectionFormatToken != null)
            {
                collectionFormat = collectionFormatToken.ToString();
            }

            Parameter parameter = new Parameter(type, parameterIn, isRequired, propDescription, collectionFormat);
            return parameter;
        }

        private void ParseDefinitions(JObject jObject, ProxyDefinition proxyDefinition)
        {
            Log.LogDebug("starting ParseDefinitions()");
            if (jObject["definitions"] != null)
            {
                foreach (JProperty definitionToken in jObject["definitions"].Where(i => i.Type == JTokenType.Property).Cast<JProperty>())
                {
                    ClassDefinition classDefinition = new ClassDefinition(definitionToken.Name);
                    JToken properties = definitionToken.Value["properties"];
                    if (properties != null)
                    {
                        foreach (JToken propertyJToken in properties)
                        {
                            //Log.DebugFormat("next propertyJToken");
                            TypeDefinition type = ParseType(propertyJToken);
                            classDefinition.Properties.Add(type);
                        }
                    }

                    proxyDefinition.ClassDefinitions.Add(classDefinition);
                }

                return;
            }
                
            if (jObject["components"] == null)
            {
                Log.LogDebug("components property is null");
                return;
            }

            JToken componentsJToken = jObject["components"];
            JToken schemasJToken = componentsJToken["schemas"];
            if (schemasJToken == null)
            {
                Log.LogDebug("schemasJToken is null");
                return;
            }

            IEnumerable<JProperty> jpropertyEnumerable = schemasJToken.Where(i => i.Type == JTokenType.Property).Cast<JProperty>();
            Log.LogDebug("jpropertyEnumerable Count is {0}", jpropertyEnumerable.Count<JProperty>());
            foreach (JProperty definitionJProperty in jpropertyEnumerable)
            {
                Log.LogDebug("next definition");
                bool addIt = true;
                ClassDefinition classDefinition = new ClassDefinition(definitionJProperty.Name);
                JToken allOfJToken = definitionJProperty.First["allOf"];
                if (allOfJToken != null)
                {
                    Log.LogDebug("allOfJToken is is not null");
                    foreach (JToken itemToken in allOfJToken)
                    {
                        JValue refType = itemToken["$ref"] as JValue;
                        if (refType != null)
                        {
                            string inheritsType = refType.Value.ToString();
                            if (inheritsType.StartsWith("#/definitions/"))
                            {
                                inheritsType = inheritsType.Replace("#/definitions/", "");
                            }

                            classDefinition.Inherits = inheritsType;
                        }

                        JToken properties = itemToken["properties"];
                        if (properties != null)
                        {
                            foreach (JToken prop in properties)
                            {
                                TypeDefinition type = ParseType(prop);
                                classDefinition.Properties.Add(type);
                            }
                        }
                    }
                }
                else
                {
                    Log.LogDebug("allOfJToken is null");
                    JToken propertiesJToken = definitionJProperty.Value["properties"];
                    if (propertiesJToken != null)
                    {
                        foreach (JToken propertyJToken in propertiesJToken)
                        {
                            Log.LogDebug("next propertyJToken");
                            TypeDefinition type = ParseType(propertyJToken);
                            classDefinition.Properties.Add(type);
                        }
                    }
                    else
                    {
                        /* TODO: Just because no properties, can still create the class */
                        //addIt = false;
                    }
                }

                classDefinition.Name = FixGenericName(classDefinition.Name);
                Log.LogDebug("classDefinition Name is {0}", classDefinition.Name);
                /* Some classes should not be added if they exist in .Net */
                if (classDefinition.Name.Equals("Void", StringComparison.InvariantCulture) || 
                    classDefinition.Name.Equals("IterableOfstring", StringComparison.InvariantCulture))
                {
                    Log.LogDebug("set addIt to false");
                    addIt = false;
                }

                Log.LogDebug("addIt is {0}", addIt);
                if (addIt)
                {
                    proxyDefinition.ClassDefinitions.Add(classDefinition);
                }
            }

            Log.LogDebug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(JToken token)
        {
            //Log.DebugFormat("starting ParseType(JToken token)");
            bool isNullable;
            JToken workingToken;
            string name;
            if (token.First is JProperty)
            {
                workingToken = token;
                name = workingToken["name"].ToString();
            }
            else
            {
                workingToken = token.First;
                if (token is JProperty)
                {
                    name = ((JProperty)token).Name;
                }
                else
                {
                    name = string.Empty;
                }
            }

            if (workingToken != null)
            {
                string typeName = GetTypeName(workingToken, out isNullable);
                //Log.DebugFormat("typeName is {0}", typeName);
                JToken enumToken = workingToken["enum"];
                string[] enumValues = null;
                bool isEnum = false;
                if (enumToken != null)
                {
                    isEnum = true;
                    //Log.DebugFormat("isEnum is {0}", isEnum);
                    List<string> enumList = new List<string>();
                    bool anyRawNumbers = false;
                    foreach (JToken enumValueToken in enumToken)
                    {
                        string enumValue = enumValueToken.ToString();
                        //Log.DebugFormat("enumValue is {0}", enumValue);
                        decimal value;
                        if (Decimal.TryParse(enumValue, out value))
                        {
                            anyRawNumbers = true;
                        }

                        enumList.Add(XCase.ProxyGenerator.REST.Enum.FixEnumValue(enumValue));
                    }

                    if (anyRawNumbers == false)
                    {
                        enumValues = enumList.ToArray();
                        typeName = FixTypeName(name + "Values");
                    }
                }

                Log.LogDebug("isEnum is {0}", isEnum);
                typeName = FixGenericName(typeName);
                //Log.DebugFormat("typeName is {0}", typeName);
                TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
                return type;
            }
            else
            {
                return null;
            }
        }

        private string ParseRef(string input)
        {
            return input.StartsWith("#/definitions/") ? input.Substring("#/definitions/".Length) : input;
        }

        private string GetTypeName(JToken token, out bool isNullable)
        {
            //Log.DebugFormat("starting GetTypeName()");
            if (token != null)
            {
                JValue refType = token["$ref"] as JValue;
                bool hasNullFlag = false;
                if (refType != null)
                {
                    
                    isNullable = false;
                    string refTypeValue = refType.Value.ToString();
                    //Log.DebugFormat("refTypeValue is {0}", refTypeValue);
                    return FixTypeName(this.ParseRef(refTypeValue));
                }

                JToken schema = token["schema"];
                if (schema != null)
                {
                    return FixTypeName(this.GetTypeName(schema, out isNullable));
                }

                JValue type = token["type"] as JValue;
                if (type == null)
                {
                    isNullable = false;
                    return null;
                }

                JValue nullableToken = token["x-nullable"] as JValue;
                if (nullableToken != null)
                {
                    hasNullFlag = true;
                }

                if (type.Value.Equals("array"))
                {
                    isNullable = false;
                    JToken jToken = token["items"];
                    bool throwawayNullable;
                    /* Temporary change: return array rather than List */
                    string itemType = this.GetTypeName(jToken, out throwawayNullable);
                    //return string.Format("List<{0}>", this.GetTypeName(jToken, out throwawayNullable));
                    return string.Format("{0}[]", itemType);
                }

                if (type.Value.Equals("boolean"))
                {
                    isNullable = true;
                    return (hasNullFlag) ? "bool?" : "bool";
                }

                if (type.Value.Equals("file"))
                {
                    isNullable = true;
                    return "file";
                }

                if (type.Value.Equals("string"))
                {
                    JValue format = token["format"] as JValue;
                    if (format == null)
                    {
                        isNullable = false;
                        return "string";
                    }

                    if (format.Value.Equals("date") || format.Value.Equals("date-time"))
                    {
                        isNullable = true;
                        return (hasNullFlag) ? "DateTime?" : "DateTime";
                    }

                    if (format.Value.Equals("byte") || format.Value.Equals("binary"))
                    {
                        isNullable = true;
                        return (hasNullFlag) ? "byte[]" : "byte[]";
                    }

                    isNullable = false;
                    return "string";
                }

                if (type.Value.Equals("integer"))
                {
                    isNullable = true;
                    JValue format = token["format"] as JValue;
                    if (format != null)
                    {
                        if (format.Value.Equals("int32"))
                            return (hasNullFlag) ? "int?" : "int";

                        if (format.Value.Equals("int64"))
                            return (hasNullFlag) ? "long?" : "long";
                    }

                    return "int";
                }

                if (type.Value.Equals("number"))
                {
                    isNullable = true;
                    JValue format = token["format"] as JValue;
                    if (format != null)
                    {
                        if (format.Value.Equals("float"))
                            return (hasNullFlag) ? "float?" : "float";

                        if (format.Value.Equals("double"))
                            return (hasNullFlag) ? "double?" : "double";

                        if (format.Value.Equals("decimal"))
                            return (hasNullFlag) ? "decimal?" : "decimal";
                    }

                    return (hasNullFlag) ? "float?" : "float";
                }

                if (type.Value.Equals("object"))
                {
                    isNullable = false;
                    return "object";
                }

                isNullable = false;
                return string.Empty;
            }
            else
            {
                isNullable = false;
                return string.Empty;
            }
        }
    }
}