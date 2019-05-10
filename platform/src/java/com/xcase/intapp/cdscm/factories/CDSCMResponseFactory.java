package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.CreateClientResponse;
import com.xcase.intapp.cdscm.transputs.CreateMatterResponse;
import com.xcase.intapp.cdscm.transputs.DeleteClientResponse;
import com.xcase.intapp.cdscm.transputs.DeleteClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.DeleteMatterResponse;
import com.xcase.intapp.cdscm.transputs.DeleteMatterSecurityResponse;
import com.xcase.intapp.cdscm.transputs.GetClientResponse;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.GetClientsModifiedSinceDateResponse;
import com.xcase.intapp.cdscm.transputs.GetMatterResponse;
import com.xcase.intapp.cdscm.transputs.GetMatterSecurityResponse;
import com.xcase.intapp.cdscm.transputs.GetMattersModifiedSinceDateResponse;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.PutMatterSecurityResponse;

public class CDSCMResponseFactory extends BaseCDSCMFactory {

    public static CreateClientResponse createCreateClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateClientResponse");
        return (CreateClientResponse) obj;
    }

    public static DeleteClientResponse createDeleteClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteClientResponse");
        return (DeleteClientResponse) obj;
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

	public static CreateMatterResponse createCreateMatterResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateMatterResponse");
        return (CreateMatterResponse) obj;
	}

	public static DeleteMatterResponse createDeleteMatterResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteMatterResponse");
        return (DeleteMatterResponse) obj;
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

	public static DeleteClientSecurityResponse createDeleteClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteClientSecurityResponse");
        return (DeleteClientSecurityResponse) obj;
	}

	public static DeleteMatterSecurityResponse createDeleteMatterSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteMatterSecurityResponse");
        return (DeleteMatterSecurityResponse) obj;
	}

}
