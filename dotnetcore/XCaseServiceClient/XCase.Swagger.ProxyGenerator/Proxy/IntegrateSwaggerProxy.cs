﻿namespace XCase.REST.ProxyGenerator.Proxy
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

    public class IntegrateSwaggerProxy : SwaggerProxy, ISwaggerProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public IntegrateSwaggerProxy(Uri baseUrl) : base(baseUrl)
        { }

        public IntegrateSwaggerProxy(Uri baseUrl, string username, string password, string tenant)
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
            request.Headers.Authorization = new AuthenticationHeaderValue("IntegrateAuthenticationToken", token);
            request.Headers.Add("IntegrateAuthenticationToken", token);
            return request;
        }

        public override string GetSwaggerDocument()
        {
            Log.LogDebug("starting GetSwaggerDocument()");
            string url = "api/v1/swagger";
            //string url = "api/api/swagger/docs/v1";
            Log.LogDebug("url is {0}", url);
            using (HttpClient apiClient = BuildHttpClient(_username, _password, _tenantId))
            {
                Log.LogDebug("about to invoke method using url {0}", url);
                System.Net.ServicePointManager.ServerCertificateValidationCallback = ((sender, certificate, chain, sslPolicyErrors) => true);
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
            Log.LogDebug("starting GetAccessToken()");
            /* In this case, we are assuming that the username is the access token, so just return this */
            return userName;
        }
    }
}
