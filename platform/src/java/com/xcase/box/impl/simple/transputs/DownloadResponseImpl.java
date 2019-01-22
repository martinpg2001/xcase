/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.DownloadResponse;
import java.io.File;

/**
 * @author martin
 *
 */
public class DownloadResponseImpl extends BoxResponseImpl implements DownloadResponse {

    /**
     * if true, will download as a File object, if false then download as bytes
     * array.
     */
    private boolean asFile;

    /**
     * the raw data downloaded.
     */
    private byte[] rawData;

    /**
     * the File object downloaded.
     */
    private File outFile;

    /**
     * @return the asFile
     */
    public boolean isAsFile() {
        return this.asFile;
    }

    /**
     * @param asFile the asFile to set
     */
    public void setAsFile(boolean asFile) {
        this.asFile = asFile;
    }

    /**
     * @return the rawData
     */
    public byte[] getRawData() {
        return this.rawData;
    }

    /**
     * @param rawData the rawData to set
     */
    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    /**
     * @return the outFile
     */
    public File getOutFile() {
        return this.outFile;
    }

    /**
     * @param outFile the outFile to set
     */
    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }
}
