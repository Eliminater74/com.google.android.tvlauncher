package com.google.android.tvlauncher.data;

import android.database.Cursor;

class ProgramsDataBuffer extends AbstractDataBuffer<ProgramRef> {
    ProgramsDataBuffer(Cursor cursor) {
        super(cursor);
    }

    public ProgramRef get(int position) {
        return new ProgramRef(this, position);
    }
}
