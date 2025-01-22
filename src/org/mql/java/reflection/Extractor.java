package org.mql.java.reflection;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.mql.java.models.*;

public class Extractor {

    public static Project extractProject(String projectPath) {
        Project project = new Project(projectPath);

        File projectDir = new File(projectPath);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.err.println("Invalid project path: " + projectPath);
            return project; // Retourne un projet vide en cas d'erreur
        }

        // Trouver le répertoire contenant les fichiers .class
        File classDirectory = findClassDirectory(projectDir);
        if (classDirectory == null) {
            System.err.println("No class directory found in project path: " + projectPath);
            return project; // Retourne un projet vide si aucun dossier de classe n'est trouvé
        }

        Map<String, PackageInfo> packageMap = new HashMap<>();
        exploreDirectory(classDirectory, classDirectory.getAbsolutePath(), packageMap);

        for (PackageInfo packageInfo : packageMap.values()) {
            project.addPackage(packageInfo); // Ajouter les packages au projet
        }
        
        return project; // Retourne le projet avec les packages et classes extraits
    }

    private static File findClassDirectory(File projectDir) {
        // Cherche un dossier 'bin' ou 'target/classes'
        File[] potentialDirs = projectDir.listFiles(File::isDirectory);
        for (File dir : potentialDirs) {
            if (dir.getName().equals("bin") || dir.getName().equals("target")) {
                // Vérifie si 'target' contient 'classes'
                File classesDir = new File(dir, "classes");
                if (classesDir.exists() && classesDir.isDirectory()) {
                    return classesDir; // Retourne le dossier 'classes'
                } else if (dir.getName().equals("bin")) {
                    return dir; // Retourne directement 'bin'
                }
            }
        }
        return null; // Aucun dossier de classe trouvé
    }

   // Explore les répertoires pour trouver les fichiers .class
    private static void exploreDirectory(File directory, String baseDirectory, Map<String, PackageInfo> packageMap) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                exploreDirectory(file, baseDirectory, packageMap);
            } else if (file.getName().endsWith(".class")) {
                String qualifiedName = convertToQualifiedName(file.getAbsolutePath(), baseDirectory);
                System.out.println("Attempting to load class: " + qualifiedName);

                try {
                    // Créer un URLClassLoader si le class loader actuel échoue
                    File classDirectory = new File(baseDirectory);
                    URLClassLoader classLoader = new URLClassLoader(new URL[] { classDirectory.toURI().toURL() });

                    Class<?> clazz = classLoader.loadClass(qualifiedName);
                    ClassInfo classInfo = extractClass(clazz);

                    String packageName = clazz.getPackage().getName();
                    PackageInfo packageInfo = packageMap.getOrDefault(packageName, new PackageInfo(packageName));
                    packageInfo.addClass(classInfo);
                    packageMap.put(packageName, packageInfo);

                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + qualifiedName); // Affiche l'erreur si la classe n'est pas trouvée
                } catch (MalformedURLException e) {
                    System.err.println("URL error while loading class: " + qualifiedName); // En cas de problème avec l'URL
                }
            }
        }
    }




   // Extrait les informations d'une classe donnée
   private static ClassInfo extractClass(Class<?> clazz) {
	   ClassInfo classInfo = new ClassInfo(clazz.getSimpleName(), clazz.isInterface(), clazz.isEnum());

       if (clazz.getSuperclass() != null) { // Récupère la superclasse si elle existe
           classInfo.setSuperclass(clazz.getSuperclass().getName());
       }

       for (Class<?> iface : clazz.getInterfaces()) { // Récupère les interfaces implémentées
           classInfo.addInterface(iface.getName());
       }

       for (Field field : clazz.getDeclaredFields()) { // Récupère les champs de la classe
           classInfo.addField(new FieldModel(field.getName(), field.getType().getName()));
       }

       for (Method method : clazz.getDeclaredMethods()) { // Récupère les méthodes de la classe
    	   MethodInfo methodInfo = new MethodInfo(method.getName(), method.getReturnType().getName());
           for (Parameter param : method.getParameters()) { // Récupère les paramètres de la méthode
               methodInfo.addParameter(param.getType().getName());
           }
           classInfo.addMethod(methodInfo);
       }

       for (Annotation annotation : clazz.getAnnotations()) { // Récupère les annotations présentes sur la classe
           classInfo.addAnnotation(new AnnotationInfo(annotation.annotationType().getSimpleName()));
       }

       return classInfo; // Retourne l'objet ClassModel rempli avec toutes les informations extraites
   }

   private static String convertToQualifiedName(String filePath, String baseDirectory) {
	    // Supprime le chemin de base
	    String relativePath = filePath.substring(baseDirectory.length() + 1);
	    // Remplace les séparateurs de fichier par des points et supprime l'extension .class
	    return relativePath.replace(File.separator, ".").replace(".class", "");
	}

}
