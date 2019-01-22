/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.SearchRulesRequest;

/**
 *
 * @author martin
 */
public class SearchRulesRequestImpl extends IntegrateRequestImpl implements SearchRulesRequest {
    private String createdEnd;
    private String createdStart;
    private String errorState;
    private String filterOption;
    private String keyword;
    private String logSettings;
    private String modifiedEnd;
    private String modifiedStart;
    private String status;

    public String getCreatedEnd() {
        return this.createdEnd;
    }
    
    public String getCreatedStart() {
        return this.createdStart;
    }
    
    public String getErrorState() {
        return this.errorState;
    }
    
    public String getFilterOption() {
        return this.filterOption;
    }
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public String getLogSettings() {
        return this.logSettings;
    }
    
    public String getModifiedEnd() {
        return this.modifiedEnd;
    }
    
    public String getModifiedStart() {
        return this.modifiedStart;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setCreatedEnd(String createdEnd) {
        this.createdEnd = createdEnd;
    }
    
    public void setCreatedStart(String createdStart) {
        this.createdStart = createdStart;
    }
    
    public void setErrorState(String errorState) {
        this.errorState = errorState;
    }
    
    public void setFilterOption(String filterOption) {
        this.filterOption = filterOption;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public void setLogSettings(String logSettings) {
        this.logSettings = logSettings;
    }
    
    public void setModifiedEnd(String modifiedEnd) {
        this.modifiedEnd = modifiedEnd;
    }
    
    public void setModifiedStart(String modifiedStart) {
        this.modifiedStart = modifiedStart;
    }
    
    public void setStatus(String status) {
        this.status = status;
    } 
}
