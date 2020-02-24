package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/* compiled from: DiskBasedCache */
final class zzan {
    long zza;
    final String zzb;
    final String zzc;
    final long zzd;
    final long zze;
    final long zzf;
    final long zzg;
    final List<zzl> zzh;

    private zzan(String str, String str2, long j, long j2, long j3, long j4, List<zzl> list) {
        this.zzb = str;
        this.zzc = "".equals(str2) ? null : str2;
        this.zzd = j;
        this.zze = j2;
        this.zzf = j3;
        this.zzg = j4;
        this.zzh = list;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzan(java.lang.String r16, com.google.android.gms.internal.zzc r17) {
        /*
            r15 = this;
            r0 = r17
            java.lang.String r3 = r0.zzb
            long r4 = r0.zzc
            long r6 = r0.zzd
            long r8 = r0.zze
            long r10 = r0.zzf
            java.util.List<com.google.android.gms.internal.zzl> r1 = r0.zzh
            if (r1 == 0) goto L_0x0015
            java.util.List<com.google.android.gms.internal.zzl> r1 = r0.zzh
            r12 = r1
            goto L_0x004a
        L_0x0015:
            java.util.Map<java.lang.String, java.lang.String> r1 = r0.zzg
            java.util.ArrayList r2 = new java.util.ArrayList
            int r12 = r1.size()
            r2.<init>(r12)
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0028:
            boolean r12 = r1.hasNext()
            if (r12 == 0) goto L_0x0049
            java.lang.Object r12 = r1.next()
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12
            com.google.android.gms.internal.zzl r13 = new com.google.android.gms.internal.zzl
            java.lang.Object r14 = r12.getKey()
            java.lang.String r14 = (java.lang.String) r14
            java.lang.Object r12 = r12.getValue()
            java.lang.String r12 = (java.lang.String) r12
            r13.<init>(r14, r12)
            r2.add(r13)
            goto L_0x0028
        L_0x0049:
            r12 = r2
        L_0x004a:
            r1 = r15
            r2 = r16
            r1.<init>(r2, r3, r4, r6, r8, r10, r12)
            byte[] r0 = r0.zza
            int r0 = r0.length
            long r0 = (long) r0
            r2 = r15
            r2.zza = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzan.<init>(java.lang.String, com.google.android.gms.internal.zzc):void");
    }

    static zzan zza(zzao zzao) throws IOException {
        if (zzam.zza((InputStream) zzao) == 538247942) {
            return new zzan(zzam.zza(zzao), zzam.zza(zzao), zzam.zzb((InputStream) zzao), zzam.zzb((InputStream) zzao), zzam.zzb((InputStream) zzao), zzam.zzb((InputStream) zzao), zzam.zzb(zzao));
        }
        throw new IOException();
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(OutputStream outputStream) {
        try {
            zzam.zza(outputStream, 538247942);
            zzam.zza(outputStream, this.zzb);
            zzam.zza(outputStream, this.zzc == null ? "" : this.zzc);
            zzam.zza(outputStream, this.zzd);
            zzam.zza(outputStream, this.zze);
            zzam.zza(outputStream, this.zzf);
            zzam.zza(outputStream, this.zzg);
            List<zzl> list = this.zzh;
            if (list != null) {
                zzam.zza(outputStream, list.size());
                for (zzl next : list) {
                    zzam.zza(outputStream, next.zza());
                    zzam.zza(outputStream, next.zzb());
                }
            } else {
                zzam.zza(outputStream, 0);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            zzaf.zzb("%s", e.toString());
            return false;
        }
    }
}
