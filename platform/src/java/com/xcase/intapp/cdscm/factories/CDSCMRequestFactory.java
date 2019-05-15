package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSCMRequestFactory extends BaseCDSCMFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static CreateClientRequest createCreateClientRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.CreateClientRequest");
        return (CreateClientRequest) obj;
    }

    public static CreateClientRequest createCreateClientRequest(String accessToken) {
        CreateClientRequest request = createCreateClientRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static DeleteClientRequest createDeleteClientRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.DeleteClientRequest");
        return (DeleteClientRequest) obj;
    }

    public static DeleteClientRequest createDeleteClientRequest(String accessToken) {
        DeleteClientRequest request = createDeleteClientRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static GetClientSecurityRequest createGetClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetClientSecurityRequest");
        return (GetClientSecurityRequest) obj;
    }

    public static GetClientSecurityRequest createGetClientSecurityRequest(String accessToken) {
        GetClientSecurityRequest request = createGetClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static PutClientSecurityRequest createPutClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.PutClientSecurityRequest");
        return (PutClientSecurityRequest) obj;
    }

    public static PutClientSecurityRequest createPutClientSecurityRequest(String accessToken) {
        PutClientSecurityRequest request = createPutClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static GetClientRequest createGetClientRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetClientRequest");
        return (GetClientRequest) obj;
    }

    public static GetClientRequest createGetClientRequest(String accessToken) {
        GetClientRequest request = createGetClientRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static GetClientsModifiedSinceDateRequest createGetClientsModifiedSinceDateRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetClientsModifiedSinceDateRequest");
        return (GetClientsModifiedSinceDateRequest) obj;
    }

    public static GetClientsModifiedSinceDateRequest createGetClientsModifiedSinceDateRequest(String accessToken) {
        GetClientsModifiedSinceDateRequest request = createGetClientsModifiedSinceDateRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
	public static CreateMatterRequest createCreateMatterRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.CreateMatterRequest");
        return (CreateMatterRequest) obj;
	}

	public static CreateMatterRequest createCreateMatterRequest(String accessToken) {
		CreateMatterRequest request = createCreateMatterRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static PutMatterSecurityRequest createPutMatterSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.PutMatterSecurityRequest");
        return (PutMatterSecurityRequest) obj;
	}

	public static PutMatterSecurityRequest createPutMatterSecurityRequest(String accessToken) {
		PutMatterSecurityRequest request = createPutMatterSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static GetMatterSecurityRequest createGetMatterSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetMatterSecurityRequest");
        return (GetMatterSecurityRequest) obj;
	}

	public static GetMatterSecurityRequest createGetMatterSecurityRequest(String accessToken) {
		GetMatterSecurityRequest request = createGetMatterSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static GetMattersModifiedSinceDateRequest createGetMattersModifiedSinceDateRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetMattersModifiedSinceDateRequest");
        return (GetMattersModifiedSinceDateRequest) obj;
	}

	public static GetMattersModifiedSinceDateRequest createGetMattersModifiedSinceDateRequest(String accessToken) {
		GetMattersModifiedSinceDateRequest request = createGetMattersModifiedSinceDateRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static DeleteMatterRequest createDeleteMatterRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.DeleteMatterRequest");
        return (DeleteMatterRequest) obj;
	}

	public static DeleteMatterRequest createDeleteMatterRequest(String accessToken) {
		DeleteMatterRequest request = createDeleteMatterRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static GetMatterRequest createGetMatterRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetMatterRequest");
        return (GetMatterRequest) obj;
	}

	public static GetMatterRequest createGetMatterRequest(String accessToken) {
		GetMatterRequest request = createGetMatterRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static DeleteMatterSecurityRequest createDeleteMatterSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.DeleteMatterSecurityRequest");
        return (DeleteMatterSecurityRequest) obj;
	}

	public static DeleteMatterSecurityRequest createDeleteMatterSecurityRequest(String accessToken) {
		DeleteMatterSecurityRequest request = createDeleteMatterSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static DeleteClientSecurityRequest createDeleteClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.DeleteClientSecurityRequest");
        return (DeleteClientSecurityRequest) obj;
	}

	public static DeleteClientSecurityRequest createDeleteClientSecurityRequest(String accessToken) {
		DeleteClientSecurityRequest request = createDeleteClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static GetMattersRequest createGetMattersRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetMattersRequest");
        return (GetMattersRequest) obj;
	}

	public static GetMattersRequest createGetMattersRequest(String accessToken) {
		GetMattersRequest request = createGetMattersRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static PublishClientsRequest createPublishClientsRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.PublishClientsRequest");
        return (PublishClientsRequest) obj;
	}

	public static PublishClientsRequest createPublishClientsRequest(String accessToken) {
		PublishClientsRequest request = createPublishClientsRequest();
        request.setAccessToken(accessToken);
        return request;
	}

}
