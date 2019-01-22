/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFile;
import com.xcase.box.transputs.GetFileInfoRequest;
import com.xcase.box.transputs.GetFileInfoResponse;
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
public class GetFileInfoMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets file info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting getFileInfo()");
        GetFileInfoResponse getFileInfoResponse = BoxResponseFactory.createGetFileInfoResponse();
        String apiKey = getFileInfoRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = getFileInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String authToken = getFileInfoRequest.getAuthToken();
        LOGGER.debug("authToken is " + authToken);
        String fileId = getFileInfoRequest.getFileId();
        LOGGER.debug("fileId is " + fileId);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            fileId = codec.encode(fileId, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("files");
            urlBuff.append("/" + fileId);
            String filesApiUrl = urlBuff.toString();
            LOGGER.debug("filesApiUrl is " + filesApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String fileInfoString = httpManager.doStringGet(filesApiUrl, headers, null);
                LOGGER.debug("fileInfoString is " + fileInfoString);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFileInfoResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element fileIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_FILE_ID);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(fileIdElm);
            actionElm.setText(BoxConstant.ACTION_NAME_GET_FILE_INFO);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            fileIdElm.setText(fileId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getFileInfoResponse.setStatus(status);
                if (BoxConstant.STATUS_S_GET_FILE_INFO.equals(status)) {
                    Element infoElm = responseElm.element(BoxConstant.PARAM_NAME_INFO);
                    BoxFile soapFileInfo = ConverterUtils.toBoxFile(infoElm);
                    getFileInfoResponse.setFile(soapFileInfo);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFileInfoResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getFileInfoResponse;
    }
}
