/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate;

import com.xcase.integrate.impl.simple.methods.*;
import com.xcase.integrate.impl.simple.core.IntegrateConfigurationManager;
import com.xcase.integrate.objects.IntegrateException;
import com.xcase.integrate.transputs.*;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class SimpleIntegrateImpl implements IntegrateExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public IntegrateConfigurationManager localConfigurationManager = IntegrateConfigurationManager.getConfigurationManager();

    /**
     * integrate action implementation.
     */
    private CreateAccessTokenMethod createAccessTokenMethod = new CreateAccessTokenMethod();
    
    /**
     * integrate action implementation.
     */
    private CreateDatasourceMethod createDatasourceMethod = new CreateDatasourceMethod();
    
    /**
     * integrate action implementation.
     */
    private DeleteDatasourceMethod deleteDatasourceMethod = new DeleteDatasourceMethod();

    /**
     * integrate action implementation.
     */
    private DeleteRuleMethod deleteRuleMethod = new DeleteRuleMethod();

    /**
     * integrate action implementation.
     */
    private ExecuteRuleMethod executeRuleMethod = new ExecuteRuleMethod();

    /**
     * integrate action implementation.
     */
    private GetAllRulesMethod getAllRulesMethod = new GetAllRulesMethod();

    /**
     * integrate action implementation.
     */
    private GetApplianceAgentStatusMethod getApplianceAgentStatusMethod = new GetApplianceAgentStatusMethod();

    /**
     * integrate action implementation.
     */
    private GetDatasourceConnectivityMethod getDatasourceConnectivityMethod = new GetDatasourceConnectivityMethod();

    /**
     * integrate action implementation.
     */
    private GetDatasourceMethod getDatasourceMethod = new GetDatasourceMethod();

    /**
     * integrate action implementation.
     */
    private GetDatasourcesMethod getDatasourcesMethod = new GetDatasourcesMethod();
    
    /**
     * integrate action implementation.
     */
    private GetLogMethod getLogMethod = new GetLogMethod();

    /**
     * integrate action implementation.
     */
    private GetLogsMethod getLogsMethod = new GetLogsMethod();

    /**
     * integrate action implementation.
     */
    private GetLogsUtilizationMethod getLogsUtilizationMethod = new GetLogsUtilizationMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleErrorSummaryMethod getRuleErrorSummaryMethod = new GetRuleErrorSummaryMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleExecutionStatusMethod getRuleExecutionStatusMethod = new GetRuleExecutionStatusMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleLongExecutingMethod getRuleLongExecutingMethod = new GetRuleLongExecutingMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleMethod getRuleMethod = new GetRuleMethod();
    
    /**
     * integrate action implementation.
     */
    private GetRulesMethod getRulesMethod = new GetRulesMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleProcessorStatusMethod getRuleProcessorStatusMethod = new GetRuleProcessorStatusMethod();

    /**
     * integrate action implementation.
     */
    private GetRuleSummaryMethod getRuleSummaryMethod = new GetRuleSummaryMethod();

    /**
     * integrate action implementation.
     */
    private GetServiceStatusMethod getServiceStatusMethod = new GetServiceStatusMethod();

    /**
     * integrate action implementation.
     */
    private GetSwaggerMethod getSwaggerMethod = new GetSwaggerMethod();

    /**
     * integrate action implementation.
     */
    private GetSystemCPULoadAverageHistoryMethod getSystemCPULoadAverageHistoryMethod = new GetSystemCPULoadAverageHistoryMethod();

    /**
     * integrate action implementation.
     */
    private GetSystemDiskUsageMethod getSystemDiskUsageMethod = new GetSystemDiskUsageMethod();

    /**
     * integrate action implementation.
     */
    private GetSystemMemoryHistoryMethod getSystemMemoryHistoryMethod = new GetSystemMemoryHistoryMethod();

    /**
     * integrate action implementation.
     */
    private GetSystemMemoryUsageMethod getSystemMemoryUsageMethod = new GetSystemMemoryUsageMethod();
    
    /**
     * integrate action implementation.
     */
    private SearchRulesMethod searchRulesMethod = new SearchRulesMethod();
    
    /**
     * integrate action implementation.
     */
    private UpdateDatasourceMethod updateDatasourceMethod = new UpdateDatasourceMethod();

    /**
     * integrate action implementation.
     */
    private UpdateRuleMethod updateRuleMethod = new UpdateRuleMethod();

    @Override
    public CreateAccessTokenResponse createAccessToken(CreateAccessTokenRequest createAcessTokenRequest) throws IOException, IntegrateException {
        return this.createAccessTokenMethod.createAccessToken(createAcessTokenRequest);
    }
    
    @Override
    public CreateDatasourceResponse createDatasource(CreateDatasourceRequest createDatasourceRequest) throws IOException, IntegrateException {
        return this.createDatasourceMethod.createDatasource(createDatasourceRequest);
    }
    
    @Override
    public DeleteDatasourceResponse deleteDatasource(DeleteDatasourceRequest deleteDatasourceRequest) throws IOException, IntegrateException {
        return this.deleteDatasourceMethod.deleteDatasource(deleteDatasourceRequest);
    }

    @Override
    public DeleteRuleResponse deleteRule(DeleteRuleRequest deleteRuleRequest) throws IOException, IntegrateException {
        return this.deleteRuleMethod.deleteRule(deleteRuleRequest);
    }

    @Override
    public ExecuteRuleResponse executeRule(ExecuteRuleRequest executeRuleRequest) throws IOException, IntegrateException {
        return this.executeRuleMethod.executeRule(executeRuleRequest);
    }

    @Override
    public GetAllDatasourcesResponse getDatasources(GetAllDatasourcesRequest getAllDatasourcesRequest) throws IOException, IntegrateException {
        return this.getDatasourcesMethod.getDatasources(getAllDatasourcesRequest);
    }

    @Override
    public GetAllRulesResponse getAllRules(GetAllRulesRequest getAllRulesRequest) throws IOException, IntegrateException {
        return this.getAllRulesMethod.getAllRules(getAllRulesRequest);
    }

    @Override
    public GetApplianceAgentStatusResponse getApplianceAgentStatus(GetApplianceAgentStatusRequest getApplianceAgentStatusRequest) throws IOException, IntegrateException {
        return this.getApplianceAgentStatusMethod.getApplianceAgentStatus(getApplianceAgentStatusRequest);
    }

    @Override
    public GetDatasourceConnectivityResponse getDatasourceConnectivity(GetDatasourceConnectivityRequest getDatasourceConnectivityRequest) throws IOException, IntegrateException {
        return this.getDatasourceConnectivityMethod.getDatasourceConnectivity(getDatasourceConnectivityRequest);
    }

    @Override
    public GetDatasourceResponse getDatasource(GetDatasourceRequest getDatasourceRequest) throws IOException, IntegrateException {
        return this.getDatasourceMethod.getDatasource(getDatasourceRequest);
    }

    @Override
    public GetLogResponse getLog(GetLogRequest getLogRequest) throws IOException, IntegrateException {
        return this.getLogMethod.getLog(getLogRequest);
    }

    @Override
    public GetLogsResponse getLogs(GetLogsRequest getLogsRequest) throws IOException, IntegrateException {
        return this.getLogsMethod.getLogs(getLogsRequest);
    }
    
    @Override
    public GetLogsUtilizationResponse getLogsUtilization(GetLogsUtilizationRequest getLogsUtilizationRequest) {
        return this.getLogsUtilizationMethod.getLogsUtilization(getLogsUtilizationRequest);
    }

    @Override
    public GetRuleResponse getRule(GetRuleRequest getRuleRequest) throws IOException, IntegrateException {
        return this.getRuleMethod.getRule(getRuleRequest);
    }
    
    @Override
    public GetRulesResponse getRules(GetRulesRequest getRulesRequest) throws IOException, IntegrateException {
        return this.getRulesMethod.getRules(getRulesRequest);
    }

    @Override
    public GetRuleErrorSummaryResponse getRuleErrorSummary(GetRuleErrorSummaryRequest getRuleErrorSummaryRequest) throws IOException, IntegrateException {
        return this.getRuleErrorSummaryMethod.getRuleErrorSummary(getRuleErrorSummaryRequest);
    }

    @Override
    public GetRuleExecutionStatusResponse getRuleExecutionStatus(GetRuleExecutionStatusRequest getRuleExecutionStatusRequest) throws IOException, IntegrateException {
        return this.getRuleExecutionStatusMethod.getRuleExecutionStatus(getRuleExecutionStatusRequest);
    }

    @Override
    public GetRuleLongExecutingResponse getRuleLongExecuting(GetRuleLongExecutingRequest getRuleLongExecutingRequest) throws IOException, IntegrateException {
        return this.getRuleLongExecutingMethod.getRuleLongExecuting(getRuleLongExecutingRequest);
    }

    @Override
    public GetRuleProcessorStatusResponse getRuleProcessorStatus(GetRuleProcessorStatusRequest getRuleProcessorStatusRequest) throws IOException, IntegrateException {
        return this.getRuleProcessorStatusMethod.getRuleProcessorStatus(getRuleProcessorStatusRequest);
    }

    @Override
    public GetRuleSummaryResponse getRuleSummary(GetRuleSummaryRequest getRuleSummaryRequest) throws IOException, IntegrateException {
        return this.getRuleSummaryMethod.getRuleSummary(getRuleSummaryRequest);
    }

    @Override
    public GetServiceStatusResponse getServiceStatus(GetServiceStatusRequest getServiceStatusRequest) throws IOException, IntegrateException {
        return this.getServiceStatusMethod.getServiceStatus(getServiceStatusRequest);
    }

    @Override
    public GetSwaggerResponse getSwagger(GetSwaggerRequest getSwaggerRequest) throws IOException, IntegrateException {
        return this.getSwaggerMethod.getSwagger(getSwaggerRequest);
    }

    @Override
    public GetSystemCPULoadAverageHistoryResponse getSystemCPULoadAverageHistory(GetSystemCPULoadAverageHistoryRequest getSystemCPULoadAverageHistoryRequest) throws IOException, IntegrateException {
        return this.getSystemCPULoadAverageHistoryMethod.getSystemCPULoadAverageHistory(getSystemCPULoadAverageHistoryRequest);
    }

    @Override
    public GetSystemDiskUsageResponse getSystemDiskUsage(GetSystemDiskUsageRequest getSystemDiskUsageRequest) throws IOException, IntegrateException {
        return this.getSystemDiskUsageMethod.getSystemDiskUsage(getSystemDiskUsageRequest);
    }

    @Override
    public GetSystemMemoryHistoryResponse getSystemMemoryHistory(GetSystemMemoryHistoryRequest getSystemMemoryHistoryRequest) throws IOException, IntegrateException {
        return this.getSystemMemoryHistoryMethod.getSystemMemoryHistory(getSystemMemoryHistoryRequest);
    }

    @Override
    public GetSystemMemoryUsageResponse getSystemMemoryUsage(GetSystemMemoryUsageRequest getSystemMemoryUsageRequest) throws IOException, IntegrateException {
        return this.getSystemMemoryUsageMethod.getSystemMemoryUsage(getSystemMemoryUsageRequest);
    }
    
    @Override
    public SearchRulesResponse searchRules(SearchRulesRequest searchRulesRequest) throws IOException, IntegrateException {
        return this.searchRulesMethod.searchRules(searchRulesRequest);
    }
    
    @Override
    public UpdateDatasourceResponse updateDatasource(UpdateDatasourceRequest updateDatasourceRequest) throws IOException, IntegrateException {
        return this.updateDatasourceMethod.updateDatasource(updateDatasourceRequest);
    }

    @Override
    public UpdateRuleResponse updateRule(UpdateRuleRequest updateRuleRequest) throws IOException, IntegrateException {
        return this.updateRuleMethod.updateRule(updateRuleRequest);
    }
}
