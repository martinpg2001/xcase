/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.RenameRequest;
import com.xcase.box.transputs.RenameResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import org.apache.commons.codec.net.URLCodec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class RenameMethod extends BaseBoxMethod {

    /**
     * This method renames a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to rename, 'target_id' is the id of a file or folder to be renamed,
     * 'new_name' is the new name for a file or folder.
     *
     * On a successful result, status will be 's_rename_node'. If result wasn't
     * successful, stat's field can be: 'e_rename_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param renameRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public RenameResponse rename(RenameRequest renameRequest)
            throws IOException, BoxException {
        RenameResponse baseBoxResponse = BoxResponseFactory
                .createRenameResponse();

        String apiKey = renameRequest.getApiKey();
        String authToken = renameRequest.getAuthToken();
        String target = renameRequest.getTarget();
        String targetId = renameRequest.getTargetId();
        String newName = renameRequest.getNewName();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            URLCodec codec = new URLCodec();
            newName = codec.encode(newName, "ISO-8859-1");
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_RENAME);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_TARGET);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(target);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_TARGET_ID);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(targetId);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_NEW_NAME);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(newName);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element targetElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_TARGET);
            Element targetIdElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_TARGET_ID);
            Element newNameElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_NEW_NAME);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(targetElm);
            requestElm.add(targetIdElm);
            requestElm.add(newNameElm);
            actionElm.setText(BoxConstant.ACTION_NAME_RENAME);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            targetElm.setText(target);
            targetIdElm.setText(targetId);
            newNameElm.setText(newName);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return baseBoxResponse;

    }
}
