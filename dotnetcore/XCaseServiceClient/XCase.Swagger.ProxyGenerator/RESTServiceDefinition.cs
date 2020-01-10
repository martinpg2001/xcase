namespace XCase.REST.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using XCase.ProxyGenerator;

    public class RESTServiceDefinition : IServiceDefinition
    {
        public string EndPoint { get; set; }
        public string[] SourceStrings { get; set; }
        public List<string> ProxyClasses = new List<string>();

        public string GetEndPoint()
        {
            return EndPoint;
        }

        public string[] GetSourceStrings()
        {
            return SourceStrings;
        }

        public List<string> GetProxyClasses()
        {
            return ProxyClasses;
        }

        public void SetEndPoint(string endPoint)
        {
            EndPoint = endPoint;
        }

        public void SetProxyClasses(List<string> proxyClasses)
        {
            ProxyClasses = proxyClasses;
        }

        public void SetSourceStrings(string[] sourceStrings)
        {
            SourceStrings = sourceStrings;
        }
    }
}
