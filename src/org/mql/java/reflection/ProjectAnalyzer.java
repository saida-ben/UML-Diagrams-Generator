package org.mql.java.reflection;

import org.mql.java.reflection.models.ClassInfo;
import org.mql.java.reflection.models.MethodInfo;
import org.mql.java.reflection.models.PackageInfo;
import org.mql.java.reflection.models.Project;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ProjectAnalyzer {

    private Project project;

    public ProjectAnalyzer(String projectPath) {
        this.project = new Project("MyProject");
        analyzeProject(projectPath);
    }

    // Analyse du projet pour extraire les packages et classes
    private void analyzeProject(String projectPath) {
        File projectDir = new File(projectPath);
        
        // Récupérer tous les packages dans le répertoire src
        File[] directories = projectDir.listFiles(File::isDirectory);

        if (directories != null) {
            for (File dir : directories) {
                PackageInfo pkg = new PackageInfo(dir.getName());
                analyzePackage(pkg, dir);
                project.addPackage(pkg);
            }
        }
    }

   // Analyse d'un package pour extraire les classes
   private void analyzePackage(PackageInfo pkg, File packageDir) {
       File[] classFiles = packageDir.listFiles((d, name) -> name.endsWith(".class"));

       if (classFiles != null) {
           for (File classFile : classFiles) {
               analyzeClass(pkg, classFile);
           }
       }
   }

   // Analyse d'une classe pour extraire les informations pertinentes
   private void analyzeClass(PackageInfo pkg, File classFile) {
       try {
           // Récupérer le nom de la classe en utilisant le chemin complet
           String className = "org.mql.java." + pkg.getName() + "." + classFile.getName().replace(".class", "");
           Class<?> clazz = Class.forName(className);

           // Ajouter la classe
           ClassInfo classInfo = new ClassInfo(clazz.getName());

           // Ajouter les interfaces implémentées
           Class<?>[] interfaces = clazz.getInterfaces();
           for (Class<?> iface : interfaces) {
               classInfo.addInterface(iface.getName());
           }

           // Ajouter les annotations
           if (clazz.isAnnotationPresent(Deprecated.class)) { // Exemple avec l'annotation Deprecated
               classInfo.addAnnotation(Deprecated.class.getName());
           }

           // Ajouter les méthodes
           Method[] methods = clazz.getDeclaredMethods();
           for (Method method : methods) {
               if (Modifier.isPublic(method.getModifiers())) { // Filtrer par visibilité si nécessaire
                   MethodInfo methodInfo = new MethodInfo(method.getName(), method.getReturnType().getName());
                   for (Class<?> paramType : method.getParameterTypes()) {
                       methodInfo.addParameter(paramType.getName());
                   }
                   classInfo.addMethod(methodInfo);
               }
           }

           // Ajouter la classe au package
           pkg.addClass(classInfo);
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }

   public Project getProject() {
       return project;
   }
}
