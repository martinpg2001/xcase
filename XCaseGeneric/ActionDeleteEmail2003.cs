namespace XCaseGeneric
{
    using System;
    using System.IO;
    using System.Net;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to delete email against an Exchange 2003 server.
    /// </summary>
    public class ActionDeleteEmail2003 : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion


        /// <summary>
        /// This method deletes email. The XmlDocument must be of this form:
        /// <operation id="Check an email" class="XCaseGeneric.ActionCheckEmail2003">
        ///     <exchange domain="XCASE" url="http://exchangeserver/Exchange" username="Administrator" password="password" />
        ///     <email folder="Inbox" mailbox="mailbox" subject="Email sent from Test Tools" />
        /// </operation>
        /// Use the ssl attribute to specify an SSL connection to the Exchange server. Use
        /// the checkvalue attribute to retrieve a string from within the email body and
        /// save it to the Helper dictionary with specified id.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                string exchangeURL = string.Empty;
                string exchangeDomain = string.Empty;
                string exchangeUsername = string.Empty;
                string exchangePassword = string.Empty;
                string emailMailbox = string.Empty;
                string emailSubject = string.Empty;
                string actualEmailBody = string.Empty;
                string expectedEmailBody = string.Empty;
                string folder = string.Empty;
                string folderURL = string.Empty;
                string mailboxURL = string.Empty;
                XmlNode exchangeNode = document.SelectSingleNode("operation/exchange");
                if (exchangeNode != null)
                {
                    if (exchangeNode.SelectSingleNode("@url") != null)
                    {
                        exchangeURL = Helper.GetStringNodeValue(exchangeNode, "@url", string.Empty, false);
                        log.Debug("exchangeURL is " + exchangeURL);
                    }

                    if (exchangeNode.SelectSingleNode("@username") != null)
                    {
                        exchangeUsername = Helper.GetStringNodeValue(exchangeNode, "@username", string.Empty, false);
                        log.Debug("exchangeUsername is " + exchangeUsername);
                    }

                    if (exchangeNode.SelectSingleNode("@domain") != null)
                    {
                        exchangeDomain = Helper.GetStringNodeValue(exchangeNode, "@domain", string.Empty, false);
                        log.Debug("exchangeDomain is " + exchangeDomain);
                    }

                    if (exchangeNode.SelectSingleNode("@password") != null)
                    {
                        exchangePassword = Helper.GetStringNodeValue(exchangeNode, "@password", string.Empty, false);
                        log.Debug("exchangePassword is " + exchangePassword);
                    }

                    string subjectQueryString = string.Empty;
                    XmlNode emailNode = document.SelectSingleNode("operation/email");
                    if (emailNode != null)
                    {
                        log.Debug("email node is not null");
                        folder = Helper.GetStringNodeValue(emailNode, "@folder", "Inbox", false);
                        log.Debug("folder is " + folder);

                        if (emailNode.SelectSingleNode("@mailbox") != null)
                        {
                            emailMailbox = Helper.GetStringNodeValue(emailNode, "@mailbox", string.Empty, false);
                            log.Debug("emailMailbox is " + emailMailbox);
                            mailboxURL = exchangeURL + "/" + emailMailbox;
                            log.Debug("mailboxURL is " + mailboxURL);
                            folderURL = mailboxURL + "/" + folder;
                            log.Debug("folderURL is " + folderURL);
                        }

                        emailSubject = Helper.GetStringNodeValue(emailNode, "@subject", string.Empty, false);
                        log.Debug("emailSubject is " + emailSubject);
                        if (emailSubject != string.Empty)
                        {
                            subjectQueryString = " AND \"urn:schemas:httpmail:subject\" = '" + emailSubject + "'";
                        }
                    }

                    string queryString = "<?xml version=\"1.0\"?><D:searchrequest xmlns:D = \"DAV:\" >" + "<D:sql>SELECT \"DAV:displayname\", \"urn:schemas:httpmail:subject\", \"urn:schemas:mailheader:from\", \"urn:schemas:httpmail:to\", \"http://schemas.microsoft.com/mapi/proptag/x1000001e\" FROM \"" + folderURL + "\"" + " WHERE \"DAV:ishidden\" = false " + subjectQueryString + " ORDER BY \"DAV:displayname\" DESC" + "</D:sql></D:searchrequest>";
                    log.Debug("queryString is " + queryString);
                    System.Net.CredentialCache credentialCache = new System.Net.CredentialCache();
                    credentialCache.Add(new System.Uri(mailboxURL), "NTLM", new System.Net.NetworkCredential(exchangeUsername, exchangePassword, exchangeDomain));
                    System.Net.HttpWebRequest httpWebRequest = (System.Net.HttpWebRequest)HttpWebRequest.Create(mailboxURL);
                    httpWebRequest.Credentials = credentialCache;
                    httpWebRequest.Method = "SEARCH";
                    byte[] bytes = Encoding.UTF8.GetBytes((string)queryString);
                    httpWebRequest.ContentLength = bytes.Length;
                    Stream requestStream = httpWebRequest.GetRequestStream();
                    requestStream.Write(bytes, 0, bytes.Length);
                    requestStream.Close();
                    httpWebRequest.ContentType = "text/xml";
                    log.Debug("about to make request");
                    WebResponse webResponse = httpWebRequest.GetResponse();
                    log.Debug("got Web response");
                    XmlDocument responseXmlDocument = new XmlDocument();
                    responseXmlDocument.Load(webResponse.GetResponseStream());
                    responseXmlDocument.Save(System.Console.Out);
                    XmlNodeList actualEmailNodeList = responseXmlDocument.GetElementsByTagName("a:response");
                    log.Debug("actual email node list has count is " + actualEmailNodeList.Count);
                    foreach (XmlNode actualEmailNode in actualEmailNodeList)
                    {
                        log.Debug("next actual email");
                        System.Xml.XmlDocument actualEmailDocument = new System.Xml.XmlDocument();
                        actualEmailDocument.LoadXml(actualEmailNode.OuterXml);
                        XmlNodeList actualEmailHrefNodeList = actualEmailDocument.GetElementsByTagName("a:href");
                        foreach (XmlNode actualEmailHrefNode in actualEmailHrefNodeList)
                        {
                            string actualEmailURL = actualEmailHrefNode.InnerText;
                            log.Debug("actualEmailURL is " + actualEmailURL);
                            System.Net.HttpWebRequest httpDeleteEmailWebRequest = (System.Net.HttpWebRequest)HttpWebRequest.Create(actualEmailURL);
                            httpDeleteEmailWebRequest.Credentials = credentialCache;
                            httpDeleteEmailWebRequest.Method = "DELETE";
                            log.Debug("set method to DELETE");
                            httpDeleteEmailWebRequest.ContentType = "text/xml";
                            log.Debug("about to make request");
                            webResponse = httpDeleteEmailWebRequest.GetResponse();
                            log.Debug("got Web response");
                        }
                    }
                }
                else
                {
                    log.Info("no Exchange server specified");
                    return new OperationResult(true, "No Exchange server specified");
                }

                return new OperationResult(true, actualEmailBody);
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception checking email message " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
