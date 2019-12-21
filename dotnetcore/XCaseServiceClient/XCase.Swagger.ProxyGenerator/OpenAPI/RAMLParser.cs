using log4net;
using RAML.Parser;
using RAML.Parser.Model;
using System;
using System.Collections.Generic;
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
            Task task = parser.Load("box.raml").ContinueWith(t =>
            {
                //var document = t.Result;
                //foreach (var resource in document.Resources)
                //{
                //    Console.WriteLine("Resource:" + resource.RelativeUri);
                //    foreach (var method in resource.Methods)
                //    {
                //        Console.WriteLine("\t" + method.Verb);
                //    }
                //}
            });

            task.Wait();
            IProxyDefinition proxyDefinition = new ProxyDefinition();
            Log.Debug("finishing ParseDoc()");
            return proxyDefinition;
        }


        private void ParsePaths(AmfModel model, ProxyDefinition proxyDefinition, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting ParsePaths()");

        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromJObject(AmfModel model, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromJObject()");
            List<XCase.ProxyGenerator.REST.Operation> operationList = new List<XCase.ProxyGenerator.REST.Operation>();

            return operationList;
        }

        private List<XCase.ProxyGenerator.REST.Operation> CreateOperationListFromPathToken(AmfModel model, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationListFromPathToken()");
            List<XCase.ProxyGenerator.REST.Operation> pathOperationList = new List<XCase.ProxyGenerator.REST.Operation>();

            return pathOperationList;
        }

        private XCase.ProxyGenerator.REST.Operation CreateOperationFromOperationToken(AmfModel model, List<XCase.ProxyGenerator.REST.Parameter> parameterList, string path, bool parseOperationIdForProxyName)
        {
            Log.DebugFormat("starting CreateOperationFromOperationToken()");

            return null;
        }

        private XCase.ProxyGenerator.REST.Parameter CreateParameterFromAmfModel(AmfModel model)
        {
            return null;
        }

        private void ParseDefinitions(AmfModel model, ProxyDefinition proxyDefinition)
        {
            Log.Debug("starting ParseDefinitions()");
 
            Log.Debug("finishing ParseDefinitions()");
        }

        private TypeDefinition ParseType(AmfModel model)
        {

            return null;
        }

        private string ParseRef(string input)
        {
            return input.StartsWith("#/definitions/") ? input.Substring("#/definitions/".Length) : input;
        }

        private string GetTypeName(AmfModel model, out bool isNullable)
        {
            isNullable = true;
            return null;
        }
    }
}
