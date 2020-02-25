package com.google.android.libraries.social.analytics.visualelement;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.Serializable;
import java.util.Locale;

public class VisualElement implements Serializable {
    private static final long serialVersionUID = 1;
    public final VisualElementTag tag;

    public VisualElement(VisualElementTag constant) {
        if (constant != null) {
            this.tag = constant;
            return;
        }
        throw new NullPointerException();
    }

    public int getVisualElementId() {
        return this.tag.f123id;
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.tag.equals(((VisualElement) o).tag);
    }

    public int hashCode() {
        return this.tag.hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE;
    }

    public String toString() {
        return String.format(Locale.US, "VisualElement {tag: %s}", this.tag);
    }
}
