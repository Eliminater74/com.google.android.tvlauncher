package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgsz;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: ClientAnalytics */
public final class zzgtd extends zzgsb<zzgtd> implements Cloneable {
    public long zza = 0;
    public long zzb = 0;
    public String zzc = "";
    public int zzd = 0;
    public int zze = 0;
    public byte[] zzf = zzgsk.zzh;
    public long zzg = 180000;
    public byte[] zzh = zzgsk.zzh;
    private long zzi = 0;
    private boolean zzj = false;
    private zzgte[] zzk = zzgte.zza();
    private byte[] zzl = zzgsk.zzh;
    private zzgsz.zza zzm = null;
    private String zzn = "";
    private String zzo = "";
    private zzgtb zzp = null;
    private String zzq = "";
    private zzgtc zzr = null;
    private String zzs = "";
    private int zzt = 0;
    private int[] zzu = zzgsk.zza;
    private long zzv = 0;
    private zzgtf zzw = null;
    private boolean zzx = false;

    public zzgtd() {
        this.zzay = null;
        this.zzaz = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtd clone() {
        try {
            zzgtd zzgtd = (zzgtd) super.clone();
            zzgte[] zzgteArr = this.zzk;
            if (zzgteArr != null && zzgteArr.length > 0) {
                zzgtd.zzk = new zzgte[zzgteArr.length];
                int i = 0;
                while (true) {
                    zzgte[] zzgteArr2 = this.zzk;
                    if (i >= zzgteArr2.length) {
                        break;
                    }
                    if (zzgteArr2[i] != null) {
                        zzgtd.zzk[i] = (zzgte) zzgteArr2[i].clone();
                    }
                    i++;
                }
            }
            zzgsz.zza zza2 = this.zzm;
            if (zza2 != null) {
                zzgtd.zzm = zza2;
            }
            zzgtb zzgtb = this.zzp;
            if (zzgtb != null) {
                zzgtd.zzp = (zzgtb) zzgtb.clone();
            }
            zzgtc zzgtc = this.zzr;
            if (zzgtc != null) {
                zzgtd.zzr = (zzgtc) zzgtc.clone();
            }
            int[] iArr = this.zzu;
            if (iArr != null && iArr.length > 0) {
                zzgtd.zzu = (int[]) iArr.clone();
            }
            zzgtf zzgtf = this.zzw;
            if (zzgtf != null) {
                zzgtd.zzw = (zzgtf) zzgtf.clone();
            }
            return zzgtd;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgtd)) {
            return false;
        }
        zzgtd zzgtd = (zzgtd) obj;
        if (this.zza != zzgtd.zza || this.zzb != zzgtd.zzb || this.zzi != zzgtd.zzi) {
            return false;
        }
        String str = this.zzc;
        if (str == null) {
            if (zzgtd.zzc != null) {
                return false;
            }
        } else if (!str.equals(zzgtd.zzc)) {
            return false;
        }
        if (this.zzd != zzgtd.zzd || this.zze != zzgtd.zze || this.zzj != zzgtd.zzj || !zzgsf.zza(this.zzk, zzgtd.zzk) || !Arrays.equals(this.zzl, zzgtd.zzl)) {
            return false;
        }
        zzgsz.zza zza2 = this.zzm;
        if (zza2 == null) {
            if (zzgtd.zzm != null) {
                return false;
            }
        } else if (!zza2.equals(zzgtd.zzm)) {
            return false;
        }
        if (!Arrays.equals(this.zzf, zzgtd.zzf)) {
            return false;
        }
        String str2 = this.zzn;
        if (str2 == null) {
            if (zzgtd.zzn != null) {
                return false;
            }
        } else if (!str2.equals(zzgtd.zzn)) {
            return false;
        }
        String str3 = this.zzo;
        if (str3 == null) {
            if (zzgtd.zzo != null) {
                return false;
            }
        } else if (!str3.equals(zzgtd.zzo)) {
            return false;
        }
        zzgtb zzgtb = this.zzp;
        if (zzgtb == null) {
            if (zzgtd.zzp != null) {
                return false;
            }
        } else if (!zzgtb.equals(zzgtd.zzp)) {
            return false;
        }
        String str4 = this.zzq;
        if (str4 == null) {
            if (zzgtd.zzq != null) {
                return false;
            }
        } else if (!str4.equals(zzgtd.zzq)) {
            return false;
        }
        if (this.zzg != zzgtd.zzg) {
            return false;
        }
        zzgtc zzgtc = this.zzr;
        if (zzgtc == null) {
            if (zzgtd.zzr != null) {
                return false;
            }
        } else if (!zzgtc.equals(zzgtd.zzr)) {
            return false;
        }
        if (!Arrays.equals(this.zzh, zzgtd.zzh)) {
            return false;
        }
        String str5 = this.zzs;
        if (str5 == null) {
            if (zzgtd.zzs != null) {
                return false;
            }
        } else if (!str5.equals(zzgtd.zzs)) {
            return false;
        }
        if (this.zzt != zzgtd.zzt || !zzgsf.zza(this.zzu, zzgtd.zzu) || this.zzv != zzgtd.zzv) {
            return false;
        }
        zzgtf zzgtf = this.zzw;
        if (zzgtf == null) {
            if (zzgtd.zzw != null) {
                return false;
            }
        } else if (!zzgtf.equals(zzgtd.zzw)) {
            return false;
        }
        if (this.zzx != zzgtd.zzx) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgtd.zzay);
        }
        if (zzgtd.zzay == null || zzgtd.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long j = this.zza;
        long j2 = this.zzb;
        long j3 = this.zzi;
        int hashCode = (((((((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31;
        String str = this.zzc;
        int i = 0;
        int i2 = 1231;
        int hashCode2 = ((((((((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.zzd) * 31) + this.zze) * 31) + (this.zzj ? 1231 : 1237)) * 31) + zzgsf.zza(this.zzk)) * 31) + Arrays.hashCode(this.zzl);
        zzgsz.zza zza2 = this.zzm;
        int hashCode3 = ((((hashCode2 * 31) + (zza2 == null ? 0 : zza2.hashCode())) * 31) + Arrays.hashCode(this.zzf)) * 31;
        String str2 = this.zzn;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzo;
        int hashCode5 = hashCode4 + (str3 == null ? 0 : str3.hashCode());
        zzgtb zzgtb = this.zzp;
        int hashCode6 = ((hashCode5 * 31) + (zzgtb == null ? 0 : zzgtb.hashCode())) * 31;
        String str4 = this.zzq;
        int hashCode7 = str4 == null ? 0 : str4.hashCode();
        long j4 = this.zzg;
        int i3 = ((hashCode6 + hashCode7) * 31) + ((int) (j4 ^ (j4 >>> 32)));
        zzgtc zzgtc = this.zzr;
        int hashCode8 = ((((i3 * 31) + (zzgtc == null ? 0 : zzgtc.hashCode())) * 31) + Arrays.hashCode(this.zzh)) * 31;
        String str5 = this.zzs;
        int hashCode9 = str5 == null ? 0 : str5.hashCode();
        long j5 = this.zzv;
        int zza3 = ((((((hashCode8 + hashCode9) * 31) + this.zzt) * 31) + zzgsf.zza(this.zzu)) * 31) + ((int) (j5 ^ (j5 >>> 32)));
        zzgtf zzgtf = this.zzw;
        int hashCode10 = ((zza3 * 31) + (zzgtf == null ? 0 : zzgtf.hashCode())) * 31;
        if (!this.zzx) {
            i2 = 1237;
        }
        int i4 = (hashCode10 + i2) * 31;
        if (this.zzay != null && !this.zzay.zzb()) {
            i = this.zzay.hashCode();
        }
        return i4 + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        long j = this.zza;
        if (j != 0) {
            zzgrz.zzb(1, j);
        }
        String str = this.zzc;
        if (str != null && !str.equals("")) {
            zzgrz.zza(2, this.zzc);
        }
        zzgte[] zzgteArr = this.zzk;
        int i = 0;
        if (zzgteArr != null && zzgteArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzgte[] zzgteArr2 = this.zzk;
                if (i2 >= zzgteArr2.length) {
                    break;
                }
                zzgte zzgte = zzgteArr2[i2];
                if (zzgte != null) {
                    zzgrz.zza(3, zzgte);
                }
                i2++;
            }
        }
        if (!Arrays.equals(this.zzl, zzgsk.zzh)) {
            zzgrz.zza(4, this.zzl);
        }
        if (!Arrays.equals(this.zzf, zzgsk.zzh)) {
            zzgrz.zza(6, this.zzf);
        }
        zzgtb zzgtb = this.zzp;
        if (zzgtb != null) {
            zzgrz.zza(7, zzgtb);
        }
        String str2 = this.zzn;
        if (str2 != null && !str2.equals("")) {
            zzgrz.zza(8, this.zzn);
        }
        zzgsz.zza zza2 = this.zzm;
        if (zza2 != null) {
            zzgrz.zza(9, zza2);
        }
        boolean z = this.zzj;
        if (z) {
            zzgrz.zza(10, z);
        }
        int i3 = this.zzd;
        if (i3 != 0) {
            zzgrz.zza(11, i3);
        }
        int i4 = this.zze;
        if (i4 != 0) {
            zzgrz.zza(12, i4);
        }
        String str3 = this.zzo;
        if (str3 != null && !str3.equals("")) {
            zzgrz.zza(13, this.zzo);
        }
        String str4 = this.zzq;
        if (str4 != null && !str4.equals("")) {
            zzgrz.zza(14, this.zzq);
        }
        long j2 = this.zzg;
        if (j2 != 180000) {
            zzgrz.zzd(15, j2);
        }
        zzgtc zzgtc = this.zzr;
        if (zzgtc != null) {
            zzgrz.zza(16, zzgtc);
        }
        long j3 = this.zzb;
        if (j3 != 0) {
            zzgrz.zzb(17, j3);
        }
        if (!Arrays.equals(this.zzh, zzgsk.zzh)) {
            zzgrz.zza(18, this.zzh);
        }
        int i5 = this.zzt;
        if (i5 != 0) {
            zzgrz.zza(19, i5);
        }
        int[] iArr = this.zzu;
        if (iArr != null && iArr.length > 0) {
            while (true) {
                int[] iArr2 = this.zzu;
                if (i >= iArr2.length) {
                    break;
                }
                zzgrz.zza(20, iArr2[i]);
                i++;
            }
        }
        long j4 = this.zzi;
        if (j4 != 0) {
            zzgrz.zzb(21, j4);
        }
        long j5 = this.zzv;
        if (j5 != 0) {
            zzgrz.zzb(22, j5);
        }
        zzgtf zzgtf = this.zzw;
        if (zzgtf != null) {
            zzgrz.zza(23, zzgtf);
        }
        String str5 = this.zzs;
        if (str5 != null && !str5.equals("")) {
            zzgrz.zza(24, this.zzs);
        }
        boolean z2 = this.zzx;
        if (z2) {
            zzgrz.zza(25, z2);
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int[] iArr;
        int computeSerializedSize = super.computeSerializedSize();
        long j = this.zza;
        if (j != 0) {
            computeSerializedSize += zzgrz.zzf(1, j);
        }
        String str = this.zzc;
        if (str != null && !str.equals("")) {
            computeSerializedSize += zzgrz.zzb(2, this.zzc);
        }
        zzgte[] zzgteArr = this.zzk;
        int i = 0;
        if (zzgteArr != null && zzgteArr.length > 0) {
            int i2 = computeSerializedSize;
            int i3 = 0;
            while (true) {
                zzgte[] zzgteArr2 = this.zzk;
                if (i3 >= zzgteArr2.length) {
                    break;
                }
                zzgte zzgte = zzgteArr2[i3];
                if (zzgte != null) {
                    i2 += zzgrz.zzb(3, zzgte);
                }
                i3++;
            }
            computeSerializedSize = i2;
        }
        if (!Arrays.equals(this.zzl, zzgsk.zzh)) {
            computeSerializedSize += zzgrz.zzb(4, this.zzl);
        }
        if (!Arrays.equals(this.zzf, zzgsk.zzh)) {
            computeSerializedSize += zzgrz.zzb(6, this.zzf);
        }
        zzgtb zzgtb = this.zzp;
        if (zzgtb != null) {
            computeSerializedSize += zzgrz.zzb(7, zzgtb);
        }
        String str2 = this.zzn;
        if (str2 != null && !str2.equals("")) {
            computeSerializedSize += zzgrz.zzb(8, this.zzn);
        }
        zzgsz.zza zza2 = this.zzm;
        if (zza2 != null) {
            computeSerializedSize += zzgnp.zzc(9, zza2);
        }
        if (this.zzj) {
            computeSerializedSize += zzgrz.zzb(10) + 1;
        }
        int i4 = this.zzd;
        if (i4 != 0) {
            computeSerializedSize += zzgrz.zzb(11, i4);
        }
        int i5 = this.zze;
        if (i5 != 0) {
            computeSerializedSize += zzgrz.zzb(12, i5);
        }
        String str3 = this.zzo;
        if (str3 != null && !str3.equals("")) {
            computeSerializedSize += zzgrz.zzb(13, this.zzo);
        }
        String str4 = this.zzq;
        if (str4 != null && !str4.equals("")) {
            computeSerializedSize += zzgrz.zzb(14, this.zzq);
        }
        long j2 = this.zzg;
        if (j2 != 180000) {
            computeSerializedSize += zzgrz.zzg(15, j2);
        }
        zzgtc zzgtc = this.zzr;
        if (zzgtc != null) {
            computeSerializedSize += zzgrz.zzb(16, zzgtc);
        }
        long j3 = this.zzb;
        if (j3 != 0) {
            computeSerializedSize += zzgrz.zzf(17, j3);
        }
        if (!Arrays.equals(this.zzh, zzgsk.zzh)) {
            computeSerializedSize += zzgrz.zzb(18, this.zzh);
        }
        int i6 = this.zzt;
        if (i6 != 0) {
            computeSerializedSize += zzgrz.zzb(19, i6);
        }
        int[] iArr2 = this.zzu;
        if (iArr2 != null && iArr2.length > 0) {
            int i7 = 0;
            while (true) {
                iArr = this.zzu;
                if (i >= iArr.length) {
                    break;
                }
                i7 += zzgrz.zza(iArr[i]);
                i++;
            }
            computeSerializedSize = computeSerializedSize + i7 + (iArr.length * 2);
        }
        long j4 = this.zzi;
        if (j4 != 0) {
            computeSerializedSize += zzgrz.zzf(21, j4);
        }
        long j5 = this.zzv;
        if (j5 != 0) {
            computeSerializedSize += zzgrz.zzf(22, j5);
        }
        zzgtf zzgtf = this.zzw;
        if (zzgtf != null) {
            computeSerializedSize += zzgrz.zzb(23, zzgtf);
        }
        String str5 = this.zzs;
        if (str5 != null && !str5.equals("")) {
            computeSerializedSize += zzgrz.zzb(24, this.zzs);
        }
        if (this.zzx) {
            return computeSerializedSize + zzgrz.zzb(25) + 1;
        }
        return computeSerializedSize;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtd mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            switch (zza2) {
                case 0:
                    return this;
                case 8:
                    this.zza = zzgry.zzb();
                    break;
                case 18:
                    this.zzc = zzgry.zze();
                    break;
                case 26:
                    int zza3 = zzgsk.zza(zzgry, 26);
                    zzgte[] zzgteArr = this.zzk;
                    int length = zzgteArr == null ? 0 : zzgteArr.length;
                    zzgte[] zzgteArr2 = new zzgte[(zza3 + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzk, 0, zzgteArr2, 0, length);
                    }
                    while (length < zzgteArr2.length - 1) {
                        zzgteArr2[length] = new zzgte();
                        zzgry.zza(zzgteArr2[length]);
                        zzgry.zza();
                        length++;
                    }
                    zzgteArr2[length] = new zzgte();
                    zzgry.zza(zzgteArr2[length]);
                    this.zzk = zzgteArr2;
                    break;
                case 34:
                    this.zzl = zzgry.zzf();
                    break;
                case 50:
                    this.zzf = zzgry.zzf();
                    break;
                case 58:
                    if (this.zzp == null) {
                        this.zzp = new zzgtb();
                    }
                    zzgry.zza(this.zzp);
                    break;
                case 66:
                    this.zzn = zzgry.zze();
                    break;
                case 74:
                    this.zzm = (zzgsz.zza) zzgry.zza(zzgsz.zza.zzc());
                    break;
                case 80:
                    this.zzj = zzgry.zzd();
                    break;
                case 88:
                    this.zzd = zzgry.zzc();
                    break;
                case 96:
                    this.zze = zzgry.zzc();
                    break;
                case 106:
                    this.zzo = zzgry.zze();
                    break;
                case 114:
                    this.zzq = zzgry.zze();
                    break;
                case 120:
                    this.zzg = zzgry.zzg();
                    break;
                case 130:
                    if (this.zzr == null) {
                        this.zzr = new zzgtc();
                    }
                    zzgry.zza(this.zzr);
                    break;
                case ClientAnalytics.LogRequest.LogSource.SAMPLE_SHM_VALUE /*136*/:
                    this.zzb = zzgry.zzb();
                    break;
                case ClientAnalytics.LogRequest.LogSource.ON_THE_GO_COUNTERS_VALUE /*146*/:
                    this.zzh = zzgry.zzf();
                    break;
                case 152:
                    try {
                        int zzc2 = zzgry.zzc();
                        if (!(zzc2 == 0 || zzc2 == 1)) {
                            if (zzc2 != 2) {
                                StringBuilder sb = new StringBuilder(45);
                                sb.append(zzc2);
                                sb.append(" is not a valid enum InternalEvent");
                                throw new IllegalArgumentException(sb.toString());
                            }
                        }
                        this.zzt = zzc2;
                        break;
                    } catch (IllegalArgumentException e) {
                        zzgry.zze(zzgry.zzn());
                        zza(zzgry, zza2);
                        break;
                    }
                case ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE /*160*/:
                    int zza4 = zzgsk.zza(zzgry, ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE);
                    int[] iArr = this.zzu;
                    int length2 = iArr == null ? 0 : iArr.length;
                    int[] iArr2 = new int[(zza4 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzu, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length - 1) {
                        iArr2[length2] = zzgry.zzc();
                        zzgry.zza();
                        length2++;
                    }
                    iArr2[length2] = zzgry.zzc();
                    this.zzu = iArr2;
                    break;
                case ClientAnalytics.LogRequest.LogSource.JAM_IMPRESSIONS_VALUE /*162*/:
                    int zzc3 = zzgry.zzc(zzgry.zzh());
                    int zzn2 = zzgry.zzn();
                    int i = 0;
                    while (zzgry.zzl() > 0) {
                        zzgry.zzc();
                        i++;
                    }
                    zzgry.zze(zzn2);
                    int[] iArr3 = this.zzu;
                    int length3 = iArr3 == null ? 0 : iArr3.length;
                    int[] iArr4 = new int[(i + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzu, 0, iArr4, 0, length3);
                    }
                    while (length3 < iArr4.length) {
                        iArr4[length3] = zzgry.zzc();
                        length3++;
                    }
                    this.zzu = iArr4;
                    zzgry.zzd(zzc3);
                    break;
                case ClientAnalytics.LogRequest.LogSource.SLIDES_ANDROID_PRIMES_VALUE /*168*/:
                    this.zzi = zzgry.zzb();
                    break;
                case ClientAnalytics.LogRequest.LogSource.ANDROID_DIALER_VALUE /*176*/:
                    this.zzv = zzgry.zzb();
                    break;
                case ClientAnalytics.LogRequest.LogSource.FUSE_VALUE /*186*/:
                    if (this.zzw == null) {
                        this.zzw = new zzgtf();
                    }
                    zzgry.zza(this.zzw);
                    break;
                case ClientAnalytics.LogRequest.LogSource.MYFIBER_VALUE /*194*/:
                    this.zzs = zzgry.zze();
                    break;
                case 200:
                    this.zzx = zzgry.zzd();
                    break;
                default:
                    if (super.zza(zzgry, zza2)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }
}
