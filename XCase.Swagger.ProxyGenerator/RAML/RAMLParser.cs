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
    using XCase.REST.ProxyGenerator.RAML;
    using XCase.REST.ProxyGenerator.RAML.Serialization;
    using XCase.REST.ProxyGenerator.RAML.Serialization.Utilities;

    public class RAMLParser : RESTParser
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
            Deserializer deserializer = new DeserializerBuilder().WithTagMapping("!include", typeof(Object)).WithTagMapping("binary/octet-stream", typeof(Object)).WithTagMapping("multipart/form-data", typeof(Object)).IgnoreUnmatchedProperties().Build();
            Log.DebugFormat("document is {0}", document);
            Dictionary<object, object> ramlObject = (Dictionary<object, object>)deserializer.Deserialize<object>(document);
            Log.DebugFormat("got ramlObject");
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            if (ramlObject != null)
            {
                proxyDefinition.Title = ramlObject.ContainsKey("title") ? ramlObject["title"].ToString() : null;
                proxyDefinition.Version = ramlObject.ContainsKey("version") ? ramlObject["version"].ToString() : null;
                string baseUri = ramlObject.ContainsKey("baseUri") ? ramlObject["baseUri"].ToString() : null;
                Log.DebugFormat("baseUri is {0}", baseUri);
                if (!string.IsNullOrEmpty(baseUri))
                {
                    if (!baseUri.EndsWith("/"))
                    {
                        baseUri = baseUri + "/";
                    }

                    proxyDefinition.BasePath = baseUri + proxyDefinition.Version + "/";
                }
                else
                {
                    proxyDefinition.BasePath = endpoint.GetBasePath();
                }

                proxyDefinition.Schemes = ramlObject.ContainsKey("protocols") ? ParseProtocolsForSchemes(ramlObject["protocols"]) : new string[] { "http" };
                Log.DebugFormat("proxyDefinition Description is {0}", proxyDefinition.Description);
                Log.DebugFormat("proxyDefinition Title is {0}", proxyDefinition.Title);
                Log.DebugFormat("proxyDefinition Version is {0}", proxyDefinition.Version);
                Log.DebugFormat("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
                Log.DebugFormat("proxyDefinition schemes is {0}", proxyDefinition.Schemes);
                this.ParsePaths(ramlObject, proxyDefinition, endpoint.GetParseOperationIdForProxyName());
                this.ParseDefinitions(ramlObject, proxyDefinition);
            }

            Log.Debug("finishing ParseDoc()");
            return proxyDefinition;
        }

        //public ProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        //{
        //    Log.Debug("starting ParseDoc()");
        //    Deserializer deserializer = new DeserializerBuilder().WithTagMapping("!include", typeof(Object)).WithTagMapping("binary/octet-stream", typeof(Object)).WithTagMapping("multipart/form-data", typeof(Object)).IgnoreUnmatchedProperties().Build();
        //    Log.DebugFormat("document is {0}", document);
        //    Dictionary<object, object> ramlObject = (Dictionary<object, object>) deserializer.Deserialize<object>(document);
        //    Log.DebugFormat("got ramlObject");
        //    ProxyDefinition proxyDefinition = new ProxyDefinition();
        //    if (ramlObject != null)
        //    {
        //        proxyDefinition.Title = ramlObject.ContainsKey("title") ? ramlObject["title"].ToString() : null;
        //        proxyDefinition.Version = ramlObject.ContainsKey("version") ? ramlObject["version"].ToString() : null;
        //        string baseUri = ramlObject.ContainsKey("baseUri") ? ramlObject["baseUri"].ToString() : null;
        //        Log.DebugFormat("baseUri is {0}", baseUri);
        //        if (!string.IsNullOrEmpty(baseUri))
        //        {
        //            if (!baseUri.EndsWith("/"))
        //            {
        //                baseUri = baseUri + "/";
        //            }

        //            proxyDefinition.BasePath = baseUri + proxyDefinition.Version + "/";
        //        }
        //        else
        //        {
        //            proxyDefinition.BasePath = endpoint.GetBasePath();
        //        }

        //        proxyDefinition.Schemes = ramlObject.ContainsKey("protocols") ? ParseProtocolsForSchemes(ramlObject["protocols"]) : new string[] { "http" };
        //        Log.DebugFormat("proxyDefinition Description is {0}", proxyDefinition.Description);
        //        Log.DebugFormat("proxyDefinition Title is {0}", proxyDefinition.Title);
        //        Log.DebugFormat("proxyDefinition Version is {0}", proxyDefinition.Version);
        //        Log.DebugFormat("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
        //        Log.DebugFormat("proxyDefinition schemes is {0}", proxyDefinition.Schemes);
        //        this.ParsePaths(ramlObject, proxyDefinition, endpoint.GetParseOperationIdForProxyName());
        //        this.ParseDefinitions(ramlObject, proxyDefinition);
        //    }

        //    Log.Debug("finishing ParseDoc()");
        //    return proxyDefinition;
        //}

        private string[] ParseProtocolsForSchemes(object p)
        {
            if (p as string[] != null)
            {
                return (string[])p;
            }
            else if (p as string != null)
            {
                return ((string)p).Split(',');
            }
            else
            {
                return new string[] { "http" };
            }
        }

        private void ParsePaths(Dictionary<object, object> ramlObject, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting ParsePaths()");
            List<Operation> pathOperationList = CreateOperationListFromRAMLObject(ramlObject, parseOperationIdForProxyName);
            Log.DebugFormat("created pathOperationList");
            proxyDefinition.Operations.AddRange(pathOperationList);
            Log.Debug("finishing ParsePaths()");
        }

        private List<Operation> CreateOperationListFromRAMLObject(Dictionary<object, object> ramlObject, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromJObject()");
            List<Operation> operationList = new List<Operation>();
            foreach (KeyValuePair<object, object> pathKeyValuePair in ramlObject.ToList<KeyValuePair<object, object>>().Where(kvp => ((string)kvp.Key).StartsWith("/")))
            {
                List<Operation> pathOperationList = CreatePathOperationListFromKeyValuePair(pathKeyValuePair, parseOperationIdForProxyName);
                operationList.AddRange(pathOperationList);
            }

            return operationList;
        }

        private List<Operation> CreatePathOperationListFromKeyValuePair(KeyValuePair<object, object> pathKeyValuePair, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromKeyValuePair()");
            List<Operation> pathOperationList = new List<Operation>();
            string path = (string)pathKeyValuePair.Key;
            Log.DebugFormat("path is {0}", path);
            pathOperationList = CreateOperationListFromPathKeyValuePair(pathKeyValuePair, path, parseOperationIdForProxyName, new List<Parameter>());
            return pathOperationList;
        }

        private List<Operation> CreateOperationListFromPathKeyValuePair(KeyValuePair<object, object> pathKeyValuePair, string path, bool parseOperationIdForProxyName, List<Parameter> parameterList)
        {
            Log.DebugFormat("starting CreateOperationListFromPathKeyValuePair()");
            List<Operation> pathOperationList = new List<Operation>();
            List<Parameter> pathParameterList = parameterList.ConvertAll<Parameter>(p => p);
            Log.DebugFormat("path is {0}", path);
            string[] methodArray = new string[] { "get", "delete", "patch", "post", "put" };
            if (pathKeyValuePair.Value is Dictionary<object, object>)
            {
                foreach (string subKey in ((Dictionary<object, object>)pathKeyValuePair.Value).Keys)
                {
                    Log.DebugFormat("subKey is {0}", subKey);
                    object subPathObject = ((Dictionary<object, object>)pathKeyValuePair.Value)[subKey];
                    if (methodArray.Contains<string>(subKey))
                    {
                        Log.DebugFormat("subKey is method");
                        string returnType = null;
                        if (((Dictionary<object, object>)pathKeyValuePair.Value).ContainsKey("type") && ((Dictionary<object, object>)((Dictionary<object, object>)pathKeyValuePair.Value)["type"] != null))
                        {
                            object returnTypeObject = ((Dictionary<object, object>)((Dictionary<object, object>)pathKeyValuePair.Value))["type"];
                            if (((Dictionary<object, object>)((Dictionary<object, object>)returnTypeObject)).ContainsKey("collection") && ((Dictionary<object, object>)((Dictionary<object, object>)returnTypeObject))["collection"] != null)
                            {
                                returnType = "object[]";
                            }
                            else
                            {
                                returnType = "object";
                            }
                        }

                        Operation pathOperation = CreateOperationFromObject(subKey, subPathObject, returnType, path, parseOperationIdForProxyName, parameterList);
                        pathOperationList.Add(pathOperation);
                    }
                    else if (subKey.StartsWith("/"))
                    {
                        if (subKey.Contains("{"))
                        {
                            Log.DebugFormat("subKey is path parameter");
                            string extendedPath = path + subKey;
                            string parameterName = subKey.Split('{', '}')[1];
                            Log.DebugFormat("parameterName is {0}", parameterName);
                            Parameter subPathParameter = new Parameter(new TypeDefinition("string", parameterName, null, false), ParameterIn.Path, true, null, null);
                            pathParameterList.Add(subPathParameter);
                            foreach (KeyValuePair<object, object> subPathKeyValuePair in ((Dictionary<object, object>)pathKeyValuePair.Value))
                            {
                                List<Operation> subPathOperationList = CreateOperationListFromPathKeyValuePair(subPathKeyValuePair, extendedPath, parseOperationIdForProxyName, pathParameterList);
                                pathOperationList.AddRange(subPathOperationList);
                            }

                            pathParameterList.Remove(subPathParameter);
                        }
                        else
                        {
                            Log.DebugFormat("subKey is path");
                            string extendedPath = path + subKey;
                            foreach (KeyValuePair<object, object> subPathKeyValuePair in ((Dictionary<object, object>)pathKeyValuePair.Value))
                            {
                                List<Operation> subPathOperationList = CreateOperationListFromPathKeyValuePair(subPathKeyValuePair, extendedPath, parseOperationIdForProxyName, parameterList);
                                pathOperationList.AddRange(subPathOperationList);
                            }
                        }
                    }
                }
            }

            return pathOperationList;
        }

        private Operation CreateOperationFromObject(string methodString, object methodObject, string returnType, string path, bool parseOperationIdForProxyName, List<Parameter> parameterList)
        {
            Log.DebugFormat("starting CreateOperationFromObject()");
            Log.DebugFormat("methodString is {0}", methodString);
            List<Parameter> operationParameterList = parameterList.ConvertAll<Parameter>(p => p);
            if (methodObject is Dictionary<object, object>)
            {
                Log.DebugFormat("methodObject is dictionary");
                string operationId = string.Empty;
                string proxyName = string.Empty;
                if (((Dictionary<object, object>)methodObject).ContainsKey("operationId") && ((Dictionary<object, object>)methodObject)["operationId"] != null)
                {
                    Log.DebugFormat("operationId is not null");
                    operationId = ((Dictionary<object, object>)methodObject)["operationId"].ToString();
                    Log.DebugFormat("operationId is {0}", operationId);
                    if (parseOperationIdForProxyName)
                    {
                        if (operationId.Contains("_"))
                        {
                            int underscoreLocation = operationId.IndexOf("_", StringComparison.OrdinalIgnoreCase);
                            proxyName = operationId.Substring(0, underscoreLocation);
                            operationId = operationId.Substring(underscoreLocation + 1);
                        }
                    }

                    Log.DebugFormat("proxyName is {0}", proxyName);
                    if (string.IsNullOrWhiteSpace(proxyName))
                    {
                        /* Did not get the proxy name from the operation id, so take the first tag value */
                        //object tagToken = ((Dictionary<string, object>)methodObject)["tags"];
                        //if (tagToken != null)
                        //{
                        //    List<string> tags = tagToken.ToObject<List<string>>();
                        //    string firstTag = tags.First();
                        //    proxyName = FixTypeName(firstTag);
                        //}
                    }
                }
                else
                {
                    Log.DebugFormat("operationId is null");
                    string[] pathArray = path.Split('/');
                    string methodPath = string.Empty;
                    foreach (string pathElement in pathArray)
                    {
                        if (!pathElement.Contains("{"))
                        {
                            methodPath = methodPath + pathElement.FirstToUpperCase();
                        }
                    }

                    operationId = methodString.FirstToUpperCase() + methodPath;
                }

                string description = string.Empty;
                if (((Dictionary<object, object>)methodObject).ContainsKey("description") && ((Dictionary<object, object>)methodObject)["description"] != null)
                {
                    Log.DebugFormat("descriptionObject is not null");
                    description = ((Dictionary<object, object>)methodObject)["description"].ToString();
                }

                Log.DebugFormat("description is {0}", description);
                Log.DebugFormat("about to create operation");
                Operation operation = new Operation(returnType, methodString, path, operationParameterList, operationId, description, proxyName);
                Log.DebugFormat("created operation");
                return operation;
            }
            else
            {
                Log.DebugFormat("methodObject is not dictionary");
            }

            return null;
        }

        private Parameter CreateParameterFromJToken(JToken paramToken)
        {
            return new Parameter(new TypeDefinition(string.Empty, string.Empty), ParameterIn.Body, false, null, null);
            /* TODO: process parameters */
            //TypeDefinition type = ParseType(paramToken);
            //bool isRequired = false;
            //if (paramToken["required"] != null)
            //{
            //    isRequired = paramToken["required"].ToObject<bool>();
            //}

            //ParameterIn parameterIn = ParameterIn.Body;
            //if (paramToken["in"] != null)
            //{
            //    if (paramToken["in"].ToString().Equals("path"))
            //    {
            //        parameterIn = ParameterIn.Path;
            //    }
            //    else if (paramToken["in"].ToString().Equals("query"))
            //    {
            //        parameterIn = ParameterIn.Query;
            //    }
            //    else if (paramToken["in"].ToString().Equals("formData"))
            //    {
            //        parameterIn = ParameterIn.FormData;
            //    }
            //}

            //JToken propDescriptionToken = paramToken["description"];
            //string propDescription = string.Empty;
            //if (propDescriptionToken != null)
            //{
            //    propDescription = propDescriptionToken.ToString();
            //}

            //string collectionFormat = string.Empty;
            //JToken collectionFormatToken = paramToken["collectionFormat"];
            //if (collectionFormatToken != null)
            //{
            //    collectionFormat = collectionFormatToken.ToString();
            //}

            //Parameter parameter = new Parameter(type, parameterIn, isRequired, propDescription, collectionFormat);
            //return parameter;
        }

        private void ParseDefinitions(object ramlObject, ProxyDefinition proxyDefinition)
        {
            Log.DebugFormat("starting ParseDefinitions()");
            if (((Dictionary<object, object>)ramlObject).ContainsKey("types"))
            {
                Log.DebugFormat("ramlObject contains types");
                object typesObject = ((Dictionary<object, object>)ramlObject)["types"];
                if (typesObject is Dictionary<object, object>)
                {
                    foreach (KeyValuePair<object, object> classKeyValuePair in ((Dictionary<object, object>)typesObject))
                    {
                        Log.DebugFormat("key is {0}", classKeyValuePair.Key);
                        bool addIt = true;
                        ClassDefinition classDefinition = new ClassDefinition((string)classKeyValuePair.Key);
                        Dictionary<object, object> classDictionary = (Dictionary<object, object>) classKeyValuePair.Value;
                        Dictionary<object, object> propertiesDictionary = (Dictionary<object, object>) classDictionary["properties"];
                        if (propertiesDictionary != null && propertiesDictionary is Dictionary<object, object>)
                        {
                            foreach (KeyValuePair<object, object> propertyKeyValuePair in ((Dictionary<object, object>)propertiesDictionary))
                            {
                                TypeDefinition type = ParseType(propertyKeyValuePair);
                                classDefinition.Properties.Add(type);
                            }
                        }
                        else
                        {
                            addIt = false;
                        }

                        classDefinition.Name = FixGenericName(classDefinition.Name);
                        if (classDefinition.Name.Equals("Void", StringComparison.InvariantCulture))
                        {
                            addIt = false;
                        }

                        if (addIt)
                        {
                            proxyDefinition.ClassDefinitions.Add(classDefinition);
                        }
                    }
                }
            }
            else
            {
                Log.DebugFormat("ramlObject does not contain types");
            }

            Log.Debug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(KeyValuePair<object, object> token)
        {
            bool isNullable = true;
            string name = string.Empty;
            if (token.Key is string)
            {
                name = (string)token.Key;
            }

            Dictionary<object, object> typeDictionary = (Dictionary<object, object>) token.Value;
            string required = (string)typeDictionary["required"];
            if (!string.IsNullOrEmpty(required) && "true".Equals(required))
            {
                isNullable = false;
            }

            string typeName = (string)typeDictionary["type"];
            object enumToken = typeDictionary["enum"];
            string[] enumValues = null;
            if (enumToken != null)
            {
                /* TODO: fix enum processing */
                //List<string> enumList = new List<string>();
                //bool anyRawNumbers = false;
                //foreach (JToken enumValueToken in enumToken)
                //{
                //    string enumValue = enumValueToken.ToString();
                //    decimal value;
                //    if (Decimal.TryParse(enumValue, out value))
                //    {
                //        anyRawNumbers = true;
                //    }

                //    enumList.Add(enumValue);
                //}

                //if (anyRawNumbers == false)
                //{
                //    enumValues = enumList.ToArray();
                //    typeName = FixTypeName(name + "Values");
                //}
            }

            typeName = FixGenericName(typeName);
            TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
            return type;
        }

        private string ParseRef(string input)
        {
            return input.StartsWith("#/definitions/") ? input.Substring("#/definitions/".Length) : input;
        }
    }
}
