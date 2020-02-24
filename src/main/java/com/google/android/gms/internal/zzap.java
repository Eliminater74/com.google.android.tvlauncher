package com.google.android.gms.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* compiled from: HttpHeaderParser */
public final class zzap {
    public static zzc zza(zzp zzp) {
        long j;
        boolean z;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        zzp zzp2 = zzp;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzp2.zzc;
        String str = map.get("Date");
        long j8 = 0;
        if (str != null) {
            j = zza(str);
        } else {
            j = 0;
        }
        String str2 = map.get("Cache-Control");
        int i = 0;
        if (str2 != null) {
            String[] split = str2.split(",");
            j3 = 0;
            j2 = 0;
            int i2 = 0;
            while (i < split.length) {
                String trim = split[i].trim();
                if (trim.equals("no-cache") || trim.equals("no-store")) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j3 = Long.parseLong(trim.substring(8));
                    } catch (Exception e) {
                    }
                } else if (trim.startsWith("stale-while-revalidate=")) {
                    try {
                        j2 = Long.parseLong(trim.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (trim.equals("must-revalidate") || trim.equals("proxy-revalidate")) {
                    i2 = 1;
                }
                i++;
            }
            i = i2;
            z = true;
        } else {
            j3 = 0;
            j2 = 0;
            z = false;
        }
        String str3 = map.get("Expires");
        if (str3 != null) {
            j4 = zza(str3);
        } else {
            j4 = 0;
        }
        String str4 = map.get("Last-Modified");
        if (str4 != null) {
            j5 = zza(str4);
        } else {
            j5 = 0;
        }
        String str5 = map.get("ETag");
        if (z) {
            long j9 = currentTimeMillis + (j3 * 1000);
            if (i != 0) {
                j7 = j9;
            } else {
                Long.signum(j2);
                j7 = (j2 * 1000) + j9;
            }
            long j10 = j9;
            j6 = j7;
            j8 = j10;
        } else if (j <= 0 || j4 < j) {
            j6 = 0;
        } else {
            j8 = currentTimeMillis + (j4 - j);
            j6 = j8;
        }
        zzc zzc = new zzc();
        zzc.zza = zzp2.zzb;
        zzc.zzb = str5;
        zzc.zzf = j8;
        zzc.zze = j6;
        zzc.zzc = j;
        zzc.zzd = j5;
        zzc.zzg = map;
        zzc.zzh = zzp2.zzd;
        return zzc;
    }

    private static long zza(String str) {
        try {
            return zza().parse(str).getTime();
        } catch (ParseException e) {
            zzaf.zza(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0;
        }
    }

    static String zza(long j) {
        return zza().format(new Date(j));
    }

    private static SimpleDateFormat zza() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }
}
