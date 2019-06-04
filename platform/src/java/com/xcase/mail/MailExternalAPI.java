package com.xcase.mail;

import com.xcase.mail.transputs.GetEmailRequest;
import com.xcase.mail.transputs.GetEmailResponse;

public interface MailExternalAPI {

    GetEmailResponse getEmail(GetEmailRequest getEmailRequest);

}
