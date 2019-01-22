package com.xcase.webapp.renderers.java.lang;

import com.xcase.renderers.ClassRenderer;
import com.xcase.renderers.IObjectRenderer;

public class IntegerRenderer extends ClassRenderer implements IObjectRenderer {

	@Override
	public String renderTableHeader() throws Exception {
		return "<tr><th>Value</th></tr>\n";
	}

	@Override
	public String renderObject(Object object, boolean list) throws Exception {
		if (list) {
			return "<tr><td>" + ((Integer) object).toString() + "</td></tr>\n";
		} else {
			return ((Integer) object).toString() + "\n";
		}
	}

	@Override
	public String renderObject(Object object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
