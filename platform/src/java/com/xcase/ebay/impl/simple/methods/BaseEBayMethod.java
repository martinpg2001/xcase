package com.xcase.ebay.impl.simple.methods;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.ConfigurationManager;
import com.xcase.ebay.constant.EBayConstant;
import com.xcase.ebay.impl.simple.core.EBayConfigurationManager;

public class BaseEBayMethod {

	public String apiVersionUrl;

	/**
	 * log4j object.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * core http manager.
	 */
	protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();

	/**
	 * the configuration.
	 */
	protected Properties ebayConfig = EBayConfigurationManager.getConfigurationManager().getConfig();

	/**
	 * the configuration.
	 */
	protected Properties localCommonConfig = ConfigurationManager.getConfigurationManager().getLocalConfig();

	/**
	 * the configuration.
	 */
	protected Properties localEBayConfig = EBayConfigurationManager.getConfigurationManager()
			.getLocalConfig();

	/**
	 * API Request Format, it's static, so no need to read each time.
	 */
	protected String apiRequestFormat;

	/**
	 * API Version, it's static, so no need to read each time.
	 */
	protected String apiVersion;

	/**
	 * Authentication header name, it's static, so no need to read each time.
	 */
	protected String authenticationHeader;

	/**
	 * API SOAP URL, it's static, so no need to read each time.
	 */
	protected String baseUrl;

	/**
	*
	*/
	public BaseEBayMethod() {
        LOGGER.debug("Starting BaseEBayMethod()");
		this.authenticationHeader = ebayConfig.getProperty(EBayConstant.CONFIG_API_AUTHENTICATION_HEADER);
        LOGGER.debug("authenticationHeader is " + authenticationHeader);
		this.apiVersion = localEBayConfig.getProperty(EBayConstant.CONFIG_API_VERSION);
        LOGGER.debug("apiVersion is " + apiVersion);
		this.apiRequestFormat = localEBayConfig.getProperty(EBayConstant.CONFIG_API_REQUEST_FORMAT);
        LOGGER.debug("apiRequestFormat is " + apiRequestFormat);
		this.apiVersionUrl = getAPIVersionUrl().toString();
        LOGGER.debug("apiVersionUrl is " + apiVersionUrl);
	}

	private StringBuffer getAPIVersionUrl() {
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(this.baseUrl);
        urlBuf.append(CommonConstant.SLASH_STRING);
        urlBuf.append(this.apiVersion);
        return urlBuf;
	}
	
    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/x-www-form-urlencoded";
        if ("json".equals(this.apiRequestFormat)) {
//            contentTypeHeader = "application/json";
        } else if ("xml".equals(this.apiRequestFormat)) {

        } else {
            LOGGER.warn("unexpected apiRequestFormat: " + this.apiRequestFormat);
        }

        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createContentTypeHeader(String contentType) {
        String contentTypeHeader = "application/xml";
        if ("json".equals(contentType)) {
            contentTypeHeader = "application/json";
        } else if ("xml".equals(contentType)) {

        } else {
            LOGGER.warn("unexpected apiRequestFormat: " + this.apiRequestFormat);
        }

        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }
}
