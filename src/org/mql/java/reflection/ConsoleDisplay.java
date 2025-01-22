package org.mql.java.reflection;

import org.mql.java.models.*;

public class ConsoleDisplay {

    public static void displayProjectInfo(Project project) {
        System.out.println("Project: " + project.getName());

        for (PackageInfo pkg : project.getPackages()) {
            System.out.println("  Package: " + pkg.getName());

            for (ClassInfo cls : pkg.getClasses()) {
                System.out.println("      Class: " + cls.getName());
                System.out.println("          Superclass: " + (cls.getSuperclass() != null ? cls.getSuperclass() : "null"));
                System.out.println("          Interfaces: " + (cls.getInterfaces().isEmpty() ? "[]" : cls.getInterfaces()));

                // Affichage des champs
                System.out.println("          Fields: ");
                if (cls.getFields().isEmpty()) {
                    System.out.println("              None");
                } else {
                    for (FieldModel f : cls.getFields()) {
                        System.out.println("              " + f.getName() + ": " + f.getType());
                    }
                }

                // Affichage des méthodes
                System.out.println("          Methods: ");
                if (cls.getMethods().isEmpty()) {
                    System.out.println("              None");
                } else {
                    for (MethodInfo m : cls.getMethods()) {
                        String params = m.getParameters().isEmpty() ? "[]" : m.getParameters().toString();
                        System.out.println("              " + m.getName() + "(" + params + "): " + m.getReturnType());
                    }
                }

                // Affichage des annotations
                System.out.println("          Annotations: " + (cls.getAnnotations().isEmpty() ? "[]" : cls.getAnnotations()));

                // Affichage des relations
                System.out.println("          Relations: ");
                if (cls.getRelations().isEmpty()) { // Utilise le getter pour accéder aux relations
                    System.out.println("              None");
                } else {
                    for (Relation relation : cls.getRelations()) { // Parcourt les objets Relation
                        System.out.println("              " + relation.toString()); // Utilise toString()
                    }
                }
            }

            System.out.println();
        }
    }
}
