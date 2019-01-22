/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.*;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetDatasourceRequest;
import com.xcase.integrate.transputs.GetDatasourceResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetDatasourceMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetDatasourceResponse getDatasource(GetDatasourceRequest request) {
        LOGGER.debug("starting getDatasource()");
        GetDatasourceResponse response = IntegrateResponseFactory.createGetDatasourceResponse();
        LOGGER.debug("created getDatasourceResponse");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "datasources";
            String datasourceType = request.getDatasourceType();
            LOGGER.debug("datasourceType is " + datasourceType);
            Integer datasourceId = request.getDatasourceId();
            LOGGER.debug("datasourceId is " + datasourceId);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + datasourceId;
            String encryptionKey = request.getEncryptionKey();
            LOGGER.debug("encryptionKey is " + encryptionKey);
            if (encryptionKey != null) {
                endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING + "encryption_key" + CommonConstant.EQUALS_SIGN_STRING + encryptionKey;
            }
            
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
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
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject datasourceJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    IntegrateDatasource datasource = null;
                    switch (datasourceType) {
                        case "database":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateDatabaseDatasource.class);
                            break;
                        case "ftp":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateFtpDatasource.class);
                            break;
                        case "http":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateHttpDatasource.class);
                            break;
                        case "intappcloud":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateIntappCloudDatasource.class);
                            break;                            
                        case "ldap":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateLdapDatasource.class);
                            break;                              
                        case "microsoftgraph":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateMicrosoftGraphDatasource.class);
                            break;
                        case "nfsfilesystem":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateNfsFileSystemDatasource.class);
                            break;
                        case "odbcaccess":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateOdbcAccessDatasource.class);
                            break;
                        case "odbcexcel":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateOdbcExcelDatasource.class);
                            break;
                        case "remoteexecution":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateRemoteExecutionDatasource.class);
                            break;
                        case "salesforce":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateSalesforceDatasource.class);
                            break;
                        case "sharepoint":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateSharePointDatasource.class);
                            break;
                        case "webservice":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateWebServiceDatasource.class);
                            break;
                        case "webdav":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateWebDavDatasource.class);
                            break;
                        case "windowssharefilesystem":
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateWindowsShareFileSystemDatasource.class);
                            break;
                        default:
                            LOGGER.warn("unexpected datasourceType value: " + datasourceType);
                            datasource = gson.fromJson(datasourceJsonObject, IntegrateDatasource.class);
                            break;
                    }
                    
                    response.setDatasource(datasource);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    Class datasourceClass = null;
                    switch (datasourceType) {
                        case "database":
                            datasourceClass = IntegrateDatabaseDatasource.class;
                            break;
                        case "ftp":
                            datasourceClass = IntegrateFtpDatasource.class;
                        case "http":
                            datasourceClass = IntegrateHttpDatasource.class;
                            break;
                        case "IntappCloud":
                        case "ldap":
                        case "MicrosoftGraph":
                        case "RemoteExecution":
                        case "WebService":
                        case "WebDAV":
                        case "WindowsShareFileSystem":
                            datasourceClass = IntegrateDatasource.class;
                            break;
                        default:
                            LOGGER.warn("unexpected datasourceType value: " + datasourceType);
                            datasourceClass = IntegrateDatasource.class;
                            break;
                    }

                    JAXBContext jaxbContext = JAXBContext.newInstance(datasourceClass);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateDatasource datasource = (IntegrateDatasource) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created datasource");
                    response.setDatasource(datasource);
                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
                    response.setMessage("Unexpected API request format: " + this.apiRequestFormat);
                    response.setStatus("FAIL");
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
