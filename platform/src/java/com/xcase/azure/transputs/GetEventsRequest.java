package com.xcase.azure.transputs;

public interface GetEventsRequest extends AzureRequest {
    String getFilter();

    String getSelect();
    
    void setFilter(String filter);

    void setSelect(String select);

}
