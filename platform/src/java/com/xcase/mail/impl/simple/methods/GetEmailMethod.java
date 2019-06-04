package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.constant.MailConstant;
import com.xcase.mail.factories.MailResponseFactory;
import com.xcase.mail.impl.simple.core.MailConfigurationManager;
import com.xcase.mail.transputs.GetEmailRequest;
import com.xcase.mail.transputs.GetEmailResponse;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.transputs.AddGroupMemberResponse;
import java.lang.invoke.MethodHandles;
import javax.mail.*;
import javax.mail.search.SubjectTerm;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEmailMethod extends MailMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetEmailResponse getEmail(GetEmailRequest request) {
        LOGGER.debug("starting getEmail()");
        GetEmailResponse response = MailResponseFactory.createGetEmailResponse();
        LOGGER.debug("created response");
        try {
            String hostname = request.getHostname();
            LOGGER.debug("hostname is " + hostname);
            String username = request.getUsername();
            LOGGER.debug("username is " + username);
            String password = request.getPassword();
            LOGGER.debug("password is " + hostname);
            String mailbox = request.getMailbox();
            LOGGER.debug("mailbox is " + mailbox);
            java.security.Security.setProperty("ssl.SocketFactory.provider",
                    "com.xcase.common.impl.simple.core.DummySSLSocketFactory");
            java.util.Properties props = new java.util.Properties();
            props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.imap.socketFactory.fallback", "false");
            props.setProperty("mail.imap.socketFactory.port", "993");
            Session session = Session.getInstance(props);
            session.setDebug(true);
            Store store = session.getStore("imap");
            store.connect(hostname, username, password);
            Folder folder = store.getFolder(mailbox);
            folder.open(javax.mail.Folder.READ_WRITE);
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
