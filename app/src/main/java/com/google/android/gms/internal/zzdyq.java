package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.Configurations;
import com.google.android.gms.phenotype.DogfoodsToken;
import com.google.android.gms.phenotype.ExperimentTokens;
import com.google.android.gms.phenotype.Flag;
import com.google.android.gms.phenotype.FlagOverrides;

/* compiled from: IPhenotypeCallbacks */
public abstract class zzdyq extends zzfa implements zzdyp {
    public zzdyq() {
        attachInterface(this, "com.google.android.gms.phenotype.internal.IPhenotypeCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzb((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zzc((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 3:
                zze((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 4:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (Configurations) zzfb.zza(parcel, Configurations.CREATOR));
                break;
            case 5:
                zzf((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 6:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (ExperimentTokens) zzfb.zza(parcel, ExperimentTokens.CREATOR));
                break;
            case 7:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (DogfoodsToken) zzfb.zza(parcel, DogfoodsToken.CREATOR));
                break;
            case 8:
                zzg((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 9:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (Flag) zzfb.zza(parcel, Flag.CREATOR));
                break;
            case 10:
                zzb((Status) zzfb.zza(parcel, Status.CREATOR), (Configurations) zzfb.zza(parcel, Configurations.CREATOR));
                break;
            case 11:
                zza((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 12:
                zzh((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 13:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (FlagOverrides) zzfb.zza(parcel, FlagOverrides.CREATOR));
                break;
            case 14:
                zzd((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
