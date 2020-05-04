package com.xcase.slack.transputs;

public interface GetAccessTokenRequest extends SlackRequest {

    void setAPIUrl(String apiEventsURL);

    void setEntityRequest(String string);

    String getEntityRequest();

}
