package com.xcase.mail.transputs;

public interface GetEmailRequest extends MailRequest {

    void setHostname(String property);

    void setUsername(String property);

    void setPassword(String property);

    void setMailbox(String property);

    String getHostname();

    String getUsername();

    String getPassword();

    String getMailbox();

    String getEmailSubject();

}
