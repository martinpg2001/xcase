namespace XCaseBase
{
    using System.Collections.Generic;
    using System.Xml;
    using log4net; 

    /// <summary>
    /// This class represents a Web service datasource.
    /// </summary>
    public class WebService : Datasource
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// Initializes a new instance of the WebService class using a Dictionary of parameters.
        /// </summary>
        /// <param name="argv">The argv Dictionary of parameters.</param>
        public WebService(Dictionary<string, string> argv)
        {
            this.Host = argv.ContainsKey("host") ? (string)argv["host"] : string.Empty;
            log.Debug("Host is " + this.Host);
            this.Domain = argv.ContainsKey("domain") ? (string)argv["domain"] : string.Empty;
            log.Debug("Domain is " + this.Domain);
            this.Username = argv.ContainsKey("username") ? (string)argv["username"] : string.Empty;
            log.Debug("Username is " + this.Username);
            this.Password = argv.ContainsKey("password") ? (string)argv["password"] : string.Empty;
            log.Debug("Password is " + this.Password);
            this.Endpoint = argv.ContainsKey("endpoint") ? (string)argv["endpoint"] : string.Empty;
            log.Debug("Endpoint is " + this.Endpoint);
        }

        /// <summary>
        /// Initializes a new instance of the WebService class using an endpoint string.
        /// </summary>
        /// <param name="endpoint">The endpoint parameter.</param>
        public WebService(string endpoint)
        {
            this.Endpoint = endpoint;
        }

        /// <summary>
        /// Initializes a new instance of the WebService class using an XmlNode containing credentials information.
        /// </summary>
        /// <param name="webServiceNode">The webServiceNode parameter.</param>
        public WebService(XmlNode webServiceNode)
        {
            log.Debug("starting XmlNode constructor");
            this.Domain = Helper.GetStringNodeValue(webServiceNode, "@domain", string.Empty, true);
            log.Debug("Domain is " + this.Domain);
            this.Endpoint = Helper.GetStringNodeValue(webServiceNode, "@endpoint", string.Empty, true);
            log.Debug("Endpoint is " + this.Endpoint);
            this.Password = Helper.GetStringNodeValue(webServiceNode, "@password", string.Empty, true);
            log.Debug("Password is " + this.Password);
            this.Username = Helper.GetStringNodeValue(webServiceNode, "@username", string.Empty, true);
            log.Debug("Username is " + this.Username);
        }

        /// <summary>
        /// Gets or sets the value of the Web service endpoint.
        /// </summary>
        public string Endpoint { get; set; }

        /// <summary>
        /// This method returns a string representation of the datasource.
        /// </summary>
        /// <returns>The datasource string.</returns>
        public string ToDatasourceString()
        {
            return "WebService:" + this.Endpoint + ":" + this.Domain + ":" + this.Username + ":" + this.Password;
        }
    }
}
