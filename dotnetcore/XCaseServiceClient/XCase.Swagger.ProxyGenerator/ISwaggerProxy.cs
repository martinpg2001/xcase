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

    public interface ISwaggerProxy : IRESTProxy
    {
        HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token);
        string GetSwaggerDocument();
    }
}
