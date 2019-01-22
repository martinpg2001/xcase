namespace XCaseBase
{
    using System.Collections.Generic;
    using log4net;

    /// <summary>
    /// This class represents a FileSystem datasource.
    /// </summary>
    public class FileSystem : Datasource
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
        private string retriesString = "-1";

        /// <summary>
        /// Initializes a new instance of the FileSystem class.
        /// </summary>
        /// <param name="argv">The argv parameter.</param>
        public FileSystem(Dictionary<string, string> argv)
        {
            log.Debug("starting dictionary constructor");
            this.Host = argv.ContainsKey("host") ? (string)argv["host"] : string.Empty;
            log.Debug("Host is " + this.Host);
            this.Domain = argv.ContainsKey("domain") ? (string)argv["domain"] : string.Empty;
            log.Debug("Domain is " + this.Domain);
            this.Username = argv.ContainsKey("username") ? (string)argv["username"] : string.Empty;
            log.Debug("Username is " + this.Username);
            this.Password = argv.ContainsKey("password") ? (string)argv["password"] : string.Empty;
            log.Debug("Password is " + this.Password);
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
        public int Retries { get; set; }

        /// <summary>
        /// This method.
        /// </summary>
        /// <returns>The datasource string.</returns>
        public string ToDatasourceString()
        {
            return "FileSystem:" + this.Host + ":" + this.Domain + ":" + this.Username + ":" + this.Password;
        }
    }
}
