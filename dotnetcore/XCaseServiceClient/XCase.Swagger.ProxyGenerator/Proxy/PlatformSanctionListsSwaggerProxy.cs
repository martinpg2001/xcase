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
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public class PlatformSanctionListsSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformSanctionListsSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformSanctionListsSwaggerProxy(Uri baseUrl, string username, string password, string tenant)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _tenantId = tenant;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("sanction-lists/v2/api-docs");
        }
    }
}
