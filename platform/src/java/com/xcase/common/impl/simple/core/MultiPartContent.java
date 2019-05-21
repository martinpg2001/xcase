package com.xcase.common.impl.simple.core;

public class MultiPartContent {
    private boolean isText;
    private byte[] content;
    private String contentType;
    
    public MultiPartContent(boolean isText, byte[] content, String contentType) {
        isText(isText);
        setContent(content);
        setContentType(contentType);
    }
    
    public boolean isText() {
        return isText;
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void isText(boolean isText) {
        this.isText = isText;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
