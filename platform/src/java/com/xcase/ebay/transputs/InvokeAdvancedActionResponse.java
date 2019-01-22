package com.xcase.ebay.transputs;

public interface InvokeAdvancedActionResponse extends EBayResponse {

	public int getResponseCode();
	
	public void setResponseCode(int responseCode);
	
	public String getMessage();
	
	public void setMessage(String message);
	
	public String getStatus();

	public void setStatus(String status);

	public String getResponseEntityString();

	public void setResponseEntityString(String responseEntityString);
}
