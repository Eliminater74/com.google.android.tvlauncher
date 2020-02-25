package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.Collections;
import java.util.Map;

/* compiled from: Request */
public abstract class zzr<T> implements Comparable<zzr<T>> {
    /* access modifiers changed from: private */
    public final zzaf.zza zza;
    private final int zzb;
    private final String zzc;
    private final int zzd;
    private final Object zze;
    private zzy zzf;
    private Integer zzg;
    private zzv zzh;
    private boolean zzi;
    private boolean zzj;
    private boolean zzk;
    private boolean zzl;
    private zzab zzm;
    private zzc zzn;
    private zzt zzo;

    public zzr(int i, String str, zzy zzy) {
        Uri parse;
        String host;
        this.zza = zzaf.zza.zza ? new zzaf.zza() : null;
        this.zze = new Object();
        this.zzi = true;
        int i2 = 0;
        this.zzj = false;
        this.zzk = false;
        this.zzl = false;
        this.zzn = null;
        this.zzb = i;
        this.zzc = str;
        this.zzf = zzy;
        this.zzm = new zzh();
        if (!(TextUtils.isEmpty(str) || (parse = Uri.parse(str)) == null || (host = parse.getHost()) == null)) {
            i2 = host.hashCode();
        }
        this.zzd = i2;
    }

    /* access modifiers changed from: protected */
    public abstract zzx<T> zza(zzp zzp);

    /* access modifiers changed from: protected */
    public abstract void zza(T t);

    public final int zza() {
        return this.zzb;
    }

    public final int zzb() {
        return this.zzd;
    }

    public final void zza(String str) {
        if (zzaf.zza.zza) {
            this.zza.zza(str, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(String str) {
        zzv zzv = this.zzh;
        if (zzv != null) {
            zzv.zzb(this);
        }
        if (zzaf.zza.zza) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new zzs(this, str, id));
                return;
            }
            this.zza.zza(str, id);
            this.zza.zza(toString());
        }
    }

    public final zzr<?> zza(zzv zzv) {
        this.zzh = zzv;
        return this;
    }

    public final zzr<?> zza(int i) {
        this.zzg = Integer.valueOf(i);
        return this;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final zzr<?> zza(zzc zzc2) {
        this.zzn = zzc2;
        return this;
    }

    public final zzc zzd() {
        return this.zzn;
    }

    public final boolean zze() {
        synchronized (this.zze) {
        }
        return false;
    }

    public Map<String, String> zzf() throws zza {
        return Collections.emptyMap();
    }

    public byte[] zzg() throws zza {
        return null;
    }

    public final boolean zzh() {
        return this.zzi;
    }

    public final int zzi() {
        return this.zzm.zza();
    }

    public final zzab zzj() {
        return this.zzm;
    }

    public final void zzk() {
        synchronized (this.zze) {
            this.zzk = true;
        }
    }

    public final boolean zzl() {
        boolean z;
        synchronized (this.zze) {
            z = this.zzk;
        }
        return z;
    }

    public final void zza(zzae zzae) {
        zzy zzy;
        synchronized (this.zze) {
            zzy = this.zzf;
        }
        if (zzy != null) {
            zzy.zza(zzae);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzt zzt) {
        synchronized (this.zze) {
            this.zzo = zzt;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzx<?> zzx) {
        zzt zzt;
        synchronized (this.zze) {
            zzt = this.zzo;
        }
        if (zzt != null) {
            zzt.zza(this, zzx);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzm() {
        zzt zzt;
        synchronized (this.zze) {
            zzt = this.zzo;
        }
        if (zzt != null) {
            zzt.zza(this);
        }
    }

    public String toString() {
        String valueOf = String.valueOf(Integer.toHexString(this.zzd));
        String concat = valueOf.length() != 0 ? "0x".concat(valueOf) : new String("0x");
        String str = this.zzc;
        String valueOf2 = String.valueOf(zzu.NORMAL);
        String valueOf3 = String.valueOf(this.zzg);
        StringBuilder sb = new StringBuilder(String.valueOf("[ ] ").length() + 3 + String.valueOf(str).length() + String.valueOf(concat).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append("[ ] ");
        sb.append(str);
        sb.append(" ");
        sb.append(concat);
        sb.append(" ");
        sb.append(valueOf2);
        sb.append(" ");
        sb.append(valueOf3);
        return sb.toString();
    }

    public /* synthetic */ int compareTo(Object obj) {
        zzr zzr = (zzr) obj;
        zzu zzu = zzu.NORMAL;
        zzu zzu2 = zzu.NORMAL;
        if (zzu == zzu2) {
            return this.zzg.intValue() - zzr.zzg.intValue();
        }
        return zzu2.ordinal() - zzu.ordinal();
    }
}
