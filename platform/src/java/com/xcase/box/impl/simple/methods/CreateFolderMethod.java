/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFolder;
import com.xcase.box.transputs.CreateFolderRequest;
import com.xcase.box.transputs.CreateFolderResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class CreateFolderMethod extends BaseBoxMethod {

    /**
     * log4j object.
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
     * @throws BoxException box exception
     */
    public CreateFolderResponse createFolder(CreateFolderRequest createFolderRequest) throws IOException, BoxException {
        LOGGER.debug("starting createFolder()");
        CreateFolderResponse createFolderResponse = BoxResponseFactory.createCreateFolderResponse();
        String apiKey = createFolderRequest.getApiKey();
        String accessToken = createFolderRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String authToken = createFolderRequest.getAuthToken();
        String name = createFolderRequest.getFolderName();
        String parentFolderId = createFolderRequest.getParentFolderId();
        boolean share = createFolderRequest.isShare();

        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
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
            Header[] headers = {header};
            String entityString = "{\"name\":\"" + name + "\", \"parent\": {\"id\": \"" + parentFolderId + "\"}}";
            LOGGER.debug("entityString is " + entityString);
            try {
                JsonElement jsonElement = httpManager.doJsonPost(foldersApiUrl, headers, null, entityString);
                LOGGER.info("done Json post");
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement typeElement = jsonObject.get("type");
                JsonElement idElement = jsonObject.get("id");
                JsonElement sequence_idElement = jsonObject.get("sequence_id");
                JsonElement nameElement = jsonObject.get("name");
                BoxFolder boxFolder = ConverterUtils.toBoxFolder(jsonObject);
                createFolderResponse.setFolder(boxFolder);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createFolderResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element parentIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_PARENT_ID);
            Element nameElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_NAME);
            Element shareElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_SHARE);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(parentIdElm);
            requestElm.add(nameElm);
            requestElm.add(shareElm);
            //
            actionElm.setText(BoxConstant.ACTION_NAME_CREATE_FOLDER);
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
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                createFolderResponse.setStatus(status);
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(BoxConstant.PARAM_NAME_FOLDER);
                    BoxFolder soapFolder = ConverterUtils.toBoxFolder(folderElm);
                    createFolderResponse.setFolder(soapFolder);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createFolderResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return createFolderResponse;

    }
}
