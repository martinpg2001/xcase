package com.xcase.intapp.cdsrefdata.factories;

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

}
