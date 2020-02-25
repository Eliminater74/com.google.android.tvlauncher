package androidx.leanback.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.p001v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;

public class PlaybackControlsRowPresenter extends PlaybackRowPresenter {
    static float sShadowZ;
    private final ControlBarPresenter.OnControlClickedListener mOnControlClickedListener;
    private final ControlBarPresenter.OnControlSelectedListener mOnControlSelectedListener;
    OnActionClickedListener mOnActionClickedListener;
    PlaybackControlsPresenter mPlaybackControlsPresenter;
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private Presenter mDescriptionPresenter;
    private int mProgressColor;
    private boolean mProgressColorSet;
    private boolean mSecondaryActionsHidden;
    private ControlBarPresenter mSecondaryControlsPresenter;

    public PlaybackControlsRowPresenter(Presenter descriptionPresenter) {
        this.mBackgroundColor = 0;
        this.mProgressColor = 0;
        this.mOnControlSelectedListener = new ControlBarPresenter.OnControlSelectedListener() {
            public void onControlSelected(Presenter.ViewHolder itemViewHolder, Object item, ControlBarPresenter.BoundData data) {
                ViewHolder vh = ((BoundData) data).mRowViewHolder;
                if (vh.mSelectedViewHolder != itemViewHolder || vh.mSelectedItem != item) {
                    vh.mSelectedViewHolder = itemViewHolder;
                    vh.mSelectedItem = item;
                    vh.dispatchItemSelection();
                }
            }
        };
        this.mOnControlClickedListener = new ControlBarPresenter.OnControlClickedListener() {
            public void onControlClicked(Presenter.ViewHolder itemViewHolder, Object item, ControlBarPresenter.BoundData data) {
                ViewHolder vh = ((BoundData) data).mRowViewHolder;
                if (vh.getOnItemViewClickedListener() != null) {
                    vh.getOnItemViewClickedListener().onItemClicked(itemViewHolder, item, vh, vh.getRow());
                }
                if (PlaybackControlsRowPresenter.this.mOnActionClickedListener != null && (item instanceof Action)) {
                    PlaybackControlsRowPresenter.this.mOnActionClickedListener.onActionClicked((Action) item);
                }
            }
        };
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        this.mDescriptionPresenter = descriptionPresenter;
        this.mPlaybackControlsPresenter = new PlaybackControlsPresenter(C0364R.layout.lb_playback_controls);
        this.mSecondaryControlsPresenter = new ControlBarPresenter(C0364R.layout.lb_control_bar);
        this.mPlaybackControlsPresenter.setOnControlSelectedListener(this.mOnControlSelectedListener);
        this.mSecondaryControlsPresenter.setOnControlSelectedListener(this.mOnControlSelectedListener);
        this.mPlaybackControlsPresenter.setOnControlClickedListener(this.mOnControlClickedListener);
        this.mSecondaryControlsPresenter.setOnControlClickedListener(this.mOnControlClickedListener);
    }

    public PlaybackControlsRowPresenter() {
        this(null);
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mOnActionClickedListener;
    }

    public void setOnActionClickedListener(OnActionClickedListener listener) {
        this.mOnActionClickedListener = listener;
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void setBackgroundColor(@ColorInt int color) {
        this.mBackgroundColor = color;
        this.mBackgroundColorSet = true;
    }

    @ColorInt
    public int getProgressColor() {
        return this.mProgressColor;
    }

    public void setProgressColor(@ColorInt int color) {
        this.mProgressColor = color;
        this.mProgressColorSet = true;
    }

    public void setSecondaryActionsHidden(boolean hidden) {
        this.mSecondaryActionsHidden = hidden;
    }

    public boolean areSecondaryActionsHidden() {
        return this.mSecondaryActionsHidden;
    }

    public void showBottomSpace(ViewHolder vh, boolean show) {
        vh.mBottomSpacer.setVisibility(show ? 0 : 8);
    }

    public void showPrimaryActions(ViewHolder vh) {
        this.mPlaybackControlsPresenter.showPrimaryActions(vh.mControlsVh);
        if (vh.view.hasFocus()) {
            this.mPlaybackControlsPresenter.resetFocus(vh.mControlsVh);
        }
    }

    public void onReappear(RowPresenter.ViewHolder rowViewHolder) {
        showPrimaryActions((ViewHolder) rowViewHolder);
    }

    private int getDefaultBackgroundColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.defaultBrandColor, outValue, true)) {
            return context.getResources().getColor(outValue.resourceId);
        }
        return context.getResources().getColor(C0364R.color.lb_default_brand_color);
    }

    private int getDefaultProgressColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.playbackProgressPrimaryColor, outValue, true)) {
            return context.getResources().getColor(outValue.resourceId);
        }
        return context.getResources().getColor(C0364R.color.lb_playback_progress_color_no_theme);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_playback_controls_row, parent, false), this.mDescriptionPresenter);
        initRow(vh);
        return vh;
    }

    private void initRow(final ViewHolder vh) {
        int i;
        int i2;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) vh.mControlsDock.getLayoutParams();
        vh.mControlsDockMarginStart = lp.getMarginStart();
        vh.mControlsDockMarginEnd = lp.getMarginEnd();
        vh.mControlsVh = (PlaybackControlsPresenter.ViewHolder) this.mPlaybackControlsPresenter.onCreateViewHolder(vh.mControlsDock);
        PlaybackControlsPresenter playbackControlsPresenter = this.mPlaybackControlsPresenter;
        PlaybackControlsPresenter.ViewHolder viewHolder = vh.mControlsVh;
        if (this.mProgressColorSet) {
            i = this.mProgressColor;
        } else {
            i = getDefaultProgressColor(vh.mControlsDock.getContext());
        }
        playbackControlsPresenter.setProgressColor(viewHolder, i);
        PlaybackControlsPresenter playbackControlsPresenter2 = this.mPlaybackControlsPresenter;
        PlaybackControlsPresenter.ViewHolder viewHolder2 = vh.mControlsVh;
        if (this.mBackgroundColorSet) {
            i2 = this.mBackgroundColor;
        } else {
            i2 = getDefaultBackgroundColor(vh.view.getContext());
        }
        playbackControlsPresenter2.setBackgroundColor(viewHolder2, i2);
        vh.mControlsDock.addView(vh.mControlsVh.view);
        vh.mSecondaryControlsVh = this.mSecondaryControlsPresenter.onCreateViewHolder(vh.mSecondaryControlsDock);
        if (!this.mSecondaryActionsHidden) {
            vh.mSecondaryControlsDock.addView(vh.mSecondaryControlsVh.view);
        }
        ((PlaybackControlsRowView) vh.view).setOnUnhandledKeyListener(new PlaybackControlsRowView.OnUnhandledKeyListener() {
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
        this.mPlaybackControlsPresenter.enableSecondaryActions(this.mSecondaryActionsHidden);
        if (row.getItem() == null) {
            vh.mDescriptionDock.setVisibility(8);
            vh.mSpacer.setVisibility(8);
        } else {
            vh.mDescriptionDock.setVisibility(0);
            if (vh.mDescriptionViewHolder != null) {
                this.mDescriptionPresenter.onBindViewHolder(vh.mDescriptionViewHolder, row.getItem());
            }
            vh.mSpacer.setVisibility(0);
        }
        if (row.getImageDrawable() == null || row.getItem() == null) {
            vh.mImageView.setImageDrawable(null);
            updateCardLayout(vh, -2);
        } else {
            vh.mImageView.setImageDrawable(row.getImageDrawable());
            updateCardLayout(vh, vh.mImageView.getLayoutParams().height);
        }
        vh.mControlsBoundData.adapter = row.getPrimaryActionsAdapter();
        vh.mControlsBoundData.secondaryActionsAdapter = row.getSecondaryActionsAdapter();
        vh.mControlsBoundData.presenter = vh.getPresenter(true);
        vh.mControlsBoundData.mRowViewHolder = vh;
        this.mPlaybackControlsPresenter.onBindViewHolder(vh.mControlsVh, vh.mControlsBoundData);
        vh.mSecondaryBoundData.adapter = row.getSecondaryActionsAdapter();
        vh.mSecondaryBoundData.presenter = vh.getPresenter(false);
        vh.mSecondaryBoundData.mRowViewHolder = vh;
        this.mSecondaryControlsPresenter.onBindViewHolder(vh.mSecondaryControlsVh, vh.mSecondaryBoundData);
        this.mPlaybackControlsPresenter.setTotalTime(vh.mControlsVh, row.getTotalTime());
        this.mPlaybackControlsPresenter.setCurrentTime(vh.mControlsVh, row.getCurrentTime());
        this.mPlaybackControlsPresenter.setSecondaryProgress(vh.mControlsVh, row.getBufferedProgress());
        row.setOnPlaybackProgressChangedListener(vh.mListener);
    }

    private void updateCardLayout(ViewHolder vh, int height) {
        int i;
        ViewGroup.LayoutParams lp = vh.mCardRightPanel.getLayoutParams();
        lp.height = height;
        vh.mCardRightPanel.setLayoutParams(lp);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) vh.mControlsDock.getLayoutParams();
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) vh.mDescriptionDock.getLayoutParams();
        if (height == -2) {
            llp.height = -2;
            mlp.setMarginStart(0);
            mlp.setMarginEnd(0);
            vh.mCard.setBackground(null);
            vh.setOutline(vh.mControlsDock);
            this.mPlaybackControlsPresenter.enableTimeMargins(vh.mControlsVh, true);
        } else {
            llp.height = 0;
            llp.weight = 1.0f;
            mlp.setMarginStart(vh.mControlsDockMarginStart);
            mlp.setMarginEnd(vh.mControlsDockMarginEnd);
            ViewGroup viewGroup = vh.mCard;
            if (this.mBackgroundColorSet) {
                i = this.mBackgroundColor;
            } else {
                i = getDefaultBackgroundColor(vh.mCard.getContext());
            }
            viewGroup.setBackgroundColor(i);
            vh.setOutline(vh.mCard);
            this.mPlaybackControlsPresenter.enableTimeMargins(vh.mControlsVh, false);
        }
        vh.mDescriptionDock.setLayoutParams(llp);
        vh.mControlsDock.setLayoutParams(mlp);
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

    static class BoundData extends PlaybackControlsPresenter.BoundData {
        ViewHolder mRowViewHolder;

        BoundData() {
        }
    }

    public class ViewHolder extends PlaybackRowPresenter.ViewHolder {
        public final Presenter.ViewHolder mDescriptionViewHolder;
        final View mBottomSpacer;
        final ViewGroup mCard;
        final ViewGroup mCardRightPanel;
        final ViewGroup mControlsDock;
        final ViewGroup mDescriptionDock;
        final ImageView mImageView;
        final ViewGroup mSecondaryControlsDock;
        final View mSpacer;
        View mBgView;
        BoundData mControlsBoundData = new BoundData();
        int mControlsDockMarginEnd;
        int mControlsDockMarginStart;
        PlaybackControlsPresenter.ViewHolder mControlsVh;
        final PlaybackControlsRow.OnPlaybackProgressCallback mListener = new PlaybackControlsRow.OnPlaybackProgressCallback() {
            public void onCurrentPositionChanged(PlaybackControlsRow row, long ms) {
                PlaybackControlsRowPresenter.this.mPlaybackControlsPresenter.setCurrentTimeLong(ViewHolder.this.mControlsVh, ms);
            }

            public void onDurationChanged(PlaybackControlsRow row, long ms) {
                PlaybackControlsRowPresenter.this.mPlaybackControlsPresenter.setTotalTimeLong(ViewHolder.this.mControlsVh, ms);
            }

            public void onBufferedPositionChanged(PlaybackControlsRow row, long ms) {
                PlaybackControlsRowPresenter.this.mPlaybackControlsPresenter.setSecondaryProgressLong(ViewHolder.this.mControlsVh, ms);
            }
        };
        BoundData mSecondaryBoundData = new BoundData();
        Presenter.ViewHolder mSecondaryControlsVh;
        Object mSelectedItem;
        Presenter.ViewHolder mSelectedViewHolder;

        ViewHolder(View rootView, Presenter descriptionPresenter) {
            super(rootView);
            Presenter.ViewHolder viewHolder;
            this.mCard = (ViewGroup) rootView.findViewById(C0364R.C0366id.controls_card);
            this.mCardRightPanel = (ViewGroup) rootView.findViewById(C0364R.C0366id.controls_card_right_panel);
            this.mImageView = (ImageView) rootView.findViewById(C0364R.C0366id.image);
            this.mDescriptionDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.description_dock);
            this.mControlsDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.controls_dock);
            this.mSecondaryControlsDock = (ViewGroup) rootView.findViewById(C0364R.C0366id.secondary_controls_dock);
            this.mSpacer = rootView.findViewById(C0364R.C0366id.spacer);
            this.mBottomSpacer = rootView.findViewById(C0364R.C0366id.bottom_spacer);
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
                ControlButtonPresenterSelector selector = (ControlButtonPresenterSelector) adapter.getPresenterSelector();
                if (primary) {
                    return selector.getPrimaryPresenter();
                }
                return selector.getSecondaryPresenter();
            }
            if (adapter.size() > 0) {
                obj = adapter.get(0);
            }
            return adapter.getPresenter(obj);
        }

        /* access modifiers changed from: package-private */
        public void setOutline(View view) {
            View view2 = this.mBgView;
            if (view2 != null) {
                RoundedRectHelper.setClipToRoundedOutline(view2, false);
                ViewCompat.setZ(this.mBgView, 0.0f);
            }
            this.mBgView = view;
            RoundedRectHelper.setClipToRoundedOutline(view, true);
            if (PlaybackControlsRowPresenter.sShadowZ == 0.0f) {
                PlaybackControlsRowPresenter.sShadowZ = (float) view.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_controls_z);
            }
            ViewCompat.setZ(view, PlaybackControlsRowPresenter.sShadowZ);
        }
    }
}
