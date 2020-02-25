package androidx.leanback.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.CircularIntArray;
import android.support.p004v7.widget.RecyclerView;
import android.util.SparseIntArray;

import java.io.PrintWriter;
import java.util.Arrays;

abstract class Grid {
    public static final int START_DEFAULT = -1;
    protected int mFirstVisibleIndex = -1;
    protected int mLastVisibleIndex = -1;
    protected int mNumRows;
    protected Provider mProvider;
    protected boolean mReversedFlow;
    protected int mSpacing;
    protected int mStartIndex = -1;
    protected CircularIntArray[] mTmpItemPositionsInRows;
    Object[] mTmpItem = new Object[1];

    Grid() {
    }

    public static Grid createGrid(int rows) {
        if (rows == 1) {
            return new SingleRow();
        }
        Grid grid = new StaggeredGridDefault();
        grid.setNumRows(rows);
        return grid;
    }

    /* access modifiers changed from: protected */
    public abstract boolean appendVisibleItems(int i, boolean z);

    public abstract void debugPrint(PrintWriter printWriter);

    /* access modifiers changed from: protected */
    public abstract int findRowMax(boolean z, int i, int[] iArr);

    /* access modifiers changed from: protected */
    public abstract int findRowMin(boolean z, int i, int[] iArr);

    public abstract CircularIntArray[] getItemPositionsInRows(int i, int i2);

    public abstract Location getLocation(int i);

    /* access modifiers changed from: protected */
    public abstract boolean prependVisibleItems(int i, boolean z);

    public final void setSpacing(int spacing) {
        this.mSpacing = spacing;
    }

    public boolean isReversedFlow() {
        return this.mReversedFlow;
    }

    public final void setReversedFlow(boolean reversedFlow) {
        this.mReversedFlow = reversedFlow;
    }

    public void setProvider(Provider provider) {
        this.mProvider = provider;
    }

    public void setStart(int startIndex) {
        this.mStartIndex = startIndex;
    }

    public int getNumRows() {
        return this.mNumRows;
    }

    /* access modifiers changed from: package-private */
    public void setNumRows(int numRows) {
        if (numRows <= 0) {
            throw new IllegalArgumentException();
        } else if (this.mNumRows != numRows) {
            this.mNumRows = numRows;
            this.mTmpItemPositionsInRows = new CircularIntArray[this.mNumRows];
            for (int i = 0; i < this.mNumRows; i++) {
                this.mTmpItemPositionsInRows[i] = new CircularIntArray();
            }
        }
    }

    public final int getFirstVisibleIndex() {
        return this.mFirstVisibleIndex;
    }

    public final int getLastVisibleIndex() {
        return this.mLastVisibleIndex;
    }

    public void resetVisibleIndex() {
        this.mLastVisibleIndex = -1;
        this.mFirstVisibleIndex = -1;
    }

    public void invalidateItemsAfter(int index) {
        int i;
        if (index >= 0 && (i = this.mLastVisibleIndex) >= 0) {
            if (i >= index) {
                this.mLastVisibleIndex = index - 1;
            }
            resetVisibleIndexIfEmpty();
            if (getFirstVisibleIndex() < 0) {
                setStart(index);
            }
        }
    }

    public final int getRowIndex(int index) {
        Location location = getLocation(index);
        if (location == null) {
            return -1;
        }
        return location.row;
    }

    public final int findRowMin(boolean findLarge, @Nullable int[] indices) {
        return findRowMin(findLarge, this.mReversedFlow ? this.mLastVisibleIndex : this.mFirstVisibleIndex, indices);
    }

    public final int findRowMax(boolean findLarge, @Nullable int[] indices) {
        return findRowMax(findLarge, this.mReversedFlow ? this.mFirstVisibleIndex : this.mLastVisibleIndex, indices);
    }

    /* access modifiers changed from: protected */
    public final boolean checkAppendOverLimit(int toLimit) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMin(true, null) <= this.mSpacing + toLimit) {
                return true;
            }
            return false;
        } else if (findRowMax(false, null) >= toLimit - this.mSpacing) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean checkPrependOverLimit(int toLimit) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMax(false, null) >= toLimit - this.mSpacing) {
                return true;
            }
            return false;
        } else if (findRowMin(true, null) <= this.mSpacing + toLimit) {
            return true;
        } else {
            return false;
        }
    }

    public final CircularIntArray[] getItemPositionsInRows() {
        return getItemPositionsInRows(getFirstVisibleIndex(), getLastVisibleIndex());
    }

    public final boolean prependOneColumnVisibleItems() {
        return prependVisibleItems(this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE, true);
    }

    public final void prependVisibleItems(int toLimit) {
        prependVisibleItems(toLimit, false);
    }

    public boolean appendOneColumnVisibleItems() {
        return appendVisibleItems(this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE, true);
    }

    public final void appendVisibleItems(int toLimit) {
        appendVisibleItems(toLimit, false);
    }

    public void removeInvisibleItemsAtEnd(int aboveIndex, int toLimit) {
        while (true) {
            int i = this.mLastVisibleIndex;
            if (i < this.mFirstVisibleIndex || i <= aboveIndex) {
                break;
            }
            boolean offEnd = false;
            if (!this.mReversedFlow) {
                if (this.mProvider.getEdge(i) >= toLimit) {
                    offEnd = true;
                }
            } else if (this.mProvider.getEdge(i) <= toLimit) {
                offEnd = true;
            }
            if (!offEnd) {
                break;
            }
            this.mProvider.removeItem(this.mLastVisibleIndex);
            this.mLastVisibleIndex--;
        }
        resetVisibleIndexIfEmpty();
    }

    public void removeInvisibleItemsAtFront(int belowIndex, int toLimit) {
        while (true) {
            int i = this.mLastVisibleIndex;
            int i2 = this.mFirstVisibleIndex;
            if (i < i2 || i2 >= belowIndex) {
                break;
            }
            int size = this.mProvider.getSize(i2);
            boolean offFront = false;
            if (!this.mReversedFlow) {
                if (this.mProvider.getEdge(this.mFirstVisibleIndex) + size <= toLimit) {
                    offFront = true;
                }
            } else if (this.mProvider.getEdge(this.mFirstVisibleIndex) - size >= toLimit) {
                offFront = true;
            }
            if (!offFront) {
                break;
            }
            this.mProvider.removeItem(this.mFirstVisibleIndex);
            this.mFirstVisibleIndex++;
        }
        resetVisibleIndexIfEmpty();
    }

    private void resetVisibleIndexIfEmpty() {
        if (this.mLastVisibleIndex < this.mFirstVisibleIndex) {
            resetVisibleIndex();
        }
    }

    /* JADX INFO: Multiple debug info for r8v0 int: [D('firstDisappearingIndex' int), D('firstPos' int)] */
    public void fillDisappearingItems(int[] positions, int positionsLength, SparseIntArray positionToRow) {
        int edge;
        int disappearingRow;
        int i;
        int edge2;
        int disappearingRow2;
        int i2;
        int[] iArr = positions;
        int i3 = positionsLength;
        SparseIntArray sparseIntArray = positionToRow;
        int lastPos = getLastVisibleIndex();
        int resultSearchLast = lastPos >= 0 ? Arrays.binarySearch(iArr, 0, i3, lastPos) : 0;
        if (resultSearchLast < 0) {
            int firstDisappearingIndex = (-resultSearchLast) - 1;
            if (this.mReversedFlow) {
                edge2 = (this.mProvider.getEdge(lastPos) - this.mProvider.getSize(lastPos)) - this.mSpacing;
            } else {
                edge2 = this.mProvider.getEdge(lastPos) + this.mProvider.getSize(lastPos) + this.mSpacing;
            }
            for (int i4 = firstDisappearingIndex; i4 < i3; i4++) {
                int disappearingIndex = iArr[i4];
                int disappearingRow3 = sparseIntArray.get(disappearingIndex);
                if (disappearingRow3 < 0) {
                    disappearingRow2 = 0;
                } else {
                    disappearingRow2 = disappearingRow3;
                }
                int size = this.mProvider.createItem(disappearingIndex, true, this.mTmpItem, true);
                this.mProvider.addItem(this.mTmpItem[0], disappearingIndex, size, disappearingRow2, edge2);
                if (this.mReversedFlow) {
                    i2 = (edge2 - size) - this.mSpacing;
                } else {
                    i2 = edge2 + size + this.mSpacing;
                }
                edge2 = i2;
            }
        }
        int firstPos = getFirstVisibleIndex();
        int resultSearchFirst = firstPos >= 0 ? Arrays.binarySearch(iArr, 0, i3, firstPos) : 0;
        if (resultSearchFirst < 0) {
            int firstDisappearingIndex2 = (-resultSearchFirst) - 2;
            if (this.mReversedFlow) {
                edge = this.mProvider.getEdge(firstPos);
            } else {
                edge = this.mProvider.getEdge(firstPos);
            }
            for (int i5 = firstDisappearingIndex2; i5 >= 0; i5--) {
                int disappearingIndex2 = iArr[i5];
                int disappearingRow4 = sparseIntArray.get(disappearingIndex2);
                if (disappearingRow4 < 0) {
                    disappearingRow = 0;
                } else {
                    disappearingRow = disappearingRow4;
                }
                int size2 = this.mProvider.createItem(disappearingIndex2, false, this.mTmpItem, true);
                if (this.mReversedFlow) {
                    i = this.mSpacing + edge + size2;
                } else {
                    i = (edge - this.mSpacing) - size2;
                }
                edge = i;
                this.mProvider.addItem(this.mTmpItem[0], disappearingIndex2, size2, disappearingRow, edge);
            }
        }
    }

    public void collectAdjacentPrefetchPositions(int fromLimit, int da, @NonNull RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
    }

    public interface Provider {
        void addItem(Object obj, int i, int i2, int i3, int i4);

        int createItem(int i, boolean z, Object[] objArr, boolean z2);

        int getCount();

        int getEdge(int i);

        int getMinIndex();

        int getSize(int i);

        void removeItem(int i);
    }

    public static class Location {
        public int row;

        public Location(int row2) {
            this.row = row2;
        }
    }
}
