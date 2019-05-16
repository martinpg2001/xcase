package com.xcase.intapp.document.transputs;

public interface SaveTemplateRequest extends DocumentRequest {

	void setCategory(String category);

	void setName(String name);

	void setItem1(String item1);

	void setItem2(byte[] item2);

	String getOperationPath();

	String getCategory();

	String getName();

	String getItem1();

	byte[] getItem2();

}
