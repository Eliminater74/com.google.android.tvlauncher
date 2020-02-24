package com.google.android.tvlauncher.inputs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gtalkservice.GTalkServiceConstants;
import com.google.android.tvlauncher.util.Util;

public class CustomTvInputEntry implements Comparable<CustomTvInputEntry> {
    public static String[] PROJECTION = {"input_id", GTalkServiceConstants.EXTRA_INTENT_STATE, "type", "parent_id", "title", TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI, "icon_uri", "selected_icon_uri", "active_icon_uri", "selected_active_icon_uri", "group_id"};
    private Uri mActiveIconUri;
    private int mFinalSortIndex;
    private String mGroupId;
    private Uri mIconUri;
    private String mId;
    private int mInitialSortIndex;
    private Intent mIntent;
    private String mIntentUri;
    private String mLabel;
    private String mParentId;
    private String mParentLabel;
    private Uri mSelectedActiveIconUri;
    private Uri mSelectedIconUri;
    private int mState;
    private String mType;

    private CustomTvInputEntry(String id, int state, String type, String parentId, String label, String intentUri, Uri iconUri, Uri selectedIconUri, Uri activeIconUri, Uri selectedActiveIconUri, String groupId) {
        this.mId = id;
        this.mState = state;
        this.mType = type;
        this.mParentId = parentId;
        this.mLabel = label;
        this.mIntentUri = intentUri;
        this.mIconUri = iconUri;
        this.mSelectedIconUri = selectedIconUri;
        this.mActiveIconUri = activeIconUri;
        this.mSelectedActiveIconUri = selectedActiveIconUri;
        this.mGroupId = groupId;
    }

    /* access modifiers changed from: package-private */
    public void preloadIcon(Context context, RequestOptions requestOptions) {
        if (this.mIconUri != null) {
            Glide.with(context).load(this.mIconUri).apply((BaseRequestOptions<?>) requestOptions).preload();
        }
        if (this.mSelectedIconUri != null) {
            Glide.with(context).load(this.mSelectedIconUri).apply((BaseRequestOptions<?>) requestOptions).preload();
        }
        if (this.mActiveIconUri != null) {
            Glide.with(context).load(this.mActiveIconUri).apply((BaseRequestOptions<?>) requestOptions).preload();
        }
        if (this.mSelectedActiveIconUri != null) {
            Glide.with(context).load(this.mSelectedActiveIconUri).apply((BaseRequestOptions<?>) requestOptions).preload();
        }
    }

    /* JADX INFO: Multiple debug info for r1v6 java.lang.String: [D('index' int), D('iconUri' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r2v7 java.lang.String: [D('index' int), D('selectedIconUri' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r3v1 java.lang.String: [D('activeIconUri' java.lang.String), D('index' int)] */
    /* JADX INFO: Multiple debug info for r4v1 java.lang.String: [D('index' int), D('selectedActiveIconUri' java.lang.String)] */
    public static CustomTvInputEntry fromCursor(Context context, Cursor cursor) {
        int index = 0 + 1;
        int index2 = index + 1;
        Builder state = new Builder().setId(cursor.getString(0)).setState(cursor.getInt(index));
        int index3 = index2 + 1;
        Builder type = state.setType(cursor.getString(index2));
        int index4 = index3 + 1;
        Builder parentId = type.setParentId(cursor.getString(index3));
        int index5 = index4 + 1;
        Builder label = parentId.setLabel(cursor.getString(index4));
        int index6 = index5 + 1;
        Builder builder = label.setIntentUri(cursor.getString(index5));
        int index7 = index6 + 1;
        int index8 = index7 + 1;
        int index9 = index8 + 1;
        int index10 = index9 + 1;
        builder.setIconUri(Util.getUri(cursor.getString(index6))).setSelectedIconUri(Util.getUri(cursor.getString(index7))).setActiveIconUri(Util.getUri(cursor.getString(index8))).setSelectedActiveIconUri(Util.getUri(cursor.getString(index9))).setGroupId(cursor.getString(index10));
        return builder.build(context);
    }

    public String getId() {
        return this.mId;
    }

    public int getState() {
        return this.mState;
    }

    public String getType() {
        return this.mType;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public String getIntentUri() {
        return this.mIntentUri;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public Uri getSelectedIconUri() {
        return this.mSelectedIconUri;
    }

    public Uri getActiveIconUri() {
        return this.mActiveIconUri;
    }

    public Uri getSelectedActiveIconUri() {
        return this.mSelectedActiveIconUri;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public String getParentLabel() {
        return this.mParentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.mParentLabel = parentLabel;
    }

    public void setInitialSortIndex(int initialSortIndex) {
        this.mInitialSortIndex = initialSortIndex;
    }

    public int getInitialSortIndex() {
        return this.mInitialSortIndex;
    }

    public void setFinalSortIndex(int finalSortIndex) {
        this.mFinalSortIndex = finalSortIndex;
    }

    public int compareTo(@NonNull CustomTvInputEntry o) {
        int compare = Integer.compare(this.mFinalSortIndex, o.mFinalSortIndex);
        if (compare == 0) {
            return Integer.compare(this.mInitialSortIndex, o.mInitialSortIndex);
        }
        return compare;
    }

    public String toString() {
        return this.mLabel;
    }

    public static class Builder {
        private Uri mActiveIconUri;
        private String mGroupId;
        private Uri mIconUri;
        private String mId;
        private String mIntentUri;
        private String mLabel;
        private String mParentId;
        private Uri mSelectedActiveIconUri;
        private Uri mSelectedIconUri;
        private int mState;
        private String mType;

        public Builder setId(String id) {
            this.mId = id;
            return this;
        }

        public Builder setState(int state) {
            this.mState = state;
            return this;
        }

        public Builder setType(String type) {
            this.mType = type;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setParentId(String parentId) {
            this.mParentId = parentId;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setLabel(String label) {
            this.mLabel = label;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setIntentUri(String intentUri) {
            this.mIntentUri = intentUri;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setIconUri(Uri iconUri) {
            this.mIconUri = iconUri;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setSelectedIconUri(Uri selectedIconUri) {
            this.mSelectedIconUri = selectedIconUri;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setActiveIconUri(Uri activeIconUri) {
            this.mActiveIconUri = activeIconUri;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setSelectedActiveIconUri(Uri selectedActiveIconUri) {
            this.mSelectedActiveIconUri = selectedActiveIconUri;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setGroupId(String groupId) {
            this.mGroupId = groupId;
            return this;
        }

        /* access modifiers changed from: package-private */
        public CustomTvInputEntry build(Context context) {
            if (this.mIconUri == null) {
                Integer type = InputsManagerUtil.getType(this.mType);
                if (type != null) {
                    this.mIconUri = Util.getDrawableUri(context, InputsManagerUtil.getIconResourceId(type.intValue()).intValue());
                }
            }
            return new CustomTvInputEntry(this.mId, this.mState, this.mType, this.mParentId, this.mLabel, this.mIntentUri, this.mIconUri, this.mSelectedIconUri, this.mActiveIconUri, this.mSelectedActiveIconUri, this.mGroupId);
        }
    }
}
