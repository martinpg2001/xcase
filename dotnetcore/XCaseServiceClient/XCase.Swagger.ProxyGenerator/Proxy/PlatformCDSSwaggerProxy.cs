namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformCDSSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformCDSSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformCDSSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("admin/cds/v2/api-docs?group=public-tenant-dependent");
        }
    }
}
