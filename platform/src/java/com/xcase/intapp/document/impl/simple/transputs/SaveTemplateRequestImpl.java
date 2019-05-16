package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.SaveTemplateRequest;

public class SaveTemplateRequestImpl extends DocumentRequestImpl implements SaveTemplateRequest {
    private String category;
    private String name;
    private String operationPath = "api/v2/templates";
    private String item1;
    private byte[] item2;
    
	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setItem1(String item1) {
		this.item1 = item1;
	}

	@Override
	public void setItem2(byte[] item2) {
		this.item2 = item2;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getItem1() {
		return item1;
	}

	@Override
	public byte[] getItem2() {
		return item2;
	}

}
