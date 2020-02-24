package com.google.protobuf;

import com.google.protobuf.ExtensionRegistryLite;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class GeneratedExtensionRegistryLoader<T extends ExtensionRegistryLite> {
    private static String LITE_CLASS_NAME = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";
    private static final Logger logger = Logger.getLogger(CodedOutputStream.class.getName());

    /* access modifiers changed from: protected */
    public abstract T getInstance();

    GeneratedExtensionRegistryLoader() {
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.util.ServiceConfigurationError]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    static <T extends ExtensionRegistryLite> T load(Class<T> extensionRegistryClass) {
        String wellKnownClassName;
        ClassLoader classLoader = GeneratedExtensionRegistryLoader.class.getClassLoader();
        if (extensionRegistryClass.equals(ExtensionRegistryLite.class)) {
            wellKnownClassName = LITE_CLASS_NAME;
        } else if (extensionRegistryClass.getPackage().equals(GeneratedExtensionRegistryLoader.class.getPackage())) {
            wellKnownClassName = String.format("%s.BlazeGenerated%sLoader", extensionRegistryClass.getPackage().getName(), extensionRegistryClass.getSimpleName());
        } else {
            throw new IllegalArgumentException(extensionRegistryClass.getName());
        }
        try {
            return (ExtensionRegistryLite) extensionRegistryClass.cast(((GeneratedExtensionRegistryLoader) Class.forName(wellKnownClassName, true, classLoader).getConstructor(new Class[0]).newInstance(new Object[0])).getInstance());
        } catch (NoSuchMethodException nsme) {
            throw new IllegalStateException(nsme);
        } catch (InstantiationException ie) {
            throw new IllegalStateException(ie);
        } catch (IllegalAccessException iae) {
            throw new IllegalStateException(iae);
        } catch (InvocationTargetException ite) {
            throw new IllegalStateException(ite);
        } catch (ClassNotFoundException e) {
            Iterator<GeneratedExtensionRegistryLoader<T>> it = ServiceLoader.load(GeneratedExtensionRegistryLoader.class, classLoader).iterator();
            ArrayList<T> registries = new ArrayList<>();
            while (it.hasNext()) {
                try {
                    registries.add(extensionRegistryClass.cast(it.next().getInstance()));
                } catch (ServiceConfigurationError e2) {
                    ServiceConfigurationError e3 = e2;
                    Logger logger2 = logger;
                    Level level = Level.SEVERE;
                    String valueOf = String.valueOf(extensionRegistryClass.getSimpleName());
                    logger2.logp(level, "com.google.protobuf.GeneratedExtensionRegistryLoader", "load", valueOf.length() != 0 ? "Unable to load ".concat(valueOf) : new String("Unable to load "), (Throwable) e3);
                }
            }
            if (registries.size() == 1) {
                return (ExtensionRegistryLite) registries.get(0);
            }
            if (registries.size() == 0) {
                return null;
            }
            try {
                return (ExtensionRegistryLite) extensionRegistryClass.getMethod("combine", Collection.class).invoke(null, registries);
            } catch (NoSuchMethodException nsme2) {
                throw new IllegalStateException(nsme2);
            } catch (IllegalAccessException iae2) {
                throw new IllegalStateException(iae2);
            } catch (InvocationTargetException ite2) {
                throw new IllegalStateException(ite2);
            }
        }
    }
}
