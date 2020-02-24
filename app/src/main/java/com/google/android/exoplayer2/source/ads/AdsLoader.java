package com.google.android.exoplayer2.source.ads;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.io.IOException;

public interface AdsLoader {

    public interface AdViewProvider {
        View[] getAdOverlayViews();

        ViewGroup getAdViewGroup();
    }

    public interface EventListener {
        void onAdClicked();

        void onAdLoadError(AdsMediaSource.AdLoadException adLoadException, DataSpec dataSpec);

        void onAdPlaybackState(AdPlaybackState adPlaybackState);

        void onAdTapped();
    }

    void handlePrepareError(int i, int i2, IOException iOException);

    void release();

    void setPlayer(@Nullable Player player);

    void setSupportedContentTypes(int... iArr);

    void start(EventListener eventListener, AdViewProvider adViewProvider);

    void stop();
}
