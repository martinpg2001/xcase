package com.xcase.intapp.cdscm.transputs;

import org.apache.http.StatusLine;

public interface CDSCMResponse {

    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
