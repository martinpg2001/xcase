package com.xcase.intapp.cdsrefdata.transputs;

import org.apache.http.StatusLine;

public interface CDSRefDataResponse {
    String getEntityString();
    
    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();
    
    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

	void setEntityString(String responseEntityString);

}
