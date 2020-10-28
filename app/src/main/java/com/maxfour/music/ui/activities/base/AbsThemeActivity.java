package com.maxfour.music.ui.activities.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;

import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.appthemehelper.common.ATHToolbarActivity;
import com.kabouzeid.appthemehelper.util.ColorUtil;
import com.kabouzeid.appthemehelper.util.MaterialDialogsUtil;
import com.maxfour.music.R;
import com.maxfour.music.util.PreferenceUtil;
import com.maxfour.music.util.Util;

public abstract class AbsThemeActivity extends ATHToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceUtil.getInstance(this).getGeneralTheme());
        super.onCreate(savedInstanceState);
        MaterialDialogsUtil.updateMaterialDialogsThemeSingleton(this);
    }

    protected void setDrawUnderStatusbar() {
        Util.setAllowDrawUnderStatusBar(getWindow());
    }

    /**
     * This will set the color of the view with the id "status_bar" on Lollipop.
     * On Lollipop if no such view is found it will set the statusbar color using the native method.
     *
     * @param color the new statusbar color (will be shifted down on Lollipop and above)
     */
    public static final void Static_setStatusbarColor(final Activity pActivity, int color) {
        final View statusBar = pActivity.getWindow().getDecorView().getRootView().findViewById(R.id.status_bar);
        if (statusBar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBar.setBackgroundColor(color);
                ATH.setLightStatusbar(pActivity, ColorUtil.isColorLight(color));
            } else {
                statusBar.setBackgroundColor(color);
                }
        } else if (Build.VERSION.SDK_INT >= 21) {
            pActivity.getWindow().setStatusBarColor(ColorUtil.darkenColor(color));
            ATH.setLightStatusbar(pActivity, ColorUtil.isColorLight(color));
        }
    }

    public void setStatusbarColor(int color) {
        final View statusBar = getWindow().getDecorView().getRootView().findViewById(R.id.status_bar);
        if (statusBar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBar.setBackgroundColor(color);
                setLightStatusbarAuto(color);
            } else {
                statusBar.setBackgroundColor(color);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ColorUtil.darkenColor(color));
            setLightStatusbarAuto(color);
        }
    }

    public void setStatusbarColorAuto() {
        setStatusbarColor(ThemeStore.primaryColor(this));
    }

    public void setTaskDescriptionColor(@ColorInt int color) {
        ATH.setTaskDescriptionColor(this, color);
    }

    public void setTaskDescriptionColorAuto() {
        setTaskDescriptionColor(ThemeStore.primaryColor(this));
    }

    public void setNavigationbarColor(int color) {
        if (ThemeStore.coloredNavigationBar(this)) {
            ATH.setNavigationbarColor(this, color);
        } else {
            ATH.setNavigationbarColor(this, Color.BLACK);
        }
    }

    public void setNavigationbarColorAuto() {
        setNavigationbarColor(ThemeStore.navigationBarColor(this));
    }

    public void setLightStatusbar(boolean enabled) {
        ATH.setLightStatusbar(this, enabled);
    }

    public void setLightStatusbarAuto(int bgColor) {
        setLightStatusbar(ColorUtil.isColorLight(bgColor));
    }
}
