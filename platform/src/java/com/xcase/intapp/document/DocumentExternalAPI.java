package com.xcase.intapp.document;

import com.xcase.intapp.document.transputs.*;

public interface DocumentExternalAPI {

	void generateTokenPair()  throws Exception;

	String getAccessToken();

	GetCategoriesResponse getCategories(GetCategoriesRequest getCategoriesRequest);

    GetTemplatesResponse getTemplates(GetTemplatesRequest getTemplatesRequest);

    HeadTemplatesResponse headTemplates(HeadTemplatesRequest headTemplatesRequest);

	SaveTemplateResponse saveTemplate(SaveTemplateRequest saveTemplateRequest);

	DeleteTemplateResponse deleteTemplate(DeleteTemplateRequest deleteTemplateRequest);

    GetTemplateFileResponse getTemplateFile(GetTemplateFileRequest getTemplateFileRequest);

}
