/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetTermsRequest;
import java.util.Date;

/**
 *
 * @author martin
 */
public class GetTermsRequestImpl extends OpenRequestImpl implements GetTermsRequest {

    private String filterCategory;
    private String filterClient;
    private String filterMatter;
    private String filterName;
    private Date filterEffectiveStartDate;
    private Date filterEffectiveEndDate;

    public String getFilterCategory() {
        return this.filterCategory;
    }

    public void setFilterCategory(String filterCategory) {
        this.filterCategory = filterCategory;
    }

    public String getFilterClient() {
        return this.filterClient;
    }

    public void setFilterClient(String filterClient) {
        this.filterClient = filterClient;
    }

    public String getFilterMatter() {
        return this.filterMatter;
    }

    public void setFilterMatter(String filterMatter) {
        this.filterMatter = filterMatter;
    }

    public String getFilterName() {
        return this.filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Date getFilterEffectiveStartDate() {
        return this.filterEffectiveStartDate;
    }

    public void setFilterEffectiveStartDate(Date filterEffectiveStartDate) {
        this.filterEffectiveStartDate = filterEffectiveStartDate;
    }

    public Date getFilterEffectiveEndDate() {
        return this.filterEffectiveEndDate;
    }

    public void setFilterEffectiveEndDate(Date filterEffectiveEndDate) {
        this.filterEffectiveEndDate = filterEffectiveEndDate;
    }
}
