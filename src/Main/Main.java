
package Main;

import org.mql.java.reflection.ProjectAnalyzer;
import org.mql.java.reflection.models.Project;

public class Main {

   public static void main(String[] args) throws Exception {
       String projectPath = "C:\\\\\\\\Users\\\\\\\\user\\\\\\\\eclipse-workspace\\\\\\\\Revision"; // Remplacez par le chemin vers votre répertoire de projet

       ProjectAnalyzer analyzer = new ProjectAnalyzer(projectPath);
       Project project = analyzer.getProject();

       // Affichage des informations sur le projet
       displayProject(project);
   }

   private static void displayProject(Project project) {
       System.out.println("Project: " + project.getName());

       for (var pkg : project.getPackages()) {
           System.out.println("  Package: " + pkg.getName());

           for (var clazz : pkg.getClasses()) {
               System.out.println("    Class: " + clazz.getName());
               System.out.println("      Interfaces: " + clazz.getInterfaces());
               System.out.println("      Annotations: " + clazz.getAnnotations());
               System.out.println("      Methods:");
               for (var method : clazz.getMethods()) {
                   System.out.printf("         - %s (%s): %s\n", method.getName(), method.getReturnType(), method.getParameters());
               }
           }
       }
   }
}
