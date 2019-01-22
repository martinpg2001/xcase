package com.xcase.webapp.example;

public class Person {
	
	protected int age;
	protected boolean married;
	protected String name;
    
    public Person() {
    	name = "Martin";
    	age = 1;
    	married = true;
    }

    public Person(String name) {
    	age = 2;
    	married = true;
    	this.name = name;
    }
    
    public Person(String name, int age) {
    	this.age = age;
    	married = true;
    	this.name = name;
    }
    
    public Person(String name, int age, boolean married) {
    	this.age = age;
    	this.married = married;
    	this.name = name;
    }
    
    public int getAge() {
    	return age;
    }
    
    public boolean getMarried() {
    	return married;
    }
    
    public String getName() {
    	return name;
    }
    
    public String toString() {
    	return name + ":" + age + ":" + married;
    }
}
