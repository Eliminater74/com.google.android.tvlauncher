package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzbky;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: SafeParcelResponse */
public class zzl extends FastSafeParcelableJsonResponse {
    public static final Parcelable.Creator<zzl> CREATOR = new zzm();
    private final int zza;
    private final Parcel zzb;
    private final int zzc;
    private final FieldMappingDictionary zzd;
    private final String zze;
    private int zzf;
    private int zzg;

    public zzl(FieldMappingDictionary fieldMappingDictionary, String str) {
        this.zza = 1;
        this.zzb = Parcel.obtain();
        this.zzc = 0;
        this.zzd = (FieldMappingDictionary) zzau.zza(fieldMappingDictionary);
        this.zze = (String) zzau.zza((Object) str);
        this.zzf = 0;
    }

    zzl(int i, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.zza = i;
        this.zzb = (Parcel) zzau.zza(parcel);
        this.zzc = 2;
        this.zzd = fieldMappingDictionary;
        FieldMappingDictionary fieldMappingDictionary2 = this.zzd;
        if (fieldMappingDictionary2 == null) {
            this.zze = null;
        } else {
            this.zze = fieldMappingDictionary2.getRootClassName();
        }
        this.zzf = 2;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
     arg types: [android.os.Parcel, int, android.os.Parcel, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.server.response.FieldMappingDictionary, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        FieldMappingDictionary fieldMappingDictionary;
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, zza(), false);
        int i2 = this.zzc;
        if (i2 == 0) {
            fieldMappingDictionary = null;
        } else if (i2 == 1) {
            fieldMappingDictionary = this.zzd;
        } else if (i2 == 2) {
            fieldMappingDictionary = this.zzd;
        } else {
            StringBuilder sb = new StringBuilder(34);
            sb.append("Invalid creation type: ");
            sb.append(i2);
            throw new IllegalStateException(sb.toString());
        }
        zzbky.zza(parcel, 3, (Parcelable) fieldMappingDictionary, i, false);
        zzbky.zza(parcel, zza2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0005, code lost:
        if (r0 != 1) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.os.Parcel zza() {
        /*
            r2 = this;
            int r0 = r2.zzf
            if (r0 == 0) goto L_0x0008
            r1 = 1
            if (r0 == r1) goto L_0x0010
            goto L_0x001a
        L_0x0008:
            android.os.Parcel r0 = r2.zzb
            int r0 = com.google.android.gms.internal.zzbky.zza(r0)
            r2.zzg = r0
        L_0x0010:
            android.os.Parcel r0 = r2.zzb
            int r1 = r2.zzg
            com.google.android.gms.internal.zzbky.zza(r0, r1)
            r0 = 2
            r2.zzf = r0
        L_0x001a:
            android.os.Parcel r0 = r2.zzb
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.zzl.zza():android.os.Parcel");
    }

    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        FieldMappingDictionary fieldMappingDictionary = this.zzd;
        if (fieldMappingDictionary == null) {
            return null;
        }
        return fieldMappingDictionary.getFieldMapping(this.zze);
    }

    public Object getValueObject(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public boolean isPrimitiveFieldSet(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    private final void zza(FastJsonResponse.Field<?, ?> field) {
        if (field.isValidSafeParcelableFieldId()) {
            Parcel parcel = this.zzb;
            if (parcel != null) {
                int i = this.zzf;
                if (i == 0) {
                    this.zzg = zzbky.zza(parcel);
                    this.zzf = 1;
                } else if (i == 1) {
                } else {
                    if (i != 2) {
                        throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
                    }
                    throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
                }
            } else {
                throw new IllegalStateException("Internal Parcel object is null.");
            }
        } else {
            throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        }
    }

    /* access modifiers changed from: protected */
    public void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), i);
    }

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
    /* access modifiers changed from: protected */
    public void setIntegersInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        zza(field);
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), iArr, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
     arg types: [android.os.Parcel, int, java.math.BigInteger, int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void */
    /* access modifiers changed from: protected */
    public void setBigIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, BigInteger bigInteger) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bigInteger, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
     arg types: [android.os.Parcel, int, java.math.BigInteger[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void */
    /* access modifiers changed from: protected */
    public void setBigIntegersInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        zza(field);
        int size = arrayList.size();
        BigInteger[] bigIntegerArr = new BigInteger[size];
        for (int i = 0; i < size; i++) {
            bigIntegerArr[i] = arrayList.get(i);
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bigIntegerArr, true);
    }

    /* access modifiers changed from: protected */
    public void setLongInternal(FastJsonResponse.Field<?, ?> field, String str, long j) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), j);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
     arg types: [android.os.Parcel, int, long[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void */
    /* access modifiers changed from: protected */
    public void setLongsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        zza(field);
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = arrayList.get(i).longValue();
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), jArr, true);
    }

    /* access modifiers changed from: protected */
    public void setFloatInternal(FastJsonResponse.Field<?, ?> field, String str, float f) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), f);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
     arg types: [android.os.Parcel, int, float[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void */
    /* access modifiers changed from: protected */
    public void setFloatsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        zza(field);
        int size = arrayList.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = arrayList.get(i).floatValue();
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), fArr, true);
    }

    /* access modifiers changed from: protected */
    public void setDoubleInternal(FastJsonResponse.Field<?, ?> field, String str, double d) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), d);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
     arg types: [android.os.Parcel, int, double[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void */
    /* access modifiers changed from: protected */
    public void setDoublesInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        zza(field);
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = arrayList.get(i).doubleValue();
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), dArr, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
     arg types: [android.os.Parcel, int, java.math.BigDecimal, int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void */
    /* access modifiers changed from: protected */
    public void setBigDecimalInternal(FastJsonResponse.Field<?, ?> field, String str, BigDecimal bigDecimal) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bigDecimal, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
     arg types: [android.os.Parcel, int, java.math.BigDecimal[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void */
    /* access modifiers changed from: protected */
    public void setBigDecimalsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        zza(field);
        int size = arrayList.size();
        BigDecimal[] bigDecimalArr = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            bigDecimalArr[i] = arrayList.get(i);
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bigDecimalArr, true);
    }

    /* access modifiers changed from: protected */
    public void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), z);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
     arg types: [android.os.Parcel, int, boolean[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void */
    /* access modifiers changed from: protected */
    public void setBooleansInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        zza(field);
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = arrayList.get(i).booleanValue();
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), zArr, true);
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
    /* access modifiers changed from: protected */
    public void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), str2, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
     arg types: [android.os.Parcel, int, java.lang.String[], int]
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void */
    /* access modifiers changed from: protected */
    public void setStringsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<String> arrayList) {
        zza(field);
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = arrayList.get(i);
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), strArr, true);
    }

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
    /* access modifiers changed from: protected */
    public void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String str, byte[] bArr) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bArr, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
     arg types: [android.os.Parcel, int, android.os.Bundle, int]
     candidates:
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void */
    /* access modifiers changed from: protected */
    public void setStringMapInternal(FastJsonResponse.Field<?, ?> field, String str, Map<String, String> map) {
        zza(field);
        Bundle bundle = new Bundle();
        for (String next : map.keySet()) {
            bundle.putString(next, map.get(next));
        }
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), bundle, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
     arg types: [android.os.Parcel, int, android.os.Parcel, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void */
    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        zza(field);
        zzbky.zza(this.zzb, field.getSafeParcelableFieldId(), ((zzl) t).zza(), true);
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        zza(field);
        ArrayList arrayList2 = new ArrayList();
        arrayList.size();
        ArrayList arrayList3 = arrayList;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            arrayList2.add(((zzl) ((FastJsonResponse) obj)).zza());
        }
        zzbky.zzd(this.zzb, field.getSafeParcelableFieldId(), arrayList2, true);
    }

    public String toString() {
        zzau.zza(this.zzd, "Cannot convert to JSON on client side.");
        Parcel zza2 = zza();
        zza2.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zza(sb, this.zzd.getFieldMapping(this.zze), zza2);
        return sb.toString();
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Type inference failed for: r7v37, types: [android.os.Parcel] */
    /* JADX WARN: Type inference failed for: r8v28, types: [android.os.Parcel] */
    /* JADX WARN: Type inference failed for: r13v1, types: [android.os.Parcel[]] */
    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: ?
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    private final void zza(java.lang.StringBuilder r17, java.util.Map<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> r18, android.os.Parcel r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r19
            android.util.SparseArray r3 = new android.util.SparseArray
            r3.<init>()
            java.util.Set r4 = r18.entrySet()
            java.util.Iterator r4 = r4.iterator()
        L_0x0014:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x002e
            java.lang.Object r5 = r4.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getValue()
            com.google.android.gms.common.server.response.FastJsonResponse$Field r6 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r6
            int r6 = r6.getSafeParcelableFieldId()
            r3.put(r6, r5)
            goto L_0x0014
        L_0x002e:
            r4 = 123(0x7b, float:1.72E-43)
            r1.append(r4)
            int r4 = com.google.android.gms.internal.zzbkw.zza(r19)
            r7 = 0
        L_0x003a:
            int r8 = r19.dataPosition()
            if (r8 >= r4) goto L_0x03e9
            int r8 = r19.readInt()
            r9 = 65535(0xffff, float:9.1834E-41)
            r9 = r9 & r8
            java.lang.Object r9 = r3.get(r9)
            java.util.Map$Entry r9 = (java.util.Map.Entry) r9
            if (r9 == 0) goto L_0x03e6
            java.lang.String r10 = ","
            if (r7 == 0) goto L_0x005b
            r1.append(r10)
        L_0x005b:
            java.lang.Object r7 = r9.getKey()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r9 = r9.getValue()
            com.google.android.gms.common.server.response.FastJsonResponse$Field r9 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r9
            java.lang.String r11 = "\""
            r1.append(r11)
            r1.append(r7)
            java.lang.String r7 = "\":"
            r1.append(r7)
            boolean r7 = r9.hasConverter()
            if (r7 == 0) goto L_0x016b
            int r7 = r9.getTypeOut()
            switch(r7) {
                case 0: goto L_0x0159;
                case 1: goto L_0x014b;
                case 2: goto L_0x0139;
                case 3: goto L_0x0127;
                case 4: goto L_0x0115;
                case 5: goto L_0x0107;
                case 6: goto L_0x00f5;
                case 7: goto L_0x00e7;
                case 8: goto L_0x00d9;
                case 9: goto L_0x00d9;
                case 10: goto L_0x00a7;
                case 11: goto L_0x009f;
                default: goto L_0x0082;
            }
        L_0x0082:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            int r2 = r9.getTypeOut()
            r3 = 36
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "Unknown field out type = "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r1.<init>(r2)
            throw r1
        L_0x009f:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Method does not accept concrete type."
            r1.<init>(r2)
            throw r1
        L_0x00a7:
            android.os.Bundle r7 = com.google.android.gms.internal.zzbkw.zzs(r2, r8)
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            java.util.Set r10 = r7.keySet()
            java.util.Iterator r10 = r10.iterator()
        L_0x00b9:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x00cd
            java.lang.Object r11 = r10.next()
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r12 = r7.getString(r11)
            r8.put(r11, r12)
            goto L_0x00b9
        L_0x00cd:
            java.lang.Object r7 = r0.getOriginalValue(r9, r8)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x00d9:
            byte[] r7 = com.google.android.gms.internal.zzbkw.zzt(r2, r8)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x00e7:
            java.lang.String r7 = com.google.android.gms.internal.zzbkw.zzq(r2, r8)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x00f5:
            boolean r7 = com.google.android.gms.internal.zzbkw.zzc(r2, r8)
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x0107:
            java.math.BigDecimal r7 = com.google.android.gms.internal.zzbkw.zzp(r2, r8)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x0115:
            double r7 = com.google.android.gms.internal.zzbkw.zzn(r2, r8)
            java.lang.Double r7 = java.lang.Double.valueOf(r7)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x0127:
            float r7 = com.google.android.gms.internal.zzbkw.zzl(r2, r8)
            java.lang.Float r7 = java.lang.Float.valueOf(r7)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x0139:
            long r7 = com.google.android.gms.internal.zzbkw.zzi(r2, r8)
            java.lang.Long r7 = java.lang.Long.valueOf(r7)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x014b:
            java.math.BigInteger r7 = com.google.android.gms.internal.zzbkw.zzk(r2, r8)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x0159:
            int r7 = com.google.android.gms.internal.zzbkw.zzg(r2, r8)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            java.lang.Object r7 = r0.getOriginalValue(r9, r7)
            r0.zza(r1, r9, r7)
            r6 = 0
            goto L_0x03e2
        L_0x016b:
            boolean r7 = r9.isTypeOutArray()
            if (r7 == 0) goto L_0x02f7
            java.lang.String r7 = "["
            r1.append(r7)
            int r7 = r9.getTypeOut()
            r12 = 0
            switch(r7) {
                case 0: goto L_0x02d4;
                case 1: goto L_0x02a7;
                case 2: goto L_0x028c;
                case 3: goto L_0x0271;
                case 4: goto L_0x0255;
                case 5: goto L_0x021e;
                case 6: goto L_0x0202;
                case 7: goto L_0x01e4;
                case 8: goto L_0x01dc;
                case 9: goto L_0x01dc;
                case 10: goto L_0x01dc;
                case 11: goto L_0x0187;
                default: goto L_0x017f;
            }
        L_0x017f:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unknown field type out."
            r1.<init>(r2)
            throw r1
        L_0x0187:
            int r7 = com.google.android.gms.internal.zzbkw.zza(r2, r8)
            int r8 = r19.dataPosition()
            if (r7 != 0) goto L_0x0193
            goto L_0x01be
        L_0x0193:
            int r11 = r19.readInt()
            android.os.Parcel[] r13 = new android.os.Parcel[r11]
            r14 = 0
        L_0x019a:
            if (r14 >= r11) goto L_0x01b9
            int r15 = r19.readInt()
            if (r15 == 0) goto L_0x01b4
            int r5 = r19.dataPosition()
            android.os.Parcel r6 = android.os.Parcel.obtain()
            r6.appendFrom(r2, r5, r15)
            r13[r14] = r6
            int r5 = r5 + r15
            r2.setDataPosition(r5)
            goto L_0x01b6
        L_0x01b4:
            r13[r14] = r12
        L_0x01b6:
            int r14 = r14 + 1
            goto L_0x019a
        L_0x01b9:
            int r8 = r8 + r7
            r2.setDataPosition(r8)
            r12 = r13
        L_0x01be:
            int r5 = r12.length
            r6 = 0
        L_0x01c1:
            if (r6 >= r5) goto L_0x01da
            if (r6 <= 0) goto L_0x01c8
            r1.append(r10)
        L_0x01c8:
            r7 = r12[r6]
            r8 = 0
            r7.setDataPosition(r8)
            java.util.Map r7 = r9.getConcreteTypeFieldMappingFromDictionary()
            r8 = r12[r6]
            r0.zza(r1, r7, r8)
            int r6 = r6 + 1
            goto L_0x01c1
        L_0x01da:
            goto L_0x02ef
        L_0x01dc:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            java.lang.String r2 = "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported"
            r1.<init>(r2)
            throw r1
        L_0x01e4:
            java.lang.String[] r5 = com.google.android.gms.internal.zzbkw.zzaa(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x01eb:
            if (r7 >= r6) goto L_0x0200
            if (r7 == 0) goto L_0x01f2
            r1.append(r10)
        L_0x01f2:
            r1.append(r11)
            r8 = r5[r7]
            r1.append(r8)
            r1.append(r11)
            int r7 = r7 + 1
            goto L_0x01eb
        L_0x0200:
            goto L_0x02ef
        L_0x0202:
            boolean[] r5 = com.google.android.gms.internal.zzbkw.zzv(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x0209:
            if (r7 >= r6) goto L_0x021c
            if (r7 == 0) goto L_0x0210
            r1.append(r10)
        L_0x0210:
            boolean r8 = r5[r7]
            java.lang.String r8 = java.lang.Boolean.toString(r8)
            r1.append(r8)
            int r7 = r7 + 1
            goto L_0x0209
        L_0x021c:
            goto L_0x02ef
        L_0x021e:
            int r5 = com.google.android.gms.internal.zzbkw.zza(r2, r8)
            int r6 = r19.dataPosition()
            if (r5 != 0) goto L_0x022a
            goto L_0x024f
        L_0x022a:
            int r7 = r19.readInt()
            java.math.BigDecimal[] r12 = new java.math.BigDecimal[r7]
            r8 = 0
        L_0x0231:
            if (r8 >= r7) goto L_0x024a
            byte[] r9 = r19.createByteArray()
            int r10 = r19.readInt()
            java.math.BigDecimal r11 = new java.math.BigDecimal
            java.math.BigInteger r13 = new java.math.BigInteger
            r13.<init>(r9)
            r11.<init>(r13, r10)
            r12[r8] = r11
            int r8 = r8 + 1
            goto L_0x0231
        L_0x024a:
            int r6 = r6 + r5
            r2.setDataPosition(r6)
        L_0x024f:
            com.google.android.gms.common.util.zzb.zza(r1, r12)
            goto L_0x02ef
        L_0x0255:
            double[] r5 = com.google.android.gms.internal.zzbkw.zzz(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x025c:
            if (r7 >= r6) goto L_0x026f
            if (r7 == 0) goto L_0x0263
            r1.append(r10)
        L_0x0263:
            r8 = r5[r7]
            java.lang.String r8 = java.lang.Double.toString(r8)
            r1.append(r8)
            int r7 = r7 + 1
            goto L_0x025c
        L_0x026f:
            goto L_0x02ef
        L_0x0271:
            float[] r5 = com.google.android.gms.internal.zzbkw.zzy(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x0278:
            if (r7 >= r6) goto L_0x028b
            if (r7 == 0) goto L_0x027f
            r1.append(r10)
        L_0x027f:
            r8 = r5[r7]
            java.lang.String r8 = java.lang.Float.toString(r8)
            r1.append(r8)
            int r7 = r7 + 1
            goto L_0x0278
        L_0x028b:
            goto L_0x02ef
        L_0x028c:
            long[] r5 = com.google.android.gms.internal.zzbkw.zzx(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x0293:
            if (r7 >= r6) goto L_0x02a6
            if (r7 == 0) goto L_0x029a
            r1.append(r10)
        L_0x029a:
            r8 = r5[r7]
            java.lang.String r8 = java.lang.Long.toString(r8)
            r1.append(r8)
            int r7 = r7 + 1
            goto L_0x0293
        L_0x02a6:
            goto L_0x02ef
        L_0x02a7:
            int r5 = com.google.android.gms.internal.zzbkw.zza(r2, r8)
            int r6 = r19.dataPosition()
            if (r5 != 0) goto L_0x02b3
            goto L_0x02cf
        L_0x02b3:
            int r7 = r19.readInt()
            java.math.BigInteger[] r12 = new java.math.BigInteger[r7]
            r8 = 0
        L_0x02ba:
            if (r8 >= r7) goto L_0x02ca
            java.math.BigInteger r9 = new java.math.BigInteger
            byte[] r10 = r19.createByteArray()
            r9.<init>(r10)
            r12[r8] = r9
            int r8 = r8 + 1
            goto L_0x02ba
        L_0x02ca:
            int r6 = r6 + r5
            r2.setDataPosition(r6)
        L_0x02cf:
            com.google.android.gms.common.util.zzb.zza(r1, r12)
            goto L_0x02ef
        L_0x02d4:
            int[] r5 = com.google.android.gms.internal.zzbkw.zzw(r2, r8)
            int r6 = r5.length
            r7 = 0
        L_0x02db:
            if (r7 >= r6) goto L_0x02ee
            if (r7 == 0) goto L_0x02e2
            r1.append(r10)
        L_0x02e2:
            r8 = r5[r7]
            java.lang.String r8 = java.lang.Integer.toString(r8)
            r1.append(r8)
            int r7 = r7 + 1
            goto L_0x02db
        L_0x02ee:
        L_0x02ef:
            java.lang.String r5 = "]"
            r1.append(r5)
            r6 = 0
            goto L_0x03e2
        L_0x02f7:
            int r5 = r9.getTypeOut()
            switch(r5) {
                case 0: goto L_0x03d9;
                case 1: goto L_0x03d0;
                case 2: goto L_0x03c7;
                case 3: goto L_0x03be;
                case 4: goto L_0x03b5;
                case 5: goto L_0x03ac;
                case 6: goto L_0x03a3;
                case 7: goto L_0x0390;
                case 8: goto L_0x037d;
                case 9: goto L_0x036a;
                case 10: goto L_0x0317;
                case 11: goto L_0x0306;
                default: goto L_0x02fe;
            }
        L_0x02fe:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unknown field type out"
            r1.<init>(r2)
            throw r1
        L_0x0306:
            android.os.Parcel r5 = com.google.android.gms.internal.zzbkw.zzad(r2, r8)
            r6 = 0
            r5.setDataPosition(r6)
            java.util.Map r7 = r9.getConcreteTypeFieldMappingFromDictionary()
            r0.zza(r1, r7, r5)
            goto L_0x03e2
        L_0x0317:
            r6 = 0
            android.os.Bundle r5 = com.google.android.gms.internal.zzbkw.zzs(r2, r8)
            java.util.Set r7 = r5.keySet()
            r7.size()
            java.lang.String r8 = "{"
            r1.append(r8)
            java.util.Iterator r7 = r7.iterator()
            r8 = 1
        L_0x032f:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x0362
            java.lang.Object r9 = r7.next()
            java.lang.String r9 = (java.lang.String) r9
            if (r8 != 0) goto L_0x0340
            r1.append(r10)
        L_0x0340:
            r1.append(r11)
            r1.append(r9)
            r1.append(r11)
            java.lang.String r8 = ":"
            r1.append(r8)
            r1.append(r11)
            java.lang.String r8 = r5.getString(r9)
            java.lang.String r8 = com.google.android.gms.common.util.zzn.zzb(r8)
            r1.append(r8)
            r1.append(r11)
            r8 = 0
            goto L_0x032f
        L_0x0362:
            java.lang.String r5 = "}"
            r1.append(r5)
            goto L_0x03e2
        L_0x036a:
            r6 = 0
            byte[] r5 = com.google.android.gms.internal.zzbkw.zzt(r2, r8)
            r1.append(r11)
            java.lang.String r5 = com.google.android.gms.common.util.zzc.zzb(r5)
            r1.append(r5)
            r1.append(r11)
            goto L_0x03e2
        L_0x037d:
            r6 = 0
            byte[] r5 = com.google.android.gms.internal.zzbkw.zzt(r2, r8)
            r1.append(r11)
            java.lang.String r5 = com.google.android.gms.common.util.zzc.zza(r5)
            r1.append(r5)
            r1.append(r11)
            goto L_0x03e2
        L_0x0390:
            r6 = 0
            java.lang.String r5 = com.google.android.gms.internal.zzbkw.zzq(r2, r8)
            r1.append(r11)
            java.lang.String r5 = com.google.android.gms.common.util.zzn.zzb(r5)
            r1.append(r5)
            r1.append(r11)
            goto L_0x03e2
        L_0x03a3:
            r6 = 0
            boolean r5 = com.google.android.gms.internal.zzbkw.zzc(r2, r8)
            r1.append(r5)
            goto L_0x03e2
        L_0x03ac:
            r6 = 0
            java.math.BigDecimal r5 = com.google.android.gms.internal.zzbkw.zzp(r2, r8)
            r1.append(r5)
            goto L_0x03e2
        L_0x03b5:
            r6 = 0
            double r7 = com.google.android.gms.internal.zzbkw.zzn(r2, r8)
            r1.append(r7)
            goto L_0x03e2
        L_0x03be:
            r6 = 0
            float r5 = com.google.android.gms.internal.zzbkw.zzl(r2, r8)
            r1.append(r5)
            goto L_0x03e2
        L_0x03c7:
            r6 = 0
            long r7 = com.google.android.gms.internal.zzbkw.zzi(r2, r8)
            r1.append(r7)
            goto L_0x03e2
        L_0x03d0:
            r6 = 0
            java.math.BigInteger r5 = com.google.android.gms.internal.zzbkw.zzk(r2, r8)
            r1.append(r5)
            goto L_0x03e2
        L_0x03d9:
            r6 = 0
            int r5 = com.google.android.gms.internal.zzbkw.zzg(r2, r8)
            r1.append(r5)
        L_0x03e2:
            r7 = 1
            goto L_0x003a
        L_0x03e6:
            r6 = 0
            goto L_0x003a
        L_0x03e9:
            int r3 = r19.dataPosition()
            if (r3 != r4) goto L_0x03f5
            r2 = 125(0x7d, float:1.75E-43)
            r1.append(r2)
            return
        L_0x03f5:
            com.google.android.gms.internal.zzbkx r1 = new com.google.android.gms.internal.zzbkx
            r3 = 37
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r3)
            java.lang.String r3 = "Overread allowed size end="
            r5.append(r3)
            r5.append(r4)
            java.lang.String r3 = r5.toString()
            r1.<init>(r3, r2)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.zzl.zza(java.lang.StringBuilder, java.util.Map, android.os.Parcel):void");
    }

    private final void zza(StringBuilder sb, FastJsonResponse.Field<?, ?> field, Object obj) {
        if (field.isTypeInArray()) {
            ArrayList arrayList = (ArrayList) obj;
            sb.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                zza(sb, field.getTypeIn(), arrayList.get(i));
            }
            sb.append("]");
            return;
        }
        zza(sb, field.getTypeIn(), obj);
    }

    private static void zza(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"");
                sb.append(zzn.zzb(obj.toString()));
                sb.append("\"");
                return;
            case 8:
                sb.append("\"");
                sb.append(zzc.zza((byte[]) obj));
                sb.append("\"");
                return;
            case 9:
                sb.append("\"");
                sb.append(zzc.zzb((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                zzo.zza(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder sb2 = new StringBuilder(26);
                sb2.append("Unknown type = ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
        }
    }
}
