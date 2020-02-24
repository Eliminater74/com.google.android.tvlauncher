package com.google.android.tvlauncher.home.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.leanback.widget.HorizontalGridView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.home.util.ChannelStateSettings;
import com.google.android.tvlauncher.home.util.ChannelUtil;
import com.google.android.tvlauncher.util.ScaleFocusHandler;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.AnimUtil;
import com.google.android.tvrecommendations.shared.util.ColorUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class ChannelView extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final float EPS = 0.001f;
    public static final int STATE_ACTIONS_NOT_SELECTED = 12;
    public static final int STATE_ACTIONS_SELECTED = 11;
    public static final int STATE_DEFAULT_ABOVE_SELECTED = 2;
    public static final int STATE_DEFAULT_ABOVE_SELECTED_LAST_ROW = 15;
    public static final int STATE_DEFAULT_APPS_ROW_SELECTED = 5;
    public static final int STATE_DEFAULT_BELOW_SELECTED = 3;
    public static final int STATE_DEFAULT_FAST_SCROLLING_NOT_SELECTED = 7;
    public static final int STATE_DEFAULT_FAST_SCROLLING_SELECTED = 6;
    public static final int STATE_DEFAULT_NOT_SELECTED = 1;
    public static final int STATE_DEFAULT_SELECTED = 0;
    public static final int STATE_DEFAULT_TOP_ROW_SELECTED = 4;
    public static final int STATE_EMPTY_ACTIONS_NOT_SELECTED = 27;
    public static final int STATE_EMPTY_DEFAULT_ABOVE_SELECTED = 18;
    public static final int STATE_EMPTY_DEFAULT_ABOVE_SELECTED_LAST_ROW = 29;
    public static final int STATE_EMPTY_DEFAULT_APPS_ROW_SELECTED = 21;
    public static final int STATE_EMPTY_DEFAULT_BELOW_SELECTED = 19;
    public static final int STATE_EMPTY_DEFAULT_FAST_SCROLLING_NOT_SELECTED = 23;
    public static final int STATE_EMPTY_DEFAULT_FAST_SCROLLING_SELECTED = 22;
    public static final int STATE_EMPTY_DEFAULT_NOT_SELECTED = 17;
    public static final int STATE_EMPTY_DEFAULT_SELECTED = 16;
    public static final int STATE_EMPTY_DEFAULT_TOP_ROW_SELECTED = 20;
    public static final int STATE_EMPTY_MOVE_NOT_SELECTED = 28;
    public static final int STATE_EMPTY_ZOOMED_OUT_NOT_SELECTED = 25;
    public static final int STATE_EMPTY_ZOOMED_OUT_SELECTED = 24;
    public static final int STATE_EMPTY_ZOOMED_OUT_TOP_ROW_SELECTED = 26;
    private static final int STATE_INVALID = -1;
    public static final int STATE_MOVE_NOT_SELECTED = 14;
    public static final int STATE_MOVE_SELECTED = 13;
    public static final int STATE_ZOOMED_OUT_NOT_SELECTED = 9;
    public static final int STATE_ZOOMED_OUT_SELECTED = 8;
    public static final int STATE_ZOOMED_OUT_TOP_ROW_SELECTED = 10;
    private static final String TAG = "ChannelView";
    private static final int WATCH_NEXT_INFO_ACKNOWLEDGED_BUTTON_VISIBILITY_DELAY_MS = 100;
    private static final int WATCH_NEXT_INFO_CARD_ADAPTER_POSITION = 1;
    private Drawable mActionMoveDownIcon;
    private Drawable mActionMoveUpDownIcon;
    private Drawable mActionMoveUpIcon;
    private View mActionsHint;
    private int mActionsHintVisibility;
    private boolean mAllowMoving = true;
    private boolean mAllowRemoving = true;
    private boolean mAllowZoomOut = true;
    private Runnable mAnimationCheckForWatchNextInfoButtonVisibilityRunnable = new Runnable() {
        public void run() {
            if (ChannelView.this.mItemsList.isAnimating()) {
                ChannelView.this.mItemsList.getItemAnimator().isRunning(new ChannelView$2$$Lambda$0(this));
            } else if (!ChannelView.this.mIsFastScrolling) {
                ChannelView.this.refreshWatchNextInfoButtonVisibility();
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$0$ChannelView$2() {
            ChannelView.this.bridge$lambda$0$ChannelView();
        }
    };
    private View mChannelActionsPaddingView;
    private ImageView mChannelLogo;
    private float mChannelLogoCurrentDimmingFactor;
    private float mChannelLogoDimmedFactorValue;
    /* access modifiers changed from: private */
    public float mChannelLogoFocusedScale;
    private float mChannelLogoSelectedElevation;
    @ColorInt
    private int mChannelLogoTitleColor;
    private int mChannelLogoTitleDefaultMarginStart;
    @ColorInt
    private int mChannelLogoTitleDimmedColor;
    private int mChannelLogoTitleZoomedOutMarginStart;
    private int mChannelLogoZoomedOutMargin;
    private int mChannelLogoZoomedOutSelectedMargin;
    private ChannelViewMainContent mChannelViewMainContent;
    private TextView mEmptyChannelMessage;
    private int mEmptyChannelMessageActionNotSelectedMarginStart;
    private int mEmptyChannelMessageDefaultMarginStart;
    private int mEmptyChannelMessageMoveNotSelectedMarginStart;
    private int mEmptyChannelMessageVisibility;
    private int mEmptyChannelMessageZoomedOutMarginStart;
    private boolean mHoldingDpadLeftRight;
    private boolean mIsBranded = true;
    /* access modifiers changed from: private */
    public boolean mIsFastScrolling;
    private boolean mIsRtl = false;
    private boolean mIsSponsored;
    private View mItemMetaContainer;
    private int mItemMetaContainerDefaultMarginStart;
    private int mItemMetaContainerDefaultMarginTop;
    private int mItemMetaContainerInvisibleMarginBottom;
    private int mItemMetaContainerSelectedMarginTop;
    private int mItemMetaContainerVisibility;
    private int mItemMetaContainerZoomedOutMarginStart;
    /* access modifiers changed from: private */
    public HorizontalGridView mItemsList;
    private FadingEdgeContainer mItemsListContainer;
    private int mItemsListMarginStart;
    private int mItemsListZoomedOutMarginStart;
    private TextView mItemsTitle;
    @ColorInt
    private int mItemsTitleDefaultColor;
    private int mItemsTitleDefaultMarginStart;
    @ColorInt
    private int mItemsTitleSelectedColor;
    @ColorInt
    private int mItemsTitleStateColor;
    private int mItemsTitleZoomedOutMarginStart;
    private TextView mLogoTitle;
    @ColorInt
    private int mLogoTitleStateColor;
    private int mLogoTitleVisibility;
    private ChannelViewMainLinearLayout mMainLinearLayout;
    private ImageView mMoveButton;
    private View mMoveChannelPaddingView;
    private View mMovingChannelBackground;
    private int mMovingChannelBackgroundVisibility;
    private View mNoMoveActionPaddingView;
    /* access modifiers changed from: private */
    public OnChannelLogoFocusedListener mOnChannelLogoFocusedListener;
    private ViewTreeObserver.OnGlobalFocusChangeListener mOnGlobalFocusChangeListener;
    private OnMoveChannelDownListener mOnMoveChannelDownListener;
    private OnMoveChannelUpListener mOnMoveChannelUpListener;
    private OnPerformMainActionListener mOnPerformMainActionListener;
    private OnRemoveListener mOnRemoveListener;
    private OnStateChangeGesturePerformedListener mOnStateChangeGesturePerformedListener;
    private ImageView mRemoveButton;
    private boolean mShowItemMeta = true;
    @VisibleForTesting
    boolean mShowItemsTitle = true;
    @ColorInt
    private int mSponsoredBackgroundDefaultColor;
    @ColorInt
    private int mSponsoredBackgroundZoomedOutSelectedColor;
    private View mSponsoredChannelBackground;
    private int mSponsoredChannelBackgroundAboveSelectedLastRowHeight;
    private int mSponsoredChannelBackgroundDefaultHeight;
    private int mSponsoredChannelBackgroundDefaultSelectedHeight;
    private int mSponsoredChannelBackgroundVisibility;
    private int mSponsoredChannelBackgroundZoomedOutHeight;
    private int mState = 1;
    private SparseArray<ChannelStateSettings> mStateSettings;
    private int mUnbrandedChannelBackgroundBelowSelectedHeight;
    private TextView mWatchNextInfoAcknowledgedButton;
    /* access modifiers changed from: private */
    public ObjectAnimator mWatchNextInfoAcknowledgedButtonBlinkAnim;
    private AnimatorListenerAdapter mWatchNextInfoAcknowledgedButtonFadeInAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        public void onAnimationEnd(Animator animation) {
            animation.removeListener(this);
            ChannelView.this.mWatchNextInfoAcknowledgedButtonBlinkAnim.start();
        }
    };
    private int mWatchNextInfoAcknowledgedButtonFadeInDuration;
    private Animator mWatchNextInfoAcknowledgedButtonFadeInTransition;
    private boolean mWatchNextInfoAcknowledgedButtonVisible;
    private int mWatchNextInfoButtonBaseMarginStart;

    /* renamed from: mWatchNextInfoButtonVisibilityRefreshDueToDataDirtyAttemptAvailable */
    private boolean f142x324a9749 = true;
    private int mWatchNextInfoContentOffset;
    private TextView mZoomedOutLogoTitle;
    @ColorInt
    private int mZoomedOutLogoTitleStateColor;
    private int mZoomedOutLogoTitleVisibility;
    private View mZoomedOutPaddingView;

    public interface OnChannelLogoFocusedListener {
        void onChannelLogoFocused();
    }

    public interface OnMoveChannelDownListener {
        void onMoveChannelDown(ChannelView channelView);
    }

    public interface OnMoveChannelUpListener {
        void onMoveChannelUp(ChannelView channelView);
    }

    public interface OnPerformMainActionListener {
        void onPerformMainAction(ChannelView channelView);
    }

    public interface OnRemoveListener {
        void onRemove(ChannelView channelView);
    }

    public interface OnStateChangeGesturePerformedListener {
        void onStateChangeGesturePerformed(ChannelView channelView, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static String stateToString(int state) {
        String stateString;
        switch (state) {
            case 0:
                stateString = "STATE_DEFAULT_SELECTED";
                break;
            case 1:
                stateString = "STATE_DEFAULT_NOT_SELECTED";
                break;
            case 2:
                stateString = "STATE_DEFAULT_ABOVE_SELECTED";
                break;
            case 3:
                stateString = "STATE_DEFAULT_BELOW_SELECTED";
                break;
            case 4:
                stateString = "STATE_DEFAULT_TOP_ROW_SELECTED";
                break;
            case 5:
                stateString = "STATE_DEFAULT_APPS_ROW_SELECTED";
                break;
            case 6:
                stateString = "STATE_DEFAULT_FAST_SCROLLING_SELECTED";
                break;
            case 7:
                stateString = "STATE_DEFAULT_FAST_SCROLLING_NOT_SELECTED";
                break;
            case 8:
                stateString = "STATE_ZOOMED_OUT_SELECTED";
                break;
            case 9:
                stateString = "STATE_ZOOMED_OUT_NOT_SELECTED";
                break;
            case 10:
                stateString = "STATE_ZOOMED_OUT_TOP_ROW_SELECTED";
                break;
            case 11:
                stateString = "STATE_ACTIONS_SELECTED";
                break;
            case 12:
                stateString = "STATE_ACTIONS_NOT_SELECTED";
                break;
            case 13:
                stateString = "STATE_MOVE_SELECTED";
                break;
            case 14:
                stateString = "STATE_MOVE_NOT_SELECTED";
                break;
            case 15:
                stateString = "STATE_DEFAULT_ABOVE_SELECTED_LAST_ROW";
                break;
            case 16:
                stateString = "STATE_EMPTY_DEFAULT_SELECTED";
                break;
            case 17:
                stateString = "STATE_EMPTY_DEFAULT_NOT_SELECTED";
                break;
            case 18:
                stateString = "STATE_EMPTY_DEFAULT_ABOVE_SELECTED";
                break;
            case 19:
                stateString = "STATE_EMPTY_DEFAULT_BELOW_SELECTED";
                break;
            case 20:
                stateString = "STATE_EMPTY_DEFAULT_TOP_ROW_SELECTED";
                break;
            case 21:
                stateString = "STATE_EMPTY_DEFAULT_APPS_ROW_SELECTED";
                break;
            case 22:
                stateString = "STATE_EMPTY_DEFAULT_FAST_SCROLLING_SELECTED";
                break;
            case 23:
                stateString = "STATE_EMPTY_DEFAULT_FAST_SCROLLING_NOT_SELECTED";
                break;
            case 24:
                stateString = "STATE_EMPTY_ZOOMED_OUT_SELECTED";
                break;
            case 25:
                stateString = "STATE_EMPTY_ZOOMED_OUT_NOT_SELECTED";
                break;
            case 26:
                stateString = "STATE_EMPTY_ZOOMED_OUT_TOP_ROW_SELECTED";
                break;
            case 27:
                stateString = "STATE_EMPTY_ACTIONS_NOT_SELECTED";
                break;
            case 28:
                stateString = "STATE_EMPTY_MOVE_NOT_SELECTED";
                break;
            case 29:
                stateString = "STATE_EMPTY_DEFAULT_ABOVE_SELECTED_LAST_ROW";
                break;
            default:
                stateString = "STATE_UNKNOWN";
                break;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(stateString).length() + 14);
        sb.append(stateString);
        sb.append(" (");
        sb.append(state);
        sb.append(")");
        return sb.toString();
    }

    public static String directionToString(int direction) {
        String directionString;
        if (direction == 17) {
            directionString = "FOCUS_LEFT";
        } else if (direction == 33) {
            directionString = "FOCUS_UP";
        } else if (direction == 66) {
            directionString = "FOCUS_RIGHT";
        } else if (direction != 130) {
            directionString = "FOCUS_UNKNOWN";
        } else {
            directionString = "FOCUS_DOWN";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(directionString).length() + 14);
        sb.append(directionString);
        sb.append(" (");
        sb.append(direction);
        sb.append(")");
        return sb.toString();
    }

    static boolean isZoomedOutState(int state) {
        switch (state) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return true;
            default:
                switch (state) {
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        return true;
                    default:
                        return false;
                }
        }
    }

    private static boolean isDefaultFastScrolling(int state) {
        return state == 6 || state == 7;
    }

    public ChannelView(Context context) {
        super(context);
    }

    public ChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mIsRtl = Util.isRtl(getContext());
        Resources r = getResources();
        this.mChannelLogoTitleDefaultMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_margin_start);
        this.mChannelLogoTitleColor = getContext().getColor(C1188R.color.channel_logo_title_color);
        this.mChannelLogoTitleDimmedColor = getContext().getColor(C1188R.color.channel_logo_title_dimmed_color);
        this.mChannelLogoTitleZoomedOutMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_logo_title_zoomed_out_margin_start);
        this.mChannelLogoZoomedOutMargin = r.getDimensionPixelOffset(C1188R.dimen.channel_logo_zoomed_out_margin);
        this.mChannelLogoZoomedOutSelectedMargin = r.getDimensionPixelOffset(C1188R.dimen.channel_logo_zoomed_out_selected_margin);
        this.mItemsListMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_items_list_margin_start);
        this.mItemsListZoomedOutMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_items_list_zoomed_out_margin_start);
        this.mItemsTitleDefaultMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_items_title_default_margin_start);
        this.mItemsTitleZoomedOutMarginStart = r.getDimensionPixelOffset(C1188R.dimen.channel_items_title_zoomed_out_margin_start);
        this.mItemsTitleDefaultColor = getContext().getColor(C1188R.color.channel_items_title_default_color);
        this.mItemsTitleSelectedColor = getContext().getColor(C1188R.color.channel_items_title_selected_color);
        this.mItemMetaContainerDefaultMarginTop = r.getDimensionPixelOffset(C1188R.dimen.program_meta_container_default_margin_top);
        this.mItemMetaContainerSelectedMarginTop = r.getDimensionPixelOffset(C1188R.dimen.program_meta_container_selected_margin_top);
        this.mItemMetaContainerInvisibleMarginBottom = r.getDimensionPixelSize(C1188R.dimen.program_meta_container_invisible_margin_bottom);
        this.mItemMetaContainerDefaultMarginStart = r.getDimensionPixelOffset(C1188R.dimen.program_meta_container_default_margin_start);
        this.mItemMetaContainerZoomedOutMarginStart = r.getDimensionPixelOffset(C1188R.dimen.program_meta_container_zoomed_out_margin_start);
        this.mSponsoredChannelBackgroundDefaultHeight = r.getDimensionPixelSize(C1188R.dimen.sponsored_channel_background_height);
        this.mSponsoredChannelBackgroundZoomedOutHeight = r.getDimensionPixelSize(C1188R.dimen.sponsored_channel_background_zoomed_out_height);
        this.mSponsoredChannelBackgroundDefaultSelectedHeight = r.getDimensionPixelSize(C1188R.dimen.sponsored_channel_background_default_selected_height);
        this.mSponsoredChannelBackgroundAboveSelectedLastRowHeight = r.getDimensionPixelSize(C1188R.dimen.sponsored_channel_background_above_selected_last_row_height);
        this.mUnbrandedChannelBackgroundBelowSelectedHeight = r.getDimensionPixelSize(C1188R.dimen.sponsored_channel_background_unbranded_below_selected_height);
        this.mSponsoredBackgroundDefaultColor = getContext().getColor(C1188R.color.sponsored_channel_background_default_color);
        this.mSponsoredBackgroundZoomedOutSelectedColor = getContext().getColor(C1188R.color.sponsored_channel_background_zoomed_out_selected_color);
        this.mEmptyChannelMessageDefaultMarginStart = r.getDimensionPixelOffset(C1188R.dimen.empty_channel_message_default_margin_start);
        this.mEmptyChannelMessageZoomedOutMarginStart = r.getDimensionPixelOffset(C1188R.dimen.empty_channel_message_zoomed_out_margin_start);
        this.mEmptyChannelMessageActionNotSelectedMarginStart = r.getDimensionPixelOffset(C1188R.dimen.empty_channel_message_action_not_selected_margin_start);
        this.mEmptyChannelMessageMoveNotSelectedMarginStart = r.getDimensionPixelOffset(C1188R.dimen.empty_channel_message_move_not_selected_margin_start);
        setOnClickListener(new ChannelView$$Lambda$0(this));
        setFocusable(false);
        this.mChannelViewMainContent = (ChannelViewMainContent) findViewById(C1188R.C1191id.main_content);
        this.mMainLinearLayout = (ChannelViewMainLinearLayout) findViewById(C1188R.C1191id.main_linear_layout);
        this.mLogoTitle = (TextView) findViewById(C1188R.C1191id.logo_title);
        this.mLogoTitleVisibility = this.mLogoTitle.getVisibility();
        this.mZoomedOutLogoTitle = (TextView) findViewById(C1188R.C1191id.logo_title_zoomed_out);
        this.mZoomedOutLogoTitleVisibility = this.mZoomedOutLogoTitle.getVisibility();
        this.mZoomedOutLogoTitleStateColor = this.mZoomedOutLogoTitle.getCurrentTextColor();
        this.mItemsTitle = (TextView) findViewById(C1188R.C1191id.items_title);
        this.mItemsTitleStateColor = this.mItemsTitle.getCurrentTextColor();
        this.mActionsHint = findViewById(C1188R.C1191id.actions_hint);
        this.mActionsHintVisibility = this.mActionsHint.getVisibility();
        this.mZoomedOutPaddingView = findViewById(C1188R.C1191id.zoomed_out_padding);
        this.mChannelActionsPaddingView = findViewById(C1188R.C1191id.channel_actions_padding);
        this.mMoveChannelPaddingView = findViewById(C1188R.C1191id.move_channel_padding);
        this.mNoMoveActionPaddingView = findViewById(C1188R.C1191id.no_move_action_padding);
        this.mSponsoredChannelBackground = findViewById(C1188R.C1191id.sponsored_channel_background);
        this.mMovingChannelBackground = findViewById(C1188R.C1191id.moving_channel_background);
        final int movingChannelBackgroundCornerRadius = getResources().getDimensionPixelSize(C1188R.dimen.moving_channel_background_corner_radius);
        this.mMovingChannelBackground.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth() + movingChannelBackgroundCornerRadius, view.getHeight(), (float) movingChannelBackgroundCornerRadius);
            }
        });
        this.mMovingChannelBackground.setClipToOutline(true);
        this.mMovingChannelBackgroundVisibility = this.mMovingChannelBackground.getVisibility();
        this.mChannelLogo = (ImageView) findViewById(C1188R.C1191id.channel_logo);
        this.mEmptyChannelMessage = (TextView) findViewById(C1188R.C1191id.channel_empty_message);
        this.mEmptyChannelMessageVisibility = this.mEmptyChannelMessage.getVisibility();
        this.mChannelLogo.setSoundEffectsEnabled(false);
        this.mChannelLogo.setOnClickListener(new ChannelView$$Lambda$1(this));
        this.mChannelLogo.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        this.mChannelLogo.setClipToOutline(true);
        this.mChannelLogoFocusedScale = r.getFraction(C1188R.fraction.channel_logo_focused_scale, 1, 1);
        this.mChannelLogoSelectedElevation = r.getDimension(C1188R.dimen.channel_logo_focused_elevation);
        if (isInEditMode() || Util.areHomeScreenAnimationsEnabled(getContext())) {
            this.mChannelLogo.setOnFocusChangeListener(new ChannelView$$Lambda$2(this));
        } else {
            ((ViewGroup.MarginLayoutParams) this.mActionsHint.getLayoutParams()).setMarginEnd(r.getDimensionPixelOffset(C1188R.dimen.channel_actions_hint_margin_end_no_animations));
            new ScaleFocusHandler(r.getInteger(C1188R.integer.channel_logo_focused_animation_duration_ms), this.mChannelLogoFocusedScale, this.mChannelLogoSelectedElevation) {
                public void onFocusChange(View v, boolean hasFocus) {
                    float f;
                    if (Util.isAccessibilityEnabled(ChannelView.this.getContext())) {
                        f = 1.0f;
                    } else {
                        f = ChannelView.this.mChannelLogoFocusedScale;
                    }
                    setFocusedScale(f);
                    if (hasFocus && Util.isAccessibilityEnabled(ChannelView.this.getContext()) && ChannelView.this.mOnChannelLogoFocusedListener != null) {
                        ChannelView.this.mOnChannelLogoFocusedListener.onChannelLogoFocused();
                    }
                    super.onFocusChange(v, hasFocus);
                }
            }.setView(this.mChannelLogo);
        }
        this.mChannelLogoDimmedFactorValue = Util.getFloat(getResources(), C1188R.dimen.unfocused_channel_dimming_factor);
        this.mChannelLogoCurrentDimmingFactor = this.mChannelLogoDimmedFactorValue;
        this.mChannelLogo.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, this.mChannelLogoCurrentDimmingFactor));
        this.mLogoTitleStateColor = this.mLogoTitle.getCurrentTextColor();
        this.mRemoveButton = (ImageView) findViewById(C1188R.C1191id.remove);
        this.mRemoveButton.setOnClickListener(new ChannelView$$Lambda$3(this));
        translateNextFocusForRtl(this.mRemoveButton);
        this.mMoveButton = (ImageView) findViewById(C1188R.C1191id.move);
        this.mMoveButton.setOnClickListener(new ChannelView$$Lambda$4(this));
        this.mItemsList = (HorizontalGridView) findViewById(C1188R.C1191id.items_list);
        this.mItemsListContainer = (FadingEdgeContainer) findViewById(C1188R.C1191id.items_list_container);
        this.mItemsListContainer.setFadeEnabled(false);
        this.mItemMetaContainer = findViewById(C1188R.C1191id.item_meta_container);
        this.mItemMetaContainerVisibility = this.mItemMetaContainer.getVisibility();
        this.mWatchNextInfoButtonBaseMarginStart = r.getDimensionPixelOffset(C1188R.dimen.watch_next_info_acknowledged_button_base_margin_start);
        this.mWatchNextInfoContentOffset = getResources().getDimensionPixelSize(C1188R.dimen.watch_next_info_card_container_default_margin_horizontal);
        this.mWatchNextInfoAcknowledgedButton = (TextView) findViewById(C1188R.C1191id.watch_next_info_acknowledged_button);
        final int watchNextAcknowledgedButtonCornerRadius = getResources().getDimensionPixelSize(C1188R.dimen.watch_next_info_acknowledged_button_corner_radius);
        this.mWatchNextInfoAcknowledgedButton.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) watchNextAcknowledgedButtonCornerRadius);
            }
        });
        this.mWatchNextInfoAcknowledgedButton.setClipToOutline(true);
        this.mOnGlobalFocusChangeListener = new ChannelView$$Lambda$5(this);
        this.mActionMoveUpDownIcon = getContext().getDrawable(C1188R.C1189drawable.ic_action_move_up_down_black);
        this.mActionMoveUpIcon = getContext().getDrawable(C1188R.C1189drawable.ic_action_move_up_black);
        this.mActionMoveDownIcon = getContext().getDrawable(C1188R.C1189drawable.ic_action_move_down_black);
        this.mWatchNextInfoAcknowledgedButtonBlinkAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.watch_next_info_acknowledged_button_blink);
        this.mWatchNextInfoAcknowledgedButtonBlinkAnim.setTarget(this.mWatchNextInfoAcknowledgedButton);
        this.mWatchNextInfoAcknowledgedButtonFadeInDuration = getResources().getInteger(C1188R.integer.watch_next_info_acknowledged_button_fade_in_duration_ms);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$0$ChannelView(View v) {
        OnStateChangeGesturePerformedListener onStateChangeGesturePerformedListener;
        if (this.mState == 13 && (onStateChangeGesturePerformedListener = this.mOnStateChangeGesturePerformedListener) != null) {
            onStateChangeGesturePerformedListener.onStateChangeGesturePerformed(this, 8);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$1$ChannelView(View v) {
        OnPerformMainActionListener onPerformMainActionListener = this.mOnPerformMainActionListener;
        if (onPerformMainActionListener != null) {
            onPerformMainActionListener.onPerformMainAction(this);
            return;
        }
        AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
        if (audioManager != null) {
            audioManager.playSoundEffect(9);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$2$ChannelView(View v, boolean hasFocus) {
        OnChannelLogoFocusedListener onChannelLogoFocusedListener;
        if (hasFocus && Util.isAccessibilityEnabled(getContext()) && (onChannelLogoFocusedListener = this.mOnChannelLogoFocusedListener) != null) {
            onChannelLogoFocusedListener.onChannelLogoFocused();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$3$ChannelView(View v) {
        OnRemoveListener onRemoveListener = this.mOnRemoveListener;
        if (onRemoveListener != null) {
            onRemoveListener.onRemove(this);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$4$ChannelView(View v) {
        OnStateChangeGesturePerformedListener onStateChangeGesturePerformedListener = this.mOnStateChangeGesturePerformedListener;
        if (onStateChangeGesturePerformedListener != null) {
            onStateChangeGesturePerformedListener.onStateChangeGesturePerformed(this, 13);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$5$ChannelView(View oldFocus, View newFocus) {
        boolean oldFocusedIsChild = isFocusableChild(oldFocus);
        boolean newFocusedIsChild = isFocusableChild(newFocus);
        if (newFocusedIsChild != oldFocusedIsChild) {
            onChannelSelected(newFocusedIsChild);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.mOnGlobalFocusChangeListener);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.mOnGlobalFocusChangeListener);
    }

    private int translateFocusDirectionForRtl(int direction) {
        if (!this.mIsRtl) {
            return direction;
        }
        if (direction == 17) {
            return 66;
        }
        if (direction == 66) {
            return 17;
        }
        return direction;
    }

    private void translateNextFocusForRtl(View view) {
        if (this.mIsRtl) {
            int temp = view.getNextFocusLeftId();
            view.setNextFocusLeftId(view.getNextFocusRightId());
            view.setNextFocusRightId(temp);
        }
    }

    private boolean isFocusableChild(View v) {
        if (v == null) {
            return false;
        }
        ViewParent parent = v.getParent();
        if (v == this.mEmptyChannelMessage || parent == this.mItemsList || parent == this.mChannelLogo.getParent() || parent == this) {
            return true;
        }
        return false;
    }

    private void onChannelSelected(boolean selected) {
        OnStateChangeGesturePerformedListener onStateChangeGesturePerformedListener;
        Integer newState = null;
        if (selected) {
            int i = this.mState;
            if (!(i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
                if (i == 7) {
                    newState = 6;
                } else if (i != 15) {
                    if (i != 23) {
                        if (i != 29) {
                            if (i == 9 || i == 10) {
                                newState = 8;
                            } else if (i == 25 || i == 26) {
                                newState = 24;
                            } else {
                                switch (i) {
                                }
                            }
                        }
                        newState = 16;
                    } else {
                        newState = 22;
                    }
                }
            }
            newState = 0;
        } else {
            int i2 = this.mState;
            if (i2 == 0) {
                newState = 1;
            } else if (i2 == 6) {
                newState = 7;
            } else if (i2 == 8) {
                newState = 9;
            } else if (i2 == 16) {
                newState = 17;
            } else if (i2 == 22) {
                newState = 23;
            } else if (i2 == 24) {
                newState = 25;
            }
        }
        if (newState != null && (onStateChangeGesturePerformedListener = this.mOnStateChangeGesturePerformedListener) != null) {
            onStateChangeGesturePerformedListener.onStateChangeGesturePerformed(this, newState.intValue());
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int i = this.mState;
        if (i == 9 || i == 10 || i == 8 || i == 25 || i == 26 || i == 24) {
            this.mChannelLogo.requestFocus();
            return true;
        } else if (ChannelUtil.isEmptyState(i)) {
            this.mEmptyChannelMessage.requestFocus();
            return true;
        } else {
            this.mItemsList.requestFocus();
            return true;
        }
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (this.mState != 0 || !Util.isAccessibilityEnabled(getContext()) || translateFocusDirectionForRtl(direction) != 17 || !isFirstItem(findFocus())) {
            int i = this.mState;
            if (i == 9 || i == 10 || i == 25 || i == 26) {
                this.mChannelLogo.addFocusables(views, direction, focusableMode);
            } else if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 15 || i == 6 || i == 7) {
                this.mItemsList.addFocusables(views, direction, focusableMode);
            } else if (i == 16 || i == 17 || i == 18 || i == 19 || i == 20 || i == 21 || i == 29 || i == 22 || i == 23) {
                this.mEmptyChannelMessage.addFocusables(views, direction, focusableMode);
            } else {
                super.addFocusables(views, direction, focusableMode);
            }
        } else {
            this.mChannelLogo.addFocusables(views, direction, focusableMode);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean result = super.dispatchKeyEvent(event);
        if (event.getKeyCode() == 21 || event.getKeyCode() == 22) {
            if (event.getAction() == 0 && event.getRepeatCount() >= 1) {
                this.mHoldingDpadLeftRight = true;
            } else if (this.mHoldingDpadLeftRight && event.getAction() == 1) {
                this.mHoldingDpadLeftRight = false;
            }
        }
        return result;
    }

    public View focusSearch(int direction) {
        OnMoveChannelDownListener onMoveChannelDownListener;
        if (direction == 33) {
            OnMoveChannelUpListener onMoveChannelUpListener = this.mOnMoveChannelUpListener;
            if (onMoveChannelUpListener != null) {
                onMoveChannelUpListener.onMoveChannelUp(this);
            }
        } else if (direction == 130 && (onMoveChannelDownListener = this.mOnMoveChannelDownListener) != null) {
            onMoveChannelDownListener.onMoveChannelDown(this);
        }
        return this;
    }

    public View focusSearch(View focused, int direction) {
        OnStateChangeGesturePerformedListener onStateChangeGesturePerformedListener;
        int originalDirection = direction;
        int direction2 = translateFocusDirectionForRtl(direction);
        if (this.mHoldingDpadLeftRight) {
            return focused;
        }
        boolean blockFocusSearch = true;
        Integer newState = null;
        if (focused == this.mEmptyChannelMessage) {
            if (direction2 == 17 || direction2 == 66) {
                return focused;
            }
        } else if (!this.mAllowZoomOut || (focused != this.mItemsList && !isFirstItem(focused))) {
            if (focused == this.mChannelLogo) {
                if (direction2 != 17) {
                    if (direction2 == 66) {
                        newState = 0;
                        blockFocusSearch = false;
                    }
                } else if (this.mState != 8 || (!this.mAllowMoving && !this.mAllowRemoving)) {
                    return focused;
                } else {
                    newState = 11;
                }
            } else if ((focused == this.mMoveButton || (focused == this.mRemoveButton && !this.mAllowMoving)) && direction2 == 66) {
                newState = 8;
            }
        } else if (direction2 == 17) {
            newState = 8;
            blockFocusSearch = false;
        }
        if (!(newState == null || (onStateChangeGesturePerformedListener = this.mOnStateChangeGesturePerformedListener) == null)) {
            onStateChangeGesturePerformedListener.onStateChangeGesturePerformed(this, newState.intValue());
            if (blockFocusSearch) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction2));
                return focused;
            }
        }
        return super.focusSearch(focused, originalDirection);
    }

    private boolean isFirstItem(View itemView) {
        HorizontalGridView horizontalGridView;
        if (itemView != null && itemView.getParent() == (horizontalGridView = this.mItemsList) && horizontalGridView.getChildAdapterPosition(itemView) == 0) {
            return true;
        }
        return false;
    }

    private void updateUi(int oldState, int newState) {
        int i;
        int newZoomedOutLogoTitleStateColor;
        int i2;
        int i3;
        int i4;
        int i5 = newState;
        if (i5 == oldState) {
            return;
        }
        if (!isDefaultFastScrolling(oldState) || !isDefaultFastScrolling(newState)) {
            switch (i5) {
                case 0:
                case 16:
                    setLogoTitleVisibility(0);
                    setZoomedOutLogoTitleVisibility(4);
                    if (this.mShowItemsTitle) {
                        this.mItemsTitle.setVisibility(i5 == 16 ? 4 : 0);
                    } else {
                        this.mItemsTitle.setVisibility(8);
                    }
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    if (this.mShowItemMeta) {
                        setItemMetaContainerVisibility(i5 == 16 ? 4 : 0);
                    } else {
                        setItemMetaContainerVisibility(8);
                    }
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(true);
                    setFocusable(false);
                    if (!Util.isAccessibilityEnabled(getContext())) {
                        if (i5 != 16) {
                            this.mItemsList.requestFocus();
                            break;
                        } else {
                            this.mEmptyChannelMessage.requestFocus();
                            break;
                        }
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    boolean showUnbrandedTitle = this.mIsSponsored && !this.mIsBranded;
                    if (i5 == 1 || i5 == 21 || i5 == 5 || i5 == 20 || i5 == 4 || ((i5 == 3 || i5 == 19) && showUnbrandedTitle)) {
                        i4 = 0;
                    } else {
                        i4 = 4;
                    }
                    setLogoTitleVisibility(i4);
                    setZoomedOutLogoTitleVisibility(4);
                    if (this.mShowItemsTitle) {
                        this.mItemsTitle.setVisibility(i5 == 3 ? 0 : 4);
                    } else {
                        this.mItemsTitle.setVisibility(8);
                    }
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    break;
                case 8:
                case 24:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    if (i5 == 24) {
                        setActionsHintVisibility(4);
                    } else {
                        setActionsHintVisibility((this.mAllowMoving || this.mAllowRemoving) ? 0 : 4);
                    }
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(0);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    this.mChannelLogo.requestFocus();
                    break;
                case 9:
                case 10:
                case 25:
                case 26:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(0);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    break;
                case 11:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(0);
                    this.mMoveButton.setVisibility(this.mAllowMoving ? 0 : 8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(this.mAllowMoving ? 8 : 0);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    if (!this.mAllowMoving) {
                        this.mRemoveButton.requestFocus();
                        break;
                    } else {
                        this.mMoveButton.requestFocus();
                        break;
                    }
                case 12:
                case 27:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(0);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    break;
                case 13:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(4);
                    this.mMoveButton.setVisibility(0);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(0);
                    setSponsoredChannelBackgroundVisibility(8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(true);
                    requestFocus();
                    break;
                case 14:
                case 28:
                    setLogoTitleVisibility(4);
                    setZoomedOutLogoTitleVisibility(0);
                    this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 4 : 8);
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(0);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(4);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(false);
                    setFocusable(false);
                    break;
                case 15:
                case 29:
                    setLogoTitleVisibility(0);
                    setZoomedOutLogoTitleVisibility(4);
                    if (this.mState == 29) {
                        this.mItemsTitle.setVisibility(8);
                    } else {
                        this.mItemsTitle.setVisibility(this.mShowItemsTitle ? 0 : 8);
                    }
                    setActionsHintVisibility(4);
                    this.mRemoveButton.setVisibility(8);
                    this.mMoveButton.setVisibility(8);
                    this.mZoomedOutPaddingView.setVisibility(8);
                    this.mChannelActionsPaddingView.setVisibility(8);
                    this.mMoveChannelPaddingView.setVisibility(8);
                    this.mNoMoveActionPaddingView.setVisibility(8);
                    setItemMetaContainerVisibility(this.mShowItemMeta ? 4 : 8);
                    setMovingChannelBackgroundVisibility(8);
                    setSponsoredChannelBackgroundVisibility(this.mIsSponsored ? 0 : 8);
                    this.mItemsListContainer.setFadeEnabled(true);
                    setFocusable(false);
                    break;
            }
            if (i5 != 0) {
                setWatchNextInfoAcknowledgedButtonVisible(false);
            }
            if (ChannelUtil.isEmptyState(newState)) {
                setEmptyChannelMessageVisibility(0);
                this.mItemsList.setFocusable(false);
            } else {
                setEmptyChannelMessageVisibility(4);
                this.mItemsList.setFocusable(true);
            }
            boolean isZoomedOut = isZoomedOutState(newState);
            this.mMainLinearLayout.setZoomedOutState(isZoomedOut);
            ViewGroup.MarginLayoutParams logoTitleLayoutParams = (ViewGroup.MarginLayoutParams) this.mLogoTitle.getLayoutParams();
            ViewGroup.MarginLayoutParams itemsTitleLayoutParams = (ViewGroup.MarginLayoutParams) this.mItemsTitle.getLayoutParams();
            SparseArray<ChannelStateSettings> sparseArray = this.mStateSettings;
            if (sparseArray != null) {
                ChannelStateSettings settings = sparseArray.get(i5);
                if (this.mIsSponsored) {
                    int keylineOffset = settings.getChannelLogoKeylineOffset();
                    this.mMainLinearLayout.setChannelLogoKeylineOffset(keylineOffset);
                    this.mChannelViewMainContent.setChannelLogoKeylineOffset(keylineOffset);
                }
                ViewGroup.MarginLayoutParams channelLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                channelLayoutParams.setMargins(0, settings.getMarginTop(), 0, settings.getMarginBottom());
                setLayoutParams(channelLayoutParams);
                ViewGroup.MarginLayoutParams itemsListContainerLayoutParams = (ViewGroup.MarginLayoutParams) this.mItemsListContainer.getLayoutParams();
                itemsListContainerLayoutParams.setMarginStart(isZoomedOut ? this.mItemsListZoomedOutMarginStart : this.mItemsListMarginStart);
                this.mItemsListContainer.setLayoutParams(itemsListContainerLayoutParams);
                ViewGroup.MarginLayoutParams itemListLayoutParams = (ViewGroup.MarginLayoutParams) this.mItemsList.getLayoutParams();
                itemListLayoutParams.height = settings.getItemHeight() + settings.getItemMarginTop() + settings.getItemMarginBottom();
                this.mItemsList.setLayoutParams(itemListLayoutParams);
                LinearLayout.LayoutParams channelLogoLayoutParams = (LinearLayout.LayoutParams) this.mChannelLogo.getLayoutParams();
                int alignmentOriginMargin = settings.getChannelLogoAlignmentOriginMargin();
                if (!Util.areHomeScreenAnimationsEnabled(getContext()) && i5 == 8) {
                    settings = this.mStateSettings.get(9);
                }
                channelLogoLayoutParams.height = settings.getChannelLogoHeight();
                channelLogoLayoutParams.width = settings.getChannelLogoWidth();
                channelLogoLayoutParams.setMarginStart(settings.getChannelLogoMarginStart());
                channelLogoLayoutParams.setMarginEnd(settings.getChannelLogoMarginEnd());
                FrameLayout.LayoutParams emptyChannelMessageLayoutParams = (FrameLayout.LayoutParams) this.mEmptyChannelMessage.getLayoutParams();
                if (isZoomedOut) {
                    int i6 = this.mState;
                    if (i6 == 27) {
                        emptyChannelMessageLayoutParams.setMarginStart(this.mEmptyChannelMessageActionNotSelectedMarginStart);
                    } else if (i6 == 28) {
                        emptyChannelMessageLayoutParams.setMarginStart(this.mEmptyChannelMessageMoveNotSelectedMarginStart);
                    } else {
                        emptyChannelMessageLayoutParams.setMarginStart(this.mEmptyChannelMessageZoomedOutMarginStart);
                    }
                } else {
                    emptyChannelMessageLayoutParams.setMarginStart(this.mEmptyChannelMessageDefaultMarginStart);
                }
                emptyChannelMessageLayoutParams.topMargin = settings.getEmptyChannelMessageMarginTop();
                this.mEmptyChannelMessage.setLayoutParams(emptyChannelMessageLayoutParams);
                if (this.mIsSponsored) {
                    if (i5 != 3 && i5 != 19) {
                        i3 = 0;
                    } else if (!this.mIsBranded) {
                        channelLogoLayoutParams.gravity = 80;
                        channelLogoLayoutParams.bottomMargin = alignmentOriginMargin;
                        channelLogoLayoutParams.topMargin = 0;
                    } else {
                        i3 = 0;
                    }
                    channelLogoLayoutParams.gravity = 48;
                    channelLogoLayoutParams.topMargin = alignmentOriginMargin;
                    channelLogoLayoutParams.bottomMargin = i3;
                } else if ((Util.areHomeScreenAnimationsEnabled(getContext()) && i5 == 8) || i5 == 24) {
                    channelLogoLayoutParams.gravity = 48;
                    int i7 = this.mChannelLogoZoomedOutSelectedMargin;
                    channelLogoLayoutParams.topMargin = alignmentOriginMargin + i7;
                    channelLogoLayoutParams.bottomMargin = i7;
                } else if (isZoomedOut) {
                    channelLogoLayoutParams.gravity = 48;
                    int i8 = this.mChannelLogoZoomedOutMargin;
                    channelLogoLayoutParams.topMargin = alignmentOriginMargin + i8;
                    channelLogoLayoutParams.bottomMargin = i8;
                } else {
                    if (i5 == 2) {
                        i2 = 0;
                    } else if (i5 == 18) {
                        i2 = 0;
                    } else {
                        channelLogoLayoutParams.gravity = 48;
                        channelLogoLayoutParams.topMargin = alignmentOriginMargin;
                        channelLogoLayoutParams.bottomMargin = 0;
                    }
                    channelLogoLayoutParams.gravity = 80;
                    channelLogoLayoutParams.topMargin = i2;
                    channelLogoLayoutParams.bottomMargin = alignmentOriginMargin;
                }
                this.mChannelLogo.setLayoutParams(channelLogoLayoutParams);
                logoTitleLayoutParams.bottomMargin = settings.getChannelLogoTitleMarginBottom();
                itemsTitleLayoutParams.topMargin = settings.getChannelItemsTitleMarginTop();
                itemsTitleLayoutParams.bottomMargin = settings.getChannelItemsTitleMarginBottom();
            }
            if (this.mIsSponsored) {
                updateSponsoredChannelBackgroundUi(i5, isZoomedOut);
            }
            if (isZoomedOut) {
                i = this.mChannelLogoTitleZoomedOutMarginStart;
            } else {
                i = this.mChannelLogoTitleDefaultMarginStart;
            }
            logoTitleLayoutParams.setMarginStart(i);
            this.mLogoTitle.setLayoutParams(logoTitleLayoutParams);
            if (Util.areHomeScreenAnimationsEnabled(getContext())) {
                this.mChannelLogo.setElevation(i5 == 8 ? this.mChannelLogoSelectedElevation : 0.0f);
            }
            setChannelLogoDimmed((i5 == 0 || i5 == 5 || i5 == 8 || i5 == 11 || i5 == 13 || i5 == 16 || i5 == 21 || i5 == 24 || i5 == 6 || i5 == 7 || i5 == 22 || i5 == 23) ? false : true);
            updateLogoTitleColor(i5);
            itemsTitleLayoutParams.setMarginStart(isZoomedOut ? this.mItemsTitleZoomedOutMarginStart : this.mItemsTitleDefaultMarginStart);
            this.mItemsTitle.setLayoutParams(itemsTitleLayoutParams);
            this.mItemsTitleStateColor = i5 == 0 ? this.mItemsTitleSelectedColor : this.mItemsTitleDefaultColor;
            this.mItemsTitle.setTextColor(this.mItemsTitleStateColor);
            if (i5 == 8 || i5 == 24 || i5 == 13) {
                newZoomedOutLogoTitleStateColor = this.mChannelLogoTitleColor;
            } else {
                newZoomedOutLogoTitleStateColor = this.mChannelLogoTitleDimmedColor;
            }
            if (newZoomedOutLogoTitleStateColor != this.mZoomedOutLogoTitleStateColor) {
                this.mZoomedOutLogoTitleStateColor = newZoomedOutLogoTitleStateColor;
                this.mZoomedOutLogoTitle.setTextColor(this.mZoomedOutLogoTitleStateColor);
            }
            ViewGroup.MarginLayoutParams metaContainerLayoutParams = (ViewGroup.MarginLayoutParams) this.mItemMetaContainer.getLayoutParams();
            if (i5 == 0 || i5 == 16 || i5 == 15 || i5 == 29) {
                metaContainerLayoutParams.topMargin = this.mItemMetaContainerSelectedMarginTop;
                metaContainerLayoutParams.bottomMargin = 0;
                metaContainerLayoutParams.setMarginStart(this.mItemMetaContainerDefaultMarginStart);
            } else if (isZoomedOut) {
                metaContainerLayoutParams.topMargin = 0;
                metaContainerLayoutParams.bottomMargin = this.mItemMetaContainerInvisibleMarginBottom;
                metaContainerLayoutParams.setMarginStart(this.mItemMetaContainerZoomedOutMarginStart);
            } else {
                metaContainerLayoutParams.topMargin = this.mItemMetaContainerDefaultMarginTop;
                metaContainerLayoutParams.bottomMargin = this.mItemMetaContainerInvisibleMarginBottom;
                metaContainerLayoutParams.setMarginStart(this.mItemMetaContainerDefaultMarginStart);
            }
            this.mItemMetaContainer.setLayoutParams(metaContainerLayoutParams);
        }
    }

    /* access modifiers changed from: package-private */
    public int getLogoTitleVisibility() {
        return this.mLogoTitleVisibility;
    }

    private void setLogoTitleVisibility(int visible) {
        if (!this.mIsSponsored || !this.mIsBranded) {
            this.mLogoTitle.setVisibility(visible);
            this.mLogoTitleVisibility = visible;
        }
    }

    /* access modifiers changed from: package-private */
    public int getZoomedOutLogoTitleVisibility() {
        return this.mZoomedOutLogoTitleVisibility;
    }

    private void setZoomedOutLogoTitleVisibility(int visible) {
        this.mZoomedOutLogoTitle.setVisibility(visible);
        this.mZoomedOutLogoTitleVisibility = visible;
    }

    /* access modifiers changed from: package-private */
    public int getActionsHintVisibility() {
        return this.mActionsHintVisibility;
    }

    private void setActionsHintVisibility(int visible) {
        this.mActionsHint.setVisibility(visible);
        this.mActionsHintVisibility = visible;
    }

    /* access modifiers changed from: package-private */
    public int getEmptyChannelMessageVisibility() {
        return this.mEmptyChannelMessageVisibility;
    }

    private void setEmptyChannelMessageVisibility(int visible) {
        this.mEmptyChannelMessage.setVisibility(visible);
        this.mEmptyChannelMessageVisibility = visible;
    }

    private void updateSponsoredChannelBackgroundUi(int newState, boolean isZoomedOut) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.mSponsoredChannelBackground.getLayoutParams();
        params.setMarginStart(0);
        if (newState == 0) {
            params.height = this.mSponsoredChannelBackgroundDefaultSelectedHeight;
        } else if ((newState == 3 || newState == 19) && !this.mIsBranded) {
            params.height = this.mUnbrandedChannelBackgroundBelowSelectedHeight;
        } else if (isZoomedOut) {
            params.height = this.mSponsoredChannelBackgroundZoomedOutHeight;
        } else if (newState == 15 || newState == 29) {
            params.height = this.mSponsoredChannelBackgroundAboveSelectedLastRowHeight;
        } else {
            params.height = this.mSponsoredChannelBackgroundDefaultHeight;
        }
        if (newState == 8) {
            this.mSponsoredChannelBackground.setBackgroundColor(this.mSponsoredBackgroundZoomedOutSelectedColor);
        } else {
            this.mSponsoredChannelBackground.setBackgroundColor(this.mSponsoredBackgroundDefaultColor);
        }
        this.mSponsoredChannelBackground.setLayoutParams(params);
    }

    /* access modifiers changed from: package-private */
    public int getSponsoredChannelBackgroundVisibility() {
        return this.mSponsoredChannelBackgroundVisibility;
    }

    private void setSponsoredChannelBackgroundVisibility(int visible) {
        this.mSponsoredChannelBackground.setVisibility(visible);
        this.mSponsoredChannelBackgroundVisibility = visible;
    }

    /* access modifiers changed from: package-private */
    public int getMovingChannelBackgroundVisibility() {
        return this.mMovingChannelBackgroundVisibility;
    }

    private void setMovingChannelBackgroundVisibility(int visible) {
        this.mMovingChannelBackground.setVisibility(visible);
        this.mMovingChannelBackgroundVisibility = visible;
    }

    private void setItemMetaContainerVisibility(int visible) {
        this.mItemMetaContainer.setVisibility(visible);
        this.mItemMetaContainerVisibility = visible;
    }

    public void setAllowMoving(boolean allowMoving) {
        this.mAllowMoving = allowMoving;
    }

    public void setAllowRemoving(boolean allowRemoving) {
        this.mAllowRemoving = allowRemoving;
    }

    public void setShowItemMeta(boolean showItemMeta) {
        this.mShowItemMeta = showItemMeta;
    }

    public void setAllowZoomOut(boolean allowZoomOut) {
        this.mAllowZoomOut = allowZoomOut;
    }

    public void setShowItemsTitle(boolean showItemsTitle) {
        this.mShowItemsTitle = showItemsTitle;
    }

    public void setStateSettings(SparseArray<ChannelStateSettings> stateSettings) {
        this.mStateSettings = stateSettings;
    }

    public void invalidateState() {
        this.mState = -1;
    }

    public void setIsSponsored(boolean isSponsored, boolean isBranded) {
        this.mIsSponsored = isSponsored;
        this.mIsBranded = isBranded;
        this.mChannelViewMainContent.setIsSponsored(isSponsored);
        this.mChannelViewMainContent.setIsBranded(isBranded);
        this.mMainLinearLayout.setIsSponsored(isSponsored);
        if (isSponsored) {
            invalidateState();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.mSponsoredChannelBackground.getLayoutParams();
            params.setMarginStart(0);
            this.mSponsoredChannelBackground.setLayoutParams(params);
            this.mChannelLogo.setOutlineProvider(null);
            if (this.mIsBranded) {
                this.mLogoTitle.setVisibility(0);
                this.mLogoTitle.setTextAppearance(C1188R.style.Channel_SponsoredLogoTitle);
                return;
            }
            this.mChannelLogoSelectedElevation = 0.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isSponsored() {
        return this.mIsSponsored;
    }

    /* access modifiers changed from: package-private */
    public boolean isBranded() {
        return this.mIsBranded;
    }

    /* access modifiers changed from: package-private */
    public View getMovingChannelBackground() {
        return this.mMovingChannelBackground;
    }

    /* access modifiers changed from: package-private */
    public View getSponsoredChannelBackground() {
        return this.mSponsoredChannelBackground;
    }

    /* access modifiers changed from: package-private */
    public TextView getLogoTitle() {
        return this.mLogoTitle;
    }

    public void setLogoTitle(String title) {
        this.mLogoTitle.setText(title);
    }

    public void setLogoContentDescription(String contentDescription) {
        this.mChannelLogo.setContentDescription(contentDescription);
    }

    private void setChannelLogoDimmed(boolean dimmed) {
        float newChannelLogoCurrentDimmingFactor;
        if (dimmed) {
            newChannelLogoCurrentDimmingFactor = this.mChannelLogoDimmedFactorValue;
        } else {
            newChannelLogoCurrentDimmingFactor = 0.0f;
        }
        if (Math.abs(newChannelLogoCurrentDimmingFactor - this.mChannelLogoCurrentDimmingFactor) > EPS) {
            this.mChannelLogoCurrentDimmingFactor = newChannelLogoCurrentDimmingFactor;
            if (newChannelLogoCurrentDimmingFactor < EPS) {
                this.mChannelLogo.setColorFilter((ColorFilter) null);
            } else {
                this.mChannelLogo.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, this.mChannelLogoCurrentDimmingFactor));
            }
        }
    }

    private void updateLogoTitleColor(int state) {
        int newLogoTitleStateColor;
        if (this.mIsSponsored) {
            if (state == 8 || state == 24 || state == 13 || state == 0 || state == 16 || state == 5 || state == 21) {
                newLogoTitleStateColor = this.mChannelLogoTitleColor;
            } else if (state == 1 || state == 2 || state == 3) {
                newLogoTitleStateColor = this.mItemsTitleDefaultColor;
            } else {
                newLogoTitleStateColor = this.mChannelLogoTitleDimmedColor;
            }
        } else if (state == 1 || state == 4 || state == 15 || state == 20 || state == 29 || state == 17) {
            newLogoTitleStateColor = this.mChannelLogoTitleDimmedColor;
        } else {
            newLogoTitleStateColor = this.mChannelLogoTitleColor;
        }
        if (newLogoTitleStateColor != this.mLogoTitleStateColor) {
            this.mLogoTitleStateColor = newLogoTitleStateColor;
            this.mLogoTitle.setTextColor(this.mLogoTitleStateColor);
        }
    }

    /* access modifiers changed from: package-private */
    public float getChannelLogoDimmingFactor() {
        return this.mChannelLogoCurrentDimmingFactor;
    }

    /* access modifiers changed from: package-private */
    @ColorInt
    public int getLogoTitleStateColor() {
        return this.mLogoTitleStateColor;
    }

    /* access modifiers changed from: package-private */
    public TextView getZoomedOutLogoTitle() {
        return this.mZoomedOutLogoTitle;
    }

    public void setZoomedOutLogoTitle(String title) {
        this.mZoomedOutLogoTitle.setText(title);
    }

    /* access modifiers changed from: package-private */
    @ColorInt
    public int getZoomedOutLogoTitleStateColor() {
        return this.mZoomedOutLogoTitleStateColor;
    }

    /* access modifiers changed from: package-private */
    @ColorInt
    public int getItemsTitleStateColor() {
        return this.mItemsTitleStateColor;
    }

    /* access modifiers changed from: package-private */
    public TextView getItemsTitle() {
        return this.mItemsTitle;
    }

    public void setItemsTitle(String title) {
        this.mItemsTitle.setText(title);
    }

    /* access modifiers changed from: package-private */
    public View getActionsHint() {
        return this.mActionsHint;
    }

    public ImageView getChannelLogoImageView() {
        return this.mChannelLogo;
    }

    public HorizontalGridView getItemsListView() {
        return this.mItemsList;
    }

    /* access modifiers changed from: package-private */
    public TextView getEmptyChannelMessage() {
        return this.mEmptyChannelMessage;
    }

    public View getItemMetadataView() {
        return this.mItemMetaContainer;
    }

    /* access modifiers changed from: package-private */
    public int getItemMetadataContainerVisibility() {
        return this.mItemMetaContainerVisibility;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        if (state != this.mState) {
            int oldState = this.mState;
            this.mState = state;
            updateUi(oldState, state);
        }
    }

    public void updateChannelMoveAction(boolean canMoveUp, boolean canMoveDown) {
        setAllowMoving(true);
        if (canMoveUp && canMoveDown) {
            this.mMoveButton.setImageDrawable(this.mActionMoveUpDownIcon);
        } else if (canMoveUp) {
            this.mMoveButton.setImageDrawable(this.mActionMoveUpIcon);
        } else if (canMoveDown) {
            this.mMoveButton.setImageDrawable(this.mActionMoveDownIcon);
        } else {
            setAllowMoving(false);
        }
    }

    public void setItemsListWindowAlignmentOffset(int offset) {
        this.mItemsList.setWindowAlignmentOffset(offset);
    }

    public void setItemsListEndPadding(int endPadding) {
        HorizontalGridView horizontalGridView = this.mItemsList;
        horizontalGridView.setPaddingRelative(horizontalGridView.getPaddingStart(), this.mItemsList.getPaddingTop(), endPadding, this.mItemsList.getPaddingBottom());
    }

    public void setOnPerformMainActionListener(OnPerformMainActionListener listener) {
        this.mOnPerformMainActionListener = listener;
        this.mChannelLogo.setSoundEffectsEnabled(listener != null);
    }

    public void setOnMoveUpListener(OnMoveChannelUpListener moveUpListener) {
        this.mOnMoveChannelUpListener = moveUpListener;
    }

    public void setOnMoveDownListener(OnMoveChannelDownListener moveDownListener) {
        this.mOnMoveChannelDownListener = moveDownListener;
    }

    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.mOnRemoveListener = onRemoveListener;
    }

    public void setOnStateChangeGesturePerformedListener(OnStateChangeGesturePerformedListener onStateChangeGesturePerformedListener) {
        this.mOnStateChangeGesturePerformedListener = onStateChangeGesturePerformedListener;
    }

    public void setOnChannelLogoFocusedListener(OnChannelLogoFocusedListener onChannelLogoFocusedListener) {
        this.mOnChannelLogoFocusedListener = onChannelLogoFocusedListener;
    }

    public void recycle() {
        this.mChannelLogo.setImageDrawable(null);
        setWatchNextInfoAcknowledgedButtonVisible(false);
    }

    public void setIsFastScrolling(boolean isFastScrolling) {
        this.mIsFastScrolling = isFastScrolling;
        if (!isFastScrolling) {
            refreshWatchNextInfoButtonVisibility();
        }
    }

    public void bindWatchNextInfoAcknowledgedButton(boolean infoCardSelected) {
        if (infoCardSelected) {
            if (this.mWatchNextInfoAcknowledgedButtonVisible) {
                setWatchNextInfoAcknowledgedButtonVisible(false);
            }
            setWatchNextInfoAcknowledgedButtonVisible(true);
            return;
        }
        setWatchNextInfoAcknowledgedButtonVisible(false);
    }

    private void setWatchNextInfoAcknowledgedButtonVisible(boolean visible) {
        this.mWatchNextInfoAcknowledgedButtonVisible = visible;
        if (!this.mWatchNextInfoAcknowledgedButtonVisible) {
            refreshWatchNextInfoButtonVisibility();
        } else if (this.mIsFastScrolling) {
        } else {
            if (this.mItemsList.isAnimating()) {
                this.mItemsList.getItemAnimator().isRunning(new ChannelView$$Lambda$6(this));
            } else {
                bridge$lambda$0$ChannelView();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: delayAnimationCheckForWatchNextInfoButtonVisibility */
    public void bridge$lambda$0$ChannelView() {
        removeCallbacks(this.mAnimationCheckForWatchNextInfoButtonVisibilityRunnable);
        postDelayed(this.mAnimationCheckForWatchNextInfoButtonVisibilityRunnable, 100);
    }

    /* access modifiers changed from: private */
    public void refreshWatchNextInfoButtonVisibility() {
        if (this.mWatchNextInfoAcknowledgedButtonVisible) {
            RecyclerView.ViewHolder infoCardViewHolder = this.mItemsList.findViewHolderForAdapterPosition(1);
            if (infoCardViewHolder != null) {
                this.f142x324a9749 = true;
                View infoCard = infoCardViewHolder.itemView;
                if (getLayoutDirection() == 0) {
                    this.mWatchNextInfoAcknowledgedButton.setTranslationX((float) (this.mWatchNextInfoButtonBaseMarginStart + infoCard.getLeft() + this.mWatchNextInfoContentOffset));
                } else {
                    this.mWatchNextInfoAcknowledgedButton.setTranslationX((float) (-(((this.mWatchNextInfoButtonBaseMarginStart + this.mItemsList.getWidth()) - infoCard.getRight()) + this.mWatchNextInfoContentOffset)));
                }
                this.mWatchNextInfoAcknowledgedButtonFadeInTransition = AnimUtil.createVisibilityAnimator(this.mWatchNextInfoAcknowledgedButton, 8, 0, 0.0f, null);
                this.mWatchNextInfoAcknowledgedButtonFadeInTransition.setDuration((long) this.mWatchNextInfoAcknowledgedButtonFadeInDuration);
                this.mWatchNextInfoAcknowledgedButtonFadeInTransition.addListener(this.mWatchNextInfoAcknowledgedButtonFadeInAnimatorListenerAdapter);
                this.mWatchNextInfoAcknowledgedButtonFadeInTransition.start();
            } else if (this.f142x324a9749) {
                Log.w(TAG, "Change watch next info button visibility to true when the horizontal grid view data is dirty. Schedule a refresh.");
                bridge$lambda$0$ChannelView();
                this.f142x324a9749 = false;
            } else {
                Log.w(TAG, "Change watch next info button visibility to true when the horizontal grid view data is dirty. Don't schedule a refresh because the only one attempt has been used.");
            }
        } else {
            this.mWatchNextInfoAcknowledgedButton.setVisibility(8);
            Animator animator = this.mWatchNextInfoAcknowledgedButtonFadeInTransition;
            if (animator != null && animator.isRunning()) {
                this.mWatchNextInfoAcknowledgedButtonFadeInTransition.cancel();
            }
            if (this.mWatchNextInfoAcknowledgedButtonBlinkAnim.isRunning()) {
                this.mWatchNextInfoAcknowledgedButtonBlinkAnim.cancel();
            }
        }
    }

    public String toString() {
        String frameLayout = super.toString();
        String valueOf = String.valueOf(this.mZoomedOutLogoTitle.getText());
        StringBuilder sb = new StringBuilder(String.valueOf(frameLayout).length() + 12 + String.valueOf(valueOf).length());
        sb.append('{');
        sb.append(frameLayout);
        sb.append(", title='");
        sb.append(valueOf);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
