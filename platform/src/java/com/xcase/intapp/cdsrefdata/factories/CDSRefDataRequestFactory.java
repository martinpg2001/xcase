package com.xcase.intapp.cdsrefdata.factories;

import com.xcase.intapp.cdsrefdata.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSRefDataRequestFactory extends BaseCDSRefDataFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static GetClientStatusesRequest createGetClientStatusesRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.GetClientStatusesRequest");
        return (GetClientStatusesRequest) obj;
    }

    public static GetClientStatusesRequest createGetClientStatusesRequest(String accessToken) {
        GetClientStatusesRequest request = createGetClientStatusesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
	public static GetMatterStatusesRequest createGetMatterStatusesRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.GetMatterStatusesRequest");
        return (GetMatterStatusesRequest) obj;
	}

	public static GetMatterStatusesRequest createGetMatterStatusesRequest(String accessToken) {
		GetMatterStatusesRequest request = createGetMatterStatusesRequest();
        request.setAccessToken(accessToken);
        return request;
	}

	public static CreateMatterStatusRequest createCreateMatterStatusRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.CreateMatterStatusRequest");
        return (CreateMatterStatusRequest) obj;
	}

	public static CreateMatterStatusRequest createCreateMatterStatusRequest(String accessToken) {
		CreateMatterStatusRequest request = createCreateMatterStatusRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static FindDepartmentsRequest createFindDepartmentsRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.FindDepartmentsRequest");
        return (FindDepartmentsRequest) obj;
	}

	public static FindDepartmentsRequest createFindDepartmentsRequest(String accessToken) {
		FindDepartmentsRequest request = createFindDepartmentsRequest();
        request.setAccessToken(accessToken);
        return request;
	}

}
