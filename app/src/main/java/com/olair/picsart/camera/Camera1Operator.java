package com.olair.picsart.camera;

import android.view.SurfaceHolder;

/**
 * Created by olair on 20.9.13.
 */
public class Camera1Operator {

    interface SurfaceHolderOperator {
        int SURFACE_CREATE = 0;
        int SURFACE_CHANGE = 1;
        int SURFACE_DESTROYED = 2;
    }

    public static class SurfaceChangeParam {
        public final SurfaceHolder surfaceHolder;
        public final int format;
        public final int width;
        public final int height;

        public SurfaceChangeParam(SurfaceHolder surfaceHolder, int format, int width, int height) {
            this.surfaceHolder = surfaceHolder;
            this.format = format;
            this.width = width;
            this.height = height;
        }
    }

}
