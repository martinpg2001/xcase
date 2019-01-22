namespace XCase.REST.ProxyGenerator.RAML
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using XCase.REST.ProxyGenerator.RAML.Tokens;

    internal static class Constants
    {
        public static readonly TagDirective[] DefaultTagDirectives =
        {
        new TagDirective("!", "!"),
        new TagDirective("!!", "tag:yaml.org,2002:")
        };

        public const int MajorVersion = 1;
        public const int MinorVersion = 1;

        public const char HandleCharacter = '!';
        public const string DefaultHandle = "!";
    }
}
