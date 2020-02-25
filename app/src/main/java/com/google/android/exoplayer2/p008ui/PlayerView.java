package com.google.android.exoplayer2.p008ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.p001v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player$EventListener$$CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.p008ui.spherical.SingleTapListener;
import com.google.android.exoplayer2.p008ui.spherical.SphericalSurfaceView;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoListener$$CC;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.PlayerView */
public class PlayerView extends FrameLayout implements AdsLoader.AdViewProvider {
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private static final int SURFACE_TYPE_MONO360_VIEW = 3;
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;
    /* access modifiers changed from: private */
    @Nullable
    public final AspectRatioFrameLayout contentFrame;
    /* access modifiers changed from: private */
    public final View shutterView;
    /* access modifiers changed from: private */
    public final SubtitleView subtitleView;
    /* access modifiers changed from: private */
    @Nullable
    public final View surfaceView;
    @Nullable
    private final FrameLayout adOverlayFrameLayout;
    private final ImageView artworkView;
    @Nullable
    private final View bufferingView;
    private final ComponentListener componentListener;
    @Nullable
    private final PlayerControlView controller;
    @Nullable
    private final TextView errorMessageView;
    @Nullable
    private final FrameLayout overlayFrameLayout;
    /* access modifiers changed from: private */
    public boolean controllerHideDuringAds;
    /* access modifiers changed from: private */
    public Player player;
    /* access modifiers changed from: private */
    public int textureViewRotation;
    private boolean controllerAutoShow;
    private boolean controllerHideOnTouch;
    private int controllerShowTimeoutMs;
    @Nullable
    private CharSequence customErrorMessage;
    @Nullable
    private Drawable defaultArtwork;
    @Nullable
    private ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider;
    private boolean keepContentOnPlayerReset;
    private int showBuffering;
    private boolean useArtwork;
    private boolean useController;

    public PlayerView(Context context) {
        this(context, null);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int controllerShowTimeoutMs2;
        int controllerShowTimeoutMs3;
        Context context2;
        Context context3 = context;
        AttributeSet attributeSet = attrs;
        if (isInEditMode()) {
            this.contentFrame = null;
            this.shutterView = null;
            this.surfaceView = null;
            this.artworkView = null;
            this.subtitleView = null;
            this.bufferingView = null;
            this.errorMessageView = null;
            this.controller = null;
            this.componentListener = null;
            this.adOverlayFrameLayout = null;
            this.overlayFrameLayout = null;
            ImageView logo = new ImageView(context3);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), logo);
            } else {
                configureEditModeLogo(getResources(), logo);
            }
            addView(logo);
            return;
        }
        int shutterColor = 0;
        int playerLayoutId = C0931R.layout.exo_player_view;
        boolean useArtwork2 = true;
        int defaultArtworkId = 0;
        boolean useController2 = true;
        int surfaceType = 1;
        int resizeMode = 0;
        boolean controllerHideOnTouch2 = true;
        boolean controllerAutoShow2 = true;
        boolean controllerHideDuringAds2 = true;
        boolean shutterColorSet = false;
        int showBuffering2 = 0;
        if (attributeSet != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, C0931R.styleable.PlayerView, 0, 0);
            try {
                boolean shutterColorSet2 = a.hasValue(C0931R.styleable.PlayerView_shutter_background_color);
                try {
                    shutterColor = a.getColor(C0931R.styleable.PlayerView_shutter_background_color, 0);
                    playerLayoutId = a.getResourceId(C0931R.styleable.PlayerView_player_layout_id, playerLayoutId);
                    useArtwork2 = a.getBoolean(C0931R.styleable.PlayerView_use_artwork, true);
                    defaultArtworkId = a.getResourceId(C0931R.styleable.PlayerView_default_artwork, 0);
                    useController2 = a.getBoolean(C0931R.styleable.PlayerView_use_controller, true);
                    surfaceType = a.getInt(C0931R.styleable.PlayerView_surface_type, 1);
                    resizeMode = a.getInt(C0931R.styleable.PlayerView_resize_mode, 0);
                    int controllerShowTimeoutMs4 = a.getInt(C0931R.styleable.PlayerView_show_timeout, 5000);
                    controllerHideOnTouch2 = a.getBoolean(C0931R.styleable.PlayerView_hide_on_touch, true);
                    controllerAutoShow2 = a.getBoolean(C0931R.styleable.PlayerView_auto_show, true);
                    showBuffering2 = a.getInteger(C0931R.styleable.PlayerView_show_buffering, 0);
                    shutterColorSet = shutterColorSet2;
                    this.keepContentOnPlayerReset = a.getBoolean(C0931R.styleable.PlayerView_keep_content_on_player_reset, this.keepContentOnPlayerReset);
                    try {
                        controllerHideDuringAds2 = a.getBoolean(C0931R.styleable.PlayerView_hide_during_ads, true);
                        a.recycle();
                        controllerShowTimeoutMs2 = controllerShowTimeoutMs4;
                    } catch (Throwable th) {
                        th = th;
                        a.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    a.recycle();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                a.recycle();
                throw th;
            }
        } else {
            controllerShowTimeoutMs2 = 5000;
        }
        LayoutInflater.from(context).inflate(playerLayoutId, this);
        this.componentListener = new ComponentListener();
        setDescendantFocusability(262144);
        this.contentFrame = (AspectRatioFrameLayout) findViewById(C0931R.C0933id.exo_content_frame);
        AspectRatioFrameLayout aspectRatioFrameLayout = this.contentFrame;
        if (aspectRatioFrameLayout != null) {
            setResizeModeRaw(aspectRatioFrameLayout, resizeMode);
        }
        this.shutterView = findViewById(C0931R.C0933id.exo_shutter);
        View view = this.shutterView;
        if (view != null && shutterColorSet) {
            view.setBackgroundColor(shutterColor);
        }
        if (this.contentFrame == null || surfaceType == 0) {
            controllerShowTimeoutMs3 = controllerShowTimeoutMs2;
            context2 = context;
            this.surfaceView = null;
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            if (surfaceType == 2) {
                controllerShowTimeoutMs3 = controllerShowTimeoutMs2;
                context2 = context;
                this.surfaceView = new TextureView(context2);
            } else if (surfaceType != 3) {
                context2 = context;
                this.surfaceView = new SurfaceView(context2);
                controllerShowTimeoutMs3 = controllerShowTimeoutMs2;
            } else {
                context2 = context;
                SphericalSurfaceView sphericalSurfaceView = new SphericalSurfaceView(context2);
                controllerShowTimeoutMs3 = controllerShowTimeoutMs2;
                sphericalSurfaceView.setSurfaceListener(this.componentListener);
                sphericalSurfaceView.setSingleTapListener(this.componentListener);
                this.surfaceView = sphericalSurfaceView;
            }
            this.surfaceView.setLayoutParams(params);
            this.contentFrame.addView(this.surfaceView, 0);
        }
        this.adOverlayFrameLayout = (FrameLayout) findViewById(C0931R.C0933id.exo_ad_overlay);
        this.overlayFrameLayout = (FrameLayout) findViewById(C0931R.C0933id.exo_overlay);
        this.artworkView = (ImageView) findViewById(C0931R.C0933id.exo_artwork);
        this.useArtwork = useArtwork2 && this.artworkView != null;
        if (defaultArtworkId != 0) {
            this.defaultArtwork = ContextCompat.getDrawable(getContext(), defaultArtworkId);
        }
        this.subtitleView = (SubtitleView) findViewById(C0931R.C0933id.exo_subtitles);
        SubtitleView subtitleView2 = this.subtitleView;
        if (subtitleView2 != null) {
            subtitleView2.setUserDefaultStyle();
            this.subtitleView.setUserDefaultTextSize();
        }
        this.bufferingView = findViewById(C0931R.C0933id.exo_buffering);
        View view2 = this.bufferingView;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.showBuffering = showBuffering2;
        this.errorMessageView = (TextView) findViewById(C0931R.C0933id.exo_error_message);
        TextView textView = this.errorMessageView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        PlayerControlView customController = (PlayerControlView) findViewById(C0931R.C0933id.exo_controller);
        View controllerPlaceholder = findViewById(C0931R.C0933id.exo_controller_placeholder);
        if (customController != null) {
            this.controller = customController;
        } else if (controllerPlaceholder != null) {
            this.controller = new PlayerControlView(context2, null, 0, attributeSet);
            this.controller.setLayoutParams(controllerPlaceholder.getLayoutParams());
            ViewGroup parent = (ViewGroup) controllerPlaceholder.getParent();
            int controllerIndex = parent.indexOfChild(controllerPlaceholder);
            parent.removeView(controllerPlaceholder);
            parent.addView(this.controller, controllerIndex);
        } else {
            this.controller = null;
        }
        this.controllerShowTimeoutMs = this.controller != null ? controllerShowTimeoutMs3 : 0;
        this.controllerHideOnTouch = controllerHideOnTouch2;
        this.controllerAutoShow = controllerAutoShow2;
        this.controllerHideDuringAds = controllerHideDuringAds2;
        this.useController = useController2 && this.controller != null;
        hideController();
    }

    public static void switchTargetView(Player player2, @Nullable PlayerView oldPlayerView, @Nullable PlayerView newPlayerView) {
        if (oldPlayerView != newPlayerView) {
            if (newPlayerView != null) {
                newPlayerView.setPlayer(player2);
            }
            if (oldPlayerView != null) {
                oldPlayerView.setPlayer(null);
            }
        }
    }

    @TargetApi(23)
    private static void configureEditModeLogoV23(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(C0931R.C0932drawable.exo_edit_mode_logo, null));
        logo.setBackgroundColor(resources.getColor(C0931R.color.exo_edit_mode_background_color, null));
    }

    private static void configureEditModeLogo(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(C0931R.C0932drawable.exo_edit_mode_logo));
        logo.setBackgroundColor(resources.getColor(C0931R.color.exo_edit_mode_background_color));
    }

    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrame, int resizeMode) {
        aspectRatioFrame.setResizeMode(resizeMode);
    }

    /* access modifiers changed from: private */
    public static void applyTextureViewRotation(TextureView textureView, int textureViewRotation2) {
        float textureViewWidth = (float) textureView.getWidth();
        float textureViewHeight = (float) textureView.getHeight();
        if (textureViewWidth == 0.0f || textureViewHeight == 0.0f || textureViewRotation2 == 0) {
            textureView.setTransform(null);
            return;
        }
        Matrix transformMatrix = new Matrix();
        float pivotX = textureViewWidth / 2.0f;
        float pivotY = textureViewHeight / 2.0f;
        transformMatrix.postRotate((float) textureViewRotation2, pivotX, pivotY);
        RectF originalTextureRect = new RectF(0.0f, 0.0f, textureViewWidth, textureViewHeight);
        RectF rotatedTextureRect = new RectF();
        transformMatrix.mapRect(rotatedTextureRect, originalTextureRect);
        transformMatrix.postScale(textureViewWidth / rotatedTextureRect.width(), textureViewHeight / rotatedTextureRect.height(), pivotX, pivotY);
        textureView.setTransform(transformMatrix);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(@Nullable Player player2) {
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        Assertions.checkArgument(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper());
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.componentListener);
                Player.VideoComponent oldVideoComponent = this.player.getVideoComponent();
                if (oldVideoComponent != null) {
                    oldVideoComponent.removeVideoListener(this.componentListener);
                    View view = this.surfaceView;
                    if (view instanceof TextureView) {
                        oldVideoComponent.clearVideoTextureView((TextureView) view);
                    } else if (view instanceof SphericalSurfaceView) {
                        ((SphericalSurfaceView) view).setVideoComponent(null);
                    } else if (view instanceof SurfaceView) {
                        oldVideoComponent.clearVideoSurfaceView((SurfaceView) view);
                    }
                }
                Player.TextComponent oldTextComponent = this.player.getTextComponent();
                if (oldTextComponent != null) {
                    oldTextComponent.removeTextOutput(this.componentListener);
                }
            }
            this.player = player2;
            if (this.useController) {
                this.controller.setPlayer(player2);
            }
            SubtitleView subtitleView2 = this.subtitleView;
            if (subtitleView2 != null) {
                subtitleView2.setCues(null);
            }
            updateBuffering();
            updateErrorMessage();
            updateForCurrentTrackSelections(true);
            if (player2 != null) {
                Player.VideoComponent newVideoComponent = player2.getVideoComponent();
                if (newVideoComponent != null) {
                    View view2 = this.surfaceView;
                    if (view2 instanceof TextureView) {
                        newVideoComponent.setVideoTextureView((TextureView) view2);
                    } else if (view2 instanceof SphericalSurfaceView) {
                        ((SphericalSurfaceView) view2).setVideoComponent(newVideoComponent);
                    } else if (view2 instanceof SurfaceView) {
                        newVideoComponent.setVideoSurfaceView((SurfaceView) view2);
                    }
                    newVideoComponent.addVideoListener(this.componentListener);
                }
                Player.TextComponent newTextComponent = player2.getTextComponent();
                if (newTextComponent != null) {
                    newTextComponent.addTextOutput(this.componentListener);
                }
                player2.addListener(this.componentListener);
                maybeShowController(false);
                return;
            }
            hideController();
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        View view = this.surfaceView;
        if (view instanceof SurfaceView) {
            view.setVisibility(visibility);
        }
    }

    public int getResizeMode() {
        Assertions.checkState(this.contentFrame != null);
        return this.contentFrame.getResizeMode();
    }

    public void setResizeMode(int resizeMode) {
        Assertions.checkState(this.contentFrame != null);
        this.contentFrame.setResizeMode(resizeMode);
    }

    public boolean getUseArtwork() {
        return this.useArtwork;
    }

    public void setUseArtwork(boolean useArtwork2) {
        Assertions.checkState(!useArtwork2 || this.artworkView != null);
        if (this.useArtwork != useArtwork2) {
            this.useArtwork = useArtwork2;
            updateForCurrentTrackSelections(false);
        }
    }

    @Nullable
    public Drawable getDefaultArtwork() {
        return this.defaultArtwork;
    }

    @Deprecated
    public void setDefaultArtwork(@Nullable Bitmap defaultArtwork2) {
        setDefaultArtwork(defaultArtwork2 == null ? null : new BitmapDrawable(getResources(), defaultArtwork2));
    }

    public void setDefaultArtwork(@Nullable Drawable defaultArtwork2) {
        if (this.defaultArtwork != defaultArtwork2) {
            this.defaultArtwork = defaultArtwork2;
            updateForCurrentTrackSelections(false);
        }
    }

    public boolean getUseController() {
        return this.useController;
    }

    public void setUseController(boolean useController2) {
        Assertions.checkState(!useController2 || this.controller != null);
        if (this.useController != useController2) {
            this.useController = useController2;
            if (useController2) {
                this.controller.setPlayer(this.player);
                return;
            }
            PlayerControlView playerControlView = this.controller;
            if (playerControlView != null) {
                playerControlView.hide();
                this.controller.setPlayer(null);
            }
        }
    }

    public void setShutterBackgroundColor(int color) {
        View view = this.shutterView;
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    public void setKeepContentOnPlayerReset(boolean keepContentOnPlayerReset2) {
        if (this.keepContentOnPlayerReset != keepContentOnPlayerReset2) {
            this.keepContentOnPlayerReset = keepContentOnPlayerReset2;
            updateForCurrentTrackSelections(false);
        }
    }

    @Deprecated
    public void setShowBuffering(boolean showBuffering2) {
        setShowBuffering((int) (showBuffering2));
    }

    public void setShowBuffering(int showBuffering2) {
        if (this.showBuffering != showBuffering2) {
            this.showBuffering = showBuffering2;
            updateBuffering();
        }
    }

    public void setErrorMessageProvider(@Nullable ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider2) {
        if (this.errorMessageProvider != errorMessageProvider2) {
            this.errorMessageProvider = errorMessageProvider2;
            updateErrorMessage();
        }
    }

    public void setCustomErrorMessage(@Nullable CharSequence message) {
        Assertions.checkState(this.errorMessageView != null);
        this.customErrorMessage = message;
        updateErrorMessage();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        Player player2 = this.player;
        if (player2 != null && player2.isPlayingAd()) {
            return super.dispatchKeyEvent(event);
        }
        boolean handled = false;
        if ((isDpadKey(event.getKeyCode()) && this.useController && !this.controller.isVisible()) || dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event)) {
            handled = true;
        }
        if (handled) {
            maybeShowController(true);
        }
        return handled;
    }

    public boolean dispatchMediaKeyEvent(KeyEvent event) {
        return this.useController && this.controller.dispatchMediaKeyEvent(event);
    }

    public boolean isControllerVisible() {
        PlayerControlView playerControlView = this.controller;
        return playerControlView != null && playerControlView.isVisible();
    }

    public void showController() {
        showController(shouldShowControllerIndefinitely());
    }

    public void hideController() {
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            playerControlView.hide();
        }
    }

    public int getControllerShowTimeoutMs() {
        return this.controllerShowTimeoutMs;
    }

    public void setControllerShowTimeoutMs(int controllerShowTimeoutMs2) {
        Assertions.checkState(this.controller != null);
        this.controllerShowTimeoutMs = controllerShowTimeoutMs2;
        if (this.controller.isVisible()) {
            showController();
        }
    }

    public boolean getControllerHideOnTouch() {
        return this.controllerHideOnTouch;
    }

    public void setControllerHideOnTouch(boolean controllerHideOnTouch2) {
        Assertions.checkState(this.controller != null);
        this.controllerHideOnTouch = controllerHideOnTouch2;
    }

    public boolean getControllerAutoShow() {
        return this.controllerAutoShow;
    }

    public void setControllerAutoShow(boolean controllerAutoShow2) {
        this.controllerAutoShow = controllerAutoShow2;
    }

    public void setControllerHideDuringAds(boolean controllerHideDuringAds2) {
        this.controllerHideDuringAds = controllerHideDuringAds2;
    }

    public void setControllerVisibilityListener(PlayerControlView.VisibilityListener listener) {
        Assertions.checkState(this.controller != null);
        this.controller.setVisibilityListener(listener);
    }

    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        Assertions.checkState(this.controller != null);
        this.controller.setPlaybackPreparer(playbackPreparer);
    }

    public void setControlDispatcher(@Nullable ControlDispatcher controlDispatcher) {
        Assertions.checkState(this.controller != null);
        this.controller.setControlDispatcher(controlDispatcher);
    }

    public void setRewindIncrementMs(int rewindMs) {
        Assertions.checkState(this.controller != null);
        this.controller.setRewindIncrementMs(rewindMs);
    }

    public void setFastForwardIncrementMs(int fastForwardMs) {
        Assertions.checkState(this.controller != null);
        this.controller.setFastForwardIncrementMs(fastForwardMs);
    }

    public void setRepeatToggleModes(int repeatToggleModes) {
        Assertions.checkState(this.controller != null);
        this.controller.setRepeatToggleModes(repeatToggleModes);
    }

    public void setShowShuffleButton(boolean showShuffleButton) {
        Assertions.checkState(this.controller != null);
        this.controller.setShowShuffleButton(showShuffleButton);
    }

    public void setShowMultiWindowTimeBar(boolean showMultiWindowTimeBar) {
        Assertions.checkState(this.controller != null);
        this.controller.setShowMultiWindowTimeBar(showMultiWindowTimeBar);
    }

    public void setExtraAdGroupMarkers(@Nullable long[] extraAdGroupTimesMs, @Nullable boolean[] extraPlayedAdGroups) {
        Assertions.checkState(this.controller != null);
        this.controller.setExtraAdGroupMarkers(extraAdGroupTimesMs, extraPlayedAdGroups);
    }

    public void setAspectRatioListener(AspectRatioFrameLayout.AspectRatioListener listener) {
        Assertions.checkState(this.contentFrame != null);
        this.contentFrame.setAspectRatioListener(listener);
    }

    public View getVideoSurfaceView() {
        return this.surfaceView;
    }

    @Nullable
    public FrameLayout getOverlayFrameLayout() {
        return this.overlayFrameLayout;
    }

    public SubtitleView getSubtitleView() {
        return this.subtitleView;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() != 0) {
            return false;
        }
        return performClick();
    }

    public boolean performClick() {
        super.performClick();
        return toggleControllerVisibility();
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (!this.useController || this.player == null) {
            return false;
        }
        maybeShowController(true);
        return true;
    }

    public void onResume() {
        View view = this.surfaceView;
        if (view instanceof SphericalSurfaceView) {
            ((SphericalSurfaceView) view).onResume();
        }
    }

    public void onPause() {
        View view = this.surfaceView;
        if (view instanceof SphericalSurfaceView) {
            ((SphericalSurfaceView) view).onPause();
        }
    }

    /* access modifiers changed from: protected */
    public void onContentAspectRatioChanged(float contentAspectRatio, @Nullable AspectRatioFrameLayout contentFrame2, @Nullable View contentView) {
        if (contentFrame2 != null) {
            contentFrame2.setAspectRatio(contentView instanceof SphericalSurfaceView ? 0.0f : contentAspectRatio);
        }
    }

    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkNotNull(this.adOverlayFrameLayout, "exo_ad_overlay must be present for ad playback");
    }

    public View[] getAdOverlayViews() {
        ArrayList<View> overlayViews = new ArrayList<>();
        FrameLayout frameLayout = this.overlayFrameLayout;
        if (frameLayout != null) {
            overlayViews.add(frameLayout);
        }
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            overlayViews.add(playerControlView);
        }
        return (View[]) overlayViews.toArray(new View[0]);
    }

    /* access modifiers changed from: private */
    public boolean toggleControllerVisibility() {
        if (!this.useController || this.player == null) {
            return false;
        }
        if (!this.controller.isVisible()) {
            maybeShowController(true);
        } else if (this.controllerHideOnTouch) {
            this.controller.hide();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void maybeShowController(boolean isForced) {
        if ((!isPlayingAd() || !this.controllerHideDuringAds) && this.useController) {
            boolean wasShowingIndefinitely = this.controller.isVisible() && this.controller.getShowTimeoutMs() <= 0;
            boolean shouldShowIndefinitely = shouldShowControllerIndefinitely();
            if (isForced || wasShowingIndefinitely || shouldShowIndefinitely) {
                showController(shouldShowIndefinitely);
            }
        }
    }

    private boolean shouldShowControllerIndefinitely() {
        Player player2 = this.player;
        if (player2 == null) {
            return true;
        }
        int playbackState = player2.getPlaybackState();
        if (!this.controllerAutoShow || (playbackState != 1 && playbackState != 4 && this.player.getPlayWhenReady())) {
            return false;
        }
        return true;
    }

    private void showController(boolean showIndefinitely) {
        if (this.useController) {
            this.controller.setShowTimeoutMs(showIndefinitely ? 0 : this.controllerShowTimeoutMs);
            this.controller.show();
        }
    }

    /* access modifiers changed from: private */
    public boolean isPlayingAd() {
        Player player2 = this.player;
        return player2 != null && player2.isPlayingAd() && this.player.getPlayWhenReady();
    }

    /* access modifiers changed from: private */
    public void updateForCurrentTrackSelections(boolean isNewPlayer) {
        Player player2 = this.player;
        if (player2 != null && !player2.getCurrentTrackGroups().isEmpty()) {
            if (isNewPlayer && !this.keepContentOnPlayerReset) {
                closeShutter();
            }
            TrackSelectionArray selections = this.player.getCurrentTrackSelections();
            int i = 0;
            while (i < selections.length) {
                if (this.player.getRendererType(i) != 2 || selections.get(i) == null) {
                    i++;
                } else {
                    hideArtwork();
                    return;
                }
            }
            closeShutter();
            if (this.useArtwork) {
                for (int i2 = 0; i2 < selections.length; i2++) {
                    TrackSelection selection = selections.get(i2);
                    if (selection != null) {
                        int j = 0;
                        while (j < selection.length()) {
                            Metadata metadata = selection.getFormat(j).metadata;
                            if (metadata == null || !setArtworkFromMetadata(metadata)) {
                                j++;
                            } else {
                                return;
                            }
                        }
                        continue;
                    }
                }
                if (setDrawableArtwork(this.defaultArtwork)) {
                    return;
                }
            }
            hideArtwork();
        } else if (!this.keepContentOnPlayerReset) {
            hideArtwork();
            closeShutter();
        }
    }

    private boolean setArtworkFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry metadataEntry = metadata.get(i);
            if (metadataEntry instanceof ApicFrame) {
                byte[] bitmapData = ((ApicFrame) metadataEntry).pictureData;
                return setDrawableArtwork(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length)));
            }
        }
        return false;
    }

    private boolean setDrawableArtwork(@Nullable Drawable drawable) {
        if (drawable != null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            if (drawableWidth > 0 && drawableHeight > 0) {
                onContentAspectRatioChanged(((float) drawableWidth) / ((float) drawableHeight), this.contentFrame, this.artworkView);
                this.artworkView.setImageDrawable(drawable);
                this.artworkView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private void hideArtwork() {
        ImageView imageView = this.artworkView;
        if (imageView != null) {
            imageView.setImageResource(17170445);
            this.artworkView.setVisibility(4);
        }
    }

    private void closeShutter() {
        View view = this.shutterView;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void updateBuffering() {
        int i;
        if (this.bufferingView != null) {
            Player player2 = this.player;
            boolean z = true;
            int i2 = 0;
            if (player2 == null || player2.getPlaybackState() != 2 || ((i = this.showBuffering) != 2 && (i != 1 || !this.player.getPlayWhenReady()))) {
                z = false;
            }
            boolean showBufferingSpinner = z;
            View view = this.bufferingView;
            if (!showBufferingSpinner) {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    /* access modifiers changed from: private */
    public void updateErrorMessage() {
        TextView textView = this.errorMessageView;
        if (textView != null) {
            CharSequence charSequence = this.customErrorMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                this.errorMessageView.setVisibility(0);
                return;
            }
            ExoPlaybackException error = null;
            Player player2 = this.player;
            if (!(player2 == null || player2.getPlaybackState() != 1 || this.errorMessageProvider == null)) {
                error = this.player.getPlaybackError();
            }
            if (error != null) {
                this.errorMessageView.setText((CharSequence) this.errorMessageProvider.getErrorMessage(error).second);
                this.errorMessageView.setVisibility(0);
                return;
            }
            this.errorMessageView.setVisibility(8);
        }
    }

    @SuppressLint({"InlinedApi"})
    private boolean isDpadKey(int keyCode) {
        return keyCode == 19 || keyCode == 270 || keyCode == 22 || keyCode == 271 || keyCode == 20 || keyCode == 269 || keyCode == 21 || keyCode == 268 || keyCode == 23;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.PlayerView$ShowBuffering */
    public @interface ShowBuffering {
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerView$ComponentListener */
    private final class ComponentListener implements Player.EventListener, TextOutput, VideoListener, View.OnLayoutChangeListener, SphericalSurfaceView.SurfaceListener, SingleTapListener {
        private ComponentListener() {
        }

        public void onLoadingChanged(boolean z) {
            Player$EventListener$$CC.onLoadingChanged$$dflt$$(this, z);
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player$EventListener$$CC.onPlaybackParametersChanged$$dflt$$(this, playbackParameters);
        }

        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            Player$EventListener$$CC.onPlayerError$$dflt$$(this, exoPlaybackException);
        }

        public void onRepeatModeChanged(int i) {
            Player$EventListener$$CC.onRepeatModeChanged$$dflt$$(this, i);
        }

        public void onSeekProcessed() {
            Player$EventListener$$CC.onSeekProcessed$$dflt$$(this);
        }

        public void onShuffleModeEnabledChanged(boolean z) {
            Player$EventListener$$CC.onShuffleModeEnabledChanged$$dflt$$(this, z);
        }

        public void onSurfaceSizeChanged(int i, int i2) {
            VideoListener$$CC.onSurfaceSizeChanged$$dflt$$(this, i, i2);
        }

        public void onTimelineChanged(Timeline timeline, Object obj, int i) {
            Player$EventListener$$CC.onTimelineChanged$$dflt$$(this, timeline, obj, i);
        }

        public void onCues(List<Cue> cues) {
            if (PlayerView.this.subtitleView != null) {
                PlayerView.this.subtitleView.onCues(cues);
            }
        }

        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            float videoAspectRatio = (height == 0 || width == 0) ? 1.0f : (((float) width) * pixelWidthHeightRatio) / ((float) height);
            if (PlayerView.this.surfaceView instanceof TextureView) {
                if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
                    videoAspectRatio = 1.0f / videoAspectRatio;
                }
                if (PlayerView.this.textureViewRotation != 0) {
                    PlayerView.this.surfaceView.removeOnLayoutChangeListener(this);
                }
                int unused = PlayerView.this.textureViewRotation = unappliedRotationDegrees;
                if (PlayerView.this.textureViewRotation != 0) {
                    PlayerView.this.surfaceView.addOnLayoutChangeListener(this);
                }
                PlayerView.applyTextureViewRotation((TextureView) PlayerView.this.surfaceView, PlayerView.this.textureViewRotation);
            }
            PlayerView playerView = PlayerView.this;
            playerView.onContentAspectRatioChanged(videoAspectRatio, playerView.contentFrame, PlayerView.this.surfaceView);
        }

        public void onRenderedFirstFrame() {
            if (PlayerView.this.shutterView != null) {
                PlayerView.this.shutterView.setVisibility(4);
            }
        }

        public void onTracksChanged(TrackGroupArray tracks, TrackSelectionArray selections) {
            PlayerView.this.updateForCurrentTrackSelections(false);
        }

        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            PlayerView.this.updateBuffering();
            PlayerView.this.updateErrorMessage();
            if (!PlayerView.this.isPlayingAd() || !PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.maybeShowController(false);
            } else {
                PlayerView.this.hideController();
            }
        }

        public void onPositionDiscontinuity(int reason) {
            if (PlayerView.this.isPlayingAd() && PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.hideController();
            }
        }

        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            PlayerView.applyTextureViewRotation((TextureView) view, PlayerView.this.textureViewRotation);
        }

        public void surfaceChanged(@Nullable Surface surface) {
            Player.VideoComponent videoComponent;
            if (PlayerView.this.player != null && (videoComponent = PlayerView.this.player.getVideoComponent()) != null) {
                videoComponent.setVideoSurface(surface);
            }
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return PlayerView.this.toggleControllerVisibility();
        }
    }
}
