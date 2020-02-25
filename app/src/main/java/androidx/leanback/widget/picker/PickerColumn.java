package androidx.leanback.widget.picker;

public class PickerColumn {
    private int mCurrentValue;
    private String mLabelFormat;
    private int mMaxValue;
    private int mMinValue;
    private CharSequence[] mStaticLabels;

    public String getLabelFormat() {
        return this.mLabelFormat;
    }

    public void setLabelFormat(String labelFormat) {
        this.mLabelFormat = labelFormat;
    }

    public CharSequence[] getStaticLabels() {
        return this.mStaticLabels;
    }

    public void setStaticLabels(CharSequence[] labels) {
        this.mStaticLabels = labels;
    }

    public CharSequence getLabelFor(int value) {
        CharSequence[] charSequenceArr = this.mStaticLabels;
        if (charSequenceArr != null) {
            return charSequenceArr[value];
        }
        return String.format(this.mLabelFormat, Integer.valueOf(value));
    }

    public int getCurrentValue() {
        return this.mCurrentValue;
    }

    public void setCurrentValue(int value) {
        this.mCurrentValue = value;
    }

    public int getCount() {
        return (this.mMaxValue - this.mMinValue) + 1;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
    }
}
