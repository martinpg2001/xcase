/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFile;
import com.xcase.box.transputs.CreateFileRequest;
import com.xcase.box.transputs.CreateFileResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class CreateFileMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method creates a new file.
     *
     * 'parent_id' param is the id of a folder in which a new file will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createFileRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public CreateFileResponse createFile(CreateFileRequest createFileRequest) throws IOException, BoxException {
        LOGGER.debug("starting createFile()");
        CreateFileResponse createFileResponse = BoxResponseFactory.createCreateFileResponse();
        String accessToken = createFileRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String fileName = createFileRequest.getFileName();
        LOGGER.debug("fileName is " + fileName);
        String parentFolderId = createFileRequest.getParentFolderId();
        LOGGER.debug("parentFolderId is " + parentFolderId);
        String fileContent = createFileRequest.getContent();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            fileName = codec.encode(fileName, "ISO-8859-1");
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_PARENT_ID, parentFolderId));
            LOGGER.debug("added folderId parameter");
            try {
                LOGGER.debug("about to build filesApiUrl");
                StringBuffer urlBuff = super.getApiUploadUrl("files");
                urlBuff.append("/content");
                String filesApiUrl = urlBuff.toString();
                LOGGER.debug("filesApiUrl is " + filesApiUrl);
                HashMap<String, byte[]> byteArrayHashMap = new HashMap<String, byte[]>();
                byte[] contentByteArray = null;
                if (fileContent != null) {
                    contentByteArray = fileContent.getBytes();
                } else {
                    contentByteArray = "This is dummy text".getBytes();
                }

                byteArrayHashMap.put(fileName, contentByteArray);
                LOGGER.info("about to do multipart post");
                String result = httpManager.doMultipartByteArrayPost(filesApiUrl, byteArrayHashMap, headers, parameters);
                LOGGER.info("done multipart post");
                LOGGER.debug("result is " + result);
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(result);
                JsonElement totalCountElement = jsonObject.get("total_count");
                LOGGER.debug("totalCountElement is " + totalCountElement);
                JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
                JsonObject jsonFileObject = (JsonObject) entriesArray.get(0);
                LOGGER.debug("got file entry");
                JsonElement typeElement = jsonFileObject.get("type");
                LOGGER.debug("typeElement is " + typeElement.getAsString());
                JsonElement idElement = jsonFileObject.get("id");
                LOGGER.debug("idElement is " + idElement.getAsString());
                JsonElement sequence_idElement = jsonFileObject.get("sequence_id");
                LOGGER.debug("sequence_idElement is " + sequence_idElement.getAsString());
                JsonElement nameElement = jsonFileObject.get("name");
                LOGGER.debug("nameElement is " + nameElement.getAsString());
                BoxFile boxFile = ConverterUtils.toBoxFile(jsonFileObject);
                LOGGER.debug("converted jsonFileObject to BoxFile");
                createFileResponse.setFile(boxFile);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createFileResponse.getStatus());
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
            parentIdElm.setText(parentFolderId);
            nameElm.setText(fileName);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                createFileResponse.setStatus(status);
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(BoxConstant.PARAM_NAME_FOLDER);
//                    BoxFile soapFile = ConverterUtils.toBoxFolder(folderElm);
//                    createFileResponse.setFile(soapFile);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createFileResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return createFileResponse;
    }
}
