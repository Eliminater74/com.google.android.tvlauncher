package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.leanback.C0364R;
import androidx.leanback.util.MathUtil;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

public class PlaybackControlsRow extends Row {
    private long mBufferedProgressMs;
    private long mCurrentTimeMs;
    private Drawable mImageDrawable;
    private Object mItem;
    private OnPlaybackProgressCallback mListener;
    private ObjectAdapter mPrimaryActionsAdapter;
    private ObjectAdapter mSecondaryActionsAdapter;
    private long mTotalTimeMs;

    public PlaybackControlsRow(Object item) {
        this.mItem = item;
    }

    public PlaybackControlsRow() {
    }

    static Bitmap createBitmap(Bitmap bitmap, int color) {
        Bitmap dst = bitmap.copy(bitmap.getConfig(), true);
        Canvas canvas = new Canvas(dst);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return dst;
    }

    static int getIconHighlightColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.playbackControlsIconHighlightColor, outValue, true)) {
            return outValue.data;
        }
        return context.getResources().getColor(C0364R.color.lb_playback_icon_highlight_no_theme);
    }

    static Drawable getStyledDrawable(Context context, int index) {
        TypedValue outValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(C0364R.attr.playbackControlsActionIcons, outValue, false)) {
            return null;
        }
        TypedArray array = context.getTheme().obtainStyledAttributes(outValue.data, C0364R.styleable.lbPlaybackControlsActionIcons);
        Drawable drawable = array.getDrawable(index);
        array.recycle();
        return drawable;
    }

    public final Object getItem() {
        return this.mItem;
    }

    public final void setImageBitmap(Context context, Bitmap bm) {
        this.mImageDrawable = new BitmapDrawable(context.getResources(), bm);
    }

    public final Drawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public final void setImageDrawable(Drawable drawable) {
        this.mImageDrawable = drawable;
    }

    public final ObjectAdapter getPrimaryActionsAdapter() {
        return this.mPrimaryActionsAdapter;
    }

    public final void setPrimaryActionsAdapter(ObjectAdapter adapter) {
        this.mPrimaryActionsAdapter = adapter;
    }

    public final ObjectAdapter getSecondaryActionsAdapter() {
        return this.mSecondaryActionsAdapter;
    }

    public final void setSecondaryActionsAdapter(ObjectAdapter adapter) {
        this.mSecondaryActionsAdapter = adapter;
    }

    @Deprecated
    public int getTotalTime() {
        return MathUtil.safeLongToInt(getTotalTimeLong());
    }

    @Deprecated
    public void setTotalTime(int ms) {
        setDuration((long) ms);
    }

    @Deprecated
    public long getTotalTimeLong() {
        return this.mTotalTimeMs;
    }

    @Deprecated
    public void setTotalTimeLong(long ms) {
        setDuration(ms);
    }

    public long getDuration() {
        return this.mTotalTimeMs;
    }

    public void setDuration(long ms) {
        if (this.mTotalTimeMs != ms) {
            this.mTotalTimeMs = ms;
            OnPlaybackProgressCallback onPlaybackProgressCallback = this.mListener;
            if (onPlaybackProgressCallback != null) {
                onPlaybackProgressCallback.onDurationChanged(this, this.mTotalTimeMs);
            }
        }
    }

    @Deprecated
    public int getCurrentTime() {
        return MathUtil.safeLongToInt(getCurrentTimeLong());
    }

    @Deprecated
    public void setCurrentTime(int ms) {
        setCurrentTimeLong((long) ms);
    }

    @Deprecated
    public long getCurrentTimeLong() {
        return this.mCurrentTimeMs;
    }

    @Deprecated
    public void setCurrentTimeLong(long ms) {
        setCurrentPosition(ms);
    }

    public long getCurrentPosition() {
        return this.mCurrentTimeMs;
    }

    public void setCurrentPosition(long ms) {
        if (this.mCurrentTimeMs != ms) {
            this.mCurrentTimeMs = ms;
            OnPlaybackProgressCallback onPlaybackProgressCallback = this.mListener;
            if (onPlaybackProgressCallback != null) {
                onPlaybackProgressCallback.onCurrentPositionChanged(this, this.mCurrentTimeMs);
            }
        }
    }

    @Deprecated
    public int getBufferedProgress() {
        return MathUtil.safeLongToInt(getBufferedPosition());
    }

    @Deprecated
    public void setBufferedProgress(int ms) {
        setBufferedPosition((long) ms);
    }

    @Deprecated
    public long getBufferedProgressLong() {
        return this.mBufferedProgressMs;
    }

    @Deprecated
    public void setBufferedProgressLong(long ms) {
        setBufferedPosition(ms);
    }

    public long getBufferedPosition() {
        return this.mBufferedProgressMs;
    }

    public void setBufferedPosition(long ms) {
        if (this.mBufferedProgressMs != ms) {
            this.mBufferedProgressMs = ms;
            OnPlaybackProgressCallback onPlaybackProgressCallback = this.mListener;
            if (onPlaybackProgressCallback != null) {
                onPlaybackProgressCallback.onBufferedPositionChanged(this, this.mBufferedProgressMs);
            }
        }
    }

    public Action getActionForKeyCode(int keyCode) {
        Action action = getActionForKeyCode(getPrimaryActionsAdapter(), keyCode);
        if (action != null) {
            return action;
        }
        return getActionForKeyCode(getSecondaryActionsAdapter(), keyCode);
    }

    public Action getActionForKeyCode(ObjectAdapter adapter, int keyCode) {
        if (adapter == this.mPrimaryActionsAdapter || adapter == this.mSecondaryActionsAdapter) {
            for (int i = 0; i < adapter.size(); i++) {
                Action action = (Action) adapter.get(i);
                if (action.respondsToKeyCode(keyCode)) {
                    return action;
                }
            }
            return null;
        }
        throw new IllegalArgumentException("Invalid adapter");
    }

    public void setOnPlaybackProgressChangedListener(OnPlaybackProgressCallback listener) {
        this.mListener = listener;
    }

    public static class OnPlaybackProgressCallback {
        public void onCurrentPositionChanged(PlaybackControlsRow row, long currentTimeMs) {
        }

        public void onDurationChanged(PlaybackControlsRow row, long totalTime) {
        }

        public void onBufferedPositionChanged(PlaybackControlsRow row, long bufferedProgressMs) {
        }
    }

    public static abstract class MultiAction extends Action {
        private Drawable[] mDrawables;
        private int mIndex;
        private String[] mLabels;
        private String[] mLabels2;

        public MultiAction(int id) {
            super((long) id);
        }

        public void setDrawables(Drawable[] drawables) {
            this.mDrawables = drawables;
            setIndex(0);
        }

        public void setLabels(String[] labels) {
            this.mLabels = labels;
            setIndex(0);
        }

        public void setSecondaryLabels(String[] labels) {
            this.mLabels2 = labels;
            setIndex(0);
        }

        public int getActionCount() {
            Drawable[] drawableArr = this.mDrawables;
            if (drawableArr != null) {
                return drawableArr.length;
            }
            String[] strArr = this.mLabels;
            if (strArr != null) {
                return strArr.length;
            }
            return 0;
        }

        public Drawable getDrawable(int index) {
            Drawable[] drawableArr = this.mDrawables;
            if (drawableArr == null) {
                return null;
            }
            return drawableArr[index];
        }

        public String getLabel(int index) {
            String[] strArr = this.mLabels;
            if (strArr == null) {
                return null;
            }
            return strArr[index];
        }

        public String getSecondaryLabel(int index) {
            String[] strArr = this.mLabels2;
            if (strArr == null) {
                return null;
            }
            return strArr[index];
        }

        public void nextIndex() {
            setIndex(this.mIndex < getActionCount() + -1 ? this.mIndex + 1 : 0);
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void setIndex(int index) {
            this.mIndex = index;
            Drawable[] drawableArr = this.mDrawables;
            if (drawableArr != null) {
                setIcon(drawableArr[this.mIndex]);
            }
            String[] strArr = this.mLabels;
            if (strArr != null) {
                setLabel1(strArr[this.mIndex]);
            }
            String[] strArr2 = this.mLabels2;
            if (strArr2 != null) {
                setLabel2(strArr2[this.mIndex]);
            }
        }
    }

    public static class PlayPauseAction extends MultiAction {
        public static final int INDEX_PAUSE = 1;
        public static final int INDEX_PLAY = 0;
        @Deprecated
        public static final int PAUSE = 1;
        @Deprecated
        public static final int PLAY = 0;

        public PlayPauseAction(Context context) {
            super(C0364R.C0366id.lb_control_play_pause);
            Drawable[] drawables = {PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_play), PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_pause)};
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_play);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_pause);
            setLabels(labels);
            addKeyCode(85);
            addKeyCode(ClientAnalytics.LogRequest.LogSource.CLASSROOM_VALUE);
            addKeyCode(ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE);
        }
    }

    public static class FastForwardAction extends MultiAction {
        public FastForwardAction(Context context) {
            this(context, 1);
        }

        public FastForwardAction(Context context, int numSpeeds) {
            super(C0364R.C0366id.lb_control_fast_forward);
            if (numSpeeds >= 1) {
                Drawable[] drawables = new Drawable[(numSpeeds + 1)];
                drawables[0] = PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_fast_forward);
                setDrawables(drawables);
                String[] labels = new String[getActionCount()];
                labels[0] = context.getString(C0364R.string.lb_playback_controls_fast_forward);
                String[] labels2 = new String[getActionCount()];
                labels2[0] = labels[0];
                for (int i = 1; i <= numSpeeds; i++) {
                    int multiplier = i + 1;
                    labels[i] = context.getResources().getString(C0364R.string.lb_control_display_fast_forward_multiplier, Integer.valueOf(multiplier));
                    labels2[i] = context.getResources().getString(C0364R.string.lb_playback_controls_fast_forward_multiplier, Integer.valueOf(multiplier));
                }
                setLabels(labels);
                setSecondaryLabels(labels2);
                addKeyCode(90);
                return;
            }
            throw new IllegalArgumentException("numSpeeds must be > 0");
        }
    }

    public static class RewindAction extends MultiAction {
        public RewindAction(Context context) {
            this(context, 1);
        }

        public RewindAction(Context context, int numSpeeds) {
            super(C0364R.C0366id.lb_control_fast_rewind);
            if (numSpeeds >= 1) {
                Drawable[] drawables = new Drawable[(numSpeeds + 1)];
                drawables[0] = PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_rewind);
                setDrawables(drawables);
                String[] labels = new String[getActionCount()];
                labels[0] = context.getString(C0364R.string.lb_playback_controls_rewind);
                String[] labels2 = new String[getActionCount()];
                labels2[0] = labels[0];
                for (int i = 1; i <= numSpeeds; i++) {
                    int multiplier = i + 1;
                    String string = context.getResources().getString(C0364R.string.lb_control_display_rewind_multiplier, Integer.valueOf(multiplier));
                    labels[i] = string;
                    labels[i] = string;
                    labels2[i] = context.getResources().getString(C0364R.string.lb_playback_controls_rewind_multiplier, Integer.valueOf(multiplier));
                }
                setLabels(labels);
                setSecondaryLabels(labels2);
                addKeyCode(89);
                return;
            }
            throw new IllegalArgumentException("numSpeeds must be > 0");
        }
    }

    public static class SkipNextAction extends Action {
        public SkipNextAction(Context context) {
            super((long) C0364R.C0366id.lb_control_skip_next);
            setIcon(PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_skip_next));
            setLabel1(context.getString(C0364R.string.lb_playback_controls_skip_next));
            addKeyCode(87);
        }
    }

    public static class SkipPreviousAction extends Action {
        public SkipPreviousAction(Context context) {
            super((long) C0364R.C0366id.lb_control_skip_previous);
            setIcon(PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_skip_previous));
            setLabel1(context.getString(C0364R.string.lb_playback_controls_skip_previous));
            addKeyCode(88);
        }
    }

    public static class PictureInPictureAction extends Action {
        public PictureInPictureAction(Context context) {
            super((long) C0364R.C0366id.lb_control_picture_in_picture);
            setIcon(PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_picture_in_picture));
            setLabel1(context.getString(C0364R.string.lb_playback_controls_picture_in_picture));
            addKeyCode(ClientAnalytics.LogRequest.LogSource.YT_CREATOR_ANDROID_PRIMES_VALUE);
        }
    }

    public static class MoreActions extends Action {
        public MoreActions(Context context) {
            super((long) C0364R.C0366id.lb_control_more_actions);
            setIcon(context.getResources().getDrawable(C0364R.C0365drawable.lb_ic_more));
            setLabel1(context.getString(C0364R.string.lb_playback_controls_more_actions));
        }
    }

    public static abstract class ThumbsAction extends MultiAction {
        public static final int INDEX_OUTLINE = 1;
        public static final int INDEX_SOLID = 0;
        @Deprecated
        public static final int OUTLINE = 1;
        @Deprecated
        public static final int SOLID = 0;

        public ThumbsAction(int id, Context context, int solidIconIndex, int outlineIconIndex) {
            super(id);
            setDrawables(new Drawable[]{PlaybackControlsRow.getStyledDrawable(context, solidIconIndex), PlaybackControlsRow.getStyledDrawable(context, outlineIconIndex)});
        }
    }

    public static class ThumbsUpAction extends ThumbsAction {
        public ThumbsUpAction(Context context) {
            super(C0364R.C0366id.lb_control_thumbs_up, context, C0364R.styleable.lbPlaybackControlsActionIcons_thumb_up, C0364R.styleable.lbPlaybackControlsActionIcons_thumb_up_outline);
            String[] labels = new String[getActionCount()];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_thumb_up);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_thumb_up_outline);
            setLabels(labels);
        }
    }

    public static class ThumbsDownAction extends ThumbsAction {
        public ThumbsDownAction(Context context) {
            super(C0364R.C0366id.lb_control_thumbs_down, context, C0364R.styleable.lbPlaybackControlsActionIcons_thumb_down, C0364R.styleable.lbPlaybackControlsActionIcons_thumb_down_outline);
            String[] labels = new String[getActionCount()];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_thumb_down);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_thumb_down_outline);
            setLabels(labels);
        }
    }

    public static class RepeatAction extends MultiAction {
        @Deprecated
        public static final int ALL = 1;
        public static final int INDEX_ALL = 1;
        public static final int INDEX_NONE = 0;
        public static final int INDEX_ONE = 2;
        @Deprecated
        public static final int NONE = 0;
        @Deprecated
        public static final int ONE = 2;

        public RepeatAction(Context context) {
            this(context, PlaybackControlsRow.getIconHighlightColor(context));
        }

        public RepeatAction(Context context, int highlightColor) {
            this(context, highlightColor, highlightColor);
        }

        public RepeatAction(Context context, int repeatAllColor, int repeatOneColor) {
            super(C0364R.C0366id.lb_control_repeat);
            BitmapDrawable bitmapDrawable;
            Drawable[] drawables = new Drawable[3];
            BitmapDrawable repeatDrawable = (BitmapDrawable) PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_repeat);
            BitmapDrawable repeatOneDrawable = (BitmapDrawable) PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_repeat_one);
            drawables[0] = repeatDrawable;
            BitmapDrawable bitmapDrawable2 = null;
            if (repeatDrawable == null) {
                bitmapDrawable = null;
            } else {
                bitmapDrawable = new BitmapDrawable(context.getResources(), PlaybackControlsRow.createBitmap(repeatDrawable.getBitmap(), repeatAllColor));
            }
            drawables[1] = bitmapDrawable;
            drawables[2] = repeatOneDrawable != null ? new BitmapDrawable(context.getResources(), PlaybackControlsRow.createBitmap(repeatOneDrawable.getBitmap(), repeatOneColor)) : bitmapDrawable2;
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_repeat_all);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_repeat_one);
            labels[2] = context.getString(C0364R.string.lb_playback_controls_repeat_none);
            setLabels(labels);
        }
    }

    public static class ShuffleAction extends MultiAction {
        public static final int INDEX_OFF = 0;
        public static final int INDEX_ON = 1;
        @Deprecated
        public static final int OFF = 0;
        @Deprecated

        /* renamed from: ON */
        public static final int f36ON = 1;

        public ShuffleAction(Context context) {
            this(context, PlaybackControlsRow.getIconHighlightColor(context));
        }

        public ShuffleAction(Context context, int highlightColor) {
            super(C0364R.C0366id.lb_control_shuffle);
            BitmapDrawable uncoloredDrawable = (BitmapDrawable) PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_shuffle);
            Drawable[] drawables = {uncoloredDrawable, new BitmapDrawable(context.getResources(), PlaybackControlsRow.createBitmap(uncoloredDrawable.getBitmap(), highlightColor))};
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_shuffle_enable);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_shuffle_disable);
            setLabels(labels);
        }
    }

    public static class HighQualityAction extends MultiAction {
        public static final int INDEX_OFF = 0;
        public static final int INDEX_ON = 1;
        @Deprecated
        public static final int OFF = 0;
        @Deprecated

        /* renamed from: ON */
        public static final int f35ON = 1;

        public HighQualityAction(Context context) {
            this(context, PlaybackControlsRow.getIconHighlightColor(context));
        }

        public HighQualityAction(Context context, int highlightColor) {
            super(C0364R.C0366id.lb_control_high_quality);
            BitmapDrawable uncoloredDrawable = (BitmapDrawable) PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_high_quality);
            Drawable[] drawables = {uncoloredDrawable, new BitmapDrawable(context.getResources(), PlaybackControlsRow.createBitmap(uncoloredDrawable.getBitmap(), highlightColor))};
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_high_quality_enable);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_high_quality_disable);
            setLabels(labels);
        }
    }

    public static class ClosedCaptioningAction extends MultiAction {
        public static final int INDEX_OFF = 0;
        public static final int INDEX_ON = 1;
        @Deprecated
        public static final int OFF = 0;
        @Deprecated

        /* renamed from: ON */
        public static final int f34ON = 1;

        public ClosedCaptioningAction(Context context) {
            this(context, PlaybackControlsRow.getIconHighlightColor(context));
        }

        public ClosedCaptioningAction(Context context, int highlightColor) {
            super(C0364R.C0366id.lb_control_closed_captioning);
            BitmapDrawable uncoloredDrawable = (BitmapDrawable) PlaybackControlsRow.getStyledDrawable(context, C0364R.styleable.lbPlaybackControlsActionIcons_closed_captioning);
            Drawable[] drawables = {uncoloredDrawable, new BitmapDrawable(context.getResources(), PlaybackControlsRow.createBitmap(uncoloredDrawable.getBitmap(), highlightColor))};
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[0] = context.getString(C0364R.string.lb_playback_controls_closed_captioning_enable);
            labels[1] = context.getString(C0364R.string.lb_playback_controls_closed_captioning_disable);
            setLabels(labels);
        }
    }
}
