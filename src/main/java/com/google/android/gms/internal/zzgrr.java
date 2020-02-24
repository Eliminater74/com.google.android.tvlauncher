package com.google.android.gms.internal;

/* compiled from: WireFormat */
public enum zzgrr {
    DOUBLE(zzgrw.DOUBLE, 1),
    FLOAT(zzgrw.FLOAT, 5),
    INT64(zzgrw.LONG, 0),
    UINT64(zzgrw.LONG, 0),
    INT32(zzgrw.INT, 0),
    FIXED64(zzgrw.LONG, 1),
    FIXED32(zzgrw.INT, 5),
    BOOL(zzgrw.BOOLEAN, 0),
    STRING(zzgrw.STRING, 2),
    GROUP(zzgrw.MESSAGE, 3),
    MESSAGE(zzgrw.MESSAGE, 2),
    BYTES(zzgrw.BYTE_STRING, 2),
    UINT32(zzgrw.INT, 0),
    ENUM(zzgrw.ENUM, 0),
    SFIXED32(zzgrw.INT, 5),
    SFIXED64(zzgrw.LONG, 1),
    SINT32(zzgrw.INT, 0),
    SINT64(zzgrw.LONG, 0);
    
    private final zzgrw zzs;
    private final int zzt;

    private zzgrr(zzgrw zzgrw, int i) {
        this.zzs = zzgrw;
        this.zzt = i;
    }

    public final zzgrw zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }
}
