package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

/* compiled from: StringRequest */
public class zzav extends zzr<String> {
    private final Object zza = new Object();
    private zzz<String> zzb;

    public zzav(int i, String str, zzz<String> zzz, zzy zzy) {
        super(i, str, zzy);
        this.zzb = zzz;
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzc */
    public void zza(String str) {
        zzz<String> zzz;
        synchronized (this.zza) {
            zzz = this.zzb;
        }
        if (zzz != null) {
            zzz.zza(str);
        }
    }

    /* access modifiers changed from: protected */
    public final zzx<String> zza(zzp zzp) {
        String str;
        try {
            byte[] bArr = zzp.zzb;
            String str2 = "ISO-8859-1";
            String str3 = zzp.zzc.get("Content-Type");
            if (str3 != null) {
                String[] split = str3.split(";");
                int i = 1;
                while (true) {
                    if (i >= split.length) {
                        break;
                    }
                    String[] split2 = split[i].trim().split("=");
                    if (split2.length == 2 && split2[0].equals("charset")) {
                        str2 = split2[1];
                        break;
                    }
                    i++;
                }
            }
            str = new String(bArr, str2);
        } catch (UnsupportedEncodingException e) {
            str = new String(zzp.zzb);
        }
        return zzx.zza(str, zzap.zza(zzp));
    }
}
