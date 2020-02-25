package com.google.android.libraries.stitch.binder;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.p003os.TraceCompat;
import android.util.Log;

import com.google.android.libraries.stitch.flags.DebugFlag;
import com.google.android.libraries.stitch.flags.Flags;
import com.google.android.libraries.stitch.flags.TestFlag;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Binder {
    static final DebugFlag EXTRA_VERIFICATION = new DebugFlag("debug.binder.verification");
    private static final TestFlag DETAIL_TRACE_ENABLED = new TestFlag("test.binder.detail_trace");
    private static final boolean IS_STRICT_MODE_ALLOWED = Flags.get(new DebugFlag("debug.binder.strict_mode"));
    private static final int MAX_SECTION_NAME_LEN = 127;
    private static final String TAG = "Binder";
    private static final TestFlag TRACE_ENABLED = new TestFlag("test.binder.trace");
    private static final Object UNBOUND = new Object();
    private static final BinderProvider rootBinderProvider = new BinderProvider(false, new RootBinderInitializer());
    private static boolean isModuleConfigureStrictModePolicySet;
    private static StrictMode.ThreadPolicy moduleConfigureStrictModePolicy;
    private final Map<Object, Object> bindings;
    private final ThreadLocal<Boolean> isInternallyBinding;
    private final Map<Class<?>, Map<Object, Object>> keyBindings;
    private final Set<Class<?>> moduleMultiBindingsAdded;
    private final CopyOnWriteArrayList<Module> modules;
    private final Map<Object, List<?>> multiBindings;
    private volatile BinderLocks binderLocks;
    private Context context;
    private Binder parent;
    private volatile boolean sealed;
    private String tag;

    public Binder(Context context2) {
        this(context2, null);
    }

    public Binder(Context context2, Binder parent2) {
        this.bindings = Collections.synchronizedMap(new HashMap());
        this.multiBindings = Collections.synchronizedMap(new HashMap());
        this.keyBindings = Collections.synchronizedMap(new HashMap());
        this.moduleMultiBindingsAdded = Collections.synchronizedSet(new HashSet());
        this.modules = new CopyOnWriteArrayList<>();
        this.isInternallyBinding = new ThreadLocal<>();
        this.binderLocks = new SingleBinderLock();
        this.context = context2;
        this.parent = parent2;
        this.tag = context2.getClass().getName();
    }

    public Binder() {
        this.bindings = Collections.synchronizedMap(new HashMap());
        this.multiBindings = Collections.synchronizedMap(new HashMap());
        this.keyBindings = Collections.synchronizedMap(new HashMap());
        this.moduleMultiBindingsAdded = Collections.synchronizedSet(new HashSet());
        this.modules = new CopyOnWriteArrayList<>();
        this.isInternallyBinding = new ThreadLocal<>();
        this.binderLocks = new SingleBinderLock();
    }

    public static void setStrictModePolicy(StrictMode.ThreadPolicy policy) {
        moduleConfigureStrictModePolicy = policy;
        isModuleConfigureStrictModePolicySet = policy != null;
    }

    @NonNull
    public static <T> T get(Context context2, Class<T> type) {
        return findBinder(context2).get(type);
    }

    @NonNull
    public static <T> T get(Context context2, Class<T> type, Object key) {
        return findBinder(context2).get(type, key);
    }

    @Nullable
    public static <T> T getOptional(Context context2, Class<T> type, Object key) {
        return findBinder(context2).getOptional(type, key);
    }

    @Nullable
    public static <T> T getOptional(Context context2, Class<T> type) {
        return findBinder(context2).getOptional(type);
    }

    public static <T> List<T> getAll(Context context2, Class<T> type) {
        return findBinder(context2).getAll(type);
    }

    public static String getString(Context context2, String key, String defaultValue) {
        return (String) findBinder(context2).getConstant(key, defaultValue);
    }

    public static int getInt(Context context2, String key, int defaultValue) {
        return ((Integer) findBinder(context2).getConstant(key, Integer.valueOf(defaultValue))).intValue();
    }

    public static long getLong(Context context2, String key, long defaultValue) {
        return ((Long) findBinder(context2).getConstant(key, Long.valueOf(defaultValue))).longValue();
    }

    public static boolean getBoolean(Context context2, String key, boolean defaultValue) {
        return ((Boolean) findBinder(context2).getConstant(key, Boolean.valueOf(defaultValue))).booleanValue();
    }

    public static <T> List<T> getChain(Context context2, Class<T> type) {
        return findBinder(context2).getChain(type);
    }

    public static Binder findBinder(Context context2, Fragment fragment) {
        while (fragment != null) {
            Binder binder = tryGetBinder(fragment);
            if (binder != null) {
                return binder;
            }
            fragment = fragment.getParentFragment();
        }
        return findBinder(context2);
    }

    public static Binder findBinder(Context context2) {
        Context applicationContext = context2.getApplicationContext();
        boolean applicationContextVisited = false;
        do {
            Binder binder = tryGetBinder(context2);
            if (binder != null) {
                return binder;
            }
            applicationContextVisited |= context2 == applicationContext;
            if (context2 instanceof ContextWrapper) {
                context2 = ((ContextWrapper) context2).getBaseContext();
                if (context2 == null) {
                    throw new IllegalStateException("Invalid ContextWrapper -- If this is a Robolectric test, have you called ActivityController.create()?");
                }
            } else if (!applicationContextVisited) {
                context2 = applicationContext;
                continue;
            } else {
                context2 = null;
                continue;
            }
        } while (context2 != null);
        return getRootBinder(applicationContext);
    }

    public static Binder getRootBinder(Context context2) {
        return rootBinderProvider.get(context2.getApplicationContext());
    }

    private static Binder tryGetBinder(Object object) {
        if (!(object instanceof BinderContext)) {
            return null;
        }
        Binder binder = ((BinderContext) object).getBinder();
        if (binder != null) {
            return binder;
        }
        String valueOf = String.valueOf(object);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
        sb.append("BinderContext must not return null Binder: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    private static void beginTrace(String method, Class<?> type) {
        if (isTracingEnabled()) {
            beginTrace(method, type.getSimpleName(), new Object[0]);
        }
    }

    private static void beginTrace(String method, Class<?> type, Object key) {
        if (isTracingEnabled()) {
            beginTrace(method, "%s %s", type.getSimpleName(), key);
        }
    }

    private static void beginTrace(String method, Module module, Class<?> type, Object key) {
        if (isTracingEnabled()) {
            beginTrace(method, "%s %s %s", module.getClass().getSimpleName(), type.getSimpleName(), key);
        }
    }

    private static void beginTrace(String method, String label, Object... formatArgs) {
        String traceSectionName = null;
        if (Flags.get(DETAIL_TRACE_ENABLED)) {
            String format = String.format(label, formatArgs);
            StringBuilder sb = new StringBuilder(String.valueOf(method).length() + 10 + String.valueOf(format).length());
            sb.append("Binder.");
            sb.append(method);
            sb.append(" - ");
            sb.append(format);
            traceSectionName = sb.toString();
        } else if (Flags.get(TRACE_ENABLED)) {
            String valueOf = String.valueOf(method);
            traceSectionName = valueOf.length() != 0 ? "Binder.".concat(valueOf) : new String("Binder.");
        }
        if (traceSectionName != null) {
            TraceCompat.beginSection(traceSectionName.substring(0, Math.min(traceSectionName.length(), 127)));
        }
    }

    private static void endTrace() {
        if (isTracingEnabled()) {
            TraceCompat.endSection();
        }
    }

    private static boolean isTracingEnabled() {
        return Flags.get(DETAIL_TRACE_ENABLED) || Flags.get(TRACE_ENABLED);
    }

    public void attachContext(Context context2) {
        this.context = context2;
        if (this.tag == null) {
            this.tag = context2.getClass().getName();
        }
    }

    public void attachParent(Binder parent2) {
        this.parent = parent2;
    }

    public void useFineGrainedLocks() {
        synchronized (this) {
            if (this.binderLocks instanceof SingleBinderLock) {
                this.binderLocks = new FineGrainedBinderLocks();
            }
        }
    }

    public <T> Binder bind(Class cls, Object obj) {
        bindKeyValue(cls, obj);
        return this;
    }

    public Binder bind(String key, String value) {
        bindKeyValue(key, value);
        return this;
    }

    public Binder bind(String key, int value) {
        bindKeyValue(key, Integer.valueOf(value));
        return this;
    }

    public Binder bind(String key, long value) {
        bindKeyValue(key, Long.valueOf(value));
        return this;
    }

    public Binder bind(String key, boolean value) {
        bindKeyValue(key, Boolean.valueOf(value));
        return this;
    }

    public Binder bind(Module module) {
        ensureUnsealed();
        this.modules.add(module);
        return this;
    }

    public <T> Binder multiBind(Class<T> type, T instance) {
        multiBindTypeValue(type, instance);
        return this;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object):com.google.android.libraries.stitch.binder.Binder
     arg types: [java.lang.Class<T>, T]
     candidates:
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.util.Collection):com.google.android.libraries.stitch.binder.Binder
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object[]):com.google.android.libraries.stitch.binder.Binder
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object):com.google.android.libraries.stitch.binder.Binder */
    public <T> Binder multiBind(Class<T> type, T... instances) {
        for (T t : instances) {
            multiBind((Class) type, (Object) t);
        }
        return this;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object):com.google.android.libraries.stitch.binder.Binder
     arg types: [java.lang.Class<T>, T]
     candidates:
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.util.Collection):com.google.android.libraries.stitch.binder.Binder
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object[]):com.google.android.libraries.stitch.binder.Binder
      com.google.android.libraries.stitch.binder.Binder.multiBind(java.lang.Class, java.lang.Object):com.google.android.libraries.stitch.binder.Binder */
    public <T> Binder multiBind(Class<T> type, Collection<T> multiple) {
        for (T t : multiple) {
            multiBind((Class) type, (Object) t);
        }
        return this;
    }

    public <T> Binder multiBindWithKey(Class<T> type, Object key, T instance) {
        multiBindTypeAndKeyToValue(type, key, instance);
        return this;
    }

    @NonNull
    public <T> T get(Class<T> type) {
        beginTrace("Get", type);
        try {
            return getInternal(type, null);
        } finally {
            endTrace();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.Class<?>, java.lang.Object):void
     arg types: [java.lang.String, java.lang.Class<T>, java.lang.Object]
     candidates:
      com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.String, java.lang.Object[]):void
      com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.Class<?>, java.lang.Object):void */
    @NonNull
    public <T> T get(Class<T> type, Object key) {
        beginTrace("Get", (Class<?>) type, key);
        try {
            return getInternal(type, key);
        } finally {
            endTrace();
        }
    }

    private <T> T getInternal(Class<T> type, Object key) {
        Preconditions.checkNotNull(type);
        T instance = getOptionalInternal(type, key);
        if (instance != null) {
            return instance;
        }
        String errorMessage = getUnboundErrorMessage(type, key);
        IllegalStateException e = new IllegalStateException(errorMessage);
        Log.e(TAG, errorMessage, e);
        throw e;
    }

    private String getUnboundErrorMessage(Class<?> type, Object key) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unbound type: ");
        sb.append(type.getName());
        sb.append("\n");
        if (key != null) {
            sb.append("key: ");
            sb.append(key);
            sb.append("\n");
        }
        sb.append("Searched binders:\n");
        Binder binder = this;
        while (true) {
            sb.append(binder.tag);
            binder = binder.parent;
            if (binder == null) {
                return sb.toString();
            }
            sb.append(" ->\n");
        }
    }

    @Nullable
    public <T> T getOptional(Class<T> type) {
        beginTrace("GetOptional", type);
        try {
            return getOptionalInternal(type, null);
        } finally {
            endTrace();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.Class<?>, java.lang.Object):void
     arg types: [java.lang.String, java.lang.Class<T>, java.lang.Object]
     candidates:
      com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.String, java.lang.Object[]):void
      com.google.android.libraries.stitch.binder.Binder.beginTrace(java.lang.String, java.lang.Class<?>, java.lang.Object):void */
    @Nullable
    public <T> T getOptional(Class<T> type, Object key) {
        beginTrace("GetOptional", (Class<?>) type, key);
        try {
            return getOptionalInternal(type, key);
        } finally {
            endTrace();
        }
    }

    private <T> T getOptionalInternal(Class<T> type, Object key) {
        Preconditions.checkNotNull(type);
        Binder binder = this;
        do {
            T instance = key == null ? binder.getInstance(type) : binder.getInstance(type, key);
            if (instance != null) {
                return instance;
            }
            binder = binder.parent;
        } while (binder != null);
        return null;
    }

    public <T> List<T> getAll(Class<T> type) {
        beginTrace("GetAll", type);
        try {
            return getAllInternal(type);
        } finally {
            endTrace();
        }
    }

    private <T> List<T> getAllInternal(Class<T> type) {
        Preconditions.checkNotNull(type);
        List<T> all = new ArrayList<>();
        Binder binder = this;
        do {
            all.addAll(binder.getAllInstances(type));
            binder = binder.parent;
        } while (binder != null);
        return all;
    }

    public String getString(String key, String defaultValue) {
        return (String) getConstant(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return ((Integer) getConstant(key, Integer.valueOf(defaultValue))).intValue();
    }

    public long getLong(String key, long defaultValue) {
        return ((Long) getConstant(key, Long.valueOf(defaultValue))).longValue();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return ((Boolean) getConstant(key, Boolean.valueOf(defaultValue))).booleanValue();
    }

    private Object getConstant(String key, Object defaultValue) {
        beginTrace("GetConstant", key, new Object[0]);
        try {
            return getConstantInternal(key, defaultValue);
        } finally {
            endTrace();
        }
    }

    private Object getLock(Object key) {
        return this.binderLocks.getLock(key);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        r1 = r0.parent;
        r0 = r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object getConstantInternal(java.lang.String r6, java.lang.Object r7) {
        /*
            r5 = this;
            android.content.Context r0 = r5.context
            if (r0 == 0) goto L_0x002b
            r0 = r5
        L_0x0005:
            java.lang.Object r1 = r0.getLock(r6)
            monitor-enter(r1)
            java.util.Map<java.lang.Object, java.lang.Object> r2 = r0.bindings     // Catch:{ all -> 0x0028 }
            java.lang.Object r2 = r2.get(r6)     // Catch:{ all -> 0x0028 }
            if (r2 == 0) goto L_0x0018
            java.lang.Object r3 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x0028 }
            if (r2 == r3) goto L_0x0018
            monitor-exit(r1)     // Catch:{ all -> 0x0028 }
            return r2
        L_0x0018:
            if (r2 != 0) goto L_0x0021
            java.util.Map<java.lang.Object, java.lang.Object> r3 = r0.bindings     // Catch:{ all -> 0x0028 }
            java.lang.Object r4 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x0028 }
            r3.put(r6, r4)     // Catch:{ all -> 0x0028 }
        L_0x0021:
            monitor-exit(r1)     // Catch:{ all -> 0x0028 }
            com.google.android.libraries.stitch.binder.Binder r1 = r0.parent
            r0 = r1
            if (r1 != 0) goto L_0x0005
            return r7
        L_0x0028:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0028 }
            throw r2
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Binder not initialized yet."
            r0.<init>(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.stitch.binder.Binder.getConstantInternal(java.lang.String, java.lang.Object):java.lang.Object");
    }

    public <T> List<T> getChain(Class<T> type) {
        beginTrace("GetChain", type);
        try {
            return getChainInternal(type);
        } finally {
            endTrace();
        }
    }

    private <T> List<T> getChainInternal(Class<T> type) {
        Preconditions.checkNotNull(type);
        List<T> chain = new ArrayList<>();
        Binder binder = this;
        do {
            T instance = binder.getInstance(type);
            if (instance != null) {
                chain.add(instance);
            }
            binder = binder.parent;
        } while (binder != null);
        return chain;
    }

    public void seal() {
        this.sealed = true;
    }

    private void ensureUnsealed() {
        if (this.sealed && !isInternallyBinding()) {
            throw new LateBindingException("This binder is sealed for modification");
        }
    }

    private void bindKeyValue(Object key, Object value) {
        ensureUnsealed();
        synchronized (getLock(key)) {
            if (Flags.get(EXTRA_VERIFICATION)) {
                if (this.multiBindings.containsKey(key)) {
                    String valueOf = String.valueOf(key);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42);
                    sb.append("Attempt to single-bind multibound object: ");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                } else if (this.keyBindings.containsKey(key)) {
                    String valueOf2 = String.valueOf(key);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 71);
                    sb2.append("Attempt to single-bind an object that is already multibound with keys: ");
                    sb2.append(valueOf2);
                    throw new IllegalStateException(sb2.toString());
                }
            }
            Object boundInstance = this.bindings.get(key);
            if (boundInstance == null) {
                this.bindings.put(key, value);
            } else if (boundInstance == UNBOUND) {
                String valueOf3 = String.valueOf(key);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 51);
                sb3.append("Bind call too late - someone already tried to get: ");
                sb3.append(valueOf3);
                throw new LateBindingException(sb3.toString());
            } else {
                String valueOf4 = String.valueOf(key);
                String valueOf5 = String.valueOf(boundInstance);
                StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf4).length() + 21 + String.valueOf(valueOf5).length());
                sb4.append("Duplicate binding: ");
                sb4.append(valueOf4);
                sb4.append(", ");
                sb4.append(valueOf5);
                throw new DuplicateBindingException(sb4.toString());
            }
        }
    }

    private void multiBindTypeValue(Object type, Object value) {
        ensureUnsealed();
        synchronized (getLock(type)) {
            if (Flags.get(EXTRA_VERIFICATION)) {
                if (this.bindings.containsKey(type)) {
                    String valueOf = String.valueOf(type);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42);
                    sb.append("Attempt to multibind single-bound object: ");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                } else if (this.keyBindings.containsKey(type)) {
                    String valueOf2 = String.valueOf(type);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 85);
                    sb2.append("Attempt to multibind an object without a key, even though other instances have keys: ");
                    sb2.append(valueOf2);
                    throw new IllegalStateException(sb2.toString());
                }
            }
            List<Object> bindings2 = this.multiBindings.get(type);
            if (bindings2 == null) {
                bindings2 = new ArrayList<>();
                this.multiBindings.put(type, bindings2);
            }
            bindings2.add(value);
        }
    }

    private void multiBindTypeAndKeyToValue(Class<?> type, Object key, Object value) {
        Preconditions.checkNotNull(key);
        ensureUnsealed();
        synchronized (getLock(type)) {
            if (Flags.get(EXTRA_VERIFICATION)) {
                if (this.bindings.containsKey(type)) {
                    String valueOf = String.valueOf(type);
                    String valueOf2 = String.valueOf(key);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 79 + String.valueOf(valueOf2).length());
                    sb.append("Attempt to multibind with a key even though the type is already single-bound: ");
                    sb.append(valueOf);
                    sb.append(" ");
                    sb.append(valueOf2);
                    throw new IllegalStateException(sb.toString());
                } else if (this.multiBindings.containsKey(type)) {
                    if (!this.multiBindings.get(type).isEmpty()) {
                        String valueOf3 = String.valueOf(type);
                        String valueOf4 = String.valueOf(key);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 87 + String.valueOf(valueOf4).length());
                        sb2.append("Attempt to multi-bind with a key even though multibound instances without keys exist: ");
                        sb2.append(valueOf3);
                        sb2.append(" ");
                        sb2.append(valueOf4);
                        throw new IllegalStateException(sb2.toString());
                    }
                }
            }
            Map<Object, Object> keyToInstanceForType = this.keyBindings.get(type);
            if (keyToInstanceForType == null) {
                keyToInstanceForType = new HashMap<>();
                this.keyBindings.put(type, keyToInstanceForType);
            }
            Object boundInstance = keyToInstanceForType.get(key);
            if (boundInstance == null) {
                keyToInstanceForType.put(key, value);
            } else if (boundInstance == UNBOUND) {
                String valueOf5 = String.valueOf(type);
                String valueOf6 = String.valueOf(key);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf5).length() + 52 + String.valueOf(valueOf6).length());
                sb3.append("Bind call too late - someone already tried to get: ");
                sb3.append(valueOf5);
                sb3.append(" ");
                sb3.append(valueOf6);
                throw new LateBindingException(sb3.toString());
            } else {
                String valueOf7 = String.valueOf(type);
                String valueOf8 = String.valueOf(key);
                String valueOf9 = String.valueOf(boundInstance);
                StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf7).length() + 22 + String.valueOf(valueOf8).length() + String.valueOf(valueOf9).length());
                sb4.append("Duplicate binding: ");
                sb4.append(valueOf7);
                sb4.append(" ");
                sb4.append(valueOf8);
                sb4.append(", ");
                sb4.append(valueOf9);
                throw new DuplicateBindingException(sb4.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0096, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00fc, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T getInstance(java.lang.Class<T> r13) {
        /*
            r12 = this;
            com.google.android.libraries.stitch.util.Preconditions.checkNotNull(r13)
            android.content.Context r0 = r12.context
            if (r0 == 0) goto L_0x0119
            java.lang.Object r0 = r12.getLock(r13)
            monitor-enter(r0)
            java.util.Map<java.lang.Object, java.lang.Object> r1 = r12.bindings     // Catch:{ all -> 0x0116 }
            java.lang.Object r1 = r1.get(r13)     // Catch:{ all -> 0x0116 }
            if (r1 == 0) goto L_0x001d
            java.lang.Object r2 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x0116 }
            if (r1 == r2) goto L_0x001a
            r2 = r1
            goto L_0x001b
        L_0x001a:
            r2 = 0
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x0116 }
            return r2
        L_0x001d:
            boolean r2 = r12.isInternallyBinding()     // Catch:{ all -> 0x0116 }
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0040
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x0116 }
            if (r5 == 0) goto L_0x0037
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x0116 }
            if (r5 == 0) goto L_0x0037
            android.os.StrictMode$ThreadPolicy r5 = android.os.StrictMode.getThreadPolicy()     // Catch:{ all -> 0x0116 }
            r3 = r5
            android.os.StrictMode$ThreadPolicy r5 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x0116 }
            android.os.StrictMode.setThreadPolicy(r5)     // Catch:{ all -> 0x0116 }
        L_0x0037:
            java.lang.ThreadLocal<java.lang.Boolean> r5 = r12.isInternallyBinding     // Catch:{ all -> 0x0116 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x0116 }
            r5.set(r6)     // Catch:{ all -> 0x0116 }
        L_0x0040:
            r5 = 0
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r6 = r12.modules     // Catch:{ all -> 0x00fd }
            int r6 = r6.size()     // Catch:{ all -> 0x00fd }
            r7 = 0
        L_0x0048:
            if (r7 >= r6) goto L_0x009f
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r8 = r12.modules     // Catch:{ all -> 0x00fd }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x00fd }
            com.google.android.libraries.stitch.binder.Module r8 = (com.google.android.libraries.stitch.binder.Module) r8     // Catch:{ all -> 0x00fd }
            java.lang.String r9 = "configure"
            java.lang.String r10 = "%s %s"
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00fd }
            r11[r5] = r8     // Catch:{ all -> 0x00fd }
            r11[r4] = r13     // Catch:{ all -> 0x00fd }
            beginTrace(r9, r10, r11)     // Catch:{ all -> 0x00fd }
            android.content.Context r9 = r12.context     // Catch:{ all -> 0x009a }
            r8.configure(r9, r13, r12)     // Catch:{ all -> 0x009a }
            endTrace()     // Catch:{ all -> 0x00fd }
            com.google.android.libraries.stitch.flags.DebugFlag r9 = com.google.android.libraries.stitch.binder.Binder.EXTRA_VERIFICATION     // Catch:{ all -> 0x00fd }
            boolean r9 = com.google.android.libraries.stitch.flags.Flags.get(r9)     // Catch:{ all -> 0x00fd }
            if (r9 != 0) goto L_0x0097
            java.util.Map<java.lang.Object, java.lang.Object> r9 = r12.bindings     // Catch:{ all -> 0x00fd }
            java.lang.Object r9 = r9.get(r13)     // Catch:{ all -> 0x00fd }
            r1 = r9
            if (r1 == 0) goto L_0x0097
            java.lang.Object r9 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x00fd }
            if (r1 == r9) goto L_0x0097
            if (r2 != 0) goto L_0x0095
            java.lang.ThreadLocal<java.lang.Boolean> r4 = r12.isInternallyBinding     // Catch:{ all -> 0x0116 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0116 }
            r4.set(r5)     // Catch:{ all -> 0x0116 }
            boolean r4 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x0095
            boolean r4 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x0095
            android.os.StrictMode.setThreadPolicy(r3)     // Catch:{ all -> 0x0116 }
        L_0x0095:
            monitor-exit(r0)     // Catch:{ all -> 0x0116 }
            return r1
        L_0x0097:
            int r7 = r7 + 1
            goto L_0x0048
        L_0x009a:
            r4 = move-exception
            endTrace()     // Catch:{ all -> 0x00fd }
            throw r4     // Catch:{ all -> 0x00fd }
        L_0x009f:
            if (r2 != 0) goto L_0x00b5
            java.lang.ThreadLocal<java.lang.Boolean> r4 = r12.isInternallyBinding     // Catch:{ all -> 0x0116 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0116 }
            r4.set(r5)     // Catch:{ all -> 0x0116 }
            boolean r4 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x00b5
            boolean r4 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x00b5
            android.os.StrictMode.setThreadPolicy(r3)     // Catch:{ all -> 0x0116 }
        L_0x00b5:
            java.util.Map<java.lang.Object, java.lang.Object> r4 = r12.bindings     // Catch:{ all -> 0x0116 }
            java.lang.Object r4 = r4.get(r13)     // Catch:{ all -> 0x0116 }
            r1 = r4
            if (r1 != 0) goto L_0x00fb
            com.google.android.libraries.stitch.flags.DebugFlag r4 = com.google.android.libraries.stitch.binder.Binder.EXTRA_VERIFICATION     // Catch:{ all -> 0x0116 }
            boolean r4 = com.google.android.libraries.stitch.flags.Flags.get(r4)     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x00f4
            java.util.Map<java.lang.Object, java.util.List<?>> r4 = r12.multiBindings     // Catch:{ all -> 0x0116 }
            boolean r4 = r4.containsKey(r13)     // Catch:{ all -> 0x0116 }
            if (r4 != 0) goto L_0x00cf
            goto L_0x00f4
        L_0x00cf:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0116 }
            java.lang.String r5 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x0116 }
            java.lang.String r6 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0116 }
            int r6 = r6.length()     // Catch:{ all -> 0x0116 }
            int r6 = r6 + 36
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0116 }
            r7.<init>(r6)     // Catch:{ all -> 0x0116 }
            java.lang.String r6 = "get() called for multibound object: "
            r7.append(r6)     // Catch:{ all -> 0x0116 }
            r7.append(r5)     // Catch:{ all -> 0x0116 }
            java.lang.String r5 = r7.toString()     // Catch:{ all -> 0x0116 }
            r4.<init>(r5)     // Catch:{ all -> 0x0116 }
            throw r4     // Catch:{ all -> 0x0116 }
        L_0x00f4:
            java.util.Map<java.lang.Object, java.lang.Object> r4 = r12.bindings     // Catch:{ all -> 0x0116 }
            java.lang.Object r5 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x0116 }
            r4.put(r13, r5)     // Catch:{ all -> 0x0116 }
        L_0x00fb:
            monitor-exit(r0)     // Catch:{ all -> 0x0116 }
            return r1
        L_0x00fd:
            r4 = move-exception
            if (r2 != 0) goto L_0x0114
            java.lang.ThreadLocal<java.lang.Boolean> r6 = r12.isInternallyBinding     // Catch:{ all -> 0x0116 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0116 }
            r6.set(r5)     // Catch:{ all -> 0x0116 }
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x0116 }
            if (r5 == 0) goto L_0x0114
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x0116 }
            if (r5 == 0) goto L_0x0114
            android.os.StrictMode.setThreadPolicy(r3)     // Catch:{ all -> 0x0116 }
        L_0x0114:
            throw r4     // Catch:{ all -> 0x0116 }
        L_0x0116:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0116 }
            throw r1
        L_0x0119:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Binder not initialized yet."
            r0.<init>(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.stitch.binder.Binder.getInstance(java.lang.Class):java.lang.Object");
    }

    /* JADX INFO: Multiple debug info for r3v0 boolean: [D('isAlreadyInternallyBinding' boolean), D('instance' T)] */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x009f, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00e5, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T getInstance(java.lang.Class<T> r12, java.lang.Object r13) {
        /*
            r11 = this;
            com.google.android.libraries.stitch.util.Preconditions.checkNotNull(r12)
            com.google.android.libraries.stitch.util.Preconditions.checkNotNull(r13)
            android.content.Context r0 = r11.context
            if (r0 == 0) goto L_0x0102
            java.lang.Object r0 = r11.getLock(r12)
            monitor-enter(r0)
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.Object, java.lang.Object>> r1 = r11.keyBindings     // Catch:{ all -> 0x00ff }
            java.lang.Object r1 = r1.get(r12)     // Catch:{ all -> 0x00ff }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x00ff }
            r2 = 0
            if (r1 == 0) goto L_0x0027
            java.lang.Object r3 = r1.get(r13)     // Catch:{ all -> 0x00ff }
            if (r3 == 0) goto L_0x0027
            java.lang.Object r4 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x00ff }
            if (r3 == r4) goto L_0x0025
            r2 = r3
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x00ff }
            return r2
        L_0x0027:
            boolean r3 = r11.isInternallyBinding()     // Catch:{ all -> 0x00ff }
            r4 = 0
            if (r3 != 0) goto L_0x004a
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x0040
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x0040
            android.os.StrictMode$ThreadPolicy r5 = android.os.StrictMode.getThreadPolicy()     // Catch:{ all -> 0x00ff }
            r4 = r5
            android.os.StrictMode$ThreadPolicy r5 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x00ff }
            android.os.StrictMode.setThreadPolicy(r5)     // Catch:{ all -> 0x00ff }
        L_0x0040:
            java.lang.ThreadLocal<java.lang.Boolean> r5 = r11.isInternallyBinding     // Catch:{ all -> 0x00ff }
            r6 = 1
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ all -> 0x00ff }
            r5.set(r6)     // Catch:{ all -> 0x00ff }
        L_0x004a:
            r5 = 0
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r6 = r11.modules     // Catch:{ all -> 0x00e6 }
            int r6 = r6.size()     // Catch:{ all -> 0x00e6 }
            r7 = 0
        L_0x0052:
            if (r7 >= r6) goto L_0x00a8
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r8 = r11.modules     // Catch:{ all -> 0x00e6 }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x00e6 }
            com.google.android.libraries.stitch.binder.Module r8 = (com.google.android.libraries.stitch.binder.Module) r8     // Catch:{ all -> 0x00e6 }
            java.lang.String r9 = "configure"
            beginTrace(r9, r8, r12, r13)     // Catch:{ all -> 0x00e6 }
            android.content.Context r9 = r11.context     // Catch:{ all -> 0x00a3 }
            r8.configure(r9, r12, r13, r11)     // Catch:{ all -> 0x00a3 }
            endTrace()     // Catch:{ all -> 0x00e6 }
            com.google.android.libraries.stitch.flags.DebugFlag r9 = com.google.android.libraries.stitch.binder.Binder.EXTRA_VERIFICATION     // Catch:{ all -> 0x00e6 }
            boolean r9 = com.google.android.libraries.stitch.flags.Flags.get(r9)     // Catch:{ all -> 0x00e6 }
            if (r9 != 0) goto L_0x00a0
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.Object, java.lang.Object>> r9 = r11.keyBindings     // Catch:{ all -> 0x00e6 }
            java.lang.Object r9 = r9.get(r12)     // Catch:{ all -> 0x00e6 }
            java.util.Map r9 = (java.util.Map) r9     // Catch:{ all -> 0x00e6 }
            r1 = r9
            if (r1 == 0) goto L_0x00a0
            java.lang.Object r9 = r1.get(r13)     // Catch:{ all -> 0x00e6 }
            if (r9 == 0) goto L_0x00a0
            java.lang.Object r10 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x00e6 }
            if (r9 == r10) goto L_0x00a0
            if (r3 != 0) goto L_0x009e
            java.lang.ThreadLocal<java.lang.Boolean> r2 = r11.isInternallyBinding     // Catch:{ all -> 0x00ff }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x00ff }
            r2.set(r5)     // Catch:{ all -> 0x00ff }
            boolean r2 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x00ff }
            if (r2 == 0) goto L_0x009e
            boolean r2 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x00ff }
            if (r2 == 0) goto L_0x009e
            android.os.StrictMode.setThreadPolicy(r4)     // Catch:{ all -> 0x00ff }
        L_0x009e:
            monitor-exit(r0)     // Catch:{ all -> 0x00ff }
            return r9
        L_0x00a0:
            int r7 = r7 + 1
            goto L_0x0052
        L_0x00a3:
            r2 = move-exception
            endTrace()     // Catch:{ all -> 0x00e6 }
            throw r2     // Catch:{ all -> 0x00e6 }
        L_0x00a8:
            if (r3 != 0) goto L_0x00be
            java.lang.ThreadLocal<java.lang.Boolean> r6 = r11.isInternallyBinding     // Catch:{ all -> 0x00ff }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x00ff }
            r6.set(r5)     // Catch:{ all -> 0x00ff }
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x00be
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x00be
            android.os.StrictMode.setThreadPolicy(r4)     // Catch:{ all -> 0x00ff }
        L_0x00be:
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.Object, java.lang.Object>> r5 = r11.keyBindings     // Catch:{ all -> 0x00ff }
            java.lang.Object r5 = r5.get(r12)     // Catch:{ all -> 0x00ff }
            java.util.Map r5 = (java.util.Map) r5     // Catch:{ all -> 0x00ff }
            r1 = r5
            if (r1 != 0) goto L_0x00d4
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ all -> 0x00ff }
            r5.<init>()     // Catch:{ all -> 0x00ff }
            r1 = r5
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.Object, java.lang.Object>> r5 = r11.keyBindings     // Catch:{ all -> 0x00ff }
            r5.put(r12, r1)     // Catch:{ all -> 0x00ff }
        L_0x00d4:
            java.lang.Object r5 = r1.get(r13)     // Catch:{ all -> 0x00ff }
            if (r5 != 0) goto L_0x00df
            java.lang.Object r6 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x00ff }
            r1.put(r13, r6)     // Catch:{ all -> 0x00ff }
        L_0x00df:
            java.lang.Object r6 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x00ff }
            if (r5 == r6) goto L_0x00e4
            r2 = r5
        L_0x00e4:
            monitor-exit(r0)     // Catch:{ all -> 0x00ff }
            return r2
        L_0x00e6:
            r2 = move-exception
            if (r3 != 0) goto L_0x00fd
            java.lang.ThreadLocal<java.lang.Boolean> r6 = r11.isInternallyBinding     // Catch:{ all -> 0x00ff }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x00ff }
            r6.set(r5)     // Catch:{ all -> 0x00ff }
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.IS_STRICT_MODE_ALLOWED     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x00fd
            boolean r5 = com.google.android.libraries.stitch.binder.Binder.isModuleConfigureStrictModePolicySet     // Catch:{ all -> 0x00ff }
            if (r5 == 0) goto L_0x00fd
            android.os.StrictMode.setThreadPolicy(r4)     // Catch:{ all -> 0x00ff }
        L_0x00fd:
            throw r2     // Catch:{ all -> 0x00ff }
        L_0x00ff:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ff }
            throw r1
        L_0x0102:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Binder not initialized yet."
            r0.<init>(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.stitch.binder.Binder.getInstance(java.lang.Class, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:60:0x010a, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> java.util.List<T> getAllInstances(java.lang.Class<T> r13) {
        /*
            r12 = this;
            com.google.android.libraries.stitch.util.Preconditions.checkNotNull(r13)
            android.content.Context r0 = r12.context
            if (r0 == 0) goto L_0x010e
            java.lang.Object r0 = r12.getLock(r13)
            monitor-enter(r0)
            java.util.Map<java.lang.Object, java.util.List<?>> r1 = r12.multiBindings     // Catch:{ all -> 0x010b }
            java.lang.Object r1 = r1.get(r13)     // Catch:{ all -> 0x010b }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x010b }
            if (r1 != 0) goto L_0x0057
            com.google.android.libraries.stitch.flags.DebugFlag r2 = com.google.android.libraries.stitch.binder.Binder.EXTRA_VERIFICATION     // Catch:{ all -> 0x010b }
            boolean r2 = com.google.android.libraries.stitch.flags.Flags.get(r2)     // Catch:{ all -> 0x010b }
            if (r2 == 0) goto L_0x004c
            java.util.Map<java.lang.Object, java.lang.Object> r2 = r12.bindings     // Catch:{ all -> 0x010b }
            boolean r2 = r2.containsKey(r13)     // Catch:{ all -> 0x010b }
            if (r2 != 0) goto L_0x0027
            goto L_0x004c
        L_0x0027:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010b }
            java.lang.String r3 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x010b }
            java.lang.String r4 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x010b }
            int r4 = r4.length()     // Catch:{ all -> 0x010b }
            int r4 = r4 + 41
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r5.<init>(r4)     // Catch:{ all -> 0x010b }
            java.lang.String r4 = "getAll() called for single-bound object: "
            r5.append(r4)     // Catch:{ all -> 0x010b }
            r5.append(r3)     // Catch:{ all -> 0x010b }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x010b }
            r2.<init>(r3)     // Catch:{ all -> 0x010b }
            throw r2     // Catch:{ all -> 0x010b }
        L_0x004c:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x010b }
            r2.<init>()     // Catch:{ all -> 0x010b }
            r1 = r2
            java.util.Map<java.lang.Object, java.util.List<?>> r2 = r12.multiBindings     // Catch:{ all -> 0x010b }
            r2.put(r13, r1)     // Catch:{ all -> 0x010b }
        L_0x0057:
            java.util.Set<java.lang.Class<?>> r2 = r12.moduleMultiBindingsAdded     // Catch:{ all -> 0x010b }
            boolean r2 = r2.add(r13)     // Catch:{ all -> 0x010b }
            if (r2 == 0) goto L_0x00d7
            boolean r2 = r12.isInternallyBinding()     // Catch:{ all -> 0x010b }
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x007e
            android.os.StrictMode$ThreadPolicy r5 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x010b }
            if (r5 == 0) goto L_0x0075
            android.os.StrictMode$ThreadPolicy r5 = android.os.StrictMode.getThreadPolicy()     // Catch:{ all -> 0x010b }
            r3 = r5
            android.os.StrictMode$ThreadPolicy r5 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x010b }
            android.os.StrictMode.setThreadPolicy(r5)     // Catch:{ all -> 0x010b }
        L_0x0075:
            java.lang.ThreadLocal<java.lang.Boolean> r5 = r12.isInternallyBinding     // Catch:{ all -> 0x010b }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x010b }
            r5.set(r6)     // Catch:{ all -> 0x010b }
        L_0x007e:
            r5 = 0
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r6 = r12.modules     // Catch:{ all -> 0x00c2 }
            int r6 = r6.size()     // Catch:{ all -> 0x00c2 }
            r7 = 0
        L_0x0086:
            if (r7 >= r6) goto L_0x00af
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.libraries.stitch.binder.Module> r8 = r12.modules     // Catch:{ all -> 0x00c2 }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x00c2 }
            com.google.android.libraries.stitch.binder.Module r8 = (com.google.android.libraries.stitch.binder.Module) r8     // Catch:{ all -> 0x00c2 }
            java.lang.String r9 = "configure"
            java.lang.String r10 = "%s %s"
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c2 }
            r11[r5] = r8     // Catch:{ all -> 0x00c2 }
            r11[r4] = r13     // Catch:{ all -> 0x00c2 }
            beginTrace(r9, r10, r11)     // Catch:{ all -> 0x00c2 }
            android.content.Context r9 = r12.context     // Catch:{ all -> 0x00aa }
            r8.configure(r9, r13, r12)     // Catch:{ all -> 0x00aa }
            endTrace()     // Catch:{ all -> 0x00c2 }
            int r7 = r7 + 1
            goto L_0x0086
        L_0x00aa:
            r4 = move-exception
            endTrace()     // Catch:{ all -> 0x00c2 }
            throw r4     // Catch:{ all -> 0x00c2 }
        L_0x00af:
            if (r2 != 0) goto L_0x00d7
            java.lang.ThreadLocal<java.lang.Boolean> r4 = r12.isInternallyBinding     // Catch:{ all -> 0x010b }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x010b }
            r4.set(r5)     // Catch:{ all -> 0x010b }
            android.os.StrictMode$ThreadPolicy r4 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x010b }
            if (r4 == 0) goto L_0x00d7
            android.os.StrictMode.setThreadPolicy(r3)     // Catch:{ all -> 0x010b }
            goto L_0x00d7
        L_0x00c2:
            r4 = move-exception
            if (r2 != 0) goto L_0x00d5
            java.lang.ThreadLocal<java.lang.Boolean> r6 = r12.isInternallyBinding     // Catch:{ all -> 0x010b }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x010b }
            r6.set(r5)     // Catch:{ all -> 0x010b }
            android.os.StrictMode$ThreadPolicy r5 = com.google.android.libraries.stitch.binder.Binder.moduleConfigureStrictModePolicy     // Catch:{ all -> 0x010b }
            if (r5 == 0) goto L_0x00d5
            android.os.StrictMode.setThreadPolicy(r3)     // Catch:{ all -> 0x010b }
        L_0x00d5:
            throw r4     // Catch:{ all -> 0x010b }
        L_0x00d7:
            boolean r2 = r1.isEmpty()     // Catch:{ all -> 0x010b }
            if (r2 != 0) goto L_0x00df
            monitor-exit(r0)     // Catch:{ all -> 0x010b }
            return r1
        L_0x00df:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x010b }
            r2.<init>()     // Catch:{ all -> 0x010b }
            r1 = r2
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.Object, java.lang.Object>> r2 = r12.keyBindings     // Catch:{ all -> 0x010b }
            java.lang.Object r2 = r2.get(r13)     // Catch:{ all -> 0x010b }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x010b }
            if (r2 == 0) goto L_0x0109
            java.util.Collection r3 = r2.values()     // Catch:{ all -> 0x010b }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x010b }
        L_0x00f7:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x010b }
            if (r4 == 0) goto L_0x0109
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x010b }
            java.lang.Object r5 = com.google.android.libraries.stitch.binder.Binder.UNBOUND     // Catch:{ all -> 0x010b }
            if (r4 == r5) goto L_0x0108
            r1.add(r4)     // Catch:{ all -> 0x010b }
        L_0x0108:
            goto L_0x00f7
        L_0x0109:
            monitor-exit(r0)     // Catch:{ all -> 0x010b }
            return r1
        L_0x010b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x010b }
            throw r1
        L_0x010e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Binder not initialized yet."
            r0.<init>(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.stitch.binder.Binder.getAllInstances(java.lang.Class):java.util.List");
    }

    private boolean isInternallyBinding() {
        Boolean isInternallyBindingNow = this.isInternallyBinding.get();
        return isInternallyBindingNow != null && isInternallyBindingNow.booleanValue();
    }

    public Binder getParent() {
        return this.parent;
    }

    public void setTag(String tag2) {
        this.tag = tag2;
    }

    static final class DuplicateBindingException extends RuntimeException {
        public DuplicateBindingException(String msg) {
            super(msg);
        }
    }

    static final class LateBindingException extends RuntimeException {
        public LateBindingException(String msg) {
            super(msg);
        }
    }
}
