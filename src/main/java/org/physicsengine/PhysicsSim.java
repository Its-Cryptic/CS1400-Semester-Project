package org.physicsengine;
import org.apache.logging.log4j.*;
import org.joml.Vector3f;
import org.physicsengine.core.*;
import org.physicsengine.render.Graph;
import org.physicsengine.util.LogUtils;

public class PhysicsSim {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Engine engine;



    //public static void main(String[] args) throws Exception {
    public void run () throws Exception{

        LOGGER.info("PhysicsSim started");

        PhysicsEnvironment physicsEnvironment = JsonParser.loadPhysicsEnvironment("projectile.json");

        PhysicsObject physicsObject = new PhysicsObject();
        physicsObject.setMass(GUI.objectMass);
        physicsObject.setVelocity(new Vector3f(GUI.x_velocity, GUI.y_velocity, 0f));
        physicsObject.addForce(new Force(new Vector3f(0f, GUI.objectMass*GUI.gravityAcc, 0f)));
        physicsEnvironment.addPhysicsObject(physicsObject);
        engine = new Engine(60, physicsEnvironment);
        engine.start();

        new Graph();

    }
}
