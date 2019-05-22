package com.xcase.intapp.document.transputs;

import org.apache.http.StatusLine;

public interface DocumentResponse {
    
    String getEntityString();
    
    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();

	void setEntityString(String responseEntityString);

	void setResponseCode(int responseCode);

	void setStatus(String reasonPhrase);

	void setStatusLine(StatusLine statusLine);

	void setMessage(String string);

}
