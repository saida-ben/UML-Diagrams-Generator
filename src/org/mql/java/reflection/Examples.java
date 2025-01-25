package org.mql.java.reflection;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.mql.java.models.*;
import org.mql.java.ui.UMLDiagramViewer;
import org.mql.java.xml.XMLParser;
import org.mql.java.xml.XMLWriter;

public class Examples {
	
	public Examples() {
		exp02();
	}

	void exp03(){
	    String projectPath = "C:\\\\Users\\\\user\\\\eclipse-workspace\\\\p04-java-xml-parsers";
	    String outputXmiPath = "C:\\\\Users\\\\user\\\\eclipse-workspace\\\\Benzariya Saida\\\\ressources\\\\output2.xmi";
	    Project project = Extractor.extractProject(projectPath);

	    try {
			XmiGenerator.generateXmi(project, outputXmiPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    private void exp02() {
        XMLParser parser = new XMLParser();
        Project project = null;
        
        try {
            project = parser.parse("C:\\Users\\user\\eclipse-workspace\\Benzariya Saida\\ressources\\output.xml");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse du fichier XML.");
            e.printStackTrace();
            return;
        }

        if (project == null) {
            System.err.println("Le projet est nul. Vérifiez le fichier XML ou le parser.");
            return;
        }

        parser.displayModel(project); 
    }



    	
private void exp01() {
    String projectPath = "C:\\Users\\user\\eclipse-workspace\\Benzariya Saida"; 
    String xmlFilePath = "C:\\Users\\user\\eclipse-workspace\\UML-Diagrams-Generator\\ressources\\output.xml";

    Project project = Extractor.extractProject(projectPath); 

    System.out.println("Project: " + project.getName());
    
    for (PackageInfo pkg : project.getPackages()) { 
        System.out.println("  Package: " + pkg.getName());
        
        for (ClassInfo cls : pkg.getClasses()) { 
            System.out.println("Class: " + cls.getName());
            System.out.println("Superclass: " + cls.getSuperclass());
            System.out.println("Interfaces: " + cls.getInterfaces());
            System.out.println("Fields: ");
            
            for (FieldModel f : cls.getFields()) { 
                System.out.println("              " + f.getName() + ": " + f.getType());
            }
            
            System.out.println("Methods: ");
            
            for (MethodInfo m : cls.getMethods()) { 
                System.out.println(" " + m.getName() + "(" + m.getParameters() + "): " + m.getReturnType());
            }
            
            System.out.println("Annotations: " + cls.getAnnotations());

            System.out.println(" Relations: ");
            if (cls.getRelations().isEmpty()) {
                System.out.println(" Aucune relation trouvée pour cette classe.");
            } else {
                for (Relation relation : cls.getRelations()) {
                    System.out.println(" " + relation.getSource() + " " + relation.getType() + " " + relation.getTarget());
                }
            }
        }
        
        System.out.println();
    }

    XMLWriter.writeProjectToXML(project, xmlFilePath); // Cela générera le fichier XML avec les informations extraites

    System.out.println("Fichier XML généré avec succès !");

    SwingUtilities.invokeLater(() -> {
        UMLDiagramViewer viewer = new UMLDiagramViewer(project);
        viewer.setVisible(true);
    });
}

		

	public static void main(String[] args) {
        new Examples();
   }
	
}
