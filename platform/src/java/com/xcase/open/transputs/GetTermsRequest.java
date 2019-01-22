/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import java.util.*;

/**
 *
 * @author martin
 */
public interface GetTermsRequest {

    String getFilterCategory();

    void setFilterCategory(String filterCategory);

    String getFilterClient();

    void setFilterClient(String filterClient);

    String getFilterMatter();

    void setFilterMatter(String filterMatter);

    String getFilterName();

    void setFilterName(String filterName);

    Date getFilterEffectiveStartDate();

    void setFilterEffectiveStartDate(Date filterEffectiveStartDate);

    Date getFilterEffectiveEndDate();

    void setFilterEffectiveEndDate(Date filterEffectiveEndDate);
}
