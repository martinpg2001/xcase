package com.xcase.mail.impl.simple.methods;

import com.xcase.mail.factories.MailResponseFactory;
import com.xcase.mail.transputs.DeleteEmailRequest;
import com.xcase.mail.transputs.DeleteEmailResponse;
import java.lang.invoke.MethodHandles;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteEmailMethod extends BaseMailMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteEmailResponse deleteEmail(DeleteEmailRequest request) {
        LOGGER.debug("starting deleteEmail()");
        DeleteEmailResponse response = MailResponseFactory.createDeleteEmailResponse();
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
            
            if (messageArray != null) {
                LOGGER.debug("messageArray length is " + messageArray.length);
                for (Message message : messageArray) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                
                folder.expunge();
                LOGGER.debug("expunged folder");
            } else {
                LOGGER.debug("messageArray is null");
            }

            folder.close(false);
            LOGGER.debug("closed folder");
            store.close();
            LOGGER.debug("closed store");
        } catch (Exception e) {
            LOGGER.warn("exception deleting email: " + e.getMessage());
        }

        return response;
    }

}
