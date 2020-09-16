package com.olair.picsart.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by olair on 20.9.12.
 */
public class Camera1Lens implements CameraLens {

    private int mCameraId;

    private Camera mCamera;

    private final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceViewHolder;

    public Camera1Lens(int mCameraId) {
        this.mCameraId = mCameraId;
        Camera.getCameraInfo(mCameraId, cameraInfo);
    }

    @Override
    public void open(SurfaceView surfaceView) {
        this.mSurfaceView = surfaceView;
        this.mSurfaceViewHolder = surfaceView.getHolder();

        mCamera = Camera.open(mCameraId);
        mSurfaceViewHolder.addCallback(mSurfaceHolderCallback);

        try {
            mCamera.setPreviewDisplay(mSurfaceViewHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {

    }

    @Override
    public ResolutionSwitcher canSwitchResolution() {
        return null;
    }

    SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

}
