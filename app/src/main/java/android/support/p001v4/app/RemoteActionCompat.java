package android.support.p001v4.app;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.graphics.drawable.IconCompat;
import android.support.p001v4.util.Preconditions;

import androidx.versionedparcelable.VersionedParcelable;

/* renamed from: android.support.v4.app.RemoteActionCompat */
public final class RemoteActionCompat implements VersionedParcelable {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PendingIntent mActionIntent;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CharSequence mContentDescription;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean mEnabled;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public IconCompat mIcon;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean mShouldShowIcon;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CharSequence mTitle;

    public RemoteActionCompat(@NonNull IconCompat icon, @NonNull CharSequence title, @NonNull CharSequence contentDescription, @NonNull PendingIntent intent) {
        this.mIcon = (IconCompat) Preconditions.checkNotNull(icon);
        this.mTitle = (CharSequence) Preconditions.checkNotNull(title);
        this.mContentDescription = (CharSequence) Preconditions.checkNotNull(contentDescription);
        this.mActionIntent = (PendingIntent) Preconditions.checkNotNull(intent);
        this.mEnabled = true;
        this.mShouldShowIcon = true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteActionCompat() {
    }

    public RemoteActionCompat(@NonNull RemoteActionCompat other) {
        Preconditions.checkNotNull(other);
        this.mIcon = other.mIcon;
        this.mTitle = other.mTitle;
        this.mContentDescription = other.mContentDescription;
        this.mActionIntent = other.mActionIntent;
        this.mEnabled = other.mEnabled;
        this.mShouldShowIcon = other.mShouldShowIcon;
    }

    @RequiresApi(26)
    @NonNull
    public static RemoteActionCompat createFromRemoteAction(@NonNull RemoteAction remoteAction) {
        Preconditions.checkNotNull(remoteAction);
        RemoteActionCompat action = new RemoteActionCompat(IconCompat.createFromIcon(remoteAction.getIcon()), remoteAction.getTitle(), remoteAction.getContentDescription(), remoteAction.getActionIntent());
        action.setEnabled(remoteAction.isEnabled());
        if (Build.VERSION.SDK_INT >= 28) {
            action.setShouldShowIcon(remoteAction.shouldShowIcon());
        }
        return action;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public void setShouldShowIcon(boolean shouldShowIcon) {
        this.mShouldShowIcon = shouldShowIcon;
    }

    public boolean shouldShowIcon() {
        return this.mShouldShowIcon;
    }

    @NonNull
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @NonNull
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @NonNull
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @NonNull
    public PendingIntent getActionIntent() {
        return this.mActionIntent;
    }

    @RequiresApi(26)
    @NonNull
    public RemoteAction toRemoteAction() {
        RemoteAction action = new RemoteAction(this.mIcon.toIcon(), this.mTitle, this.mContentDescription, this.mActionIntent);
        action.setEnabled(isEnabled());
        if (Build.VERSION.SDK_INT >= 28) {
            action.setShouldShowIcon(shouldShowIcon());
        }
        return action;
    }
}
