package com.olair.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by olair on 20.9.10.
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var viewBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = onInflateView(savedInstanceState)
        setContentView(viewBinding.root)
    }

    protected abstract fun onInflateView(savedInstanceState: Bundle?): T
}