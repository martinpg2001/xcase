package com.xcase.slack.transputs;

import com.google.gson.JsonObject;
import com.xcase.common.transputs.*;

public interface SlackResponse extends RestResponse {
    String getEventId();
    String getEventType();
    JsonObject getEventMessage();
    int getResponseCode();
    void setResponseCode(int responseCode);
    void setEventId(String asString);
    void setEventType(String asString);
    void setEventMessage(JsonObject eventMessageJsonObject);
}
