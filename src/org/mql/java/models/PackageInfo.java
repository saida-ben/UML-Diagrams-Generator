package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {
    private String name;
    private List<ClassInfo> classes; // List of class information
    private List<Relation> relations; // List of relations

    // Constructor with only name
    public PackageInfo(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
        this.relations = new ArrayList<>();
    }

    // Constructor with all fields
    public PackageInfo(String name, List<ClassInfo> classes, List<Relation> relations) {
        this.name = name;
        this.classes = classes != null ? classes : new ArrayList<>();
        this.relations = relations != null ? relations : new ArrayList<>();
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for classes
    public List<ClassInfo> getClasses() {
        return classes;
    }

    // Method to add a class to the list
    public void addClass(ClassInfo classInfo) {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        classes.add(classInfo);
    }

    // Getter for relations
    public List<Relation> getRelations() {
        return relations;
    }

    // Method to add a relation to the list
    public void addRelation(Relation relation) {
        if (relations == null) {
            relations = new ArrayList<>();
        }
        relations.add(relation);
    }

    // Optional: Override toString() for debugging
    @Override
    public String toString() {
        return "PackageInfo{" +
               "name='" + name + '\'' +
               ", classes=" + classes +
               ", relations=" + relations +
               '}';
    }
}
