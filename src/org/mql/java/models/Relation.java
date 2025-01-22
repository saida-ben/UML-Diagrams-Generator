package org.mql.java.models;

public class Relation {
    private String source; // Nom de la classe source
    private String target; // Nom de la classe cible
    private String type;   // Type de relation : Association, Aggregation, Composition, Inheritance, Realization
    private String label;  // Étiquette optionnelle pour la relation (ex. multiplicité)

    public Relation(String source, String target, String type) {
        this.source = source;
        this.target = target;
        this.type = type;
    }

    public Relation(String source, String target, String type, String label) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.label = label;
    }

    // Getters et Setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "RelationModel{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
