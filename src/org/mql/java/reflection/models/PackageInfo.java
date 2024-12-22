package org.mql.java.reflection.models;

import java.util.List;

public class PackageInfo {
	String name;
	List<ClassInfo> classes;
	List<InterfaceInfo> interfaces;
	List<EnumInfo> enums;
	List<AnnotationInfo> annotations;
	
	
	public PackageInfo() {}


	public PackageInfo(String name, List<ClassInfo> classes, List<InterfaceInfo> interfaces, List<EnumInfo> enums,
			List<AnnotationInfo> annotations) {
		super();
		this.name = name;
		this.classes = classes;
		this.interfaces = interfaces;
		this.enums = enums;
		this.annotations = annotations;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<ClassInfo> getClasses() {
		return classes;
	}


	public void setClasses(List<ClassInfo> classes) {
		this.classes = classes;
	}


	public List<InterfaceInfo> getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(List<InterfaceInfo> interfaces) {
		this.interfaces = interfaces;
	}


	public List<EnumInfo> getEnums() {
		return enums;
	}


	public void setEnums(List<EnumInfo> enums) {
		this.enums = enums;
	}


	public List<AnnotationInfo> getAnnotations() {
		return annotations;
	}


	public void setAnnotations(List<AnnotationInfo> annotations) {
		this.annotations = annotations;
	}
	
	

}
