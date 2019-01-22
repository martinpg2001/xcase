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
    using System.Text;
    using System.Threading.Tasks;
    using System.Web;
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public class NetDocsRESTProxy : RESTProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public NetDocsRESTProxy()
        {

        }

        public NetDocsRESTProxy(Uri baseUrl, string username, string password, string tenant)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _tenantId = tenant;
            BuildHttpClient(username, password, tenant);
        }

        public NetDocsRESTProxy(Uri baseUrl)
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
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.DebugFormat("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.DebugFormat("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, tenant);
            this.token = token;
            Log.DebugFormat("set token to {0}", token);
            return httpClient;
        }

        public override string GetAccessToken(HttpClient client, string clientId = "admin", string clientSecret = "", string authorizationCode = null)
        {
            Log.DebugFormat("starting GetAccessToken()");
            string redirectURL = "https://www.intapp.com/auth/netdocuments-auth?region=US";
            string tokenURL = _baseUrl + "v1/OAuth";
            Log.DebugFormat("tokenURL is {0}", tokenURL);
            Log.DebugFormat("clientId is {0}", clientId);
            Log.DebugFormat("clientSecret is {0}", clientSecret);
            Log.DebugFormat("authorizationCode is {0}", authorizationCode);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/xml"));
            string clientIdSecret = clientId + ":" + clientSecret;
            byte[] clientIdSecretBytes = Encoding.UTF8.GetBytes(clientIdSecret);
            string encodedClientIdSecret = Convert.ToBase64String(clientIdSecretBytes);
            Log.DebugFormat("encodedClientIdSecret is {0}", encodedClientIdSecret);
            string authorizationHeader = string.Format("Basic {0}", encodedClientIdSecret);
            Log.DebugFormat("authorizationHeader is {0}", authorizationHeader);
            request.Headers.Add("Authorization", authorizationHeader);
            //request.Content = new FormUrlEncodedContent(
            //new[]
            //{
            //    new KeyValuePair<string, string>("grant_type", "authorization_code"),
            //    new KeyValuePair<string, string>("code", authorizationCode),
            //    new KeyValuePair<string, string>("redirect_uri", redirectURL)
            //});
            string stringContent = "grant_type=authorization_code&code=" + HttpUtility.UrlEncode(authorizationCode) + "&redirect_uri=" + redirectURL;
            Log.DebugFormat("stringContent is {0}", stringContent);
            request.Content = new StringContent(stringContent, Encoding.UTF8, "application/x-www-form-urlencoded");
            HttpResponseMessage response = client.SendAsync(request).Result;
            Log.DebugFormat("got response status code {0}", response.StatusCode.ToString());
            if (!response.IsSuccessStatusCode)
            {
                Log.DebugFormat("response status code is not IsSuccessStatusCode");
                throw new Exception("Response status code is " + response.StatusCode);
            }

            Log.DebugFormat("response status code is IsSuccessStatusCode");
            string content = response.Content.ReadAsStringAsync().Result;
            Log.DebugFormat("content is {0}", content);
            StringReader stringReader = null;
            try
            {
                stringReader = new StringReader(content);
                using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                {
                    stringReader = null;
                    JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                    string token = json["access_token"].Value<string>();
                    Log.DebugFormat("token is {0}", token);
                    return token;
                }
            }
            finally
            {
                if (stringReader != null)
                {
                    stringReader.Dispose();
                }
            }
        }
    }
}
