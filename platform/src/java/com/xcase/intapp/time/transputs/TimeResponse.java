package com.xcase.intapp.time.transputs;

import org.apache.http.StatusLine;

public interface TimeResponse {

    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
