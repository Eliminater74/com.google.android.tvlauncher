package com.google.android.tvlauncher.inputs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.tv.TvContract;
import android.media.tv.TvInputInfo;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.tvlauncher.C1188R;

import java.util.Comparator;
import java.util.Objects;

@VisibleForTesting(otherwise = 3)
public class TvInputEntry {
    /* access modifiers changed from: private */
    public final TvInputInfo mInfo;
    /* access modifiers changed from: private */
    public final int mType;
    private final HdmiDeviceInfo mHdmiInfo;
    private final Uri mIconUri;
    private final String mId;
    private final TvInputEntry mParentEntry;
    /* access modifiers changed from: private */
    public String mLabel;
    /* access modifiers changed from: private */
    public int mPriority;
    /* access modifiers changed from: private */
    public int mSortKey;
    /* access modifiers changed from: private */
    public String mSortingParentLabel;
    /* access modifiers changed from: private */
    public int mState;
    private Drawable mIcon;
    private int mIconState;
    private int mNumChildren;
    private String mParentLabel;

    TvInputEntry(String id, int type, String label, Uri iconUri) {
        this.mId = id;
        this.mParentEntry = null;
        this.mInfo = null;
        this.mHdmiInfo = null;
        this.mType = type;
        this.mIconUri = iconUri;
        this.mIconState = 0;
        this.mState = 0;
        this.mLabel = label != null ? label : "";
        this.mSortKey = Integer.MAX_VALUE;
    }

    TvInputEntry(TvInputInfo info, TvInputEntry parent, int state) {
        this.mId = info.getId();
        this.mParentEntry = parent;
        this.mInfo = info;
        this.mType = getTypeFromTifInput(this.mInfo);
        this.mIconUri = null;
        if (this.mInfo.getType() == 1007) {
            this.mHdmiInfo = this.mInfo.getHdmiDeviceInfo();
        } else {
            this.mHdmiInfo = null;
        }
        this.mState = state;
    }

    /* access modifiers changed from: package-private */
    public void init(Context context) {
        this.mPriority = TifInputsManager.getInstance(context).getPriorityForType(this.mType);
        TvInputInfo tvInputInfo = this.mInfo;
        if (tvInputInfo != null) {
            CharSequence label = tvInputInfo.loadCustomLabel(context);
            if (TextUtils.isEmpty(label)) {
                label = this.mInfo.loadLabel(context);
            }
            if (label != null) {
                this.mLabel = label.toString();
            } else {
                this.mLabel = "";
            }
            this.mSortKey = this.mInfo.getServiceInfo().metaData.getInt("input_sort_key", Integer.MAX_VALUE);
            TvInputEntry tvInputEntry = this.mParentEntry;
            if (tvInputEntry != null) {
                this.mParentLabel = tvInputEntry.getLabel();
                this.mSortingParentLabel = this.mParentLabel;
            } else {
                this.mSortingParentLabel = this.mLabel;
            }
            this.mIcon = getImageDrawable(context, this.mState);
        }
    }

    /* access modifiers changed from: package-private */
    public void preloadIcon(Context context) {
        if (this.mIconUri != null) {
            int iconMaxSize = context.getResources().getDimensionPixelSize(C1188R.dimen.input_icon_view_size);
            Glide.with(context).load(this.mIconUri).apply((BaseRequestOptions<?>) ((RequestOptions) ((RequestOptions) new RequestOptions().override(iconMaxSize, iconMaxSize)).centerInside())).preload();
        }
    }

    /* access modifiers changed from: package-private */
    public String getId() {
        return this.mId;
    }

    /* access modifiers changed from: package-private */
    public TvInputEntry getParentEntry() {
        return this.mParentEntry;
    }

    /* access modifiers changed from: package-private */
    public TvInputInfo getInfo() {
        return this.mInfo;
    }

    /* access modifiers changed from: package-private */
    public int getType() {
        return this.mType;
    }

    /* access modifiers changed from: package-private */
    public Uri getIconUri() {
        return this.mIconUri;
    }

    /* access modifiers changed from: package-private */
    public int getState() {
        return this.mState;
    }

    /* access modifiers changed from: package-private */
    public void setState(int state) {
        this.mState = state;
    }

    /* access modifiers changed from: package-private */
    public String getLabel() {
        HdmiDeviceInfo hdmiDeviceInfo = this.mHdmiInfo;
        if (hdmiDeviceInfo == null || TextUtils.isEmpty(hdmiDeviceInfo.getDisplayName())) {
            return this.mLabel;
        }
        return this.mHdmiInfo.getDisplayName();
    }

    /* access modifiers changed from: package-private */
    public void setLabel(String label) {
        this.mLabel = label;
    }

    /* access modifiers changed from: package-private */
    public int getNumChildren() {
        return this.mNumChildren;
    }

    /* access modifiers changed from: package-private */
    public void setNumChildren(int numChildren) {
        this.mNumChildren = numChildren;
    }

    /* access modifiers changed from: package-private */
    public String getParentLabel() {
        return this.mParentLabel;
    }

    /* access modifiers changed from: package-private */
    public Drawable getIcon() {
        return this.mIcon;
    }

    /* access modifiers changed from: package-private */
    public boolean isBundledTuner() {
        return this.mType == -3;
    }

    /* access modifiers changed from: package-private */
    public boolean isConnected() {
        return isCustomTifInput() || this.mState != 2;
    }

    /* access modifiers changed from: package-private */
    public boolean isStandby() {
        return this.mState == 1;
    }

    /* access modifiers changed from: package-private */
    public boolean isDisconnected() {
        return !isConnected();
    }

    /* access modifiers changed from: package-private */
    public Drawable getImageDrawable(Context context, int newState) {
        Drawable drawable = this.mIcon;
        if (drawable != null && this.mState == this.mIconState) {
            return drawable;
        }
        this.mIconState = newState;
        if (this.mInfo != null) {
            this.mIcon = loadIcon(context);
            Drawable drawable2 = this.mIcon;
            if (drawable2 != null) {
                return drawable2;
            }
        }
        Integer drawableId = InputsManagerUtil.getIconResourceId(this.mType);
        if (drawableId == null) {
            drawableId = Integer.valueOf(C1188R.C1189drawable.ic_icon_32dp_hdmi);
        }
        Drawable drawable3 = ContextCompat.getDrawable(context, drawableId.intValue());
        this.mIcon = drawable3;
        return drawable3;
    }

    /* access modifiers changed from: package-private */
    public Intent getLaunchIntent() {
        TvInputInfo tvInputInfo = this.mInfo;
        if (tvInputInfo == null) {
            int i = this.mType;
            if (i == -7) {
                return new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME");
            }
            if (i == -3) {
                return new Intent("android.intent.action.VIEW", TvContract.Channels.CONTENT_URI);
            }
            return null;
        } else if (tvInputInfo.isPassthroughInput()) {
            return new Intent("android.intent.action.VIEW", TvContract.buildChannelUriForPassthroughInput(this.mInfo.getId()));
        } else {
            return new Intent("android.intent.action.VIEW", TvContract.buildChannelsUriForInput(this.mInfo.getId()));
        }
    }

    private Drawable loadIcon(Context context) {
        Drawable icon;
        if (!TifInputsManager.getInstance(context).shouldGetStateIconFromTVInput() || (icon = this.mInfo.loadIcon(context, this.mState)) == null) {
            return this.mInfo.loadIcon(context);
        }
        return icon;
    }

    private int getTypeFromTifInput(TvInputInfo info) {
        if (info.getHdmiDeviceInfo() != null && info.getHdmiDeviceInfo().isCecDevice()) {
            int cecDeviceType = info.getHdmiDeviceInfo().getDeviceType();
            if (cecDeviceType == 0) {
                return -8;
            }
            if (cecDeviceType == 1) {
                return -4;
            }
            if (cecDeviceType == 3) {
                return -10;
            }
            if (cecDeviceType == 4) {
                return -5;
            }
            if (cecDeviceType != 5) {
                return -2;
            }
            return -9;
        } else if (info.getHdmiDeviceInfo() == null || !info.getHdmiDeviceInfo().isMhlDevice()) {
            return info.getType();
        } else {
            return -6;
        }
    }

    private boolean isCustomTifInput() {
        int i = this.mType;
        return i == -3 || i == -7;
    }

    public boolean equals(Object o) {
        TvInputInfo tvInputInfo;
        if (o == this) {
            return true;
        }
        if (!(o instanceof TvInputEntry)) {
            return false;
        }
        TvInputEntry obj = (TvInputEntry) o;
        if (isCustomTifInput() && obj.isCustomTifInput() && this.mType == obj.mType) {
            return true;
        }
        TvInputInfo tvInputInfo2 = this.mInfo;
        if (tvInputInfo2 == null || (tvInputInfo = obj.mInfo) == null || !tvInputInfo2.equals(tvInputInfo)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), this.mInfo);
    }

    static class InputsComparator implements Comparator<TvInputEntry> {
        private Context mContext;

        InputsComparator(Context context) {
            this.mContext = context;
        }

        public int compare(TvInputEntry lhs, TvInputEntry rhs) {
            boolean lIsPhysical;
            boolean disconnectedR = false;
            if (rhs == null) {
                if (lhs == null) {
                    return 0;
                }
                return -1;
            } else if (lhs == null) {
                return 1;
            } else {
                if (TifInputsManager.getInstance(this.mContext).shouldDisableDisconnectedInputs()) {
                    boolean disconnectedL = lhs.mState == 2;
                    if (rhs.mState == 2) {
                        disconnectedR = true;
                    }
                    if (disconnectedL != disconnectedR) {
                        if (disconnectedL) {
                            return 1;
                        }
                        return -1;
                    }
                }
                if (lhs.mPriority != rhs.mPriority) {
                    return lhs.mPriority - rhs.mPriority;
                }
                if (lhs.mType == 0 && rhs.mType == 0 && TifInputsManager.isPhysicalTuner(this.mContext.getPackageManager(), rhs.mInfo) != (lIsPhysical = TifInputsManager.isPhysicalTuner(this.mContext.getPackageManager(), lhs.mInfo))) {
                    if (lIsPhysical) {
                        return -1;
                    }
                    return 1;
                } else if (lhs.mSortKey != rhs.mSortKey) {
                    return rhs.mSortKey - lhs.mSortKey;
                } else {
                    if (!TextUtils.equals(lhs.mSortingParentLabel, rhs.mSortingParentLabel)) {
                        return lhs.mSortingParentLabel.compareToIgnoreCase(rhs.mSortingParentLabel);
                    }
                    return lhs.mLabel.compareToIgnoreCase(rhs.mLabel);
                }
            }
        }
    }
}
