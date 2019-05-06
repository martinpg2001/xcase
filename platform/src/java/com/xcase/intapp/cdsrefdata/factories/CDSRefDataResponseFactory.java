package com.xcase.intapp.cdsrefdata.factories;

import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;

public class CDSRefDataResponseFactory extends BaseCDSRefDataFactory {

    public static GetClientStatusesResponse createGetClientStatusesResponse() {
        Object obj = newInstanceOf("cdsrefdata.config.responsefactory.GetClientStatusesResponse");
        return (GetClientStatusesResponse) obj;
    }

}
