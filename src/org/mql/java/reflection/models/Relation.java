package org.mql.java.reflection.models;

public class Relation {
    private String type; // Type de relation (e.g., "extends", "implements", "uses")
    private String relatedClass; // Nom de la classe liée

    public Relation(String type, String relatedClass) {
        this.type = type;
        this.relatedClass = relatedClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedClass() {
        return relatedClass;
    }

    public void setRelatedClass(String relatedClass) {
        this.relatedClass = relatedClass;
    }

    @Override
    public String toString() {
        return type + " " + relatedClass; // Exemple : "extends java.lang.Object"
    }
}
