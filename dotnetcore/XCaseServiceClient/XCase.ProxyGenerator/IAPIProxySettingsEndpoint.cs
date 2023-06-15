namespace XCase.ProxyGenerator
{
    public interface IAPIProxySettingsEndpoint
    {
        public string GetAccept();

        bool GetAppendAsyncToMethodName();

        string GetBasePath();

        string GetBaseProxyClass();

        string GetHost();

        string GetId();

        string GetNamespace();

        bool GetParseOperationIdForProxyName();

        string GetProxyConstructorSuffix();

        public string GetTokenName();

        string GetUrl();
    }
}
