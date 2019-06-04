package com.xcase.mail.transputs;

import javax.mail.Message;

public interface DeleteEmailRequest extends MailRequest {
    String getEmailSubject();

    void setEmailSubject(String emailSubject);

}
