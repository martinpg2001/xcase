package com.xcase.intapp.advanced;

import com.xcase.intapp.advanced.transputs.*;

public interface AdvancedExternalAPI {

    public InvokeOperationResponse invokeOperation(InvokeOperationRequest invokeOperationRequest);

	public void generateTokenPair() throws Exception;
	
	public String getAccessToken();
}