/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate;

import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateRequestFactory;
import com.xcase.integrate.impl.simple.core.IntegrateConfigurationManager;
import com.xcase.integrate.objects.*;
import com.xcase.integrate.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class IntegrateApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        /**
         * Access token from local configuration file
         */
        String accessToken = IntegrateConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(IntegrateConstant.ACCESS_TOKEN);
        LOGGER.debug("accessToken is " + accessToken);
        IntegrateExternalAPI integrateExternalAPI = new SimpleIntegrateImpl();
        LOGGER.debug("created integrateExternalAPI");
        try {
            LOGGER.debug("about to get all datasources");
            GetAllDatasourcesRequest getAllDatasourcesRequest = IntegrateRequestFactory.createGetAllDatasourcesRequest(accessToken);
            LOGGER.debug("created getAllDatasourcesRequest");
            GetAllDatasourcesResponse getAllDatasourcesResponse = integrateExternalAPI.getDatasources(getAllDatasourcesRequest);
            LOGGER.debug("got all datasources");
            LOGGER.debug("about to get all rules");
            GetAllRulesRequest getAllRulesRequest = IntegrateRequestFactory.createGetAllRulesRequest(accessToken);
            LOGGER.debug("created getAllRulesRequest");
            GetAllRulesResponse getAllRulesResponse = integrateExternalAPI.getAllRules(getAllRulesRequest);
            LOGGER.debug("got all rules");
            LOGGER.debug("about to search rules");
            SearchRulesRequest searchRulesRequest = IntegrateRequestFactory.createSearchRulesRequest(accessToken);
            LOGGER.debug("created searchRulesRequest");
            //searchRulesRequest.setStatus("Inactive,Active,Simulated,Unmapped");
            searchRulesRequest.setStatus("Active");
            SearchRulesResponse searchRulesResponse = integrateExternalAPI.searchRules(searchRulesRequest);
            LOGGER.debug("searched rules");
            IntegrateRule[] rules = searchRulesResponse.getRules();
            for (IntegrateRule rule : rules) {
                LOGGER.debug("rule name is " + rule.name);
            }
            
            LOGGER.debug("about to get all logs");
            GetLogsRequest getLogsRequest = IntegrateRequestFactory.createGetLogsRequest(accessToken, null);
            LOGGER.debug("created getLogsRequest");
            getLogsRequest.setLogType("rule");
            getLogsRequest.setStatusString("status=OK");
            GetLogsResponse getLogsResponse = integrateExternalAPI.getLogs(getLogsRequest);
            LOGGER.debug("got all logs");
            LOGGER.debug("about to create datasource");
            String datasource;
            if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(IntegrateConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(IntegrateConstant.CONFIG_API_REQUEST_FORMAT))) {
                datasource = "{\n" 
                        + "      \"name\": \"b01f1550-b3ab-44d9-8dcf-e5b024eddd00\",\n" 
                        + "      \"owner\": \"owner_user\",\n" 
                        + "      \"host\": \"test1.test.net\",\n" 
                        + "      \"port\": 0,\n" 
                        + "      \"user_name\": \"user1\",\n" 
                        + "      \"password\": \"*****\",\n" 
                        + "      \"is_virtual\": false,\n" 
                        + "      \"entry_point\": \"pub\",\n" 
                        + "      \"network_timeout\": 0,\n" 
                        + "      \"action_timeout\": 0,\n" 
                        + "      \"general_type\": \"Box\",\n" 
                        + "      \"requires_sync_group\": false,\n" 
                        + "      \"client_id\": \"ABCD123\",\n" 
                        + "      \"client_secret\": \"ABCD123\",\n" 
                        + "      \"options\": {\n" 
                        + "            \"access_token\": null,\n" 
                        + "            \"refresh_token\": \"ABCD123\",\n" 
                        + "            \"token_type\": \"Bearer\",\n" 
                        + "            \"last_update\": \"2017-09-10 20:00:05\"\n" 
                        + "      }\n" 
                        + "}";
            } else {
                datasource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                        + "<create_ds_request xmlns=\"http://intapp/integrate/api/v1\">\n" 
                        + "    <name>b01f1550-b3ab-44d9-8dcf-e5b024eddd00</name>\n" 
                        + "    <owner>owner_user</owner>\n" 
                        + "    <host>test1.test.net</host>\n" 
                        + "    <port>0</port>\n" 
                        + "    <user_name>user1</user_name>\n" 
                        + "    <password>*******</password>\n" 
                        + "    <is_virtual>false</is_virtual>\n" 
                        + "    <entry_point>pub</entry_point>\n" 
                        + "    <network_timeout>0</network_timeout>\n" 
                        + "    <action_timeout>0</action_timeout>\n" 
                        + "    <general_type>Box</general_type>\n" 
                        + "    <requires_sync_group>false</requires_sync_group>\n" 
                        + "    <client_id>ABCD123</client_id>\n" 
                        + "    <client_secret>ABCD123</client_secret>\n" 
                        + "    <options>\n" 
                        + "      <access_token>null</access_token>\n" 
                        + "      <refresh_token>ABCD123</refresh_token>\n" 
                        + "      <token_type>Bearer</token_type>\n" 
                        + "      <last_update>2017-09-10 20:00:05</last_update>\n" 
                        + "    </options>\n" 
                        + "</create_ds_request>";
            }
            
            CreateDatasourceRequest createDatasourceRequest = IntegrateRequestFactory.createCreateDatasourceRequest(accessToken, datasource);
            LOGGER.debug("created createDatasourceRequest");
            CreateDatasourceResponse createDatasourceResponse = integrateExternalAPI.createDatasource(createDatasourceRequest);
            LOGGER.debug("created datasource");
            Integer datasourceId = createDatasourceResponse.getDatasourceId();
            LOGGER.debug("datasourceId is " + datasourceId);
            LOGGER.debug("about to update datasource");
            if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(IntegrateConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(IntegrateConstant.CONFIG_API_REQUEST_FORMAT))) {
                datasource = "{\n" 
                        + "      \"name\": \"b01f1550-b3ab-44d9-8dcf-e5b024eddd00\",\n" 
                        + "      \"owner\": \"owner_user\",\n" 
                        + "      \"host\": \"test1.test.net\",\n" 
                        + "      \"port\": 0,\n" 
                        + "      \"user_name\": \"user1\",\n" 
                        + "      \"password\": \"*****\",\n" 
                        + "      \"is_virtual\": false,\n" 
                        + "      \"entry_point\": \"pub\",\n" 
                        + "      \"network_timeout\": 0,\n" 
                        + "      \"action_timeout\": 0,\n" 
                        + "      \"general_type\": \"Box\",\n" 
                        + "      \"requires_sync_group\": false,\n" 
                        + "      \"client_id\": \"ABCD123\",\n" 
                        + "      \"client_secret\": \"ABCD123\",\n" 
                        + "      \"options\": {\n" 
                        + "            \"access_token\": null,\n" 
                        + "            \"refresh_token\": \"ABCD123\",\n" 
                        + "            \"token_type\": \"Bearer\",\n" 
                        + "            \"last_update\": \"2017-09-10 20:00:05\"\n" 
                        + "      }\n" 
                        + "}";
            } else {
                datasource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                        + "<create_ds_request xmlns=\"http://intapp/integrate/api/v1\">\n" 
                        + "    <name>b01f1550-b3ab-44d9-8dcf-e5b024eddd00</name>\n" 
                        + "    <owner>owner_user</owner>\n" 
                        + "    <host>test1.test.net</host>\n" 
                        + "    <port>0</port>\n" 
                        + "    <user_name>user1</user_name>\n" 
                        + "    <password>*******</password>\n" 
                        + "    <is_virtual>false</is_virtual>\n" 
                        + "    <entry_point>pub</entry_point>\n" 
                        + "    <network_timeout>0</network_timeout>\n" 
                        + "    <action_timeout>0</action_timeout>\n" 
                        + "    <general_type>Box</general_type>\n" 
                        + "    <requires_sync_group>false</requires_sync_group>\n" 
                        + "    <client_id>ABCD123</client_id>\n" 
                        + "    <client_secret>ABCD123</client_secret>\n" 
                        + "    <options>\n" 
                        + "      <access_token>null</access_token>\n" 
                        + "      <refresh_token>ABCD123</refresh_token>\n" 
                        + "      <token_type>Bearer</token_type>\n" 
                        + "      <last_update>2017-09-10 20:00:05</last_update>\n" 
                        + "    </options>\n" 
                        + "</create_ds_request>";
            }
            
            UpdateDatasourceRequest updateDatasourceRequest = IntegrateRequestFactory.createUpdateDatasourceRequest(accessToken, datasourceId, datasource);
            LOGGER.debug("created updateDatasourceRequest");
            UpdateDatasourceResponse updateDatasourceResponse = integrateExternalAPI.updateDatasource(updateDatasourceRequest);
            LOGGER.debug("updated datasource");
            LOGGER.debug("about to get datasource");
            GetDatasourceRequest getDatasourceRequest = IntegrateRequestFactory.createGetDatasourceRequest(accessToken, datasourceId);
            LOGGER.debug("created getDatasourceRequest");
            GetDatasourceResponse getDatasourceResponse = integrateExternalAPI.getDatasource(getDatasourceRequest);
            LOGGER.debug("got datasource");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }
}
