package com.example.muchao.addressbook.menu;

import android.content.Context;
import android.view.ActionProvider;
import android.view.View;

/**
 * Created by muchao on 16/2/19.
 */
public class PlusActionProvider extends ActionProvider {
    private Context mContext;

    public PlusActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean hasSubMenu() {
        return false;
    }
}
