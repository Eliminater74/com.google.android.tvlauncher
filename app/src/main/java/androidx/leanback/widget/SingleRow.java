package androidx.leanback.widget;

import android.support.annotation.NonNull;
import android.support.p001v4.util.CircularIntArray;
import android.support.p004v7.widget.RecyclerView;

import java.io.PrintWriter;

class SingleRow extends Grid {
    private final Grid.Location mTmpLocation = new Grid.Location(0);

    SingleRow() {
        setNumRows(1);
    }

    public final Grid.Location getLocation(int index) {
        return this.mTmpLocation;
    }

    public final void debugPrint(PrintWriter pw) {
        pw.print("SingleRow<");
        pw.print(this.mFirstVisibleIndex);
        pw.print(",");
        pw.print(this.mLastVisibleIndex);
        pw.print(">");
        pw.println();
    }

    /* access modifiers changed from: package-private */
    public int getStartIndexForAppend() {
        if (this.mLastVisibleIndex >= 0) {
            return this.mLastVisibleIndex + 1;
        }
        if (this.mStartIndex != -1) {
            return Math.min(this.mStartIndex, this.mProvider.getCount() - 1);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getStartIndexForPrepend() {
        if (this.mFirstVisibleIndex >= 0) {
            return this.mFirstVisibleIndex - 1;
        }
        if (this.mStartIndex != -1) {
            return Math.min(this.mStartIndex, this.mProvider.getCount() - 1);
        }
        return this.mProvider.getCount() - 1;
    }

    /* access modifiers changed from: protected */
    public final boolean prependVisibleItems(int toLimit, boolean oneColumnMode) {
        int edge;
        int edge2;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!oneColumnMode && checkPrependOverLimit(toLimit)) {
            return false;
        }
        boolean filledOne = false;
        int minIndex = this.mProvider.getMinIndex();
        for (int index = getStartIndexForPrepend(); index >= minIndex; index--) {
            int size = this.mProvider.createItem(index, false, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                int edge3 = this.mReversedFlow != 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                this.mFirstVisibleIndex = index;
                this.mLastVisibleIndex = index;
                edge = edge3;
            } else {
                if (this.mReversedFlow) {
                    edge2 = this.mProvider.getEdge(index + 1) + this.mSpacing + size;
                } else {
                    edge2 = (this.mProvider.getEdge(index + 1) - this.mSpacing) - size;
                }
                this.mFirstVisibleIndex = index;
                edge = edge2;
            }
            this.mProvider.addItem(this.mTmpItem[0], index, size, 0, edge);
            filledOne = true;
            if (oneColumnMode || checkPrependOverLimit(toLimit)) {
                break;
            }
        }
        return filledOne;
    }

    /* access modifiers changed from: protected */
    public final boolean appendVisibleItems(int toLimit, boolean oneColumnMode) {
        int edge;
        int edge2;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!oneColumnMode && checkAppendOverLimit(toLimit)) {
            return false;
        }
        boolean filledOne = false;
        for (int index = getStartIndexForAppend(); index < this.mProvider.getCount(); index++) {
            int size = this.mProvider.createItem(index, true, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                int edge3 = this.mReversedFlow != 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                this.mFirstVisibleIndex = index;
                this.mLastVisibleIndex = index;
                edge = edge3;
            } else {
                if (this.mReversedFlow) {
                    edge2 = (this.mProvider.getEdge(index - 1) - this.mProvider.getSize(index - 1)) - this.mSpacing;
                } else {
                    edge2 = this.mProvider.getEdge(index - 1) + this.mProvider.getSize(index - 1) + this.mSpacing;
                }
                this.mLastVisibleIndex = index;
                edge = edge2;
            }
            this.mProvider.addItem(this.mTmpItem[0], index, size, 0, edge);
            filledOne = true;
            if (oneColumnMode || checkAppendOverLimit(toLimit)) {
                break;
            }
        }
        return filledOne;
    }

    public void collectAdjacentPrefetchPositions(int fromLimit, int da, @NonNull RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int itemSizeWithSpace;
        int indexToPrefetch;
        if (!this.mReversedFlow ? da >= 0 : da <= 0) {
            if (getLastVisibleIndex() != this.mProvider.getCount() - 1) {
                indexToPrefetch = getStartIndexForAppend();
                int itemSizeWithSpace2 = this.mProvider.getSize(this.mLastVisibleIndex) + this.mSpacing;
                itemSizeWithSpace = this.mProvider.getEdge(this.mLastVisibleIndex) + (this.mReversedFlow ? -itemSizeWithSpace2 : itemSizeWithSpace2);
            } else {
                return;
            }
        } else if (getFirstVisibleIndex() != 0) {
            indexToPrefetch = getStartIndexForPrepend();
            itemSizeWithSpace = this.mProvider.getEdge(this.mFirstVisibleIndex) + (this.mReversedFlow ? this.mSpacing : -this.mSpacing);
        } else {
            return;
        }
        layoutPrefetchRegistry.addPosition(indexToPrefetch, Math.abs(itemSizeWithSpace - fromLimit));
    }

    public final CircularIntArray[] getItemPositionsInRows(int startPos, int endPos) {
        this.mTmpItemPositionsInRows[0].clear();
        this.mTmpItemPositionsInRows[0].addLast(startPos);
        this.mTmpItemPositionsInRows[0].addLast(endPos);
        return this.mTmpItemPositionsInRows;
    }

    /* access modifiers changed from: protected */
    public final int findRowMin(boolean findLarge, int indexLimit, int[] indices) {
        if (indices != null) {
            indices[0] = 0;
            indices[1] = indexLimit;
        }
        if (this.mReversedFlow) {
            return this.mProvider.getEdge(indexLimit) - this.mProvider.getSize(indexLimit);
        }
        return this.mProvider.getEdge(indexLimit);
    }

    /* access modifiers changed from: protected */
    public final int findRowMax(boolean findLarge, int indexLimit, int[] indices) {
        if (indices != null) {
            indices[0] = 0;
            indices[1] = indexLimit;
        }
        if (this.mReversedFlow) {
            return this.mProvider.getEdge(indexLimit);
        }
        return this.mProvider.getEdge(indexLimit) + this.mProvider.getSize(indexLimit);
    }
}
