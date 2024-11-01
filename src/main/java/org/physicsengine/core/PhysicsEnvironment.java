package org.physicsengine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhysicsEnvironment {
    public Map<String, Double> constants;
    public List<PhysicsObject> physicsObjects;

    public PhysicsEnvironment(Map<String, Double> constants, List<PhysicsObject> physicsObjects) {
        this.constants = constants;
        this.physicsObjects = physicsObjects;
    }


    public Map<String, Double> getConstants() {
        return constants;
    }

    public List<PhysicsObject> getPhysicsObjects() {
        return physicsObjects;
    }

    public void evaluatePhysics() {
        for (PhysicsObject physicsObject : this.physicsObjects) {
            physicsObject.evaluatePhysics();
        }
    }

    public void addPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObjects.add(physicsObject);
    }

    public Map<String, Double> compileAllVariables() {
        Map<String, Double> allVariables = new HashMap<>(this.constants);
        for (PhysicsObject physicsObject : this.physicsObjects) {
            String name = physicsObject.getName();
            allVariables.put(name + ".mass", (double) physicsObject.getMass());
            allVariables.put(name + ".position.x", (double) physicsObject.getPosition().x);
            allVariables.put(name + ".position.y", (double) physicsObject.getPosition().y);
            allVariables.put(name + ".position.z", (double) physicsObject.getPosition().z);
            allVariables.put(name + ".velocity.x", (double) physicsObject.getVelocity().x);
            allVariables.put(name + ".velocity.y", (double) physicsObject.getVelocity().y);
            allVariables.put(name + ".velocity.z", (double) physicsObject.getVelocity().z);
        }
        return allVariables;
    }
}
