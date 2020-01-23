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
	public static CreateActorResponse createCreateActorResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.CreateActorResponse");
        return (CreateActorResponse) obj;
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
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
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
    public static SendMessageResponse createSendMessageResponse() {
        Object obj = newInstanceOf("klearnow.config.responsefactory.SendMessageResponse");
        return (SendMessageResponse) obj;
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
