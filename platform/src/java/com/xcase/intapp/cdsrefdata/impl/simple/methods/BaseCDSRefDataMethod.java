package com.xcase.intapp.cdsrefdata.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdscm.constant.CDSCMConstant;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdsrefdata.constant.CDSRefDataConstant;
import com.xcase.intapp.cdsrefdata.impl.simple.core.CDSRefDataConfigurationManager;
import com.xcase.intapp.cdsrefdata.transputs.CDSRefDataResponse;
import com.xcase.integrate.transputs.IntegrateResponse;

import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseCDSRefDataMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();

    public String endPoint;

    public String getAPIVersionUrl() {
        return CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.API_VERSION_URL);
    }

    public Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createCDSRefDataAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSRefDataConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSRefDataConstant.CONFIG_API_AUTHENTICATION_HEADER), "Bearer " + accessToken);
    }

    public void handleUnexpectedResponseCode(CDSRefDataResponse response, CommonHttpResponse commonHttpResponse) {
        LOGGER.warn("unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setMessage("Unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        response.setStatus(commonHttpResponse.getStatusLine().getReasonPhrase());
    }

    public void handleUnexpectedException(CDSRefDataResponse response, Exception e) {
        LOGGER.warn("exception invoking API: " + e.getMessage());
        response.setMessage("Exception invoking API: " + e.getMessage());
        response.setStatus("FAIL");
    }
}
