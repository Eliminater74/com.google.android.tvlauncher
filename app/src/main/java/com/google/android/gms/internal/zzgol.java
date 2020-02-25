package com.google.android.gms.internal;

/* compiled from: Int2ObjectHashMap */
final class zzgol<V> {
    private static final Object zza = new Object();
    private static final zzgol<Object> zzb = new zzgol<>(true);
    private final float zzd;
    private int zzc;
    private int[] zze;
    private V[] zzf;
    private int zzg;
    private int zzh;

    public zzgol() {
        this(8, 0.5f);
    }

    private zzgol(int i, float f) {
        this.zzd = 0.5f;
        int numberOfLeadingZeros = 1 << (32 - Integer.numberOfLeadingZeros(7));
        this.zzh = numberOfLeadingZeros - 1;
        this.zze = new int[numberOfLeadingZeros];
        this.zzf = new Object[numberOfLeadingZeros];
        this.zzc = zzc(numberOfLeadingZeros);
    }

    private zzgol(boolean z) {
        this.zzd = 0.5f;
        this.zze = null;
        this.zzf = null;
    }

    static <V> zzgol<V> zza() {
        return zzb;
    }

    private static <T> T zza(T t) {
        if (t == zza) {
            return null;
        }
        return t;
    }

    private static <T> T zzb(T t) {
        return t == null ? zza : t;
    }

    public final V zza(int i) {
        if (this.zze == null) {
            return null;
        }
        int i2 = this.zzh & i;
        int i3 = i2;
        while (true) {
            if (this.zzf[i3] != null) {
                if (i == this.zze[i3]) {
                    break;
                }
                i3 = zzb(i3);
                if (i3 == i2) {
                    i3 = -1;
                    break;
                }
            } else {
                i3 = -1;
                break;
            }
        }
        if (i3 == -1) {
            return null;
        }
        return zza(this.zzf[i3]);
    }

    public final V zza(int i, V v) {
        V[] vArr;
        if (this.zze != null) {
            int i2 = this.zzh & i;
            int i3 = i2;
            do {
                V[] vArr2 = this.zzf;
                if (vArr2[i3] == null) {
                    this.zze[i3] = i;
                    vArr2[i3] = zzb(v);
                    this.zzg++;
                    int i4 = this.zzg;
                    if (i4 <= this.zzc) {
                        return null;
                    }
                    int[] iArr = this.zze;
                    if (iArr.length != Integer.MAX_VALUE) {
                        int length = iArr.length << 1;
                        V[] vArr3 = this.zzf;
                        this.zze = new int[length];
                        this.zzf = new Object[length];
                        this.zzc = zzc(length);
                        this.zzh = length - 1;
                        for (int i5 = 0; i5 < vArr3.length; i5++) {
                            V v2 = vArr3[i5];
                            if (v2 != null) {
                                int i6 = iArr[i5];
                                int i7 = this.zzh & i6;
                                while (true) {
                                    vArr = this.zzf;
                                    if (vArr[i7] == null) {
                                        break;
                                    }
                                    i7 = zzb(i7);
                                }
                                this.zze[i7] = i6;
                                vArr[i7] = v2;
                            }
                        }
                        return null;
                    }
                    StringBuilder sb = new StringBuilder(40);
                    sb.append("Max capacity reached at size=");
                    sb.append(i4);
                    throw new IllegalStateException(sb.toString());
                } else if (this.zze[i3] == i) {
                    V v3 = vArr2[i3];
                    vArr2[i3] = zzb(v);
                    return zza(v3);
                } else {
                    i3 = zzb(i3);
                }
            } while (i3 != i2);
            throw new IllegalStateException("Unable to insert");
        }
        throw new IllegalStateException("Trying to modify an immutable map.");
    }

    public final boolean zzb() {
        return this.zzg == 0;
    }

    private final int zzb(int i) {
        return (i + 1) & this.zzh;
    }

    private final int zzc(int i) {
        return Math.min(i - 1, (int) (((float) i) * this.zzd));
    }

    public final String toString() {
        if (zzb()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.zzg * 4);
        sb.append('{');
        int i = 0;
        boolean z = true;
        while (true) {
            V[] vArr = this.zzf;
            if (i < vArr.length) {
                V v = vArr[i];
                if (v != null) {
                    if (!z) {
                        sb.append(", ");
                    }
                    sb.append(Integer.toString(this.zze[i]));
                    sb.append('=');
                    sb.append(v == this ? "(this Map)" : zza(v));
                    z = false;
                }
                i++;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }
}
