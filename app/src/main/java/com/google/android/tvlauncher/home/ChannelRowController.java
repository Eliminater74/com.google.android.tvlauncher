package com.google.android.tvlauncher.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.leanback.widget.HorizontalGridView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.data.ChannelOrderManager;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.home.util.ChannelUtil;
import com.google.android.tvlauncher.home.util.ProgramStateUtil;
import com.google.android.tvlauncher.home.view.ChannelItemsAnimator;
import com.google.android.tvlauncher.home.view.ChannelView;
import com.google.android.tvlauncher.model.HomeChannel;
import com.google.android.tvlauncher.util.AccessibilityContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;
import com.google.android.tvlauncher.util.IntentLaunchDispatcher;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

class ChannelRowController implements HomeRow, ChannelView.OnPerformMainActionListener, ChannelView.OnMoveChannelUpListener, ChannelView.OnMoveChannelDownListener, ChannelView.OnRemoveListener, ChannelView.OnStateChangeGesturePerformedListener, ChannelView.OnChannelLogoFocusedListener, EventLogger, BackHomeControllerListeners.OnBackPressedListener, BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnHomeNotHandledListener, RecyclerViewStateProvider, RecyclerViewFastScrollingManager.OnFastScrollingChangedListener {
    @VisibleForTesting
    static final int ACCESSIBILITY_MENU_DOWN = 2;
    @VisibleForTesting
    static final int ACCESSIBILITY_MENU_UP = 1;
    private static final int ACCESSIBILITY_MENU_DONE = 4;
    private static final int ACCESSIBILITY_MENU_OPEN = 0;
    private static final int ACCESSIBILITY_MENU_REMOVE = 3;
    private static final boolean DEBUG = false;
    private static final String TAG = "ChannelRowController";
    private final RequestManager mChannelLogoRequestManager;
    private final ChannelView mChannelView;
    private final EventLogger mEventLogger;
    private final HorizontalGridView mItemsListView;
    private AccessibilityContextMenu mAccessibilityContextMenu;
    private String mActionUri;
    private long mChannelId;
    private String mChannelLogoContentDescriptionFormat;
    private Uri mChannelLogoThumbnailUri;
    private ChannelOrderManager mChannelOrderManager;
    private RecyclerViewFastScrollingManager mFastScrollingManager;
    private boolean mHomeIsFastScrolling;
    private RecyclerViewStateProvider mHomeListStateProvider;
    private IntentLaunchDispatcher mIntentLauncher;
    private boolean mIsBranded;
    private boolean mIsLegacy;
    private boolean mIsSponsored;
    private ChannelItemsAdapter mItemsAdapter;
    private ImageViewTarget<Bitmap> mLogoGlideTarget;
    private ChannelItemMetadataController mMetadataController;
    private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    private OnHomeRowRemovedListener mOnHomeRowRemovedListener;
    private OnHomeRowSelectedListener mOnHomeRowSelectedListener;
    private OnHomeStateChangeListener mOnHomeStateChangeListener;
    private OnProgramSelectedListener mOnProgramSelectedListener;
    private String mPackageName;
    private String mTitle;

    ChannelRowController(ChannelView channelView, RequestManager channelLogoRequestManager, EventLogger eventLogger, ChannelOrderManager channelOrderManager, ChannelItemMetadataController metadataController, IntentLaunchDispatcher intentLaunchDispatcher, final boolean isSponsored, boolean isBranded) {
        String str;
        this.mChannelView = channelView;
        this.mChannelLogoRequestManager = channelLogoRequestManager;
        this.mEventLogger = eventLogger;
        this.mChannelOrderManager = channelOrderManager;
        this.mMetadataController = metadataController;
        if (!isSponsored) {
            this.mChannelView.setOnPerformMainActionListener(this);
        }
        this.mChannelView.setOnMoveUpListener(this);
        this.mChannelView.setOnMoveDownListener(this);
        this.mChannelView.setOnRemoveListener(this);
        this.mChannelView.setOnStateChangeGesturePerformedListener(this);
        this.mChannelView.setOnChannelLogoFocusedListener(this);
        this.mIntentLauncher = intentLaunchDispatcher;
        this.mIsSponsored = isSponsored;
        this.mIsBranded = isBranded;
        if (isSponsored) {
            this.mChannelView.setShowItemMeta(false);
            ChannelView channelView2 = this.mChannelView;
            if (this.mIsBranded) {
                str = channelView2.getContext().getString(C1188R.string.sponsored_channel_logo_title);
            } else {
                str = channelView2.getContext().getString(C1188R.string.sponsored_channel_unbranded_logo_title);
            }
            channelView2.setLogoTitle(str);
            if (!isBranded) {
                String title = this.mChannelView.getContext().getString(C1188R.string.sponsored_channel_unbranded_logo_title);
                this.mChannelView.setZoomedOutLogoTitle(title);
                this.mChannelView.setLogoContentDescription(title);
            }
        }
        this.mChannelView.setIsSponsored(this.mIsSponsored, this.mIsBranded);
        if (this.mIsSponsored) {
            this.mChannelView.setStateSettings(ChannelUtil.getSponsoredChannelStateSettings(channelView.getContext()));
        } else {
            this.mChannelView.setStateSettings(ChannelUtil.getDefaultChannelStateSettings(channelView.getContext()));
        }
        this.mItemsListView = this.mChannelView.getItemsListView();
        ChannelUtil.configureItemsListAlignment(this.mItemsListView);
        final int programLogoDefaultBackground = channelView.getContext().getColor(C1188R.color.channel_logo_default_background);
        if (!this.mIsSponsored || this.mIsBranded) {
            this.mChannelView.getChannelLogoImageView().setBackgroundColor(programLogoDefaultBackground);
        } else {
            this.mChannelView.getChannelLogoImageView().setBackground(null);
            this.mChannelView.getChannelLogoImageView().setImageDrawable(null);
        }
        this.mLogoGlideTarget = new ImageViewTarget<Bitmap>(this, this.mChannelView.getChannelLogoImageView()) {
            /* access modifiers changed from: protected */
            public void setResource(@Nullable Bitmap bitmap) {
                if (bitmap != null) {
                    ((ImageView) this.view).setImageBitmap(bitmap);
                    if (isSponsored || !bitmap.hasAlpha()) {
                        ((ImageView) this.view).setBackground(null);
                    } else {
                        ((ImageView) this.view).setBackgroundColor(programLogoDefaultBackground);
                    }
                } else {
                    ((ImageView) this.view).setImageDrawable(null);
                    ((ImageView) this.view).setBackgroundColor(programLogoDefaultBackground);
                }
            }
        };
        this.mChannelLogoContentDescriptionFormat = channelView.getContext().getString(C1188R.string.sponsored_channel_branding);
    }

    public void setOnHomeStateChangeListener(OnHomeStateChangeListener listener) {
        this.mOnHomeStateChangeListener = listener;
    }

    public void setOnHomeRowSelectedListener(OnHomeRowSelectedListener listener) {
        this.mOnHomeRowSelectedListener = listener;
    }

    public void setOnHomeRowRemovedListener(OnHomeRowRemovedListener listener) {
        this.mOnHomeRowRemovedListener = listener;
    }

    public void setHomeIsFastScrolling(boolean homeIsFastScrolling) {
        if (this.mHomeIsFastScrolling != homeIsFastScrolling) {
            this.mHomeIsFastScrolling = homeIsFastScrolling;
            updateStateForHomeFastScrolling();
        }
    }

    public void onFastScrollingChanged(boolean fastScrolling) {
        if (fastScrolling) {
            this.mItemsAdapter.setProgramState(4, false);
        } else {
            this.mItemsAdapter.setProgramState(getProgramState(this.mChannelView.getState()), true);
        }
    }

    public View getView() {
        return this.mChannelView;
    }

    /* access modifiers changed from: package-private */
    public void setOnProgramSelectedListener(OnProgramSelectedListener listener) {
        this.mOnProgramSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener listener) {
        this.mOnBackNotHandledListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener listener) {
        this.mOnHomeNotHandledListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    private void ensureItemListIsSetUp() {
        if (this.mItemsAdapter == null) {
            this.mItemsAdapter = new ChannelItemsAdapter(this.mChannelView.getContext(), this);
            this.mItemsAdapter.setIsSponsored(this.mIsSponsored, this.mIsBranded);
            this.mItemsAdapter.setOnProgramSelectedListener(this.mOnProgramSelectedListener);
            this.mItemsAdapter.setOnHomeNotHandledListener(this);
            this.mItemsAdapter.setListStateProvider(this);
            this.mItemsAdapter.setHomeListStateProvider(this.mHomeListStateProvider);
            this.mItemsListView.setAdapter(this.mItemsAdapter);
            this.mFastScrollingManager = new RecyclerViewFastScrollingManager(this.mItemsListView, new ChannelItemsAnimator());
            this.mFastScrollingManager.setOnFastScrollingChangedListener(this);
            updateStateForHomeFastScrolling();
        }
    }

    private void updateStateForHomeFastScrolling() {
        this.mFastScrollingManager.setAnimatorEnabled(!this.mHomeIsFastScrolling);
        this.mFastScrollingManager.setScrollEnabled(false);
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        this.mItemsAdapter.onStart();
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        this.mItemsAdapter.onStop();
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        this.mItemsAdapter.recycle();
        this.mItemsListView.setSelectedPosition(0);
        this.mChannelView.recycle();
        this.mMetadataController.clear();
        this.mChannelLogoThumbnailUri = null;
        AccessibilityContextMenu accessibilityContextMenu = this.mAccessibilityContextMenu;
        if (accessibilityContextMenu != null) {
            accessibilityContextMenu.dismiss();
        }
    }

    /* access modifiers changed from: package-private */
    public void bind(HomeChannel channel, int channelViewState, boolean canAddToWatchNext, boolean canRemoveProgram) {
        AccessibilityContextMenu accessibilityContextMenu;
        ensureItemListIsSetUp();
        if (!(this.mAccessibilityContextMenu == null || this.mChannelId == channel.getId())) {
            this.mAccessibilityContextMenu.dismiss();
        }
        this.mChannelId = channel.getId();
        this.mActionUri = channel.getLaunchUri();
        this.mTitle = channel.getDisplayName();
        this.mPackageName = channel.getPackageName();
        this.mIsLegacy = channel.isLegacy();
        this.mMetadataController.setLegacy(this.mIsLegacy);
        this.mChannelView.setAllowMoving(channel.canMove());
        this.mChannelView.setAllowRemoving(channel.canRemove());
        if (this.mIsLegacy || this.mIsSponsored) {
            canAddToWatchNext = false;
            canRemoveProgram = false;
        }
        if (!this.mIsSponsored) {
            bindChannelLogoTitle();
        } else if (this.mIsBranded) {
            bindSponsoredChannelLogoContentDescription(channel.getLogoContentDescription());
        }
        if (!this.mIsSponsored || this.mIsBranded) {
            this.mChannelView.setZoomedOutLogoTitle(this.mTitle);
            this.mChannelView.setItemsTitle(this.mTitle);
        }
        bindChannelMoveAction();
        if (!ChannelUtil.isEmptyState(channelViewState) || (accessibilityContextMenu = this.mAccessibilityContextMenu) == null || !accessibilityContextMenu.isShowing()) {
            updateAccessibilityContextMenuIfNeeded();
        } else {
            this.mAccessibilityContextMenu.dismiss();
        }
        if (!this.mIsSponsored || this.mIsBranded) {
            Uri logoUri = TvDataManager.getInstance(this.mChannelView.getContext()).getChannelLogoUri(Long.valueOf(channel.getId()));
            this.mChannelLogoRequestManager.asBitmap().load(logoUri).thumbnail(this.mChannelLogoRequestManager.asBitmap().load(this.mChannelLogoThumbnailUri)).into(this.mLogoGlideTarget);
            this.mChannelLogoThumbnailUri = logoUri;
        }
        this.mChannelView.setState(channelViewState);
        int oldProgramState = this.mItemsAdapter.getProgramState();
        int newProgramState = getProgramState(channelViewState);
        this.mItemsAdapter.bind(channel.getId(), this.mPackageName, newProgramState, canAddToWatchNext, canRemoveProgram, this.mIsLegacy);
        updateItemsListPosition(newProgramState, oldProgramState);
    }

    /* access modifiers changed from: package-private */
    public void bindChannelMoveAction() {
        this.mChannelView.updateChannelMoveAction(this.mChannelOrderManager.canMoveChannelUp(this.mChannelId), this.mChannelOrderManager.canMoveChannelDown(this.mChannelId));
    }

    /* access modifiers changed from: package-private */
    public void bindChannelLogoTitle() {
        LaunchItem launchItem = LaunchItemsManagerProvider.getInstance(this.mChannelView.getContext()).getLaunchItem(this.mPackageName);
        String title = launchItem != null ? launchItem.getLabel().toString() : "";
        this.mChannelView.setLogoTitle(title);
        this.mChannelView.setLogoContentDescription(title);
    }

    private void bindSponsoredChannelLogoContentDescription(String contentDescription) {
        this.mChannelView.setLogoContentDescription(String.format(this.mChannelLogoContentDescriptionFormat, contentDescription));
    }

    public void setState(int channelViewState) {
        AccessibilityContextMenu accessibilityContextMenu;
        if (this.mChannelView.getState() != channelViewState && this.mFastScrollingManager.isFastScrollingEnabled() && this.mItemsListView.getSelectedPosition() == 0) {
            this.mFastScrollingManager.setAnimatorEnabled(!this.mHomeIsFastScrolling);
        }
        this.mChannelView.setState(channelViewState);
        int oldProgramState = this.mItemsAdapter.getProgramState();
        int newProgramState = getProgramState(channelViewState);
        this.mItemsAdapter.setProgramState(newProgramState);
        updateItemsListPosition(newProgramState, oldProgramState);
        if (ChannelUtil.isEmptyState(channelViewState) && (accessibilityContextMenu = this.mAccessibilityContextMenu) != null && accessibilityContextMenu.isShowing()) {
            this.mAccessibilityContextMenu.dismiss();
        }
    }

    private void updateItemsListPosition(int newState, int oldState) {
        if (newState != oldState && ProgramStateUtil.isZoomedOutState(newState) && this.mItemsAdapter.getItemCount() > 1 && this.mItemsListView.getSelectedPosition() != 0) {
            this.mItemsListView.scrollToPosition(0);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public AccessibilityContextMenu getAccessibilityContextMenu() {
        return this.mAccessibilityContextMenu;
    }

    private int getProgramState(int channelViewState) {
        switch (channelViewState) {
            case 0:
                return 3;
            case 1:
            case 2:
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
            case 7:
                return 4;
            case 8:
                return 6;
            case 9:
                return 5;
            case 10:
                return 7;
            case 11:
                return 9;
            case 12:
                return 8;
            case 13:
                return 11;
            case 14:
                return 10;
            case 15:
                return 12;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                return 0;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void bindItemMetadata() {
        if (!this.mIsSponsored) {
            int position = this.mItemsListView.getSelectedPosition();
            TvDataManager dataManager = TvDataManager.getInstance(this.mItemsListView.getContext());
            if (position == -1 || this.mItemsAdapter.getItemCount() == 0) {
                this.mMetadataController.clear();
            } else if (position >= 0 && position < dataManager.getProgramCount(this.mChannelId)) {
                this.mMetadataController.bindView(dataManager.getProgram(this.mChannelId, position));
            }
        }
    }

    public void onPerformMainAction(ChannelView v) {
        if (ChannelUtil.isEmptyState(v.getState()) || !Util.isAccessibilityEnabled(v.getContext())) {
            performMainChannelAction(v.getChannelLogoImageView());
        } else {
            showAccessibilityMenu();
        }
    }

    private void performMainChannelAction(View view) {
        if (this.mActionUri != null) {
            logChannelAction(this.mChannelView.getContext(), TvlauncherLogEnum.TvLauncherEventCode.START_APP, TvLauncherConstants.CHANNEL_TITLE_BUTTON);
            this.mIntentLauncher.launchChannelIntentFromUriWithAnimation(this.mPackageName, this.mActionUri, false, view);
        }
    }

    private void showAccessibilityMenu() {
        if (this.mAccessibilityContextMenu == null) {
            Context context = this.mChannelView.getContext();
            this.mAccessibilityContextMenu = new AccessibilityContextMenu((Activity) context);
            this.mAccessibilityContextMenu.addItem(new ContextMenuItem(0, context.getString(C1188R.string.context_menu_primary_action_text), context.getDrawable(C1188R.C1189drawable.ic_context_menu_open_black)));
            this.mAccessibilityContextMenu.addItem(new ContextMenuItem(1, context.getString(C1188R.string.accessibility_menu_item_move_up), context.getDrawable(C1188R.C1189drawable.ic_arrow_up_black_24dp)));
            this.mAccessibilityContextMenu.addItem(new ContextMenuItem(2, context.getString(C1188R.string.accessibility_menu_item_move_down), context.getDrawable(C1188R.C1189drawable.ic_arrow_down_black_24dp)));
            this.mAccessibilityContextMenu.addItem(new ContextMenuItem(3, context.getString(C1188R.string.channel_action_remove), context.getDrawable(C1188R.C1189drawable.ic_remove_circle_black)));
            this.mAccessibilityContextMenu.addItem(new ContextMenuItem(4, context.getString(C1188R.string.accessibility_menu_item_done), context.getDrawable(C1188R.C1189drawable.ic_done_black_24dp)));
            this.mAccessibilityContextMenu.setOnMenuItemClickListener(new ChannelRowController$$Lambda$0(this));
        }
        updateAccessibilityContextMenu();
        this.mAccessibilityContextMenu.show();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$showAccessibilityMenu$0$ChannelRowController(ContextMenuItem item) {
        int id = item.getId();
        if (id == 0) {
            performMainChannelAction(this.mChannelView.getChannelLogoImageView());
        } else if (id == 1) {
            onMoveChannelUp(this.mChannelView);
        } else if (id == 2) {
            onMoveChannelDown(this.mChannelView);
        } else if (id == 3) {
            this.mAccessibilityContextMenu.dismiss();
            onRemove(this.mChannelView);
        } else if (id == 4) {
            this.mAccessibilityContextMenu.dismiss();
        }
    }

    public void onMoveChannelUp(ChannelView v) {
        if (this.mChannelOrderManager.canMoveChannelUp(this.mChannelId)) {
            this.mChannelOrderManager.moveChannelUp(this.mChannelId);
            logChannelAction(v.getContext(), TvlauncherLogEnum.TvLauncherEventCode.MOVE_CHANNEL_UP, TvLauncherConstants.MOVE_CHANNEL_BUTTON);
            this.mChannelView.updateChannelMoveAction(this.mChannelOrderManager.canMoveChannelUp(this.mChannelId), this.mChannelOrderManager.canMoveChannelDown(this.mChannelId));
            updateAccessibilityContextMenuIfNeeded();
        }
    }

    public void onMoveChannelDown(ChannelView v) {
        if (this.mChannelOrderManager.canMoveChannelDown(this.mChannelId)) {
            this.mChannelOrderManager.moveChannelDown(this.mChannelId);
            logChannelAction(v.getContext(), TvlauncherLogEnum.TvLauncherEventCode.MOVE_CHANNEL_DOWN, TvLauncherConstants.MOVE_CHANNEL_BUTTON);
            this.mChannelView.updateChannelMoveAction(this.mChannelOrderManager.canMoveChannelUp(this.mChannelId), this.mChannelOrderManager.canMoveChannelDown(this.mChannelId));
            updateAccessibilityContextMenuIfNeeded();
        }
    }

    public void onRemove(ChannelView v) {
        logChannelAction(v.getContext(), TvlauncherLogEnum.TvLauncherEventCode.REMOVE_CHANNEL, TvLauncherConstants.REMOVE_CHANNEL_BUTTON);
        TvDataManager.getInstance(v.getContext()).removeHomeChannel(this.mChannelId);
        OnHomeRowRemovedListener onHomeRowRemovedListener = this.mOnHomeRowRemovedListener;
        if (onHomeRowRemovedListener != null) {
            onHomeRowRemovedListener.onHomeRowRemoved(this);
        }
    }

    private void logChannelAction(Context context, TvlauncherLogEnum.TvLauncherEventCode eventCode, VisualElementTag buttonVeTag) {
        LogEvent event = new UserActionEvent(eventCode).setVisualElementTag(buttonVeTag);
        TvlauncherClientLog.Channel.Builder channel = event.getChannel();
        channel.setPackageName(this.mPackageName);
        if (!TextUtils.isEmpty(this.mTitle)) {
            channel.setTitle(this.mTitle);
        }
        event.getChannelCollection().setBrowsableCount(TvDataManager.getInstance(context).getHomeChannelCount());
        ChannelItemsAdapter channelItemsAdapter = this.mItemsAdapter;
        if (channelItemsAdapter != null) {
            channel.setProgramCount(channelItemsAdapter.getItemCount());
        }
        channel.setIsLegacy(this.mIsLegacy);
        this.mEventLogger.log(event);
    }

    public void onStateChangeGesturePerformed(ChannelView v, int newState) {
        switch (newState) {
            case 0:
            case 6:
            case 16:
            case 22:
                OnHomeRowSelectedListener onHomeRowSelectedListener = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener != null) {
                    onHomeRowSelectedListener.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener != null) {
                    onHomeStateChangeListener.onHomeStateChange(0);
                    return;
                }
                return;
            case 1:
            case 7:
            case 17:
            case 23:
                OnHomeStateChangeListener onHomeStateChangeListener2 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener2 != null) {
                    onHomeStateChangeListener2.onHomeStateChange(0);
                    return;
                }
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            case 12:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 26:
            case 27:
            case 28:
            case 29:
                String valueOf = String.valueOf(ChannelView.stateToString(newState));
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported ChannelView state change gesture: ".concat(valueOf) : new String("Unsupported ChannelView state change gesture: "));
            case 8:
            case 24:
                OnHomeRowSelectedListener onHomeRowSelectedListener2 = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener2 != null) {
                    onHomeRowSelectedListener2.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener3 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener3 != null) {
                    onHomeStateChangeListener3.onHomeStateChange(1);
                    return;
                }
                return;
            case 9:
            case 25:
                OnHomeStateChangeListener onHomeStateChangeListener4 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener4 != null) {
                    onHomeStateChangeListener4.onHomeStateChange(1);
                    return;
                }
                return;
            case 11:
                OnHomeRowSelectedListener onHomeRowSelectedListener3 = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener3 != null) {
                    onHomeRowSelectedListener3.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener5 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener5 != null) {
                    onHomeStateChangeListener5.onHomeStateChange(2);
                    return;
                }
                return;
            case 13:
                OnHomeRowSelectedListener onHomeRowSelectedListener4 = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener4 != null) {
                    onHomeRowSelectedListener4.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener6 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener6 != null) {
                    onHomeStateChangeListener6.onHomeStateChange(3);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onChannelLogoFocused() {
        this.mItemsAdapter.updateProgramFocusState(this.mItemsListView.getSelectedPosition());
    }

    public void log(LogEvent event) {
        if (event.getEventCode() == TvlauncherLogEnum.TvLauncherEventCode.START_PROGRAM) {
            this.mMetadataController.populateLogEvent(event);
        }
        TvlauncherClientLog.Channel.Builder channel = event.getChannel();
        channel.setPackageName(this.mPackageName);
        ChannelItemsAdapter channelItemsAdapter = this.mItemsAdapter;
        if (channelItemsAdapter != null) {
            channel.setProgramCount(channelItemsAdapter.getItemCount());
        }
        if (!TextUtils.isEmpty(this.mTitle)) {
            channel.setTitle(this.mTitle);
        }
        channel.setIsLegacy(this.mIsLegacy);
        this.mEventLogger.log(event);
    }

    private void setSelectedItemPosition(int position) {
        if (Util.areHomeScreenAnimationsEnabled(this.mChannelView.getContext())) {
            this.mItemsListView.setSelectedPosition(position);
        } else {
            this.mItemsListView.setSelectedPositionSmooth(position);
        }
    }

    public void onBackPressed(Context c) {
        if (this.mItemsListView.getAdapter() != null) {
            if (this.mChannelView.getState() == 0 && this.mItemsListView.getSelectedPosition() != 0 && this.mItemsListView.getAdapter().getItemCount() > 0) {
                setSelectedItemPosition(0);
            } else if (this.mChannelView.getState() == 13 || this.mChannelView.getState() == 11) {
                onStateChangeGesturePerformed(this.mChannelView, 8);
            } else {
                BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener = this.mOnBackNotHandledListener;
                if (onBackNotHandledListener != null) {
                    onBackNotHandledListener.onBackNotHandled(c);
                }
            }
        }
    }

    public void onHomePressed(Context c) {
        AccessibilityContextMenu accessibilityContextMenu = this.mAccessibilityContextMenu;
        if (accessibilityContextMenu != null && accessibilityContextMenu.isShowing()) {
            this.mAccessibilityContextMenu.dismiss();
        } else if (this.mChannelView.getState() == 13 || this.mChannelView.getState() == 11) {
            onStateChangeGesturePerformed(this.mChannelView, 8);
        } else {
            if (this.mChannelView.getState() == 0) {
                HorizontalGridView horizontalGridView = this.mItemsListView;
                RecyclerView.ViewHolder selectedViewHolder = horizontalGridView.findViewHolderForAdapterPosition(horizontalGridView.getSelectedPosition());
                if (selectedViewHolder instanceof BackHomeControllerListeners.OnHomePressedListener) {
                    ((BackHomeControllerListeners.OnHomePressedListener) selectedViewHolder).onHomePressed(c);
                    return;
                }
            }
            onHomeNotHandled(c);
        }
    }

    public void onHomeNotHandled(Context c) {
        BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener = this.mOnHomeNotHandledListener;
        if (onHomeNotHandledListener != null) {
            onHomeNotHandledListener.onHomeNotHandled(c);
        }
    }

    public boolean isAnimating() {
        return this.mItemsListView.getItemAnimator() != null && this.mItemsListView.getItemAnimator().isRunning();
    }

    public boolean isAnimating(RecyclerView.ItemAnimator.ItemAnimatorFinishedListener listener) {
        HorizontalGridView horizontalGridView = this.mItemsListView;
        if (horizontalGridView != null && horizontalGridView.getItemAnimator() != null) {
            return this.mItemsListView.getItemAnimator().isRunning(listener);
        }
        if (listener == null) {
            return false;
        }
        listener.onAnimationsFinished();
        return false;
    }

    private void updateAccessibilityContextMenu() {
        this.mAccessibilityContextMenu.findItem(1).setEnabled(this.mChannelOrderManager.canMoveChannelUp(this.mChannelId));
        this.mAccessibilityContextMenu.findItem(2).setEnabled(this.mChannelOrderManager.canMoveChannelDown(this.mChannelId));
    }

    private void updateAccessibilityContextMenuIfNeeded() {
        AccessibilityContextMenu accessibilityContextMenu = this.mAccessibilityContextMenu;
        if (accessibilityContextMenu != null && accessibilityContextMenu.isShowing()) {
            updateAccessibilityContextMenu();
        }
    }

    public String toString() {
        String obj = super.toString();
        long j = this.mChannelId;
        String str = this.mTitle;
        StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 48 + String.valueOf(str).length());
        sb.append('{');
        sb.append(obj);
        sb.append(", mChannelId='");
        sb.append(j);
        sb.append('\'');
        sb.append(", mTitle='");
        sb.append(str);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
