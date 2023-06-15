namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformRefDataSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformRefDataSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformRefDataSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("admin/refdata/v2/api-docs?group=public-tenant-dependent");
        }
    }
}
