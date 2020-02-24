package androidx.leanback.widget;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class GuidedActionDiffCallback extends DiffCallback<GuidedAction> {
    static final GuidedActionDiffCallback sInstance = new GuidedActionDiffCallback();

    public static GuidedActionDiffCallback getInstance() {
        return sInstance;
    }

    public boolean areItemsTheSame(@NonNull GuidedAction oldItem, @NonNull GuidedAction newItem) {
        if (oldItem == null) {
            if (newItem == null) {
                return true;
            }
            return false;
        } else if (newItem != null && oldItem.getId() == newItem.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean areContentsTheSame(@NonNull GuidedAction oldItem, @NonNull GuidedAction newItem) {
        if (oldItem == null) {
            if (newItem == null) {
                return true;
            }
            return false;
        } else if (newItem != null && oldItem.getCheckSetId() == newItem.getCheckSetId() && oldItem.mActionFlags == newItem.mActionFlags && TextUtils.equals(oldItem.getTitle(), newItem.getTitle()) && TextUtils.equals(oldItem.getDescription(), newItem.getDescription()) && oldItem.getInputType() == newItem.getInputType() && TextUtils.equals(oldItem.getEditTitle(), newItem.getEditTitle()) && TextUtils.equals(oldItem.getEditDescription(), newItem.getEditDescription()) && oldItem.getEditInputType() == newItem.getEditInputType() && oldItem.getDescriptionEditInputType() == newItem.getDescriptionEditInputType()) {
            return true;
        } else {
            return false;
        }
    }
}
