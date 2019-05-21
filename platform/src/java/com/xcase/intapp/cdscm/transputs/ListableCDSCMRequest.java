package com.xcase.intapp.cdscm.transputs;

public interface ListableCDSCMRequest extends CDSCMRequest {
    int getLimit();
    
    int getSkip();
    
    void setLimit(int limit);
    
    void setSkip(int skip);
}
