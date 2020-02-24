package com.google.android.gms.internal;

import java.lang.reflect.Field;

/* compiled from: RawMessageInfo */
final class zzgqi {
    private final zzgqj zza;
    private int zzaa;
    private int zzab;
    private Field zzac;
    private zzgol<Class<?>> zzad = zzgol.zza();
    private zzgol<zzgop<?>> zzae = zzgol.zza();
    private zzgol<Object> zzaf = zzgol.zza();
    private final Object[] zzb;
    private Class<?> zzc;
    /* access modifiers changed from: private */
    public final int zzd;
    /* access modifiers changed from: private */
    public final int zze;
    private final int zzf;
    private final int zzg;
    /* access modifiers changed from: private */
    public final int zzh;
    /* access modifiers changed from: private */
    public final int zzi;
    /* access modifiers changed from: private */
    public final int zzj;
    /* access modifiers changed from: private */
    public final int zzk;
    /* access modifiers changed from: private */
    public final int zzl;
    /* access modifiers changed from: private */
    public final int zzm;
    /* access modifiers changed from: private */
    public final int[] zzn;
    private int zzo;
    private int zzp;
    private int zzq = Integer.MAX_VALUE;
    private int zzr = Integer.MIN_VALUE;
    private int zzs = 0;
    private int zzt = 0;
    private int zzu = 0;
    private int zzv = 0;
    private int zzw = 0;
    private int zzx;
    private int zzy;
    private int zzz;

    zzgqi(Class<?> cls, String str, Object[] objArr) {
        this.zzc = cls;
        this.zza = new zzgqj(str);
        this.zzb = objArr;
        this.zzd = this.zza.zzb();
        this.zze = this.zza.zzb();
        int[] iArr = null;
        if (this.zze == 0) {
            this.zzf = 0;
            this.zzg = 0;
            this.zzh = 0;
            this.zzi = 0;
            this.zzj = 0;
            this.zzl = 0;
            this.zzk = 0;
            this.zzm = 0;
            this.zzn = null;
            return;
        }
        this.zzf = this.zza.zzb();
        this.zzg = this.zza.zzb();
        this.zzh = this.zza.zzb();
        this.zzi = this.zza.zzb();
        this.zzl = this.zza.zzb();
        this.zzk = this.zza.zzb();
        this.zzj = this.zza.zzb();
        this.zzm = this.zza.zzb();
        int zzb2 = this.zza.zzb();
        this.zzn = zzb2 != 0 ? new int[zzb2] : iArr;
        this.zzo = (this.zzf << 1) + this.zzg;
    }

    private final Object zzp() {
        Object[] objArr = this.zzb;
        int i = this.zzo;
        this.zzo = i + 1;
        return objArr[i];
    }

    /* access modifiers changed from: package-private */
    public final boolean zza() {
        boolean z = false;
        if (!this.zza.zza()) {
            return false;
        }
        this.zzx = this.zza.zzb();
        this.zzy = this.zza.zzb();
        this.zzz = this.zzy & 255;
        int i = this.zzx;
        if (i < this.zzq) {
            this.zzq = i;
        }
        int i2 = this.zzx;
        if (i2 > this.zzr) {
            this.zzr = i2;
        }
        if (this.zzz == zzgod.MAP.zza()) {
            this.zzs++;
        } else if (this.zzz >= zzgod.DOUBLE_LIST.zza() && this.zzz <= zzgod.GROUP_LIST.zza()) {
            this.zzt++;
        }
        this.zzw++;
        if (zzgqn.zza(this.zzq, this.zzx, this.zzw)) {
            this.zzv = this.zzx + 1;
            this.zzu = this.zzv - this.zzq;
        } else {
            this.zzu++;
        }
        if ((this.zzy & 1024) != 0) {
            int[] iArr = this.zzn;
            int i3 = this.zzp;
            this.zzp = i3 + 1;
            iArr[i3] = this.zzx;
        }
        if (zzd()) {
            this.zzaa = this.zza.zzb();
            if (this.zzz == zzgod.MESSAGE.zza() + 51 || this.zzz == zzgod.GROUP.zza() + 51) {
                zza(this.zzx, (Class) zzp());
            } else if (this.zzz == zzgod.ENUM.zza() + 51 && zzq()) {
                zza(this.zzx, (zzgop) zzp());
            }
        } else {
            this.zzac = zza(this.zzc, (String) zzp());
            if (zzh()) {
                this.zzab = this.zza.zzb();
            }
            if (this.zzz == zzgod.MESSAGE.zza() || this.zzz == zzgod.GROUP.zza()) {
                zza(this.zzx, this.zzac.getType());
            } else if (this.zzz == zzgod.MESSAGE_LIST.zza() || this.zzz == zzgod.GROUP_LIST.zza()) {
                zza(this.zzx, (Class) zzp());
            } else if (this.zzz == zzgod.ENUM.zza() || this.zzz == zzgod.ENUM_LIST.zza() || this.zzz == zzgod.ENUM_LIST_PACKED.zza()) {
                if (zzq()) {
                    zza(this.zzx, (zzgop) zzp());
                }
            } else if (this.zzz == zzgod.MAP.zza()) {
                int i4 = this.zzx;
                Object zzp2 = zzp();
                if (this.zzaf == zzgol.zza()) {
                    this.zzaf = new zzgol<>();
                }
                this.zzaf.zza(i4, zzp2);
                if ((this.zzy & 2048) != 0) {
                    z = true;
                }
                if (z) {
                    zza(this.zzx, (zzgop) zzp());
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        return this.zzx;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return this.zzz;
    }

    private final boolean zzq() {
        return (this.zzd & 1) == 1;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzd() {
        return this.zzz > zzgod.MAP.zza();
    }

    /* access modifiers changed from: package-private */
    public final Field zze() {
        int i = this.zzaa << 1;
        Object obj = this.zzb[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza2 = zza(this.zzc, (String) obj);
        this.zzb[i] = zza2;
        return zza2;
    }

    /* access modifiers changed from: package-private */
    public final Field zzf() {
        int i = (this.zzaa << 1) + 1;
        Object obj = this.zzb[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza2 = zza(this.zzc, (String) obj);
        this.zzb[i] = zza2;
        return zza2;
    }

    /* access modifiers changed from: package-private */
    public final Field zzg() {
        return this.zzac;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzh() {
        return zzq() && this.zzz <= zzgod.GROUP.zza();
    }

    /* access modifiers changed from: package-private */
    public final Field zzi() {
        int i = (this.zzf << 1) + (this.zzab / 32);
        Object obj = this.zzb[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza2 = zza(this.zzc, (String) obj);
        this.zzb[i] = zza2;
        return zza2;
    }

    /* access modifiers changed from: package-private */
    public final int zzj() {
        return this.zzab % 32;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzk() {
        return (this.zzy & 256) != 0;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzl() {
        return (this.zzy & 512) != 0;
    }

    private final void zzr() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        if (!this.zza.zza()) {
            int i10 = this.zze;
            if (i10 <= 0 || (i9 = this.zzw) == i10) {
                Object[] objArr = this.zzb;
                if (objArr == null || (i8 = this.zzo) == objArr.length) {
                    int[] iArr = this.zzn;
                    if (iArr != null && (i7 = this.zzp) != iArr.length) {
                        int length = iArr.length;
                        StringBuilder sb = new StringBuilder(74);
                        sb.append("checkInitializedPosition = ");
                        sb.append(i7);
                        sb.append(" is not at end (length = ");
                        sb.append(length);
                        throw new IllegalStateException(sb.toString());
                    } else if (this.zze > 0 && (i5 = this.zzh) != (i6 = this.zzq)) {
                        StringBuilder sb2 = new StringBuilder(54);
                        sb2.append("minFieldNumber is ");
                        sb2.append(i5);
                        sb2.append(" but expected ");
                        sb2.append(i6);
                        throw new IllegalStateException(sb2.toString());
                    } else if (this.zze <= 0 || (i3 = this.zzi) == (i4 = this.zzr)) {
                        int i11 = this.zzj;
                        int i12 = this.zzs;
                        if (i11 == i12) {
                            int i13 = this.zzm;
                            int i14 = this.zzt;
                            if (i13 == i14) {
                                int i15 = this.zzl;
                                if (i15 <= 0 || (i2 = this.zzu) == i15) {
                                    int i16 = this.zzk;
                                    if (i16 > 0 && (i = this.zzv) != i16) {
                                        StringBuilder sb3 = new StringBuilder(62);
                                        sb3.append("lookUpStartFieldNumber is ");
                                        sb3.append(i16);
                                        sb3.append(" but expected ");
                                        sb3.append(i);
                                        throw new IllegalStateException(sb3.toString());
                                    }
                                    return;
                                }
                                StringBuilder sb4 = new StringBuilder(52);
                                sb4.append("entriesCount is ");
                                sb4.append(i15);
                                sb4.append(" but expected ");
                                sb4.append(i2);
                                throw new IllegalStateException(sb4.toString());
                            }
                            StringBuilder sb5 = new StringBuilder(58);
                            sb5.append("repeatedFieldCount is ");
                            sb5.append(i13);
                            sb5.append(" but expected ");
                            sb5.append(i14);
                            throw new IllegalStateException(sb5.toString());
                        }
                        StringBuilder sb6 = new StringBuilder(53);
                        sb6.append("mapFieldCount is ");
                        sb6.append(i11);
                        sb6.append(" but expected ");
                        sb6.append(i12);
                        throw new IllegalStateException(sb6.toString());
                    } else {
                        StringBuilder sb7 = new StringBuilder(54);
                        sb7.append("maxFieldNumber is ");
                        sb7.append(i3);
                        sb7.append(" but expected ");
                        sb7.append(i4);
                        throw new IllegalStateException(sb7.toString());
                    }
                } else {
                    int length2 = objArr.length;
                    StringBuilder sb8 = new StringBuilder(65);
                    sb8.append("objectsPosition = ");
                    sb8.append(i8);
                    sb8.append(" is not at end (length = ");
                    sb8.append(length2);
                    throw new IllegalStateException(sb8.toString());
                }
            } else {
                StringBuilder sb9 = new StringBuilder(50);
                sb9.append("fieldCount is ");
                sb9.append(i10);
                sb9.append(" but expected ");
                sb9.append(i9);
                throw new IllegalStateException(sb9.toString());
            }
        } else {
            int zza2 = this.zza.zzb;
            int length3 = this.zza.zza.length();
            StringBuilder sb10 = new StringBuilder(66);
            sb10.append("decoder position = ");
            sb10.append(zza2);
            sb10.append(" is not at end (length = ");
            sb10.append(length3);
            throw new IllegalStateException(sb10.toString());
        }
    }

    private final void zza(int i, Class<?> cls) {
        if (this.zzad == zzgol.zza()) {
            this.zzad = new zzgol<>();
        }
        this.zzad.zza(i, cls);
    }

    /* access modifiers changed from: package-private */
    public final zzgol<Class<?>> zzm() {
        zzr();
        return this.zzad;
    }

    private final void zza(int i, zzgop<?> zzgop) {
        if (this.zzae == zzgol.zza()) {
            this.zzae = new zzgol<>();
        }
        this.zzae.zza(i, zzgop);
    }

    /* access modifiers changed from: package-private */
    public final zzgol<zzgop<?>> zzn() {
        zzr();
        return this.zzae;
    }

    /* access modifiers changed from: package-private */
    public final zzgol<Object> zzo() {
        zzr();
        return this.zzaf;
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
