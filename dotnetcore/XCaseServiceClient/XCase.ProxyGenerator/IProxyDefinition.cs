namespace XCase.ProxyGenerator
{
    using System.Collections.Generic;
    using XCase.ProxyGenerator.REST;

    public interface IProxyDefinition
    {
        string BasePath
        {
            get;
            set;
        }

        List<ClassDefinition> ClassDefinitions
        {
            get;
            set;
        }

        string Host
        {
            get;
            set;
        }

        List<Operation> Operations
        {
            get;
            set;
        }

        string[] Schemes
        {
            get;
            set;
        }

        string Title
        {
            get;
            set;
        }
    }
}
