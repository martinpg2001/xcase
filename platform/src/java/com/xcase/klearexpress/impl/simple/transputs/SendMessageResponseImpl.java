package com.xcase.klearexpress.impl.simple.transputs;

import com.google.gson.JsonObject;
import com.xcase.klearexpress.transputs.SendMessageResponse;

public class SendMessageResponseImpl extends KlearExpressResponseImpl implements SendMessageResponse {
    private String eventId;
    private JsonObject eventMessageJsonObject;
    private String eventType;
    
    @Override
    public String getEventId() {
        return eventId;
    }
    
    @Override
    public JsonObject getEventMessage() {
        return eventMessageJsonObject;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    @Override
    public void setEventMessage(JsonObject eventMessageJsonObject) {
        this.eventMessageJsonObject = eventMessageJsonObject;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

}
