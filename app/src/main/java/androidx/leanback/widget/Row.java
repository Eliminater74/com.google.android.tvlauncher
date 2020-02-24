package androidx.leanback.widget;

public class Row {
    private static final int FLAG_ID_USE_HEADER = 1;
    private static final int FLAG_ID_USE_ID = 0;
    private static final int FLAG_ID_USE_MASK = 1;
    private int mFlags = 1;
    private HeaderItem mHeaderItem;
    private long mId = -1;

    public Row(long id, HeaderItem headerItem) {
        setId(id);
        setHeaderItem(headerItem);
    }

    public Row(HeaderItem headerItem) {
        setHeaderItem(headerItem);
    }

    public Row() {
    }

    public final HeaderItem getHeaderItem() {
        return this.mHeaderItem;
    }

    public final void setHeaderItem(HeaderItem headerItem) {
        this.mHeaderItem = headerItem;
    }

    public final void setId(long id) {
        this.mId = id;
        setFlags(0, 1);
    }

    public final long getId() {
        if ((this.mFlags & 1) != 1) {
            return this.mId;
        }
        HeaderItem header = getHeaderItem();
        if (header != null) {
            return header.getId();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public final void setFlags(int flags, int mask) {
        this.mFlags = (this.mFlags & (mask ^ -1)) | (flags & mask);
    }

    /* access modifiers changed from: package-private */
    public final int getFlags() {
        return this.mFlags;
    }

    public boolean isRenderedAsRowView() {
        return true;
    }
}
