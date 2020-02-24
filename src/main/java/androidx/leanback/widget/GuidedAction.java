package androidx.leanback.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.p001v4.content.ContextCompat;
import androidx.leanback.C0364R;
import java.util.List;

public class GuidedAction extends Action {
    public static final long ACTION_ID_CANCEL = -5;
    public static final long ACTION_ID_CONTINUE = -7;
    public static final long ACTION_ID_CURRENT = -3;
    public static final long ACTION_ID_FINISH = -6;
    public static final long ACTION_ID_NEXT = -2;
    public static final long ACTION_ID_NO = -9;
    public static final long ACTION_ID_OK = -4;
    public static final long ACTION_ID_YES = -8;
    public static final int CHECKBOX_CHECK_SET_ID = -1;
    public static final int DEFAULT_CHECK_SET_ID = 1;
    static final int EDITING_ACTIVATOR_VIEW = 3;
    static final int EDITING_DESCRIPTION = 2;
    static final int EDITING_NONE = 0;
    static final int EDITING_TITLE = 1;
    public static final int NO_CHECK_SET = 0;
    static final int PF_AUTORESTORE = 64;
    static final int PF_CHECKED = 1;
    static final int PF_ENABLED = 16;
    static final int PF_FOCUSABLE = 32;
    static final int PF_HAS_NEXT = 4;
    static final int PF_INFO_ONLY = 8;
    static final int PF_MULTI_LINE_DESCRIPTION = 2;
    private static final String TAG = "GuidedAction";
    int mActionFlags;
    String[] mAutofillHints;
    int mCheckSetId;
    int mDescriptionEditInputType;
    int mDescriptionInputType;
    private CharSequence mEditDescription;
    int mEditInputType;
    private CharSequence mEditTitle;
    int mEditable;
    int mInputType;
    Intent mIntent;
    List<GuidedAction> mSubActions;

    public static abstract class BuilderBase<B extends BuilderBase> {
        private int mActionFlags;
        private String[] mAutofillHints;
        private int mCheckSetId = 0;
        private Context mContext;
        private CharSequence mDescription;
        private int mDescriptionEditInputType = 1;
        private int mDescriptionInputType = 524289;
        private CharSequence mEditDescription;
        private int mEditInputType = 1;
        private CharSequence mEditTitle;
        private int mEditable = 0;
        private Drawable mIcon;
        private long mId;
        private int mInputType = 524289;
        private Intent mIntent;
        private List<GuidedAction> mSubActions;
        private CharSequence mTitle;

        public BuilderBase(Context context) {
            this.mContext = context;
            this.mActionFlags = 112;
        }

        public Context getContext() {
            return this.mContext;
        }

        private void setFlags(int flag, int mask) {
            this.mActionFlags = (this.mActionFlags & (mask ^ -1)) | (flag & mask);
        }

        /* access modifiers changed from: protected */
        public final void applyValues(GuidedAction action) {
            action.setId(this.mId);
            action.setLabel1(this.mTitle);
            action.setEditTitle(this.mEditTitle);
            action.setLabel2(this.mDescription);
            action.setEditDescription(this.mEditDescription);
            action.setIcon(this.mIcon);
            action.mIntent = this.mIntent;
            action.mEditable = this.mEditable;
            action.mInputType = this.mInputType;
            action.mDescriptionInputType = this.mDescriptionInputType;
            action.mAutofillHints = this.mAutofillHints;
            action.mEditInputType = this.mEditInputType;
            action.mDescriptionEditInputType = this.mDescriptionEditInputType;
            action.mActionFlags = this.mActionFlags;
            action.mCheckSetId = this.mCheckSetId;
            action.mSubActions = this.mSubActions;
        }

        public B clickAction(long id) {
            if (id == -4) {
                this.mId = -4;
                this.mTitle = this.mContext.getString(17039370);
            } else if (id == -5) {
                this.mId = -5;
                this.mTitle = this.mContext.getString(17039360);
            } else if (id == -6) {
                this.mId = -6;
                this.mTitle = this.mContext.getString(C0364R.string.lb_guidedaction_finish_title);
            } else if (id == -7) {
                this.mId = -7;
                this.mTitle = this.mContext.getString(C0364R.string.lb_guidedaction_continue_title);
            } else if (id == -8) {
                this.mId = -8;
                this.mTitle = this.mContext.getString(17039370);
            } else if (id == -9) {
                this.mId = -9;
                this.mTitle = this.mContext.getString(17039360);
            }
            return this;
        }

        /* renamed from: id */
        public B mo9430id(long id) {
            this.mId = id;
            return this;
        }

        public B title(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public B title(@StringRes int titleResourceId) {
            this.mTitle = getContext().getString(titleResourceId);
            return this;
        }

        public B editTitle(CharSequence editTitle) {
            this.mEditTitle = editTitle;
            return this;
        }

        public B editTitle(@StringRes int editTitleResourceId) {
            this.mEditTitle = getContext().getString(editTitleResourceId);
            return this;
        }

        public B description(CharSequence description) {
            this.mDescription = description;
            return this;
        }

        public B description(@StringRes int descriptionResourceId) {
            this.mDescription = getContext().getString(descriptionResourceId);
            return this;
        }

        public B editDescription(CharSequence description) {
            this.mEditDescription = description;
            return this;
        }

        public B editDescription(@StringRes int descriptionResourceId) {
            this.mEditDescription = getContext().getString(descriptionResourceId);
            return this;
        }

        public B intent(Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public B icon(Drawable icon) {
            this.mIcon = icon;
            return this;
        }

        @Deprecated
        public B iconResourceId(@DrawableRes int iconResourceId, Context context) {
            return icon(ContextCompat.getDrawable(context, iconResourceId));
        }

        public B icon(@DrawableRes int iconResourceId) {
            return icon(ContextCompat.getDrawable(getContext(), iconResourceId));
        }

        public B editable(boolean editable) {
            if (!editable) {
                if (this.mEditable == 1) {
                    this.mEditable = 0;
                }
                return this;
            }
            this.mEditable = 1;
            if (!isChecked() && this.mCheckSetId == 0) {
                return this;
            }
            throw new IllegalArgumentException("Editable actions cannot also be checked");
        }

        public B descriptionEditable(boolean editable) {
            if (!editable) {
                if (this.mEditable == 2) {
                    this.mEditable = 0;
                }
                return this;
            }
            this.mEditable = 2;
            if (!isChecked() && this.mCheckSetId == 0) {
                return this;
            }
            throw new IllegalArgumentException("Editable actions cannot also be checked");
        }

        public B hasEditableActivatorView(boolean editable) {
            if (!editable) {
                if (this.mEditable == 3) {
                    this.mEditable = 0;
                }
                return this;
            }
            this.mEditable = 3;
            if (!isChecked() && this.mCheckSetId == 0) {
                return this;
            }
            throw new IllegalArgumentException("Editable actions cannot also be checked");
        }

        public B inputType(int inputType) {
            this.mInputType = inputType;
            return this;
        }

        public B descriptionInputType(int inputType) {
            this.mDescriptionInputType = inputType;
            return this;
        }

        public B editInputType(int inputType) {
            this.mEditInputType = inputType;
            return this;
        }

        public B descriptionEditInputType(int inputType) {
            this.mDescriptionEditInputType = inputType;
            return this;
        }

        private boolean isChecked() {
            return (this.mActionFlags & 1) == 1;
        }

        public B checked(boolean checked) {
            setFlags(checked, 1);
            if (this.mEditable == 0) {
                return this;
            }
            throw new IllegalArgumentException("Editable actions cannot also be checked");
        }

        public B checkSetId(int checkSetId) {
            this.mCheckSetId = checkSetId;
            if (this.mEditable == 0) {
                return this;
            }
            throw new IllegalArgumentException("Editable actions cannot also be in check sets");
        }

        public B multilineDescription(boolean multilineDescription) {
            setFlags(multilineDescription ? 2 : 0, 2);
            return this;
        }

        public B hasNext(boolean hasNext) {
            setFlags(hasNext ? 4 : 0, 4);
            return this;
        }

        public B infoOnly(boolean infoOnly) {
            setFlags(infoOnly ? 8 : 0, 8);
            return this;
        }

        public B enabled(boolean enabled) {
            setFlags(enabled ? 16 : 0, 16);
            return this;
        }

        public B focusable(boolean focusable) {
            setFlags(focusable ? 32 : 0, 32);
            return this;
        }

        public B subActions(List<GuidedAction> subActions) {
            this.mSubActions = subActions;
            return this;
        }

        public B autoSaveRestoreEnabled(boolean autoSaveRestoreEnabled) {
            setFlags(autoSaveRestoreEnabled ? 64 : 0, 64);
            return this;
        }

        public B autofillHints(String... hints) {
            this.mAutofillHints = hints;
            return this;
        }
    }

    public static class Builder extends BuilderBase<Builder> {
        @Deprecated
        public Builder() {
            super(null);
        }

        public Builder(Context context) {
            super(context);
        }

        public GuidedAction build() {
            GuidedAction action = new GuidedAction();
            applyValues(action);
            return action;
        }
    }

    protected GuidedAction() {
        super(0);
    }

    private void setFlags(int flag, int mask) {
        this.mActionFlags = (this.mActionFlags & (mask ^ -1)) | (flag & mask);
    }

    public CharSequence getTitle() {
        return getLabel1();
    }

    public void setTitle(CharSequence title) {
        setLabel1(title);
    }

    public CharSequence getEditTitle() {
        return this.mEditTitle;
    }

    public void setEditTitle(CharSequence editTitle) {
        this.mEditTitle = editTitle;
    }

    public CharSequence getEditDescription() {
        return this.mEditDescription;
    }

    public void setEditDescription(CharSequence editDescription) {
        this.mEditDescription = editDescription;
    }

    public boolean isEditTitleUsed() {
        return this.mEditTitle != null;
    }

    public CharSequence getDescription() {
        return getLabel2();
    }

    public void setDescription(CharSequence description) {
        setLabel2(description);
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public boolean isEditable() {
        return this.mEditable == 1;
    }

    public boolean isDescriptionEditable() {
        return this.mEditable == 2;
    }

    public boolean hasTextEditable() {
        int i = this.mEditable;
        return i == 1 || i == 2;
    }

    public boolean hasEditableActivatorView() {
        return this.mEditable == 3;
    }

    public int getEditInputType() {
        return this.mEditInputType;
    }

    public int getDescriptionEditInputType() {
        return this.mDescriptionEditInputType;
    }

    public int getInputType() {
        return this.mInputType;
    }

    public int getDescriptionInputType() {
        return this.mDescriptionInputType;
    }

    public boolean isChecked() {
        return (this.mActionFlags & 1) == 1;
    }

    public void setChecked(boolean checked) {
        setFlags(checked, 1);
    }

    public int getCheckSetId() {
        return this.mCheckSetId;
    }

    public boolean hasMultilineDescription() {
        return (this.mActionFlags & 2) == 2;
    }

    public boolean isEnabled() {
        return (this.mActionFlags & 16) == 16;
    }

    public void setEnabled(boolean enabled) {
        setFlags(enabled ? 16 : 0, 16);
    }

    public boolean isFocusable() {
        return (this.mActionFlags & 32) == 32;
    }

    public void setFocusable(boolean focusable) {
        setFlags(focusable ? 32 : 0, 32);
    }

    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public boolean hasNext() {
        return (this.mActionFlags & 4) == 4;
    }

    public boolean infoOnly() {
        return (this.mActionFlags & 8) == 8;
    }

    public void setSubActions(List<GuidedAction> actions) {
        this.mSubActions = actions;
    }

    public List<GuidedAction> getSubActions() {
        return this.mSubActions;
    }

    public boolean hasSubActions() {
        return this.mSubActions != null;
    }

    public final boolean isAutoSaveRestoreEnabled() {
        return (this.mActionFlags & 64) == 64;
    }

    public void onSaveInstanceState(Bundle bundle, String key) {
        if (needAutoSaveTitle() && getTitle() != null) {
            bundle.putString(key, getTitle().toString());
        } else if (needAutoSaveDescription() && getDescription() != null) {
            bundle.putString(key, getDescription().toString());
        } else if (getCheckSetId() != 0) {
            bundle.putBoolean(key, isChecked());
        }
    }

    public void onRestoreInstanceState(Bundle bundle, String key) {
        if (needAutoSaveTitle()) {
            String title = bundle.getString(key);
            if (title != null) {
                setTitle(title);
            }
        } else if (needAutoSaveDescription()) {
            String description = bundle.getString(key);
            if (description != null) {
                setDescription(description);
            }
        } else if (getCheckSetId() != 0) {
            setChecked(bundle.getBoolean(key, isChecked()));
        }
    }

    static boolean isPasswordVariant(int inputType) {
        int variation = inputType & 4080;
        return variation == 128 || variation == 144 || variation == 224;
    }

    /* access modifiers changed from: package-private */
    public final boolean needAutoSaveTitle() {
        return isEditable() && !isPasswordVariant(getEditInputType());
    }

    /* access modifiers changed from: package-private */
    public final boolean needAutoSaveDescription() {
        return isDescriptionEditable() && !isPasswordVariant(getDescriptionEditInputType());
    }
}
