/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CommonHttpResponse {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private byte[] content;
    private HttpEntity responseEntity;
    private Header[] responseHeaders;
    private int statusCode;
    private StatusLine statusLine;
    private String responseEntityString;

    public CommonHttpResponse() {

    }

    public CommonHttpResponse(HttpResponse httpResponse) {
        this.responseEntity = httpResponse.getEntity();
        try {
            this.content = EntityUtils.toByteArray(this.responseEntity);
            this.responseEntityString = new String(this.content, "UTF-8");
            LOGGER.debug("responseEntityString is " + this.responseEntityString);
        } catch (Exception e) {
            /* TODO: work out what to do here ... */
        }

        this.responseHeaders = httpResponse.getAllHeaders();
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        this.statusLine = httpResponse.getStatusLine();
    }

    public byte[] getContent() {
        return this.content;
    }

    public HttpEntity getResponseEntity() {
        return this.responseEntity;
    }

    public void setResponseEntity(HttpEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public Header[] getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public int getResponseCode() {
        return this.statusCode;
    }

    public void setResponseCode(int responseCode) {
        this.statusCode = responseCode;
    }

    public StatusLine getStatusLine() {
        return this.statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public String getResponseEntityString() {
        return this.responseEntityString;
    }

    public void setResponseEntityString(String responseEntityString) {
        this.responseEntityString = responseEntityString;
    }
}
