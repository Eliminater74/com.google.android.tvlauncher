package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.List;

public class FlagOverrides extends zzbkv {
    public static final Parcelable.Creator<FlagOverrides> CREATOR = new zzm();
    public final List<FlagOverride> overrides;

    public FlagOverrides(List<FlagOverride> list) {
        this.overrides = list;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlagOverrides)) {
            return false;
        }
        return this.overrides.equals(((FlagOverrides) obj).overrides);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FlagOverrides(");
        boolean z = true;
        for (FlagOverride next : this.overrides) {
            if (!z) {
                sb.append(", ");
            }
            z = false;
            next.toString(sb);
        }
        sb.append(")");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbky.zza(parcel);
        zzbky.zzc(parcel, 2, this.overrides, false);
        zzbky.zza(parcel, zza);
    }
}
