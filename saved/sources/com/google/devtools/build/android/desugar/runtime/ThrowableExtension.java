package com.google.devtools.build.android.desugar.runtime;

import java.io.Closeable;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public final class ThrowableExtension {
    private static final String ANDROID_OS_BUILD_VERSION = "android.os.Build$VERSION";
    static final int API_LEVEL;
    static final AbstractDesugaringStrategy STRATEGY;
    public static final String SYSTEM_PROPERTY_TWR_DISABLE_MIMIC = "com.google.devtools.build.android.desugar.runtime.twr_disable_mimic";

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0065  */
    static {
        /*
            r0 = 0
            java.lang.Integer r1 = readApiLevelFromBuildVersion()     // Catch:{ all -> 0x0028 }
            r0 = r1
            if (r0 == 0) goto L_0x0016
            int r1 = r0.intValue()     // Catch:{ all -> 0x0028 }
            r2 = 19
            if (r1 < r2) goto L_0x0016
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension$ReuseDesugaringStrategy r1 = new com.google.devtools.build.android.desugar.runtime.ThrowableExtension$ReuseDesugaringStrategy     // Catch:{ all -> 0x0028 }
            r1.<init>()     // Catch:{ all -> 0x0028 }
            goto L_0x0027
        L_0x0016:
            boolean r1 = useMimicStrategy()     // Catch:{ all -> 0x0028 }
            if (r1 == 0) goto L_0x0022
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension$MimicDesugaringStrategy r1 = new com.google.devtools.build.android.desugar.runtime.ThrowableExtension$MimicDesugaringStrategy     // Catch:{ all -> 0x0028 }
            r1.<init>()     // Catch:{ all -> 0x0028 }
            goto L_0x0027
        L_0x0022:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension$NullDesugaringStrategy r1 = new com.google.devtools.build.android.desugar.runtime.ThrowableExtension$NullDesugaringStrategy     // Catch:{ all -> 0x0028 }
            r1.<init>()     // Catch:{ all -> 0x0028 }
        L_0x0027:
            goto L_0x005f
        L_0x0028:
            r1 = move-exception
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.Class<com.google.devtools.build.android.desugar.runtime.ThrowableExtension$NullDesugaringStrategy> r3 = com.google.devtools.build.android.desugar.runtime.ThrowableExtension.NullDesugaringStrategy.class
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            int r4 = r4 + 133
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy "
            r5.append(r4)
            r5.append(r3)
            java.lang.String r3 = "will be used. The error is: "
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r2.println(r3)
            java.io.PrintStream r2 = java.lang.System.err
            r1.printStackTrace(r2)
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension$NullDesugaringStrategy r2 = new com.google.devtools.build.android.desugar.runtime.ThrowableExtension$NullDesugaringStrategy
            r2.<init>()
            r1 = r2
        L_0x005f:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.STRATEGY = r1
            if (r0 != 0) goto L_0x0065
            r2 = 1
            goto L_0x0069
        L_0x0065:
            int r2 = r0.intValue()
        L_0x0069:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.API_LEVEL = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.devtools.build.android.desugar.runtime.ThrowableExtension.<clinit>():void");
    }

    public static AbstractDesugaringStrategy getStrategy() {
        return STRATEGY;
    }

    public static void addSuppressed(Throwable receiver, Throwable suppressed) {
        STRATEGY.addSuppressed(receiver, suppressed);
    }

    public static Throwable[] getSuppressed(Throwable receiver) {
        return STRATEGY.getSuppressed(receiver);
    }

    public static void printStackTrace(Throwable receiver) {
        STRATEGY.printStackTrace(receiver);
    }

    public static void printStackTrace(Throwable receiver, PrintWriter writer) {
        STRATEGY.printStackTrace(receiver, writer);
    }

    public static void printStackTrace(Throwable receiver, PrintStream stream) {
        STRATEGY.printStackTrace(receiver, stream);
    }

    public static void closeResource(Throwable throwable, Object resource) throws Throwable {
        if (resource != null) {
            try {
                if (API_LEVEL >= 19) {
                    ((AutoCloseable) resource).close();
                    return;
                } else if (resource instanceof Closeable) {
                    ((Closeable) resource).close();
                    return;
                } else {
                    resource.getClass().getMethod("close", new Class[0]).invoke(resource, new Object[0]);
                    return;
                }
            } catch (NoSuchMethodException e) {
                e = e;
            } catch (SecurityException e2) {
                e = e2;
            } catch (IllegalAccessException e3) {
                e = e3;
                String valueOf = String.valueOf(resource.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Fail to call close() on ");
                sb.append(valueOf);
                throw new AssertionError(sb.toString(), e);
            } catch (IllegalArgumentException e4) {
                e = e4;
                String valueOf2 = String.valueOf(resource.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 24);
                sb2.append("Fail to call close() on ");
                sb2.append(valueOf2);
                throw new AssertionError(sb2.toString(), e);
            } catch (ExceptionInInitializerError e5) {
                e = e5;
                String valueOf22 = String.valueOf(resource.getClass());
                StringBuilder sb22 = new StringBuilder(String.valueOf(valueOf22).length() + 24);
                sb22.append("Fail to call close() on ");
                sb22.append(valueOf22);
                throw new AssertionError(sb22.toString(), e);
            } catch (InvocationTargetException e6) {
                throw e6.getCause();
            } catch (Throwable e7) {
                if (throwable != null) {
                    addSuppressed(throwable, e7);
                    throw throwable;
                }
                throw e7;
            }
        } else {
            return;
        }
        String valueOf3 = String.valueOf(resource.getClass());
        StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 32);
        sb3.append(valueOf3);
        sb3.append(" does not have a close() method.");
        throw new AssertionError(sb3.toString(), e);
    }

    private static boolean useMimicStrategy() {
        return !Boolean.getBoolean(SYSTEM_PROPERTY_TWR_DISABLE_MIMIC);
    }

    private static Integer readApiLevelFromBuildVersion() {
        try {
            return (Integer) Class.forName(ANDROID_OS_BUILD_VERSION).getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    static abstract class AbstractDesugaringStrategy {
        protected static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

        public abstract void addSuppressed(Throwable th, Throwable th2);

        public abstract Throwable[] getSuppressed(Throwable th);

        public abstract void printStackTrace(Throwable th);

        public abstract void printStackTrace(Throwable th, PrintStream printStream);

        public abstract void printStackTrace(Throwable th, PrintWriter printWriter);

        AbstractDesugaringStrategy() {
        }
    }

    static final class ReuseDesugaringStrategy extends AbstractDesugaringStrategy {
        ReuseDesugaringStrategy() {
        }

        public void addSuppressed(Throwable receiver, Throwable suppressed) {
            receiver.addSuppressed(suppressed);
        }

        public Throwable[] getSuppressed(Throwable receiver) {
            return receiver.getSuppressed();
        }

        public void printStackTrace(Throwable receiver) {
            receiver.printStackTrace();
        }

        public void printStackTrace(Throwable receiver, PrintStream stream) {
            receiver.printStackTrace(stream);
        }

        public void printStackTrace(Throwable receiver, PrintWriter writer) {
            receiver.printStackTrace(writer);
        }
    }

    static final class MimicDesugaringStrategy extends AbstractDesugaringStrategy {
        static final String SUPPRESSED_PREFIX = "Suppressed: ";
        private final ConcurrentWeakIdentityHashMap map = new ConcurrentWeakIdentityHashMap();

        MimicDesugaringStrategy() {
        }

        public void addSuppressed(Throwable receiver, Throwable suppressed) {
            if (suppressed == receiver) {
                throw new IllegalArgumentException("Self suppression is not allowed.", suppressed);
            } else if (suppressed != null) {
                this.map.get(receiver, true).add(suppressed);
            } else {
                throw new NullPointerException("The suppressed exception cannot be null.");
            }
        }

        public Throwable[] getSuppressed(Throwable receiver) {
            List<Throwable> list = this.map.get(receiver, false);
            if (list == null || list.isEmpty()) {
                return EMPTY_THROWABLE_ARRAY;
            }
            return (Throwable[]) list.toArray(EMPTY_THROWABLE_ARRAY);
        }

        public void printStackTrace(Throwable receiver) {
            receiver.printStackTrace();
            List<Throwable> suppressedList = this.map.get(receiver, false);
            if (suppressedList != null) {
                synchronized (suppressedList) {
                    for (Throwable suppressed : suppressedList) {
                        System.err.print(SUPPRESSED_PREFIX);
                        suppressed.printStackTrace();
                    }
                }
            }
        }

        public void printStackTrace(Throwable receiver, PrintStream stream) {
            receiver.printStackTrace(stream);
            List<Throwable> suppressedList = this.map.get(receiver, false);
            if (suppressedList != null) {
                synchronized (suppressedList) {
                    for (Throwable suppressed : suppressedList) {
                        stream.print(SUPPRESSED_PREFIX);
                        suppressed.printStackTrace(stream);
                    }
                }
            }
        }

        public void printStackTrace(Throwable receiver, PrintWriter writer) {
            receiver.printStackTrace(writer);
            List<Throwable> suppressedList = this.map.get(receiver, false);
            if (suppressedList != null) {
                synchronized (suppressedList) {
                    for (Throwable suppressed : suppressedList) {
                        writer.print(SUPPRESSED_PREFIX);
                        suppressed.printStackTrace(writer);
                    }
                }
            }
        }
    }

    static final class ConcurrentWeakIdentityHashMap {
        private final ConcurrentHashMap<WeakKey, List<Throwable>> map = new ConcurrentHashMap<>(16, 0.75f, 10);
        private final ReferenceQueue<Throwable> referenceQueue = new ReferenceQueue<>();

        ConcurrentWeakIdentityHashMap() {
        }

        public List<Throwable> get(Throwable throwable, boolean createOnAbsence) {
            deleteEmptyKeys();
            List<Throwable> list = this.map.get(new WeakKey(throwable, null));
            if (!createOnAbsence || list != null) {
                return list;
            }
            List<Throwable> newValue = new Vector<>(2);
            List<Throwable> list2 = this.map.putIfAbsent(new WeakKey(throwable, this.referenceQueue), newValue);
            return list2 == null ? newValue : list2;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.map.size();
        }

        /* access modifiers changed from: package-private */
        public void deleteEmptyKeys() {
            Reference<?> key = this.referenceQueue.poll();
            while (key != null) {
                this.map.remove(key);
                key = this.referenceQueue.poll();
            }
        }

        private static final class WeakKey extends WeakReference<Throwable> {
            private final int hash;

            public WeakKey(Throwable referent, ReferenceQueue<Throwable> q) {
                super(referent, q);
                if (referent != null) {
                    this.hash = System.identityHashCode(referent);
                    return;
                }
                throw new NullPointerException("The referent cannot be null");
            }

            public int hashCode() {
                return this.hash;
            }

            public boolean equals(Object obj) {
                if (obj == null || obj.getClass() != getClass()) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                WeakKey other = (WeakKey) obj;
                if (this.hash == other.hash && get() == other.get()) {
                    return true;
                }
                return false;
            }
        }
    }

    static final class NullDesugaringStrategy extends AbstractDesugaringStrategy {
        NullDesugaringStrategy() {
        }

        public void addSuppressed(Throwable receiver, Throwable suppressed) {
        }

        public Throwable[] getSuppressed(Throwable receiver) {
            return EMPTY_THROWABLE_ARRAY;
        }

        public void printStackTrace(Throwable receiver) {
            receiver.printStackTrace();
        }

        public void printStackTrace(Throwable receiver, PrintStream stream) {
            receiver.printStackTrace(stream);
        }

        public void printStackTrace(Throwable receiver, PrintWriter writer) {
            receiver.printStackTrace(writer);
        }
    }
}
