/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFolder;
import com.xcase.box.transputs.GetFolderInfoRequest;
import com.xcase.box.transputs.GetFolderInfoResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
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
public class GetFolderInfoMethod extends BaseBoxMethod {

    /**
     * log4j object.
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
     * @throws BoxException box exception
     */
    public GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting getFolderInfo()");
        GetFolderInfoResponse getFolderInfoResponse = BoxResponseFactory.createGetFolderInfoResponse();
        String apiKey = getFolderInfoRequest.getApiKey();
        String accessToken = getFolderInfoRequest.getAccessToken();
        String authToken = getFolderInfoRequest.getAuthToken();
        String name = getFolderInfoRequest.getFolderName();
        String folderId = getFolderInfoRequest.getFolderId();
        boolean share = getFolderInfoRequest.isShare();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            name = codec.encode(name, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            urlBuff.append("/" + folderId);
            String folderUrl = urlBuff.toString();
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getFolderInfoString = httpManager.doStringGet(folderUrl, headers, null);
                LOGGER.debug("folderInfoString is " + getFolderInfoString);
                JsonElement jsonElement = com.xcase.common.utils.ConverterUtils.parseStringToJson(getFolderInfoString);
                JsonObject jsonObject = (JsonObject) jsonElement;
                LOGGER.info("about to convert jsonObject to BoxFolder");
                BoxFolder boxFolder = ConverterUtils.toBoxFolder(jsonObject);
                LOGGER.info("converted jsonObject to BoxFolder");
                getFolderInfoResponse.setFolder(boxFolder);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFolderInfoResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element parentIdElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_PARENT_ID);
            Element nameElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_NAME);
            Element shareElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_SHARE);
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
//            parentIdElm.setText(parentFolderId);
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
                getFolderInfoResponse.setStatus(status);
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(BoxConstant.PARAM_NAME_FOLDER);
                    BoxFolder soapFolder = ConverterUtils.toBoxFolder(folderElm);
                    getFolderInfoResponse.setFolder(soapFolder);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFolderInfoResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getFolderInfoResponse;
    }
}
