package androidx.leanback.media;

import android.content.Context;
import android.support.annotation.CallSuper;
import androidx.leanback.media.PlaybackGlueHost;
import java.util.ArrayList;
import java.util.List;

public abstract class PlaybackGlue {
    private final Context mContext;
    private PlaybackGlueHost mPlaybackGlueHost;
    ArrayList<PlayerCallback> mPlayerCallbacks;

    public static abstract class PlayerCallback {
        public void onPreparedStateChanged(PlaybackGlue glue) {
        }

        public void onPlayStateChanged(PlaybackGlue glue) {
        }

        public void onPlayCompleted(PlaybackGlue glue) {
        }
    }

    public PlaybackGlue(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public boolean isPrepared() {
        return true;
    }

    public void addPlayerCallback(PlayerCallback playerCallback) {
        if (this.mPlayerCallbacks == null) {
            this.mPlayerCallbacks = new ArrayList<>();
        }
        this.mPlayerCallbacks.add(playerCallback);
    }

    public void removePlayerCallback(PlayerCallback callback) {
        ArrayList<PlayerCallback> arrayList = this.mPlayerCallbacks;
        if (arrayList != null) {
            arrayList.remove(callback);
        }
    }

    /* access modifiers changed from: protected */
    public List<PlayerCallback> getPlayerCallbacks() {
        ArrayList<PlayerCallback> arrayList = this.mPlayerCallbacks;
        if (arrayList == null) {
            return null;
        }
        return new ArrayList(arrayList);
    }

    public boolean isPlaying() {
        return false;
    }

    public void play() {
    }

    public void playWhenPrepared() {
        if (isPrepared()) {
            play();
        } else {
            addPlayerCallback(new PlayerCallback() {
                public void onPreparedStateChanged(PlaybackGlue glue) {
                    if (glue.isPrepared()) {
                        PlaybackGlue.this.removePlayerCallback(this);
                        PlaybackGlue.this.play();
                    }
                }
            });
        }
    }

    public void pause() {
    }

    public void next() {
    }

    public void previous() {
    }

    public final void setHost(PlaybackGlueHost host) {
        PlaybackGlueHost playbackGlueHost = this.mPlaybackGlueHost;
        if (playbackGlueHost != host) {
            if (playbackGlueHost != null) {
                playbackGlueHost.attachToGlue(null);
            }
            this.mPlaybackGlueHost = host;
            PlaybackGlueHost playbackGlueHost2 = this.mPlaybackGlueHost;
            if (playbackGlueHost2 != null) {
                playbackGlueHost2.attachToGlue(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onHostStart() {
    }

    /* access modifiers changed from: protected */
    public void onHostStop() {
    }

    /* access modifiers changed from: protected */
    public void onHostResume() {
    }

    /* access modifiers changed from: protected */
    public void onHostPause() {
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onAttachedToHost(PlaybackGlueHost host) {
        this.mPlaybackGlueHost = host;
        this.mPlaybackGlueHost.setHostCallback(new PlaybackGlueHost.HostCallback() {
            public void onHostStart() {
                PlaybackGlue.this.onHostStart();
            }

            public void onHostStop() {
                PlaybackGlue.this.onHostStop();
            }

            public void onHostResume() {
                PlaybackGlue.this.onHostResume();
            }

            public void onHostPause() {
                PlaybackGlue.this.onHostPause();
            }

            public void onHostDestroy() {
                PlaybackGlue.this.setHost(null);
            }
        });
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onDetachedFromHost() {
        PlaybackGlueHost playbackGlueHost = this.mPlaybackGlueHost;
        if (playbackGlueHost != null) {
            playbackGlueHost.setHostCallback(null);
            this.mPlaybackGlueHost = null;
        }
    }

    public PlaybackGlueHost getHost() {
        return this.mPlaybackGlueHost;
    }
}
