namespace XCase.REST.ProxyGenerator
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
//    using Microsoft.Owin.Testing;
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Serilog;
    using Serilog.Events;
    using XCase.ProxyGenerator;
    using XCase.REST.ProxyGenerator.Generator;
    using XCase.REST.ProxyGenerator.OpenAPI;

    public abstract class SwaggerProxyGenerator : CSharpProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        //public static readonly Serilog.ILogger Log = new LoggerConfiguration().MinimumLevel.Debug().WriteTo.Console().WriteTo.File("XCaseServiceClient.log", rollingInterval: RollingInterval.Day).CreateLogger();

        #endregion

        public static StringBuilder SourceStringBuilder { get; set; }
        public static ConcurrentDictionary<IAPIProxySettingsEndpoint, string> swaggerDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();

        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        public override abstract IServiceDefinition GenerateSourceString(string document);

        public static async Task GetEndpointSwaggerDoc(string requestUri, IAPIProxySettingsEndpoint endPoint)
        {
            Log.Debug("starting GetEndpointSwaggerDoc()");
            string swaggerString = null;
            System.Net.WebRequest webRequest = System.Net.WebRequest.Create(requestUri);
            Log.Debug("created webRequest");
            using (WebResponse webResponse = await webRequest.GetResponseAsync().ConfigureAwait(false))
            {
                Log.Debug("got webResponse");
                Stream webResponseStream = webResponse.GetResponseStream();
                StreamReader webResponseStreamReader = new StreamReader(webResponseStream);
                swaggerString = await webResponseStreamReader.ReadToEndAsync().ConfigureAwait(false);
            }

            if (swaggerString == null)
            {
                throw new Exception(string.Format("Error downloading from: {0}", endPoint.GetUrl()));
            }

            Log.Debug("downloaded: {0}", requestUri);
            swaggerDocDictionary.GetOrAdd(endPoint, swaggerString);
            Log.Debug("finishing GetEndpointSwaggerDoc()");
        }
    }
}
