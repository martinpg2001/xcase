package com.xcase.mail;

import com.xcase.mail.transputs.DeleteEmailRequest;
import com.xcase.mail.transputs.DeleteEmailResponse;
import com.xcase.mail.transputs.GetEmailRequest;
import com.xcase.mail.transputs.GetEmailResponse;
import com.xcase.mail.transputs.SendEmailRequest;
import com.xcase.mail.transputs.SendEmailResponse;

public interface MailExternalAPI {

    GetEmailResponse getEmail(GetEmailRequest getEmailRequest);

    DeleteEmailResponse deleteEmail(DeleteEmailRequest deleteEmailRequest);

    SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest);

}
