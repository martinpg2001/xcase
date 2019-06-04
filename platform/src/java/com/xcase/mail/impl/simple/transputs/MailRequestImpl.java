package com.xcase.mail.impl.simple.transputs;

import com.xcase.mail.transputs.MailRequest;

public class MailRequestImpl implements MailRequest {
    private boolean debug = false;
    
    private String hostname;
    
    private String mailbox;
    
    private String password;
    
    private String username;
    
    private String smtpHostname;
    
    private String smtpMailbox;
    
    private String smtpPassword;
    
    private String smtpPort;
    
    private String smtpUsername;
    
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

    @Override
    public String getSMTPHostname() {
        return smtpHostname;
    }

    @Override
    public String getSMTPPort() {
        return smtpPort;
    }

    @Override
    public String getSMTPPassword() {
        return smtpPassword;
    }

    @Override
    public String getSMTPMailbox() {
        return smtpMailbox;
    }

    @Override
    public String getSMTPUsername() {
        return smtpUsername;
    }

    @Override
    public void setSMTPHostname(String hostname) {
        this.smtpHostname = hostname;        
    }

    @Override
    public void setSMTPMailbox(String mailbox) {
        this.smtpMailbox = mailbox;          
    }

    @Override
    public void setSMTPPassword(String password) {
        this.smtpPassword = password;         
    }

    @Override
    public void setSMTPPort(String port) {
        this.smtpPort = port;         
    }

    @Override
    public void setSMTPUsername(String username) {
        this.smtpUsername = username; 
        
    }

    @Override
    public boolean isSecure() {
        return true;
    }

}
