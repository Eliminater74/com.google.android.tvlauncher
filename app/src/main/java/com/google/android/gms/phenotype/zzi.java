package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: ExperimentTokensCreator */
public final class zzi implements Parcelable.Creator<ExperimentTokens> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new ExperimentTokens[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        byte[] bArr = null;
        byte[][] bArr2 = null;
        byte[][] bArr3 = null;
        byte[][] bArr4 = null;
        byte[][] bArr5 = null;
        int[] iArr = null;
        byte[][] bArr6 = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzbkw.zzq(parcel, readInt);
                    break;
                case 3:
                    bArr = zzbkw.zzt(parcel, readInt);
                    break;
                case 4:
                    bArr2 = zzbkw.zzu(parcel, readInt);
                    break;
                case 5:
                    bArr3 = zzbkw.zzu(parcel, readInt);
                    break;
                case 6:
                    bArr4 = zzbkw.zzu(parcel, readInt);
                    break;
                case 7:
                    bArr5 = zzbkw.zzu(parcel, readInt);
                    break;
                case 8:
                    iArr = zzbkw.zzw(parcel, readInt);
                    break;
                case 9:
                    bArr6 = zzbkw.zzu(parcel, readInt);
                    break;
                default:
                    zzbkw.zzb(parcel, readInt);
                    break;
            }
        }
        zzbkw.zzae(parcel, zza);
        return new ExperimentTokens(str, bArr, bArr2, bArr3, bArr4, bArr5, iArr, bArr6);
    }
}
