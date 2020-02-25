package androidx.leanback.media;

import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ControlButtonPresenterSelector;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;

import java.util.List;

public abstract class PlaybackBaseControlGlue<T extends PlayerAdapter> extends PlaybackGlue implements OnActionClickedListener, View.OnKeyListener {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REPEAT = 512;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SHUFFLE = 1024;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    static final boolean DEBUG = false;
    static final String TAG = "PlaybackTransportGlue";
    final T mPlayerAdapter;
    boolean mBuffering = false;
    PlaybackControlsRow mControlsRow;
    PlaybackRowPresenter mControlsRowPresenter;
    Drawable mCover;
    int mErrorCode;
    String mErrorMessage;
    boolean mErrorSet = false;
    boolean mFadeWhenPlaying = true;
    boolean mIsPlaying = false;
    PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    PlaybackGlueHost.PlayerCallback mPlayerCallback;
    CharSequence mSubtitle;
    CharSequence mTitle;
    int mVideoHeight = 0;
    int mVideoWidth = 0;
    final PlayerAdapter.Callback mAdapterCallback = new PlayerAdapter.Callback() {
        public void onPlayStateChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onPlayStateChanged();
        }

        public void onCurrentPositionChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onUpdateProgress();
        }

        public void onBufferedPositionChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onUpdateBufferedProgress();
        }

        public void onDurationChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onUpdateDuration();
        }

        public void onPlayCompleted(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onPlayCompleted();
        }

        public void onPreparedStateChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onPreparedStateChanged();
        }

        public void onVideoSizeChanged(PlayerAdapter wrapper, int width, int height) {
            PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
            playbackBaseControlGlue.mVideoWidth = width;
            playbackBaseControlGlue.mVideoHeight = height;
            if (playbackBaseControlGlue.mPlayerCallback != null) {
                PlaybackBaseControlGlue.this.mPlayerCallback.onVideoSizeChanged(width, height);
            }
        }

        public void onError(PlayerAdapter wrapper, int errorCode, String errorMessage) {
            PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
            playbackBaseControlGlue.mErrorSet = true;
            playbackBaseControlGlue.mErrorCode = errorCode;
            playbackBaseControlGlue.mErrorMessage = errorMessage;
            if (playbackBaseControlGlue.mPlayerCallback != null) {
                PlaybackBaseControlGlue.this.mPlayerCallback.onError(errorCode, errorMessage);
            }
        }

        public void onBufferingStateChanged(PlayerAdapter wrapper, boolean start) {
            PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
            playbackBaseControlGlue.mBuffering = start;
            if (playbackBaseControlGlue.mPlayerCallback != null) {
                PlaybackBaseControlGlue.this.mPlayerCallback.onBufferingStateChanged(start);
            }
        }

        public void onMetadataChanged(PlayerAdapter wrapper) {
            PlaybackBaseControlGlue.this.onMetadataChanged();
        }
    };

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public PlaybackBaseControlGlue(android.content.Context r3, T r4) {
        /*
            r2 = this;
            r2.<init>(r3)
            r0 = 0
            r2.mIsPlaying = r0
            r1 = 1
            r2.mFadeWhenPlaying = r1
            r2.mBuffering = r0
            r2.mVideoWidth = r0
            r2.mVideoHeight = r0
            r2.mErrorSet = r0
            androidx.leanback.media.PlaybackBaseControlGlue$1 r0 = new androidx.leanback.media.PlaybackBaseControlGlue$1
            r0.<init>()
            r2.mAdapterCallback = r0
            r2.mPlayerAdapter = r4
            T r0 = r2.mPlayerAdapter
            androidx.leanback.media.PlayerAdapter$Callback r1 = r2.mAdapterCallback
            r0.setCallback(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.<init>(android.content.Context, androidx.leanback.media.PlayerAdapter):void");
    }

    protected static void notifyItemChanged(ArrayObjectAdapter adapter, Object object) {
        int index = adapter.indexOf(object);
        if (index >= 0) {
            adapter.notifyArrayItemRangeChanged(index, 1);
        }
    }

    public abstract void onActionClicked(Action action);

    /* access modifiers changed from: protected */
    public abstract PlaybackRowPresenter onCreateRowPresenter();

    public abstract boolean onKey(View view, int i, KeyEvent keyEvent);

    public final T getPlayerAdapter() {
        return this.mPlayerAdapter;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected void onAttachedToHost(androidx.leanback.media.PlaybackGlueHost r2) {
        /*
            r1 = this;
            super.onAttachedToHost(r2)
            r2.setOnKeyInterceptListener(r1)
            r2.setOnActionClickedListener(r1)
            r1.onCreateDefaultControlsRow()
            r1.onCreateDefaultRowPresenter()
            androidx.leanback.widget.PlaybackRowPresenter r0 = r1.getPlaybackRowPresenter()
            r2.setPlaybackRowPresenter(r0)
            androidx.leanback.widget.PlaybackControlsRow r0 = r1.getControlsRow()
            r2.setPlaybackRow(r0)
            androidx.leanback.media.PlaybackGlueHost$PlayerCallback r0 = r2.getPlayerCallback()
            r1.mPlayerCallback = r0
            r1.onAttachHostCallback()
            T r0 = r1.mPlayerAdapter
            r0.onAttachedToHost(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.onAttachedToHost(androidx.leanback.media.PlaybackGlueHost):void");
    }

    /* access modifiers changed from: package-private */
    public void onAttachHostCallback() {
        int i;
        PlaybackGlueHost.PlayerCallback playerCallback = this.mPlayerCallback;
        if (playerCallback != null) {
            int i2 = this.mVideoWidth;
            if (!(i2 == 0 || (i = this.mVideoHeight) == 0)) {
                playerCallback.onVideoSizeChanged(i2, i);
            }
            if (this.mErrorSet) {
                this.mPlayerCallback.onError(this.mErrorCode, this.mErrorMessage);
            }
            this.mPlayerCallback.onBufferingStateChanged(this.mBuffering);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDetachHostCallback() {
        this.mErrorSet = false;
        this.mErrorCode = 0;
        this.mErrorMessage = null;
        PlaybackGlueHost.PlayerCallback playerCallback = this.mPlayerCallback;
        if (playerCallback != null) {
            playerCallback.onBufferingStateChanged(false);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected void onHostStart() {
        /*
            r2 = this;
            T r0 = r2.mPlayerAdapter
            r1 = 1
            r0.setProgressUpdatingEnabled(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.onHostStart():void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected void onHostStop() {
        /*
            r2 = this;
            T r0 = r2.mPlayerAdapter
            r1 = 0
            r0.setProgressUpdatingEnabled(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.onHostStop():void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected void onDetachedFromHost() {
        /*
            r2 = this;
            r2.onDetachHostCallback()
            r0 = 0
            r2.mPlayerCallback = r0
            T r0 = r2.mPlayerAdapter
            r0.onDetachedFromHost()
            T r0 = r2.mPlayerAdapter
            r1 = 0
            r0.setProgressUpdatingEnabled(r1)
            super.onDetachedFromHost()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.onDetachedFromHost():void");
    }

    /* access modifiers changed from: package-private */
    public void onCreateDefaultControlsRow() {
        if (this.mControlsRow == null) {
            setControlsRow(new PlaybackControlsRow(this));
        }
    }

    /* access modifiers changed from: package-private */
    public void onCreateDefaultRowPresenter() {
        if (this.mControlsRowPresenter == null) {
            setPlaybackRowPresenter(onCreateRowPresenter());
        }
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return this.mFadeWhenPlaying;
    }

    public void setControlsOverlayAutoHideEnabled(boolean enable) {
        this.mFadeWhenPlaying = enable;
        if (!this.mFadeWhenPlaying && getHost() != null) {
            getHost().setControlsOverlayAutoHideEnabled(false);
        }
    }

    public PlaybackControlsRow getControlsRow() {
        return this.mControlsRow;
    }

    public void setControlsRow(PlaybackControlsRow controlsRow) {
        this.mControlsRow = controlsRow;
        this.mControlsRow.setCurrentPosition(-1);
        this.mControlsRow.setDuration(-1);
        this.mControlsRow.setBufferedPosition(-1);
        if (this.mControlsRow.getPrimaryActionsAdapter() == null) {
            ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
            onCreatePrimaryActions(adapter);
            this.mControlsRow.setPrimaryActionsAdapter(adapter);
        }
        if (this.mControlsRow.getSecondaryActionsAdapter() == null) {
            ArrayObjectAdapter secondaryActions = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
            onCreateSecondaryActions(secondaryActions);
            getControlsRow().setSecondaryActionsAdapter(secondaryActions);
        }
        updateControlsRow();
    }

    public PlaybackRowPresenter getPlaybackRowPresenter() {
        return this.mControlsRowPresenter;
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter presenter) {
        this.mControlsRowPresenter = presenter;
    }

    private void updateControlsRow() {
        onMetadataChanged();
    }

    public final boolean isPlaying() {
        return this.mPlayerAdapter.isPlaying();
    }

    public void play() {
        this.mPlayerAdapter.play();
    }

    public void pause() {
        this.mPlayerAdapter.pause();
    }

    public void next() {
        this.mPlayerAdapter.next();
    }

    public void previous() {
        this.mPlayerAdapter.previous();
    }

    /* access modifiers changed from: protected */
    public void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
    }

    /* access modifiers changed from: protected */
    public void onCreateSecondaryActions(ArrayObjectAdapter secondaryActionsAdapter) {
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onUpdateProgress() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setCurrentPosition(this.mPlayerAdapter.isPrepared() ? getCurrentPosition() : -1);
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onUpdateBufferedProgress() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setBufferedPosition(this.mPlayerAdapter.getBufferedPosition());
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onUpdateDuration() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setDuration(this.mPlayerAdapter.isPrepared() ? this.mPlayerAdapter.getDuration() : -1);
        }
    }

    public final long getDuration() {
        return this.mPlayerAdapter.getDuration();
    }

    public long getCurrentPosition() {
        return this.mPlayerAdapter.getCurrentPosition();
    }

    public final long getBufferedPosition() {
        return this.mPlayerAdapter.getBufferedPosition();
    }

    public final boolean isPrepared() {
        return this.mPlayerAdapter.isPrepared();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onPreparedStateChanged() {
        onUpdateDuration();
        List<PlaybackGlue.PlayerCallback> callbacks = getPlayerCallbacks();
        if (callbacks != null) {
            int size = callbacks.size();
            for (int i = 0; i < size; i++) {
                callbacks.get(i).onPreparedStateChanged(this);
            }
        }
    }

    public Drawable getArt() {
        return this.mCover;
    }

    public void setArt(Drawable cover) {
        if (this.mCover != cover) {
            this.mCover = cover;
            this.mControlsRow.setImageDrawable(this.mCover);
            if (getHost() != null) {
                getHost().notifyPlaybackRowChanged();
            }
        }
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setSubtitle(CharSequence subtitle) {
        if (!TextUtils.equals(subtitle, this.mSubtitle)) {
            this.mSubtitle = subtitle;
            if (getHost() != null) {
                getHost().notifyPlaybackRowChanged();
            }
        }
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.equals(title, this.mTitle)) {
            this.mTitle = title;
            if (getHost() != null) {
                getHost().notifyPlaybackRowChanged();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMetadataChanged() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setImageDrawable(getArt());
            this.mControlsRow.setDuration(getDuration());
            this.mControlsRow.setCurrentPosition(getCurrentPosition());
            if (getHost() != null) {
                getHost().notifyPlaybackRowChanged();
            }
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onPlayStateChanged() {
        List<PlaybackGlue.PlayerCallback> callbacks = getPlayerCallbacks();
        if (callbacks != null) {
            int size = callbacks.size();
            for (int i = 0; i < size; i++) {
                callbacks.get(i).onPlayStateChanged(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onPlayCompleted() {
        List<PlaybackGlue.PlayerCallback> callbacks = getPlayerCallbacks();
        if (callbacks != null) {
            int size = callbacks.size();
            for (int i = 0; i < size; i++) {
                callbacks.get(i).onPlayCompleted(this);
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public final void seekTo(long r2) {
        /*
            r1 = this;
            T r0 = r1.mPlayerAdapter
            r0.seekTo(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.media.PlaybackBaseControlGlue.seekTo(long):void");
    }

    public long getSupportedActions() {
        return this.mPlayerAdapter.getSupportedActions();
    }
}
