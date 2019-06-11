package com.xcase.azure.impl.simple.transputs;

import com.microsoft.azure.management.Azure;
import com.xcase.azure.transputs.AzureRequest;
import com.xcase.common.impl.simple.transputs.CommonRequestImpl;

public class AzureRequestImpl extends CommonRequestImpl implements AzureRequest {
    private Azure azure;
    
    @Override
    public Azure getAzure() {
        return azure;
    }

    @Override
    public void setAzure(Azure azure) {
        this.azure = azure;
    }

}
