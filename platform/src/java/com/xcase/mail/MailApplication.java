package com.xcase.mail;

import com.xcase.mail.constant.MailConstant;
import com.xcase.mail.factories.MailRequestFactory;
import com.xcase.mail.impl.simple.core.MailConfigurationManager;
import com.xcase.mail.transputs.*;
import java.lang.invoke.MethodHandles;

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
            /* Get email messages */
            GetEmailRequest getEmailRequest = MailRequestFactory.createGetEmailRequest();
            getEmailRequest.setHostname(MailConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MailConstant.LOCAL_MAIL_HOSTNAME));
            getEmailRequest.setUsername(MailConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MailConstant.LOCAL_MAIL_USERNAME));
            getEmailRequest.setPassword(MailConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MailConstant.LOCAL_MAIL_PASSWORD));
            getEmailRequest.setMailbox(MailConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MailConstant.LOCAL_MAIL_MAILBOX));
            GetEmailResponse getMailResponse = mailExternalAPI.getEmail(getEmailRequest);
            Message[] messageArray = getMailResponse.getMessages();
            if (messageArray != null) {
                LOGGER.debug("messageArray has length " + messageArray.length);
                /* TODO: work out why email subjects cause NPE
                for (Message message : messageArray) {
                    if (message.getSubject() != null) {
                        LOGGER.debug("message subject is " + message.getSubject());
                    } else {
                        LOGGER.debug("message subject is null");
                    }
                }
                */
            } else {
                LOGGER.debug("messageArray is null");
            }
            
            /* Got email messages */
        } catch (Exception e) {
            LOGGER.warn("exception getting email: " + e.getMessage());
        }
    }
}
