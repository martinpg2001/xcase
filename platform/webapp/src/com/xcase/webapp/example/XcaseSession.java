package com.xcase.webapp.example;

import com.xcase.annotations.XcaseMethodAnnotation;
import com.xcase.annotations.XcaseParameterAnnotation;
import java.util.*;

public class XcaseSession {
	private String defaultName = "Martin";
	private int defaultAge = 59;
	
//	private XcaseSession() {
//		
//	}
	
	@XcaseMethodAnnotation(group = "constructors")
	public XcaseSession(@XcaseParameterAnnotation(name = "defaultName") String defaultName, @XcaseParameterAnnotation(name = "defaultAge", displayType = "int") int defaultAge, @XcaseParameterAnnotation(name = "defaultMarried", displayType = "boolean") boolean defaultMarried) {
		this.defaultName = defaultName;
		this.defaultAge = defaultAge;
	}
	
	@XcaseMethodAnnotation(group = "arithmetic")
    public Integer add(@XcaseParameterAnnotation(name = "summand1", displayType = "int") int summand1, @XcaseParameterAnnotation(name = "summand2", displayType = "int") int summand2) {
    	return Integer.valueOf(summand1 + summand2);
    }
	
	@XcaseMethodAnnotation(group = "arithmetic")
    public Integer multiply(@XcaseParameterAnnotation(name = "factor1", displayType = "int") int factor1, @XcaseParameterAnnotation(name = "factor2", displayType = "int") int factor2) {
    	return Integer.valueOf(factor1 * factor2);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Employee createEmployee(@XcaseParameterAnnotation(name = "married", displayType = "boolean") boolean married, @XcaseParameterAnnotation(name = "remote", displayType = "boolean") boolean remote) {
    	return new Employee(defaultName, defaultAge, married, remote);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Employee createEmployee(@XcaseParameterAnnotation(name = "name") String name, @XcaseParameterAnnotation(name = "age", displayType = "int") int age, @XcaseParameterAnnotation(name = "married", displayType = "boolean") boolean married, @XcaseParameterAnnotation(name = "remote", displayType = "boolean") boolean remote) {
    	return new Employee(name, age, married, remote);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Person createPerson(@XcaseParameterAnnotation(name = "married", displayType = "boolean") boolean married) {
    	return new Person(defaultName, defaultAge, married);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Person createPerson(@XcaseParameterAnnotation(name = "name") String name, @XcaseParameterAnnotation(name = "age", displayType = "int") int age, @XcaseParameterAnnotation(name = "married", displayType = "boolean") boolean married) {
    	return new Person(name, age, married);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Person getEmployee() {
    	return new Employee(defaultName);
    }
	
	@XcaseMethodAnnotation(group = "people")
    public Person getEmployee(@XcaseParameterAnnotation(name = "name") String name) {
    	return new Employee(name);
    }	
	
	@XcaseMethodAnnotation(group = "people")
    public Person getDefaultPerson() {
    	return new Person(defaultName, defaultAge);
	}
	
	@XcaseMethodAnnotation(group = "people")
    public List<Person> getPeople() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Abigail"));
		personList.add(new Person("Martin"));
		personList.add(new Person("Alison"));
		personList.add(new Person("Andrew"));
		personList.add(new Person("Daniel"));
    	return personList;
	}
	
	@XcaseMethodAnnotation(group = "people")
    public Person getPerson(@XcaseParameterAnnotation(name = "name") String name) {
    	return new Person(name);
    }
	
	@XcaseMethodAnnotation(group = "string")
    public String concatenate(@XcaseParameterAnnotation(name = "string1") String string1, @XcaseParameterAnnotation(name = "string2") String string2) {
    	return string1 + string2;
    }
	
	@XcaseMethodAnnotation(group = "string")
    public String reverse(@XcaseParameterAnnotation(name = "string") String string) {
    	return new StringBuilder(string).reverse().toString();
    }
	
	@XcaseMethodAnnotation(group = "test")
    public String throwException(@XcaseParameterAnnotation(name = "string") String message) throws Exception {
    	throw new Exception(message);
    }
}
