package com.google.android.gms.clearcut;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.clearcut.internal.PlayLoggerContext;
import com.google.android.gms.clearcut.internal.zzi;
import com.google.android.gms.clearcut.internal.zzs;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.zzgtd;
import com.google.android.gms.phenotype.ExperimentTokens;

import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@KeepForSdk
public final class ClearcutLogger {
    public static final int QOS_TIER_DEFAULT = 0;
    public static final int QOS_TIER_FAST_IF_RADIO_AWAKE = 3;
    public static final int QOS_TIER_UNMETERED_ONLY = 1;
    public static final int QOS_TIER_UNMETERED_OR_DAILY = 2;
    /* access modifiers changed from: private */
    public static final ExperimentTokens[] zzc = new ExperimentTokens[0];
    /* access modifiers changed from: private */
    public static final String[] zzd = new String[0];
    /* access modifiers changed from: private */
    public static final byte[][] zze = new byte[0][];
    @Hide
    private static final Api.ClientKey<zzi> zza = new Api.ClientKey<>();
    @Hide
    private static final Api.zza<zzi, Api.ApiOptions.NoOptions> zzb = new zzd();
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("ClearcutLogger.API", zzb, zza);
    /* access modifiers changed from: private */
    public final String zzf;
    /* access modifiers changed from: private */
    public final int zzg;
    /* access modifiers changed from: private */
    public final boolean zzl;
    /* access modifiers changed from: private */
    public final ClearcutLoggerApi zzn;
    /* access modifiers changed from: private */
    public final Clock zzo;
    /* access modifiers changed from: private */
    public final LogSampler zzq;
    /* access modifiers changed from: private */
    public String zzh;
    /* access modifiers changed from: private */
    public int zzi;
    /* access modifiers changed from: private */
    public String zzj;
    /* access modifiers changed from: private */
    public String zzk;
    /* access modifiers changed from: private */
    public int zzm;
    /* access modifiers changed from: private */
    public TimeZoneOffsetProvider zzp;

    @Deprecated
    public ClearcutLogger(Context context, String str, String str2, String str3) {
        this(context, -1, str, str2, str3, false, zzb.zza(context), zzh.zza(), null, new zzs(context));
    }

    public ClearcutLogger(Context context, String str, String str2) {
        this(context, -1, str, str2, null, false, zzb.zza(context), zzh.zza(), null, new zzs(context));
    }

    @Deprecated
    public ClearcutLogger(Context context, String str, String str2, String str3, ClearcutLoggerApi clearcutLoggerApi, Clock clock, TimeZoneOffsetProvider timeZoneOffsetProvider) {
        this(context, -1, str, str2, str3, false, clearcutLoggerApi, clock, timeZoneOffsetProvider, new zzs(context));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ClearcutLogger(Context context, String str, String str2, ClearcutLoggerApi clearcutLoggerApi, Clock clock, TimeZoneOffsetProvider timeZoneOffsetProvider) {
        this(context, -1, str, str2, null, false, clearcutLoggerApi, clock != null ? clock : zzh.zza(), timeZoneOffsetProvider, new zzs(context));
    }

    @Deprecated
    public ClearcutLogger(Context context, int i, String str, String str2) {
        this(context, i, str, str2, zzb.zza(context), zzh.zza(), new TimeZoneOffsetProvider());
    }

    @Deprecated
    public ClearcutLogger(Context context, int i, String str, String str2, ClearcutLoggerApi clearcutLoggerApi, Clock clock, TimeZoneOffsetProvider timeZoneOffsetProvider) {
        this(context, i, "", str, str2, false, clearcutLoggerApi, clock, timeZoneOffsetProvider, new zzs(context));
    }

    public ClearcutLogger(Context context, int i, String str, String str2, String str3, boolean z, ClearcutLoggerApi clearcutLoggerApi, Clock clock, TimeZoneOffsetProvider timeZoneOffsetProvider) {
        this(context, i, str, str2, str3, z, clearcutLoggerApi, clock, timeZoneOffsetProvider, new zze());
    }

    public ClearcutLogger(Context context, int i, String str, String str2, String str3, boolean z, ClearcutLoggerApi clearcutLoggerApi, Clock clock, TimeZoneOffsetProvider timeZoneOffsetProvider, LogSampler logSampler) {
        this.zzi = -1;
        boolean z2 = false;
        this.zzm = 0;
        this.zzf = context.getPackageName();
        this.zzg = zza(context);
        this.zzi = i;
        this.zzh = str;
        this.zzj = str2;
        this.zzk = str3;
        this.zzl = z;
        this.zzn = clearcutLoggerApi;
        this.zzo = clock;
        this.zzp = timeZoneOffsetProvider == null ? new TimeZoneOffsetProvider() : timeZoneOffsetProvider;
        this.zzm = 0;
        this.zzq = logSampler;
        if (z) {
            zzau.zzb(str2 == null ? true : z2, "can't be anonymous with an upload account");
        }
    }

    @KeepForSdk
    public static ClearcutLogger anonymousLogger(Context context, String str) {
        return new ClearcutLogger(context, -1, str, null, null, true, zzb.zza(context), zzh.zza(), null, new zzs(context));
    }

    private static int zza(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("ClearcutLogger", "This can't happen.", e);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public static int[] zzb(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            iArr[i2] = ((Integer) obj).intValue();
            i2++;
        }
        return iArr;
    }

    public final boolean isAnonymous() {
        return this.zzl;
    }

    public final ClearcutLogger setTimeZoneOffsetProvider(TimeZoneOffsetProvider timeZoneOffsetProvider) {
        if (timeZoneOffsetProvider == null) {
            timeZoneOffsetProvider = new TimeZoneOffsetProvider();
        }
        this.zzp = timeZoneOffsetProvider;
        return this;
    }

    public final ClearcutLogger setQosTier(int i) {
        if (i < 0 || i > 4) {
            i = 0;
        }
        this.zzm = i;
        return this;
    }

    @KeepForSdk
    public final LogEventBuilder newEvent(byte[] bArr) {
        return new LogEventBuilder(this, bArr, (zzd) null);
    }

    public final LogEventBuilder newEvent(MessageProducer messageProducer) {
        return new LogEventBuilder(this, messageProducer, (zzd) null);
    }

    @Deprecated
    public final boolean flush(GoogleApiClient googleApiClient, long j, TimeUnit timeUnit) {
        return flush(j, timeUnit);
    }

    @WorkerThread
    public final boolean flush(long j, TimeUnit timeUnit) {
        return this.zzn.flush(null, j, timeUnit);
    }

    @Deprecated
    public final void disconnectAsync(GoogleApiClient googleApiClient) {
        googleApiClient.disconnect();
    }

    @Deprecated
    public final int getLogSource() {
        return this.zzi;
    }

    public final String getLogSourceName() {
        return this.zzh;
    }

    public final String getUploadAccountName() {
        return this.zzj;
    }

    @Deprecated
    public final String getLoggingId() {
        return this.zzk;
    }

    @Deprecated
    public final PendingResult<Status> forceUpload() {
        return this.zzn.forceUpload();
    }

    public interface LogSampler {
        boolean shouldLog(String str, int i);
    }

    public interface MessageProducer {
        byte[] toProtoBytes();
    }

    public static class TimeZoneOffsetProvider {
        public long getOffsetSeconds(long j) {
            return (long) (TimeZone.getDefault().getOffset(j) / 1000);
        }
    }

    public class LogEventBuilder {
        private final MessageProducer zzf;
        private final zzgtd zzn;
        private int zza;
        private String zzb;
        private String zzc;
        private String zzd;
        private int zze;
        private MessageProducer zzg;
        private ArrayList<Integer> zzh;
        private ArrayList<String> zzi;
        private ArrayList<Integer> zzj;
        private ArrayList<ExperimentTokens> zzk;
        private ArrayList<byte[]> zzl;
        private boolean zzm;
        private boolean zzo;

        private LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr) {
            this(bArr, (MessageProducer) null);
        }

        private LogEventBuilder(ClearcutLogger clearcutLogger, MessageProducer messageProducer) {
            this((byte[]) null, messageProducer);
        }

        private LogEventBuilder(byte[] bArr, MessageProducer messageProducer) {
            this.zza = ClearcutLogger.this.zzi;
            this.zzb = ClearcutLogger.this.zzh;
            this.zzc = ClearcutLogger.this.zzj;
            this.zzd = ClearcutLogger.this.zzk;
            this.zze = ClearcutLogger.this.zzm;
            this.zzh = null;
            this.zzi = null;
            this.zzj = null;
            this.zzk = null;
            this.zzl = null;
            this.zzm = true;
            this.zzn = new zzgtd();
            this.zzo = false;
            this.zzc = ClearcutLogger.this.zzj;
            this.zzd = ClearcutLogger.this.zzk;
            this.zzn.zza = ClearcutLogger.this.zzo.currentTimeMillis();
            this.zzn.zzb = ClearcutLogger.this.zzo.elapsedRealtime();
            this.zzn.zzg = ClearcutLogger.this.zzp.getOffsetSeconds(this.zzn.zza);
            if (bArr != null) {
                this.zzn.zzf = bArr;
            }
            this.zzf = messageProducer;
        }

        /* synthetic */ LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr, zzd zzd2) {
            this(clearcutLogger, bArr);
        }

        /* synthetic */ LogEventBuilder(ClearcutLogger clearcutLogger, MessageProducer messageProducer, zzd zzd2) {
            this(clearcutLogger, messageProducer);
        }

        @Deprecated
        public LogEventBuilder setLogSource(int i) {
            this.zza = i;
            return this;
        }

        public LogEventBuilder setLogSourceName(String str) {
            this.zzb = str;
            return this;
        }

        public LogEventBuilder setQosTier(int i) {
            this.zze = i;
            return this;
        }

        public LogEventBuilder setUploadAccountName(String str) {
            if (!ClearcutLogger.this.zzl) {
                this.zzc = str;
                return this;
            }
            throw new IllegalArgumentException("setUploadAccountName forbidden on anonymous logger");
        }

        @Deprecated
        public LogEventBuilder setLoggingId(String str) {
            this.zzd = str;
            return this;
        }

        @Deprecated
        public LogEventBuilder setTag(String str) {
            this.zzn.zzc = str;
            return this;
        }

        public LogEventBuilder setEventCode(int i) {
            this.zzn.zzd = i;
            return this;
        }

        public LogEventBuilder setEventFlowId(int i) {
            this.zzn.zze = i;
            return this;
        }

        public LogEventBuilder setClientVisualElements(byte[] bArr) {
            if (bArr != null) {
                this.zzn.zzh = bArr;
            }
            return this;
        }

        public LogEventBuilder setClientVisualElementsProducer(MessageProducer messageProducer) {
            this.zzg = messageProducer;
            return this;
        }

        public LogEventBuilder addTestCode(int i) {
            if (this.zzh == null) {
                this.zzh = new ArrayList<>();
            }
            this.zzh.add(Integer.valueOf(i));
            return this;
        }

        public LogEventBuilder addMendelPackage(String str) {
            if (ClearcutLogger.this.zzl) {
                Log.e("ClearcutLogger", "addMendelPackage forbidden on anonymous logger");
            }
            if (this.zzi == null) {
                this.zzi = new ArrayList<>();
            }
            this.zzi.add(str);
            return this;
        }

        public LogEventBuilder addExperimentIds(int[] iArr) {
            if (ClearcutLogger.this.zzl) {
                Log.e("ClearcutLogger", "addExperimentIds forbidden on anonymous logger");
            }
            if (iArr == null || iArr.length == 0) {
                return this;
            }
            if (this.zzj == null) {
                this.zzj = new ArrayList<>();
            }
            for (int valueOf : iArr) {
                this.zzj.add(Integer.valueOf(valueOf));
            }
            return this;
        }

        public LogEventBuilder addExperimentTokens(ExperimentTokens experimentTokens) {
            if (ClearcutLogger.this.zzl) {
                throw new IllegalArgumentException("addExperimentTokens forbidden on anonymous logger");
            } else if (experimentTokens == null) {
                return this;
            } else {
                if (this.zzk == null) {
                    this.zzk = new ArrayList<>();
                }
                this.zzk.add(experimentTokens);
                return this;
            }
        }

        public LogEventBuilder addExperimentTokensAndSkipPhenotype(ExperimentTokens experimentTokens) {
            if (!ClearcutLogger.this.zzl) {
                this.zzm = false;
                return addExperimentTokens(experimentTokens);
            }
            throw new IllegalArgumentException("addExperimentTokens forbidden on anonymous logger");
        }

        @Deprecated
        public LogEventBuilder addExperimentToken(byte[] bArr) {
            if (ClearcutLogger.this.zzl) {
                throw new IllegalArgumentException("addExperimentToken forbidden on anonymous logger");
            } else if (bArr == null || bArr.length == 0) {
                return this;
            } else {
                if (this.zzl == null) {
                    this.zzl = new ArrayList<>();
                }
                this.zzl.add(bArr);
                return this;
            }
        }

        @Deprecated
        public LogEventBuilder addExperimentTokenAndSkipPhenotype(byte[] bArr) {
            if (!ClearcutLogger.this.zzl) {
                this.zzm = false;
                return addExperimentToken(bArr);
            }
            throw new IllegalArgumentException("addExperimentToken forbidden on anonymous logger");
        }

        public LogEventParcelable getLogEventParcelable() {
            ExperimentTokens[] experimentTokensArr;
            PlayLoggerContext playLoggerContext = new PlayLoggerContext(ClearcutLogger.this.zzf, ClearcutLogger.this.zzg, this.zza, this.zzb, this.zzc, this.zzd, ClearcutLogger.this.zzl, this.zze);
            zzgtd zzgtd = this.zzn;
            MessageProducer messageProducer = this.zzf;
            MessageProducer messageProducer2 = this.zzg;
            int[] zza2 = ClearcutLogger.zzb(this.zzh);
            ArrayList<String> arrayList = this.zzi;
            String[] strArr = arrayList != null ? (String[]) arrayList.toArray(ClearcutLogger.zzd) : null;
            int[] zza3 = ClearcutLogger.zzb(this.zzj);
            ArrayList<byte[]> arrayList2 = this.zzl;
            byte[][] bArr = arrayList2 != null ? (byte[][]) arrayList2.toArray(ClearcutLogger.zze) : null;
            ArrayList<ExperimentTokens> arrayList3 = this.zzk;
            if (arrayList3 != null) {
                experimentTokensArr = (ExperimentTokens[]) arrayList3.toArray(ClearcutLogger.zzc);
            } else {
                experimentTokensArr = null;
            }
            return new LogEventParcelable(playLoggerContext, zzgtd, messageProducer, messageProducer2, zza2, strArr, zza3, bArr, experimentTokensArr, this.zzm);
        }

        @Deprecated
        public PendingResult<Status> log(GoogleApiClient googleApiClient) {
            return logAsync();
        }

        @KeepForSdk
        public void log() {
            logAsync();
        }

        @Deprecated
        public PendingResult<Status> logAsync(GoogleApiClient googleApiClient) {
            return logAsync();
        }

        @Deprecated
        public PendingResult<Status> logAsync() {
            if (!this.zzo) {
                this.zzo = true;
                LogEventParcelable logEventParcelable = getLogEventParcelable();
                PlayLoggerContext playLoggerContext = logEventParcelable.playLoggerContext;
                if (ClearcutLogger.this.zzq.shouldLog(playLoggerContext.logSourceName, playLoggerContext.logSource)) {
                    return ClearcutLogger.this.zzn.logEvent(logEventParcelable);
                }
                return PendingResults.zza(Status.zza, (GoogleApiClient) null);
            }
            throw new IllegalStateException("do not reuse LogEventBuilder");
        }
    }
}
