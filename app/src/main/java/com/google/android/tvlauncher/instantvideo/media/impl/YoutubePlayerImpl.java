package com.google.android.tvlauncher.instantvideo.media.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.WorkerThread;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.util.YouTubeUriUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class YoutubePlayerImpl implements MediaPlayer {
    private static final byte[] BUFFER = new byte[1024];
    private static final boolean DEBUG = false;
    private static final int DEFAULT_DISPLAY_HEIGHT = 524;
    private static final int DEFAULT_DISPLAY_WIDTH = 932;
    private static final String JAVA_SCRIPT_BRIDGE_TAG = "Android";
    private static final String TAG = "YoutubePlayerImpl";
    private static final String TAG_DISPLAY_HEIGHT = "<display_height>";
    private static final String TAG_DISPLAY_WIDTH = "<display_width>";
    private static final String TAG_VIDEO_ID = "<video_id>";
    private static final int WEBVIEW_STATE_LOADED = 3;
    private static final int WEBVIEW_STATE_LOADING = 1;
    private static final int WEBVIEW_STATE_LOADING_BACKGROUND = 2;
    private static final int WEBVIEW_STATE_NONE = 0;
    private static final int YOUTUBE_STATE_BUFFERING = 3;
    private static final int YOUTUBE_STATE_ENDED = 0;
    private static final int YOUTUBE_STATE_PAUSED = 2;
    private static final int YOUTUBE_STATE_PLAYING = 1;
    private static final int YOUTUBE_STATE_UNSTARTED = -1;
    private static String sYoutubeHtmlTemplate;
    /* access modifiers changed from: private */
    public final WebView mWebView;
    /* access modifiers changed from: private */
    public int mDisplayHeight;
    /* access modifiers changed from: private */
    public boolean mDisplaySizeSet;
    /* access modifiers changed from: private */
    public int mDisplayWidth;
    /* access modifiers changed from: private */
    public boolean mFirstFrameDrawn;
    /* access modifiers changed from: private */
    public boolean mPlayWhenReady;
    /* access modifiers changed from: private */
    public int mState = 1;
    /* access modifiers changed from: private */
    public MediaPlayer.VideoCallback mVideoCallback;
    /* access modifiers changed from: private */
    public int mWebViewState;
    private Context mContext;
    private Handler mHandler = new Handler();
    private String mVideoId;
    private Uri mYoutubeUri;

    public YoutubePlayerImpl(Context context) {
        this.mContext = context;
        if (sYoutubeHtmlTemplate == null) {
            sYoutubeHtmlTemplate = readYoutubeHtmlTemplate();
        }
        if (sYoutubeHtmlTemplate != null) {
            this.mWebViewState = 0;
            this.mWebView = new WebView(this.mContext);
            WebSettings settings = this.mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setAllowFileAccess(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            settings.setMediaPlaybackRequiresUserGesture(false);
            settings.setCacheMode(1);
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            this.mWebView.setVerticalScrollBarEnabled(false);
            this.mWebView.setHorizontalScrollBarEnabled(false);
            this.mWebView.setOnTouchListener(new View.OnTouchListener(this) {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            return;
        }
        throw new IllegalStateException("Failed to read youtube html template");
    }

    public int getPlaybackState() {
        return this.mState;
    }

    public Uri getVideoUri() {
        return this.mYoutubeUri;
    }

    public void setVideoUri(Uri uri) {
        if (Uri.EMPTY.equals(uri) || !YouTubeUriUtils.isYouTubeWatchUri(uri)) {
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
            sb.append("Malformed youtube uri:");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }
        this.mYoutubeUri = uri;
        this.mVideoId = YouTubeUriUtils.getYouTubeVideoId(uri);
        this.mDisplayWidth = 932;
        this.mDisplayHeight = 524;
    }

    public void prepare() {
        this.mState = 2;
        this.mDisplaySizeSet = true;
        this.mFirstFrameDrawn = false;
        if (this.mWebViewState == 0) {
            this.mWebView.addJavascriptInterface(this, JAVA_SCRIPT_BRIDGE_TAG);
            this.mWebView.loadDataWithBaseURL("", sYoutubeHtmlTemplate.replace(TAG_VIDEO_ID, this.mVideoId).replace(TAG_DISPLAY_WIDTH, Integer.toString(this.mDisplayWidth)).replace(TAG_DISPLAY_HEIGHT, Integer.toString(this.mDisplayHeight)), "text/html", "UTF-8", null);
            this.mWebView.setInitialScale(100);
            this.mWebViewState = 1;
            this.mWebView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    int previousState = YoutubePlayerImpl.this.mWebViewState;
                    int unused = YoutubePlayerImpl.this.mWebViewState = 3;
                    super.onPageFinished(view, url);
                    YoutubePlayerImpl.this.mWebView.zoomBy(1.0f);
                    if (previousState == 1) {
                        YoutubePlayerImpl youtubePlayerImpl = YoutubePlayerImpl.this;
                        youtubePlayerImpl.setPlayWhenReady(youtubePlayerImpl.mPlayWhenReady);
                    }
                }
            });
            return;
        }
        this.mWebView.setInitialScale(100);
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.mPlayWhenReady = playWhenReady;
        if (this.mWebViewState == 3) {
            if (playWhenReady) {
                callJavaScript("player.playVideo();");
            } else {
                callJavaScript("player.pauseVideo();");
            }
        }
    }

    public void stop() {
        if (this.mWebViewState == 1) {
            this.mWebViewState = 2;
        }
        if (this.mWebViewState == 3) {
            callJavaScript("player.seekTo(0, false);player.pauseVideo();");
            this.mState = 1;
        }
    }

    public void seekTo(int positionMs) {
    }

    public void setDisplaySize(int width, int height) {
        this.mDisplayWidth = width;
        this.mDisplayHeight = height;
        if (this.mState == 3) {
            WebView webView = this.mWebView;
            StringBuilder sb = new StringBuilder(40);
            sb.append("player.setSize(");
            sb.append(width);
            sb.append(",");
            sb.append(height);
            sb.append(");");
            webView.evaluateJavascript(sb.toString(), null);
            this.mDisplaySizeSet = true;
            return;
        }
        this.mDisplaySizeSet = false;
    }

    public int getCurrentPosition() {
        return 0;
    }

    public void setVolume(float volume) {
        if (volume < 0.0f) {
            volume = 0.0f;
        } else if (volume > 1.0f) {
            volume = 1.0f;
        }
        StringBuilder sb = new StringBuilder(30);
        sb.append("player.setVolume(");
        sb.append((int) ((volume / 1.0f) * 100.0f));
        sb.append(");");
        callJavaScript(sb.toString());
    }

    public View getPlayerView() {
        return this.mWebView;
    }

    public void setVideoCallback(MediaPlayer.VideoCallback callback) {
        this.mVideoCallback = callback;
    }

    @WorkerThread
    @JavascriptInterface
    public void onJsPlayerChangeState(final int playerState) {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!YoutubePlayerImpl.this.mDisplaySizeSet) {
                    if (!(YoutubePlayerImpl.this.mDisplayWidth == 0 || YoutubePlayerImpl.this.mDisplayHeight == 0)) {
                        YoutubePlayerImpl youtubePlayerImpl = YoutubePlayerImpl.this;
                        int access$400 = youtubePlayerImpl.mDisplayWidth;
                        int access$500 = YoutubePlayerImpl.this.mDisplayHeight;
                        StringBuilder sb = new StringBuilder(40);
                        sb.append("player.setSize(");
                        sb.append(access$400);
                        sb.append(",");
                        sb.append(access$500);
                        sb.append(");");
                        youtubePlayerImpl.callJavaScript(sb.toString());
                    }
                    boolean unused = YoutubePlayerImpl.this.mDisplaySizeSet = true;
                }
                int i = playerState;
                if (i == 1 || i == 2) {
                    if (!YoutubePlayerImpl.this.mFirstFrameDrawn) {
                        if (YoutubePlayerImpl.this.mVideoCallback != null) {
                            YoutubePlayerImpl.this.mVideoCallback.onVideoAvailable();
                        }
                        boolean unused2 = YoutubePlayerImpl.this.mFirstFrameDrawn = true;
                    }
                    int unused3 = YoutubePlayerImpl.this.mState = 3;
                } else if (i == 0) {
                    int unused4 = YoutubePlayerImpl.this.mState = 4;
                    if (YoutubePlayerImpl.this.mVideoCallback != null) {
                        YoutubePlayerImpl.this.mVideoCallback.onVideoEnded();
                    }
                }
            }
        });
    }

    @WorkerThread
    @JavascriptInterface
    public void onJsPlayerError(final int errorCode) {
        this.mHandler.post(new Runnable() {
            public void run() {
                YoutubePlayerImpl.this.stop();
                if (YoutubePlayerImpl.this.mVideoCallback != null) {
                    YoutubePlayerImpl.this.mVideoCallback.onVideoError();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void callJavaScript(String command) {
        this.mWebView.evaluateJavascript(command, null);
    }

    private String readYoutubeHtmlTemplate() {
        try {
            InputStream inputStream = this.mContext.getAssets().open("youtube_template.html");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                for (int readSize = inputStream.read(BUFFER); readSize >= 0; readSize = inputStream.read(BUFFER)) {
                    byteArrayOutputStream.write(BUFFER, 0, readSize);
                }
                inputStream.close();
                return byteArrayOutputStream.toString();
            } catch (IOException e) {
                return null;
            }
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
            return null;
        }
    }

    public void release() {
        if (this.mWebViewState != 0) {
            this.mWebView.removeJavascriptInterface(JAVA_SCRIPT_BRIDGE_TAG);
            this.mWebView.loadUrl("about:blank");
        }
        this.mYoutubeUri = null;
        this.mVideoId = null;
        this.mDisplayWidth = 0;
        this.mDisplayHeight = 0;
        this.mDisplaySizeSet = false;
        this.mFirstFrameDrawn = false;
        this.mWebViewState = 0;
        this.mVideoCallback = null;
        this.mState = 1;
    }
}
