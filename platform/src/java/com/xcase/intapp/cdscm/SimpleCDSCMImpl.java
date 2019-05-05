package com.xcase.intapp.cdscm;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.impl.simple.methods.*;
import com.xcase.intapp.cdscm.transputs.*;

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
     * method implementation.
     */
    private CreateClientMethod createClientMethod = new CreateClientMethod();

    @Override
    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        return this.createClientMethod.createClient(createClientRequest);
    }
    
    /**
     * method implementation.
     */
    private DeleteClientMethod deleteClientMethod = new DeleteClientMethod();

    @Override
    public DeleteClientResponse deleteClient(DeleteClientRequest deleteClientRequest) {
        return this.deleteClientMethod.deleteClient(deleteClientRequest);
    }
    
    /**
     * method implementation.
     */
    private GetClientSecurityMethod getClientSecurityMethod = new GetClientSecurityMethod();

    @Override
    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest) {
        return this.getClientSecurityMethod.getClientSecurity(getClientSecurityRequest);
    }
    
    /**
     * method implementation.
     */
    private PutClientSecurityMethod putClientSecurityMethod = new PutClientSecurityMethod();

    @Override
    public PutClientSecurityResponse putClientSecurity(PutClientSecurityRequest putClientSecurityRequest) {
        return this.putClientSecurityMethod.putClientSecurity(putClientSecurityRequest);
    }

}
