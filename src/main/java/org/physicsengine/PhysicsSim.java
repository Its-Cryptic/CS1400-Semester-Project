package org.physicsengine;

import org.apache.logging.log4j.*;
import org.joml.Vector3f;
import org.physicsengine.core.Engine;
import org.physicsengine.core.Force;
import org.physicsengine.core.PhysicsObject;
import org.physicsengine.render.Graph;
import org.physicsengine.util.LogUtils;

public class PhysicsSim {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Engine engine;
    public static void main(String[] args) {
        LOGGER.info("PhysicsSim started");
        engine = new Engine(120);

        PhysicsObject physicsObject = new PhysicsObject();
        physicsObject.setMass(30);
        physicsObject.setVelocity(new Vector3f(1f, -2f, 0f));
        physicsObject.addForce(new Force(new Vector3f(0f, 0.5f, 0f)));
        engine.addPhysicsObject(physicsObject);

        engine.start();

        new Graph();
    }
}
