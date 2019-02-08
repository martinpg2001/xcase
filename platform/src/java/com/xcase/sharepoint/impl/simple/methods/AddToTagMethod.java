/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.AddToTagRequest;
import com.xcase.sharepoint.transputs.AddToTagResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class AddToTagMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method adds file or folder to tags list. 'target' param can be
     * either 'file' or 'folder' depending on what do you want to add,
     * 'target_id' is the id of a file or folder to be added, 'tags' array of
     * tags where target will be added.
     *
     * On successful a result, you will receive 'addtotag_ok'. If the result
     * wasn't successful, status field can be: addtotag_error.
     *
     * @param addToTagRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException box exception
     */
    public AddToTagResponse addToTag(AddToTagRequest addToTagRequest) throws IOException, SharepointException {
        LOGGER.debug("starting addToTag()");
        AddToTagResponse baseBoxResponse = SharepointResponseFactory.createAddToTagResponse();
        String apiKey = addToTagRequest.getApiKey();
        String authToken = addToTagRequest.getAuthToken();
        String target = addToTagRequest.getTarget();
        String targetId = addToTagRequest.getTargetId();
        String[] tags = addToTagRequest.getTags();

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            if (tags != null) {
                for (int i = 0; i < tags.length; i++) {
                    String tag = tags[i];
                    tag = codec.encode(tag, "ISO-8859-1");
                    tags[i] = tag;
                }
            }

            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_ADD_TO_TAG);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            if (tags == null) {
                urlBuff.append(CommonConstant.AND_SIGN_STRING);
                urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_TAGS);
                urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            } else {
                for (int i = 0; i < tags.length; i++) {
                    String tag = tags[i];
                    urlBuff.append(CommonConstant.AND_SIGN_STRING);
                    urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_TAGS);
                    urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
                    urlBuff.append(tag);
                }
            }

            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_TARGET);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(target);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_TARGET_ID);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(targetId);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("Failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            Element tagsElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_TAGS);
            Element targetElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_TARGET);
            Element targetIdElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_TARGET_ID);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(tagsElm);
            requestElm.add(targetElm);
            requestElm.add(targetIdElm);
            //
            actionElm.setText(SharepointConstant.ACTION_NAME_ADD_TO_TAG);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            targetElm.setText(target);
            targetIdElm.setText(targetId);
            if (tags != null) {
                for (int i = 0; i < tags.length; i++) {
                    String tag = tags[i];
                    Element tagElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ITEM);
                    tagElm.setText(tag);
                    tagsElm.add(tagElm);
                }
            }

            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return baseBoxResponse;
    }
}
