package com.olair.picsart.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.olair.utils.SizeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
