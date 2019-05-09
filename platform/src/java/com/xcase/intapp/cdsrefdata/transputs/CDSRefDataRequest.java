package com.xcase.intapp.cdsrefdata.transputs;

public interface CDSRefDataRequest {
    public String getAccessToken();

    public String getEntityString();
    
    public void setAccessToken(String accessToken);
    
    public void setEntityString(String entityString);
}
