package com.google.android.tvlauncher.instantvideo.media.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player$EventListener$$CC;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.p008ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;

public class ExoPlayerImpl implements MediaPlayer {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final int TYPE_DASH = 0;
    private static final int TYPE_HLS = 2;
    private static final int TYPE_OTHER = 3;
    private static final int TYPE_SS = 1;
    private final DataSource.Factory cacheDataFactory;
    private final Context context;
    private final Handler handler;
    private final SimpleExoPlayer player;
    private final SimpleExoPlayerView playerView;
    /* access modifiers changed from: private */
    public MediaPlayer.VideoCallback videoCallback;
    private Uri videoUri;

    public ExoPlayerImpl(Context context2, SimpleExoPlayer player2, SimpleExoPlayerView playerView2, Handler handler2, DataSource.Factory cacheDataFactory2) {
        this.context = context2;
        this.player = player2;
        this.handler = handler2;
        this.cacheDataFactory = cacheDataFactory2;
        this.playerView = playerView2;
    }

    public int getPlaybackState() {
        return this.player.getPlaybackState();
    }

    public void setVideoUri(Uri uri) {
        this.videoUri = uri;
    }

    public Uri getVideoUri() {
        return this.videoUri;
    }

    public void prepare() {
        this.player.prepare(buildMediaSource(this.videoUri, null));
        this.playerView.setPlayer(this.player);
        this.player.setVideoDebugListener(new VideoRendererEventListener() {
            public void onVideoEnabled(DecoderCounters counters) {
            }

            public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            }

            public void onVideoInputFormatChanged(Format format) {
            }

            public void onDroppedFrames(int count, long elapsedMs) {
            }

            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            }

            public void onRenderedFirstFrame(Surface surface) {
                if (ExoPlayerImpl.this.videoCallback != null) {
                    ExoPlayerImpl.this.videoCallback.onVideoAvailable();
                }
            }

            public void onVideoDisabled(DecoderCounters counters) {
            }
        });
        this.player.addListener(new Player.EventListener() {
            public void onPositionDiscontinuity(int i) {
                Player$EventListener$$CC.onPositionDiscontinuity$$dflt$$(this, i);
            }

            public void onSeekProcessed() {
                Player$EventListener$$CC.onSeekProcessed$$dflt$$(this);
            }

            public void onShuffleModeEnabledChanged(boolean z) {
                Player$EventListener$$CC.onShuffleModeEnabledChanged$$dflt$$(this, z);
            }

            public void onTimelineChanged(Timeline timeline, Object obj, int i) {
                Player$EventListener$$CC.onTimelineChanged$$dflt$$(this, timeline, obj, i);
            }

            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            public void onLoadingChanged(boolean isLoading) {
            }

            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == 4 && ExoPlayerImpl.this.videoCallback != null) {
                    ExoPlayerImpl.this.videoCallback.onVideoEnded();
                }
            }

            public void onRepeatModeChanged(int repeatMode) {
            }

            public void onPlayerError(ExoPlaybackException error) {
                if (ExoPlayerImpl.this.videoCallback != null) {
                    ExoPlayerImpl.this.videoCallback.onVideoError();
                }
            }

            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
        });
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.player.setPlayWhenReady(playWhenReady);
    }

    public void stop() {
        this.player.stop();
        this.player.setVideoDebugListener(null);
        this.playerView.setPlayer(null);
    }

    public void seekTo(int positionMs) {
        this.player.seekTo((long) positionMs);
    }

    public void setDisplaySize(int width, int height) {
    }

    public int getCurrentPosition() {
        return (int) this.player.getCurrentPosition();
    }

    public void setVolume(float volume) {
        this.player.setVolume(volume);
    }

    public View getPlayerView() {
        return this.playerView;
    }

    public void setVideoCallback(MediaPlayer.VideoCallback callback) {
        this.videoCallback = callback;
    }

    public SimpleExoPlayer getExoPlayerIntance() {
        return this.player;
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        String str;
        if (!TextUtils.isEmpty(overrideExtension)) {
            String valueOf = String.valueOf(overrideExtension);
            str = valueOf.length() != 0 ? ".".concat(valueOf) : new String(".");
        } else {
            str = uri.getLastPathSegment();
        }
        int type = inferContentType(str);
        if (type == 0) {
            return new DashMediaSource(uri, buildDataSourceFactory(this.context, false), new DefaultDashChunkSource.Factory(this.cacheDataFactory), this.handler, (MediaSourceEventListener) null);
        } else if (type == 1) {
            return new SsMediaSource(uri, buildDataSourceFactory(this.context, false), new DefaultSsChunkSource.Factory(this.cacheDataFactory), this.handler, (MediaSourceEventListener) null);
        } else if (type == 2) {
            return new HlsMediaSource.Factory(this.cacheDataFactory).createMediaSource(uri);
        } else {
            if (type == 3) {
                return new ExtractorMediaSource(uri, this.cacheDataFactory, new DefaultExtractorsFactory(), this.handler, null);
            }
            StringBuilder sb = new StringBuilder(29);
            sb.append("Unsupported type: ");
            sb.append(type);
            throw new IllegalStateException(sb.toString());
        }
    }

    private static int inferContentType(String fileName) {
        if (fileName == null) {
            return 3;
        }
        if (fileName.endsWith(".mpd")) {
            return 0;
        }
        if (fileName.endsWith(".ism") || fileName.endsWith(".isml")) {
            return 1;
        }
        if (fileName.endsWith(".m3u8")) {
            return 2;
        }
        return 3;
    }

    private static DefaultDataSourceFactory buildDataSourceFactory(Context context2, boolean useBandwidthMeter) {
        DefaultBandwidthMeter bandwidthMeter = useBandwidthMeter ? BANDWIDTH_METER : null;
        return new DefaultDataSourceFactory(context2, bandwidthMeter, new DefaultHttpDataSourceFactory("ExoPlayerImpl", bandwidthMeter));
    }
}
