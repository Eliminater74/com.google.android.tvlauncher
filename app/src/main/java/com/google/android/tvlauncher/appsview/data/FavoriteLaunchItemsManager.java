package com.google.android.tvlauncher.appsview.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import android.util.Pair;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.settings.FavoriteLaunchItemsActivity;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@VisibleForTesting
public class FavoriteLaunchItemsManager implements LaunchItemsManager.AppsViewChangeListener {
    private static final int MAX_HOME_SCREEN_APP_ITEMS = 100;
    private static final int MAX_HOME_SCREEN_OOB_ORDER_APP_ITEMS = 10;
    private static final String MORE_FAVORITES_PKGNAME = "com.google.android.tvlauncher.appsview.FavoriteItemsManager.MORE_FAVORITES_PKGNAME";
    private static final String PREF_FILE_NAME = "com.google.android.tvlauncher.appsview.FavoriteItemsManager.PREFERENCE_KEY";
    private static final String USER_CUSTOMIZATION_KEY = "com.google.android.tvlauncher.appsview.FavoriteItemsManager.USER_CUSTOMIZATION_KEY";
    private final Context mContext;
    private boolean mCustomizedByUser;
    private final Map<String, Integer> mDefaultItemsOrder = new HashMap();
    /* access modifiers changed from: private */
    public final Map<LaunchItem, Integer> mFavoriteItemsToDesiredPosition = new HashMap();
    private LaunchItemsManager.HomeScreenItemsChangeListener mHomeScreenItemsChangeListener;
    private final LaunchItem mMoreFavoritesItem;
    private final SharedPreferences mPackageNamePrefs;
    private Map<String, Integer> mPinnedAppPackagesToPosition;
    private LaunchItem[] mPinnedItems;
    private final SharedPreferences mUserCustomizationPref;

    private class LaunchItemUserOrderComparator implements Comparator<LaunchItem> {
        private LaunchItemUserOrderComparator() {
        }

        public int compare(LaunchItem o1, LaunchItem o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            Integer o1Index = (Integer) FavoriteLaunchItemsManager.this.mFavoriteItemsToDesiredPosition.get(o1);
            Integer o2Index = (Integer) FavoriteLaunchItemsManager.this.mFavoriteItemsToDesiredPosition.get(o2);
            Integer o1Index2 = Integer.valueOf(o1Index == null ? FavoriteLaunchItemsManager.this.mFavoriteItemsToDesiredPosition.keySet().size() : o1Index.intValue());
            Integer o2Index2 = Integer.valueOf(o2Index == null ? FavoriteLaunchItemsManager.this.mFavoriteItemsToDesiredPosition.keySet().size() : o2Index.intValue());
            if (o1Index2.intValue() < o2Index2.intValue()) {
                return -1;
            }
            if (o1Index2.intValue() > o2Index2.intValue()) {
                return 1;
            }
            return o1.compareTo(o2);
        }
    }

    FavoriteLaunchItemsManager(Context context) {
        this.mPackageNamePrefs = context.getSharedPreferences(PREF_FILE_NAME, 0);
        this.mUserCustomizationPref = context.getSharedPreferences(USER_CUSTOMIZATION_KEY, 0);
        this.mContext = context;
        this.mMoreFavoritesItem = createMoreFavoritesLaunchItem(this.mContext.getString(C1188R.string.favorite_more_apps), new Intent(this.mContext, FavoriteLaunchItemsActivity.class));
        this.mCustomizedByUser = this.mUserCustomizationPref.getBoolean(USER_CUSTOMIZATION_KEY, false);
    }

    /* access modifiers changed from: package-private */
    public void init(OemConfiguration oemConfiguration) {
        List<String> oemOrder;
        if (oemConfiguration.isDataLoaded()) {
            if (!this.mCustomizedByUser && (oemOrder = oemConfiguration.getOutOfBoxFavoriteAppsList()) != null && !oemOrder.isEmpty()) {
                int i = 0;
                while (i < oemOrder.size() && i < 10) {
                    this.mDefaultItemsOrder.put(oemOrder.get(i), Integer.valueOf(i));
                    i++;
                }
                if (this.mPackageNamePrefs.getAll().isEmpty()) {
                    initializeDefaultOrderFavorites();
                }
            }
            if (Util.isOperatorTierDevice(this.mContext)) {
                this.mPinnedAppPackagesToPosition = new HashMap();
                List<String> pinnedApps = oemConfiguration.getPinnedFavoriteApps();
                for (int i2 = 0; i2 < pinnedApps.size(); i2++) {
                    this.mPinnedAppPackagesToPosition.put(pinnedApps.get(i2), Integer.valueOf(i2));
                }
                this.mPinnedItems = new LaunchItem[this.mPinnedAppPackagesToPosition.size()];
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void userAddToFavorites(LaunchItem item) {
        if (!this.mFavoriteItemsToDesiredPosition.containsKey(item) && this.mFavoriteItemsToDesiredPosition.size() < 100) {
            onCustomizedByUser();
            this.mFavoriteItemsToDesiredPosition.put(item, Integer.valueOf(this.mFavoriteItemsToDesiredPosition.keySet().size()));
            List<LaunchItem> items = getSortedFavorites();
            saveOrderSnapshot(items);
            notifyOnHomeScreenItemsChanged(items);
        }
    }

    /* access modifiers changed from: package-private */
    public void userRemoveFromFavorites(LaunchItem item) {
        onCustomizedByUser();
        removeFavorite(item);
    }

    private int removeFavorite(LaunchItem item) {
        Integer found = this.mFavoriteItemsToDesiredPosition.remove(item);
        if (found != null) {
            List<LaunchItem> items = getSortedFavorites();
            saveOrderSnapshotIfCustomizedByUser(items);
            notifyOnHomeScreenItemsChanged(items);
        }
        if (found == null) {
            return -1;
        }
        return found.intValue();
    }

    /* access modifiers changed from: package-private */
    public List<LaunchItem> getFavoriteItems() {
        List<LaunchItem> items = getSortedFavorites();
        if (items.size() < 100 && !items.contains(this.mMoreFavoritesItem)) {
            items.add(this.mMoreFavoritesItem);
        }
        LaunchItem[] launchItemArr = this.mPinnedItems;
        if (launchItemArr != null) {
            for (int i = launchItemArr.length - 1; i >= 0; i--) {
                LaunchItem[] launchItemArr2 = this.mPinnedItems;
                if (launchItemArr2[i] != null) {
                    items.add(0, launchItemArr2[i]);
                }
            }
        }
        return items;
    }

    /* access modifiers changed from: package-private */
    public boolean isFavorite(LaunchItem item) {
        return this.mFavoriteItemsToDesiredPosition.containsKey(item) || isPinnedFavorite(item);
    }

    /* access modifiers changed from: package-private */
    public boolean isPinnedFavorite(LaunchItem item) {
        Map<String, Integer> map = this.mPinnedAppPackagesToPosition;
        if (map != null) {
            return map.containsKey(item.getPackageName());
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isOnlyFavorite(LaunchItem item) {
        if (this.mFavoriteItemsToDesiredPosition.size() != 1 || !this.mFavoriteItemsToDesiredPosition.containsKey(item)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setHomeScreenItemsChangeListener(LaunchItemsManager.HomeScreenItemsChangeListener listener) {
        this.mHomeScreenItemsChangeListener = listener;
    }

    public void onLaunchItemsLoaded() {
        addAppsToFavoritesFromSharedPreferences();
        notifyOnHomeScreenItemsLoaded();
    }

    public void onLaunchItemsAddedOrUpdated(ArrayList<LaunchItem> addedOrUpdatedItems) {
        boolean changed = false;
        Iterator<LaunchItem> it = addedOrUpdatedItems.iterator();
        while (it.hasNext()) {
            LaunchItem updatedItem = it.next();
            if (this.mFavoriteItemsToDesiredPosition.containsKey(updatedItem)) {
                Map<LaunchItem, Integer> map = this.mFavoriteItemsToDesiredPosition;
                map.put(updatedItem, map.get(updatedItem));
                changed = true;
            } else if (!this.mCustomizedByUser && this.mDefaultItemsOrder.containsKey(updatedItem.getPackageName())) {
                this.mFavoriteItemsToDesiredPosition.put(updatedItem, this.mDefaultItemsOrder.get(updatedItem.getPackageName()));
                changed = true;
            }
            if (this.mPinnedAppPackagesToPosition != null) {
                changed |= addToPinnedApps(updatedItem);
            }
        }
        if (changed) {
            saveOrderSnapshotIfCustomizedByUser(getSortedFavorites());
            notifyOnHomeScreenItemsChanged(getFavoriteItems());
        }
    }

    public void onLaunchItemsRemoved(ArrayList<LaunchItem> removedItems) {
        Integer index;
        boolean changed = false;
        Iterator<LaunchItem> it = removedItems.iterator();
        while (it.hasNext()) {
            LaunchItem removedItem = it.next();
            changed |= removeFavorite(removedItem) == -1;
            Map<String, Integer> map = this.mPinnedAppPackagesToPosition;
            if (!(map == null || (index = map.get(removedItem.getPackageName())) == null)) {
                this.mPinnedItems[index.intValue()] = null;
                changed = true;
            }
        }
        if (changed) {
            notifyOnHomeScreenItemsChanged(getFavoriteItems());
        }
    }

    public void onEditModeItemOrderChange(ArrayList<LaunchItem> arrayList, boolean isGameItems, Pair<Integer, Integer> pair) {
    }

    /* access modifiers changed from: package-private */
    public void swapAppOrder(LaunchItem from, LaunchItem to) {
        onCustomizedByUser();
        List<LaunchItem> displayedFavoritesList = getFavoriteItems();
        int fromIndex = this.mFavoriteItemsToDesiredPosition.get(from).intValue();
        int toIndex = this.mFavoriteItemsToDesiredPosition.get(to).intValue();
        this.mFavoriteItemsToDesiredPosition.put(from, Integer.valueOf(toIndex));
        this.mFavoriteItemsToDesiredPosition.put(to, Integer.valueOf(fromIndex));
        SharedPreferences.Editor editor = this.mPackageNamePrefs.edit();
        editor.putInt(from.getPackageName(), toIndex);
        editor.putInt(to.getPackageName(), fromIndex);
        editor.apply();
        notifyItemsSwapped(displayedFavoritesList.indexOf(from), displayedFavoritesList.indexOf(to));
    }

    /* access modifiers changed from: package-private */
    public int getOrderedFavoritePosition(LaunchItem item) {
        if (this.mFavoriteItemsToDesiredPosition.containsKey(item)) {
            return this.mFavoriteItemsToDesiredPosition.get(item).intValue();
        }
        return -1;
    }

    private List<LaunchItem> getSortedFavorites() {
        ArrayList<LaunchItem> items = new ArrayList<>(this.mFavoriteItemsToDesiredPosition.keySet());
        Collections.sort(items, new LaunchItemUserOrderComparator());
        return items;
    }

    private void addAppsToFavoritesFromSharedPreferences() {
        this.mFavoriteItemsToDesiredPosition.clear();
        Map<String, ?> keyValMap = this.mPackageNamePrefs.getAll();
        for (LaunchItem item : LaunchItemsManagerProvider.getInstance(this.mContext).getAllLaunchItemsWithoutSorting()) {
            addToPinnedApps(item);
            if (keyValMap.keySet().contains(item.getPackageName()) && !isPinnedFavorite(item)) {
                this.mFavoriteItemsToDesiredPosition.put(item, (Integer) keyValMap.get(item.getPackageName()));
            }
        }
    }

    private boolean addToPinnedApps(LaunchItem item) {
        Integer index;
        Map<String, Integer> map = this.mPinnedAppPackagesToPosition;
        if (map == null || (index = map.get(item.getPackageName())) == null) {
            return false;
        }
        this.mPinnedItems[index.intValue()] = item;
        return true;
    }

    private void initializeDefaultOrderFavorites() {
        SharedPreferences.Editor editor = this.mPackageNamePrefs.edit();
        editor.clear();
        for (String pkgName : this.mDefaultItemsOrder.keySet()) {
            editor.putInt(pkgName, this.mDefaultItemsOrder.get(pkgName).intValue());
        }
        editor.apply();
    }

    private void notifyOnHomeScreenItemsChanged(List<LaunchItem> changedItems) {
        LaunchItemsManager.HomeScreenItemsChangeListener homeScreenItemsChangeListener = this.mHomeScreenItemsChangeListener;
        if (homeScreenItemsChangeListener != null) {
            homeScreenItemsChangeListener.onHomeScreenItemsChanged(changedItems);
        }
    }

    private void notifyOnHomeScreenItemsLoaded() {
        LaunchItemsManager.HomeScreenItemsChangeListener homeScreenItemsChangeListener = this.mHomeScreenItemsChangeListener;
        if (homeScreenItemsChangeListener != null) {
            homeScreenItemsChangeListener.onHomeScreenItemsLoaded();
        }
    }

    private void notifyItemsSwapped(int fromDisplayedPosition, int toDisplayedPosition) {
        LaunchItemsManager.HomeScreenItemsChangeListener homeScreenItemsChangeListener = this.mHomeScreenItemsChangeListener;
        if (homeScreenItemsChangeListener != null) {
            homeScreenItemsChangeListener.onHomeScreenItemsSwapped(fromDisplayedPosition, toDisplayedPosition);
        }
    }

    private void saveOrderSnapshotIfCustomizedByUser(List<LaunchItem> items) {
        if (this.mCustomizedByUser) {
            saveOrderSnapshot(items);
        }
    }

    private void saveOrderSnapshot(List<LaunchItem> items) {
        SharedPreferences.Editor editor = this.mPackageNamePrefs.edit();
        editor.clear();
        for (int i = 0; i < items.size(); i++) {
            LaunchItem item = items.get(i);
            this.mFavoriteItemsToDesiredPosition.put(item, Integer.valueOf(i));
            editor.putInt(item.getPackageName(), i);
        }
        editor.apply();
    }

    /* access modifiers changed from: package-private */
    public boolean isFull() {
        return this.mFavoriteItemsToDesiredPosition.size() == 100;
    }

    /* access modifiers changed from: package-private */
    public void onCustomizedByUser() {
        if (!this.mCustomizedByUser) {
            this.mUserCustomizationPref.edit().putBoolean(USER_CUSTOMIZATION_KEY, true).apply();
            this.mCustomizedByUser = true;
            saveOrderSnapshot(getSortedFavorites());
        }
    }

    private static LaunchItem createMoreFavoritesLaunchItem(CharSequence appLabel, Intent intent) {
        LaunchItem item = new LaunchItem();
        item.setLabel(appLabel);
        item.setPackageName(MORE_FAVORITES_PKGNAME);
        item.setIntent(intent);
        return item;
    }
}
