namespace XCase.REST.ProxyGenerator.Generator
{
    using Microsoft.Extensions.Configuration;
    using System;
    using System.Collections.Generic;
    using System.Configuration;
    using System.Net;
    using XCase.ProxyGenerator;

    public class RESTApiProxySettingsEndPoint : IAPIProxySettingsEndpoint
    {
        public static IConfigurationRoot iConfigurationRoot = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build();

        public string Id { get; set; }
        public string Url { get; set; }
        public string Namespace { get; set; }
        public string Suffix { get; set; }
        public string BaseProxyClass { get; set; }
        public string ProxyConstructorSuffix { get; set; }
        public bool ParseOperationIdForProxyName { get; set; }
        public bool AppendAsyncToMethodName { get; set; }

        public RESTApiProxySettingsEndPoint()
        {
            BaseProxyClass = ConfigurationManager.AppSettings["SwaggerProxy"];
            Id = "SwaggerProxy";
            Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;// "XCaseServiceClient";// ConfigurationManager.AppSettings["Namespace"];
            ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
            ParseOperationIdForProxyName = true;
            AppendAsyncToMethodName = true;
        }

        public RESTApiProxySettingsEndPoint(string language, string baseProxyClass)
        {
            BaseProxyClass = baseProxyClass;
            Id = "RESTProxy";
            Namespace = Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;// ConfigurationManager.AppSettings["Namespace"];
            switch (language)
            {
                case "CSharp":
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
                case "Java":
                    //Namespace = "com.xcase.harness.actions.integrate.objects";
                    ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
                default:
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
            }
        }

        public RESTApiProxySettingsEndPoint(string language)
        {
            BaseProxyClass = "OpenCloudSwaggerProxy";
            Id = "SwaggerProxy";
            Namespace = Namespace = iConfigurationRoot.GetSection("AppSettings").GetSection("Namespace").Value;// ConfigurationManager.AppSettings["Namespace"];
            switch (language)
            {
                case "CSharp":
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
                case "Java":
                    ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
                default:
                    ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                    ParseOperationIdForProxyName = true;
                    AppendAsyncToMethodName = false;
                    break;
            }
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

            Uri uri = new Uri(Url);
            return uri.Authority;
        }

        public string GetBaseBath()
        {
            if (Url == null)
            {
                return null;
            }

            Uri uri = new Uri(Url);
            return uri.AbsolutePath;
        }
    }
}