package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.p001v4.content.ContextCompat;
import android.support.p001v4.graphics.drawable.DrawableCompat;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.util.LongSparseArray;
import android.support.p001v4.util.LruCache;
import android.support.p001v4.util.SparseArrayCompat;
import android.support.p004v7.graphics.drawable.AnimatedStateListDrawableCompat;
import android.support.p004v7.resources.C0245R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import org.xmlpull.v1.XmlPullParser;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.ResourceManagerInternal */
public final class ResourceManagerInternal {
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "ResourceManagerInternal";
    private static ResourceManagerInternal INSTANCE = null;
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap<>(0);
    private ArrayMap<String, InflateDelegate> mDelegates;
    private boolean mHasCheckedVectorDrawableSetup;
    private ResourceManagerHooks mHooks;
    private SparseArrayCompat<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    public static synchronized ResourceManagerInternal get() {
        ResourceManagerInternal resourceManagerInternal;
        synchronized (ResourceManagerInternal.class) {
            if (INSTANCE == null) {
                INSTANCE = new ResourceManagerInternal();
                installDefaultInflateDelegates(INSTANCE);
            }
            resourceManagerInternal = INSTANCE;
        }
        return resourceManagerInternal;
    }

    private static void installDefaultInflateDelegates(@NonNull ResourceManagerInternal manager) {
        if (Build.VERSION.SDK_INT < 24) {
            manager.addDelegate("vector", new VdcInflateDelegate());
            manager.addDelegate("animated-vector", new AvdcInflateDelegate());
            manager.addDelegate("animated-selector", new AsldcInflateDelegate());
        }
    }

    private static long createCacheKey(TypedValue tv) {
        return (((long) tv.assetCookie) << 32) | ((long) tv.data);
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int id : array) {
            if (id == value) {
                return true;
            }
        }
        return false;
    }

    static void tintDrawable(Drawable drawable, TintInfo tint, int[] state) {
        if (!DrawableUtils.canSafelyMutateDrawable(drawable) || drawable.mutate() == drawable) {
            if (tint.mHasTintList || tint.mHasTintMode) {
                drawable.setColorFilter(createTintFilter(tint.mHasTintList ? tint.mTintList : null, tint.mHasTintMode ? tint.mTintMode : DEFAULT_MODE, state));
            } else {
                drawable.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d(TAG, "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList tint, PorterDuff.Mode tintMode, int[] state) {
        if (tint == null || tintMode == null) {
            return null;
        }
        return getPorterDuffColorFilter(tint.getColorForState(state, 0), tintMode);
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int color, PorterDuff.Mode mode) {
        PorterDuffColorFilter filter;
        synchronized (ResourceManagerInternal.class) {
            filter = COLOR_FILTER_CACHE.get(color, mode);
            if (filter == null) {
                filter = new PorterDuffColorFilter(color, mode);
                COLOR_FILTER_CACHE.put(color, mode, filter);
            }
        }
        return filter;
    }

    private static boolean isVectorDrawable(@NonNull Drawable d) {
        return (d instanceof VectorDrawableCompat) || PLATFORM_VD_CLAZZ.equals(d.getClass().getName());
    }

    public synchronized void setHooks(ResourceManagerHooks hooks) {
        this.mHooks = hooks;
    }

    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return getDrawable(context, resId, false);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int resId, boolean failIfNotKnown) {
        Drawable drawable;
        checkVectorDrawableSetup(context);
        drawable = loadDrawableFromDelegates(context, resId);
        if (drawable == null) {
            drawable = createDrawableIfNeeded(context, resId);
        }
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(context, resId);
        }
        if (drawable != null) {
            drawable = tintDrawable(context, resId, failIfNotKnown, drawable);
        }
        if (drawable != null) {
            DrawableUtils.fixDrawable(drawable);
        }
        return drawable;
    }

    public synchronized void onConfigurationChanged(@NonNull Context context) {
        LongSparseArray<WeakReference<Drawable.ConstantState>> cache = this.mDrawableCaches.get(context);
        if (cache != null) {
            cache.clear();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    private Drawable createDrawableIfNeeded(@NonNull Context context, @DrawableRes int resId) {
        Drawable drawable;
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue tv = this.mTypedValue;
        context.getResources().getValue(resId, tv, true);
        long key = createCacheKey(tv);
        Drawable dr = getCachedDrawable(context, key);
        if (dr != null) {
            return dr;
        }
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        if (resourceManagerHooks == null) {
            drawable = null;
        } else {
            drawable = resourceManagerHooks.createDrawableFor(this, context, resId);
        }
        Drawable dr2 = drawable;
        if (dr2 != null) {
            dr2.setChangingConfigurations(tv.changingConfigurations);
            addDrawableToCache(context, key, dr2);
        }
        return dr2;
    }

    private Drawable tintDrawable(@NonNull Context context, @DrawableRes int resId, boolean failIfNotKnown, @NonNull Drawable drawable) {
        ColorStateList tintList = getTintList(context, resId);
        if (tintList != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable drawable2 = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable2, tintList);
            PorterDuff.Mode tintMode = getTintMode(resId);
            if (tintMode == null) {
                return drawable2;
            }
            DrawableCompat.setTintMode(drawable2, tintMode);
            return drawable2;
        }
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        if ((resourceManagerHooks == null || !resourceManagerHooks.tintDrawable(context, resId, drawable)) && !tintDrawableUsingColorFilter(context, resId, drawable) && failIfNotKnown) {
            return null;
        }
        return drawable;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0078 A[Catch:{ Exception -> 0x00a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00a0 A[Catch:{ Exception -> 0x00a8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable loadDrawableFromDelegates(@android.support.annotation.NonNull android.content.Context r13, @android.support.annotation.DrawableRes int r14) {
        /*
            r12 = this;
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.ResourceManagerInternal$InflateDelegate> r0 = r12.mDelegates
            r1 = 0
            if (r0 == 0) goto L_0x00b8
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x00b8
            android.support.v4.util.SparseArrayCompat<java.lang.String> r0 = r12.mKnownDrawableIdTags
            java.lang.String r2 = "appcompat_skip_skip"
            if (r0 == 0) goto L_0x002a
            java.lang.Object r0 = r0.get(r14)
            java.lang.String r0 = (java.lang.String) r0
            boolean r3 = r2.equals(r0)
            if (r3 != 0) goto L_0x0029
            if (r0 == 0) goto L_0x0028
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.ResourceManagerInternal$InflateDelegate> r3 = r12.mDelegates
            java.lang.Object r3 = r3.get(r0)
            if (r3 != 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            goto L_0x0031
        L_0x0029:
            return r1
        L_0x002a:
            android.support.v4.util.SparseArrayCompat r0 = new android.support.v4.util.SparseArrayCompat
            r0.<init>()
            r12.mKnownDrawableIdTags = r0
        L_0x0031:
            android.util.TypedValue r0 = r12.mTypedValue
            if (r0 != 0) goto L_0x003c
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            r12.mTypedValue = r0
        L_0x003c:
            android.util.TypedValue r0 = r12.mTypedValue
            android.content.res.Resources r1 = r13.getResources()
            r3 = 1
            r1.getValue(r14, r0, r3)
            long r4 = createCacheKey(r0)
            android.graphics.drawable.Drawable r6 = r12.getCachedDrawable(r13, r4)
            if (r6 == 0) goto L_0x0051
            return r6
        L_0x0051:
            java.lang.CharSequence r7 = r0.string
            if (r7 == 0) goto L_0x00b0
            java.lang.CharSequence r7 = r0.string
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = ".xml"
            boolean r7 = r7.endsWith(r8)
            if (r7 == 0) goto L_0x00b0
            android.content.res.XmlResourceParser r7 = r1.getXml(r14)     // Catch:{ Exception -> 0x00a8 }
            android.util.AttributeSet r8 = android.util.Xml.asAttributeSet(r7)     // Catch:{ Exception -> 0x00a8 }
        L_0x006b:
            int r9 = r7.next()     // Catch:{ Exception -> 0x00a8 }
            r10 = r9
            r11 = 2
            if (r9 == r11) goto L_0x0076
            if (r10 == r3) goto L_0x0076
            goto L_0x006b
        L_0x0076:
            if (r10 != r11) goto L_0x00a0
            java.lang.String r3 = r7.getName()     // Catch:{ Exception -> 0x00a8 }
            android.support.v4.util.SparseArrayCompat<java.lang.String> r9 = r12.mKnownDrawableIdTags     // Catch:{ Exception -> 0x00a8 }
            r9.append(r14, r3)     // Catch:{ Exception -> 0x00a8 }
            android.support.v4.util.ArrayMap<java.lang.String, android.support.v7.widget.ResourceManagerInternal$InflateDelegate> r9 = r12.mDelegates     // Catch:{ Exception -> 0x00a8 }
            java.lang.Object r9 = r9.get(r3)     // Catch:{ Exception -> 0x00a8 }
            android.support.v7.widget.ResourceManagerInternal$InflateDelegate r9 = (android.support.p004v7.widget.ResourceManagerInternal.InflateDelegate) r9     // Catch:{ Exception -> 0x00a8 }
            if (r9 == 0) goto L_0x0095
            android.content.res.Resources$Theme r11 = r13.getTheme()     // Catch:{ Exception -> 0x00a8 }
            android.graphics.drawable.Drawable r11 = r9.createFromXmlInner(r13, r7, r8, r11)     // Catch:{ Exception -> 0x00a8 }
            r6 = r11
        L_0x0095:
            if (r6 == 0) goto L_0x009f
            int r11 = r0.changingConfigurations     // Catch:{ Exception -> 0x00a8 }
            r6.setChangingConfigurations(r11)     // Catch:{ Exception -> 0x00a8 }
            r12.addDrawableToCache(r13, r4, r6)     // Catch:{ Exception -> 0x00a8 }
        L_0x009f:
            goto L_0x00b0
        L_0x00a0:
            org.xmlpull.v1.XmlPullParserException r3 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r9 = "No start tag found"
            r3.<init>(r9)     // Catch:{ Exception -> 0x00a8 }
            throw r3     // Catch:{ Exception -> 0x00a8 }
        L_0x00a8:
            r3 = move-exception
            java.lang.String r7 = "ResourceManagerInternal"
            java.lang.String r8 = "Exception while inflating drawable"
            android.util.Log.e(r7, r8, r3)
        L_0x00b0:
            if (r6 != 0) goto L_0x00b7
            android.support.v4.util.SparseArrayCompat<java.lang.String> r3 = r12.mKnownDrawableIdTags
            r3.append(r14, r2)
        L_0x00b7:
            return r6
        L_0x00b8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ResourceManagerInternal.loadDrawableFromDelegates(android.content.Context, int):android.graphics.drawable.Drawable");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized android.graphics.drawable.Drawable getCachedDrawable(@android.support.annotation.NonNull android.content.Context r5, long r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.WeakHashMap<android.content.Context, android.support.v4.util.LongSparseArray<java.lang.ref.WeakReference<android.graphics.drawable.Drawable$ConstantState>>> r0 = r4.mDrawableCaches     // Catch:{ all -> 0x002d }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x002d }
            android.support.v4.util.LongSparseArray r0 = (android.support.p001v4.util.LongSparseArray) r0     // Catch:{ all -> 0x002d }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r4)
            return r1
        L_0x000e:
            java.lang.Object r2 = r0.get(r6)     // Catch:{ all -> 0x002d }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x002b
            java.lang.Object r3 = r2.get()     // Catch:{ all -> 0x002d }
            android.graphics.drawable.Drawable$ConstantState r3 = (android.graphics.drawable.Drawable.ConstantState) r3     // Catch:{ all -> 0x002d }
            if (r3 == 0) goto L_0x0028
            android.content.res.Resources r1 = r5.getResources()     // Catch:{ all -> 0x002d }
            android.graphics.drawable.Drawable r1 = r3.newDrawable(r1)     // Catch:{ all -> 0x002d }
            monitor-exit(r4)
            return r1
        L_0x0028:
            r0.delete(r6)     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r4)
            return r1
        L_0x002d:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ResourceManagerInternal.getCachedDrawable(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    private synchronized boolean addDrawableToCache(@NonNull Context context, long key, @NonNull Drawable drawable) {
        Drawable.ConstantState cs = drawable.getConstantState();
        if (cs == null) {
            return false;
        }
        LongSparseArray<WeakReference<Drawable.ConstantState>> cache = this.mDrawableCaches.get(context);
        if (cache == null) {
            cache = new LongSparseArray<>();
            this.mDrawableCaches.put(context, cache);
        }
        cache.put(key, new WeakReference(cs));
        return true;
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources resources, @DrawableRes int resId) {
        Drawable drawable = loadDrawableFromDelegates(context, resId);
        if (drawable == null) {
            drawable = resources.superGetDrawable(resId);
        }
        if (drawable == null) {
            return null;
        }
        return tintDrawable(context, resId, false, drawable);
    }

    /* access modifiers changed from: package-private */
    public boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int resId, @NonNull Drawable drawable) {
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        return resourceManagerHooks != null && resourceManagerHooks.tintDrawableUsingColorFilter(context, resId, drawable);
    }

    private void addDelegate(@NonNull String tagName, @NonNull InflateDelegate delegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap<>();
        }
        this.mDelegates.put(tagName, delegate);
    }

    private void removeDelegate(@NonNull String tagName, @NonNull InflateDelegate delegate) {
        ArrayMap<String, InflateDelegate> arrayMap = this.mDelegates;
        if (arrayMap != null && arrayMap.get(tagName) == delegate) {
            this.mDelegates.remove(tagName);
        }
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getTintMode(int resId) {
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        if (resourceManagerHooks == null) {
            return null;
        }
        return resourceManagerHooks.getTintModeForDrawableRes(resId);
    }

    /* access modifiers changed from: package-private */
    public synchronized ColorStateList getTintList(@NonNull Context context, @DrawableRes int resId) {
        ColorStateList tint;
        tint = getTintListFromCache(context, resId);
        if (tint == null) {
            tint = this.mHooks == null ? null : this.mHooks.getTintListForDrawableRes(context, resId);
            if (tint != null) {
                addTintListToCache(context, resId, tint);
            }
        }
        return tint;
    }

    private ColorStateList getTintListFromCache(@NonNull Context context, @DrawableRes int resId) {
        SparseArrayCompat<ColorStateList> tints;
        WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.mTintLists;
        if (weakHashMap == null || (tints = weakHashMap.get(context)) == null) {
            return null;
        }
        return (ColorStateList) tints.get(resId);
    }

    private void addTintListToCache(@NonNull Context context, @DrawableRes int resId, @NonNull ColorStateList tintList) {
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap<>();
        }
        SparseArrayCompat<ColorStateList> themeTints = this.mTintLists.get(context);
        if (themeTints == null) {
            themeTints = new SparseArrayCompat<>();
            this.mTintLists.put(context, themeTints);
        }
        themeTints.append(resId, tintList);
    }

    private void checkVectorDrawableSetup(@NonNull Context context) {
        if (!this.mHasCheckedVectorDrawableSetup) {
            this.mHasCheckedVectorDrawableSetup = true;
            Drawable d = getDrawable(context, C0245R.C0246drawable.abc_vector_test);
            if (d == null || !isVectorDrawable(d)) {
                this.mHasCheckedVectorDrawableSetup = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    /* renamed from: android.support.v7.widget.ResourceManagerInternal$InflateDelegate */
    private interface InflateDelegate {
        Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            /* renamed from: android.support.v7.widget.ResourceManagerInternal$ResourceManagerHooks */
    interface ResourceManagerHooks {
        Drawable createDrawableFor(@NonNull ResourceManagerInternal resourceManagerInternal, @NonNull Context context, @DrawableRes int i);

        ColorStateList getTintListForDrawableRes(@NonNull Context context, @DrawableRes int i);

        PorterDuff.Mode getTintModeForDrawableRes(int i);

        boolean tintDrawable(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable);

        boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable);
    }

    /* renamed from: android.support.v7.widget.ResourceManagerInternal$ColorFilterLruCache */
    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int maxSize) {
            super(maxSize);
        }

        private static int generateCacheKey(int color, PorterDuff.Mode mode) {
            return (((1 * 31) + color) * 31) + mode.hashCode();
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter get(int color, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(color, mode)));
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter put(int color, PorterDuff.Mode mode, PorterDuffColorFilter filter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(color, mode)), filter);
        }
    }

    /* renamed from: android.support.v7.widget.ResourceManagerInternal$VdcInflateDelegate */
    private static class VdcInflateDelegate implements InflateDelegate {
        VdcInflateDelegate() {
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: android.support.graphics.drawable.VectorDrawableCompat.createFromXmlInner(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.support.graphics.drawable.VectorDrawableCompat
         arg types: [android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme]
         candidates:
          ClspMth{android.graphics.drawable.Drawable.createFromXmlInner(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.graphics.drawable.Drawable throws java.io.IOException, org.xmlpull.v1.XmlPullParserException}
          android.support.graphics.drawable.VectorDrawableCompat.createFromXmlInner(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.support.graphics.drawable.VectorDrawableCompat */
        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), parser, attrs, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    /* renamed from: android.support.v7.widget.ResourceManagerInternal$AvdcInflateDelegate */
    private static class AvdcInflateDelegate implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), parser, attrs, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    @RequiresApi(11)
    /* renamed from: android.support.v7.widget.ResourceManagerInternal$AsldcInflateDelegate */
    static class AsldcInflateDelegate implements InflateDelegate {
        AsldcInflateDelegate() {
        }

        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) {
            try {
                return AnimatedStateListDrawableCompat.createFromXmlInner(context, context.getResources(), parser, attrs, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }
}
