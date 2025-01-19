package org.mql.java.reflection.models;

public class Relation {
    private String type;
    private String relatedClass;

    public Relation(String type, String relatedClass) {
        this.type = type;
        this.relatedClass = relatedClass;
    }

    public String getType() {
        return type;
    }

    public String getRelatedClass() {
        return relatedClass;
    }
}
