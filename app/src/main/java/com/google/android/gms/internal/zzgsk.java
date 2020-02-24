package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: WireFormatNano */
public final class zzgsk {
    public static final int[] zza = new int[0];
    public static final long[] zzb = new long[0];
    public static final float[] zzc = new float[0];
    public static final double[] zzd = new double[0];
    public static final boolean[] zze = new boolean[0];
    public static final String[] zzf = new String[0];
    public static final byte[][] zzg = new byte[0][];
    public static final byte[] zzh = new byte[0];
    private static final int zzi = 11;
    private static final int zzj = 12;
    private static final int zzk = 16;
    private static final int zzl = 26;

    public static final int zza(zzgry zzgry, int i) throws IOException {
        int zzn = zzgry.zzn();
        zzgry.zzb(i);
        int i2 = 1;
        while (zzgry.zza() == i) {
            zzgry.zzb(i);
            i2++;
        }
        zzgry.zzb(zzn, i);
        return i2;
    }
}
