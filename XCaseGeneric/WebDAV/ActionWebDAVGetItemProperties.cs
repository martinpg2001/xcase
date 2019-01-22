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
    /// This action class gets properties for a WebDAV item. The XML file should be of this form:
    /// <operation id="Get WebDAV item proeprties" class="XCaseGeneric.ActionWebDAVListItems">
    ///     <webdav datasource="datasourceName" server="" username="" password="" path="" file="" ssl="true|false" binary="true|false" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionWebDAVGetItemProperties : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method lists WebDAV items. The XML file should be of this form:
        /// <operation id="Get WebDAV item proeprties" class="XCaseGeneric.ActionWebDAVGetItemProperties">
        ///     <webdav server="" username="" password=""  />
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
                OperationResult operationResult = new OperationResult("Success getting WebDAV item properties");
                XmlNode webdavNode = document.SelectSingleNode("operation/webdav");
                if (webdavNode != null)
                {
                    WebDAV webdavDatasource = null;
                    if (webdavNode.SelectSingleNode("@datasource") != null)
                    {
                        string datasourceName = Helper.GetStringNodeValue(webdavNode, "@datasource", true);
                        webdavDatasource = (WebDAV)Helper.GetHelperDictionary()[datasourceName];
                    }
                    else
                    {
                        Dictionary<string, string> webdavDictionary = new Dictionary<string, string>();
                        string server = Helper.GetStringNodeValue(webdavNode, "@server", true);
                        webdavDictionary.Add("host", server);
                        string domain = Helper.GetStringNodeValue(webdavNode, "@domain", false);
                        webdavDictionary.Add("domain", domain);
                        string username = Helper.GetStringNodeValue(webdavNode, "@username", false);
                        webdavDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(webdavNode, "@password", false);
                        webdavDictionary.Add("password", password);
                        webdavDatasource = new WebDAV(webdavDictionary);
                    }

                    string uriPath = "http://" + webdavDatasource.Host;
                    log.Debug("uriPath is " + uriPath);
                    HttpWebRequest httpWebRequest = (HttpWebRequest)HttpWebRequest.Create(uriPath);
                    CredentialCache credentialCache = new System.Net.CredentialCache();
                    credentialCache.Add(new System.Uri(uriPath), "NTLM", new System.Net.NetworkCredential(webdavDatasource.Username, webdavDatasource.Password, webdavDatasource.Domain));
                    httpWebRequest.Credentials = credentialCache;
                    httpWebRequest.Method = "SEARCH";
                    string query = "<?xml version=\"1.0\"?><D:searchrequest xmlns:a = \"DAV:\" xmlns:m = \"urn:schemas:httpmail:\">" + "<D:sql>SELECT \"urn:schemas:httpmail:htmldescription\" ,  \"urn:schemas:httpmail:subject\"  FROM \"" + uriPath + "\"" + "WHERE \"DAV:ishidden\" = false AND \"DAV:isfolder\" = false" + "</D:sql></D:searchrequest>";
                    byte[] bytes = Encoding.UTF8.GetBytes((string)query);
                    httpWebRequest.ContentLength = bytes.Length;
                    Stream requestStream = httpWebRequest.GetRequestStream();
                    requestStream.Write(bytes, 0, bytes.Length);
                    requestStream.Close();
                    httpWebRequest.ContentType = "text/xml";
                    httpWebRequest.Headers.Add("Translate", "F");
                    HttpWebResponse httpWebResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                    Stream responseStream = httpWebResponse.GetResponseStream();
                }
                else
                {
                    string missingWebDAVNodeMessage = "Missing webdav node";
                    log.Warn(missingWebDAVNodeMessage);
                    return new OperationResult(false, missingWebDAVNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception getting WebDAV item proprties " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
