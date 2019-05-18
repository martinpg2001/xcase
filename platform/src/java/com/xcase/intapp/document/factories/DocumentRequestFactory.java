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
	
    public static GetTemplatesRequest createGetTemplatesRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.GetTemplatesRequest");
        return (GetTemplatesRequest) obj;
    }

    public static GetTemplatesRequest createGetTemplatesRequest(String accessToken) {
        GetTemplatesRequest request = createGetTemplatesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static HeadTemplatesRequest createHeadTemplatesRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.HeadTemplatesRequest");
        return (HeadTemplatesRequest) obj;
    }

    public static HeadTemplatesRequest createHeadTemplatesRequest(String accessToken) {
        HeadTemplatesRequest request = createHeadTemplatesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static SaveTemplateRequest createSaveTemplateRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.SaveTemplateRequest");
        return (SaveTemplateRequest) obj;
    }

	public static SaveTemplateRequest createSaveTemplateRequest(String accessToken) {
		SaveTemplateRequest request = createSaveTemplateRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static DeleteTemplateRequest createDeleteTemplateRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.DeleteTemplateRequest");
        return (DeleteTemplateRequest) obj;
    }

	public static DeleteTemplateRequest createDeleteTemplateRequest(String accessToken) {
		DeleteTemplateRequest request = createDeleteTemplateRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static GetTemplateFileRequest createGetTemplateFileRequest() {
        Object obj = newInstanceOf("document.config.requestfactory.GetTemplateFileRequest");
        return (GetTemplateFileRequest) obj;
    }

    public static GetTemplateFileRequest createGetTemplateFileRequest(String accessToken) {
        GetTemplateFileRequest request = createGetTemplateFileRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
