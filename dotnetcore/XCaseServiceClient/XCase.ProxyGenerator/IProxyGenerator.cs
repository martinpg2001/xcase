namespace XCase.ProxyGenerator
{
    public interface IProxyGenerator
    {
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        IServiceDefinition GenerateSourceString(string document);
    }
}
