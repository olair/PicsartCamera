package com.olair.picsart.camera;

import android.view.SurfaceHolder;

/**
 * Created by olair on 20.9.13.
 */
public class Camera1Operator {

    interface SurfaceHolderOperator {
        int SURFACE_CREATE = 0;
        int SURFACE_CHANGE = 2;
    }

    void startPreview(SurfaceHolder holder) {
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                holder.removeCallback(this);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

}
