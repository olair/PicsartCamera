package com.olair.picsart.camera;

import android.view.SurfaceView;

import java.util.List;


/**
 * 一个手机镜头
 * Created by olair on 20.9.10.
 */
public interface CameraLens {

    /**
     * 开启图传
     *
     * @param surfaceView 图传目标
     */
    void open(SurfaceView surfaceView, OnProcedureCallback<ResolutionSwitcher> onProcedureCallback);

    /**
     * 关闭图传
     */
    void close();

    /**
     * 是否可以切分辨率
     *
     * @return true 可以
     */
    ResolutionSwitcher canSwitchResolution();

    /**
     * 分辨率切换
     */
    interface ResolutionSwitcher {

        void switchTo(Resolution resolution, OperatorCallback<Resolution> callback);

        List<Resolution> getPreviewSizeList();

        List<Resolution> getVideoSizeList();

    }

    interface OnProcedureCallback<T> {
        void onProcedure(T operator);
    }

    interface OperatorCallback<T> {

        void onSuccess(T param);

        default void onError(Throwable throwable) {
        }
    }


}
