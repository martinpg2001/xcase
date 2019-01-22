/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface GetRuleRequest extends IntegrateRequest {

    public Integer getRuleId();

    public void setRuleId(Integer ruleId);

    public String getLocationSeparator();

    public void setLocationSeparator(String locationSeparator);

    public String getIncludeTemplate();

    public void setIncludeTemplate(String includeTemplate);
    
    public String getPathDelimiter();
    
    public void setPathDelimiter(String pathDelimiter);
}
