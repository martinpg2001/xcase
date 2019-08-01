namespace XCaseGeneric
{
    using System;
    using System.Management;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class restarts IIS servers.
    /// </summary>
    public class ActionRestartIIS : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This is the iisserver string.
        /// </summary>
        private string iisserver = "Auk";

        /// <summary>
        /// This is the domain string.
        /// </summary>
        private string domain = "xcase-auk";

        /// <summary>
        /// This is the username string.
        /// </summary>
        private string username = "Administrator";

        /// <summary>
        /// This is the password string.
        /// </summary>
        private string password = "password";

        /// <summary>
        /// This is the appPoolsStringArray string array.
        /// </summary>
        private string[] appPoolsStringArray;

        /// <summary>
        /// This method recycles the IIS app pools specified by the apppools
        /// element. The XML document should be of this form:
        /// <operation class="WebServiceClientWalls.ActionRestartIIS">
        ///   <iisconnectionstring>iisserver=krish.internal.xcase.net;username=Administrator;password=password;domain=XCASE</iisconnectionstring>
        ///   <apppools>APIServiceAppPool,ExtensionServiceAppPool,WallsAppPool</apppools>
        /// </operation>
        /// You must be able to access the IIS server manager to use this class.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            OperationResult operationResult = new OperationResult(true, "Successfully restarted IIS");
            try
            {
                string iisConnectionString = "iisserver=Auk;domain=xcase-auk;username=Administrator;password=password";
                XmlNode iisConnectionstringNode = document.SelectSingleNode("operation/iisconnectionstring");
                if (iisConnectionstringNode != null)
                {
                    log.Debug("iisConnectionstringNode is not null");
                    log.Debug("iisConnectionstringNode inner text is " + Helper.GetStringNodeValue(document, "operation/iisconnectionstring", string.Empty, false));
                    iisConnectionString = Helper.GetStringNodeValue(document, "operation/iisconnectionstring", string.Empty, false);
                    log.Debug("iisConnectionString is " + iisConnectionString);
                    this.ParseConfigurationString(iisConnectionString);
                }
                else
                {
                    log.Debug("iisConnectionstringNode is null");
                }

                string appPoolsString = string.Empty;
                XmlNode appPoolsNode = document.SelectSingleNode("operation/apppools");
                if (appPoolsNode != null)
                {
                    log.Debug("apppoolsNode is not null");
                    log.Debug("apppoolsNode inner text is " + Helper.GetStringNodeValue(document, "operation/apppools", string.Empty, false));
                    appPoolsString = Helper.GetStringNodeValue(document, "operation/apppools", string.Empty, false);
                    log.Debug("appPoolsString is " + appPoolsString);
                    this.appPoolsStringArray = this.ParseAppPoolsString(appPoolsString);
                }
                else
                {
                    log.Debug("appPoolsNode is null");
                }

                /* Now recycle the App pools */
                string userId = this.domain + "\\" + this.username;
                log.Debug("userId is " + userId);
                ConnectionOptions connectionOptions = new ConnectionOptions();
                if (!string.Equals(iisserver, "localhost", StringComparison.CurrentCultureIgnoreCase) && !Helper.IsServerLocal(this.iisserver))
                {
                    log.Debug("server is not local");
                    connectionOptions.Username = userId;
                    connectionOptions.Password = this.password;
                }
                else
                {
                    log.Debug("server is local");
                }

                connectionOptions.EnablePrivileges = true;
                connectionOptions.Authentication = AuthenticationLevel.PacketPrivacy;
                log.Debug("created connection options");
                string iisPath = Helper.GetStringNodeValue(appPoolsNode, "@iispath", "microsoftiisv2", false);
                log.Debug("iisPath is " + iisPath);
                string managementScopePath = @"\\" + this.iisserver + @"\root\" + iisPath;
                log.Debug("managementScopePath is " + managementScopePath);
                ManagementScope managementScope = new ManagementScope(managementScopePath, connectionOptions);
                managementScope.Connect();
                log.Debug("connected to " + this.iisserver);
                StringBuilder appPoolStringBuilder = new StringBuilder();
                foreach (string apppool in this.appPoolsStringArray)
                {
                    try
                    {
                        /* Catch exceptions so that any available appools do get recycled */
                        //string appPoolPath = "IISApplicationPool.Name='W3SVC/AppPools/" + apppool + "'";
                        //string appPoolPath = "ApplicationPool.Name='" + apppool + "'";
                        string appPoolPathPrefix = Helper.GetStringNodeValue(appPoolsNode, "@apppoolpathprefix", "IISApplicationPool.Name", false);
                        string appPoolPath = null;
                        if (appPoolPathPrefix == "IISApplicationPool.Name")
                        {
                            appPoolPath = appPoolPathPrefix + "='W3SVC/AppPools/" + apppool + "'";
                        }
                        else
                        {
                            appPoolPath = appPoolPathPrefix + "='" + apppool + "'";
                        }

                        log.Debug("appPoolPath is " + appPoolPath);
                        ManagementObject appPool = new ManagementObject(managementScope, new ManagementPath(appPoolPath), null);
                        log.Debug("created management object for " + apppool);
                        appPool.InvokeMethod("Recycle", null, null);
                        log.Debug("recycled " + apppool);
                        appPoolStringBuilder.Append(apppool + ",");
                    }
                    catch (Exception e)
                    {
                        log.Warn("exception recycling " + apppool + ": " + e.Message);
                    }
                }

                log.Debug("recycled app pools " + appPoolStringBuilder.ToString());
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception restarting IIS " + e.ToString();
                log.Warn(exceptionMessage);
                operationResult.Result = false;
                operationResult.Message = exceptionMessage;
            }

            return operationResult;
        }

        /// <summary>
        /// This methods parses the configuration string.
        /// </summary>
        /// <param name="configString">The configuration string.</param>
        public void ParseConfigurationString(string configString)
        {
            log.Debug("starting parseConfigurationString()");
            string[] configArray = configString.Split(';');
            foreach (string parameter in configArray)
            {
                string[] nameValue = parameter.Split('=');
                switch (nameValue[0])
                {
                    case "iisserver":
                        log.Debug("iisserver is " + nameValue[1]);
                        this.iisserver = nameValue[1];
                        break;
                    case "domain":
                        log.Debug("domain is " + nameValue[1]);
                        this.domain = nameValue[1];
                        break;
                    case "username":
                        log.Debug("username is " + nameValue[1]);
                        this.username = nameValue[1];
                        break;
                    case "password":
                        log.Debug("password is " + nameValue[1]);
                        this.password = nameValue[1];
                        break;
                    default:
                        log.Info("parameter name is unrecognized " + nameValue[0]);
                        break;
                }
            }
        }

        /// <summary>
        /// This method parses the appPoolsString into an array.
        /// </summary>
        /// <param name="appPoolsString">The appPoolsString parameter.</param>
        /// <returns>The array of AppPool names.</returns>
        public string[] ParseAppPoolsString(string appPoolsString)
        {
            log.Debug("starting ParseAppPoolsString()");
            if (appPoolsString != null)
            {
                return appPoolsString.Split(',');
            }

            return null;
        }
    }
}
