package com.xcase.mail.impl.simple.transputs;

import javax.mail.Message;

import com.xcase.mail.transputs.DeleteEmailRequest;

public class DeleteEmailRequestImpl extends MailRequestImpl implements DeleteEmailRequest {
    private String emailSubject;
    
    @Override
    public String getEmailSubject() {
        return emailSubject;
    }

    @Override
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

}
