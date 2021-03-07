package com.olair.picsart.camera.utils

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Create by oLair on 2020/11/4.
 */
object CameraSchedulers {
    private val backgroundScheduler = Schedulers.newThread()
    fun background(): Scheduler {
        return backgroundScheduler
    }
}