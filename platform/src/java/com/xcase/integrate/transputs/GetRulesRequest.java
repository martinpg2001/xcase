/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface GetRulesRequest extends IntegrateRequest {

    public String getLocationSeparator();

    public void setLocationSeparator(String locationSeparator);

    public String getParentPath();
    
    public void setParentPath(String parentPath);
    
    public String getPathDelimiter();
    
    public void setPathDelimiter(String pathDelimiter);
    
    public Boolean getRecursive();
    
    public void setRecursive(Boolean recursive);
}
