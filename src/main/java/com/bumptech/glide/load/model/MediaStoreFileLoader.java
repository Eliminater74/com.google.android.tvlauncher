package com.bumptech.glide.load.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import java.io.FileNotFoundException;

public final class MediaStoreFileLoader implements ModelLoader<Uri, File> {
    private final Context context;

    public MediaStoreFileLoader(Context context2) {
        this.context = context2;
    }

    public ModelLoader.LoadData<File> buildLoadData(@NonNull Uri uri, int width, int height, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(uri), new FilePathFetcher(this.context, uri));
    }

    public boolean handles(@NonNull Uri uri) {
        return MediaStoreUtil.isMediaStoreUri(uri);
    }

    private static class FilePathFetcher implements DataFetcher<File> {
        private static final String[] PROJECTION = {"_data"};
        private final Context context;
        private final Uri uri;

        FilePathFetcher(Context context2, Uri uri2) {
            this.context = context2;
            this.uri = uri2;
        }

        public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super File> callback) {
            Cursor cursor = this.context.getContentResolver().query(this.uri, PROJECTION, null, null, null);
            String filePath = null;
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (TextUtils.isEmpty(filePath)) {
                String valueOf = String.valueOf(this.uri);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
                sb.append("Failed to find file path for: ");
                sb.append(valueOf);
                callback.onLoadFailed(new FileNotFoundException(sb.toString()));
                return;
            }
            callback.onDataReady(new File(filePath));
        }

        public void cleanup() {
        }

        public void cancel() {
        }

        @NonNull
        public Class<File> getDataClass() {
            return File.class;
        }

        @NonNull
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }

    public static final class Factory implements ModelLoaderFactory<Uri, File> {
        private final Context context;

        public Factory(Context context2) {
            this.context = context2;
        }

        @NonNull
        public ModelLoader<Uri, File> build(MultiModelLoaderFactory multiFactory) {
            return new MediaStoreFileLoader(this.context);
        }

        public void teardown() {
        }
    }
}
