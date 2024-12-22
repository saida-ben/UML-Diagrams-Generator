package org.mql.java.reflection.models;

import java.util.List;

public class ClassInfo {
	private String name;
	private List<String> methods;
	private  List<String> fields;
	


	public ClassInfo() {}



	public ClassInfo(String name, List<String> methods, List<String> fields) {
		super();
		this.name = name;
		this.methods = methods;
		this.fields = fields;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<String> getMethods() {
		return methods;
	}



	public void setMethods(List<String> methods) {
		this.methods = methods;
	}



	public List<String> getFields() {
		return fields;
	}



	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	
}
