package org.mql.java.reflection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldModel;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.Project;
import org.mql.java.models.Relation;
import org.mql.java.parsers.XMLParser;
import org.mql.java.ui.UMLDiagramViewer;
import org.mql.java.xml.XMLWriter;

public class Examples {
	
	public Examples() {
		exp02();
	}

	void exp03(){
	    String projectPath = "C:\\\\Users\\\\user\\\\eclipse-workspace\\\\p04-java-xml-parsers";
	    String outputXmiPath = "C:\\\\Users\\\\user\\\\eclipse-workspace\\\\Benzariya Saida\\\\ressources\\\\output2.xmi";

	    // Extraire les informations du projet
	    Project project = Extractor.extractProject(projectPath);

	    // Générer le fichier XMI
	    try {
			XmiGenerator.generateXmi(project, outputXmiPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    private void exp05() {
        // Initialisation du parseur XML
        XMLParser parser = new XMLParser();
        Project project = null;
        
        // Appel à la méthode parse pour charger le projet depuis le fichier XML
        try {
            project = parser.parse("C:\\Users\\user\\eclipse-workspace\\Benzariya Saida\\ressources\\output.xml");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse du fichier XML.");
            e.printStackTrace();
            return;
        }

        // Vérification si le projet est nul
        if (project == null) {
            System.err.println("Le projet est nul. Vérifiez le fichier XML ou le parser.");
            return;
        }

        // Affichage du modèle directement à l'aide de la méthode displayModel dans le parser
        parser.displayModel(project); // Appel de la méthode displayModel dans XMLParser
    }


	
	
    private void exp01() {
    	
    	      String projectPath = "C:\\Users\\user\\eclipse-workspace\\tests-relations"; 
    	      Project project = Extractor.extractProject(projectPath); 

    	      SwingUtilities.invokeLater(() -> { 
    	         UMLDiagramViewer viewer = new UMLDiagramViewer(project); 
    	         viewer.setVisible(true); 
    	      });
    	   }
    	
private void exp02() {
    // Définir le chemin du projet et le chemin de sortie pour le fichier XML
    String projectPath = "C:\\Users\\user\\eclipse-workspace\\tests-relations"; // Remplacez par le chemin réel du projet
    String xmlFilePath = "C:\\Users\\user\\eclipse-workspace\\UML-Diagrams-Generator\\ressources\\output.xml"; // Chemin vers le fichier XML de sortie

    // Extraction des informations du projet en utilisant Extractor
    Project project = Extractor.extractProject(projectPath); 

    // Affichage des informations extraites par package
    System.out.println("Project: " + project.getName());
    
    // Boucle à travers les packages du projet
    for (PackageInfo pkg : project.getPackages()) { 
        System.out.println("  Package: " + pkg.getName());
        
        // Boucle à travers les classes dans chaque package
        for (ClassInfo cls : pkg.getClasses()) { 
            System.out.println("      Class: " + cls.getName());
            System.out.println("          Superclass: " + cls.getSuperclass());
            System.out.println("          Interfaces: " + cls.getInterfaces());
            System.out.println("          Fields: ");
            
            // Afficher les champs de la classe
            for (FieldModel f : cls.getFields()) { 
                System.out.println("              " + f.getName() + ": " + f.getType());
            }
            
            System.out.println("          Methods: ");
            
            // Afficher les méthodes de la classe
            for (MethodInfo m : cls.getMethods()) { 
                System.out.println("              " + m.getName() + "(" + m.getParameters() + "): " + m.getReturnType());
            }
            
            System.out.println("          Annotations: " + cls.getAnnotations());

            // Afficher les relations dans la classe
            System.out.println("          Relations: ");
            if (cls.getRelations().isEmpty()) {
                System.out.println("          Aucune relation trouvée pour cette classe.");
            } else {
                for (Relation relation : cls.getRelations()) {
                    System.out.println("              " + relation.getSource() + " " + relation.getType() + " " + relation.getTarget());
                }
            }
        }
        
        System.out.println();
    }

    // Appel de la méthode pour écrire les informations du projet dans un fichier XML
    XMLWriter.writeProjectToXML(project, xmlFilePath); // Cela générera le fichier XML avec les informations extraites

    System.out.println("Fichier XML généré avec succès !");

    // Créer et afficher l'interface graphique pour le diagramme UML
    SwingUtilities.invokeLater(() -> {
        UMLDiagramViewer viewer = new UMLDiagramViewer(project);
        viewer.setVisible(true);
    });
}

		

	public static void main(String[] args) {
        new Examples();
   }
	
}
