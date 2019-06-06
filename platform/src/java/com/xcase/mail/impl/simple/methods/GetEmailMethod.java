package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.constant.MailConstant;
import com.xcase.mail.factories.MailResponseFactory;
import com.xcase.mail.impl.simple.core.MailConfigurationManager;
import com.xcase.mail.transputs.GetEmailRequest;
import com.xcase.mail.transputs.GetEmailResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SubjectTerm;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEmailMethod extends BaseMailMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetEmailResponse getEmail(GetEmailRequest request) {
        LOGGER.debug("starting getEmail()");
        GetEmailResponse response = MailResponseFactory.createGetEmailResponse();
        LOGGER.debug("created response");
        try {
            Store store = connectToStore(request);
            String mailbox = request.getMailbox();
            LOGGER.debug("mailbox is " + mailbox);
            Folder folder = store.getFolder(mailbox);
            folder.open(Folder.READ_WRITE);
            LOGGER.debug("connected to INBOX");
            String emailSubject = request.getEmailSubject();
            LOGGER.debug("emailSubject is " + emailSubject);
            Message[] messageArray = null;
            if (emailSubject != null) {
                LOGGER.debug("about to search folder");
                messageArray = folder.search(new SubjectTerm(emailSubject));
                LOGGER.debug("searched folder");
            } else {
                LOGGER.debug("about to get messages");
                messageArray = folder.getMessages();
                LOGGER.debug("got messages");
            }
            
            /* We are going to close the connection to the server, and so we must clone the messages to 
             * return them to the calling class.
             */
            if (messageArray != null) {
                LOGGER.debug("messageArray has length " + messageArray.length);
                List<Message> messageList = new ArrayList<Message>();
                for (Message message : messageArray) {
                    messageList.add(new MimeMessage( (MimeMessage) message ));
                }
                
                response.setMessages(messageList.toArray(new Message[0]));
            } else {
                LOGGER.debug("messageArray is null");
            }
            
            folder.close(false);
            LOGGER.debug("closed folder");
            store.close();
            LOGGER.debug("closed store");
            response.setMessages(messageArray);
        } catch (Exception e) {
            LOGGER.warn("exception getting email: " + e.getMessage());
        }

        return response;
    }
}
