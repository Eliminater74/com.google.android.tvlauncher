package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: InternalGoogleApiClient */
public interface zzcc {
    ConnectionResult zza(long j, TimeUnit timeUnit);

    @Nullable
    ConnectionResult zza(@NonNull Api<?> api);

    <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull zzn zzn);

    void zza();

    void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    boolean zza(zzda zzda);

    ConnectionResult zzb();

    <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull zzn zzn);

    void zzc();

    boolean zzd();

    boolean zze();

    void zzf();

    void zzg();
}
