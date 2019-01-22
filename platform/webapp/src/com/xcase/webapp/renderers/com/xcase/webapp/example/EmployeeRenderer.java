package com.xcase.webapp.renderers.com.xcase.webapp.example;

import com.xcase.renderers.IObjectRenderer;
import com.xcase.webapp.example.*;

public class EmployeeRenderer extends PersonRenderer implements IObjectRenderer {
	@Override
	public String renderObject(Object object, boolean list) throws Exception {
		if (list) {
			return "<tr><td>" + ((Person) object).getName() + "</td><td>" + ((Person) object).getAge() + "</td><td>" + ((Person) object).getMarried() + "</td></tr>\n";
		} else {
			return ((Employee) object).toString() + "\n";
		}
	}
}
