package com.olair.picsart.camera

import android.view.SurfaceHolder

/**
 * Created by olair on 20.9.13.
 */
class Camera1Operator {
    internal interface SurfaceHolderOperator {
        companion object {
            const val SURFACE_CREATE = 0
            const val SURFACE_CHANGE = 1
            const val SURFACE_DESTROYED = 2
        }
    }

    class SurfaceChangeParam(val surfaceHolder: SurfaceHolder, val format: Int, val width: Int, val height: Int)
}