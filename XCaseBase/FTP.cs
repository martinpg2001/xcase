namespace XCaseBase
{
    using System.Collections.Generic;
    using log4net;

    /// <summary>
    /// This class represents an FTP datasource.
    /// </summary>
    public class FTP : Datasource
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
        private string asciiString = "true";

        /// <summary>
        /// This field.
        /// </summary>
        private string binaryString = "true";

        /// <summary>
        /// This field.
        /// </summary>
        private string portString = "21";

        /// <summary>
        /// This field.
        /// </summary>
        private string sslString = "true";

        /// <summary>
        /// This field.
        /// </summary>
        private string retriesString = "-1";

        /// <summary>
        /// Initializes a new instance of the FTP class.
        /// </summary>
        /// <param name="argv">The argv parameter.</param>
        public FTP(Dictionary<string, string> argv)
        {
            log.Debug("starting dictionary constructor");
            this.Host = argv.ContainsKey("host") ? (string)argv["host"] : string.Empty;
            log.Debug("Host is " + this.Host);
            this.portString = argv.ContainsKey("port") ? (string)argv["port"] : string.Empty;
            log.Debug("portString is " + this.portString);
            int portInt = 21;
            if (this.portString != string.Empty)
            {
                int.TryParse(this.portString, out portInt);
            }

            this.Port = portInt;
            log.Debug("Port is " + this.Port);
            this.Domain = argv.ContainsKey("domain") ? (string)argv["domain"] : string.Empty;
            log.Debug("Domain is " + this.Domain);
            this.Username = argv.ContainsKey("username") ? (string)argv["username"] : string.Empty;
            log.Debug("Username is " + this.Username);
            this.Password = argv.ContainsKey("password") ? (string)argv["password"] : string.Empty;
            log.Debug("Password is " + this.Password);
            this.asciiString = argv.ContainsKey("ascii") ? (string)argv["ascii"] : string.Empty;
            bool asciiBool = true;
            bool.TryParse(this.asciiString, out asciiBool);
            this.binaryString = argv.ContainsKey("binary") ? (string)argv["binary"] : string.Empty;
            bool binaryBool = false;
            bool.TryParse(this.binaryString, out binaryBool);
            this.Ascii = !binaryBool;
            log.Debug("Ascii is " + this.Ascii);
            this.sslString = argv.ContainsKey("ssl") ? (string)argv["ssl"] : string.Empty;
            bool sslBool = true;
            bool.TryParse(this.sslString, out sslBool);
            this.Ssl = sslBool;
            log.Debug("Ssl is " + this.Ssl);
            this.retriesString = argv.ContainsKey("retries") ? (string)argv["retries"] : string.Empty;
            int retriesInt = -1;
            if (this.retriesString != string.Empty)
            {
                int.TryParse(this.retriesString, out retriesInt);
            }

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
            return "FTP:" + this.Host + ":" + this.Port + ":" + this.Path + ":" + this.Username + ":" + this.Password;
        }
    }
}
