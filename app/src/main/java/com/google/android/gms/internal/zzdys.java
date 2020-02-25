package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.phenotype.Flag;
import com.google.android.gms.phenotype.RegistrationInfo;

/* compiled from: IPhenotypeService */
public final class zzdys extends zzez implements zzdyr {
    zzdys(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.phenotype.internal.IPhenotypeService");
    }

    public final void zza(zzdyp zzdyp, String str, int i, String[] strArr, byte[] bArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeInt(i);
        a_.writeStringArray(strArr);
        a_.writeByteArray(bArr);
        zzb(1, a_);
    }

    public final void zza(zzdyp zzdyp, String str, int i, String[] strArr, int[] iArr, byte[] bArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeInt(i);
        a_.writeStringArray(strArr);
        a_.writeIntArray(iArr);
        a_.writeByteArray(bArr);
        zzb(2, a_);
    }

    public final void zza(zzdyp zzdyp, RegistrationInfo[] registrationInfoArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeTypedArray(registrationInfoArr, 0);
        zzb(19, a_);
    }

    public final void zza(zzdyp zzdyp, String str, int i, String[] strArr, byte[] bArr, String str2, String str3) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeInt(i);
        a_.writeStringArray(strArr);
        a_.writeByteArray(bArr);
        a_.writeString(str2);
        a_.writeString(str3);
        zzb(13, a_);
    }

    public final void zza(zzdyp zzdyp, String str, byte[] bArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeByteArray(bArr);
        zzb(20, a_);
    }

    public final void zza(zzdyp zzdyp, String str) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        zzb(3, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        zzb(11, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2, String str3, String str4) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        a_.writeString(str4);
        zzb(17, a_);
    }

    public final void zzb(zzdyp zzdyp, String str) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        zzb(5, a_);
    }

    public final void zzc(zzdyp zzdyp, String str) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        zzb(10, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2, int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeInt(i);
        zzb(9, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        zzb(6, a_);
    }

    public final void zza(zzdyp zzdyp, long j) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeLong(j);
        zzb(12, a_);
    }

    public final void zza(zzdyp zzdyp) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        zzb(7, a_);
    }

    public final void zza(zzdyp zzdyp, byte[] bArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeByteArray(bArr);
        zzb(8, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        a_.writeInt(i);
        a_.writeInt(i2);
        a_.writeString(str4);
        zzb(14, a_);
    }

    public final void zza(zzdyp zzdyp, String str, String str2, Flag[] flagArr) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeTypedArray(flagArr, 0);
        zzb(18, a_);
    }

    public final void zzb(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        zzb(15, a_);
    }

    public final void zzc(zzdyp zzdyp, String str, String str2, String str3) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzdyp);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        zzb(16, a_);
    }
}
