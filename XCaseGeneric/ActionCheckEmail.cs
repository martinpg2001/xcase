namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Net;
    using System.Text.RegularExpressions;
    using System.Xml;
    using Microsoft.Exchange.WebServices.Autodiscover;
    using Microsoft.Exchange.WebServices.Data;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to check email.
    /// </summary>
    public class ActionCheckEmail : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method checks for an email. The XmlDocument must be of this form:
        /// <operation id="Check an email" class="XCaseGeneric.ActionCheckEmail">
        ///     <exchange url="http://server/EWS/Exchange.asmx" username="DOMAIN\user" password="" />
        ///     <email mailbox="username@domain.com" subject="Email sent from Test Tools" />
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
                        if (emailNode.SelectSingleNode("@mailbox") != null)
                        {
                            emailMailbox = Helper.GetStringNodeValue(emailNode, "@mailbox", string.Empty, false);
                            log.DebugFormat("emailMailbox is {0}", emailMailbox);
                        }

                        if (emailNode.SelectSingleNode("@subject") != null)
                        {
                            emailSubject = Helper.GetStringNodeValue(emailNode, "@subject", string.Empty, false);
                            log.DebugFormat("emailSubject is {0}", emailSubject);
                        }
                    }

                    ServicePointManager.ServerCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true;
                    ExchangeService exchangeService = this.GetBinding(exchangeURL, exchangeUsername, exchangePassword, exchangeDomain);
                    log.DebugFormat("bound to Exchange Web service {0}", exchangeService.Url);
                    Mailbox mailBox = new Mailbox(emailMailbox);
                    log.Debug("created mail box " + emailMailbox);
                    FolderId folderID = new FolderId(WellKnownFolderName.Inbox, mailBox);
                    SearchFilter.IsEqualTo subjectSearchFilter = new SearchFilter.IsEqualTo(EmailMessageSchema.Subject, emailSubject);
                    log.DebugFormat("created subject search filter: {0}", emailSubject);
                    ItemView itemView = new ItemView(5);
                    log.Debug("created item view");
                    itemView.OrderBy.Add(EmailMessageSchema.DateTimeSent, SortDirection.Descending);
                    /* This should get the most recent email that matches the subject line */
                    FindItemsResults<Item> findResults = exchangeService.FindItems(folderID, subjectSearchFilter, itemView);
                    log.DebugFormat("count of found items is {0}", findResults.Count<Item>());
                    if (findResults.Count<Item>() == 0)
                    {
                        /* If email expected but no ones found return false */
                        if (document.SelectSingleNode("operation/expected/email") != null)
                        {
                            string noEmailMessagesFoundMessage = string.Format("No email messages found matching subject: {0}", emailSubject);
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
                        log.Debug("at least one email found");
                        exchangeService.LoadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
                        log.Debug("loaded properties for items");
                        string actualValue = string.Empty;
                        XmlNode expectedEmailNode = document.SelectSingleNode("operation/expected/email");
                        if (expectedEmailNode != null)
                        {
                            log.DebugFormat("expected email node is not null");
                            expectedEmailBody = Helper.GetStringNodeValue(document, "operation/expected/email", string.Empty, false);
                            int endInitial = expectedEmailBody.Length;
                            int startFinal = expectedEmailBody.Length;
                            if (expectedEmailNode.SelectSingleNode("@endinitial") != null)
                            {
                                endInitial = Helper.GetIntNodeValue(expectedEmailNode, "@endinitial", -1, false);
                                log.DebugFormat("endInitial is {0}", endInitial);
                            }

                            if (expectedEmailNode.SelectSingleNode("@startfinal") != null)
                            {
                                startFinal = Helper.GetIntNodeValue(expectedEmailNode, "@startfinal", -1, false);
                                log.DebugFormat("startFinal is {0}", startFinal);
                            }
                        }
                        else
                        {
                            log.Debug("expected email node is null");
                            /* Empty email node means that we are not expecting to see email messages */
                            string emailBodyMessage = "At least 1 email found but expected email node is null";
                            return new OperationResult(false, emailBodyMessage);
                        }

                        foreach (Item item in findResults.Items)
                        {
                            log.DebugFormat("item subject is {0}", item.Subject);
                            log.DebugFormat("item body is {0}", item.Body);
                            actualEmailBody = item.Body;
                            if (actualEmailBody.Contains("</style>"))
                            {
                                log.DebugFormat("email is formatted as HTML");
                                actualEmailBody = actualEmailBody.Substring(actualEmailBody.IndexOf("</style>") + 8);
                                /* Remove all markup chars */
                                actualEmailBody = Regex.Replace(actualEmailBody, @"</head>|<body>|</body>|</html>", string.Empty).Replace(Environment.NewLine, string.Empty);
                            }

                            log.DebugFormat("replaced actual email body is {0}", actualEmailBody);
                            /* Now check specific values */
                            XmlNodeList checkValuesNodeList = document.SelectNodes("operation/expected/checkvalue");
                            foreach (XmlNode checkValueNode in checkValuesNodeList)
                            {
                                log.DebugFormat("next check value");
                                string regexString = Helper.GetStringNodeValue(checkValueNode, "@regex", string.Empty, false);
                                log.DebugFormat("regexString is {0}", regexString);
                                Regex regex = new Regex(regexString);
                                if (regex.IsMatch(actualEmailBody))
                                {
                                    log.DebugFormat("regex got a match");
                                    actualValue = Regex.Replace(actualEmailBody.Substring(actualEmailBody.IndexOf("aid")), @"[\D]", string.Empty);
                                    log.DebugFormat("actualValue is {0}", actualValue);
                                    if (checkValueNode.SelectSingleNode("@value") != null)
                                    {
                                        log.DebugFormat("value attribute is not null");
                                        string expectedvalue = Helper.GetStringNodeValue(checkValueNode, "@value", string.Empty, false);
                                        if (!expectedvalue.Equals(actualValue))
                                        {
                                            string valueMessage = string.Format("Actual check value {0} does not match expected check value {1}", actualValue, expectedvalue);
                                            log.Warn(valueMessage);
                                            return new OperationResult(false, valueMessage);
                                        }
                                        else
                                        {
                                            string emailBodyMessage = "Success checking email message";
                                            return new OperationResult(true, emailBodyMessage);
                                        }
                                    }

                                    if (checkValueNode.SelectSingleNode("@id") != null)
                                    {
                                        log.DebugFormat("id attribute is not null");
                                        string id = Helper.GetStringNodeValue(checkValueNode, "@id", string.Empty, false);
                                        log.DebugFormat("id is {0}", id);
                                        Dictionary<string, object> helperDictionary = Helper.GetHelperDictionary();
                                        if (!helperDictionary.ContainsKey("acknowledgmentid" + actualValue))
                                        {
                                            helperDictionary.Add("acknowledgmentid" + actualValue, actualValue);
                                        }

                                        log.DebugFormat("saved {0} to key {1}", actualValue, id);
                                    }
                                }
                            }

                            if (expectedEmailBody != string.Empty)
                            {
                                log.DebugFormat("expected email body is not empty");
                                /* Now replace aid in the expected link */
                                if (actualValue != string.Empty)
                                {
                                    /* We use the string 20 as a placeholder for a dynamically-generated aid */
                                    expectedEmailBody = Regex.Replace(expectedEmailBody, @"20", actualValue);
                                }

                                log.DebugFormat("replaced expected email body is {0}", expectedEmailBody);
                                if (this.IsEmailsBodyEquals(expectedEmailBody, actualEmailBody))
                                {
                                    string emailBodyMessage = "Success checking email message";
                                    return new OperationResult(true, emailBodyMessage);
                                }
                                else
                                {
                                    log.Info("actual email body does not match expected email body");
                                    int stringCompare = string.Compare(actualEmailBody, expectedEmailBody, false);
                                    log.InfoFormat("string comparison value is {0}", stringCompare);
                                    if (actualValue != string.Empty)
                                    {
                                        /* Now replace the value with 20 again for the next loop iteration.
                                         * I think that this code is pretty broken, but do not have time to fix this.
                                         */
                                        expectedEmailBody = Regex.Replace(expectedEmailBody, actualValue, @"20");
                                    }
                                }
                            }
                           else
                            {
                                log.DebugFormat("expected email body is empty");
                            }
                        }

                        if (expectedEmailNode != null)
                        {
                            log.DebugFormat("none of the found emails match the expected");
                            return new OperationResult(false, "None of the found emails match the expected");
                        }
                        else
                        {
                            string emailBodyMessage = "Success checking email message";
                            return new OperationResult(true, emailBodyMessage);
                        }
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

        /// <summary>
        /// Equals body of emails, replaces $variables in actualEmailBody to variables from Helper.GetHelperDictionary
        /// </summary>
        /// <param name="expectedEmailBodyString">Expected email body string</param>
        /// <param name="actualEmailBodyString">Actual email body string</param>
        /// <returns>True, if strings are equals, include variables; Otherwise False</returns>
        public bool IsEmailsBodyEquals(string expectedEmailBodyString, string actualEmailBodyString)
        {
            Dictionary<string, object> dictionary = Helper.GetHelperDictionary();
            foreach (KeyValuePair<string, object> pair in dictionary)
            {
                if (expectedEmailBodyString.Contains('$' + pair.Key))
                {
                    expectedEmailBodyString = expectedEmailBodyString.Replace('$' + pair.Key, pair.Value.ToString());
                }
            }

            if (string.Equals(
                expectedEmailBodyString.Replace(" ", string.Empty),
                actualEmailBodyString.Replace(" ", string.Empty),
                StringComparison.InvariantCultureIgnoreCase))
            {
                return true;
            }

            return false;
        }
    }
}
