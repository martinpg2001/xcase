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
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public interface IRESTProxy
    {
        HttpClient BuildHttpClient();

        HttpClient BuildHttpClient(string username, string password, string tenant);

        string GetAccessToken(HttpClient client, string username, string password, string tenantId = null);
    }
}
