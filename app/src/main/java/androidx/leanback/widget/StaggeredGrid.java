package androidx.leanback.widget;

import android.support.p001v4.util.CircularArray;
import android.support.p001v4.util.CircularIntArray;

import java.io.PrintWriter;

abstract class StaggeredGrid extends Grid {
    protected int mFirstIndex = -1;
    protected CircularArray<Location> mLocations = new CircularArray<>(64);
    protected Object mPendingItem;
    protected int mPendingItemSize;

    StaggeredGrid() {
    }

    /* access modifiers changed from: protected */
    public abstract boolean appendVisibleItemsWithoutCache(int i, boolean z);

    /* access modifiers changed from: protected */
    public abstract boolean prependVisibleItemsWithoutCache(int i, boolean z);

    public final int getFirstIndex() {
        return this.mFirstIndex;
    }

    public final int getLastIndex() {
        return (this.mFirstIndex + this.mLocations.size()) - 1;
    }

    public final int getSize() {
        return this.mLocations.size();
    }

    public final Location getLocation(int index) {
        int indexInArray = index - this.mFirstIndex;
        if (indexInArray < 0 || indexInArray >= this.mLocations.size()) {
            return null;
        }
        return this.mLocations.get(indexInArray);
    }

    public final void debugPrint(PrintWriter pw) {
        int size = this.mLocations.size();
        for (int i = 0; i < size; i++) {
            pw.print("<" + (this.mFirstIndex + i) + "," + this.mLocations.get(i).row + ">");
            pw.print(" ");
            pw.println();
        }
    }

    /* access modifiers changed from: protected */
    public final boolean prependVisibleItems(int toLimit, boolean oneColumnMode) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!oneColumnMode && checkPrependOverLimit(toLimit)) {
            return false;
        }
        try {
            if (prependVisbleItemsWithCache(toLimit, oneColumnMode)) {
                return true;
            }
            boolean prependVisibleItemsWithoutCache = prependVisibleItemsWithoutCache(toLimit, oneColumnMode);
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
            return prependVisibleItemsWithoutCache;
        } finally {
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean prependVisbleItemsWithCache(int toLimit, boolean oneColumnMode) {
        int itemIndex;
        int offset;
        int edge;
        if (this.mLocations.size() == 0) {
            return false;
        }
        if (this.mFirstVisibleIndex >= 0) {
            edge = this.mProvider.getEdge(this.mFirstVisibleIndex);
            offset = getLocation(this.mFirstVisibleIndex).offset;
            itemIndex = this.mFirstVisibleIndex - 1;
        } else {
            edge = Integer.MAX_VALUE;
            offset = 0;
            itemIndex = this.mStartIndex != -1 ? this.mStartIndex : 0;
            if (itemIndex > getLastIndex() || itemIndex < getFirstIndex() - 1) {
                this.mLocations.clear();
                return false;
            } else if (itemIndex < getFirstIndex()) {
                return false;
            }
        }
        int firstIndex = Math.max(this.mProvider.getMinIndex(), this.mFirstIndex);
        while (itemIndex >= firstIndex) {
            Location loc = getLocation(itemIndex);
            int rowIndex = loc.row;
            int size = this.mProvider.createItem(itemIndex, false, this.mTmpItem, false);
            if (size != loc.size) {
                this.mLocations.removeFromStart((itemIndex + 1) - this.mFirstIndex);
                this.mFirstIndex = this.mFirstVisibleIndex;
                this.mPendingItem = this.mTmpItem[0];
                this.mPendingItemSize = size;
                return false;
            }
            this.mFirstVisibleIndex = itemIndex;
            if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = itemIndex;
            }
            this.mProvider.addItem(this.mTmpItem[0], itemIndex, size, rowIndex, edge - offset);
            if (!oneColumnMode && checkPrependOverLimit(toLimit)) {
                return true;
            }
            edge = this.mProvider.getEdge(itemIndex);
            offset = loc.offset;
            if (rowIndex == 0 && oneColumnMode) {
                return true;
            }
            itemIndex--;
        }
        return false;
    }

    private int calculateOffsetAfterLastItem(int row) {
        int offset;
        int cachedIndex = getLastIndex();
        boolean foundCachedItemInSameRow = false;
        while (true) {
            if (cachedIndex < this.mFirstIndex) {
                break;
            } else if (getLocation(cachedIndex).row == row) {
                foundCachedItemInSameRow = true;
                break;
            } else {
                cachedIndex--;
            }
        }
        if (!foundCachedItemInSameRow) {
            cachedIndex = getLastIndex();
        }
        if (isReversedFlow()) {
            offset = (-getLocation(cachedIndex).size) - this.mSpacing;
        } else {
            offset = getLocation(cachedIndex).size + this.mSpacing;
        }
        for (int i = cachedIndex + 1; i <= getLastIndex(); i++) {
            offset -= getLocation(i).offset;
        }
        return offset;
    }

    /* access modifiers changed from: protected */
    public final int prependVisibleItemToRow(int itemIndex, int rowIndex, int edge) {
        Object item;
        if (this.mFirstVisibleIndex < 0 || (this.mFirstVisibleIndex == getFirstIndex() && this.mFirstVisibleIndex == itemIndex + 1)) {
            int i = this.mFirstIndex;
            Location oldFirstLoc = i >= 0 ? getLocation(i) : null;
            int oldFirstEdge = this.mProvider.getEdge(this.mFirstIndex);
            Location loc = new Location(rowIndex, 0, 0);
            this.mLocations.addFirst(loc);
            if (this.mPendingItem != null) {
                loc.size = this.mPendingItemSize;
                item = this.mPendingItem;
                this.mPendingItem = null;
            } else {
                loc.size = this.mProvider.createItem(itemIndex, false, this.mTmpItem, false);
                item = this.mTmpItem[0];
            }
            this.mFirstVisibleIndex = itemIndex;
            this.mFirstIndex = itemIndex;
            if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = itemIndex;
            }
            int thisEdge = !this.mReversedFlow ? edge - loc.size : loc.size + edge;
            if (oldFirstLoc != null) {
                oldFirstLoc.offset = oldFirstEdge - thisEdge;
            }
            this.mProvider.addItem(item, itemIndex, loc.size, rowIndex, thisEdge);
            return loc.size;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final boolean appendVisibleItems(int toLimit, boolean oneColumnMode) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!oneColumnMode && checkAppendOverLimit(toLimit)) {
            return false;
        }
        try {
            if (appendVisbleItemsWithCache(toLimit, oneColumnMode)) {
                return true;
            }
            boolean appendVisibleItemsWithoutCache = appendVisibleItemsWithoutCache(toLimit, oneColumnMode);
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
            return appendVisibleItemsWithoutCache;
        } finally {
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean appendVisbleItemsWithCache(int toLimit, boolean oneColumnMode) {
        int edge;
        int itemIndex;
        if (this.mLocations.size() == 0) {
            return false;
        }
        int count = this.mProvider.getCount();
        if (this.mLastVisibleIndex >= 0) {
            itemIndex = this.mLastVisibleIndex + 1;
            edge = this.mProvider.getEdge(this.mLastVisibleIndex);
        } else {
            edge = Integer.MAX_VALUE;
            itemIndex = this.mStartIndex != -1 ? this.mStartIndex : 0;
            if (itemIndex > getLastIndex() + 1 || itemIndex < getFirstIndex()) {
                this.mLocations.clear();
                return false;
            } else if (itemIndex > getLastIndex()) {
                return false;
            }
        }
        int lastIndex = getLastIndex();
        while (itemIndex < count && itemIndex <= lastIndex) {
            Location loc = getLocation(itemIndex);
            if (edge != Integer.MAX_VALUE) {
                edge += loc.offset;
            }
            int rowIndex = loc.row;
            int size = this.mProvider.createItem(itemIndex, true, this.mTmpItem, false);
            if (size != loc.size) {
                loc.size = size;
                this.mLocations.removeFromEnd(lastIndex - itemIndex);
                lastIndex = itemIndex;
            }
            this.mLastVisibleIndex = itemIndex;
            if (this.mFirstVisibleIndex < 0) {
                this.mFirstVisibleIndex = itemIndex;
            }
            this.mProvider.addItem(this.mTmpItem[0], itemIndex, size, rowIndex, edge);
            if (!oneColumnMode && checkAppendOverLimit(toLimit)) {
                return true;
            }
            if (edge == Integer.MAX_VALUE) {
                edge = this.mProvider.getEdge(itemIndex);
            }
            if (rowIndex == this.mNumRows - 1 && oneColumnMode) {
                return true;
            }
            itemIndex++;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final int appendVisibleItemToRow(int itemIndex, int rowIndex, int location) {
        int offset;
        Object item;
        if (this.mLastVisibleIndex < 0 || (this.mLastVisibleIndex == getLastIndex() && this.mLastVisibleIndex == itemIndex - 1)) {
            if (this.mLastVisibleIndex >= 0) {
                offset = location - this.mProvider.getEdge(this.mLastVisibleIndex);
            } else if (this.mLocations.size() <= 0 || itemIndex != getLastIndex() + 1) {
                offset = 0;
            } else {
                offset = calculateOffsetAfterLastItem(rowIndex);
            }
            Location loc = new Location(rowIndex, offset, 0);
            this.mLocations.addLast(loc);
            if (this.mPendingItem != null) {
                loc.size = this.mPendingItemSize;
                item = this.mPendingItem;
                this.mPendingItem = null;
            } else {
                loc.size = this.mProvider.createItem(itemIndex, true, this.mTmpItem, false);
                item = this.mTmpItem[0];
            }
            if (this.mLocations.size() == 1) {
                this.mLastVisibleIndex = itemIndex;
                this.mFirstVisibleIndex = itemIndex;
                this.mFirstIndex = itemIndex;
            } else if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = itemIndex;
                this.mFirstVisibleIndex = itemIndex;
            } else {
                this.mLastVisibleIndex++;
            }
            this.mProvider.addItem(item, itemIndex, loc.size, rowIndex, location);
            return loc.size;
        }
        throw new IllegalStateException();
    }

    public final CircularIntArray[] getItemPositionsInRows(int startPos, int endPos) {
        for (int i = 0; i < this.mNumRows; i++) {
            this.mTmpItemPositionsInRows[i].clear();
        }
        if (startPos >= 0) {
            for (int i2 = startPos; i2 <= endPos; i2++) {
                CircularIntArray row = this.mTmpItemPositionsInRows[getLocation(i2).row];
                if (row.size() <= 0 || row.getLast() != i2 - 1) {
                    row.addLast(i2);
                    row.addLast(i2);
                } else {
                    row.popLast();
                    row.addLast(i2);
                }
            }
        }
        return this.mTmpItemPositionsInRows;
    }

    public void invalidateItemsAfter(int index) {
        super.invalidateItemsAfter(index);
        this.mLocations.removeFromEnd((getLastIndex() - index) + 1);
        if (this.mLocations.size() == 0) {
            this.mFirstIndex = -1;
        }
    }

    public static class Location extends Grid.Location {
        public int offset;
        public int size;

        public Location(int row, int offset2, int size2) {
            super(row);
            this.offset = offset2;
            this.size = size2;
        }
    }
}
