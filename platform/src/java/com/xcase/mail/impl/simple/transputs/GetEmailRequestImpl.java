package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.GetEmailRequest;

public class GetEmailRequestImpl extends MailRequestImpl implements GetEmailRequest {
    private String emailSubject = null;
    
    @Override
    public String getEmailSubject() {
        return emailSubject;
    }

    @Override
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
        
    }
    

}
