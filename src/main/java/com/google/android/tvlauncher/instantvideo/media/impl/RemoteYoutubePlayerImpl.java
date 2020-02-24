package com.google.android.tvlauncher.instantvideo.media.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerClient;
import com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerService;

public class RemoteYoutubePlayerImpl implements MediaPlayer {
    private static final String TAG = "RemoteYoutubePlayerImpl";
    private IRemoteYoutubePlayerClient.Stub mClient = new IRemoteYoutubePlayerClient.Stub() {
        public void onSessionCreated(String sessionToken) throws RemoteException {
            String unused = RemoteYoutubePlayerImpl.this.mSessionToken = sessionToken;
            RemoteYoutubePlayerImpl.this.mView.post(new Runnable() {
                public void run() {
                    RemoteYoutubePlayerImpl.this.start();
                }
            });
        }

        public void onVideoAvailable() throws RemoteException {
            RemoteYoutubePlayerImpl.this.mView.post(new Runnable() {
                public void run() {
                    int unused = RemoteYoutubePlayerImpl.this.mPlaybackState = 3;
                    if (RemoteYoutubePlayerImpl.this.mListener != null) {
                        RemoteYoutubePlayerImpl.this.mListener.onVideoAvailable();
                    }
                }
            });
        }

        public void onVideoError() throws RemoteException {
            RemoteYoutubePlayerImpl.this.mView.post(new Runnable() {
                public void run() {
                    int unused = RemoteYoutubePlayerImpl.this.mPlaybackState = 4;
                    if (RemoteYoutubePlayerImpl.this.mListener != null) {
                        RemoteYoutubePlayerImpl.this.mListener.onVideoError();
                    }
                }
            });
        }

        public void onVideoEnded() throws RemoteException {
            RemoteYoutubePlayerImpl.this.mView.post(new Runnable() {
                public void run() {
                    int unused = RemoteYoutubePlayerImpl.this.mPlaybackState = 4;
                    if (RemoteYoutubePlayerImpl.this.mListener != null) {
                        RemoteYoutubePlayerImpl.this.mListener.onVideoEnded();
                    }
                }
            });
        }
    };
    private final Context mContext;
    /* access modifiers changed from: private */
    public MediaPlayer.VideoCallback mListener;
    /* access modifiers changed from: private */
    public int mPlaybackState = 2;
    /* access modifiers changed from: private */
    public IRemoteYoutubePlayerService mService;
    private ServiceConnection mServiceConnection;
    /* access modifiers changed from: private */
    public String mSessionToken;
    /* access modifiers changed from: private */
    public Surface mSurface;
    /* access modifiers changed from: private */
    public final RemoteYoutubeView mView;
    private float mVolume;
    private Uri mYoutubeUri;

    public RemoteYoutubePlayerImpl(Context context) {
        this.mView = new RemoteYoutubeView(context);
        this.mContext = context;
        this.mView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                Surface unused = RemoteYoutubePlayerImpl.this.mSurface = holder.getSurface();
                RemoteYoutubePlayerImpl.this.connectIfConfigured();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                Surface unused = RemoteYoutubePlayerImpl.this.mSurface = null;
                RemoteYoutubePlayerImpl.this.stop();
            }
        });
    }

    public int getPlaybackState() {
        return this.mPlaybackState;
    }

    public void setVideoUri(Uri uri) {
        this.mYoutubeUri = uri;
        connectIfConfigured();
    }

    public Uri getVideoUri() {
        return this.mYoutubeUri;
    }

    /* access modifiers changed from: private */
    public void start() {
        String str;
        Uri uri;
        IRemoteYoutubePlayerService iRemoteYoutubePlayerService = this.mService;
        if (iRemoteYoutubePlayerService != null && (str = this.mSessionToken) != null && (uri = this.mYoutubeUri) != null) {
            try {
                iRemoteYoutubePlayerService.start(str, uri, this.mVolume);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot start session", e);
            }
        }
    }

    public void stop() {
        String str;
        this.mView.setVisibility(8);
        if (this.mServiceConnection != null) {
            IRemoteYoutubePlayerService iRemoteYoutubePlayerService = this.mService;
            if (!(iRemoteYoutubePlayerService == null || (str = this.mSessionToken) == null)) {
                try {
                    iRemoteYoutubePlayerService.destroySession(str);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot destroy session", e);
                }
                this.mService = null;
            }
            this.mContext.unbindService(this.mServiceConnection);
            this.mServiceConnection = null;
            this.mSessionToken = null;
        }
    }

    public void setDisplaySize(int width, int height) {
        String str;
        IRemoteYoutubePlayerService iRemoteYoutubePlayerService = this.mService;
        if (iRemoteYoutubePlayerService != null && (str = this.mSessionToken) != null) {
            try {
                iRemoteYoutubePlayerService.setSize(str, width, height);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot set size", e);
            }
        }
    }

    public void setVolume(float volume) {
        String str;
        this.mVolume = volume;
        IRemoteYoutubePlayerService iRemoteYoutubePlayerService = this.mService;
        if (iRemoteYoutubePlayerService != null && (str = this.mSessionToken) != null) {
            try {
                iRemoteYoutubePlayerService.setVolume(str, volume);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot set volume", e);
            }
        }
    }

    public View getPlayerView() {
        return this.mView;
    }

    public void setVideoCallback(MediaPlayer.VideoCallback listener) {
        this.mListener = listener;
        if (listener != null) {
            connectIfConfigured();
        }
    }

    public void prepare() {
    }

    public void setPlayWhenReady(boolean playWhenReady) {
    }

    public int getCurrentPosition() {
        return 0;
    }

    public void seekTo(int positionMs) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: private */
    public void connectIfConfigured() {
        if (this.mSurface != null && this.mListener != null && this.mYoutubeUri != null) {
            this.mServiceConnection = new ServiceConnection() {
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    IRemoteYoutubePlayerService unused = RemoteYoutubePlayerImpl.this.mService = IRemoteYoutubePlayerService.Stub.asInterface(binder);
                    RemoteYoutubePlayerImpl.this.onConnected();
                }

                public void onServiceDisconnected(ComponentName name) {
                    IRemoteYoutubePlayerService unused = RemoteYoutubePlayerImpl.this.mService = null;
                    if (RemoteYoutubePlayerImpl.this.mPlaybackState != 4 && RemoteYoutubePlayerImpl.this.mListener != null) {
                        RemoteYoutubePlayerImpl.this.mListener.onVideoEnded();
                    }
                }
            };
            this.mContext.bindService(new Intent(this.mContext, RemoteYoutubePlayerService.class), this.mServiceConnection, 1);
        }
    }

    /* access modifiers changed from: private */
    public void onConnected() {
        try {
            if (this.mSurface != null && this.mService != null) {
                this.mService.createSession(this.mSurface, this.mView.getWidth(), this.mView.getHeight(), this.mClient);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot create session", e);
        }
    }

    public static class RemoteYoutubeView extends SurfaceView {
        public RemoteYoutubeView(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            getHolder().setFixedSize(right - left, top - bottom);
        }
    }
}
