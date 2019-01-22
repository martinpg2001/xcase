/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointObjectFactory;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.objects.SharepointUrlGenerator;
import com.xcase.sharepoint.transputs.GetAccessTokenRequest;
import com.xcase.sharepoint.transputs.GetAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.auth.NTCredentials;
import org.apache.logging.log4j.*;
import org.dom4j.DocumentHelper;

/**
 *
 * @author martin
 */
public class GetAccessTokenMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     */
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SharepointException {
        LOGGER.info("starting getAccessToken()");
        SharepointUrlGenerator sharepointUrlGenerator = SharepointObjectFactory.createSharepointUrlGenerator();
        GetAccessTokenResponse getAccessTokenResponse = SharepointResponseFactory.createGetAccessTokenResponse();
        String domain = getAccessTokenRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = getAccessTokenRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = getAccessTokenRequest.getPassword();
        LOGGER.debug("password is " + password);
        String serverUrl = getAccessTokenRequest.getServerUrl();
        LOGGER.debug("serverUrl is " + serverUrl);
        String site = getAccessTokenRequest.getSite();
        LOGGER.debug("site is " + site);
        URLCodec codec = new URLCodec();
        serverUrl = SharepointConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SharepointConstant.LOCAL_WEB_URL);
        String serverSiteUrl = serverUrl + "/" + site;
        LOGGER.debug("serverSiteUrl is " + serverSiteUrl);
        String contextInfoUrl = serverSiteUrl + "/" + "_api/contextinfo";
        LOGGER.info("contextInfoUrl is " + contextInfoUrl);
        try {
            NTCredentials ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
            LOGGER.debug("created ntCredentials");
            String responseEntityString = CommonHTTPManager.refreshCommonHTTPManager().doStringPost(contextInfoUrl, ntCredentials);
            LOGGER.debug("responseEntityString is " + responseEntityString);
            org.dom4j.Document responseDocument = DocumentHelper.parseText(responseEntityString);
            LOGGER.debug("responseDocument root element name is " + responseDocument.getRootElement().getName());
            LOGGER.debug(responseDocument.asXML());
            String formDigestValue = responseDocument.getRootElement().elementText("FormDigestValue");
            LOGGER.debug("formDigestValue is " + formDigestValue);
            getAccessTokenResponse.setAccessToken(formDigestValue);
            return getAccessTokenResponse;
        } catch (Exception e) {
            LOGGER.debug("failed to parse to a document");
            SharepointException be = new SharepointException("Failed to parse to a document.", e);
            be.setStatus(getAccessTokenResponse.getStatus());
            throw be;
        }
    }
}
