package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: BasicNetwork */
public class zzaj implements zzm {
    private static final boolean zza = zzaf.zza;
    @Deprecated
    private final zzar zzb;
    private final zzai zzc;
    private final zzak zzd;

    @Deprecated
    public zzaj(zzar zzar) {
        this(zzar, new zzak(4096));
    }

    @Deprecated
    private zzaj(zzar zzar, zzak zzak) {
        this.zzb = zzar;
        this.zzc = new zzah(zzar);
        this.zzd = zzak;
    }

    public zzaj(zzai zzai) {
        this(zzai, new zzak(4096));
    }

    private zzaj(zzai zzai, zzak zzak) {
        this.zzc = zzai;
        this.zzb = zzai;
        this.zzd = zzak;
    }

    private static void zza(String str, zzr<?> zzr, zzae zzae) throws zzae {
        zzab zzj = zzr.zzj();
        int zzi = zzr.zzi();
        try {
            zzj.zza(zzae);
            zzr.zza(String.format("%s-retry [timeout=%s]", str, Integer.valueOf(zzi)));
        } catch (zzae e) {
            zzr.zza(String.format("%s-timeout-giveup [timeout=%s]", str, Integer.valueOf(zzi)));
            throw e;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void
     arg types: [?, ?[OBJECT, ARRAY], int, long, java.util.List<com.google.android.gms.internal.zzl>]
     candidates:
      com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map<java.lang.String, java.lang.String>, boolean, long):void
      com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void
     arg types: [?, byte[], int, long, java.util.ArrayList]
     candidates:
      com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map<java.lang.String, java.lang.String>, boolean, long):void
      com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void
     arg types: [int, byte[], int, long, java.util.List<com.google.android.gms.internal.zzl>]
     candidates:
      com.google.android.gms.internal.zzp.<init>(int, byte[], java.util.Map<java.lang.String, java.lang.String>, boolean, long):void
      com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List<com.google.android.gms.internal.zzl>):void */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01f4, code lost:
        throw new com.google.android.gms.internal.zzac(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01f5, code lost:
        zza("auth", r2, new com.google.android.gms.internal.zza(r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0201, code lost:
        zza("network", r2, new com.google.android.gms.internal.zzo());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0212, code lost:
        throw new com.google.android.gms.internal.zzq(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0213, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0214, code lost:
        r2 = java.lang.String.valueOf(r22.zzc());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0224, code lost:
        if (r2.length() != 0) goto L_0x0226;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0226, code lost:
        r2 = "Bad URL ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x022b, code lost:
        r2 = new java.lang.String("Bad URL ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0233, code lost:
        throw new java.lang.RuntimeException(r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0235, code lost:
        zza("socket", r2, new com.google.android.gms.internal.zzad());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0197, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0198, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x019b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x019c, code lost:
        r17 = r5;
        r10 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x019f, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01a2, code lost:
        r0 = r10.zza();
        com.google.android.gms.internal.zzaf.zzc("Unexpected response code %d for %s", java.lang.Integer.valueOf(r0), r22.zzc());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01b9, code lost:
        if (r13 != null) goto L_0x01bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01bb, code lost:
        r11 = new com.google.android.gms.internal.zzp(r0, r13, false, android.os.SystemClock.elapsedRealtime() - r3, r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01cb, code lost:
        if (r0 == 401) goto L_0x01f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01d4, code lost:
        if (r0 < 400) goto L_0x01e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01e0, code lost:
        throw new com.google.android.gms.internal.zzg(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01e3, code lost:
        if (r0 < 500) goto L_0x01ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01ee, code lost:
        throw new com.google.android.gms.internal.zzac(r11);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0213 A[ExcHandler: MalformedURLException (r0v2 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:2:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0234 A[ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:2:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x020d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzp zza(com.google.android.gms.internal.zzr<?> r22) throws com.google.android.gms.internal.zzae {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            long r3 = android.os.SystemClock.elapsedRealtime()
        L_0x0008:
            java.util.List r5 = java.util.Collections.emptyList()
            r6 = 1
            r7 = 2
            r9 = 0
            com.google.android.gms.internal.zzc r0 = r22.zzd()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            if (r0 != 0) goto L_0x001d
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            goto L_0x0041
        L_0x001d:
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            r10.<init>()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            java.lang.String r11 = r0.zzb     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            if (r11 == 0) goto L_0x002d
            java.lang.String r11 = "If-None-Match"
            java.lang.String r12 = r0.zzb     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            r10.put(r11, r12)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
        L_0x002d:
            long r11 = r0.zzd     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            r13 = 0
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 <= 0) goto L_0x0040
            java.lang.String r11 = "If-Modified-Since"
            long r12 = r0.zzd     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            java.lang.String r0 = com.google.android.gms.internal.zzap.zza(r12)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            r10.put(r11, r0)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
        L_0x0040:
            r0 = r10
        L_0x0041:
            com.google.android.gms.internal.zzai r10 = r1.zzc     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            com.google.android.gms.internal.zzaq r10 = r10.zza(r2, r0)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x019b }
            int r12 = r10.zza()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.List r5 = r10.zzb()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r0 = 304(0x130, float:4.26E-43)
            if (r12 != r0) goto L_0x011e
            com.google.android.gms.internal.zzc r0 = r22.zzd()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r0 != 0) goto L_0x006e
            com.google.android.gms.internal.zzp r0 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r14 = 304(0x130, float:4.26E-43)
            r15 = 0
            r16 = 1
            long r11 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            long r17 = r11 - r3
            r13 = r0
            r19 = r5
            r13.<init>(r14, r15, r16, r17, r19)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            return r0
        L_0x006e:
            java.util.TreeSet r11 = new java.util.TreeSet     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.Comparator r12 = java.lang.String.CASE_INSENSITIVE_ORDER     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r11.<init>(r12)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            boolean r12 = r5.isEmpty()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r12 != 0) goto L_0x0094
            java.util.Iterator r12 = r5.iterator()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
        L_0x0080:
            boolean r13 = r12.hasNext()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r13 == 0) goto L_0x0094
            java.lang.Object r13 = r12.next()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            com.google.android.gms.internal.zzl r13 = (com.google.android.gms.internal.zzl) r13     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.String r13 = r13.zza()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r11.add(r13)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            goto L_0x0080
        L_0x0094:
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r12.<init>(r5)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.List<com.google.android.gms.internal.zzl> r13 = r0.zzh     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r13 == 0) goto L_0x00c6
            java.util.List<com.google.android.gms.internal.zzl> r13 = r0.zzh     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            boolean r13 = r13.isEmpty()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r13 != 0) goto L_0x0105
            java.util.List<com.google.android.gms.internal.zzl> r13 = r0.zzh     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
        L_0x00ab:
            boolean r14 = r13.hasNext()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r14 == 0) goto L_0x00c5
            java.lang.Object r14 = r13.next()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            com.google.android.gms.internal.zzl r14 = (com.google.android.gms.internal.zzl) r14     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.String r15 = r14.zza()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            boolean r15 = r11.contains(r15)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r15 != 0) goto L_0x00c4
            r12.add(r14)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
        L_0x00c4:
            goto L_0x00ab
        L_0x00c5:
            goto L_0x0105
        L_0x00c6:
            java.util.Map<java.lang.String, java.lang.String> r13 = r0.zzg     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            boolean r13 = r13.isEmpty()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r13 != 0) goto L_0x0105
            java.util.Map<java.lang.String, java.lang.String> r13 = r0.zzg     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.Set r13 = r13.entrySet()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
        L_0x00d8:
            boolean r14 = r13.hasNext()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r14 == 0) goto L_0x0105
            java.lang.Object r14 = r13.next()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.Object r15 = r14.getKey()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            boolean r15 = r11.contains(r15)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r15 != 0) goto L_0x0104
            com.google.android.gms.internal.zzl r15 = new com.google.android.gms.internal.zzl     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.Object r16 = r14.getKey()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r8 = r16
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.Object r14 = r14.getValue()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r15.<init>(r8, r14)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r12.add(r15)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
        L_0x0104:
            goto L_0x00d8
        L_0x0105:
            com.google.android.gms.internal.zzp r8 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r15 = 304(0x130, float:4.26E-43)
            byte[] r0 = r0.zza     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r17 = 1
            long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            long r18 = r13 - r3
            r14 = r8
            r16 = r0
            r20 = r12
            r14.<init>(r15, r16, r17, r18, r20)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            return r8
        L_0x011e:
            java.io.InputStream r0 = r10.zzd()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            if (r0 == 0) goto L_0x012f
            int r8 = r10.zzc()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            byte[] r0 = r1.zza(r0, r8)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r8 = r0
            goto L_0x0132
        L_0x012f:
            byte[] r0 = new byte[r9]     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0197 }
            r8 = r0
        L_0x0132:
            long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            long r13 = r13 - r3
            boolean r0 = com.google.android.gms.internal.zzaj.zza     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            if (r0 != 0) goto L_0x0142
            r15 = 3000(0xbb8, double:1.482E-320)
            int r0 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r0 <= 0) goto L_0x0174
        L_0x0142:
            java.lang.String r0 = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]"
            r11 = 5
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r11[r9] = r2     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r11[r6] = r13     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            if (r8 == 0) goto L_0x0157
            int r13 = r8.length     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            goto L_0x0159
        L_0x0157:
            java.lang.String r13 = "null"
        L_0x0159:
            r11[r7] = r13     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r13 = 3
            java.lang.Integer r14 = java.lang.Integer.valueOf(r12)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r11[r13] = r14     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r13 = 4
            com.google.android.gms.internal.zzab r14 = r22.zzj()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            int r14 = r14.zzb()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r11[r13] = r14     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            com.google.android.gms.internal.zzaf.zzb(r0, r11)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
        L_0x0174:
            r0 = 200(0xc8, float:2.8E-43)
            if (r12 < r0) goto L_0x018c
            r0 = 299(0x12b, float:4.19E-43)
            if (r12 > r0) goto L_0x018c
            com.google.android.gms.internal.zzp r0 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r14 = 0
            long r15 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            long r15 = r15 - r3
            r11 = r0
            r13 = r8
            r17 = r5
            r11.<init>(r12, r13, r14, r15, r17)     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            return r0
        L_0x018c:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            r0.<init>()     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
            throw r0     // Catch:{ SocketTimeoutException -> 0x0234, MalformedURLException -> 0x0213, IOException -> 0x0192 }
        L_0x0192:
            r0 = move-exception
            r17 = r5
            r13 = r8
            goto L_0x01a0
        L_0x0197:
            r0 = move-exception
            r17 = r5
            goto L_0x019f
        L_0x019b:
            r0 = move-exception
            r17 = r5
            r10 = 0
        L_0x019f:
            r13 = 0
        L_0x01a0:
            if (r10 == 0) goto L_0x020d
            int r0 = r10.zza()
            java.lang.Object[] r5 = new java.lang.Object[r7]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)
            r5[r9] = r7
            java.lang.String r7 = r22.zzc()
            r5[r6] = r7
            java.lang.String r6 = "Unexpected response code %d for %s"
            com.google.android.gms.internal.zzaf.zzc(r6, r5)
            if (r13 == 0) goto L_0x0201
            com.google.android.gms.internal.zzp r5 = new com.google.android.gms.internal.zzp
            r14 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()
            long r15 = r6 - r3
            r11 = r5
            r12 = r0
            r11.<init>(r12, r13, r14, r15, r17)
            r6 = 401(0x191, float:5.62E-43)
            if (r0 == r6) goto L_0x01f5
            r6 = 403(0x193, float:5.65E-43)
            if (r0 != r6) goto L_0x01d2
            goto L_0x01f5
        L_0x01d2:
            r2 = 400(0x190, float:5.6E-43)
            if (r0 < r2) goto L_0x01e1
            r2 = 499(0x1f3, float:6.99E-43)
            if (r0 <= r2) goto L_0x01db
            goto L_0x01e1
        L_0x01db:
            com.google.android.gms.internal.zzg r0 = new com.google.android.gms.internal.zzg
            r0.<init>(r5)
            throw r0
        L_0x01e1:
            r2 = 500(0x1f4, float:7.0E-43)
            if (r0 < r2) goto L_0x01ef
            r2 = 599(0x257, float:8.4E-43)
            if (r0 > r2) goto L_0x01ef
            com.google.android.gms.internal.zzac r0 = new com.google.android.gms.internal.zzac
            r0.<init>(r5)
            throw r0
        L_0x01ef:
            com.google.android.gms.internal.zzac r0 = new com.google.android.gms.internal.zzac
            r0.<init>(r5)
            throw r0
        L_0x01f5:
            com.google.android.gms.internal.zza r0 = new com.google.android.gms.internal.zza
            r0.<init>(r5)
            java.lang.String r5 = "auth"
            zza(r5, r2, r0)
            goto L_0x0008
        L_0x0201:
            com.google.android.gms.internal.zzo r0 = new com.google.android.gms.internal.zzo
            r0.<init>()
            java.lang.String r5 = "network"
            zza(r5, r2, r0)
            goto L_0x0008
        L_0x020d:
            com.google.android.gms.internal.zzq r2 = new com.google.android.gms.internal.zzq
            r2.<init>(r0)
            throw r2
        L_0x0213:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Bad URL "
            java.lang.String r2 = r22.zzc()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r5 = r2.length()
            if (r5 == 0) goto L_0x022b
            java.lang.String r2 = r4.concat(r2)
            goto L_0x0230
        L_0x022b:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r4)
        L_0x0230:
            r3.<init>(r2, r0)
            throw r3
        L_0x0234:
            r0 = move-exception
            com.google.android.gms.internal.zzad r0 = new com.google.android.gms.internal.zzad
            r0.<init>()
            java.lang.String r5 = "socket"
            zza(r5, r2, r0)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaj.zza(com.google.android.gms.internal.zzr):com.google.android.gms.internal.zzp");
    }

    private final byte[] zza(InputStream inputStream, int i) throws IOException, zzac {
        zzau zzau = new zzau(this.zzd, i);
        if (inputStream != null) {
            try {
                byte[] zza2 = this.zzd.zza(1024);
                while (true) {
                    int read = inputStream.read(zza2);
                    if (read == -1) {
                        break;
                    }
                    zzau.write(zza2, 0, read);
                }
                byte[] byteArray = zzau.toByteArray();
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zzaf.zza("Error occurred when closing InputStream", new Object[0]);
                }
                this.zzd.zza(zza2);
                zzau.close();
                return byteArray;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        zzaf.zza("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.zzd.zza((byte[]) null);
                zzau.close();
                throw th;
            }
        } else {
            throw new zzac();
        }
    }
}
