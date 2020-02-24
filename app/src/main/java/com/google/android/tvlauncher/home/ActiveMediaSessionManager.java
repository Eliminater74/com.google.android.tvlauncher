package com.google.android.tvlauncher.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import java.util.List;

public class ActiveMediaSessionManager implements MediaSessionManager.OnActiveSessionsChangedListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "ActiveMediaSessionManager";
    @SuppressLint({"StaticFieldLeak"})
    private static ActiveMediaSessionManager sMediaSessionListener;
    private MediaController mActiveMediaController;
    private Context mContext;

    public static ActiveMediaSessionManager getInstance(Context context) {
        if (sMediaSessionListener == null) {
            sMediaSessionListener = new ActiveMediaSessionManager(context);
        }
        return sMediaSessionListener;
    }

    private ActiveMediaSessionManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void onActiveSessionsChanged(List<MediaController> controllers) {
        this.mActiveMediaController = controllers.size() == 0 ? null : controllers.get(0);
    }

    public void start() {
        MediaSessionManager manager = (MediaSessionManager) this.mContext.getSystemService("media_session");
        onActiveSessionsChanged(manager.getActiveSessions(null));
        manager.addOnActiveSessionsChangedListener(this, null);
    }

    public void stop() {
        ((MediaSessionManager) this.mContext.getSystemService("media_session")).removeOnActiveSessionsChangedListener(this);
    }

    /* access modifiers changed from: package-private */
    public boolean hasActiveMediaSession() {
        PlaybackState state;
        MediaController mediaController = this.mActiveMediaController;
        if (mediaController == null || (state = mediaController.getPlaybackState()) == null || state.getState() != 3) {
            return false;
        }
        return true;
    }
}
