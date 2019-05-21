package com.xcase.intapp.document.transputs;

public interface RenderDocumentRequest extends DocumentRequest {

	String getOperationPath();

	String getDataItem1();

	byte[] getDataItem2();
	
    String getDataItem3();

	String getFileItem1();

	byte[] getFileItem2();
	
    String getFileItem3();
	
	void setDataItem1(String dataItem1);

	void setDataItem2(byte[] dataItem2);
	
    void setDataItem3(String dataContentType);

	void setFileItem1(String fileItem1);

	void setFileItem2(byte[] fileItem2);

    void setFileItem3(String fileContentType);

    void setDataItem4(String dataFileName);

    void setFileItem4(String fileFileName);

    String getDataItem4();

    String getFileItem4();

}
