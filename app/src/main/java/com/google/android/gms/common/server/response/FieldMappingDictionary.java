package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldMappingDictionary extends zzbkv {
    public static final Parcelable.Creator<FieldMappingDictionary> CREATOR = new zzj();
    private final int zza;
    private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzb;
    private final ArrayList<Entry> zzc;
    private final String zzd;

    FieldMappingDictionary(int i, ArrayList<Entry> arrayList, String str) {
        this.zza = i;
        this.zzc = null;
        HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Entry entry = arrayList.get(i2);
            String str2 = entry.zza;
            HashMap hashMap2 = new HashMap();
            int size2 = entry.zzb.size();
            for (int i3 = 0; i3 < size2; i3++) {
                FieldMapPair fieldMapPair = entry.zzb.get(i3);
                hashMap2.put(fieldMapPair.zza, fieldMapPair.zzb);
            }
            hashMap.put(str2, hashMap2);
        }
        this.zzb = hashMap;
        this.zzd = (String) zzau.zza((Object) str);
        linkFields();
    }

    public FieldMappingDictionary(Class<? extends FastJsonResponse> cls) {
        this.zza = 1;
        this.zzc = null;
        this.zzb = new HashMap<>();
        this.zzd = cls.getCanonicalName();
    }

    public void linkFields() {
        for (String str : this.zzb.keySet()) {
            Map map = this.zzb.get(str);
            for (String str2 : map.keySet()) {
                ((FastJsonResponse.Field) map.get(str2)).setFieldMappingDictionary(this);
            }
        }
    }

    public void copyInternalFieldMappings() {
        for (String next : this.zzb.keySet()) {
            Map map = this.zzb.get(next);
            HashMap hashMap = new HashMap();
            for (String str : map.keySet()) {
                hashMap.put(str, ((FastJsonResponse.Field) map.get(str)).copyForDictionary());
            }
            this.zzb.put(next, hashMap);
        }
    }

    public void put(Class<? extends FastJsonResponse> cls, Map<String, FastJsonResponse.Field<?, ?>> map) {
        this.zzb.put(cls.getCanonicalName(), map);
    }

    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMapping(Class<? extends FastJsonResponse> cls) {
        return this.zzb.get(cls.getCanonicalName());
    }

    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMapping(String str) {
        return this.zzb.get(str);
    }

    public boolean hasFieldMappingForClass(Class<? extends FastJsonResponse> cls) {
        return this.zzb.containsKey(cls.getCanonicalName());
    }

    public String getRootClassName() {
        return this.zzd;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String next : this.zzb.keySet()) {
            sb.append(next);
            sb.append(":\n");
            Map map = this.zzb.get(next);
            for (String str : map.keySet()) {
                sb.append("  ");
                sb.append(str);
                sb.append(": ");
                sb.append(map.get(str));
            }
        }
        return sb.toString();
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
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        ArrayList arrayList = new ArrayList();
        for (String next : this.zzb.keySet()) {
            arrayList.add(new Entry(next, this.zzb.get(next)));
        }
        zzbky.zzc(parcel, 2, arrayList, false);
        zzbky.zza(parcel, 3, getRootClassName(), false);
        zzbky.zza(parcel, zza2);
    }

    public static class Entry extends zzbkv {
        public static final Parcelable.Creator<Entry> CREATOR = new zzk();
        final String zza;
        final ArrayList<FieldMapPair> zzb;
        private final int zzc;

        Entry(int i, String str, ArrayList<FieldMapPair> arrayList) {
            this.zzc = i;
            this.zza = str;
            this.zzb = arrayList;
        }

        Entry(String str, Map<String, FastJsonResponse.Field<?, ?>> map) {
            ArrayList<FieldMapPair> arrayList;
            this.zzc = 1;
            this.zza = str;
            if (map == null) {
                arrayList = null;
            } else {
                arrayList = new ArrayList<>();
                for (String next : map.keySet()) {
                    arrayList.add(new FieldMapPair(next, map.get(next)));
                }
            }
            this.zzb = arrayList;
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
        public void writeToParcel(Parcel parcel, int i) {
            int zza2 = zzbky.zza(parcel);
            zzbky.zza(parcel, 1, this.zzc);
            zzbky.zza(parcel, 2, this.zza, false);
            zzbky.zzc(parcel, 3, this.zzb, false);
            zzbky.zza(parcel, zza2);
        }
    }

    public static class FieldMapPair extends zzbkv {
        public static final Parcelable.Creator<FieldMapPair> CREATOR = new zzi();
        final String zza;
        final FastJsonResponse.Field<?, ?> zzb;
        private final int zzc;

        FieldMapPair(int i, String str, FastJsonResponse.Field<?, ?> field) {
            this.zzc = i;
            this.zza = str;
            this.zzb = field;
        }

        FieldMapPair(String str, FastJsonResponse.Field<?, ?> field) {
            this.zzc = 1;
            this.zza = str;
            this.zzb = field;
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
         arg types: [android.os.Parcel, int, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>, int, int]
         candidates:
          com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
          com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
        public void writeToParcel(Parcel parcel, int i) {
            int zza2 = zzbky.zza(parcel);
            zzbky.zza(parcel, 1, this.zzc);
            zzbky.zza(parcel, 2, this.zza, false);
            zzbky.zza(parcel, 3, (Parcelable) this.zzb, i, false);
            zzbky.zza(parcel, zza2);
        }
    }
}
