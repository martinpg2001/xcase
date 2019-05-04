package com.xcase.intapp.cdsusers;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import com.xcase.intapp.cdsusers.impl.simple.methods.GetClientSecurityMethod;
import com.xcase.intapp.cdsusers.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdsusers.transputs.GetClientSecurityResponse;
import com.xcase.integrate.impl.simple.methods.CreateAccessTokenMethod;

public class SimpleCDSUsersImpl implements CDSUsersExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSUsersConfigurationManager localConfigurationManager = CDSUsersConfigurationManager.getConfigurationManager();

    /**
     * integrate action implementation.
     */
    private GetClientSecurityMethod getClientSecurityMethod = new GetClientSecurityMethod();
    
    @Override
    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest) {
        return this.getClientSecurityMethod.getClientSecurity(getClientSecurityRequest);
    }

}
