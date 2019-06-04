package com.xcase.mail.factories;

import com.xcase.mail.transputs.*;

public class MailResponseFactory extends BaseMailFactory {
    /**
     * create response object.
     *
     * @return response object
     */
    public static GetEmailResponse createGetEmailResponse() {
        Object obj = newInstanceOf("mail.config.responsefactory.GetEmailResponse");
        return (GetEmailResponse) obj;
    }
}
