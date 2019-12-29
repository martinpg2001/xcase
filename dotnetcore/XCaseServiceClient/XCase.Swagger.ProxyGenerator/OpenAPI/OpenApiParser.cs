using log4net;
using Microsoft.OpenApi.Any;
using Microsoft.OpenApi.Models;
using Microsoft.OpenApi.Readers;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
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
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public override IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint)
        {
            Log.Debug("starting ParseDoc()");
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            try
            {
                byte[] byteArray = Encoding.ASCII.GetBytes(document);
                MemoryStream stream = new MemoryStream(byteArray);
                OpenApiDocument openApiDocument = new OpenApiStreamReader().Read(stream, out var diagnostic);
                Log.DebugFormat("read model");
                OpenApiInfo openApiInfo = openApiDocument.Info;
                if (openApiInfo != null)
                {
                    proxyDefinition.Description = openApiInfo.Description;
                    proxyDefinition.Title = openApiInfo.Title;
                    proxyDefinition.Version = openApiInfo.Version;
                }

                Log.DebugFormat("proxyDefinition Description is {0}", proxyDefinition.Description);
                Log.DebugFormat("proxyDefinition Title is {0}", proxyDefinition.Title);
                Log.DebugFormat("proxyDefinition Version is {0}", proxyDefinition.Version);
                if (openApiDocument.Servers != null)
                {
                    proxyDefinition.Host = openApiDocument.Servers[0].Url;
                }
                else
                {
                    proxyDefinition.Host = endpoint.GetHost();
                }

                Log.DebugFormat("proxyDefinition Host is {0}", proxyDefinition.Host);
                if (openApiDocument.Servers != null)
                {
                    proxyDefinition.BasePath = openApiDocument.Servers[0].Url;
                }
                else
                {
                    proxyDefinition.BasePath = endpoint.GetBasePath();
                }

                Log.DebugFormat("proxyDefinition BasePath is {0}", proxyDefinition.BasePath);
                ParsePaths(openApiDocument, proxyDefinition, false);
            }
            catch (Exception e)
            {
                Log.WarnFormat(e.Message);
                Log.WarnFormat(e.StackTrace);
            }

            Log.Debug("finishing ParseDoc()");
            return proxyDefinition;
        }


        private void ParsePaths(OpenApiDocument openApiDocument, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting ParsePaths()");
            OpenApiPaths openApiPaths = openApiDocument.Paths;
            List<KeyValuePair<string, OpenApiPathItem>> endpointEnumerable = openApiPaths.ToList();
            foreach(KeyValuePair<string, OpenApiPathItem> endpoint in endpointEnumerable)
            {
                List<XCase.ProxyGenerator.REST.Operation> endpointOperationList = CreateOperationListFromEndpoint(endpoint, parseOperationIdForProxyName);
                Log.DebugFormat("created endpointOperationList");
                proxyDefinition.Operations.AddRange(endpointOperationList);
            }

            Log.Debug("finishing ParsePaths()");
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromEndpoint(KeyValuePair<string, OpenApiPathItem> endpoint, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromEndpoint()");
            List<XCase.ProxyGenerator.REST.Operation> operationList = new List<XCase.ProxyGenerator.REST.Operation>();
            List < XCase.ProxyGenerator.REST.Operation> restOperationList = CreateOperationListFromPathToken(endpoint.Key, endpoint.Value, null, parseOperationIdForProxyName);
            operationList.AddRange(restOperationList);
            return operationList;
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromPathToken(string path, OpenApiPathItem endpoint, List<XCase.ProxyGenerator.REST.Parameter> parameterList, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromPathToken()");
            List<XCase.ProxyGenerator.REST.Operation> pathOperationList = new List<XCase.ProxyGenerator.REST.Operation>();
            foreach (KeyValuePair<OperationType, OpenApiOperation> operation in endpoint.Operations)
            {
                XCase.ProxyGenerator.REST.Operation restOperation = CreateOperationFromOpenApiOperation(path, operation, null, true);
                pathOperationList.Add(restOperation);
            }

            return pathOperationList;
        }

        private XCase.ProxyGenerator.REST.Operation CreateOperationFromOpenApiOperation(string path, KeyValuePair<OperationType, OpenApiOperation> keyValuePair, List<XCase.ProxyGenerator.REST.Parameter> parameterList, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationFromOpenApiOperation()");
            string method = keyValuePair.Key.ToString();
            Log.DebugFormat("method is {0}", method);
            string operationId = method + keyValuePair.Value.OperationId;// operationToken.First["operationId"].ToString();
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

            string description = keyValuePair.Value.Description;
            string returnType;
            List<KeyValuePair<string, OpenApiResponse>> responses = keyValuePair.Value.Responses.ToList();
            string schema = responses.First<KeyValuePair<string, OpenApiResponse>>().Value.Description;
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
                    Log.DebugFormat("created parameter");
                    parameters.Add(parameter);
                }
            }

            XCase.ProxyGenerator.REST.Operation operation = new XCase.ProxyGenerator.REST.Operation(returnType, method, path, parameters, operationId, description, proxyName);
            Log.DebugFormat("created operation");
            return operation;
        }

        private Parameter CreateParameterFromOpenApiParameter(OpenApiParameter paramToken)
        {
            TypeDefinition type = ParseType(paramToken);
            bool isRequired = false;
            if (paramToken.Required)
            {
                isRequired = paramToken.Required;
            }

            ParameterIn parameterIn = ParameterIn.Body;
            parameterIn = ParameterIn.Query;
            string propDescription = string.Empty;
            if (paramToken.Description != null)
            {
                propDescription = paramToken.Description;
            }

            string collectionFormat = string.Empty;
            XCase.ProxyGenerator.REST.Parameter parameter = new XCase.ProxyGenerator.REST.Parameter(type, parameterIn, isRequired, propDescription, collectionFormat);
            return parameter;
        }

        private void ParseDefinitions(OpenApiDocument model, ProxyDefinition proxyDefinition)
        {
            Log.Debug("starting ParseDefinitions()");
            OpenApiComponents components = model.Components;
            Log.Debug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(OpenApiParameter parameter)
        {
            bool isNullable;
            //JToken workingToken;
            string name = parameter.Name;
            //if (token.First is JProperty)
            //{
            //    workingToken = token;
            //    name = workingToken["name"].ToString();
            //}
            //else
            //{
            //    workingToken = token.First;
            //    if (token is JProperty)
            //    {
            //        name = ((JProperty)token).Name;
            //    }
            //    else
            //    {
            //        name = string.Empty;
            //    }
            //}

            if (parameter != null)
            {
                string typeName = GetTypeName(parameter, out isNullable);
                List<string> enumList = new List<string>();
                IList<IOpenApiAny> enums = parameter.Schema.Enum;
                string[] enumValues = null;
                if (enums != null)
                {
                    bool anyRawNumbers = false;
                    foreach (IOpenApiAny enumValueToken in enums)
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

        private string GetTypeName(OpenApiParameter parameter, out bool isNullable)
        {
            isNullable = true;
            return parameter.Schema.Type;
        }

        private string GetTypeName(string name, out bool isNullable)
        {
            isNullable = true;
            return null;
        }
    }
}
