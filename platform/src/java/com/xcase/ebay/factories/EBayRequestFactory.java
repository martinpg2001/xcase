package com.xcase.ebay.factories;

import com.xcase.ebay.transputs.CreateApplicationAccessTokenRequest;
import com.xcase.ebay.transputs.CreateApplicationAuthorizationCodeRequest;
import com.xcase.ebay.transputs.InvokeAdvancedActionRequest;

public class EBayRequestFactory extends BaseEBayFactory {

	public static CreateApplicationAuthorizationCodeRequest createCreateApplicationAuthorizationCodeRequest(
			String clientId, String tenantId, String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public static CreateApplicationAccessTokenRequest createCreateApplicationAccessTokenRequest() {
        Object obj = newInstanceOf("ebay.config.requestfactory.CreateApplicationAccessTokenRequest");
        return (CreateApplicationAccessTokenRequest) obj;
	}

    public static CreateApplicationAccessTokenRequest createCreateApplicationAccessTokenRequest(String clientId, String clientSecret) {
        CreateApplicationAccessTokenRequest request = createCreateApplicationAccessTokenRequest();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        return request;
    }
    
	public static InvokeAdvancedActionRequest createInvokeAdvancedActionRequest() {
        Object obj = newInstanceOf("ebay.config.requestfactory.InvokeAdvancedActionRequest");
        return (InvokeAdvancedActionRequest) obj;
	}

	public static InvokeAdvancedActionRequest createInvokeAdvancedActionRequest(String accessToken,
			String endpoint) {
		InvokeAdvancedActionRequest request = createInvokeAdvancedActionRequest();
        request.setAccessToken(accessToken);
        request.setEndpoint(endpoint);
        return request;
	}
}
