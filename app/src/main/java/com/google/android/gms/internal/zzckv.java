package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.List;

/* compiled from: FeedbackUtils */
public final class zzckv {
    public static Bundle zza(@Nullable List<Pair<String, String>> list) {
        if (list == null) {
            return null;
        }
        int size = list.size();
        Bundle bundle = new Bundle(size);
        for (int i = 0; i < size; i++) {
            Pair pair = list.get(i);
            bundle.putString((String) pair.first, (String) pair.second);
        }
        return bundle;
    }
}
