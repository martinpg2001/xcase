namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Net;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class renames a file from an FTP server. The XML file should be of this form:
    /// <operation id="Do Nothing" class="XCaseGeneric.ActionFTPRename">
    ///     <ftp datasource="" server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional. You can use the datasource attribute to retrieve a pre-existing FTP datasource.
    /// </summary>
    public class ActionFTPRename : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method renames a file.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success renaming file");
                XmlNode ftpNode = document.SelectSingleNode("operation/ftp");
                if (ftpNode != null)
                {
                    FTP ftpDatasource = null;
                    if (ftpNode.SelectSingleNode("@datasource") != null)
                    {
                        string datasourceName = Helper.GetStringNodeValue(ftpNode, "@datasource", true);
                        ftpDatasource = (FTP)Helper.GetHelperDictionary()[datasourceName];
                        if (ftpDatasource != null)
                        {
                            log.Debug("retrieved FTP datasource with name " + datasourceName);
                        }
                        else
                        {
                            string missingFTPDatasourceMessage = "No FTP datasource with the following name exists " + datasourceName;
                            log.Debug("" + missingFTPDatasourceMessage);
                            return new OperationResult(false, missingFTPDatasourceMessage);
                        }
                    }
                    else
                    {
                        Dictionary<string, string> ftpDictionary = new Dictionary<string, string>();
                        string server = Helper.GetStringNodeValue(ftpNode, "@server", true);
                        ftpDictionary.Add("host", server);
                        string username = Helper.GetStringNodeValue(ftpNode, "@username", false);
                        ftpDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(ftpNode, "@password", false);
                        ftpDictionary.Add("password", password);
                        bool ssl = Helper.GetBooleanNodeValue(ftpNode, "@ssl", false, false);
                        log.Debug("ssl is " + ssl);
                        ftpDictionary.Add("ssl", ssl.ToString());
                        bool binary = Helper.GetBooleanNodeValue(ftpNode, "@binary", false, false);
                        log.Debug("binary is " + binary);
                        ftpDictionary.Add("binary", binary.ToString());
                        ftpDatasource = new FTP(ftpDictionary);
                    }

                    string path = Helper.GetStringNodeValue(ftpNode, "@path", false);
                    string file = Helper.GetStringNodeValue(ftpNode, "@file", true);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + path + "/" + file;
                    log.Debug("uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    ftpWebRequest.Method = WebRequestMethods.Ftp.DeleteFile;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UsePassive = false;
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
                string exceptionMessage = "Exception renaming file " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
