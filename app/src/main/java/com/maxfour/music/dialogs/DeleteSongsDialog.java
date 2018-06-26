package com.maxfour.music.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;
import com.maxfour.music.R;
import com.maxfour.music.model.Song;
import com.maxfour.music.util.MusicUtil;

import java.util.ArrayList;

public class DeleteSongsDialog extends DialogFragment {

    @NonNull
    public static DeleteSongsDialog create(Song song) {
        ArrayList<Song> list = new ArrayList<>();
        list.add(song);
        return create(list);
    }

    @NonNull
    public static DeleteSongsDialog create(ArrayList<Song> songs) {
        DeleteSongsDialog dialog = new DeleteSongsDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("songs", songs);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection unchecked
        final ArrayList<Song> songs = getArguments().getParcelableArrayList("songs");
        int title;
        CharSequence content;
        if (songs.size() > 1) {
            title = R.string.delete_songs_title;
            content = Html.fromHtml(getResources().getQuantityString(R.plurals.delete_x_songs, songs.size(), songs.size()));
        } else {
            title = R.string.delete_song_title;
            content = Html.fromHtml(getString(R.string.delete_song_x, songs.get(0).title));
        }
        return new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText(R.string.delete_action)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    if (getActivity() == null)
                        return;
                    MusicUtil.deleteSongs(getActivity(), songs);
                })
                .build();
    }
}
