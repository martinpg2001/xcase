/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.factories;

import com.xcase.integrate.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class IntegrateRequestFactory extends BaseIntegrateFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static CreateAccessTokenRequest createCreateAccessTokenRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.CreateAccessTokenRequest");
        return (CreateAccessTokenRequest) obj;
    }

    public static CreateAccessTokenRequest createCreateAccessTokenRequest(String username, String password) {
        CreateAccessTokenRequest request = createCreateAccessTokenRequest();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
    
    public static CreateDatasourceRequest createCreateDatasourceRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.CreateDatasourceRequest");
        return (CreateDatasourceRequest) obj;
    }

    public static CreateDatasourceRequest createCreateDatasourceRequest(String accessToken, String datasource) {
        CreateDatasourceRequest request = createCreateDatasourceRequest();
        request.setAccessToken(accessToken);
        request.setDatasource(datasource);
        return request;
    }
    
    public static DeleteDatasourceRequest createDeleteDatasourceRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.DeleteDatasourceRequest");
        return (DeleteDatasourceRequest) obj;
    }

    public static DeleteDatasourceRequest createDeleteDatasourceRequest(String accessToken, Integer datasourceId) {
        DeleteDatasourceRequest request = createDeleteDatasourceRequest();
        request.setAccessToken(accessToken);
        request.setDatasourceId(datasourceId);
        return request;
    }

    public static DeleteRuleRequest createDeleteRuleRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.DeleteRuleRequest");
        return (DeleteRuleRequest) obj;
    }

    public static DeleteRuleRequest createDeleteRuleRequest(String accessToken, Integer ruleId) {
        DeleteRuleRequest request = createDeleteRuleRequest();
        request.setAccessToken(accessToken);
        request.setRuleId(ruleId);
        return request;
    }

    public static ExecuteRuleRequest createExecuteRuleRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.ExecuteRuleRequest");
        return (ExecuteRuleRequest) obj;
    }

    public static ExecuteRuleRequest createExecuteRuleRequest(String accessToken, Integer ruleId) {
        ExecuteRuleRequest request = createExecuteRuleRequest();
        request.setAccessToken(accessToken);
        request.setRuleId(ruleId);
        return request;
    }

    public static GetRuleRequest createGetRuleRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleRequest");
        return (GetRuleRequest) obj;
    }

    public static GetRuleRequest createGetRuleRequest(String accessToken, Integer ruleId) {
        GetRuleRequest request = createGetRuleRequest();
        request.setAccessToken(accessToken);
        request.setRuleId(ruleId);
        return request;
    }
    
    public static GetRulesRequest createGetRulesRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRulesRequest");
        return (GetRulesRequest) obj;
    }

    public static GetRulesRequest createGetRulesRequest(String accessToken) {
        GetRulesRequest request = createGetRulesRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetAllDatasourcesRequest createGetAllDatasourcesRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetAllDatasourcesRequest");
        return (GetAllDatasourcesRequest) obj;
    }

    public static GetAllDatasourcesRequest createGetAllDatasourcesRequest(String accessToken) {
        GetAllDatasourcesRequest request = createGetAllDatasourcesRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetAllRulesRequest createGetAllRulesRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetAllRulesRequest");
        return (GetAllRulesRequest) obj;
    }

    public static GetAllRulesRequest createGetAllRulesRequest(String accessToken) {
        GetAllRulesRequest request = createGetAllRulesRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetApplianceAgentStatusRequest createGetApplianceAgentStatusRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetApplianceAgentStatusRequest");
        return (GetApplianceAgentStatusRequest) obj;
    }

    public static GetApplianceAgentStatusRequest createGetApplianceAgentStatusRequest(String accessToken) {
        GetApplianceAgentStatusRequest request = createGetApplianceAgentStatusRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetDatasourceRequest createGetDatasourceRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetDatasourceRequest");
        return (GetDatasourceRequest) obj;
    }

    public static GetDatasourceRequest createGetDatasourceRequest(String accessToken, Integer datasourceId) {
        GetDatasourceRequest request = createGetDatasourceRequest();
        request.setAccessToken(accessToken);
        request.setDatasourceId(datasourceId);
        return request;
    }

    public static GetDatasourceConnectivityRequest createGetDatasourceConnectivityRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetDatasourceConnectivityRequest");
        return (GetDatasourceConnectivityRequest) obj;
    }

    public static GetDatasourceConnectivityRequest createGetDatasourceConnectivityRequest(String accessToken, Integer datasourceId) {
        GetDatasourceConnectivityRequest request = createGetDatasourceConnectivityRequest();
        request.setAccessToken(accessToken);
        request.setDatasourceId(datasourceId);
        return request;
    }

    public static GetLogRequest createGetLogRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetLogRequest");
        return (GetLogRequest) obj;
    }

    public static GetLogRequest createGetLogRequest(String accessToken, Integer logId, String logType) {
        GetLogRequest request = createGetLogRequest();
        request.setAccessToken(accessToken);
        request.setLogId(logId);
        request.setLogType(logType);
        return request;
    }

    public static GetLogsRequest createGetLogsRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetLogsRequest");
        return (GetLogsRequest) obj;
    }

    public static GetLogsRequest createGetLogsRequest(String accessToken, String logType) {
        GetLogsRequest request = createGetLogsRequest();
        request.setAccessToken(accessToken);
        request.setLogType(logType);
        return request;
    }
    
    public static GetLogsUtilizationRequest createGetLogsUtilizationRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetLogsUtilizationRequest");
        return (GetLogsUtilizationRequest) obj;
    }

    public static GetLogsUtilizationRequest createGetLogsUtilizationRequest(String accessToken) {
        GetLogsUtilizationRequest request = createGetLogsUtilizationRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetRuleErrorSummaryRequest createGetRuleErrorSummaryRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleErrorSummaryRequest");
        return (GetRuleErrorSummaryRequest) obj;
    }

    public static GetRuleErrorSummaryRequest createGetRuleErrorSummaryRequest(String accessToken) {
        GetRuleErrorSummaryRequest request = createGetRuleErrorSummaryRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetRuleSummaryRequest createGetRuleSummaryRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleSummaryRequest");
        return (GetRuleSummaryRequest) obj;
    }

    public static GetRuleSummaryRequest createGetRuleSummaryRequest(String accessToken) {
        GetRuleSummaryRequest request = createGetRuleSummaryRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetRuleExecutionStatusRequest createGetRuleExecutionStatusRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleExecutionStatusRequest");
        return (GetRuleExecutionStatusRequest) obj;
    }

    public static GetRuleExecutionStatusRequest createGetRuleExecutionStatusRequest(String accessToken, String correlationId) {
        GetRuleExecutionStatusRequest request = createGetRuleExecutionStatusRequest();
        request.setAccessToken(accessToken);
        request.setCorrelationId(correlationId);
        return request;
    }

    public static GetRuleLongExecutingRequest createGetRuleLongExecutingRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleLongExecutingRequest");
        return (GetRuleLongExecutingRequest) obj;
    }

    public static GetRuleLongExecutingRequest createGetRuleLongExecutingRequest(String accessToken) {
        GetRuleLongExecutingRequest request = createGetRuleLongExecutingRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetRuleProcessorStatusRequest createGetRuleProcessorStatusRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetRuleProcessorStatusRequest");
        return (GetRuleProcessorStatusRequest) obj;
    }

    public static GetRuleProcessorStatusRequest createGetRuleProcessorStatusRequest(String accessToken) {
        GetRuleProcessorStatusRequest request = createGetRuleProcessorStatusRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetServiceStatusRequest createGetServiceStatusRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetServiceStatusRequest");
        return (GetServiceStatusRequest) obj;
    }

    public static GetServiceStatusRequest createGetServiceStatusRequest(String accessToken) {
        GetServiceStatusRequest request = createGetServiceStatusRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetSwaggerRequest createGetSwaggerRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetSwaggerRequest");
        return (GetSwaggerRequest) obj;
    }

    public static GetSwaggerRequest createGetSwaggerRequest(String accessToken) {
        GetSwaggerRequest request = createGetSwaggerRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetSystemDiskUsageRequest createGetSystemDiskUsageRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetSystemDiskUsageRequest");
        return (GetSystemDiskUsageRequest) obj;
    }

    public static GetSystemDiskUsageRequest createGetSystemDiskUsageRequest(String accessToken) {
        GetSystemDiskUsageRequest request = createGetSystemDiskUsageRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetSystemCPULoadAverageHistoryRequest createGetSystemCPULoadAverageHistoryRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetSystemCPULoadAverageHistoryRequest");
        return (GetSystemCPULoadAverageHistoryRequest) obj;
    }

    public static GetSystemCPULoadAverageHistoryRequest createGetSystemCPULoadAverageHistoryRequest(String accessToken) {
        GetSystemCPULoadAverageHistoryRequest request = createGetSystemCPULoadAverageHistoryRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetSystemMemoryHistoryRequest createGetSystemMemoryHistoryRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetSystemMemoryHistoryRequest");
        return (GetSystemMemoryHistoryRequest) obj;
    }

    public static GetSystemMemoryHistoryRequest createGetSystemMemoryHistoryRequest(String accessToken) {
        GetSystemMemoryHistoryRequest request = createGetSystemMemoryHistoryRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetSystemMemoryUsageRequest createGetSystemMemoryUsageRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.GetSystemMemoryUsageRequest");
        return (GetSystemMemoryUsageRequest) obj;
    }

    public static GetSystemMemoryUsageRequest createGetSystemMemoryUsageRequest(String accessToken) {
        GetSystemMemoryUsageRequest request = createGetSystemMemoryUsageRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static SearchRulesRequest createSearchRulesRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.SearchRulesRequest");
        return (SearchRulesRequest) obj;
    }

    public static SearchRulesRequest createSearchRulesRequest(String accessToken) {
        SearchRulesRequest request = createSearchRulesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static UpdateDatasourceRequest createUpdateDatasourceRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.UpdateDatasourceRequest");
        return (UpdateDatasourceRequest) obj;
    }

    public static UpdateDatasourceRequest createUpdateDatasourceRequest(String accessToken, Integer datasourceId, String datasource) {
        UpdateDatasourceRequest request = createUpdateDatasourceRequest();
        request.setAccessToken(accessToken);
        request.setDatasourceId(datasourceId);
        request.setDatasource(datasource);
        return request;
    }

    public static UpdateRuleRequest createUpdateRuleRequest() {
        Object obj = newInstanceOf("integrate.config.requestfactory.UpdateRuleRequest");
        return (UpdateRuleRequest) obj;
    }

    public static UpdateRuleRequest createUpdateRuleRequest(String accessToken, Integer ruleId) {
        UpdateRuleRequest request = createUpdateRuleRequest();
        request.setAccessToken(accessToken);
        request.setRuleId(ruleId);
        return request;
    }
}
