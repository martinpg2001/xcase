﻿using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Any;
using Microsoft.OpenApi.Models;
using Microsoft.OpenApi.Readers;
using Serilog;
using Serilog.Events;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using XCase.ProxyGenerator;
using XCase.ProxyGenerator.REST;
using XCase.REST.ProxyGenerator.Generator;

namespace XCase.Swagger.ProxyGenerator.OpenAPI
{
    public class OpenApiParser : RESTParser
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        //static readonly Serilog.ILogger Log = new LoggerConfiguration().MinimumLevel.Debug().WriteTo.File("XCaseServiceClient.log").WriteTo.Console(restrictedToMinimumLevel: LogEventLevel.Information).CreateLogger();

        #endregion

        public override IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        {
            Log.Debug("starting ParseDoc()");
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            Log.Debug("document is {0}", document);
            try
            {
                byte[] byteArray = Encoding.ASCII.GetBytes(document);
                MemoryStream stream = new MemoryStream(byteArray);
                OpenApiDocument openApiDocument = new OpenApiStreamReader().Read(stream, out var diagnostic);
                Log.Debug("read model");
                OpenApiInfo openApiInfo = openApiDocument.Info;
                if (openApiInfo != null)
                {
                    proxyDefinition.Description = openApiInfo.Description;
                    proxyDefinition.Title = openApiInfo.Title;
                    proxyDefinition.Version = openApiInfo.Version;
                }

                Log.Debug("proxyDefinition Description is {0}", proxyDefinition.Description);
                Log.Debug("proxyDefinition Title is {0}", proxyDefinition.Title);
                Log.Debug("proxyDefinition Version is {0}", proxyDefinition.Version);
                PopulateBasePathAndHost(endpoint, proxyDefinition, openApiDocument);
                Log.Debug("proxyDefinition Host is {0}", proxyDefinition.Host);
                Log.Debug("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
                ParsePaths(openApiDocument, proxyDefinition, false);
                ParseDefinitions(openApiDocument, proxyDefinition);
                ParseSecuritySchema(openApiDocument, proxyDefinition);
            }
            catch (Exception e)
            {
                Log.Warning(e.Message);
                Log.Warning(e.StackTrace);
            }

            Log.Debug("finishing ParseDoc()");
            return proxyDefinition;
        }

        private void ParseSecuritySchema(OpenApiDocument openApiDocument, ProxyDefinition proxyDefinition)
        {
            Log.Debug("starting ParseSecuritySchema()");
            IDictionary<string, RESTSecurityScheme> restSecuritySchemeDictionary = new Dictionary<string, RESTSecurityScheme>();
            if (openApiDocument.Components != null)
            {
                IDictionary<string, OpenApiSecurityScheme> openApiSecuritySchemeDictionary = openApiDocument.Components.SecuritySchemes;
                foreach (string key in openApiSecuritySchemeDictionary.Keys)
                {
                    OpenApiSecurityScheme openApiSecurityScheme = openApiSecuritySchemeDictionary[key];
                    RESTSecurityScheme restSecurityScheme = CreateRESTSecuritySchemeFromOpenApiSecurityScheme(openApiSecurityScheme);
                    restSecuritySchemeDictionary.Add(key, restSecurityScheme);
                }
            }
            else
            {
                Log.Debug("openApiDocument.Components is null)");
            }

            proxyDefinition.SecuritySchemes = restSecuritySchemeDictionary;
        }

        private RESTSecurityScheme CreateRESTSecuritySchemeFromOpenApiSecurityScheme(OpenApiSecurityScheme openApiSecurityScheme)
        {
            Log.Debug("starting CreateRESTSecuritySchemeFromOpenApiSecurityScheme()");
            RESTSecurityScheme restSecurityScheme = new RESTSecurityScheme();
            restSecurityScheme.Type = openApiSecurityScheme.Type.ToString();
            restSecurityScheme.Scheme = openApiSecurityScheme.Scheme;
            restSecurityScheme.ApiKeyLocation = openApiSecurityScheme.In.ToString();
            restSecurityScheme.Name = openApiSecurityScheme.Name;
            return restSecurityScheme;
        }

        private static void PopulateBasePathAndHost(IAPIProxySettingsEndpoint endpoint, ProxyDefinition proxyDefinition, OpenApiDocument openApiDocument)
        {
            Log.Debug("starting PopulateBasePathAndHost()");
            if (openApiDocument.Servers != null && (openApiDocument.Servers.Count<OpenApiServer>() > 0))
            {
                Log.Debug("openApiDocument.Servers is not null and openApiDocument.Servers.Count is greater than zero");
                string url = openApiDocument.Servers[0].Url;
                Log.Debug("url is {0}", url);
                for (int index = 0; index < openApiDocument.Servers.Count; index++)
                {
                    Log.Debug("index is {0}", index);
                    OpenApiServer openApiServer = openApiDocument.Servers[index];
                    url = openApiServer.Url;
                    Log.Debug("url is {0}", url);
                    GroupCollection groupCollection = Regex.Match(url, @"\{([^)]*)\}").Groups;
                    if (groupCollection.Count > 0)
                    {
                        Log.Debug("groupCollection Count is {0}", groupCollection.Count);
                        for (int key = 1; key < groupCollection.Count; key++)
                        {
                            Log.Debug("group key is {0}", key);
                            Group group = groupCollection[key];
                            if (group != null)
                            {
                                Log.Debug("group value is {0}", group.Value);
                                string variable = group.Value;
                                Log.Debug("variable is {0}", variable);
                                IDictionary<string, OpenApiServerVariable> variablesDictionary = openApiServer.Variables;
                                if (variablesDictionary[variable] != null && variablesDictionary[variable].Default != null)
                                {
                                    url = url.Replace("{" + variable + "}", variablesDictionary[variable].Default);
                                }
                                else
                                {
                                    url = url.Replace("{" + variable + "}", variablesDictionary[variable].Enum[0]);
                                }
                            }
                            else
                            {
                                Log.Warning("failed to get value for {0}", key);
                            }
                        }
                    }
                }

                Log.Debug("url is {0}", url);
                proxyDefinition.Host = url;
                proxyDefinition.BasePath = url;
            }
            else
            {
                proxyDefinition.Host = endpoint.GetHost();
                proxyDefinition.BasePath = endpoint.GetBasePath();
            }
        }

        private void ParsePaths(OpenApiDocument openApiDocument, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.Debug("starting ParsePaths()");
            OpenApiPaths openApiPaths = openApiDocument.Paths;
            List<KeyValuePair<string, OpenApiPathItem>> endpointEnumerable = openApiPaths.ToList();
            foreach(KeyValuePair<string, OpenApiPathItem> endpoint in endpointEnumerable)
            {
                List<XCase.ProxyGenerator.REST.Operation> endpointOperationList = CreateOperationListFromEndpoint(endpoint, parseOperationIdForProxyName, proxyDefinition.SecuritySchemes);
                Log.Debug("created endpointOperationList");
                proxyDefinition.Operations.AddRange(endpointOperationList);
            }

            Log.Debug("finishing ParsePaths()");
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromEndpoint(KeyValuePair<string, OpenApiPathItem> endpoint, bool parseOperationIdForProxyName, IDictionary<string, RESTSecurityScheme> restSecuritySchemeDictionary)
        {
            Log.Debug("starting CreateOperationListFromEndpoint()");
            List<XCase.ProxyGenerator.REST.Operation> operationList = new List<XCase.ProxyGenerator.REST.Operation>();
            List < XCase.ProxyGenerator.REST.Operation> restOperationList = CreateOperationListFromPathToken(endpoint.Key, endpoint.Value, null, parseOperationIdForProxyName, restSecuritySchemeDictionary);
            operationList.AddRange(restOperationList);
            return operationList;
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromPathToken(string path, OpenApiPathItem endpoint, List<XCase.ProxyGenerator.REST.Parameter> parameterList, bool parseOperationIdForProxyName, IDictionary<string, RESTSecurityScheme> restSecuritySchemeDictionary)
        {
            Log.Debug("starting CreateOperationListFromPathToken()");
            List<XCase.ProxyGenerator.REST.Operation> pathOperationList = new List<XCase.ProxyGenerator.REST.Operation>();
            foreach (KeyValuePair<OperationType, OpenApiOperation> operation in endpoint.Operations)
            {
                XCase.ProxyGenerator.REST.Operation restOperation = CreateOperationFromOpenApiOperation(path, operation, null, true, restSecuritySchemeDictionary);
                pathOperationList.Add(restOperation);
            }

            return pathOperationList;
        }

        private XCase.ProxyGenerator.REST.Operation CreateOperationFromOpenApiOperation(string path, KeyValuePair<OperationType, OpenApiOperation> keyValuePair, List<XCase.ProxyGenerator.REST.Parameter> parameterList, bool parseOperationIdForProxyName, IDictionary<string, RESTSecurityScheme> restSecuritySchemeDictionary)
        {
            Log.Debug("starting CreateOperationFromOpenApiOperation()");
            string method = keyValuePair.Key.ToString();
            Log.Debug("method is {0}", method);
            string operationId = null;
            /* We want a proxy name for each path supported by the API, and a unique operationId
             * for each method supported by the path. Use the OperationId if possible, but fall back to using
             * path and method if need be.
             */
            string unqueriedPath = path.Contains("?") ? path.Substring(0, path.LastIndexOf("?")) : path;
            Log.Debug("unqueriedPath is {0}", unqueriedPath);
            string proxyName = unqueriedPath.Replace("/", "").Replace("{", "").Replace("}", "");
            Log.Debug("proxyName is {0}", proxyName);
            if (parseOperationIdForProxyName)
            {
                operationId = keyValuePair.Value.OperationId ?? String.Empty;
                Log.Debug("operationId is {0}", operationId);
                if (operationId.Contains("_"))
                {
                    int underscoreLocation = operationId.IndexOf("_", StringComparison.OrdinalIgnoreCase);
                    proxyName = operationId.Substring(0, underscoreLocation);
                    operationId = operationId.Substring(underscoreLocation + 1);
                }
            }

            Log.Debug("proxyName is {0}", proxyName);
            if (string.IsNullOrWhiteSpace(operationId))
            {
                /* Did not get the operationId from property, so generate it from method and path */
                operationId = method + proxyName;
            }

            Log.Debug("operationId is {0}", operationId);
            string description = keyValuePair.Value.Description;
            /* For the moment, we check for the first (if any) security requirement and use that */
            RESTSecurityScheme restSecurityScheme = null;
            IList <OpenApiSecurityRequirement> openApiSecurityRequirementList = keyValuePair.Value.Security;
            if (openApiSecurityRequirementList != null && openApiSecurityRequirementList.Count > 0)
            {
                OpenApiSecurityRequirement openApiSecurityRequirement = openApiSecurityRequirementList[0];
                if (openApiSecurityRequirement.Count > 0)
                {
                    KeyValuePair<OpenApiSecurityScheme, IList<string>> firstKeyValuePair = openApiSecurityRequirement.First<KeyValuePair<OpenApiSecurityScheme, IList<string>>>();
                    foreach (string value in firstKeyValuePair.Value)
                    {
                        if (restSecuritySchemeDictionary.ContainsKey(value))
                        {
                            restSecurityScheme = restSecuritySchemeDictionary[value];
                            break;
                        }
                    }
                }
            }

            string returnType = null;
            List<KeyValuePair<string, OpenApiResponse>> responses = keyValuePair.Value.Responses.ToList();
            KeyValuePair<string, OpenApiResponse> firstResponse = responses.First<KeyValuePair<string, OpenApiResponse>>();
            if (firstResponse.Key != null)
            {
                Log.Debug("firstResponse code is {0}", firstResponse.Key);
                ICollection<OpenApiMediaType> openApiMediaTypeCollection = responses.First<KeyValuePair<string, OpenApiResponse>>().Value.Content.Values;
                if (openApiMediaTypeCollection.Count > 0)
                {
                    Log.Debug("openApiMediaTypeCollection Count is {0}", openApiMediaTypeCollection.Count);
                    OpenApiMediaType firstOpenApiMediaType = openApiMediaTypeCollection.First<OpenApiMediaType>();
                    if (firstOpenApiMediaType != null)
                    {
                        Log.Debug("firstOpenApiMediaType is not null");
                        OpenApiSchema openApiSchema = firstOpenApiMediaType.Schema;
                        if (openApiSchema != null)
                        {
                            Log.Debug("openApiSchema is not null");
                            bool dummyNullable;
                            returnType = this.GetTypeName(openApiSchema, out dummyNullable);
                            if (returnType != null && returnType.Equals("Void"))
                            {
                                returnType = null;
                            }
                        }
                        else
                        {
                            Log.Debug("openApiSchema is null");
                            returnType = null;
                        }
                    }
                }
            }

            List<XCase.ProxyGenerator.REST.Parameter> parameters = null;
            if (parameterList != null)
            {
                parameters = parameterList;
            }
            else
            {
                parameters = new List<XCase.ProxyGenerator.REST.Parameter>();
            }

            IList<OpenApiParameter> paramTokens = keyValuePair.Value.Parameters;
            if (paramTokens != null)
            {
                foreach (OpenApiParameter paramToken in paramTokens)
                {
                    XCase.ProxyGenerator.REST.Parameter parameter = CreateParameterFromOpenApiParameter(paramToken);
                    Log.Debug("created parameter");
                    parameters.Add(parameter);
                }
            }

            if (keyValuePair.Value.RequestBody != null)
            {
                XCase.ProxyGenerator.REST.Parameter parameter = CreateParameterFromOpenApiRequestBody(keyValuePair.Value.RequestBody);
                Log.Debug("created parameter");
                parameters.Add(parameter);
            }

            Log.Debug("about to create operation");
            XCase.ProxyGenerator.REST.Operation operation = new XCase.ProxyGenerator.REST.Operation(returnType, method, path, parameters, operationId, description, proxyName);
            operation.SecurityScheme = restSecurityScheme;
            Log.Debug("created operation");
            return operation;
        }

        private Parameter CreateParameterFromOpenApiRequestBody(OpenApiRequestBody requestBody)
        {
            Log.Debug("starting CreateParameterFromOpenApiRequestBody()");
            TypeDefinition typeDefinition = ParseType(requestBody);
            Log.Debug("typeDefinition TypeName is {0}", typeDefinition.TypeName);
            bool isRequired = false;
            if (requestBody.Required)
            {
                isRequired = requestBody.Required;
            }

            string propDescription = string.Empty;
            if (requestBody.Description != null)
            {
                propDescription = requestBody.Description;
            }

            string collectionFormat = string.Empty;
            XCase.ProxyGenerator.REST.Parameter parameter = new XCase.ProxyGenerator.REST.Parameter(typeDefinition, ParameterIn.Body, isRequired, propDescription, collectionFormat);
            return parameter;
        }

        private TypeDefinition ParseType(OpenApiRequestBody requestBody)
        {
            Log.Debug("starting ParseType(OpenApiRequestBody requestBody)");
            string typeName = null;
            string name = null;
            IDictionary<string, OpenApiMediaType> openApiMediaTypeDictionary = requestBody.Content;
            ICollection<OpenApiMediaType> openApiMediaTypeCollection = openApiMediaTypeDictionary.Values;
            if (openApiMediaTypeCollection.Count > 0)
            {
                Log.Debug("openApiMediaTypeCollection Count is {0}", openApiMediaTypeCollection.Count);
                OpenApiMediaType firstOpenApiMediaType = openApiMediaTypeCollection.First<OpenApiMediaType>();
                if (firstOpenApiMediaType != null)
                {
                    OpenApiSchema openApiSchema = firstOpenApiMediaType.Schema;
                    Log.Debug("schema is {0}", openApiSchema.ToString());
                    if (openApiSchema != null)
                    {
                        OpenApiReference openApiReference = openApiSchema.Reference;
                        if (openApiReference != null)
                        {
                            ReferenceType? referenceTye = openApiReference.Type;
                            typeName = openApiSchema.Reference.ReferenceV3.Substring("#/components/schemas/".Length);
                            Log.Debug("typeName is {0}", typeName);
                            name = typeName.ToLower();
                            Log.Debug("name is {0}", name);
                            return new TypeDefinition(typeName, name, null, false);
                        }

                        string type = openApiSchema.Type;
                        if (type != null)
                        {
                            Log.Debug("type is {0}", type);
                            typeName = "string";
                            Log.Debug("typeName is {0}", typeName);
                            name = "name";
                            Log.Debug("name is {0}", name);
                            return new TypeDefinition(typeName, name, null, false);
                        }
                    }
                }
            }

            TypeDefinition typeDefinition = new TypeDefinition(typeName, name, null, false);
            return typeDefinition;
        }

        private Parameter CreateParameterFromOpenApiParameter(OpenApiParameter openApiParameter)
        {
            Log.Debug("starting CreateParameterFromOpenApiParameter()");
            TypeDefinition typeDefinition = ParseType(openApiParameter);
            Log.Debug("typeDefinition TypeName is {0}", typeDefinition.TypeName);
            bool isRequired = false;
            if (openApiParameter.Required)
            {
                isRequired = openApiParameter.Required;
            }

            ParameterIn parameterIn = ParameterIn.Query;
            ParameterLocation? parameterInLocation = openApiParameter.In;
            if (parameterInLocation != null)
            {
                if (parameterInLocation.Equals(ParameterLocation.Path))
                {
                    parameterIn = ParameterIn.Path;
                }
                else if (parameterInLocation.Equals(ParameterLocation.Header))
                {
                    parameterIn = ParameterIn.Header;
                }
            }

            string propDescription = string.Empty;
            if (openApiParameter.Description != null)
            {
                propDescription = openApiParameter.Description;
            }

            string collectionFormat = string.Empty;
            XCase.ProxyGenerator.REST.Parameter parameter = new XCase.ProxyGenerator.REST.Parameter(typeDefinition, parameterIn, isRequired, propDescription, collectionFormat);
            return parameter;
        }

        private void ParseDefinitions(OpenApiDocument openApiDocument, ProxyDefinition proxyDefinition)
        {
            Log.Debug("starting ParseDefinitions()");
            OpenApiComponents components = openApiDocument.Components;
            if (components == null)
            {
                return;
            }

            IDictionary<string, OpenApiSchema> schemaDictionary = components.Schemas;
            foreach (KeyValuePair<string, OpenApiSchema> schemaKeyValuePair in schemaDictionary)
            {
                Log.Debug("next schema {0}", schemaKeyValuePair.Key);
                bool addIt = true;
                ClassDefinition classDefinition = new ClassDefinition(schemaKeyValuePair.Key);
                IList<OpenApiSchema> allOfOpenApiSchemaList = schemaKeyValuePair.Value.AllOf;
                if (allOfOpenApiSchemaList != null && allOfOpenApiSchemaList.Count > 0)
                {
                    Log.Debug("openApiSchemaList is not null or empty");
                    foreach (OpenApiSchema itemToken in allOfOpenApiSchemaList)
                    {
                        Log.Debug("next itemToken {0}", itemToken.Title);
                        OpenApiReference refType = itemToken.Reference;
                        if (refType != null)
                        {
                            Log.Debug("refType Type is {0}", refType.Type);
                            string inheritsType = null;
                            if (refType.Type != null && refType.Type == ReferenceType.Schema)
                            {
                                if (refType.ReferenceV3.StartsWith("#/components/schemas/"))
                                {
                                    Log.Debug("refType.ReferenceV3 starts with #/components/schemas/");
                                    inheritsType = refType.ReferenceV3.Replace("#/components/schemas/", "");
                                }
                                else if (refType.ReferenceV2.StartsWith("#/definitions/"))
                                {
                                    Log.Debug("refType.ReferenceV2 starts with #/definitions/");
                                    inheritsType = refType.ReferenceV2.Replace("#/definitions/", "");
                                }
                                else
                                {
                                    Log.Warning("inheritsType is null");
                                }
                            }

                            Log.Debug("inheritsType is {0}", inheritsType);
                            classDefinition.Inherits = inheritsType;
                        }
                        else
                        {
                            Log.Warning("refType is null");
                        }

                        IDictionary<string, OpenApiSchema> propertiesDictionary = itemToken.Properties;
                        if (propertiesDictionary != null)
                        {
                            foreach (KeyValuePair<string, OpenApiSchema> propertyKeyValuePair in propertiesDictionary)
                            {
                                TypeDefinition typeDefinition = ParseType(propertyKeyValuePair);
                                classDefinition.Properties.Add(typeDefinition);
                            }
                        }
                    }
                }
                else
                {
                    Log.Debug("allOfOpenApiSchemaList is null or has zero count");
                    if (schemaKeyValuePair.Value.Type == null || !schemaKeyValuePair.Value.Type.Equals("array"))
                    {
                        Log.Debug("schemaKeyValuePair.Value.Type is {0}", schemaKeyValuePair.Value.Type);
                        IDictionary<string, OpenApiSchema> propertiesDictionary = schemaKeyValuePair.Value.Properties;
                        if (propertiesDictionary != null)
                        {
                            foreach (KeyValuePair<string, OpenApiSchema> propertyKeyValuePair in propertiesDictionary)
                            {
                                TypeDefinition typeDefinition = ParseType(propertyKeyValuePair);
                                classDefinition.Properties.Add(typeDefinition);
                            }
                        }
                    }
                    else
                    {
                        Log.Debug("schemaKeyValuePair.Value.Type is array");
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

        private TypeDefinition ParseType(KeyValuePair<string, OpenApiSchema> propertyKeyValuePair)
        {
            Log.Debug("starting ParseType(KeyValuePair<string, OpenApiSchema> propertyKeyValuePair)");
            string name = propertyKeyValuePair.Key;
            Log.Debug("name is {0}", name);
            bool isNullable = propertyKeyValuePair.Value.Nullable;
            Log.Debug("isNullable is {0}", isNullable);
            string[] enumValues = null;
            string typeName = null;
            if ("array".Equals(propertyKeyValuePair.Value.Type))
            {
                Log.Debug("propertyKeyValuePair.Value Type is array");
                OpenApiSchema itemsOpenApiSchema = propertyKeyValuePair.Value.Items;
                if (itemsOpenApiSchema.Reference != null)
                {
                    Log.Debug("schema Reference is {0}", itemsOpenApiSchema.Reference.ReferenceV3);
                    if (itemsOpenApiSchema.Reference.ReferenceV3.StartsWith("#/components/schemas/"))
                    {
                        isNullable = false;
                        typeName = string.Format("{0}[]", itemsOpenApiSchema.Reference.ReferenceV3.Substring("#/components/schemas/".Length));
                        Log.Debug("typeName is {0}", typeName);
                    }
                }
            }
            else
            {
                Log.Debug("propertyKeyValuePair.Value Type is not array");
                typeName = GetTypeName(propertyKeyValuePair.Value, isNullable);
                Log.Debug("typeName is {0}", typeName);
                List<string> enumList = new List<string>();
                IList<IOpenApiAny> enums = propertyKeyValuePair.Value.Enum;
                if (enums != null && enums.Count > 0)
                {
                    Log.Debug("enums is not null and count > 0");
                    bool anyRawNumbers = false;
                    foreach (IOpenApiAny enumIOpenApiAny in enums)
                    {
                        Log.Debug("next enumIOpenApiAny");
                        string enumValue = ((OpenApiPrimitive<string>)enumIOpenApiAny).Value;
                        Log.Debug("enumValue is {0}", enumValue);
                        decimal value;
                        if (Decimal.TryParse(enumValue, out value))
                        {
                            anyRawNumbers = true;
                        }

                        enumList.Add(enumValue);
                        Log.Debug("added enumValue {0}", enumValue);
                    }

                    Log.Debug("anyRawNumbers is {0}", anyRawNumbers);
                    if (anyRawNumbers == false)
                    {
                        enumValues = enumList.ToArray();
                        typeName = FixTypeName(name + "Values");
                    }
                }
            }

            TypeDefinition typeDefinition = new TypeDefinition(typeName, name, enumValues, isNullable);
            return typeDefinition;
        }

        private string GetTypeName(OpenApiSchema schema, bool isNullable)
        {
            Log.Debug("starting GetTypeName(OpenApiSchema schema, bool isNullable)");
            if (schema.Reference != null)
            {
                Log.Debug("schema Reference is {0}", schema.Reference.ReferenceV3);
                if (schema.Reference.ReferenceV3.StartsWith("#/components/schemas/"))
                {
                    return schema.Reference.ReferenceV3.Substring("#/components/schemas/".Length);
                }
            }
            else if ("integer".Equals(schema.Type))
            {
                return "int";
            }
            else if ("number".Equals(schema.Type))
            {
                return "float";
            }
            else if ("boolean".Equals(schema.Type))
            {
                return "bool";
            }
            else
            {
                return schema.Type;
            }

            return null;
        }

        private TypeDefinition ParseType(OpenApiParameter openApiParameter)
        {
            Log.Debug("starting ParseType(OpenApiParameter parameter)");
            bool isArray = false;
            bool isNullable = false;
            string name = openApiParameter.Name;
            Log.Debug("name is {0}", name);
            string typeName = openApiParameter.Schema.Type;
            string[] enumValues = null;
            if (openApiParameter != null)
            {
                if (!"array".Equals(openApiParameter.Schema.Type))
                {
                    Log.Debug("parameter type is not array");
                    typeName = GetTypeName(openApiParameter, out isNullable);
                    List<string> enumList = new List<string>();
                    IList<IOpenApiAny> enums = openApiParameter.Schema.Enum;
                    if (enums != null && enums.Count > 0)
                    {
                        Log.Debug("enums is not null and count > 0");
                        bool anyRawNumbers = false;
                        foreach (IOpenApiAny enumIOpenApiAny in enums)
                        {
                            Log.Debug("next enumIOpenApiAny");
                            string enumValue = ((OpenApiPrimitive<string>)enumIOpenApiAny).Value;
                            Log.Debug("enumValue is {0}", enumValue);
                            decimal value;
                            if (Decimal.TryParse(enumValue, out value))
                            {
                                anyRawNumbers = true;
                            }

                            enumList.Add(enumValue);
                            Log.Debug("added enumValue {0}", enumValue);
                        }

                        Log.Debug("anyRawNumbers is {0}", anyRawNumbers);
                        if (anyRawNumbers == false)
                        {
                            enumValues = enumList.ToArray();
                            typeName = FixTypeName(name + "Values");
                        }
                    }
                }
                else
                {
                    Log.Debug("parameter type is array");
                    isArray = true;
                    OpenApiSchema itemsOpenApiSchema = openApiParameter.Schema.Items;
                    typeName = GetTypeName(itemsOpenApiSchema, out isNullable) + "[]";
                    Log.Debug("items typeName is {0}", typeName);
                }

                typeName = FixGenericName(typeName);
                Log.Debug("typeName is {0}", typeName);
                TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
                type.IsArray = isArray;
                return type;
            }
            else
            {
                return null;
            }
        }

        private string ParseRef(string input)
        {
            Log.Debug("starting ParseRef()");
            return input.StartsWith("#/definitions/") ? input.Substring("#/definitions/".Length) : input;
        }

        private string GetTypeName(OpenApiParameter parameter, out bool isNullable)
        {
            Log.Debug("starting GetTypeName()");
            isNullable = true;
            string type = parameter.Schema.Type;
            Log.Debug("type is {0}", type);
            if ("number".Equals(type))
            {
                string format = parameter.Schema.Format;
                Log.Debug("format is {0}", format);
                if ("double".Equals(format))
                {
                    return "double";
                }
                else
                {
                    return "float";
                }
            }

            return type;
        }

        private string GetTypeName(string name, out bool isNullable)
        {
            Log.Debug("starting GetTypeName()");
            isNullable = true;
            return null;
        }

        private string GetTypeName(OpenApiSchema schema, out bool isNullable)
        {
            Log.Debug("starting GetTypeName(OpenApiSchema schema, out bool isNullable)");
            if (schema.Type != null && schema.Type.Equals("array"))
            {
                Log.Debug("schema.Type is not null and schema.Type equals array");
                isNullable = false;
                string itemsType = GetTypeName(schema.Items, isNullable);
                return string.Format("{0}[]", itemsType);
            }

            if (schema.Reference != null)
            {
                Log.Debug("schema Reference is {0}", schema.Reference.ReferenceV3);
                if (schema.Reference.ReferenceV3.StartsWith("#/components/schemas/"))
                {
                    isNullable = false;
                    return schema.Reference.ReferenceV3.Substring("#/components/schemas/".Length);
                }
            }

            if (schema.Type != null)
            {
                Log.Debug("schema.Type is not null");
                isNullable = false;
                return schema.Type;
            }

            isNullable = true;
            return null;
        }
    }
}
