package com.google.android.tvlauncher.instantvideo.preload;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.LruCache;

import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;

public class InstantVideoPreloadManager {
    private static final boolean DEBUG = false;
    private static final int MAX_CONCURRENT_VIDEO_PRELOAD_COUNT = 10;
    private static final String TAG = "InstantVideoPreloadMgr";
    private static InstantVideoPreloadManager sInstance;
    /* access modifiers changed from: private */
    public final LruCache<Uri, Preloader> mVideoPreloaderCache = new LruCache<Uri, Preloader>(10) {
        /* access modifiers changed from: protected */
        public void entryRemoved(boolean evicted, Uri key, Preloader oldValue, Preloader newValue) {
            if (newValue != null) {
                InstantVideoPreloadManager.this.onEntryRemovedFromCache(key, oldValue);
            }
        }
    };
    private final Context mAppContext;
    private PreloaderManagerImpl mPreloaderManager;

    @VisibleForTesting
    InstantVideoPreloadManager(Context context) {
        this.mAppContext = context.getApplicationContext();
        this.mPreloaderManager = new PreloaderManagerImpl();
    }

    public static synchronized InstantVideoPreloadManager getInstance(Context context) {
        InstantVideoPreloadManager instantVideoPreloadManager;
        synchronized (InstantVideoPreloadManager.class) {
            if (sInstance == null) {
                sInstance = new InstantVideoPreloadManager(context);
            }
            instantVideoPreloadManager = sInstance;
        }
        return instantVideoPreloadManager;
    }

    public void preload(@NonNull Uri videoUri) {
        if (videoUri == null) {
            throw new IllegalArgumentException("The video URI shouldn't be null.");
        } else if (this.mPreloaderManager.isPreloaded(videoUri)) {
            this.mPreloaderManager.bringPreloadedVideoToTopPriority(videoUri);
        } else {
            Preloader preloader = this.mVideoPreloaderCache.get(videoUri);
            if (preloader == null) {
                Preloader preloader2 = startPreloading(videoUri);
                if (preloader2 != null) {
                    this.mVideoPreloaderCache.put(videoUri, preloader2);
                    return;
                }
                return;
            }
            this.mVideoPreloaderCache.put(videoUri, preloader);
            this.mPreloaderManager.bringPreloadedVideoToTopPriority(videoUri);
        }
    }

    /* access modifiers changed from: private */
    public void onEntryRemovedFromCache(Uri videoUri, Preloader preloader) {
        preloader.stopPreload();
    }

    public void clearCache() {
    }

    public void registerPreloaderManager(PreloaderManager preloaderManager) {
        this.mPreloaderManager.registerPreloaderManager(preloaderManager);
    }

    public void unregisterPreloaderManager(PreloaderManager preloaderManager) {
        this.mPreloaderManager.unregisterPreloaderManager(preloaderManager);
    }

    public MediaPlayer getOrCreatePlayer(Uri videoUri) {
        return this.mPreloaderManager.getOrCreatePlayer(videoUri);
    }

    public void recyclePlayer(MediaPlayer player, Uri videoUri) {
        this.mPreloaderManager.recycleMediaPlayer(player);
    }

    private Preloader startPreloading(final Uri videoUri) {
        Preloader preloader = this.mPreloaderManager.createPreloader(videoUri);
        if (preloader == null) {
            return null;
        }
        preloader.startPreload(new Preloader.OnPreloadFinishedListener() {
            public void onPreloadFinishedListener() {
                InstantVideoPreloadManager.this.mVideoPreloaderCache.remove(videoUri);
            }
        });
        return preloader;
    }
}
