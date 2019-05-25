package com.xcase.intapp.cdsusers.transputs;

/**
 * The basic interface for making requests to the CDS servuce.
 * 
 * @author martinpg
 *
 */
public interface CDSUsersRequest {
    public String getAccessToken();
    
    public void setAccessToken(String accessToken);
}
