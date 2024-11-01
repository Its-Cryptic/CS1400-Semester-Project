package org.physicsengine.core;

import org.joml.Vector3f;

public class Force {
    private String name;
    private Vector3f force;

    public Force() {
        this(new Vector3f());
    }

    public Force(Vector3f force) {
        this.force = force;
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
