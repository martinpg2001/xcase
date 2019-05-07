namespace XCase.ProxyGenerator.REST
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public abstract class RESTParser : IParser
    {
        public abstract IProxyDefinition ParseDoc(string document, IAPIProxySettingsEndpoint endpoint);

        public static string FixGenericName(string name)
        {
            if (name.Contains("[") == false || name.Contains("]") == false)
            {
                return name;
            }

            if (name.StartsWith("Dictionary[") || name.StartsWith("IDictionary["))
            {
                return name.Replace("[", "<").Replace("]", ">");
            }

            int firstBracket = name.IndexOf("[", StringComparison.InvariantCulture) + 1;
            int secondBracket = name.IndexOf("]", StringComparison.InvariantCulture);
            string typeName = name.Substring(firstBracket, secondBracket - firstBracket);
            string genericName = name.Substring(0, firstBracket - 1);
            /* Temporary change: do not remove square braces until understand this better */
            return name;
        }

        public static string FixTypeName(string typeName)
        {
            //Log.DebugFormat("starting FixTypeName()");
            if (string.IsNullOrWhiteSpace(typeName))
            {
                return typeName;
            }

            if (typeName.Equals("IterableOfstring"))
            {
                return "List<string>";
            }

            string fixedTypeName = typeName
                .Replace(":", "_Colon_")
                .Replace(",", "_Comma_")
                .Replace("-", "_Dash_")
                .Replace("$", "_Dollar_")
                .Replace(".", "_Dot_")
                .Replace("(", "_Left_")
                .Replace("%", "_Percent_")
                .Replace(")", "_Right_")
                .Replace("/", "_Slash_")
                .Replace(" ", "_Space_");
            fixedTypeName = RESTParser.FixGenericName(fixedTypeName);
            if ((char.IsLetter(fixedTypeName[0]) == false) && (fixedTypeName[0] != '_'))
            {
                fixedTypeName = "_" + fixedTypeName;
            }

            return fixedTypeName;
        }

        public static string FixJavaTypeName(string input)
        {
            if (string.IsNullOrWhiteSpace(input))
            {
                return input;
            }

            string output = input.Replace(" ", "");
            output = RESTParser.FixGenericName(output);
            output = output.Replace(".", "__");
            if ((char.IsLetter(output[0]) == false) && (output[0] != '_'))
            {
                output = "_" + output;
            }

            if (output == "DateTime")
            {
                output = "Date";
            }

            if (output == "string[]")
            {
                output = "String[]";
            }

            if (output.EndsWith("[][]"))
            {
                output.Replace("[][]", "[]");
            }

            return output;
        }


        public static string FixMethodName(string methodName)
        {
            if (string.IsNullOrWhiteSpace(methodName))
            {
                return methodName;
            }

            string fixedMethodName = methodName
                .Replace(":", "_Colon_")
                .Replace("-", "_Dash_")
                .Replace("$", "_Dollar_")
                .Replace(".", "_Dot_")
                .Replace("(", "_Left_")
                .Replace("%", "_Percent_")
                .Replace(")", "_Right_")
                .Replace(" ", "_Space_");
            fixedMethodName = RESTParser.FixGenericName(fixedMethodName);
            if ((char.IsLetter(fixedMethodName[0]) == false) && (fixedMethodName[0] != '_'))
            {
                fixedMethodName = "_" + fixedMethodName;
            }

            return fixedMethodName;
        }
    }
}
