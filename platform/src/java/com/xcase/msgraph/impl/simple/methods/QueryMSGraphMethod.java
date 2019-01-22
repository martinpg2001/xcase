/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.msgraph.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.msgraph.transputs.MSGraphQueryRequest;

/**
 *
 * @author martinpg
 */
public class QueryMSGraphMethod extends BaseMSGraphMethod {
    public String addQuery(StringBuffer endPointStringBuffer, MSGraphQueryRequest request) {
        if (request.getSearch() != null) {
            endPointStringBuffer.append("%24search=" + request.getSearch() + CommonConstant.AND_SIGN_STRING);
        }

        if (request.getSelect() != null) {
            endPointStringBuffer.append("$select=" + request.getSelect() + CommonConstant.AND_SIGN_STRING);
        }

        if (request.getSkip() != null) {
            endPointStringBuffer.append("$skip=" + request.getSkip() + CommonConstant.AND_SIGN_STRING);
        }

        if (request.getTop() != null) {
            endPointStringBuffer.append("$top=" + request.getTop() + CommonConstant.AND_SIGN_STRING);
        }

        return endPointStringBuffer.toString();
    }
}
