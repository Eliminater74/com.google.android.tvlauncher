package com.google.android.tvlauncher.instantvideo.preload.impl;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.media.impl.YoutubePlayerImpl;
import com.google.android.tvlauncher.instantvideo.preload.Preloader;
import com.google.android.tvlauncher.instantvideo.preload.PreloaderManager;
import com.google.android.tvlauncher.instantvideo.util.YouTubeUriUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class YoutubePreloaderManager extends PreloaderManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "YoutubePreloaderManager";
    private static final int YOUTUBE_PLAYER_CACHE_SIZE = 2;
    private final YoutubePlayerLruCache mYoutubePlayerCache = new YoutubePlayerLruCache(2);
    /* access modifiers changed from: private */
    public Context mContext;

    public YoutubePreloaderManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public boolean isPreloaded(Uri videoUri) {
        return false;
    }

    public Preloader createPreloader(Uri videoUri) {
        return null;
    }

    public void clearPreloadedData(Uri videoUri) {
    }

    public void bringPreloadedVideoToTopPriority(Uri videoUri) {
    }

    public MediaPlayer getOrCreatePlayer(Uri videoUri) {
        return this.mYoutubePlayerCache.get(videoUri);
    }

    public void recycleMediaPlayer(MediaPlayer mediaPlayer) {
        YoutubePlayerImpl youtubePlayer = (YoutubePlayerImpl) mediaPlayer;
        if (youtubePlayer.getPlaybackState() != 1) {
            youtubePlayer.stop();
        }
        this.mYoutubePlayerCache.put(mediaPlayer.getVideoUri(), youtubePlayer);
    }

    public int canPlayVideo(Uri videoUri) {
        return YouTubeUriUtils.isYouTubeWatchUri(videoUri) ? 100 : 0;
    }

    private class YoutubePlayerLruCache {
        private final ArrayList<Pair<Uri, YoutubePlayerImpl>> mCache;
        private final int mMaxCapacity;
        private YoutubePlayerImpl mReleasedPlayer;

        private YoutubePlayerLruCache(int maxCapacity) {
            this.mCache = new ArrayList<>();
            this.mMaxCapacity = maxCapacity;
        }

        /* access modifiers changed from: private */
        public YoutubePlayerImpl get(Uri key) {
            int index = 0;
            Iterator<Pair<Uri, YoutubePlayerImpl>> it = this.mCache.iterator();
            while (it.hasNext() && !((Uri) it.next().first).equals(key)) {
                index++;
            }
            if (index < this.mCache.size()) {
                YoutubePlayerImpl ret = (YoutubePlayerImpl) this.mCache.get(index).second;
                this.mCache.remove(index);
                return ret;
            }
            YoutubePlayerImpl ret2 = this.mReleasedPlayer;
            if (ret2 != null) {
                this.mReleasedPlayer = null;
            } else {
                ret2 = new YoutubePlayerImpl(YoutubePreloaderManager.this.mContext);
            }
            ret2.setVideoUri(key);
            return ret2;
        }

        /* access modifiers changed from: private */
        public void put(Uri key, YoutubePlayerImpl value) {
            if (this.mMaxCapacity <= 0) {
                this.mReleasedPlayer = value;
                this.mReleasedPlayer.release();
                return;
            }
            if (this.mCache.size() >= this.mMaxCapacity) {
                this.mReleasedPlayer = (YoutubePlayerImpl) this.mCache.get(0).second;
                this.mReleasedPlayer.release();
                this.mCache.remove(0);
            }
            if (this.mCache.size() < this.mMaxCapacity) {
                this.mCache.add(new Pair(key, value));
            }
        }
    }
}
