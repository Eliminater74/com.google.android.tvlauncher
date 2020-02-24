package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: NetworkResponse */
public final class zzp {
    public final int zza;
    public final byte[] zzb;
    public final Map<String, String> zzc;
    public final List<zzl> zzd;
    public final boolean zze;
    private final long zzf;

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzp(int r9, byte[] r10, java.util.Map<java.lang.String, java.lang.String> r11, boolean r12, long r13) {
        /*
            r8 = this;
            if (r11 != 0) goto L_0x0006
            r0 = 0
            r4 = r0
            goto L_0x0045
        L_0x0006:
            boolean r0 = r11.isEmpty()
            if (r0 == 0) goto L_0x0012
            java.util.List r0 = java.util.Collections.emptyList()
            r4 = r0
            goto L_0x0045
        L_0x0012:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r11.size()
            r0.<init>(r1)
            java.util.Set r1 = r11.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0023:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0044
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            com.google.android.gms.internal.zzl r3 = new com.google.android.gms.internal.zzl
            java.lang.Object r4 = r2.getKey()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r2 = r2.getValue()
            java.lang.String r2 = (java.lang.String) r2
            r3.<init>(r4, r2)
            r0.add(r3)
            goto L_0x0023
        L_0x0044:
            r4 = r0
        L_0x0045:
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            r5 = r12
            r6 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map, boolean, long):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzp(int r9, byte[] r10, boolean r11, long r12, java.util.List<com.google.android.gms.internal.zzl> r14) {
        /*
            r8 = this;
            if (r14 != 0) goto L_0x0006
            r0 = 0
            r3 = r0
            goto L_0x0036
        L_0x0006:
            boolean r0 = r14.isEmpty()
            if (r0 == 0) goto L_0x0012
            java.util.Map r0 = java.util.Collections.emptyMap()
            r3 = r0
            goto L_0x0036
        L_0x0012:
            java.util.TreeMap r0 = new java.util.TreeMap
            java.util.Comparator r1 = java.lang.String.CASE_INSENSITIVE_ORDER
            r0.<init>(r1)
            java.util.Iterator r1 = r14.iterator()
        L_0x001d:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0035
            java.lang.Object r2 = r1.next()
            com.google.android.gms.internal.zzl r2 = (com.google.android.gms.internal.zzl) r2
            java.lang.String r3 = r2.zza()
            java.lang.String r2 = r2.zzb()
            r0.put(r3, r2)
            goto L_0x001d
        L_0x0035:
            r3 = r0
        L_0x0036:
            r0 = r8
            r1 = r9
            r2 = r10
            r4 = r14
            r5 = r11
            r6 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List):void");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map<java.lang.String, java.lang.String>, boolean, long):void
     arg types: [int, byte[], java.util.Map<java.lang.String, java.lang.String>, int, int]
     candidates:
      com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void
      com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map<java.lang.String, java.lang.String>, boolean, long):void */
    @Deprecated
    public zzp(byte[] bArr, Map<String, String> map) {
        this(200, bArr, map, false, 0L);
    }

    private zzp(int i, byte[] bArr, Map<String, String> map, List<zzl> list, boolean z, long j) {
        this.zza = i;
        this.zzb = bArr;
        this.zzc = map;
        if (list == null) {
            this.zzd = null;
        } else {
            this.zzd = Collections.unmodifiableList(list);
        }
        this.zze = z;
        this.zzf = j;
    }
}
