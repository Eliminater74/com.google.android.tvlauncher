package android.support.p001v4.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.util.SimpleArrayMap;
import java.lang.reflect.InvocationTargetException;

/* renamed from: android.support.v4.app.FragmentFactory */
public class FragmentFactory {
    private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap<>();

    @NonNull
    private static Class<?> loadClass(@NonNull ClassLoader classLoader, @NonNull String className) throws ClassNotFoundException {
        Class<?> clazz = sClassMap.get(className);
        if (clazz != null) {
            return clazz;
        }
        Class<?> clazz2 = Class.forName(className, false, classLoader);
        sClassMap.put(className, clazz2);
        return clazz2;
    }

    static boolean isFragmentClass(@NonNull ClassLoader classLoader, @NonNull String className) {
        try {
            return Fragment.class.isAssignableFrom(loadClass(classLoader, className));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @NonNull
    public static Class<? extends Fragment> loadFragmentClass(@NonNull ClassLoader classLoader, @NonNull String className) {
        try {
            return loadClass(classLoader, className);
        } catch (ClassNotFoundException e) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists", e);
        } catch (ClassCastException e2) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class is a valid subclass of Fragment", e2);
        }
    }

    @Deprecated
    @NonNull
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className, @Nullable Bundle args) {
        return instantiate(classLoader, className);
    }

    @NonNull
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        try {
            return (Fragment) loadFragmentClass(classLoader, className).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (InstantiationException e) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (IllegalAccessException e2) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (NoSuchMethodException e3) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": could not find Fragment constructor", e3);
        } catch (InvocationTargetException e4) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": calling Fragment constructor caused an exception", e4);
        }
    }
}
