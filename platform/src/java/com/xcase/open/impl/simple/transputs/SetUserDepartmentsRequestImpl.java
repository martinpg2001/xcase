/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.SetUserDepartmentsRequest;

public class SetUserDepartmentsRequestImpl implements SetUserDepartmentsRequest {

    private String userId;
    private String[] departmentArray;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String[] getDepartmentArray() {
        return this.departmentArray;
    }

    @Override
    public void setDepartmentArray(String[] departmentArray) {
        this.departmentArray = departmentArray;
    }

}
