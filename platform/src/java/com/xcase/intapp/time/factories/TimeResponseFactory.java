package com.xcase.intapp.time.factories;

import com.xcase.intapp.time.transputs.*;

public class TimeResponseFactory extends BaseTimeFactory {

	public static GetRestrictedTextsResponse createGetRestrictedTextsResponse() {
        Object obj = newInstanceOf("time.config.responsefactory.GetRestrictedTextsResponse");
        return (GetRestrictedTextsResponse) obj;
	}

}
