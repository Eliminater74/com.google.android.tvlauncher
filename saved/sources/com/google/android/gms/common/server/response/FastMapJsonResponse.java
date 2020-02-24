package com.google.android.gms.common.server.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FastMapJsonResponse extends FastJsonResponse {
    private final HashMap<String, Object> zza = new HashMap<>();

    public HashMap<String, Object> getValues() {
        return this.zza;
    }

    public Object getValueObject(String str) {
        return this.zza.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean isPrimitiveFieldSet(String str) {
        return this.zza.containsKey(str);
    }

    public void setInteger(String str, int i) {
        this.zza.put(str, Integer.valueOf(i));
    }

    public void setIntegers(String str, ArrayList<Integer> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setBigInteger(String str, BigInteger bigInteger) {
        this.zza.put(str, bigInteger);
    }

    public void setBigIntegers(String str, ArrayList<BigInteger> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setLong(String str, long j) {
        this.zza.put(str, Long.valueOf(j));
    }

    public void setLongs(String str, ArrayList<Long> arrayList) {
        this.zza.put(str, arrayList);
    }

    /* access modifiers changed from: protected */
    public void setFloat(String str, float f) {
        this.zza.put(str, Float.valueOf(f));
    }

    /* access modifiers changed from: protected */
    public void setFloats(String str, ArrayList<Float> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setDouble(String str, double d) {
        this.zza.put(str, Double.valueOf(d));
    }

    public void setDoubles(String str, ArrayList<Double> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setBigDecimal(String str, BigDecimal bigDecimal) {
        this.zza.put(str, bigDecimal);
    }

    public void setBigDecimals(String str, ArrayList<BigDecimal> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setBoolean(String str, boolean z) {
        this.zza.put(str, Boolean.valueOf(z));
    }

    public void setBooleans(String str, ArrayList<Boolean> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setString(String str, String str2) {
        this.zza.put(str, str2);
    }

    public void setStrings(String str, ArrayList<String> arrayList) {
        this.zza.put(str, arrayList);
    }

    public void setDecodedBytes(String str, byte[] bArr) {
        this.zza.put(str, bArr);
    }

    public void setStringMap(String str, Map<String, String> map) {
        this.zza.put(str, map);
    }
}
