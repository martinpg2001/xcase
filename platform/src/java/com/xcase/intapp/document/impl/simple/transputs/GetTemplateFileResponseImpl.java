package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.GetTemplateFileResponse;

public class GetTemplateFileResponseImpl extends DocumentResponseImpl implements GetTemplateFileResponse {
    private byte[] bytes;
    
    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void setBytes(byte[] bytes) {
         this.bytes = bytes;
    }

}
