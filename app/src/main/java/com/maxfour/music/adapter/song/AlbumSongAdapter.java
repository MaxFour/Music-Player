package com.maxfour.music.adapter.song;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxfour.music.interfaces.CabHolder;
import com.maxfour.music.model.Song;
import com.maxfour.music.util.MusicUtil;

import java.util.ArrayList;
import java.util.List;

public class AlbumSongAdapter extends SongAdapter {

    public AlbumSongAdapter(AppCompatActivity activity, List<Song> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder) {
        super(activity, dataSet, itemLayoutRes, usePalette, cabHolder);
    }

    @Override
    protected SongAdapter.ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Song song = dataSet.get(position);

        if (holder.imageText != null) {
            final int songNumber = MusicUtil.getFixedSongNumber(song.songNumber);
            final String songNumberString = songNumber > 0 ? String.valueOf(songNumber) : "-";
            holder.imageText.setText(songNumberString);
        }
    }

    @Override
    protected String getSongText(Song song) {
        return MusicUtil.getReadableDurationString(song.duration);
    }

    public class ViewHolder extends SongAdapter.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (imageText != null) {
                imageText.setVisibility(View.VISIBLE);
            }
            if (image != null) {
                image.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void loadAlbumCover(Song song, SongAdapter.ViewHolder holder) {
        // We don't want to load it in this adapter
    }
}
