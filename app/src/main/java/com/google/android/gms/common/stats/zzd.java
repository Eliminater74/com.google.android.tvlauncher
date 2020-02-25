package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

import java.util.ArrayList;

@Hide
/* compiled from: WakeLockEventCreator */
public final class zzd implements Parcelable.Creator<WakeLockEvent> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new WakeLockEvent[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbkw.zza(parcel);
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        String str = null;
        ArrayList<String> arrayList = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        float f = 0.0f;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbkw.zzg(parcel2, readInt);
                    break;
                case 2:
                    j = zzbkw.zzi(parcel2, readInt);
                    break;
                case 3:
                case 7:
                case 9:
                default:
                    zzbkw.zzb(parcel2, readInt);
                    break;
                case 4:
                    str = zzbkw.zzq(parcel2, readInt);
                    break;
                case 5:
                    i3 = zzbkw.zzg(parcel2, readInt);
                    break;
                case 6:
                    arrayList = zzbkw.zzac(parcel2, readInt);
                    break;
                case 8:
                    j2 = zzbkw.zzi(parcel2, readInt);
                    break;
                case 10:
                    str3 = zzbkw.zzq(parcel2, readInt);
                    break;
                case 11:
                    i2 = zzbkw.zzg(parcel2, readInt);
                    break;
                case 12:
                    str2 = zzbkw.zzq(parcel2, readInt);
                    break;
                case 13:
                    str4 = zzbkw.zzq(parcel2, readInt);
                    break;
                case 14:
                    i4 = zzbkw.zzg(parcel2, readInt);
                    break;
                case 15:
                    f = zzbkw.zzl(parcel2, readInt);
                    break;
                case 16:
                    j3 = zzbkw.zzi(parcel2, readInt);
                    break;
                case 17:
                    str5 = zzbkw.zzq(parcel2, readInt);
                    break;
            }
        }
        zzbkw.zzae(parcel2, zza);
        return new WakeLockEvent(i, j, i2, str, i3, arrayList, str2, j2, i4, str3, str4, f, j3, str5);
    }
}
