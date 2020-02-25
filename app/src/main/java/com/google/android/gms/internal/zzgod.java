package com.google.android.gms.internal;

import java.lang.reflect.Type;

/* compiled from: FieldType */
public enum zzgod {
    DOUBLE(0, zzgof.SCALAR, zzgov.DOUBLE),
    FLOAT(1, zzgof.SCALAR, zzgov.FLOAT),
    INT64(2, zzgof.SCALAR, zzgov.LONG),
    UINT64(3, zzgof.SCALAR, zzgov.LONG),
    INT32(4, zzgof.SCALAR, zzgov.INT),
    FIXED64(5, zzgof.SCALAR, zzgov.LONG),
    FIXED32(6, zzgof.SCALAR, zzgov.INT),
    BOOL(7, zzgof.SCALAR, zzgov.BOOLEAN),
    STRING(8, zzgof.SCALAR, zzgov.STRING),
    MESSAGE(9, zzgof.SCALAR, zzgov.MESSAGE),
    BYTES(10, zzgof.SCALAR, zzgov.BYTE_STRING),
    UINT32(11, zzgof.SCALAR, zzgov.INT),
    ENUM(12, zzgof.SCALAR, zzgov.ENUM),
    SFIXED32(13, zzgof.SCALAR, zzgov.INT),
    SFIXED64(14, zzgof.SCALAR, zzgov.LONG),
    SINT32(15, zzgof.SCALAR, zzgov.INT),
    SINT64(16, zzgof.SCALAR, zzgov.LONG),
    GROUP(17, zzgof.SCALAR, zzgov.MESSAGE),
    DOUBLE_LIST(18, zzgof.VECTOR, zzgov.DOUBLE),
    FLOAT_LIST(19, zzgof.VECTOR, zzgov.FLOAT),
    INT64_LIST(20, zzgof.VECTOR, zzgov.LONG),
    UINT64_LIST(21, zzgof.VECTOR, zzgov.LONG),
    INT32_LIST(22, zzgof.VECTOR, zzgov.INT),
    FIXED64_LIST(23, zzgof.VECTOR, zzgov.LONG),
    FIXED32_LIST(24, zzgof.VECTOR, zzgov.INT),
    BOOL_LIST(25, zzgof.VECTOR, zzgov.BOOLEAN),
    STRING_LIST(26, zzgof.VECTOR, zzgov.STRING),
    MESSAGE_LIST(27, zzgof.VECTOR, zzgov.MESSAGE),
    BYTES_LIST(28, zzgof.VECTOR, zzgov.BYTE_STRING),
    UINT32_LIST(29, zzgof.VECTOR, zzgov.INT),
    ENUM_LIST(30, zzgof.VECTOR, zzgov.ENUM),
    SFIXED32_LIST(31, zzgof.VECTOR, zzgov.INT),
    SFIXED64_LIST(32, zzgof.VECTOR, zzgov.LONG),
    SINT32_LIST(33, zzgof.VECTOR, zzgov.INT),
    SINT64_LIST(34, zzgof.VECTOR, zzgov.LONG),
    DOUBLE_LIST_PACKED(35, zzgof.PACKED_VECTOR, zzgov.DOUBLE),
    FLOAT_LIST_PACKED(36, zzgof.PACKED_VECTOR, zzgov.FLOAT),
    INT64_LIST_PACKED(37, zzgof.PACKED_VECTOR, zzgov.LONG),
    UINT64_LIST_PACKED(38, zzgof.PACKED_VECTOR, zzgov.LONG),
    INT32_LIST_PACKED(39, zzgof.PACKED_VECTOR, zzgov.INT),
    FIXED64_LIST_PACKED(40, zzgof.PACKED_VECTOR, zzgov.LONG),
    FIXED32_LIST_PACKED(41, zzgof.PACKED_VECTOR, zzgov.INT),
    BOOL_LIST_PACKED(42, zzgof.PACKED_VECTOR, zzgov.BOOLEAN),
    UINT32_LIST_PACKED(43, zzgof.PACKED_VECTOR, zzgov.INT),
    ENUM_LIST_PACKED(44, zzgof.PACKED_VECTOR, zzgov.ENUM),
    SFIXED32_LIST_PACKED(45, zzgof.PACKED_VECTOR, zzgov.INT),
    SFIXED64_LIST_PACKED(46, zzgof.PACKED_VECTOR, zzgov.LONG),
    SINT32_LIST_PACKED(47, zzgof.PACKED_VECTOR, zzgov.INT),
    SINT64_LIST_PACKED(48, zzgof.PACKED_VECTOR, zzgov.LONG),
    GROUP_LIST(49, zzgof.VECTOR, zzgov.MESSAGE),
    MAP(50, zzgof.MAP, zzgov.VOID);

    private static final zzgod[] zzbe;
    private static final Type[] zzbf = new Type[0];

    static {
        zzgod[] values = values();
        zzbe = new zzgod[values.length];
        for (zzgod zzgod : values) {
            zzbe[zzgod.zzba] = zzgod;
        }
    }

    private final zzgov zzaz;
    private final int zzba;
    private final zzgof zzbb;
    private final Class<?> zzbc;
    private final boolean zzbd;

    private zzgod(int i, zzgof zzgof, zzgov zzgov) {
        int i2;
        this.zzba = i;
        this.zzbb = zzgof;
        this.zzaz = zzgov;
        int i3 = zzgoe.zza[zzgof.ordinal()];
        if (i3 == 1) {
            this.zzbc = zzgov.zza();
        } else if (i3 != 2) {
            this.zzbc = null;
        } else {
            this.zzbc = zzgov.zza();
        }
        boolean z = false;
        if (!(zzgof != zzgof.SCALAR || (i2 = zzgoe.zzb[zzgov.ordinal()]) == 1 || i2 == 2 || i2 == 3)) {
            z = true;
        }
        this.zzbd = z;
    }

    public final int zza() {
        return this.zzba;
    }
}
