namespace XCase.ProxyGenerator.REST
{
    using Microsoft.Extensions.Configuration;
    using Serilog;
    using System;
    using System.IO;

    public abstract class RESTParser : IParser
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        public static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder()
    .SetBasePath(Directory.GetCurrentDirectory())
    .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
    .Build()).CreateLogger();

        #endregion

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

            if (typeName.StartsWith("#/components/schemas/"))
            {
                Log.Debug("typeName starts with #/components/schemas/");
                return typeName.Substring("#/components/schemas/".Length);
            }

            if (typeName.Equals("IterableOfstring"))
            {
                /* This block used to return List<string> but am not sure what is best way to handle this type name */
                return "List<string>";
            }

            if (typeName.Equals("integer"))
            {
                return "int";
            }

            if (typeName.Equals("boolean"))
            {
                return "bool";
            }

            string fixedTypeName = typeName
                .Replace(":", "_Colon_")
                .Replace(",", "_Comma_")
                .Replace("-", "_Dash_")
                .Replace("#", "_Hash_")
                .Replace("$", "_Dollar_")
                .Replace(".", "_Dot_")
                .Replace("(", "_Left_")
                .Replace("{", "_LeftBrace_")
                .Replace("%", "_Percent_")
                .Replace(")", "_Right_")
                .Replace("}", "_RightBrace_")
                .Replace("/", "_Slash_")
                .Replace(" ", "_Space_")
                .Replace("&", "_And_")
                .Replace("*", "_Star_");
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
                .Replace("{", "_LeftBrace_")
                .Replace("%", "_Percent_")
                .Replace(")", "_Right_")
                .Replace("}", "_RightBrace_")
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
