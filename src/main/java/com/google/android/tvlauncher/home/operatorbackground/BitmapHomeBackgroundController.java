package com.google.android.tvlauncher.home.operatorbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.tvlauncher.C1188R;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

public class BitmapHomeBackgroundController {
    private final int TRANSITION_DURATION_MILLIS = ClientAnalytics.LogRequest.LogSource.ANDROID_OTA_VALUE;
    private final Context mContext;
    private final int mOptimalHeight;
    private final int mOptimalWidth;
    /* access modifiers changed from: private */
    public BitmapBackgroundTransitionDrawable mTransitionDrawable;

    public BitmapHomeBackgroundController(String backgroundUri, View backgroundView) {
        this.mContext = backgroundView.getContext();
        this.mTransitionDrawable = new BitmapBackgroundTransitionDrawable();
        backgroundView.setBackground(this.mTransitionDrawable);
        this.mOptimalWidth = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.home_background_image_optimal_width);
        this.mOptimalHeight = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.home_background_image_optimal_height);
        loadBitmap(backgroundUri);
    }

    private void loadBitmap(String backgroundUri) {
        Transformation[] transformationArr = {new CenterCrop(), new AddScrimTransformation(this.mContext), new CropBackgroundBitmapTransformation(this.mContext)};
        Glide.with(this.mContext).asBitmap().load(backgroundUri).apply((BaseRequestOptions<?>) ((RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().override(this.mOptimalWidth, this.mOptimalHeight)).downsample(DownsampleStrategy.AT_LEAST)).transform(new MultiTransformation(transformationArr))).skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.RESOURCE))).into(new SimpleTarget<Bitmap>() {
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                BitmapHomeBackgroundController.this.mTransitionDrawable.animateFadeIn(resource, 600);
            }
        });
    }
}
