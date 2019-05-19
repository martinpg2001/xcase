package com.xcase.intapp.cdscm;

import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.impl.simple.methods.*;
import com.xcase.intapp.cdscm.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private CheckClientSecurityMethod checkClientSecurityMethod = new CheckClientSecurityMethod();
    
    /**
     * method implementation.
     */
    private CreateClientMethod createClientMethod = new CreateClientMethod();
    
    /**
     * method implementation.
     */
    private CreateMatterMethod createMatterMethod = new CreateMatterMethod();
    
    /**
     * method implementation.
     */
    private CreateMattersUsingPatchMethod createMattersUsingPatchMethod = new CreateMattersUsingPatchMethod();
    
    /**
     * method implementation.
     */
    private DeleteClientMethod deleteClientMethod = new DeleteClientMethod();
    
    /**
     * method implementation.
     */
    private DeleteClientSecurityMethod deleteClientSecurityMethod = new DeleteClientSecurityMethod();
    
    /**
     * method implementation.
     */
    private DeleteMatterMethod deleteMatterMethod = new DeleteMatterMethod();
    
    /**
     * method implementation.
     */
    private DeleteMatterSecurityMethod deleteMatterSecurityMethod = new DeleteMatterSecurityMethod();
    
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
    private GetMatterMethod getMatterMethod = new GetMatterMethod();
    
    /**
     * method implementation.
     */
    private GetMattersMethod getMattersMethod = new GetMattersMethod();
    
    /**
     * method implementation.
     */
    private GetMatterSecurityMethod getMatterSecurityMethod = new GetMatterSecurityMethod();
    
    /**
     * method implementation.
     */
    private GetMattersModifiedSinceDateMethod getMattersModifiedSinceDateMethod = new GetMattersModifiedSinceDateMethod();

    /**
     * method implementation.
     */
    private PublishClientsMethod publishClientsMethod = new PublishClientsMethod();
    
    /**
     * method implementation.
     */
    private PublishMattersMethod publishMattersMethod = new PublishMattersMethod();
    
    /**
     * method implementation.
     */
    private PutClientSecurityMethod putClientSecurityMethod = new PutClientSecurityMethod();
    
    /**
     * method implementation.
     */
    private PutMatterSecurityMethod putMatterSecurityMethod = new PutMatterSecurityMethod();

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

	@Override
	public CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest) {
		return this.createMatterMethod.createMatter(createMatterRequest);
	}

	@Override
	public GetMatterResponse getMatter(GetMatterRequest getMatterRequest) {
		return this.getMatterMethod.getMatter(getMatterRequest);
	}

	@Override
	public PutMatterSecurityResponse putMatterSecurity(PutMatterSecurityRequest putMatterSecurityRequest) {
		return this.putMatterSecurityMethod.putMatterSecurity(putMatterSecurityRequest);
	}

	@Override
	public GetMatterSecurityResponse getMatterSecurity(GetMatterSecurityRequest getMatterSecurityRequest) {
		return this.getMatterSecurityMethod.getMatterSecurity(getMatterSecurityRequest);
	}

	@Override
	public GetMattersModifiedSinceDateResponse getMattersModifiedSinceDate(
			GetMattersModifiedSinceDateRequest getMattersModifiedSinceDateRequest) {
		return this.getMattersModifiedSinceDateMethod.getMattersModifiedSinceDate(getMattersModifiedSinceDateRequest);
	}

	@Override
	public DeleteMatterResponse deleteMatter(DeleteMatterRequest deleteMatterRequest) {
		return this.deleteMatterMethod.deleteMatter(deleteMatterRequest);
	}

	@Override
	public DeleteMatterSecurityResponse deleteMatterSecurity(DeleteMatterSecurityRequest deleteMatterSecurityRequest) {
		return this.deleteMatterSecurityMethod.deleteMatterSecurity(deleteMatterSecurityRequest);
	}

	@Override
	public DeleteClientSecurityResponse deleteClientSecurity(DeleteClientSecurityRequest deleteClientSecurityRequest) {
		return this.deleteClientSecurityMethod.deleteClientSecurity(deleteClientSecurityRequest);
	}

	@Override
	public GetMattersResponse getMatters(GetMattersRequest getMattersRequest) {
		return this.getMattersMethod.getMatters(getMattersRequest);
	}

	@Override
	public PublishClientsResponse publishClients(PublishClientsRequest publishClientsRequest) {
		return publishClientsMethod.publishClients(publishClientsRequest);
	}

	@Override
	public PublishMattersResponse publishMatters(PublishMattersRequest publishMattersRequest) {
		return this.publishMattersMethod.publishMatters(publishMattersRequest);
	}

	@Override
	public CheckClientSecurityResponse checkClientSecurity(CheckClientSecurityRequest checkClientSecurityRequest) {
		return this.checkClientSecurityMethod.checkClientSecurity(checkClientSecurityRequest);
	}

	@Override
	public CreateMattersUsingPatchResponse createMattersUsingPatch(
			CreateMattersUsingPatchRequest createMattersUsingPatchRequest) {
		return this.createMattersUsingPatchMethod.createMattersUsingPatch(createMattersUsingPatchRequest);
	}

}
