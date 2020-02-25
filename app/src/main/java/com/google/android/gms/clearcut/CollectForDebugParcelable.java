package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.Arrays;

public class CollectForDebugParcelable extends zzbkv {
    public static final Parcelable.Creator<CollectForDebugParcelable> CREATOR = new zzn();
    public final long collectForDebugExpiryTimeMillis;
    public final long collectForDebugStartTimeMillis;
    public final boolean skipPersistentStorage;

    public CollectForDebugParcelable(boolean z) {
        this(z, 0, 0);
    }

    public CollectForDebugParcelable(boolean z, long j, long j2) {
        this.skipPersistentStorage = z;
        this.collectForDebugStartTimeMillis = j;
        this.collectForDebugExpiryTimeMillis = j2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CollectForDebugParcelable)) {
            return false;
        }
        CollectForDebugParcelable collectForDebugParcelable = (CollectForDebugParcelable) obj;
        if (this.skipPersistentStorage == collectForDebugParcelable.skipPersistentStorage && this.collectForDebugStartTimeMillis == collectForDebugParcelable.collectForDebugStartTimeMillis && this.collectForDebugExpiryTimeMillis == collectForDebugParcelable.collectForDebugExpiryTimeMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.skipPersistentStorage), Long.valueOf(this.collectForDebugStartTimeMillis), Long.valueOf(this.collectForDebugExpiryTimeMillis)});
    }

    public String toString() {
        return "CollectForDebugParcelable[skipPersistentStorage: " + this.skipPersistentStorage + ",collectForDebugStartTimeMillis: " + this.collectForDebugStartTimeMillis + ",collectForDebugExpiryTimeMillis: " + this.collectForDebugExpiryTimeMillis + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.skipPersistentStorage);
        zzbky.zza(parcel, 2, this.collectForDebugExpiryTimeMillis);
        zzbky.zza(parcel, 3, this.collectForDebugStartTimeMillis);
        zzbky.zza(parcel, zza);
    }
}
