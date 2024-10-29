package org.physicsengine;

public class Engine {
    public boolean isRunning = false;
    public int tickRate = 20;
    public long tick = 0;
    private String name = "Engine";
    public Engine(int tickRate) {
        this.tickRate = tickRate;
    }
    public void start() {
        System.out.println("Engine started");
        isRunning = true;
        this.run();
    }

    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000 / tickRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " running, tick: " + tick);
            tick++;
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
