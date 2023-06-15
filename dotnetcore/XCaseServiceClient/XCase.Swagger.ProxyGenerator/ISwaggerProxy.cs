namespace XCase.REST.ProxyGenerator
{
    using System.Net.Http;

    public interface ISwaggerProxy : IRESTProxy
    {
        HttpRequestMessage CreateRequestMessageForSwaggerDocument(string requestURL, string token);
        string GetSwaggerDocument();
    }
}
