package com.xcase.intapp.cdsrefdata.transputs;

public interface ListableRefDataRequest extends CDSRefDataRequest {
    int getLimit();
    
    int getSkip();
    
    void setLimit(int limit);
    
    void setSkip(int skip);
}
