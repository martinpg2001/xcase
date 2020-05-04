package com.xcase.slack.transputs;

import com.google.gson.JsonObject;

public interface GetAccessTokenResponse extends SlackResponse {

    JsonObject getEventMessage();

}
