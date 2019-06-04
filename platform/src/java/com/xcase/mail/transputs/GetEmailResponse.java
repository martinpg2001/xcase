package com.xcase.mail.transputs;

import javax.mail.Message;

public interface GetEmailResponse extends MailResponse {
    Message[] getMessages();
    
    void setMessages(Message[] messageArray);

}
