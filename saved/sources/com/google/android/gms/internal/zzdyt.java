package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.phenotype.Configurations;
import com.google.android.gms.phenotype.DogfoodsToken;
import com.google.android.gms.phenotype.ExperimentTokens;
import com.google.android.gms.phenotype.Flag;
import com.google.android.gms.phenotype.FlagOverrides;
import com.google.android.gms.phenotype.Phenotype;
import com.google.android.gms.phenotype.PhenotypeApi;

/* compiled from: PhenotypeApiImpl */
public class zzdyt implements PhenotypeApi {
    /* access modifiers changed from: private */
    public static long zza = 0;

    /* compiled from: PhenotypeApiImpl */
    public static class zza extends zzdyq {
        public void zzb(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zzc(Status status) {
            throw new UnsupportedOperationException();
        }

        public final void zzd(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zze(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status, Configurations configurations) {
            throw new UnsupportedOperationException();
        }

        public void zzf(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status, ExperimentTokens experimentTokens) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status, DogfoodsToken dogfoodsToken) {
            throw new UnsupportedOperationException();
        }

        public void zzg(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status, Flag flag) {
            throw new UnsupportedOperationException();
        }

        public void zzb(Status status, Configurations configurations) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zzh(Status status) {
            throw new UnsupportedOperationException();
        }

        public void zza(Status status, FlagOverrides flagOverrides) {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: PhenotypeApiImpl */
    static abstract class zzb<R extends Result> extends zzn<R, zzeac> {
        public zzb(GoogleApiClient googleApiClient) {
            super(Phenotype.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void zza(Object obj) {
            super.zza((Result) obj);
        }
    }

    public PendingResult<Status> register(GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr) {
        return googleApiClient.zza((zzn) new zzdyu(this, googleApiClient, str, i, strArr, bArr));
    }

    public PendingResult<Status> weakRegister(GoogleApiClient googleApiClient, String str, int i, String[] strArr, int[] iArr, byte[] bArr) {
        return googleApiClient.zza((zzn) new zzdzm(this, googleApiClient, str, i, strArr, iArr, bArr));
    }

    /* compiled from: PhenotypeApiImpl */
    static class zzc implements PhenotypeApi.ConfigurationsResult {
        private final Status zza;
        private final Configurations zzb;

        public zzc(Status status, Configurations configurations) {
            this.zza = status;
            this.zzb = configurations;
        }

        public final Status getStatus() {
            return this.zza;
        }

        public final Configurations getConfigurations() {
            return this.zzb;
        }
    }

    /* compiled from: PhenotypeApiImpl */
    static class zzd implements PhenotypeApi.DogfoodsTokenResult {
        private final Status zza;
        private final DogfoodsToken zzb;

        public zzd(Status status, DogfoodsToken dogfoodsToken) {
            this.zza = status;
            this.zzb = dogfoodsToken;
        }

        public final Status getStatus() {
            return this.zza;
        }

        public final DogfoodsToken getDogfoodsToken() {
            return this.zzb;
        }
    }

    /* compiled from: PhenotypeApiImpl */
    static class zze implements PhenotypeApi.ExperimentTokensResult {
        private final Status zza;
        private final ExperimentTokens zzb;

        public zze(Status status, ExperimentTokens experimentTokens) {
            this.zza = status;
            this.zzb = experimentTokens;
        }

        public final Status getStatus() {
            return this.zza;
        }

        public final ExperimentTokens getExperimentTokens() {
            return this.zzb;
        }
    }

    /* compiled from: PhenotypeApiImpl */
    static class zzf implements PhenotypeApi.FlagOverridesResult {
        private final Status zza;
        private final FlagOverrides zzb;

        public zzf(Status status, FlagOverrides flagOverrides) {
            this.zza = status;
            this.zzb = flagOverrides;
        }

        public final Status getStatus() {
            return this.zza;
        }

        public final FlagOverrides getFlagOverrides() {
            return this.zzb;
        }
    }

    /* compiled from: PhenotypeApiImpl */
    static class zzg implements PhenotypeApi.FlagResult {
        private final Status zza;
        private final Flag zzb;

        public zzg(Status status, Flag flag) {
            this.zza = status;
            this.zzb = flag;
        }

        public final Status getStatus() {
            return this.zza;
        }

        public final Flag getFlag() {
            return this.zzb;
        }
    }

    public PendingResult<Status> unRegister(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zza((zzn) new zzdzo(this, googleApiClient, str));
    }

    @Deprecated
    public PendingResult<PhenotypeApi.ConfigurationsResult> getConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2) {
        return getConfigurationSnapshot(googleApiClient, str, str2, null);
    }

    public PendingResult<PhenotypeApi.ConfigurationsResult> getConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2, String str3) {
        return googleApiClient.zza((zzn) new zzdzq(this, googleApiClient, str, str2, str3));
    }

    public PendingResult<Status> commitToConfiguration(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zza((zzn) new zzdzs(this, googleApiClient, str));
    }

    public PendingResult<PhenotypeApi.ExperimentTokensResult> getExperimentsForLogging(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zza((zzn) new zzdzu(this, googleApiClient, str));
    }

    public PendingResult<PhenotypeApi.ExperimentTokensResult> getExperiments(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zza((zzn) new zzdzw(this, googleApiClient, str));
    }

    public PendingResult<PhenotypeApi.DogfoodsTokenResult> getDogfoodsToken(GoogleApiClient googleApiClient) {
        return googleApiClient.zza((zzn) new zzdzy(this, googleApiClient));
    }

    public PendingResult<Status> setDogfoodsToken(GoogleApiClient googleApiClient, byte[] bArr) {
        return googleApiClient.zza((zzn) new zzeaa(this, googleApiClient, bArr));
    }

    public PendingResult<PhenotypeApi.FlagResult> getFlag(GoogleApiClient googleApiClient, String str, String str2, int i) {
        return googleApiClient.zza((zzn) new zzdyw(this, googleApiClient, str, str2, i));
    }

    public PendingResult<PhenotypeApi.ConfigurationsResult> getCommittedConfiguration(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zza((zzn) new zzdyy(this, googleApiClient, str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0031 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.common.api.PendingResult<com.google.android.gms.common.api.Status> syncAfter(com.google.android.gms.common.api.GoogleApiClient r9, com.google.android.gms.phenotype.ServingVersion r10) {
        /*
            r8 = this;
            java.lang.Class<com.google.android.gms.internal.zzdyt> r0 = com.google.android.gms.internal.zzdyt.class
            monitor-enter(r0)
            long r1 = r10.getServingVersion()     // Catch:{ all -> 0x003c }
            long r3 = com.google.android.gms.internal.zzdyt.zza     // Catch:{ all -> 0x003c }
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0025
            r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x0023
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0023
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r1 = 0
            goto L_0x0026
        L_0x0025:
            r1 = 1
        L_0x0026:
            if (r1 != 0) goto L_0x0031
            com.google.android.gms.common.api.Status r9 = com.google.android.gms.common.api.Status.zza     // Catch:{ all -> 0x003c }
            r10 = 0
            com.google.android.gms.common.api.PendingResult r9 = com.google.android.gms.common.api.PendingResults.zza(r9, r10)     // Catch:{ all -> 0x003c }
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return r9
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            com.google.android.gms.internal.zzdza r0 = new com.google.android.gms.internal.zzdza
            r0.<init>(r8, r9, r10)
            com.google.android.gms.common.api.internal.zzn r9 = r9.zza(r0)
            return r9
        L_0x003c:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdyt.syncAfter(com.google.android.gms.common.api.GoogleApiClient, com.google.android.gms.phenotype.ServingVersion):com.google.android.gms.common.api.PendingResult");
    }

    public PendingResult<PhenotypeApi.ConfigurationsResult> registerSync(GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr, String str2, String str3) {
        return googleApiClient.zza((zzn) new zzdzc(this, googleApiClient, str, i, strArr, bArr, str2, str3));
    }

    public PendingResult<Status> setFlagOverride(GoogleApiClient googleApiClient, String str, String str2, String str3, int i, int i2, String str4) {
        return googleApiClient.zza((zzn) new zzdze(this, googleApiClient, str, str2, str3, i, i2, str4));
    }

    public PendingResult<PhenotypeApi.FlagOverridesResult> deleteFlagOverrides(GoogleApiClient googleApiClient, String str, String str2, String str3) {
        return googleApiClient.zza((zzn) new zzdzg(this, googleApiClient, str, str2, str3));
    }

    public PendingResult<PhenotypeApi.FlagOverridesResult> listFlagOverrides(GoogleApiClient googleApiClient, String str, String str2, String str3) {
        return googleApiClient.zza((zzn) new zzdzi(this, googleApiClient, str, str2, str3));
    }

    public PendingResult<PhenotypeApi.ConfigurationsResult> getAlternateConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2, String str3, String str4) {
        return googleApiClient.zza((zzn) new zzdzk(this, googleApiClient, str, str2, str3, str4));
    }
}
