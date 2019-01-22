package com.xcase.ebay;

import com.xcase.ebay.impl.simple.core.EBayConfigurationManager;
import com.xcase.ebay.impl.simple.methods.*;
import com.xcase.ebay.objects.EBayException;
import com.xcase.ebay.transputs.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleEBayImpl implements EBayExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public EBayConfigurationManager localConfigurationManager = EBayConfigurationManager.getConfigurationManager();

    private CreateApplicationAccessTokenMethod createApplicationAccessTokenMethod = new CreateApplicationAccessTokenMethod();
    
	private CreateApplicationAuthorizationCodeMethod createApplicationAuthorizationCodeMethod = new CreateApplicationAuthorizationCodeMethod();
    
	private InvokeAdvancedActionMethod invokeAdvancedActionMethod = new InvokeAdvancedActionMethod();

    @Override
    public CreateApplicationAccessTokenResponse createApplicationAccessToken(CreateApplicationAccessTokenRequest createApplicationAcessTokenRequest)
            throws Exception {
        return this.createApplicationAccessTokenMethod.createApplicationAccessToken(createApplicationAcessTokenRequest);
    }

	@Override
	public CreateApplicationAuthorizationCodeResponse createApplicationAuthorizationCode(
			CreateApplicationAuthorizationCodeRequest createApplicationAuthorizationCodeRequest) throws Exception {
		return this.createApplicationAuthorizationCodeMethod.createApplicationAuthorizationCode(createApplicationAuthorizationCodeRequest);
	}

	@Override
	public InvokeAdvancedActionResponse invokeAdvancedActionRequest(
			InvokeAdvancedActionRequest invokeAdvancedActionRequest) throws Exception {
			return this.invokeAdvancedActionMethod.invokeAdvancedAction(invokeAdvancedActionRequest);
	}

}
