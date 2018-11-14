package com.maxfour.appthemehelper.util;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

public final class NavigationViewUtil {

    public static void setItemIconColors(@NonNull NavigationView navigationView, @ColorInt int normalColor, @ColorInt int selectedColor) {
        final ColorStateList iconSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        normalColor,
                        selectedColor
                });
        navigationView.setItemIconTintList(iconSl);
    }

    public static void setItemTextColors(@NonNull NavigationView navigationView, @ColorInt int normalColor, @ColorInt int selectedColor) {
        final ColorStateList textSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        normalColor,
                        selectedColor
                });
        navigationView.setItemTextColor(textSl);
    }

    private NavigationViewUtil() {
    }
}
