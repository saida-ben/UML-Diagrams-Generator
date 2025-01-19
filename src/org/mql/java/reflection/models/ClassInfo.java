package org.mql.java.reflection.models;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private String name;
    private List<String> interfaces;
    private List<String> annotations;
    private List<MethodInfo> methods;

    public ClassInfo(String name) {
        this.name = name;
        this.interfaces = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public void addInterface(String interfaceName) {
        interfaces.add(interfaceName);
    }

    public void addAnnotation(String annotationName) {
        annotations.add(annotationName);
    }

    public void addMethod(MethodInfo method) {
        methods.add(method);
    }

    public String getName() {
        return name;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

	public String[] getRelations() {
		// TODO Auto-generated method stub
		return null;
	}
}
