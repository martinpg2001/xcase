using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Reflection;
using System.Text;
using XCase.REST.ProxyGenerator;

namespace XCase.REST.ProxyGenerator.Proxy
{
    public class CustomBaseProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public CustomBaseProxy(Uri baseUrl) : base(baseUrl)
        {
            _username = "";
            _password = "";
            _tenantId = "";
        }

        public override HttpClient BuildHttpClient(string username, string password, string tenant)
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy, UseCookies = false };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.LogDebug("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.LogDebug("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, tenant);
            this.token = token;
            Log.LogDebug("set token to {0}", token);
            return httpClient;
        }

        public override HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token)
        {
            throw new NotImplementedException();
        }

        public override string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            /* TODO: implement if access token required */
            return null;
        }

        public override string GetSwaggerDocument()
        {
            throw new NotImplementedException();
        }
    }
}
