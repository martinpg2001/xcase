/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.SetUserPracticeAreasRequest;

public class SetUserPracticeAreasRequestImpl implements SetUserPracticeAreasRequest {

    private String userId;
    private String[] practiceAreaArray;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String[] getPracticeAreaArray() {
        return this.practiceAreaArray;
    }

    @Override
    public void setPracticeAreaArray(String[] practiceAreaArray) {
        this.practiceAreaArray = practiceAreaArray;
    }

}
