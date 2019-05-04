package com.xcase.intapp.cdsusers.transputs;

import org.apache.http.StatusLine;

public interface CDSUsersResponse {

    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
