package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.InvokeOperationRequest;
import java.util.List;
import org.apache.http.NameValuePair;

public class InvokeOperationRequestImpl extends AdvancedRequestImpl implements InvokeOperationRequest {
    private String apiURL;
    private String entityString;
    private String method;
    private List<NameValuePair> parameters;
    private int successResponseCode = 200;
    
	@Override
	public String getEntityString() {
		return entityString;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public List<NameValuePair> getParameters() {
		return parameters;
	}

	@Override
	public void setEntityString(String entityString) {
        this.entityString = entityString;
	}
	
	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getAPIURL() {
		return apiURL;
	}

	@Override
	public void setAPIURL(String apiURL) {
		this.apiURL = apiURL;
	}

	public void setSuccessResponseCode(int successResponseCode) {
		this.successResponseCode = successResponseCode;
	}
}
