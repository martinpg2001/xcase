package com.xcase.intapp.cdsusers.transputs;

import org.apache.http.StatusLine;

public interface CDSUsersResponse {
    String getEntityString();

    String getMessage();

    int getResponseCode();

    StatusLine getStatusLine();

    String getStatus();

    void setMessage(String message);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

	void setEntityString(String entityString);

}
