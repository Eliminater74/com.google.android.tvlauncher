package androidx.leanback.widget;

public class ListRow extends Row {
    private final ObjectAdapter mAdapter;
    private CharSequence mContentDescription;

    public ListRow(HeaderItem header, ObjectAdapter adapter) {
        super(header);
        this.mAdapter = adapter;
        verify();
    }

    public ListRow(long id, HeaderItem header, ObjectAdapter adapter) {
        super(id, header);
        this.mAdapter = adapter;
        verify();
    }

    public ListRow(ObjectAdapter adapter) {
        this.mAdapter = adapter;
        verify();
    }

    public final ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    private void verify() {
        if (this.mAdapter == null) {
            throw new IllegalArgumentException("ObjectAdapter cannot be null");
        }
    }

    public CharSequence getContentDescription() {
        CharSequence charSequence = this.mContentDescription;
        if (charSequence != null) {
            return charSequence;
        }
        HeaderItem headerItem = getHeaderItem();
        if (headerItem == null) {
            return null;
        }
        CharSequence contentDescription = headerItem.getContentDescription();
        if (contentDescription != null) {
            return contentDescription;
        }
        return headerItem.getName();
    }

    public void setContentDescription(CharSequence contentDescription) {
        this.mContentDescription = contentDescription;
    }
}
