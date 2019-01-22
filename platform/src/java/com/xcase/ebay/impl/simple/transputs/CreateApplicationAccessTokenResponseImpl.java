package com.xcase.ebay.impl.simple.transputs;

import com.xcase.ebay.transputs.CreateApplicationAccessTokenResponse;

public class CreateApplicationAccessTokenResponseImpl extends EBayResponseImpl implements CreateApplicationAccessTokenResponse {
    private String accessToken;
    private String status;
    private int expiresIn;
    private String refreshToken;
    
	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

}
