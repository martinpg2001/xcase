package com.xcase.mail.factories;

import com.xcase.mail.transputs.*;

public class MailRequestFactory extends BaseMailFactory {
    public static DeleteEmailRequest createDeleteEmailRequest() {
        Object obj = newInstanceOf("mail.config.requestfactory.DeleteEmailRequest");
        return (DeleteEmailRequest) obj;
    }
    
    public static GetEmailRequest createGetEmailRequest() {
        Object obj = newInstanceOf("mail.config.requestfactory.GetEmailRequest");
        return (GetEmailRequest) obj;
    }

    public static SendEmailRequest createSendEmailRequest() {
        Object obj = newInstanceOf("mail.config.requestfactory.SendEmailRequest");
        return (SendEmailRequest) obj;
    }

}
