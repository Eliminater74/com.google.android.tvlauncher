package com.google.android.gsf;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class GoogleLoginCredentialsResult implements Parcelable {
    public static final Parcelable.Creator<GoogleLoginCredentialsResult> CREATOR = new Parcelable.Creator<GoogleLoginCredentialsResult>() {
        public GoogleLoginCredentialsResult createFromParcel(Parcel in) {
            return new GoogleLoginCredentialsResult(in);
        }

        public GoogleLoginCredentialsResult[] newArray(int size) {
            return new GoogleLoginCredentialsResult[size];
        }
    };
    private String mAccount;
    private Intent mCredentialsIntent;
    private String mCredentialsString;

    public String getAccount() {
        return this.mAccount;
    }

    public String getCredentialsString() {
        return this.mCredentialsString;
    }

    public Intent getCredentialsIntent() {
        return this.mCredentialsIntent;
    }

    public GoogleLoginCredentialsResult() {
        this.mCredentialsString = null;
        this.mCredentialsIntent = null;
        this.mAccount = null;
    }

    public void setCredentialsString(String s) {
        this.mCredentialsString = s;
    }

    public void setCredentialsIntent(Intent intent) {
        this.mCredentialsIntent = intent;
    }

    public void setAccount(String account) {
        this.mAccount = account;
    }

    public int describeContents() {
        Intent intent = this.mCredentialsIntent;
        if (intent != null) {
            return intent.describeContents();
        }
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mAccount);
        out.writeString(this.mCredentialsString);
        if (this.mCredentialsIntent != null) {
            out.writeInt(1);
            this.mCredentialsIntent.writeToParcel(out, 0);
            return;
        }
        out.writeInt(0);
    }

    private GoogleLoginCredentialsResult(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.mAccount = in.readString();
        this.mCredentialsString = in.readString();
        int hasIntent = in.readInt();
        this.mCredentialsIntent = null;
        if (hasIntent == 1) {
            this.mCredentialsIntent = new Intent();
            this.mCredentialsIntent.readFromParcel(in);
            this.mCredentialsIntent.setExtrasClassLoader(getClass().getClassLoader());
        }
    }
}
