package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.MailRequest;

public class MailRequestImpl implements MailRequest {
    private boolean debug = false;
    
    private String hostname;
    
    private String mailbox;
    
    private String password;
    
    private String username;
    
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

    @Override
    public boolean getDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;        
    }

}
