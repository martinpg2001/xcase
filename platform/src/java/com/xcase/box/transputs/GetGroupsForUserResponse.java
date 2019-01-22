/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxGroup;
import java.util.*;

/**
 *
 * @author martin
 */
public interface GetGroupsForUserResponse extends BoxResponse {

    /**
     *
     * @return boxGroupList
     */
    public List<BoxGroup> getGroups();

    /**
     *
     * @param boxGroupList
     */
    public void setGroups(List<BoxGroup> boxGroupList);

    /**
     *
     * @return totalCount
     */
    public String getTotalCount();

    /**
     *
     * @param totalCount
     */
    public void setTotalCount(String totalCount);
}
