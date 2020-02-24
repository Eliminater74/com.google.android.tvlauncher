package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
public class FinalizableReferenceQueue implements Closeable {
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method startFinalizer = getStartFinalizer(loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));
    final PhantomReference<Object> frqRef = new PhantomReference<>(this, this.queue);
    final ReferenceQueue<Object> queue = new ReferenceQueue<>();
    final boolean threadStarted;

    interface FinalizerLoader {
        @NullableDecl
        Class<?> loadFinalizer();
    }

    public FinalizableReferenceQueue() {
        boolean threadStarted2 = false;
        try {
            startFinalizer.invoke(null, FinalizableReference.class, this.queue, this.frqRef);
            threadStarted2 = true;
        } catch (IllegalAccessException impossible) {
            throw new AssertionError(impossible);
        } catch (Throwable th) {
            logger.logp(Level.INFO, "com.google.common.base.FinalizableReferenceQueue", "<init>", "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
        }
        this.threadStarted = threadStarted2;
    }

    public void close() {
        this.frqRef.enqueue();
        cleanUp();
    }

    /* access modifiers changed from: package-private */
    public void cleanUp() {
        if (!this.threadStarted) {
            while (true) {
                Reference<?> poll = this.queue.poll();
                Reference<?> reference = poll;
                if (poll != null) {
                    reference.clear();
                    try {
                        ((FinalizableReference) reference).finalizeReferent();
                    } catch (Throwable th) {
                        logger.logp(Level.SEVERE, "com.google.common.base.FinalizableReferenceQueue", "cleanUp", "Error cleaning up after reference.", th);
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static Class<?> loadFinalizer(FinalizerLoader... loaders) {
        for (FinalizerLoader loader : loaders) {
            Class<?> finalizer = loader.loadFinalizer();
            if (finalizer != null) {
                return finalizer;
            }
        }
        throw new AssertionError();
    }

    static class SystemLoader implements FinalizerLoader {
        @VisibleForTesting
        static boolean disabled;

        SystemLoader() {
        }

        @NullableDecl
        public Class<?> loadFinalizer() {
            if (disabled) {
                return null;
            }
            try {
                ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
                if (systemLoader == null) {
                    return null;
                }
                try {
                    return systemLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
                } catch (ClassNotFoundException e) {
                    return null;
                }
            } catch (SecurityException e2) {
                FinalizableReferenceQueue.logger.logp(Level.INFO, "com.google.common.base.FinalizableReferenceQueue$SystemLoader", "loadFinalizer", "Not allowed to access system class loader.");
                return null;
            }
        }
    }

    static class DecoupledLoader implements FinalizerLoader {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

        DecoupledLoader() {
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
         arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Exception]
         candidates:
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
        @NullableDecl
        public Class<?> loadFinalizer() {
            try {
                return newLoader(getBaseUrl()).loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (Exception e) {
                FinalizableReferenceQueue.logger.logp(Level.WARNING, "com.google.common.base.FinalizableReferenceQueue$DecoupledLoader", "loadFinalizer", LOADING_ERROR, (Throwable) e);
                return null;
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
         arg types: [int, int]
         candidates:
          ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
          ClspMth{java.lang.String.replace(char, char):java.lang.String} */
        /* access modifiers changed from: package-private */
        public URL getBaseUrl() throws IOException {
            String finalizerPath = String.valueOf(FinalizableReferenceQueue.FINALIZER_CLASS_NAME.replace('.', '/')).concat(".class");
            URL finalizerUrl = getClass().getClassLoader().getResource(finalizerPath);
            if (finalizerUrl != null) {
                String urlString = finalizerUrl.toString();
                if (urlString.endsWith(finalizerPath)) {
                    return new URL(finalizerUrl, urlString.substring(0, urlString.length() - finalizerPath.length()));
                }
                String valueOf = String.valueOf(urlString);
                throw new IOException(valueOf.length() != 0 ? "Unsupported path style: ".concat(valueOf) : new String("Unsupported path style: "));
            }
            throw new FileNotFoundException(finalizerPath);
        }

        /* access modifiers changed from: package-private */
        public URLClassLoader newLoader(URL base) {
            return new URLClassLoader(new URL[]{base}, null);
        }
    }

    static class DirectLoader implements FinalizerLoader {
        DirectLoader() {
        }

        public Class<?> loadFinalizer() {
            try {
                return Class.forName(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    static Method getStartFinalizer(Class<?> finalizer) {
        try {
            return finalizer.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
