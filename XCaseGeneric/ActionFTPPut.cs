// ----------------------------------------------------------------------------
// <copyright file="ActionFTPPut.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class uploads a file to an FTP server.
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
    /// This action class uploads a file to an FTP server. The XML file should be of this form:
    /// <operation id="Upload file" class="XCaseGeneric.ActionFTPPut">
    ///     <ftp server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionFTPPut : ActionAbstract
    {
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
            Helper.LogDebug(GetType().Name + ": starting Execute()");
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
                        Helper.LogDebug(GetType().Name + ": ssl is " + ssl);
                        ftpDictionary.Add("ssl", ssl.ToString());
                        bool binary = Helper.GetBooleanNodeValue(ftpNode, "@binary", false, false);
                        Helper.LogDebug(GetType().Name + ": binary is " + binary);
                        ftpDictionary.Add("binary", binary.ToString());
                        ftpDatasource = new FTP(ftpDictionary);
                    }

                    string sourcePath = Helper.GetStringNodeValue(ftpNode, "@sourcepath", false);
                    string targetPath = Helper.GetStringNodeValue(ftpNode, "@targetpath", false);
                    string file = Helper.GetStringNodeValue(ftpNode, "@file", true);
                    string uriPath = "ftp://" + ftpDatasource.Host + "/" + targetPath + "/" + file;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    Helper.LogDebug(GetType().Name + ": created request");
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    Helper.LogDebug(GetType().Name + ": created credentials");
                    ftpWebRequest.Method = WebRequestMethods.Ftp.UploadFile;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UseBinary = !ftpDatasource.Ascii;
                    ftpWebRequest.UsePassive = false;
                    Helper.LogDebug(GetType().Name + ": set request flags");
                    string fullPathToFile = sourcePath + Path.DirectorySeparatorChar + file;
                    Helper.LogDebug(GetType().Name + ": fullPathToFile is " + fullPathToFile);
                    StreamReader sourceStream = new StreamReader(fullPathToFile);
                    byte[] fileContents = Encoding.UTF8.GetBytes(sourceStream.ReadToEnd());
                    Helper.LogDebug(GetType().Name + ": read file to end");
                    sourceStream.Close();
                    Helper.LogDebug(GetType().Name + ": closed source stream");
                    ftpWebRequest.ContentLength = fileContents.Length;
                    Helper.LogDebug(GetType().Name + ": file contents length is " + fileContents.Length);
                    Stream requestStream = ftpWebRequest.GetRequestStream();
                    Helper.LogDebug(GetType().Name + ": got request stream");
                    requestStream.Write(fileContents, 0, fileContents.Length);
                    Helper.LogDebug(GetType().Name + ": written file contents");
                    requestStream.Close();
                    Helper.LogDebug(GetType().Name + ": about to get response");
                    FtpWebResponse response = (FtpWebResponse)ftpWebRequest.GetResponse();
                    Helper.LogDebug(GetType().Name + ": got response");
                    response.Close();
                    Helper.LogDebug(GetType().Name + ": closed response");
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
                string exceptionMessage = "Exception putting file " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
