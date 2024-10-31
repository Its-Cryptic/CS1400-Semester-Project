package org.physicsengine.render;

import org.apache.logging.log4j.Logger;

import org.jspecify.annotations.Nullable;
import org.physicsengine.util.LogUtils;

public class RenderSystem {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Nullable
    private static Thread renderThread;

    public static void initRenderThread() {
        if (renderThread != null) {
            throw new IllegalStateException("Could not initialize render thread");
        } else {
            renderThread = Thread.currentThread();
        }
    }

    public static boolean isOnRenderThread() {
        return Thread.currentThread() == renderThread;
    }
}
