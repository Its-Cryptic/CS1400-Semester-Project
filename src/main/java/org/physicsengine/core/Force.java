package org.physicsengine.core;

import org.joml.Vector3f;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class Force {
    private String name;
    private Vector3f force;
    public String[] evals = new String[3];

    public Force() {
        this(new Vector3f());
    }

    public Force(Vector3f force) {
        this.force = force;
    }

    public static Force fromJson(JSONObject forceJson) {
        Force force = new Force();
        force.name = forceJson.getString("name");
        JSONArray forceArray = forceJson.getJSONArray("force");
        for (int i = 0; i < 3; i++) {
            force.evals[i] = forceArray.get(i).toString();
        }
        return force;
    }

    public Vector3f evaluate(PhysicsEnvironment environment) {
        Map<String, Double> variables = environment.compileAllVariables();
        Vector3f evaluatedForce = new Vector3f();
        for (int i = 0; i < 3; i++) {
            try {
                evaluatedForce.setComponent(i, (float) EquationEvaluator.eval(evals[i], variables));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return evaluatedForce;
    }

    public Vector3f getForce() {
        return force;
    }

    public void setForce(Vector3f force) {
        this.force = force;
    }

    public Vector3f getDirection() {
        return new Vector3f(force).normalize();
    }

    public void setDirection(Vector3f direction) {
        force = new Vector3f(direction).normalize().mul(getMagnitude());
    }

    public float getMagnitude() {
        return force.length();
    }

    public void setMagnitude(float magnitude) {
        force = new Vector3f(getDirection()).mul(magnitude);
    }

    public void add(Force force) {
        this.force.add(force.getForce());
    }
}
