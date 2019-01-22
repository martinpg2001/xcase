/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface SetUserPracticeAreasRequest {

    String getUserId();

    void setUserId(String userId);

    String[] getPracticeAreaArray();

    void setPracticeAreaArray(String[] userPracticeAreaArray);
}
