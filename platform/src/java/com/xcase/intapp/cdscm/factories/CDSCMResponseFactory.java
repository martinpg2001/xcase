package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.*;

public class CDSCMResponseFactory extends BaseCDSCMFactory {

    public static CreateClientResponse createCreateClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateClientResponse");
        return (CreateClientResponse) obj;
    }
    
	public static CreateMatterResponse createCreateMatterResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateMatterResponse");
        return (CreateMatterResponse) obj;
	}

	public static DeleteMatterResponse createDeleteMatterResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteMatterResponse");
        return (DeleteMatterResponse) obj;
	}

    public static DeleteClientResponse createDeleteClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteClientResponse");
        return (DeleteClientResponse) obj;
    }
    
	public static DeleteClientSecurityResponse createDeleteClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteClientSecurityResponse");
        return (DeleteClientSecurityResponse) obj;
	}

	public static DeleteMatterSecurityResponse createDeleteMatterSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteMatterSecurityResponse");
        return (DeleteMatterSecurityResponse) obj;
	}
    
    public static GetClientResponse createGetClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientResponse");
        return (GetClientResponse) obj;
    }
    
    public static GetClientSecurityResponse createGetClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientSecurityResponse");
        return (GetClientSecurityResponse) obj;
    }

    public static PutClientSecurityResponse createPutClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.PutClientSecurityResponse");
        return (PutClientSecurityResponse) obj;
    }

    public static GetClientsModifiedSinceDateResponse createGetClientsModifiedSinceDateResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientsModifiedSinceDateResponse");
        return (GetClientsModifiedSinceDateResponse) obj;
    }

	public static GetMatterResponse createGetMatterResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetMatterResponse");
        return (GetMatterResponse) obj;
	}

	public static GetMatterSecurityResponse createGetMatterSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetMatterSecurityResponse");
        return (GetMatterSecurityResponse) obj;
	}

	public static GetMattersModifiedSinceDateResponse createGetMattersModifiedSinceDateResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetMattersModifiedSinceDateResponse");
        return (GetMattersModifiedSinceDateResponse) obj;
	}

	public static PutMatterSecurityResponse createPutMatterSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.PutMatterSecurityResponse");
        return (PutMatterSecurityResponse) obj;
	}

	public static GetMattersResponse createGetMattersResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetMattersResponse");
        return (GetMattersResponse) obj;
	}

	public static PublishClientsResponse createPublishClientsResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.PublishClientsResponse");
        return (PublishClientsResponse) obj;
	}

	public static PublishMattersResponse createPublishMattersResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.PublishMattersResponse");
        return (PublishMattersResponse) obj;
	}

	public static CheckClientSecurityResponse createCheckClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CheckClientSecurityResponse");
        return (CheckClientSecurityResponse) obj;
	}

	public static CreateMattersUsingPatchResponse createCreateMattersUsingPatchResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateMattersUsingPatchResponse");
        return (CreateMattersUsingPatchResponse) obj;
	}

    public static CreateClientsUsingPatchResponse createCreateClientsUsingPatchResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateClientsUsingPatchResponse");
        return (CreateClientsUsingPatchResponse) obj;
    }

}
