package org.mql.java.reflection;

import javax.swing.SwingUtilities;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldModel;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.Project;
import org.mql.java.ui.UMLDiagramViewer;
import org.mql.java.xml.XMLWriter;

public class Examples {
    public static void main(String[] args) {
        String projectPath = "C:\\Users\\user\\eclipse-workspace\\p04-java-xml-parsers"; // Chemin vers le projet
        String xmlFilePath = "C:\\Users\\user\\eclipse-workspace\\UML-Diagrams-Generator\\ressources\\output.xml";

        // Extraction des informations du projet
        Project project = Extractor.extractProject(projectPath); 

        // Affichage des informations extraites par package
        System.out.println("Project: " + project.getName());
        
        for (PackageInfo pkg : project.getPackages()) { 
            System.out.println("  Package: " + pkg.getName());
            
            for (ClassInfo cls : pkg.getClasses()) { 
                System.out.println("      Class: " + cls.getName());
                System.out.println("          Superclass: " + cls.getSuperclass());
                System.out.println("          Interfaces: " + cls.getInterfaces());
                System.out.println("          Fields: ");
                
                for (FieldModel f : cls.getFields()) { 
                    System.out.println("              " + f.getName() + ": " + f.getType());
                }
                
                System.out.println("          Methods: ");
                
                for (MethodInfo m : cls.getMethods()) { 
                    System.out.println("              " + m.getName() + "(" + m.getParameters() + "): " + m.getReturnType());
                }
                
                System.out.println("          Annotations: " + cls.getAnnotations());
            }
            
            System.out.println();
        }

        // Appel de la méthode d'écriture XML dans la classe XMLWriter
        XMLWriter.writeProjectToXML(project, xmlFilePath); // Cela fonctionnera maintenant correctement

        System.out.println("Fichier XML généré avec succès !");

        // Créer et afficher l'interface graphique pour le diagramme UML
        SwingUtilities.invokeLater(() -> {
            UMLDiagramViewer viewer = new UMLDiagramViewer(project);
            viewer.setVisible(true);
        });
    }
}
