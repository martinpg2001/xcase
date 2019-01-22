namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Net;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class creates a directory on an FTP server. The XML file should be of this form:
    /// <operation id="Make directory" class="XCaseGeneric.ActionFTPMakeDirectory">
    ///     <ftp server="" username="" password="" basepath="" newdirectory="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionFTPMakeDirectory : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method creates a directory.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success making directory");
                XmlNode ftpNode = document.SelectSingleNode("operation/ftp");
                if (ftpNode != null)
                {
                    FTP ftpDatasource = null;
                    if (ftpNode.SelectSingleNode("@datasource") != null)
                    {
                        string datasourceName = Helper.GetStringNodeValue(ftpNode, "@datasource", true);
                        log.Debug("datasourceName is " + datasourceName);
                        ftpDatasource = (FTP)Helper.GetHelperDictionary()[datasourceName];
                        log.Debug("retrieved FTP datasource with name " + datasourceName);
                    }
                    else
                    {
                        Dictionary<string, string> ftpDictionary = new Dictionary<string, string>();
                        string server = Helper.GetStringNodeValue(ftpNode, "@server", true);
                        log.Debug("server is " + server);
                        ftpDictionary.Add("host", server);
                        string username = Helper.GetStringNodeValue(ftpNode, "@username", false);
                        log.Debug("username is " + username);
                        ftpDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(ftpNode, "@password", false);
                        log.Debug("password is " + password);
                        ftpDictionary.Add("password", password);
                        bool ssl = Helper.GetBooleanNodeValue(ftpNode, "@ssl", false, false);
                        log.Debug("ssl is " + ssl);
                        ftpDictionary.Add("ssl", ssl.ToString());
                        ftpDatasource = new FTP(ftpDictionary);
                    }

                    string basePath = Helper.GetStringNodeValue(ftpNode, "@basepath", false);
                    log.Debug("basePath is " + basePath);
                    string newDirectory = Helper.GetStringNodeValue(ftpNode, "@newdirectory", true);
                    log.Debug("newDirectory is " + newDirectory);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + basePath + "/" + newDirectory;
                    log.Debug("uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    ftpWebRequest.Method = WebRequestMethods.Ftp.MakeDirectory;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UsePassive = false;
                    log.Debug("about to make directory");
                    FtpWebResponse ftpWebResponse = (FtpWebResponse)ftpWebRequest.GetResponse();
                    log.Debug("got response");
                }
                else
                {
                    string missingFTPNodeMessage = "Missing ftp node";
                    log.Warn(missingFTPNodeMessage);
                    return new OperationResult(false, missingFTPNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception making directory " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
