package com.google.android.gms.internal;

/* compiled from: LazyFieldLite */
public class zzgpa {
    private static final zzgnv zza = zzgnv.zza();
    private zzgnb zzb;
    private volatile zzgpt zzc;
    private volatile zzgnb zzd;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgpa)) {
            return false;
        }
        zzgpa zzgpa = (zzgpa) obj;
        zzgpt zzgpt = this.zzc;
        zzgpt zzgpt2 = zzgpa.zzc;
        if (zzgpt == null && zzgpt2 == null) {
            return zzc().equals(zzgpa.zzc());
        }
        if (zzgpt != null && zzgpt2 != null) {
            return zzgpt.equals(zzgpt2);
        }
        if (zzgpt != null) {
            return zzgpt.equals(zzgpa.zzb(zzgpt.zzr()));
        }
        return zzb(zzgpt2.zzr()).equals(zzgpt2);
    }

    public int hashCode() {
        return 1;
    }

    private final zzgpt zzb(zzgpt zzgpt) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzgpt;
                        this.zzd = zzgnb.zza;
                    } catch (zzgot e) {
                        this.zzc = zzgpt;
                        this.zzd = zzgnb.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public final zzgpt zza(zzgpt zzgpt) {
        zzgpt zzgpt2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzgpt;
        return zzgpt2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zza();
        }
        if (this.zzc != null) {
            return this.zzc.zza();
        }
        return 0;
    }

    public final zzgnb zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                zzgnb zzgnb = this.zzd;
                return zzgnb;
            }
            if (this.zzc == null) {
                this.zzd = zzgnb.zza;
            } else {
                this.zzd = this.zzc.zzj();
            }
            zzgnb zzgnb2 = this.zzd;
            return zzgnb2;
        }
    }
}
