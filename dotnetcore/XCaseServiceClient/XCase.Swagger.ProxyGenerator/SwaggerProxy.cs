namespace XCase.REST.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Net.Http;
    using System.Net.Http.Headers;
    using System.Reflection;
    using System.Threading.Tasks;
    using System.Web;
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public abstract class SwaggerProxy : RESTProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public SwaggerProxy()
        {

        }

        public SwaggerProxy(Uri baseUrl, string username, string password, string tenant)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _tenantId = tenant;
        }

        public SwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public override HttpClient BuildHttpClient()
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.DebugFormat("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.DebugFormat("set BaseAddress to {0}", _baseUrl);
            Log.DebugFormat("_username is {0}", _username);
            Log.DebugFormat("_password is {0}", _password);
            Log.DebugFormat("_tenantId is {0}", _tenantId);
            string token = this.GetAccessToken(httpClient, _username, _password, _tenantId);
            this.token = token;
            Log.DebugFormat("set token to {0}", token);
            return httpClient;
        }

        public override HttpClient BuildHttpClient(string username, string password, string tenant)
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy, UseCookies = false };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.DebugFormat("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.DebugFormat("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, tenant);
            this.token = token;
            Log.DebugFormat("set token to {0}", token);
            return httpClient;
        }


        public abstract string GetSwaggerDocument();

        public abstract HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token);
    }
}
