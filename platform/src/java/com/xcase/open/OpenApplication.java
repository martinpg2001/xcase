/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open;

import com.xcase.open.constant.OpenConstant;
import com.xcase.open.factories.OpenRequestFactory;
import com.xcase.open.impl.simple.core.CreateClientData;
import com.xcase.open.impl.simple.core.GetEntitySecurityentityType;
import com.xcase.open.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class OpenApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            SimpleOpenImpl openExternalAPI = new SimpleOpenImpl();
            LOGGER.debug("created openExternalAPI");
            /* Create authorization code */
            CreateAuthorizationCodeFromApplicationRequest createAuthorizationCodeFromApplicationRequest = OpenRequestFactory.createCreateAuthorizationCodeFromApplicationRequest();
            LOGGER.debug("created createAuthorizationCodeFromApplicationRequest");
            String authorizationUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_AUTHORIZATION_URL);
            LOGGER.debug("authorizationUrl is " + authorizationUrl);
            createAuthorizationCodeFromApplicationRequest.setAuthorizationUrl(authorizationUrl);
            String baseUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_BASE_URL);
            LOGGER.debug("baseUrl is " + baseUrl);            
            createAuthorizationCodeFromApplicationRequest.setBaseUrl(baseUrl);
            String clientId = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_CLIENT_ID);
            LOGGER.debug("clientId is " + clientId);            
            createAuthorizationCodeFromApplicationRequest.setClientId(clientId);
            String clientSecret = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_CLIENT_SECRET);
            LOGGER.debug("clientSecret is " + clientSecret);            
            createAuthorizationCodeFromApplicationRequest.setClientSecret(clientSecret);
            String redirectUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_REDIRECT_URL);
            LOGGER.debug("redirectUrl is " + redirectUrl);            
            createAuthorizationCodeFromApplicationRequest.setRedirectUrl(redirectUrl);
            String tenantId = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_TENANT_ID);
            LOGGER.debug("tenantId is " + tenantId);
            createAuthorizationCodeFromApplicationRequest.setTenantId(tenantId);
            String username = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty("open.config.api.oauth2_username");
            LOGGER.debug("username is " + username);
            createAuthorizationCodeFromApplicationRequest.setUsername(username);
            String password = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty("open.config.api.oauth2_password");
            LOGGER.debug("password is " + password);            
            createAuthorizationCodeFromApplicationRequest.setPassword(password);
            CreateAuthorizationCodeFromApplicationResponse createAuthorizationCodeFromApplicationResponse = openExternalAPI.createAuthorizationCodeFromApplication(createAuthorizationCodeFromApplicationRequest);
            LOGGER.debug("created createAuthorizationCodeFromApplicationResponse");
            String createdAuthorizationCode = createAuthorizationCodeFromApplicationResponse.getAuthorizationCode();
            LOGGER.debug("createdAuthorizationCode is " + createdAuthorizationCode);
            openExternalAPI.localConfigurationManager.getLocalConfig().setProperty(OpenConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE, createdAuthorizationCode);
            openExternalAPI.localConfigurationManager.storeLocalConfigProperties();
            /* Create tokens from usernamd and password */
//            CreateTokensFromUsernamePasswordRequest createTokensFromUsernamePasswordRequest = OpenRequestFactory.createCreateTokensFromUsernamePasswordRequest();
//            LOGGER.debug("created createTokensFromUsernamePasswordRequest");
            String tokenUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_TOKEN_URL);
            LOGGER.debug("tokenUrl is " + tokenUrl);
//            createTokensFromUsernamePasswordRequest.setUsername(username);           
//            createTokensFromUsernamePasswordRequest.setPassword(password);
//            createTokensFromUsernamePasswordRequest.setTokenUrl(tokenUrl);
//            CreateTokensFromUsernamePasswordResponse createTokensFromUsernamePasswordResponse = openExternalAPI.createTokensFromUsernamePassword(createTokensFromUsernamePasswordRequest);
//            LOGGER.debug("created createTokensFromUsernamePasswordResponse");
//            String accessToken = createTokensFromUsernamePasswordResponse.getAccessToken();
//            LOGGER.debug("accessToken is " + accessToken);
            /* Create tokens from authorization code */
            CreateTokensFromAuthorizationCodeRequest createTokensFromAuthorizationCodeRequest = OpenRequestFactory.createCreateTokensFromAuthorizationCodeRequest();
            LOGGER.debug("created createTokensFromAuthorizationCodeRequest");
            String authorizationCode = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE);
            LOGGER.debug("authorizationCode is " + authorizationCode);
            createTokensFromAuthorizationCodeRequest.setAuthorizationCode(authorizationCode);
            baseUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_BASE_URL);
            LOGGER.debug("baseUrl is " + baseUrl);
            createTokensFromAuthorizationCodeRequest.setBaseUrl(baseUrl);
            clientId = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_CLIENT_ID);
            LOGGER.debug("clientId is " + clientId);            
            createTokensFromAuthorizationCodeRequest.setClientId(clientId);
            clientSecret = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_CLIENT_SECRET);
            LOGGER.debug("clientSecret is " + clientSecret);            
            createTokensFromAuthorizationCodeRequest.setClientSecret(clientSecret);
            redirectUrl = openExternalAPI.localConfigurationManager.getLocalConfig().getProperty(OpenConstant.LOCAL_OAUTH2_REDIRECT_URL);
            LOGGER.debug("redirectUrl is " + redirectUrl);            
            createTokensFromAuthorizationCodeRequest.setRedirectUrl(redirectUrl);
            CreateTokensFromAuthorizationCodeResponse createTokensFromAuthorizationCodeResponse = openExternalAPI.createTokensFromAuthorizationCode(createTokensFromAuthorizationCodeRequest);
            LOGGER.debug("created createTokensFromAuthorizationCodeResponse");
            String accessToken = createTokensFromAuthorizationCodeResponse.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            openExternalAPI.localConfigurationManager.getLocalConfig().setProperty(OpenConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
            String refreshToken = createTokensFromAuthorizationCodeResponse.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            openExternalAPI.localConfigurationManager.getLocalConfig().setProperty(OpenConstant.LOCAL_OAUTH2_REFRESH_TOKEN, refreshToken);
            openExternalAPI.localConfigurationManager.storeLocalConfigProperties();
            /* Create tokens from refresh token */
            CreateTokensFromRefreshTokenRequest createTokensFromRefreshTokenRequest = OpenRequestFactory.createCreateTokensFromRefreshTokenRequest();
            LOGGER.debug("created createTokensFromRefreshCodeRequest");
            createTokensFromRefreshTokenRequest.setRefreshToken(refreshToken);
            createTokensFromRefreshTokenRequest.setBaseUrl(baseUrl);
            createTokensFromRefreshTokenRequest.setClientId(clientId);           
            createTokensFromRefreshTokenRequest.setClientSecret(clientSecret);
            createTokensFromRefreshTokenRequest.setTenantId(tenantId);
            createTokensFromRefreshTokenRequest.setTokenUrl(tokenUrl);
            CreateTokensFromRefreshTokenResponse createTokensFromRefreshTokenResponse = openExternalAPI.createTokensFromRefreshToken(createTokensFromRefreshTokenRequest);
            LOGGER.debug("created createTokensFromRefreshTokenResponse");
            accessToken = createTokensFromRefreshTokenResponse.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            openExternalAPI.localConfigurationManager.getLocalConfig().setProperty(OpenConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
            refreshToken = createTokensFromRefreshTokenResponse.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            openExternalAPI.localConfigurationManager.getLocalConfig().setProperty(OpenConstant.LOCAL_OAUTH2_REFRESH_TOKEN, refreshToken);
            openExternalAPI.localConfigurationManager.storeLocalConfigProperties();
           /* Get client */
            GetClientRequest getClientRequest = OpenRequestFactory.createGetClientRequest();
            LOGGER.debug("created getClientRequest");
            getClientRequest.setAccessToken(accessToken);
            getClientRequest.setClientId("20001");
            GetClientResponse getClientResponse = openExternalAPI.getClient(getClientRequest);
            LOGGER.debug("got response " + getClientResponse.getResponseCode());
            /* Get matter */
            GetMatterRequest getMatterRequest = OpenRequestFactory.createGetMatterRequest();
            LOGGER.debug("created getMatterRequest");
            getMatterRequest.setAccessToken(accessToken);
            getMatterRequest.setClientId("10001");
            getMatterRequest.setMatterId("0001");
            GetMatterResponse getMatterResponse = openExternalAPI.getMatter(getMatterRequest);
            LOGGER.debug("got response " + getMatterResponse.getResponseCode());
            /* Get entity security */
            GetEntitySecurityRequest getEntitySecurityRequest = OpenRequestFactory.createGetEntitySecurityRequest();
            LOGGER.debug("created getEntitySecuritytRequest");
            getEntitySecurityRequest.setAccessToken(accessToken);
            getEntitySecurityRequest.setEntityId("0001");
            getEntitySecurityRequest.setEntitySecurityentityType(GetEntitySecurityentityType.Work);
            getEntitySecurityRequest.setUserIds("AMY");
            getEntitySecurityRequest.setParentEntityId("10002");
            GetEntitySecurityResponse getEntitySecurityResponse = openExternalAPI.getEntitySecurity(getEntitySecurityRequest);
            LOGGER.debug("got response " + getEntitySecurityResponse.getResponseCode());
            /* Create client */
            CreateClientRequest createClientRequest = OpenRequestFactory.createCreateClientRequest();
            createClientRequest.setAccessToken(accessToken);
            LOGGER.debug("created createClientRequest");
            CreateClientData createClientData = new CreateClientData();
            createClientData.clientId = "99996";
            createClientData.name = "Martin Test";
            createClientData.partyId = "20001";
            createClientData.status = "Opened";
            createClientRequest.setCreateClientData(createClientData);
            CreateClientResponse createClientResponse = openExternalAPI.createClient(createClientRequest);
            LOGGER.debug("got response " + createClientResponse.getResponseCode());
        } catch (Exception e) {
            LOGGER.warn("exception running application: " + e.getMessage());
        }
    }
}
