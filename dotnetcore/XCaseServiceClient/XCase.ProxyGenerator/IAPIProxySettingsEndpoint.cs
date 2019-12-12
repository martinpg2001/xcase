namespace XCase.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public interface IAPIProxySettingsEndpoint
    {
        bool GetAppendAsyncToMethodName();

        string GetBasePath();

        string GetBaseProxyClass();

        string GetHost();

        string GetId();

        string GetNamespace();

        bool GetParseOperationIdForProxyName();

        string GetProxyConstructorSuffix();

        string GetUrl();
    }
}
