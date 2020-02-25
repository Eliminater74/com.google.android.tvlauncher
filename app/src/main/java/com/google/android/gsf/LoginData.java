package com.google.android.gsf;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginData implements Parcelable {
    public static final Parcelable.Creator<LoginData> CREATOR = new Parcelable.Creator<LoginData>() {
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
    public String mAuthtoken;
    public String mCaptchaAnswer;
    public byte[] mCaptchaData;
    public String mCaptchaMimeType;
    public String mCaptchaToken;
    public String mEncryptedPassword;
    public int mFlags;
    public String mJsonString;
    public String mOAuthAccessToken;
    public String mPassword;
    public String mService;
    public String mSid;
    public Status mStatus;
    public String mUsername;

    public LoginData() {
        this.mUsername = null;
        this.mEncryptedPassword = null;
        this.mPassword = null;
        this.mService = null;
        this.mCaptchaToken = null;
        this.mCaptchaData = null;
        this.mCaptchaMimeType = null;
        this.mCaptchaAnswer = null;
        this.mFlags = 0;
        this.mStatus = null;
        this.mJsonString = null;
        this.mSid = null;
        this.mAuthtoken = null;
        this.mOAuthAccessToken = null;
    }

    public LoginData(LoginData other) {
        this.mUsername = null;
        this.mEncryptedPassword = null;
        this.mPassword = null;
        this.mService = null;
        this.mCaptchaToken = null;
        this.mCaptchaData = null;
        this.mCaptchaMimeType = null;
        this.mCaptchaAnswer = null;
        this.mFlags = 0;
        this.mStatus = null;
        this.mJsonString = null;
        this.mSid = null;
        this.mAuthtoken = null;
        this.mOAuthAccessToken = null;
        this.mUsername = other.mUsername;
        this.mEncryptedPassword = other.mEncryptedPassword;
        this.mPassword = other.mPassword;
        this.mService = other.mService;
        this.mCaptchaToken = other.mCaptchaToken;
        this.mCaptchaData = other.mCaptchaData;
        this.mCaptchaMimeType = other.mCaptchaMimeType;
        this.mCaptchaAnswer = other.mCaptchaAnswer;
        this.mFlags = other.mFlags;
        this.mStatus = other.mStatus;
        this.mJsonString = other.mJsonString;
        this.mSid = other.mSid;
        this.mAuthtoken = other.mAuthtoken;
        this.mOAuthAccessToken = other.mOAuthAccessToken;
    }

    private LoginData(Parcel in) {
        this.mUsername = null;
        this.mEncryptedPassword = null;
        this.mPassword = null;
        this.mService = null;
        this.mCaptchaToken = null;
        this.mCaptchaData = null;
        this.mCaptchaMimeType = null;
        this.mCaptchaAnswer = null;
        this.mFlags = 0;
        this.mStatus = null;
        this.mJsonString = null;
        this.mSid = null;
        this.mAuthtoken = null;
        this.mOAuthAccessToken = null;
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mUsername);
        out.writeString(this.mEncryptedPassword);
        out.writeString(this.mPassword);
        out.writeString(this.mService);
        out.writeString(this.mCaptchaToken);
        byte[] bArr = this.mCaptchaData;
        if (bArr == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(bArr.length);
            out.writeByteArray(this.mCaptchaData);
        }
        out.writeString(this.mCaptchaMimeType);
        out.writeString(this.mCaptchaAnswer);
        out.writeInt(this.mFlags);
        Status status = this.mStatus;
        if (status == null) {
            out.writeString(null);
        } else {
            out.writeString(status.name());
        }
        out.writeString(this.mJsonString);
        out.writeString(this.mSid);
        out.writeString(this.mAuthtoken);
        out.writeString(this.mOAuthAccessToken);
    }

    public void readFromParcel(Parcel in) {
        this.mUsername = in.readString();
        this.mEncryptedPassword = in.readString();
        this.mPassword = in.readString();
        this.mService = in.readString();
        this.mCaptchaToken = in.readString();
        int len = in.readInt();
        if (len == -1) {
            this.mCaptchaData = null;
        } else {
            this.mCaptchaData = new byte[len];
            in.readByteArray(this.mCaptchaData);
        }
        this.mCaptchaMimeType = in.readString();
        this.mCaptchaAnswer = in.readString();
        this.mFlags = in.readInt();
        String status = in.readString();
        if (status == null) {
            this.mStatus = null;
        } else {
            this.mStatus = Status.valueOf(status);
        }
        this.mJsonString = in.readString();
        this.mSid = in.readString();
        this.mAuthtoken = in.readString();
        this.mOAuthAccessToken = in.readString();
    }

    public String dump() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("         status: ");
        sb.append(this.mStatus);
        sb.append("\n       username: ");
        sb.append(this.mUsername);
        sb.append("\n       password: ");
        sb.append(this.mPassword);
        sb.append("\n   enc password: ");
        sb.append(this.mEncryptedPassword);
        sb.append("\n        service: ");
        sb.append(this.mService);
        sb.append("\n      authtoken: ");
        sb.append(this.mAuthtoken);
        sb.append("\n      oauthAccessToken: ");
        sb.append(this.mOAuthAccessToken);
        sb.append("\n   captchatoken: ");
        sb.append(this.mCaptchaToken);
        sb.append("\n  captchaanswer: ");
        sb.append(this.mCaptchaAnswer);
        sb.append("\n    captchadata: ");
        if (this.mCaptchaData == null) {
            str = "null";
        } else {
            str = Integer.toString(this.mCaptchaData.length) + " bytes";
        }
        sb.append(str);
        return sb.toString();
    }

    public enum Status {
        SUCCESS,
        ACCOUNT_DISABLED,
        BAD_USERNAME,
        BAD_REQUEST,
        LOGIN_FAIL,
        SERVER_ERROR,
        MISSING_APPS,
        NO_GMAIL,
        NETWORK_ERROR,
        CAPTCHA,
        CANCELLED,
        DELETED_GMAIL,
        OAUTH_MIGRATION_REQUIRED,
        DMAGENT
    }
}
