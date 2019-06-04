package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.GetEmailRequest;

public class GetEmailRequestImpl extends MailRequestImpl implements GetEmailRequest {
    private String emailSubject = null;
    private String hostname;
    private String mailbox;
    private String password;
    private String username;
    
    @Override
    public String getEmailSubject() {
        return emailSubject;
    }
    
    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public String getUsername() {
        return username;        
    }

    @Override
    public String getPassword() {
        return password;        
    }

    @Override
    public String getMailbox() {
        return mailbox;        
    }
    
    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;        
    }

    @Override
    public void setPassword(String password) {
        this.password = password;        
    }

    @Override
    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;        
    }

}
