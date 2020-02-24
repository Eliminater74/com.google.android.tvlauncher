package com.google.android.gms.phenotype;

public final class ServingVersion {
    private final long zza;

    private ServingVersion(long j) {
        this.zza = j;
    }

    public final long getServingVersion() {
        return this.zza;
    }

    public static ServingVersion fromServer(long j) {
        return new ServingVersion(j);
    }
}
