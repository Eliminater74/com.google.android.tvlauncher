package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: FieldData */
final class zzgse implements Cloneable {
    private zzgsc<?, ?> zza;
    private Object zzb;
    private List<zzgsj> zzc;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.internal.zzgsc<?, T>, com.google.android.gms.internal.zzgsc<?, ?>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    <T> zzgse(com.google.android.gms.internal.zzgsc<?, T> r1, T r2) {
        /*
            r0 = this;
            r0.<init>()
            r0.zza = r1
            r0.zzb = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgse.<init>(com.google.android.gms.internal.zzgsc, java.lang.Object):void");
    }

    zzgse() {
        this.zzc = new ArrayList();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgsj zzgsj) {
        this.zzc.add(zzgsj);
    }

    /* access modifiers changed from: package-private */
    public final <T> T zza(zzgsc zzgsc) {
        if (this.zzb == null) {
            this.zza = zzgsc;
            this.zzb = zzgsc.zza(this.zzc);
            this.zzc = null;
        } else if (!this.zza.equals(zzgsc)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.zzb;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.internal.zzgsc<?, T>, com.google.android.gms.internal.zzgsc<?, ?>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> void zza(com.google.android.gms.internal.zzgsc<?, T> r1, T r2) {
        /*
            r0 = this;
            r0.zza = r1
            r0.zzb = r2
            r1 = 0
            r0.zzc = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgse.zza(com.google.android.gms.internal.zzgsc, java.lang.Object):void");
    }

    /* access modifiers changed from: package-private */
    public final int zza() {
        Object obj = this.zzb;
        if (obj != null) {
            zzgsc<?, ?> zzgsc = this.zza;
            if (!zzgsc.zzc) {
                return zzgsc.zza(obj);
            }
            int length = Array.getLength(obj);
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (Array.get(obj, i2) != null) {
                    i += zzgsc.zza(Array.get(obj, i2));
                }
            }
            return i;
        }
        int i3 = 0;
        for (zzgsj next : this.zzc) {
            i3 += zzgrz.zzd(next.zza) + 0 + next.zzb.length;
        }
        return i3;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgrz zzgrz) throws IOException {
        Object obj = this.zzb;
        if (obj != null) {
            zzgsc<?, ?> zzgsc = this.zza;
            if (zzgsc.zzc) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzgsc.zza(obj2, zzgrz);
                    }
                }
                return;
            }
            zzgsc.zza(obj, zzgrz);
            return;
        }
        for (zzgsj next : this.zzc) {
            zzgrz.zzc(next.zza);
            zzgrz.zzd(next.zzb);
        }
    }

    public final boolean equals(Object obj) {
        List<zzgsj> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgse)) {
            return false;
        }
        zzgse zzgse = (zzgse) obj;
        if (this.zzb == null || zzgse.zzb == null) {
            List<zzgsj> list2 = this.zzc;
            if (list2 != null && (list = zzgse.zzc) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(zzb(), zzgse.zzb());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            zzgsc<?, ?> zzgsc = this.zza;
            if (zzgsc != zzgse.zza) {
                return false;
            }
            if (!zzgsc.zza.isArray()) {
                return this.zzb.equals(zzgse.zzb);
            }
            Object obj2 = this.zzb;
            if (obj2 instanceof byte[]) {
                return Arrays.equals((byte[]) obj2, (byte[]) zzgse.zzb);
            }
            if (obj2 instanceof int[]) {
                return Arrays.equals((int[]) obj2, (int[]) zzgse.zzb);
            }
            if (obj2 instanceof long[]) {
                return Arrays.equals((long[]) obj2, (long[]) zzgse.zzb);
            }
            if (obj2 instanceof float[]) {
                return Arrays.equals((float[]) obj2, (float[]) zzgse.zzb);
            }
            if (obj2 instanceof double[]) {
                return Arrays.equals((double[]) obj2, (double[]) zzgse.zzb);
            }
            if (obj2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj2, (boolean[]) zzgse.zzb);
            }
            return Arrays.deepEquals((Object[]) obj2, (Object[]) zzgse.zzb);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(zzb()) + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private final byte[] zzb() throws IOException {
        byte[] bArr = new byte[zza()];
        zza(zzgrz.zza(bArr));
        return bArr;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzc */
    public final zzgse clone() {
        zzgse zzgse = new zzgse();
        try {
            zzgse.zza = this.zza;
            if (this.zzc == null) {
                zzgse.zzc = null;
            } else {
                zzgse.zzc.addAll(this.zzc);
            }
            if (this.zzb != null) {
                if (this.zzb instanceof zzgsh) {
                    zzgse.zzb = (zzgsh) ((zzgsh) this.zzb).clone();
                } else if (this.zzb instanceof byte[]) {
                    zzgse.zzb = ((byte[]) this.zzb).clone();
                } else {
                    int i = 0;
                    if (this.zzb instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.zzb;
                        byte[][] bArr2 = new byte[bArr.length][];
                        zzgse.zzb = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.zzb instanceof boolean[]) {
                        zzgse.zzb = ((boolean[]) this.zzb).clone();
                    } else if (this.zzb instanceof int[]) {
                        zzgse.zzb = ((int[]) this.zzb).clone();
                    } else if (this.zzb instanceof long[]) {
                        zzgse.zzb = ((long[]) this.zzb).clone();
                    } else if (this.zzb instanceof float[]) {
                        zzgse.zzb = ((float[]) this.zzb).clone();
                    } else if (this.zzb instanceof double[]) {
                        zzgse.zzb = ((double[]) this.zzb).clone();
                    } else if (this.zzb instanceof zzgsh[]) {
                        zzgsh[] zzgshArr = (zzgsh[]) this.zzb;
                        zzgsh[] zzgshArr2 = new zzgsh[zzgshArr.length];
                        zzgse.zzb = zzgshArr2;
                        while (i < zzgshArr.length) {
                            zzgshArr2[i] = (zzgsh) zzgshArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return zzgse;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
