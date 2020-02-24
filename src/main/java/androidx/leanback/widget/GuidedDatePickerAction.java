package androidx.leanback.widget;

import android.content.Context;
import android.os.Bundle;
import androidx.leanback.widget.GuidedAction;
import java.util.Calendar;

public class GuidedDatePickerAction extends GuidedAction {
    long mDate;
    String mDatePickerFormat;
    long mMaxDate = Long.MAX_VALUE;
    long mMinDate = Long.MIN_VALUE;

    public static abstract class BuilderBase<B extends BuilderBase> extends GuidedAction.BuilderBase<B> {
        private long mDate = Calendar.getInstance().getTimeInMillis();
        private String mDatePickerFormat;
        private long mMaxDate = Long.MAX_VALUE;
        private long mMinDate = Long.MIN_VALUE;

        public BuilderBase(Context context) {
            super(context);
            hasEditableActivatorView(true);
        }

        public B datePickerFormat(String format) {
            this.mDatePickerFormat = format;
            return this;
        }

        public B date(long date) {
            this.mDate = date;
            return this;
        }

        public B minDate(long minDate) {
            this.mMinDate = minDate;
            return this;
        }

        public B maxDate(long maxDate) {
            this.mMaxDate = maxDate;
            return this;
        }

        /* access modifiers changed from: protected */
        public final void applyDatePickerValues(GuidedDatePickerAction action) {
            super.applyValues(action);
            action.mDatePickerFormat = this.mDatePickerFormat;
            action.mDate = this.mDate;
            long j = this.mMinDate;
            long j2 = this.mMaxDate;
            if (j <= j2) {
                action.mMinDate = j;
                action.mMaxDate = j2;
                return;
            }
            throw new IllegalArgumentException("MinDate cannot be larger than MaxDate");
        }
    }

    public static final class Builder extends BuilderBase<Builder> {
        public Builder(Context context) {
            super(context);
        }

        public GuidedDatePickerAction build() {
            GuidedDatePickerAction action = new GuidedDatePickerAction();
            applyDatePickerValues(action);
            return action;
        }
    }

    public String getDatePickerFormat() {
        return this.mDatePickerFormat;
    }

    public long getDate() {
        return this.mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }

    public long getMinDate() {
        return this.mMinDate;
    }

    public long getMaxDate() {
        return this.mMaxDate;
    }

    public void onSaveInstanceState(Bundle bundle, String key) {
        bundle.putLong(key, getDate());
    }

    public void onRestoreInstanceState(Bundle bundle, String key) {
        setDate(bundle.getLong(key, getDate()));
    }
}
