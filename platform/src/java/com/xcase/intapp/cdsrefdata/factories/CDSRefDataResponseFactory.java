package com.xcase.intapp.cdsrefdata.factories;

import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusResponse;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesResponse;

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

}
