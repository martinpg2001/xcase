namespace XCase.ProxyGenerator.REST
{
    using System.Linq;

    public class Enum
    {
        public string Name { get; set; }
        public string[] Values { get; set; }

        public static string FixEnumValue(string value)
        {
            string[] specialValues = { "false", "is", "new", "true" };
            if (string.IsNullOrEmpty(value))
            {
                return value;
            }

            if (specialValues.Contains<string>(value))
            {
                return value.ToUpper();
            }

            if (value.Contains("-"))
            {
                return value.Replace("-", "_Dash_");
            }

            return value;
        }

        public override bool Equals(object obj)
        {
            return ((Enum)obj).Name == Name;
        }

        public override int GetHashCode()
        {
            return Name.GetHashCode();
        }
    }
}