package com.google.android.tvlauncher.instantvideo.preload.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.p001v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.SurfaceView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.p008ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.media.impl.ExoPlayerImpl;
import com.google.android.tvlauncher.instantvideo.preload.Preloader;
import com.google.android.tvlauncher.instantvideo.preload.PreloaderManager;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class ExoPlayerPreloaderManager extends PreloaderManager {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    /* access modifiers changed from: private */
    public static final byte[] BUFFER = new byte[1000];
    private static final boolean DEBUG = false;
    private static final int MSG_START_PRELOAD = 100;
    private static final int PLAYER_VIEW_POOL_SIZE = 2;
    private static final long PRELOAD_TIMEOUT_MS = 10000;
    private static final String TAG = "ExoPlayerPreloader";
    private static SimpleCache cache;
    /* access modifiers changed from: private */
    public final CacheDataSourceFactory cacheDataSourceFactory;
    /* access modifiers changed from: private */
    public final long cacheSizePerVideo;
    private final Context context;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final HandlerThread handlerThread;
    private final Deque<SimpleExoPlayerView> playerViewPool = new ArrayDeque();
    private final Set<Uri> preloadedVideo = new HashSet();

    public ExoPlayerPreloaderManager(Context context2, long diskCacheSizeInMb, long cacheSizePerVideo2) {
        this.context = context2.getApplicationContext();
        if (cache == null) {
            cache = new SimpleCache(this.context.getCacheDir(), new LeastRecentlyUsedCacheEvictor(diskCacheSizeInMb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
        }
        DefaultBandwidthMeter defaultBandwidthMeter = BANDWIDTH_METER;
        this.cacheDataSourceFactory = new CacheDataSourceFactory(cache, new DefaultDataSourceFactory(context2, defaultBandwidthMeter, new DefaultHttpDataSourceFactory("ExoPlayerPreloaderManager", defaultBandwidthMeter)), 0);
        this.handlerThread = new HandlerThread("ExoPlayerPreloaderManager");
        this.handlerThread.start();
        this.cacheSizePerVideo = cacheSizePerVideo2;
    }

    public boolean isPreloaded(Uri videoUri) {
        return this.preloadedVideo.contains(videoUri);
    }

    public Preloader createPreloader(final Uri videoUri) {
        return new Preloader() {
            PreloadHandler preloadHandler;

            public void startPreload(Preloader.OnPreloadFinishedListener listener) {
                if (this.preloadHandler == null) {
                    ExoPlayerPreloaderManager exoPlayerPreloaderManager = ExoPlayerPreloaderManager.this;
                    this.preloadHandler = new PreloadHandler(exoPlayerPreloaderManager.handlerThread.getLooper(), videoUri, listener);
                    this.preloadHandler.sendEmptyMessage(100);
                }
            }

            public void stopPreload() {
                PreloadHandler preloadHandler2 = this.preloadHandler;
                if (preloadHandler2 != null) {
                    boolean unused = preloadHandler2.stopped = true;
                    this.preloadHandler = null;
                }
            }
        };
    }

    public void clearPreloadedData(Uri videoUri) {
        this.preloadedVideo.remove(videoUri);
    }

    public void bringPreloadedVideoToTopPriority(Uri videoUri) {
    }

    public MediaPlayer getOrCreatePlayer(Uri videoUri) {
        SimpleExoPlayerView playerView = this.playerViewPool.pollFirst();
        if (playerView == null) {
            playerView = new SimpleExoPlayerView(this.context);
            ((SurfaceView) playerView.getVideoSurfaceView()).getHolder().setFormat(-2);
            playerView.setUseController(false);
            playerView.setResizeMode(3);
        }
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
        Context context2 = this.context;
        ExoPlayerImpl player = new ExoPlayerImpl(context2, ExoPlayerFactory.newSimpleInstance(context2, new DefaultRenderersFactory(context2), trackSelector, new DefaultLoadControl()), playerView, this.handler, this.cacheDataSourceFactory);
        player.setVideoUri(videoUri);
        return player;
    }

    public void recycleMediaPlayer(MediaPlayer mediaPlayer) {
        ExoPlayerImpl player = (ExoPlayerImpl) mediaPlayer;
        player.getExoPlayerIntance().release();
        if (this.playerViewPool.size() < 2) {
            this.playerViewPool.addFirst((SimpleExoPlayerView) player.getPlayerView());
        }
    }

    public int canPlayVideo(Uri videoUri) {
        if (videoUri == null) {
            return 0;
        }
        if (videoUri.toString().endsWith(".mp4")) {
            return 100;
        }
        return 10;
    }

    private class PreloadHandler extends Handler {
        /* access modifiers changed from: private */
        public final Preloader.OnPreloadFinishedListener onPreloadFinishedListener;
        /* access modifiers changed from: private */
        public volatile boolean stopped;
        private final Uri videoUri;

        PreloadHandler(Looper looper, Uri videoUri2, Preloader.OnPreloadFinishedListener onPreloadFinishedListener2) {
            super(looper);
            this.videoUri = videoUri2;
            this.onPreloadFinishedListener = onPreloadFinishedListener2;
        }

        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                DataSource dataSource = ExoPlayerPreloaderManager.this.cacheDataSourceFactory.createDataSource();
                try {
                    dataSource.open(new DataSpec(this.videoUri));
                    long readTimeoutStartTimeMs = 0;
                    int totalReadSize = 0;
                    while (true) {
                        if (((long) totalReadSize) < ExoPlayerPreloaderManager.this.cacheSizePerVideo && !this.stopped) {
                            try {
                                int readSize = dataSource.read(ExoPlayerPreloaderManager.BUFFER, 0, ExoPlayerPreloaderManager.BUFFER.length);
                                if (readSize >= 0) {
                                    if (readSize != 0) {
                                        readTimeoutStartTimeMs = 0;
                                    } else if (readTimeoutStartTimeMs == 0) {
                                        readTimeoutStartTimeMs = System.currentTimeMillis();
                                    } else if (System.currentTimeMillis() - readTimeoutStartTimeMs > ExoPlayerPreloaderManager.PRELOAD_TIMEOUT_MS) {
                                        String valueOf = String.valueOf(this.videoUri);
                                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42);
                                        sb.append("Timeout during preloading the video uri (");
                                        sb.append(valueOf);
                                        sb.append(")");
                                        Log.w(ExoPlayerPreloaderManager.TAG, sb.toString());
                                        break;
                                    }
                                    totalReadSize += readSize;
                                }
                            } catch (IOException e) {
                                String valueOf2 = String.valueOf(this.videoUri);
                                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 31);
                                sb2.append("Failed to open the video uri (");
                                sb2.append(valueOf2);
                                sb2.append(")");
                                Log.w(ExoPlayerPreloaderManager.TAG, sb2.toString());
                            }
                        }
                    }
                    try {
                        dataSource.close();
                    } catch (IOException e2) {
                    }
                    notifyPreloadFinished();
                } catch (IOException e3) {
                    notifyPreloadFinished();
                }
            }
        }

        private void notifyPreloadFinished() {
            if (this.onPreloadFinishedListener != null) {
                ExoPlayerPreloaderManager.this.handler.post(new Runnable() {
                    public void run() {
                        PreloadHandler.this.onPreloadFinishedListener.onPreloadFinishedListener();
                    }
                });
            }
        }
    }
}
