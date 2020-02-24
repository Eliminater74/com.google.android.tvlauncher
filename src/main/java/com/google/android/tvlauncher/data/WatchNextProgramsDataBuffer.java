package com.google.android.tvlauncher.data;

import android.database.Cursor;

public class WatchNextProgramsDataBuffer extends AbstractDataBuffer<ProgramRef> {
    private int mStartIndex = 0;

    public /* bridge */ /* synthetic */ byte[] getBlob(int i, int i2) {
        return super.getBlob(i, i2);
    }

    public /* bridge */ /* synthetic */ boolean isReleased() {
        return super.isReleased();
    }

    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    public WatchNextProgramsDataBuffer(Cursor cursor) {
        super(cursor);
        calculateStartIndex();
    }

    public ProgramRef get(int position) {
        return new ProgramRef(this, position);
    }

    public long getLong(int row, int column) {
        return super.getLong(this.mStartIndex + row, column);
    }

    public int getInt(int row, int column) {
        return super.getInt(this.mStartIndex + row, column);
    }

    public String getString(int row, int column) {
        return super.getString(this.mStartIndex + row, column);
    }

    public int getCount() {
        return super.getCount() - this.mStartIndex;
    }

    /* access modifiers changed from: package-private */
    public boolean refresh() {
        int oldStartIndex = this.mStartIndex;
        calculateStartIndex();
        return oldStartIndex != this.mStartIndex;
    }

    private void calculateStartIndex() {
        if (this.mCursor == null || this.mCursor.getCount() == 0) {
            this.mStartIndex = 0;
            return;
        }
        long currentTime = System.currentTimeMillis();
        int savedCursorPos = this.mCursor.getPosition();
        this.mCursor.moveToFirst();
        int pos = 0;
        while (Long.valueOf(this.mCursor.getLong(42)).compareTo(Long.valueOf(currentTime)) > 0) {
            pos++;
            if (!this.mCursor.moveToNext()) {
                break;
            }
        }
        this.mCursor.moveToPosition(savedCursorPos);
        this.mStartIndex = pos;
    }
}
