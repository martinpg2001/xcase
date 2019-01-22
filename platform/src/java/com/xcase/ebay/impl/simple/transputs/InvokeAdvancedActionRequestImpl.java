package com.xcase.ebay.impl.simple.transputs;

import com.xcase.ebay.transputs.InvokeAdvancedActionRequest;

public class InvokeAdvancedActionRequestImpl extends EBayRequestImpl implements InvokeAdvancedActionRequest {

	private String accessToken;
	private String endpoint;
	
	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public String getEndpoint() {
		return endpoint;
	}

}
