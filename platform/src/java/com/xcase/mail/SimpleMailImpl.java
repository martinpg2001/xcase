package com.xcase.mail;

import com.xcase.mail.impl.simple.core.MailConfigurationManager;
import com.xcase.mail.transputs.*;
import com.xcase.mail.impl.simple.methods.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleMailImpl implements MailExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public MailConfigurationManager LocalConfigurationManager = MailConfigurationManager.getConfigurationManager();

    /**
     * Mail action implementation.
     */
    private DeleteEmailMethod deleteEmailMethod = new DeleteEmailMethod();
    
    /**
     * Mail action implementation.
     */
    private GetEmailMethod getEmailMethod = new GetEmailMethod();
    
    @Override
    public DeleteEmailResponse deleteEmail(DeleteEmailRequest deleteEmailRequest) {
        return this.deleteEmailMethod.deleteEmail(deleteEmailRequest);
    }
    
    @Override
    public GetEmailResponse getEmail(GetEmailRequest getEmailRequest) {
        return this.getEmailMethod.getEmail(getEmailRequest);
    }

}
