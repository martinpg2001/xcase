/**
 * Copyright 2016 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martinpg
 */
public interface MSGraphQueryRequest extends MSGraphRequest {
    public String getSearch();
    public String getSelect();
    public Integer getSkip();
    public Integer getTop();
    public void setSearch(String search);
    public void setSelect(String select);
    public void setSkip(Integer skip);
    public void setTop(Integer top);
}
