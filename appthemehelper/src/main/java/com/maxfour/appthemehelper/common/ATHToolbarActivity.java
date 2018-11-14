package com.maxfour.appthemehelper.common;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.maxfour.appthemehelper.ATHActivity;
import com.maxfour.appthemehelper.util.ToolbarContentTintHelper;

public class ATHToolbarActivity extends ATHActivity {
    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = getATHToolbar();
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(this, toolbar, menu, getToolbarBackgroundColor(toolbar));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(this, getATHToolbar());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        this.toolbar = toolbar;
        super.setSupportActionBar(toolbar);
    }

    protected Toolbar getATHToolbar() {
        return toolbar;
    }

    public static int getToolbarBackgroundColor(Toolbar toolbar) {
        if (toolbar != null) {
            if (toolbar.getBackground() instanceof ColorDrawable) {
                return ((ColorDrawable) toolbar.getBackground()).getColor();
            }
        }
        return 0;
    }
}