/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.google.gson.*;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointObjectFactory;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.objects.SharepointFile;
import com.xcase.sharepoint.objects.UploadResult;
import com.xcase.sharepoint.transputs.UploadRequest;
import com.xcase.sharepoint.transputs.UploadResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.NTCredentials;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class UploadMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * upload.
     *
     * @param uploadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public UploadResponse upload(UploadRequest uploadRequest) throws IOException, SharepointException {
        LOGGER.debug("starting upload()");
        UploadResponse uploadResponse = SharepointResponseFactory.createUploadResponse();
        LOGGER.debug("created uploadResponse");
        String domain = uploadRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = uploadRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = uploadRequest.getPassword();
        LOGGER.debug("password is " + password);
        String accessToken = uploadRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        Map nameValueMap = uploadRequest.getDataMap();
        LOGGER.debug("got nameValueMap");
        String folderName = uploadRequest.getDirectoryName();
        LOGGER.debug("folderName is " + folderName);
        String fileName = uploadRequest.getFileName();
        LOGGER.debug("fileName is " + fileName);
        String serverUrl = uploadRequest.getServerUrl();
        LOGGER.debug("serverUrl is " + serverUrl);
        String site = uploadRequest.getSite();
        LOGGER.debug("site is " + site);
        serverUrl = SharepointConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SharepointConstant.LOCAL_WEB_URL);
        String serverSiteUrl = serverUrl + "/" + site;
        LOGGER.debug("serverSiteUrl is " + serverSiteUrl);
        String filesApiUrl = serverSiteUrl + "/_api/web/GetFolderByServerRelativeUrl('" + folderName + "')/Files/add(url='" + fileName + "',overwrite=true)";
        LOGGER.debug("filesApiUrl is " + filesApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header authorizationHeader = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header xRequestDigestHeader = new BasicHeader("X-RequestDigest", accessToken);
        LOGGER.debug("created xRequestDigestHeader header");
//        Header contentLengthHeader = new BasicHeader("content-length", "12");
//        LOGGER.debug("created contentLengthHeader header");
//        Header[] headers = {authorizationHeader, xRequestDigestHeader, contentLengthHeader};
//        Header[] headers = {authorizationHeader, xRequestDigestHeader};
        Header[] headers = {xRequestDigestHeader};
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        LOGGER.debug("about to upload file");
        try {
            NTCredentials ntCredentials = null;
            if (username != null && !username.equals(""));
            {
                ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
                LOGGER.debug("created ntCredentials");
            }

            String responseEntityString = httpManager.doStringPost(filesApiUrl, headers, data, "File content", ntCredentials);
            LOGGER.debug("responseEntityString is " + responseEntityString);
            uploadResponse.setStatus("OK");
        } catch (Exception e) {
            LOGGER.warn("exception doing Post " + e.getMessage());
            uploadResponse.setStatus("Error");
        }

        return uploadResponse;
    }

    /**
     *
     * @param filesElm file element
     * @return file status object list
     */
    private List toFileStatusList(JsonArray filesJsonArray) {
        LOGGER.debug("starting toFileStatusList()");
        List list = new ArrayList();
        if (filesJsonArray != null) {
            LOGGER.debug("filesJsonArray is not null");
            Iterator<JsonElement> fileIterator = filesJsonArray.iterator();
            while (fileIterator.hasNext()) {
                LOGGER.debug("next file element");
                JsonElement fileElement = fileIterator.next();
                JsonObject fileObject = fileElement.getAsJsonObject();
                UploadResult uploadFileStatus = SharepointObjectFactory.createUploadResult();
                SharepointFile soapFileInfo = SharepointObjectFactory.createSharepointFile();
                uploadFileStatus.setFile(soapFileInfo);
                String fileName = fileObject.get("name").getAsString();
                soapFileInfo.setFileName(fileName);
                LOGGER.debug("fileName is " + fileName);
                JsonElement errorElement = fileObject.get("error");
                if (errorElement == null) {
                    LOGGER.debug("error string is null or empty");
                    String id = fileObject.get("id").getAsString();
                    LOGGER.debug("id is " + id);
                    soapFileInfo.setFileId(id);
                    JsonObject parentObject = fileObject.getAsJsonObject("parent");
                    String folderId = parentObject.get("id").getAsString();
                    LOGGER.debug("folderId is " + folderId);
                    soapFileInfo.setFolderId(folderId);
                    soapFileInfo.setShared(false);
                    JsonElement sharedElement = fileObject.get("shared_link");
                    if (!sharedElement.isJsonNull()) {
                        LOGGER.debug("shared element is not null");
                        JsonObject sharedObject = fileObject.getAsJsonObject("shared_link");
                        String shared = sharedObject.getAsString();
                        LOGGER.debug("shared is " + shared);
                        soapFileInfo.setShared(true);
                        soapFileInfo.setSharedName(shared);
                    }
                } else {
                    uploadFileStatus.setErrorInfo(errorElement.getAsString());
                }

                list.add(uploadFileStatus);
            }
        } else {
            LOGGER.debug("filesJsonArray is null");
        }

        return list;
    }

    /**
     *
     * @param filesElm file element
     * @return file status object list
     */
    private List toFileStatusList(Element filesElm) {
        List list = new ArrayList();
        for (int i = 0; i < filesElm.nodeCount(); i++) {
            UploadResult uploadFileStatus = SharepointObjectFactory.createUploadResult();
            SharepointFile soapFileInfo = SharepointObjectFactory.createSharepointFile();
            uploadFileStatus.setFile(soapFileInfo);
            Element fileElm = (Element) filesElm.node(i);
            String fileName = fileElm.attributeValue("file_name");
            soapFileInfo.setFileName(fileName);
            String errorStr = fileElm.attributeValue("error");
            if (errorStr == null || errorStr.length() == 0) {
                String id = fileElm.attributeValue("id");
                String folderId = fileElm.attributeValue("folder_id");
                String shared = fileElm.attributeValue("shared");
                String publicName = fileElm.attributeValue("public_name");
                soapFileInfo.setFileId(id);
                soapFileInfo.setFolderId(folderId);
                if ("1".equals(shared)) {
                    soapFileInfo.setShared(true);
                } else {
                    soapFileInfo.setShared(false);
                }
                soapFileInfo.setSharedName(publicName);

            } else {
                uploadFileStatus.setErrorInfo(errorStr);
            }
            list.add(uploadFileStatus);
        }
        return list;
    }
}
