namespace XCaseBase
{
    using System.Collections.Generic;
    using log4net;

    /// <summary>
    /// This class represents a WebDAV datasource.
    /// </summary>
    public class WebDAV : Datasource
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This field.
        /// </summary>
        private string portString = "80";

        /// <summary>
        /// This field.
        /// </summary>
        private string sslString = "true";

        /// <summary>
        /// This field.
        /// </summary>
        private string retriesString = "-1";

        /// <summary>
        /// Initializes a new instance of the WebDAV class.
        /// </summary>
        /// <param name="argv">The argv parameter.</param>
        public WebDAV(Dictionary<string, string> argv)
        {
            this.Host = argv.ContainsKey("host") ? (string)argv["host"] : string.Empty;
            log.Debug("Host is " + this.Host);
            this.portString = argv.ContainsKey("port") ? (string)argv["port"] : string.Empty;
            int portInt = 80;
            int.TryParse(this.portString, out portInt);
            this.Port = portInt;
            log.Debug("Port is " + this.Port);
            this.Path = argv.ContainsKey("path") ? (string)argv["path"] : string.Empty;
            log.Debug("Path is " + this.Path);
            this.Domain = argv.ContainsKey("domain") ? (string)argv["domain"] : string.Empty;
            log.Debug("Domain is " + this.Domain);
            this.Username = argv.ContainsKey("username") ? (string)argv["username"] : string.Empty;
            log.Debug("Username is " + this.Username);
            this.Password = argv.ContainsKey("password") ? (string)argv["password"] : string.Empty;
            log.Debug("Password is " + this.Password);
            this.sslString = argv.ContainsKey("ssl") ? (string)argv["ssl"] : string.Empty;
            bool sslBool = true;
            bool.TryParse(this.sslString, out sslBool);
            this.Ssl = sslBool;
            log.Debug("Ssl is " + this.Ssl);
            this.retriesString = argv.ContainsKey("retries") ? (string)argv["retries"] : string.Empty;
            int retriesInt = -1;
            int.TryParse(this.retriesString, out retriesInt);
            this.Retries = retriesInt;
            log.Debug("Retries is " + this.Retries);
        }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public bool Ssl { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public bool Passive { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public int BlockSize { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public bool Ascii { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public int Port { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public string Path { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether.
        /// </summary>
        public int Retries { get; set; }

        /// <summary>
        /// This method.
        /// </summary>
        /// <returns>The datasource string.</returns>
        public string ToDatasourceString()
        {
            return "WebDAV:" + this.Host + ":" + this.Port + ":" + this.Path + ":" + this.Username + ":" + this.Password;
        }
    }
}
