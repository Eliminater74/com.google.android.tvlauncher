package androidx.leanback.database;

import android.database.Cursor;

public abstract class CursorMapper {
    private Cursor mCursor;

    /* access modifiers changed from: protected */
    public abstract Object bind(Cursor cursor);

    /* access modifiers changed from: protected */
    public abstract void bindColumns(Cursor cursor);

    public Object convert(Cursor cursor) {
        if (cursor != this.mCursor) {
            this.mCursor = cursor;
            bindColumns(this.mCursor);
        }
        return bind(this.mCursor);
    }
}
