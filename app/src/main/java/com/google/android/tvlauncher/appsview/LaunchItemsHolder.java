package com.google.android.tvlauncher.appsview;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

class LaunchItemsHolder {
    private static final String TAG = "LaunchItemsHolder";
    private final ArrayList<LaunchItem> mData = new ArrayList<>();

    LaunchItemsHolder() {
    }

    static Pair<Integer, Integer> getRowColIndexFromListIndex(int listIndex) {
        if (listIndex == -1) {
            return null;
        }
        return new Pair<>(Integer.valueOf(listIndex / 4), Integer.valueOf(listIndex % 4));
    }

    static int getRowCount(int numItems) {
        return ((numItems + 4) - 1) / 4;
    }

    public void addData(ArrayList<LaunchItem> items) {
        this.mData.addAll(items);
    }

    public void clear() {
        this.mData.clear();
    }

    /* access modifiers changed from: package-private */
    public int getNumRows() {
        return ((this.mData.size() + 4) - 1) / 4;
    }

    /* access modifiers changed from: package-private */
    public List<LaunchItem> getRowData(int rowIndex) {
        if (rowIndex <= getNumRows() - 1 && rowIndex >= 0) {
            return this.mData.subList(getListIndexFromRowColIndex(rowIndex, 0), Math.min(getListIndexFromRowColIndex(rowIndex, 4), this.mData.size()));
        }
        int numRows = getNumRows();
        StringBuilder sb = new StringBuilder(80);
        sb.append("Row index out of bounds. Given Index : ");
        sb.append(rowIndex);
        sb.append(". Number of Rows : ");
        sb.append(numRows);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public LaunchItem getItem(int rowIndex, int colIndex) {
        int listIndex = getListIndexFromRowColIndex(rowIndex, colIndex);
        if (rowIndex > getNumRows() - 1 || rowIndex < 0) {
            int numRows = getNumRows();
            StringBuilder sb = new StringBuilder(80);
            sb.append("Row index out of bounds. Given Index : ");
            sb.append(rowIndex);
            sb.append(". Number of Rows : ");
            sb.append(numRows);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (colIndex > 3 || colIndex < 0) {
            StringBuilder sb2 = new StringBuilder(71);
            sb2.append("App index out of bounds. Given Index : ");
            sb2.append(colIndex);
            sb2.append(". Max apps per row: ");
            sb2.append(4);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (listIndex <= this.mData.size() - 1 && listIndex >= 0) {
            return this.mData.get(listIndex);
        } else {
            int size = this.mData.size();
            StringBuilder sb3 = new StringBuilder(82);
            sb3.append("List index out of bounds. Given Index : ");
            sb3.append(listIndex);
            sb3.append(". Number of items : ");
            sb3.append(size);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public Pair<Integer, Integer> findIndex(LaunchItem item) {
        return getRowColIndexFromListIndex(this.mData.indexOf(item));
    }

    /* access modifiers changed from: package-private */
    public Pair<Integer, Integer> removeItem(LaunchItem item) {
        Pair removedIndex = findIndex(item);
        this.mData.remove(item);
        return removedIndex;
    }

    public void set(Pair<Integer, Integer> index, LaunchItem item) {
        set(((Integer) index.first).intValue(), ((Integer) index.second).intValue(), item);
    }

    public void set(int rowIndex, int colIndex, LaunchItem item) {
        this.mData.set(getListIndexFromRowColIndex(rowIndex, colIndex), item);
    }

    public int size() {
        return this.mData.size();
    }

    public void addItem(LaunchItem newItem) {
        this.mData.add(newItem);
    }

    public Pair<Integer, Integer> addItemAtIndexElseEnd(int index, LaunchItem item) {
        int insertedAt = index;
        if (index < this.mData.size()) {
            this.mData.add(index, item);
        } else {
            this.mData.add(item);
            insertedAt = this.mData.size() - 1;
        }
        return getRowColIndexFromListIndex(insertedAt);
    }

    public List<LaunchItem> getData() {
        return this.mData;
    }

    public void setData(ArrayList<LaunchItem> items) {
        this.mData.clear();
        this.mData.addAll(items);
    }

    private int getListIndexFromRowColIndex(int rowIndex, int colIndex) {
        return (rowIndex * 4) + colIndex;
    }
}
