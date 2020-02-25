package com.google.android.gms.clearcut.internal;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.internal.zzbmk;
import com.google.android.gms.internal.zzfij;
import com.google.android.gms.internal.zzfja;
import com.google.android.gms.internal.zzfjh;
import com.google.android.gms.phenotype.Phenotype;
import com.google.android.gsf.Gservices;
import com.google.android.gsf.GservicesKeys;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/* compiled from: LogSamplerImpl */
public final class zzs implements ClearcutLogger.LogSampler {
    private static final Charset zza = Charset.forName("UTF-8");
    private static final zzfjh zzb = new zzfjh(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).zza("gms:playlog:service:sampling_").zzb("LogSampling__");
    private static Map<String, zzfja<String>> zzd = null;
    private static Boolean zze = null;
    private static Long zzf = null;
    private final Context zzc;

    public zzs(Context context) {
        this.zzc = context;
        if (zzd == null) {
            zzd = new HashMap();
        }
        Context context2 = this.zzc;
        if (context2 != null) {
            zzfja.zzb(context2);
        }
    }

    private static boolean zza(Context context) {
        if (zze == null) {
            zze = Boolean.valueOf(zzbmk.zza(context).zza(Gservices.PERMISSION_READ_GSERVICES) == 0);
        }
        return zze.booleanValue();
    }

    private static zzt zza(String str) {
        String str2;
        int i;
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(44);
        if (indexOf >= 0) {
            String substring = str.substring(0, indexOf);
            i = indexOf + 1;
            str2 = substring;
        } else {
            str2 = "";
            i = 0;
        }
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            String valueOf = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf.length() != 0 ? "Failed to parse the rule: ".concat(valueOf) : new String("Failed to parse the rule: "));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return new zzt(str2, parseLong, parseLong2);
            }
            StringBuilder sb = new StringBuilder(72);
            sb.append("negative values not supported: ");
            sb.append(parseLong);
            sb.append("/");
            sb.append(parseLong2);
            Log.e("LogSamplerImpl", sb.toString());
            return null;
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf2.length() != 0 ? "parseLong() failed while parsing: ".concat(valueOf2) : new String("parseLong() failed while parsing: "), e);
            return null;
        }
    }

    public final boolean shouldLog(String str, int i) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        String str2 = null;
        if (str == null || str.isEmpty()) {
            if (i >= 0) {
                str = String.valueOf(i);
            } else {
                str = null;
            }
        }
        if (str == null) {
            return true;
        }
        Context context = this.zzc;
        if (context != null && zza(context)) {
            zzfja<String> zzfja = zzd.get(str);
            if (zzfja == null) {
                zzfja = zzb.zza(str, null);
                zzd.put(str, zzfja);
            }
            str2 = zzfja.zzb();
        }
        zzt zza2 = zza(str2);
        if (zza2 == null) {
            return true;
        }
        String str3 = zza2.zza;
        Context context2 = this.zzc;
        if (zzf == null) {
            if (context2 == null) {
                j = 0;
                if (str3 != null || str3.isEmpty()) {
                    j2 = zzj.zza(ByteBuffer.allocate(8).putLong(j).array());
                } else {
                    byte[] bytes = str3.getBytes(zza);
                    ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
                    allocate.put(bytes);
                    allocate.putLong(j);
                    j2 = zzj.zza(allocate.array());
                }
                j3 = zza2.zzb;
                j4 = zza2.zzc;
                if (j3 >= 0 || j4 < 0) {
                    StringBuilder sb = new StringBuilder(72);
                    sb.append("negative values not supported: ");
                    sb.append(j3);
                    sb.append("/");
                    sb.append(j4);
                    throw new IllegalArgumentException(sb.toString());
                } else if (j4 <= 0) {
                    return false;
                } else {
                    if (j2 >= 0) {
                        j5 = j2 % j4;
                    } else {
                        j5 = (((Long.MAX_VALUE % j4) + 1) + ((j2 & Long.MAX_VALUE) % j4)) % j4;
                    }
                    if (j5 < j3) {
                        return true;
                    }
                    return false;
                }
            } else if (zza(context2)) {
                zzf = Long.valueOf(zzfij.zza(context2.getContentResolver(), GservicesKeys.ANDROID_ID, 0));
            } else {
                zzf = 0L;
            }
        }
        j = zzf.longValue();
        if (str3 != null) {
        }
        j2 = zzj.zza(ByteBuffer.allocate(8).putLong(j).array());
        j3 = zza2.zzb;
        j4 = zza2.zzc;
        if (j3 >= 0) {
        }
        StringBuilder sb2 = new StringBuilder(72);
        sb2.append("negative values not supported: ");
        sb2.append(j3);
        sb2.append("/");
        sb2.append(j4);
        throw new IllegalArgumentException(sb2.toString());
    }
}
