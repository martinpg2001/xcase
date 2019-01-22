/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFolder;
import com.xcase.box.transputs.GetFolderItemsRequest;
import com.xcase.box.transputs.GetFolderItemsResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class GetFolderItemsMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets folder items for a specified folder.
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
     * @param getFolderItemsRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetFolderItemsResponse getFolderItems(GetFolderItemsRequest getFolderItemsRequest) throws IOException, BoxException {
        LOGGER.debug("starting getFolderItems()");
        GetFolderItemsResponse getFolderItemsResponse = BoxResponseFactory.createGetFolderItemsResponse();
        String apiKey = getFolderItemsRequest.getApiKey();
        String accessToken = getFolderItemsRequest.getAccessToken();
        String folderId = getFolderItemsRequest.getFolderId();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            urlBuff.append("/" + folderId + "/items");
            String folderItemsUrl = urlBuff.toString();
            LOGGER.debug("folderItemsUrl is " + folderItemsUrl);
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                JsonElement jsonElement = httpManager.doJsonGet(folderItemsUrl, headers, null);
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
                List<BoxAbstractFile> abstractBoxFileList = new ArrayList<BoxAbstractFile>();
                for (int i = 0; i < entriesArray.size(); i++) {
                    BoxAbstractFile boxAbstractFile = BoxObjectFactory.createBoxAbstractFile();
                    JsonObject abstractFileJsonObject = (JsonObject) entriesArray.get(i);
                    JsonElement typeElement = abstractFileJsonObject.get("type");
                    String type = typeElement.getAsString();
                    if (type.equalsIgnoreCase("folder")) {
                        boxAbstractFile.setFolder(true);
                    }

                    JsonElement idElement = abstractFileJsonObject.get("id");
                    String id = idElement.getAsString();
                    boxAbstractFile.setId(id);
                    JsonElement nameElement = abstractFileJsonObject.get("name");
                    String name = nameElement.getAsString();
                    boxAbstractFile.setName(name);
                    abstractBoxFileList.add(boxAbstractFile);
                }

                getFolderItemsResponse.setFolderItems(abstractBoxFileList);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("Failed to parse to a document.", e);
                be.setStatus(getFolderItemsResponse.getStatus());
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
            actionElm.setText(BoxConstant.ACTION_NAME_CREATE_FOLDER);
            apiKeyElm.setText(apiKey);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getFolderItemsResponse.setStatus(status);
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(BoxConstant.PARAM_NAME_FOLDER);
                    BoxFolder soapFolder = ConverterUtils.toBoxFolder(folderElm);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFolderItemsResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getFolderItemsResponse;
    }
}
