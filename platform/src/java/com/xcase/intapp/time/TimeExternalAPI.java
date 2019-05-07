package com.xcase.intapp.time;

import com.xcase.intapp.time.transputs.*;

public interface TimeExternalAPI {

	GetRestrictedTextsResponse getRestrictedTexts(GetRestrictedTextsRequest getRestrictedTextsRequest);

	GetClientsResponse getClients(GetClientsRequest getClientsRequest);

}
