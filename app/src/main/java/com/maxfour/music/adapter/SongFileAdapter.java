package com.maxfour.music.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.kabouzeid.appthemehelper.util.ATHUtil;
import com.maxfour.music.R;
import com.maxfour.music.adapter.base.AbsMultiSelectAdapter;
import com.maxfour.music.adapter.base.MediaEntryViewHolder;
import com.maxfour.music.glide.audiocover.AudioFileCover;
import com.maxfour.music.interfaces.CabHolder;
import com.maxfour.music.util.ImageUtil;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class SongFileAdapter extends AbsMultiSelectAdapter<SongFileAdapter.ViewHolder, File> implements FastScrollRecyclerView.SectionedAdapter {

    private static final int FILE = 0;
    private static final int FOLDER = 1;

    private final AppCompatActivity activity;
    private final int itemLayoutRes;
    @Nullable
    private final Callbacks callbacks;
    private List<File> dataSet;

    public SongFileAdapter(@NonNull AppCompatActivity activity, @NonNull List<File> songFiles, @LayoutRes int itemLayoutRes, @Nullable Callbacks callback, @Nullable CabHolder cabHolder) {
        super(activity, cabHolder, R.menu.menu_media_selection);
        this.activity = activity;
        this.dataSet = songFiles;
        this.itemLayoutRes = itemLayoutRes;
        this.callbacks = callback;
        setHasStableIds(true);
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return size + " B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).isDirectory() ? FOLDER : FILE;
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).hashCode();
    }

    public void swapDataSet(@NonNull List<File> songFiles) {
        this.dataSet = songFiles;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(itemLayoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        final File file = dataSet.get(index);

        holder.itemView.setActivated(isChecked(file));

        if (holder.getAdapterPosition() == getItemCount() - 1) {
            if (holder.shortSeparator != null) {
                holder.shortSeparator.setVisibility(View.GONE);
            }
        } else {
            if (holder.shortSeparator != null) {
                holder.shortSeparator.setVisibility(View.VISIBLE);
            }
        }

        if (holder.title != null) {
            holder.title.setText(getFileTitle(file));
        }
        if (holder.text != null) {
            if (holder.getItemViewType() == FILE) {
                holder.text.setText(getFileText(file));
            } else {
                holder.text.setVisibility(View.GONE);
            }
        }

        if (holder.image != null) {
            loadFileImage(file, holder);
        }
    }

    protected String getFileTitle(File file) {
        return file.getName();
    }

    protected String getFileText(File file) {
        return file.isDirectory() ? null : readableFileSize(file.length());
    }

    @SuppressWarnings("ConstantConditions")
    protected void loadFileImage(File file, final ViewHolder holder) {
        final int iconColor = ATHUtil.resolveColor(activity, R.attr.iconColor);
        if (file.isDirectory()) {
            holder.image.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
            holder.image.setImageResource(R.drawable.ic_folder_white_24dp);
        } else {
            Drawable error = ImageUtil.getTintedVectorDrawable(activity, R.drawable.ic_file_music_white_24dp, iconColor);
            Glide.with(activity)
                    .load(new AudioFileCover(file.getPath()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(error)
                    .placeholder(error)
                    .animate(android.R.anim.fade_in)
                    .signature(new MediaStoreSignature("", file.lastModified(), 0))
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    protected File getIdentifier(int position) {
        return dataSet.get(position);
    }

    @Override
    protected String getName(File object) {
        return getFileTitle(object);
    }

    @Override
    protected void onMultipleItemAction(MenuItem menuItem, List<File> selection) {
        if (callbacks == null) return;
        callbacks.onMultipleItemAction(menuItem, selection);
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(dataSet.get(position).getName().charAt(0)).toUpperCase();
    }

    public interface Callbacks {
        void onFileSelected(File file);

        void onFileMenuClicked(File file, View view);

        void onMultipleItemAction(MenuItem item, List<File> files);
    }

    public class ViewHolder extends MediaEntryViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            if (menu != null && callbacks != null) {
                menu.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (isPositionInRange(position)) {
                        callbacks.onFileMenuClicked(dataSet.get(position), v);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (isPositionInRange(position)) {
                if (isInQuickSelectMode()) {
                    toggleChecked(position);
                } else {
                    if (callbacks != null) {
                        callbacks.onFileSelected(dataSet.get(position));
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            return isPositionInRange(position) && toggleChecked(position);
        }

        private boolean isPositionInRange(int position) {
            return position >= 0 && position < dataSet.size();
        }
    }
}
