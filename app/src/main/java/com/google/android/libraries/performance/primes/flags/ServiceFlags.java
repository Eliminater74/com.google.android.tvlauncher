package com.google.android.libraries.performance.primes.flags;

import android.content.Context;
import com.google.android.libraries.performance.primes.PrimesFlags;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.Supplier;
import com.google.android.libraries.performance.primes.trace.PrimesTrace;
import com.google.android.libraries.phenotype.client.PhenotypeFlag;
import java.util.HashMap;
import java.util.Map;

public class ServiceFlags {
    private static final String DISABLE_MEMORY_SUMMARY = "disable_memory_summary_metrics";
    private static final String ENABLE_PER_APP_ENABLE_PERSIST_CRASH_STATS = "enable_persist_crash_stats";
    private static final String ENABLE_PER_APP_ENABLE_STARTUP_TRACE = "enable_startup_trace";
    private static final String ENABLE_PER_APP_ENABLE_URL_AUTO_SANITIZATION = "enable_url_auto_sanitization";
    private static final String ENABLE_PER_APP_LEAK_DETECTION = "enable_leak_detection";
    private static final String ENABLE_PER_APP_LEAK_DETECTION_V2 = "enable_leak_detection_v2";
    private static final String ENABLE_PER_APP_MAGIC_EYE_LOG = "enable_magic_eye_log";
    private static final String ENABLE_PER_APP_PRIMES_FOR_PRIMES = "enable_primes_for_primes";
    public static final String SHARED_PREFS_FILE = "primes-ph";
    private static final String TAG = "PrimesServerFlags";

    private static final class FlagWithDefault {
        /* access modifiers changed from: private */
        public final boolean defaultValue;
        /* access modifiers changed from: private */
        public final String flagName;

        FlagWithDefault(String flagName2, boolean defaultValue2) {
            this.flagName = flagName2;
            this.defaultValue = defaultValue2;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, boolean):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.Boolean>
     arg types: [java.lang.String, int]
     candidates:
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, double):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.Double>
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, int):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.Integer>
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, long):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.Long>
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, java.lang.String):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.String>
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, byte[]):com.google.android.libraries.phenotype.client.PhenotypeFlag<byte[]>
      com.google.android.libraries.phenotype.client.PhenotypeFlag.Factory.createFlag(java.lang.String, boolean):com.google.android.libraries.phenotype.client.PhenotypeFlag<java.lang.Boolean> */
    private static Map<String, PhenotypeFlag<Boolean>> initFlags(Context context, PrimesFlags defaults) {
        PhenotypeFlag.Factory perAppFlagFactory = new PhenotypeFlag.Factory(SHARED_PREFS_FILE).withPhenotypePrefix("PrimesFlagsFeature__").withGservicePrefix(String.format("primes:%s:", context.getPackageName())).disableBypassPhenotypeForDebug();
        PhenotypeFlag.Factory globalFlagFactory = new PhenotypeFlag.Factory(SHARED_PREFS_FILE).withPhenotypePrefix("PrimesFlagsFeature__").withGservicePrefix("primes:").disableBypassPhenotypeForDebug();
        FlagWithDefault[] perAppFlags = {new FlagWithDefault(ENABLE_PER_APP_LEAK_DETECTION, defaults.isLeakDetectionEnabled()), new FlagWithDefault(ENABLE_PER_APP_LEAK_DETECTION_V2, defaults.isLeakDetectionV2Enabled()), new FlagWithDefault(ENABLE_PER_APP_MAGIC_EYE_LOG, defaults.isMagicEyeLogEnabled()), new FlagWithDefault(ENABLE_PER_APP_ENABLE_STARTUP_TRACE, defaults.isStartupTraceEnabled()), new FlagWithDefault(ENABLE_PER_APP_ENABLE_URL_AUTO_SANITIZATION, defaults.isUrlAutoSanitizationEnabled()), new FlagWithDefault(ENABLE_PER_APP_ENABLE_PERSIST_CRASH_STATS, defaults.isPersistCrashStatsEnabled()), new FlagWithDefault(ENABLE_PER_APP_PRIMES_FOR_PRIMES, defaults.isPrimesForPrimesEnabled())};
        Map<String, PhenotypeFlag<Boolean>> phenotypeFlags = new HashMap<>();
        for (FlagWithDefault flag : perAppFlags) {
            phenotypeFlags.put(flag.flagName, perAppFlagFactory.createFlag(flag.flagName, flag.defaultValue));
        }
        phenotypeFlags.put(DISABLE_MEMORY_SUMMARY, globalFlagFactory.createFlag(DISABLE_MEMORY_SUMMARY, false));
        PhenotypeFlag.maybeInit(context);
        return phenotypeFlags;
    }

    public static class GserviceFlagsSupplier implements Supplier<PrimesFlags> {
        private final Context context;

        public GserviceFlagsSupplier(Context context2) {
            this.context = context2;
        }

        public PrimesFlags get() {
            PrimesLog.m50i("PrimesTesting", "GserviceFlagsSupplier.get()", new Object[0]);
            return ServiceFlags.readPrimesFlags(this.context);
        }
    }

    public static class DefaultFlagsSupplier implements Supplier<PrimesFlags> {
        public PrimesFlags get() {
            PrimesLog.m50i("PrimesTesting", "DefaultFlagsSupplier.get()", new Object[0]);
            return PrimesFlags.newBuilder().build();
        }
    }

    public static PrimesFlags readPrimesFlags(Context context) {
        return readPrimesFlags(context, PrimesFlags.newBuilder().build());
    }

    static PrimesFlags readPrimesFlags(Context context, PrimesFlags defaults) {
        try {
            PrimesTrace.beginSection("ServiceFlags-updateFlags");
            Map<String, PhenotypeFlag<Boolean>> flags = initFlags(context, defaults);
            return PrimesFlags.newBuilder().enableLeakDetection(((Boolean) flags.get(ENABLE_PER_APP_LEAK_DETECTION).get()).booleanValue()).enableLeakDetectionV2(((Boolean) flags.get(ENABLE_PER_APP_LEAK_DETECTION_V2).get()).booleanValue()).disableMemorySummary(((Boolean) flags.get(DISABLE_MEMORY_SUMMARY).get()).booleanValue()).enableMagicEyeLog(((Boolean) flags.get(ENABLE_PER_APP_MAGIC_EYE_LOG).get()).booleanValue()).enablePersistCrashStats(((Boolean) flags.get(ENABLE_PER_APP_ENABLE_PERSIST_CRASH_STATS).get()).booleanValue()).enableStartupTrace(((Boolean) flags.get(ENABLE_PER_APP_ENABLE_STARTUP_TRACE).get()).booleanValue()).enableUrlAutoSanitization(((Boolean) flags.get(ENABLE_PER_APP_ENABLE_URL_AUTO_SANITIZATION).get()).booleanValue()).enablePrimesForPrimes(((Boolean) flags.get(ENABLE_PER_APP_PRIMES_FOR_PRIMES).get()).booleanValue()).build();
        } finally {
            PrimesTrace.endSection();
        }
    }

    private ServiceFlags() {
    }
}
