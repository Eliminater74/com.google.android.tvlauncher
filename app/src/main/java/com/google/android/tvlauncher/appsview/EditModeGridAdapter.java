package com.google.android.tvlauncher.appsview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.leanback.widget.FacetProvider;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.appsview.data.LaunchItemImageDataSource;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import com.google.android.tvlauncher.util.KeylineUtil;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import java.util.ArrayList;
import java.util.Iterator;

class EditModeGridAdapter extends RecyclerView.Adapter<LaunchItemViewHolder> implements LaunchItemsManager.AppsViewChangeListener {
    /* access modifiers changed from: private */
    public final int mBannerHeight;
    private final int mBannerMarginBottom;
    private final int mBannerMarginEnd;
    /* access modifiers changed from: private */
    public final int mBannerWidth;
    /* access modifiers changed from: private */
    public int mBottomKeyline;
    private final EventLogger mEventLogger;
    private final int mKeylineAppsRowTwo;
    /* access modifiers changed from: private */
    public final int mKeylineAppsRowTwoTitleAbove;
    /* access modifiers changed from: private */
    public final int mKeylineLastRow;
    private final ArrayList<LaunchItem> mLaunchItems = new ArrayList<>();
    private OnEditItemRemovedListener mOnEditItemRemovedListener;
    /* access modifiers changed from: private */
    public OnShowAccessibilityMenuListener mOnShowAccessibilityMenuListener;
    /* access modifiers changed from: private */
    public final Drawable mPlaceholderBanner;
    /* access modifiers changed from: private */
    public int mTopKeyline;

    interface OnEditItemRemovedListener {
        void onEditItemRemoved(int i);
    }

    EditModeGridAdapter(Context context, EventLogger eventLogger) {
        Resources res = context.getResources();
        this.mKeylineAppsRowTwo = res.getDimensionPixelSize(C1188R.dimen.app_view_grid_keyline_app_row_two);
        this.mKeylineAppsRowTwoTitleAbove = res.getDimensionPixelSize(C1188R.dimen.app_view_grid_keyline_app_row_two_title_above);
        this.mKeylineLastRow = res.getDimensionPixelSize(C1188R.dimen.app_view_grid_keyline_last_row);
        this.mBannerMarginEnd = res.getDimensionPixelSize(C1188R.dimen.app_banner_margin_end);
        this.mBannerMarginBottom = res.getDimensionPixelSize(C1188R.dimen.app_row_view_margin_bottom);
        this.mPlaceholderBanner = new ColorDrawable(ContextCompat.getColor(context, C1188R.color.app_banner_background_color));
        this.mBannerWidth = res.getDimensionPixelOffset(C1188R.dimen.app_banner_image_max_width);
        this.mBannerHeight = res.getDimensionPixelOffset(C1188R.dimen.app_banner_image_max_height);
        this.mEventLogger = eventLogger;
    }

    public void onLaunchItemsLoaded() {
    }

    public void onLaunchItemsAddedOrUpdated(ArrayList<LaunchItem> addedOrUpdatedItems) {
        Iterator<LaunchItem> it = addedOrUpdatedItems.iterator();
        while (it.hasNext()) {
            LaunchItem item = it.next();
            int index = this.mLaunchItems.indexOf(item);
            if (index != -1) {
                notifyItemChanged(index);
            } else if (this.mLaunchItems.size() <= 0 || this.mLaunchItems.get(0).isGame() == item.isGame()) {
                this.mLaunchItems.add(item);
                notifyItemInserted(this.mLaunchItems.size() - 1);
            }
        }
    }

    public void onLaunchItemsRemoved(ArrayList<LaunchItem> removedItems) {
        Iterator<LaunchItem> it = removedItems.iterator();
        while (it.hasNext()) {
            int index = this.mLaunchItems.indexOf(it.next());
            if (index != -1) {
                this.mLaunchItems.remove(index);
                this.mOnEditItemRemovedListener.onEditItemRemoved(index);
                notifyItemRemoved(index);
            }
        }
    }

    public void onEditModeItemOrderChange(ArrayList<LaunchItem> arrayList, boolean isGameItems, Pair<Integer, Integer> pair) {
    }

    final class LaunchItemViewHolder extends RecyclerView.ViewHolder implements FacetProvider, View.OnClickListener, View.OnFocusChangeListener {
        private BannerView mBannerView;

        LaunchItemViewHolder(View itemView) {
            super(itemView);
            this.mBannerView = (BannerView) itemView;
            this.mBannerView.setOnClickListener(this);
            this.mBannerView.setOnFocusChangeListener(this);
            this.mBannerView.setSelected(false);
        }

        public void set(LaunchItem launchItem) {
            this.mBannerView.setLaunchItem(launchItem);
            new LaunchItemImageLoader(this.itemView.getContext()).setLaunchItemImageDataSource(new LaunchItemImageDataSource(launchItem, PackageImageDataSource.ImageType.BANNER, LaunchItemsManagerProvider.getInstance(this.itemView.getContext()).getCurrentLocale())).setTargetImageView(this.mBannerView.getBannerImage()).setPlaceholder(EditModeGridAdapter.this.mPlaceholderBanner).setWidth(EditModeGridAdapter.this.mBannerWidth).setHeight(EditModeGridAdapter.this.mBannerHeight).loadLaunchItemImage();
        }

        public Object getFacet(Class<?> cls) {
            if (getAdapterPosition() == -1) {
                return null;
            }
            return KeylineUtil.createItemAlignmentFacet(-calculateOffset());
        }

        private int calculateOffset() {
            Pair<Integer, Integer> rowColIndex = LaunchItemsHolder.getRowColIndexFromListIndex(getAdapterPosition());
            if (rowColIndex == null) {
                return EditModeGridAdapter.this.mTopKeyline;
            }
            int rowPosition = ((Integer) rowColIndex.first).intValue();
            int totalRows = LaunchItemsHolder.getRowCount(EditModeGridAdapter.this.getItemCount());
            if (EditModeGridAdapter.this.mKeylineLastRow == EditModeGridAdapter.this.mBottomKeyline && rowPosition == totalRows - 2 && totalRows >= 3) {
                return EditModeGridAdapter.this.mKeylineAppsRowTwoTitleAbove;
            }
            if (rowPosition == totalRows - 1) {
                return EditModeGridAdapter.this.mBottomKeyline;
            }
            return EditModeGridAdapter.this.mTopKeyline;
        }

        public void onClick(View v) {
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                EditModeGridAdapter.this.mOnShowAccessibilityMenuListener.onShowAccessibilityMenu(true);
            }
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (v instanceof BannerView) {
                ((BannerView) v).setIsBeingEdited(hasFocus);
            }
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public BannerView getBannerView() {
            return this.mBannerView;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public LaunchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BannerView bannerView = (BannerView) LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_app_banner, parent, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bannerView.getLayoutParams());
        params.bottomMargin = this.mBannerMarginBottom;
        params.setMarginEnd(this.mBannerMarginEnd);
        bannerView.setLayoutParams(params);
        return new LaunchItemViewHolder(bannerView);
    }

    public void onBindViewHolder(@NonNull LaunchItemViewHolder holder, int position) {
        holder.set(this.mLaunchItems.get(position));
    }

    public int getItemCount() {
        return this.mLaunchItems.size();
    }

    /* access modifiers changed from: package-private */
    public void setTopKeyline(int topKeyline) {
        this.mTopKeyline = topKeyline;
    }

    /* access modifiers changed from: package-private */
    public void setBottomKeyline(int bottomKeyline) {
        this.mBottomKeyline = bottomKeyline;
    }

    /* access modifiers changed from: package-private */
    public void setLaunchItems(ArrayList<LaunchItem> items) {
        this.mLaunchItems.clear();
        this.mLaunchItems.addAll(items);
        notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void moveLaunchItems(int from, int to, int direction) {
        TvlauncherLogEnum.TvLauncherEventCode eventCode;
        if (from >= 0) {
            int offset = 1;
            if (from <= this.mLaunchItems.size() - 1 && to >= 0 && to <= this.mLaunchItems.size() - 1) {
                LaunchItem fromItem = this.mLaunchItems.get(from);
                this.mLaunchItems.set(from, this.mLaunchItems.get(to));
                this.mLaunchItems.set(to, fromItem);
                notifyItemMoved(from, to);
                int positionDifference = to - from;
                if (Math.abs(positionDifference) > 1) {
                    if (positionDifference > 0) {
                        offset = -1;
                    }
                    notifyItemMoved(to + offset, from);
                }
                if (direction == 17) {
                    eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_LEFT;
                } else if (direction == 33) {
                    eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_UP;
                } else if (direction == 66) {
                    eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_RIGHT;
                } else if (direction == 130) {
                    eventCode = TvlauncherLogEnum.TvLauncherEventCode.MOVE_LAUNCH_ITEM_DOWN;
                } else {
                    StringBuilder sb = new StringBuilder(30);
                    sb.append("Invalid direction: ");
                    sb.append(direction);
                    throw new IllegalArgumentException(sb.toString());
                }
                LogEvent logEvent = new LogEvent(eventCode).setVisualElementTag(TvLauncherConstants.LAUNCH_ITEM).setVisualElementRowIndex(to / 4).setVisualElementIndex(to % 4);
                logEvent.getApplication().setPackageName(fromItem.getPackageName());
                logEvent.pushParentVisualElementTag(fromItem.isGame() ? TvLauncherConstants.GAMES_CONTAINER : TvLauncherConstants.APPS_CONTAINER);
                this.mEventLogger.log(logEvent);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<LaunchItem> getLaunchItems() {
        return this.mLaunchItems;
    }

    /* access modifiers changed from: package-private */
    public void setOnShowAccessibilityMenuListener(OnShowAccessibilityMenuListener listener) {
        this.mOnShowAccessibilityMenuListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnEditItemRemovedListener(OnEditItemRemovedListener listener) {
        this.mOnEditItemRemovedListener = listener;
    }
}
