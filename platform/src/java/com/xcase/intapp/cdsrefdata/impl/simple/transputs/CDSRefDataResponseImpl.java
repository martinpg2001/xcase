package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import org.apache.http.StatusLine;

import com.xcase.intapp.cdsrefdata.transputs.CDSRefDataResponse;

public class CDSRefDataResponseImpl implements CDSRefDataResponse {
    private String entityString;
    private String message;
    private int responseCode;
    private StatusLine statusLine;
    private String status;
    
	@Override
	public String getEntityString() {
		return entityString;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getResponseCode() {
		return responseCode;
	}

	@Override
	public StatusLine getStatusLine() {
		return statusLine;
	}

	@Override
	public String getStatus() {
		return status;
	}
	
	@Override
	public void setEntityString(String entityString) {
		this.entityString = entityString;
	}
    
    @Override
    public void setMessage(String message) {
        
    }

    @Override
    public void setResponseCode(int responseCode) {
    	this.responseCode = responseCode;
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
    	this.statusLine = statusLine;
    }

    @Override
    public void setStatus(String reasonPhrase) {
    	this.status = reasonPhrase;
    }

}
