package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;

import java.io.IOException;

public interface DataRewinder<T> {

    void cleanup();

    @NonNull
    T rewindAndGet() throws IOException;

    public interface Factory<T> {
        @NonNull
        DataRewinder<T> build(@NonNull Object obj);

        @NonNull
        Class<T> getDataClass();
    }
}
