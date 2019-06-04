package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.GetEmailResponse;

import javax.mail.Message;

public class GetEmailResponseImpl extends MailResponseImpl implements GetEmailResponse {
    private Message[]  messageArray;
    
    @Override
    public Message[] getMessages() {
        return messageArray;
    }

    @Override
    public void setMessages(Message[] messageArray) {
        this.messageArray = messageArray;
    }

}
