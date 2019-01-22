namespace XCase.REST.ProxyGenerator.Generator
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using XCase.ProxyGenerator;

    public abstract class RESTProxyGenerator : IProxyGenerator
    {
        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint[] endpoints);
        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document, string username, string password, string tenant);
        public abstract IServiceDefinition GenerateSourceString(IAPIProxySettingsEndpoint endpoint, string document);
        public abstract IServiceDefinition GenerateSourceString(string document);

        public static int TextPadding { get; set; }

        public static void WriteLine(StringBuilder stringBuilder)
        {
            stringBuilder.AppendLine(string.Empty);
        }

        public static void WriteLine(StringBuilder stringBuilder, string text)
        {
            if (text == "}" && TextPadding != 0)
            {
                TextPadding--;
            }

            string textPadding = new string(' ', TextPadding * 4);
            stringBuilder.AppendLine(string.Format("{1}{0}", text, textPadding));
            if (text.EndsWith("{"))
            {
                TextPadding++;
            }
        }
    }
}
