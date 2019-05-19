package com.xcase.intapp.cdsrefdata;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsrefdata.CDSRefDataExternalAPI;
import com.xcase.intapp.cdsrefdata.SimpleCDSRefDataImpl;
import com.xcase.intapp.cdsrefdata.constant.CDSRefDataConstant;
import com.xcase.intapp.cdsrefdata.factories.CDSRefDataRequestFactory;
import com.xcase.intapp.cdsrefdata.impl.simple.core.CDSRefDataConfigurationManager;
import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusRequest;
import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusResponse;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsRequest;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSRefDataApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        CDSRefDataExternalAPI cdsRefDataExternalAPI = new SimpleCDSRefDataImpl();
        LOGGER.debug("created cdsRefDataExternalAPI");
        try {
            generateTokenPair();
            String accessToken = CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.ACCESS_TOKEN);
            LOGGER.debug("about to get client statuses");
            GetClientStatusesRequest getClientStatusesRequest = CDSRefDataRequestFactory.createGetClientStatusesRequest(accessToken);
            LOGGER.debug("created getClientStatusesRequest");
            GetClientStatusesResponse getClientStatusesResponse = cdsRefDataExternalAPI.getClientStatuses(getClientStatusesRequest);
            LOGGER.debug("got client statuses");
            LOGGER.debug("about to create matter status");
            CreateMatterStatusRequest createMatterStatusRequest = CDSRefDataRequestFactory.createCreateMatterStatusRequest(accessToken);
            LOGGER.debug("created createMatterStatusRequest");
            createMatterStatusRequest.setEntityString("[{\"key\":\"ACT\",\"name\":\"Active\",\"active\":true,\"system\":false},{\"key\":\"INA\",\"name\":\"Inactive\",\"active\":true,\"system\":false}]");
            CreateMatterStatusResponse CreateMatterStatusResponse = cdsRefDataExternalAPI.createMatterStatus(createMatterStatusRequest);
            LOGGER.debug("created matter statuses");            
            LOGGER.debug("about to get matter statuses");
            GetMatterStatusesRequest getMatterStatusesRequest = CDSRefDataRequestFactory.createGetMatterStatusesRequest(accessToken);
            LOGGER.debug("created getMatterStatusesRequest");
            GetMatterStatusesResponse getMatterStatusesResponse = cdsRefDataExternalAPI.getMatterStatuses(getMatterStatusesRequest);
            LOGGER.debug("got matter statuses");
            LOGGER.debug("about to get find departments by key or name");
            FindDepartmentsRequest findDepartmentsRequest = CDSRefDataRequestFactory.createFindDepartmentsRequest(accessToken);
            LOGGER.debug("created findDepartmentsRequest");
            findDepartmentsRequest.setKey(null);
            findDepartmentsRequest.setName(null);
            FindDepartmentsResponse findDepartmentsResponse = cdsRefDataExternalAPI.findDepartments(findDepartmentsRequest);
            LOGGER.debug("found departments by key or name");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String cdsUsersClientID = CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.CONFIG_CLIENT_ID);
        String cdsUsersClientSecret = CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.CONFIG_CLIENT_SECRET);
        String cdsUsersSwaggerAPIURL = CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.CONFIG_SWAGGER_API_URL);
        String cdsUsersTokenURL = CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSRefDataConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", cdsUsersClientID));
        parameters.add(new BasicNameValuePair("client_secret", cdsUsersClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", cdsUsersTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSRefDataConstant.ACCESS_TOKEN, accessToken);
        CDSRefDataConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createCDSRefDataAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", cdsUsersSwaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        CDSRefDataConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSRefDataConstant.API_VERSION_URL, "https://" + host + basePath);
        CDSRefDataConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
    }

    public static Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public static Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public static Header createCDSRefDataAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSRefDataConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSRefDataConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
