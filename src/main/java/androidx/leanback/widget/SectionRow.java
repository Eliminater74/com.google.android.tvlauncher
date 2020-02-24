package androidx.leanback.widget;

public class SectionRow extends Row {
    public SectionRow(HeaderItem headerItem) {
        super(headerItem);
    }

    public SectionRow(long id, String name) {
        super(new HeaderItem(id, name));
    }

    public SectionRow(String name) {
        super(new HeaderItem(name));
    }

    public final boolean isRenderedAsRowView() {
        return false;
    }
}
