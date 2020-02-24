package com.google.android.tvlauncher.home.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.tvlauncher.trace.AppTrace;

public abstract class ImageViewTargetWithTrace<T> extends ImageViewTarget<T> {
    private final String mTraceSection;
    private AppTrace.TraceTag mTraceTag;

    public ImageViewTargetWithTrace(ImageView view, String traceSection) {
        super(view);
        this.mTraceSection = traceSection;
    }

    public void setRequest(@Nullable Request request) {
        this.mTraceTag = AppTrace.beginAsyncSection(this.mTraceSection);
        super.setRequest(request);
    }

    public void onResourceReady(T resource, @Nullable Transition<? super T> transition) {
        super.onResourceReady(resource, transition);
        AppTrace.TraceTag traceTag = this.mTraceTag;
        if (traceTag != null) {
            AppTrace.endAsyncSection(traceTag);
            this.mTraceTag = null;
        }
    }

    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        AppTrace.TraceTag traceTag = this.mTraceTag;
        if (traceTag != null) {
            AppTrace.endAsyncSection(traceTag);
            this.mTraceTag = null;
        }
    }
}
