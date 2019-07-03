package com.xcase.klearexpress.impl.simple.transputs;

import com.xcase.klearexpress.transputs.SendMessageRequest;

public class SendMessageRequestImpl extends KlearExpressRequestImpl implements SendMessageRequest {
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
