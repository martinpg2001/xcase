package com.xcase.klearexpress.transputs;

import com.google.gson.JsonObject;

public interface SendMessageResponse extends KlearExpressResponse {
    String getEventId();
    JsonObject getEventMessage();
    String getEventType();
    void setEventId(String eventId);
    void setEventMessage(JsonObject eventMessageJsonObject);
    void setEventType(String eventType);

}
