namespace XCaseGeneric
{
    using System;
    using System.Net;
    using System.Net.Mail;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to send email.
    /// </summary>
    public class ActionEmail : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method sends an email. The XmlDocument must be of this form:
        /// <operation id="Send an email" class="XCaseGeneric.ActionEmail">
        ///     <message to="pratibha@xcase.com" from="martin@xcase.com" subject="Email sent from Test Tools"  smtpserver="mail.xcase.com" smtpusername="martin@exchangehosting.xcase.com" smtppassword="" smtpssl="true">
        ///     This is the body of the email message.
        ///     </message>
        /// </operation>
        /// Use the ssl attribute to speicfy an SSL connection to the SMTP server.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success sending email message");
                XmlNode messageNode = document.SelectSingleNode("operation/message");
                string smtpServerString = "mail.navisite.com";
                string smtpUsernameString = "username";
                string smtpPasswordString = string.Empty;
                if (messageNode != null)
                {
                    MailMessage mailMessage = new MailMessage();
                    XmlNode toNode = messageNode.SelectSingleNode("@to");
                    if (toNode != null)
                    {
                        log.Debug("toMailAddress is " + toNode.Value);
                        MailAddress toMailAddress = new MailAddress(toNode.Value);
                        mailMessage.To.Add(toMailAddress);
                        log.Debug("toMailAddress is " + toMailAddress);
                    }

                    XmlNode fromNode = messageNode.SelectSingleNode("@from");
                    if (fromNode != null)
                    {
                        log.Debug("fromMailAddress is " + fromNode.Value);
                        MailAddress fromMailAddress = new MailAddress(fromNode.Value);
                        mailMessage.From = fromMailAddress;
                        log.Debug("fromMailAddress is " + fromMailAddress);
                    }

                    XmlNode subjectNode = messageNode.SelectSingleNode("@subject");
                    if (subjectNode != null)
                    {
                        log.Debug("subject is " + Helper.GetStringNodeValue(messageNode, "@subject", string.Empty, false));
                        string subjectString = Helper.GetStringNodeValue(messageNode, "@subject", string.Empty, false);
                        mailMessage.Subject = subjectString;
                        log.Debug("subjectString is " + subjectString);
                    }

                    XmlNode smtpServerNode = messageNode.SelectSingleNode("@smtpserver");
                    if (smtpServerNode != null)
                    {
                        smtpServerString = Helper.GetStringNodeValue(messageNode, "@smtpserver", string.Empty, false);
                        log.Debug("smtpServerString is " + smtpServerString);
                    }

                    mailMessage.Body = messageNode.InnerText;
                    log.Debug("message body is " + mailMessage.Body);
                    SmtpClient smtpClient = new SmtpClient(smtpServerString);
                    XmlNode smtpUsernameNode = messageNode.SelectSingleNode("@smtpusername");
                    if (smtpUsernameNode != null)
                    {
                        smtpUsernameString = Helper.GetStringNodeValue(messageNode, "@smtpusername", string.Empty, false);
                        log.Debug("smtpUsernameString is " + smtpUsernameString);
                    }

                    XmlNode smtpPasswordNode = messageNode.SelectSingleNode("@smtppassword");
                    if (smtpPasswordNode != null)
                    {
                        smtpPasswordString = Helper.GetStringNodeValue(messageNode, "@smtppassword", string.Empty, false);
                        log.Debug("smtpPasswordString is " + smtpPasswordString);
                    }

                    smtpClient.Credentials = new NetworkCredential(smtpUsernameString, smtpPasswordString);
                    smtpClient.EnableSsl = true;
                    XmlNode smtpSSLNode = messageNode.SelectSingleNode("@smtpssl");
                    if (smtpSSLNode != null)
                    {
                        string smtpSSLString = Helper.GetStringNodeValue(messageNode, "@smtpssl", string.Empty, false);
                        log.Debug("smtpSSLString is " + smtpSSLString);
                        bool enableSSL = true;
                        bool result = bool.TryParse(smtpSSLString, out enableSSL);
                        log.Debug("enableSSL is " + enableSSL);
                        smtpClient.EnableSsl = enableSSL;
                    }

                    smtpClient.Send(mailMessage);
                    log.Debug("message sent");
                }
                else
                {
                    log.Info("no message to send");
                }

                log.Debug("finishing Execute()");
                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception sending email message " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
