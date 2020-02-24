package com.google.android.libraries.phenotype.client;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.android.gsf.Gservices;
import com.google.common.base.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public abstract class PhenotypeFlag<T> {
    public static final String DIRECT_BOOT_PREFIX = "direct_boot:";
    private static final String TAG = "PhenotypeFlag";
    @SuppressLint({"StaticFieldLeak"})
    private static Context context = null;
    private static final AtomicInteger globalVersion = new AtomicInteger();
    private static Collection<PhenotypeFlag<?>> overriddenFlags;
    private static final Object setContextLock = new Object();
    private static boolean testMode = false;
    private volatile T cachedValue;
    private volatile int cachedVersion;
    private final T defaultValue;
    private final Factory factory;
    private final String name;

    public interface BytesConverter<T> {
        T fromBytes(byte[] bArr) throws IOException;
    }

    /* access modifiers changed from: package-private */
    public abstract T convertValue(Object obj);

    public static void init(Context ctx) {
        if (!testMode) {
            synchronized (setContextLock) {
                Context contextToUse = ctx.getApplicationContext();
                if (contextToUse == null) {
                    contextToUse = ctx;
                }
                if (context != contextToUse) {
                    synchronized (ConfigurationContentLoader.class) {
                        ConfigurationContentLoader.LOADERS_BY_URI.clear();
                    }
                    synchronized (SharedPreferencesLoader.class) {
                        SharedPreferencesLoader.LOADERS_BY_NAME.clear();
                    }
                    synchronized (GservicesLoader.class) {
                        GservicesLoader.loader = null;
                    }
                    invalidateProcessCache();
                    context = contextToUse;
                }
            }
        }
    }

    public static void initForTest() {
        setTestMode(true);
    }

    public static void maybeInit(Context ctx) {
        if (context == null && !testMode) {
            init(ctx);
        }
    }

    public static void setTestMode(boolean testMode2) {
        testMode = testMode2;
    }

    public static class Factory {
        /* access modifiers changed from: private */
        @Nullable
        public final Function<Context, Boolean> allowGservicesFn;
        /* access modifiers changed from: private */
        public final boolean autoSubpackage;
        /* access modifiers changed from: private */
        public final Uri contentProviderUri;
        /* access modifiers changed from: private */
        public final boolean disableBypassPhenotypeForDebug;
        /* access modifiers changed from: private */
        public final String gservicesPrefix;
        /* access modifiers changed from: private */
        public final String phenotypePrefix;
        /* access modifiers changed from: private */
        public final boolean preferGservices;
        /* access modifiers changed from: private */
        public final String sharedPrefsName;
        /* access modifiers changed from: private */
        public final boolean skipGservices;

        public Factory(String sharedPrefsName2) {
            this(sharedPrefsName2, null, "", "", false, false, false, false, null);
        }

        public Factory(Uri contentProviderUri2) {
            this(null, contentProviderUri2, "", "", false, false, false, false, null);
        }

        private Factory(String sharedPrefsName2, Uri contentProviderUri2, String gservicesPrefix2, String phenotypePrefix2, boolean skipGservices2, boolean preferGservices2, boolean disableBypassPhenotypeForDebug2, boolean autoSubpackage2, @Nullable Function<Context, Boolean> allowGservicesFn2) {
            this.sharedPrefsName = sharedPrefsName2;
            this.contentProviderUri = contentProviderUri2;
            this.gservicesPrefix = gservicesPrefix2;
            this.phenotypePrefix = phenotypePrefix2;
            this.skipGservices = skipGservices2;
            this.preferGservices = preferGservices2;
            this.disableBypassPhenotypeForDebug = disableBypassPhenotypeForDebug2;
            this.autoSubpackage = autoSubpackage2;
            this.allowGservicesFn = allowGservicesFn2;
        }

        public Factory withGservicePrefix(String gservicesPrefix2) {
            boolean z = this.skipGservices;
            if (!z) {
                return new Factory(this.sharedPrefsName, this.contentProviderUri, gservicesPrefix2, this.phenotypePrefix, z, this.preferGservices, this.disableBypassPhenotypeForDebug, this.autoSubpackage, this.allowGservicesFn);
            }
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }

        public Factory withPhenotypePrefix(String phenotypePrefix2) {
            return new Factory(this.sharedPrefsName, this.contentProviderUri, this.gservicesPrefix, phenotypePrefix2, this.skipGservices, this.preferGservices, this.disableBypassPhenotypeForDebug, this.autoSubpackage, this.allowGservicesFn);
        }

        public Factory skipGservices() {
            if (this.gservicesPrefix.isEmpty()) {
                Function<Context, Boolean> function = this.allowGservicesFn;
                if (function == null) {
                    return new Factory(this.sharedPrefsName, this.contentProviderUri, this.gservicesPrefix, this.phenotypePrefix, true, this.preferGservices, this.disableBypassPhenotypeForDebug, this.autoSubpackage, function);
                }
                throw new IllegalStateException("Cannot skip gservices both always and conditionally");
            }
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }

        public Factory allowGservicesIf(Function<Context, Boolean> allowGservicesFn2) {
            boolean z = this.skipGservices;
            if (!z) {
                return new Factory(this.sharedPrefsName, this.contentProviderUri, this.gservicesPrefix, this.phenotypePrefix, z, this.preferGservices, this.disableBypassPhenotypeForDebug, this.autoSubpackage, allowGservicesFn2);
            }
            throw new IllegalStateException("Cannot skip gservices both always and conditionally");
        }

        public Factory preferGservices() {
            return new Factory(this.sharedPrefsName, this.contentProviderUri, this.gservicesPrefix, this.phenotypePrefix, this.skipGservices, true, this.disableBypassPhenotypeForDebug, this.autoSubpackage, this.allowGservicesFn);
        }

        public Factory disableBypassPhenotypeForDebug() {
            return new Factory(this.sharedPrefsName, this.contentProviderUri, this.gservicesPrefix, this.phenotypePrefix, this.skipGservices, this.preferGservices, true, this.autoSubpackage, this.allowGservicesFn);
        }

        public Factory enableAutoSubpackage() {
            Uri uri = this.contentProviderUri;
            if (uri != null) {
                return new Factory(this.sharedPrefsName, uri, this.gservicesPrefix, this.phenotypePrefix, this.skipGservices, this.preferGservices, this.disableBypassPhenotypeForDebug, true, this.allowGservicesFn);
            }
            throw new IllegalStateException("Cannot set enableAutoSubpackage on SharedPrefs-backed flags");
        }

        public PhenotypeFlag<Long> createFlag(String name, long defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<Boolean> createFlag(String name, boolean defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<Integer> createFlag(String name, int defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<Double> createFlag(String name, double defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<Float> createFloatFlag(String name, float defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<String> createFlag(String name, String defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public PhenotypeFlag<byte[]> createFlag(String name, byte[] defaultValue) {
            return PhenotypeFlag.value(this, name, defaultValue);
        }

        public <T> PhenotypeFlag<T> createFlag(String name, T defaultValue, BytesConverter<T> converter) {
            return PhenotypeFlag.value(this, name, defaultValue, converter);
        }
    }

    static void invalidateProcessCache() {
        globalVersion.incrementAndGet();
    }

    private PhenotypeFlag(Factory factory2, String name2, T defaultValue2) {
        this.cachedVersion = -1;
        if (factory2.sharedPrefsName == null && factory2.contentProviderUri == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        } else if (factory2.sharedPrefsName == null || factory2.contentProviderUri == null) {
            this.factory = factory2;
            this.name = name2;
            this.defaultValue = defaultValue2;
        } else {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
    }

    private String getPrefixedName(String prefix) {
        if (prefix != null && prefix.isEmpty()) {
            return this.name;
        }
        String valueOf = String.valueOf(prefix);
        String valueOf2 = String.valueOf(this.name);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public String getMendelFlagName() {
        return getPrefixedName(this.factory.phenotypePrefix);
    }

    public String getGservicesFlagName() {
        if (this.factory.skipGservices) {
            return null;
        }
        return getPrefixedName(this.factory.gservicesPrefix);
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T get() {
        int globalV = globalVersion.get();
        if (this.cachedVersion < globalV) {
            synchronized (this) {
                if (this.cachedVersion < globalV) {
                    this.cachedValue = getInternal();
                    this.cachedVersion = globalV;
                }
            }
        }
        return this.cachedValue;
    }

    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    private T getInternal() {
        if (testMode) {
            return this.defaultValue;
        }
        if (context != null) {
            if (this.factory.preferGservices) {
                T result = loadValueFromGservices();
                if (result != null) {
                    return result;
                }
                T result2 = loadValueFromPhenotype();
                if (result2 != null) {
                    return result2;
                }
            } else {
                T result3 = loadValueFromPhenotype();
                if (result3 != null) {
                    return result3;
                }
                T result4 = loadValueFromGservices();
                if (result4 != null) {
                    return result4;
                }
            }
            return this.defaultValue;
        }
        throw new IllegalStateException("Must call PhenotypeFlag.init() first");
    }

    @Nullable
    private T loadValueFromPhenotype() {
        Object value;
        if (!shouldBypassPhenotypeForDebug()) {
            FlagLoader loader = null;
            if (this.factory.contentProviderUri == null) {
                loader = SharedPreferencesLoader.getLoader(context, this.factory.sharedPrefsName);
            } else if (PhenotypeClientHelper.validateContentProvider(context, this.factory.contentProviderUri)) {
                if (this.factory.autoSubpackage) {
                    ContentResolver contentResolver = context.getContentResolver();
                    String lastPathSegment = this.factory.contentProviderUri.getLastPathSegment();
                    String packageName = context.getPackageName();
                    StringBuilder sb = new StringBuilder(String.valueOf(lastPathSegment).length() + 1 + String.valueOf(packageName).length());
                    sb.append(lastPathSegment);
                    sb.append("#");
                    sb.append(packageName);
                    loader = ConfigurationContentLoader.getLoader(contentResolver, PhenotypeConstants.getContentProviderUri(sb.toString()));
                } else {
                    loader = ConfigurationContentLoader.getLoader(context.getContentResolver(), this.factory.contentProviderUri);
                }
            }
            if (loader == null || (value = loader.getFlag(getMendelFlagName())) == null) {
                return null;
            }
            return convertValue(value);
        } else if (!Log.isLoggable(TAG, 3)) {
            return null;
        } else {
            String valueOf = String.valueOf(getMendelFlagName());
            Log.d(TAG, valueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(valueOf) : new String("Bypass reading Phenotype values for flag: "));
            return null;
        }
    }

    @Nullable
    private T loadValueFromGservices() {
        Object value;
        if (this.factory.skipGservices) {
            return null;
        }
        if ((this.factory.allowGservicesFn == null || ((Boolean) this.factory.allowGservicesFn.apply(context)).booleanValue()) && (value = GservicesLoader.getLoader(context).getFlag(getGservicesFlagName())) != null) {
            return convertValue(value);
        }
        return null;
    }

    public void override(T value) {
        if (value == null) {
            resetOverride();
            return;
        }
        this.cachedValue = value;
        this.cachedVersion = Integer.MAX_VALUE;
        if (overriddenFlags == null) {
            overriddenFlags = new ArrayList();
        }
        overriddenFlags.add(this);
    }

    public void resetOverride() {
        this.cachedVersion = -1;
    }

    public static void resetAllOverrides() {
        Collection<PhenotypeFlag<?>> collection = overriddenFlags;
        if (collection != null) {
            for (PhenotypeFlag<?> flag : collection) {
                flag.resetOverride();
            }
            overriddenFlags.clear();
        }
    }

    private boolean shouldBypassPhenotypeForDebug() {
        return !this.factory.disableBypassPhenotypeForDebug && GservicesLoader.getLoader(context).getBooleanFlagOrFalse("gms:phenotype:phenotype_flag:debug_bypass_phenotype");
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Long> value(Factory factory2, String name2, long defaultValue2) {
        return new PhenotypeFlag<Long>(factory2, name2, Long.valueOf(defaultValue2)) {
            /* access modifiers changed from: package-private */
            public Long convertValue(Object o) {
                if (o instanceof Long) {
                    return (Long) o;
                }
                if (o instanceof String) {
                    try {
                        return Long.valueOf(Long.parseLong((String) o));
                    } catch (NumberFormatException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 25 + String.valueOf(valueOf).length());
                sb.append("Invalid long value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Integer> value(Factory factory2, String name2, int defaultValue2) {
        return new PhenotypeFlag<Integer>(factory2, name2, Integer.valueOf(defaultValue2)) {
            /* access modifiers changed from: package-private */
            public Integer convertValue(Object o) {
                if (o instanceof Integer) {
                    return (Integer) o;
                }
                if (o instanceof Long) {
                    return Integer.valueOf(((Long) o).intValue());
                }
                if (o instanceof String) {
                    try {
                        return Integer.valueOf(Integer.parseInt((String) o));
                    } catch (NumberFormatException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 24 + String.valueOf(valueOf).length());
                sb.append("Invalid int value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Boolean> value(Factory factory2, String name2, boolean defaultValue2) {
        return new PhenotypeFlag<Boolean>(factory2, name2, Boolean.valueOf(defaultValue2)) {
            /* access modifiers changed from: package-private */
            public Boolean convertValue(Object o) {
                if (o instanceof Boolean) {
                    return (Boolean) o;
                }
                if (o instanceof String) {
                    String s = (String) o;
                    if (Gservices.TRUE_PATTERN.matcher(s).matches()) {
                        return true;
                    }
                    if (Gservices.FALSE_PATTERN.matcher(s).matches()) {
                        return false;
                    }
                }
                String s2 = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(s2).length() + 28 + String.valueOf(valueOf).length());
                sb.append("Invalid boolean value for ");
                sb.append(s2);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Double> value(Factory factory2, String name2, double defaultValue2) {
        return new PhenotypeFlag<Double>(factory2, name2, Double.valueOf(defaultValue2)) {
            /* access modifiers changed from: package-private */
            public Double convertValue(Object o) {
                if (o instanceof Double) {
                    return (Double) o;
                }
                if (o instanceof Float) {
                    return Double.valueOf(((Float) o).doubleValue());
                }
                if (o instanceof String) {
                    try {
                        return Double.valueOf(Double.parseDouble((String) o));
                    } catch (NumberFormatException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 27 + String.valueOf(valueOf).length());
                sb.append("Invalid double value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Float> value(Factory factory2, String name2, float defaultValue2) {
        return new PhenotypeFlag<Float>(factory2, name2, Float.valueOf(defaultValue2)) {
            /* access modifiers changed from: package-private */
            public Float convertValue(Object o) {
                if (o instanceof Float) {
                    return (Float) o;
                }
                if (o instanceof Double) {
                    return Float.valueOf(((Double) o).floatValue());
                }
                if (o instanceof String) {
                    try {
                        return Float.valueOf(Float.parseFloat((String) o));
                    } catch (NumberFormatException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 26 + String.valueOf(valueOf).length());
                sb.append("Invalid float value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<String> value(Factory factory2, String name2, String defaultValue2) {
        return new PhenotypeFlag<String>(factory2, name2, defaultValue2) {
            /* access modifiers changed from: package-private */
            public String convertValue(Object o) {
                if (o instanceof String) {
                    return (String) o;
                }
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<byte[]> value(Factory factory2, String name2, byte[] defaultValue2) {
        return new PhenotypeFlag<byte[]>(factory2, name2, defaultValue2) {
            /* access modifiers changed from: package-private */
            public byte[] convertValue(Object o) {
                if (o instanceof String) {
                    try {
                        return Base64.decode((String) o, 3);
                    } catch (IllegalArgumentException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 27 + String.valueOf(valueOf).length());
                sb.append("Invalid byte[] value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public static <T> PhenotypeFlag<T> value(Factory factory2, String name2, T defaultValue2, final BytesConverter<T> converter) {
        return new PhenotypeFlag<T>(factory2, name2, defaultValue2) {
            /* access modifiers changed from: package-private */
            public T convertValue(Object o) {
                if (o instanceof String) {
                    try {
                        return converter.fromBytes(Base64.decode((String) o, 3));
                    } catch (IOException | IllegalArgumentException e) {
                    }
                }
                String mendelFlagName = PhenotypeFlag.super.getMendelFlagName();
                String valueOf = String.valueOf(o);
                StringBuilder sb = new StringBuilder(String.valueOf(mendelFlagName).length() + 27 + String.valueOf(valueOf).length());
                sb.append("Invalid byte[] value for ");
                sb.append(mendelFlagName);
                sb.append(": ");
                sb.append(valueOf);
                Log.e(PhenotypeFlag.TAG, sb.toString());
                return null;
            }
        };
    }
}
