package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {
    private String name;
    private List<ClassInfo> classes;

    public PackageInfo(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
    }

    public void addClass(ClassInfo classInfo) {
        classes.add(classInfo);
    }

    public String getName() {
        return name;
    }

    public List<ClassInfo> getClasses() {
        return classes;
    }
}
