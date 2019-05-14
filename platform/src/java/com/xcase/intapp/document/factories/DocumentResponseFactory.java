package com.xcase.intapp.document.factories;

import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;
import com.xcase.intapp.document.transputs.GetCategoriesResponse;

public class DocumentResponseFactory extends BaseDocumentFactory {

	public static GetCategoriesResponse createGetCategoriesResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.GetCategoriesResponse");
        return (GetCategoriesResponse) obj;
	}

}
