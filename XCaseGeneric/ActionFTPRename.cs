// ----------------------------------------------------------------------------
// <copyright file="ActionFTPRename.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class deletes a file from an FTP server.
// </summary>
// ----------------------------------------------------------------------------
namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Text;
    using System.Xml;
    using System.Xml.XPath;
    using TestClientBase;

    /// <summary>
    /// This action class renames a file from an FTP server. The XML file should be of this form:
    /// <operation id="Do Nothing" class="XCaseGeneric.ActionFTPRename">
    ///     <ftp datasource="" server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional. You can use the datasource attribute to retrieve a pre-existing FTP datasource.
    /// </summary>
    public class ActionFTPRename : ActionAbstract
    {
        /// <summary>
        /// This method renames a file.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            Helper.LogDebug(GetType().Name + ": starting Execute()");
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
                            Helper.LogDebug(GetType().Name + ": retrieved FTP datasource with name " + datasourceName);
                        }
                        else
                        {
                            string missingFTPDatasourceMessage = "No FTP datasource with the following name exists " + datasourceName;
                            Helper.LogDebug(GetType().Name + ": " + missingFTPDatasourceMessage);
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
                        Helper.LogDebug(GetType().Name + ": ssl is " + ssl);
                        ftpDictionary.Add("ssl", ssl.ToString());
                        bool binary = Helper.GetBooleanNodeValue(ftpNode, "@binary", false, false);
                        Helper.LogDebug(GetType().Name + ": binary is " + binary);
                        ftpDictionary.Add("binary", binary.ToString());
                        ftpDatasource = new FTP(ftpDictionary);
                    }

                    string path = Helper.GetStringNodeValue(ftpNode, "@path", false);
                    string file = Helper.GetStringNodeValue(ftpNode, "@file", true);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + path + "/" + file;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    ftpWebRequest.Method = WebRequestMethods.Ftp.DeleteFile;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UsePassive = false;
                    FtpWebResponse ftpWebResponse = (FtpWebResponse)ftpWebRequest.GetResponse();
                    Helper.LogDebug(GetType().Name + ": got response");
                }
                else
                {
                    string missingFTPNodeMessage = "Missing ftp node";
                    Helper.LogWarn(GetType().Name + ": " + missingFTPNodeMessage);
                    return new OperationResult(false, missingFTPNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception renaming file " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
