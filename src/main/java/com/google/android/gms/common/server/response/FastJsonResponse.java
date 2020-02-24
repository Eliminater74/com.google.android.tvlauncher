package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.server.response.FastParser;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import com.google.android.gms.internal.zzbly;
import com.google.android.gms.people.PeopleConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public abstract class FastJsonResponse {
    protected static final String QUOTE = "\"";
    private int zza;
    private byte[] zzb;
    private boolean zzc;

    public interface FieldConverter<I, O> {
        O convert(I i);

        I convertBack(O o);

        int getTypeIn();

        int getTypeOut();
    }

    public abstract Map<String, Field<?, ?>> getFieldMappings();

    /* access modifiers changed from: protected */
    public abstract Object getValueObject(String str);

    /* access modifiers changed from: protected */
    public abstract boolean isPrimitiveFieldSet(String str);

    public <T extends FastJsonResponse> void parseNetworkResponse(int i, byte[] bArr) throws FastParser.ParseException {
        this.zza = i;
        this.zzb = bArr;
        this.zzc = true;
        InputStream unzippedStream = getUnzippedStream(bArr);
        try {
            new FastParser().parse(unzippedStream, this);
            try {
                unzippedStream.close();
            } catch (IOException e) {
            }
        } catch (Throwable th) {
            try {
                unzippedStream.close();
            } catch (IOException e2) {
            }
            throw th;
        }
    }

    public static InputStream getUnzippedStream(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        if (IOUtils.isGzipByteBuffer(bArr)) {
            try {
                return new GZIPInputStream(byteArrayInputStream);
            } catch (IOException e) {
            }
        }
        return byteArrayInputStream;
    }

    public static class Field<I, O> extends zzbkv {
        public static final FieldCreator CREATOR = new FieldCreator();
        protected final Class<? extends FastJsonResponse> mConcreteType;
        protected final String mConcreteTypeName;
        protected final String mOutputFieldName;
        protected final int mSafeParcelableFieldId;
        protected final int mTypeIn;
        protected final boolean mTypeInArray;
        protected final int mTypeOut;
        protected final boolean mTypeOutArray;
        private final int zza;
        private FieldMappingDictionary zzb;
        /* access modifiers changed from: private */
        public FieldConverter<I, O> zzc;

        Field(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, zzbly zzbly) {
            this.zza = i;
            this.mTypeIn = i2;
            this.mTypeInArray = z;
            this.mTypeOut = i3;
            this.mTypeOutArray = z2;
            this.mOutputFieldName = str;
            this.mSafeParcelableFieldId = i4;
            if (str2 == null) {
                this.mConcreteType = null;
                this.mConcreteTypeName = null;
            } else {
                this.mConcreteType = zzl.class;
                this.mConcreteTypeName = str2;
            }
            if (zzbly == null) {
                this.zzc = null;
            } else {
                this.zzc = zzbly.zza();
            }
        }

        protected Field(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends FastJsonResponse> cls, FieldConverter<I, O> fieldConverter) {
            this.zza = 1;
            this.mTypeIn = i;
            this.mTypeInArray = z;
            this.mTypeOut = i2;
            this.mTypeOutArray = z2;
            this.mOutputFieldName = str;
            this.mSafeParcelableFieldId = i3;
            this.mConcreteType = cls;
            if (cls == null) {
                this.mConcreteTypeName = null;
            } else {
                this.mConcreteTypeName = cls.getCanonicalName();
            }
            this.zzc = fieldConverter;
        }

        public Field<I, O> copyForDictionary() {
            return new Field(this.zza, this.mTypeIn, this.mTypeInArray, this.mTypeOut, this.mTypeOutArray, this.mOutputFieldName, this.mSafeParcelableFieldId, this.mConcreteTypeName, zzb());
        }

        public int getVersionCode() {
            return this.zza;
        }

        public int getTypeIn() {
            return this.mTypeIn;
        }

        public boolean isTypeInArray() {
            return this.mTypeInArray;
        }

        public int getTypeOut() {
            return this.mTypeOut;
        }

        public boolean isTypeOutArray() {
            return this.mTypeOutArray;
        }

        public String getOutputFieldName() {
            return this.mOutputFieldName;
        }

        public int getSafeParcelableFieldId() {
            return this.mSafeParcelableFieldId;
        }

        public boolean isValidSafeParcelableFieldId() {
            return this.mSafeParcelableFieldId != -1;
        }

        public Class<? extends FastJsonResponse> getConcreteType() {
            return this.mConcreteType;
        }

        private final String zza() {
            String str = this.mConcreteTypeName;
            if (str == null) {
                return null;
            }
            return str;
        }

        public boolean hasConverter() {
            return this.zzc != null;
        }

        public void setFieldMappingDictionary(FieldMappingDictionary fieldMappingDictionary) {
            this.zzb = fieldMappingDictionary;
        }

        private final zzbly zzb() {
            FieldConverter<I, O> fieldConverter = this.zzc;
            if (fieldConverter == null) {
                return null;
            }
            return zzbly.zza(fieldConverter);
        }

        public FastJsonResponse newConcreteTypeInstance() throws InstantiationException, IllegalAccessException {
            Class<? extends FastJsonResponse> cls = this.mConcreteType;
            if (cls != zzl.class) {
                return (FastJsonResponse) cls.newInstance();
            }
            zzau.zza(this.zzb, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new zzl(this.zzb, this.mConcreteTypeName);
        }

        public Map<String, Field<?, ?>> getConcreteTypeFieldMappingFromDictionary() {
            zzau.zza((Object) this.mConcreteTypeName);
            zzau.zza(this.zzb);
            return this.zzb.getFieldMapping(this.mConcreteTypeName);
        }

        public O convert(I i) {
            return this.zzc.convert(i);
        }

        public I convertBack(O o) {
            return this.zzc.convertBack(o);
        }

        public static Field<Integer, Integer> forInteger(String str, int i) {
            return new Field(0, false, 0, false, str, i, null, null);
        }

        public static Field<ArrayList<Integer>, ArrayList<Integer>> forIntegers(String str, int i) {
            return new Field(0, true, 0, true, str, i, null, null);
        }

        public static Field<BigInteger, BigInteger> forBigInteger(String str, int i) {
            return new Field(1, false, 1, false, str, i, null, null);
        }

        public static Field<ArrayList<BigInteger>, ArrayList<BigInteger>> forBigIntegers(String str, int i) {
            return new Field(0, true, 1, true, str, i, null, null);
        }

        public static Field<Long, Long> forLong(String str, int i) {
            return new Field(2, false, 2, false, str, i, null, null);
        }

        public static Field<ArrayList<Long>, ArrayList<Long>> forLongs(String str, int i) {
            return new Field(2, true, 2, true, str, i, null, null);
        }

        public static Field<Float, Float> forFloat(String str, int i) {
            return new Field(3, false, 3, false, str, i, null, null);
        }

        public static Field<ArrayList<Float>, ArrayList<Float>> forFloats(String str, int i) {
            return new Field(3, true, 3, true, str, i, null, null);
        }

        public static Field<Double, Double> forDouble(String str, int i) {
            return new Field(4, false, 4, false, str, i, null, null);
        }

        public static Field<ArrayList<Double>, ArrayList<Double>> forDoubles(String str, int i) {
            return new Field(4, true, 4, true, str, i, null, null);
        }

        public static Field<BigDecimal, BigDecimal> forBigDecimal(String str, int i) {
            return new Field(5, false, 5, false, str, i, null, null);
        }

        public static Field<ArrayList<BigDecimal>, ArrayList<BigDecimal>> forBigDecimals(String str, int i) {
            return new Field(5, true, 5, true, str, i, null, null);
        }

        public static Field<Boolean, Boolean> forBoolean(String str, int i) {
            return new Field(6, false, 6, false, str, i, null, null);
        }

        public static Field<ArrayList<Boolean>, ArrayList<Boolean>> forBooleans(String str, int i) {
            return new Field(6, true, 6, true, str, i, null, null);
        }

        public static Field<String, String> forString(String str, int i) {
            return new Field(7, false, 7, false, str, i, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> forStrings(String str, int i) {
            return new Field(7, true, 7, true, str, i, null, null);
        }

        public static Field<byte[], byte[]> forBase64(String str, int i) {
            return new Field(8, false, 8, false, str, i, null, null);
        }

        public static Field<byte[], byte[]> forBase64UrlSafe(String str, int i) {
            return new Field(9, false, 9, false, str, i, null, null);
        }

        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(String str, int i) {
            return new Field(10, false, 10, false, str, i, null, null);
        }

        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(String str, int i, Class<T> cls) {
            return new Field(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String str, int i, Class<T> cls) {
            return new Field(11, true, 11, true, str, i, cls, null);
        }

        public static <T extends FieldConverter> Field withConverter(String str, int i, Class<T> cls, boolean z) {
            try {
                return withConverter(str, i, (FieldConverter) cls.newInstance(), z);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }

        public static Field withConverter(String str, FieldConverter<?, ?> fieldConverter, boolean z) {
            return withConverter(str, -1, fieldConverter, z);
        }

        public static Field withConverter(String str, int i, FieldConverter<?, ?> fieldConverter, boolean z) {
            return new Field(fieldConverter.getTypeIn(), z, fieldConverter.getTypeOut(), false, str, i, null, fieldConverter);
        }

        public static Field<Integer, Integer> forInteger(String str) {
            return new Field(0, false, 0, false, str, -1, null, null);
        }

        public static Field<ArrayList<Integer>, ArrayList<Integer>> forIntegers(String str) {
            return new Field(0, true, 0, true, str, -1, null, null);
        }

        public static Field<BigInteger, BigInteger> forBigInteger(String str) {
            return new Field(1, false, 1, false, str, -1, null, null);
        }

        public static Field<ArrayList<BigInteger>, ArrayList<BigInteger>> forBigIntegers(String str) {
            return new Field(0, true, 1, true, str, -1, null, null);
        }

        public static Field<Long, Long> forLong(String str) {
            return new Field(2, false, 2, false, str, -1, null, null);
        }

        public static Field<ArrayList<Long>, ArrayList<Long>> forLongs(String str) {
            return new Field(2, true, 2, true, str, -1, null, null);
        }

        public static Field<Float, Float> forFloat(String str) {
            return new Field(3, false, 3, false, str, -1, null, null);
        }

        public static Field<ArrayList<Float>, ArrayList<Float>> forFloats(String str) {
            return new Field(3, true, 3, true, str, -1, null, null);
        }

        public static Field<Double, Double> forDouble(String str) {
            return new Field(4, false, 4, false, str, -1, null, null);
        }

        public static Field<ArrayList<Double>, ArrayList<Double>> forDoubles(String str) {
            return new Field(4, true, 4, true, str, -1, null, null);
        }

        public static Field<BigDecimal, BigDecimal> forBigDecimal(String str) {
            return new Field(5, false, 5, false, str, -1, null, null);
        }

        public static Field<ArrayList<BigDecimal>, ArrayList<BigDecimal>> forBigDecimals(String str) {
            return new Field(5, true, 5, true, str, -1, null, null);
        }

        public static Field<Boolean, Boolean> forBoolean(String str) {
            return new Field(6, false, 6, false, str, -1, null, null);
        }

        public static Field<ArrayList<Boolean>, ArrayList<Boolean>> forBooleans(String str) {
            return new Field(6, true, 6, true, str, -1, null, null);
        }

        public static Field<String, String> forString(String str) {
            return new Field(7, false, 7, false, str, -1, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> forStrings(String str) {
            return new Field(7, true, 7, true, str, -1, null, null);
        }

        public static Field<byte[], byte[]> forBase64(String str) {
            return new Field(8, false, 8, false, str, -1, null, null);
        }

        public static Field<byte[], byte[]> forBase64UrlSafe(String str) {
            return new Field(9, false, 9, false, str, -1, null, null);
        }

        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(String str) {
            return new Field(10, false, 10, false, str, -1, null, null);
        }

        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(String str, Class<T> cls) {
            return new Field(11, false, 11, false, str, -1, cls, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String str, Class<T> cls) {
            return new Field(11, true, 11, true, str, -1, cls, null);
        }

        public static <T extends FieldConverter> Field withConverter(String str, Class<T> cls, boolean z) {
            return withConverter(str, -1, cls, z);
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
         method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
         arg types: [android.os.Parcel, int, com.google.android.gms.internal.zzbly, int, int]
         candidates:
          com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
          com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
        public void writeToParcel(Parcel parcel, int i) {
            int zza2 = zzbky.zza(parcel);
            zzbky.zza(parcel, 1, getVersionCode());
            zzbky.zza(parcel, 2, getTypeIn());
            zzbky.zza(parcel, 3, isTypeInArray());
            zzbky.zza(parcel, 4, getTypeOut());
            zzbky.zza(parcel, 5, isTypeOutArray());
            zzbky.zza(parcel, 6, getOutputFieldName(), false);
            zzbky.zza(parcel, 7, getSafeParcelableFieldId());
            zzbky.zza(parcel, 8, zza(), false);
            zzbky.zza(parcel, 9, (Parcelable) zzb(), i, false);
            zzbky.zza(parcel, zza2);
        }

        public String toString() {
            zzam zza2 = zzak.zza(this).zza("versionCode", Integer.valueOf(this.zza)).zza("typeIn", Integer.valueOf(this.mTypeIn)).zza("typeInArray", Boolean.valueOf(this.mTypeInArray)).zza("typeOut", Integer.valueOf(this.mTypeOut)).zza("typeOutArray", Boolean.valueOf(this.mTypeOutArray)).zza("outputFieldName", this.mOutputFieldName).zza("safeParcelFieldId", Integer.valueOf(this.mSafeParcelableFieldId)).zza("concreteTypeName", zza());
            Class<? extends FastJsonResponse> concreteType = getConcreteType();
            if (concreteType != null) {
                zza2.zza("concreteType.class", concreteType.getCanonicalName());
            }
            FieldConverter<I, O> fieldConverter = this.zzc;
            if (fieldConverter != null) {
                zza2.zza("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return zza2.toString();
        }
    }

    public int getResponseCode() {
        zzau.zza(this.zzc);
        return this.zza;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0044 A[SYNTHETIC, Splitter:B:23:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004c A[SYNTHETIC, Splitter:B:28:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getResponseBody() {
        /*
            r8 = this;
            boolean r0 = r8.zzc
            com.google.android.gms.common.internal.zzau.zza(r0)
            r0 = 0
            java.util.zip.GZIPInputStream r1 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x003f }
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x003f }
            byte[] r3 = r8.zzb     // Catch:{ IOException -> 0x003f }
            r2.<init>(r3)     // Catch:{ IOException -> 0x003f }
            r1.<init>(r2)     // Catch:{ IOException -> 0x003f }
            r0 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r0]     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            r3.<init>()     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
        L_0x001c:
            r4 = 0
            int r5 = r1.read(r2, r4, r0)     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            r6 = -1
            if (r5 == r6) goto L_0x0028
            r3.write(r2, r4, r5)     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            goto L_0x001c
        L_0x0028:
            r3.flush()     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            byte[] r0 = r3.toByteArray()     // Catch:{ IOException -> 0x0037, all -> 0x0035 }
            r1.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x0034
        L_0x0033:
            r1 = move-exception
        L_0x0034:
            return r0
        L_0x0035:
            r0 = move-exception
            goto L_0x004a
        L_0x0037:
            r0 = move-exception
            r0 = r1
            goto L_0x0040
        L_0x003a:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x004a
        L_0x003f:
            r1 = move-exception
        L_0x0040:
            byte[] r1 = r8.zzb     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x0049
            r0.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x0049
        L_0x0048:
            r0 = move-exception
        L_0x0049:
            return r1
        L_0x004a:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ IOException -> 0x0050 }
            goto L_0x0051
        L_0x0050:
            r1 = move-exception
        L_0x0051:
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastJsonResponse.getResponseBody():byte[]");
    }

    /* access modifiers changed from: protected */
    public boolean isFieldSet(Field field) {
        if (field.getTypeOut() != 11) {
            return isPrimitiveFieldSet(field.getOutputFieldName());
        }
        if (field.isTypeOutArray()) {
            return isConcreteTypeArrayFieldSet(field.getOutputFieldName());
        }
        return isConcreteTypeFieldSet(field.getOutputFieldName());
    }

    /* access modifiers changed from: protected */
    public boolean isConcreteTypeFieldSet(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    /* access modifiers changed from: protected */
    public boolean isConcreteTypeArrayFieldSet(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    private final <I, O> void zza(Field<I, O> field, I i) {
        String outputFieldName = field.getOutputFieldName();
        O convert = field.convert(i);
        switch (field.getTypeOut()) {
            case 0:
                if (zza(outputFieldName, convert)) {
                    setIntegerInternal(field, outputFieldName, ((Integer) convert).intValue());
                    return;
                }
                return;
            case 1:
                setBigIntegerInternal(field, outputFieldName, (BigInteger) convert);
                return;
            case 2:
                if (zza(outputFieldName, convert)) {
                    setLongInternal(field, outputFieldName, ((Long) convert).longValue());
                    return;
                }
                return;
            case 3:
            default:
                int typeOut = field.getTypeOut();
                StringBuilder sb = new StringBuilder(44);
                sb.append("Unsupported type for conversion: ");
                sb.append(typeOut);
                throw new IllegalStateException(sb.toString());
            case 4:
                if (zza(outputFieldName, convert)) {
                    setDoubleInternal(field, outputFieldName, ((Double) convert).doubleValue());
                    return;
                }
                return;
            case 5:
                setBigDecimalInternal(field, outputFieldName, (BigDecimal) convert);
                return;
            case 6:
                if (zza(outputFieldName, convert)) {
                    setBooleanInternal(field, outputFieldName, ((Boolean) convert).booleanValue());
                    return;
                }
                return;
            case 7:
                setStringInternal(field, outputFieldName, (String) convert);
                return;
            case 8:
            case 9:
                if (zza(outputFieldName, convert)) {
                    setDecodedBytesInternal(field, outputFieldName, (byte[]) convert);
                    return;
                }
                return;
        }
    }

    /* access modifiers changed from: protected */
    public <O, I> I getOriginalValue(Field<I, O> field, Object obj) {
        if (field.zzc != null) {
            return field.convertBack(obj);
        }
        return obj;
    }

    public final <O> void setInteger(Field<Integer, O> field, int i) {
        if (field.zzc != null) {
            zza(field, Integer.valueOf(i));
        } else {
            setIntegerInternal(field, field.getOutputFieldName(), i);
        }
    }

    public final <O> void setIntegers(Field<ArrayList<Integer>, O> field, ArrayList<Integer> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setIntegersInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setBigInteger(Field<BigInteger, O> field, BigInteger bigInteger) {
        if (field.zzc != null) {
            zza(field, bigInteger);
        } else {
            setBigIntegerInternal(field, field.getOutputFieldName(), bigInteger);
        }
    }

    public final <O> void setBigIntegers(Field<ArrayList<BigInteger>, O> field, ArrayList<BigInteger> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setBigIntegersInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setLong(Field<Long, O> field, long j) {
        if (field.zzc != null) {
            zza(field, Long.valueOf(j));
        } else {
            setLongInternal(field, field.getOutputFieldName(), j);
        }
    }

    public final <O> void setLongs(Field<ArrayList<Long>, O> field, ArrayList<Long> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setLongsInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setFloat(Field<Float, O> field, float f) {
        if (field.zzc != null) {
            zza(field, Float.valueOf(f));
        } else {
            setFloatInternal(field, field.getOutputFieldName(), f);
        }
    }

    public final <O> void setFloats(Field<ArrayList<Float>, O> field, ArrayList<Float> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setFloatsInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setDouble(Field<Double, O> field, double d) {
        if (field.zzc != null) {
            zza(field, Double.valueOf(d));
        } else {
            setDoubleInternal(field, field.getOutputFieldName(), d);
        }
    }

    public final <O> void setDoubles(Field<ArrayList<Double>, O> field, ArrayList<Double> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setDoublesInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setBigDecimal(Field<BigDecimal, O> field, BigDecimal bigDecimal) {
        if (field.zzc != null) {
            zza(field, bigDecimal);
        } else {
            setBigDecimalInternal(field, field.getOutputFieldName(), bigDecimal);
        }
    }

    public final <O> void setBigDecimals(Field<ArrayList<BigDecimal>, O> field, ArrayList<BigDecimal> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setBigDecimalsInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setBoolean(Field<Boolean, O> field, boolean z) {
        if (field.zzc != null) {
            zza(field, Boolean.valueOf(z));
        } else {
            setBooleanInternal(field, field.getOutputFieldName(), z);
        }
    }

    public final <O> void setBooleans(Field<ArrayList<Boolean>, O> field, ArrayList<Boolean> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setBooleansInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setString(Field<String, O> field, String str) {
        if (field.zzc != null) {
            zza(field, str);
        } else {
            setStringInternal(field, field.getOutputFieldName(), str);
        }
    }

    public final <O> void setStrings(Field<ArrayList<String>, O> field, ArrayList<String> arrayList) {
        if (field.zzc != null) {
            zza(field, arrayList);
        } else {
            setStringsInternal(field, field.getOutputFieldName(), arrayList);
        }
    }

    public final <O> void setDecodedBytes(Field<byte[], O> field, byte[] bArr) {
        if (field.zzc != null) {
            zza(field, bArr);
        } else {
            setDecodedBytesInternal(field, field.getOutputFieldName(), bArr);
        }
    }

    public final <O> void setStringMap(Field<Map<String, String>, O> field, Map<String, String> map) {
        if (field.zzc != null) {
            zza(field, map);
        } else {
            setStringMapInternal(field, field.getOutputFieldName(), map);
        }
    }

    /* access modifiers changed from: protected */
    public void setIntegerInternal(Field<?, ?> field, String str, int i) {
        setInteger(str, i);
    }

    /* access modifiers changed from: protected */
    public void setIntegersInternal(Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        setIntegers(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setBigIntegerInternal(Field<?, ?> field, String str, BigInteger bigInteger) {
        setBigInteger(str, bigInteger);
    }

    /* access modifiers changed from: protected */
    public void setBigIntegersInternal(Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        setBigIntegers(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setLongInternal(Field<?, ?> field, String str, long j) {
        setLong(str, j);
    }

    /* access modifiers changed from: protected */
    public void setLongsInternal(Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        setLongs(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setFloatInternal(Field<?, ?> field, String str, float f) {
        setFloat(str, f);
    }

    /* access modifiers changed from: protected */
    public void setFloatsInternal(Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        setFloats(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setDoubleInternal(Field<?, ?> field, String str, double d) {
        setDouble(str, d);
    }

    /* access modifiers changed from: protected */
    public void setDoublesInternal(Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        setDoubles(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setBigDecimalInternal(Field<?, ?> field, String str, BigDecimal bigDecimal) {
        setBigDecimal(str, bigDecimal);
    }

    /* access modifiers changed from: protected */
    public void setBigDecimalsInternal(Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        setBigDecimals(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setBooleanInternal(Field<?, ?> field, String str, boolean z) {
        setBoolean(str, z);
    }

    /* access modifiers changed from: protected */
    public void setBooleansInternal(Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        setBooleans(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setStringInternal(Field<?, ?> field, String str, String str2) {
        setString(str, str2);
    }

    /* access modifiers changed from: protected */
    public void setStringsInternal(Field<?, ?> field, String str, ArrayList<String> arrayList) {
        setStrings(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setDecodedBytesInternal(Field<?, ?> field, String str, byte[] bArr) {
        setDecodedBytes(str, bArr);
    }

    /* access modifiers changed from: protected */
    public void setStringMapInternal(Field<?, ?> field, String str, Map<String, String> map) {
        setStringMap(str, map);
    }

    private static <O> boolean zza(String str, O o) {
        if (o != null) {
            return true;
        }
        if (!Log.isLoggable("FastJsonResponse", 6)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
        sb.append("Output field (");
        sb.append(str);
        sb.append(") has a null value, but expected a primitive");
        Log.e("FastJsonResponse", sb.toString());
        return false;
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t) {
        addConcreteType(str, t);
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String str, ArrayList<T> arrayList) {
        addConcreteTypeArray(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setInteger(String str, int i) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    /* access modifiers changed from: protected */
    public void setIntegers(String str, ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    /* access modifiers changed from: protected */
    public void setBigInteger(String str, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    /* access modifiers changed from: protected */
    public void setBigIntegers(String str, ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    /* access modifiers changed from: protected */
    public void setLong(String str, long j) {
        throw new UnsupportedOperationException("Long not supported");
    }

    /* access modifiers changed from: protected */
    public void setLongs(String str, ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    /* access modifiers changed from: protected */
    public void setFloat(String str, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    /* access modifiers changed from: protected */
    public void setFloats(String str, ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    /* access modifiers changed from: protected */
    public void setDouble(String str, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    /* access modifiers changed from: protected */
    public void setDoubles(String str, ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    /* access modifiers changed from: protected */
    public void setBigDecimal(String str, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    /* access modifiers changed from: protected */
    public void setBigDecimals(String str, ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    /* access modifiers changed from: protected */
    public void setBoolean(String str, boolean z) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    /* access modifiers changed from: protected */
    public void setBooleans(String str, ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    /* access modifiers changed from: protected */
    public void setString(String str, String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    /* access modifiers changed from: protected */
    public void setStrings(String str, ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    /* access modifiers changed from: protected */
    public void setDecodedBytes(String str, byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    /* access modifiers changed from: protected */
    public void setStringMap(String str, Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    public <T extends FastJsonResponse> void addConcreteType(String str, T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeArray(String str, ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    public HashMap<String, Object> getConcreteTypes() {
        return null;
    }

    public HashMap<String, Object> getConcreteTypeArrays() {
        return null;
    }

    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String next : fieldMappings.keySet()) {
            Field field = fieldMappings.get(next);
            if (isFieldSet(field)) {
                Object originalValue = getOriginalValue(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append(QUOTE);
                sb.append(next);
                sb.append("\":");
                if (originalValue != null) {
                    switch (field.getTypeOut()) {
                        case 8:
                            sb.append(QUOTE);
                            sb.append(zzc.zza((byte[]) originalValue));
                            sb.append(QUOTE);
                            continue;
                        case 9:
                            sb.append(QUOTE);
                            sb.append(zzc.zzb((byte[]) originalValue));
                            sb.append(QUOTE);
                            continue;
                        case 10:
                            zzo.zza(sb, (HashMap) originalValue);
                            continue;
                        default:
                            if (!field.isTypeInArray()) {
                                zza(sb, field, originalValue);
                                break;
                            } else {
                                ArrayList arrayList = (ArrayList) originalValue;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    if (i > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i);
                                    if (obj != null) {
                                        zza(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                                continue;
                            }
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public Object getFieldValue(Field field) {
        String outputFieldName = field.getOutputFieldName();
        if (field.getConcreteType() == null) {
            return getValueObject(field.getOutputFieldName());
        }
        zzau.zza(getValueObject(field.getOutputFieldName()) == null, "Concrete field shouldn't be value object: %s", field.getOutputFieldName());
        HashMap<String, Object> concreteTypeArrays = field.isTypeOutArray() ? getConcreteTypeArrays() : getConcreteTypes();
        if (concreteTypeArrays != null) {
            return concreteTypeArrays.get(outputFieldName);
        }
        try {
            char upperCase = Character.toUpperCase(outputFieldName.charAt(0));
            String substring = outputFieldName.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 4);
            sb.append(PeopleConstants.Endpoints.ENDPOINT_GET);
            sb.append(upperCase);
            sb.append(substring);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void zza(StringBuilder sb, Field field, Object obj) {
        if (field.getTypeIn() == 11) {
            sb.append(((FastJsonResponse) field.getConcreteType().cast(obj)).toString());
        } else if (field.getTypeIn() == 7) {
            sb.append(QUOTE);
            sb.append(zzn.zzb((String) obj));
            sb.append(QUOTE);
        } else {
            sb.append(obj);
        }
    }
}
