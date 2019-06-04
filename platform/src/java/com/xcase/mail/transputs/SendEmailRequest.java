package com.xcase.mail.transputs;

public interface SendEmailRequest extends MailRequest {
    String getEmailSubject();
    
    String getEmailText();
    
    String getEmailTo();
    
    void setEmailSubject(String emailSubject);
    
    void setEmailText(String emailText);

    void setEmailTo(String emailTo);

}
