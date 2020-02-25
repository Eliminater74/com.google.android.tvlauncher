package com.google.android.gms.usagereporting;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.ArrayList;
import java.util.List;

@Hide
public class UsageReportingOptInOptions extends zzbkv {
    @Hide
    public static final Parcelable.Creator<UsageReportingOptInOptions> CREATOR = new zzb();
    public static final int OPTION_NO_CHANGE = 0;
    public static final int OPTION_OPT_IN = 1;
    public static final int OPTION_OPT_OUT = 2;
    private final List<String> zzb = new ArrayList();
    private int zza;

    public UsageReportingOptInOptions(int i) {
        this.zza = i;
    }

    @Hide
    public final int zza() {
        return this.zza;
    }

    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, this.zza);
        zzbky.zza(parcel, zza2);
    }

    @Hide
    public final List<String> zzb() {
        return this.zzb;
    }
}
