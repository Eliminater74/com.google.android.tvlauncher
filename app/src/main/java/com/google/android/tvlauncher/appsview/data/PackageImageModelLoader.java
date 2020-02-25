package com.google.android.tvlauncher.appsview.data;

import android.support.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

public class PackageImageModelLoader implements ModelLoader<PackageImageDataSource, PackageImageDataSource> {
    @Nullable
    public ModelLoader.LoadData<PackageImageDataSource> buildLoadData(final PackageImageDataSource packageImageDataSource, int width, int height, Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(packageImageDataSource), new DataFetcher<PackageImageDataSource>(this) {
            public void loadData(Priority priority, DataFetcher.DataCallback<? super PackageImageDataSource> callback) {
                callback.onDataReady(packageImageDataSource);
            }

            public void cleanup() {
            }

            public void cancel() {
            }

            public Class<PackageImageDataSource> getDataClass() {
                return PackageImageDataSource.class;
            }

            public DataSource getDataSource() {
                return DataSource.LOCAL;
            }
        });
    }

    public boolean handles(PackageImageDataSource packageImageDataSource) {
        return true;
    }
}
