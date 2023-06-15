using XCase.ProxyGenerator;

namespace XCase.REST.ProxyGenerator.Generator
{
    public abstract class RAMLProxyGenerator : CSharpProxyGenerator
    {
        #region Logger Setup

        #endregion

        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        public override abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        public override abstract IServiceDefinition GenerateSourceString(string document);
    }
}
