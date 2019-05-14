package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.DocumentRequest;

public class DocumentRequestImpl implements DocumentRequest {
    private String accessToken;
    
	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
	}

}
