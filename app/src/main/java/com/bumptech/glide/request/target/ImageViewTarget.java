package com.bumptech.glide.request.target;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.transition.Transition;

public abstract class ImageViewTarget<Z> extends ViewTarget<ImageView, Z> implements Transition.ViewAdapter {
    @Nullable
    private Animatable animatable;

    public ImageViewTarget(ImageView view) {
        super(view);
    }

    @Deprecated
    public ImageViewTarget(ImageView view, boolean waitForLayout) {
        super(view, waitForLayout);
    }

    /* access modifiers changed from: protected */
    public abstract void setResource(@Nullable Object obj);

    @Nullable
    public Drawable getCurrentDrawable() {
        return ((ImageView) this.view).getDrawable();
    }

    public void setDrawable(Drawable drawable) {
        ((ImageView) this.view).setImageDrawable(drawable);
    }

    public void onLoadStarted(@Nullable Drawable placeholder) {
        super.onLoadStarted(placeholder);
        setResourceInternal(null);
        setDrawable(placeholder);
    }

    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        setResourceInternal(null);
        setDrawable(errorDrawable);
    }

    public void onLoadCleared(@Nullable Drawable placeholder) {
        super.onLoadCleared(placeholder);
        Animatable animatable2 = this.animatable;
        if (animatable2 != null) {
            animatable2.stop();
        }
        setResourceInternal(null);
        setDrawable(placeholder);
    }

    public void onResourceReady(@NonNull Z resource, @Nullable Transition<? super Z> transition) {
        if (transition == null || !transition.transition(resource, this)) {
            setResourceInternal(resource);
        } else {
            maybeUpdateAnimatable(resource);
        }
    }

    public void onStart() {
        Animatable animatable2 = this.animatable;
        if (animatable2 != null) {
            animatable2.start();
        }
    }

    public void onStop() {
        Animatable animatable2 = this.animatable;
        if (animatable2 != null) {
            animatable2.stop();
        }
    }

    private void setResourceInternal(@Nullable Z resource) {
        setResource(resource);
        maybeUpdateAnimatable(resource);
    }

    private void maybeUpdateAnimatable(@Nullable Z resource) {
        if (resource instanceof Animatable) {
            this.animatable = (Animatable) resource;
            this.animatable.start();
            return;
        }
        this.animatable = null;
    }
}
