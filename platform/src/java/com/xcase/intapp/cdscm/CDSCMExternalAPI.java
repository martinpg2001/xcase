package com.xcase.intapp.cdscm;

import com.xcase.intapp.cdscm.transputs.*;

public interface CDSCMExternalAPI {

    CreateClientResponse createClient(CreateClientRequest createClientRequest);
    
	CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest);
    
    DeleteClientResponse deleteClient(DeleteClientRequest deleteClientRequest);
    
	DeleteClientSecurityResponse deleteClientSecurity(DeleteClientSecurityRequest deleteClientSecurityRequest);
    
	DeleteMatterResponse deleteMatter(DeleteMatterRequest deleteMatterRequest);

	DeleteMatterSecurityResponse deleteMatterSecurity(DeleteMatterSecurityRequest deleteMatterSecurityRequest);
    
    GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest);

    GetClientResponse getClient(GetClientRequest getClientRequest);

    GetClientsModifiedSinceDateResponse getClientsModifiedSinceDate(GetClientsModifiedSinceDateRequest getClientsModifiedSinceDateRequest);

	GetMatterResponse getMatter(GetMatterRequest getMatterRequest);

	GetMatterSecurityResponse getMatterSecurity(GetMatterSecurityRequest getMatterSecurityRequest);

	GetMattersModifiedSinceDateResponse getMattersModifiedSinceDate(
			GetMattersModifiedSinceDateRequest getMattersModifiedSinceDateRequest);

	GetMattersResponse getMatters(GetMattersRequest getMattersRequest);

	PublishClientsResponse publishClients(PublishClientsRequest publishClientsRequest);

	PublishMattersResponse publishMatters(PublishMattersRequest publishMattersRequest);

    PutClientSecurityResponse putClientSecurity(PutClientSecurityRequest putClientSecurityRequest);

	PutMatterSecurityResponse putMatterSecurity(PutMatterSecurityRequest putMatterSecurityRequest);
}
