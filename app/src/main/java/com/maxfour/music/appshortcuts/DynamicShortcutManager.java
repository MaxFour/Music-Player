package com.maxfour.music.appshortcuts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import com.maxfour.music.appshortcuts.shortcuttype.LastAddedShortcutType;
import com.maxfour.music.appshortcuts.shortcuttype.ShuffleAllShortcutType;
import com.maxfour.music.appshortcuts.shortcuttype.TopSongsShortcutType;

import java.util.Arrays;
import java.util.List;

@TargetApi(Build.VERSION_CODES.N_MR1)
public class DynamicShortcutManager {

    private Context context;
    private ShortcutManager shortcutManager;

    public DynamicShortcutManager(Context context) {
        this.context = context;
        shortcutManager = this.context.getSystemService(ShortcutManager.class);
    }

    public static ShortcutInfo createShortcut(Context context, String id, String shortLabel, String longLabel, Icon icon, Intent intent) {
        return new ShortcutInfo.Builder(context, id)
                .setShortLabel(shortLabel)
                .setLongLabel(longLabel)
                .setIcon(icon)
                .setIntent(intent)
                .build();
    }

    public static void reportShortcutUsed(Context context, String shortcutId) {
        context.getSystemService(ShortcutManager.class).reportShortcutUsed(shortcutId);
    }

    public void initDynamicShortcuts() {
        if (shortcutManager.getDynamicShortcuts().size() == 0) {
            shortcutManager.setDynamicShortcuts(getDefaultShortcuts());
        }
    }

    public void updateDynamicShortcuts() {
        shortcutManager.updateShortcuts(getDefaultShortcuts());
    }

    public List<ShortcutInfo> getDefaultShortcuts() {
        return (Arrays.asList(
                new ShuffleAllShortcutType(context).getShortcutInfo(),
                new TopSongsShortcutType(context).getShortcutInfo(),
                new LastAddedShortcutType(context).getShortcutInfo()
        ));
    }
}
