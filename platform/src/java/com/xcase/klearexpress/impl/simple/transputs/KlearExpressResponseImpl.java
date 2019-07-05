package com.xcase.klearexpress.impl.simple.transputs;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.transputs.*;
import com.xcase.klearexpress.transputs.KlearExpressResponse;

public class KlearExpressResponseImpl extends RestResponseImpl implements KlearExpressResponse {
    private String eventId;
    private String eventType;
    private JsonObject eventMessage;
    private String message;
    private int responseCode;
    
    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public JsonObject getEventMessage() {
        return eventMessage;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public void setEventType(String setEventType) {
        this.eventType = setEventType;        
    }

    @Override
    public void setEventMessage(JsonObject eventMessage) {
        this.eventMessage = eventMessage;        
    }

}
