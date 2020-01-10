namespace XCase.REST.ProxyGenerator.Proxy
{
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Net.Http;
    using System.Net.Http.Headers;
    using System.Reflection;
    using System.Text;
    using XCase.REST.ProxyGenerator;

    public class KlearNowProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public KlearNowProxy(Uri baseUrl) : base(baseUrl)
        {
            _username = "";
            _password = "";
            _tenantId = "";
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

        public override HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token)
        {
            throw new NotImplementedException();
        }

        public override string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            Log.DebugFormat("starting GetAccessToken()");
            string tokenURL = _baseUrl.ToString();
            Log.DebugFormat("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            Log.DebugFormat("tenantId is {0}", tenantId);
            string loginString = string.Format("{{\"email\" : \"{0}\", \"password\": \"{1}\" }}", userName, password);
            Log.DebugFormat("loginString is {0}", loginString);
            request.Content = new StringContent(loginString);
            HttpResponseMessage response = client.SendAsync(request).Result;
            Log.DebugFormat("got response status code {0}", response.StatusCode.ToString());
            if (!response.IsSuccessStatusCode)
            {
                Log.DebugFormat("response status code is not IsSuccessStatusCode");
                return null;
            }

            Log.DebugFormat("response status code is IsSuccessStatusCode");
            string content = response.Content.ReadAsStringAsync().Result;
            Log.DebugFormat("content is {0}", content);
            using (StringReader stringReader = new StringReader(content))
            {
                using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                {
                    JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                    string token = json["kxToken"].Value<string>();
                    Log.DebugFormat("token is {0}", token);
                    return token;
                }
            }
        }

        public override string GetSwaggerDocument()
        {
            throw new NotImplementedException();
        }
    }
}
