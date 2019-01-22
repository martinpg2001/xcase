namespace XCaseGeneric
{
    using System;
    using System.Collections.ObjectModel;
    using System.Management.Automation;
    using System.Management.Automation.Runspaces;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class executes a remote command. The XML file should be of this form:
    /// <operation id="Execute remote command" class="XCaseGeneric.ActionRemoteExecution">
    ///     <remoteexecution server="" username="" password="" command="" arguments="" />
    /// </operation>
    /// The server attribute is required.
    /// </summary>
    public class ActionRemoteExecution : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method executes a remote command. The XML file should be of this form:
        /// <operation id="Executes a remote command" class="XCaseGeneric.ActionRemoteExecution">
        ///     <remoteexecution domain="" server="" username="" password="" command="" arguments="" />
        /// </operation>
        /// The server attribute is required.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success executing remote command");
                XmlNode webdavNode = document.SelectSingleNode("operation/remoteexecution");
                if (webdavNode != null)
                {
                    string server = Helper.GetStringNodeValue(webdavNode, "@server", true);
                    log.Debug("server is " + server);
                    string domain = Helper.GetStringNodeValue(webdavNode, "@domain", false);
                    log.Debug("domain is " + domain);
                    string username = Helper.GetStringNodeValue(webdavNode, "@username", false);
                    log.Debug("username is " + username);
                    string password = Helper.GetStringNodeValue(webdavNode, "@password", false);
                    log.Debug("password is " + password);
                    string command = Helper.GetStringNodeValue(webdavNode, "@command", true);
                    log.Debug("command is " + command);
                    string arguments = Helper.GetStringNodeValue(webdavNode, "@arguments", false);
                    log.Debug("arguments is " + arguments);
                    System.Management.Automation.Runspaces.Runspace remoteRunspace = null;
                    string serverURL = "http://" + server + ":5985/wsman";
                    log.Debug("serverURL is " + serverURL);
                    string schema = "http://schemas.microsoft.com/powershell/Microsoft.PowerShell";
                    log.Debug("schema is " + schema);
                    string userid = domain + "\\" + username;
                    log.Debug("userid is " + userid);
                    this.OpenRunspace(serverURL, schema, userid, password, ref remoteRunspace);
                    log.Debug("opened runspace");
                    using (PowerShell powershell = PowerShell.Create())
                    {
                        powershell.Runspace = remoteRunspace;
                        powershell.AddCommand(command);
                        powershell.AddArgument(arguments);
                        ////powershell.Invoke();
                        Collection<PSObject> results = powershell.Invoke();
                        foreach (PSObject result in results)
                        {
                            log.Debug("next result " + result.ToString());
                        }
                    }

                    remoteRunspace.Close();
                }
                else
                {
                    string missingRemoteExecutionNodeMessage = "Missing remoteexecution node";
                    log.Warn(missingRemoteExecutionNodeMessage);
                    return new OperationResult(false, missingRemoteExecutionNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception during remote execution " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }

        /// <summary>
        /// This method opens the runspace using the credentials provided.
        /// </summary>
        /// <param name="serverURL">The serverURL parameter.</param>
        /// <param name="serverSchema">The serverSchema parameter.</param>
        /// <param name="username">The username parameter.</param>
        /// <param name="password">The paswword parameter.</param>
        /// <param name="remoteRunspace">The reference to the remote runspace.</param>
        private void OpenRunspace(string serverURL, string serverSchema, string username, string password, ref Runspace remoteRunspace)
        {
            System.Security.SecureString securePassword = new System.Security.SecureString();
            foreach (char c in password.ToCharArray())
            {
                securePassword.AppendChar(c);
            }

            PSCredential credential = new PSCredential(username, securePassword);
            log.Debug("created PS credential");
            WSManConnectionInfo windowsManagementConnectionInfo = new WSManConnectionInfo(new Uri(serverURL), serverSchema, credential);
            log.Debug("created connection info");
            windowsManagementConnectionInfo.AuthenticationMechanism = AuthenticationMechanism.Kerberos;
            windowsManagementConnectionInfo.NoMachineProfile = true;
            ////wsManConnectionInfo.ProxyAuthentication = AuthenticationMechanism.Negotiate;
            remoteRunspace = System.Management.Automation.Runspaces.RunspaceFactory.CreateRunspace(windowsManagementConnectionInfo);
            log.Debug("created runspace");
            try
            {
                remoteRunspace.Open();
            }
            catch (Exception e)
            {
                log.Warn("exception message is " + e.Message);
                log.Warn("inner exception is " + e.InnerException);
                log.Warn("stack trace is " + e.StackTrace);
                if (e is System.Management.Automation.Remoting.PSRemotingTransportException)
                {
                    int errorCode = ((System.Management.Automation.Remoting.PSRemotingTransportException)e).ErrorCode;
                    log.Warn("error code is " + errorCode);
                    switch (errorCode)
                    {
                        case 5:
                            log.Warn("password issue");
                            break;
                        case -2144108526:
                            log.Warn("URL issue");
                            break;
                        case -2147217388:
                            log.Warn("unknown issue");
                            break;
                        case -2147217406:
                            log.Warn("unknown issue");
                            break;
                        default:
                            log.Warn("default issue");
                            break;
                    }
                } 

                throw e;
            }

            log.Debug("opened runspace");
        }
    }
}
