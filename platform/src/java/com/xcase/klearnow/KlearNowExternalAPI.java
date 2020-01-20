package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {

    SendMessageResponse sendMessage(SendMessageRequest sendMessageRequest);

    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest);

}
