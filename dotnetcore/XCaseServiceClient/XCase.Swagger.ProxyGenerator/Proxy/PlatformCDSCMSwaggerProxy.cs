namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformCDSCMSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformCDSCMSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformCDSCMSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("admin/cdscm/v2/api-docs?group=tenant-dependent");
        }
    }
}
