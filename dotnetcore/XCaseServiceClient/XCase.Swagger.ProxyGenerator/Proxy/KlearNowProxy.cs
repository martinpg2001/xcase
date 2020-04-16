﻿namespace XCase.REST.ProxyGenerator.Proxy
{
    using Microsoft.Extensions.Logging;
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
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public KlearNowProxy(Uri baseUrl) : base(baseUrl)
        {
            _username = "";
            _password = "";
            _tenantId = "";
        }

        public KlearNowProxy(Uri baseUrl, string username, string password, string domain) : base(baseUrl)
        {
            _username = username;
            _password = password;
            _tenantId = domain;
        }

        public override HttpClient BuildHttpClient(string username, string password, string domain)
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy, UseCookies = false };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.LogDebug("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.LogDebug("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, domain);
            this.token = token;
            Log.LogDebug("set token to {0}", token);
            return httpClient;
        }

        public override HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token)
        {
            throw new NotImplementedException();
        }

        public override string GetAccessToken(HttpClient client, string username = "admin", string password = "", string domain = null)
        {
            Log.LogDebug("starting GetAccessToken()");
            string tokenURL = _baseUrl.ToString() + "login";
            Log.LogDebug("tokenURL is {0}", tokenURL);
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, tokenURL);
            request.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse("application/json"));
            Log.LogDebug("domain is {0}", domain);
            string loginString = string.Format("{{\"email\" : \"{0}\", \"password\": \"{1}\" }}", username, password);
            Log.LogDebug("loginString is {0}", loginString);
            request.Content = new StringContent(loginString);
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
                    string token = json["kxToken"].Value<string>();
                    Log.LogDebug("token is {0}", token);
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
