package org.physicsengine.core;

import java.util.List;
import java.util.Map;

public class PhysicsEnvironment {
    public Map<String, Double> constants;
    public List<PhysicsObject> physicsObjects;

    public PhysicsEnvironment(Map<String, Double> constants, List<PhysicsObject> physicsObjects) {
        this.constants = constants;
        this.physicsObjects = physicsObjects;
    }
}
