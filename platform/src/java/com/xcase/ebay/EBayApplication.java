package com.xcase.ebay;

import com.xcase.ebay.EBayExternalAPI;
import com.xcase.ebay.SimpleEBayImpl;
import com.xcase.ebay.constant.EBayConstant;
import com.xcase.ebay.factories.EBayRequestFactory;
import com.xcase.ebay.impl.simple.core.EBayConfigurationManager;
import com.xcase.ebay.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EBayApplication {

	/**
	 * log4j object.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		LOGGER.debug("starting main()");
		/**
		 * Client information from local configuration file
		 */
		try {
			String clientId = EBayConfigurationManager.getConfigurationManager().getLocalConfig()
					.getProperty(EBayConstant.LOCAL_OAUTH2_CLIENT_ID);
			LOGGER.debug("clientId is " + clientId);
			String clientSecret = EBayConfigurationManager.getConfigurationManager().getLocalConfig()
					.getProperty(EBayConstant.LOCAL_OAUTH2_CLIENT_SECRET);
			LOGGER.debug("clientSecret is " + clientSecret);
			String username = EBayConfigurationManager.getConfigurationManager().getLocalConfig()
					.getProperty(EBayConstant.LOCAL_OAUTH2_USERNAME);
			LOGGER.debug("username is " + username);
			String password = EBayConfigurationManager.getConfigurationManager().getLocalConfig()
					.getProperty(EBayConstant.LOCAL_OAUTH2_PASSWORD);
			LOGGER.debug("password is " + password);
			EBayExternalAPI ebayExternalAPI = new SimpleEBayImpl();
			LOGGER.debug("created ebayExternalAPI");
//			LOGGER.debug("about to get authorization code");
//			CreateApplicationAuthorizationCodeRequest createApplicationAuthorizationCodeRequest = EBayRequestFactory
//					.createCreateApplicationAuthorizationCodeRequest(clientId, clientSecret, username, password);
//			LOGGER.debug("created createApplicationAuthorizationCodeRequest");
//			CreateApplicationAuthorizationCodeResponse createApplicationAuthorizationCodeResponse = ebayExternalAPI
//					.createApplicationAuthorizationCode(createApplicationAuthorizationCodeRequest);
//			LOGGER.debug("got authorization code "
//					+ createApplicationAuthorizationCodeResponse.getApplicationAuthorizationCode());
			LOGGER.debug("about to get access token");
			CreateApplicationAccessTokenRequest createApplicationAccessTokenRequest = EBayRequestFactory
					.createCreateApplicationAccessTokenRequest(clientId, clientSecret);
			LOGGER.debug("created createApplicationAccessTokenRequest");
			CreateApplicationAccessTokenResponse createApplicationAccessTokenResponse = ebayExternalAPI
					.createApplicationAccessToken(createApplicationAccessTokenRequest);
			String accessToken = createApplicationAccessTokenResponse.getAccessToken();
			LOGGER.debug("got access token " + accessToken);
			LOGGER.debug("about to invoke advanced action");
			String endpoint = "https://api.sandbox.ebay.com/shopping?callname=FindProducts&appid=" + clientId + "&version=1063&siteid=0&responseencoding=NV";
			InvokeAdvancedActionRequest invokeAdvancedActionRequest = EBayRequestFactory
					.createInvokeAdvancedActionRequest(accessToken, endpoint);
			LOGGER.debug("created invokeAdvancedActionRequest");
			InvokeAdvancedActionResponse invokeAdvancedActionResponse = ebayExternalAPI
					.invokeAdvancedActionRequest(invokeAdvancedActionRequest);
			LOGGER.debug("invoked advanced action");			
		} catch (Exception e) {
			LOGGER.warn("exception executing methods: " + e.getMessage());
		}
	}
}
