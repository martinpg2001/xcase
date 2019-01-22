/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;
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
public class GetFolderInfoMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets folder info.
     *
     * 'parent_id' param is the id of a folder in which a new folder will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getFolderInfoRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException sharepoint exception
     */
    public GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getFolderInfo()");
        GetFolderInfoResponse getFolderInfoResponse = SharepointResponseFactory.createGetFolderInfoResponse();
        String domain = getFolderInfoRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = getFolderInfoRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = getFolderInfoRequest.getPassword();
        LOGGER.debug("password is " + password);
        String accessToken = getFolderInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderName = getFolderInfoRequest.getFolderName();
        LOGGER.debug("folderName is " + folderName);
        String serverUrl = getFolderInfoRequest.getServerUrl();
        LOGGER.debug("serverUrl is " + serverUrl);
        String site = getFolderInfoRequest.getSite();
        LOGGER.debug("site is " + site);
        serverUrl = SharepointConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SharepointConstant.LOCAL_WEB_URL);
        String serverSiteUrl = serverUrl + "/" + site;
        LOGGER.debug("serverSiteUrl is " + serverSiteUrl);
        String folderApiUrl = serverSiteUrl + "/_api/web/GetFolderByServerRelativeUrl('/" + site + "/" + folderName + "')";
        LOGGER.debug("folderApiUrl is " + folderApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header authorizationHeader = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = new BasicHeader("accept", "application/atom+xml");
        LOGGER.debug("created acceptHeader header");
        Header xRequestDigestHeader = new BasicHeader("X-RequestDigest", accessToken);
        LOGGER.debug("created xRequestDigestHeader header");
        Header[] headers = {xRequestDigestHeader};
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        LOGGER.debug("about to get folder info");
        try {
            NTCredentials ntCredentials = null;
            if (username != null && !username.equals(""));
            {
                ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
                LOGGER.debug("created ntCredentials");
            }

            String entityString = httpManager.doStringGet(folderApiUrl, headers, data, ntCredentials);
            LOGGER.debug("entityString is " + entityString);
            getFolderInfoResponse.setStatus("OK");
        } catch (Exception e) {
            LOGGER.warn("exception doing GET: " + e.getMessage());
            getFolderInfoResponse.setStatus("Error");
        }

        return getFolderInfoResponse;
    }
}
