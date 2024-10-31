package org.physicsengine.render;

import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.physicsengine.PhysicsSim;
import org.physicsengine.core.Engine;
import org.physicsengine.util.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Graph extends JFrame {
    private static final Logger LOGGER = LogUtils.getLogger();
    private CvGraphingCalculator canvas;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private static final List<Vector3f> pastPositions = new ArrayList<>();

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
            Vector3f pos;
            synchronized (engine) {
                pos = engine.getPhysicsObjects().get(0).getPosition();
                pastPositions.add(new Vector3f(pos));
            }
            Vector2i mid = new Vector2i(max.x() / 2, max.y() / 2);

            int scale = 25;

            g.setColor(Color.BLUE);
            g.drawOval((int) pos.x() + mid.x() - scale/2, (int) pos.y() + mid.y() - scale/2, scale, scale);
            //LOGGER.info("Drawing point at: " + pos);
        }

        void drawPath(Graphics g, Vector2i max) {
            Vector2i mid = new Vector2i(max.x() / 2, max.y() / 2);
            for (int i = 0; i < pastPositions.size() - 1; i++) {
                Vector3f pos1 = pastPositions.get(i);
                Vector3f pos2 = pastPositions.get(i + 1);
                if (pos2 == null) break;
                g.setColor(Color.RED);
                g.drawLine((int) pos1.x() + mid.x(), (int) pos1.y() + mid.y(), (int) pos2.x() + mid.x(), (int) pos2.y() + mid.y());
            }
        }
    }
}
