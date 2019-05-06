package com.xcase.intapp.cdsrefdata.transputs;

import org.apache.http.StatusLine;

public interface CDSRefDataResponse {

    void setMessage(String string);

    void setResponseCode(int responseCode);

    void setStatusLine(StatusLine statusLine);

    void setStatus(String reasonPhrase);

}
