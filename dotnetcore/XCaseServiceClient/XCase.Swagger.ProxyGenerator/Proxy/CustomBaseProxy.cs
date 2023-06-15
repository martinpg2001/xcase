using System;
using System.Net.Http;

namespace XCase.REST.ProxyGenerator.Proxy
{
    public class CustomBaseProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        #endregion

        public CustomBaseProxy(Uri baseUrl) : base(baseUrl)
        {
            _username = "";
            _password = "";
            _domain = "";
        }

        public override HttpClient BuildHttpClient(string username, string password, string domain)
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy, UseCookies = false };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.Debug("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.Debug("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, domain);
            this.token = token;
            Log.Debug("set token to {0}", token);
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
