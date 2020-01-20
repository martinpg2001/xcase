package com.xcase.klearnow.transputs;

import com.google.gson.JsonObject;

public interface GetAccessTokenResponse extends KlearNowResponse {

    String getEventId();

    JsonObject getEventMessage();

    String getEventType();

    void setEventId(String asString);

    void setEventType(String asString);

    void setEventMessage(JsonObject eventMessageJsonObject);

}
