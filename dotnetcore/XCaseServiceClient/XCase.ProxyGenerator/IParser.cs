namespace XCase.ProxyGenerator
{
    public interface IParser
    { 
        IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint);
    }
}
