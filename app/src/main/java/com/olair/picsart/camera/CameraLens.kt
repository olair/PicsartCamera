package com.olair.picsart.camera

import android.view.SurfaceView
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.util.concurrent.FutureTask

/**
 * 一个手机镜头
 * Created by olair on 20.9.10.
 */
interface CameraLens {
    /**
     * 开启图传
     *
     * @param surfaceView 图传目标
     */
    fun open(surfaceView: SurfaceView, onProcedureCallback: OnProcedureCallback<ResolutionSwitcher>)

    /**
     * 关闭图传
     */
    fun close()

    /**
     * 是否可以切分辨率
     *
     * @return true 可以
     */
    fun canSwitchResolution(): ResolutionSwitcher?

    /**
     * 是否可以切闪光灯（假想每一个类型是一个单独的灯）
     *
     * @return true 可以
     */
    fun canSwitchFlashLamp(): FlashLampSwitcher?
    fun canRecode(): Recorder?
    fun canTake(): Taker?

    /**
     * 分辨率切换
     */
    interface ResolutionSwitcher {

        val previewSizeList: List<Resolution>

        val videoSizeList: List<Resolution>

        fun switchTo(resolution: Resolution, callback: OperatorCallback<Resolution>)
    }

    interface FlashLampSwitcher {
        fun switchTo(lamp: FlashLamp, callback: OperatorCallback<FlashLamp>)

        /**
         * 支持的闪光灯列表
         */
        val flashLampList: List<FlashLamp>
    }

    interface Recorder {
        fun record(outPath: String, resolution: Resolution)
    }

    interface Taker {
        fun take(outDir: String, fileName: String): Single<Result>

        /**
         * TODO 需要一个策略，比如AEB/连拍等，需要提供一些初始化支持，考虑是在策略内直接支持还是交给外部定义
         */
        fun takeGroup(outDir: String): FutureTask<String>

        class Result(dir: String, fileName: String) {
            private val file: File = File(dir, fileName)

        }
    }

    interface OnProcedureCallback<T> {
        fun onProcedure(operator: T?)
    }

    interface OperatorCallback<T> {
        fun onSuccess(param: T)
        fun onError(throwable: Throwable) {}
    }
}