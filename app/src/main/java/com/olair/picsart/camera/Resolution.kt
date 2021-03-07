package com.olair.picsart.camera

import android.util.Size
import androidx.annotation.StringRes
import com.olair.picsart.R
import java.util.*

/**
 * Create by oLair on 2020/9/18.
 */
@Suppress("EnumEntryName")
enum class Resolution(private val width: Int, private val height: Int, @StringRes vararg alias: Int) {
    RESOLUTION_1080x720(1080, 720, R.string.resolution_720P),
    RESOLUTION_1920x1080(1920, 1080, R.string.resolution_1080P),
    RESOLUTION_2560x1440(2560, 1440, R.string.resolution_2K),
    RESOLUTION_3840x2160(3840, 2160, R.string.resolution_4K),
    RESOLUTION_NOT_SUPPORT(-1, -1, R.string.resolution_4K);

    val size = Size(width, height)

    companion object {
        private fun toResolution(size: Size): Resolution {
            val resolutions = values()
            for (r in resolutions) {
                if (r.size == size) {
                    return r
                }
            }
            return RESOLUTION_NOT_SUPPORT
        }

        @JvmStatic
        fun toResolutionList(sizeList: List<Size>, filterNotSupport: Boolean): List<Resolution> {
            val result: MutableList<Resolution> = ArrayList()
            for (s in sizeList) {
                val resolution = toResolution(s)
                if (resolution == RESOLUTION_NOT_SUPPORT && filterNotSupport) {
                    continue
                }
                result.add(resolution)
            }
            return result
        }
    }

}