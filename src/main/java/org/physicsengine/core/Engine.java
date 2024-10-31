package org.physicsengine.core;

import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import org.physicsengine.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Thread engineThread;
    private boolean isRunning = false;
    private int tickRate = 20;
    private long tick;
    private String name;
    private final List<PhysicsObject> physicsObjects;

    public Engine(int tickRate, String name) {
        this.tickRate = tickRate;
        this.name = name;
        this.physicsObjects = new ArrayList<>();
        engineThread = new Thread(this);
        engineThread.setName(name + " Thread");
    }

    public Engine(int tickRate) {
        this(tickRate, "Engine");
    }

    public void start() {
        LOGGER.info(this.name + " started with a tick rate of: " + this.tickRate);
        this.isRunning = true;
        this.engineThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / this.tickRate;

        while (this.isRunning) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / nsPerTick;
            lastTime = now;

            //LOGGER.info(this.name + " running, tick: " + this.tick);
            //LOGGER.info("Thread: " + Thread.currentThread().getName());
            this.tick++;
            this.evaluatePhysics();
            PhysicsObject physicsObject = this.physicsObjects.get(0);
            if (physicsObject != null) LOGGER.info("PhysicsObject position: " + physicsObject.getPosition());

            try {
                long sleepTime = (long) (nsPerTick - (System.nanoTime() - now)) / 1_000_000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                } else {
                    // If sleepTime is negative, we're running behind schedule
                    LOGGER.warn("Simulation is running behind schedule.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                LOGGER.error("Engine thread interrupted", e);
            }
        }
        LOGGER.info("Engine stopped");
    }

    public void setName(String name) {
        this.name = name;
    }

    private void evaluatePhysics() {
        for (PhysicsObject physicsObject : this.physicsObjects) {
            float mass = physicsObject.getMass();


            if (mass <= 0) continue;

            float dt = 1f;

            Vector3f netForce = physicsObject.getNetForce().getForce();

            // Calculate acceleration: a = F / m
            Vector3f acceleration = new Vector3f(netForce).div(mass);

            // Update velocity: v_new = v_old + a * dt
            Vector3f newVelocity = new Vector3f(physicsObject.getVelocity());
            newVelocity.add(new Vector3f(acceleration).mul(dt));

            // Update position: x_new = x_old + v_new * dt
            Vector3f newPosition = new Vector3f(physicsObject.getPosition());
            newPosition.add(new Vector3f(newVelocity).mul(dt));

            physicsObject.setVelocity(newVelocity);
            physicsObject.setPosition(newPosition);

            physicsObject.getNetForce().setForce(new Vector3f(0, 0, 0));
        }
    }

    public void addPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObjects.add(physicsObject);
    }

    public List<PhysicsObject> getPhysicsObjects() {
        return this.physicsObjects;
    }
}
