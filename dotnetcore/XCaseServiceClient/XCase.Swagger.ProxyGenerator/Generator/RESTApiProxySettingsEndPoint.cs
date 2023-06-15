namespace XCase.REST.ProxyGenerator.Generator
{
    using Microsoft.Extensions.Configuration;
    using System;
    using XCase.ProxyGenerator;

    public class RESTApiProxySettingsEndPoint : IAPIProxySettingsEndpoint
    {
        public static IConfigurationRoot iConfigurationRoot = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build();

        public string Accept { get; set; }
        public string Id { get; set; }
        public string Namespace { get; set; }
        public string Suffix { get; set; }
        public string TokenName { get; set; }
        public string Url { get; set; }
        public string BaseProxyClass { get; set; }
        public string ProxyConstructorSuffix { get; set; }
        public bool ParseOperationIdForProxyName { get; set; }
        public bool AppendAsyncToMethodName { get; set; }

        public RESTApiProxySettingsEndPoint()
        {
            Accept = "application/json";
            BaseProxyClass = iConfigurationRoot["SwaggerProxy"];
            Id = "SwaggerProxy";
            Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;// "XCaseServiceClient";// ConfigurationManager.AppSettings["Namespace"];
            ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
            ParseOperationIdForProxyName = true;
            AppendAsyncToMethodName = true;
            TokenName = "Bearer";
        }

        public RESTApiProxySettingsEndPoint(string language, string baseProxyClass)
        {
            BaseProxyClass = baseProxyClass;
            Id = "RESTProxy";
            Namespace = Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;// ConfigurationManager.AppSettings["Namespace"];
            switch (language)
            {
                case "CSharp":
                    Accept = "application/json";
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    TokenName = "Bearer";
                    break;
                case "Java":
                    Accept = "application/json";
                    ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    TokenName = "Bearer";
                    break;
                default:
                    Accept = "application/json";
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    TokenName = "Bearer";
                    break;
            }
        }

        public RESTApiProxySettingsEndPoint(string language)
        {
            BaseProxyClass = "OpenApiProxy";
            Id = "OpenApiProxy";
            Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;
            switch (language)
            {
                case "CSharp":
                    Accept = "application/json";
                    AppendAsyncToMethodName = false;
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    TokenName = "Bearer";
                    break;
                case "Java":
                    Accept = "application/json";
                    AppendAsyncToMethodName = false;
                    ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    TokenName = "Bearer";
                    break;
                default:
                    Accept = "application/json";
                    AppendAsyncToMethodName = false;
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    TokenName = "Bearer";
                    break;
            }
        }

        public string GetAccept()
        {
            return Accept;
        }

        public string GetBasePath()
        {
            /* TODO: work out what to do here. */
            return string.Empty;
        }

        public string GetId()
        {
            return Id;
        }

        public string GetNamespace()
        {
            return Namespace;
        }

        public string GetBaseProxyClass()
        {
            return BaseProxyClass;
        }

        public string GetProxyConstructorSuffix()
        {
            return ProxyConstructorSuffix;
        }

        public string GetTokenName()
        {
            return TokenName;
        }

        public string GetUrl()
        {
            return Url;
        }

        public bool GetAppendAsyncToMethodName()
        {
            return AppendAsyncToMethodName;
        }

        public bool GetParseOperationIdForProxyName()
        {
            return ParseOperationIdForProxyName;
        }

        public string GetHost()
        {
            if (Url == null)
            {
                return null;
            }

            Uri uri = new(Url);
            return uri.Authority;
        }

        public string GetBaseBath()
        {
            if (Url == null)
            {
                return null;
            }

            Uri uri = new(Url);
            return uri.AbsolutePath;
        }
    }
}