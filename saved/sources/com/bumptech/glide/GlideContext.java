package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import java.util.List;
import java.util.Map;

public class GlideContext extends ContextWrapper {
    @VisibleForTesting
    static final TransitionOptions<?, ?> DEFAULT_TRANSITION_OPTIONS = new GenericTransitionOptions();
    private final ArrayPool arrayPool;
    private final List<RequestListener<Object>> defaultRequestListeners;
    @Nullable
    @GuardedBy("this")
    private RequestOptions defaultRequestOptions;
    private final Glide.RequestOptionsFactory defaultRequestOptionsFactory;
    private final Map<Class<?>, TransitionOptions<?, ?>> defaultTransitionOptions;
    private final Engine engine;
    private final ImageViewTargetFactory imageViewTargetFactory;
    private final boolean isLoggingRequestOriginsEnabled;
    private final int logLevel;
    private final Registry registry;

    public GlideContext(@NonNull Context context, @NonNull ArrayPool arrayPool2, @NonNull Registry registry2, @NonNull ImageViewTargetFactory imageViewTargetFactory2, @NonNull Glide.RequestOptionsFactory defaultRequestOptionsFactory2, @NonNull Map<Class<?>, TransitionOptions<?, ?>> defaultTransitionOptions2, @NonNull List<RequestListener<Object>> defaultRequestListeners2, @NonNull Engine engine2, boolean isLoggingRequestOriginsEnabled2, int logLevel2) {
        super(context.getApplicationContext());
        this.arrayPool = arrayPool2;
        this.registry = registry2;
        this.imageViewTargetFactory = imageViewTargetFactory2;
        this.defaultRequestOptionsFactory = defaultRequestOptionsFactory2;
        this.defaultRequestListeners = defaultRequestListeners2;
        this.defaultTransitionOptions = defaultTransitionOptions2;
        this.engine = engine2;
        this.isLoggingRequestOriginsEnabled = isLoggingRequestOriginsEnabled2;
        this.logLevel = logLevel2;
    }

    public List<RequestListener<Object>> getDefaultRequestListeners() {
        return this.defaultRequestListeners;
    }

    public synchronized RequestOptions getDefaultRequestOptions() {
        if (this.defaultRequestOptions == null) {
            this.defaultRequestOptions = (RequestOptions) this.defaultRequestOptionsFactory.build().lock();
        }
        return this.defaultRequestOptions;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.bumptech.glide.TransitionOptions<?, T>} */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.bumptech.glide.TransitionOptions<?, T> getDefaultTransitionOptions(@android.support.annotation.NonNull java.lang.Class<T> r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.TransitionOptions<?, ?>> r0 = r4.defaultTransitionOptions
            java.lang.Object r0 = r0.get(r5)
            com.bumptech.glide.TransitionOptions r0 = (com.bumptech.glide.TransitionOptions) r0
            if (r0 != 0) goto L_0x0034
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.TransitionOptions<?, ?>> r1 = r4.defaultTransitionOptions
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0014:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0034
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r3 = r3.isAssignableFrom(r5)
            if (r3 == 0) goto L_0x0033
            java.lang.Object r3 = r2.getValue()
            r0 = r3
            com.bumptech.glide.TransitionOptions r0 = (com.bumptech.glide.TransitionOptions) r0
        L_0x0033:
            goto L_0x0014
        L_0x0034:
            if (r0 != 0) goto L_0x0038
            com.bumptech.glide.TransitionOptions<?, ?> r0 = com.bumptech.glide.GlideContext.DEFAULT_TRANSITION_OPTIONS
        L_0x0038:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.GlideContext.getDefaultTransitionOptions(java.lang.Class):com.bumptech.glide.TransitionOptions");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Class<X>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <X> com.bumptech.glide.request.target.ViewTarget<android.widget.ImageView, X> buildImageViewTarget(@android.support.annotation.NonNull android.widget.ImageView r2, @android.support.annotation.NonNull java.lang.Class<X> r3) {
        /*
            r1 = this;
            com.bumptech.glide.request.target.ImageViewTargetFactory r0 = r1.imageViewTargetFactory
            com.bumptech.glide.request.target.ViewTarget r0 = r0.buildTarget(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.GlideContext.buildImageViewTarget(android.widget.ImageView, java.lang.Class):com.bumptech.glide.request.target.ViewTarget");
    }

    @NonNull
    public Engine getEngine() {
        return this.engine;
    }

    @NonNull
    public Registry getRegistry() {
        return this.registry;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    @NonNull
    public ArrayPool getArrayPool() {
        return this.arrayPool;
    }

    public boolean isLoggingRequestOriginsEnabled() {
        return this.isLoggingRequestOriginsEnabled;
    }
}
