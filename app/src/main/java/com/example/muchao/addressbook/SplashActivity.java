package com.example.muchao.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private final static String TAG = SplashActivity.class.getSimpleName();

    private final int SPLASH_DISPLAY_LENGHT = 1; //延迟
    private TextView mCountdown_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_splash);
        mCountdown_tv = (TextView) findViewById(R.id.countdown_tv);
        mCountdown_tv.setText(String.valueOf(SPLASH_DISPLAY_LENGHT));

        mHandler.sendEmptyMessage(SPLASH_DISPLAY_LENGHT);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(SplashActivity.this, PersonListActivity.class);
//                SplashActivity.this.startActivity(i);
//                SplashActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGHT);
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mHandler.removeMessages(msg.what);
            mCountdown_tv.setText(String.valueOf(msg.what));
            if (msg.what > 0) {
                Log.d(TAG, "handleMessage: " + msg.what);
                mHandler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else {
                callMainActivity();
                SplashActivity.this.finish();
            }
            return false;
        }
    });

    private void callMainActivity() {
        Intent i = new Intent(SplashActivity.this, PersonListActivity.class);
        SplashActivity.this.startActivity(i);
        SplashActivity.this.finish();
    }

}
