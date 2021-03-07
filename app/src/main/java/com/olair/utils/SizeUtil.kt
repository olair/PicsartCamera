@file:Suppress("DEPRECATION")

package com.olair.utils

import android.hardware.Camera
import android.util.Size
import java.util.*

/**
 * Create by oLair on 2020/9/18.
 */
object SizeUtil {
    fun toSizeList(cameraSizeList: List<Camera.Size>): List<Size> {
        val result: MutableList<Size> = ArrayList()
        for (cameraSize in cameraSizeList) {
            result.add(Size(cameraSize.width, cameraSize.height))
        }
        return result
    }

    fun toSize(cameraSize: Camera.Size): Size {
        return Size(cameraSize.width, cameraSize.height)
    }
}