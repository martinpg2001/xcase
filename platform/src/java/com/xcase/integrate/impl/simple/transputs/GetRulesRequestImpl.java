/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetRulesRequest;

/**
 *
 * @author martin
 */
public class GetRulesRequestImpl extends IntegrateRequestImpl implements GetRulesRequest {

    private String locationSeparator;
    private String parentPath;
    private String pathDelimiter;
    private Boolean recursive;

    public String getLocationSeparator() {
        return this.locationSeparator;
    }

    public void setLocationSeparator(String locationSeparator) {
        this.locationSeparator = locationSeparator;
    }
    
    public String getParentPath() {
        return this.parentPath;
    }
    
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
    
    public String getPathDelimiter() {
        return this.pathDelimiter;
    }
    
    public void setPathDelimiter(String pathDelimiter) {
        this.pathDelimiter = pathDelimiter;
    }
    
    public Boolean getRecursive() {
        return this.recursive;
    }
    
    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }
}
