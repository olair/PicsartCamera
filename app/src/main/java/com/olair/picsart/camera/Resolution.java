package com.olair.picsart.camera;

import android.util.Size;

import androidx.annotation.StringRes;

import com.olair.picsart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by oLair on 2020/9/18.
 */
public enum Resolution {

    RESOLUTION_1080x720(1080, 720, R.string.resolution_720P),
    RESOLUTION_1920x1080(1920, 1080, R.string.resolution_1080P),
    RESOLUTION_2560x1440(2560, 1440, R.string.resolution_2K),
    RESOLUTION_3840x2160(3840, 2160, R.string.resolution_4K),
    RESOLUTION_NOT_SUPPORT(-1, -1, R.string.resolution_4K),
    ;

    @StringRes
    private final int[] alias;

    public final Size size;

    Resolution(int width, int height, @StringRes int... alias) {
        size = new Size(width, height);
        this.alias = alias;
    }


    public static Resolution toResolution(Size size) {
        Resolution[] resolutions = values();
        for (Resolution r : resolutions) {
            if (r.size.equals(size)) {
                return r;
            }
        }
        return RESOLUTION_NOT_SUPPORT;
    }

    public static List<Resolution> toResolutionList(List<Size> sizeList, boolean filterNotSupport) {
        List<Resolution> result = new ArrayList<>();
        for (Size s : sizeList) {
            Resolution resolution = toResolution(s);
            if (resolution == RESOLUTION_NOT_SUPPORT && filterNotSupport) {
                continue;
            }
            result.add(resolution);
        }
        return result;
    }
}
