package com.google.android.gms.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: CodedOutputStreamWriter */
final class zzgnr implements zzgrx {
    private final zzgnp zza;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T
     arg types: [com.google.android.gms.internal.zzgnp, java.lang.String]
     candidates:
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T */
    private zzgnr(zzgnp zzgnp) {
        this.zza = (zzgnp) zzgon.zza((Object) zzgnp, "output");
        this.zza.zza = this;
    }

    public static zzgnr zza(zzgnp zzgnp) {
        if (zzgnp.zza != null) {
            return zzgnp.zza;
        }
        return new zzgnr(zzgnp);
    }

    public final int zza() {
        return zzgoj.zzg.zzk;
    }

    public final void zza(int i, int i2) {
        try {
            this.zza.zze(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, long j) {
        try {
            this.zza.zza(i, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(int i, long j) {
        try {
            this.zza.zzc(i, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, float f) {
        try {
            this.zza.zza(i, f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, double d) {
        try {
            this.zza.zza(i, d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(int i, int i2) {
        try {
            this.zza.zzb(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzc(int i, long j) {
        try {
            this.zza.zza(i, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzc(int i, int i2) {
        try {
            this.zza.zzb(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzd(int i, long j) {
        try {
            this.zza.zzc(i, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzd(int i, int i2) {
        try {
            this.zza.zze(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, boolean z) {
        try {
            this.zza.zza(i, z);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, String str) {
        try {
            this.zza.zza(i, str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, zzgnb zzgnb) {
        try {
            this.zza.zza(i, zzgnb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zze(int i, int i2) {
        try {
            this.zza.zzc(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzf(int i, int i2) {
        try {
            this.zza.zzd(i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zze(int i, long j) {
        try {
            this.zza.zzb(i, j);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, Object obj) {
        try {
            this.zza.zza(i, (zzgpt) obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(int i, Object obj) {
        try {
            this.zza.zze(i, (zzgpt) obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i) {
        try {
            this.zza.zza(i, 3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(int i) {
        try {
            this.zza.zza(i, 4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzc(int i, Object obj) {
        try {
            if (obj instanceof zzgnb) {
                this.zza.zzb(i, (zzgnb) obj);
            } else {
                this.zza.zzb(i, (zzgpt) obj);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzf(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzb(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzi(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzd(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zze(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzd(list.get(i4).longValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).longValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2).longValue());
                i2++;
            }
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zze(list.get(i4).longValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).longValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2).longValue());
                i2++;
            }
        }
    }

    public final void zze(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzg(list.get(i4).longValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzc(list.get(i2).longValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzc(i, list.get(i2).longValue());
                i2++;
            }
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzb(list.get(i4).floatValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).floatValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2).floatValue());
                i2++;
            }
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzb(list.get(i4).doubleValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).doubleValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2).doubleValue());
                i2++;
            }
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzk(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzb(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzb(list.get(i4).booleanValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zza(list.get(i2).booleanValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2).booleanValue());
                i2++;
            }
        }
    }

    public final void zza(int i, List<String> list) {
        try {
            int i2 = 0;
            if (list instanceof zzgpc) {
                zzgpc zzgpc = (zzgpc) list;
                while (i2 < list.size()) {
                    Object zzb = zzgpc.zzb(i2);
                    if (zzb instanceof String) {
                        this.zza.zza(i, (String) zzb);
                    } else {
                        this.zza.zza(i, (zzgnb) zzb);
                    }
                    i2++;
                }
                return;
            }
            while (i2 < list.size()) {
                this.zza.zza(i, list.get(i2));
                i2++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(int i, List<zzgnb> list) {
        int i2 = 0;
        while (i2 < list.size()) {
            try {
                this.zza.zza(i, list.get(i2));
                i2++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzg(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzb(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzc(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzj(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzd(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zze(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzh(list.get(i4).longValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzc(list.get(i2).longValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzc(i, list.get(i2).longValue());
                i2++;
            }
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzh(list.get(i4).intValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzc(list.get(i2).intValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzd(i, list.get(i2).intValue());
                i2++;
            }
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            try {
                this.zza.zza(i, 2);
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i3 += zzgnp.zzf(list.get(i4).longValue());
                }
                this.zza.zzb(i3);
                while (i2 < list.size()) {
                    this.zza.zzb(list.get(i2).longValue());
                    i2++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (i2 < list.size()) {
                this.zza.zzb(i, list.get(i2).longValue());
                i2++;
            }
        }
    }

    public final void zzc(int i, List<?> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2));
        }
    }

    public final void zzd(int i, List<?> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2));
        }
    }

    public final <K, V> void zza(int i, zzgpm<K, V> zzgpm, Map<K, V> map) {
        try {
            for (Map.Entry next : map.entrySet()) {
                this.zza.zza(i, 2);
                this.zza.zzb(zzgpl.zza(zzgpm, next.getKey(), next.getValue()));
                zzgpl.zza(this.zza, zzgpm, next.getKey(), next.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
