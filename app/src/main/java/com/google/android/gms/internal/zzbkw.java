package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p001v4.internal.view.SupportMenu;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SafeParcelReader */
public final class zzbkw {
    public static int zza(Parcel parcel, int i) {
        if ((i & SupportMenu.CATEGORY_MASK) != -65536) {
            return (i >> 16) & SupportMenu.USER_MASK;
        }
        return parcel.readInt();
    }

    public static void zzb(Parcel parcel, int i) {
        parcel.setDataPosition(parcel.dataPosition() + zza(parcel, i));
    }

    private static void zza(Parcel parcel, int i, int i2) {
        int zza = zza(parcel, i);
        if (zza != i2) {
            String hexString = Integer.toHexString(zza);
            StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 46);
            sb.append("Expected size ");
            sb.append(i2);
            sb.append(" got ");
            sb.append(zza);
            sb.append(" (0x");
            sb.append(hexString);
            sb.append(")");
            throw new zzbkx(sb.toString(), parcel);
        }
    }

    private static void zza(Parcel parcel, int i, int i2, int i3) {
        if (i2 != i3) {
            String hexString = Integer.toHexString(i2);
            StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 46);
            sb.append("Expected size ");
            sb.append(i3);
            sb.append(" got ");
            sb.append(i2);
            sb.append(" (0x");
            sb.append(hexString);
            sb.append(")");
            throw new zzbkx(sb.toString(), parcel);
        }
    }

    public static int zza(Parcel parcel) {
        int readInt = parcel.readInt();
        int zza = zza(parcel, readInt);
        int dataPosition = parcel.dataPosition();
        if ((65535 & readInt) != 20293) {
            String valueOf = String.valueOf(Integer.toHexString(readInt));
            throw new zzbkx(valueOf.length() != 0 ? "Expected object header. Got 0x".concat(valueOf) : new String("Expected object header. Got 0x"), parcel);
        }
        int i = zza + dataPosition;
        if (i >= dataPosition && i <= parcel.dataSize()) {
            return i;
        }
        StringBuilder sb = new StringBuilder(54);
        sb.append("Size read is invalid start=");
        sb.append(dataPosition);
        sb.append(" end=");
        sb.append(i);
        throw new zzbkx(sb.toString(), parcel);
    }

    public static boolean zzc(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static Boolean zzd(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        if (zza == 0) {
            return null;
        }
        zza(parcel, i, zza, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte zze(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (byte) parcel.readInt();
    }

    public static short zzf(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (short) parcel.readInt();
    }

    public static int zzg(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt();
    }

    public static Integer zzh(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        if (zza == 0) {
            return null;
        }
        zza(parcel, i, zza, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static long zzi(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readLong();
    }

    public static Long zzj(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        if (zza == 0) {
            return null;
        }
        zza(parcel, i, zza, 8);
        return Long.valueOf(parcel.readLong());
    }

    public static BigInteger zzk(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(dataPosition + zza);
        return new BigInteger(createByteArray);
    }

    public static float zzl(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readFloat();
    }

    public static Float zzm(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        if (zza == 0) {
            return null;
        }
        zza(parcel, i, zza, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static double zzn(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readDouble();
    }

    public static Double zzo(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        if (zza == 0) {
            return null;
        }
        zza(parcel, i, zza, 8);
        return Double.valueOf(parcel.readDouble());
    }

    public static BigDecimal zzp(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        int readInt = parcel.readInt();
        parcel.setDataPosition(dataPosition + zza);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    public static String zzq(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        String readString = parcel.readString();
        parcel.setDataPosition(dataPosition + zza);
        return readString;
    }

    public static IBinder zzr(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(dataPosition + zza);
        return readStrongBinder;
    }

    public static <T extends Parcelable> T zza(Parcel parcel, int i, Parcelable.Creator creator) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        T t = (Parcelable) creator.createFromParcel(parcel);
        parcel.setDataPosition(dataPosition + zza);
        return t;
    }

    public static Bundle zzs(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        Bundle readBundle = parcel.readBundle();
        parcel.setDataPosition(dataPosition + zza);
        return readBundle;
    }

    public static byte[] zzt(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(dataPosition + zza);
        return createByteArray;
    }

    public static byte[][] zzu(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        byte[][] bArr = new byte[readInt][];
        for (int i2 = 0; i2 < readInt; i2++) {
            bArr[i2] = parcel.createByteArray();
        }
        parcel.setDataPosition(dataPosition + zza);
        return bArr;
    }

    public static boolean[] zzv(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        boolean[] createBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(dataPosition + zza);
        return createBooleanArray;
    }

    public static int[] zzw(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        int[] createIntArray = parcel.createIntArray();
        parcel.setDataPosition(dataPosition + zza);
        return createIntArray;
    }

    public static long[] zzx(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        long[] createLongArray = parcel.createLongArray();
        parcel.setDataPosition(dataPosition + zza);
        return createLongArray;
    }

    public static float[] zzy(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        float[] createFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(dataPosition + zza);
        return createFloatArray;
    }

    public static double[] zzz(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        double[] createDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(dataPosition + zza);
        return createDoubleArray;
    }

    public static String[] zzaa(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        String[] createStringArray = parcel.createStringArray();
        parcel.setDataPosition(dataPosition + zza);
        return createStringArray;
    }

    public static ArrayList<Integer> zzab(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Integer.valueOf(parcel.readInt()));
        }
        parcel.setDataPosition(dataPosition + zza);
        return arrayList;
    }

    public static ArrayList<String> zzac(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(dataPosition + zza);
        return createStringArrayList;
    }

    public static <T> T[] zzb(Parcel parcel, int i, Parcelable.Creator<T> creator) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        T[] createTypedArray = parcel.createTypedArray(creator);
        parcel.setDataPosition(dataPosition + zza);
        return createTypedArray;
    }

    public static <T> ArrayList<T> zzc(Parcel parcel, int i, Parcelable.Creator<T> creator) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        ArrayList<T> createTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(dataPosition + zza);
        return createTypedArrayList;
    }

    public static Parcel zzad(Parcel parcel, int i) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza == 0) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.appendFrom(parcel, dataPosition, zza);
        parcel.setDataPosition(dataPosition + zza);
        return obtain;
    }

    public static void zza(Parcel parcel, int i, List list, ClassLoader classLoader) {
        int zza = zza(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (zza != 0) {
            parcel.readList(list, classLoader);
            parcel.setDataPosition(dataPosition + zza);
        }
    }

    public static void zzae(Parcel parcel, int i) {
        if (parcel.dataPosition() != i) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(i);
            throw new zzbkx(sb.toString(), parcel);
        }
    }
}
