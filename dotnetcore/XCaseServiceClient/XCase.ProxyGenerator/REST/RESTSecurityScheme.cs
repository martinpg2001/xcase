namespace XCase.ProxyGenerator.REST
{
    using System;
    using System.Collections.Generic;
    using System.Text;

    public class RESTSecurityScheme
    {
        public string Type { get; set; }
        public string Scheme { get; set; }
        public string ApiKeyLocation { get; set; }
        public string Name { get; set; }
    }
}
