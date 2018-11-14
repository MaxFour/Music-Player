package com.maxfour.appthemehelper.common;

import android.support.v7.widget.Toolbar;

import com.maxfour.appthemehelper.util.ToolbarContentTintHelper;

public class ATHActionBarActivity extends ATHToolbarActivity {

    @Override
    protected Toolbar getATHToolbar() {
        return ToolbarContentTintHelper.getSupportActionBarView(getSupportActionBar());
    }
}
