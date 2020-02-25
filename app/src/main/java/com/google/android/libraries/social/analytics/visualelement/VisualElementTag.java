package com.google.android.libraries.social.analytics.visualelement;

import com.google.errorprone.annotations.Immutable;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.Serializable;
import java.util.Locale;

@Immutable
public final class VisualElementTag implements Serializable {
    private static final long serialVersionUID = 1;
    public final Class expectedClass;

    /* renamed from: id */
    public final int f123id;
    public final boolean isRootPage;

    public VisualElementTag(int id) {
        this(id, false, VisualElement.class);
    }

    public VisualElementTag(int id, boolean isRootPage2, Class expectedClass2) {
        this.f123id = id;
        this.isRootPage = isRootPage2;
        this.expectedClass = expectedClass2;
    }

    public String toString() {
        return String.format(Locale.US, "VisualElementTag {id: %d, isRootPage: %b}", Integer.valueOf(this.f123id), Boolean.valueOf(this.isRootPage));
    }

    public boolean equals(Object o) {
        return this == o || ((o instanceof VisualElementTag) && this.f123id == ((VisualElementTag) o).f123id);
    }

    public int hashCode() {
        return this.f123id + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE;
    }
}
