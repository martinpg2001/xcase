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
public class IntegrateResponseFactory extends BaseIntegrateFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateAccessTokenResponse createCreateAccessTokenResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.CreateAccessTokenResponse");
        return (CreateAccessTokenResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateDatasourceResponse createCreateDatasourceResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.CreateDatasourceResponse");
        return (CreateDatasourceResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteDatasourceResponse createDeleteDatasourceResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.DeleteDatasourceResponse");
        return (DeleteDatasourceResponse) obj;
    }

    public static ExecuteRuleResponse createExecuteRuleResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.ExecuteRuleResponse");
        return (ExecuteRuleResponse) obj;
    }

    public static GetAllDatasourcesResponse createGetAllDatasourcesResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetAllDatasourcesResponse");
        return (GetAllDatasourcesResponse) obj;
    }

    public static GetDatasourceResponse createGetDatasourceResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetDatasourceResponse");
        return (GetDatasourceResponse) obj;
    }

    public static DeleteRuleResponse createDeleteRuleResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.DeleteRuleResponse");
        return (DeleteRuleResponse) obj;
    }

    public static GetRuleResponse createGetRuleResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleResponse");
        return (GetRuleResponse) obj;
    }
    
    public static GetRulesResponse createGetRulesResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRulesResponse");
        return (GetRulesResponse) obj;
    }

    public static GetRuleSummaryResponse createGetRuleSummaryResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleSummaryResponse");
        return (GetRuleSummaryResponse) obj;
    }

    public static GetAllRulesResponse createGetAllRulesResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetAllRulesResponse");
        return (GetAllRulesResponse) obj;
    }

    public static GetApplianceAgentStatusResponse createGetApplianceAgentStatusResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetApplianceAgentStatusResponse");
        return (GetApplianceAgentStatusResponse) obj;
    }

    public static GetDatasourceConnectivityResponse createGetDatasourceConnectivityResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetDatasourceConnectivityResponse");
        return (GetDatasourceConnectivityResponse) obj;
    }

    public static GetLogResponse createGetLogResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetLogResponse");
        return (GetLogResponse) obj;
    }

    public static GetLogsResponse createGetLogsResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetLogsResponse");
        return (GetLogsResponse) obj;
    }
    
    public static GetLogsUtilizationResponse createGetLogsUtilizationResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetLogsUtilizationResponse");
        return (GetLogsUtilizationResponse) obj;
    }

    public static GetRuleErrorSummaryResponse createGetRuleErrorSummaryResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleErrorSummaryResponse");
        return (GetRuleErrorSummaryResponse) obj;
    }

    public static GetRuleExecutionStatusResponse createGetRuleExecutionStatusResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleExecutionStatusResponse");
        return (GetRuleExecutionStatusResponse) obj;
    }

    public static GetRuleLongExecutingResponse createGetRuleLongExecutingResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleLongExecutingResponse");
        return (GetRuleLongExecutingResponse) obj;
    }

    public static GetRuleProcessorStatusResponse createGetRuleProcessorStatusResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetRuleProcessorStatusResponse");
        return (GetRuleProcessorStatusResponse) obj;
    }

    public static GetServiceStatusResponse createGetServiceStatusResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetServiceStatusResponse");
        return (GetServiceStatusResponse) obj;
    }

    public static GetSwaggerResponse createGetSwaggerResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetSwaggerResponse");
        return (GetSwaggerResponse) obj;
    }

    public static GetSystemMemoryHistoryResponse createGetSystemMemoryHistoryResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetSystemMemoryHistoryResponse");
        return (GetSystemMemoryHistoryResponse) obj;
    }

    public static GetSystemCPULoadAverageHistoryResponse createGetSystemCPULoadAverageHistoryResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetSystemCPULoadAverageHistoryResponse");
        return (GetSystemCPULoadAverageHistoryResponse) obj;
    }

    public static GetSystemDiskUsageResponse createGetSystemDiskUsageResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetSystemDiskUsageResponse");
        return (GetSystemDiskUsageResponse) obj;
    }

    public static GetSystemMemoryUsageResponse createGetSystemMemoryUsageResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.GetSystemMemoryUsageResponse");
        return (GetSystemMemoryUsageResponse) obj;
    }
    
    public static SearchRulesResponse createSearchRulesResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.SearchRulesResponse");
        return (SearchRulesResponse) obj;
    }

    public static UpdateRuleResponse createUpdateRuleResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.UpdateRuleResponse");
        return (UpdateRuleResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateDatasourceResponse createUpdateDatasourceResponse() {
        Object obj = newInstanceOf("integrate.config.responsefactory.UpdateDatasourceResponse");
        return (UpdateDatasourceResponse) obj;
    }
}
