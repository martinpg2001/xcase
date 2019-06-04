package com.xcase.mail;

import com.xcase.mail.constant.MailConstant;
import com.xcase.mail.factories.MailRequestFactory;
import com.xcase.mail.impl.simple.core.MailConfigurationManager;
import com.xcase.mail.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.mail.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
            MailExternalAPI mailExternalAPI = new SimpleMailImpl();
            Properties localMailProperties = MailConfigurationManager.getConfigurationManager().getLocalConfig();
            /* Send email message */
            SendEmailRequest sendEmailRequest = MailRequestFactory.createSendEmailRequest();
            populateSMTPMailRequest(sendEmailRequest, localMailProperties);
            sendEmailRequest.setEmailSubject("Test Subject");
            sendEmailRequest.setEmailText("This is a test.");
            sendEmailRequest.setEmailTo(localMailProperties.getProperty(MailConstant.LOCAL_MAIL_USERNAME));
            SendEmailResponse sendMailResponse = mailExternalAPI.sendEmail(sendEmailRequest);            
            /* Sent email message */
            /* Get email messages */
            GetEmailRequest getEmailRequest = MailRequestFactory.createGetEmailRequest();
            populateMailRequest(getEmailRequest, localMailProperties);
            getEmailRequest.setEmailSubject(null);
            GetEmailResponse getMailResponse = mailExternalAPI.getEmail(getEmailRequest);
            Message[] messageArray = getMailResponse.getMessages();
            if (messageArray != null) {
                LOGGER.debug("messageArray has length " + messageArray.length);
                for (Message message : messageArray) {
                    if (message.getSubject() != null) {
                        LOGGER.debug("message subject is " + message.getSubject());
                    } else {
                        LOGGER.debug("message subject is null");
                    }
                }
            } else {
                LOGGER.debug("messageArray is null");
            }
            
            /* Got email messages */
            /* Delete email messages */
            DeleteEmailRequest deleteEmailRequest = MailRequestFactory.createDeleteEmailRequest();
            populateMailRequest(deleteEmailRequest, localMailProperties);
            String emailSubject = null;
            deleteEmailRequest.setEmailSubject(emailSubject);
            DeleteEmailResponse deleteMailResponse = mailExternalAPI.deleteEmail(deleteEmailRequest);
            /* Deleted email messages */
        } catch (Exception e) {
            LOGGER.warn("exception processing email: " + e.getMessage());
        }
    }

    private static void populateSMTPMailRequest(SendEmailRequest sendEmailRequest, Properties localConfig) {
        sendEmailRequest.setSMTPHostname(localConfig.getProperty(MailConstant.LOCAL_MAIL_SMTP_HOSTNAME));
        sendEmailRequest.setSMTPMailbox(localConfig.getProperty(MailConstant.LOCAL_MAIL_SMTP_MAILBOX));
        sendEmailRequest.setSMTPPassword(localConfig.getProperty(MailConstant.LOCAL_MAIL_SMTP_PASSWORD));
        sendEmailRequest.setSMTPPort(localConfig.getProperty(MailConstant.LOCAL_MAIL_SMTP_PORT));
        sendEmailRequest.setSMTPUsername(localConfig.getProperty(MailConstant.LOCAL_MAIL_SMTP_USERNAME));
    }

    private static void populateMailRequest(MailRequest mailRequest, Properties localConfig) {
        mailRequest.setHostname(localConfig.getProperty(MailConstant.LOCAL_MAIL_HOSTNAME));
        mailRequest.setUsername(localConfig.getProperty(MailConstant.LOCAL_MAIL_USERNAME));
        mailRequest.setPassword(localConfig.getProperty(MailConstant.LOCAL_MAIL_PASSWORD));
        mailRequest.setMailbox(localConfig.getProperty(MailConstant.LOCAL_MAIL_MAILBOX));
    }
}
