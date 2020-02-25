package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import com.google.android.gms.common.server.response.FastJsonResponse;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: StringToIntConverter */
public final class zzbma extends zzbkv implements FastJsonResponse.FieldConverter<String, Integer> {
    public static final Parcelable.Creator<zzbma> CREATOR = new zzbmc();
    private final int zza;
    private final HashMap<String, Integer> zzb;
    private final SparseArray<String> zzc;
    private final ArrayList<zzbmb> zzd;

    zzbma(int i, ArrayList<zzbmb> arrayList) {
        this.zza = i;
        this.zzb = new HashMap<>();
        this.zzc = new SparseArray<>();
        this.zzd = null;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            zzbmb zzbmb = (zzbmb) obj;
            zza(zzbmb.zza, zzbmb.zzb);
        }
    }

    public zzbma() {
        this.zza = 1;
        this.zzb = new HashMap<>();
        this.zzc = new SparseArray<>();
        this.zzd = null;
    }

    public final zzbma zza(String str, int i) {
        this.zzb.put(str, Integer.valueOf(i));
        this.zzc.put(i, str);
        return this;
    }

    public final int getTypeIn() {
        return 7;
    }

    public final int getTypeOut() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        ArrayList arrayList = new ArrayList();
        for (String next : this.zzb.keySet()) {
            arrayList.add(new zzbmb(next, this.zzb.get(next).intValue()));
        }
        zzbky.zzc(parcel, 2, arrayList, false);
        zzbky.zza(parcel, zza2);
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        String str = this.zzc.get(((Integer) obj).intValue());
        if (str != null || !this.zzb.containsKey("gms_unknown")) {
            return str;
        }
        return "gms_unknown";
    }

    public final /* synthetic */ Object convert(Object obj) {
        Integer num = this.zzb.get((String) obj);
        if (num == null) {
            return this.zzb.get("gms_unknown");
        }
        return num;
    }
}
