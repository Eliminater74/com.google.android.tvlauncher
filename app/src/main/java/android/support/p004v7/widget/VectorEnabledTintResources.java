package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.VectorEnabledTintResources */
public class VectorEnabledTintResources extends Resources {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    private static boolean sCompatVectorFromResourcesEnabled = false;
    private final WeakReference<Context> mContextRef;

    public VectorEnabledTintResources(@NonNull Context context, @NonNull Resources res) {
        super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
        this.mContextRef = new WeakReference<>(context);
    }

    public static boolean shouldBeUsed() {
        return isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20;
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }

    public static void setCompatVectorFromResourcesEnabled(boolean enabled) {
        sCompatVectorFromResourcesEnabled = enabled;
    }

    public Drawable getDrawable(int id) throws Resources.NotFoundException {
        Context context = this.mContextRef.get();
        if (context != null) {
            return ResourceManagerInternal.get().onDrawableLoadedFromResources(context, this, id);
        }
        return super.getDrawable(id);
    }

    /* access modifiers changed from: package-private */
    public final Drawable superGetDrawable(int id) {
        return super.getDrawable(id);
    }
}
