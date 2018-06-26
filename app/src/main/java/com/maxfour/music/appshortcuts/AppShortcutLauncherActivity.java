package com.maxfour.music.appshortcuts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.maxfour.music.appshortcuts.shortcuttype.LastAddedShortcutType;
import com.maxfour.music.appshortcuts.shortcuttype.ShuffleAllShortcutType;
import com.maxfour.music.appshortcuts.shortcuttype.TopSongsShortcutType;
import com.maxfour.music.model.Playlist;
import com.maxfour.music.model.smartplaylist.LastAddedPlaylist;
import com.maxfour.music.model.smartplaylist.MyTopSongsPlaylist;
import com.maxfour.music.model.smartplaylist.ShuffleAllPlaylist;
import com.maxfour.music.service.MusicService;

public class AppShortcutLauncherActivity extends Activity {
    public static final String KEY_SHORTCUT_TYPE = "com.maxfour.music.appshortcuts.ShortcutType";

    public static final int SHORTCUT_TYPE_SHUFFLE_ALL = 0;
    public static final int SHORTCUT_TYPE_TOP_SONGS = 1;
    public static final int SHORTCUT_TYPE_LAST_ADDED = 2;
    public static final int SHORTCUT_TYPE_NONE = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int shortcutType = SHORTCUT_TYPE_NONE;

        // Set shortcutType from the intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //noinspection WrongConstant
            shortcutType = extras.getInt(KEY_SHORTCUT_TYPE, SHORTCUT_TYPE_NONE);
        }

        switch (shortcutType) {
            case SHORTCUT_TYPE_SHUFFLE_ALL:
                startServiceWithPlaylist(MusicService.SHUFFLE_MODE_SHUFFLE,
                        new ShuffleAllPlaylist(getApplicationContext()));
                DynamicShortcutManager.reportShortcutUsed(this, ShuffleAllShortcutType.getId());
                break;
            case SHORTCUT_TYPE_TOP_SONGS:
                startServiceWithPlaylist(MusicService.SHUFFLE_MODE_NONE,
                        new MyTopSongsPlaylist(getApplicationContext()));
                DynamicShortcutManager.reportShortcutUsed(this, TopSongsShortcutType.getId());
                break;
            case SHORTCUT_TYPE_LAST_ADDED:
                startServiceWithPlaylist(MusicService.SHUFFLE_MODE_NONE,
                        new LastAddedPlaylist(getApplicationContext()));
                DynamicShortcutManager.reportShortcutUsed(this, LastAddedShortcutType.getId());
                break;
        }

        finish();
    }

    private void startServiceWithPlaylist(int shuffleMode, Playlist playlist) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_PLAY_PLAYLIST);

        Bundle bundle = new Bundle();
        bundle.putParcelable(MusicService.INTENT_EXTRA_PLAYLIST, playlist);
        bundle.putInt(MusicService.INTENT_EXTRA_SHUFFLE_MODE, shuffleMode);

        intent.putExtras(bundle);

        startService(intent);
    }
}
