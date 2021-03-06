package com.xcase.intapp.cdscm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.CDSCMExternalAPI;
import com.xcase.intapp.cdscm.SimpleCDSCMImpl;
import com.xcase.intapp.cdscm.constant.CDSCMConstant;
import com.xcase.intapp.cdscm.factories.CDSCMRequestFactory;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.transputs.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSCMApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        CDSCMExternalAPI cdscmExternalAPI = new SimpleCDSCMImpl();
        LOGGER.debug("created CDSCMExternalAPI");
        try {
            generateTokenPair();
            String accessToken = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.ACCESS_TOKEN);
            String clientId = "66666";
            LOGGER.debug("clientId is " + clientId);
            /* Create client */
            LOGGER.debug("about to create client");
            CreateClientRequest createClientRequest = CDSCMRequestFactory.createCreateClientRequest(accessToken);
            LOGGER.debug("created createClientRequest");
            createClientRequest.setClientId(clientId);
            createClientRequest.setClientString("{\"clientId\":\"{clientId}\",\"name\":\"Underture Science\",\"status\":\"ACT\",\"description\":\"This is a test description.\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}".replace("{clientId}", clientId));
            CreateClientResponse createClientResponse = cdscmExternalAPI.createClient(createClientRequest);
            LOGGER.debug("created client");
            LOGGER.debug("about to create clients using patch");
            CreateClientsUsingPatchRequest createClientsUsingPatchRequest = CDSCMRequestFactory.createCreateClientsUsingPatchRequest(accessToken);
            LOGGER.debug("created createMattersUsingPatchRequest");
            List<String> clientsList = new ArrayList<String>();
            clientId = "66667";
            String client66667 = "{\"clientId\":\"{clientId}\",\"name\":\"Underture Science\",\"status\":\"ACT\",\"description\":\"This is a test description.\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}".replace("{clientId}", clientId);
            clientsList.add(client66667);
            clientId = "66668";
            String client66668 = "{\"clientId\":\"{clientId}\",\"name\":\"Underture Science\",\"status\":\"ACT\",\"description\":\"This is a test description.\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}".replace("{clientId}", clientId);
            clientsList.add(client66668);
            String[] clientsArray = clientsList.toArray(new String[0]);
            createClientsUsingPatchRequest.setClients(clientsArray);
            CreateClientsUsingPatchResponse createClientsUsingPatchResponse = cdscmExternalAPI.createClientsUsingPatch(createClientsUsingPatchRequest);
            LOGGER.debug("created clients using patch");
            /* Get client */
            clientId = "66666";
            LOGGER.debug("about to get client");
            GetClientRequest getClientRequest = CDSCMRequestFactory.createGetClientRequest(accessToken);
            LOGGER.debug("created getClientRequest");
            getClientRequest.setClientId(clientId);
            GetClientResponse getClientResponse = cdscmExternalAPI.getClient(getClientRequest);
            LOGGER.debug("got client");
            /* Create client security */
            LOGGER.debug("about to put client security");
            PutClientSecurityRequest putClientSecurityRequest = CDSCMRequestFactory.createPutClientSecurityRequest(accessToken);
            LOGGER.debug("created putClientSecurityRequest");
            putClientSecurityRequest.setClientId(clientId);
            putClientSecurityRequest.setClientSecurity("{\"defaultAccess\":255,\"users\":[{\"userId\":\"ADMIN\",\"access\":255},{\"userId\":\"martin.gilchrist@xcase.com\",\"access\":254}]}");
            PutClientSecurityResponse putClientSecurityResponse = cdscmExternalAPI.putClientSecurity(putClientSecurityRequest);
            LOGGER.debug("put client security");
            /* Get client security */
            LOGGER.debug("about to get client security");
            GetClientSecurityRequest getClientSecurityRequest = CDSCMRequestFactory.createGetClientSecurityRequest(accessToken);
            LOGGER.debug("created getClientSecurityRequest");
            getClientSecurityRequest.setClientId(clientId);
            GetClientSecurityResponse getClientSecurityResponse = cdscmExternalAPI.getClientSecurity(getClientSecurityRequest);
            LOGGER.debug("got client security");
            /* Check client security */
            LOGGER.debug("about to check client security");
            CheckClientSecurityRequest checkClientSecurityRequest = CDSCMRequestFactory.createCheckClientSecurityRequest(accessToken);
            LOGGER.debug("created checkClientSecurityRequest");
            String[] idsArray = {"66666"};
            checkClientSecurityRequest.setIdsArray(idsArray);
            CheckClientSecurityResponse checkClientSecurityResponse = cdscmExternalAPI.checkClientSecurity(checkClientSecurityRequest);
            LOGGER.debug("checked client security");
            LOGGER.debug("about to delete client security");
            DeleteClientSecurityRequest deleteClientSecurityRequest = CDSCMRequestFactory.createDeleteClientSecurityRequest(accessToken);
            LOGGER.debug("created deleteClientSecurityRequest");
            deleteClientSecurityRequest.setClientId(clientId);
            DeleteClientSecurityResponse deleteClientSecurityResponse = cdscmExternalAPI.deleteClientSecurity(deleteClientSecurityRequest);
            LOGGER.debug("deleted client security");
            getClientSecurityResponse = cdscmExternalAPI.getClientSecurity(getClientSecurityRequest);
            LOGGER.debug("got client security");
            /* Get clients */
            LOGGER.debug("about to get clients");
            GetClientsRequest getClientsRequest = CDSCMRequestFactory.createGetClientsRequest(accessToken);
            LOGGER.debug("created getClientsRequest");
            GetClientsResponse getClientsResponse = cdscmExternalAPI.getClients(getClientsRequest);
            LOGGER.debug("got clients");
            /* Get clients modified since yesterday */
            LOGGER.debug("about to get clients modified since yesterday");
            GetClientsModifiedSinceDateRequest getClientsModifiedSinceDateRequest = CDSCMRequestFactory.createGetClientsModifiedSinceDateRequest(accessToken);
            LOGGER.debug("created getClientsModifiedSinceDateRequest");
            Date date = new Date(new Date().getTime() - 24*3600*1000);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String since = formatter.format(date);
            LOGGER.debug("since is " + since);
            getClientsModifiedSinceDateRequest.setSince(since);
            GetClientsModifiedSinceDateResponse getClientsModifiedSinceDateResponse = cdscmExternalAPI.getClientsModifiedSinceDate(getClientsModifiedSinceDateRequest);
            LOGGER.debug("got clients modified since yesterday");
            /* Publish clients */
            LOGGER.debug("about to publish clients");
            PublishClientsRequest publishClientsRequest = CDSCMRequestFactory.createPublishClientsRequest(accessToken);
            LOGGER.debug("created publishClientsRequest");
            String[] publishClientsArray = {"66666","66667","66668"};
            String topicName = "MartinTopic";
            publishClientsRequest.setClientsArray(publishClientsArray);
            publishClientsRequest.setTopicName(topicName);
            PublishClientsResponse publishClientsResponse = cdscmExternalAPI.publishClients(publishClientsRequest);
            LOGGER.debug("published clients");
            /* Create matter */
            String matterId = "0001";
            LOGGER.debug("about to create matter");
            CreateMatterRequest createMatterRequest = CDSCMRequestFactory.createCreateMatterRequest(accessToken);
            LOGGER.debug("created createMatterRequest");
            createMatterRequest.setClientId(clientId);
            createMatterRequest.setMatterId(matterId);
            createMatterRequest.setEntityString("{\"matterId\": \"{matterId}\",\"name\": \"Asbestos Litigation\",\"status\": \"ACT\",\"shortDescription\": \"This is short matter description.\",\"description\": \"This is a test description.\",\"lastBillOn\": null,\"lastTimeEntryOn\": null,\"openedOn\": null,\"closedOn\": null,\"organizationUnitId\": null,\"practiceArea\": null,\"currencyIsoCode\": null,\"office\": null,\"department\": null,\"matterPersons\": null,\"externalIdentifiers\": null,\"rounding\": null,\"timeNote\": null,\"billableStatus\": null,\"lcidDictionary\": null,\"ebillinghubValidation\": null,\"clientId\": \"{clientId}\",\"timelinks\": null,\"_pricingAppData\": null,\"_experienceAppData\": null,\"_timeAppData\": null}".replace("{clientId}", clientId).replace("{matterId}", matterId));
            CreateMatterResponse createMatterResponse = cdscmExternalAPI.createMatter(createMatterRequest);
            LOGGER.debug("created matter");
            /* Create matter */
            matterId = "0002";
            LOGGER.debug("about to create matter");
            createMatterRequest = CDSCMRequestFactory.createCreateMatterRequest(accessToken);
            LOGGER.debug("created createMatterRequest");
            createMatterRequest.setClientId(clientId);
            createMatterRequest.setMatterId(matterId);
            createMatterRequest.setEntityString("{\"matterId\": \"{matterId}\",\"name\": \"Domestos Litigation\",\"status\": \"ACT\",\"shortDescription\": \"This is short matter description.\",\"description\": \"This is a test description.\",\"lastBillOn\": null,\"lastTimeEntryOn\": null,\"openedOn\": null,\"closedOn\": null,\"organizationUnitId\": null,\"practiceArea\": null,\"currencyIsoCode\": null,\"office\": null,\"department\": null,\"matterPersons\": null,\"externalIdentifiers\": null,\"rounding\": null,\"timeNote\": null,\"billableStatus\": null,\"lcidDictionary\": null,\"ebillinghubValidation\": null,\"clientId\": \"{clientId}\",\"timelinks\": null,\"_pricingAppData\": null,\"_experienceAppData\": null,\"_timeAppData\": null}".replace("{clientId}", clientId).replace("{matterId}", matterId));
            createMatterResponse = cdscmExternalAPI.createMatter(createMatterRequest);
            LOGGER.debug("created matter");
            LOGGER.debug("about to create matters using patch");
            CreateMattersUsingPatchRequest createMattersUsingPatchRequest = CDSCMRequestFactory.createCreateMattersUsingPatchRequest(accessToken);
            LOGGER.debug("created createMattersUsingPatchRequest");
            createMattersUsingPatchRequest.setClientId(clientId);
            List<String> mattersList = new ArrayList<String>();
            matterId = "0003";
            String matter0003 = "{\"matterId\":\"{matterId}\",\"name\":\"Domestos Litigation\",\"status\":\"ACT\",\"shortDescription\":\"This is short matter description.\",\"description\":\"This is a test description.\",\"lastBillOn\":null,\"lastTimeEntryOn\":null,\"openedOn\":null,\"closedOn\":null,\"organizationUnitId\":null,\"practiceArea\":null,\"currencyIsoCode\":null,\"office\":null,\"department\":null,\"matterPersons\":null,\"externalIdentifiers\":null,\"rounding\":null,\"timeNote\":null,\"billableStatus\":null,\"lcidDictionary\": null,\"ebillinghubValidation\":null,\"clientId\":\"{clientId}\",\"timelinks\":null,\"_pricingAppData\": null,\"_experienceAppData\":null,\"_timeAppData\":null}".replace("{clientId}", clientId).replace("{matterId}", matterId);
            mattersList.add(matter0003);
            matterId = "0004";
            String matter0004 = "{\"matterId\":\"{matterId}\",\"name\":\"Domestos Litigation\",\"status\":\"ACT\",\"shortDescription\":\"This is short matter description.\",\"description\":\"This is a test description.\",\"lastBillOn\":null,\"lastTimeEntryOn\":null,\"openedOn\":null,\"closedOn\":null,\"organizationUnitId\":null,\"practiceArea\":null,\"currencyIsoCode\":null,\"office\":null,\"department\":null,\"matterPersons\":null,\"externalIdentifiers\":null,\"rounding\":null,\"timeNote\":null,\"billableStatus\":null,\"lcidDictionary\": null,\"ebillinghubValidation\":null,\"clientId\":\"{clientId}\",\"timelinks\":null,\"_pricingAppData\": null,\"_experienceAppData\":null,\"_timeAppData\":null}".replace("{clientId}", clientId).replace("{matterId}", matterId);
            mattersList.add(matter0004);
            String[] mattersArray = mattersList.toArray(new String[0]);
            createMattersUsingPatchRequest.setMatters(mattersArray);
            CreateMattersUsingPatchResponse createMattersUsingPatchResponse = cdscmExternalAPI.createMattersUsingPatch(createMattersUsingPatchRequest);
            LOGGER.debug("created matters using patch");
            /* Get matters */
            LOGGER.debug("about to get matters");
            GetMattersRequest getMattersRequest = CDSCMRequestFactory.createGetMattersRequest(accessToken);
            LOGGER.debug("created getMattersRequest");
            getMattersRequest.setClientId(clientId);
            GetMattersResponse getMattersResponse = cdscmExternalAPI.getMatters(getMattersRequest);
            LOGGER.debug("got matters");
            /* Get matter */
            matterId = "0001";
            LOGGER.debug("about to get matter");
            GetMatterRequest getMatterRequest = CDSCMRequestFactory.createGetMatterRequest(accessToken);
            LOGGER.debug("created getMatterRequest");
            getMatterRequest.setClientId(clientId);
            getMatterRequest.setMatterId(matterId);
            GetMatterResponse getMatterResponse = cdscmExternalAPI.getMatter(getMatterRequest);
            LOGGER.debug("got matter");
            LOGGER.debug("about to get matter by key");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
            JsonObject matterJsonObject = (JsonObject) ConverterUtils.parseStringToJson(getMatterResponse.getEntityString());
            String matterKey = matterJsonObject.getAsJsonPrimitive("key").getAsString();
            LOGGER.debug("matterKey is " + matterKey);
            getMatterRequest = CDSCMRequestFactory.createGetMatterRequest(accessToken);
            LOGGER.debug("created getMatterRequest");
            getMatterRequest.setClientId(null);
            getMatterRequest.setMatterId(null);
            getMatterRequest.setMatterKey(matterKey);
            getMatterResponse = cdscmExternalAPI.getMatter(getMatterRequest);
            LOGGER.debug("got matter by key");
            /* Publish matters */
            LOGGER.debug("about to publish matters");
            PublishMattersRequest publishMattersRequest = CDSCMRequestFactory.createPublishMattersRequest(accessToken);
            LOGGER.debug("created publishMattersRequest");
            String[] keysArray = {matterKey};
            String mattersTopicName = "MartinTopic";
            publishMattersRequest.setKeysArray(keysArray);
            publishMattersRequest.setTopicName(mattersTopicName);
            PublishMattersResponse publishMattersResponse = cdscmExternalAPI.publishMatters(publishMattersRequest);
            LOGGER.debug("published matters");
            /* Create matter security */
            LOGGER.debug("about to put matter security");
            PutMatterSecurityRequest putMatterSecurityRequest = CDSCMRequestFactory.createPutMatterSecurityRequest(accessToken);
            LOGGER.debug("created getClientSecurityRequest");
            putMatterSecurityRequest.setClientId(clientId);
            putMatterSecurityRequest.setMatterId(matterId);
            putMatterSecurityRequest.setMatterSecurity("{\"defaultAccess\":255,\"users\":[{\"userId\":\"ADMIN\",\"access\":255},{\"userId\":\"xmartin.gilchrist@xcase.com\",\"access\":254}]}");
            PutMatterSecurityResponse putMatterSecurityResponse = cdscmExternalAPI.putMatterSecurity(putMatterSecurityRequest);
            LOGGER.debug("put matter security");
            /* Get matter security */
            LOGGER.debug("about to get matter security");
            GetMatterSecurityRequest getMatterSecurityRequest = CDSCMRequestFactory.createGetMatterSecurityRequest(accessToken);
            LOGGER.debug("created getMatterSecurityRequest");
            getMatterSecurityRequest.setClientId(clientId);
            getMatterSecurityRequest.setMatterId(matterId);
            GetMatterSecurityResponse getMatterSecurityResponse = cdscmExternalAPI.getMatterSecurity(getMatterSecurityRequest);
            LOGGER.debug("got matter security");
            LOGGER.debug("about to delete matter security");
            DeleteMatterSecurityRequest deleteMatterSecurityRequest = CDSCMRequestFactory.createDeleteMatterSecurityRequest(accessToken);
            LOGGER.debug("created deleteMatterSecurityRequest");
            deleteMatterSecurityRequest.setClientId(clientId);
            deleteMatterSecurityRequest.setMatterId(matterId);
            DeleteMatterSecurityResponse deleteMatterSecurityResponse = cdscmExternalAPI.deleteMatterSecurity(deleteMatterSecurityRequest);
            LOGGER.debug("deleted matter security");
            /* Get matter security */
            LOGGER.debug("about to get matter security");
            getMatterSecurityResponse = cdscmExternalAPI.getMatterSecurity(getMatterSecurityRequest);
            LOGGER.debug("got matter security");
            /* Get matters modified since yesterday */
            LOGGER.debug("about to get matters modified since yesterday");
            GetMattersModifiedSinceDateRequest getMattersModifiedSinceDateRequest = CDSCMRequestFactory.createGetMattersModifiedSinceDateRequest(accessToken);
            LOGGER.debug("created getMattersModifiedSinceDateRequest");
            getMattersModifiedSinceDateRequest.setSince(since);
            GetMattersModifiedSinceDateResponse getMattersModifiedSinceDateResponse = cdscmExternalAPI.getMattersModifiedSinceDate(getMattersModifiedSinceDateRequest);
            LOGGER.debug("got matters modified since yesterday");
            /* Delete matters */
            LOGGER.debug("about to delete matters");
            DeleteMatterRequest deleteMatterRequest = CDSCMRequestFactory.createDeleteMatterRequest(accessToken);
            LOGGER.debug("created deleteMatterRequest");
            deleteMatterRequest.setClientId(clientId);
            deleteMatterRequest.setMatterId(matterId);
            DeleteMatterResponse deleteMatterResponse = cdscmExternalAPI.deleteMatter(deleteMatterRequest);
            deleteMatterRequest.setMatterId("0002");
            deleteMatterResponse = cdscmExternalAPI.deleteMatter(deleteMatterRequest);
            deleteMatterRequest.setMatterId("0003");
            deleteMatterResponse = cdscmExternalAPI.deleteMatter(deleteMatterRequest);
            deleteMatterRequest.setMatterId("0004");
            deleteMatterResponse = cdscmExternalAPI.deleteMatter(deleteMatterRequest);
            LOGGER.debug("deleted matters");
            /* Delete client */
            LOGGER.debug("about to delete clients");
            DeleteClientRequest deleteClientRequest = CDSCMRequestFactory.createDeleteClientRequest(accessToken);
            LOGGER.debug("created deleteClientRequest");
            deleteClientRequest.setClientId(clientId);
            DeleteClientResponse deleteClientResponse = cdscmExternalAPI.deleteClient(deleteClientRequest);
            clientId = "66667";
            deleteClientRequest.setClientId(clientId);
            deleteClientResponse = cdscmExternalAPI.deleteClient(deleteClientRequest);
            clientId = "66668";
            deleteClientRequest.setClientId(clientId);
            deleteClientResponse = cdscmExternalAPI.deleteClient(deleteClientRequest);
            LOGGER.debug("deleted clients");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String CDSCMClientID = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_CLIENT_ID);
        String CDSCMClientSecret = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_CLIENT_SECRET);
        String CDSCMSwaggerAPIURL = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_SWAGGER_API_URL);
        String CDSCMTokenURL = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", CDSCMClientID));
        parameters.add(new BasicNameValuePair("client_secret", CDSCMClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", CDSCMTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSCMConstant.ACCESS_TOKEN, accessToken);
        CDSCMConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        LOGGER.debug("stored local config properties");
        Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", CDSCMSwaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSCMConstant.API_VERSION_URL, "https://" + host + basePath);
        CDSCMConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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

    public static Header createCDSCMAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSCMConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSCMConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
