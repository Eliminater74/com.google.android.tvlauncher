package com.google.android.libraries.stitch.util;

import android.os.ParcelFileDescriptor;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;

public final class Closeables {
    @Deprecated
    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(@Nullable ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
            }
        }
    }

    private Closeables() {
    }
}
