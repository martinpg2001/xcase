package com.xcase.webapp.renderers.com.xcase.webapp.example;

import com.xcase.renderers.IObjectRenderer;
import com.xcase.webapp.example.*;

public class PersonRenderer implements IObjectRenderer {

	@Override
	public String renderTableHeader() throws Exception {
		return "<tr><th>Name</th><th>Age</th><th>Married</th></tr>\n";
	}

	@Override
	public String renderObject(Object object, boolean list) throws Exception {
		if (list) {
			return "<tr><td>" + ((Person) object).getName() + "</td><td>" + ((Person) object).getAge() + "</td><td>" + ((Person) object).getMarried() + "</td></tr>\n";
		} else {
			return ((Person) object).toString() + "\n";
		}
	}

	@Override
	public String renderObject(Object object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
