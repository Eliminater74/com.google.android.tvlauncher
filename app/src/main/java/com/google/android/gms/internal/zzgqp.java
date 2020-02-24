package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: SmallSortedMap */
final class zzgqp extends zzgqo<FieldDescriptorType, Object> {
    zzgqp(int i) {
        super(i, null);
    }

    public final void zza() {
        if (!zzb()) {
            for (int i = 0; i < zzc(); i++) {
                Map.Entry zzb = zzb(i);
                if (((zzgoc) zzb.getKey()).zzd()) {
                    zzb.setValue(Collections.unmodifiableList((List) zzb.getValue()));
                }
            }
            for (Map.Entry entry : zzd()) {
                if (((zzgoc) entry.getKey()).zzd()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zza();
    }
}
