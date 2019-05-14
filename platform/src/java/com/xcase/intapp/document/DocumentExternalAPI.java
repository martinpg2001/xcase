package com.xcase.intapp.document;

import com.xcase.intapp.document.transputs.*;

public interface DocumentExternalAPI {

	void generateTokenPair()  throws Exception;

	String getAccessToken();

	GetCategoriesResponse getCategories(GetCategoriesRequest getCategoriesRequest);

}
