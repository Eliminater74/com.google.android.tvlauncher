package com.google.android.gms.common.server.response;

import android.content.ContentValues;

public abstract class FastContentValuesJsonResponse extends FastJsonResponse {
    private final ContentValues zza;

    public FastContentValuesJsonResponse() {
        this.zza = new ContentValues();
    }

    public FastContentValuesJsonResponse(ContentValues contentValues) {
        this.zza = contentValues;
    }

    /* access modifiers changed from: protected */
    public Object getValueObject(String str) {
        return this.zza.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean isPrimitiveFieldSet(String str) {
        return this.zza.containsKey(str);
    }

    public ContentValues getValues() {
        return this.zza;
    }

    /* access modifiers changed from: protected */
    public void setInteger(String str, int i) {
        this.zza.put(str, Integer.valueOf(i));
    }

    /* access modifiers changed from: protected */
    public void setLong(String str, long j) {
        this.zza.put(str, Long.valueOf(j));
    }

    /* access modifiers changed from: protected */
    public void setFloat(String str, float f) {
        this.zza.put(str, Float.valueOf(f));
    }

    /* access modifiers changed from: protected */
    public void setDouble(String str, double d) {
        this.zza.put(str, Double.valueOf(d));
    }

    /* access modifiers changed from: protected */
    public void setBoolean(String str, boolean z) {
        this.zza.put(str, Boolean.valueOf(z));
    }

    /* access modifiers changed from: protected */
    public void setString(String str, String str2) {
        this.zza.put(str, str2);
    }

    /* access modifiers changed from: protected */
    public void setDecodedBytes(String str, byte[] bArr) {
        this.zza.put(str, bArr);
    }
}
