package com.example.muchao.addressbook;

import android.support.v4.app.Fragment;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PersonListFragment();
    }
}
