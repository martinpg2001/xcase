/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface SearchRulesRequest extends IntegrateRequest {
    String getCreatedEnd();
    String getCreatedStart();
    String getErrorState();
    String getFilterOption();
    String getKeyword();
    String getLogSettings();
    String getModifiedEnd();
    String getModifiedStart();
    String getStatus();
    void setCreatedEnd(String createdEnd);
    void setCreatedStart(String createdStart);
    void setErrorState(String errorState);
    void setFilterOption(String filterOption);
    void setKeyword(String keyword);
    void setLogSettings(String logSettings);
    void setModifiedEnd(String modifiedEnd);
    void setModifiedStart(String modifiedStart);
    void setStatus(String status); 
}
