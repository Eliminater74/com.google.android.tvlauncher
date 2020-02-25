package androidx.leanback.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.p001v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.leanback.C0364R;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMediaItemPresenter extends RowPresenter {
    public static final int PLAY_STATE_INITIAL = 0;
    public static final int PLAY_STATE_PAUSED = 1;
    public static final int PLAY_STATE_PLAYING = 2;
    static final Rect sTempRect = new Rect();
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private Presenter mMediaItemActionPresenter;
    private boolean mMediaRowSeparator;
    private int mThemeId;

    public AbstractMediaItemPresenter() {
        this(0);
    }

    public AbstractMediaItemPresenter(int themeId) {
        this.mBackgroundColor = 0;
        this.mMediaItemActionPresenter = new MediaItemActionPresenter();
        this.mThemeId = themeId;
        setHeaderPresenter(null);
    }

    static int calculateMediaItemNumberFlipperIndex(ViewHolder vh) {
        int newPlayState = vh.mRowPresenter.getMediaPlayState(vh.getRowObject());
        int childIndex = -1;
        if (newPlayState == 0) {
            if (vh.mMediaItemNumberView != null) {
                childIndex = vh.mMediaItemNumberViewFlipper.indexOfChild(vh.mMediaItemNumberView);
            }
            return childIndex;
        } else if (newPlayState == 1) {
            if (vh.mMediaItemPausedView != null) {
                childIndex = vh.mMediaItemNumberViewFlipper.indexOfChild(vh.mMediaItemPausedView);
            }
            return childIndex;
        } else if (newPlayState != 2) {
            return -1;
        } else {
            if (vh.mMediaItemPlayingView != null) {
                childIndex = vh.mMediaItemNumberViewFlipper.indexOfChild(vh.mMediaItemPlayingView);
            }
            return childIndex;
        }
    }

    static ValueAnimator updateSelector(View selectorView, View focusChangedView, ValueAnimator layoutAnimator, boolean isDetails) {
        ValueAnimator layoutAnimator2;
        int animationDuration = focusChangedView.getContext().getResources().getInteger(17694720);
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        int layoutDirection = ViewCompat.getLayoutDirection(selectorView);
        if (!focusChangedView.hasFocus()) {
            selectorView.animate().cancel();
            selectorView.animate().alpha(0.0f).setDuration((long) animationDuration).setInterpolator(interpolator).start();
            return layoutAnimator;
        }
        if (layoutAnimator != null) {
            layoutAnimator.cancel();
            layoutAnimator2 = null;
        } else {
            layoutAnimator2 = layoutAnimator;
        }
        float currentAlpha = selectorView.getAlpha();
        selectorView.animate().alpha(1.0f).setDuration((long) animationDuration).setInterpolator(interpolator).start();
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) selectorView.getLayoutParams();
        ViewGroup rootView = (ViewGroup) selectorView.getParent();
        sTempRect.set(0, 0, focusChangedView.getWidth(), focusChangedView.getHeight());
        rootView.offsetDescendantRectToMyCoords(focusChangedView, sTempRect);
        if (isDetails) {
            if (layoutDirection == 1) {
                sTempRect.right += rootView.getHeight();
                sTempRect.left -= rootView.getHeight() / 2;
            } else {
                sTempRect.left -= rootView.getHeight();
                sTempRect.right += rootView.getHeight() / 2;
            }
        }
        int targetLeft = sTempRect.left;
        int targetWidth = sTempRect.width();
        float deltaWidth = (float) (lp.width - targetWidth);
        final float deltaLeft = (float) (lp.leftMargin - targetLeft);
        if (!(deltaLeft == 0.0f && deltaWidth == 0.0f)) {
            if (currentAlpha == 0.0f) {
                lp.width = targetWidth;
                lp.leftMargin = targetLeft;
                selectorView.requestLayout();
            } else {
                ValueAnimator layoutAnimator3 = ValueAnimator.ofFloat(0.0f, 1.0f);
                layoutAnimator3.setDuration((long) animationDuration);
                layoutAnimator3.setInterpolator(interpolator);
                final ViewGroup.MarginLayoutParams marginLayoutParams = lp;
                final int i = targetLeft;
                float deltaWidth2 = deltaWidth;
                final int i2 = targetWidth;
                final float f = deltaWidth2;
                final View view = selectorView;
                layoutAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fractionToEnd = 1.0f - valueAnimator.getAnimatedFraction();
                        marginLayoutParams.leftMargin = Math.round(((float) i) + (deltaLeft * fractionToEnd));
                        marginLayoutParams.width = Math.round(((float) i2) + (f * fractionToEnd));
                        view.requestLayout();
                    }
                });
                layoutAnimator3.start();
                return layoutAnimator3;
            }
        }
        return layoutAnimator2;
    }

    /* access modifiers changed from: protected */
    public abstract void onBindMediaDetails(ViewHolder viewHolder, Object obj);

    public int getThemeId() {
        return this.mThemeId;
    }

    public void setThemeId(int themeId) {
        this.mThemeId = themeId;
    }

    public Presenter getActionPresenter() {
        return this.mMediaItemActionPresenter;
    }

    public void setActionPresenter(Presenter actionPresenter) {
        this.mMediaItemActionPresenter = actionPresenter;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        int i = this.mThemeId;
        if (i != 0) {
            context = new ContextThemeWrapper(context, i);
        }
        ViewHolder vh = new ViewHolder(LayoutInflater.from(context).inflate(C0364R.layout.lb_row_media_item, parent, false));
        vh.mRowPresenter = this;
        if (this.mBackgroundColorSet) {
            vh.mMediaRowView.setBackgroundColor(this.mBackgroundColor);
        }
        return vh;
    }

    public boolean isUsingDefaultSelectEffect() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isClippingChildren() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(RowPresenter.ViewHolder vh, Object item) {
        super.onBindRowViewHolder(vh, item);
        ViewHolder mvh = (ViewHolder) vh;
        onBindRowActions(mvh);
        mvh.getMediaItemRowSeparator().setVisibility(hasMediaRowSeparator() ? 0 : 8);
        onBindMediaPlayState(mvh);
        onBindMediaDetails((ViewHolder) vh, item);
    }

    /* access modifiers changed from: protected */
    public void onBindRowActions(ViewHolder vh) {
        vh.onBindRowActions();
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundColorSet = true;
        this.mBackgroundColor = color;
    }

    public void setHasMediaRowSeparator(boolean hasSeparator) {
        this.mMediaRowSeparator = hasSeparator;
    }

    public boolean hasMediaRowSeparator() {
        return this.mMediaRowSeparator;
    }

    /* access modifiers changed from: protected */
    public void onUnbindMediaDetails(ViewHolder vh) {
    }

    public void onBindMediaPlayState(ViewHolder vh) {
        int childIndex = calculateMediaItemNumberFlipperIndex(vh);
        if (childIndex != -1 && vh.mMediaItemNumberViewFlipper.getDisplayedChild() != childIndex) {
            vh.mMediaItemNumberViewFlipper.setDisplayedChild(childIndex);
        }
    }

    public void onUnbindMediaPlayState(ViewHolder vh) {
    }

    /* access modifiers changed from: protected */
    public int getMediaPlayState(Object item) {
        return 0;
    }

    public static class ViewHolder extends RowPresenter.ViewHolder {
        final TextView mMediaItemNumberView;
        final ViewFlipper mMediaItemNumberViewFlipper;
        final View mMediaItemPausedView;
        final View mMediaItemPlayingView;
        final View mMediaRowView;
        final View mSelectorView;
        private final List<Presenter.ViewHolder> mActionViewHolders = new ArrayList();
        private final ViewGroup mMediaItemActionsContainer;
        private final View mMediaItemDetailsView;
        private final TextView mMediaItemDurationView;
        private final TextView mMediaItemNameView;
        private final View mMediaItemRowSeparator;
        ValueAnimator mFocusViewAnimator;
        MultiActionsProvider.MultiAction[] mMediaItemRowActions;
        AbstractMediaItemPresenter mRowPresenter;

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.widget.ViewFlipper, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public ViewHolder(View view) {
            super(view);
            this.mSelectorView = view.findViewById(C0364R.C0366id.mediaRowSelector);
            this.mMediaRowView = view.findViewById(C0364R.C0366id.mediaItemRow);
            this.mMediaItemDetailsView = view.findViewById(C0364R.C0366id.mediaItemDetails);
            this.mMediaItemNameView = (TextView) view.findViewById(C0364R.C0366id.mediaItemName);
            this.mMediaItemDurationView = (TextView) view.findViewById(C0364R.C0366id.mediaItemDuration);
            this.mMediaItemRowSeparator = view.findViewById(C0364R.C0366id.mediaRowSeparator);
            this.mMediaItemActionsContainer = (ViewGroup) view.findViewById(C0364R.C0366id.mediaItemActionsContainer);
            getMediaItemDetailsView().setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                        BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                        ViewHolder viewHolder = ViewHolder.this;
                        onItemViewClickedListener.onItemClicked(null, null, viewHolder, viewHolder.getRowObject());
                    }
                }
            });
            getMediaItemDetailsView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View view, boolean hasFocus) {
                    ViewHolder viewHolder = ViewHolder.this;
                    viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view, ViewHolder.this.mFocusViewAnimator, true);
                }
            });
            this.mMediaItemNumberViewFlipper = (ViewFlipper) view.findViewById(C0364R.C0366id.mediaItemNumberViewFlipper);
            TypedValue typedValue = new TypedValue();
            View mergeView = LayoutInflater.from(view.getContext()).inflate(view.getContext().getTheme().resolveAttribute(C0364R.attr.playbackMediaItemNumberViewFlipperLayout, typedValue, true) ? typedValue.resourceId : C0364R.layout.lb_media_item_number_view_flipper, (ViewGroup) this.mMediaItemNumberViewFlipper, true);
            this.mMediaItemNumberView = (TextView) mergeView.findViewById(C0364R.C0366id.initial);
            this.mMediaItemPausedView = mergeView.findViewById(C0364R.C0366id.paused);
            this.mMediaItemPlayingView = mergeView.findViewById(C0364R.C0366id.playing);
        }

        public void onBindRowActions() {
            int i = getMediaItemActionsContainer().getChildCount();
            while (true) {
                i--;
                if (i < this.mActionViewHolders.size()) {
                    break;
                }
                getMediaItemActionsContainer().removeViewAt(i);
                this.mActionViewHolders.remove(i);
            }
            this.mMediaItemRowActions = null;
            Object rowObject = getRowObject();
            if (rowObject instanceof MultiActionsProvider) {
                MultiActionsProvider.MultiAction[] actionList = ((MultiActionsProvider) rowObject).getActions();
                Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
                if (actionPresenter != null) {
                    this.mMediaItemRowActions = actionList;
                    for (int i2 = this.mActionViewHolders.size(); i2 < actionList.length; i2++) {
                        final int actionIndex = i2;
                        final Presenter.ViewHolder actionViewHolder = actionPresenter.onCreateViewHolder(getMediaItemActionsContainer());
                        getMediaItemActionsContainer().addView(actionViewHolder.view);
                        this.mActionViewHolders.add(actionViewHolder);
                        actionViewHolder.view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            public void onFocusChange(View view, boolean hasFocus) {
                                ViewHolder viewHolder = ViewHolder.this;
                                viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view, ViewHolder.this.mFocusViewAnimator, false);
                            }
                        });
                        actionViewHolder.view.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                                    BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                                    Presenter.ViewHolder viewHolder = actionViewHolder;
                                    MultiActionsProvider.MultiAction multiAction = ViewHolder.this.mMediaItemRowActions[actionIndex];
                                    ViewHolder viewHolder2 = ViewHolder.this;
                                    onItemViewClickedListener.onItemClicked(viewHolder, multiAction, viewHolder2, viewHolder2.getRowObject());
                                }
                            }
                        });
                    }
                    if (this.mMediaItemActionsContainer != null) {
                        for (int i3 = 0; i3 < actionList.length; i3++) {
                            Presenter.ViewHolder avh = this.mActionViewHolders.get(i3);
                            actionPresenter.onUnbindViewHolder(avh);
                            actionPresenter.onBindViewHolder(avh, this.mMediaItemRowActions[i3]);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int findActionIndex(MultiActionsProvider.MultiAction action) {
            if (this.mMediaItemRowActions == null) {
                return -1;
            }
            int i = 0;
            while (true) {
                MultiActionsProvider.MultiAction[] multiActionArr = this.mMediaItemRowActions;
                if (i >= multiActionArr.length) {
                    return -1;
                }
                if (multiActionArr[i] == action) {
                    return i;
                }
                i++;
            }
        }

        public void notifyActionChanged(MultiActionsProvider.MultiAction action) {
            int actionIndex;
            Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
            if (actionPresenter != null && (actionIndex = findActionIndex(action)) >= 0) {
                Presenter.ViewHolder actionViewHolder = this.mActionViewHolders.get(actionIndex);
                actionPresenter.onUnbindViewHolder(actionViewHolder);
                actionPresenter.onBindViewHolder(actionViewHolder, action);
            }
        }

        public void notifyDetailsChanged() {
            this.mRowPresenter.onUnbindMediaDetails(this);
            this.mRowPresenter.onBindMediaDetails(this, getRowObject());
        }

        public void notifyPlayStateChanged() {
            this.mRowPresenter.onBindMediaPlayState(this);
        }

        public View getSelectorView() {
            return this.mSelectorView;
        }

        public ViewFlipper getMediaItemNumberViewFlipper() {
            return this.mMediaItemNumberViewFlipper;
        }

        public TextView getMediaItemNumberView() {
            return this.mMediaItemNumberView;
        }

        public View getMediaItemPausedView() {
            return this.mMediaItemPausedView;
        }

        public View getMediaItemPlayingView() {
            return this.mMediaItemPlayingView;
        }

        public void setSelectedMediaItemNumberView(int position) {
            if (position >= 0 && position < this.mMediaItemNumberViewFlipper.getChildCount()) {
                this.mMediaItemNumberViewFlipper.setDisplayedChild(position);
            }
        }

        public TextView getMediaItemNameView() {
            return this.mMediaItemNameView;
        }

        public TextView getMediaItemDurationView() {
            return this.mMediaItemDurationView;
        }

        public View getMediaItemDetailsView() {
            return this.mMediaItemDetailsView;
        }

        public View getMediaItemRowSeparator() {
            return this.mMediaItemRowSeparator;
        }

        public ViewGroup getMediaItemActionsContainer() {
            return this.mMediaItemActionsContainer;
        }

        public MultiActionsProvider.MultiAction[] getMediaItemRowActions() {
            return this.mMediaItemRowActions;
        }
    }
}
