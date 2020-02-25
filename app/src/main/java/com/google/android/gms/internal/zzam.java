package com.google.android.gms.internal;

import android.os.SystemClock;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: DiskBasedCache */
public final class zzam implements zzb {
    private final Map<String, zzan> zza;
    private final File zzc;
    private final int zzd;
    private long zzb;

    private zzam(File file, int i) {
        this.zza = new LinkedHashMap(16, 0.75f, true);
        this.zzb = 0;
        this.zzc = file;
        this.zzd = 5242880;
    }

    public zzam(File file) {
        this(file, 5242880);
    }

    private static String zzc(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private static byte[] zza(zzao zzao, long j) throws IOException {
        long zza2 = zzao.zza();
        if (j >= 0 && j <= zza2) {
            int i = (int) j;
            if (((long) i) == j) {
                byte[] bArr = new byte[i];
                new DataInputStream(zzao).readFully(bArr);
                return bArr;
            }
        }
        StringBuilder sb = new StringBuilder(73);
        sb.append("streamToBytes length=");
        sb.append(j);
        sb.append(", maxLength=");
        sb.append(zza2);
        throw new IOException(sb.toString());
    }

    private static InputStream zza(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    private static int zzc(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static int zza(InputStream inputStream) throws IOException {
        return (zzc(inputStream) << 24) | zzc(inputStream) | 0 | (zzc(inputStream) << 8) | (zzc(inputStream) << 16);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static long zzb(InputStream inputStream) throws IOException {
        return (((long) zzc(inputStream)) & 255) | 0 | ((((long) zzc(inputStream)) & 255) << 8) | ((((long) zzc(inputStream)) & 255) << 16) | ((((long) zzc(inputStream)) & 255) << 24) | ((((long) zzc(inputStream)) & 255) << 32) | ((((long) zzc(inputStream)) & 255) << 40) | ((((long) zzc(inputStream)) & 255) << 48) | ((255 & ((long) zzc(inputStream))) << 56);
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    static String zza(zzao zzao) throws IOException {
        return new String(zza(zzao, zzb((InputStream) zzao)), "UTF-8");
    }

    static List<zzl> zzb(zzao zzao) throws IOException {
        List<zzl> list;
        int zza2 = zza((InputStream) zzao);
        if (zza2 == 0) {
            list = Collections.emptyList();
        } else {
            list = new ArrayList<>(zza2);
        }
        for (int i = 0; i < zza2; i++) {
            list.add(new zzl(zza(zzao).intern(), zza(zzao).intern()));
        }
        return list;
    }

    public final synchronized zzc zza(String str) {
        zzao zzao;
        zzan zzan = this.zza.get(str);
        if (zzan == null) {
            return null;
        }
        File zzd2 = zzd(str);
        try {
            zzao = new zzao(new BufferedInputStream(zza(zzd2)), zzd2.length());
            zzan zza2 = zzan.zza(zzao);
            if (!TextUtils.equals(str, zza2.zzb)) {
                zzaf.zzb("%s: key=%s, found=%s", zzd2.getAbsolutePath(), str, zza2.zzb);
                zze(str);
                zzao.close();
                return null;
            }
            byte[] zza3 = zza(zzao, zzao.zza());
            zzc zzc2 = new zzc();
            zzc2.zza = zza3;
            zzc2.zzb = zzan.zzc;
            zzc2.zzc = zzan.zzd;
            zzc2.zzd = zzan.zze;
            zzc2.zze = zzan.zzf;
            zzc2.zzf = zzan.zzg;
            List<zzl> list = zzan.zzh;
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            for (zzl next : list) {
                treeMap.put(next.zza(), next.zzb());
            }
            zzc2.zzg = treeMap;
            zzc2.zzh = Collections.unmodifiableList(zzan.zzh);
            zzao.close();
            return zzc2;
        } catch (IOException e) {
            zzaf.zzb("%s: %s", zzd2.getAbsolutePath(), e.toString());
            zzb(str);
            return null;
        } catch (Throwable th) {
            zzao.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void zza() {
        /*
            r9 = this;
            monitor-enter(r9)
            java.io.File r0 = r9.zzc     // Catch:{ all -> 0x0062 }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x0062 }
            r1 = 0
            if (r0 != 0) goto L_0x0024
            java.io.File r0 = r9.zzc     // Catch:{ all -> 0x0062 }
            boolean r0 = r0.mkdirs()     // Catch:{ all -> 0x0062 }
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Unable to create cache dir %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0062 }
            java.io.File r3 = r9.zzc     // Catch:{ all -> 0x0062 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x0062 }
            r2[r1] = r3     // Catch:{ all -> 0x0062 }
            com.google.android.gms.internal.zzaf.zzc(r0, r2)     // Catch:{ all -> 0x0062 }
        L_0x0022:
            monitor-exit(r9)
            return
        L_0x0024:
            java.io.File r0 = r9.zzc     // Catch:{ all -> 0x0062 }
            java.io.File[] r0 = r0.listFiles()     // Catch:{ all -> 0x0062 }
            if (r0 != 0) goto L_0x002e
            monitor-exit(r9)
            return
        L_0x002e:
            int r2 = r0.length     // Catch:{ all -> 0x0062 }
        L_0x002f:
            if (r1 >= r2) goto L_0x0060
            r3 = r0[r1]     // Catch:{ all -> 0x0062 }
            long r4 = r3.length()     // Catch:{ IOException -> 0x0059 }
            com.google.android.gms.internal.zzao r6 = new com.google.android.gms.internal.zzao     // Catch:{ IOException -> 0x0059 }
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0059 }
            java.io.InputStream r8 = zza(r3)     // Catch:{ IOException -> 0x0059 }
            r7.<init>(r8)     // Catch:{ IOException -> 0x0059 }
            r6.<init>(r7, r4)     // Catch:{ IOException -> 0x0059 }
            com.google.android.gms.internal.zzan r7 = com.google.android.gms.internal.zzan.zza(r6)     // Catch:{ all -> 0x0054 }
            r7.zza = r4     // Catch:{ all -> 0x0054 }
            java.lang.String r4 = r7.zzb     // Catch:{ all -> 0x0054 }
            r9.zza(r4, r7)     // Catch:{ all -> 0x0054 }
            r6.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x005d
        L_0x0054:
            r4 = move-exception
            r6.close()     // Catch:{ IOException -> 0x0059 }
            throw r4     // Catch:{ IOException -> 0x0059 }
        L_0x0059:
            r4 = move-exception
            r3.delete()     // Catch:{ all -> 0x0062 }
        L_0x005d:
            int r1 = r1 + 1
            goto L_0x002f
        L_0x0060:
            monitor-exit(r9)
            return
        L_0x0062:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzam.zza():void");
    }

    public final synchronized void zza(String str, zzc zzc2) {
        long j;
        Iterator<Map.Entry<String, zzan>> it;
        String str2 = str;
        zzc zzc3 = zzc2;
        synchronized (this) {
            long length = (long) zzc3.zza.length;
            if (this.zzb + length >= ((long) this.zzd)) {
                if (zzaf.zza) {
                    zzaf.zza("Pruning old cache entries.", new Object[0]);
                }
                long j2 = this.zzb;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator<Map.Entry<String, zzan>> it2 = this.zza.entrySet().iterator();
                int i = 0;
                while (true) {
                    if (!it2.hasNext()) {
                        j = j2;
                        break;
                    }
                    zzan zzan = (zzan) it2.next().getValue();
                    if (zzd(zzan.zzb).delete()) {
                        j = j2;
                        it = it2;
                        this.zzb -= zzan.zza;
                    } else {
                        j = j2;
                        it = it2;
                        zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", zzan.zzb, zzc(zzan.zzb));
                    }
                    it.remove();
                    i++;
                    if (((float) (this.zzb + length)) < ((float) this.zzd) * 0.9f) {
                        break;
                    }
                    j2 = j;
                    it2 = it;
                }
                if (zzaf.zza) {
                    zzaf.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(this.zzb - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            }
            File zzd2 = zzd(str);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zzd2));
                zzan zzan2 = new zzan(str2, zzc3);
                if (zzan2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.write(zzc3.zza);
                    bufferedOutputStream.close();
                    zza(str2, zzan2);
                } else {
                    bufferedOutputStream.close();
                    zzaf.zzb("Failed to write header for %s", zzd2.getAbsolutePath());
                    throw new IOException();
                }
            } catch (IOException e) {
                if (!zzd2.delete()) {
                    zzaf.zzb("Could not clean up file %s", zzd2.getAbsolutePath());
                }
            }
        }
    }

    private final synchronized void zzb(String str) {
        boolean delete = zzd(str).delete();
        zze(str);
        if (!delete) {
            zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzc(str));
        }
    }

    private final File zzd(String str) {
        return new File(this.zzc, zzc(str));
    }

    private final void zza(String str, zzan zzan) {
        if (!this.zza.containsKey(str)) {
            this.zzb += zzan.zza;
        } else {
            this.zzb += zzan.zza - this.zza.get(str).zza;
        }
        this.zza.put(str, zzan);
    }

    private final void zze(String str) {
        zzan remove = this.zza.remove(str);
        if (remove != null) {
            this.zzb -= remove.zza;
        }
    }
}
