namespace XCase.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public interface IProxyGenerator
    {
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        IServiceDefinition GenerateSourceString(string document);
    }
}
