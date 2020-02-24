package com.google.android.gms.phenotype;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.internal.zzdyq;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class PhenotypeClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    /* access modifiers changed from: private */
    public static long zzb = 0;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
     arg types: [android.app.Activity, com.google.android.gms.common.api.Api<com.google.android.gms.common.api.Api$ApiOptions$NoOptions>, ?[OBJECT, ARRAY], com.google.android.gms.common.api.GoogleApi$zza]
     candidates:
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void */
    PhenotypeClient(Activity activity) {
        super(activity, (Api) Phenotype.API, (Api.ApiOptions) null, GoogleApi.zza.zza);
    }

    static class zza extends zzdyq {
        private final TaskCompletionSource zza;

        private zza(TaskCompletionSource taskCompletionSource) {
            this.zza = taskCompletionSource;
        }

        public final void zzb(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zzc(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zzd(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zze(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zza(Status status, Configurations configurations) {
            zzdm.zza(status, configurations, this.zza);
        }

        public final void zzf(Status status) {
            if (status.isSuccess()) {
                ConfigurationContentLoader.invalidateAllCaches();
            }
            zzdm.zza(status, null, this.zza);
        }

        public final void zza(Status status, ExperimentTokens experimentTokens) {
            zzdm.zza(status, experimentTokens, this.zza);
        }

        public final void zza(Status status, DogfoodsToken dogfoodsToken) {
            zzdm.zza(status, dogfoodsToken, this.zza);
        }

        public final void zzg(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zza(Status status, Flag flag) {
            zzdm.zza(status, flag, this.zza);
        }

        public final void zzb(Status status, Configurations configurations) {
            zzdm.zza(status, configurations, this.zza);
        }

        public void zza(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zzh(Status status) {
            zzdm.zza(status, null, this.zza);
        }

        public final void zza(Status status, FlagOverrides flagOverrides) {
            zzdm.zza(status, flagOverrides, this.zza);
        }

        /* synthetic */ zza(TaskCompletionSource taskCompletionSource, zzo zzo) {
            this(taskCompletionSource);
        }
    }

    PhenotypeClient(Context context) {
        super(context, Phenotype.API, (Api.ApiOptions) null, GoogleApi.zza.zza);
    }

    public Task<Void> register(String str, int i, String[] strArr, byte[] bArr) {
        return zza(new zzo(this, str, i, strArr, bArr));
    }

    public Task<Void> weakRegister(String str, int i, String[] strArr, int[] iArr, byte[] bArr) {
        return zza(new zzaa(this, str, i, strArr, iArr, bArr));
    }

    public Task<Void> bulkRegister(RegistrationInfo[] registrationInfoArr) {
        return zza(new zzac(this, registrationInfoArr));
    }

    public Task<Void> setAppSpecificProperties(String str, byte[] bArr) {
        return zza(new zzad(this, str, bArr));
    }

    public Task<Void> unRegister(String str) {
        return zza(new zzae(this, str));
    }

    public Task<Configurations> getConfigurationSnapshot(String str, String str2, String str3) {
        return zza(new zzaf(this, str, str2, str3));
    }

    public Task<Void> commitToConfiguration(String str) {
        return zza(new zzag(this, str));
    }

    public Task<Void> commitToCurrentConfiguration(String str, String str2) {
        return zza(new zzah(this, str, str2));
    }

    public Task<ExperimentTokens> getExperimentsForLogging(String str) {
        return zza(new zzai(this, str));
    }

    public Task<ExperimentTokens> getExperiments(String str) {
        return zza(new zzp(this, str));
    }

    public Task<DogfoodsToken> getDogfoodsToken() {
        return zza(new zzq(this));
    }

    public Task<Void> setDogfoodsToken(byte[] bArr) {
        return zza(new zzr(this, bArr));
    }

    public Task<Flag> getFlag(String str, String str2, int i) {
        return zza(new zzs(this, str, str2, i));
    }

    public Task<Configurations> getCommittedConfiguration(String str) {
        return zza(new zzt(this, str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002f A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.tasks.Task<java.lang.Void> syncAfter(com.google.android.gms.phenotype.ServingVersion r9) {
        /*
            r8 = this;
            java.lang.Class<com.google.android.gms.phenotype.PhenotypeClient> r0 = com.google.android.gms.phenotype.PhenotypeClient.class
            monitor-enter(r0)
            long r1 = r9.getServingVersion()     // Catch:{ all -> 0x003a }
            long r3 = com.google.android.gms.phenotype.PhenotypeClient.zzb     // Catch:{ all -> 0x003a }
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
            if (r1 != 0) goto L_0x002f
            r9 = 0
            com.google.android.gms.tasks.Task r9 = com.google.android.gms.tasks.Tasks.forResult(r9)     // Catch:{ all -> 0x003a }
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return r9
        L_0x002f:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            com.google.android.gms.phenotype.zzu r0 = new com.google.android.gms.phenotype.zzu
            r0.<init>(r8, r9)
            com.google.android.gms.tasks.Task r9 = r8.zza(r0)
            return r9
        L_0x003a:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.phenotype.PhenotypeClient.syncAfter(com.google.android.gms.phenotype.ServingVersion):com.google.android.gms.tasks.Task");
    }

    public Task<Configurations> registerSync(String str, int i, String[] strArr, byte[] bArr, String str2, String str3) {
        return zza(new zzw(this, str, i, strArr, bArr, str2, str3));
    }

    public Task<Void> setFlagOverride(String str, String str2, String str3, int i, int i2, String str4) {
        return setFlagOverrides(str, str2, new Flag[]{new Flag(str3, str4, i2, i)});
    }

    public Task<Void> setFlagOverrides(String str, String str2, Flag[] flagArr) {
        return zza(new zzx(this, str, str2, flagArr));
    }

    public Task<FlagOverrides> deleteFlagOverrides(String str, String str2, String str3) {
        return zza(new zzy(this, str, str2, str3));
    }

    public Task<FlagOverrides> listFlagOverrides(String str, String str2, String str3) {
        return zza(new zzz(this, str, str2, str3));
    }

    public Task<Configurations> getAlternateConfigurationSnapshot(String str, String str2, String str3, String str4) {
        return zza(new zzab(this, str, str2, str3, str4));
    }
}
