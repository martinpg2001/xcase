package com.xcase.intapp.document.factories;

import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;
import com.xcase.intapp.document.transputs.GetCategoriesResponse;
import com.xcase.intapp.document.transputs.GetTemplatesResponse;
import com.xcase.intapp.document.transputs.HeadTemplatesResponse;

public class DocumentResponseFactory extends BaseDocumentFactory {

	public static GetCategoriesResponse createGetCategoriesResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.GetCategoriesResponse");
        return (GetCategoriesResponse) obj;
	}

    public static GetTemplatesResponse createGetTemplatesResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.GetTemplatesResponse");
        return (GetTemplatesResponse) obj;
    }

    public static HeadTemplatesResponse createHeadTemplatesResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.HeadTemplatesResponse");
        return (HeadTemplatesResponse) obj;
    }

}
