namespace XCase.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public interface IServiceDefinition
    {
        string GetEndPoint();

        string[] GetSourceStrings();

        List<string> GetProxyClasses();
    }
}
