/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateTokensFromUsernamePasswordResponse;

/**
 *
 * @author martinpg
 */
public class CreateTokensFromUsernamePasswordResponseImpl extends OpenResponseImpl implements CreateTokensFromUsernamePasswordResponse {
    private String accessToken;
    private String refreshToken;
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }     
}
