package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.common.sqlite.CursorWrapper;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@KeepName
@Hide
public final class DataHolder extends zzbkv implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zzf();
    private static final Builder zzk = new zze(new String[0], null);
    private final int zza;
    private final String[] zzb;
    private Bundle zzc;
    private final CursorWindow[] zzd;
    private final int zze;
    private final Bundle zzf;
    private int[] zzg;
    private int zzh;
    private boolean zzi;
    private boolean zzj;

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.zzi = false;
        this.zzj = true;
        this.zza = i;
        this.zzb = strArr;
        this.zzd = cursorWindowArr;
        this.zze = i2;
        this.zzf = bundle;
    }

    static final class zza implements Comparator<HashMap<String, Object>> {
        private final String zza;

        zza(String str) {
            this.zza = (String) zzau.zza((Object) str);
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            Object zza2 = zzau.zza(((HashMap) obj).get(this.zza));
            Object zza3 = zzau.zza(((HashMap) obj2).get(this.zza));
            if (zza2.equals(zza3)) {
                return 0;
            }
            if (zza2 instanceof Boolean) {
                return ((Boolean) zza2).compareTo((Boolean) zza3);
            }
            if (zza2 instanceof Long) {
                return ((Long) zza2).compareTo((Long) zza3);
            }
            if (zza2 instanceof Integer) {
                return ((Integer) zza2).compareTo((Integer) zza3);
            }
            if (zza2 instanceof String) {
                return ((String) zza2).compareTo((String) zza3);
            }
            if (zza2 instanceof Double) {
                return ((Double) zza2).compareTo((Double) zza3);
            }
            if (zza2 instanceof Float) {
                return ((Float) zza2).compareTo((Float) zza3);
            }
            String valueOf = String.valueOf(zza2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("Unknown type for lValue ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final String[] zza;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> zzb;
        private final String zzc;
        private final HashMap<Object, Integer> zzd;
        private boolean zze;
        private String zzf;

        private Builder(String[] strArr, String str) {
            this.zza = (String[]) zzau.zza(strArr);
            this.zzb = new ArrayList<>();
            this.zzc = str;
            this.zzd = new HashMap<>();
            this.zze = false;
            this.zzf = null;
        }

        public Builder withRow(HashMap<String, Object> hashMap) {
            int i;
            zzc.zza(hashMap);
            String str = this.zzc;
            if (str == null) {
                i = -1;
            } else {
                Object obj = hashMap.get(str);
                if (obj == null) {
                    i = -1;
                } else {
                    Integer num = this.zzd.get(obj);
                    if (num == null) {
                        this.zzd.put(obj, Integer.valueOf(this.zzb.size()));
                        i = -1;
                    } else {
                        i = num.intValue();
                    }
                }
            }
            if (i == -1) {
                this.zzb.add(hashMap);
            } else {
                this.zzb.remove(i);
                this.zzb.add(i, hashMap);
            }
            this.zze = false;
            return this;
        }

        public void modifyUniqueRowValue(Object obj, String str, Object obj2) {
            Integer num;
            if (this.zzc != null && (num = this.zzd.get(obj)) != null) {
                this.zzb.get(num.intValue()).put(str, obj2);
            }
        }

        public Builder withRow(ContentValues contentValues) {
            zzc.zza(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Map.Entry next : contentValues.valueSet()) {
                hashMap.put((String) next.getKey(), next.getValue());
            }
            return withRow(hashMap);
        }

        public boolean containsRowWithValue(String str, Object obj) {
            int size = this.zzb.size();
            for (int i = 0; i < size; i++) {
                if (zzak.zza(this.zzb.get(i).get(str), obj)) {
                    return true;
                }
            }
            return false;
        }

        public Builder removeRowsWithValue(String str, Object obj) {
            for (int size = this.zzb.size() - 1; size >= 0; size--) {
                if (zzak.zza(this.zzb.get(size).get(str), obj)) {
                    this.zzb.remove(size);
                }
            }
            return this;
        }

        public Builder sort(String str) {
            if (zza(str)) {
                return this;
            }
            Collections.sort(this.zzb, new zza(str));
            if (this.zzc != null) {
                this.zzd.clear();
                int size = this.zzb.size();
                for (int i = 0; i < size; i++) {
                    Object obj = this.zzb.get(i).get(this.zzc);
                    if (obj != null) {
                        this.zzd.put(obj, Integer.valueOf(i));
                    }
                }
            }
            this.zze = true;
            this.zzf = str;
            return this;
        }

        public Builder descendingSort(String str) {
            if (zza(str)) {
                return this;
            }
            sort(str);
            Collections.reverse(this.zzb);
            return this;
        }

        private final boolean zza(String str) {
            zzc.zza((Object) str);
            if (!this.zze || !str.equals(this.zzf)) {
                return false;
            }
            return true;
        }

        public int getCount() {
            return this.zzb.size();
        }

        public DataHolder build(int i) {
            return new DataHolder(this, i, (Bundle) null, (zze) null);
        }

        public DataHolder build(int i, Bundle bundle) {
            return new DataHolder(this, i, bundle, -1, (zze) null);
        }

        public DataHolder build(int i, Bundle bundle, int i2) {
            return new DataHolder(this, i, bundle, i2, (zze) null);
        }

        /* synthetic */ Builder(String[] strArr, String str, zze zze2) {
            this(strArr, str);
        }
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.zzi = false;
        this.zzj = true;
        this.zza = 1;
        this.zzb = (String[]) zzau.zza(strArr);
        this.zzd = (CursorWindow[]) zzau.zza(cursorWindowArr);
        this.zze = i;
        this.zzf = bundle;
        validateContents();
    }

    public DataHolder(CursorWrapper cursorWrapper, int i, Bundle bundle) {
        this(cursorWrapper.getColumnNames(), zza(cursorWrapper), i, bundle);
    }

    public DataHolder(Cursor cursor, int i, Bundle bundle) {
        this(new CursorWrapper(cursor), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle) {
        this(builder.zza, zza(builder, -1), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle, int i2) {
        this(builder.zza, zza(builder, i2), i, bundle);
    }

    public final void validateContents() {
        this.zzc = new Bundle();
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = this.zzb;
            if (i2 >= strArr.length) {
                break;
            }
            this.zzc.putInt(strArr[i2], i2);
            i2++;
        }
        this.zzg = new int[this.zzd.length];
        int i3 = 0;
        while (true) {
            CursorWindow[] cursorWindowArr = this.zzd;
            if (i < cursorWindowArr.length) {
                this.zzg[i] = i3;
                i3 += this.zzd[i].getNumRows() - (i3 - cursorWindowArr[i].getStartPosition());
                i++;
            } else {
                this.zzh = i3;
                return;
            }
        }
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
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
     arg types: [android.os.Parcel, int, android.database.CursorWindow[], int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void */
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
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zzb, false);
        zzbky.zza(parcel, 2, (Parcelable[]) this.zzd, i, false);
        zzbky.zza(parcel, 3, getStatusCode());
        zzbky.zza(parcel, 4, this.zzf, false);
        zzbky.zza(parcel, 1000, this.zza);
        zzbky.zza(parcel, zza2);
        if ((i & 1) != 0) {
            close();
        }
    }

    public final void logCursorMetadataForDebugging() {
        Log.d("DataHolder", "*******************************************");
        int length = this.zzd.length;
        StringBuilder sb = new StringBuilder(32);
        sb.append("num cursor windows : ");
        sb.append(length);
        Log.d("DataHolder", sb.toString());
        int i = this.zzh;
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("total number of objects in holder: ");
        sb2.append(i);
        Log.d("DataHolder", sb2.toString());
        int length2 = this.zzg.length;
        StringBuilder sb3 = new StringBuilder(42);
        sb3.append("total mumber of windowOffsets: ");
        sb3.append(length2);
        Log.d("DataHolder", sb3.toString());
        int i2 = 0;
        while (true) {
            int[] iArr = this.zzg;
            if (i2 < iArr.length) {
                int i3 = iArr[i2];
                StringBuilder sb4 = new StringBuilder(43);
                sb4.append("offset for window ");
                sb4.append(i2);
                sb4.append(" : ");
                sb4.append(i3);
                Log.d("DataHolder", sb4.toString());
                int numRows = this.zzd[i2].getNumRows();
                StringBuilder sb5 = new StringBuilder(45);
                sb5.append("num rows for window ");
                sb5.append(i2);
                sb5.append(" : ");
                sb5.append(numRows);
                Log.d("DataHolder", sb5.toString());
                int startPosition = this.zzd[i2].getStartPosition();
                StringBuilder sb6 = new StringBuilder(46);
                sb6.append("start pos for window ");
                sb6.append(i2);
                sb6.append(" : ");
                sb6.append(startPosition);
                Log.d("DataHolder", sb6.toString());
                i2++;
            } else {
                Log.d("DataHolder", "*******************************************");
                return;
            }
        }
    }

    public final int getStatusCode() {
        return this.zze;
    }

    @Hide
    public final Bundle zza() {
        return this.zzf;
    }

    /* JADX INFO: finally extract failed */
    private static CursorWindow[] zza(CursorWrapper cursorWrapper) {
        int i;
        ArrayList arrayList = new ArrayList();
        try {
            int count = cursorWrapper.getCount();
            CursorWindow window = cursorWrapper.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                i = 0;
            } else {
                window.acquireReference();
                cursorWrapper.setWindow(null);
                arrayList.add(window);
                i = window.getNumRows();
            }
            while (i < count && cursorWrapper.moveToPosition(i)) {
                CursorWindow window2 = cursorWrapper.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    cursorWrapper.setWindow(null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(i);
                    cursorWrapper.fillWindow(i, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                i = window2.getStartPosition() + window2.getNumRows();
            }
            cursorWrapper.close();
            return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
        } catch (Throwable th) {
            cursorWrapper.close();
            throw th;
        }
    }

    private static CursorWindow[] zza(Builder builder, int i) {
        List list;
        if (builder.zza.length == 0) {
            return new CursorWindow[0];
        }
        if (i < 0 || i >= builder.zzb.size()) {
            list = builder.zzb;
        } else {
            list = builder.zzb.subList(0, i);
        }
        int size = list.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(builder.zza.length);
        CursorWindow cursorWindow2 = cursorWindow;
        int i2 = 0;
        boolean z = false;
        while (i2 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    StringBuilder sb = new StringBuilder(72);
                    sb.append("Allocating additional cursor window for large data set (row ");
                    sb.append(i2);
                    sb.append(")");
                    Log.d("DataHolder", sb.toString());
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i2);
                    cursorWindow2.setNumColumns(builder.zza.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) list.get(i2);
                boolean z2 = true;
                for (int i3 = 0; i3 < builder.zza.length && z2; i3++) {
                    String str = builder.zza[i3];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z2 = cursorWindow2.putNull(i2, i3);
                    } else if (obj instanceof String) {
                        z2 = cursorWindow2.putString((String) obj, i2, i3);
                    } else if (obj instanceof Long) {
                        z2 = cursorWindow2.putLong(((Long) obj).longValue(), i2, i3);
                    } else if (obj instanceof Integer) {
                        z2 = cursorWindow2.putLong((long) ((Integer) obj).intValue(), i2, i3);
                    } else if (obj instanceof Boolean) {
                        z2 = cursorWindow2.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i2, i3);
                    } else if (obj instanceof byte[]) {
                        z2 = cursorWindow2.putBlob((byte[]) obj, i2, i3);
                    } else if (obj instanceof Double) {
                        z2 = cursorWindow2.putDouble(((Double) obj).doubleValue(), i2, i3);
                    } else if (obj instanceof Float) {
                        z2 = cursorWindow2.putDouble((double) ((Float) obj).floatValue(), i2, i3);
                    } else {
                        String valueOf = String.valueOf(obj);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(valueOf).length());
                        sb2.append("Unsupported object for column ");
                        sb2.append(str);
                        sb2.append(": ");
                        sb2.append(valueOf);
                        throw new IllegalArgumentException(sb2.toString());
                    }
                }
                if (z2) {
                    z = false;
                } else if (!z) {
                    StringBuilder sb3 = new StringBuilder(74);
                    sb3.append("Couldn't populate window data for row ");
                    sb3.append(i2);
                    sb3.append(" - allocating new window.");
                    Log.d("DataHolder", sb3.toString());
                    cursorWindow2.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i2);
                    cursorWindow2.setNumColumns(builder.zza.length);
                    arrayList.add(cursorWindow2);
                    i2--;
                    z = true;
                } else {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                }
                i2++;
            } catch (RuntimeException e) {
                int size2 = arrayList.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    ((CursorWindow) arrayList.get(i4)).close();
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    private final void zza(String str, int i) {
        Bundle bundle = this.zzc;
        if (bundle == null || !bundle.containsKey(str)) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "No such column: ".concat(valueOf) : new String("No such column: "));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzh) {
            throw new CursorIndexOutOfBoundsException(i, this.zzh);
        }
    }

    public final boolean hasColumn(String str) {
        return this.zzc.containsKey(str);
    }

    public final long getLong(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getLong(i, this.zzc.getInt(str));
    }

    public final int getInteger(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getInt(i, this.zzc.getInt(str));
    }

    public final String getString(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getString(i, this.zzc.getInt(str));
    }

    public final boolean getBoolean(String str, int i, int i2) {
        zza(str, i);
        return Long.valueOf(this.zzd[i2].getLong(i, this.zzc.getInt(str))).longValue() == 1;
    }

    public final float getFloat(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getFloat(i, this.zzc.getInt(str));
    }

    public final double getDouble(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getDouble(i, this.zzc.getInt(str));
    }

    public final byte[] getByteArray(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].getBlob(i, this.zzc.getInt(str));
    }

    public final Uri parseUri(String str, int i, int i2) {
        String string = getString(str, i, i2);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public final void copyToBuffer(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zza(str, i);
        this.zzd[i2].copyStringToBuffer(i, this.zzc.getInt(str), charArrayBuffer);
    }

    public final boolean hasNull(String str, int i, int i2) {
        zza(str, i);
        return this.zzd[i2].isNull(i, this.zzc.getInt(str));
    }

    public final int getCount() {
        return this.zzh;
    }

    @Hide
    public final int zza(int i) {
        int i2 = 0;
        zzau.zza(i >= 0 && i < this.zzh);
        while (true) {
            int[] iArr = this.zzg;
            if (i2 >= iArr.length) {
                break;
            } else if (i < iArr[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        if (i2 == this.zzg.length) {
            return i2 - 1;
        }
        return i2;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.zzi;
        }
        return z;
    }

    public final void close() {
        synchronized (this) {
            if (!this.zzi) {
                this.zzi = true;
                for (CursorWindow close : this.zzd) {
                    close.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            if (this.zzj && this.zzd.length > 0 && !isClosed()) {
                close();
                String obj = toString();
                StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + ClientAnalytics.LogRequest.LogSource.SNAPSEED_ANDROID_PRIMES_VALUE);
                sb.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                sb.append(obj);
                sb.append(")");
                Log.e("DataBuffer", sb.toString());
            }
        } finally {
            super.finalize();
        }
    }

    public static Builder builder(String[] strArr) {
        return new Builder(strArr, null, null);
    }

    public static Builder builder(String[] strArr, String str) {
        zzau.zza((Object) str);
        return new Builder(strArr, str, null);
    }

    public static DataHolder empty(int i) {
        return empty(i, null);
    }

    public static DataHolder empty(int i, Bundle bundle) {
        return new DataHolder(zzk, i, bundle);
    }

    /* synthetic */ DataHolder(Builder builder, int i, Bundle bundle, zze zze2) {
        this(builder, i, (Bundle) null);
    }

    /* synthetic */ DataHolder(Builder builder, int i, Bundle bundle, int i2, zze zze2) {
        this(builder, i, bundle, i2);
    }
}
