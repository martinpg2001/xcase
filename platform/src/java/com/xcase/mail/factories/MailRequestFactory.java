package com.xcase.mail.factories;

import com.xcase.mail.transputs.GetEmailRequest;
import com.xcase.mail.transputs.GetEmailResponse;

public class MailRequestFactory extends BaseMailFactory {

    public static GetEmailRequest createGetEmailRequest() {
        Object obj = newInstanceOf("mail.config.requestfactory.GetEmailRequest");
        return (GetEmailRequest) obj;
    }

}
