package com.xcase.intapp.cdsusers.impl.simple.methods;

import java.io.StringReader;
import java.lang.invoke.MethodHandles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsusers.factories.CDSUsersResponseFactory;
import com.xcase.intapp.cdsusers.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdsusers.transputs.GetClientSecurityResponse;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.objects.IntegrateDatabaseDatasource;
import com.xcase.integrate.objects.IntegrateDatasource;
import com.xcase.integrate.objects.IntegrateFtpDatasource;
import com.xcase.integrate.objects.IntegrateHttpDatasource;
import com.xcase.integrate.objects.IntegrateIntappCloudDatasource;
import com.xcase.integrate.objects.IntegrateLdapDatasource;
import com.xcase.integrate.objects.IntegrateMicrosoftGraphDatasource;
import com.xcase.integrate.objects.IntegrateNfsFileSystemDatasource;
import com.xcase.integrate.objects.IntegrateOdbcAccessDatasource;
import com.xcase.integrate.objects.IntegrateOdbcExcelDatasource;
import com.xcase.integrate.objects.IntegrateRemoteExecutionDatasource;
import com.xcase.integrate.objects.IntegrateSalesforceDatasource;
import com.xcase.integrate.objects.IntegrateSharePointDatasource;
import com.xcase.integrate.objects.IntegrateWebDavDatasource;
import com.xcase.integrate.objects.IntegrateWebServiceDatasource;
import com.xcase.integrate.objects.IntegrateWindowsShareFileSystemDatasource;
import com.xcase.integrate.transputs.GetDatasourceResponse;

public class GetClientSecurityMethod extends BaseCDSUsersMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest request) {
        LOGGER.debug("starting getClientSecurity()");
        GetClientSecurityResponse response = CDSUsersResponseFactory.createGetClientSecurityResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String clientId = request.getClientId();
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + request.getOperationPath() + CommonConstant.SLASH_STRING + clientId;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSUsersAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                JsonObject clientSecurityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }

}
