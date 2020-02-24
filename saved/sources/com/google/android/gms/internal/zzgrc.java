package com.google.android.gms.internal;

import java.util.List;

/* compiled from: UninitializedMessageException */
public final class zzgrc extends RuntimeException {
    private final List<String> zza = null;

    public zzgrc(zzgpt zzgpt) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzgot zza() {
        return new zzgot(getMessage());
    }
}
