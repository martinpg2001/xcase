package com.xcase.intapp.advanced.transputs;

import java.util.List;
import org.apache.http.NameValuePair;

public interface InvokeOperationRequest extends AdvancedRequest {

	String getEntityString();
	
	String getMethod();
	
	String getOperationPath();
	
	List<NameValuePair> getParameters();
	
	void setEntityString(String entityString);
	
	void setMethod(String method);

	void setOperationPath(String operationPath);

	void setParameters(List<NameValuePair> parameters);

}
