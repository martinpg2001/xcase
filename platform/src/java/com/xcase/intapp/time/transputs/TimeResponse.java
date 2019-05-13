package com.xcase.intapp.time.transputs;

import org.apache.http.StatusLine;

public interface TimeResponse {
    String getEntityString();
    
    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();

	void setEntityString(String responseEntityString);
	
    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
