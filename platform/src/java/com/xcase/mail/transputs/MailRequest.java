package com.xcase.mail.transputs;

public interface MailRequest {
    
    boolean getDebug();
    
    String getHostname();

    String getUsername();

    String getPassword();

    String getMailbox();
    
    String getSMTPHostname();
    
    String getSMTPPort();

    String getSMTPPassword();

    String getSMTPMailbox();
    
    String getSMTPUsername();
    
    void setHostname(String hostname);

    void setUsername(String username);

    void setPassword(String password);

    void setMailbox(String mailbox);

    void setDebug(boolean debug);
    
    void setSMTPHostname(String hostname);
    
    void setSMTPMailbox(String mailbox);
    
    void setSMTPPassword(String password);
    
    void setSMTPPort(String port);

    void setSMTPUsername(String username);

    boolean isSecure();

}
