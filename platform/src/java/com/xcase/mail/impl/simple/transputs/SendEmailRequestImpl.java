package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.SendEmailRequest;

public class SendEmailRequestImpl extends MailRequestImpl implements SendEmailRequest {
    private String emailSubject;
    private String emailText;
    private String emailTo;
    
    @Override
    public String getEmailSubject() {
        return emailSubject;
    }

    @Override
    public String getEmailText() {
        return emailText;
    }

    @Override
    public String getEmailTo() {
        return emailTo;
    }

    @Override
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    @Override
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

}
