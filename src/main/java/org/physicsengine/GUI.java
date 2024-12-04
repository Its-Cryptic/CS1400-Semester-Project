package org.physicsengine;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class GUI {

    public static float gravityAcc = (float) -9.81;
    public static float x_velocity;
    public static float y_velocity;
    public static float objectMass;
    public static boolean isRunning = false;

    public static void run(){

        JFrame jFrame = new JFrame("Environment Control Panel");
        DecimalFormat formatter = new DecimalFormat();
        formatter.setGroupingUsed(false);
        ChangeListener changeListener;

        JButton startButton = new JButton("Start");
        JButton earthButton = new JButton("Earth");
        JButton sunButton = new JButton("Sun");
        JButton jupiterButton = new JButton("Jupiter");
        JButton marsButton = new JButton("Mars");

        JPanel gravityPanel = new JPanel();
        JPanel positionPanel = new JPanel();
        JPanel velocityPanel = new JPanel();
        JPanel massPanel = new JPanel();
        JPanel selectionPanel = new JPanel();
        JLabel gravityLabel = new JLabel();
        JLabel selectedGravity = new JLabel();
        selectedGravity.setText("Selected Gravity: Earth");
        gravityLabel.setText("Gravity:");
        JLabel label4 = new JLabel();
        label4.setText("X velocity vector:");
        JLabel label5 = new JLabel();
        label5.setText("Y velocity vector:");
        JLabel label6 = new JLabel();
        label6.setText("Object Mass");
        JLabel errorLab = new JLabel();

        JFormattedTextField x_vel = new JFormattedTextField(formatter);
        JFormattedTextField y_vel = new JFormattedTextField(formatter);
        JFormattedTextField objMass = new JFormattedTextField(formatter);

        x_vel.setColumns(3);
        y_vel.setColumns(3);
        objMass.setColumns(3);
        jFrame.setLayout(new FlowLayout());
        velocityPanel.add(label4);
        velocityPanel.add(x_vel);
        velocityPanel.add(label5);
        velocityPanel.add(y_vel);
        gravityPanel.add(gravityLabel);
        gravityPanel.add(earthButton);
        gravityPanel.add(marsButton);
        gravityPanel.add(sunButton);
        gravityPanel.add(jupiterButton);
        selectionPanel.add(selectedGravity);
        massPanel.add(label6);
        massPanel.add(objMass);
        jFrame.setSize(450, 250);
        jFrame.add(positionPanel);
        jFrame.add(velocityPanel);
        jFrame.add(gravityPanel);
        jFrame.add(selectionPanel);
        jFrame.add(massPanel);
        jFrame.add(startButton);
        jFrame.add(errorLab);
        errorLab.setForeground(Color.RED);
        jFrame.setVisible(true);


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (("Earth").equals(e.getActionCommand())){
                    gravityAcc = (float) -9.81;
                    selectedGravity.setText("Selected Gravity: Earth");

                }
                if (("Mars").equals(e.getActionCommand())){
                    gravityAcc = (float) -3.73;
                    selectedGravity.setText("Selected Gravity: Mars");

                }
                if (("Sun").equals(e.getActionCommand())){
                    gravityAcc = (float) -274.0;
                    selectedGravity.setText("Selected Gravity: Sun");

                }
                if (("Jupiter").equals(e.getActionCommand())){
                    gravityAcc = (float) -24.79;
                    selectedGravity.setText("Selected Gravity: Jupiter");

                }
                if (("Start").equals(e.getActionCommand())){
                    try {
                        x_velocity = Float.parseFloat(x_vel.getText());
                        y_velocity = Float.parseFloat(y_vel.getText());
                        objectMass = Float.parseFloat(objMass.getText());
                        isRunning = true;
                        jFrame.dispose();
                    }
                    catch (Exception ex){
                        System.out.println(ex.getMessage());
                        errorLab.setText("⚠ Please complete all fields.");
                    }
                }


            }
        };

        startButton.addActionListener(actionListener);
        earthButton.addActionListener(actionListener);
        marsButton.addActionListener(actionListener);
        sunButton.addActionListener(actionListener);
        jupiterButton.addActionListener(actionListener);


    }

}


