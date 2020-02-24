package androidx.versionedparcelable;

import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class CustomVersionedParcelable implements VersionedParcelable {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onPreParceling(boolean isStream) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onPostParceling() {
    }
}
