package com.xcase.intapp.document.transputs;

public interface RenderDocumentRequest extends DocumentRequest {

	String getOperationPath();

	String getDataItem1();

	byte[] getDataItem2();

	String getFileItem1();

	byte[] getFileItem2();
	
	void setDataItem1(String dataItem1);

	void setDataItem2(byte[] dataItem2);

	void setFileItem1(String fileItem1);

	void setFileItem2(byte[] fileItem2);

}
