package com.xcase.webapp.example;

public class Employee extends Person {
    protected Person manager;
    protected int employeeId;
    protected boolean remote;
    
    public Employee(String name) {
    	super(name);
    	manager = new Person("Martin");
    	employeeId = 12345;
    }
    
    public Employee(String name, int age, boolean married, boolean remote) {
    	super(name);
    	this.age = age;
    	this.married = married;
    	this.manager = new Person("Martin");
    	this.employeeId = 12345;
    	this.remote = remote;
    }
    
    public Person getManager() {
    	return manager;
    }
    
    public int getEmployeeID() {
    	return employeeId;
    }
    
    public boolean getRemote() {
    	return remote;
    }    
    
    public String toString() {
    	return name + ":" + age + ":" + married + ":(" + manager + "):" + employeeId + ":" + remote;
    }
}
