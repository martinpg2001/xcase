namespace XCaseGeneric
{
    using System;
    using System.IO;
    using System.Net;
    using System.Net.Mail;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to check email against an Exchange 2003 server.
    /// </summary>
    public class ActionCheckEmail2003 : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method checks for an email. The XmlDocument must be of this form:
        /// <operation id="Check an email" class="XCaseGeneric.ActionCheckEmail2003">
        ///     <exchange domain="XCASE" url="http://exchangeserver/Exchange" username="Administrator" password="password" />
        ///     <email folder="Inbox" mailbox="mailbox" subject="Email sent from Test Tools" />
        ///     <expected>
        ///         <email>
        ///             This is the body of the email.
        ///         </email>
        ///         <checkvalue name="id" regex="Click here to acknowledge:()" value="1" />
        ///     </expected>
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
                        log.DebugFormat("exchangeURL is {0}", exchangeURL);
                    }

                    if (exchangeNode.SelectSingleNode("@username") != null)
                    {
                        exchangeUsername = Helper.GetStringNodeValue(exchangeNode, "@username", string.Empty, false);
                        log.DebugFormat("exchangeUsername is {0}", exchangeUsername);
                    }

                    if (exchangeNode.SelectSingleNode("@domain") != null)
                    {
                        exchangeDomain = Helper.GetStringNodeValue(exchangeNode, "@domain", string.Empty, false);
                        log.DebugFormat("exchangeDomain is {0}", exchangeDomain);
                    }

                    if (exchangeNode.SelectSingleNode("@password") != null)
                    {
                        exchangePassword = Helper.GetStringNodeValue(exchangeNode, "@password", string.Empty, false);
                        log.DebugFormat("exchangePassword is {0}", exchangePassword);
                    }

                    XmlNode emailNode = document.SelectSingleNode("operation/email");
                    if (emailNode != null)
                    {
                        log.DebugFormat("email node is not null");
                        folder = Helper.GetStringNodeValue(emailNode, "@folder", "Inbox", false);
                        log.DebugFormat("folder is {0}", folder);

                        if (emailNode.SelectSingleNode("@mailbox") != null)
                        {
                            emailMailbox = Helper.GetStringNodeValue(emailNode, "@mailbox", string.Empty, false);
                            log.DebugFormat("emailMailbox is {0}", emailMailbox);
                            mailboxURL = exchangeURL + "/" + emailMailbox;
                            log.DebugFormat("mailboxURL is {0}", mailboxURL);
                            folderURL = mailboxURL + "/" + folder;
                            log.DebugFormat("folderURL is {0}", folderURL);
                        }

                        if (emailNode.SelectSingleNode("@subject") != null)
                        {
                            emailSubject = Helper.GetStringNodeValue(emailNode, "@subject", string.Empty, false);
                            log.DebugFormat("emailSubject is {0}", emailSubject);
                        }
                    }

                    string queryString = "<?xml version=\"1.0\"?><D:searchrequest xmlns:D = \"DAV:\" >" + "<D:sql>SELECT \"DAV:displayname\", \"urn:schemas:httpmail:subject\", \"urn:schemas:mailheader:from\", \"urn:schemas:httpmail:to\", \"http://schemas.microsoft.com/mapi/proptag/x1000001e\" FROM \"" + folderURL + "\"" + " WHERE \"DAV:ishidden\" = false AND \"urn:schemas:httpmail:subject\" = '" + emailSubject + "' ORDER BY \"DAV:displayname\" DESC" + "</D:sql></D:searchrequest>";
                    log.DebugFormat("queryString is {0}", queryString);
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
                    log.DebugFormat("about to make request");
                    WebResponse webResponse = httpWebRequest.GetResponse();
                    log.DebugFormat("got Web response");
                    XmlDocument responseXmlDocument = new XmlDocument();
                    responseXmlDocument.Load(webResponse.GetResponseStream());
                    responseXmlDocument.Save(System.Console.Out);
                    MailMessage actualEmailMessage = null;
                    XmlNodeList actualEmailNodeList = responseXmlDocument.GetElementsByTagName("a:response");
                    log.DebugFormat("actual email node list has count is {0}", actualEmailNodeList.Count);
                    foreach (XmlNode actualEmailNode in actualEmailNodeList)
                    {
                        log.DebugFormat("next actual email");
                        System.Xml.XmlDocument actualEmailDocument = new System.Xml.XmlDocument();
                        actualEmailDocument.LoadXml(actualEmailNode.OuterXml);
                        actualEmailMessage = GenericHelper.CreateMailMessageFromWebDavResponse(actualEmailDocument);
                        log.DebugFormat("actual email is {0}", actualEmailMessage.Body);
                    }

                    if (actualEmailNodeList.Count == 0)
                    {
                        /* If email expected, but no email, found return false */
                        if (document.SelectSingleNode("operation/expected/email") != null)
                        {
                            string noEmailMessagesFoundMessage = string.Format("No email messages found matching subject {0}", emailSubject);
                            log.Warn(noEmailMessagesFoundMessage);
                            return new OperationResult(false, noEmailMessagesFoundMessage);
                        }
                        else
                        {
                            string emailBodyMessage = "Success checking email message";
                            return new OperationResult(true, emailBodyMessage);
                        }
                    }
                    else
                    {
                        log.DebugFormat("at least one email found");
                        XmlNode expectedEmailNode = document.SelectSingleNode("operation/expected/email");
                        if (expectedEmailNode != null)
                        {
                            log.DebugFormat("expected email node is not null");
                            MailMessage expectedEmailMessage = GenericHelper.CreateMailMessageFromXmlNode(expectedEmailNode);
                            log.DebugFormat("create expected email message");
                            CompareResult compareMailMessages = GenericHelper.CompareMailMessages(actualEmailMessage, expectedEmailMessage);
                            if (!compareMailMessages.Result)
                            {
                                string compareMailMessageMessage = string.Format("Actual and expected mail messages do not match: {0}", compareMailMessages.Message);
                                log.Warn(compareMailMessageMessage);
                                return new OperationResult(false, compareMailMessageMessage);
                            }
                        }

                        return new OperationResult(true, actualEmailBody);
                    }
                }
                else
                {
                    log.Info("no Exchange server specified");
                    return new OperationResult(true, "No Exchange server specified");
                }
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception checking email message: " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
