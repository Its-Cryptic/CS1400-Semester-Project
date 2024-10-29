package org.physicsengine;

import org.apache.logging.log4j.*;

public class PhysicsSim {
    public static final Logger LOGGER = LogManager.getLogger(PhysicsSim.class.getSimpleName());
    public static void main(String[] args) {
        Engine engine = new Engine(60);
        Thread thread = new Thread(engine::start);
        thread.start();
    }
}
