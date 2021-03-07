package com.olair.picsart

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import com.olair.base.BaseActivity
import com.olair.picsart.camera.Camera1Lens
import com.olair.picsart.camera.CameraLens.*
import com.olair.picsart.camera.Resolution
import com.olair.picsart.databinding.ActivityCameraBinding
import com.tbruyelle.rxpermissions3.RxPermissions

class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private lateinit var camera1Lens: Camera1Lens
    override fun onInflateView(savedInstanceState: Bundle?): ActivityCameraBinding {
        return ActivityCameraBinding.inflate(layoutInflater)
    }

    @SuppressLint("SdCardPath")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { agree: Boolean ->
                    if (agree) {
                        viewBinding.surfvCamera.holder.addCallback(surfvHolderCallback)
                    }
                }
        viewBinding.btnSwitchResolution.setOnClickListener {
            val resolutionSwitcher = camera1Lens.canSwitchResolution()
            if (resolutionSwitcher != null) {
                val listView = ListView(this)
                listView.adapter = object : BaseAdapter() {
                    var resolutionList = resolutionSwitcher.videoSizeList
                    override fun getCount(): Int {
                        return resolutionList.size
                    }

                    override fun getItem(position: Int): Any {
                        return resolutionList[position]
                    }

                    override fun getItemId(position: Int): Long {
                        return position.toLong()
                    }

                    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
                        val tv = TextView(this@CameraActivity)
                        tv.text = resolutionList[position].toString()
                        return tv
                    }
                }
                val popupWindow = PopupWindow(this)
                popupWindow.contentView = listView
                popupWindow.showAsDropDown(viewBinding.btnSwitchResolution)
            }
        }
        viewBinding.btnTakePicture.setOnClickListener {
            val taker = camera1Lens.canTake()
            taker.take("/sdcard", "test.jpg")
        }
    }

    private fun startPreview() {
        camera1Lens = Camera1Lens(1, 90, Handler())
        camera1Lens.open(viewBinding.surfvCamera, object : OnProcedureCallback<ResolutionSwitcher> {
            override fun onProcedure(operator: ResolutionSwitcher?) {
                if (operator == null) {
                    return
                }
                val previewSizeList = operator.previewSizeList
                val targetPreviewSize = previewSizeList[0]
                operator.switchTo(targetPreviewSize, object : OperatorCallback<Resolution> {
                    override fun onSuccess(param: Resolution) {
                        val layoutParams = viewBinding.surfvCamera.layoutParams
                        layoutParams.width = param.size.width
                        layoutParams.height = param.size.height
                        viewBinding.surfvCamera.layoutParams = layoutParams
                    }
                })
            }
        })
    }

    private val surfvHolderCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            startPreview()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }
}