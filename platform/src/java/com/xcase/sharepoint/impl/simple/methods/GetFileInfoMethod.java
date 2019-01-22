/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.NTCredentials;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class GetFileInfoMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets file info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getFileInfo()");
        GetFileInfoResponse getFileInfoResponse = SharepointResponseFactory.createGetFileInfoResponse();
        String domain = getFileInfoRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = getFileInfoRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = getFileInfoRequest.getPassword();
        LOGGER.debug("password is " + password);
        String accessToken = getFileInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderName = getFileInfoRequest.getDirectoryId();
        LOGGER.debug("folderName is " + folderName);
        String fileName = getFileInfoRequest.getFileId();
        LOGGER.debug("fileName is " + fileName);
        String serverUrl = getFileInfoRequest.getServerUrl();
        LOGGER.debug("serverUrl is " + serverUrl);
        String site = getFileInfoRequest.getSite();
        LOGGER.debug("site is " + site);
        serverUrl = SharepointConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SharepointConstant.LOCAL_WEB_URL);
        String serverSiteUrl = serverUrl + "/" + site;
        LOGGER.debug("serverSiteUrl is " + serverSiteUrl);
        String filesApiUrl = serverSiteUrl + "/_api/web/GetFileByServerRelativeUrl('/" + site + "/" + folderName + "/" + fileName + "')";
        LOGGER.debug("filesApiUrl is " + filesApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header authorizationHeader = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header xRequestDigestHeader = new BasicHeader("X-RequestDigest", accessToken);
        LOGGER.debug("created xRequestDigestHeader header");
        Header[] headers = {xRequestDigestHeader};
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        LOGGER.debug("about to get file info");
        try {
            if (fileName != null) {
                LOGGER.debug("fileName is " + fileName);
                NTCredentials ntCredentials = null;
                if (username != null && !username.equals(""));
                {
                    ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
                    LOGGER.debug("created ntCredentials");
                }

                String entityString = httpManager.doStringGet(filesApiUrl, headers, data, ntCredentials);
                LOGGER.debug("entityString is " + entityString);
                getFileInfoResponse.setStatus("OK");
            } else {
                LOGGER.debug("fileName is null");
            }
        } catch (Exception e) {
            LOGGER.debug("failed to parse to a document");
            SharepointException be = new SharepointException("Failed to parse to a document.", e);
            be.setStatus(getFileInfoResponse.getStatus());
            throw be;
        }

        return getFileInfoResponse;
    }
}
