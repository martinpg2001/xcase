using log4net;
using RAML.Parser;
using RAML.Parser.Model;
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
            RamlParser parser = new RamlParser();
            string fileName = Path.GetRandomFileName();
            Log.DebugFormat("fileName is {0}", fileName);
            File.WriteAllText(fileName, document);
            Log.DebugFormat("wrote text to file");
            AmfModel model = parser.Load(fileName).Result;
            Log.DebugFormat("loaded model");
            ProxyDefinition proxyDefinition = new ProxyDefinition();
            ParsePaths(model, proxyDefinition, false);
            Log.Debug("finishing ParseDoc()");
            return proxyDefinition;
        }


        private void ParsePaths(AmfModel model, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting ParsePaths()");
            WebApi webApi = model.WebApi;
            IEnumerable<EndPoint> endpointEnumerable = webApi.EndPoints;
            foreach(EndPoint endpoint in endpointEnumerable)
            {
                List<XCase.ProxyGenerator.REST.Operation> endpointOperationList = CreateOperationListFromEndpoint(endpoint, parseOperationIdForProxyName);
                Log.DebugFormat("created endpointOperationList");
                proxyDefinition.Operations.AddRange(endpointOperationList);
            }

            Log.Debug("finishing ParsePaths()");
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromEndpoint(EndPoint endpoint, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromJObject()");
            List<XCase.ProxyGenerator.REST.Operation> operationList = new List<XCase.ProxyGenerator.REST.Operation>();
            foreach (RAML.Parser.Model.Operation operation in endpoint.Operations)
            {
                XCase.ProxyGenerator.REST.Operation restOperation = CreateOperationFromRAMLOperation(operation, null, null, parseOperationIdForProxyName);
                operationList.Add(restOperation);
            }

            return operationList;
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromPathToken(EndPoint endpoint, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromPathToken()");
            List<XCase.ProxyGenerator.REST.Operation> pathOperationList = new List<XCase.ProxyGenerator.REST.Operation>();

            return pathOperationList;
        }

        private XCase.ProxyGenerator.REST.Operation CreateOperationFromRAMLOperation(RAML.Parser.Model.Operation ramlOperation, List<XCase.ProxyGenerator.REST.Parameter> parameterList, string path, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationFromRAMLOperation()");
            string method = ramlOperation.Method;
            Log.DebugFormat("method is {0}", method);
            string operationId = method + path.Substring(1);// operationToken.First["operationId"].ToString();
            if (ramlOperation.Name != null) {
                operationId = ramlOperation.Name;
            }

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

            string description = ramlOperation.Description;
            string returnType;
            IEnumerable<Response> responses = ramlOperation.Responses;
            string schema = ramlOperation.Schemes.First<string>();
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

            IEnumerable<RAML.Parser.Model.Parameter> paramTokens = ramlOperation.Request.QueryParameters;
            if (paramTokens != null)
            {
                foreach (RAML.Parser.Model.Parameter paramToken in paramTokens)
                {
                    XCase.ProxyGenerator.REST.Parameter parameter = CreateParameterFromRAMLParameter(paramToken);
                    Log.DebugFormat("created parameter");
                    parameters.Add(parameter);
                }
            }

            XCase.ProxyGenerator.REST.Operation operation = new XCase.ProxyGenerator.REST.Operation(returnType, method, path, parameters, operationId, description, proxyName);
            Log.DebugFormat("created operation");
            return operation;
        }

        private XCase.ProxyGenerator.REST.Parameter CreateParameterFromRAMLParameter(RAML.Parser.Model.Parameter paramToken)
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

        private void ParseDefinitions(AmfModel model, ProxyDefinition proxyDefinition)
        {
            Log.Debug("starting ParseDefinitions()");
 
            Log.Debug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(RAML.Parser.Model.Parameter parameter)
        {

            return null;
        }

        private string ParseRef(string input)
        {
            return input.StartsWith("#/definitions/") ? input.Substring("#/definitions/".Length) : input;
        }

        private string GetTypeName(string name, out bool isNullable)
        {
            isNullable = true;
            return null;
        }
    }
}
