package com.google.android.tvlauncher.data;

import android.database.Cursor;

abstract class AbstractDataBuffer<T> {
    protected Cursor mCursor;

    public abstract T get(int i);

    AbstractDataBuffer(Cursor cursor) {
        this.mCursor = cursor;
    }

    public void release() {
        this.mCursor.close();
    }

    public boolean isReleased() {
        return this.mCursor.isClosed();
    }

    public long getLong(int row, int column) {
        checkNotReleased();
        this.mCursor.moveToPosition(row);
        return this.mCursor.getLong(column);
    }

    public int getInt(int row, int column) {
        checkNotReleased();
        this.mCursor.moveToPosition(row);
        return this.mCursor.getInt(column);
    }

    public String getString(int row, int column) {
        checkNotReleased();
        this.mCursor.moveToPosition(row);
        return this.mCursor.getString(column);
    }

    public byte[] getBlob(int row, int column) {
        checkNotReleased();
        this.mCursor.moveToPosition(row);
        return this.mCursor.getBlob(column);
    }

    public int getCount() {
        checkNotReleased();
        return this.mCursor.getCount();
    }

    private void checkNotReleased() {
        if (isReleased()) {
            throw new IllegalArgumentException("Buffer is released.");
        }
    }
}
