/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxGroup;
import com.xcase.box.transputs.GetGroupsForUserResponse;
import java.util.*;

/**
 *
 * @author martin
 */
public class GetGroupsForUserResponseImpl extends BoxResponseImpl implements GetGroupsForUserResponse {

    private List<BoxGroup> boxGroupList;
    private String totalCount;

    public List<BoxGroup> getGroups() {
        return this.boxGroupList;
    }

    public void setGroups(List<BoxGroup> boxGroupList) {
        this.boxGroupList = boxGroupList;
    }

    public String getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
