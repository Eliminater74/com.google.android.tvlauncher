package com.google.android.exoplayer2.p008ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player$EventListener$$CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;

/* renamed from: com.google.android.exoplayer2.ui.PlayerControlView */
public class PlayerControlView extends FrameLayout {
    public static final int DEFAULT_FAST_FORWARD_MS = 15000;
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_REWIND_MS = 5000;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    private static final int MAX_UPDATE_INTERVAL_MS = 1000;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    /* access modifiers changed from: private */
    public final View fastForwardButton;
    /* access modifiers changed from: private */
    public final StringBuilder formatBuilder;
    /* access modifiers changed from: private */
    public final Formatter formatter;
    /* access modifiers changed from: private */
    public final View nextButton;
    /* access modifiers changed from: private */
    public final View pauseButton;
    /* access modifiers changed from: private */
    public final View playButton;
    /* access modifiers changed from: private */
    public final TextView positionView;
    /* access modifiers changed from: private */
    public final View previousButton;
    /* access modifiers changed from: private */
    public final ImageView repeatToggleButton;
    /* access modifiers changed from: private */
    public final View rewindButton;
    /* access modifiers changed from: private */
    public final View shuffleButton;
    private final ComponentListener componentListener;
    private final TextView durationView;
    private final Runnable hideAction;
    private final Timeline.Period period;
    private final String repeatAllButtonContentDescription;
    private final Drawable repeatAllButtonDrawable;
    private final String repeatOffButtonContentDescription;
    private final Drawable repeatOffButtonDrawable;
    private final String repeatOneButtonContentDescription;
    private final Drawable repeatOneButtonDrawable;
    private final TimeBar timeBar;
    private final Runnable updateProgressAction;
    private final View vrButton;
    private final Timeline.Window window;
    /* access modifiers changed from: private */
    public ControlDispatcher controlDispatcher;
    /* access modifiers changed from: private */
    @Nullable
    public PlaybackPreparer playbackPreparer;
    /* access modifiers changed from: private */
    @Nullable
    public Player player;
    /* access modifiers changed from: private */
    public int repeatToggleModes;
    /* access modifiers changed from: private */
    public boolean scrubbing;
    private long[] adGroupTimesMs;
    private long currentWindowOffset;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;
    private int fastForwardMs;
    private long hideAtMs;
    private boolean isAttachedToWindow;
    private boolean multiWindowTimeBar;
    private boolean[] playedAdGroups;
    private int rewindMs;
    private boolean showMultiWindowTimeBar;
    private boolean showShuffleButton;
    private int showTimeoutMs;
    private int timeBarMinUpdateIntervalMs;
    private VisibilityListener visibilityListener;

    public PlayerControlView(Context context) {
        this(context, null);
    }

    public PlayerControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, attrs);
    }

    public PlayerControlView(Context context, AttributeSet attrs, int defStyleAttr, AttributeSet playbackAttrs) {
        super(context, attrs, defStyleAttr);
        int controllerLayoutId = C0931R.layout.exo_player_control_view;
        this.rewindMs = 5000;
        this.fastForwardMs = 15000;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.timeBarMinUpdateIntervalMs = 200;
        this.hideAtMs = C0841C.TIME_UNSET;
        this.showShuffleButton = false;
        if (playbackAttrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(playbackAttrs, C0931R.styleable.PlayerControlView, 0, 0);
            try {
                this.rewindMs = a.getInt(C0931R.styleable.PlayerControlView_rewind_increment, this.rewindMs);
                this.fastForwardMs = a.getInt(C0931R.styleable.PlayerControlView_fastforward_increment, this.fastForwardMs);
                this.showTimeoutMs = a.getInt(C0931R.styleable.PlayerControlView_show_timeout, this.showTimeoutMs);
                controllerLayoutId = a.getResourceId(C0931R.styleable.PlayerControlView_controller_layout_id, controllerLayoutId);
                this.repeatToggleModes = getRepeatToggleModes(a, this.repeatToggleModes);
                this.showShuffleButton = a.getBoolean(C0931R.styleable.PlayerControlView_show_shuffle_button, this.showShuffleButton);
                setTimeBarMinUpdateInterval(a.getInt(C0931R.styleable.PlayerControlView_time_bar_min_update_interval, this.timeBarMinUpdateIntervalMs));
            } finally {
                a.recycle();
            }
        }
        this.period = new Timeline.Period();
        this.window = new Timeline.Window();
        this.formatBuilder = new StringBuilder();
        this.formatter = new Formatter(this.formatBuilder, Locale.getDefault());
        this.adGroupTimesMs = new long[0];
        this.playedAdGroups = new boolean[0];
        this.extraAdGroupTimesMs = new long[0];
        this.extraPlayedAdGroups = new boolean[0];
        this.componentListener = new ComponentListener();
        this.controlDispatcher = new DefaultControlDispatcher();
        this.updateProgressAction = new PlayerControlView$$Lambda$0(this);
        this.hideAction = new PlayerControlView$$Lambda$1(this);
        LayoutInflater.from(context).inflate(controllerLayoutId, this);
        setDescendantFocusability(262144);
        this.durationView = (TextView) findViewById(C0931R.C0933id.exo_duration);
        this.positionView = (TextView) findViewById(C0931R.C0933id.exo_position);
        this.timeBar = (TimeBar) findViewById(C0931R.C0933id.exo_progress);
        TimeBar timeBar2 = this.timeBar;
        if (timeBar2 != null) {
            timeBar2.addListener(this.componentListener);
        }
        this.playButton = findViewById(C0931R.C0933id.exo_play);
        View view = this.playButton;
        if (view != null) {
            view.setOnClickListener(this.componentListener);
        }
        this.pauseButton = findViewById(C0931R.C0933id.exo_pause);
        View view2 = this.pauseButton;
        if (view2 != null) {
            view2.setOnClickListener(this.componentListener);
        }
        this.previousButton = findViewById(C0931R.C0933id.exo_prev);
        View view3 = this.previousButton;
        if (view3 != null) {
            view3.setOnClickListener(this.componentListener);
        }
        this.nextButton = findViewById(C0931R.C0933id.exo_next);
        View view4 = this.nextButton;
        if (view4 != null) {
            view4.setOnClickListener(this.componentListener);
        }
        this.rewindButton = findViewById(C0931R.C0933id.exo_rew);
        View view5 = this.rewindButton;
        if (view5 != null) {
            view5.setOnClickListener(this.componentListener);
        }
        this.fastForwardButton = findViewById(C0931R.C0933id.exo_ffwd);
        View view6 = this.fastForwardButton;
        if (view6 != null) {
            view6.setOnClickListener(this.componentListener);
        }
        this.repeatToggleButton = (ImageView) findViewById(C0931R.C0933id.exo_repeat_toggle);
        ImageView imageView = this.repeatToggleButton;
        if (imageView != null) {
            imageView.setOnClickListener(this.componentListener);
        }
        this.shuffleButton = findViewById(C0931R.C0933id.exo_shuffle);
        View view7 = this.shuffleButton;
        if (view7 != null) {
            view7.setOnClickListener(this.componentListener);
        }
        this.vrButton = findViewById(C0931R.C0933id.exo_vr);
        setShowVrButton(false);
        Resources resources = context.getResources();
        this.repeatOffButtonDrawable = resources.getDrawable(C0931R.C0932drawable.exo_controls_repeat_off);
        this.repeatOneButtonDrawable = resources.getDrawable(C0931R.C0932drawable.exo_controls_repeat_one);
        this.repeatAllButtonDrawable = resources.getDrawable(C0931R.C0932drawable.exo_controls_repeat_all);
        this.repeatOffButtonContentDescription = resources.getString(C0931R.string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = resources.getString(C0931R.string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = resources.getString(C0931R.string.exo_controls_repeat_all_description);
    }

    private static int getRepeatToggleModes(TypedArray a, int repeatToggleModes2) {
        return a.getInt(C0931R.styleable.PlayerControlView_repeat_toggle_modes, repeatToggleModes2);
    }

    @SuppressLint({"InlinedApi"})
    private static boolean isHandledMediaKey(int keyCode) {
        return keyCode == 90 || keyCode == 89 || keyCode == 85 || keyCode == 126 || keyCode == 127 || keyCode == 87 || keyCode == 88;
    }

    private static boolean canShowMultiWindowTimeBar(Timeline timeline, Timeline.Window window2) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, window2).durationUs == C0841C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(@Nullable Player player2) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.componentListener);
            }
            this.player = player2;
            if (player2 != null) {
                player2.addListener(this.componentListener);
            }
            updateAll();
        }
    }

    public void setShowMultiWindowTimeBar(boolean showMultiWindowTimeBar2) {
        this.showMultiWindowTimeBar = showMultiWindowTimeBar2;
        updateTimeline();
    }

    public void setExtraAdGroupMarkers(@Nullable long[] extraAdGroupTimesMs2, @Nullable boolean[] extraPlayedAdGroups2) {
        boolean z = false;
        if (extraAdGroupTimesMs2 == null) {
            this.extraAdGroupTimesMs = new long[0];
            this.extraPlayedAdGroups = new boolean[0];
        } else {
            boolean[] extraPlayedAdGroups3 = (boolean[]) Assertions.checkNotNull(extraPlayedAdGroups2);
            if (extraAdGroupTimesMs2.length == extraPlayedAdGroups3.length) {
                z = true;
            }
            Assertions.checkArgument(z);
            this.extraAdGroupTimesMs = extraAdGroupTimesMs2;
            this.extraPlayedAdGroups = extraPlayedAdGroups3;
        }
        updateTimeline();
    }

    public void setVisibilityListener(VisibilityListener listener) {
        this.visibilityListener = listener;
    }

    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer2) {
        this.playbackPreparer = playbackPreparer2;
    }

    public void setControlDispatcher(@Nullable ControlDispatcher controlDispatcher2) {
        ControlDispatcher controlDispatcher3;
        if (controlDispatcher2 == null) {
            controlDispatcher3 = new DefaultControlDispatcher();
        } else {
            controlDispatcher3 = controlDispatcher2;
        }
        this.controlDispatcher = controlDispatcher3;
    }

    public void setRewindIncrementMs(int rewindMs2) {
        this.rewindMs = rewindMs2;
        updateNavigation();
    }

    public void setFastForwardIncrementMs(int fastForwardMs2) {
        this.fastForwardMs = fastForwardMs2;
        updateNavigation();
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public void setShowTimeoutMs(int showTimeoutMs2) {
        this.showTimeoutMs = showTimeoutMs2;
        if (isVisible()) {
            hideAfterTimeout();
        }
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public void setRepeatToggleModes(int repeatToggleModes2) {
        this.repeatToggleModes = repeatToggleModes2;
        Player player2 = this.player;
        if (player2 != null) {
            int currentMode = player2.getRepeatMode();
            if (repeatToggleModes2 == 0 && currentMode != 0) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 0);
            } else if (repeatToggleModes2 == 1 && currentMode == 2) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 1);
            } else if (repeatToggleModes2 == 2 && currentMode == 1) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 2);
            }
        }
        updateRepeatModeButton();
    }

    public boolean getShowShuffleButton() {
        return this.showShuffleButton;
    }

    public void setShowShuffleButton(boolean showShuffleButton2) {
        this.showShuffleButton = showShuffleButton2;
        updateShuffleButton();
    }

    public boolean getShowVrButton() {
        View view = this.vrButton;
        return view != null && view.getVisibility() == 0;
    }

    public void setShowVrButton(boolean showVrButton) {
        View view = this.vrButton;
        if (view != null) {
            view.setVisibility(showVrButton ? 0 : 8);
        }
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        View view = this.vrButton;
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setTimeBarMinUpdateInterval(int minUpdateIntervalMs) {
        this.timeBarMinUpdateIntervalMs = Util.constrainValue(minUpdateIntervalMs, 16, 1000);
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(0);
            VisibilityListener visibilityListener2 = this.visibilityListener;
            if (visibilityListener2 != null) {
                visibilityListener2.onVisibilityChange(getVisibility());
            }
            updateAll();
            requestPlayPauseFocus();
        }
        hideAfterTimeout();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            VisibilityListener visibilityListener2 = this.visibilityListener;
            if (visibilityListener2 != null) {
                visibilityListener2.onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.updateProgressAction);
            removeCallbacks(this.hideAction);
            this.hideAtMs = C0841C.TIME_UNSET;
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    private void hideAfterTimeout() {
        removeCallbacks(this.hideAction);
        if (this.showTimeoutMs > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = this.showTimeoutMs;
            this.hideAtMs = uptimeMillis + ((long) i);
            if (this.isAttachedToWindow) {
                postDelayed(this.hideAction, (long) i);
                return;
            }
            return;
        }
        this.hideAtMs = C0841C.TIME_UNSET;
    }

    private void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateTimeline();
    }

    /* access modifiers changed from: private */
    public void updatePlayPauseButton() {
        if (isVisible() && this.isAttachedToWindow) {
            boolean requestPlayPauseFocus = false;
            boolean playing = isPlaying();
            View view = this.playButton;
            int i = 8;
            boolean z = true;
            if (view != null) {
                requestPlayPauseFocus = false | (playing && view.isFocused());
                this.playButton.setVisibility(playing ? 8 : 0);
            }
            View view2 = this.pauseButton;
            if (view2 != null) {
                if (playing || !view2.isFocused()) {
                    z = false;
                }
                requestPlayPauseFocus |= z;
                View view3 = this.pauseButton;
                if (playing) {
                    i = 0;
                }
                view3.setVisibility(i);
            }
            if (requestPlayPauseFocus) {
                requestPlayPauseFocus();
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateNavigation() {
        if (isVisible() && this.isAttachedToWindow) {
            boolean enableSeeking = false;
            boolean enablePrevious = false;
            boolean enableRewind = false;
            boolean enableFastForward = false;
            boolean enableNext = false;
            Player player2 = this.player;
            if (player2 != null) {
                Timeline timeline = player2.getCurrentTimeline();
                if (!timeline.isEmpty() && !this.player.isPlayingAd()) {
                    timeline.getWindow(this.player.getCurrentWindowIndex(), this.window);
                    boolean isSeekable = this.window.isSeekable;
                    enableSeeking = isSeekable;
                    boolean z = false;
                    enablePrevious = isSeekable || !this.window.isDynamic || this.player.hasPrevious();
                    enableRewind = isSeekable && this.rewindMs > 0;
                    enableFastForward = isSeekable && this.fastForwardMs > 0;
                    if (this.window.isDynamic || this.player.hasNext()) {
                        z = true;
                    }
                    enableNext = z;
                }
            }
            setButtonEnabled(enablePrevious, this.previousButton);
            setButtonEnabled(enableRewind, this.rewindButton);
            setButtonEnabled(enableFastForward, this.fastForwardButton);
            setButtonEnabled(enableNext, this.nextButton);
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setEnabled(enableSeeking);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateRepeatModeButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.repeatToggleButton) != null) {
            if (this.repeatToggleModes == 0) {
                imageView.setVisibility(8);
            } else if (this.player == null) {
                setButtonEnabled(false, imageView);
            } else {
                setButtonEnabled(true, imageView);
                int repeatMode = this.player.getRepeatMode();
                if (repeatMode == 0) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                } else if (repeatMode == 1) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
                } else if (repeatMode == 2) {
                    this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
                }
                this.repeatToggleButton.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateShuffleButton() {
        View view;
        if (isVisible() && this.isAttachedToWindow && (view = this.shuffleButton) != null) {
            if (!this.showShuffleButton) {
                view.setVisibility(8);
                return;
            }
            Player player2 = this.player;
            if (player2 == null) {
                setButtonEnabled(false, view);
                return;
            }
            view.setAlpha(player2.getShuffleModeEnabled() ? 1.0f : 0.3f);
            this.shuffleButton.setEnabled(true);
            this.shuffleButton.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void updateTimeline() {
        long j;
        long adGroupTimeInPeriodUs;
        Player player2 = this.player;
        if (player2 != null) {
            boolean z = true;
            this.multiWindowTimeBar = this.showMultiWindowTimeBar && canShowMultiWindowTimeBar(player2.getCurrentTimeline(), this.window);
            long j2 = 0;
            this.currentWindowOffset = 0;
            long durationUs = 0;
            int adGroupCount = 0;
            Timeline timeline = this.player.getCurrentTimeline();
            if (!timeline.isEmpty()) {
                int currentWindowIndex = this.player.getCurrentWindowIndex();
                int firstWindowIndex = this.multiWindowTimeBar ? 0 : currentWindowIndex;
                int lastWindowIndex = this.multiWindowTimeBar ? timeline.getWindowCount() - 1 : currentWindowIndex;
                int i = firstWindowIndex;
                while (true) {
                    if (i > lastWindowIndex) {
                        break;
                    }
                    if (i == currentWindowIndex) {
                        this.currentWindowOffset = C0841C.usToMs(durationUs);
                    }
                    timeline.getWindow(i, this.window);
                    if (this.window.durationUs == C0841C.TIME_UNSET) {
                        Assertions.checkState(this.multiWindowTimeBar ^ z);
                        break;
                    }
                    int j3 = this.window.firstPeriodIndex;
                    while (j3 <= this.window.lastPeriodIndex) {
                        timeline.getPeriod(j3, this.period);
                        int periodAdGroupCount = this.period.getAdGroupCount();
                        int adGroupCount2 = adGroupCount;
                        int adGroupIndex = 0;
                        while (adGroupIndex < periodAdGroupCount) {
                            long adGroupTimeInPeriodUs2 = this.period.getAdGroupTimeUs(adGroupIndex);
                            if (adGroupTimeInPeriodUs2 != Long.MIN_VALUE) {
                                adGroupTimeInPeriodUs = adGroupTimeInPeriodUs2;
                            } else if (this.period.durationUs == C0841C.TIME_UNSET) {
                                j = 0;
                                adGroupIndex++;
                                j2 = j;
                            } else {
                                adGroupTimeInPeriodUs = this.period.durationUs;
                            }
                            long adGroupTimeInWindowUs = adGroupTimeInPeriodUs + this.period.getPositionInWindowUs();
                            j = 0;
                            if (adGroupTimeInWindowUs >= 0) {
                                if (adGroupTimeInWindowUs <= this.window.durationUs) {
                                    long[] jArr = this.adGroupTimesMs;
                                    if (adGroupCount2 == jArr.length) {
                                        int newLength = jArr.length == 0 ? 1 : jArr.length * 2;
                                        this.adGroupTimesMs = Arrays.copyOf(this.adGroupTimesMs, newLength);
                                        this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, newLength);
                                    }
                                    this.adGroupTimesMs[adGroupCount2] = C0841C.usToMs(durationUs + adGroupTimeInWindowUs);
                                    this.playedAdGroups[adGroupCount2] = this.period.hasPlayedAdGroup(adGroupIndex);
                                    adGroupCount2++;
                                }
                            }
                            adGroupIndex++;
                            j2 = j;
                        }
                        j3++;
                        adGroupCount = adGroupCount2;
                    }
                    durationUs += this.window.durationUs;
                    i++;
                    j2 = j2;
                    z = true;
                }
            }
            long durationMs = C0841C.usToMs(durationUs);
            TextView textView = this.durationView;
            if (textView != null) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, durationMs));
            }
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setDuration(durationMs);
                int extraAdGroupCount = this.extraAdGroupTimesMs.length;
                int totalAdGroupCount = adGroupCount + extraAdGroupCount;
                long[] jArr2 = this.adGroupTimesMs;
                if (totalAdGroupCount > jArr2.length) {
                    this.adGroupTimesMs = Arrays.copyOf(jArr2, totalAdGroupCount);
                    this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, totalAdGroupCount);
                }
                System.arraycopy(this.extraAdGroupTimesMs, 0, this.adGroupTimesMs, adGroupCount, extraAdGroupCount);
                System.arraycopy(this.extraPlayedAdGroups, 0, this.playedAdGroups, adGroupCount, extraAdGroupCount);
                this.timeBar.setAdGroupTimesMs(this.adGroupTimesMs, this.playedAdGroups, totalAdGroupCount);
            }
            bridge$lambda$0$PlayerControlView();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: updateProgress */
    public void bridge$lambda$0$PlayerControlView() {
        if (isVisible() && this.isAttachedToWindow) {
            long position = 0;
            long bufferedPosition = 0;
            Player player2 = this.player;
            if (player2 != null) {
                position = this.currentWindowOffset + player2.getContentPosition();
                bufferedPosition = this.currentWindowOffset + this.player.getContentBufferedPosition();
            }
            TextView textView = this.positionView;
            if (textView != null && !this.scrubbing) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, position));
            }
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setPosition(position);
                this.timeBar.setBufferedPosition(bufferedPosition);
            }
            removeCallbacks(this.updateProgressAction);
            Player player3 = this.player;
            int playbackState = player3 == null ? 1 : player3.getPlaybackState();
            long delayMs = 1000;
            if (playbackState == 3 && this.player.getPlayWhenReady()) {
                TimeBar timeBar3 = this.timeBar;
                long mediaTimeDelayMs = Math.min(timeBar3 != null ? timeBar3.getPreferredUpdateDelay() : 1000, 1000 - (position % 1000));
                float playbackSpeed = this.player.getPlaybackParameters().speed;
                if (playbackSpeed > 0.0f) {
                    delayMs = (long) (((float) mediaTimeDelayMs) / playbackSpeed);
                }
                postDelayed(this.updateProgressAction, Util.constrainValue(delayMs, (long) this.timeBarMinUpdateIntervalMs, 1000));
            } else if (playbackState != 4 && playbackState != 1) {
                postDelayed(this.updateProgressAction, 1000);
            }
        }
    }

    private void requestPlayPauseFocus() {
        View view;
        View view2;
        boolean playing = isPlaying();
        if (!playing && (view2 = this.playButton) != null) {
            view2.requestFocus();
        } else if (playing && (view = this.pauseButton) != null) {
            view.requestFocus();
        }
    }

    private void setButtonEnabled(boolean enabled, View view) {
        if (view != null) {
            view.setEnabled(enabled);
            view.setAlpha(enabled ? 1.0f : 0.3f);
            view.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void previous(Player player2) {
        Timeline timeline = player2.getCurrentTimeline();
        if (!timeline.isEmpty() && !player2.isPlayingAd()) {
            timeline.getWindow(player2.getCurrentWindowIndex(), this.window);
            int previousWindowIndex = player2.getPreviousWindowIndex();
            if (previousWindowIndex == -1 || (player2.getCurrentPosition() > MAX_POSITION_FOR_SEEK_TO_PREVIOUS && (!this.window.isDynamic || this.window.isSeekable))) {
                seekTo(player2, 0);
            } else {
                seekTo(player2, previousWindowIndex, C0841C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void next(Player player2) {
        Timeline timeline = player2.getCurrentTimeline();
        if (!timeline.isEmpty() && !player2.isPlayingAd()) {
            int windowIndex = player2.getCurrentWindowIndex();
            int nextWindowIndex = player2.getNextWindowIndex();
            if (nextWindowIndex != -1) {
                seekTo(player2, nextWindowIndex, C0841C.TIME_UNSET);
            } else if (timeline.getWindow(windowIndex, this.window).isDynamic) {
                seekTo(player2, windowIndex, C0841C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void rewind(Player player2) {
        if (player2.isCurrentWindowSeekable() && this.rewindMs > 0) {
            seekTo(player2, player2.getCurrentPosition() - ((long) this.rewindMs));
        }
    }

    /* access modifiers changed from: private */
    public void fastForward(Player player2) {
        if (player2.isCurrentWindowSeekable() && this.fastForwardMs > 0) {
            seekTo(player2, player2.getCurrentPosition() + ((long) this.fastForwardMs));
        }
    }

    private void seekTo(Player player2, long positionMs) {
        seekTo(player2, player2.getCurrentWindowIndex(), positionMs);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private boolean seekTo(Player player2, int windowIndex, long positionMs) {
        long durationMs = player2.getDuration();
        if (durationMs != C0841C.TIME_UNSET) {
            positionMs = Math.min(positionMs, durationMs);
        }
        return this.controlDispatcher.dispatchSeekTo(player2, windowIndex, Math.max(positionMs, 0L));
    }

    /* access modifiers changed from: private */
    public void seekToTimeBarPosition(Player player2, long positionMs) {
        int windowIndex;
        Timeline timeline = player2.getCurrentTimeline();
        if (this.multiWindowTimeBar && !timeline.isEmpty()) {
            int windowCount = timeline.getWindowCount();
            windowIndex = 0;
            while (true) {
                long windowDurationMs = timeline.getWindow(windowIndex, this.window).getDurationMs();
                if (positionMs < windowDurationMs) {
                    break;
                } else if (windowIndex == windowCount - 1) {
                    positionMs = windowDurationMs;
                    break;
                } else {
                    positionMs -= windowDurationMs;
                    windowIndex++;
                }
            }
        } else {
            windowIndex = player2.getCurrentWindowIndex();
        }
        if (!seekTo(player2, windowIndex, positionMs)) {
            bridge$lambda$0$PlayerControlView();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;
        long j = this.hideAtMs;
        if (j != C0841C.TIME_UNSET) {
            long delayMs = j - SystemClock.uptimeMillis();
            if (delayMs <= 0) {
                hide();
            } else {
                postDelayed(this.hideAction, delayMs);
            }
        } else if (isVisible()) {
            hideAfterTimeout();
        }
        updateAll();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        removeCallbacks(this.hideAction);
    }

    public final boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            removeCallbacks(this.hideAction);
        } else if (ev.getAction() == 1) {
            hideAfterTimeout();
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this.player == null || !isHandledMediaKey(keyCode)) {
            return false;
        }
        if (event.getAction() == 0) {
            if (keyCode == 90) {
                fastForward(this.player);
            } else if (keyCode == 89) {
                rewind(this.player);
            } else if (event.getRepeatCount() == 0) {
                if (keyCode == 85) {
                    ControlDispatcher controlDispatcher2 = this.controlDispatcher;
                    Player player2 = this.player;
                    controlDispatcher2.dispatchSetPlayWhenReady(player2, !player2.getPlayWhenReady());
                } else if (keyCode == 87) {
                    next(this.player);
                } else if (keyCode == 88) {
                    previous(this.player);
                } else if (keyCode == 126) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(this.player, true);
                } else if (keyCode == 127) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(this.player, false);
                }
            }
        }
        return true;
    }

    private boolean isPlaying() {
        Player player2 = this.player;
        if (player2 == null || player2.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) {
            return false;
        }
        return true;
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$VisibilityListener */
    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$ComponentListener */
    private final class ComponentListener implements Player.EventListener, TimeBar.OnScrubListener, View.OnClickListener {
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

        public void onSeekProcessed() {
            Player$EventListener$$CC.onSeekProcessed$$dflt$$(this);
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            Player$EventListener$$CC.onTracksChanged$$dflt$$(this, trackGroupArray, trackSelectionArray);
        }

        public void onScrubStart(TimeBar timeBar, long position) {
            boolean unused = PlayerControlView.this.scrubbing = true;
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, position));
            }
        }

        public void onScrubMove(TimeBar timeBar, long position) {
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, position));
            }
        }

        public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
            boolean unused = PlayerControlView.this.scrubbing = false;
            if (!canceled && PlayerControlView.this.player != null) {
                PlayerControlView playerControlView = PlayerControlView.this;
                playerControlView.seekToTimeBarPosition(playerControlView.player, position);
            }
        }

        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            PlayerControlView.this.updatePlayPauseButton();
            PlayerControlView.this.bridge$lambda$0$PlayerControlView();
        }

        public void onRepeatModeChanged(int repeatMode) {
            PlayerControlView.this.updateRepeatModeButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            PlayerControlView.this.updateShuffleButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onPositionDiscontinuity(int reason) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateTimeline();
        }

        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateTimeline();
        }

        public void onClick(View view) {
            Player player = PlayerControlView.this.player;
            if (player != null) {
                if (PlayerControlView.this.nextButton == view) {
                    PlayerControlView.this.next(player);
                } else if (PlayerControlView.this.previousButton == view) {
                    PlayerControlView.this.previous(player);
                } else if (PlayerControlView.this.fastForwardButton == view) {
                    PlayerControlView.this.fastForward(player);
                } else if (PlayerControlView.this.rewindButton == view) {
                    PlayerControlView.this.rewind(player);
                } else if (PlayerControlView.this.playButton == view) {
                    if (player.getPlaybackState() == 1) {
                        if (PlayerControlView.this.playbackPreparer != null) {
                            PlayerControlView.this.playbackPreparer.preparePlayback();
                        }
                    } else if (player.getPlaybackState() == 4) {
                        PlayerControlView.this.controlDispatcher.dispatchSeekTo(player, player.getCurrentWindowIndex(), C0841C.TIME_UNSET);
                    }
                    PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(player, true);
                } else if (PlayerControlView.this.pauseButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(player, false);
                } else if (PlayerControlView.this.repeatToggleButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), PlayerControlView.this.repeatToggleModes));
                } else if (PlayerControlView.this.shuffleButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetShuffleModeEnabled(player, true ^ player.getShuffleModeEnabled());
                }
            }
        }
    }
}
