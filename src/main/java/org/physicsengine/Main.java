package org.physicsengine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!!");

        Engine engine = new Engine(20);
        Thread thread = new Thread(engine::start);
        thread.start();

        Engine fastEngine = new Engine(120);
        fastEngine.setName("FastEngine");
        Thread fastThread = new Thread(fastEngine::start);
        fastThread.start();
    }
}
