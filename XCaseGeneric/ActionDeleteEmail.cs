namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Net;
    using System.Xml;
    using Microsoft.Exchange.WebServices.Autodiscover;
    using Microsoft.Exchange.WebServices.Data;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to delete email.
    /// </summary>
    public class ActionDeleteEmail : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method deletes email. The XmlDocument must be of this form:
        /// <operation id="Check an email" class="XCaseGeneric.ActionDeleteEmail">
        ///     <exchange url="http://balrog/EWS/Exchange.asmx" username="IAINTERNAL\Administrator" password="tsunami" />
        ///     <email mailbox="mdev@xcase.com" subject="Email sent from Test Tools" />
        /// </operation>
        /// Use the ssl attribute to specify an SSL connection to the Exchange server.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                XmlNode operationNode = document.SelectSingleNode("operation");
                XmlNode exchangeNode = operationNode.SelectSingleNode("exchange");
                if (exchangeNode != null)
                {
                    string exchangeURL = Helper.GetStringNodeValue(exchangeNode, "@url", string.Empty, true);
                    log.DebugFormat("exchangeURL is {0}", exchangeURL);
                    string exchangeUsername = Helper.GetStringNodeValue(exchangeNode, "@username", string.Empty, false);
                    log.DebugFormat("exchangeUsername is {0}", exchangeUsername);
                    string exchangeDomain = Helper.GetStringNodeValue(exchangeNode, "@domain", string.Empty, false);
                    log.DebugFormat("exchangeDomain is {0}", exchangeDomain);
                    string exchangePassword = Helper.GetStringNodeValue(exchangeNode, "@password", string.Empty, false);
                    log.DebugFormat("exchangePassword is {0}", exchangePassword);
                    XmlNode emailNode = operationNode.SelectSingleNode("email");
                    if (emailNode != null)
                    {
                        string emailMailbox = Helper.GetStringNodeValue(emailNode, "@mailbox", string.Empty, false);
                        log.DebugFormat("emailMailbox is {0}", emailMailbox);
                        string emailSubject = Helper.GetStringNodeValue(emailNode, "@subject", string.Empty, false);
                        log.DebugFormat("emailSubject is {0}", emailSubject);
                        int pageSize = Helper.GetIntNodeValue(emailNode, "@pagesize", 1000, false);
                        log.DebugFormat("pageSize is {0}", pageSize);
                        DeleteMode deleteMode = DeleteMode.MoveToDeletedItems;
                        string deleteModeString = Helper.GetStringNodeValue(emailNode, "@deletemode", "Move", false);
                        if (!deleteModeString.Equals("Move"))
                        {
                            switch (deleteModeString)
                            {
                                case "Hard":
                                    deleteMode = DeleteMode.HardDelete;
                                    break;
                                case "Soft":
                                    deleteMode = DeleteMode.SoftDelete;
                                    break;
                                default:
                                    log.DebugFormat("unrecognized delete mode {0}", deleteModeString);
                                    break;
                            }
                        }

                        ServicePointManager.ServerCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true;
                        ExchangeService exchangeService = this.GetBinding(exchangeURL, exchangeUsername, exchangePassword, exchangeDomain);
                        log.DebugFormat("bound to Exchange Web service {0}", exchangeService.Url);
                        Mailbox mailBox = new Mailbox(emailMailbox);
                        log.DebugFormat("created mail box {0}", emailMailbox);
                        FolderId folderID = new FolderId(WellKnownFolderName.Inbox, mailBox);
                        ItemView itemView = new ItemView(pageSize);
                        log.Debug("created item view");
                        FindItemsResults<Item> findItemsResults = null;
                        if (!string.IsNullOrEmpty(emailSubject))
                        {
                            SearchFilter.IsEqualTo subjectSearchFilter = new SearchFilter.IsEqualTo(EmailMessageSchema.Subject, emailSubject);
                            log.DebugFormat("created subject search filter: {0}", emailSubject);
                            findItemsResults = exchangeService.FindItems(folderID, subjectSearchFilter, itemView);
                        }
                        else
                        {
                            findItemsResults = exchangeService.FindItems(folderID, itemView);
                        }

                        log.DebugFormat("count of found items is {0}", findItemsResults.Count<Item>());
                        if (findItemsResults.Count<Item>() > 0)
                        {
                            List<ItemId> itemIDList = new List<ItemId>();
                            foreach (Item item in findItemsResults)
                            {
                                itemIDList.Add(item.Id);
                            }

                            exchangeService.DeleteItems(itemIDList, deleteMode, SendCancellationsMode.SendToNone, AffectedTaskOccurrence.AllOccurrences);
                            log.Debug("deleted items");
                        }

                        return new OperationResult("Email items deleted");
                    }
                    else
                    {
                        log.Info("no mailbox specified");
                        return new OperationResult("No mailbox specified");
                    }
                }
                else
                {
                    log.Info("no Exchange server specified");
                    return new OperationResult("No Exchange server specified");
                }
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception deleting email messages: " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }

        /// <summary>
        /// This method creates the binding to the default Exchange service.
        /// </summary>
        /// <returns>The ExchangeService object.</returns>
        public ExchangeService GetBinding()
        {
            return this.GetBinding("http://balrog/EWS/Exchange.asmx", "IAINTERNAL\\Administrator", "tsunami", string.Empty);
        }

        /// <summary>
        /// This method creates the binding to the Exchange service.
        /// </summary>
        /// <param name="url">The URL to the Exchange Web service.</param>
        /// <param name="username">The username to connect to the Exchange Web service.</param>
        /// <param name="password">The password to connect to the Exchange Web service.</param>
        /// /// <param name="domain">The domain to connect to the Exchange Web service.</param>
        /// <returns>The ExchangeService object.</returns>
        public ExchangeService GetBinding(string url, string username, string password, string domain)
        {
            /* Create the binding */
            ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
            /* Define credentials */
            exchangeService.Credentials = new WebCredentials(username, password, domain);
            /* Use the AutodiscoverUrl method to locate the service endpoint */
            try
            {
                ////exchangeService.AutodiscoverUrl("mdev@internal.xcase.net", RedirectionUrlValidationCallback);
                exchangeService.Url = new Uri(url);
            }
            catch (AutodiscoverRemoteException are)
            {
                Console.WriteLine("Exception thrown: " + are.Error.Message);
            }

            return exchangeService;
        }

        /// <summary>
        /// This method is used to validate the Exchange URL.
        /// </summary>
        /// <param name="redirectionUrl">The redirectionUrl parameter.</param>
        /// <returns>This currently returns true.</returns>
        public bool RedirectionUrlValidationCallback(string redirectionUrl)
        {
            // Perform validation.
            // Validation is developer dependent to ensure a safe redirect.
            return true;
        }
    }
}
