package com.xcase.intapp.time;

import com.xcase.intapp.time.transputs.GetRestrictedTextsRequest;
import com.xcase.intapp.time.transputs.GetRestrictedTextsResponse;

public interface TimeExternalAPI {

	GetRestrictedTextsResponse getRestrictedTexts(GetRestrictedTextsRequest getRestrictedTextsRequest);

}
