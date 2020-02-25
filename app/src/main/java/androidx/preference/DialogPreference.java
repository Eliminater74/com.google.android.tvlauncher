package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.support.p004v7.content.res.AppCompatResources;
import android.util.AttributeSet;

public abstract class DialogPreference extends Preference {
    private Drawable mDialogIcon;
    private int mDialogLayoutResId;
    private CharSequence mDialogMessage;
    private CharSequence mDialogTitle;
    private CharSequence mNegativeButtonText;
    private CharSequence mPositiveButtonText;

    public DialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.DialogPreference, defStyleAttr, defStyleRes);
        this.mDialogTitle = TypedArrayUtils.getString(a, C0731R.styleable.DialogPreference_dialogTitle, C0731R.styleable.DialogPreference_android_dialogTitle);
        if (this.mDialogTitle == null) {
            this.mDialogTitle = getTitle();
        }
        this.mDialogMessage = TypedArrayUtils.getString(a, C0731R.styleable.DialogPreference_dialogMessage, C0731R.styleable.DialogPreference_android_dialogMessage);
        this.mDialogIcon = TypedArrayUtils.getDrawable(a, C0731R.styleable.DialogPreference_dialogIcon, C0731R.styleable.DialogPreference_android_dialogIcon);
        this.mPositiveButtonText = TypedArrayUtils.getString(a, C0731R.styleable.DialogPreference_positiveButtonText, C0731R.styleable.DialogPreference_android_positiveButtonText);
        this.mNegativeButtonText = TypedArrayUtils.getString(a, C0731R.styleable.DialogPreference_negativeButtonText, C0731R.styleable.DialogPreference_android_negativeButtonText);
        this.mDialogLayoutResId = TypedArrayUtils.getResourceId(a, C0731R.styleable.DialogPreference_dialogLayout, C0731R.styleable.DialogPreference_android_dialogLayout, 0);
        a.recycle();
    }

    public DialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, C0731R.attr.dialogPreferenceStyle, 16842897));
    }

    public DialogPreference(Context context) {
        this(context, null);
    }

    public CharSequence getDialogTitle() {
        return this.mDialogTitle;
    }

    public void setDialogTitle(CharSequence dialogTitle) {
        this.mDialogTitle = dialogTitle;
    }

    public void setDialogTitle(int dialogTitleResId) {
        setDialogTitle(getContext().getString(dialogTitleResId));
    }

    public CharSequence getDialogMessage() {
        return this.mDialogMessage;
    }

    public void setDialogMessage(CharSequence dialogMessage) {
        this.mDialogMessage = dialogMessage;
    }

    public void setDialogMessage(int dialogMessageResId) {
        setDialogMessage(getContext().getString(dialogMessageResId));
    }

    public Drawable getDialogIcon() {
        return this.mDialogIcon;
    }

    public void setDialogIcon(Drawable dialogIcon) {
        this.mDialogIcon = dialogIcon;
    }

    public void setDialogIcon(int dialogIconRes) {
        this.mDialogIcon = AppCompatResources.getDrawable(getContext(), dialogIconRes);
    }

    public CharSequence getPositiveButtonText() {
        return this.mPositiveButtonText;
    }

    public void setPositiveButtonText(CharSequence positiveButtonText) {
        this.mPositiveButtonText = positiveButtonText;
    }

    public void setPositiveButtonText(int positiveButtonTextResId) {
        setPositiveButtonText(getContext().getString(positiveButtonTextResId));
    }

    public CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public void setNegativeButtonText(CharSequence negativeButtonText) {
        this.mNegativeButtonText = negativeButtonText;
    }

    public void setNegativeButtonText(int negativeButtonTextResId) {
        setNegativeButtonText(getContext().getString(negativeButtonTextResId));
    }

    public int getDialogLayoutResource() {
        return this.mDialogLayoutResId;
    }

    public void setDialogLayoutResource(int dialogLayoutResId) {
        this.mDialogLayoutResId = dialogLayoutResId;
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        getPreferenceManager().showDialog(this);
    }

    public interface TargetFragment {
        @Nullable
        <T extends Preference> T findPreference(@NonNull CharSequence charSequence);
    }
}
