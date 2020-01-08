namespace XCase.ProxyGenerator.REST
{
    using System.Linq;

    public class Enum
    {
        public string Name { get; set; }
        public string[] Values { get; set; }

        public static string FixEnumValue(string value)
        {
            string[] specialValues = { "false", "new", "true" };
            if (string.IsNullOrEmpty(value))
            {
                return value;
            }

            if (specialValues.Contains<string>(value))
            {
                return value.ToUpper();
            }

            return value;
        }
    }
}