package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.factories.MailResponseFactory;
import com.xcase.mail.transputs.SendEmailRequest;
import com.xcase.mail.transputs.SendEmailResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SubjectTerm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendEmailMethod extends BaseMailMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public SendEmailResponse sendEmail(SendEmailRequest request) {
        LOGGER.debug("starting sendEmail()");
        SendEmailResponse response = MailResponseFactory.createSendEmailResponse();
        LOGGER.debug("created response");
        try {
            Session session = connectToSMTPSession(request);
            LOGGER.debug("created session");
            MimeMessage message = new MimeMessage(session);
            LOGGER.debug("created message");
            String emailFrom = request.getSMTPUsername();
            LOGGER.debug("emailFrom is " + emailFrom);
            message.setFrom(new InternetAddress(emailFrom));
            String emailTo = request.getEmailTo();
            LOGGER.debug("emailTo is " + emailTo);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            String emailSubject = request.getEmailSubject();
            LOGGER.debug("emailSubject is " + emailSubject);            
            message.setSubject(emailSubject);
            String emailText = request.getEmailText();
            LOGGER.debug("emailText is " + emailText); 
            message.setText(emailText);
            LOGGER.debug("about to send message");
            Transport.send(message);
            LOGGER.debug("sent message");
        } catch (Exception e) {
            LOGGER.warn("exception sending email: " + e.getMessage());
        }

        return response;
    }

}
