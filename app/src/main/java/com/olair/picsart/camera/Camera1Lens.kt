@file:Suppress("DEPRECATION")

package com.olair.picsart.camera

import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.os.Handler
import android.view.SurfaceView
import com.olair.picsart.camera.CameraLens.*
import com.olair.picsart.camera.Resolution.Companion.toResolutionList
import com.olair.picsart.camera.utils.CameraSchedulers
import com.olair.utils.FileUtil
import com.olair.utils.SizeUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 * Created by olair on 20.9.12.
 */
class Camera1Lens(private val mCameraId: Int,
                  private val mScreenOrientation: Int,
                  private val mHandler: Handler) : CameraLens {
    private lateinit var mCamera: Camera
    private val mCameraInfo = CameraInfo()
    private lateinit var mParameters: Camera.Parameters
    private lateinit var mSurfaceView: SurfaceView

    init {
        Camera.getCameraInfo(mCameraId, mCameraInfo)
    }

    override fun open(surfaceView: SurfaceView, onProcedureCallback: OnProcedureCallback<ResolutionSwitcher>) {
        mSurfaceView = surfaceView
        val surfaceViewHolder = surfaceView.holder
        mCamera = Camera.open(mCameraId)
        mParameters = mCamera.parameters
        onProcedureCallback.onProcedure(canSwitchResolution())
        try {
            mCamera.setPreviewDisplay(surfaceViewHolder)
            mCamera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun close() {}

    @Suppress("RedundantNullableReturnType")
    override fun canSwitchResolution(): ResolutionSwitcher? {
        return resolutionSwitcher
    }

    override fun canSwitchFlashLamp(): FlashLampSwitcher? {
        return null
    }

    override fun canRecode(): Recorder? {
        return null
    }

    override fun canTake(): Taker {
        return object : Taker {
            override fun take(outDir: String, fileName: String): Single<Taker.Result> {
                return Single.create { emitter: SingleEmitter<Taker.Result> ->
                    mCamera.takePicture(null, null, null,
                            { data, _ ->
                                FileUtil.saveToFile(data, outDir, fileName)
                                emitter.onSuccess(Taker.Result(outDir, fileName))
                            })
                }
                        .subscribeOn(CameraSchedulers.background())
                        .observeOn(AndroidSchedulers.mainThread())
            }

            override fun takeGroup(outDir: String): FutureTask<String> {
                return FutureTask(Callable {
                    return@Callable ""
                })
            }
        }
    }

    private val resolutionSwitcher: ResolutionSwitcher = object : ResolutionSwitcher {
        override fun switchTo(resolution: Resolution, callback: OperatorCallback<Resolution>) {
            mParameters.setPreviewSize(resolution.size.width, resolution.size.height)
            mCamera.parameters = mParameters
            mHandler.post { callback.onSuccess(resolution) }
        }

        override val previewSizeList: List<Resolution>
            get() {
                val sizeList = mParameters.supportedPreviewSizes
                return toResolutionList(SizeUtil.toSizeList(sizeList), true)
            }
        override val videoSizeList: List<Resolution>
            get() = object : ArrayList<Resolution>() {
                init {
                    add(Resolution.RESOLUTION_1080x720)
                    add(Resolution.RESOLUTION_1920x1080)
                    add(Resolution.RESOLUTION_2560x1440)
                }
            }
    }
}