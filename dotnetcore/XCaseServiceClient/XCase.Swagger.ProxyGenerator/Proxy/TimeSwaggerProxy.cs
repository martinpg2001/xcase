namespace XCase.REST.ProxyGenerator.Proxy
{
    using System;

    public class TimeSwaggerProxy : PlatformSwaggerProxy, ISwaggerProxy
    {
        public TimeSwaggerProxy(Uri baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public TimeSwaggerProxy(Uri baseUrl, string username, string password, string domain)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _domain = domain;
        }

        public override string GetSwaggerDocument()
        {
            Log.Debug("starting GetSwaggerDocument()");
            return GetSwaggerDocument("time/Api/time/api/swagger/docs/v1");
        }
    }
}
