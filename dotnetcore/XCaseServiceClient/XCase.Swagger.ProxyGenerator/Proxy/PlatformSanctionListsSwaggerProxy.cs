namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformSanctionListsSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformSanctionListsSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformSanctionListsSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("sanction-lists/v2/api-docs");
        }
    }
}
