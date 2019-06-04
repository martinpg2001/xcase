package com.xcase.mail.transputs;

public interface GetEmailRequest extends MailRequest {

    String getEmailSubject();

    void setEmailSubject(String emailSubject);
}
