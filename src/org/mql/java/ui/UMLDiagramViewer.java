package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.reflection.Extractor;
import org.mql.java.reflection.models.ClassInfo;
import org.mql.java.reflection.models.PackageInfo;
import org.mql.java.reflection.models.Project;
import org.mql.java.reflection.models.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UMLDiagramViewer extends JFrame {
    
    private JPanel drawingPanel;
    private Project project;

    public UMLDiagramViewer(Project project) {
        this.project = project; // Recevoir le projet extrait
        setTitle("UML Diagram Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un panneau de dessin
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // Créer un panneau de contrôle
        JPanel controlPanel = new JPanel();
        JButton refreshButton = new JButton("Rafraîchir Diagramme");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint(); // Redessiner le panneau
            }
        });
        
        controlPanel.add(refreshButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Classe interne pour dessiner sur le panneau
    private class DrawingPanel extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawUMLDiagram(g); // Appeler la méthode pour dessiner le diagramme UML
        }

        private void drawUMLDiagram(Graphics g) {
            int x = 50;
            int y = 50;
            int width = 200;
            int height = 100;

            // Dessiner chaque classe (entité)
            for (PackageInfo pkg : project.getPackages()) {
                for (ClassInfo cls : pkg.getClasses()) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, width, height); // Dessine un rectangle pour la classe
                    
                    g.setColor(Color.WHITE);
                    g.drawString(cls.getName(), x + 10, y + 20); // Nom de la classe
                    
                    g.setColor(Color.BLACK);
                    g.drawLine(x, y + 30, x + width, y + 30); // Ligne séparant les attributs et méthodes
                    
                    g.drawString("Attributs", x + 10, y + 50); // Attributs (exemple)
                    g.drawString("Méthodes", x + 10, y + 70); // Méthodes (exemple)

                    y += height + 20; // Espacement entre les classes
                }
                x += width + 50; // Espacement horizontal entre les packages
                y = 50; // Réinitialiser la position Y pour le prochain package
            }

            // Dessiner des relations entre les classes si nécessaire
            // Exemple simple: dessiner une ligne entre deux classes
            if (project.getPackages().size() > 1) {
                g.setColor(Color.RED);
                g.drawLine(150, 150, 400, 150); // Ligne de relation entre deux classes (exemple)
                g.drawString("Relation", 250, 140); // Étiquette de relation
            }
        }
    }

}
