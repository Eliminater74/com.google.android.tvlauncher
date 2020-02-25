package android.support.p001v4.view;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressLint({"BanParcelableUsage"})
/* renamed from: android.support.v4.view.AbsSavedState */
public abstract class AbsSavedState implements Parcelable {
    public static final AbsSavedState EMPTY_STATE = new AbsSavedState() {
    };
    public static final Parcelable.Creator<AbsSavedState> CREATOR = new Parcelable.ClassLoaderCreator<AbsSavedState>() {
        public AbsSavedState createFromParcel(Parcel in, ClassLoader loader) {
            if (in.readParcelable(loader) == null) {
                return AbsSavedState.EMPTY_STATE;
            }
            throw new IllegalStateException("superState must be null");
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: android.support.v4.view.AbsSavedState.2.createFromParcel(android.os.Parcel, java.lang.ClassLoader):android.support.v4.view.AbsSavedState
         arg types: [android.os.Parcel, ?[OBJECT, ARRAY]]
         candidates:
          android.support.v4.view.AbsSavedState.2.createFromParcel(android.os.Parcel, java.lang.ClassLoader):java.lang.Object
          ClspMth{android.os.Parcelable.ClassLoaderCreator.createFromParcel(android.os.Parcel, java.lang.ClassLoader):T}
          android.support.v4.view.AbsSavedState.2.createFromParcel(android.os.Parcel, java.lang.ClassLoader):android.support.v4.view.AbsSavedState */
        public AbsSavedState createFromParcel(Parcel in) {
            return createFromParcel(in, (ClassLoader) null);
        }

        public AbsSavedState[] newArray(int size) {
            return new AbsSavedState[size];
        }
    };
    private final Parcelable mSuperState;

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(@NonNull Parcelable superState) {
        if (superState != null) {
            this.mSuperState = superState != EMPTY_STATE ? superState : null;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    protected AbsSavedState(@NonNull Parcel source) {
        this(source, null);
    }

    protected AbsSavedState(@NonNull Parcel source, @Nullable ClassLoader loader) {
        Parcelable superState = source.readParcelable(loader);
        this.mSuperState = superState != null ? superState : EMPTY_STATE;
    }

    @Nullable
    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSuperState, flags);
    }
}
