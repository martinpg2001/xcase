namespace XCase.REST.ProxyGenerator
{
    using System.Net.Http;

    public interface IRESTProxy
    {
        HttpClient BuildHttpClient();

        HttpClient BuildHttpClient(string username, string password, string tenant);

        string GetAccessToken(HttpClient client, string username, string password, string tenantId = null);
    }
}
