package com.olair.utils;

import android.hardware.Camera;
import android.util.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by oLair on 2020/9/18.
 */
public class SizeUtil {

    public static List<Size> toSizeList(List<Camera.Size> cameraSizeList) {
        List<Size> result = new ArrayList<>();
        for (Camera.Size cameraSize : cameraSizeList) {
            result.add(new Size(cameraSize.width, cameraSize.height));
        }
        return result;
    }

    public static Size toSize(Camera.Size cameraSize) {
        return new Size(cameraSize.width, cameraSize.height);
    }

}
