namespace XCase.REST.ProxyGenerator.Proxy
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
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public class OpenSwaggerProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public OpenSwaggerProxy(Uri baseUrl) : base(baseUrl)
        {}

        public OpenSwaggerProxy(Uri baseUrl, string username, string password, string tenant)
            : base(baseUrl)
        {
            _username = username;
            _password = password;
            _tenantId = tenant;
        }

        public override HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token)
        {
            HttpRequestMessage request = new HttpRequestMessage() { RequestUri = new Uri(requestURL), Method = HttpMethod.Get };
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", token);
            return request;
        }

        public override string GetSwaggerDocument()
        {
            Log.LogDebug("starting GetOpenSwaggerDocument()");
            string url = "api/swagger/docs/v1";
            //string url = "api/api/swagger/docs/v1";
            Log.LogDebug("url is {0}", url);
            using (HttpClient apiClient = BuildHttpClient(_username, _password, _tenantId))
            {
                Log.LogDebug("about to invoke method using url {0}", url);
                Log.LogDebug("method is GET");
                string requestURL = string.Format("{0}{1}", apiClient.BaseAddress, url);
                Log.LogDebug("requestURL is {0}", requestURL);
                HttpRequestMessage request = CreateRequestMessageForSwaggerDocument(requestURL, token);
                Log.LogDebug("about to send request for Swagger document");
                HttpResponseMessage response = apiClient.SendAsync(request).Result;
                Log.LogDebug("response StatusCode is {0}", response.StatusCode.ToString());
                string content = response.Content.ReadAsStringAsync().Result;
                Log.LogDebug("content is {0}", content);
                return content;
            }
        }

        public override string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            return GetAccessTokenFromUsernamePassword(client, userName, password, tenantId);
        }

        public string GetAccessTokenFromAuthorizationCode(HttpClient client, string clientId = "admin", string clientSecret = "", string tenantId = null)
        {
            Log.LogDebug("starting GetAccessToken()");
            string tokenURL = string.Format("{0}token", _baseUrl);
            //string tokenURL = string.Format("{0}auth/oauth/token", _baseUrl);
            Log.LogDebug("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            string authorizationCode = "e864c8edc6264a719c216aa81489ac57";
            string redirectUri = "http://cornhill:80/Open.Services/oauth2/callback";
            Log.LogDebug("clientId is {0}", clientId);
            Log.LogDebug("clientSecret is {0}", clientSecret);
            Log.LogDebug("tenantId is {0}", tenantId);
            Log.LogDebug("authorizationCode is {0}", authorizationCode);
            Log.LogDebug("redirectUri is {0}", redirectUri);
            request.Content = new FormUrlEncodedContent(
                new[]
                {
                    new KeyValuePair<string, string>("grant_type", "authorization_code"),
                    new KeyValuePair<string, string>("client_id", clientId),
                    new KeyValuePair<string, string>("client_secret", clientSecret),
                    new KeyValuePair<string, string>("tenant_id", tenantId),
                    new KeyValuePair<string, string>("code", authorizationCode),
                    new KeyValuePair<string, string>("redirect_uri", redirectUri)
                });

            HttpResponseMessage response = client.SendAsync(request).Result;
            Log.LogDebug("got response status code {0}", response.StatusCode.ToString());
            if (!response.IsSuccessStatusCode)
            {
                Log.LogDebug("response status code is not IsSuccessStatusCode");
                return null;
            }

            Log.LogDebug("response status code is IsSuccessStatusCode");
            string content = response.Content.ReadAsStringAsync().Result;
            Log.LogDebug("content is {0}", content);
            using (StringReader stringReader = new StringReader(content))
            {
                using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                {
                    JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                    string token = json["access_token"].Value<string>();
                    Log.LogDebug("token is {0}", token);
                    return token;
                }
            }
        }

        public string GetAccessTokenFromUsernamePassword(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            Log.LogDebug("starting GetAccessToken()");
            string tokenURL = string.Format("{0}token", _baseUrl);
            //string tokenURL = string.Format("{0}auth/oauth/token", _baseUrl);
            Log.LogDebug("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            Log.LogDebug("userName is {0}", userName);
            Log.LogDebug("password is {0}", password);
            Log.LogDebug("tenantId is {0}", tenantId);
            request.Content = new FormUrlEncodedContent(
                new[]
                {
                    new KeyValuePair<string, string>("grant_type", "password"),
                    new KeyValuePair<string, string>("username", userName),
                    new KeyValuePair<string, string>("password", password),
                    new KeyValuePair<string, string>("tenantId", tenantId)
                });

            HttpResponseMessage response = client.SendAsync(request).Result;
            Log.LogDebug("got response status code {0}", response.StatusCode.ToString());
            if (!response.IsSuccessStatusCode)
            {
                Log.LogDebug("response status code is not IsSuccessStatusCode");
                return null;
            }

            Log.LogDebug("response status code is IsSuccessStatusCode");
            string content = response.Content.ReadAsStringAsync().Result;
            Log.LogDebug("content is {0}", content);
            using (StringReader stringReader = new StringReader(content))
            {
                using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                {
                    JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                    string token = json["access_token"].Value<string>();
                    Log.LogDebug("token is {0}", token);
                    return token;
                }
            }
        }
    }
}
