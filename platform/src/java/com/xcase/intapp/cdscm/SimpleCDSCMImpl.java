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
    
    /**
     * method implementation.
     */
    private DeleteClientMethod deleteClientMethod = new DeleteClientMethod();
    
    /**
     * method implementation.
     */
    private GetClientMethod getClientMethod = new GetClientMethod();
    
    /**
     * method implementation.
     */
    private GetClientSecurityMethod getClientSecurityMethod = new GetClientSecurityMethod();
    
    /**
     * method implementation.
     */
    private GetClientsModifiedSinceDateMethod getClientsModifiedSinceDateMethod = new GetClientsModifiedSinceDateMethod();
    
    /**
     * method implementation.
     */
    private PutClientSecurityMethod putClientSecurityMethod = new PutClientSecurityMethod();

    @Override
    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        return this.createClientMethod.createClient(createClientRequest);
    }

    @Override
    public DeleteClientResponse deleteClient(DeleteClientRequest deleteClientRequest) {
        return this.deleteClientMethod.deleteClient(deleteClientRequest);
    }
    
    @Override
    public GetClientResponse getClient(GetClientRequest getClientRequest) {
        return this.getClientMethod.getClient(getClientRequest);
    }

    @Override
    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest) {
        return this.getClientSecurityMethod.getClientSecurity(getClientSecurityRequest);
    }
    
    @Override
    public GetClientsModifiedSinceDateResponse getClientsModifiedSinceDate(GetClientsModifiedSinceDateRequest getClientsModifiedSinceDateRequest) {
        return this.getClientsModifiedSinceDateMethod.getClientsModifiedSinceDate(getClientsModifiedSinceDateRequest);
    }

    @Override
    public PutClientSecurityResponse putClientSecurity(PutClientSecurityRequest putClientSecurityRequest) {
        return this.putClientSecurityMethod.putClientSecurity(putClientSecurityRequest);
    }

}
