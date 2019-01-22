/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.google.gson.*;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.utils.ConverterUtils;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.objects.SharepointFolder;
import com.xcase.sharepoint.transputs.CreateFolderRequest;
import com.xcase.sharepoint.transputs.CreateFolderResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class CreateFolderMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method creates a new folder.
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
     * @param createFolderRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public CreateFolderResponse createFolder(CreateFolderRequest createFolderRequest) throws IOException, SharepointException {
        LOGGER.debug("starting createFolder()");
        CreateFolderResponse createFolderResponse = SharepointResponseFactory.createCreateFolderResponse();
        String apiKey = createFolderRequest.getApiKey();
        String accessToken = createFolderRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String authToken = createFolderRequest.getAuthToken();
        String name = createFolderRequest.getFolderName();
        String parentFolderId = createFolderRequest.getParentFolderId();
        boolean share = createFolderRequest.isShare();

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            name = codec.encode(name, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            HttpPost postMethod = new HttpPost(foldersApiUrl);
            LOGGER.debug("created postMethod with URL " + foldersApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            postMethod.addHeader(header);
            LOGGER.debug("set request header");
            String bodyString = "{\"name\":\"" + name + "\", \"parent\": {\"id\": \"0\"}}";
            LOGGER.debug("bodyString is " + bodyString);
            postMethod.setEntity(new StringEntity(bodyString));
            LOGGER.debug("set request body is " + bodyString);
            String responseBodyString = null;
            try {
                HttpResponse httpResponse = httpManager.getHttpClient().execute(postMethod);
                int returnCode = httpResponse.getStatusLine().getStatusCode();
                LOGGER.debug("returnCode is " + returnCode);
                HttpEntity responseEntity = httpResponse.getEntity();
                if (responseEntity != null) {
                    responseBodyString = EntityUtils.toString(responseEntity);
                }

                LOGGER.debug("responseBodyString is " + responseBodyString);
                if (returnCode == 200 || returnCode == 201) {
                    LOGGER.debug("status is " + SharepointConstant.STATUS_CREATE_OK);
                    createFolderResponse.setStatus(SharepointConstant.STATUS_CREATE_OK);
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBodyString);
                    JsonElement typeElement = jsonObject.get("type");
                    JsonElement idElement = jsonObject.get("id");
                    JsonElement sequence_idElement = jsonObject.get("sequence_id");
                    JsonElement nameElement = jsonObject.get("name");
                    SharepointFolder sharepointFolder = ConverterUtils.toSharepointFolder(jsonObject);
                    createFolderResponse.setFolder(sharepointFolder);
                } else {
                    LOGGER.debug("returnCode is " + returnCode);
                }
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(createFolderResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            Element parentIdElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_PARENT_ID);
            Element nameElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_NAME);
            Element shareElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_SHARE);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(parentIdElm);
            requestElm.add(nameElm);
            requestElm.add(shareElm);
            //
            actionElm.setText(SharepointConstant.ACTION_NAME_CREATE_FOLDER);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            parentIdElm.setText(parentFolderId);
            nameElm.setText(name);
            if (share) {
                shareElm.setText("1");
            } else {
                shareElm.setText("0");
            }

            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                createFolderResponse.setStatus(status);
                if (SharepointConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(SharepointConstant.PARAM_NAME_FOLDER);
                    SharepointFolder soapFolder = ConverterUtils.toSharepointFolder(folderElm);
                    createFolderResponse.setFolder(soapFolder);
                }
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(createFolderResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return createFolderResponse;

    }
}
