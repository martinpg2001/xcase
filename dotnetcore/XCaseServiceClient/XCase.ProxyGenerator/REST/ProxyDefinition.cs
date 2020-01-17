namespace XCase.ProxyGenerator.REST
{
    using System;
    using System.Collections.Generic;

    public class ProxyDefinition : IProxyDefinition
    {
        public ProxyDefinition()
        {
            this.ClassDefinitions = new List<ClassDefinition>();
            this.Operations = new List<Operation>();
        }

        public string BasePath { get; set; }
        public string BaseURL { get; set; }
        public string Description { get; set; }
        public string Host { get; set; }
        public string[] Schemes { get; set; }
        public IDictionary<string, RESTSecurityScheme> SecuritySchemes { get; set; }
        public string Swagger { get; set; }
        public string Title { get; set; }
        public string Version { get; set; }
        public List<ClassDefinition> ClassDefinitions { get; set; }
        public List<Operation> Operations { get; set; }
    }
}