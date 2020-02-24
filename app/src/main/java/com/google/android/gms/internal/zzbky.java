package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p001v4.internal.view.SupportMenu;
import android.util.SparseArray;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/* compiled from: SafeParcelWriter */
public final class zzbky {
    private static void zzb(Parcel parcel, int i, int i2) {
        if (i2 >= 65535) {
            parcel.writeInt(i | SupportMenu.CATEGORY_MASK);
            parcel.writeInt(i2);
            return;
        }
        parcel.writeInt(i | (i2 << 16));
    }

    private static int zzb(Parcel parcel, int i) {
        parcel.writeInt(i | SupportMenu.CATEGORY_MASK);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzc(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(i - 4);
        parcel.writeInt(dataPosition - i);
        parcel.setDataPosition(dataPosition);
    }

    public static int zza(Parcel parcel) {
        return zzb(parcel, 20293);
    }

    public static void zza(Parcel parcel, int i) {
        zzc(parcel, i);
    }

    public static void zza(Parcel parcel, int i, boolean z) {
        zzb(parcel, i, 4);
        parcel.writeInt(z ? 1 : 0);
    }

    public static void zza(Parcel parcel, int i, Boolean bool, boolean z) {
        if (bool != null) {
            zzb(parcel, i, 4);
            parcel.writeInt(bool.booleanValue() ? 1 : 0);
        }
    }

    public static void zza(Parcel parcel, int i, byte b) {
        zzb(parcel, i, 4);
        parcel.writeInt(b);
    }

    public static void zza(Parcel parcel, int i, short s) {
        zzb(parcel, 3, 4);
        parcel.writeInt(s);
    }

    public static void zza(Parcel parcel, int i, int i2) {
        zzb(parcel, i, 4);
        parcel.writeInt(i2);
    }

    public static void zza(Parcel parcel, int i, Integer num, boolean z) {
        if (num != null) {
            zzb(parcel, i, 4);
            parcel.writeInt(num.intValue());
        }
    }

    public static void zza(Parcel parcel, int i, long j) {
        zzb(parcel, i, 8);
        parcel.writeLong(j);
    }

    public static void zza(Parcel parcel, int i, Long l, boolean z) {
        if (l != null) {
            zzb(parcel, i, 8);
            parcel.writeLong(l.longValue());
        }
    }

    public static void zza(Parcel parcel, int i, BigInteger bigInteger, boolean z) {
        if (bigInteger == null) {
            zzb(parcel, i, 0);
            return;
        }
        int zzb = zzb(parcel, i);
        parcel.writeByteArray(bigInteger.toByteArray());
        zzc(parcel, zzb);
    }

    public static void zza(Parcel parcel, int i, float f) {
        zzb(parcel, i, 4);
        parcel.writeFloat(f);
    }

    public static void zza(Parcel parcel, int i, Float f, boolean z) {
        if (f != null) {
            zzb(parcel, i, 4);
            parcel.writeFloat(f.floatValue());
        }
    }

    public static void zza(Parcel parcel, int i, double d) {
        zzb(parcel, i, 8);
        parcel.writeDouble(d);
    }

    public static void zza(Parcel parcel, int i, Double d, boolean z) {
        if (d != null) {
            zzb(parcel, i, 8);
            parcel.writeDouble(d.doubleValue());
        }
    }

    public static void zza(Parcel parcel, int i, BigDecimal bigDecimal, boolean z) {
        if (bigDecimal != null) {
            int zzb = zzb(parcel, i);
            parcel.writeByteArray(bigDecimal.unscaledValue().toByteArray());
            parcel.writeInt(bigDecimal.scale());
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, String str, boolean z) {
        if (str != null) {
            int zzb = zzb(parcel, i);
            parcel.writeString(str);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, IBinder iBinder, boolean z) {
        if (iBinder != null) {
            int zzb = zzb(parcel, i);
            parcel.writeStrongBinder(iBinder);
            zzc(parcel, zzb);
        }
    }

    public static void zza(Parcel parcel, int i, Parcelable parcelable, int i2, boolean z) {
        if (parcelable != null) {
            int zzb = zzb(parcel, i);
            parcelable.writeToParcel(parcel, i2);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, Bundle bundle, boolean z) {
        if (bundle != null) {
            int zzb = zzb(parcel, i);
            parcel.writeBundle(bundle);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, byte[] bArr, boolean z) {
        if (bArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeByteArray(bArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, byte[][] bArr, boolean z) {
        if (bArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeInt(r5);
            for (byte[] writeByteArray : bArr) {
                parcel.writeByteArray(writeByteArray);
            }
            zzc(parcel, zzb);
        }
    }

    public static void zza(Parcel parcel, int i, boolean[] zArr, boolean z) {
        if (zArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeBooleanArray(zArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, int[] iArr, boolean z) {
        if (iArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeIntArray(iArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, long[] jArr, boolean z) {
        if (jArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeLongArray(jArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, BigInteger[] bigIntegerArr, boolean z) {
        int zzb = zzb(parcel, i);
        parcel.writeInt(r5);
        for (BigInteger byteArray : bigIntegerArr) {
            parcel.writeByteArray(byteArray.toByteArray());
        }
        zzc(parcel, zzb);
    }

    public static void zza(Parcel parcel, int i, float[] fArr, boolean z) {
        if (fArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeFloatArray(fArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, double[] dArr, boolean z) {
        if (dArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeDoubleArray(dArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, BigDecimal[] bigDecimalArr, boolean z) {
        int zzb = zzb(parcel, i);
        int length = bigDecimalArr.length;
        parcel.writeInt(length);
        for (int i2 = 0; i2 < length; i2++) {
            parcel.writeByteArray(bigDecimalArr[i2].unscaledValue().toByteArray());
            parcel.writeInt(bigDecimalArr[i2].scale());
        }
        zzc(parcel, zzb);
    }

    public static void zza(Parcel parcel, int i, String[] strArr, boolean z) {
        if (strArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeStringArray(strArr);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, List<Integer> list, boolean z) {
        if (list != null) {
            int zzb = zzb(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(list.get(i2).intValue());
            }
            zzc(parcel, zzb);
        }
    }

    public static void zzb(Parcel parcel, int i, List<String> list, boolean z) {
        if (list != null) {
            int zzb = zzb(parcel, i);
            parcel.writeStringList(list);
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static <T extends Parcelable> void zza(Parcel parcel, int i, Parcelable[] parcelableArr, int i2, boolean z) {
        if (parcelableArr != null) {
            int zzb = zzb(parcel, i);
            parcel.writeInt(r7);
            for (Parcelable parcelable : parcelableArr) {
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    zza(parcel, parcelable, i2);
                }
            }
            zzc(parcel, zzb);
        }
    }

    public static <T extends Parcelable> void zzc(Parcel parcel, int i, List<T> list, boolean z) {
        if (list != null) {
            int zzb = zzb(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Parcelable parcelable = (Parcelable) list.get(i2);
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    zza(parcel, parcelable, 0);
                }
            }
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    private static <T extends Parcelable> void zza(Parcel parcel, Parcelable parcelable, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        parcelable.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    public static void zza(Parcel parcel, int i, Parcel parcel2, boolean z) {
        if (parcel2 != null) {
            int zzb = zzb(parcel, i);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            zzc(parcel, zzb);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zzd(Parcel parcel, int i, List<Parcel> list, boolean z) {
        int zzb = zzb(parcel, i);
        int size = list.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Parcel parcel2 = list.get(i2);
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzc(parcel, zzb);
    }

    public static void zze(Parcel parcel, int i, List list, boolean z) {
        if (list != null) {
            int zzb = zzb(parcel, i);
            parcel.writeList(list);
            zzc(parcel, zzb);
        }
    }

    public static void zza(Parcel parcel, int i, SparseArray<String> sparseArray, boolean z) {
        if (sparseArray != null) {
            int zzb = zzb(parcel, 6);
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(sparseArray.keyAt(i2));
                parcel.writeString(sparseArray.valueAt(i2));
            }
            zzc(parcel, zzb);
        }
    }

    public static void zzb(Parcel parcel, int i, SparseArray<byte[]> sparseArray, boolean z) {
        if (sparseArray != null) {
            int zzb = zzb(parcel, 1);
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(sparseArray.keyAt(i2));
                parcel.writeByteArray(sparseArray.valueAt(i2));
            }
            zzc(parcel, zzb);
        }
    }
}
