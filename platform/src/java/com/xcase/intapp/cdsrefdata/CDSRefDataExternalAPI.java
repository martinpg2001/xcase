package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;

public interface CDSRefDataExternalAPI {

    GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest getClientStatusesRequest);

}
