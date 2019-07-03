package com.xcase.klearexpress.impl.simple.transputs;

import com.google.gson.JsonObject;
import com.xcase.klearexpress.transputs.GetAccessTokenResponse;

public class GetAccessTokenResponseImpl extends KlearExpressResponseImpl implements GetAccessTokenResponse {
    private String eventId;
    private JsonObject eventMessageJsonObject;
    private String eventType;
    
    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public void setEventMessage(JsonObject eventMessageJsonObject) {
        this.eventMessageJsonObject = eventMessageJsonObject;
    }

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

}
