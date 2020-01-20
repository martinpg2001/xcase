package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.SendMessageRequest;

public class SendMessageRequestImpl extends KlearNowRequestImpl implements SendMessageRequest {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
