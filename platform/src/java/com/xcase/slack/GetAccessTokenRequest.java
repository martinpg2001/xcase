package com.xcase.slack;

import com.xcase.slack.transputs.SlackRequest;

public interface GetAccessTokenRequest extends SlackRequest {

    void setAPIUrl(String apiEventsURL);

    void setEntityRequest(String string);

}
