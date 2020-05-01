package com.xcase.slack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.slack.SlackExternalAPI;
import com.xcase.slack.SimpleSlackImpl;
import com.xcase.slack.constant.SlackConstant;
import com.xcase.slack.factories.SlackRequestFactory;
import com.xcase.slack.impl.simple.core.SlackConfigurationManager;
import com.xcase.slack.transputs.*;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlackApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            SlackExternalAPI slackExternalAPI = new SimpleSlackImpl();
            String apiEventsURL = SlackConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SlackConstant.LOCAL_API_URL);
            LOGGER.debug("apiEventsURL is " + apiEventsURL);
            String userEmail = SlackConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SlackConstant.LOCAL_USER_EMAIL);
            LOGGER.debug("userEmail is " + userEmail);
            String userPassword = SlackConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SlackConstant.LOCAL_USER_PASSWORD);
            LOGGER.debug("userPassword is " + userPassword);
            /* First, get access token */
            String accessToken = null;
            GetAccessTokenRequest getAccessTokenRequest = SlackRequestFactory.createGetAccessTokenRequest();
            getAccessTokenRequest.setAPIUrl(apiEventsURL);
            getAccessTokenRequest.setEntityRequest("{\n" +
                                                   "  \"email\" : \"" + userEmail + "\",\n" +
                                                   "  \"password\": \"" + userPassword + "\"\n" +
                                                   "}");
            GetAccessTokenResponse getAccessTokenResponse = slackExternalAPI.getAccessToken(getAccessTokenRequest);
            JsonObject eventMessageJsonObject = getAccessTokenResponse.getEventMessage();
            LOGGER.debug("got eventMessageJsonObject");
            JsonPrimitive kxTokenJsonPrimitive = eventMessageJsonObject.getAsJsonPrimitive("kxToken");
            LOGGER.debug("kxTokenJsonPrimitive is " + kxTokenJsonPrimitive);
            if (kxTokenJsonPrimitive != null && !kxTokenJsonPrimitive.isJsonNull()) {
                LOGGER.debug("kxTokenJsonPrimitive is not null");
                accessToken = kxTokenJsonPrimitive.getAsString();
                LOGGER.debug("accessToken is " + accessToken);
                SlackConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(SlackConstant.LOCAL_ACCESS_TOKEN, accessToken);
                SlackConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                LOGGER.debug("stored local config properties");
            } else {
                JsonElement errorElement = eventMessageJsonObject.get("error");
                JsonElement errorDescriptionElement = eventMessageJsonObject.get("error_description");
                LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
            }
        } catch (Exception e) {
            LOGGER.warn("exception invoking Slack operation: " + e.getMessage());
        }
    }
}
