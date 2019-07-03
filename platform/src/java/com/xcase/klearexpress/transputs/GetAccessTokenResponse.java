package com.xcase.klearexpress.transputs;

import com.google.gson.JsonObject;

public interface GetAccessTokenResponse extends KlearExpressResponse {

    String getEventId();
    
    JsonObject getEventMessage();
    
    String getEventType();
    
    void setEventId(String asString);

    void setEventType(String asString);

    void setEventMessage(JsonObject eventMessageJsonObject);

}
