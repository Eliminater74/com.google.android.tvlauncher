package android.support.p004v7.util;

import android.support.annotation.Nullable;

/* renamed from: android.support.v7.util.ListUpdateCallback */
public interface ListUpdateCallback {
    void onChanged(int i, int i2, @Nullable Object obj);

    void onInserted(int i, int i2);

    void onMoved(int i, int i2);

    void onRemoved(int i, int i2);
}
