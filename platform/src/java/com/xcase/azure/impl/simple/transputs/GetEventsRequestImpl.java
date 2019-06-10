package com.xcase.azure.impl.simple.transputs;

import com.microsoft.azure.management.Azure;
import com.xcase.azure.transputs.GetEventsRequest;
import com.xcase.common.impl.simple.transputs.CommonRequestImpl;

public class GetEventsRequestImpl extends CommonRequestImpl implements GetEventsRequest {
    private Azure azure;
    private String filter;
    private String select;
    
    @Override
    public Azure getAzure() {
        return azure;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public String getSelect() {
        return select;
    }

    @Override
    public void setAzure(Azure azure) {
        this.azure = azure;
    }

    @Override
    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public void setSelect(String select) {
        this.select = select;
    }

}
