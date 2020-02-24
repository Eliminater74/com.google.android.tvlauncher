package androidx.leanback.widget;

public class HeaderItem {
    private CharSequence mContentDescription;
    private CharSequence mDescription;
    private final long mId;
    private final String mName;

    public HeaderItem(long id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public HeaderItem(String name) {
        this(-1, name);
    }

    public final long getId() {
        return this.mId;
    }

    public final String getName() {
        return this.mName;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(CharSequence contentDescription) {
        this.mContentDescription = contentDescription;
    }

    public void setDescription(CharSequence description) {
        this.mDescription = description;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }
}
