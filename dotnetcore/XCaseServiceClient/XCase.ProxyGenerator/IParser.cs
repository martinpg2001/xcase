using System;
namespace XCase.ProxyGenerator
{
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using XCase.ProxyGenerator.REST;

    public interface IParser
    { 
        IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint);
    }
}
