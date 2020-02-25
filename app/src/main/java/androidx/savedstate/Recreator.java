package androidx.savedstate;

import android.annotation.SuppressLint;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressLint({"RestrictedApi"})
final class Recreator implements GenericLifecycleObserver {
    static final String CLASSES_KEY = "classes_to_restore";
    static final String COMPONENT_KEY = "androidx.savedstate.Restarter";
    private final SavedStateRegistryOwner mOwner;

    Recreator(SavedStateRegistryOwner owner) {
        this.mOwner = owner;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            source.getLifecycle().removeObserver(this);
            Bundle bundle = this.mOwner.getSavedStateRegistry().consumeRestoredStateForKey(COMPONENT_KEY);
            if (bundle != null) {
                ArrayList<String> classes = bundle.getStringArrayList(CLASSES_KEY);
                if (classes != null) {
                    Iterator<String> it = classes.iterator();
                    while (it.hasNext()) {
                        reflectiveNew(it.next());
                    }
                    return;
                }
                throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
            }
            return;
        }
        throw new AssertionError("Next event must be ON_CREATE");
    }

    private void reflectiveNew(String className) {
        try {
            Class<? extends SavedStateRegistry.AutoRecreated> clazz = Class.forName(className, false, Recreator.class.getClassLoader()).asSubclass(SavedStateRegistry.AutoRecreated.class);
            try {
                Constructor<? extends SavedStateRegistry.AutoRecreated> constructor = clazz.getDeclaredConstructor(new Class[0]);
                constructor.setAccessible(true);
                try {
                    ((SavedStateRegistry.AutoRecreated) constructor.newInstance(new Object[0])).onRecreated(this.mOwner);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate " + className, e);
                }
            } catch (NoSuchMethodException e2) {
                throw new IllegalStateException("Class" + clazz.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
            }
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("Class " + className + " wasn't found", e3);
        }
    }

    static final class SavedStateProvider implements SavedStateRegistry.SavedStateProvider {
        final Set<String> mClasses = new HashSet();

        SavedStateProvider(SavedStateRegistry registry) {
            registry.registerSavedStateProvider(Recreator.COMPONENT_KEY, this);
        }

        @NonNull
        public Bundle saveState() {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Recreator.CLASSES_KEY, new ArrayList(this.mClasses));
            return bundle;
        }

        /* access modifiers changed from: package-private */
        public void add(String className) {
            this.mClasses.add(className);
        }
    }
}
