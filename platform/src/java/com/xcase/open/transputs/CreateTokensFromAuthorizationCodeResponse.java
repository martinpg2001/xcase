/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface CreateTokensFromAuthorizationCodeResponse extends OpenResponse {
    String getAccessToken();
    String getRefreshToken();
    void setAccessToken(String accessToken); 
    void setRefreshToken(String refreshToken);    
}
