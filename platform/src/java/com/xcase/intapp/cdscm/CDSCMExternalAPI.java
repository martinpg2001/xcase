package com.xcase.intapp.cdscm;

import com.xcase.intapp.cdscm.transputs.*;

public interface CDSCMExternalAPI {

    CreateClientResponse createClient(CreateClientRequest createClientRequest);
    
    DeleteClientResponse deleteClient(DeleteClientRequest deleteClientRequest);
    
    GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest);

    PutClientSecurityResponse putClientSecurity(PutClientSecurityRequest putClientSecurityRequest);

    GetClientResponse getClient(GetClientRequest getClientRequest);

    GetClientsModifiedSinceDateResponse getClientsModifiedSinceDate(GetClientsModifiedSinceDateRequest getClientsModifiedSinceDateRequest);

	CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest);

	GetMatterResponse getMatter(GetMatterRequest getMatterRequest);

	PutMatterSecurityResponse putMatterSecurity(PutMatterSecurityRequest putMatterSecurityRequest);

	GetMatterSecurityResponse getMatterSecurity(GetMatterSecurityRequest getMatterSecurityRequest);

	GetMattersModifiedSinceDateResponse getMattersModifiedSinceDate(
			GetMattersModifiedSinceDateRequest getMattersModifiedSinceDateRequest);

	DeleteMatterResponse deleteMatter(DeleteMatterRequest deleteMatterRequest);

}
