package com.xcase.slack.impl.simple.transputs;

import com.xcase.common.impl.simple.transputs.CommonRequestImpl;
import com.xcase.slack.transputs.SlackRequest;

public class SlackRequestImpl extends CommonRequestImpl implements SlackRequest {
    private String accessToken;
    private String apiUrl;
    private String message;

    @Override
    public String getAccessToken() {
        return accessToken;
    }
    
    @Override
    public String getAPIUrl() {
        return apiUrl;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void setAPIUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
