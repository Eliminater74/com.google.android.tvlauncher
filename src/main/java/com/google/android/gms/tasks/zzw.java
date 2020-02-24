package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: Tasks */
final class zzw implements Continuation<Void, List<TResult>> {
    private final /* synthetic */ Collection zza;

    zzw(Collection collection) {
        this.zza = collection;
    }

    public final /* synthetic */ Object then(@NonNull Task task) throws Exception {
        if (this.zza.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Task result : this.zza) {
            arrayList.add(result.getResult());
        }
        return arrayList;
    }
}
