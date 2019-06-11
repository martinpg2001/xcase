package com.xcase.azure.transputs;

import com.microsoft.azure.management.Azure;
import com.xcase.common.transputs.CommonRequest;

public interface AzureRequest extends CommonRequest {
    Azure getAzure();
    
    void setAzure(Azure azure);
}
