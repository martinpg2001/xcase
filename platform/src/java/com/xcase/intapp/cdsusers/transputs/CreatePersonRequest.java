package com.xcase.intapp.cdsusers.transputs;

public interface CreatePersonRequest extends CDSUsersRequest {
    public String getOperationPath();
    
	public String getPersonString();

	public void setPersonString(String string);
}
