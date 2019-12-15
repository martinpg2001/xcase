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
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public class OpenCloudSwaggerProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public OpenCloudSwaggerProxy(Uri baseUrl) : base(baseUrl)
        { }

        public OpenCloudSwaggerProxy(Uri baseUrl, string username, string password, string tenant)
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
            Log.Debug("starting GetSwaggerDocument()");
            string url = "api/swagger/docs/v1";
            Log.DebugFormat("url is {0}", url);
            using HttpClient apiClient = BuildHttpClient(_username, _password, _tenantId);
            Log.DebugFormat("about to invoke method using url {0}", url);
            Log.DebugFormat("method is GET");
            string requestURL = string.Format("{0}{1}", apiClient.BaseAddress, url);
            Log.DebugFormat("requestURL is {0}", requestURL);
            HttpRequestMessage request = CreateRequestMessageForSwaggerDocument(requestURL, token);
            Log.DebugFormat("about to send request for Swagger document");
            HttpResponseMessage response = apiClient.SendAsync(request).Result;
            Log.DebugFormat("response StatusCode is {0}", response.StatusCode.ToString());
            string content = response.Content.ReadAsStringAsync().Result;
            Log.DebugFormat("content is {0}", content);
            return content;
        }

        public override string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null)
        {
            Log.DebugFormat("starting GetAccessToken()");
            string baseUrl = _baseUrl.AbsoluteUri;
            string truncatedBaseUrl = baseUrl[0..^4];
            string tokenURL = string.Format("{0}auth/oauth/token", truncatedBaseUrl);
            Log.DebugFormat("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            Log.DebugFormat("userName is {0}", userName);
            Log.DebugFormat("password is {0}", password);
            Log.DebugFormat("tenantId is {0}", tenantId);
            request.Content = new FormUrlEncodedContent(
                new[]
                {
                    new KeyValuePair<string, string>("grant_type", "password"),
                    new KeyValuePair<string, string>("username", userName),
                    new KeyValuePair<string, string>("password", password),
                    new KeyValuePair<string, string>("tenantId", tenantId)
                });

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
            StringReader stringReader = null;
            try
            {
                //stringReader = new StringReader(content);
                //using JsonTextReader jsonReader = new JsonTextReader(stringReader);
                //stringReader = null;
                //JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                //string token = json["access_token"].Value<string>();
                //Log.DebugFormat("token is {0}", token);
                return null;
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
