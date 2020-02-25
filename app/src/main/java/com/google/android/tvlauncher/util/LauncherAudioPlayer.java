package com.google.android.tvlauncher.util;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class LauncherAudioPlayer {
    private static final String TAG = "LauncherAudioPlayer";
    /* access modifiers changed from: private */
    public CallBacks mCallBacks;
    /* access modifiers changed from: private */
    public String mDataSource;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;

    private void initializeAudio() {
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                LauncherAudioPlayer.this.mMediaPlayer.setOnCompletionListener(null);
                LauncherAudioPlayer.this.stopAndRelease();
                if (LauncherAudioPlayer.this.mCallBacks != null) {
                    LauncherAudioPlayer.this.mCallBacks.onCompleted();
                }
            }
        });
        this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                if (LauncherAudioPlayer.this.mCallBacks != null) {
                    LauncherAudioPlayer.this.mCallBacks.onPrepared();
                }
            }
        });
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String access$200 = LauncherAudioPlayer.this.mDataSource;
                StringBuilder sb = new StringBuilder(String.valueOf(access$200).length() + 65);
                sb.append("Uri ");
                sb.append(access$200);
                sb.append(" cannot be played with what=");
                sb.append(what);
                sb.append(" and extra=");
                sb.append(extra);
                Log.e(LauncherAudioPlayer.TAG, sb.toString());
                LauncherAudioPlayer.this.stopAndRelease();
                if (LauncherAudioPlayer.this.mCallBacks == null) {
                    return true;
                }
                LauncherAudioPlayer.this.mCallBacks.onError();
                return true;
            }
        });
    }

    public String getDataSource() {
        return this.mDataSource;
    }

    public void setDataSource(String dataSource) {
        this.mDataSource = dataSource;
    }

    public void setCallBacks(CallBacks callBacks) {
        this.mCallBacks = callBacks;
    }

    public void prepare() {
        initializeAudio();
        try {
            this.mMediaPlayer.setDataSource(this.mDataSource);
            this.mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.e(TAG, valueOf.length() != 0 ? "[ERROR] ".concat(valueOf) : new String("[ERROR] "));
            stopAndRelease();
            CallBacks callBacks = this.mCallBacks;
            if (callBacks != null) {
                callBacks.onError();
            }
        }
    }

    public void start() {
        this.mMediaPlayer.start();
        CallBacks callBacks = this.mCallBacks;
        if (callBacks != null) {
            callBacks.onStarted();
        }
    }

    public void stopAndRelease() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    public interface CallBacks {
        void onCompleted();

        void onError();

        void onPrepared();

        void onStarted();
    }
}
