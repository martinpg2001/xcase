namespace XCaseBase
{
    using log4net;

    public class ProcessEnvironment
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// Gets or sets the messenger type.
        /// </summary>
        /// <value>
        /// The messenger type.
        /// </value>
        public string MessengerTypeString { get; set; }

        /// <summary>
        /// Gets or sets the configuration information for the messenger.
        /// </summary>
        /// <value>
        /// The configuration information for the messenger.
        /// </value>
        public string MessengerTypeConfigurationString { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether the operation runs silently (that is, without human intervention).
        /// </summary>
        /// <value>
        /// The property to specify that the operation runs silently (that is, without human intervention).
        /// </value>
        public bool Silent { get; set; }

        /// <summary>
        /// This method processes the environment element to set environment values.
        /// </summary>
        /// <param name="environmentNode">The environment element.</param>
        public void Process(System.Xml.XmlNode environmentNode)
        {
            if (environmentNode.SelectSingleNode("@messengertype") != null)
            {
                string messengerType = environmentNode.SelectSingleNode("@messengertype").Value;
                log.Debug("messengerType is " + messengerType);
                this.MessengerTypeString = messengerType;
            }

            if (environmentNode.SelectSingleNode("@messengertypeconfiguration") != null)
            {
                string messengerTypeConfiguration = environmentNode.SelectSingleNode("@messengertypeconfiguration").Value;
                log.Debug("messengerTypeConfiguration is " + messengerTypeConfiguration);
                this.MessengerTypeConfigurationString = messengerTypeConfiguration;
            }

            bool silentBool = true;
            if (environmentNode.SelectSingleNode("@silent") != null)
            {
                string silentString = environmentNode.SelectSingleNode("@silent").Value;
                log.Debug("silentString is " + silentString);
                bool.TryParse(silentString, out silentBool);
            }

            this.Silent = silentBool;
        }
    }
}
