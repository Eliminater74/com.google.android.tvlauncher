package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.C0364R;
import androidx.leanback.widget.ControlBarPresenter;
import androidx.leanback.widget.PlaybackControlsPresenter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.PlaybackSeekDataProvider;
import androidx.leanback.widget.PlaybackSeekUi;
import androidx.leanback.widget.PlaybackTransportRowView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SeekBar;
import java.util.Arrays;

public class PlaybackTransportRowPresenter extends PlaybackRowPresenter {
    float mDefaultSeekIncrement = 0.01f;
    Presenter mDescriptionPresenter;
    OnActionClickedListener mOnActionClickedListener;
    private final ControlBarPresenter.OnControlClickedListener mOnControlClickedListener = new ControlBarPresenter.OnControlClickedListener() {
        public void onControlClicked(Presenter.ViewHolder itemViewHolder, Object item, ControlBarPresenter.BoundData data) {
            ViewHolder vh = ((BoundData) data).mRowViewHolder;
            if (vh.getOnItemViewClickedListener() != null) {
                vh.getOnItemViewClickedListener().onItemClicked(itemViewHolder, item, vh, vh.getRow());
            }
            if (PlaybackTransportRowPresenter.this.mOnActionClickedListener != null && (item instanceof Action)) {
                PlaybackTransportRowPresenter.this.mOnActionClickedListener.onActionClicked((Action) item);
            }
        }
    };
    private final ControlBarPresenter.OnControlSelectedListener mOnControlSelectedListener = new ControlBarPresenter.OnControlSelectedListener() {
        public void onControlSelected(Presenter.ViewHolder itemViewHolder, Object item, ControlBarPresenter.BoundData data) {
            ViewHolder vh = ((BoundData) data).mRowViewHolder;
            if (vh.mSelectedViewHolder != itemViewHolder || vh.mSelectedItem != item) {
                vh.mSelectedViewHolder = itemViewHolder;
                vh.mSelectedItem = item;
                vh.dispatchItemSelection();
            }
        }
    };
    ControlBarPresenter mPlaybackControlsPresenter;
    int mProgressColor = 0;
    boolean mProgressColorSet;
    ControlBarPresenter mSecondaryControlsPresenter;
    int mSecondaryProgressColor = 0;
    boolean mSecondaryProgressColorSet;

    static class BoundData extends PlaybackControlsPresenter.BoundData {
        ViewHolder mRowViewHolder;

        BoundData() {
        }
    }

    public class ViewHolder extends PlaybackRowPresenter.ViewHolder implements PlaybackSeekUi {
        BoundData mControlsBoundData = new BoundData();
        final ViewGroup mControlsDock;
        ControlBarPresenter.ViewHolder mControlsVh;
        final TextView mCurrentTime;
        long mCurrentTimeInMs = Long.MIN_VALUE;
        final ViewGroup mDescriptionDock;
        final Presenter.ViewHolder mDescriptionViewHolder;
        final ImageView mImageView;
        boolean mInSeek;
        final PlaybackControlsRow.OnPlaybackProgressCallback mListener = new PlaybackControlsRow.OnPlaybackProgressCallback() {
            public void onCurrentPositionChanged(PlaybackControlsRow row, long ms) {
                ViewHolder.this.setCurrentPosition(ms);
            }

            public void onDurationChanged(PlaybackControlsRow row, long ms) {
                ViewHolder.this.setTotalTime(ms);
            }

            public void onBufferedPositionChanged(PlaybackControlsRow row, long ms) {
                ViewHolder.this.setBufferedPosition(ms);
            }
        };
        PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
        long[] mPositions;
        int mPositionsLength;
        final SeekBar mProgressBar;
        BoundData mSecondaryBoundData = new BoundData();
        final ViewGroup mSecondaryControlsDock;
        ControlBarPresenter.ViewHolder mSecondaryControlsVh;
        long mSecondaryProgressInMs;
        PlaybackSeekUi.Client mSeekClient;
        PlaybackSeekDataProvider mSeekDataProvider;
        Object mSelectedItem;
        Presenter.ViewHolder mSelectedViewHolder;
        final StringBuilder mTempBuilder = new StringBuilder();
        int mThumbHeroIndex = -1;
        PlaybackSeekDataProvider.ResultCallback mThumbResult = new PlaybackSeekDataProvider.ResultCallback() {
            public void onThumbnailLoaded(Bitmap bitmap, int index) {
                int childIndex = index - (ViewHolder.this.mThumbHeroIndex - (ViewHolder.this.mThumbsBar.getChildCount() / 2));
                if (childIndex >= 0 && childIndex < ViewHolder.this.mThumbsBar.getChildCount()) {
                    ViewHolder.this.mThumbsBar.setThumbBitmap(childIndex, bitmap);
                }
            }
        };
        final ThumbsBar mThumbsBar;
        final TextView mTotalTime;
        long mTotalTimeInMs = Long.MIN_VALUE;

        /* JADX INFO: Multiple debug info for r3v4 int: [D('insertIndex' int), D('thumbHeroIndex' int)] */
        /* JADX INFO: Multiple debug info for r3v10 'insertIndex'  int: [D('thumbHeroIndex' int), D('insertIndex' int)] */
        /* access modifiers changed from: package-private */
        public void updateProgressInSeek(boolean forward) {
            long newPos;
            int insertIndex;
            long pos = this.mCurrentTimeInMs;
            int i = this.mPositionsLength;
            if (i > 0) {
                int i2 = 0;
                int index = Arrays.binarySearch(this.mPositions, 0, i, pos);
                if (forward) {
                    if (index < 0) {
                        insertIndex = -1 - index;
                        if (insertIndex <= this.mPositionsLength - 1) {
                            newPos = this.mPositions[insertIndex];
                        } else {
                            long newPos2 = this.mTotalTimeInMs;
                            if (insertIndex > 0) {
                                i2 = insertIndex - 1;
                            }
                            insertIndex = i2;
                            newPos = newPos2;
                        }
                    } else if (index < this.mPositionsLength - 1) {
                        newPos = this.mPositions[index + 1];
                        insertIndex = index + 1;
                    } else {
                        newPos = this.mTotalTimeInMs;
                        insertIndex = index;
                    }
                } else if (index < 0) {
                    int thumbHeroIndex = -1 - index;
                    if (thumbHeroIndex > 0) {
                        long newPos3 = this.mPositions[thumbHeroIndex - 1];
                        insertIndex = thumbHeroIndex - 1;
                        newPos = newPos3;
                    } else {
                        newPos = 0;
                        insertIndex = 0;
                    }
                } else if (index > 0) {
                    newPos = this.mPositions[index - 1];
                    insertIndex = index - 1;
                } else {
                    newPos = 0;
                    insertIndex = 0;
                }
                updateThumbsInSeek(insertIndex, forward);
            } else {
                long interval = (long) (((float) this.mTotalTimeInMs) * PlaybackTransportRowPresenter.this.getDefaultSeekIncrement());
                newPos = (forward ? interval : -interval) + pos;
                if (newPos > this.mTotalTimeInMs) {
                    newPos = this.mTotalTimeInMs;
                } else if (newPos < 0) {
                    newPos = 0;
                }
            }
            double d = (double) newPos;
            double d2 = (double) this.mTotalTimeInMs;
            Double.isNaN(d);
            Double.isNaN(d2);
            this.mProgressBar.setProgress((int) (2.147483647E9d * (d / d2)));
            this.mSeekClient.onSeekPositionChanged(newPos);
        }

        /* access modifiers changed from: package-private */
        public void updateThumbsInSeek(int thumbHeroIndex, boolean forward) {
            int oldEnd;
            int oldStart;
            int i;
            if (this.mThumbHeroIndex != thumbHeroIndex) {
                int totalNum = this.mThumbsBar.getChildCount();
                if (totalNum < 0 || (totalNum & 1) == 0) {
                    throw new RuntimeException();
                }
                int heroChildIndex = totalNum / 2;
                int start = Math.max(thumbHeroIndex - (totalNum / 2), 0);
                int end = Math.min((totalNum / 2) + thumbHeroIndex, this.mPositionsLength - 1);
                int newRequestEnd = this.mThumbHeroIndex;
                if (newRequestEnd < 0) {
                    oldStart = start;
                    oldEnd = end;
                } else {
                    forward = thumbHeroIndex > newRequestEnd;
                    int oldStart2 = Math.max(this.mThumbHeroIndex - (totalNum / 2), 0);
                    int oldEnd2 = Math.min(this.mThumbHeroIndex + (totalNum / 2), this.mPositionsLength - 1);
                    if (forward) {
                        int newRequestStart = Math.max(oldEnd2 + 1, start);
                        int newRequestEnd2 = end;
                        for (int i2 = start; i2 <= newRequestStart - 1; i2++) {
                            ThumbsBar thumbsBar = this.mThumbsBar;
                            thumbsBar.setThumbBitmap((i2 - thumbHeroIndex) + heroChildIndex, thumbsBar.getThumbBitmap((i2 - this.mThumbHeroIndex) + heroChildIndex));
                        }
                        oldStart = newRequestStart;
                        oldEnd = newRequestEnd2;
                    } else {
                        int newRequestEnd3 = Math.min(oldStart2 - 1, end);
                        int newRequestStart2 = start;
                        for (int i3 = end; i3 >= newRequestEnd3 + 1; i3--) {
                            ThumbsBar thumbsBar2 = this.mThumbsBar;
                            thumbsBar2.setThumbBitmap((i3 - thumbHeroIndex) + heroChildIndex, thumbsBar2.getThumbBitmap((i3 - this.mThumbHeroIndex) + heroChildIndex));
                        }
                        oldEnd = newRequestEnd3;
                        oldStart = newRequestStart2;
                    }
                }
                this.mThumbHeroIndex = thumbHeroIndex;
                if (forward) {
                    for (int i4 = oldStart; i4 <= oldEnd; i4++) {
                        this.mSeekDataProvider.getThumbnail(i4, this.mThumbResult);
                    }
                } else {
                    for (int i5 = oldEnd; i5 >= oldStart; i5--) {
                        this.mSeekDataProvider.getThumbnail(i5, this.mThumbResult);
                    }
                }
                int childIndex = 0;
                while (true) {
                    i = this.mThumbHeroIndex;
                    if (childIndex >= (heroChildIndex - i) + start) {
                        break;
                    }
                    this.mThumbsBar.setThumbBitmap(childIndex, null);
                    childIndex++;
                }
                for (int childIndex2 = ((heroChildIndex + end) - i) + 1; childIndex2 < totalNum; childIndex2++) {
                    this.mThumbsBar.setThumbBitmap(childIndex2, null);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean onForward() {
            if (!startSeek()) {
                return false;
            }
            updateProgressInSeek(true);
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean onBackward() {
            if (!startSeek()) {
                return false;
            }
            updateProgressInSeek(false);
            return true;
        }

        public ViewHolder(View rootView, Presenter descriptionPresenter) {
            super(rootView);
            Presenter.ViewHolder viewHolder;
            this.mImageView = (ImageView) rootView.findViewById(C0364R.C0366id.image);
            this.mDescriptionDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.description_dock);
            this.mCurrentTime = (TextView) rootView.findViewById(C0364R.C0366id.current_time);
            this.mTotalTime = (TextView) rootView.findViewById(C0364R.C0366id.total_time);
            this.mProgressBar = (SeekBar) rootView.findViewById(C0364R.C0366id.playback_progress);
            this.mProgressBar.setOnClickListener(new View.OnClickListener(PlaybackTransportRowPresenter.this) {
                public void onClick(View view) {
                    PlaybackTransportRowPresenter.this.onProgressBarClicked(ViewHolder.this);
                }
            });
            this.mProgressBar.setOnKeyListener(new View.OnKeyListener(PlaybackTransportRowPresenter.this) {
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    boolean z = false;
                    if (keyCode != 4) {
                        if (keyCode != 66) {
                            if (keyCode != 69) {
                                if (keyCode != 81) {
                                    if (keyCode != 111) {
                                        if (keyCode != 89) {
                                            if (keyCode != 90) {
                                                switch (keyCode) {
                                                    case 19:
                                                    case 20:
                                                        return ViewHolder.this.mInSeek;
                                                    case 21:
                                                        break;
                                                    case 22:
                                                        break;
                                                    case 23:
                                                        break;
                                                    default:
                                                        return false;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (keyEvent.getAction() == 0) {
                                    ViewHolder.this.onForward();
                                }
                                return true;
                            }
                            if (keyEvent.getAction() == 0) {
                                ViewHolder.this.onBackward();
                            }
                            return true;
                        }
                        if (!ViewHolder.this.mInSeek) {
                            return false;
                        }
                        if (keyEvent.getAction() == 1) {
                            ViewHolder.this.stopSeek(false);
                        }
                        return true;
                    }
                    if (!ViewHolder.this.mInSeek) {
                        return false;
                    }
                    if (keyEvent.getAction() == 1) {
                        ViewHolder viewHolder = ViewHolder.this;
                        if (Build.VERSION.SDK_INT < 21 || !ViewHolder.this.mProgressBar.isAccessibilityFocused()) {
                            z = true;
                        }
                        viewHolder.stopSeek(z);
                    }
                    return true;
                }
            });
            this.mProgressBar.setAccessibilitySeekListener(new SeekBar.AccessibilitySeekListener(PlaybackTransportRowPresenter.this) {
                public boolean onAccessibilitySeekForward() {
                    return ViewHolder.this.onForward();
                }

                public boolean onAccessibilitySeekBackward() {
                    return ViewHolder.this.onBackward();
                }
            });
            this.mProgressBar.setMax(Integer.MAX_VALUE);
            this.mControlsDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.controls_dock);
            this.mSecondaryControlsDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.secondary_controls_dock);
            if (descriptionPresenter == null) {
                viewHolder = null;
            } else {
                viewHolder = descriptionPresenter.onCreateViewHolder(this.mDescriptionDock);
            }
            this.mDescriptionViewHolder = viewHolder;
            Presenter.ViewHolder viewHolder2 = this.mDescriptionViewHolder;
            if (viewHolder2 != null) {
                this.mDescriptionDock.addView(viewHolder2.view);
            }
            this.mThumbsBar = (ThumbsBar) rootView.findViewById(C0364R.C0366id.thumbs_row);
        }

        public final Presenter.ViewHolder getDescriptionViewHolder() {
            return this.mDescriptionViewHolder;
        }

        public void setPlaybackSeekUiClient(PlaybackSeekUi.Client client) {
            this.mSeekClient = client;
        }

        /* access modifiers changed from: package-private */
        public boolean startSeek() {
            if (this.mInSeek) {
                return true;
            }
            PlaybackSeekUi.Client client = this.mSeekClient;
            if (client == null || !client.isSeekEnabled() || this.mTotalTimeInMs <= 0) {
                return false;
            }
            this.mInSeek = true;
            this.mSeekClient.onSeekStarted();
            this.mSeekDataProvider = this.mSeekClient.getPlaybackSeekDataProvider();
            PlaybackSeekDataProvider playbackSeekDataProvider = this.mSeekDataProvider;
            this.mPositions = playbackSeekDataProvider != null ? playbackSeekDataProvider.getSeekPositions() : null;
            long[] jArr = this.mPositions;
            if (jArr != null) {
                int pos = Arrays.binarySearch(jArr, this.mTotalTimeInMs);
                if (pos >= 0) {
                    this.mPositionsLength = pos + 1;
                } else {
                    this.mPositionsLength = -1 - pos;
                }
            } else {
                this.mPositionsLength = 0;
            }
            this.mControlsVh.view.setVisibility(8);
            this.mSecondaryControlsVh.view.setVisibility(4);
            this.mDescriptionViewHolder.view.setVisibility(4);
            this.mThumbsBar.setVisibility(0);
            return true;
        }

        /* access modifiers changed from: package-private */
        public void stopSeek(boolean cancelled) {
            if (this.mInSeek) {
                this.mInSeek = false;
                this.mSeekClient.onSeekFinished(cancelled);
                PlaybackSeekDataProvider playbackSeekDataProvider = this.mSeekDataProvider;
                if (playbackSeekDataProvider != null) {
                    playbackSeekDataProvider.reset();
                }
                this.mThumbHeroIndex = -1;
                this.mThumbsBar.clearThumbBitmaps();
                this.mSeekDataProvider = null;
                this.mPositions = null;
                this.mPositionsLength = 0;
                this.mControlsVh.view.setVisibility(0);
                this.mSecondaryControlsVh.view.setVisibility(0);
                this.mDescriptionViewHolder.view.setVisibility(0);
                this.mThumbsBar.setVisibility(4);
            }
        }

        /* access modifiers changed from: package-private */
        public void dispatchItemSelection() {
            if (isSelected()) {
                if (this.mSelectedViewHolder == null) {
                    if (getOnItemViewSelectedListener() != null) {
                        getOnItemViewSelectedListener().onItemSelected(null, null, this, getRow());
                    }
                } else if (getOnItemViewSelectedListener() != null) {
                    getOnItemViewSelectedListener().onItemSelected(this.mSelectedViewHolder, this.mSelectedItem, this, getRow());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public Presenter getPresenter(boolean primary) {
            ObjectAdapter adapter;
            if (primary) {
                adapter = ((PlaybackControlsRow) getRow()).getPrimaryActionsAdapter();
            } else {
                adapter = ((PlaybackControlsRow) getRow()).getSecondaryActionsAdapter();
            }
            Object obj = null;
            if (adapter == null) {
                return null;
            }
            if (adapter.getPresenterSelector() instanceof ControlButtonPresenterSelector) {
                return ((ControlButtonPresenterSelector) adapter.getPresenterSelector()).getSecondaryPresenter();
            }
            if (adapter.size() > 0) {
                obj = adapter.get(0);
            }
            return adapter.getPresenter(obj);
        }

        public final TextView getDurationView() {
            return this.mTotalTime;
        }

        /* access modifiers changed from: protected */
        public void onSetDurationLabel(long totalTimeMs) {
            if (this.mTotalTime != null) {
                PlaybackTransportRowPresenter.formatTime(totalTimeMs, this.mTempBuilder);
                this.mTotalTime.setText(this.mTempBuilder.toString());
            }
        }

        /* access modifiers changed from: package-private */
        public void setTotalTime(long totalTimeMs) {
            if (this.mTotalTimeInMs != totalTimeMs) {
                this.mTotalTimeInMs = totalTimeMs;
                onSetDurationLabel(totalTimeMs);
            }
        }

        public final TextView getCurrentPositionView() {
            return this.mCurrentTime;
        }

        /* access modifiers changed from: protected */
        public void onSetCurrentPositionLabel(long currentTimeMs) {
            if (this.mCurrentTime != null) {
                PlaybackTransportRowPresenter.formatTime(currentTimeMs, this.mTempBuilder);
                this.mCurrentTime.setText(this.mTempBuilder.toString());
            }
        }

        /* access modifiers changed from: package-private */
        public void setCurrentPosition(long currentTimeMs) {
            if (currentTimeMs != this.mCurrentTimeInMs) {
                this.mCurrentTimeInMs = currentTimeMs;
                onSetCurrentPositionLabel(currentTimeMs);
            }
            if (!this.mInSeek) {
                int progressRatio = 0;
                long j = this.mTotalTimeInMs;
                if (j > 0) {
                    double d = (double) this.mCurrentTimeInMs;
                    double d2 = (double) j;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    progressRatio = (int) (2.147483647E9d * (d / d2));
                }
                this.mProgressBar.setProgress(progressRatio);
            }
        }

        /* access modifiers changed from: package-private */
        public void setBufferedPosition(long progressMs) {
            this.mSecondaryProgressInMs = progressMs;
            double d = (double) progressMs;
            double d2 = (double) this.mTotalTimeInMs;
            Double.isNaN(d);
            Double.isNaN(d2);
            this.mProgressBar.setSecondaryProgress((int) (2.147483647E9d * (d / d2)));
        }
    }

    static void formatTime(long ms, StringBuilder sb) {
        sb.setLength(0);
        if (ms < 0) {
            sb.append("--");
            return;
        }
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long seconds2 = seconds - (minutes * 60);
        long minutes2 = minutes - (60 * hours);
        if (hours > 0) {
            sb.append(hours);
            sb.append(':');
            if (minutes2 < 10) {
                sb.append('0');
            }
        }
        sb.append(minutes2);
        sb.append(':');
        if (seconds2 < 10) {
            sb.append('0');
        }
        sb.append(seconds2);
    }

    public PlaybackTransportRowPresenter() {
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        this.mPlaybackControlsPresenter = new ControlBarPresenter(C0364R.layout.lb_control_bar);
        this.mPlaybackControlsPresenter.setDefaultFocusToMiddle(false);
        this.mSecondaryControlsPresenter = new ControlBarPresenter(C0364R.layout.lb_control_bar);
        this.mSecondaryControlsPresenter.setDefaultFocusToMiddle(false);
        this.mPlaybackControlsPresenter.setOnControlSelectedListener(this.mOnControlSelectedListener);
        this.mSecondaryControlsPresenter.setOnControlSelectedListener(this.mOnControlSelectedListener);
        this.mPlaybackControlsPresenter.setOnControlClickedListener(this.mOnControlClickedListener);
        this.mSecondaryControlsPresenter.setOnControlClickedListener(this.mOnControlClickedListener);
    }

    public void setDescriptionPresenter(Presenter descriptionPresenter) {
        this.mDescriptionPresenter = descriptionPresenter;
    }

    public void setOnActionClickedListener(OnActionClickedListener listener) {
        this.mOnActionClickedListener = listener;
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mOnActionClickedListener;
    }

    public void setProgressColor(@ColorInt int color) {
        this.mProgressColor = color;
        this.mProgressColorSet = true;
    }

    @ColorInt
    public int getProgressColor() {
        return this.mProgressColor;
    }

    public void setSecondaryProgressColor(@ColorInt int color) {
        this.mSecondaryProgressColor = color;
        this.mSecondaryProgressColorSet = true;
    }

    @ColorInt
    public int getSecondaryProgressColor() {
        return this.mSecondaryProgressColor;
    }

    public void onReappear(RowPresenter.ViewHolder rowViewHolder) {
        ViewHolder vh = (ViewHolder) rowViewHolder;
        if (vh.view.hasFocus()) {
            vh.mProgressBar.requestFocus();
        }
    }

    private static int getDefaultProgressColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.playbackProgressPrimaryColor, outValue, true)) {
            return context.getResources().getColor(outValue.resourceId);
        }
        return context.getResources().getColor(C0364R.color.lb_playback_progress_color_no_theme);
    }

    private static int getDefaultSecondaryProgressColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.playbackProgressSecondaryColor, outValue, true)) {
            return context.getResources().getColor(outValue.resourceId);
        }
        return context.getResources().getColor(C0364R.color.lb_playback_progress_secondary_color_no_theme);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_playback_transport_controls_row, parent, false), this.mDescriptionPresenter);
        initRow(vh);
        return vh;
    }

    private void initRow(final ViewHolder vh) {
        int i;
        int i2;
        vh.mControlsVh = (ControlBarPresenter.ViewHolder) this.mPlaybackControlsPresenter.onCreateViewHolder(vh.mControlsDock);
        SeekBar seekBar = vh.mProgressBar;
        if (this.mProgressColorSet) {
            i = this.mProgressColor;
        } else {
            i = getDefaultProgressColor(vh.mControlsDock.getContext());
        }
        seekBar.setProgressColor(i);
        SeekBar seekBar2 = vh.mProgressBar;
        if (this.mSecondaryProgressColorSet) {
            i2 = this.mSecondaryProgressColor;
        } else {
            i2 = getDefaultSecondaryProgressColor(vh.mControlsDock.getContext());
        }
        seekBar2.setSecondaryProgressColor(i2);
        vh.mControlsDock.addView(vh.mControlsVh.view);
        vh.mSecondaryControlsVh = (ControlBarPresenter.ViewHolder) this.mSecondaryControlsPresenter.onCreateViewHolder(vh.mSecondaryControlsDock);
        vh.mSecondaryControlsDock.addView(vh.mSecondaryControlsVh.view);
        ((PlaybackTransportRowView) vh.view.findViewById(C0364R.C0366id.transport_row)).setOnUnhandledKeyListener(new PlaybackTransportRowView.OnUnhandledKeyListener() {
            public boolean onUnhandledKey(KeyEvent event) {
                if (vh.getOnKeyListener() == null || !vh.getOnKeyListener().onKey(vh.view, event.getKeyCode(), event)) {
                    return false;
                }
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        ViewHolder vh = (ViewHolder) holder;
        PlaybackControlsRow row = (PlaybackControlsRow) vh.getRow();
        if (row.getItem() == null) {
            vh.mDescriptionDock.setVisibility(8);
        } else {
            vh.mDescriptionDock.setVisibility(0);
            if (vh.mDescriptionViewHolder != null) {
                this.mDescriptionPresenter.onBindViewHolder(vh.mDescriptionViewHolder, row.getItem());
            }
        }
        if (row.getImageDrawable() == null) {
            vh.mImageView.setVisibility(8);
        } else {
            vh.mImageView.setVisibility(0);
        }
        vh.mImageView.setImageDrawable(row.getImageDrawable());
        vh.mControlsBoundData.adapter = row.getPrimaryActionsAdapter();
        vh.mControlsBoundData.presenter = vh.getPresenter(true);
        vh.mControlsBoundData.mRowViewHolder = vh;
        this.mPlaybackControlsPresenter.onBindViewHolder(vh.mControlsVh, vh.mControlsBoundData);
        vh.mSecondaryBoundData.adapter = row.getSecondaryActionsAdapter();
        vh.mSecondaryBoundData.presenter = vh.getPresenter(false);
        vh.mSecondaryBoundData.mRowViewHolder = vh;
        this.mSecondaryControlsPresenter.onBindViewHolder(vh.mSecondaryControlsVh, vh.mSecondaryBoundData);
        vh.setTotalTime(row.getDuration());
        vh.setCurrentPosition(row.getCurrentPosition());
        vh.setBufferedPosition(row.getBufferedPosition());
        row.setOnPlaybackProgressChangedListener(vh.mListener);
    }

    /* access modifiers changed from: protected */
    public void onUnbindRowViewHolder(RowPresenter.ViewHolder holder) {
        ViewHolder vh = (ViewHolder) holder;
        PlaybackControlsRow row = (PlaybackControlsRow) vh.getRow();
        if (vh.mDescriptionViewHolder != null) {
            this.mDescriptionPresenter.onUnbindViewHolder(vh.mDescriptionViewHolder);
        }
        this.mPlaybackControlsPresenter.onUnbindViewHolder(vh.mControlsVh);
        this.mSecondaryControlsPresenter.onUnbindViewHolder(vh.mSecondaryControlsVh);
        row.setOnPlaybackProgressChangedListener(null);
        super.onUnbindRowViewHolder(holder);
    }

    /* access modifiers changed from: protected */
    public void onProgressBarClicked(ViewHolder vh) {
        if (vh != null) {
            if (vh.mPlayPauseAction == null) {
                vh.mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(vh.view.getContext());
            }
            if (vh.getOnItemViewClickedListener() != null) {
                vh.getOnItemViewClickedListener().onItemClicked(vh, vh.mPlayPauseAction, vh, vh.getRow());
            }
            OnActionClickedListener onActionClickedListener = this.mOnActionClickedListener;
            if (onActionClickedListener != null) {
                onActionClickedListener.onActionClicked(vh.mPlayPauseAction);
            }
        }
    }

    public void setDefaultSeekIncrement(float ratio) {
        this.mDefaultSeekIncrement = ratio;
    }

    public float getDefaultSeekIncrement() {
        return this.mDefaultSeekIncrement;
    }

    /* access modifiers changed from: protected */
    public void onRowViewSelected(RowPresenter.ViewHolder vh, boolean selected) {
        super.onRowViewSelected(vh, selected);
        if (selected) {
            ((ViewHolder) vh).dispatchItemSelection();
        }
    }

    /* access modifiers changed from: protected */
    public void onRowViewAttachedToWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewAttachedToWindow(vh);
        Presenter presenter = this.mDescriptionPresenter;
        if (presenter != null) {
            presenter.onViewAttachedToWindow(((ViewHolder) vh).mDescriptionViewHolder);
        }
    }

    /* access modifiers changed from: protected */
    public void onRowViewDetachedFromWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewDetachedFromWindow(vh);
        Presenter presenter = this.mDescriptionPresenter;
        if (presenter != null) {
            presenter.onViewDetachedFromWindow(((ViewHolder) vh).mDescriptionViewHolder);
        }
    }
}
