package com.olair.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

/**
 * Created by olair on 20.9.10.
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = onInflateView(savedInstanceState);
        setContentView(viewBinding.getRoot());
    }

    protected abstract T onInflateView(@Nullable Bundle savedInstanceState);

}
