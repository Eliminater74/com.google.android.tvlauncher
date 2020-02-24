package com.google.android.tvlauncher.instantvideo.media.impl;

import android.content.Context;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.media.tv.TvView;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.widget.CustomTvView;
import java.util.List;

public class TvPlayerImpl implements MediaPlayer {
    private static final boolean DEBUG = false;
    private static final String PARAM_INPUT = "input";
    private static final String PATH_PREVIEW_PROGRAM = "preview_program";
    private static final String PATH_RECORDED_PROGRAM = "recorded_program";
    private static final String TAG = "TvPlayerImpl";
    private static final int TYPE_LIVE_CONTENT = 1;
    private static final int TYPE_PREVIEW_PROGRAM = 3;
    private static final int TYPE_RECORDED_PROGRAM = 2;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public long mCurrentPosition = 0;
    /* access modifiers changed from: private */
    public boolean mStarted = false;
    /* access modifiers changed from: private */
    public int mState = 1;
    /* access modifiers changed from: private */
    public String mTvInputId;
    /* access modifiers changed from: private */
    public final CustomTvView mTvView;
    /* access modifiers changed from: private */
    public MediaPlayer.VideoCallback mVideoCallback;
    /* access modifiers changed from: private */
    public int mVideoType;
    /* access modifiers changed from: private */
    public Uri mVideoUri;
    private float mVolume = 1.0f;
    /* access modifiers changed from: private */
    public boolean mVolumeUpdated = false;

    public TvPlayerImpl(Context context, CustomTvView tvView) {
        this.mContext = context;
        this.mTvView = tvView;
    }

    public Uri getVideoUri() {
        return this.mVideoUri;
    }

    public void setVideoUri(Uri uri) {
        this.mVideoUri = uri;
        this.mTvInputId = this.mVideoUri.getQueryParameter("input");
        if (TvContract.isChannelUri(uri)) {
            this.mVideoType = 1;
        } else if (isRecordedProgramUri(uri)) {
            this.mVideoType = 2;
        } else if (isPreviewProgramUri(uri)) {
            this.mVideoType = 3;
        }
    }

    public void prepare() {
        this.mStarted = true;
        this.mVolumeUpdated = false;
        this.mState = 2;
        this.mTvView.setCallback(new TvView.TvInputCallback() {
            public void onVideoAvailable(String inputId) {
                if (TvPlayerImpl.this.mVideoCallback != null) {
                    int unused = TvPlayerImpl.this.mState = 3;
                    TvPlayerImpl.this.mVideoCallback.onVideoAvailable();
                }
            }

            public void onVideoUnavailable(String inputId, int reason) {
                if (reason == 0) {
                    TvPlayerImpl.this.stop();
                    if (TvPlayerImpl.this.mVideoCallback != null) {
                        TvPlayerImpl.this.mVideoCallback.onVideoError();
                    }
                }
            }

            public void onConnectionFailed(String inputId) {
                TvPlayerImpl.this.stop();
                if (TvPlayerImpl.this.mVideoCallback != null) {
                    TvPlayerImpl.this.mVideoCallback.onVideoError();
                }
            }

            public void onDisconnected(String inputId) {
                TvPlayerImpl.this.stop();
                if (TvPlayerImpl.this.mVideoCallback != null) {
                    TvPlayerImpl.this.mVideoCallback.onVideoError();
                }
            }
        });
        if (this.mVideoType == 2) {
            this.mTvView.setTimeShiftPositionCallback(new TvView.TimeShiftPositionCallback() {
                public void onTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
                    super.onTimeShiftCurrentPositionChanged(inputId, timeMs);
                    long unused = TvPlayerImpl.this.mCurrentPosition = timeMs;
                }
            });
        }
        if (this.mTvInputId != null) {
            prepareVideo();
            this.mTvView.setStreamVolume(0.0f);
        } else if (this.mVideoType != 3) {
            new AsyncTask<Void, Void, String>() {
                /* access modifiers changed from: protected */
                public String doInBackground(Void... params) {
                    Cursor cursor = TvPlayerImpl.this.mContext.getContentResolver().query(TvPlayerImpl.this.mVideoUri, getProjection(), null, null, null);
                    String result = null;
                    if (cursor != null && cursor.moveToNext()) {
                        result = cursor.getString(0);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return result;
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(String tvInputId) {
                    super.onPostExecute((Object) tvInputId);
                    if (tvInputId != null && TvPlayerImpl.this.mStarted) {
                        String unused = TvPlayerImpl.this.mTvInputId = tvInputId;
                        TvPlayerImpl.this.prepareVideo();
                        if (!TvPlayerImpl.this.mVolumeUpdated) {
                            TvPlayerImpl.this.mTvView.setStreamVolume(0.0f);
                        }
                    }
                }

                private String[] getProjection() {
                    if (TvPlayerImpl.this.mVideoType == 1) {
                        return new String[]{"input_id"};
                    }
                    return new String[]{"input_id"};
                }
            }.execute(new Void[0]);
        } else {
            Log.e(TAG, "TV input id must be given via URI query parameter");
            stop();
            MediaPlayer.VideoCallback videoCallback = this.mVideoCallback;
            if (videoCallback != null) {
                videoCallback.onVideoError();
            }
        }
    }

    public void setDisplaySize(int width, int height) {
    }

    public void seekTo(int positionMs) {
        if (this.mVideoType == 2) {
            this.mTvView.timeShiftSeekTo((long) positionMs);
        }
    }

    public void stop() {
        this.mStarted = false;
        this.mState = 1;
        this.mTvView.reset();
        this.mTvView.setCallback(null);
        this.mTvView.setTimeShiftPositionCallback(null);
        this.mCurrentPosition = 0;
    }

    public int getCurrentPosition() {
        return (int) this.mCurrentPosition;
    }

    public void setVolume(float volume) {
        this.mVolume = volume;
        if (this.mVolumeUpdated) {
            this.mTvView.setStreamVolume(volume);
        }
    }

    public int getPlaybackState() {
        return this.mState;
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        if (playWhenReady) {
            this.mVolumeUpdated = true;
            this.mTvView.setStreamVolume(this.mVolume);
        }
    }

    public void setVideoCallback(MediaPlayer.VideoCallback callback) {
        this.mVideoCallback = callback;
    }

    public View getPlayerView() {
        return this.mTvView;
    }

    public static boolean isRecordedProgramUri(Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_RECORDED_PROGRAM);
    }

    public static boolean isPreviewProgramUri(Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_PREVIEW_PROGRAM);
    }

    private static boolean isTvUri(Uri uri) {
        return uri != null && "content".equals(uri.getScheme()) && TvContractCompat.AUTHORITY.equals(uri.getAuthority());
    }

    private static boolean isTwoSegmentUriStartingWith(Uri uri, String pathSegment) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() == 2 && pathSegment.equals(pathSegments.get(0))) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void prepareVideo() {
        int i = this.mVideoType;
        if (i == 1) {
            this.mTvView.tune(this.mTvInputId, this.mVideoUri);
        } else if (i == 2) {
            this.mTvView.timeShiftPlay(this.mTvInputId, this.mVideoUri);
        } else if (i == 3) {
            this.mTvView.tune(this.mTvInputId, this.mVideoUri);
        }
    }
}
