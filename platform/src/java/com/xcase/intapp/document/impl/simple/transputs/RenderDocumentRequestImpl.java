package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.RenderDocumentRequest;

public class RenderDocumentRequestImpl extends DocumentRequestImpl implements RenderDocumentRequest {
    private String dataItem1;
    private byte[] dataItem2;
    private String dataItem3;
    private String fileItem1;
    private byte[] fileItem2;
    private String fileItem3;
    private String operationPath = "api/v3/render";
    
	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getDataItem1() {
		return dataItem1;
	}

	@Override
	public byte[] getDataItem2() {
		return dataItem2;
	}
	
    @Override
    public String getDataItem3() {
        return dataItem3;
    }

	@Override
	public String getFileItem1() {
		return fileItem1;
	}

	@Override
	public byte[] getFileItem2() {
		return fileItem2;
	}
	
    @Override
    public String getfileItem3() {
        return fileItem3;
    }

	@Override
	public void setDataItem1(String dataItem1) {
		this.dataItem1 = dataItem1;
	}

	@Override
	public void setDataItem2(byte[] dataItem2) {
		this.dataItem2 = dataItem2;
	}
	
    @Override
    public void setDataItem3(String dataItem3) {
        this.dataItem3 = dataItem3;        
    }

	@Override
	public void setFileItem1(String fileItem1) {
		this.fileItem1 = fileItem1;
	}

	@Override
	public void setFileItem2(byte[] fileItem2) {
		this.fileItem2 = fileItem2;
	}

    @Override
    public void setFileItem3(String fileItem3) {
        this.fileItem3 = fileItem3;        
    }

}
