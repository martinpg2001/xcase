/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.DeleteRequest;
import com.xcase.box.transputs.DeleteResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.DocumentException;

/**
 * @author martin
 *
 */
public class DeleteMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method deletes a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to delete, 'target_id' is id of a file or folder to be deleted.
     *
     * On a successful result, the status will be 's_delete_node'. If the result
     * wasn't successful, status field can be: 'e_delete_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param deleteRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException, BoxException {
        LOGGER.debug("starting delete()");
        DeleteResponse baseBoxResponse = BoxResponseFactory.createDeleteResponse();
        String accessToken = deleteRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String eTag = deleteRequest.getETag();
        boolean recursive = deleteRequest.getRecursive();
        String target = deleteRequest.getTarget();
        String targetId = deleteRequest.getTargetId();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("files" + CommonConstant.SLASH_STRING);
            if ("file".contains(target)) {

            } else {
                urlBuff = super.getApiUrl("folders" + CommonConstant.SLASH_STRING);
            }

            urlBuff.append(targetId);
            String filesApiUrl = urlBuff.toString();
            LOGGER.debug("filesApiUrl is " + filesApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header bearerHeader = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header ifMatchHeader = null;
            if (eTag != null) {
                ifMatchHeader = new BasicHeader("If-Match", eTag);
            }

            Header[] headers = {bearerHeader, ifMatchHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            if (recursive) {
                LOGGER.debug("recursive is true");
                //parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_RECURSIVE, "true"));
                filesApiUrl = filesApiUrl.concat(CommonConstant.QUESTION_MARK_STRING + BoxConstant.PARAM_NAME_RECURSIVE + CommonConstant.EQUALS_SIGN_STRING + BoxConstant.TRUE_STRING);
            }

            try {
                JsonElement jsonElement = httpManager.doJsonDelete(filesApiUrl, headers, parameters);
                if (jsonElement.isJsonNull()) {
                    LOGGER.debug("jsonElement is JsonNull");
                } else {
                    LOGGER.debug("jsonElement is not JsonNull");
                }
            } catch (DocumentException de) {
                BoxException be = new BoxException("Failed to parse to a document.", de);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            } catch (IOException ioe) {
                BoxException be = new BoxException("IOException thrown.", ioe);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            } catch (Exception e) {
                BoxException be = new BoxException("Exception thrown.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {

        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {

        } else {

        }

        return baseBoxResponse;
    }
}
