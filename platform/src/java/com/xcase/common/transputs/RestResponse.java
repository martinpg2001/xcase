package com.xcase.common.transputs;

import org.apache.http.StatusLine;

public interface RestResponse extends CommonResponse {
    String getEntityString();
    
    int getResponseCode();
    
    String getStatus();
    
    StatusLine getStatusLine();
    
    void setEntityString(String entityString);

    void setResponseCode(int responseCode);
    
    void setStatus(String reasonPhrase);
    
    void setStatusLine(StatusLine statusLine);
}
