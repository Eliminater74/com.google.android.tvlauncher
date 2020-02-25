package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class ExperimentTokens extends zzbkv {
    @KeepForSdk
    public static final Parcelable.Creator<ExperimentTokens> CREATOR = new zzi();
    public static final ExperimentTokens EMPTY;
    public static final byte[][] EMPTY_BYTES = new byte[0][];
    private static final zza zza = new zze();
    private static final zza zzb = new zzf();
    private static final zza zzc = new zzg();
    private static final zza zzd = new zzh();

    static {
        byte[][] bArr = EMPTY_BYTES;
        EMPTY = new ExperimentTokens("", null, bArr, bArr, bArr, bArr, null, null);
    }

    public final byte[][] additionalDirectExperimentTokens;
    public final byte[][] alwaysCrossExperimentTokens;
    public final byte[] directExperimentToken;
    public final byte[][] gaiaCrossExperimentTokens;
    public final byte[][] otherCrossExperimentTokens;
    public final byte[][] pseudonymousCrossExperimentTokens;
    public final String user;
    public final int[] weakExperimentIds;

    public ExperimentTokens(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.user = str;
        this.directExperimentToken = bArr;
        this.gaiaCrossExperimentTokens = bArr2;
        this.pseudonymousCrossExperimentTokens = bArr3;
        this.alwaysCrossExperimentTokens = bArr4;
        this.otherCrossExperimentTokens = bArr5;
        this.weakExperimentIds = iArr;
        this.additionalDirectExperimentTokens = bArr6;
    }

    public ExperimentTokens(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr) {
        this(str, bArr, bArr2, bArr3, bArr4, bArr5, iArr, null);
    }

    public static ExperimentTokens merge(List<ExperimentTokens> list) {
        return merge(list, false);
    }

    public static ExperimentTokens merge(List<ExperimentTokens> list, boolean z) {
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        boolean sameUser = sameUser(list);
        return new ExperimentTokens((!sameUser || list == null || list.isEmpty()) ? "" : list.get(0).user, null, (z || sameUser) ? zza(list, zza) : EMPTY_BYTES, zza(list, zzb), zza(list, zzc), zza(list, zzd), zzb(list), zza(list));
    }

    public static ExperimentTokens mergeNoCross(List<ExperimentTokens> list) {
        byte[][] bArr = EMPTY_BYTES;
        return new ExperimentTokens("", null, bArr, bArr, bArr, bArr, zzb(list), zza(list));
    }

    public static boolean sameUser(List<ExperimentTokens> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        String str = list.get(0).user;
        for (ExperimentTokens experimentTokens : list) {
            if (!PhenotypeCore.zza(str, experimentTokens.user)) {
                return false;
            }
        }
        return true;
    }

    private static byte[][] zza(byte[][] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[][] bArr2 = new byte[bArr.length][];
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == null || bArr[i].length == 0) {
                bArr2[i] = bArr[i];
            } else {
                bArr2[i] = Arrays.copyOf(bArr[i], bArr[i].length);
            }
        }
        return bArr2;
    }

    private static void zza(StringBuilder sb, String str, byte[][] bArr) {
        sb.append(str);
        sb.append("=");
        if (bArr == null) {
            sb.append("null");
            return;
        }
        sb.append("(");
        int length = bArr.length;
        int i = 0;
        boolean z = true;
        while (i < length) {
            byte[] bArr2 = bArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append("'");
            sb.append(Base64.encodeToString(bArr2, 3));
            sb.append("'");
            i++;
            z = false;
        }
        sb.append(")");
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zza(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static int zzc(byte[][] bArr) {
        if (bArr == null) {
            return 0;
        }
        int i = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2 != null) {
                i += bArr2.length;
            }
        }
        return i;
    }

    private static byte[][] zza(List<ExperimentTokens> list) {
        byte[][] bArr;
        byte[] bArr2;
        byte[][] bArr3;
        int i = 0;
        for (ExperimentTokens next : list) {
            if (!(next == null || next.directExperimentToken == null)) {
                i++;
            }
            if (!(next == null || (bArr3 = next.additionalDirectExperimentTokens) == null)) {
                i += bArr3.length;
            }
        }
        byte[][] bArr4 = new byte[i][];
        int i2 = 0;
        for (ExperimentTokens next2 : list) {
            if (!(next2 == null || (bArr2 = next2.directExperimentToken) == null)) {
                bArr4[i2] = bArr2;
                i2++;
            }
            if (!(next2 == null || (bArr = next2.additionalDirectExperimentTokens) == null)) {
                int length = bArr.length;
                int i3 = i2;
                int i4 = 0;
                while (i4 < length) {
                    bArr4[i3] = bArr[i4];
                    i4++;
                    i3++;
                }
                i2 = i3;
            }
        }
        return bArr4;
    }

    private static int[] zzb(List<ExperimentTokens> list) {
        int[] iArr;
        int[] iArr2;
        int i = 0;
        for (ExperimentTokens next : list) {
            if (!(next == null || (iArr2 = next.weakExperimentIds) == null)) {
                i += iArr2.length;
            }
        }
        int[] iArr3 = new int[i];
        int i2 = 0;
        for (ExperimentTokens next2 : list) {
            if (!(next2 == null || (iArr = next2.weakExperimentIds) == null)) {
                int length = iArr.length;
                int i3 = i2;
                int i4 = 0;
                while (i4 < length) {
                    iArr3[i3] = iArr[i4];
                    i4++;
                    i3++;
                }
                i2 = i3;
            }
        }
        return iArr3;
    }

    private static byte[][] zza(List<ExperimentTokens> list, zza zza2) {
        int i = 0;
        for (ExperimentTokens next : list) {
            if (!(next == null || zza2.zza(next) == null)) {
                i += zza2.zza(next).length;
            }
        }
        byte[][] bArr = new byte[i][];
        int i2 = 0;
        for (ExperimentTokens next2 : list) {
            if (!(next2 == null || zza2.zza(next2) == null)) {
                byte[][] zza3 = zza2.zza(next2);
                int length = zza3.length;
                int i3 = i2;
                int i4 = 0;
                while (i4 < length) {
                    bArr[i3] = zza3[i4];
                    i4++;
                    i3++;
                }
                i2 = i3;
            }
        }
        return bArr;
    }

    private static void zza(byte[] bArr, DataOutputStream dataOutputStream) throws IOException {
        if (bArr != null) {
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr, 0, bArr.length);
            return;
        }
        dataOutputStream.writeInt(0);
    }

    private static void zza(byte[][] bArr, DataOutputStream dataOutputStream) throws IOException {
        if (bArr != null) {
            dataOutputStream.writeInt(bArr.length);
            for (byte[] zza2 : bArr) {
                zza(zza2, dataOutputStream);
            }
            return;
        }
        dataOutputStream.writeInt(0);
    }

    public static ExperimentTokens deserializeFromString(String str) {
        int[] iArr;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(Base64.decode(str, 0)));
        try {
            int readInt = dataInputStream.readInt();
            if (readInt == 1) {
                String readUTF = dataInputStream.readUTF();
                byte[] zza2 = zza(dataInputStream);
                byte[][] zzb2 = zzb(dataInputStream);
                byte[][] zzb3 = zzb(dataInputStream);
                byte[][] zzb4 = zzb(dataInputStream);
                byte[][] zzb5 = zzb(dataInputStream);
                int readInt2 = dataInputStream.readInt();
                if (readInt2 == 0) {
                    iArr = null;
                } else {
                    int[] iArr2 = new int[readInt2];
                    for (int i = 0; i < readInt2; i++) {
                        iArr2[i] = dataInputStream.readInt();
                    }
                    iArr = iArr2;
                }
                return new ExperimentTokens(readUTF, zza2, zzb2, zzb3, zzb4, zzb5, iArr, zzb(dataInputStream));
            }
            StringBuilder sb = new StringBuilder(30);
            sb.append("Unexpected version ");
            sb.append(readInt);
            throw new RuntimeException(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] zza(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        if (readInt == 0) {
            return null;
        }
        byte[] bArr = new byte[readInt];
        dataInputStream.readFully(bArr);
        return bArr;
    }

    private static byte[][] zzb(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        if (readInt == 0) {
            return null;
        }
        byte[][] bArr = new byte[readInt][];
        for (int i = 0; i < readInt; i++) {
            bArr[i] = zza(dataInputStream);
        }
        return bArr;
    }

    public ExperimentTokens deepCopy() {
        byte[] bArr;
        int[] iArr;
        String str = this.user;
        byte[] bArr2 = this.directExperimentToken;
        if (bArr2 == null || bArr2.length == 0) {
            bArr = bArr2;
        } else {
            bArr = Arrays.copyOf(bArr2, bArr2.length);
        }
        byte[][] zza2 = zza(this.gaiaCrossExperimentTokens);
        byte[][] zza3 = zza(this.pseudonymousCrossExperimentTokens);
        byte[][] zza4 = zza(this.alwaysCrossExperimentTokens);
        byte[][] zza5 = zza(this.otherCrossExperimentTokens);
        int[] iArr2 = this.weakExperimentIds;
        if (iArr2 == null || iArr2.length == 0) {
            iArr = iArr2;
        } else {
            iArr = Arrays.copyOf(iArr2, iArr2.length);
        }
        return new ExperimentTokens(str, bArr, zza2, zza3, zza4, zza5, iArr, zza(this.additionalDirectExperimentTokens));
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("ExperimentTokens");
        sb.append("(");
        String str2 = this.user;
        if (str2 == null) {
            str = "null";
        } else {
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 2);
            sb2.append("'");
            sb2.append(str2);
            sb2.append("'");
            str = sb2.toString();
        }
        sb.append(str);
        sb.append(", ");
        byte[] bArr = this.directExperimentToken;
        sb.append("direct");
        sb.append("=");
        if (bArr == null) {
            sb.append("null");
        } else {
            sb.append("'");
            sb.append(Base64.encodeToString(bArr, 3));
            sb.append("'");
        }
        sb.append(", ");
        zza(sb, "GAIA", this.gaiaCrossExperimentTokens);
        sb.append(", ");
        zza(sb, "PSEUDO", this.pseudonymousCrossExperimentTokens);
        sb.append(", ");
        zza(sb, "ALWAYS", this.alwaysCrossExperimentTokens);
        sb.append(", ");
        zza(sb, "OTHER", this.otherCrossExperimentTokens);
        sb.append(", ");
        int[] iArr = this.weakExperimentIds;
        sb.append("weak");
        sb.append("=");
        if (iArr == null) {
            sb.append("null");
        } else {
            sb.append("(");
            int length = iArr.length;
            int i = 0;
            boolean z = true;
            while (i < length) {
                int i2 = iArr[i];
                if (!z) {
                    sb.append(", ");
                }
                sb.append(i2);
                i++;
                z = false;
            }
            sb.append(")");
        }
        sb.append(", ");
        zza(sb, "directs", this.additionalDirectExperimentTokens);
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExperimentTokens)) {
            return false;
        }
        ExperimentTokens experimentTokens = (ExperimentTokens) obj;
        if (!PhenotypeCore.zza(this.user, experimentTokens.user) || !Arrays.equals(this.directExperimentToken, experimentTokens.directExperimentToken) || !PhenotypeCore.zza(zzb(this.gaiaCrossExperimentTokens), zzb(experimentTokens.gaiaCrossExperimentTokens)) || !PhenotypeCore.zza(zzb(this.pseudonymousCrossExperimentTokens), zzb(experimentTokens.pseudonymousCrossExperimentTokens)) || !PhenotypeCore.zza(zzb(this.alwaysCrossExperimentTokens), zzb(experimentTokens.alwaysCrossExperimentTokens)) || !PhenotypeCore.zza(zzb(this.otherCrossExperimentTokens), zzb(experimentTokens.otherCrossExperimentTokens)) || !PhenotypeCore.zza(zza(this.weakExperimentIds), zza(experimentTokens.weakExperimentIds)) || !PhenotypeCore.zza(zzb(this.additionalDirectExperimentTokens), zzb(experimentTokens.additionalDirectExperimentTokens))) {
            return false;
        }
        return true;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
     arg types: [android.os.Parcel, int, java.lang.String, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
     arg types: [android.os.Parcel, int, byte[], int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
     arg types: [android.os.Parcel, int, byte[][], int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
     arg types: [android.os.Parcel, int, int[], int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, this.user, false);
        zzbky.zza(parcel, 3, this.directExperimentToken, false);
        zzbky.zza(parcel, 4, this.gaiaCrossExperimentTokens, false);
        zzbky.zza(parcel, 5, this.pseudonymousCrossExperimentTokens, false);
        zzbky.zza(parcel, 6, this.alwaysCrossExperimentTokens, false);
        zzbky.zza(parcel, 7, this.otherCrossExperimentTokens, false);
        zzbky.zza(parcel, 8, this.weakExperimentIds, false);
        zzbky.zza(parcel, 9, this.additionalDirectExperimentTokens, false);
        zzbky.zza(parcel, zza2);
    }

    public int byteCount() {
        int length = this.user.length() * 2;
        byte[] bArr = this.directExperimentToken;
        int i = 0;
        int length2 = length + (bArr == null ? 0 : bArr.length) + zzc(this.gaiaCrossExperimentTokens) + zzc(this.pseudonymousCrossExperimentTokens) + zzc(this.alwaysCrossExperimentTokens) + zzc(this.otherCrossExperimentTokens) + zzc(this.additionalDirectExperimentTokens);
        int[] iArr = this.weakExperimentIds;
        if (iArr != null) {
            i = iArr.length;
        }
        return length2 + (i * 4);
    }

    public String serializeToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeInt(1);
            dataOutputStream.writeUTF(this.user);
            zza(this.directExperimentToken, dataOutputStream);
            zza(this.gaiaCrossExperimentTokens, dataOutputStream);
            zza(this.pseudonymousCrossExperimentTokens, dataOutputStream);
            zza(this.alwaysCrossExperimentTokens, dataOutputStream);
            zza(this.otherCrossExperimentTokens, dataOutputStream);
            int[] iArr = this.weakExperimentIds;
            if (iArr != null) {
                dataOutputStream.writeInt(iArr.length);
                for (int writeInt : iArr) {
                    dataOutputStream.writeInt(writeInt);
                }
            } else {
                dataOutputStream.writeInt(0);
            }
            zza(this.additionalDirectExperimentTokens, dataOutputStream);
            dataOutputStream.flush();
            String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            try {
                dataOutputStream.close();
                return encodeToString;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            try {
                dataOutputStream.close();
                throw th;
            } catch (IOException e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    interface zza {
        byte[][] zza(ExperimentTokens experimentTokens);
    }
}
