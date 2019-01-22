/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.DeleteRequest;
import com.xcase.sharepoint.transputs.DeleteResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.NTCredentials;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class DeleteMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method deletes a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to delete, 'target_id' is id of a file or folder to be deleted.
     *
     * On a successful result, the status will be 's_delete_node'. If the result
     * wasn't successful, status field can be: 'e_delete_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param deleteRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException, SharepointException {
        LOGGER.debug("starting delete()");
        DeleteResponse deleteResponse = SharepointResponseFactory.createDeleteResponse();
        LOGGER.debug("created deleteResponse");
        String domain = deleteRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = deleteRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = deleteRequest.getPassword();
        LOGGER.debug("password is " + password);
        String accessToken = deleteRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderName = deleteRequest.getDirectoryName();
        LOGGER.debug("folderName is " + folderName);
        String fileName = deleteRequest.getFileName();
        LOGGER.debug("fileName is " + fileName);
        String serverUrl = deleteRequest.getServerUrl();
        LOGGER.debug("serverUrl is " + serverUrl);
        String site = deleteRequest.getSite();
        LOGGER.debug("site is " + site);
        URLCodec codec = new URLCodec();
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
        Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "DELETE");
        LOGGER.debug("created xHTTPMethodHeader header");
        Header ifMatchHeader = new BasicHeader("IF-MATCH", "*");
        LOGGER.debug("created ifMatchHeader header");
        Header[] headers = {xRequestDigestHeader, xHTTPMethodHeader, ifMatchHeader};
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        LOGGER.debug("about to delete file");
        try {
            NTCredentials ntCredentials = null;
            if (username != null && !username.equals(""));
            {
                ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
                LOGGER.debug("created ntCredentials");
            }

            String responseEntityString = httpManager.doStringPost(filesApiUrl, headers, data, ntCredentials);
            LOGGER.debug("responseEntityString is " + responseEntityString);
            deleteResponse.setStatus("OK");
        } catch (Exception e) {
            LOGGER.warn("exception doing Post: " + e.getMessage());
            deleteResponse.setStatus("Error");
        }

        return deleteResponse;
    }
}
