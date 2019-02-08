/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.ExportTagsRequest;
import com.xcase.box.transputs.ExportTagsResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class ExportTagsMethod extends BaseBoxMethod {

    /**
     * This method returns all the user's tags.
     *
     * On successful a result, you will receive 'export_tags_ok' and tag_xml
     * will be base64 encoded tags xml. After decoding tag_xml you will get xml
     * like this:
     *
     * {@code
     * <?xml version="1.0"?> <tags> <tag id="37"> music </tag> <tag id="38"> mp3
     * </tag> </tags>
     * }
     * 
     * If the result wasn't successful, status field can be:
     * not_logged_id, application_restricted.
     *
     * @param exportTagsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public ExportTagsResponse exportTags(ExportTagsRequest exportTagsRequest)
            throws IOException, BoxException {
        ExportTagsResponse exportTagsResponse = BoxResponseFactory
                .createExportTagsResponse();

        String apiKey = exportTagsRequest.getApiKey();
        String authToken = exportTagsRequest.getAuthToken();

        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_EXPORT_TAGS);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                exportTagsResponse.setStatus(status);
                if (BoxConstant.STATUS_EXPORT_TAGS_OK.equals(status)) {
                    Element tagsXMLElm = responseElm.element(BoxConstant.PARAM_NAME_TAGS);
                    List tagsList = ConverterUtils.transferTags2List(tagsXMLElm);
                    exportTagsResponse.setTagList(tagsList);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(exportTagsResponse.getStatus());
                throw be;
            }

        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML
                .equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            actionElm.setText(BoxConstant.ACTION_NAME_EXPORT_TAGS);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                exportTagsResponse.setStatus(status);
                if (BoxConstant.STATUS_EXPORT_TAGS_OK.equals(status)) {
                    Element tagsXMLElm = responseElm.element(BoxConstant.PARAM_NAME_TAGS);
                    List tagsList = ConverterUtils.transferTags2List(tagsXMLElm);
                    exportTagsResponse.setTagList(tagsList);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(exportTagsResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return exportTagsResponse;
    }
}
