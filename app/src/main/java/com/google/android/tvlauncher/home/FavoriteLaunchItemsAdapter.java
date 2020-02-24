package com.google.android.tvlauncher.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogUtils;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.BannerView;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.LaunchItemImageLoader;
import com.google.android.tvlauncher.appsview.OnEditModeFocusSearchCallback;
import com.google.android.tvlauncher.appsview.data.LaunchItemImageDataSource;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import com.google.android.tvlauncher.home.view.AddFavoriteAppCardView;
import com.google.android.tvlauncher.home.view.FavoriteLaunchItemView;
import com.google.android.tvlauncher.util.AccessibilityContextMenu;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;
import com.google.android.tvlauncher.util.LaunchUtil;
import com.google.android.tvlauncher.util.ScaleFocusHandler;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import java.util.List;

class FavoriteLaunchItemsAdapter extends RecyclerView.Adapter<BaseViewHolder> implements EventLogger {
    private static final boolean DEBUG = false;
    private static final String PAYLOAD_FOCUS_CHANGED = "PAYLOAD_FOCUS_CHANGED";
    private static final String PAYLOAD_STATE = "PAYLOAD_STATE";
    private static final String PAYLOAD_UPDATE_PIVOT = "PAYLOAD_UPDATE_PIVOT";
    private static final String TAG = "FavLaunchItemsAdapter";
    private static final int TYPE_APP = 0;
    private static final int TYPE_MORE = 1;
    /* access modifiers changed from: private */
    public int mAppState = 0;
    /* access modifiers changed from: private */
    public final float mBannerFocusedElevation;
    /* access modifiers changed from: private */
    public final float mBannerFocusedScale;
    /* access modifiers changed from: private */
    public final int mBannerHeight;
    /* access modifiers changed from: private */
    public final int mBannerWidth;
    /* access modifiers changed from: private */
    public final LaunchItemsManager mDataManager;
    /* access modifiers changed from: private */
    public final int mDefaultAboveSelectedBottomMargin;
    /* access modifiers changed from: private */
    public final int mDefaultBottomMargin;
    /* access modifiers changed from: private */
    public final int mDefaultHorizontalMargin;
    /* access modifiers changed from: private */
    public final int mDefaultTopMargin;
    /* access modifiers changed from: private */
    public FavoriteLaunchItemsRowEditModeActionCallbacks mEditModeCallbacks;
    private final EventLogger mEventLogger;
    /* access modifiers changed from: private */
    public final ScaleFocusHandler mFocusHandlerTemplate;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mHomeListStateProvider;
    /* access modifiers changed from: private */
    public int mLastUnfocusedAdapterPosition = -1;
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mListStateProvider;
    private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    /* access modifiers changed from: private */
    public final Drawable mPlaceholderBanner;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public final int mZoomedOutBottomMargin;
    /* access modifiers changed from: private */
    public final int mZoomedOutHorizontalMargin;
    /* access modifiers changed from: private */
    public final int mZoomedOutTopMargin;

    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        onBindViewHolder((BaseViewHolder) viewHolder, i, (List<Object>) list);
    }

    FavoriteLaunchItemsAdapter(Context context, EventLogger eventLogger) {
        this.mDataManager = LaunchItemsManagerProvider.getInstance(context);
        this.mEventLogger = eventLogger;
        this.mDataManager.setHomeScreenItemsChangeListener(new LaunchItemsManager.HomeScreenItemsChangeListener() {
            public void onHomeScreenItemsLoaded() {
                int unused = FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
                FavoriteLaunchItemsAdapter.this.notifyDataSetChanged();
            }

            public void onHomeScreenItemsChanged(List<LaunchItem> list) {
                int unused = FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
                FavoriteLaunchItemsAdapter.this.notifyDataSetChanged();
            }

            public void onHomeScreenItemsSwapped(int fromDisplayedPosition, int toDisplayedPosition) {
                int unused = FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
                FavoriteLaunchItemsAdapter.this.notifyItemMoved(fromDisplayedPosition, toDisplayedPosition);
            }
        });
        Resources resources = context.getResources();
        this.mBannerFocusedScale = resources.getFraction(C1188R.fraction.home_app_banner_focused_scale, 1, 1);
        this.mBannerFocusedElevation = resources.getDimension(C1188R.dimen.home_app_banner_focused_elevation);
        this.mFocusHandlerTemplate = new ScaleFocusHandler(resources.getInteger(C1188R.integer.home_app_banner_focused_animation_duration_ms), this.mBannerFocusedScale, this.mBannerFocusedElevation);
        this.mDefaultTopMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_default_margin_top);
        this.mDefaultBottomMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_default_margin_bottom);
        this.mDefaultHorizontalMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_default_margin_horizontal);
        this.mDefaultAboveSelectedBottomMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_default_above_selected_margin_bottom);
        this.mZoomedOutHorizontalMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_zoomed_out_margin_horizontal);
        this.mZoomedOutTopMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_zoomed_out_margin_top);
        this.mZoomedOutBottomMargin = resources.getDimensionPixelSize(C1188R.dimen.home_app_banner_zoomed_out_margin_bottom);
        this.mPlaceholderBanner = new ColorDrawable(ContextCompat.getColor(context, C1188R.color.app_banner_background_color));
        this.mBannerWidth = resources.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_width);
        this.mBannerHeight = resources.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_height);
        setHasStableIds(true);
        if (!this.mDataManager.areItemsLoaded() || this.mDataManager.hasPendingLoadRequest()) {
            this.mDataManager.refreshLaunchItems();
        }
    }

    /* access modifiers changed from: package-private */
    public int getAppState() {
        return this.mAppState;
    }

    /* access modifiers changed from: package-private */
    public void setAppState(int state) {
        if (this.mAppState != state) {
            this.mAppState = state;
            this.mLastUnfocusedAdapterPosition = -1;
            notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAppsRowEditModeActionCallbacks(FavoriteLaunchItemsRowEditModeActionCallbacks callbacks) {
        this.mEditModeCallbacks = callbacks;
    }

    /* access modifiers changed from: package-private */
    public void setListStateProvider(RecyclerViewStateProvider listStateProvider) {
        this.mListStateProvider = listStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener) {
        this.mOnHomeNotHandledListener = onHomeNotHandledListener;
    }

    /* access modifiers changed from: package-private */
    public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener) {
        this.mOnBackNotHandledListener = onBackNotHandledListener;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType != 0) {
            return new AddMoreViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_home_add_more_apps_banner, parent, false));
        }
        AppViewHolder appViewHolder = new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_app_banner_home, parent, false));
        appViewHolder.setOnHomeNotHandledListener(this.mOnHomeNotHandledListener);
        appViewHolder.setOnBackNotHandledListener(this.mOnBackNotHandledListener);
        return appViewHolder;
    }

    public int getItemViewType(int position) {
        LaunchItem item = this.mDataManager.getHomeScreenItems().get(position);
        if (this.mDataManager.isFavorite(item) || this.mDataManager.isPinnedFavorite(item)) {
            return 0;
        }
        return 1;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setItem(this.mDataManager.getHomeScreenItems().get(position));
        holder.updateSize();
        holder.updateBannerDimmingFactor();
        if (Util.areHomeScreenAnimationsEnabled(holder.itemView.getContext())) {
            holder.updateFocusedState();
        } else {
            holder.mFocusHandler.resetFocusedState();
        }
        if (holder instanceof AppViewHolder) {
            ((AppViewHolder) holder).updateAccessibilityContextMenuIfNeeded();
        }
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        if (payloads.contains(PAYLOAD_STATE)) {
            holder.updateSize();
            holder.updateBannerDimmingFactor();
        }
        if ((payloads.contains("PAYLOAD_FOCUS_CHANGED") || payloads.contains(PAYLOAD_STATE) || payloads.contains(PAYLOAD_UPDATE_PIVOT)) && Util.areHomeScreenAnimationsEnabled(holder.itemView.getContext())) {
            holder.updateFocusedState();
        }
    }

    public int getItemCount() {
        if (this.mDataManager.areItemsLoaded()) {
            return this.mDataManager.getHomeScreenItems().size();
        }
        return 0;
    }

    public long getItemId(int position) {
        return (long) this.mDataManager.getHomeScreenItems().get(position).getPackageName().hashCode();
    }

    public void log(LogEvent event) {
        event.pushParentVisualElementTag(TvLauncherConstants.FAVORITE_APPS_CONTAINER);
        this.mEventLogger.log(event);
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = null;
    }

    @VisibleForTesting
    class AppViewHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener, OnEditModeFocusSearchCallback, BannerView.OnWindowVisibilityChangedListener, BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnBackPressedListener {
        @VisibleForTesting
        static final int ACCESSIBILITY_MENU_DONE = 2;
        @VisibleForTesting
        static final int ACCESSIBILITY_MENU_LEFT = 0;
        @VisibleForTesting
        static final int ACCESSIBILITY_MENU_RIGHT = 1;
        private static final int MENU_FAVORITE = 2;
        private static final int MENU_MOVE = 1;
        private static final int MENU_PRIMARY_ACTION = 0;
        private AccessibilityContextMenu mAccessibilityContextMenu;
        /* access modifiers changed from: private */
        public int mAdapterPositionAfterMovement;
        /* access modifiers changed from: private */
        public int mAdapterPositionBeforeMovement;
        /* access modifiers changed from: private */
        public final BannerView mBanner;
        private ContextMenu mContextMenu;
        private LaunchItem mItem;
        private Runnable mNotifyPivotChangedRunnable = new Runnable() {
            public void run() {
                FavoriteLaunchItemsAdapter.this.notifyItemChanged(AppViewHolder.this.mAdapterPositionBeforeMovement, FavoriteLaunchItemsAdapter.PAYLOAD_UPDATE_PIVOT);
                FavoriteLaunchItemsAdapter.this.notifyItemChanged(AppViewHolder.this.mAdapterPositionAfterMovement, FavoriteLaunchItemsAdapter.PAYLOAD_UPDATE_PIVOT);
            }
        };
        private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
        private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
        private boolean mPinned;

        AppViewHolder(View v) {
            super(v);
            this.mBanner = (BannerView) v;
            if (!Util.areHomeScreenAnimationsEnabled(this.mContext)) {
                this.mFocusHandler.setPivotProvider(new ScaleFocusHandler.PivotProvider(FavoriteLaunchItemsAdapter.this) {
                    public int getPivot() {
                        if (AppViewHolder.this.getAdapterPosition() == 0) {
                            return 1;
                        }
                        return 0;
                    }

                    public boolean shouldAnimate() {
                        return AppViewHolder.this.mBanner.isBeingEdited() && AppViewHolder.this.getAdapterPosition() <= 1;
                    }
                });
            }
            this.mBanner.setOnClickListener(this);
            this.mBanner.setOnLongClickListener(this);
            this.mBanner.setOnEditModeFocusSearchCallback(this);
            this.mBanner.setOnWindowVisibilityChangedListener(this);
            this.mBanner.setDefaultScaleAnimationsEnabled(false);
        }

        public void setItem(LaunchItem item) {
            super.setItem(item);
            this.mPinned = FavoriteLaunchItemsAdapter.this.mDataManager.isPinnedFavorite(item);
            this.mItem = item;
            this.mBanner.setLaunchItem(this.mItem);
            new LaunchItemImageLoader(this.mBanner.getContext()).setLaunchItemImageDataSource(new LaunchItemImageDataSource(item, PackageImageDataSource.ImageType.BANNER, LaunchItemsManagerProvider.getInstance(this.mContext).getCurrentLocale())).setTargetImageView(this.mBanner.getBannerImage()).setPlaceholder(FavoriteLaunchItemsAdapter.this.mPlaceholderBanner).setWidth(FavoriteLaunchItemsAdapter.this.mBannerWidth).setHeight(FavoriteLaunchItemsAdapter.this.mBannerHeight).loadLaunchItemImage();
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public BannerView getBanner() {
            return this.mBanner;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public AccessibilityContextMenu getAccessibilityContextMenu() {
            return this.mAccessibilityContextMenu;
        }

        /* access modifiers changed from: package-private */
        public void onEnterEditModeView() {
            if (FavoriteLaunchItemsAdapter.this.mEditModeCallbacks != null) {
                FavoriteLaunchItemsAdapter.this.mEditModeCallbacks.onEnterEditMode();
            }
            this.mBanner.setIsBeingEdited(true);
            if (Util.isAccessibilityEnabled(this.mContext)) {
                showAccessibilityMenu();
            }
        }

        /* access modifiers changed from: package-private */
        public void onExitEditModeView() {
            if (FavoriteLaunchItemsAdapter.this.mEditModeCallbacks != null) {
                FavoriteLaunchItemsAdapter.this.mEditModeCallbacks.onExitEditMode();
            }
            this.mBanner.setIsBeingEdited(false);
            AccessibilityContextMenu accessibilityContextMenu = this.mAccessibilityContextMenu;
            if (accessibilityContextMenu != null) {
                accessibilityContextMenu.dismiss();
            }
        }

        /* access modifiers changed from: package-private */
        public void onPrimaryAction(LaunchItem item, View view) {
            try {
                String packageName = LogUtils.getPackage(item.getIntent());
                if (packageName == null) {
                    packageName = item.getPackageName();
                }
                LogEvent event = new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.START_APP).setVisualElementTag(TvLauncherConstants.LAUNCH_ITEM).setVisualElementIndex(getAdapterPosition());
                event.getApplication().setPackageName(packageName);
                FavoriteLaunchItemsAdapter.this.log(event);
                LaunchUtil.startActivityWithAnimation(item.getIntent(), view);
            } catch (ActivityNotFoundException | SecurityException e) {
                Toast.makeText(this.mContext, C1188R.string.failed_launch, 0).show();
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Cannot start activity : ");
                sb.append(valueOf);
                Log.e(FavoriteLaunchItemsAdapter.TAG, sb.toString());
            }
        }

        /* access modifiers changed from: package-private */
        public void onUnFavorite(LaunchItem item) {
            LogEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.UNFAVORITE_APP);
            if (item.isAppLink()) {
                event.getAppLink().setPackageName(item.getPackageName());
                if (item.getDataUri() != null) {
                    event.getAppLink().setUri(item.getDataUri());
                }
            }
            FavoriteLaunchItemsAdapter.this.log(event);
            FavoriteLaunchItemsAdapter.this.mDataManager.removeFromFavorites(item);
        }

        public void onClick(View view) {
            if (Util.isAccessibilityEnabled(this.mContext)) {
                showContextMenu(this.mBanner);
            } else if (this.mBanner.isBeingEdited()) {
                onExitEditModeView();
            } else {
                onPrimaryAction(this.mBanner.getItem(), view);
            }
        }

        public boolean onLongClick(View v) {
            if (this.mBanner.isBeingEdited()) {
                return true;
            }
            showContextMenu(this.mBanner);
            return true;
        }

        public View onEditModeFocusSearch(int direction, View searchedView) {
            TvlauncherLogEnum.TvLauncherEventCode eventCode;
            boolean isBeingEdited = this.mBanner.isBeingEdited();
            if (isBeingEdited && (!(searchedView instanceof BannerView) || FavoriteLaunchItemsAdapter.this.mDataManager.isPinnedFavorite(((BannerView) searchedView).getItem()))) {
                return this.mBanner;
            }
            if (!isBeingEdited) {
                return searchedView;
            }
            LaunchItem item = this.mBanner.getItem();
            LaunchItem searchedItem = ((BannerView) searchedView).getItem();
            this.mAdapterPositionBeforeMovement = getAdapterPosition();
            FavoriteLaunchItemsAdapter.this.mDataManager.swapFavoriteAppOrder(item, searchedItem);
            this.mAdapterPositionAfterMovement = getAdapterPosition();
            if (!Util.areHomeScreenAnimationsEnabled(this.mContext)) {
                this.mFocusHandler.animateFocusedState(true);
            } else if (this.mAdapterPositionBeforeMovement <= 1 && this.mAdapterPositionAfterMovement <= 1) {
                FavoriteLaunchItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyPivotChangedRunnable);
                if (FavoriteLaunchItemsAdapter.this.mRecyclerView == null || FavoriteLaunchItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                    FavoriteLaunchItemsAdapter.this.mHandler.post(this.mNotifyPivotChangedRunnable);
                } else {
                    this.mNotifyPivotChangedRunnable.run();
                }
            }
            updateAccessibilityContextMenuIfNeeded();
            int moveDirection = direction;
            if (!(moveDirection == 66 || moveDirection == 17)) {
                moveDirection = this.mAdapterPositionAfterMovement > this.mAdapterPositionBeforeMovement ? 66 : 17;
            }
            if (moveDirection == 17) {
                eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_LEFT;
            } else if (moveDirection == 66) {
                eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_RIGHT;
            } else {
                StringBuilder sb = new StringBuilder(30);
                sb.append("Invalid direction: ");
                sb.append(direction);
                throw new IllegalArgumentException(sb.toString());
            }
            LogEvent logEvent = new LogEvent(eventCode).setVisualElementTag(TvLauncherConstants.LAUNCH_ITEM).setVisualElementIndex(FavoriteLaunchItemsAdapter.this.mDataManager.getOrderedFavoritePosition(item));
            logEvent.getApplication().setPackageName(item.getPackageName());
            FavoriteLaunchItemsAdapter.this.log(logEvent);
            return this.mBanner;
        }

        /* access modifiers changed from: protected */
        public void handleFocusChange(boolean hasFocus) {
            super.handleFocusChange(hasFocus);
            if (this.mBanner.isBeingEdited() && !hasFocus) {
                onExitEditModeView();
            }
            ContextMenu contextMenu = this.mContextMenu;
            if (contextMenu != null && contextMenu.isShowing()) {
                this.mContextMenu.forceDismiss();
            }
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public ContextMenu getContextMenu() {
            return this.mContextMenu;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public void showContextMenu(BannerView bannerView) {
            if (!bannerView.hasFocus()) {
                return;
            }
            if (FavoriteLaunchItemsAdapter.this.mListStateProvider != null && FavoriteLaunchItemsAdapter.this.mListStateProvider.isAnimating()) {
                return;
            }
            if (FavoriteLaunchItemsAdapter.this.mHomeListStateProvider == null || !FavoriteLaunchItemsAdapter.this.mHomeListStateProvider.isAnimating()) {
                LaunchItem item = bannerView.getItem();
                this.mContextMenu = new ContextMenu((Activity) this.mContext, bannerView.getBannerContainer(), bannerView.getCornerRadius(), bannerView.getScaleX(), bannerView.getScaleY());
                ContextMenuItem primaryActionItem = new ContextMenuItem(0, this.mContext.getString(C1188R.string.context_menu_primary_action_text), this.mContext.getDrawable(C1188R.C1189drawable.ic_context_menu_open_black));
                primaryActionItem.setAutoDismiss(false);
                this.mContextMenu.addItem(primaryActionItem);
                if (this.mPinned) {
                    ContextMenuItem menuCantMoveItem = new ContextMenuItem(1, this.mContext.getString(C1188R.string.context_menu_can_not_move_text), this.mContext.getDrawable(C1188R.C1189drawable.ic_context_menu_move_left_right_black));
                    menuCantMoveItem.setEnabled(false);
                    ContextMenuItem menuCantRemove = new ContextMenuItem(2, this.mContext.getString(C1188R.string.context_menu_can_not_remove_text), this.mContext.getDrawable(C1188R.C1189drawable.ic_context_menu_unfavorite_black));
                    menuCantRemove.setEnabled(false);
                    this.mContextMenu.addItem(menuCantMoveItem);
                    this.mContextMenu.addItem(menuCantRemove);
                } else {
                    this.mContextMenu.addItem(new ContextMenuItem(1, this.mContext.getString(C1188R.string.context_menu_move_text), this.mContext.getDrawable(C1188R.C1189drawable.ic_context_menu_move_left_right_black)));
                    this.mContextMenu.addItem(new ContextMenuItem(2, this.mContext.getString(C1188R.string.context_menu_unfavorite_text), this.mContext.getDrawable(C1188R.C1189drawable.ic_context_menu_unfavorite_black)));
                    this.mContextMenu.findItem(1).setEnabled(!FavoriteLaunchItemsAdapter.this.mDataManager.isOnlyFavorite(item));
                }
                this.mContextMenu.setOnMenuItemClickListener(createOnItemClickListener(item, bannerView));
                this.mContextMenu.show();
            }
        }

        private ContextMenu.OnItemClickListener createOnItemClickListener(LaunchItem launchItem, View view) {
            return new FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$0(this, launchItem, view);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$createOnItemClickListener$0$FavoriteLaunchItemsAdapter$AppViewHolder */
        public final /* synthetic */ void mo21377x708b30d3(LaunchItem launchItem, View view, ContextMenuItem item) {
            int id = item.getId();
            if (id == 0) {
                onPrimaryAction(launchItem, view);
            } else if (id == 1) {
                onEnterEditModeView();
            } else if (id == 2) {
                onUnFavorite(launchItem);
            }
        }

        private void showAccessibilityMenu() {
            if (this.mAccessibilityContextMenu == null) {
                this.mAccessibilityContextMenu = new AccessibilityContextMenu((Activity) this.mContext);
                ContextMenuItem menuLeft = new ContextMenuItem(0, this.mContext.getString(C1188R.string.accessibility_menu_item_move_left), this.mContext.getDrawable(C1188R.C1189drawable.ic_arrow_left_black_24dp));
                ContextMenuItem menuRight = new ContextMenuItem(1, this.mContext.getString(C1188R.string.accessibility_menu_item_move_right), this.mContext.getDrawable(C1188R.C1189drawable.ic_arrow_right_black_24dp));
                ContextMenuItem menuDone = new ContextMenuItem(2, this.mContext.getString(C1188R.string.accessibility_menu_item_done), this.mContext.getDrawable(C1188R.C1189drawable.ic_done_black_24dp));
                if (Util.isRtl(this.mContext)) {
                    this.mAccessibilityContextMenu.addItem(menuRight);
                    this.mAccessibilityContextMenu.addItem(menuLeft);
                    this.mAccessibilityContextMenu.addItem(menuDone);
                } else {
                    this.mAccessibilityContextMenu.addItem(menuLeft);
                    this.mAccessibilityContextMenu.addItem(menuRight);
                    this.mAccessibilityContextMenu.addItem(menuDone);
                }
                this.mAccessibilityContextMenu.setOnMenuItemClickListener(new FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$1(this));
                this.mAccessibilityContextMenu.setOnDismissListener(new FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$2(this));
            }
            updateAccessibilityContextMenu();
            this.mAccessibilityContextMenu.show();
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$showAccessibilityMenu$1$FavoriteLaunchItemsAdapter$AppViewHolder */
        public final /* synthetic */ void mo21378x32081830(ContextMenuItem item) {
            int id = item.getId();
            if (id == 0) {
                this.mBanner.focusSearch(17);
            } else if (id == 1) {
                this.mBanner.focusSearch(66);
            } else if (id == 2) {
                this.mAccessibilityContextMenu.dismiss();
            }
        }

        public void onWindowVisibilityChanged(int visibility) {
            ContextMenu contextMenu;
            if ((visibility == 4 || visibility == 8) && (contextMenu = this.mContextMenu) != null && contextMenu.isShowing()) {
                this.mContextMenu.forceDismiss();
            }
        }

        /* access modifiers changed from: package-private */
        public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener) {
            this.mOnHomeNotHandledListener = onHomeNotHandledListener;
        }

        /* access modifiers changed from: package-private */
        public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener) {
            this.mOnBackNotHandledListener = onBackNotHandledListener;
        }

        public void onHomePressed(Context c) {
            BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener;
            if (!handleHomeBackPress() && (onHomeNotHandledListener = this.mOnHomeNotHandledListener) != null) {
                onHomeNotHandledListener.onHomeNotHandled(c);
            }
        }

        public void onBackPressed(Context c) {
            BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener;
            if (!handleHomeBackPress() && (onBackNotHandledListener = this.mOnBackNotHandledListener) != null) {
                onBackNotHandledListener.onBackNotHandled(c);
            }
        }

        private boolean handleHomeBackPress() {
            if (this.mBanner.isBeingEdited()) {
                onExitEditModeView();
                return true;
            }
            ContextMenu contextMenu = this.mContextMenu;
            if (contextMenu == null || !contextMenu.isShowing()) {
                return false;
            }
            this.mContextMenu.forceDismiss();
            return true;
        }

        /* access modifiers changed from: package-private */
        public void updateFocusedState() {
            super.updateFocusedState();
            ((BannerView) this.itemView).setTitleVisibility(this.itemView.isFocused() ? 0 : 4);
        }

        private void updateAccessibilityContextMenu() {
            boolean z = true;
            if (Util.isRtl(this.mContext)) {
                this.mAccessibilityContextMenu.findItem(0).setEnabled(getAdapterPosition() < FavoriteLaunchItemsAdapter.this.getItemCount() + -2);
                ContextMenuItem findItem = this.mAccessibilityContextMenu.findItem(1);
                if (getAdapterPosition() <= 0) {
                    z = false;
                }
                findItem.setEnabled(z);
                return;
            }
            this.mAccessibilityContextMenu.findItem(0).setEnabled(getAdapterPosition() > 0);
            ContextMenuItem findItem2 = this.mAccessibilityContextMenu.findItem(1);
            if (getAdapterPosition() >= FavoriteLaunchItemsAdapter.this.getItemCount() - 2) {
                z = false;
            }
            findItem2.setEnabled(z);
        }

        /* access modifiers changed from: private */
        public void updateAccessibilityContextMenuIfNeeded() {
            AccessibilityContextMenu accessibilityContextMenu = this.mAccessibilityContextMenu;
            if (accessibilityContextMenu != null && accessibilityContextMenu.isShowing()) {
                updateAccessibilityContextMenu();
            }
        }
    }

    @VisibleForTesting
    class AddMoreViewHolder extends BaseViewHolder implements View.OnClickListener {
        private Intent mIntent;

        AddMoreViewHolder(final FavoriteLaunchItemsAdapter this$0, View v) {
            super(v);
            this.itemView.setOnClickListener(this);
            final int cornerRadius = this.itemView.getResources().getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius);
            this.mImageView.setOutlineProvider(new ViewOutlineProvider(this) {
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) cornerRadius);
                }
            });
            this.mImageView.setClipToOutline(true);
            this.itemView.setOutlineProvider(new ViewOutlineProvider(this) {
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getResources().getDimensionPixelSize(C1188R.dimen.home_app_banner_width), view.getResources().getDimensionPixelSize(C1188R.dimen.home_app_banner_image_height), (float) cornerRadius);
                }
            });
            if (!Util.areHomeScreenAnimationsEnabled(this.mContext)) {
                this.mFocusHandler.setPivotProvider(new ScaleFocusHandler.PivotProvider() {
                    public int getPivot() {
                        if (AddMoreViewHolder.this.getAdapterPosition() == 0) {
                            return 1;
                        }
                        return 0;
                    }

                    public boolean shouldAnimate() {
                        return false;
                    }
                });
            }
        }

        public void setItem(LaunchItem item) {
            super.setItem(item);
            this.mIntent = item.getIntent();
        }

        public void onClick(View v) {
            try {
                this.mContext.startActivity(this.mIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this.mContext, C1188R.string.failed_launch, 0).show();
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Cannot start activity : ");
                sb.append(valueOf);
                Log.e(FavoriteLaunchItemsAdapter.TAG, sb.toString());
            }
        }

        /* access modifiers changed from: package-private */
        public void updateFocusedState() {
            super.updateFocusedState();
            ((AddFavoriteAppCardView) this.itemView).setTitleVisibility(this.itemView.isFocused() ? 0 : 4);
        }
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        final Context mContext;
        ScaleFocusHandler mFocusHandler;
        ImageView mImageView;
        private Runnable mNotifyFocusChangedRunnable = new FavoriteLaunchItemsAdapter$BaseViewHolder$$Lambda$0(this);
        int mPivotVerticalShift;
        TextView mTitleView;

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$0$FavoriteLaunchItemsAdapter$BaseViewHolder() {
            FavoriteLaunchItemsAdapter.this.notifyItemChanged(getAdapterPosition(), "PAYLOAD_FOCUS_CHANGED");
            if (FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition != -1) {
                FavoriteLaunchItemsAdapter favoriteLaunchItemsAdapter = FavoriteLaunchItemsAdapter.this;
                favoriteLaunchItemsAdapter.notifyItemChanged(favoriteLaunchItemsAdapter.mLastUnfocusedAdapterPosition, "PAYLOAD_FOCUS_CHANGED");
                int unused = FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            }
        }

        BaseViewHolder(View v) {
            super(v);
            this.mContext = v.getContext();
            this.mTitleView = (TextView) v.findViewById(C1188R.C1191id.app_title);
            this.mImageView = (ImageView) v.findViewById(C1188R.C1191id.banner_image);
            this.mPivotVerticalShift = (-v.getResources().getDimensionPixelSize(C1188R.dimen.app_banner_title_height)) / 2;
            View.OnFocusChangeListener onFocusChangeListener = new FavoriteLaunchItemsAdapter$BaseViewHolder$$Lambda$1(this);
            if (!Util.areHomeScreenAnimationsEnabled(this.mContext)) {
                this.mFocusHandler = new ScaleFocusHandler(FavoriteLaunchItemsAdapter.this.mFocusHandlerTemplate);
                this.mFocusHandler.setView(v);
                this.mFocusHandler.setOnFocusChangeListener(onFocusChangeListener);
                this.mFocusHandler.setPivotVerticalShift(this.mPivotVerticalShift);
                return;
            }
            v.setOnFocusChangeListener(onFocusChangeListener);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$1$FavoriteLaunchItemsAdapter$BaseViewHolder(View view, boolean hasFocus) {
            handleFocusChange(hasFocus);
        }

        /* access modifiers changed from: protected */
        public void handleFocusChange(boolean hasFocus) {
            if (!Util.areHomeScreenAnimationsEnabled(this.mContext)) {
                handleFocusChangeWhenAnimationsDisabled(hasFocus);
                return;
            }
            FavoriteLaunchItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyFocusChangedRunnable);
            if (!hasFocus) {
                int unused = FavoriteLaunchItemsAdapter.this.mLastUnfocusedAdapterPosition = getAdapterPosition();
            } else if (FavoriteLaunchItemsAdapter.this.mRecyclerView == null || FavoriteLaunchItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                FavoriteLaunchItemsAdapter.this.mHandler.post(this.mNotifyFocusChangedRunnable);
            } else {
                this.mNotifyFocusChangedRunnable.run();
            }
        }

        private void handleFocusChangeWhenAnimationsDisabled(boolean hasFocus) {
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setSelected(this.itemView.hasFocus());
                this.itemView.postDelayed(new FavoriteLaunchItemsAdapter$BaseViewHolder$$Lambda$2(this, hasFocus), 60);
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$handleFocusChangeWhenAnimationsDisabled$2$FavoriteLaunchItemsAdapter$BaseViewHolder */
        public final /* synthetic */ void mo21389xe3903b3a(boolean hasFocus) {
            this.mTitleView.animate().alpha(hasFocus ? 1.0f : 0.0f).setDuration((long) FavoriteLaunchItemsAdapter.this.mFocusHandlerTemplate.getAnimationDuration()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    BaseViewHolder.this.mTitleView.setVisibility(0);
                }

                public void onAnimationEnd(Animator animation) {
                    if (BaseViewHolder.this.mTitleView.getAlpha() == 0.0f) {
                        BaseViewHolder.this.mTitleView.setVisibility(4);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void updateSize() {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.itemView.getLayoutParams();
            switch (FavoriteLaunchItemsAdapter.this.mAppState) {
                case 0:
                case 2:
                    lp.setMargins(0, FavoriteLaunchItemsAdapter.this.mDefaultTopMargin, 0, FavoriteLaunchItemsAdapter.this.mDefaultBottomMargin);
                    lp.setMarginEnd(FavoriteLaunchItemsAdapter.this.mDefaultHorizontalMargin);
                    break;
                case 1:
                    lp.setMargins(0, FavoriteLaunchItemsAdapter.this.mDefaultTopMargin, 0, FavoriteLaunchItemsAdapter.this.mDefaultAboveSelectedBottomMargin);
                    lp.setMarginEnd(FavoriteLaunchItemsAdapter.this.mDefaultHorizontalMargin);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    lp.setMargins(0, FavoriteLaunchItemsAdapter.this.mZoomedOutTopMargin, 0, FavoriteLaunchItemsAdapter.this.mZoomedOutBottomMargin);
                    lp.setMarginEnd(FavoriteLaunchItemsAdapter.this.mZoomedOutHorizontalMargin);
                    break;
            }
            this.itemView.setLayoutParams(lp);
        }

        public void setItem(LaunchItem item) {
        }

        /* access modifiers changed from: package-private */
        public void updateFocusedState() {
            int pivot;
            int pivotX;
            boolean selected = this.itemView.isFocused();
            float scale = selected ? FavoriteLaunchItemsAdapter.this.mBannerFocusedScale : 1.0f;
            float elevation = selected ? FavoriteLaunchItemsAdapter.this.mBannerFocusedElevation : 0.0f;
            this.itemView.setScaleX(scale);
            this.itemView.setScaleY(scale);
            this.itemView.setElevation(elevation);
            this.mTitleView.setSelected(selected);
            int width = this.itemView.getLayoutParams().width;
            int height = this.itemView.getLayoutParams().height;
            if (width <= 0 || height <= 0) {
                width = this.itemView.getWidth();
                height = this.itemView.getHeight();
            }
            if (width > 0 && height > 0) {
                if (getAdapterPosition() == 0) {
                    pivot = 1;
                } else {
                    pivot = 0;
                }
                if (pivot == 0) {
                    pivotX = width / 2;
                } else if (this.itemView.getLayoutDirection() == 1) {
                    pivotX = width;
                } else {
                    pivotX = 0;
                }
                this.itemView.setPivotX((float) pivotX);
                this.itemView.setPivotY((float) ((height / 2) + this.mPivotVerticalShift));
            }
        }

        /* access modifiers changed from: package-private */
        public void updateBannerDimmingFactor() {
            FavoriteLaunchItemView favoriteLaunchItemView = (FavoriteLaunchItemView) this.itemView;
            boolean z = true;
            if (!(FavoriteLaunchItemsAdapter.this.mAppState == 0 || FavoriteLaunchItemsAdapter.this.mAppState == 1 || FavoriteLaunchItemsAdapter.this.mAppState == 3 || FavoriteLaunchItemsAdapter.this.mAppState == 5 || FavoriteLaunchItemsAdapter.this.mAppState == 6 || FavoriteLaunchItemsAdapter.this.mAppState == 7)) {
                z = false;
            }
            favoriteLaunchItemView.setBannerImageDimmed(z);
        }
    }
}
