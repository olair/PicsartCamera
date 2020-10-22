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
     * 是否可以切闪光灯（假想每一个类型是一个单独的灯）
     *
     * @return true 可以
     */
    FlashLampSwitcher canSwitchFlashLamp();

    Recorder canRecode();

    Taker canTake();

    /**
     * 分辨率切换
     */
    interface ResolutionSwitcher {

        void switchTo(Resolution resolution, OperatorCallback<Resolution> callback);

        List<Resolution> getPreviewSizeList();

        List<Resolution> getVideoSizeList();

    }

    interface FlashLampSwitcher {

        void switchTo(FlashLamp lamp, OperatorCallback<FlashLamp> callback);

        /**
         * 支持的闪光灯列表
         */
        List<FlashLamp> getFlashLampList();
    }

    interface Recorder {

        void record(String outPath, Resolution resolution);

    }

    interface Taker {

        void take(String outPath);

        /**
         * TODO 需要一个策略，比如AEB/连拍等，需要提供一些初始化支持，考虑是在策略内直接支持还是交给外部定义
         */
        void takeGroup(String outDir);

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
