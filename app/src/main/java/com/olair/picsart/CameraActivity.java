package com.olair.picsart;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;

import com.olair.base.BaseActivity;
import com.olair.picsart.databinding.ActivityCameraBinding;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.IOException;

import androidx.annotation.Nullable;

public class CameraActivity extends BaseActivity<ActivityCameraBinding> {

    private Camera camera;

    @Override
    protected ActivityCameraBinding onInflateView(@Nullable Bundle savedInstanceState) {
        return ActivityCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ResultOfMethodCallIgnored
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(agree -> {
                    if (agree) {
                        viewBinding.surfvCamera.getHolder().addCallback(surfvHolderCallback);
                    }
                });
    }

    private void startPreview() {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(viewBinding.surfvCamera.getHolder());
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SurfaceHolder.Callback surfvHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

}