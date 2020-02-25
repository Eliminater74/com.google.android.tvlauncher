package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.Arrays;
import java.util.Comparator;

public class Flag extends zzbkv implements Comparable<Flag> {
    public static final Parcelable.Creator<Flag> CREATOR = new zzk();
    public static final Comparator<Flag> NON_VALUE_COMPARATOR = new zzj();
    public static final int VALUE_TYPE_BOOLEAN = 2;
    public static final int VALUE_TYPE_BYTES = 5;
    public static final int VALUE_TYPE_DOUBLE = 3;
    public static final int VALUE_TYPE_LONG = 1;
    public static final int VALUE_TYPE_STRING = 4;
    public final int flagStorageType;
    public final int flagValueType;
    public final String name;
    private final long zza;
    private final boolean zzb;
    private final double zzc;
    private final String zzd;
    private final byte[] zze;

    public Flag(String str, long j, boolean z, double d, String str2, byte[] bArr, int i, int i2) {
        this.name = str;
        this.zza = j;
        this.zzb = z;
        this.zzc = d;
        this.zzd = str2;
        this.zze = bArr;
        this.flagValueType = i;
        this.flagStorageType = i2;
    }

    public Flag(String str, String str2, int i, int i2) {
        this.name = str;
        this.flagValueType = i;
        this.flagStorageType = i2;
        if (i == 1) {
            this.zza = Long.parseLong(str2);
            this.zzb = false;
            this.zzc = 0.0d;
            this.zzd = "";
            this.zze = new byte[0];
        } else if (i == 2) {
            if (str2.equalsIgnoreCase("true")) {
                this.zzb = true;
            } else if (str2.equalsIgnoreCase("false")) {
                this.zzb = false;
            } else {
                String valueOf = String.valueOf(str2);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "Unrecognized boolean value: ".concat(valueOf) : new String("Unrecognized boolean value: "));
            }
            this.zza = 0;
            this.zzc = 0.0d;
            this.zzd = "";
            this.zze = new byte[0];
        } else if (i == 3) {
            this.zzc = Double.parseDouble(str2);
            this.zza = 0;
            this.zzb = false;
            this.zzd = "";
            this.zze = new byte[0];
        } else if (i == 4) {
            this.zzd = str2;
            this.zza = 0;
            this.zzb = false;
            this.zzc = 0.0d;
            this.zze = new byte[0];
        } else if (i == 5) {
            this.zze = Base64.decode(str2, 3);
            this.zza = 0;
            this.zzb = false;
            this.zzc = 0.0d;
            this.zzd = "";
        } else {
            StringBuilder sb = new StringBuilder(35);
            sb.append("Unrecognized flag type: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public Flag(String str, long j, int i) {
        this(str, j, false, 0.0d, "", new byte[0], 1, i);
    }

    public Flag(String str, boolean z, int i) {
        this(str, 0, z, 0.0d, "", new byte[0], 2, i);
    }

    public Flag(String str, double d, int i) {
        this(str, 0, false, d, "", new byte[0], 3, i);
    }

    public Flag(String str, String str2, int i) {
        this(str, 0, false, 0.0d, str2, new byte[0], 4, i);
    }

    public Flag(String str, byte[] bArr, int i) {
        this(str, 0, false, 0.0d, "", bArr, 5, i);
    }

    private static int zza(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public long getLong() {
        if (this.flagValueType == 1) {
            return this.zza;
        }
        throw new IllegalArgumentException("Not a long type");
    }

    public boolean getBoolean() {
        if (this.flagValueType == 2) {
            return this.zzb;
        }
        throw new IllegalArgumentException("Not a boolean type");
    }

    public double getDouble() {
        if (this.flagValueType == 3) {
            return this.zzc;
        }
        throw new IllegalArgumentException("Not a double type");
    }

    public String getString() {
        if (this.flagValueType == 4) {
            return this.zzd;
        }
        throw new IllegalArgumentException("Not a String type");
    }

    public byte[] getBytesValue() {
        if (this.flagValueType == 5) {
            return this.zze;
        }
        throw new IllegalArgumentException("Not a bytes type");
    }

    public String asString() {
        int i = this.flagValueType;
        if (i == 1) {
            return Long.toString(this.zza);
        }
        if (i == 2) {
            return this.zzb ? "true" : "false";
        }
        if (i == 3) {
            return Double.toString(this.zzc);
        }
        if (i == 4) {
            return this.zzd;
        }
        if (i == 5) {
            return Base64.encodeToString(this.zze, 3);
        }
        StringBuilder sb = new StringBuilder(31);
        sb.append("Invalid enum value: ");
        sb.append(i);
        throw new AssertionError(sb.toString());
    }

    public String toString() {
        return toString(new StringBuilder());
    }

    public String toString(StringBuilder sb) {
        sb.append("Flag(");
        sb.append(this.name);
        sb.append(", ");
        int i = this.flagValueType;
        if (i == 1) {
            sb.append(this.zza);
        } else if (i == 2) {
            sb.append(this.zzb);
        } else if (i == 3) {
            sb.append(this.zzc);
        } else if (i == 4) {
            sb.append("'");
            sb.append(this.zzd);
            sb.append("'");
        } else if (i != 5) {
            String str = this.name;
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 27);
            sb2.append("Invalid type: ");
            sb2.append(str);
            sb2.append(", ");
            sb2.append(i);
            throw new AssertionError(sb2.toString());
        } else if (this.zze == null) {
            sb.append("null");
        } else {
            sb.append("'");
            sb.append(Base64.encodeToString(this.zze, 3));
            sb.append("'");
        }
        sb.append(", ");
        sb.append(this.flagValueType);
        sb.append(", ");
        sb.append(this.flagStorageType);
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        int i;
        if (!(obj instanceof Flag)) {
            return false;
        }
        Flag flag = (Flag) obj;
        if (!PhenotypeCore.zza(this.name, flag.name) || (i = this.flagValueType) != flag.flagValueType || this.flagStorageType != flag.flagStorageType) {
            return false;
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return PhenotypeCore.zza(this.zzd, flag.zzd);
                    }
                    if (i == 5) {
                        return Arrays.equals(this.zze, flag.zze);
                    }
                    StringBuilder sb = new StringBuilder(31);
                    sb.append("Invalid enum value: ");
                    sb.append(i);
                    throw new AssertionError(sb.toString());
                } else if (this.zzc == flag.zzc) {
                    return true;
                } else {
                    return false;
                }
            } else if (this.zzb == flag.zzb) {
                return true;
            } else {
                return false;
            }
        } else if (this.zza == flag.zza) {
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(Flag flag) {
        int compareTo = this.name.compareTo(flag.name);
        if (compareTo != 0) {
            return compareTo;
        }
        int zza2 = zza(this.flagValueType, flag.flagValueType);
        if (zza2 != 0) {
            return zza2;
        }
        int i = this.flagValueType;
        if (i == 1) {
            long j = this.zza;
            long j2 = flag.zza;
            if (j < j2) {
                return -1;
            }
            return j == j2 ? 0 : 1;
        } else if (i == 2) {
            boolean z = this.zzb;
            if (z == flag.zzb) {
                return 0;
            }
            return z ? 1 : -1;
        } else if (i == 3) {
            return Double.compare(this.zzc, flag.zzc);
        } else {
            if (i == 4) {
                String str = this.zzd;
                String str2 = flag.zzd;
                if (str == str2) {
                    return 0;
                }
                if (str == null) {
                    return -1;
                }
                if (str2 == null) {
                    return 1;
                }
                return str.compareTo(str2);
            } else if (i == 5) {
                byte[] bArr = this.zze;
                byte[] bArr2 = flag.zze;
                if (bArr == bArr2) {
                    return 0;
                }
                if (bArr == null) {
                    return -1;
                }
                if (bArr2 == null) {
                    return 1;
                }
                for (int i2 = 0; i2 < Math.min(this.zze.length, flag.zze.length); i2++) {
                    int i3 = this.zze[i2] - flag.zze[i2];
                    if (i3 != 0) {
                        return i3;
                    }
                }
                return zza(this.zze.length, flag.zze.length);
            } else {
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid enum value: ");
                sb.append(i);
                throw new AssertionError(sb.toString());
            }
        }
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
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, this.name, false);
        zzbky.zza(parcel, 3, this.zza);
        zzbky.zza(parcel, 4, this.zzb);
        zzbky.zza(parcel, 5, this.zzc);
        zzbky.zza(parcel, 6, this.zzd, false);
        zzbky.zza(parcel, 7, this.zze, false);
        zzbky.zza(parcel, 8, this.flagValueType);
        zzbky.zza(parcel, 9, this.flagStorageType);
        zzbky.zza(parcel, zza2);
    }
}
