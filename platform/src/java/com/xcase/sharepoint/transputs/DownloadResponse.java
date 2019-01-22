/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import java.io.*;

/**
 *
 * @author martin
 */
public interface DownloadResponse extends SharepointResponse {

    /**
     * @return the asFile
     */
    public boolean isAsFile();

    /**
     * @param asFile the asFile to set
     */
    public void setAsFile(boolean asFile);

    /**
     * @return the rawData
     */
    public byte[] getRawData();

    /**
     * @param rawData the rawData to set
     */
    public void setRawData(byte[] rawData);

    /**
     * @return the outFile
     */
    public File getOutFile();

    /**
     * @param outFile the outFile to set
     */
    public void setOutFile(File outFile);
}
