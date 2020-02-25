package com.google.android.exoplayer2.p008ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: com.google.android.exoplayer2.ui.DefaultTimeBar */
public class DefaultTimeBar extends View implements TimeBar {
    public static final int DEFAULT_AD_MARKER_COLOR = -1291845888;
    public static final int DEFAULT_AD_MARKER_WIDTH_DP = 4;
    public static final int DEFAULT_BAR_HEIGHT_DP = 4;
    public static final int DEFAULT_PLAYED_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_DISABLED_SIZE_DP = 0;
    public static final int DEFAULT_SCRUBBER_DRAGGED_SIZE_DP = 16;
    public static final int DEFAULT_SCRUBBER_ENABLED_SIZE_DP = 12;
    public static final int DEFAULT_TOUCH_TARGET_HEIGHT_DP = 26;
    private static final String ACCESSIBILITY_CLASS_NAME = "android.widget.SeekBar";
    private static final int DEFAULT_INCREMENT_COUNT = 20;
    private static final int FINE_SCRUB_RATIO = 3;
    private static final int FINE_SCRUB_Y_THRESHOLD_DP = -50;
    private static final long STOP_SCRUBBING_TIMEOUT_MS = 1000;
    private final Paint adMarkerPaint = new Paint();
    private final int adMarkerWidth;
    private final int barHeight;
    private final Rect bufferedBar = new Rect();
    private final Paint bufferedPaint = new Paint();
    private final float density;
    private final int fineScrubYThreshold;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;
    private final CopyOnWriteArraySet<TimeBar.OnScrubListener> listeners;
    private final int[] locationOnScreen;
    private final Paint playedAdMarkerPaint = new Paint();
    private final Paint playedPaint = new Paint();
    private final Rect progressBar = new Rect();
    private final Rect scrubberBar = new Rect();
    private final int scrubberDisabledSize;
    private final int scrubberDraggedSize;
    @Nullable
    private final Drawable scrubberDrawable;
    private final int scrubberEnabledSize;
    private final int scrubberPadding;
    private final Paint scrubberPaint = new Paint();
    private final Rect seekBounds = new Rect();
    private final Runnable stopScrubbingRunnable;
    private final Point touchPosition;
    private final int touchTargetHeight;
    private final Paint unplayedPaint = new Paint();
    private int adGroupCount;
    @Nullable
    private long[] adGroupTimesMs;
    private long bufferedPosition;
    private long duration;
    private int keyCountIncrement;
    private long keyTimeIncrement;
    private int lastCoarseScrubXPosition;
    @Nullable
    private boolean[] playedAdGroups;
    private long position;
    private long scrubPosition;
    private boolean scrubbing;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefaultTimeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        AttributeSet attributeSet = attrs;
        this.scrubberPaint.setAntiAlias(true);
        this.listeners = new CopyOnWriteArraySet<>();
        this.locationOnScreen = new int[2];
        this.touchPosition = new Point();
        this.density = context.getResources().getDisplayMetrics().density;
        this.fineScrubYThreshold = dpToPx(this.density, FINE_SCRUB_Y_THRESHOLD_DP);
        int defaultBarHeight = dpToPx(this.density, 4);
        int defaultTouchTargetHeight = dpToPx(this.density, 26);
        int defaultAdMarkerWidth = dpToPx(this.density, 4);
        int defaultScrubberEnabledSize = dpToPx(this.density, 12);
        int defaultScrubberDisabledSize = dpToPx(this.density, 0);
        int defaultScrubberDraggedSize = dpToPx(this.density, 16);
        if (attributeSet != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, C0931R.styleable.DefaultTimeBar, 0, 0);
            try {
                this.scrubberDrawable = a.getDrawable(C0931R.styleable.DefaultTimeBar_scrubber_drawable);
                if (this.scrubberDrawable != null) {
                    try {
                        setDrawableLayoutDirection(this.scrubberDrawable);
                        defaultTouchTargetHeight = Math.max(this.scrubberDrawable.getMinimumHeight(), defaultTouchTargetHeight);
                    } catch (Throwable th) {
                        th = th;
                        a.recycle();
                        throw th;
                    }
                }
                this.barHeight = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_bar_height, defaultBarHeight);
                this.touchTargetHeight = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_touch_target_height, defaultTouchTargetHeight);
                this.adMarkerWidth = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_ad_marker_width, defaultAdMarkerWidth);
                this.scrubberEnabledSize = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_scrubber_enabled_size, defaultScrubberEnabledSize);
                this.scrubberDisabledSize = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_scrubber_disabled_size, defaultScrubberDisabledSize);
                this.scrubberDraggedSize = a.getDimensionPixelSize(C0931R.styleable.DefaultTimeBar_scrubber_dragged_size, defaultScrubberDraggedSize);
                int playedColor = a.getInt(C0931R.styleable.DefaultTimeBar_played_color, -1);
                int scrubberColor = a.getInt(C0931R.styleable.DefaultTimeBar_scrubber_color, getDefaultScrubberColor(playedColor));
                int bufferedColor = a.getInt(C0931R.styleable.DefaultTimeBar_buffered_color, getDefaultBufferedColor(playedColor));
                int unplayedColor = a.getInt(C0931R.styleable.DefaultTimeBar_unplayed_color, getDefaultUnplayedColor(playedColor));
                int adMarkerColor = a.getInt(C0931R.styleable.DefaultTimeBar_ad_marker_color, DEFAULT_AD_MARKER_COLOR);
                try {
                    int playedAdMarkerColor = a.getInt(C0931R.styleable.DefaultTimeBar_played_ad_marker_color, getDefaultPlayedAdMarkerColor(adMarkerColor));
                    this.playedPaint.setColor(playedColor);
                    this.scrubberPaint.setColor(scrubberColor);
                    this.bufferedPaint.setColor(bufferedColor);
                    this.unplayedPaint.setColor(unplayedColor);
                    this.adMarkerPaint.setColor(adMarkerColor);
                    this.playedAdMarkerPaint.setColor(playedAdMarkerColor);
                    a.recycle();
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
            this.barHeight = defaultBarHeight;
            this.touchTargetHeight = defaultTouchTargetHeight;
            this.adMarkerWidth = defaultAdMarkerWidth;
            this.scrubberEnabledSize = defaultScrubberEnabledSize;
            this.scrubberDisabledSize = defaultScrubberDisabledSize;
            this.scrubberDraggedSize = defaultScrubberDraggedSize;
            this.playedPaint.setColor(-1);
            this.scrubberPaint.setColor(getDefaultScrubberColor(-1));
            this.bufferedPaint.setColor(getDefaultBufferedColor(-1));
            this.unplayedPaint.setColor(getDefaultUnplayedColor(-1));
            this.adMarkerPaint.setColor((int) DEFAULT_AD_MARKER_COLOR);
            this.scrubberDrawable = null;
        }
        this.formatBuilder = new StringBuilder();
        this.formatter = new Formatter(this.formatBuilder, Locale.getDefault());
        this.stopScrubbingRunnable = new DefaultTimeBar$$Lambda$0(this);
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null) {
            this.scrubberPadding = (drawable.getMinimumWidth() + 1) / 2;
        } else {
            this.scrubberPadding = (Math.max(this.scrubberDisabledSize, Math.max(this.scrubberEnabledSize, this.scrubberDraggedSize)) + 1) / 2;
        }
        this.duration = C0841C.TIME_UNSET;
        this.keyTimeIncrement = C0841C.TIME_UNSET;
        this.keyCountIncrement = 20;
        setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    private static boolean setDrawableLayoutDirection(Drawable drawable, int layoutDirection) {
        return Util.SDK_INT >= 23 && drawable.setLayoutDirection(layoutDirection);
    }

    public static int getDefaultScrubberColor(int playedColor) {
        return -16777216 | playedColor;
    }

    public static int getDefaultUnplayedColor(int playedColor) {
        return (16777215 & playedColor) | 855638016;
    }

    public static int getDefaultBufferedColor(int playedColor) {
        return (16777215 & playedColor) | -872415232;
    }

    public static int getDefaultPlayedAdMarkerColor(int adMarkerColor) {
        return (16777215 & adMarkerColor) | 855638016;
    }

    private static int dpToPx(float density2, int dps) {
        return (int) ((((float) dps) * density2) + 0.5f);
    }

    private static int pxToDp(float density2, int px) {
        return (int) (((float) px) / density2);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$DefaultTimeBar() {
        stopScrubbing(false);
    }

    public void setPlayedColor(@ColorInt int playedColor) {
        this.playedPaint.setColor(playedColor);
        invalidate(this.seekBounds);
    }

    public void setScrubberColor(@ColorInt int scrubberColor) {
        this.scrubberPaint.setColor(scrubberColor);
        invalidate(this.seekBounds);
    }

    public void setBufferedColor(@ColorInt int bufferedColor) {
        this.bufferedPaint.setColor(bufferedColor);
        invalidate(this.seekBounds);
    }

    public void setUnplayedColor(@ColorInt int unplayedColor) {
        this.unplayedPaint.setColor(unplayedColor);
        invalidate(this.seekBounds);
    }

    public void setAdMarkerColor(@ColorInt int adMarkerColor) {
        this.adMarkerPaint.setColor(adMarkerColor);
        invalidate(this.seekBounds);
    }

    public void setPlayedAdMarkerColor(@ColorInt int playedAdMarkerColor) {
        this.playedAdMarkerPaint.setColor(playedAdMarkerColor);
        invalidate(this.seekBounds);
    }

    public void addListener(TimeBar.OnScrubListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(TimeBar.OnScrubListener listener) {
        this.listeners.remove(listener);
    }

    public void setKeyTimeIncrement(long time) {
        Assertions.checkArgument(time > 0);
        this.keyCountIncrement = -1;
        this.keyTimeIncrement = time;
    }

    public void setKeyCountIncrement(int count) {
        Assertions.checkArgument(count > 0);
        this.keyCountIncrement = count;
        this.keyTimeIncrement = C0841C.TIME_UNSET;
    }

    public void setPosition(long position2) {
        this.position = position2;
        setContentDescription(getProgressText());
        update();
    }

    public void setBufferedPosition(long bufferedPosition2) {
        this.bufferedPosition = bufferedPosition2;
        update();
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
        if (this.scrubbing && duration2 == C0841C.TIME_UNSET) {
            stopScrubbing(true);
        }
        update();
    }

    public long getPreferredUpdateDelay() {
        int timeBarWidthDp = pxToDp(this.density, this.progressBar.width());
        if (timeBarWidthDp != 0) {
            long j = this.duration;
            if (!(j == 0 || j == C0841C.TIME_UNSET)) {
                return j / ((long) timeBarWidthDp);
            }
        }
        return Long.MAX_VALUE;
    }

    public void setAdGroupTimesMs(@Nullable long[] adGroupTimesMs2, @Nullable boolean[] playedAdGroups2, int adGroupCount2) {
        Assertions.checkArgument(adGroupCount2 == 0 || !(adGroupTimesMs2 == null || playedAdGroups2 == null));
        this.adGroupCount = adGroupCount2;
        this.adGroupTimesMs = adGroupTimesMs2;
        this.playedAdGroups = playedAdGroups2;
        update();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.scrubbing && !enabled) {
            stopScrubbing(true);
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        drawTimeBar(canvas);
        drawPlayhead(canvas);
        canvas.restore();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        if (r4 != 3) goto L_0x0079;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            boolean r0 = r8.isEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x007a
            long r2 = r8.duration
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x0010
            goto L_0x007a
        L_0x0010:
            android.graphics.Point r0 = r8.resolveRelativeTouchPosition(r9)
            int r2 = r0.x
            int r3 = r0.y
            int r4 = r9.getAction()
            r5 = 1
            if (r4 == 0) goto L_0x005f
            r6 = 3
            if (r4 == r5) goto L_0x0050
            r7 = 2
            if (r4 == r7) goto L_0x0028
            if (r4 == r6) goto L_0x0050
            goto L_0x0079
        L_0x0028:
            boolean r4 = r8.scrubbing
            if (r4 == 0) goto L_0x0079
            int r1 = r8.fineScrubYThreshold
            if (r3 >= r1) goto L_0x003c
            int r1 = r8.lastCoarseScrubXPosition
            int r4 = r2 - r1
            int r6 = r4 / 3
            int r1 = r1 + r6
            float r1 = (float) r1
            r8.positionScrubber(r1)
            goto L_0x0042
        L_0x003c:
            r8.lastCoarseScrubXPosition = r2
            float r1 = (float) r2
            r8.positionScrubber(r1)
        L_0x0042:
            long r6 = r8.getScrubberPosition()
            r8.updateScrubbing(r6)
            r8.update()
            r8.invalidate()
            return r5
        L_0x0050:
            boolean r4 = r8.scrubbing
            if (r4 == 0) goto L_0x0079
            int r4 = r9.getAction()
            if (r4 != r6) goto L_0x005b
            r1 = 1
        L_0x005b:
            r8.stopScrubbing(r1)
            return r5
        L_0x005f:
            float r4 = (float) r2
            float r6 = (float) r3
            boolean r4 = r8.isInSeekBar(r4, r6)
            if (r4 == 0) goto L_0x0079
            float r1 = (float) r2
            r8.positionScrubber(r1)
            long r6 = r8.getScrubberPosition()
            r8.startScrubbing(r6)
            r8.update()
            r8.invalidate()
            return r5
        L_0x0079:
            return r1
        L_0x007a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p008ui.DefaultTimeBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r7, android.view.KeyEvent r8) {
        /*
            r6 = this;
            boolean r0 = r6.isEnabled()
            if (r0 == 0) goto L_0x0030
            long r0 = r6.getPositionIncrement()
            r2 = 66
            r3 = 1
            if (r7 == r2) goto L_0x0027
            switch(r7) {
                case 21: goto L_0x0013;
                case 22: goto L_0x0014;
                case 23: goto L_0x0027;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0030
        L_0x0013:
            long r0 = -r0
        L_0x0014:
            boolean r2 = r6.scrubIncrementally(r0)
            if (r2 == 0) goto L_0x0030
            java.lang.Runnable r2 = r6.stopScrubbingRunnable
            r6.removeCallbacks(r2)
            java.lang.Runnable r2 = r6.stopScrubbingRunnable
            r4 = 1000(0x3e8, double:4.94E-321)
            r6.postDelayed(r2, r4)
            return r3
        L_0x0027:
            boolean r2 = r6.scrubbing
            if (r2 == 0) goto L_0x0030
            r2 = 0
            r6.stopScrubbing(r2)
            return r3
        L_0x0030:
            boolean r0 = super.onKeyDown(r7, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p008ui.DefaultTimeBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (this.scrubbing && !gainFocus) {
            stopScrubbing(false);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == 0) {
            height = this.touchTargetHeight;
        } else {
            height = heightMode == 1073741824 ? heightSize : Math.min(this.touchTargetHeight, heightSize);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), height);
        updateDrawableState();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int barY = ((bottom - top) - this.touchTargetHeight) / 2;
        int i = this.touchTargetHeight;
        int progressY = ((i - this.barHeight) / 2) + barY;
        this.seekBounds.set(getPaddingLeft(), barY, (right - left) - getPaddingRight(), i + barY);
        this.progressBar.set(this.seekBounds.left + this.scrubberPadding, progressY, this.seekBounds.right - this.scrubberPadding, this.barHeight + progressY);
        update();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null && setDrawableLayoutDirection(drawable, layoutDirection)) {
            invalidate();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (event.getEventType() == 4) {
            event.getText().add(getProgressText());
        }
        event.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @TargetApi(21)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ACCESSIBILITY_CLASS_NAME);
        info.setContentDescription(getProgressText());
        if (this.duration > 0) {
            if (Util.SDK_INT >= 21) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                return;
            }
            info.addAction(4096);
            info.addAction(8192);
        }
    }

    public boolean performAccessibilityAction(int action, @Nullable Bundle args) {
        if (super.performAccessibilityAction(action, args)) {
            return true;
        }
        if (this.duration <= 0) {
            return false;
        }
        if (action == 8192) {
            if (scrubIncrementally(-getPositionIncrement())) {
                stopScrubbing(false);
            }
        } else if (action != 4096) {
            return false;
        } else {
            if (scrubIncrementally(getPositionIncrement())) {
                stopScrubbing(false);
            }
        }
        sendAccessibilityEvent(4);
        return true;
    }

    private void startScrubbing(long scrubPosition2) {
        this.scrubPosition = scrubPosition2;
        this.scrubbing = true;
        setPressed(true);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStart(this, scrubPosition2);
        }
    }

    private void updateScrubbing(long scrubPosition2) {
        if (this.scrubPosition != scrubPosition2) {
            this.scrubPosition = scrubPosition2;
            Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onScrubMove(this, scrubPosition2);
            }
        }
    }

    private void stopScrubbing(boolean canceled) {
        removeCallbacks(this.stopScrubbingRunnable);
        this.scrubbing = false;
        setPressed(false);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStop(this, this.scrubPosition, canceled);
        }
    }

    private boolean scrubIncrementally(long positionChange) {
        if (this.duration <= 0) {
            return false;
        }
        long previousPosition = this.scrubbing ? this.scrubPosition : this.position;
        long scrubPosition2 = Util.constrainValue(previousPosition + positionChange, 0, this.duration);
        if (scrubPosition2 == previousPosition) {
            return false;
        }
        if (!this.scrubbing) {
            startScrubbing(scrubPosition2);
        } else {
            updateScrubbing(scrubPosition2);
        }
        update();
        return true;
    }

    private void update() {
        this.bufferedBar.set(this.progressBar);
        this.scrubberBar.set(this.progressBar);
        long newScrubberTime = this.scrubbing ? this.scrubPosition : this.position;
        if (this.duration > 0) {
            this.bufferedBar.right = Math.min(this.progressBar.left + ((int) ((((long) this.progressBar.width()) * this.bufferedPosition) / this.duration)), this.progressBar.right);
            this.scrubberBar.right = Math.min(this.progressBar.left + ((int) ((((long) this.progressBar.width()) * newScrubberTime) / this.duration)), this.progressBar.right);
        } else {
            this.bufferedBar.right = this.progressBar.left;
            this.scrubberBar.right = this.progressBar.left;
        }
        invalidate(this.seekBounds);
    }

    private void positionScrubber(float xPosition) {
        this.scrubberBar.right = Util.constrainValue((int) xPosition, this.progressBar.left, this.progressBar.right);
    }

    private Point resolveRelativeTouchPosition(MotionEvent motionEvent) {
        getLocationOnScreen(this.locationOnScreen);
        this.touchPosition.set(((int) motionEvent.getRawX()) - this.locationOnScreen[0], ((int) motionEvent.getRawY()) - this.locationOnScreen[1]);
        return this.touchPosition;
    }

    private long getScrubberPosition() {
        if (this.progressBar.width() <= 0 || this.duration == C0841C.TIME_UNSET) {
            return 0;
        }
        return (((long) this.scrubberBar.width()) * this.duration) / ((long) this.progressBar.width());
    }

    private boolean isInSeekBar(float x, float y) {
        return this.seekBounds.contains((int) x, (int) y);
    }

    private void drawTimeBar(Canvas canvas) {
        DefaultTimeBar defaultTimeBar = this;
        int progressBarHeight = defaultTimeBar.progressBar.height();
        int barTop = defaultTimeBar.progressBar.centerY() - (progressBarHeight / 2);
        int barBottom = barTop + progressBarHeight;
        if (defaultTimeBar.duration <= 0) {
            canvas.drawRect((float) defaultTimeBar.progressBar.left, (float) barTop, (float) defaultTimeBar.progressBar.right, (float) barBottom, defaultTimeBar.unplayedPaint);
            return;
        }
        int bufferedLeft = defaultTimeBar.bufferedBar.left;
        int bufferedRight = defaultTimeBar.bufferedBar.right;
        int progressLeft = Math.max(Math.max(defaultTimeBar.progressBar.left, bufferedRight), defaultTimeBar.scrubberBar.right);
        if (progressLeft < defaultTimeBar.progressBar.right) {
            canvas.drawRect((float) progressLeft, (float) barTop, (float) defaultTimeBar.progressBar.right, (float) barBottom, defaultTimeBar.unplayedPaint);
        }
        int bufferedLeft2 = Math.max(bufferedLeft, defaultTimeBar.scrubberBar.right);
        if (bufferedRight > bufferedLeft2) {
            canvas.drawRect((float) bufferedLeft2, (float) barTop, (float) bufferedRight, (float) barBottom, defaultTimeBar.bufferedPaint);
        }
        if (defaultTimeBar.scrubberBar.width() > 0) {
            canvas.drawRect((float) defaultTimeBar.scrubberBar.left, (float) barTop, (float) defaultTimeBar.scrubberBar.right, (float) barBottom, defaultTimeBar.playedPaint);
        }
        if (defaultTimeBar.adGroupCount != 0) {
            long[] adGroupTimesMs2 = (long[]) Assertions.checkNotNull(defaultTimeBar.adGroupTimesMs);
            boolean[] playedAdGroups2 = (boolean[]) Assertions.checkNotNull(defaultTimeBar.playedAdGroups);
            int adMarkerOffset = defaultTimeBar.adMarkerWidth / 2;
            int i = 0;
            while (i < defaultTimeBar.adGroupCount) {
                int bufferedLeft3 = bufferedLeft2;
                int bufferedRight2 = bufferedRight;
                int markerLeft = defaultTimeBar.progressBar.left + Math.min(defaultTimeBar.progressBar.width() - defaultTimeBar.adMarkerWidth, Math.max(0, ((int) ((((long) defaultTimeBar.progressBar.width()) * Util.constrainValue(adGroupTimesMs2[i], 0, defaultTimeBar.duration)) / defaultTimeBar.duration)) - adMarkerOffset));
                canvas.drawRect((float) markerLeft, (float) barTop, (float) (defaultTimeBar.adMarkerWidth + markerLeft), (float) barBottom, playedAdGroups2[i] ? defaultTimeBar.playedAdMarkerPaint : defaultTimeBar.adMarkerPaint);
                i++;
                defaultTimeBar = this;
                bufferedRight = bufferedRight2;
                progressBarHeight = progressBarHeight;
                bufferedLeft2 = bufferedLeft3;
            }
        }
    }

    private void drawPlayhead(Canvas canvas) {
        int scrubberSize;
        if (this.duration > 0) {
            int playheadX = Util.constrainValue(this.scrubberBar.right, this.scrubberBar.left, this.progressBar.right);
            int playheadY = this.scrubberBar.centerY();
            Drawable drawable = this.scrubberDrawable;
            if (drawable == null) {
                if (this.scrubbing || isFocused()) {
                    scrubberSize = this.scrubberDraggedSize;
                } else {
                    scrubberSize = isEnabled() ? this.scrubberEnabledSize : this.scrubberDisabledSize;
                }
                canvas.drawCircle((float) playheadX, (float) playheadY, (float) (scrubberSize / 2), this.scrubberPaint);
                return;
            }
            int scrubberDrawableWidth = drawable.getIntrinsicWidth();
            int scrubberDrawableHeight = this.scrubberDrawable.getIntrinsicHeight();
            this.scrubberDrawable.setBounds(playheadX - (scrubberDrawableWidth / 2), playheadY - (scrubberDrawableHeight / 2), (scrubberDrawableWidth / 2) + playheadX, (scrubberDrawableHeight / 2) + playheadY);
            this.scrubberDrawable.draw(canvas);
        }
    }

    private void updateDrawableState() {
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null && drawable.isStateful() && this.scrubberDrawable.setState(getDrawableState())) {
            invalidate();
        }
    }

    private String getProgressText() {
        return Util.getStringForTime(this.formatBuilder, this.formatter, this.position);
    }

    private long getPositionIncrement() {
        long j = this.keyTimeIncrement;
        if (j != C0841C.TIME_UNSET) {
            return j;
        }
        long j2 = this.duration;
        if (j2 == C0841C.TIME_UNSET) {
            return 0;
        }
        return j2 / ((long) this.keyCountIncrement);
    }

    private boolean setDrawableLayoutDirection(Drawable drawable) {
        return Util.SDK_INT >= 23 && setDrawableLayoutDirection(drawable, getLayoutDirection());
    }
}
