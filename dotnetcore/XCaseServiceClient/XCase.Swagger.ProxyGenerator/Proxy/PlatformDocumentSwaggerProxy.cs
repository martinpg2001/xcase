namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class PlatformDocumentSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public PlatformDocumentSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public PlatformDocumentSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("admin/document/v2/api-docs");
        }
    }
}
