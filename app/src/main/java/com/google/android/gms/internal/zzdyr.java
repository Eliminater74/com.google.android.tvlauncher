package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.phenotype.Flag;
import com.google.android.gms.phenotype.RegistrationInfo;

/* compiled from: IPhenotypeService */
public interface zzdyr extends IInterface {
    void zza(zzdyp zzdyp) throws RemoteException;

    void zza(zzdyp zzdyp, long j) throws RemoteException;

    void zza(zzdyp zzdyp, String str) throws RemoteException;

    void zza(zzdyp zzdyp, String str, int i, String[] strArr, byte[] bArr) throws RemoteException;

    void zza(zzdyp zzdyp, String str, int i, String[] strArr, byte[] bArr, String str2, String str3) throws RemoteException;

    void zza(zzdyp zzdyp, String str, int i, String[] strArr, int[] iArr, byte[] bArr) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2, int i) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2, String str3, int i, int i2, String str4) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2, String str3, String str4) throws RemoteException;

    void zza(zzdyp zzdyp, String str, String str2, Flag[] flagArr) throws RemoteException;

    void zza(zzdyp zzdyp, String str, byte[] bArr) throws RemoteException;

    void zza(zzdyp zzdyp, byte[] bArr) throws RemoteException;

    void zza(zzdyp zzdyp, RegistrationInfo[] registrationInfoArr) throws RemoteException;

    void zzb(zzdyp zzdyp, String str) throws RemoteException;

    void zzb(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException;

    void zzc(zzdyp zzdyp, String str) throws RemoteException;

    void zzc(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException;
}
