/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetRuleRequest;

/**
 *
 * @author martin
 */
public class GetRuleRequestImpl extends IntegrateRequestImpl implements GetRuleRequest {

    private String includeTemplate;
    private String locationSeparator;
    private String pathDelimiter;
    private Integer ruleId;

    public String getIncludeTemplate() {
        return this.includeTemplate;
    }

    public void setIncludeTemplate(String includeTemplate) {
        this.includeTemplate = includeTemplate;
    }

    public Integer getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getLocationSeparator() {
        return this.locationSeparator;
    }

    public void setLocationSeparator(String locationSeparator) {
        this.locationSeparator = locationSeparator;
    }
    
    public String getPathDelimiter() {
        return this.pathDelimiter;
    }
    
    public void setPathDelimiter(String pathDelimiter) {
        this.pathDelimiter = pathDelimiter;
    }
}
