package com.xcase.intapp.advanced.factories;

import com.xcase.intapp.advanced.transputs.*;

public class AdvancedResponseFactory extends BaseAdvancedFactory {

	public static InvokeOperationResponse createInvokeOperationResponse() {
        Object obj = newInstanceOf("advanced.config.responsefactory.InvokeOperationResponse");
        return (InvokeOperationResponse) obj;
	}

}
