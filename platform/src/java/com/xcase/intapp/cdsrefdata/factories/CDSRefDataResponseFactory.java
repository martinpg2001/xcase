package com.xcase.intapp.cdsrefdata.factories;

import com.xcase.intapp.cdsrefdata.transputs.*;

public class CDSRefDataResponseFactory extends BaseCDSRefDataFactory {

    public static GetClientStatusesResponse createGetClientStatusesResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.GetClientStatusesResponse");
        return (GetClientStatusesResponse) obj;
    }

	public static GetMatterStatusesResponse createGetMatterStatusesResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.GetMatterStatusesResponse");
        return (GetMatterStatusesResponse) obj;
	}

	public static CreateMatterStatusResponse createCreateMatterStatusResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.CreateMatterStatusResponse");
        return (CreateMatterStatusResponse) obj;
	}

	public static FindDepartmentsResponse createFindDepartmentsResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.FindDepartmentsResponse");
        return (FindDepartmentsResponse) obj;
	}

	public static FindTypesResponse createFindTypesResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.FindTypesResponse");
        return (FindTypesResponse) obj;
	}

	public static GetTypeByKeyResponse createGetTypeByKeyResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.GetTypeByKeyResponse");
        return (GetTypeByKeyResponse) obj;
	}

}
