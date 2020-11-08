package com.olair.picsart.camera.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Create by oLair on 2020/10/29.
 */
public enum CameraExecutor {

    BACKGROUND(Executors.newSingleThreadExecutor()),
    ;

    private Executor executor;

    CameraExecutor(Executor executor) {
        this.executor = executor;
    }

    public void post(Runnable runnable) {
        executor.execute(runnable);
    }
}
