package com.xcase.ebay;

import java.io.IOException;

import com.xcase.ebay.objects.EBayException;
import com.xcase.ebay.transputs.CreateApplicationAccessTokenRequest;
import com.xcase.ebay.transputs.CreateApplicationAccessTokenResponse;
import com.xcase.ebay.transputs.CreateApplicationAuthorizationCodeRequest;
import com.xcase.ebay.transputs.CreateApplicationAuthorizationCodeResponse;
import com.xcase.ebay.transputs.InvokeAdvancedActionRequest;
import com.xcase.ebay.transputs.InvokeAdvancedActionResponse;

public interface EBayExternalAPI {
    public CreateApplicationAccessTokenResponse createApplicationAccessToken(CreateApplicationAccessTokenRequest createApplicationAcessTokenRequest) throws IOException, EBayException, Exception;

	public CreateApplicationAuthorizationCodeResponse createApplicationAuthorizationCode(
			CreateApplicationAuthorizationCodeRequest createApplicationAuthorizationCodeRequest) throws IOException, EBayException, Exception;

	public InvokeAdvancedActionResponse invokeAdvancedActionRequest(
			InvokeAdvancedActionRequest invokeAdvancedActionRequest) throws IOException, EBayException, Exception;
}
