package com.xcase.intapp.cdscm.transputs;

import org.apache.http.StatusLine;

public interface CDSCMResponse {
    
    String getEntityString();
    
    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();
    
    void setEntityString(String entityString);

    void setMessage(String message);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
