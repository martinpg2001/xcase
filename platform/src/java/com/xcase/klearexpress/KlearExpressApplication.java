package com.xcase.klearexpress;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.klearexpress.impl.simple.core.KlearExpressConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KlearExpressApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
    		String apiEventsURL = "https://api.klearexpress.com/staging/v1/events";
    		/* First get token */
    		String entityString = "{\n" + 
    				"\"eventMessage\": {\n" + 
    				"\"email\" : \"martin.gilchrist@klearexpress.com\",\n" + 
    				"\"hashedPassword\": \"Ng062010))))\",\n" + 
    				"\"typeOfUser\" : \"CUSTOMER_USER\"\n" + 
    				"},\n" + 
    				"\"eventType\": \"KXUSER_LOGIN\",\n" + 
    				"\"eventTime\": 1234\n" + 
    				"}\n" + 
    				"";
            LOGGER.debug("entityString is " + entityString);
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {contentTypeHeader};
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", apiEventsURL, headers, null, entityString, null);
            int responseCode = commonHttpResponse.getResponseCode();
            if (responseCode == 200) {
                try {
                    JsonElement jsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());;
                    if (!jsonElement.isJsonNull()) {
                        JsonObject jsonObject = (JsonObject) jsonElement;
                        JsonElement accessTokenElement = jsonObject.get("$.eventMessage.user.kxToken");
                        if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                            LOGGER.debug("access token element is not null");
                            KlearExpressConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                            LOGGER.debug("stored local config properties");
                        } else {
                            JsonElement errorElement = jsonObject.get("error");
                            JsonElement errorDescriptionElement = jsonObject.get("error_description");
                            LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                        }
                    } else {

                    }
                } catch (Exception e) {
                    throw new Exception("Failed to parse to a document.", e);
                }
            } else {
                LOGGER.debug("apiRequestFormat is unrecognized");
            }
        } catch (Exception e) {
            LOGGER.warn("exception getting KlearExpress token: " + e.getMessage());
        }

    }

	private static Header createContentTypeHeader() {
        return new BasicHeader("Content-Type","application/json");
	}
}
