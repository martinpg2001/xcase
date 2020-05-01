package com.xcase.slack;

import com.xcase.slack.transputs.GetAccessTokenResponse;

public interface SlackExternalAPI {

    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest);

}
