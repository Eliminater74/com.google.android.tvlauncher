package com.google.android.tvlauncher.instantvideo.media.impl;

import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerService;
import java.util.HashMap;
import java.util.Map;

public class RemoteYoutubePlayerService extends Service {
    private static final String TAG = "RemoteYTPlayerService";
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    private int mNextSessionToken;
    private final Map<String, Session> mSessions = new HashMap();
    private IRemoteYoutubePlayerService.Stub mStub = new IRemoteYoutubePlayerService.Stub() {
        public void createSession(Surface surface, int width, int height, IRemoteYoutubePlayerClient client) {
            final Surface surface2 = surface;
            final int i = width;
            final int i2 = height;
            final IRemoteYoutubePlayerClient iRemoteYoutubePlayerClient = client;
            RemoteYoutubePlayerService.this.mHandler.post(new Runnable() {
                public void run() {
                    String unused = RemoteYoutubePlayerService.this.doCreateSession(surface2, i, i2, iRemoteYoutubePlayerClient);
                }
            });
        }

        public void destroySession(final String sessionToken) throws RemoteException {
            RemoteYoutubePlayerService.this.mHandler.post(new Runnable() {
                public void run() {
                    RemoteYoutubePlayerService.this.doDestroySession(sessionToken);
                }
            });
        }

        public void start(final String sessionToken, final Uri youtubeUri, float volume) throws RemoteException {
            RemoteYoutubePlayerService.this.mHandler.post(new Runnable() {
                public void run() {
                    RemoteYoutubePlayerService.this.doStart(sessionToken, youtubeUri);
                }
            });
        }

        public void setVolume(final String sessionToken, final float volume) throws RemoteException {
            RemoteYoutubePlayerService.this.mHandler.post(new Runnable() {
                public void run() {
                    RemoteYoutubePlayerService.this.doSetVolume(sessionToken, volume);
                }
            });
        }

        public void setSize(final String sessionToken, final int width, final int height) throws RemoteException {
            RemoteYoutubePlayerService.this.mHandler.post(new Runnable() {
                public void run() {
                    RemoteYoutubePlayerService.this.doSetSize(sessionToken, width, height);
                }
            });
        }
    };

    public IBinder onBind(Intent intent) {
        return this.mStub;
    }

    /* access modifiers changed from: private */
    @MainThread
    public String doCreateSession(Surface surface, int width, int height, IRemoteYoutubePlayerClient client) {
        Session session = new Session(surface, width, height, client);
        int i = this.mNextSessionToken;
        this.mNextSessionToken = i + 1;
        String sessionToken = String.valueOf(i);
        this.mSessions.put(sessionToken, session);
        try {
            client.onSessionCreated(sessionToken);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot start session", e);
            this.mSessions.remove(sessionToken);
        }
        return sessionToken;
    }

    /* access modifiers changed from: private */
    @MainThread
    public void doDestroySession(String sessionToken) {
        Session session = this.mSessions.get(sessionToken);
        if (session != null) {
            session.stop();
            this.mSessions.remove(sessionToken);
        }
    }

    /* access modifiers changed from: private */
    @MainThread
    public void doStart(String sessionToken, Uri youtubeUri) {
        Session session = this.mSessions.get(sessionToken);
        if (session != null) {
            session.start(youtubeUri);
        }
    }

    /* access modifiers changed from: private */
    @MainThread
    public void doSetVolume(String sessionToken, float volume) {
        Session session = this.mSessions.get(sessionToken);
        if (session != null) {
            session.setVolume(volume);
        }
    }

    /* access modifiers changed from: private */
    @MainThread
    public void doSetSize(String sessionToken, int width, int height) {
        Session session = this.mSessions.get(sessionToken);
        if (session != null) {
            session.setSize(width, height);
        }
    }

    private class Session {
        /* access modifiers changed from: private */
        public final IRemoteYoutubePlayerClient mClient;
        private final int mHeight;
        private YoutubePlayerImpl mPlayer;
        private final Surface mSurface;
        private VirtualDisplay mVirtualDisplay;
        private final int mWidth;
        private WindowManager mWindowManager;

        Session(Surface surface, int width, int height, IRemoteYoutubePlayerClient client) {
            this.mSurface = surface;
            this.mWidth = width;
            this.mHeight = height;
            this.mClient = client;
        }

        /* access modifiers changed from: package-private */
        public void start(Uri youtubeUri) {
            if (this.mPlayer == null) {
                createPlayer();
            }
            this.mPlayer.setVideoUri(youtubeUri);
            this.mPlayer.setDisplaySize(this.mWidth, this.mHeight);
            this.mPlayer.prepare();
            this.mPlayer.setPlayWhenReady(true);
            this.mPlayer.setVideoCallback(new MediaPlayer.VideoCallback() {
                public void onVideoAvailable() {
                    try {
                        Session.this.mClient.onVideoAvailable();
                    } catch (RemoteException e) {
                        Log.e(RemoteYoutubePlayerService.TAG, "Video callback failed", e);
                    }
                }

                public void onVideoError() {
                    try {
                        Session.this.mClient.onVideoError();
                    } catch (RemoteException e) {
                        Log.e(RemoteYoutubePlayerService.TAG, "Video callback failed", e);
                    }
                }

                public void onVideoEnded() {
                    try {
                        Session.this.mClient.onVideoEnded();
                    } catch (RemoteException e) {
                        Log.e(RemoteYoutubePlayerService.TAG, "Video callback failed", e);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            YoutubePlayerImpl youtubePlayerImpl = this.mPlayer;
            if (youtubePlayerImpl != null) {
                youtubePlayerImpl.stop();
                this.mWindowManager.removeViewImmediate(this.mPlayer.getPlayerView());
                this.mVirtualDisplay.release();
            }
        }

        /* access modifiers changed from: package-private */
        public void setVolume(float volume) {
            YoutubePlayerImpl youtubePlayerImpl = this.mPlayer;
            if (youtubePlayerImpl != null) {
                youtubePlayerImpl.setVolume(volume);
            }
        }

        /* access modifiers changed from: package-private */
        public void setSize(int width, int height) {
            YoutubePlayerImpl youtubePlayerImpl = this.mPlayer;
            if (youtubePlayerImpl != null) {
                youtubePlayerImpl.setDisplaySize(width, height);
            }
        }

        private void createPlayer() {
            this.mPlayer = new YoutubePlayerImpl(RemoteYoutubePlayerService.this);
            this.mVirtualDisplay = ((DisplayManager) RemoteYoutubePlayerService.this.getSystemService(DisplayManager.class)).createVirtualDisplay("youtube", this.mWidth, this.mHeight, getDefaultDisplayDensity(), this.mSurface, 8);
            this.mWindowManager = (WindowManager) RemoteYoutubePlayerService.this.createDisplayContext(this.mVirtualDisplay.getDisplay()).getSystemService(WindowManager.class);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = this.mWidth;
            params.height = this.mHeight;
            params.type = 2030;
            params.flags = 8;
            this.mWindowManager.addView(this.mPlayer.getPlayerView(), params);
        }

        private int getDefaultDisplayDensity() {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) RemoteYoutubePlayerService.this.getSystemService(WindowManager.class)).getDefaultDisplay().getMetrics(metrics);
            return metrics.densityDpi;
        }
    }
}
