package com.xcase.klearnow.factories;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.klearnow.transputs.*;

public class KlearNowResponseFactory extends BaseKlearNowFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * create response object.
     *
     * @return response object
     */    
	public static AddSupplierTeamMemberResponse createAddSupplierTeamMemberResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.AddSupplierTeamMemberResponse");
        return (AddSupplierTeamMemberResponse) obj;
	}
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static CreateActorResponse createCreateActorResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateActorResponse");
        return (CreateActorResponse) obj;
	}
	
    /**
     * create response object.
     *
     * @return response object
     */
	public static CreateContainerResponse createCreateContainerResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateContainerResponse");
        return (CreateContainerResponse) obj;
	}
	
    /**
     * create response object.
     *
     * @return response object
     */
	public static CreateMerchandiseLineItemResponse createCreateMerchandiseLineItemResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateMerchandiseLineItemResponse");
        return (CreateMerchandiseLineItemResponse) obj;
	}

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateShipmentResponse createCreateShipmentResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateShipmentResponse");
        return (CreateShipmentResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static CreateSupplierAdminResponse createCreateSupplierAdminResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateSupplierAdminResponse");
        return (CreateSupplierAdminResponse) obj;
	}

    /**
     * create response object.
     *
     * @return response object
     */
	public static DeleteActorResponse createDeleteActorResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.DeleteActorResponse");
        return (DeleteActorResponse) obj;
	}
	
    /**
     * create response object.
     *
     * @return response object
     */
	public static DeleteContainerResponse createDeleteContainerResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.DeleteContainerResponse");
        return (DeleteContainerResponse) obj;
	}

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static GetActorResponse createGetActorResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetActorResponse");
        return (GetActorResponse) obj;
	}
	
    /**
     * create response object.
     *
     * @return response object
     */
	public static GetContainerResponse createGetContainerResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetContainerResponse");
        return (GetContainerResponse) obj;
	}
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static GetShipmentResponse createGetShipmentResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetShipmentResponse");
        return (GetShipmentResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static GetShipmentStatusResponse createGetShipmentStatusResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetShipmentStatusResponse");
        return (GetShipmentStatusResponse) obj;
	}
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static GetSupplierOnboardingStatusResponse createGetSupplierOnboardingStatusResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetSupplierOnboardingStatusResponse");
        return (GetSupplierOnboardingStatusResponse) obj;
	}
	
    /**
     * create response object.
     *
     * @return response object
     */
	public static SearchShipmentsResponse createSearchShipmentsResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.SearchShipmentsResponse");
        return (SearchShipmentsResponse) obj;
	}
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static SendMessageResponse createSendMessageResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.SendMessageResponse");
        return (SendMessageResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
	public static UpdateContainerResponse createUpdateContainerResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.UpdateContainerResponse");
        return (UpdateContainerResponse) obj;
	}

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateShipmentResponse createUpdateShipmentResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.UpdateShipmentResponse");
        return (UpdateShipmentResponse) obj;
    }

}
