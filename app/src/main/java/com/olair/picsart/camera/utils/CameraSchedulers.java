package com.olair.picsart.camera.utils;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Create by oLair on 2020/11/4.
 */
public class CameraSchedulers {
    private final static Scheduler backgroundScheduler = Schedulers.newThread();

    public static Scheduler background() {
        return backgroundScheduler;
    }
}
