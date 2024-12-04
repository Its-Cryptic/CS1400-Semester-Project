package org.physicsengine.render;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.physicsengine.PhysicsSim;
import org.physicsengine.core.Engine;
import org.physicsengine.core.PhysicsObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Graph extends JFrame {
    private static final Logger LOGGER = LogManager.getLogger(Graph.class);
    private CvGraphingCalculator canvas;

    public Graph() {
        super("Graph");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1000, 1000);

        canvas = new CvGraphingCalculator();
        add(canvas, BorderLayout.CENTER);
        setVisible(true);

        new Timer(2, e -> canvas.repaint()).start();
    }

    public static class CvGraphingCalculator extends JPanel {
        public CvGraphingCalculator() {
            setDoubleBuffered(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension d = getSize();
            Vector2i max = new Vector2i(d.width - 1, d.height - 1);

            drawGraph(g, max);
            drawPoints(g, max);
            drawPath(g, max);
        }


        /** @param g awt graphics
         * @param scale scale of the graph (how "zoomed in" it is)
         * @param resolution resolution of the graph (space between each point drawn; higher resolution = closer together)
         */
        void equation(Graphics g, Vector2i max, int scale, int resolution) {
            float x, y;

            g.translate(max.x() / 2, max.y() / 2);

            g.setColor(Color.black);

            for (x = -((float) max.x() / 2); x < (float) max.x() / 2; x += (float) .01 / resolution) {
                y = (float)
                        -(
                                Math.sin(100 * x) + Math.sin(x)
                        );

                g.drawLine(Math.round(x * scale), Math.round(y * scale), Math.round(x * scale), Math.round(y * scale));
            }
        }

        void drawGraph(Graphics g, Vector2i max) {
            Vector2i mid = new Vector2i(max.x() / 2, max.y() / 2);

            // draw x and y axis
            g.setColor(Color.lightGray);
            g.drawLine(0, mid.y(), max.x(), mid.y());
            g.drawLine(mid.x(), 0, mid.x(), max.y());
        }

        void drawPoints(Graphics g, Vector2i max) {
            Engine engine = PhysicsSim.engine;
            List<PhysicsObject> physicsObjects;
            synchronized (engine) {
                physicsObjects = engine.getPhysicsEnvironment().getPhysicsObjects();
            }
            Vector2i mid = new Vector2i(max.x() / 2, max.y() / 2);

            int scale = 25;

            for (PhysicsObject object : physicsObjects) {
                Vector3f pos = object.getPosition();
                Vector2i point = new Vector2i((int) pos.x(), (int) -pos.y());
                point.add(mid);
                point.sub(new Vector2i(scale / 2));

                g.setColor(Color.BLUE);
                g.drawOval(point.x(), point.y(), scale, scale);
                g.drawChars(object.getName().toCharArray(), 0, object.getName().length(), point.x(), point.y() - 20);
                String position = "Position: (" + formatFloat(pos.x()) + ", " + formatFloat(pos.y()) + ")";
                g.drawChars(position.toCharArray(), 0, position.length(), point.x(), point.y() - 5);
            }
        }

        private float formatFloat(float f) {
            return (float) Math.round(f * 100) / 100;
        }

        void drawPath(Graphics g, Vector2i max) {
            Engine engine = PhysicsSim.engine;

            List<PhysicsObject> physicsObjects;
            synchronized (engine) {
                physicsObjects = engine.getPhysicsEnvironment().getPhysicsObjects();
            }

            Vector2i mid = new Vector2i(max.x() / 2, max.y() / 2);
            for (int i = 0; i < physicsObjects.size(); i++) {
                PhysicsObject object = physicsObjects.get(i);
                List<Vector3f> history = object.getHistory();
                for (int j = 0; j < history.size() - 1; j++) {
                    Vector3f pos1 = new Vector3f(history.get(j));
                    pos1.y *= -1;
                    Vector3f pos2 = new Vector3f(history.get(j + 1));
                    pos2.y *= -1;
                    g.setColor(colors.get(i % colors.size()));
                    g.drawLine((int) pos1.x() + mid.x(), (int) pos1.y() + mid.y(), (int) pos2.x() + mid.x(), (int) pos2.y() + mid.y());
                }
            }
        }


    }

    private static List<Color> colors = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA);
}
