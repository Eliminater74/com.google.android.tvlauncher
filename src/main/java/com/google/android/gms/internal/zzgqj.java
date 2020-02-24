package com.google.android.gms.internal;

/* compiled from: RawMessageInfo */
final class zzgqj {
    /* access modifiers changed from: private */
    public final String zza;
    /* access modifiers changed from: private */
    public int zzb = 0;

    zzgqj(String str) {
        this.zza = str;
    }

    /* access modifiers changed from: package-private */
    public final boolean zza() {
        return this.zzb < this.zza.length();
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        String str = this.zza;
        int i = this.zzb;
        this.zzb = i + 1;
        char charAt = str.charAt(i);
        if (charAt < 55296) {
            return charAt;
        }
        char c = charAt & 8191;
        int i2 = 13;
        while (true) {
            String str2 = this.zza;
            int i3 = this.zzb;
            this.zzb = i3 + 1;
            char charAt2 = str2.charAt(i3);
            if (charAt2 < 55296) {
                return c | (charAt2 << i2);
            }
            c |= (charAt2 & 8191) << i2;
            i2 += 13;
        }
    }
}
