package org.physicsengine.core;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsObject {
    private Vector3f position;
    private float mass;
    private Vector3f velocity;
    private List<Force> forces;

    public PhysicsObject() {
        this.position = new Vector3f();
        this.forces = new ArrayList<>();
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

    public Force getNetForce() {
        Force netForce = new Force();
        for (Force force : forces) {
            netForce.add(force);
        }
        return netForce;
    }
}
