package com.example.muchao.addressbook.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.muchao.addressbook.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by muchao on 16/2/17.
 */
public class CameraFragment extends Fragment {
    private static final String TAG = CameraFragment.class.getSimpleName();

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, parent, false);
        Button takePictureBtn = (Button) v.findViewById(R.id.camera_takePicture_btn);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mSurfaceView = (SurfaceView) v.findViewById(R.id.camera_surfaceView);
        initCamera();

        return v;
    }

    public void initCamera() {
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initCameraAndPreview(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera != null) {
                    Camera.Parameters parameters = mCamera.getParameters();
                    Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
                    parameters.setPreviewSize(s.width, s.height);
                    mCamera.setParameters(parameters);
                    try {
                        mCamera.setDisplayOrientation(90);
                        mCamera.startPreview();

                    } catch (Exception e) {
                        Log.d(TAG, "surfaceChanged: " + e.getMessage());
                        mCamera.release();
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mCamera != null) {
                    mCamera.release();
                }
            }
        });

        // 为了兼容老设备
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height) {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size size : sizes) {
            int area = size.width * size.height;
            if (area > largestArea) {
                bestSize = size;
                largestArea = area;
            }
        }
        return bestSize;
    }

    public void initCameraAndPreview(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException e) {
            Log.d(TAG, "initCameraAndPreview: " + e.getMessage());
        }
    }

    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open();
        } else {
            mCamera = Camera.open(0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
        }
    }
}
