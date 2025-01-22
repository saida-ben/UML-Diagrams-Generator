package org.mql.java.ui;

import javax.swing.*;
import org.mql.java.models.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class UMLDiagramViewer extends JFrame {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 700;
    private static final int CLASS_BOX_WIDTH = 250;
    private static final int CLASS_BOX_HEIGHT = 300;
    private static final int HORIZONTAL_SPACING = 200; // Augmenté
    private static final int VERTICAL_SPACING = 250;   // Augmenté

    private final Project project;
    private final Map<String, Point> classPositions = new HashMap<>();
    private final DrawingPanel drawingPanel;

    public UMLDiagramViewer(Project project) {
        this.project = project;
        setTitle("UML Diagram Viewer");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(FRAME_WIDTH * 2, FRAME_HEIGHT * 2));
        
        JScrollPane scrollPane = new JScrollPane(drawingPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane);
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            renderClassesWithIntelligentSpacing(g2);
        }

        private void renderClassesWithIntelligentSpacing(Graphics2D g2) {
            List<ClassInfo> allClasses = collectAllClasses();
            int gridColumns = (int) Math.ceil(Math.sqrt(allClasses.size()));
            
            for (int i = 0; i < allClasses.size(); i++) {
                int row = i / gridColumns;
                int col = i % gridColumns;
                
                int x = col * (CLASS_BOX_WIDTH + HORIZONTAL_SPACING) + 50;
                int y = row * (CLASS_BOX_HEIGHT + VERTICAL_SPACING) + 50;
                
                ClassInfo classInfo = allClasses.get(i);
                renderClassBox(g2, classInfo, x, y);
            }

            // Conserver l'ancien code de relations
            drawRelations(g2);
        }

        private void drawRelations(Graphics2D g2) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);

            for (PackageInfo pkg : project.getPackages()) {
                for (ClassInfo cls : pkg.getClasses()) {
                    Point sourcePosition = classPositions.get(cls.getName());

                    for (Relation relation : cls.getRelations()) {
                        Point targetPosition = classPositions.get(relation.getTarget());

                        if (sourcePosition != null && targetPosition != null) {
                            switch (relation.getType()) {
                                case "Association":
                                    g2.setColor(Color.BLACK);
                                    g2.drawLine(sourcePosition.x, sourcePosition.y, targetPosition.x, targetPosition.y);
                                    break;
                                case "Aggregation":
                                    g2.setColor(Color.BLUE);
                                    drawAggregation(g2, sourcePosition, targetPosition);
                                    break;
                                case "Composition":
                                    g2.setColor(Color.RED);
                                    drawComposition(g2, sourcePosition, targetPosition);
                                    break;
                                case "Inheritance":
                                    g2.setColor(Color.GREEN);
                                    drawInheritance(g2, sourcePosition, targetPosition);
                                    break;
                            }
                            String relationType = relation.getType();
                            int midX = (sourcePosition.x + targetPosition.x) / 2;
                            int midY = (sourcePosition.y + targetPosition.y) / 2;
                            int offsetX = 5;
                            int offsetY = -10;
                            g2.drawString(relationType, midX - relationType.length() * 3 / 2 + offsetX, midY + offsetY);
                        }
                    }
                }
            }
        }

        private void renderClassBox(Graphics2D g2, ClassInfo cls, int x, int y) {
            // Style de boîte de classe amélioré
            g2.setColor(new Color(70, 130, 180, 200));
            g2.fillRoundRect(x, y, CLASS_BOX_WIDTH, CLASS_BOX_HEIGHT, 15, 15);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, CLASS_BOX_WIDTH, CLASS_BOX_HEIGHT, 15, 15);

            // Titre de la classe
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(cls.getName(), x + 10, y + 30);

            // Attributs
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            int yOffset = y + 60;
            g2.drawString("Attributs:", x + 10, yOffset);
            yOffset += 20;
            for (FieldModel field : cls.getFields()) {
                g2.drawString("- " + field.getName() + ": " + field.getType(), x + 20, yOffset);
                yOffset += 20;
            }

            // Méthodes
            yOffset += 10;
            g2.drawString("Méthodes:", x + 10, yOffset);
            yOffset += 20;
            for (MethodInfo method : cls.getMethods()) {
                g2.drawString("+ " + method.getName() + "()", x + 20, yOffset);
                yOffset += 20;
            }

            // Stocker la position du centre de la classe
            classPositions.put(cls.getName(), new Point(x + CLASS_BOX_WIDTH / 2, y + CLASS_BOX_HEIGHT / 2));
        }

        // Méthodes de relation existantes (drawAggregation, drawComposition, drawInheritance)
        private void drawAggregation(Graphics2D g2, Point sourcePoint, Point targetPoint) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);

            int diamondSize = 10;

            int[] xPoints = {sourcePoint.x - diamondSize / 2, sourcePoint.x, sourcePoint.x + diamondSize / 2};
            int[] yPoints = {sourcePoint.y - diamondSize, sourcePoint.y - diamondSize / 4, sourcePoint.y - diamondSize / 4};

            g2.drawPolygon(xPoints, yPoints, xPoints.length);
            g2.drawLine(sourcePoint.x, sourcePoint.y, targetPoint.x, targetPoint.y);
        }

        private void drawComposition(Graphics2D g2, Point sourcePoint, Point targetPoint) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);

            int diamondSize = 10;

            int[] xPoints = {sourcePoint.x - diamondSize / 2, sourcePoint.x, sourcePoint.x + diamondSize / 2};
            int[] yPoints = {sourcePoint.y - diamondSize, sourcePoint.y - diamondSize / 4, sourcePoint.y - diamondSize / 4};

            g2.fillPolygon(xPoints, yPoints, xPoints.length);
            g2.drawLine(sourcePoint.x, sourcePoint.y, targetPoint.x, targetPoint.y);
        }

        private void drawInheritance(Graphics2D g2, Point sourcePoint, Point targetPoint) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);

            int arrowSize = 10;

            double dx = targetPoint.x - sourcePoint.x;
            double dy = targetPoint.y - sourcePoint.y;

            double angle = Math.atan2(dy, dx);

            int arrowX1 = (int) (targetPoint.x - arrowSize * Math.cos(angle - Math.PI / 6));
            int arrowY1 = (int) (targetPoint.y - arrowSize * Math.sin(angle - Math.PI / 6));

            int arrowX2 = (int) (targetPoint.x - arrowSize * Math.cos(angle + Math.PI / 6));
            int arrowY2 = (int) (targetPoint.y - arrowSize * Math.sin(angle + Math.PI / 6));

            g2.drawLine(sourcePoint.x, sourcePoint.y, targetPoint.x, targetPoint.y);
            g2.drawLine(targetPoint.x, targetPoint.y, arrowX1, arrowY1);
            g2.drawLine(targetPoint.x, targetPoint.y, arrowX2, arrowY2);
        }

        private List<ClassInfo> collectAllClasses() {
            List<ClassInfo> allClasses = new ArrayList<>();
            for (PackageInfo pkg : project.getPackages()) {
                allClasses.addAll(pkg.getClasses());
            }
            return allClasses;
        }
    }

}
