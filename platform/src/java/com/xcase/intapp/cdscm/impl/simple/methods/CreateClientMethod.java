package com.xcase.intapp.cdscm.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdscm.transputs.CreateClientResponse;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateClientMethod extends BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientResponse createClient(CreateClientRequest request) {
        LOGGER.debug("starting createClient()");
        CreateClientResponse response = CDSCMResponseFactory.createCreateClientResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String clientId = request.getClientId();
            endPoint = baseVersionUrl + request.getOperationPath();
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
            Header[] headers = {acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader};
            String unreplacedEntityString = "{\"clientId\":\"{clientId}\",\"name\":\"Underture Science\",\"status\":\"ACTIVE\",\"description\":\"\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}";
            String entityString = unreplacedEntityString.replace("{clientId}", clientId);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, entityString, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 201) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (responseEntityString != null && !responseEntityString.isEmpty()) {
                	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                	JsonObject jsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                } else {
                	LOGGER.debug("responseEntityString is null or empty");
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
