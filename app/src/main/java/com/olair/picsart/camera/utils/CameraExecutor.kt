package com.olair.picsart.camera.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Create by oLair on 2020/10/29.
 */
enum class CameraExecutor(private val executor: Executor) {
    BACKGROUND(Executors.newSingleThreadExecutor());

    fun post(runnable: Runnable) {
        executor.execute(runnable)
    }
}