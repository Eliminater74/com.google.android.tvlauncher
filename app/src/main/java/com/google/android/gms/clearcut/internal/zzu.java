package com.google.android.gms.clearcut.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: PlayLoggerContextCreator */
public final class zzu implements Parcelable.Creator<PlayLoggerContext> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new PlayLoggerContext[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i = 0;
        int i2 = 0;
        boolean z = true;
        boolean z2 = false;
        int i3 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzbkw.zzq(parcel, readInt);
                    break;
                case 3:
                    i = zzbkw.zzg(parcel, readInt);
                    break;
                case 4:
                    i2 = zzbkw.zzg(parcel, readInt);
                    break;
                case 5:
                    str2 = zzbkw.zzq(parcel, readInt);
                    break;
                case 6:
                    str3 = zzbkw.zzq(parcel, readInt);
                    break;
                case 7:
                    z = zzbkw.zzc(parcel, readInt);
                    break;
                case 8:
                    str4 = zzbkw.zzq(parcel, readInt);
                    break;
                case 9:
                    z2 = zzbkw.zzc(parcel, readInt);
                    break;
                case 10:
                    i3 = zzbkw.zzg(parcel, readInt);
                    break;
                default:
                    zzbkw.zzb(parcel, readInt);
                    break;
            }
        }
        zzbkw.zzae(parcel, zza);
        return new PlayLoggerContext(str, i, i2, str2, str3, z, str4, z2, i3);
    }
}
