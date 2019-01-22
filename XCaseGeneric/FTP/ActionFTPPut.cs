namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Net;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class uploads a file to an FTP server. The XML file should be of this form:
    /// <operation id="Upload file" class="XCaseGeneric.ActionFTPPut">
    ///     <ftp server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionFTPPut : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method uploads a file to an FTP server. The XML file should be of this form:
        /// <operation id="Upload file" class="XCaseGeneric.ActionFTPPut">
        ///     <ftp server="" username="" password="" sourcepath="" file="" targetpath="" binary="true|false" ssl="true|false" />
        /// </operation>
        /// The ssl attribute is optional.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success putting file");
                XmlNode ftpNode = document.SelectSingleNode("operation/ftp");
                if (ftpNode != null)
                {
                    FTP ftpDatasource = null;
                    if (ftpNode.SelectSingleNode("@datasource") != null)
                    {
                        string datasourceName = Helper.GetStringNodeValue(ftpNode, "@datasource", true);
                        ftpDatasource = (FTP)Helper.GetHelperDictionary()[datasourceName];
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

                    string sourcePath = Helper.GetStringNodeValue(ftpNode, "@sourcepath", false);
                    string targetPath = Helper.GetStringNodeValue(ftpNode, "@targetpath", false);
                    string file = Helper.GetStringNodeValue(ftpNode, "@file", true);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + targetPath + "/" + file;
                    log.Debug("uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    log.Debug("created request");
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    log.Debug("created credentials");
                    ftpWebRequest.Method = WebRequestMethods.Ftp.UploadFile;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UseBinary = !ftpDatasource.Ascii;
                    ftpWebRequest.UsePassive = false;
                    log.Debug("set request flags");
                    string fullPathToFile = sourcePath + Path.DirectorySeparatorChar + file;
                    log.Debug("fullPathToFile is " + fullPathToFile);
                    StreamReader sourceStream = new StreamReader(fullPathToFile);
                    byte[] fileContents = Encoding.UTF8.GetBytes(sourceStream.ReadToEnd());
                    log.Debug("read file to end");
                    sourceStream.Close();
                    log.Debug("closed source stream");
                    ftpWebRequest.ContentLength = fileContents.Length;
                    log.Debug("file contents length is " + fileContents.Length);
                    Stream requestStream = ftpWebRequest.GetRequestStream();
                    log.Debug("got request stream");
                    requestStream.Write(fileContents, 0, fileContents.Length);
                    log.Debug("written file contents");
                    requestStream.Close();
                    log.Debug("about to get response");
                    FtpWebResponse response = (FtpWebResponse)ftpWebRequest.GetResponse();
                    log.Debug("got response");
                    response.Close();
                    log.Debug("closed response");
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
                string exceptionMessage = "Exception putting file " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
