package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.Configurations;
import com.google.android.gms.phenotype.DogfoodsToken;
import com.google.android.gms.phenotype.ExperimentTokens;
import com.google.android.gms.phenotype.Flag;
import com.google.android.gms.phenotype.FlagOverrides;

/* compiled from: IPhenotypeCallbacks */
public interface zzdyp extends IInterface {
    void zza(Status status) throws RemoteException;

    void zza(Status status, Configurations configurations) throws RemoteException;

    void zza(Status status, DogfoodsToken dogfoodsToken) throws RemoteException;

    void zza(Status status, ExperimentTokens experimentTokens) throws RemoteException;

    void zza(Status status, Flag flag) throws RemoteException;

    void zza(Status status, FlagOverrides flagOverrides) throws RemoteException;

    void zzb(Status status) throws RemoteException;

    void zzb(Status status, Configurations configurations) throws RemoteException;

    void zzc(Status status) throws RemoteException;

    void zzd(Status status) throws RemoteException;

    void zze(Status status) throws RemoteException;

    void zzf(Status status) throws RemoteException;

    void zzg(Status status) throws RemoteException;

    void zzh(Status status) throws RemoteException;
}
