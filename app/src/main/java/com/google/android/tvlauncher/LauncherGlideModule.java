package com.google.android.tvlauncher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.module.GlideModule;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import com.google.android.tvlauncher.appsview.data.PackageImageDecoder;
import com.google.android.tvlauncher.appsview.data.PackageImageModelLoader;

public class LauncherGlideModule implements GlideModule {
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.append(PackageImageDataSource.class, PackageImageDataSource.class, new ModelLoaderFactory<PackageImageDataSource, PackageImageDataSource>(this) {
            public ModelLoader<PackageImageDataSource, PackageImageDataSource> build(MultiModelLoaderFactory multiFactory) {
                return new PackageImageModelLoader();
            }

            public void teardown() {
            }
        }).append(PackageImageDataSource.class, Drawable.class, new PackageImageDecoder(context));
    }
}
