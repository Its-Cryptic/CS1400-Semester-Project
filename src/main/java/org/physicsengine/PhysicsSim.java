package org.physicsengine;
import org.apache.logging.log4j.*;
import org.joml.Vector3f;
import org.physicsengine.core.*;
import org.physicsengine.render.Graph;
import org.physicsengine.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PhysicsSim {
    public static final Logger LOGGER = LogUtils.getLogger();
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
            projectile.addForce(new Force(new Vector3f(0f, GUI.objectMass*GUI.gravityAcc, 0f)));
            physicsEnvironment.addPhysicsObject(projectile);

            engine = new Engine(60, physicsEnvironment);
            engine.start();

            new Graph();
        } else {
            LOGGER.info("Environment file: " + environmentFile.get());
            LOGGER.info("PhysicsSim started");

            physicsEnvironment = JsonParser.loadPhysicsEnvironment(environmentFile.get());

//            PhysicsObject physicsObject = new PhysicsObject();
//            physicsObject.setMass(GUI.objectMass);
//            physicsObject.setVelocity(new Vector3f(GUI.x_velocity, GUI.y_velocity, 0f));
//            physicsObject.addForce(new Force(new Vector3f(0f, GUI.objectMass*GUI.gravityAcc, 0f)));
            //physicsEnvironment.addPhysicsObject(physicsObject);
            //engine = new Engine(60, physicsEnvironment);
            //engine.start();

            //new Graph();
        }
    }
}
