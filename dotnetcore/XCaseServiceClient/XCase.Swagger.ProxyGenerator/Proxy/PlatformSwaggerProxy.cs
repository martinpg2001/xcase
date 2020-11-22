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
    using Serilog;
    using Serilog.Events;

    public abstract class PlatformSwaggerProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log instance.
        /// </summary>
        //static readonly Serilog.ILogger Log = new LoggerConfiguration().MinimumLevel.Debug().WriteTo.File("XCaseServiceClient.log").WriteTo.Console(restrictedToMinimumLevel: LogEventLevel.Information).CreateLogger();

        #endregion

        public override HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token)
        {
            HttpRequestMessage request = new HttpRequestMessage() { RequestUri = new Uri(requestURL), Method = HttpMethod.Get };
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", token);
            /* Add cookie for Time swagger proxy */
            request.Headers.Add("Cookie", "Authorization=" + token + "; Refresh-Token=");
            return request;
        }

        public override string GetAccessToken(HttpClient client, string clientId = "admin", string clientSecret = "", string tenantId = null)
        {
            Log.Debug("starting GetAccessToken()");
            string baseUrl = _baseUrl.GetLeftPart(UriPartial.Authority);
            Log.Debug("baseUrl is {0}", baseUrl);
            //string replacedBaseUrl = baseUrl.Replace("isuitesaml", "idm") + "/";
            string replacedBaseUrl = ReplaceHostNameInURL(baseUrl, "idmus") + "/";
            Log.Debug("replacedBaseUrl is {0}", replacedBaseUrl);
            //string truncatedBaseUrl = baseUrl.Substring(0, baseUrl.Length - 4);
            string tokenURL = string.Format("{0}auth/realms/{1}/protocol/openid-connect/token", replacedBaseUrl, tenantId);
            Log.Debug("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            Log.Debug("clientId is {0}", clientId);
            Log.Debug("clientSecret is {0}", clientSecret);
            Log.Debug("tenantId is {0}", tenantId);
            request.Content = new FormUrlEncodedContent(
                new[]
                {
                    new KeyValuePair<string, string>("grant_type", "client_credentials"),
                    new KeyValuePair<string, string>("client_id", clientId),
                    new KeyValuePair<string, string>("client_secret", clientSecret)
                });

            HttpResponseMessage response = client.SendAsync(request).Result;
            Log.Debug("got response status code {0}", response.StatusCode.ToString());
            if (!response.IsSuccessStatusCode)
            {
                Log.Debug("response status code is not IsSuccessStatusCode");
                return null;
            }

            Log.Debug("response status code is IsSuccessStatusCode");
            string content = response.Content.ReadAsStringAsync().Result;
            Log.Debug("content is {0}", content);
            StringReader stringReader = null;
            try
            {
                stringReader = new StringReader(content);
                using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                {
                    stringReader = null;
                    JObject json = (JObject)JsonSerializer.CreateDefault().Deserialize(jsonReader);
                    string token = json["access_token"].Value<string>();
                    Log.Debug("token is {0}", token);
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

        public string GetSwaggerDocument(string url)
        {
            Log.Debug("starting GetSwaggerDocument(string url)");
            Log.Debug("url is {0}", url);
            using (HttpClient apiClient = BuildHttpClient(_username, _password, _domain))
            {
                Log.Debug("about to invoke method using url {0}", url);
                Log.Debug("method is GET");
                string requestURL = string.Format("{0}{1}", apiClient.BaseAddress, url);
                Log.Debug("requestURL is {0}", requestURL);
                HttpRequestMessage request = CreateRequestMessageForSwaggerDocument(requestURL, token);
                Log.Debug("about to send request for Swagger document");
                HttpResponseMessage response = apiClient.SendAsync(request).Result;
                Log.Debug("response StatusCode is {0}", response.StatusCode.ToString());
                string content = response.Content.ReadAsStringAsync().Result;
                Log.Debug("content is {0}", content);
                return content;
            }
        }

        private string ReplaceHostNameInURL(string baseUrl, string replaceHostname)
        {
            if (string.IsNullOrEmpty(baseUrl))
            {
                return baseUrl;
            }

            Log.Debug("baseUrl is {0}", baseUrl);
            int start = baseUrl.IndexOf("//");
            int end = baseUrl.IndexOf(".");
            string hostname = baseUrl.Substring(start + 2, end - start - 2);
            Log.Debug("hostname is {0}", hostname);
            string replacedBaseUrl = ReplaceFirst(baseUrl, hostname, replaceHostname);
            Log.Debug("replacedBaseUrl is {0}", replacedBaseUrl);
            //return baseUrl.Replace(hostname, replaceHostname);
            return replacedBaseUrl;
        }

        private string ReplaceFirst(string text, string toBeReplacedString, string toReplaceString)
        {
            if (text == null)
            {
                return null;
            }

            int pos = text.IndexOf(toBeReplacedString);
            if (pos < 0)
            {
                return text;
            }

            return text.Substring(0, pos) + toReplaceString + text.Substring(pos + toBeReplacedString.Length);
        }
    }
}
