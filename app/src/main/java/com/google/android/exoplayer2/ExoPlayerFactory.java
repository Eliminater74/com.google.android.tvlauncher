package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;

public final class ExoPlayerFactory {
    @Nullable
    private static BandwidthMeter singletonBandwidthMeter;

    private ExoPlayerFactory() {
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int extensionRendererMode) {
        return newSimpleInstance(context, new DefaultRenderersFactory(context).setExtensionRendererMode(extensionRendererMode), trackSelector, loadControl, drmSessionManager);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int extensionRendererMode, long allowedVideoJoiningTimeMs) {
        return newSimpleInstance(context, new DefaultRenderersFactory(context).setExtensionRendererMode(extensionRendererMode).setAllowedVideoJoiningTimeMs(allowedVideoJoiningTimeMs), trackSelector, loadControl, drmSessionManager);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context) {
        return newSimpleInstance(context, new DefaultTrackSelector());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector) {
        return newSimpleInstance(context, new DefaultRenderersFactory(context), trackSelector);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector) {
        return newSimpleInstance(context, renderersFactory, trackSelector, new DefaultLoadControl());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl) {
        return newSimpleInstance(context, new DefaultRenderersFactory(context), trackSelector, loadControl);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, new DefaultRenderersFactory(context), trackSelector, loadControl, drmSessionManager);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, renderersFactory, trackSelector, new DefaultLoadControl(), drmSessionManager);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, (DrmSessionManager<FrameworkMediaCrypto>) null, Util.getLooper());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, Util.getLooper());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, new AnalyticsCollector.Factory(), Util.getLooper());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, AnalyticsCollector.Factory analyticsCollectorFactory) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, analyticsCollectorFactory, Util.getLooper());
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Looper looper) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, new AnalyticsCollector.Factory(), looper);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, AnalyticsCollector.Factory analyticsCollectorFactory, Looper looper) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, getDefaultBandwidthMeter(context), analyticsCollectorFactory, looper);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter, AnalyticsCollector.Factory analyticsCollectorFactory, Looper looper) {
        return new SimpleExoPlayer(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, analyticsCollectorFactory, looper);
    }

    public static ExoPlayer newInstance(Context context, Renderer[] renderers, TrackSelector trackSelector) {
        return newInstance(context, renderers, trackSelector, new DefaultLoadControl());
    }

    public static ExoPlayer newInstance(Context context, Renderer[] renderers, TrackSelector trackSelector, LoadControl loadControl) {
        return newInstance(context, renderers, trackSelector, loadControl, Util.getLooper());
    }

    public static ExoPlayer newInstance(Context context, Renderer[] renderers, TrackSelector trackSelector, LoadControl loadControl, Looper looper) {
        return newInstance(context, renderers, trackSelector, loadControl, getDefaultBandwidthMeter(context), looper);
    }

    public static ExoPlayer newInstance(Context context, Renderer[] renderers, TrackSelector trackSelector, LoadControl loadControl, BandwidthMeter bandwidthMeter, Looper looper) {
        return new ExoPlayerImpl(renderers, trackSelector, loadControl, bandwidthMeter, Clock.DEFAULT, looper);
    }

    private static synchronized BandwidthMeter getDefaultBandwidthMeter(Context context) {
        BandwidthMeter bandwidthMeter;
        synchronized (ExoPlayerFactory.class) {
            if (singletonBandwidthMeter == null) {
                singletonBandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
            }
            bandwidthMeter = singletonBandwidthMeter;
        }
        return bandwidthMeter;
    }
}
