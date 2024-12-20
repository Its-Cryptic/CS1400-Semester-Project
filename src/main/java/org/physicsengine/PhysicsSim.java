package org.physicsengine;
import org.apache.logging.log4j.*;
import org.joml.Vector3f;
import org.physicsengine.core.*;
import org.physicsengine.render.GUI;
import org.physicsengine.render.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PhysicsSim {
    public static final Logger LOGGER = LogManager.getLogger(PhysicsSim.class);
    public static Engine engine;
    public static PhysicsEnvironment physicsEnvironment;



    public static void main(String[] args) throws Exception {
    //public void run () throws Exception{
        //ArgumentParser argumentParser = ArgumentParser.of(args);
        List<String> flags = List.of(args);
        // One argument and its just "-env:projectile.json"
        // Look through each flag and check to see if it starts with "-env:"
        AtomicReference<String> environmentFile = new AtomicReference<>(null);
        flags.stream().filter(flag -> flag.startsWith("-env:")).findFirst().ifPresent(flag -> {
            String[] split = flag.split(":");
            if (split.length != 2) {
                LOGGER.error("Invalid flag: " + flag);
                return;
            }
            environmentFile.set(split[1]);
        });
        if (environmentFile.get() == null) {
            LOGGER.info("No environment file specified, loading default application");
            GUI.run();
            while (!GUI.isRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            physicsEnvironment = new PhysicsEnvironment(new HashMap<>(), new ArrayList<>());
            PhysicsObject projectile = new PhysicsObject();
            projectile.setMass(GUI.objectMass);
            projectile.setVelocity(new Vector3f(GUI.x_velocity, GUI.y_velocity, 0f));
            Force gravity = new Force();
            gravity.evals[0] = "0";
            gravity.evals[1] = GUI.gravityAcc + "*" + GUI.objectMass;
            gravity.evals[2] = "0";
            projectile.addForce(gravity);
            physicsEnvironment.addPhysicsObject(projectile);

            engine = new Engine(60, physicsEnvironment);
            engine.start();

            new Graph();
        } else {
            LOGGER.info("Environment file: " + environmentFile.get());
            LOGGER.info("PhysicsSim started");

            physicsEnvironment = JsonParser.loadPhysicsEnvironment(environmentFile.get());
            engine = new Engine(60, physicsEnvironment);
            engine.start();

            new Graph();
        }
    }
}
