namespace XCase.REST.ProxyGenerator.Swagger
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;
    using log4net;
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
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public override IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        {
            Log.Debug("starting ParseDoc()");
            JObject jObject = JObject.Parse(document);
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            JToken swaggerToken = jObject["swagger"];
            if (swaggerToken != null)
            {
                proxyDefinition.Swagger = swaggerToken.ToString();
            }
            else
            {

            }

            Log.DebugFormat("proxyDefinition REST is {0}", proxyDefinition.Swagger);
            JToken infoToken = jObject["info"];
            if (infoToken != null)
            {
                JToken descriptionToken = infoToken["description"];
                proxyDefinition.Description = descriptionToken != null ? descriptionToken.ToString() : null;
                proxyDefinition.Title = infoToken["title"].ToString();
                JToken versionToken = infoToken["version"];
                proxyDefinition.Version = versionToken != null ? versionToken.ToString() : null;
            }

            Log.DebugFormat("proxyDefinition Description is {0}", proxyDefinition.Description);
            Log.DebugFormat("proxyDefinition Title is {0}", proxyDefinition.Title);
            Log.DebugFormat("proxyDefinition Version is {0}", proxyDefinition.Version);
            JToken hostToken = jObject["host"];
            if (hostToken != null)
            {
                proxyDefinition.Host = hostToken.ToString();
            }
            else
            {
                proxyDefinition.Host = endpoint.GetHost();
            }

            Log.DebugFormat("proxyDefinition Host is {0}", proxyDefinition.Host);
            JToken basePathToken = jObject["basePath"];
            if (basePathToken != null)
            {
                proxyDefinition.BasePath = basePathToken.ToString();
            }
            else
            {
                proxyDefinition.BasePath = endpoint.GetBasePath();
            }

            Log.DebugFormat("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
            JToken schemesToken = jObject["schemes"];
            if (schemesToken != null)
            {
                IEnumerable<JToken> schemeIJEnumerable = schemesToken.Values<JToken>();
            }
            else
            {
                Log.Debug("no schemes specified");
            }

            this.ParsePaths(jObject, proxyDefinition, endpoint.GetParseOperationIdForProxyName());
            this.ParseDefinitions(jObject, proxyDefinition);
            Log.Debug("finishing ParseSwaggerDoc()");
            return proxyDefinition;
        }

        //public ProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        //{
        //    Log.Debug("starting ParseDoc()");
        //    JObject jObject = JObject.Parse(document);
        //    ProxyDefinition proxyDefinition = new ProxyDefinition();
        //    JToken swaggerToken = jObject["swagger"];
        //    if (swaggerToken != null)
        //    {
        //        proxyDefinition.REST = swaggerToken.ToString();
        //    }
        //    else
        //    {
                
        //    }

        //    Log.DebugFormat("proxyDefinition REST is {0}", proxyDefinition.REST);
        //    JToken infoToken = jObject["info"];
        //    if (infoToken != null)
        //    {
        //        JToken descriptionToken = infoToken["description"];
        //        proxyDefinition.Description = descriptionToken != null ? descriptionToken.ToString() : null;
        //        proxyDefinition.Title = infoToken["title"].ToString();
        //        JToken versionToken = infoToken["version"];
        //        proxyDefinition.Version = versionToken != null ? versionToken.ToString() : null;
        //    }

        //    Log.DebugFormat("proxyDefinition Description is {0}", proxyDefinition.Description);
        //    Log.DebugFormat("proxyDefinition Title is {0}", proxyDefinition.Title);
        //    Log.DebugFormat("proxyDefinition Version is {0}", proxyDefinition.Version);
        //    JToken hostToken = jObject["host"];
        //    if (hostToken != null)
        //    {
        //        proxyDefinition.Host = hostToken.ToString();
        //    }
        //    else
        //    {
        //        proxyDefinition.Host = endpoint.GetHost();
        //    }

        //    Log.DebugFormat("proxyDefinition Host is {0}", proxyDefinition.Host);
        //    JToken basePathToken = jObject["basePath"];
        //    if (basePathToken != null)
        //    {
        //        proxyDefinition.BasePath = basePathToken.ToString();
        //    }
        //    else
        //    {
        //        proxyDefinition.BasePath = endpoint.GetBasePath();
        //    }

        //    Log.DebugFormat("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
        //    JToken schemesToken = jObject["schemes"];
        //    if (schemesToken != null)
        //    {
        //        IEnumerable<JToken> schemeIJEnumerable = schemesToken.Values<JToken>();
        //    }
        //    else
        //    {
        //        Log.Debug("no schemes specified");
        //    }

        //    this.ParsePaths(jObject, proxyDefinition, endpoint.GetParseOperationIdForProxyName());
        //    this.ParseDefinitions(jObject, proxyDefinition);
        //    Log.Debug("finishing ParseSwaggerDoc()");
        //    return proxyDefinition;
        //}

        private void ParsePaths(JObject jObject, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting ParsePaths()");
            List<Operation> pathOperationList = CreateOperationListFromJObject(jObject, parseOperationIdForProxyName);
            Log.DebugFormat("created pathOperationList");
            proxyDefinition.Operations.AddRange(pathOperationList);
            Log.Debug("finishing ParsePaths()");
        }

        private List<Operation> CreateOperationListFromJObject(JObject jObject, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromJObject()");
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
            Log.DebugFormat("starting CreateOperationListFromJObject()");
            List<Operation> pathOperationList = new List<Operation>();
            string path = pathToken.Name;
            Log.DebugFormat("path is {0}", path);
            foreach (JProperty operationToken in pathToken.First.Cast<JProperty>())
            {
                Operation operation = CreateOperationFromOperationToken(operationToken, path, parseOperationIdForProxyName);
                Log.DebugFormat("created operation");
                pathOperationList.Add(operation);
            }

            return pathOperationList;
        }

        private Operation CreateOperationFromOperationToken(JProperty operationToken, string path, bool parseOperationIdForProxyName)
        {
            string method = operationToken.Name;
            Log.DebugFormat("method is {0}", method);
            string operationId = operationToken.First["operationId"].ToString();
            Log.DebugFormat("operationId is {0}", operationId);
            string proxyName = string.Empty;
            if (parseOperationIdForProxyName)
            {
                if (operationId.Contains("_"))
                {
                    int underscoreLocation = operationId.IndexOf("_", StringComparison.OrdinalIgnoreCase);
                    proxyName = operationId.Substring(0, underscoreLocation);
                    operationId = operationId.Substring(underscoreLocation + 1);
                }
            }

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

            List<Parameter> parameters = new List<Parameter>();
            JToken paramTokens = operationToken.First["parameters"];
            if (paramTokens != null)
            {
                foreach (JToken paramToken in paramTokens)
                {
                    Parameter parameter = CreateParameterFromJToken(paramToken);
                    Log.DebugFormat("created parameter");
                    parameters.Add(parameter);
                }
            }

            Operation operation = new Operation(returnType, method, path, parameters, operationId, description, proxyName);
            Log.DebugFormat("created operation");
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
            Log.Debug("starting ParseDefinitions()");
            foreach (JProperty definitionToken in jObject["definitions"].Where(i => i.Type == JTokenType.Property).Cast<JProperty>())
            {
                bool addIt = true;
                ClassDefinition classDefinition = new ClassDefinition(definitionToken.Name);
                JToken allOf = definitionToken.First["allOf"];
                if (allOf != null)
                {
                    foreach (JToken itemToken in allOf)
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
                    JToken properties = definitionToken.Value["properties"];
                    if (properties != null)
                    {
                        foreach (JToken prop in properties)
                        {
                            TypeDefinition type = ParseType(prop);
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
                Log.Debug("classDefinition Name is " + classDefinition.Name);
                /* Some classes should not be added if they exist in .Net */
                if (classDefinition.Name.Equals("Void", StringComparison.InvariantCulture) || 
                    classDefinition.Name.Equals("IterableOfstring", StringComparison.InvariantCulture))
                {
                    Log.Debug("set addIt to false");
                    addIt = false;
                }

                if (addIt)
                {
                    proxyDefinition.ClassDefinitions.Add(classDefinition);
                }
            }

            Log.Debug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(JToken token)
        {
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
                JToken enumToken = workingToken["enum"];
                string[] enumValues = null;
                if (enumToken != null)
                {
                    List<string> enumList = new List<string>();
                    bool anyRawNumbers = false;
                    foreach (JToken enumValueToken in enumToken)
                    {
                        string enumValue = enumValueToken.ToString();
                        decimal value;
                        if (Decimal.TryParse(enumValue, out value))
                        {
                            anyRawNumbers = true;
                        }

                        enumList.Add(enumValue);
                    }

                    if (anyRawNumbers == false)
                    {
                        enumValues = enumList.ToArray();
                        typeName = FixTypeName(name + "Values");
                    }
                }

                typeName = FixGenericName(typeName);
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
            if (token != null)
            {
                JValue refType = token["$ref"] as JValue;
                bool hasNullFlag = false;
                if (refType != null)
                {
                    isNullable = false;
                    return FixTypeName(this.ParseRef(refType.Value.ToString()));
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