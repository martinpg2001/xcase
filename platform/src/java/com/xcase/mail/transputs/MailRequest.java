package com.xcase.mail.transputs;

public interface MailRequest {
    
    boolean getDebug();
    
    String getHostname();

    String getUsername();

    String getPassword();

    String getMailbox();
    
    void setHostname(String property);

    void setUsername(String property);

    void setPassword(String property);

    void setMailbox(String property);

    void setDebug(boolean debug);
}
