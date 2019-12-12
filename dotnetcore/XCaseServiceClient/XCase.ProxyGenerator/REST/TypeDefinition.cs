namespace XCase.ProxyGenerator.REST
{
    using System;
    using System.Collections.Generic;
    using System.Linq;

    public class TypeDefinition
    {
        public TypeDefinition(string typeName, string name, string[] enumValues = null, bool isNullableType = false)
        {
            this.TypeName = typeName;
            this.Name = name;
            this.EnumValues = enumValues;
            this.IsNullableType = isNullableType;
        }

        public string Name { get; set; }
        public string TypeName { get; set; }
        public string[] EnumValues { get; set; }
        public bool IsNullableType { get; set; }

        public string GetCleanTypeName()
        {
            /* Replace hyphenated type names with camelCase names */
            while (Name.Contains("-"))
            {
                int index = Name.IndexOf("-", StringComparison.InvariantCulture);
                var letter = Name[index + 1].ToString().ToUpper();
                Name = Name.Remove(index, 2);
                Name = Name.Insert(index, letter);
            }

            string fixedName = RESTParser.FixTypeName(Name);
            return string.Format("@{0}", fixedName);
        }

        public string GetCleanJavaTypeName()
        {
            /* Replace hyphenated type names with camelCase names */
            while (Name.Contains("-"))
            {
                int index = Name.IndexOf("-", StringComparison.InvariantCulture);
                var letter = Name[index + 1].ToString().ToUpper();
                Name = Name.Remove(index, 2);
                Name = Name.Insert(index, letter);
            }

            if (CheckJavaKeyword(Name))
            {
                Name = string.Format("_{0}", Name);
            }

            string fixedName = RESTParser.FixTypeName(Name);
            return fixedName;
        }

        public bool CheckJavaKeyword(string name)
        {
            string[] keywords = new string[] { "abstract", "continue", "for", "new", "switch", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while" };
            if (name == null)
            {
                return false;
            }

            if (keywords.ToList<string>().Contains(name))
            {
                return true;
            }

            return false;
        }

        public string GetCleanJavaTypename()
        {
            if (TypeName == "bool")
            {
                TypeName = "boolean";
            }

            if (TypeName == "DateTime")
            {
                TypeName = "Date";
            }

            if (TypeName == "object")
            {
                TypeName = "Object";
            }

            if (TypeName == "string")
            {
                TypeName = "String";
            }

            if (TypeName == "string[]")
            {
                TypeName = "String[]";
            }

            return TypeName.Replace("$", string.Empty).Replace(" ", string.Empty).Replace(".", "__");
        }
    }
}