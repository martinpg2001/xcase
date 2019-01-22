namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Net.Mail;
    using System.Reflection;
    using System.ServiceModel;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    public class GenericHelper
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method populates the credentials into the API service client. It uses the document
        /// to retrieve the API Service datasource unless this is null.
        /// </summary>
        /// <param name="client">The client parameter.</param>
        /// <param name="webServiceNode">The webServiceNode parameter.</param>
        public static void PopulateClientCredentials(ClientBase<IClientChannel> client, XmlNode webServiceNode)
        {
            log.Debug("starting PopulateClientCredentials()");
            if (webServiceNode != null)
            {
                log.Debug("datasource node is not null");
                if (webServiceNode.SelectSingleNode("@name") != null)
                {
                    string name = Helper.GetStringNodeValue(webServiceNode, "@name", true);
                    log.Debug("name is " + name);
                    if (Helper.GetHelperDictionary().ContainsKey(name))
                    {
                        WebService webServiceDatasource = (WebService)Helper.GetHelperDictionary()[name];
                        PopulateClientCredentials(client, webServiceDatasource);
                        log.Debug("client endpoint is set to " + client.Endpoint.Address.ToString());
                    }
                    else
                    {
                        string missingWebServiceDatasourceMessage = "Missing WebServiceDatasource for name " + name;
                        log.Warn(missingWebServiceDatasourceMessage);
                    }
                }
                else if (webServiceNode.SelectSingleNode("@endpoint") != null)
                {
                    string endpoint = Helper.GetStringNodeValue(webServiceNode, "@endpoint", true);
                    log.Debug("endpoint is " + endpoint);
                    if ((webServiceNode.SelectSingleNode("@windows") != null) && Helper.GetBooleanNodeValueDefaultFalse(webServiceNode, "@windows", false))
                    {
                        EndpointAddress endpointAddress = new System.ServiceModel.EndpointAddress(endpoint);
                        client.Endpoint.Address = endpointAddress;
                        client.ClientCredentials.Windows.ClientCredential = System.Net.CredentialCache.DefaultNetworkCredentials;
                        try
                        {
                            ////client.ClientCredentials.Windows.AllowNtlm = true;
                            log.Debug("set Allow NTLM to true");
                            client.ClientCredentials.Windows.AllowedImpersonationLevel = System.Security.Principal.TokenImpersonationLevel.Impersonation;
                            log.Debug("set AllowedImpersonationLevel to Impersonation");
                        }
                        catch (Exception e)
                        {
                            log.Warn("exception setting NTLM " + e.Message);
                        }
                    }
                    else
                    {
                        log.Debug("datasource name endpoint is not null");
                        Dictionary<string, string> webServiceDatasourceDictionary = new Dictionary<string, string>();
                        webServiceDatasourceDictionary.Add("endpoint", endpoint);
                        string domain = Helper.GetStringNodeValue(webServiceNode, "@domain", false);
                        webServiceDatasourceDictionary.Add("domain", domain);
                        string username = Helper.GetStringNodeValue(webServiceNode, "@username", false);
                        webServiceDatasourceDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(webServiceNode, "@password", false);
                        webServiceDatasourceDictionary.Add("password", password);
                        log.Debug("retrieved parameters");
                        WebService webServiceDatasource = new WebService(webServiceDatasourceDictionary);
                        log.Debug("created Common Service datasource");
                        PopulateClientCredentials(client, webServiceDatasource);
                        log.Debug("client endpoint is set to " + client.Endpoint.Address.ToString());
                    }
                }
                else
                {
                    log.Debug("neither datasource name or endpoint defined");
                }
            }
            else
            {
                log.Warn("datasource node is null");
            }
        }

        /// <summary>
        /// This method populates the client credntials using a Web service datasource parameter.
        /// </summary>
        /// <param name="client">The client parameter.</param>
        /// <param name="webServiceDatasource">The webServiceDatasource parameter.</param>
        public static void PopulateClientCredentials(ClientBase<IClientChannel> client, WebService webServiceDatasource)
        {
            log.Debug("starting PopulateClientCredentials() from commonServiceDatasource");
            client.Endpoint.Address = new System.ServiceModel.EndpointAddress(webServiceDatasource.Endpoint);
            log.Debug("endpoint set to " + webServiceDatasource.Endpoint);
            client.ClientCredentials.Windows.ClientCredential.Domain = webServiceDatasource.Domain;
            log.Debug("domain set to " + webServiceDatasource.Domain);
            client.ClientCredentials.Windows.ClientCredential.UserName = webServiceDatasource.Username;
            log.Debug("username set to " + webServiceDatasource.Username);
            client.ClientCredentials.Windows.ClientCredential.Password = webServiceDatasource.Password;
            log.Debug("password set to " + webServiceDatasource.Password);
            try
            {
                ////client.ClientCredentials.Windows.AllowNtlm = true;
                log.Debug("set Allow NTLM to true");
            }
            catch (Exception e)
            {
                log.Warn("exception setting NTLM " + e.Message);
            }

            log.Debug("finishing PopulateClientCredentials()");
        }

        /// <summary>
        /// This method creates a mail message from an XmlNode.
        /// </summary>
        /// <param name="mailNode">The XML node representing the mail message.</param>
        /// <returns>The corresponding mail message.</returns>
        public static MailMessage CreateMailMessageFromXmlNode(XmlNode mailNode)
        {
            log.DebugFormat("starting CreateMailMessageFromXmlNode()");
            MailMessage mailMessage = new MailMessage();
            string mailBody = mailNode.InnerText;
            log.DebugFormat("mailBody is {0}", mailBody);
            mailMessage.Body = mailBody;
            string fromAddress = Helper.GetStringNodeValue(mailNode, "@from", string.Empty, false);
            log.DebugFormat("fromAddress is {0}", fromAddress);
            MailAddress fromMailAddress = new MailAddress(fromAddress);
            log.DebugFormat("created from address");
            mailMessage.From = fromMailAddress;
            string subject = Helper.GetStringNodeValue(mailNode, "@subject", string.Empty, false);
            log.DebugFormat("subject is {0}", subject);
            mailMessage.Subject = subject;
            string toAddressList = Helper.GetStringNodeValue(mailNode, "@to", string.Empty, false);
            log.DebugFormat("toAddressList is {0}", toAddressList);
            string[] toAddressArray = toAddressList.Split(',');
            foreach (string toAddress in toAddressArray)
            {
                log.DebugFormat("next toAddress is {0}", toAddress);
                MailAddress toMailAddress = new MailAddress(toAddress);
                mailMessage.To.Add(toMailAddress);
                log.DebugFormat("added next toAddress");
            }

            log.DebugFormat("addedd to address collection");
            return mailMessage;
        }

        /// <summary>
        /// This method converts an a:response element into a MailMessage object.
        /// </summary>
        /// <param name="webDavResponseDocument">The email in the form of an a:response element.</param>
        /// <returns>The corresponding MailMessage object.</returns>
        public static MailMessage CreateMailMessageFromWebDavResponse(XmlDocument webDavResponseDocument)
        {
            log.Debug("starting CreateMailMessageFromWebDavResponse()");
            MailMessage mailMessage = new MailMessage();
            log.Debug("created mail message");
            XmlNodeList bodyNodeList = webDavResponseDocument.GetElementsByTagName("f:x1000001e");
            foreach (XmlNode bodyNode in bodyNodeList)
            {
                string mailBody = bodyNode.InnerText;
                log.DebugFormat("mailBody is {0}", mailBody);
                mailMessage.Body = mailBody;
            }

            XmlNodeList fromNodeList = webDavResponseDocument.GetElementsByTagName("e:from");
            foreach (XmlNode fromNode in fromNodeList)
            {
                string fromAddress = fromNode.InnerText;
                log.DebugFormat("fromAddress is {0}", fromAddress);
                MailAddress fromMailAddress = new MailAddress(fromAddress);
                mailMessage.From = fromMailAddress;
            }

            XmlNodeList subjectNodeList = webDavResponseDocument.GetElementsByTagName("d:subject");
            foreach (XmlNode subjectNode in subjectNodeList)
            {
                string subject = subjectNode.InnerText;
                log.DebugFormat("subject is {0}", subject);
                mailMessage.Subject = subject;
            }

            XmlNodeList toNodeList = webDavResponseDocument.GetElementsByTagName("d:to");
            foreach (XmlNode toNode in toNodeList)
            {
                string toNodeString = toNode.InnerText;
                log.DebugFormat("toNodeString is {0}", toNodeString);
                string[] toMailAddressStringArray = toNodeString.Split(',');
                foreach (string toMailAddressString in toMailAddressStringArray)
                {
                    log.DebugFormat("toMailAddressString is {0}", toMailAddressString);
                    MailAddress toMailAddress = new MailAddress(toMailAddressString);
                    mailMessage.To.Add(toMailAddress);
                }
            }

            return mailMessage;
        }

        /// <summary>
        /// This method compares two mail messages.
        /// </summary>
        /// <param name="sourceMailMessage">The source mail message.</param>
        /// <param name="targetMailMessage">The target mail maessage.</param>
        /// <returns>The result of comparing the two mail messages.</returns>
        public static CompareResult CompareMailMessages(MailMessage sourceMailMessage, MailMessage targetMailMessage)
        {
            log.DebugFormat("starting CompareMailMessages()");
            StringBuilder mailCompareStringBuilder = new StringBuilder();
            bool mailCompareBool = true;
            if (sourceMailMessage.Body != targetMailMessage.Body)
            {
                mailCompareBool = false;
                string compareMailBodyMessage = string.Format("Source mail body {0} does not match target mail body {1}", sourceMailMessage.Body, targetMailMessage.Body);
                log.Warn(compareMailBodyMessage);
                mailCompareStringBuilder.Append(compareMailBodyMessage + ";");
            }

            if (string.Compare(sourceMailMessage.From.Address, targetMailMessage.From.Address) != 0)
            {
                mailCompareBool = false;
                string compareMailFromMessage = string.Format("Source mail from {0} does not match target mail from {1}", sourceMailMessage.From, targetMailMessage.From);
                log.Warn(compareMailFromMessage);
                mailCompareStringBuilder.Append(compareMailFromMessage + ";");
            }
            else
            {
                log.Debug("mail from addresses match: " + sourceMailMessage.From.Address);
            }

            if (sourceMailMessage.Subject != targetMailMessage.Subject)
            {
                mailCompareBool = false;
                string compareMailSubjectMessage = string.Format("Source mail subject {0} does not match target mail subject {1}", sourceMailMessage.Subject, targetMailMessage.Subject);
                log.Warn(compareMailSubjectMessage);
                mailCompareStringBuilder.Append(compareMailSubjectMessage + ";");
            }
            else
            {
                log.DebugFormat("mail subjects match: {0}", sourceMailMessage.Subject);
            }

            CompareResult compareToMailAddresses = CompareMailAddressCollections(sourceMailMessage.To, targetMailMessage.To);
            if (!compareToMailAddresses.Result)
            {
                mailCompareBool = false;
                string compareMailToMessage = string.Format("Source mail To addresses do not match target mail To addresses: {0}", compareToMailAddresses.Message);
                log.Warn(compareMailToMessage);
                mailCompareStringBuilder.Append(compareMailToMessage + ";");
            }
            else
            {
                log.DebugFormat("mail To addresses match: {0}", sourceMailMessage.To.ToString());
            }

            return new CompareResult(mailCompareBool, mailCompareStringBuilder.ToString());
        }

        /// <summary>
        /// This method compares to email address collections.
        /// </summary>
        /// <param name="sourceMailAddressCollection">The sourceMailAddressCollection parameter.</param>
        /// <param name="targetMailAddressCollection">The targetMailAddressCollection parameter.</param>
        /// <returns>The result of comparing the two email address collections.</returns>
        public static CompareResult CompareMailAddressCollections(MailAddressCollection sourceMailAddressCollection, MailAddressCollection targetMailAddressCollection)
        {
            log.Debug("starting CompareMailAddressCollections()");
            if (sourceMailAddressCollection == null || targetMailAddressCollection == null)
            {
                return new CompareResult(false, "One of mail address collections is null");
            }

            if (sourceMailAddressCollection.Count != targetMailAddressCollection.Count)
            {
                string mismatchEmailAddressCollectionCount = string.Format("Source mail address collection count {0} is not equal to target mail address collection count {1}", sourceMailAddressCollection.Count, targetMailAddressCollection.Count);
                return new CompareResult(false, mismatchEmailAddressCollectionCount);
            }
            
            bool compareMailAddressCollectionsBool = true;
            StringBuilder compareMailAddressCollectionsStringBuilder = new StringBuilder();
            foreach (MailAddress sourceMailAddress in sourceMailAddressCollection)
            {
                if (!targetMailAddressCollection.Any(mailAddress => sourceMailAddress.Address == mailAddress.Address))
                {
                    compareMailAddressCollectionsBool = false;
                    string sourceMailAddressMissing = string.Format("Source mail address is not in target mail address collection {0}", sourceMailAddress.Address);
                    log.Debug(sourceMailAddressMissing);
                    compareMailAddressCollectionsStringBuilder.Append(sourceMailAddressMissing + ";");
                }
                else
                {
                    log.DebugFormat("source mail address is in target mail address collection {0}", sourceMailAddress.Address);
                }
            }

            foreach (MailAddress targetMailAddress in targetMailAddressCollection)
            {
                if (!sourceMailAddressCollection.Any(mailAddress => targetMailAddress.Address == mailAddress.Address))
                {
                    compareMailAddressCollectionsBool = false;
                    string targetMailAddressMissing = string.Format("Target mail address is not in source mail address collection {0}", targetMailAddress.Address);
                    log.Debug(targetMailAddressMissing);
                    compareMailAddressCollectionsStringBuilder.Append(targetMailAddressMissing + ";");
                }
                else
                {
                    log.DebugFormat("source mail address is in target mail address collection {0}", targetMailAddress.Address);
                }
            }

            return new CompareResult(compareMailAddressCollectionsBool, compareMailAddressCollectionsStringBuilder.ToString());
        }
    }
}
