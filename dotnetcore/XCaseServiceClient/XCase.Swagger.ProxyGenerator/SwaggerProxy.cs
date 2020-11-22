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
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;
    using Serilog;
    using Serilog.Events;

    public abstract class SwaggerProxy : RESTProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        //public static readonly Serilog.ILogger Log = new LoggerConfiguration().MinimumLevel.Debug().WriteTo.File("XCaseServiceClient.log").WriteTo.Console(restrictedToMinimumLevel: LogEventLevel.Information).CreateLogger();

        #endregion

        public SwaggerProxy()
        {

        }

        public SwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public SwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public override HttpClient BuildHttpClient()
        {
            Log.Debug("starting BuildHttpClient()");
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.Debug("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.Debug("set BaseAddress to {0}", _baseUrl);
            Log.Debug("_username is {0}", _username);
            Log.Debug("_password is {0}", _password);
            Log.Debug("_tenantId is {0}", _domain);
            string token = this.GetAccessToken(httpClient, _username, _password, _domain);
            Log.Debug("got access token {0}", token);
            this.token = token;
            Log.Debug("set token to {0}", token);
            return httpClient;
        }

        public override HttpClient BuildHttpClient(string username, string password, string domain)
        {
            Log.Debug("starting BuildHttpClient(string username, string password, string tenant)");
            HttpClientHandler httpClientHandler = new HttpClientHandler { Credentials = ClientCredentials, Proxy = Proxy, UseCookies = false };
            HttpClient httpClient = new HttpClient(httpClientHandler);
            Log.Debug("created httpClient");
            httpClient.BaseAddress = _baseUrl;
            Log.Debug("set BaseAddress to {0}", _baseUrl);
            string token = this.GetAccessToken(httpClient, username, password, domain);
            Log.Debug("got access token {0}", token);
            this.token = token;
            Log.Debug("set token to {0}", token);
            return httpClient;
        }


        public abstract string GetSwaggerDocument();

        public abstract HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token);
    }
}
