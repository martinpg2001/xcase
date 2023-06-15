namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformTMSSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformTMSSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformTMSSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("tms/v2/api-docs?group=tenant-dependent");
        }
    }
}
