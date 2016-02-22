package com.example.muchao.addressbook.camera;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.muchao.addressbook.SingleFragmentActivity;

/**
 * Created by muchao on 16/2/17.
 */
public class CameraActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new CameraFragment();
    }
}
