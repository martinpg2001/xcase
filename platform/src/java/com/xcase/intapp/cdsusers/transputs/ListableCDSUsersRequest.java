package com.xcase.intapp.cdsusers.transputs;

public interface ListableCDSUsersRequest extends CDSUsersRequest {
    int getLimit();
    
    int getSkip();
    
    void setLimit(int limit);
    
    void setskip(int skip);
}
