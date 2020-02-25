package com.google.android.gms.internal;

/* compiled from: WireFormat */
public enum zzgrw {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(false),
    STRING(""),
    BYTE_STRING(zzgnb.zza),
    ENUM(null),
    MESSAGE(null);

    private final Object zzj;

    private zzgrw(Object obj) {
        this.zzj = obj;
    }
}
