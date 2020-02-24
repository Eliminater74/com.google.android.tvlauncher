package com.google.android.libraries.social.analytics.visualelement;

import com.google.android.libraries.stitch.util.Objects;
import java.io.Serializable;
import java.util.Locale;

public final class IndexedVisualElement extends VisualElement implements Indexed, Serializable {
    private static final long serialVersionUID = 1;
    private final int visualElementIndex;

    public IndexedVisualElement(VisualElementTag tag, int visualElementIndex2) {
        super(tag);
        this.visualElementIndex = visualElementIndex2;
    }

    public Integer getIndex() {
        return Integer.valueOf(this.visualElementIndex);
    }

    public boolean equals(Object o) {
        if (!super.equals(o) || this.visualElementIndex != ((IndexedVisualElement) o).visualElementIndex) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(super.hashCode(), this.visualElementIndex);
    }

    public String toString() {
        return String.format(Locale.US, "IndexedVisualElement {tag: %s, index: %d}", this.tag, Integer.valueOf(this.visualElementIndex));
    }
}
