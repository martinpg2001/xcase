/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate;

import com.xcase.integrate.objects.IntegrateException;
import com.xcase.integrate.transputs.*;
import java.io.IOException;

/**
 *
 * @author martinpg
 */
public interface IntegrateExternalAPI {

    public CreateAccessTokenResponse createAccessToken(CreateAccessTokenRequest createAcessTokenRequest) throws IOException, IntegrateException;
    
    public CreateDatasourceResponse createDatasource(CreateDatasourceRequest createDatasourceRequest) throws IOException, IntegrateException;
    
    public DeleteDatasourceResponse deleteDatasource(DeleteDatasourceRequest deleteDatasourceRequest) throws IOException, IntegrateException;

    public DeleteRuleResponse deleteRule(DeleteRuleRequest deleteRuleRequest) throws IOException, IntegrateException;

    public ExecuteRuleResponse executeRule(ExecuteRuleRequest executeRuleRequest) throws IOException, IntegrateException;

    public GetAllRulesResponse getAllRules(GetAllRulesRequest getAllRulesRequest) throws IOException, IntegrateException;

    public GetApplianceAgentStatusResponse getApplianceAgentStatus(GetApplianceAgentStatusRequest applianceAgentStatusRequest) throws IOException, IntegrateException;

    public GetDatasourceConnectivityResponse getDatasourceConnectivity(GetDatasourceConnectivityRequest getDatasourceConnectivityRequest) throws IOException, IntegrateException;

    public GetDatasourceResponse getDatasource(GetDatasourceRequest getDatasourceRequest) throws IOException, IntegrateException;
    
    public GetAllDatasourcesResponse getDatasources(GetAllDatasourcesRequest getAllDatasourcesRequest) throws IOException, IntegrateException;

    public GetLogResponse getLog(GetLogRequest getLogRequest) throws IOException, IntegrateException;

    public GetLogsResponse getLogs(GetLogsRequest getLogsRequest) throws IOException, IntegrateException;
    
    public GetLogsUtilizationResponse getLogsUtilization(GetLogsUtilizationRequest logsUtilizationRequest);

    public GetRuleErrorSummaryResponse getRuleErrorSummary(GetRuleErrorSummaryRequest ruleErrorSummaryRequest) throws IOException, IntegrateException;

    public GetRuleExecutionStatusResponse getRuleExecutionStatus(GetRuleExecutionStatusRequest ruleExecutionStatusRequest) throws IOException, IntegrateException;

    public GetRuleLongExecutingResponse getRuleLongExecuting(GetRuleLongExecutingRequest ruleLongExecutingRequest) throws IOException, IntegrateException;

    public GetRuleProcessorStatusResponse getRuleProcessorStatus(GetRuleProcessorStatusRequest ruleProcessorStatusRequest) throws IOException, IntegrateException;

    public GetRuleResponse getRule(GetRuleRequest getRuleRequest) throws IOException, IntegrateException;
    
    public GetRulesResponse getRules(GetRulesRequest getRulesRequest) throws IOException, IntegrateException;

    public GetRuleSummaryResponse getRuleSummary(GetRuleSummaryRequest ruleSummaryRequest) throws IOException, IntegrateException;

    public GetServiceStatusResponse getServiceStatus(GetServiceStatusRequest serviceStatusRequest) throws IOException, IntegrateException;

    public GetSwaggerResponse getSwagger(GetSwaggerRequest swaggerRequest) throws IOException, IntegrateException;

    public GetSystemCPULoadAverageHistoryResponse getSystemCPULoadAverageHistory(GetSystemCPULoadAverageHistoryRequest systemCPULoadAverageHistoryRequest) throws IOException, IntegrateException;

    public GetSystemDiskUsageResponse getSystemDiskUsage(GetSystemDiskUsageRequest systemDiskUsageRequest) throws IOException, IntegrateException;

    public GetSystemMemoryHistoryResponse getSystemMemoryHistory(GetSystemMemoryHistoryRequest systemMemoryHistoryRequest) throws IOException, IntegrateException;

    public GetSystemMemoryUsageResponse getSystemMemoryUsage(GetSystemMemoryUsageRequest systemMemoryUsageRequest) throws IOException, IntegrateException;
    
    public SearchRulesResponse searchRules(SearchRulesRequest searchRulesRequest) throws IOException, IntegrateException;
    
    public UpdateDatasourceResponse updateDatasource(UpdateDatasourceRequest updateDatasourceRequest) throws IOException, IntegrateException;

    public UpdateRuleResponse updateRule(UpdateRuleRequest updateRuleRequest) throws IOException, IntegrateException;
}
