package com.google.android.tvlauncher.appsview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.widget.FacetProvider;
import androidx.leanback.widget.HorizontalGridView;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.data.LaunchItemImageDataSource;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import com.google.android.tvlauncher.settings.ProfilesManager;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;
import com.google.android.tvlauncher.util.KeylineUtil;
import com.google.android.tvlauncher.util.OemAppPromotions;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.OemPromotionApp;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.AppUtil;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class RowListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements LaunchItemsManager.AppsViewChangeListener, OemAppPromotions.OnAppPromotionsLoadedListener {
    static final int ROW_TYPE_STORE = 3;
    static final int ROW_TYPE_TITLE = 5;
    private static final int ROW_TYPE_APPS = 1;
    private static final int ROW_TYPE_GAMES = 2;
    private static final int ROW_TYPE_PROMOTIONS = 4;
    private static final String TAG = "RowListAdapter";
    /* access modifiers changed from: private */
    public final LaunchItemsHolder appLaunchItems = new LaunchItemsHolder();
    /* access modifiers changed from: private */
    public final String appStoreTitle;
    /* access modifiers changed from: private */
    public final int bannerHeight;
    /* access modifiers changed from: private */
    public final int bannerMarginEnd;
    /* access modifiers changed from: private */
    public final int bannerWidth;
    /* access modifiers changed from: private */
    public final Drawable defaultAppStoreIcon;
    /* access modifiers changed from: private */
    public final Drawable defaultGameStoreIcon;
    /* access modifiers changed from: private */
    public final EventLogger eventLogger;
    /* access modifiers changed from: private */
    public final LaunchItemsHolder gameLaunchItems = new LaunchItemsHolder();
    /* access modifiers changed from: private */
    public final String gameStoreTitle;
    /* access modifiers changed from: private */
    public final LaunchItemsManager launchItemsManager;
    /* access modifiers changed from: private */
    public final Drawable placeholderBanner;
    /* access modifiers changed from: private */
    public final ArrayList<Integer> rows = new ArrayList<>();
    /* access modifiers changed from: private */
    public final int storeKeylineOffset;
    private final Handler changeHandler = new Handler();
    private final List<Integer> enabledRowOrder = new ArrayList(3);
    private final Set<Integer> enabledRowTypes = new HashSet(3);
    private final int keylineLastRow;
    private final int keylineRowOne;
    private final int keylineRowOneTitleAbove;
    private final int keylineRowThree;
    private final int keylineRowTwo;
    private final int keylineRowTwoTitleAbove;
    private final ArrayList<LaunchItem> storeLaunchItems = new ArrayList<>();
    /* access modifiers changed from: private */
    public OnAppsViewActionListener onAppsViewActionListener;
    /* access modifiers changed from: private */
    public List<OemPromotionApp> promotions = new ArrayList();
    private AppsViewFragment.OnEditModeOrderChangeCallback onEditModeOrderChangeCallback;
    private boolean resetPositions;

    RowListAdapter(Context context, EventLogger eventLogger2, LaunchItemsManager manager) {
        this.eventLogger = eventLogger2;
        Resources res = context.getResources();
        this.keylineRowOne = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_keyline_app_row_one);
        this.keylineRowOneTitleAbove = res.getDimensionPixelSize(C1188R.dimen.app_view_grid_keyline_app_row_one_title_above);
        this.keylineRowTwo = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_keyline_app_row_two);
        this.keylineRowTwoTitleAbove = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_keyline_app_row_two_title_above);
        this.keylineRowThree = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_keyline_app_row_three);
        this.keylineLastRow = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_keyline_last_row);
        this.storeKeylineOffset = res.getDimensionPixelOffset(C1188R.dimen.app_view_grid_store_offset);
        this.bannerMarginEnd = res.getDimensionPixelSize(C1188R.dimen.app_banner_margin_end);
        this.appStoreTitle = res.getString(C1188R.string.store_find_more_apps);
        this.gameStoreTitle = res.getString(C1188R.string.store_find_more_games);
        this.placeholderBanner = new ColorDrawable(ContextCompat.getColor(context, C1188R.color.app_banner_background_color));
        this.bannerWidth = res.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_width);
        this.bannerHeight = res.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_height);
        this.defaultAppStoreIcon = context.getDrawable(C1188R.C1189drawable.product_logo_play_prism_color_36);
        this.defaultGameStoreIcon = context.getDrawable(C1188R.C1189drawable.product_logo_play_games_color_36);
        this.launchItemsManager = manager;
        setEnabledRows(OemConfiguration.get(context).getAppsViewLayoutOption());
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        onBindViewHolder((BaseViewHolder) viewHolder, i, (List<Object>) list);
    }

    public void onAppPromotionsLoaded(List<OemPromotionApp> promotions2) {
        this.promotions = promotions2;
        boolean rowRemovedOrAdded = false;
        if (!this.promotions.isEmpty()) {
            int promoIndex = this.rows.indexOf(4);
            if (promoIndex == -1) {
                int insertIndex = getRowIndexForRowType(4);
                this.rows.add(insertIndex, 5);
                notifyItemInserted(insertIndex);
                this.rows.add(insertIndex + 1, 4);
                notifyItemInserted(insertIndex + 1);
                rowRemovedOrAdded = true;
            } else if (promoIndex != -1) {
                notifyItemChanged(promoIndex);
            }
        } else {
            int index = this.rows.indexOf(4);
            if (index != -1) {
                this.rows.remove(index);
                notifyItemRemoved(index);
                this.rows.remove(index - 1);
                notifyItemRemoved(index - 1);
                rowRemovedOrAdded = true;
            }
        }
        if (rowRemovedOrAdded) {
            notifyDataSetChanged();
        }
    }

    public void onLaunchItemsLoaded() {
        setDataInRows();
        initRows();
        int appCount = this.appLaunchItems.size();
        int gameCount = this.gameLaunchItems.size();
        LogEvent event = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_APPS_VIEW, LogEventParameters.APP_COUNT);
        event.getLaunchItemCollection().setCount(appCount + gameCount).setGameCount(gameCount);
        this.eventLogger.log(event);
    }

    public void onLaunchItemsAddedOrUpdated(final ArrayList<LaunchItem> addedOrUpdatedItems) {
        this.changeHandler.post(new Runnable() {
            public void run() {
                boolean saveOrder = false;
                Iterator it = addedOrUpdatedItems.iterator();
                while (true) {
                    boolean z = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    LaunchItem newItem = (LaunchItem) it.next();
                    saveOrder = updateItemHolders(RowListAdapter.this.appLaunchItems, newItem, 1) || saveOrder;
                    if (RowListAdapter.this.isRowTypeEnabled(2)) {
                        if (updateItemHolders(RowListAdapter.this.gameLaunchItems, newItem, 2) || saveOrder) {
                            z = true;
                        }
                        saveOrder = z;
                    }
                }
                if (saveOrder && !addedOrUpdatedItems.isEmpty()) {
                    if (!((LaunchItem) addedOrUpdatedItems.get(0)).isGame() || !RowListAdapter.this.isRowTypeEnabled(2)) {
                        RowListAdapter.this.launchItemsManager.saveOrderSnapshot(RowListAdapter.this.appLaunchItems.getData());
                    } else {
                        RowListAdapter.this.launchItemsManager.saveOrderSnapshot(RowListAdapter.this.gameLaunchItems.getData());
                    }
                }
            }

            private boolean updateItemHolders(LaunchItemsHolder holder, LaunchItem newItem, int rowType) {
                Pair<Integer, Integer> index = holder.findIndex(newItem);
                boolean bannerAddedOrRemoved = false;
                boolean z = false;
                boolean rowTypeMatch = (newItem.isGame() && RowListAdapter.this.isRowTypeEnabled(2)) == (rowType == 2);
                if (index != null) {
                    if (!rowTypeMatch) {
                        int numberOfRowsBefore = holder.getNumRows();
                        if (holder.removeItem(newItem) != null) {
                            z = true;
                        }
                        bannerAddedOrRemoved = z;
                        int startRow = RowListAdapter.this.rows.indexOf(Integer.valueOf(rowType)) + ((Integer) index.first).intValue();
                        int endRow = RowListAdapter.this.rows.lastIndexOf(Integer.valueOf(rowType));
                        if (holder.getNumRows() < numberOfRowsBefore) {
                            RowListAdapter.this.notifyItemRangeChanged(startRow, endRow - startRow);
                            RowListAdapter.this.rows.remove(endRow);
                            RowListAdapter.this.notifyItemRemoved(endRow);
                        } else {
                            RowListAdapter.this.notifyItemRangeChanged(startRow, (endRow - startRow) + 1);
                        }
                    } else {
                        holder.set(index, newItem);
                        RowListAdapter rowListAdapter = RowListAdapter.this;
                        rowListAdapter.notifyItemChanged(rowListAdapter.rows.indexOf(Integer.valueOf(rowType)) + ((Integer) index.first).intValue(), index.second);
                    }
                } else if (rowTypeMatch) {
                    int insertedAtRow = ((Integer) holder.addItemAtIndexElseEnd(RowListAdapter.this.getOrderedPosition(newItem), newItem).first).intValue();
                    bannerAddedOrRemoved = true;
                    int startRow2 = RowListAdapter.this.rows.indexOf(Integer.valueOf(rowType));
                    int endRow2 = RowListAdapter.this.rows.lastIndexOf(Integer.valueOf(rowType));
                    int insertedAtRow2 = insertedAtRow + startRow2;
                    if (startRow2 == -1) {
                        int titleInsertPosition = RowListAdapter.this.getRowIndexForRowType(rowType);
                        RowListAdapter.this.rows.add(titleInsertPosition, 5);
                        RowListAdapter.this.notifyItemInserted(titleInsertPosition);
                        int startRow3 = titleInsertPosition + 1;
                        RowListAdapter.this.rows.add(startRow3, Integer.valueOf(rowType));
                        RowListAdapter.this.notifyItemInserted(startRow3 - 1);
                        return true;
                    }
                    if ((endRow2 - startRow2) + 1 < holder.getNumRows()) {
                        RowListAdapter.this.rows.add(endRow2 + 1, Integer.valueOf(rowType));
                        endRow2++;
                        RowListAdapter.this.notifyItemInserted(endRow2);
                    }
                    if (insertedAtRow2 != endRow2) {
                        RowListAdapter.this.notifyItemRangeChanged(insertedAtRow2, (endRow2 - insertedAtRow2) + 1);
                    } else {
                        RowListAdapter.this.notifyItemChanged(endRow2);
                    }
                }
                return bannerAddedOrRemoved;
            }
        });
    }

    public void onLaunchItemsRemoved(final ArrayList<LaunchItem> removedItems) {
        this.changeHandler.post(new Runnable() {
            public void run() {
                Iterator it = removedItems.iterator();
                while (it.hasNext()) {
                    LaunchItem removedItem = (LaunchItem) it.next();
                    if (removedItem.isGame() && RowListAdapter.this.isRowTypeEnabled(2)) {
                        removeItemFromHolderIfExists(RowListAdapter.this.gameLaunchItems, removedItem, 2);
                    }
                    removeItemFromHolderIfExists(RowListAdapter.this.appLaunchItems, removedItem, 1);
                }
            }

            private void removeItemFromHolderIfExists(LaunchItemsHolder holder, LaunchItem item, int rowType) {
                int numberOfRowsBefore = holder.getNumRows();
                Pair<Integer, Integer> removedIndex = holder.removeItem(item);
                if (removedIndex != null) {
                    int startRow = RowListAdapter.this.rows.indexOf(Integer.valueOf(rowType)) + ((Integer) removedIndex.first).intValue();
                    int endRow = RowListAdapter.this.rows.lastIndexOf(Integer.valueOf(rowType));
                    if (numberOfRowsBefore > holder.getNumRows()) {
                        RowListAdapter.this.rows.remove(endRow);
                        RowListAdapter.this.notifyItemRemoved(endRow);
                        if (endRow > 0 && ((Integer) RowListAdapter.this.rows.get(endRow - 1)).intValue() == 5) {
                            RowListAdapter.this.rows.remove(endRow - 1);
                            RowListAdapter.this.notifyItemRemoved(endRow - 1);
                        }
                        RowListAdapter.this.notifyItemRangeChanged(startRow, endRow - startRow);
                        return;
                    }
                    RowListAdapter.this.notifyItemRangeChanged(startRow, (endRow - startRow) + 1);
                }
            }
        });
    }

    public void onEditModeItemOrderChange(ArrayList<LaunchItem> newOrderItems, boolean isGameItems, Pair<Integer, Integer> focusedIndex) {
        if (newOrderItems != null) {
            setDataInRows();
            initRows();
        }
        ArrayList<Integer> arrayList = this.rows;
        int i = 2;
        if (!isGameItems || !isRowTypeEnabled(2)) {
            i = 1;
        }
        int startIndex = arrayList.indexOf(Integer.valueOf(i));
        if (focusedIndex != null && startIndex != -1 && this.onEditModeOrderChangeCallback != null) {
            this.onEditModeOrderChangeCallback.onEditModeExited(((Integer) focusedIndex.first).intValue() + startIndex, ((Integer) focusedIndex.second).intValue());
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1 || viewType == 2) {
            return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.apps_view_base_row_view, parent, false), this.eventLogger);
        }
        if (viewType == 3) {
            return new StoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.apps_view_store_row_view, parent, false), this.eventLogger);
        }
        if (viewType == 4) {
            return new PromotionRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.apps_promotion_row, parent, false), this.eventLogger);
        }
        if (viewType == 5) {
            return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.title_row_view, parent, false), this.eventLogger);
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Unexpected row type : ");
        sb.append(viewType);
        throw new IllegalArgumentException(sb.toString());
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof StoreViewHolder) {
            ((StoreViewHolder) holder).addStoreItems(this.storeLaunchItems);
        }
        if (holder instanceof AppViewHolder) {
            addLaunchItemsToViewHolder(holder, position);
        }
        if (holder instanceof TitleViewHolder) {
            int titleContentPosition = position + 1;
            if (titleContentPosition >= this.rows.size()) {
                ((TitleViewHolder) holder).setTitle("");
            } else {
                Resources res = holder.itemView.getContext().getResources();
                int intValue = this.rows.get(titleContentPosition).intValue();
                if (intValue == 1) {
                    ((TitleViewHolder) holder).setTitle(res.getString(C1188R.string.app_folder_title));
                } else if (intValue == 2) {
                    ((TitleViewHolder) holder).setTitle(res.getString(C1188R.string.game_folder_title));
                } else if (intValue != 4) {
                    ((TitleViewHolder) holder).setTitle("");
                } else {
                    ((TitleViewHolder) holder).setTitle(OemAppPromotions.get(holder.itemView.getContext()).getAppsPromotionRowTitle());
                    ((TitleViewHolder) holder).setBackgroundColor(res.getColor(C1188R.color.reference_white_10, null));
                }
            }
        }
        holder.set();
        if (this.resetPositions) {
            holder.resetPositions();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: android.support.v7.widget.RecyclerView.Adapter.onBindViewHolder(android.support.v7.widget.RecyclerView$ViewHolder, int, java.util.List<java.lang.Object>):void
     arg types: [com.google.android.tvlauncher.appsview.RowListAdapter$BaseViewHolder, int, java.util.List<java.lang.Object>]
     candidates:
      com.google.android.tvlauncher.appsview.RowListAdapter.onBindViewHolder(com.google.android.tvlauncher.appsview.RowListAdapter$BaseViewHolder, int, java.util.List<java.lang.Object>):void
      android.support.v7.widget.RecyclerView.Adapter.onBindViewHolder(android.support.v7.widget.RecyclerView$ViewHolder, int, java.util.List<java.lang.Object>):void */
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!(holder instanceof AppViewHolder) || payloads.isEmpty() || !(payloads.get(0) instanceof Integer)) {
            super.onBindViewHolder((RecyclerView.ViewHolder) holder, position, payloads);
        } else {
            ((AppViewHolder) holder).setItemAt(((Integer) payloads.get(0)).intValue());
        }
    }

    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled((RecyclerView.ViewHolder) holder);
        holder.recycle();
    }

    private void addLaunchItemsToViewHolder(BaseViewHolder holder, int position) {
        List<LaunchItem> items;
        int rowType = this.rows.get(position).intValue();
        int titleRelativePosition = getPositionRelativeToTitle(position);
        if (titleRelativePosition < 0 || titleRelativePosition >= this.rows.size()) {
            StringBuilder sb = new StringBuilder(103);
            sb.append("RowListAdapter: Title relative position was out of bounds : ");
            sb.append(titleRelativePosition);
            sb.append(", in addLaunchItemToViewHolder()");
            Log.e(TAG, sb.toString());
            return;
        }
        if (rowType == 1) {
            items = this.appLaunchItems.getRowData(titleRelativePosition);
        } else if (rowType == 2) {
            items = this.gameLaunchItems.getRowData(titleRelativePosition);
        } else {
            return;
        }
        if (holder instanceof AppViewHolder) {
            ((AppViewHolder) holder).addAllLaunchItems(items);
        }
    }

    private int getPositionRelativeToTitle(int position) {
        for (int i = position; i >= 0; i--) {
            if (this.rows.get(i).intValue() == 5) {
                return (position - i) - 1;
            }
        }
        return -1;
    }

    public int getItemCount() {
        return this.rows.size();
    }

    public int getItemViewType(int position) {
        return this.rows.get(position).intValue();
    }

    /* access modifiers changed from: package-private */
    public void setDataInRows() {
        this.appLaunchItems.clear();
        this.gameLaunchItems.clear();
        this.storeLaunchItems.clear();
        if (isRowTypeEnabled(2)) {
            this.appLaunchItems.setData(this.launchItemsManager.getAppLaunchItems());
            this.gameLaunchItems.setData(this.launchItemsManager.getGameLaunchItems());
        } else {
            this.appLaunchItems.setData(this.launchItemsManager.getAllLaunchItemsWithSorting());
        }
        this.storeLaunchItems.add(this.launchItemsManager.getAppStoreLaunchItem());
        this.storeLaunchItems.add(this.launchItemsManager.getGameStoreLaunchItem());
        this.storeLaunchItems.removeAll(Collections.singleton(null));
    }

    /* access modifiers changed from: package-private */
    public void setResetViewHolderPositions(boolean resetPositions2) {
        this.resetPositions = resetPositions2;
    }

    /* access modifiers changed from: package-private */
    public void initRows() {
        if (this.launchItemsManager.areItemsLoaded()) {
            this.rows.clear();
            if (!this.storeLaunchItems.isEmpty()) {
                this.rows.add(3);
            }
            for (Integer intValue : this.enabledRowOrder) {
                int rowType = intValue.intValue();
                if (rowType != 1) {
                    if (rowType != 2) {
                        if (rowType == 4 && !this.promotions.isEmpty() && isRowTypeEnabled(4)) {
                            this.rows.add(5);
                            this.rows.add(4);
                        }
                    } else if (this.gameLaunchItems.size() > 0) {
                        this.rows.add(5);
                        for (int i = 0; i < this.gameLaunchItems.getNumRows(); i++) {
                            this.rows.add(2);
                        }
                    }
                } else if (this.appLaunchItems.size() > 0) {
                    this.rows.add(5);
                    for (int i2 = 0; i2 < this.appLaunchItems.getNumRows(); i2++) {
                        this.rows.add(1);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnAppsViewActionListener(OnAppsViewActionListener listener) {
        this.onAppsViewActionListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnEditModeOrderChangeCallback(AppsViewFragment.OnEditModeOrderChangeCallback callback) {
        this.onEditModeOrderChangeCallback = callback;
    }

    /* access modifiers changed from: package-private */
    public int getTopKeylineForEditMode(int editModeType) {
        if (editModeType != 1 || !isRowTypeEnabled(2)) {
            return getKeylineForPosition(this.rows.indexOf(1));
        }
        return getKeylineForPosition(this.rows.indexOf(2));
    }

    /* access modifiers changed from: package-private */
    public int getBottomKeylineForEditMode(int editModeType) {
        if (editModeType != 1 || !isRowTypeEnabled(2)) {
            return getKeylineForPosition(this.rows.lastIndexOf(1));
        }
        return getKeylineForPosition(this.rows.lastIndexOf(2));
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<OemPromotionApp> getPromotions() {
        return this.promotions;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<LaunchItem> getStoreItems() {
        return this.storeLaunchItems;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setEnabledRows(OemConfiguration.LayoutOrderOptions options) {
        this.enabledRowOrder.clear();
        this.enabledRowTypes.clear();
        this.enabledRowTypes.add(1);
        if (options == null) {
            options = OemConfiguration.LayoutOrderOptions.APPS_OEM_GAMES;
        }
        int i = C12283.f133x70c2e182[options.ordinal()];
        if (i == 1) {
            this.enabledRowTypes.add(4);
            this.enabledRowTypes.add(2);
            this.enabledRowOrder.addAll(Arrays.asList(1, 4, 2));
        } else if (i == 2) {
            this.enabledRowTypes.add(4);
            this.enabledRowTypes.add(2);
            this.enabledRowOrder.addAll(Arrays.asList(1, 2, 4));
        } else if (i == 3) {
            this.enabledRowTypes.add(4);
            this.enabledRowTypes.add(2);
            this.enabledRowOrder.addAll(Arrays.asList(2, 1, 4));
        } else if (i == 4) {
            this.enabledRowTypes.add(4);
            this.enabledRowOrder.addAll(Arrays.asList(1, 4));
        }
    }

    /* access modifiers changed from: private */
    public int getKeylineForPosition(int position) {
        if (position < 0 || position > this.rows.size() - 1) {
            return this.keylineRowOne;
        }
        int itemCount = getItemCount();
        int nonTitleNonStoreRowsCount = getNonTitleNonStoreRowsCount();
        if (nonTitleNonStoreRowsCount == 1) {
            return this.keylineRowOne;
        }
        if (nonTitleNonStoreRowsCount != 2) {
            if (nonTitleNonStoreRowsCount != 3) {
                if (position == itemCount - 1) {
                    return this.keylineLastRow;
                }
                if (position == itemCount - 2) {
                    return this.keylineRowTwoTitleAbove;
                }
                if (position == itemCount - 3 && this.rows.get(position + 1).intValue() == 5) {
                    return this.keylineRowTwo;
                }
                if (position == itemCount - 3) {
                    return this.keylineRowOneTitleAbove;
                }
            } else if (position == itemCount - 1 && (this.rows.get(position - 1).intValue() == 5 || this.rows.get(position - 2).intValue() == 5)) {
                return this.keylineLastRow;
            } else {
                if (position == itemCount - 1) {
                    return this.keylineRowThree;
                }
                if (position == itemCount - 2 && this.rows.get(position - 1).intValue() == 5) {
                    return this.keylineRowTwoTitleAbove;
                }
                if (position == itemCount - 2) {
                    return this.keylineRowTwo;
                }
                if (position == itemCount - 3 && this.rows.get(position + 1).intValue() == 5) {
                    return this.keylineRowTwo;
                }
            }
        } else if (position == itemCount - 1 && this.rows.get(position - 1).intValue() == 5) {
            return this.keylineRowTwoTitleAbove;
        } else {
            if (position == itemCount - 1) {
                return this.keylineRowTwo;
            }
        }
        return this.keylineRowOne;
    }

    private int getNonTitleNonStoreRowsCount() {
        return this.appLaunchItems.getNumRows() + this.gameLaunchItems.getNumRows() + (this.promotions.isEmpty() ^ true ? 1 : 0);
    }

    /* access modifiers changed from: private */
    public boolean isRowTypeEnabled(int rowType) {
        return this.enabledRowTypes.contains(Integer.valueOf(rowType));
    }

    /* access modifiers changed from: private */
    public int getRowIndexForRowType(int rowType) {
        int orderIndex = this.enabledRowOrder.indexOf(Integer.valueOf(rowType));
        for (int i = orderIndex - 1; i >= 0; i--) {
            int lastIndexOfTypeBefore = this.rows.lastIndexOf(Integer.valueOf(this.enabledRowOrder.get(i).intValue()));
            if (lastIndexOfTypeBefore != -1) {
                return lastIndexOfTypeBefore + 1;
            }
        }
        for (int i2 = orderIndex + 1; i2 < this.enabledRowOrder.size(); i2++) {
            int firstIndexOfTypeAfter = this.rows.indexOf(Integer.valueOf(this.enabledRowOrder.get(i2).intValue()));
            if (firstIndexOfTypeAfter != -1) {
                return firstIndexOfTypeAfter - 1;
            }
        }
        return this.rows.size();
    }

    /* access modifiers changed from: private */
    public int getOrderedPosition(LaunchItem item) {
        List<LaunchItem> launchItemList;
        if (!isRowTypeEnabled(2)) {
            launchItemList = this.launchItemsManager.getAllLaunchItemsWithSorting();
        } else if (item.isGame()) {
            launchItemList = this.launchItemsManager.getGameLaunchItems();
        } else {
            launchItemList = this.launchItemsManager.getAppLaunchItems();
        }
        return launchItemList.indexOf(item);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder implements FacetProvider, EventLogger {
        private final EventLogger eventLogger;

        BaseViewHolder(View itemView, EventLogger eventLogger2) {
            super(itemView);
            this.eventLogger = eventLogger2;
        }

        public void set() {
        }

        public void resetPositions() {
        }

        public Object getFacet(Class<?> cls) {
            if (getAdapterPosition() == -1) {
                return null;
            }
            return KeylineUtil.createItemAlignmentFacet(-calculateOffset());
        }

        /* access modifiers changed from: protected */
        public int calculateOffset() {
            return 0;
        }

        public void log(LogEvent event) {
            this.eventLogger.log(event);
        }

        public void recycle() {
        }
    }

    private static class TitleViewHolder extends BaseViewHolder {
        @ColorInt
        private int backgroundColor;
        private String title;

        TitleViewHolder(View itemView, EventLogger eventLogger) {
            super(itemView, eventLogger);
        }

        public void setTitle(String title2) {
            this.title = title2;
        }

        public void setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
        }

        public void set() {
            ((TextView) this.itemView).setText(this.title);
            this.itemView.setBackgroundColor(this.backgroundColor);
        }

        public void recycle() {
            super.recycle();
            this.title = "";
            this.backgroundColor = 0;
        }
    }

    /* renamed from: com.google.android.tvlauncher.appsview.RowListAdapter$3 */
    static /* synthetic */ class C12283 {

        /* renamed from: $SwitchMap$com$google$android$tvlauncher$util$OemConfiguration$LayoutOrderOptions */
        static final /* synthetic */ int[] f133x70c2e182 = new int[OemConfiguration.LayoutOrderOptions.values().length];

        static {
            try {
                f133x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_OEM_GAMES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f133x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_GAMES_OEM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f133x70c2e182[OemConfiguration.LayoutOrderOptions.GAMES_APPS_OEM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f133x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_OEM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private class AppViewHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnFocusChangeListener, BannerView.OnWindowVisibilityChangedListener, EventLogger {
        private static final int MENU_FAVORITE = 2;
        private static final int MENU_INFO = 3;
        private static final int MENU_MOVE = 1;
        private static final int MENU_PRIMARY_ACTION = 0;
        private static final int MENU_UNINSTALL = 4;
        private final Drawable favoriteIcon;
        private final String favoriteText;
        private final ArrayList<LaunchItem> launchItems = new ArrayList<>();
        private final LaunchItemsRowView rowView;
        private final Drawable unfavoriteIcon;
        private final String unfavoriteText;
        private ContextMenu contextMenu;

        AppViewHolder(View itemView, EventLogger eventLogger) {
            super(itemView, eventLogger);
            this.rowView = (LaunchItemsRowView) itemView;
            Context context = itemView.getContext();
            this.favoriteText = context.getString(C1188R.string.context_menu_favorite_text);
            this.unfavoriteText = context.getString(C1188R.string.context_menu_unfavorite_text);
            this.favoriteIcon = context.getDrawable(C1188R.C1189drawable.ic_context_menu_favorite_black);
            this.unfavoriteIcon = context.getDrawable(C1188R.C1189drawable.ic_context_menu_unfavorite_black);
        }

        /* access modifiers changed from: package-private */
        public void addAllLaunchItems(List<LaunchItem> items) {
            this.launchItems.clear();
            this.launchItems.addAll(items);
        }

        /* JADX WARN: Type inference failed for: r4v4, types: [android.view.View] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void set() {
            /*
                r8 = this;
                super.set()
                r0 = 0
            L_0x0004:
                r1 = 4
                if (r0 >= r1) goto L_0x0077
                java.util.ArrayList<com.google.android.tvlauncher.appsview.LaunchItem> r1 = r8.launchItems
                int r1 = r1.size()
                r2 = 8
                if (r0 >= r1) goto L_0x0053
                java.util.ArrayList<com.google.android.tvlauncher.appsview.LaunchItem> r1 = r8.launchItems
                java.lang.Object r1 = r1.get(r0)
                com.google.android.tvlauncher.appsview.LaunchItem r1 = (com.google.android.tvlauncher.appsview.LaunchItem) r1
                r3 = 0
                com.google.android.tvlauncher.appsview.LaunchItemsRowView r4 = r8.rowView
                int r4 = r4.getChildCount()
                if (r0 >= r4) goto L_0x0044
                com.google.android.tvlauncher.appsview.LaunchItemsRowView r4 = r8.rowView
                android.view.View r4 = r4.getChildAt(r0)
                boolean r5 = r4 instanceof com.google.android.tvlauncher.appsview.BannerView
                if (r5 == 0) goto L_0x0043
                r3 = r4
                com.google.android.tvlauncher.appsview.BannerView r3 = (com.google.android.tvlauncher.appsview.BannerView) r3
                android.widget.ImageView r5 = r3.getBannerImage()
                int r6 = r5.getVisibility()
                r7 = 0
                if (r6 != r2) goto L_0x003d
                r5.setVisibility(r7)
            L_0x003d:
                r3.setLaunchItem(r1)
                r3.setVisibility(r7)
            L_0x0043:
                goto L_0x004d
            L_0x0044:
                com.google.android.tvlauncher.appsview.BannerView r3 = r8.createBannerView(r1)
                com.google.android.tvlauncher.appsview.LaunchItemsRowView r2 = r8.rowView
                r2.addView(r3)
            L_0x004d:
                if (r3 == 0) goto L_0x0073
                r8.loadImage(r1, r3)
                goto L_0x0073
            L_0x0053:
                com.google.android.tvlauncher.appsview.LaunchItemsRowView r1 = r8.rowView
                int r1 = r1.getChildCount()
                if (r0 >= r1) goto L_0x0073
                com.google.android.tvlauncher.appsview.LaunchItemsRowView r1 = r8.rowView
                android.view.View r1 = r1.getChildAt(r0)
                boolean r3 = r1 instanceof com.google.android.tvlauncher.appsview.BannerView
                if (r3 == 0) goto L_0x0074
                r3 = r1
                com.google.android.tvlauncher.appsview.BannerView r3 = (com.google.android.tvlauncher.appsview.BannerView) r3
                r3.setVisibility(r2)
                android.widget.ImageView r4 = r3.getBannerImage()
                r4.setVisibility(r2)
                goto L_0x0074
            L_0x0073:
            L_0x0074:
                int r0 = r0 + 1
                goto L_0x0004
            L_0x0077:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.appsview.RowListAdapter.AppViewHolder.set():void");
        }

        private void loadImage(LaunchItem item, BannerView bannerView) {
            new LaunchItemImageLoader(bannerView.getContext()).setLaunchItemImageDataSource(new LaunchItemImageDataSource(item, PackageImageDataSource.ImageType.BANNER, RowListAdapter.this.launchItemsManager.getCurrentLocale())).setTargetImageView(bannerView.getBannerImage()).setPlaceholder(RowListAdapter.this.placeholderBanner).setWidth(RowListAdapter.this.bannerWidth).setHeight(RowListAdapter.this.bannerHeight).loadLaunchItemImage();
        }

        /* access modifiers changed from: protected */
        public int calculateOffset() {
            return RowListAdapter.this.getKeylineForPosition(getAdapterPosition());
        }

        /* access modifiers changed from: package-private */
        public void setItemAt(int position) {
            LaunchItemsRowView rowView2 = (LaunchItemsRowView) this.itemView;
            if (position < rowView2.getChildCount()) {
                BannerView bannerView = (BannerView) rowView2.getChildAt(position);
                bannerView.setLaunchItem(this.launchItems.get(position));
                loadImage(this.launchItems.get(position), bannerView);
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, com.google.android.tvlauncher.appsview.LaunchItemsRowView, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        private BannerView createBannerView(LaunchItem item) {
            BannerView bannerView = (BannerView) LayoutInflater.from(this.rowView.getContext()).inflate(C1188R.layout.view_app_banner, (ViewGroup) this.rowView, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bannerView.getLayoutParams());
            params.setMarginEnd(RowListAdapter.this.bannerMarginEnd);
            bannerView.setLayoutParams(params);
            bannerView.setLaunchItem(item);
            bannerView.setOnClickListener(this);
            bannerView.setOnLongClickListener(this);
            bannerView.setOnFocusChangeListener(this);
            bannerView.setOnWindowVisibilityChangedListener(this);
            return bannerView;
        }

        public void onClick(View v) {
            if (!(v instanceof BannerView)) {
                return;
            }
            if (Util.isAccessibilityEnabled(v.getContext())) {
                showContextMenu((BannerView) v);
            } else {
                onPrimaryAction(((BannerView) v).getItem(), v);
            }
        }

        public boolean onLongClick(View v) {
            showContextMenu((BannerView) v);
            return true;
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (v instanceof BannerView) {
                ContextMenu contextMenu2 = this.contextMenu;
                if (contextMenu2 != null && contextMenu2.isShowing() && !hasFocus) {
                    this.contextMenu.forceDismiss();
                }
                ((BannerView) v).setFocusedState(hasFocus);
            }
        }

        private void showContextMenu(BannerView bannerView) {
            Context context = bannerView.getContext();
            LaunchItem item = bannerView.getItem();
            boolean isFavorite = RowListAdapter.this.launchItemsManager.isFavorite(item);
            this.contextMenu = new ContextMenu((Activity) context, bannerView.getBannerContainer(), bannerView.getCornerRadius(), bannerView.getScaleX(), bannerView.getScaleY());
            ContextMenuItem primaryActionItem = new ContextMenuItem(0, context.getString(C1188R.string.context_menu_primary_action_text), context.getDrawable(C1188R.C1189drawable.ic_context_menu_open_black));
            primaryActionItem.setAutoDismiss(false);
            this.contextMenu.addItem(primaryActionItem);
            boolean z = true;
            this.contextMenu.addItem(new ContextMenuItem(1, context.getString(C1188R.string.context_menu_move_text), context.getDrawable(C1188R.C1189drawable.ic_context_menu_move_black)));
            this.contextMenu.addItem(new ContextMenuItem(2, isFavorite ? this.unfavoriteText : this.favoriteText, isFavorite ? this.unfavoriteIcon : this.favoriteIcon));
            if (!item.isAppLink()) {
                this.contextMenu.addItem(new ContextMenuItem(3, context.getString(C1188R.string.context_menu_info_text), context.getDrawable(C1188R.C1189drawable.ic_context_menu_info_black)));
            }
            this.contextMenu.addItem(new ContextMenuItem(4, context.getString(C1188R.string.context_menu_uninstall_text), context.getDrawable(C1188R.C1189drawable.ic_context_menu_uninstall_black)));
            this.contextMenu.setOnMenuItemClickListener(createOnItemClickListener(item, bannerView));
            this.contextMenu.findItem(1).setEnabled(!(item.isGame() ? RowListAdapter.this.launchItemsManager.isOnlyGame(item) : RowListAdapter.this.launchItemsManager.isOnlyApp(item)));
            boolean isOemHiddenUninstallPackage = false;
            Iterator<String> it = OemConfiguration.get(context).getHiddenUninstallPackageList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().equals(item.getPackageName())) {
                    isOemHiddenUninstallPackage = true;
                    break;
                }
            }
            this.contextMenu.findItem(4).setEnabled(!item.isInstalling() && !AppUtil.isSystemApp(context, item.getPackageName()) && !isOemHiddenUninstallPackage);
            this.contextMenu.findItem(2).setEnabled(!item.isInstalling() && (!RowListAdapter.this.launchItemsManager.isFavoritesFull() || RowListAdapter.this.launchItemsManager.isFavorite(item)));
            if (!item.isAppLink()) {
                ContextMenuItem findItem = this.contextMenu.findItem(3);
                if (item.isInstalling() || ProfilesManager.getInstance(context).isRestrictedProfile()) {
                    z = false;
                }
                findItem.setEnabled(z);
            }
            this.contextMenu.show();
        }

        private ContextMenu.OnItemClickListener createOnItemClickListener(LaunchItem launchItem, View view) {
            return new RowListAdapter$AppViewHolder$$Lambda$0(this, launchItem, view);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$createOnItemClickListener$0$RowListAdapter$AppViewHolder(LaunchItem launchItem, View view, ContextMenuItem item) {
            int id = item.getId();
            if (id == 0) {
                onPrimaryAction(launchItem, view);
            } else if (id == 1) {
                onEnterEditMode(launchItem);
            } else if (id == 2) {
                onFavorite(launchItem);
            } else if (id == 3) {
                onShowInfoView(launchItem);
            } else if (id == 4) {
                onShowUninstall(launchItem);
            }
        }

        private void onPrimaryAction(LaunchItem item, View view) {
            if (item == null || item.getIntent() == null || RowListAdapter.this.onAppsViewActionListener == null) {
                Toast.makeText(this.rowView.getContext(), C1188R.string.failed_launch, 0).show();
                if (item == null) {
                    Log.e(RowListAdapter.TAG, "Cannot start activity: item was null");
                } else if (item.getIntent() == null) {
                    String valueOf = String.valueOf(item);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
                    sb.append("Cannot start activity: intent was null for ");
                    sb.append(valueOf);
                    Log.e(RowListAdapter.TAG, sb.toString());
                } else if (RowListAdapter.this.onAppsViewActionListener == null) {
                    String valueOf2 = String.valueOf(item);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 44);
                    sb2.append("Cannot start activity: no listener for item ");
                    sb2.append(valueOf2);
                    Log.e(RowListAdapter.TAG, sb2.toString());
                }
            } else {
                logUserAction(item, TvlauncherLogEnum.TvLauncherEventCode.START_APP);
                RowListAdapter.this.onAppsViewActionListener.onLaunchApp(item.getIntent(), view);
            }
        }

        private int getItemColumnIndex(LaunchItem item) {
            LaunchItemsRowView rowView2 = (LaunchItemsRowView) this.itemView;
            int childCount = rowView2.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = rowView2.getChildAt(i);
                if ((view instanceof BannerView) && ((BannerView) view).getItem() == item) {
                    return i;
                }
            }
            throw new IllegalStateException("Item not found in LaunchItemsRowView");
        }

        private void onFavorite(LaunchItem item) {
            if (RowListAdapter.this.onAppsViewActionListener != null) {
                if (RowListAdapter.this.launchItemsManager.isFavorite(item)) {
                    logUserAction(item, TvlauncherLogEnum.TvLauncherEventCode.UNFAVORITE_APP);
                } else {
                    logUserAction(item, TvlauncherLogEnum.TvLauncherEventCode.FAVORITE_APP);
                }
                RowListAdapter.this.onAppsViewActionListener.onToggleFavorite(item);
            }
        }

        /* access modifiers changed from: package-private */
        public void onEnterEditMode(LaunchItem item) {
            int i;
            if (item != null && RowListAdapter.this.onAppsViewActionListener != null) {
                boolean gamesRowEnabled = RowListAdapter.this.isRowTypeEnabled(2);
                OnAppsViewActionListener access$1200 = RowListAdapter.this.onAppsViewActionListener;
                if (!item.isGame() || !gamesRowEnabled) {
                    i = 0;
                } else {
                    i = 1;
                }
                access$1200.onShowEditModeView(i, RowListAdapter.this.getOrderedPosition(item));
            }
        }

        private void onShowInfoView(LaunchItem item) {
            if (RowListAdapter.this.onAppsViewActionListener != null) {
                logUserAction(item, TvlauncherLogEnum.TvLauncherEventCode.GET_APP_INFO);
                RowListAdapter.this.onAppsViewActionListener.onShowAppInfo(item.getPackageName());
            }
        }

        private void onShowUninstall(LaunchItem item) {
            if (RowListAdapter.this.onAppsViewActionListener != null) {
                logUserAction(item, TvlauncherLogEnum.TvLauncherEventCode.UNINSTALL_APP);
                RowListAdapter.this.onAppsViewActionListener.onUninstallApp(item.getPackageName());
            }
        }

        public void onWindowVisibilityChanged(int visibility) {
            ContextMenu contextMenu2;
            if ((visibility == 4 || visibility == 8) && (contextMenu2 = this.contextMenu) != null && contextMenu2.isShowing()) {
                this.contextMenu.forceDismiss();
            }
        }

        private void logUserAction(LaunchItem item, TvlauncherLogEnum.TvLauncherEventCode eventCode) {
            LogEvent event = new UserActionEvent(eventCode).setVisualElementTag(TvLauncherConstants.LAUNCH_ITEM).setVisualElementIndex(getItemColumnIndex(item));
            event.getApplication().setPackageName(item.getPackageName()).setIsGame(item.isGame());
            log(event);
        }

        public void log(LogEvent event) {
            int viewType = getItemViewType();
            int indexInSection = getAdapterPosition() - RowListAdapter.this.rows.indexOf(Integer.valueOf(viewType));
            if (viewType == 1) {
                event.pushParentVisualElementTag(TvLauncherConstants.APPS_CONTAINER).setVisualElementRowIndex(indexInSection);
            } else if (viewType == 2) {
                event.pushParentVisualElementTag(TvLauncherConstants.GAMES_CONTAINER).setVisualElementRowIndex(indexInSection);
            }
            RowListAdapter.this.eventLogger.log(event);
        }

        public void recycle() {
            super.recycle();
            this.rowView.recycle();
        }
    }

    private class StoreViewHolder extends BaseViewHolder {
        private final StoreRowButtonView appStore;
        private final StoreRowButtonView gameStore;
        private List<LaunchItem> items;

        StoreViewHolder(View itemView, EventLogger eventLogger) {
            super(itemView, eventLogger);
            this.appStore = (StoreRowButtonView) itemView.findViewById(C1188R.C1191id.app_store);
            this.appStore.setVisualElementTag(TvLauncherConstants.GET_MORE_APPS_BUTTON);
            this.gameStore = (StoreRowButtonView) itemView.findViewById(C1188R.C1191id.game_store);
            this.gameStore.setVisualElementTag(TvLauncherConstants.GET_MORE_GAMES_BUTTON);
        }

        /* access modifiers changed from: package-private */
        public void addStoreItems(List<LaunchItem> items2) {
            this.items = items2;
        }

        public void set() {
            super.set();
            boolean showAppStore = false;
            boolean showGameStore = false;
            for (LaunchItem item : this.items) {
                if (LaunchItemsManager.checkIfAppStore(item.getPackageName())) {
                    this.appStore.setStoreTitle(RowListAdapter.this.appStoreTitle);
                    this.appStore.getStoreIconView().setImageDrawable(RowListAdapter.this.defaultAppStoreIcon);
                    this.appStore.setStoreItem(item, RowListAdapter.this.onAppsViewActionListener);
                    showAppStore = true;
                } else if (LaunchItemsManager.checkIfGameStore(item.getPackageName())) {
                    this.gameStore.setStoreTitle(RowListAdapter.this.gameStoreTitle);
                    this.gameStore.getStoreIconView().setImageDrawable(RowListAdapter.this.defaultGameStoreIcon);
                    this.gameStore.setStoreItem(item, RowListAdapter.this.onAppsViewActionListener);
                    showGameStore = true;
                } else {
                    Log.e(RowListAdapter.TAG, "Trying to add an app to store button that is not a store.");
                }
            }
            int i = 0;
            this.appStore.setVisibility(showAppStore ? 0 : 8);
            StoreRowButtonView storeRowButtonView = this.gameStore;
            if (!showGameStore) {
                i = 8;
            }
            storeRowButtonView.setVisibility(i);
        }

        /* access modifiers changed from: protected */
        public int calculateOffset() {
            return RowListAdapter.this.storeKeylineOffset;
        }
    }

    private class PromotionRowViewHolder extends BaseViewHolder {
        private final PromotionRowAdapter adapter;
        private final HorizontalGridView rowView;

        PromotionRowViewHolder(View itemView, EventLogger eventLogger) {
            super(itemView, eventLogger);
            this.rowView = (HorizontalGridView) itemView;
            this.rowView.setItemSpacing(itemView.getContext().getResources().getDimensionPixelSize(C1188R.dimen.app_banner_margin_end));
            this.adapter = new PromotionRowAdapter(itemView.getContext());
            this.adapter.setHasStableIds(true);
            this.rowView.setAdapter(this.adapter);
            this.adapter.setOnAppsViewActionListener(RowListAdapter.this.onAppsViewActionListener);
        }

        public void set() {
            super.set();
            this.adapter.setPromotions(RowListAdapter.this.promotions);
        }

        public void resetPositions() {
            super.resetPositions();
            if (this.rowView.getChildCount() > 0) {
                this.rowView.setSelectedPosition(0);
            }
        }

        /* access modifiers changed from: protected */
        public int calculateOffset() {
            return RowListAdapter.this.getKeylineForPosition(getAdapterPosition());
        }
    }
}
