// ----------------------------------------------------------------------------
// <copyright file="ActionFTPGet.cs" company="XCase">
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
    /// This action class retrieves a file from an FTP server. The XML file should be of this form:
    /// <operation id="Do Nothing" class="XCaseGeneric.ActionFTPGet">
    ///     <ftp server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionFTPGet : ActionAbstract
    {
        /// <summary>
        /// This method retrieves a file from an FTP server. The XML file should be of this form:
        /// <operation id="Do Nothing" class="XCaseGeneric.ActionFTPGet">
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
                OperationResult operationResult = new OperationResult("Success getting file");
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
                        int port = Helper.GetIntNodeValue(ftpNode, "@port", 21, false);
                        Helper.LogDebug(GetType().Name + ": port is " + port);
                        ftpDictionary.Add("port", port.ToString());
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
                    string uriPath = "ftp://" + ftpDatasource.Host + ":" + ftpDatasource.Port + "/" + sourcePath + "/" + file;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    Uri ftpUri = new Uri(uriPath);
                    FtpWebRequest ftpWebRequest = (FtpWebRequest)WebRequest.Create(ftpUri);
                    ftpWebRequest.Credentials = new NetworkCredential(ftpDatasource.Username, ftpDatasource.Password);
                    ftpWebRequest.Method = WebRequestMethods.Ftp.DownloadFile;
                    ftpWebRequest.EnableSsl = ftpDatasource.Ssl;
                    Helper.LogDebug(GetType().Name + ": EnableSsl is " + ftpDatasource.Ssl);
                    ftpWebRequest.KeepAlive = false;
                    ftpWebRequest.UseBinary = !ftpDatasource.Ascii;
                    ftpWebRequest.UsePassive = false;
                    FtpWebResponse ftpWebResponse = (FtpWebResponse)ftpWebRequest.GetResponse();
                    Helper.LogDebug(GetType().Name + ": got response");
                    Stream responseStream = ftpWebResponse.GetResponseStream();
                    byte[] buffer = new byte[2048];
                    FileStream outputFileStream = new FileStream(file, FileMode.CreateNew);
                    while (responseStream.Read(buffer, 0, buffer.Length) > 0)
                    {
                        outputFileStream.Write(buffer, 0, buffer.Length);
                    }

                    Helper.LogDebug(GetType().Name + ": written file to stream");
                    outputFileStream.Flush();
                    outputFileStream.Close();
                    Helper.LogDebug(GetType().Name + ": closed output file stream");
                    responseStream.Flush();
                    responseStream.Close();
                    responseStream.Dispose();
                    Helper.LogDebug(GetType().Name + ": disposed of response stream");
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
                string exceptionMessage = "Exception getting file " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
