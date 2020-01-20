package com.xcase.klearnow.transputs;

import com.google.gson.JsonObject;

public interface SendMessageResponse extends KlearNowResponse {
    String getEventId();
    JsonObject getEventMessage();
    String getEventType();
    void setEventId(String eventId);
    void setEventMessage(JsonObject eventMessageJsonObject);
    void setEventType(String eventType);

}
