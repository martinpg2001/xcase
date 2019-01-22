namespace XCaseGeneric
{
    using System;
    using System.Configuration;
    using System.IO;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class updates connection string. If filelocation is set then the connection string of this file is updated, otherwise test tool entry points' one.
    /// </summary>
    public class ActionUpdateConnectionString : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method updates connection string of the config file.
        /// The XML document should be of this form:
        /// <operation id="Update connection string" class="XCaseGeneric.ActionUpdateConnectionString">
        ///   <connectionstringname>walls</connectionstringname>
        ///   <connectionstring>Data Source=o3Marina;Initial Catalog=api;uid=rp\marina.shestunina;Password=fc459292;integrated security=true;.</connectionstring>
        /// </operation>
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            string connectionStringName = Helper.GetStringNodeValue(document, "operation/connectionstringname", string.Empty, false);
            string connectionString = ConfigurationManager.AppSettings[connectionStringName];
            OperationResult operationResult = new OperationResult(true, "Successfully updated connection string");
            try
            {
                connectionString = "Data Source=Qawin1;Initial Catalog=walls414;uid=sa;Password=;";
                XmlNode connectionstringNode = document.SelectSingleNode("operation/connectionstring");
                if (connectionstringNode != null)
                {
                    connectionString = Helper.GetStringNodeValue(document, "operation/connectionstring", string.Empty, false);
                }

                Configuration config = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
                /* Change config of another file */
                if (document.SelectSingleNode("operation/filelocation") != null)
                {
                    string fileLocaton = Helper.GetStringNodeValue(document, "operation/filelocation", string.Empty, false);
                    ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                    /* Clear read-only flag if set */
                    File.SetAttributes(fileLocaton, FileAttributes.Normal);
                    fileMap.ExeConfigFilename = fileLocaton;
                    config = ConfigurationManager.OpenMappedExeConfiguration(fileMap, ConfigurationUserLevel.None);
                }

                config.ConnectionStrings.ConnectionStrings.Clear();
                config.ConnectionStrings.ConnectionStrings.Remove(connectionStringName);
                config.ConnectionStrings.ConnectionStrings.Add(
                    new ConnectionStringSettings(connectionStringName, connectionString));
                config.Save(ConfigurationSaveMode.Full);
                ConfigurationManager.RefreshSection("connectionStrings");
            }
            catch (Exception e)
            {
                log.Info("exception updating connection string" + e.ToString());
                return new OperationResult(false, e.Message);
            }

            return operationResult;
        }
    }
}
