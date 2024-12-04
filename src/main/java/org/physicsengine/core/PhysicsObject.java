package org.physicsengine.core;

import org.joml.Vector3f;
import org.json.JSONArray;
import org.json.JSONObject;
import org.physicsengine.PhysicsSim;

import java.util.ArrayList;
import java.util.List;

public class PhysicsObject {
    private String name = "PhysicsObject";
    private Vector3f position;
    private float mass;
    private Vector3f velocity;
    private List<Force> forces;
    private final List<Vector3f> history;


    public PhysicsObject() {
        this.position = new Vector3f();
        this.forces = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public static PhysicsObject fromJson(JSONObject object) {
        PhysicsObject physicsObject = new PhysicsObject();
        physicsObject.setName(object.getString("name"));
        JSONObject initialValues = object.getJSONObject("initialValues");
        physicsObject.setMass(initialValues.getFloat("mass"));
        physicsObject.setPosition(new Vector3f(
                initialValues.getJSONArray("position").getFloat(0),
                initialValues.getJSONArray("position").getFloat(1),
                initialValues.getJSONArray("position").getFloat(2)
        ));
        physicsObject.setVelocity(new Vector3f(
                initialValues.getJSONArray("velocity").getFloat(0),
                initialValues.getJSONArray("velocity").getFloat(1),
                initialValues.getJSONArray("velocity").getFloat(2)
        ));

        JSONArray forces = object.getJSONArray("forces");
        for (int i = 0; i < forces.length(); i++) {
            JSONObject force = forces.getJSONObject(i);
            physicsObject.addForce(Force.fromJson(force));
        }
        return physicsObject;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public List<Force> getForces() {
        return forces;
    }

    public void setForces(List<Force> forces) {
        this.forces = forces;
    }

    public void addForce(Force force) {
        forces.add(force);
    }

    public void removeForce(Force force) {
        forces.remove(force);
    }

    public Force getNetForce(PhysicsEnvironment environment) {
        Force netForce = new Force();
        for (Force force : forces) {
            force.setForce(force.evaluate(environment));
            netForce.add(force);
        }
        return netForce;
    }

    public void evaluatePhysics(PhysicsEnvironment environment) {
        float mass = getMass();
        addHistory(getPosition());

        if (mass <= 0) return;

        float dt = PhysicsSim.engine.getSecondsPerTick();

        Vector3f netForce = getNetForce(environment).getForce();

        // Calculate acceleration: a = F / m
        Vector3f acceleration = new Vector3f(netForce).div(mass);

        // Update velocity: v_new = v_old + a * dt
        Vector3f newVelocity = new Vector3f(getVelocity());
        newVelocity.add(new Vector3f(acceleration).mul(dt));

        // Update position: x_new = x_old + v_new * dt
        Vector3f newPosition = new Vector3f(getPosition());
        newPosition.add(new Vector3f(newVelocity).mul(dt));

        setVelocity(newVelocity);
        setPosition(newPosition);
    }

    public List<Vector3f> getHistory() {
        return history;
    }

    public void addHistory(Vector3f position) {
        history.add(position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PhysicsObject{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", mass=" + mass +
                ", velocity=" + velocity +
                ", forces=" + forces +
                ", history=" + history +
                '}';
    }
}
