package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public final class Presence implements Parcelable {
    public static final Parcelable.Creator<Presence> CREATOR = new Parcelable.Creator<Presence>() {
        public Presence createFromParcel(Parcel source) {
            return new Presence(source);
        }

        public Presence[] newArray(int size) {
            return new Presence[size];
        }
    };
    public static final int HAS_CAMERA_V1 = 4;
    public static final int HAS_PMUC_V1 = 8;
    public static final int HAS_VIDEO_V1 = 2;
    public static final int HAS_VOICE_V1 = 1;
    public static final int NO_CAPABILITIES = 0;
    public static final Presence OFFLINE = new Presence();
    private static final int STATUS_MIN_VERSION_FOR_INVISIBILITY = 2;
    private boolean mAllowInvisibility;
    private boolean mAvailable;
    private int mCapabilities;
    private List<String> mDefaultStatusList;
    private List<String> mDndStatusList;
    private boolean mInvisible;
    private Show mShow;
    private String mStatus;
    private int mStatusListContentsMax;
    private int mStatusListMax;
    private int mStatusMax;

    public enum Show {
        NONE,
        AWAY,
        EXTENDED_AWAY,
        DND,
        AVAILABLE
    }

    public Presence() {
        this(false, Show.NONE, null, 8);
    }

    public Presence(boolean available, Show show, String status, int caps) {
        this.mAvailable = available;
        this.mShow = show;
        this.mStatus = status;
        this.mInvisible = false;
        this.mDefaultStatusList = new ArrayList();
        this.mDndStatusList = new ArrayList();
        this.mCapabilities = caps;
    }

    public Presence(Parcel source) {
        setStatusMax(source.readInt());
        setStatusListMax(source.readInt());
        setStatusListContentsMax(source.readInt());
        boolean z = true;
        setAllowInvisibility(source.readInt() != 0);
        setAvailable(source.readInt() != 0);
        setShow((Show) Enum.valueOf(Show.class, source.readString()));
        this.mStatus = source.readString();
        setInvisible(source.readInt() == 0 ? false : z);
        this.mDefaultStatusList = new ArrayList();
        source.readStringList(this.mDefaultStatusList);
        this.mDndStatusList = new ArrayList();
        source.readStringList(this.mDndStatusList);
        setCapabilities(source.readInt());
    }

    public Presence(Presence presence) {
        this.mStatusMax = presence.mStatusMax;
        this.mStatusListMax = presence.mStatusListMax;
        this.mStatusListContentsMax = presence.mStatusListContentsMax;
        this.mAllowInvisibility = presence.mAllowInvisibility;
        this.mAvailable = presence.mAvailable;
        this.mShow = presence.mShow;
        this.mStatus = presence.mStatus;
        this.mInvisible = presence.mInvisible;
        this.mDefaultStatusList = presence.mDefaultStatusList;
        this.mDndStatusList = presence.mDndStatusList;
        this.mCapabilities = presence.mCapabilities;
    }

    public int getStatusMax() {
        return this.mStatusMax;
    }

    public void setStatusMax(int max) {
        this.mStatusMax = max;
    }

    public int getStatusListMax() {
        return this.mStatusListMax;
    }

    public void setStatusListMax(int max) {
        this.mStatusListMax = max;
    }

    public int getStatusListContentsMax() {
        return this.mStatusListContentsMax;
    }

    public void setStatusListContentsMax(int max) {
        this.mStatusListContentsMax = max;
    }

    public boolean allowInvisibility() {
        return this.mAllowInvisibility;
    }

    public void setAllowInvisibility(boolean allowInvisibility) {
        this.mAllowInvisibility = allowInvisibility;
    }

    public boolean isAvailable() {
        return this.mAvailable;
    }

    public void setAvailable(boolean available) {
        this.mAvailable = available;
    }

    public boolean isInvisible() {
        return this.mInvisible;
    }

    public boolean setInvisible(boolean invisible) {
        this.mInvisible = invisible;
        if (!invisible || allowInvisibility()) {
            return true;
        }
        return false;
    }

    public Show getShow() {
        return this.mShow;
    }

    public void setShow(Show show) {
        this.mShow = show;
    }

    public String getStatus() {
        return this.mStatus;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gtalkservice.Presence.setStatus(java.lang.String, boolean):void
     arg types: [java.lang.String, int]
     candidates:
      com.google.android.gtalkservice.Presence.setStatus(com.google.android.gtalkservice.Presence$Show, java.lang.String):void
      com.google.android.gtalkservice.Presence.setStatus(java.lang.String, boolean):void */
    public void setStatus(String status) {
        setStatus(status, false);
    }

    private void setStatus(String status, boolean updateLists) {
        this.mStatus = status;
        if (updateLists) {
            int i = C09872.$SwitchMap$com$google$android$gtalkservice$Presence$Show[this.mShow.ordinal()];
            if (i == 1) {
                addToList(this.mDndStatusList, status);
            } else if (i == 2) {
                addToList(this.mDefaultStatusList, status);
            }
        }
    }

    /* renamed from: com.google.android.gtalkservice.Presence$2 */
    static /* synthetic */ class C09872 {
        static final /* synthetic */ int[] $SwitchMap$com$google$android$gtalkservice$Presence$Show = new int[Show.values().length];

        static {
            try {
                $SwitchMap$com$google$android$gtalkservice$Presence$Show[Show.DND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$android$gtalkservice$Presence$Show[Show.AVAILABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gtalkservice.Presence.setStatus(java.lang.String, boolean):void
     arg types: [java.lang.String, int]
     candidates:
      com.google.android.gtalkservice.Presence.setStatus(com.google.android.gtalkservice.Presence$Show, java.lang.String):void
      com.google.android.gtalkservice.Presence.setStatus(java.lang.String, boolean):void */
    public void setStatus(Show show, String status) {
        setShow(show);
        setStatus(status, true);
    }

    private List<String> checkListContentsLength(List<String> list) {
        int maxListContentsLength = getStatusListContentsMax();
        int listLength = list.size();
        if (listLength > maxListContentsLength) {
            for (int i = listLength - 1; i >= maxListContentsLength; i--) {
                list.remove(i);
            }
        }
        return list;
    }

    private boolean addToList(List<String> list, String status) {
        if (TextUtils.isEmpty(status)) {
            return false;
        }
        for (String s : list) {
            if (s.trim().equals(status.trim())) {
                return false;
            }
        }
        int statusMax = getStatusMax();
        if (status.length() > statusMax) {
            status = status.substring(0, statusMax);
        }
        list.add(0, status);
        checkListContentsLength(list);
        return true;
    }

    public List<String> getDefaultStatusList() {
        return new ArrayList(this.mDefaultStatusList);
    }

    public List<String> getDndStatusList() {
        return new ArrayList(this.mDndStatusList);
    }

    public void clearStatusLists() {
        this.mDefaultStatusList.clear();
        this.mDndStatusList.clear();
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public void setCapabilities(int capabilities) {
        this.mCapabilities = capabilities;
    }

    public boolean equals(Presence other) {
        if (other == null || this.mAvailable != other.mAvailable || this.mShow != other.mShow) {
            return false;
        }
        String str = this.mStatus;
        if (str != null) {
            if (!str.equals(other.mStatus)) {
                return false;
            }
        } else if (other.mStatus != null) {
            return false;
        }
        if (this.mInvisible == other.mInvisible && this.mStatusMax == other.mStatusMax && this.mStatusListMax == other.mStatusListMax && this.mStatusListContentsMax == other.mStatusListContentsMax && this.mAllowInvisibility == other.mAllowInvisibility && listEqual(this.mDefaultStatusList, other.mDefaultStatusList) && listEqual(this.mDndStatusList, other.mDndStatusList) && this.mCapabilities == other.mCapabilities) {
            return true;
        }
        return false;
    }

    private boolean listEqual(List<String> list1, List<String> list2) {
        int count1 = list1.size();
        if (count1 != list2.size()) {
            return false;
        }
        for (int i = 0; i < count1; i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getStatusMax());
        dest.writeInt(getStatusListMax());
        dest.writeInt(getStatusListContentsMax());
        dest.writeInt(allowInvisibility() ? 1 : 0);
        dest.writeInt(this.mAvailable ? 1 : 0);
        dest.writeString(this.mShow.toString());
        dest.writeString(this.mStatus);
        dest.writeInt(this.mInvisible ? 1 : 0);
        dest.writeStringList(this.mDefaultStatusList);
        dest.writeStringList(this.mDndStatusList);
        dest.writeInt(getCapabilities());
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        if (!isAvailable()) {
            return "UNAVAILABLE";
        }
        if (isInvisible()) {
            return "INVISIBLE";
        }
        StringBuilder sb = new StringBuilder(40);
        if (this.mShow == Show.NONE) {
            sb.append("AVAILABLE(x)");
        } else {
            sb.append(this.mShow.toString());
        }
        if ((this.mCapabilities & 8) != 0) {
            sb.append(" pmuc-v1");
        }
        if ((this.mCapabilities & 1) != 0) {
            sb.append(" voice-v1");
        }
        if ((this.mCapabilities & 2) != 0) {
            sb.append(" video-v1");
        }
        if ((this.mCapabilities & 4) != 0) {
            sb.append(" camera-v1");
        }
        return sb.toString();
    }

    public String printDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ available=");
        sb.append(this.mAvailable);
        sb.append(", show=");
        sb.append(this.mShow);
        sb.append(", ");
        String str = this.mStatus;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(", invisible=" + this.mInvisible);
        sb.append(", allowInvisible=");
        sb.append(this.mAllowInvisibility);
        sb.append(", caps=0x");
        sb.append(Integer.toHexString(this.mCapabilities));
        sb.append(", default={");
        int i = 0;
        List<String> list = this.mDefaultStatusList;
        if (list != null) {
            for (String s : list) {
                int i2 = i + 1;
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(s);
                i = i2;
            }
        }
        sb.append("}, dnd={");
        List<String> list2 = this.mDndStatusList;
        if (list2 != null) {
            int i3 = 0;
            for (String s2 : list2) {
                int i4 = i3 + 1;
                if (i3 > 0) {
                    sb.append(", ");
                }
                sb.append(s2);
                i3 = i4;
            }
        }
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }
}
