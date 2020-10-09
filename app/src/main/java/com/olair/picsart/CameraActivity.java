package com.olair.picsart;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.olair.base.BaseActivity;
import com.olair.picsart.camera.Camera1Lens;
import com.olair.picsart.camera.CameraLens;
import com.olair.picsart.camera.Resolution;
import com.olair.picsart.databinding.ActivityCameraBinding;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.List;

public class CameraActivity extends BaseActivity<ActivityCameraBinding> {

    private Camera1Lens camera1Lens;

    @Override
    protected ActivityCameraBinding onInflateView(@Nullable Bundle savedInstanceState) {
        return ActivityCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ResultOfMethodCallIgnored
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(agree -> {
                    if (agree) {
                        viewBinding.surfvCamera.getHolder().addCallback(surfvHolderCallback);
                    }
                });

        viewBinding.btnSwitchResolution.setOnClickListener(v -> {
            CameraLens.ResolutionSwitcher resolutionSwitcher = camera1Lens.canSwitchResolution();
            if (resolutionSwitcher != null) {
                ListView listView = new ListView(this);
                listView.setAdapter(new BaseAdapter() {
                    List<Resolution> resolutionList = resolutionSwitcher.getVideoSizeList();

                    @Override
                    public int getCount() {
                        return resolutionList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return resolutionList.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView tv = new TextView(CameraActivity.this);
                        tv.setText(resolutionList.get(position).toString());
                        return tv;
                    }
                });
                PopupWindow popupWindow = new PopupWindow(this);
                popupWindow.setContentView(listView);
                popupWindow.showAsDropDown(viewBinding.btnSwitchResolution);

            }
        });
    }

    private void startPreview() {
        camera1Lens = new Camera1Lens(1, 90, new Handler());
        camera1Lens.open(viewBinding.surfvCamera, operator -> {
            List<Resolution> previewSizeList = operator.getPreviewSizeList();
            Resolution targetPreviewSize = previewSizeList.get(0);
            operator.switchTo(targetPreviewSize, param -> {
                ViewGroup.LayoutParams layoutParams = viewBinding.surfvCamera.getLayoutParams();
                layoutParams.width = param.size.getWidth();
                layoutParams.height = param.size.getHeight();
                viewBinding.surfvCamera.setLayoutParams(layoutParams);
            });
        });
    }

    private SurfaceHolder.Callback surfvHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

}