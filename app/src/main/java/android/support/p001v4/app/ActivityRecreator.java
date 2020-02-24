package android.support.p001v4.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.app.ActivityRecreator */
final class ActivityRecreator {
    protected static final Class<?> activityThreadClass = getActivityThreadClass();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    protected static final Field mainThreadField = getMainThreadField();
    protected static final Method performStopActivity2ParamsMethod = getPerformStopActivity2Params(activityThreadClass);
    protected static final Method performStopActivity3ParamsMethod = getPerformStopActivity3Params(activityThreadClass);
    protected static final Method requestRelaunchActivityMethod = getRequestRelaunchActivityMethod(activityThreadClass);
    protected static final Field tokenField = getTokenField();

    private ActivityRecreator() {
    }

    static boolean recreate(@NonNull Activity activity) {
        Object activityThread;
        final Application application;
        final LifecycleCheckCallbacks callbacks;
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return true;
        } else if (needsRelaunchCall() && requestRelaunchActivityMethod == null) {
            return false;
        } else {
            if (performStopActivity2ParamsMethod == null && performStopActivity3ParamsMethod == null) {
                return false;
            }
            try {
                final Object token = tokenField.get(activity);
                if (token == null || (activityThread = mainThreadField.get(activity)) == null) {
                    return false;
                }
                application = activity.getApplication();
                callbacks = new LifecycleCheckCallbacks(activity);
                application.registerActivityLifecycleCallbacks(callbacks);
                mainHandler.post(new Runnable() {
                    public void run() {
                        callbacks.currentlyRecreatingToken = token;
                    }
                });
                if (needsRelaunchCall()) {
                    requestRelaunchActivityMethod.invoke(activityThread, token, null, null, 0, false, null, null, false, false);
                } else {
                    activity.recreate();
                }
                mainHandler.post(new Runnable() {
                    public void run() {
                        application.unregisterActivityLifecycleCallbacks(callbacks);
                    }
                });
                return true;
            } catch (Throwable t) {
                handleReflectiveException(t);
                return false;
            }
        }
    }

    /* renamed from: android.support.v4.app.ActivityRecreator$LifecycleCheckCallbacks */
    private static final class LifecycleCheckCallbacks implements Application.ActivityLifecycleCallbacks {
        Object currentlyRecreatingToken;
        private Activity mActivity;
        private boolean mDestroyed = false;
        private boolean mStarted = false;
        private boolean mStopQueued = false;

        LifecycleCheckCallbacks(@NonNull Activity aboutToRecreate) {
            this.mActivity = aboutToRecreate;
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
            if (this.mActivity == activity) {
                this.mStarted = true;
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
            if (this.mDestroyed && !this.mStopQueued && !this.mStarted && ActivityRecreator.queueOnStopIfNecessary(this.currentlyRecreatingToken, activity)) {
                this.mStopQueued = true;
                this.currentlyRecreatingToken = null;
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.mActivity == activity) {
                this.mActivity = null;
                this.mDestroyed = true;
            }
        }
    }

    protected static boolean queueOnStopIfNecessary(Object currentlyRecreatingToken, Activity activity) {
        try {
            final Object token = tokenField.get(activity);
            if (token != currentlyRecreatingToken) {
                return false;
            }
            final Object activityThread = mainThreadField.get(activity);
            mainHandler.postAtFrontOfQueue(new Runnable() {
                public void run() {
                    try {
                        if (ActivityRecreator.performStopActivity3ParamsMethod != null) {
                            ActivityRecreator.performStopActivity3ParamsMethod.invoke(activityThread, token, false, "AppCompat recreation");
                            return;
                        }
                        ActivityRecreator.performStopActivity2ParamsMethod.invoke(activityThread, token, false);
                    } catch (RuntimeException e) {
                        if (e.getClass() != RuntimeException.class || e.getMessage() == null || !e.getMessage().startsWith("Unable to stop")) {
                            ActivityRecreator.handleReflectiveException(e);
                            return;
                        }
                        throw e;
                    } catch (Throwable t) {
                        ActivityRecreator.handleReflectiveException(t);
                    }
                }
            });
            return true;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return true;
        }
    }

    private static Method getPerformStopActivity3Params(Class<?> activityThreadClass2) {
        if (activityThreadClass2 == null) {
            return null;
        }
        try {
            Method performStop = activityThreadClass2.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE, String.class);
            performStop.setAccessible(true);
            return performStop;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    private static Method getPerformStopActivity2Params(Class<?> activityThreadClass2) {
        if (activityThreadClass2 == null) {
            return null;
        }
        try {
            Method performStop = activityThreadClass2.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE);
            performStop.setAccessible(true);
            return performStop;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    private static boolean needsRelaunchCall() {
        return Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27;
    }

    private static Method getRequestRelaunchActivityMethod(Class<?> activityThreadClass2) {
        if (!needsRelaunchCall() || activityThreadClass2 == null) {
            return null;
        }
        try {
            Method relaunch = activityThreadClass2.getDeclaredMethod("requestRelaunchActivity", IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE);
            relaunch.setAccessible(true);
            return relaunch;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    private static Field getMainThreadField() {
        try {
            Field mainThreadField2 = Activity.class.getDeclaredField("mMainThread");
            mainThreadField2.setAccessible(true);
            return mainThreadField2;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    private static Field getTokenField() {
        try {
            Field tokenField2 = Activity.class.getDeclaredField("mToken");
            tokenField2.setAccessible(true);
            return tokenField2;
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    private static Class<?> getActivityThreadClass() {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (Throwable t) {
            handleReflectiveException(t);
            return null;
        }
    }

    protected static void handleReflectiveException(Throwable t) {
        ThrowableExtension.printStackTrace(t);
    }
}
