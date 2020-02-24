package androidx.leanback.widget;

import android.database.Cursor;
import android.util.LruCache;
import androidx.leanback.database.CursorMapper;

public class CursorObjectAdapter extends ObjectAdapter {
    private static final int CACHE_SIZE = 100;
    private Cursor mCursor;
    private final LruCache<Integer, Object> mItemCache = new LruCache<>(100);
    private CursorMapper mMapper;

    public CursorObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public CursorObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public CursorObjectAdapter() {
    }

    public void changeCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor != cursor2) {
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mCursor = cursor;
            this.mItemCache.trimToSize(0);
            onCursorChanged();
        }
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return cursor2;
        }
        Cursor oldCursor = this.mCursor;
        this.mCursor = cursor;
        this.mItemCache.trimToSize(0);
        onCursorChanged();
        return oldCursor;
    }

    /* access modifiers changed from: protected */
    public void onCursorChanged() {
        notifyChanged();
    }

    public final Cursor getCursor() {
        return this.mCursor;
    }

    public final void setMapper(CursorMapper mapper) {
        boolean changed = this.mMapper != mapper;
        this.mMapper = mapper;
        if (changed) {
            onMapperChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void onMapperChanged() {
    }

    public final CursorMapper getMapper() {
        return this.mMapper;
    }

    public int size() {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public Object get(int index) {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToPosition(index)) {
            Object item = this.mItemCache.get(Integer.valueOf(index));
            if (item != null) {
                return item;
            }
            Object item2 = this.mMapper.convert(this.mCursor);
            this.mItemCache.put(Integer.valueOf(index), item2);
            return item2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void close() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            cursor.close();
            this.mCursor = null;
        }
    }

    public boolean isClosed() {
        Cursor cursor = this.mCursor;
        return cursor == null || cursor.isClosed();
    }

    /* access modifiers changed from: protected */
    public final void invalidateCache(int index) {
        this.mItemCache.remove(Integer.valueOf(index));
    }

    /* access modifiers changed from: protected */
    public final void invalidateCache(int index, int count) {
        int limit = count + index;
        while (index < limit) {
            invalidateCache(index);
            index++;
        }
    }

    public boolean isImmediateNotifySupported() {
        return true;
    }
}
