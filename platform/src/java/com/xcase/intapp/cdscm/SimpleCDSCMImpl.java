package com.xcase.intapp.cdscm;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.impl.simple.methods.GetClientSecurityMethod;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import com.xcase.integrate.impl.simple.methods.CreateAccessTokenMethod;

public class SimpleCDSCMImpl implements CDSCMExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSCMConfigurationManager localConfigurationManager = CDSCMConfigurationManager.getConfigurationManager();

    /**
     * integrate action implementation.
     */
    private GetClientSecurityMethod getClientSecurityMethod = new GetClientSecurityMethod();

    @Override
    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest) {
        return this.getClientSecurityMethod.getClientSecurity(getClientSecurityRequest);
    }

}
