package com.xcase.azure.transputs;

import com.microsoft.azure.management.Azure;
import com.xcase.common.transputs.CommonRequest;

public interface GetEventsRequest extends CommonRequest {
    Azure getAzure();

    String getFilter();

    String getSelect();
    
    void setAzure(Azure azure);

    void setFilter(String filter);

    void setSelect(String select);

}
