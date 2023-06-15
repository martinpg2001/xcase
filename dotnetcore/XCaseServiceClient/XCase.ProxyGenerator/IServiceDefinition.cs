namespace XCase.ProxyGenerator
{
    using System.Collections.Generic;

    public interface IServiceDefinition
    {
        string GetEndPoint();

        string[] GetSourceStrings();

        List<string> GetProxyClasses();
    }
}
