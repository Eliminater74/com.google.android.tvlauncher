package com.google.android.tvlauncher.inputs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.Util;

import java.util.Locale;

class InputPreference extends Preference {
    private final ColorStateList mActiveIconBackgroundColorStateList;
    private final int mActiveIconSelectedTint;
    private final int mActiveIconTint;
    private final String mConnectedContentDescription;
    private final ColorStateList mConnectedIconBackgroundColorStateList;
    private final int mConnectedIconSelectedTint;
    private final int mConnectedIconTint;
    private final ColorStateList mConnectedSelectedIconBackgroundColorStateList;
    private final int mDefaultLabelTextColor;
    private final int mDefaultParentLabelTextColor;
    private final String mDisconnectedContentDescription;
    private final ColorStateList mDisconnectedIconBackgroundColorStateList;
    private final int mDisconnectedIconTint;
    private final int mDisconnectedLabelColor;
    private final ColorStateList mDisconnectedSelectedIconBackgroundColorStateList;
    private final Drawable mIconBackground;
    private final RequestOptions mImageRequestOptions;
    private final String mInputNameAndStatusFormat;
    private final int mModifiedIconPadding;
    private final OnPreferenceFocusedListener mOnPreferenceFocusedListener;
    private final View.OnFocusChangeListener mOnViewFocusChangeListener = new InputPreference$$Lambda$0(this);
    private final Drawable mSelectedIconBackground;
    private final String mStandbyContentDescription;
    private Uri mActiveIconUri;
    private boolean mApplyStandardStyleToInputStateIcons;
    private Uri mIconUri;
    private ImageView mIconView;
    private boolean mIsActive;
    private TextView mLabelView;
    private TextView mParentLabelView;
    private Uri mSelectedActiveIconUri;
    private Uri mSelectedIconUri;
    private int mState;

    InputPreference(Context context, OnPreferenceFocusedListener onPreferenceFocusedListener) {
        super(context);
        setLayoutResource(C1188R.layout.input_preference);
        this.mOnPreferenceFocusedListener = onPreferenceFocusedListener;
        this.mDefaultLabelTextColor = context.getColor(C1188R.color.input_label_default_text_color);
        this.mDefaultParentLabelTextColor = context.getColor(C1188R.color.input_parent_label_default_text_color);
        this.mDisconnectedLabelColor = context.getColor(C1188R.color.input_label_disconnected_text_color);
        this.mConnectedContentDescription = context.getString(C1188R.string.input_state_connected);
        this.mStandbyContentDescription = context.getString(C1188R.string.input_state_standby);
        this.mDisconnectedContentDescription = context.getString(C1188R.string.input_state_disconnected);
        this.mInputNameAndStatusFormat = context.getString(C1188R.string.input_name_and_input_status);
        int iconMaxSize = context.getResources().getDimensionPixelSize(C1188R.dimen.input_icon_view_size);
        this.mImageRequestOptions = (RequestOptions) ((RequestOptions) new RequestOptions().override(iconMaxSize, iconMaxSize)).centerInside();
        this.mModifiedIconPadding = context.getResources().getDimensionPixelOffset(C1188R.dimen.input_modified_icon_view_padding);
        this.mActiveIconTint = context.getColor(C1188R.color.input_icon_active_tint);
        this.mActiveIconSelectedTint = context.getColor(C1188R.color.input_icon_active_selected_tint);
        this.mActiveIconBackgroundColorStateList = context.getColorStateList(C1188R.color.input_icon_background_active_tint);
        this.mConnectedIconTint = context.getColor(C1188R.color.input_icon_tint);
        this.mConnectedIconSelectedTint = context.getColor(C1188R.color.input_icon_selected_tint);
        this.mConnectedIconBackgroundColorStateList = context.getColorStateList(C1188R.color.input_icon_background_tint);
        this.mConnectedSelectedIconBackgroundColorStateList = context.getColorStateList(C1188R.color.input_icon_background_selected_tint);
        this.mDisconnectedIconTint = context.getColor(C1188R.color.input_icon_disconnected_tint);
        this.mDisconnectedIconBackgroundColorStateList = context.getColorStateList(C1188R.color.input_icon_background_disconnected_tint);
        this.mDisconnectedSelectedIconBackgroundColorStateList = context.getColorStateList(C1188R.color.input_icon_background_disconnected_selected_tint);
        this.mIconBackground = context.getDrawable(C1188R.C1189drawable.filled_circle_input_background_black);
        this.mSelectedIconBackground = context.getDrawable(C1188R.C1189drawable.hollow_circle_input_background_black);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        if (this.mIconView == null) {
            this.mIconView = (ImageView) holder.findViewById(16908294);
        }
        if (this.mLabelView == null) {
            this.mLabelView = (TextView) holder.findViewById(16908310);
        }
        if (this.mParentLabelView == null) {
            this.mParentLabelView = (TextView) holder.findViewById(16908304);
        }
        bridge$lambda$0$InputPreference(holder.itemView, holder.itemView.hasFocus());
        holder.itemView.setOnFocusChangeListener(this.mOnViewFocusChangeListener);
    }

    /* access modifiers changed from: package-private */
    public void setState(int state) {
        this.mState = state;
    }

    /* access modifiers changed from: package-private */
    public void setIsActive(boolean isActive) {
        this.mIsActive = isActive;
    }

    /* access modifiers changed from: package-private */
    public void setApplyStandardStyleToInputStateIcons(boolean applyStandardStyleToInputStateIcons) {
        this.mApplyStandardStyleToInputStateIcons = applyStandardStyleToInputStateIcons;
    }

    /* access modifiers changed from: package-private */
    public void setIconUri(Uri iconUri) {
        this.mIconUri = iconUri;
    }

    /* access modifiers changed from: package-private */
    public void setSelectedIconUri(Uri selectedIconUri) {
        this.mSelectedIconUri = selectedIconUri;
    }

    /* access modifiers changed from: package-private */
    public void setActiveIconUri(Uri activeIconUri) {
        this.mActiveIconUri = activeIconUri;
    }

    /* access modifiers changed from: package-private */
    public void setSelectedActiveIconUri(Uri selectedActiveIconUri) {
        this.mSelectedActiveIconUri = selectedActiveIconUri;
    }

    /* access modifiers changed from: private */
    /* renamed from: updateInputUi */
    public void bridge$lambda$0$InputPreference(View view, boolean hasFocus) {
        OnPreferenceFocusedListener onPreferenceFocusedListener;
        Uri iconUri;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        if (this.mIsActive) {
            this.mLabelView.setTextColor(this.mDefaultLabelTextColor);
            this.mParentLabelView.setTextColor(this.mDefaultParentLabelTextColor);
        } else {
            int i = this.mState;
            if (i == 0) {
                this.mLabelView.setTextColor(this.mDefaultLabelTextColor);
                this.mParentLabelView.setTextColor(this.mDefaultParentLabelTextColor);
            } else if (i == 1 || i == 2) {
                this.mLabelView.setTextColor(this.mDisconnectedLabelColor);
                this.mParentLabelView.setTextColor(this.mDisconnectedLabelColor);
            }
        }
        if (this.mApplyStandardStyleToInputStateIcons) {
            ImageView imageView = this.mIconView;
            int i2 = this.mModifiedIconPadding;
            imageView.setPadding(i2, i2, i2, i2);
            if (this.mIsActive) {
                this.mIconView.setColorFilter(hasFocus ? this.mActiveIconSelectedTint : this.mActiveIconTint, PorterDuff.Mode.SRC_IN);
                this.mIconView.setBackground(this.mIconBackground);
                this.mIconView.setBackgroundTintList(this.mActiveIconBackgroundColorStateList);
            } else {
                int i3 = this.mState;
                if (i3 == 0 || i3 == 1) {
                    this.mIconView.setColorFilter(hasFocus ? this.mConnectedIconSelectedTint : this.mConnectedIconTint, PorterDuff.Mode.SRC_IN);
                    this.mIconView.setBackground(hasFocus ? this.mSelectedIconBackground : this.mIconBackground);
                    ImageView imageView2 = this.mIconView;
                    if (hasFocus) {
                        colorStateList = this.mConnectedSelectedIconBackgroundColorStateList;
                    } else {
                        colorStateList = this.mConnectedIconBackgroundColorStateList;
                    }
                    imageView2.setBackgroundTintList(colorStateList);
                } else if (i3 == 2) {
                    this.mIconView.setColorFilter(this.mDisconnectedIconTint, PorterDuff.Mode.SRC_IN);
                    this.mIconView.setBackground(hasFocus ? this.mSelectedIconBackground : this.mIconBackground);
                    ImageView imageView3 = this.mIconView;
                    if (hasFocus) {
                        colorStateList2 = this.mDisconnectedSelectedIconBackgroundColorStateList;
                    } else {
                        colorStateList2 = this.mDisconnectedIconBackgroundColorStateList;
                    }
                    imageView3.setBackgroundTintList(colorStateList2);
                }
            }
        } else {
            this.mIconView.setPadding(0, 0, 0, 0);
        }
        String status = null;
        if (this.mIsActive) {
            status = "Active";
        } else {
            int i4 = this.mState;
            if (i4 == 0) {
                status = this.mConnectedContentDescription;
            } else if (i4 == 1) {
                status = this.mStandbyContentDescription;
            } else if (i4 == 2) {
                status = this.mDisconnectedContentDescription;
            }
        }
        this.mLabelView.setContentDescription(String.format(Locale.getDefault(), this.mInputNameAndStatusFormat, this.mLabelView.getText(), status));
        if (this.mIconUri != null) {
            this.mIconView.setVisibility(0);
            if (this.mIsActive) {
                iconUri = hasFocus ? this.mSelectedActiveIconUri : this.mActiveIconUri;
            } else {
                iconUri = hasFocus ? this.mSelectedIconUri : this.mIconUri;
            }
            Context context = view.getContext();
            if (Util.isValidContextForGlide(context)) {
                Glide.with(context).load(iconUri).apply((BaseRequestOptions<?>) this.mImageRequestOptions).into(this.mIconView);
            }
        }
        if (hasFocus && (onPreferenceFocusedListener = this.mOnPreferenceFocusedListener) != null) {
            onPreferenceFocusedListener.onPreferenceFocused(getKey());
        }
    }

    interface OnPreferenceFocusedListener {
        void onPreferenceFocused(String str);
    }
}
