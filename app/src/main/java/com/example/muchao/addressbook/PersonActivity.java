package com.example.muchao.addressbook;

import android.support.v4.app.Fragment;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new PersonFragment();
    }
}
