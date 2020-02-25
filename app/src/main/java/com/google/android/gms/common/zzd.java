package com.google.android.gms.common;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

import java.util.Map;

/* compiled from: GoogleApiAvailability */
final class zzd implements Continuation<Map<zzi<?>, String>, Void> {
    zzd(GoogleApiAvailability googleApiAvailability) {
    }

    public final /* synthetic */ Object then(@NonNull Task task) throws Exception {
        task.getResult();
        return null;
    }
}
