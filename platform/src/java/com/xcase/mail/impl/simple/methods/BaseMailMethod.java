package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.transputs.MailRequest;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseMailMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    protected Session connectToSMTPSession(MailRequest request) throws Exception {
         LOGGER.debug("starting connectToSMTPSession()");
        try {
            String hostname = request.getSMTPHostname();
            LOGGER.debug("hostname is " + hostname);
            String port = request.getSMTPPort();
            LOGGER.debug("port is " + port);
            final String username = request.getSMTPUsername();
            LOGGER.debug("username is " + username);
            final String password = request.getSMTPPassword();
            LOGGER.debug("password is " + password);
            java.security.Security.setProperty("ssl.SocketFactory.provider", "com.xcase.common.impl.simple.core.DummySSLSocketFactory");
            Properties smtpProperties = new Properties();
            smtpProperties.setProperty("mail.transport.protocol", "smtp");
            smtpProperties.setProperty("mail.smtp.host", hostname);
            LOGGER.debug("set mail.smtp.host to " + hostname);
            smtpProperties.setProperty("mail.smtp.port", port);
            LOGGER.debug("set mail.smtp.port to " + port);
            smtpProperties.setProperty("mail.smtp.submitter", username);
            LOGGER.debug("set mail.smtp.submitter to " + username);
            if (request.isSecure()) {
                LOGGER.debug("setting secure settings");
                smtpProperties.setProperty("mail.smtp.socketFactory.port", port);
                LOGGER.debug("set mail.smtp.socketFactory.port to " + port);
                smtpProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
            } else {
                LOGGER.debug("do not set secure settings");
            }

            Session session = null;
            if (username != null) {
                LOGGER.debug("username is not null, so setting authenticator");
                smtpProperties.setProperty("mail.smtp.auth", "true");
                session = Session.getInstance(smtpProperties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
            } else {
                session = Session.getInstance(smtpProperties);
            }

            LOGGER.debug("got session instance");
            session.setDebug(request.getDebug());
            return session;
        } catch (Exception e) {
            LOGGER.warn("exception connecting to session: " + e.getMessage());
            throw e;
        }
    }

    protected Store connectToStore(MailRequest request) throws Exception {
        // LOGGER.debug("starting connectToStore()");
        try {
            String hostname = request.getHostname();
            LOGGER.debug("hostname is " + hostname);
            String username = request.getUsername();
            LOGGER.debug("username is " + username);
            String password = request.getPassword();
            LOGGER.debug("password is " + hostname);
            java.security.Security.setProperty("ssl.SocketFactory.provider", "com.xcase.common.impl.simple.core.DummySSLSocketFactory");
            java.util.Properties imapProperties = new java.util.Properties();
            imapProperties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            imapProperties.setProperty("mail.imap.socketFactory.fallback", "false");
            imapProperties.setProperty("mail.imap.socketFactory.port", "993");
            Session session = Session.getInstance(imapProperties);
            session.setDebug(request.getDebug());
            Store store = session.getStore("imap");
            store.connect(hostname, username, password);
            return store;
        } catch (Exception e) {
            LOGGER.warn("exception connecting to store: " + e.getMessage());
            throw e;
        }
    }
}
