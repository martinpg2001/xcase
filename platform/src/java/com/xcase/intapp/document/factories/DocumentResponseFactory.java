package com.xcase.intapp.document.factories;

import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;
import com.xcase.intapp.document.transputs.DeleteTemplateResponse;
import com.xcase.intapp.document.transputs.GetCategoriesResponse;
import com.xcase.intapp.document.transputs.GetTemplateFileResponse;
import com.xcase.intapp.document.transputs.GetTemplatesResponse;
import com.xcase.intapp.document.transputs.HeadTemplatesResponse;
import com.xcase.intapp.document.transputs.RenderDocumentResponse;
import com.xcase.intapp.document.transputs.SaveTemplateResponse;

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

	public static SaveTemplateResponse createSaveTemplateResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.SaveTemplateResponse");
        return (SaveTemplateResponse) obj;
	}

	public static DeleteTemplateResponse createDeleteTemplateResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.DeleteTemplateResponse");
        return (DeleteTemplateResponse) obj;
	}

    public static GetTemplateFileResponse createGetTemplateFileResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.GetTemplateFileResponse");
        return (GetTemplateFileResponse) obj;
    }

	public static RenderDocumentResponse createRenderDocumentResponse() {
        Object obj = newInstanceOf("document.config.responsefactory.RenderDocumentResponse");
        return (RenderDocumentResponse) obj;
	}

}
