package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import java.util.Arrays;

@Hide
/* compiled from: ImageRequest */
final class zzb {
    public final Uri zza;

    public zzb(Uri uri) {
        this.zza = uri;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzb)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return zzak.zza(((zzb) obj).zza, this.zza);
    }
}
