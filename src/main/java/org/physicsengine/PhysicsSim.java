package org.physicsengine;

import org.apache.logging.log4j.*;
import org.joml.Vector3f;
import org.physicsengine.core.*;
import org.physicsengine.render.Graph;
import org.physicsengine.util.LogUtils;

public class PhysicsSim {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Engine engine;
    public static void main(String[] args) throws Exception {
        LOGGER.info("PhysicsSim started");
        engine = new Engine(20);
        JsonParser.loadPhysicsEnvironment("projectile.json");
        //String function = "1.34 * 2.5 + 1";
        //LOGGER.info("Evaluate: " + function + " = " + EquationEvaluator.eval(function));

        PhysicsObject physicsObject = new PhysicsObject();
        physicsObject.setMass(30);
        physicsObject.setVelocity(new Vector3f(20f, 40f, 0f));
        physicsObject.addForce(new Force(new Vector3f(0f, -30f*9.81f, 0f)));
        engine.addPhysicsObject(physicsObject);

        engine.start();

        new Graph();
    }
}
