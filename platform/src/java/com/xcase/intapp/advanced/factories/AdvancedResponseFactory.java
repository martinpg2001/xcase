package com.xcase.intapp.advanced.factories;

import com.xcase.intapp.advanced.transputs.*;

public class AdvancedResponseFactory extends BaseAdvancedFactory {

	public static InvokeOperationResponse createInvokeOperationResponse() {
        Object obj = newInstanceOf("advanced.config.responsefactory.InvokeOperationResponse");
        return (InvokeOperationResponse) obj;
	}

    public static GenerateTokensResponse createGenerateTokensResponse() {
        Object obj = newInstanceOf("advanced.config.responsefactory.GenerateTokensResponse");
        return (GenerateTokensResponse) obj;
    }

    public static GetSwaggerDocumentResponse createGetSwaggerDocumentResponse() {
        Object obj = newInstanceOf("advanced.config.responsefactory.GetSwaggerDocumentResponse");
        return (GetSwaggerDocumentResponse) obj;
    }

}
