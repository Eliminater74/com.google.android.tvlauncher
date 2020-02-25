package androidx.leanback.widget;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.leanback.C0364R;
import androidx.leanback.util.MathUtil;

class PlaybackControlsPresenter extends ControlBarPresenter {
    private static int sChildMarginBigger;
    private static int sChildMarginBiggest;
    private boolean mMoreActionsEnabled = true;

    public PlaybackControlsPresenter(int layoutResourceId) {
        super(layoutResourceId);
    }

    static void formatTime(long seconds, StringBuilder sb) {
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long seconds2 = seconds - (minutes * 60);
        long minutes2 = minutes - (60 * hours);
        sb.setLength(0);
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

    public void enableSecondaryActions(boolean enable) {
        this.mMoreActionsEnabled = enable;
    }

    public boolean areMoreActionsEnabled() {
        return this.mMoreActionsEnabled;
    }

    public void setProgressColor(ViewHolder vh, @ColorInt int color) {
        ((LayerDrawable) vh.mProgressBar.getProgressDrawable()).setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(color), 3, 1));
    }

    public void setTotalTime(ViewHolder vh, int ms) {
        setTotalTimeLong(vh, (long) ms);
    }

    public void setTotalTimeLong(ViewHolder vh, long ms) {
        vh.setTotalTime(ms);
    }

    public int getTotalTime(ViewHolder vh) {
        return MathUtil.safeLongToInt(getTotalTimeLong(vh));
    }

    public long getTotalTimeLong(ViewHolder vh) {
        return vh.getTotalTime();
    }

    public void setCurrentTime(ViewHolder vh, int ms) {
        setCurrentTimeLong(vh, (long) ms);
    }

    public void setCurrentTimeLong(ViewHolder vh, long ms) {
        vh.setCurrentTime(ms);
    }

    public int getCurrentTime(ViewHolder vh) {
        return MathUtil.safeLongToInt(getCurrentTimeLong(vh));
    }

    public long getCurrentTimeLong(ViewHolder vh) {
        return vh.getCurrentTime();
    }

    public void setSecondaryProgress(ViewHolder vh, int progressMs) {
        setSecondaryProgressLong(vh, (long) progressMs);
    }

    public void setSecondaryProgressLong(ViewHolder vh, long progressMs) {
        vh.setSecondaryProgress(progressMs);
    }

    public int getSecondaryProgress(ViewHolder vh) {
        return MathUtil.safeLongToInt(getSecondaryProgressLong(vh));
    }

    public long getSecondaryProgressLong(ViewHolder vh) {
        return vh.getSecondaryProgress();
    }

    public void showPrimaryActions(ViewHolder vh) {
        if (vh.mMoreActionsShowing) {
            vh.toggleMoreActions();
        }
    }

    public void resetFocus(ViewHolder vh) {
        vh.mControlBar.requestFocus();
    }

    public void enableTimeMargins(ViewHolder vh, boolean enable) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) vh.mCurrentTime.getLayoutParams();
        int i = 0;
        lp.setMarginStart(enable ? vh.mCurrentTimeMarginStart : 0);
        vh.mCurrentTime.setLayoutParams(lp);
        ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) vh.mTotalTime.getLayoutParams();
        if (enable) {
            i = vh.mTotalTimeMarginEnd;
        }
        lp2.setMarginEnd(i);
        vh.mTotalTime.setLayoutParams(lp2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutResourceId(), parent, false));
    }

    public void onBindViewHolder(Presenter.ViewHolder holder, Object item) {
        ViewHolder vh = (ViewHolder) holder;
        BoundData data = (BoundData) item;
        if (vh.mMoreActionsAdapter != data.secondaryActionsAdapter) {
            vh.mMoreActionsAdapter = data.secondaryActionsAdapter;
            vh.mMoreActionsAdapter.registerObserver(vh.mMoreActionsObserver);
            vh.mMoreActionsShowing = false;
        }
        super.onBindViewHolder(holder, item);
        vh.showMoreActions(this.mMoreActionsEnabled);
    }

    public void onUnbindViewHolder(Presenter.ViewHolder holder) {
        super.onUnbindViewHolder(holder);
        ViewHolder vh = (ViewHolder) holder;
        if (vh.mMoreActionsAdapter != null) {
            vh.mMoreActionsAdapter.unregisterObserver(vh.mMoreActionsObserver);
            vh.mMoreActionsAdapter = null;
        }
    }

    /* access modifiers changed from: package-private */
    public int getChildMarginBigger(Context context) {
        if (sChildMarginBigger == 0) {
            sChildMarginBigger = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_controls_child_margin_bigger);
        }
        return sChildMarginBigger;
    }

    /* access modifiers changed from: package-private */
    public int getChildMarginBiggest(Context context) {
        if (sChildMarginBiggest == 0) {
            sChildMarginBiggest = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_controls_child_margin_biggest);
        }
        return sChildMarginBiggest;
    }

    static class BoundData extends ControlBarPresenter.BoundData {
        ObjectAdapter secondaryActionsAdapter;

        BoundData() {
        }
    }

    class ViewHolder extends ControlBarPresenter.ViewHolder {
        final TextView mCurrentTime;
        final FrameLayout mMoreActionsDock;
        final ProgressBar mProgressBar;
        final TextView mTotalTime;
        long mCurrentTimeInMs = -1;
        int mCurrentTimeMarginStart;
        StringBuilder mCurrentTimeStringBuilder = new StringBuilder();
        ObjectAdapter mMoreActionsAdapter;
        ObjectAdapter.DataObserver mMoreActionsObserver;
        boolean mMoreActionsShowing;
        Presenter.ViewHolder mMoreActionsViewHolder;
        long mSecondaryProgressInMs = -1;
        long mTotalTimeInMs = -1;
        int mTotalTimeMarginEnd;
        StringBuilder mTotalTimeStringBuilder = new StringBuilder();

        ViewHolder(View rootView) {
            super(rootView);
            this.mMoreActionsDock = (FrameLayout) rootView.findViewById(C0364R.C0366id.more_actions_dock);
            this.mCurrentTime = (TextView) rootView.findViewById(C0364R.C0366id.current_time);
            this.mTotalTime = (TextView) rootView.findViewById(C0364R.C0366id.total_time);
            this.mProgressBar = (ProgressBar) rootView.findViewById(C0364R.C0366id.playback_progress);
            this.mMoreActionsObserver = new ObjectAdapter.DataObserver(PlaybackControlsPresenter.this) {
                public void onChanged() {
                    if (ViewHolder.this.mMoreActionsShowing) {
                        ViewHolder viewHolder = ViewHolder.this;
                        viewHolder.showControls(viewHolder.mPresenter);
                    }
                }

                public void onItemRangeChanged(int positionStart, int itemCount) {
                    if (ViewHolder.this.mMoreActionsShowing) {
                        for (int i = 0; i < itemCount; i++) {
                            ViewHolder viewHolder = ViewHolder.this;
                            viewHolder.bindControlToAction(positionStart + i, viewHolder.mPresenter);
                        }
                    }
                }
            };
            this.mCurrentTimeMarginStart = ((ViewGroup.MarginLayoutParams) this.mCurrentTime.getLayoutParams()).getMarginStart();
            this.mTotalTimeMarginEnd = ((ViewGroup.MarginLayoutParams) this.mTotalTime.getLayoutParams()).getMarginEnd();
        }

        /* access modifiers changed from: package-private */
        public void showMoreActions(boolean show) {
            if (show) {
                if (this.mMoreActionsViewHolder == null) {
                    Action action = new PlaybackControlsRow.MoreActions(this.mMoreActionsDock.getContext());
                    this.mMoreActionsViewHolder = this.mPresenter.onCreateViewHolder(this.mMoreActionsDock);
                    this.mPresenter.onBindViewHolder(this.mMoreActionsViewHolder, action);
                    this.mPresenter.setOnClickListener(this.mMoreActionsViewHolder, new View.OnClickListener() {
                        public void onClick(View v) {
                            ViewHolder.this.toggleMoreActions();
                        }
                    });
                }
                if (this.mMoreActionsViewHolder.view.getParent() == null) {
                    this.mMoreActionsDock.addView(this.mMoreActionsViewHolder.view);
                    return;
                }
                return;
            }
            Presenter.ViewHolder viewHolder = this.mMoreActionsViewHolder;
            if (viewHolder != null && viewHolder.view.getParent() != null) {
                this.mMoreActionsDock.removeView(this.mMoreActionsViewHolder.view);
            }
        }

        /* access modifiers changed from: package-private */
        public void toggleMoreActions() {
            this.mMoreActionsShowing = !this.mMoreActionsShowing;
            showControls(this.mPresenter);
        }

        /* access modifiers changed from: package-private */
        public ObjectAdapter getDisplayedAdapter() {
            return this.mMoreActionsShowing ? this.mMoreActionsAdapter : this.mAdapter;
        }

        /* access modifiers changed from: package-private */
        public int getChildMarginFromCenter(Context context, int numControls) {
            int margin = PlaybackControlsPresenter.this.getControlIconWidth(context);
            if (numControls < 4) {
                return margin + PlaybackControlsPresenter.this.getChildMarginBiggest(context);
            }
            if (numControls < 6) {
                return margin + PlaybackControlsPresenter.this.getChildMarginBigger(context);
            }
            return margin + PlaybackControlsPresenter.this.getChildMarginDefault(context);
        }

        /* access modifiers changed from: package-private */
        public long getTotalTime() {
            return this.mTotalTimeInMs;
        }

        /* access modifiers changed from: package-private */
        public void setTotalTime(long totalTimeMs) {
            if (totalTimeMs <= 0) {
                this.mTotalTime.setVisibility(8);
                this.mProgressBar.setVisibility(8);
                return;
            }
            this.mTotalTime.setVisibility(0);
            this.mProgressBar.setVisibility(0);
            this.mTotalTimeInMs = totalTimeMs;
            PlaybackControlsPresenter.formatTime(totalTimeMs / 1000, this.mTotalTimeStringBuilder);
            this.mTotalTime.setText(this.mTotalTimeStringBuilder.toString());
            this.mProgressBar.setMax(Integer.MAX_VALUE);
        }

        /* access modifiers changed from: package-private */
        public long getCurrentTime() {
            return this.mTotalTimeInMs;
        }

        /* access modifiers changed from: package-private */
        public void setCurrentTime(long currentTimeMs) {
            long seconds = currentTimeMs / 1000;
            if (currentTimeMs != this.mCurrentTimeInMs) {
                this.mCurrentTimeInMs = currentTimeMs;
                PlaybackControlsPresenter.formatTime(seconds, this.mCurrentTimeStringBuilder);
                this.mCurrentTime.setText(this.mCurrentTimeStringBuilder.toString());
            }
            double d = (double) this.mCurrentTimeInMs;
            double d2 = (double) this.mTotalTimeInMs;
            Double.isNaN(d);
            Double.isNaN(d2);
            this.mProgressBar.setProgress((int) (2.147483647E9d * (d / d2)));
        }

        /* access modifiers changed from: package-private */
        public long getSecondaryProgress() {
            return this.mSecondaryProgressInMs;
        }

        /* access modifiers changed from: package-private */
        public void setSecondaryProgress(long progressMs) {
            this.mSecondaryProgressInMs = progressMs;
            double d = (double) progressMs;
            double d2 = (double) this.mTotalTimeInMs;
            Double.isNaN(d);
            Double.isNaN(d2);
            this.mProgressBar.setSecondaryProgress((int) (2.147483647E9d * (d / d2)));
        }
    }
}
