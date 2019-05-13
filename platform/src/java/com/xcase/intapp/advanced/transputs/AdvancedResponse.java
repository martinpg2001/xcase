package com.xcase.intapp.advanced.transputs;

import org.apache.http.StatusLine;

public interface AdvancedResponse {
    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();

    String getEntityString();
	
    void setMessage(String message);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

	void setEntityString(String responseEntityString);

}
