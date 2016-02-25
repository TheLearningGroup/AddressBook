package com.example.muchao.addressbook;


import android.app.Fragment;
import android.util.Log;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonActivity extends SingleFragmentActivity {
    private static String TAG = PersonActivity.class.getSimpleName();
//    private static boolean isSaved = false;

    @Override
    public Fragment createFragment() {
        return new PersonFragment();
    }

    @Override
    public void onBackPressed() {
//        Intent i = new Intent();
//        if (isSaved) {
//            setResult(Activity.RESULT_OK, i);
//        }
        Log.d(TAG, "onBackPressed: ");
        finish();
    }
}
