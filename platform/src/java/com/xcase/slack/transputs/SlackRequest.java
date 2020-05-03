package com.xcase.slack.transputs;

import com.xcase.common.transputs.CommonRequest;

public interface SlackRequest extends CommonRequest {

    String getAccessToken();

    String getAPIUrl();

    String getMessage();

    void setAccessToken(String accessToken);

    void setAPIUrl(String apiUrl);

    void setMessage(String message);

}
