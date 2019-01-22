/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxAbstractFile;

/**
 *
 * @author martin
 */
public interface MoveResponse extends BoxResponse {

    /**
     *
     * @return boxAbstractFile
     */
    public BoxAbstractFile getFile();

    /**
     *
     * @param boxAbstractFile
     */
    public void setFile(BoxAbstractFile boxAbstractFile);
}
