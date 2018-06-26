package com.maxfour.music.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.maxfour.music.R;
import com.maxfour.music.loader.TopAndRecentlyPlayedSongsLoader;
import com.maxfour.music.model.Song;
import com.maxfour.music.provider.SongPlayCountStore;

import java.util.ArrayList;

public class MyTopSongsPlaylist extends AbsSmartPlaylist {

    public MyTopSongsPlaylist(@NonNull Context context) {
        super(context.getString(R.string.my_top_songs), R.drawable.ic_trending_up_white_24dp);
    }

    @NonNull
    @Override
    public ArrayList<Song> getSongs(@NonNull Context context) {
        return TopAndRecentlyPlayedSongsLoader.getTopSongs(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        SongPlayCountStore.getInstance(context).clear();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected MyTopSongsPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<MyTopSongsPlaylist> CREATOR = new Creator<MyTopSongsPlaylist>() {
        public MyTopSongsPlaylist createFromParcel(Parcel source) {
            return new MyTopSongsPlaylist(source);
        }

        public MyTopSongsPlaylist[] newArray(int size) {
            return new MyTopSongsPlaylist[size];
        }
    };
}