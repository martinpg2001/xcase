package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.InvokeOperationRequest;
import java.util.List;
import org.apache.http.NameValuePair;

public class InvokeOperationRequestImpl extends AdvancedRequestImpl implements InvokeOperationRequest {
    private String entityString;
    private String method;
    private List<NameValuePair> parameters;
    
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
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

}
