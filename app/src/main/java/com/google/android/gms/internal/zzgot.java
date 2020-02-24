package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: InvalidProtocolBufferException */
public class zzgot extends IOException {
    private zzgpt zza = null;

    public zzgot(String str) {
        super(str);
    }

    public final zzgot zza(zzgpt zzgpt) {
        this.zza = zzgpt;
        return this;
    }

    static zzgot zza() {
        return new zzgot("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzgot zzb() {
        return new zzgot("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzgot zzc() {
        return new zzgot("CodedInputStream encountered a malformed varint.");
    }

    static zzgot zzd() {
        return new zzgot("Protocol message contained an invalid tag (zero).");
    }

    static zzgot zze() {
        return new zzgot("Protocol message end-group tag did not match expected tag.");
    }

    static zzgou zzf() {
        return new zzgou("Protocol message tag had invalid wire type.");
    }

    static zzgot zzg() {
        return new zzgot("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static zzgot zzh() {
        return new zzgot("Failed to parse the message.");
    }

    static zzgot zzi() {
        return new zzgot("Protocol message had invalid UTF-8.");
    }
}
