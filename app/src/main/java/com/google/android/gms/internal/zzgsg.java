package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: InvalidProtocolBufferNanoException */
public final class zzgsg extends IOException {
    public zzgsg(String str) {
        super(str);
    }

    static zzgsg zza() {
        return new zzgsg("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzgsg zzb() {
        return new zzgsg("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzgsg zzc() {
        return new zzgsg("CodedInputStream encountered a malformed varint.");
    }

    static zzgsg zzd() {
        return new zzgsg("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
