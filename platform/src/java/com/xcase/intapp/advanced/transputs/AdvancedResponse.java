package com.xcase.intapp.advanced.transputs;

import org.apache.http.StatusLine;

public interface AdvancedResponse {

    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
