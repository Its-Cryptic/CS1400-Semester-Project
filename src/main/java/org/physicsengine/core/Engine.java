package org.physicsengine.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Thread engineThread;
    private boolean isRunning = false;
    private int tickRate;
    private long tick;
    private String name;
    private final PhysicsEnvironment physicsEnvironment;

    public Engine(int tickRate, PhysicsEnvironment physicsEnvironment, String name) {
        if (tickRate <= 0) throw new IllegalArgumentException("Tick rate must be greater than 0");
        this.tickRate = tickRate;
        this.physicsEnvironment = physicsEnvironment;
        this.name = name;
        engineThread = new Thread(this);
        engineThread.setName(name + " Thread");
    }

    public Engine(int tickRate, PhysicsEnvironment physicsEnvironment) {
        this(tickRate, physicsEnvironment, "Engine");
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
            this.physicsEnvironment.evaluatePhysics();
            PhysicsObject physicsObject = this.physicsEnvironment.getPhysicsObjects().get(0);
            if (physicsObject != null) LOGGER.info("PhysicsObject position: " + physicsObject.getPosition());
            LOGGER.info("Tick: " + this.tick + ", Seconds: " + this.getSecondsElapsed());

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

    public PhysicsEnvironment getPhysicsEnvironment() {
        return this.physicsEnvironment;
    }

    public int getTickRate() {
        return this.tickRate;
    }

    public float getSecondsPerTick() {
        return 1.0f / this.tickRate;
    }

    public long getTicksElapsed() {
        return this.tick;
    }

    public long getSecondsElapsed() {
        return this.tick / this.tickRate;
    }
}
