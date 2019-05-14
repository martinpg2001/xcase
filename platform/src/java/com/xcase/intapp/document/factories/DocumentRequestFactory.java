package com.xcase.intapp.document.factories;

import com.xcase.intapp.document.transputs.*;

public class DocumentRequestFactory extends BaseDocumentFactory {
	public static GetCategoriesRequest createGetCategoriesRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.GetCategoriesRequest");
        return (GetCategoriesRequest) obj;
	}
	
	public static GetCategoriesRequest createGetCategoriesRequest(String accessToken) {
		GetCategoriesRequest request = createGetCategoriesRequest();
        request.setAccessToken(accessToken);
        return request;
	}

}
