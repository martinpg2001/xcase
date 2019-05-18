package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CheckClientSecurityRequest;

public class CheckClientSecurityRequestImpl extends CDSCMRequestImpl implements CheckClientSecurityRequest {
    private String[] idsArray;
    private String operationPath = "api/v1/clients/security/_check";;
    private int successResponseCode = 200;
    
	@Override
	public void setIdsArray(String[] idsArray) {
		this.idsArray = idsArray;
	}

	@Override
	public String[] getIdsArray() {
		return idsArray;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

}
