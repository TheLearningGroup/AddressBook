package com.example.muchao.addressbook;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PersonListFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu){
        return super.onMenuOpened(featureId, menu);
    }
}
