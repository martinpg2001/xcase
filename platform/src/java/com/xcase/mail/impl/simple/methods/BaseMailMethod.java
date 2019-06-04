package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.transputs.MailRequest;
import java.lang.invoke.MethodHandles;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseMailMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    protected Store connectToStore(MailRequest request) throws Exception {
//        LOGGER.debug("starting connectToStore()");
        try {
            String hostname = request.getHostname();
            LOGGER.debug("hostname is " + hostname);
            String username = request.getUsername();
            LOGGER.debug("username is " + username);
            String password = request.getPassword();
            LOGGER.debug("password is " + hostname);
            java.security.Security.setProperty("ssl.SocketFactory.provider", "com.xcase.common.impl.simple.core.DummySSLSocketFactory");
            java.util.Properties props = new java.util.Properties();
            props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.imap.socketFactory.fallback", "false");
            props.setProperty("mail.imap.socketFactory.port", "993");
            Session session = Session.getInstance(props);
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
