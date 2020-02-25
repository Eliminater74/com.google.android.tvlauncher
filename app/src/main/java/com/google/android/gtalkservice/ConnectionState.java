package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;

public final class ConnectionState implements Parcelable {
    public static final int CONNECTING = 2;
    public static final Parcelable.Creator<ConnectionState> CREATOR = new Parcelable.Creator<ConnectionState>() {
        public ConnectionState createFromParcel(Parcel source) {
            return new ConnectionState(source);
        }

        public ConnectionState[] newArray(int size) {
            return new ConnectionState[size];
        }
    };
    public static final int IDLE = 0;
    public static final int LOGGED_IN = 3;
    public static final int ONLINE = 4;
    public static final int PENDING = 1;
    private volatile int mState;

    public ConnectionState(int state) {
        setState(state);
    }

    public ConnectionState(Parcel source) {
        this.mState = source.readInt();
    }

    public static final String toString(int state) {
        if (state == 1) {
            return "RECONNECTION_SCHEDULED";
        }
        if (state == 2) {
            return "CONNECTING";
        }
        if (state == 3) {
            return "AUTHENTICATED";
        }
        if (state != 4) {
            return "IDLE";
        }
        return "ONLINE";
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public boolean isLoggingIn() {
        return this.mState == 2;
    }

    public boolean isLoggedIn() {
        return this.mState >= 3;
    }

    public boolean isOnline() {
        return this.mState == 4;
    }

    public boolean isDisconnected() {
        return this.mState == 0 || this.mState == 1;
    }

    public boolean isPendingReconnect() {
        return this.mState == 1;
    }

    public final String toString() {
        return toString(this.mState);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mState);
    }

    public int describeContents() {
        return 0;
    }
}
