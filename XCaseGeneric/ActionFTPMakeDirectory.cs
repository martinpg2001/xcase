// ----------------------------------------------------------------------------
// <copyright file="ActionFTPMakeDirectory.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class gets a file from an FTP server.
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
    /// This action class creates a directory on an FTP server. The XML file should be of this form:
    /// <operation id="Make directory" class="XCaseGeneric.ActionFTPMakeDirectory">
    ///     <ftp server="" username="" password="" basepath="" newdirectory="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionFTPMakeDirectory : ActionAbstract
    {
        /// <summary>
        /// This method creates a directory.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            Helper.LogDebug(GetType().Name + ": starting Execute()");
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
                        Helper.LogDebug(GetType().Name + ": datasourceName is " + datasourceName);
                        ftpDatasource = (FTP)Helper.GetHelperDictionary()[datasourceName];
                        Helper.LogDebug(GetType().Name + ": retrieved FTP datasource with name " + datasourceName);
                    }
                    else
                    {
                        Dictionary<string, string> ftpDictionary = new Dictionary<string, string>();
                        string server = Helper.GetStringNodeValue(ftpNode, "@server", true);
                        Helper.LogDebug(GetType().Name + ": server is " + server);
                        ftpDictionary.Add("host", server);
                        string username = Helper.GetStringNodeValue(ftpNode, "@username", false);
                        Helper.LogDebug(GetType().Name + ": username is " + username);
                        ftpDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(ftpNode, "@password", false);
                        Helper.LogDebug(GetType().Name + ": password is " + password);
                        ftpDictionary.Add("password", password);
                        bool ssl = Helper.GetBooleanNodeValue(ftpNode, "@ssl", false, false);
                        Helper.LogDebug(GetType().Name + ": ssl is " + ssl);
                        ftpDictionary.Add("ssl", ssl.ToString());
                        ftpDatasource = new FTP(ftpDictionary);
                    }

                    string basePath = Helper.GetStringNodeValue(ftpNode, "@basepath", false);
                    Helper.LogDebug(GetType().Name + ": basePath is " + basePath);
                    string newDirectory = Helper.GetStringNodeValue(ftpNode, "@newdirectory", true);
                    Helper.LogDebug(GetType().Name + ": newDirectory is " + newDirectory);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + basePath + "/" + newDirectory;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    ftpWebRequest.Method = WebRequestMethods.Ftp.MakeDirectory;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UsePassive = false;
                    Helper.LogDebug(GetType().Name + ": about to make directory");
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
                string exceptionMessage = "Exception making directory " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
