using log4net;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using XCase.ProxyGenerator;

namespace XCase.REST.ProxyGenerator.Generator
{
    public abstract class RAMLProxyGenerator : RESTProxyGenerator
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public static StringBuilder SourceStringBuilder { get; set; }
        public static ConcurrentDictionary<IAPIProxySettingsEndpoint, string> ramlDocDictionary = new ConcurrentDictionary<IAPIProxySettingsEndpoint, string>();

        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        public override abstract IServiceDefinition GenerateSourceString(string document);

        public static async Task GetEndpointRAMLDoc(string requestUri, IAPIProxySettingsEndpoint endPoint)
        {
            Log.Debug("starting GetEndpointRAMLDoc()");
            string ramlString = null;
            System.Net.WebRequest webRequest = System.Net.WebRequest.Create(requestUri);
            Log.Debug("created webRequest");
            using (WebResponse webResponse = await webRequest.GetResponseAsync().ConfigureAwait(false))
            {
                Log.Debug("got webResponse");
                Stream webResponseStream = webResponse.GetResponseStream();
                StreamReader webResponseStreamReader = new StreamReader(webResponseStream);
                ramlString = await webResponseStreamReader.ReadToEndAsync().ConfigureAwait(false);
            }

            if (ramlString == null)
            {
                throw new Exception(string.Format("Error downloading from: {0}", endPoint.GetUrl()));
            }

            Log.DebugFormat("downloaded: {0}", requestUri);
            ramlDocDictionary.GetOrAdd(endPoint, ramlString);
            Log.Debug("finishing GetEndpointSwaggerDoc()");
        }
    }
}
