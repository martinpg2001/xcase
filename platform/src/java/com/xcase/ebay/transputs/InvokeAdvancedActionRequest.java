package com.xcase.ebay.transputs;

public interface InvokeAdvancedActionRequest extends EBayRequest {

	void setAccessToken(String accessToken);

	void setEndpoint(String endpoint);

	String getAccessToken();

	String getEndpoint();
}
