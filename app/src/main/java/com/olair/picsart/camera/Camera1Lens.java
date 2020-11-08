package com.olair.picsart.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.olair.picsart.camera.utils.CameraSchedulers;
import com.olair.utils.FileUtil;
import com.olair.utils.SizeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

/**
 * Created by olair on 20.9.12.
 */
@SuppressWarnings("deprecation")
public class Camera1Lens implements CameraLens {

    private int mCameraId;

    private int mScreenOrientation;

    private Handler mHandler;

    private Camera mCamera;

    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();

    private Camera.Parameters mParameters;

    private SurfaceView mSurfaceView;

    public Camera1Lens(int mCameraId, int screenOrientation, Handler handler) {
        this.mCameraId = mCameraId;
        this.mScreenOrientation = screenOrientation;
        this.mHandler = handler;
        Camera.getCameraInfo(mCameraId, mCameraInfo);
    }

    @Override
    public void open(SurfaceView surfaceView, OnProcedureCallback<ResolutionSwitcher> onProcedureCallback) {
        this.mSurfaceView = surfaceView;
        SurfaceHolder surfaceViewHolder = surfaceView.getHolder();

        mCamera = Camera.open(mCameraId);
        mParameters = mCamera.getParameters();

        onProcedureCallback.onProcedure(canSwitchResolution());

        try {
            mCamera.setPreviewDisplay(surfaceViewHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void close() {

    }

    @Override
    public ResolutionSwitcher canSwitchResolution() {
        return resolutionSwitcher;
    }

    @Override
    public FlashLampSwitcher canSwitchFlashLamp() {
        return null;
    }

    @Override
    public Recorder canRecode() {
        return null;
    }

    @Override
    public Taker canTake() {
        return new Taker() {
            @Override
            public Single<Result> take(String outDir, String fileName) {
                return Single.create(
                        (SingleOnSubscribe<Result>) emitter ->
                                mCamera.takePicture(null, null, null,
                                        ((data, camera) -> {
                                            FileUtil.saveToFile(data, outDir, fileName);
                                            emitter.onSuccess(new Result(outDir, fileName));
                                        })))
                        .subscribeOn(CameraSchedulers.background())
                        .observeOn(AndroidSchedulers.mainThread());
            }

            @Override
            public FutureTask<String> takeGroup(String outDir) {
                return null;
            }
        };
    }

    private ResolutionSwitcher resolutionSwitcher = new ResolutionSwitcher() {
        @Override
        public void switchTo(Resolution resolution, OperatorCallback<Resolution> callback) {
            mParameters.setPreviewSize(resolution.size.getWidth(), resolution.size.getHeight());
            mCamera.setParameters(mParameters);
            mHandler.post(() -> callback.onSuccess(resolution));
        }

        @Override
        public List<Resolution> getPreviewSizeList() {
            List<Camera.Size> sizeList = mParameters.getSupportedPreviewSizes();
            return Resolution.toResolutionList(SizeUtil.toSizeList(sizeList), true);
        }

        @Override
        public List<Resolution> getVideoSizeList() {
            return new ArrayList<Resolution>() {
                {
                    add(Resolution.RESOLUTION_1080x720);
                    add(Resolution.RESOLUTION_1920x1080);
                    add(Resolution.RESOLUTION_2560x1440);
                }
            };
        }

    };

}
