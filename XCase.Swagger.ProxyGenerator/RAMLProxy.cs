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

    public class RAMLProxy : RESTProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion
        public RAMLProxy() : base()
        { }


        public RAMLProxy(Uri baseUrl) : base(baseUrl)
        { }

        public RAMLProxy(Uri baseUrl, string username, string password, string tenant)
            : base(baseUrl)
        {
            _username = username;
            _password = password;
            _tenantId = tenant;
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

        public override HttpClient BuildHttpClient(string username, string password, string tenantId)
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

        public override string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            Log.DebugFormat("starting GetAccessToken()");
            return null;
        }
    }
}
