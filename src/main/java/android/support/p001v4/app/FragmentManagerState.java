package android.support.p001v4.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
/* renamed from: android.support.v4.app.FragmentManagerState */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() {
        public FragmentManagerState createFromParcel(Parcel in) {
            return new FragmentManagerState(in);
        }

        public FragmentManagerState[] newArray(int size) {
            return new FragmentManagerState[size];
        }
    };
    ArrayList<FragmentState> mActive;
    ArrayList<String> mAdded;
    BackStackState[] mBackStack;
    int mNextFragmentIndex;
    String mPrimaryNavActiveWho = null;

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel in) {
        this.mActive = in.createTypedArrayList(FragmentState.CREATOR);
        this.mAdded = in.createStringArrayList();
        this.mBackStack = (BackStackState[]) in.createTypedArray(BackStackState.CREATOR);
        this.mPrimaryNavActiveWho = in.readString();
        this.mNextFragmentIndex = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mActive);
        dest.writeStringList(this.mAdded);
        dest.writeTypedArray(this.mBackStack, flags);
        dest.writeString(this.mPrimaryNavActiveWho);
        dest.writeInt(this.mNextFragmentIndex);
    }
}
