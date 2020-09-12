package com.olair.picsart.camera;

import android.view.SurfaceView;


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
    void open(SurfaceView surfaceView);

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
    }
}
