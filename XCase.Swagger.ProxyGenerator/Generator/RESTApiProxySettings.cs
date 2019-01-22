namespace XCase.REST.ProxyGenerator.Generator
{
    public class RESTApiProxySettings
    {
        public string BaseUrl { get; set; }
        public string ProxyOutputFile { get; set; }
        public string WebApiAssembly { get; set; }
        public string WebApiConfig { get; set; }
        public RESTApiProxySettingsEndPoint[] EndPoints { get; set; }
    }
}