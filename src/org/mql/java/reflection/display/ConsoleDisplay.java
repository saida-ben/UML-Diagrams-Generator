package org.mql.java.reflection.display;

import org.mql.java.reflection.models.Project;
import org.mql.java.reflection.models.PackageInfo;
import org.mql.java.reflection.models.ClassInfo;

public class ConsoleDisplay {

    // Méthode pour afficher les informations du projet dans la console
    public static void displayProjectInfo(Project project) {
        System.out.println("Project: " + project.getName());
        for (PackageInfo projectPackage : project.getPackages()) {
            System.out.println("  Package: " + projectPackage.getName());
            for (ClassInfo classInfo : projectPackage.getClasses()) {
                System.out.println("    Class: " + classInfo.getName());
                for (String relation : classInfo.getRelations()) {
                    System.out.println("      Relation: " + relation);
                }
            }
        }
    }
}
