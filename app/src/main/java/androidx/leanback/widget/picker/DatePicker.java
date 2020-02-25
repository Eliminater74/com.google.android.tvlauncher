package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.leanback.C0364R;

import com.google.protos.datapol.SemanticAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatePicker extends Picker {
    private static final int[] DATE_FIELDS = {5, 2, 1};
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final String LOG_TAG = "DatePicker";
    private final DateFormat mDateFormat;
    private int mColDayIndex;
    private int mColMonthIndex;
    private int mColYearIndex;
    private PickerUtility.DateConstant mConstant;
    private Calendar mCurrentDate;
    private String mDatePickerFormat;
    private PickerColumn mDayColumn;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private PickerColumn mMonthColumn;
    private Calendar mTempDate;
    private PickerColumn mYearColumn;

    public DatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.datePickerStyle);
    }

    /* JADX INFO: finally extract failed */
    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDateFormat = new SimpleDateFormat(DATE_FORMAT);
        updateCurrentLocale();
        TypedArray attributesArray = context.obtainStyledAttributes(attrs, C0364R.styleable.lbDatePicker);
        try {
            String minDate = attributesArray.getString(C0364R.styleable.lbDatePicker_android_minDate);
            String maxDate = attributesArray.getString(C0364R.styleable.lbDatePicker_android_maxDate);
            String datePickerFormat = attributesArray.getString(C0364R.styleable.lbDatePicker_datePickerFormat);
            attributesArray.recycle();
            this.mTempDate.clear();
            if (TextUtils.isEmpty(minDate)) {
                this.mTempDate.set(SemanticAnnotations.SemanticType.ST_USER_CONTENT_VALUE, 0, 1);
            } else if (!parseDate(minDate, this.mTempDate)) {
                this.mTempDate.set(SemanticAnnotations.SemanticType.ST_USER_CONTENT_VALUE, 0, 1);
            }
            this.mMinDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            this.mTempDate.clear();
            if (TextUtils.isEmpty(maxDate)) {
                this.mTempDate.set(SemanticAnnotations.SemanticType.ST_TIMESTAMP_VALUE, 0, 1);
            } else if (!parseDate(maxDate, this.mTempDate)) {
                this.mTempDate.set(SemanticAnnotations.SemanticType.ST_TIMESTAMP_VALUE, 0, 1);
            }
            this.mMaxDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            setDatePickerFormat(TextUtils.isEmpty(datePickerFormat) ? new String(android.text.format.DateFormat.getDateFormatOrder(context)) : datePickerFormat);
        } catch (Throwable th) {
            attributesArray.recycle();
            throw th;
        }
    }

    private static boolean isAnyOf(char c, char[] any) {
        for (char c2 : any) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    private static boolean updateMin(PickerColumn column, int value) {
        if (value == column.getMinValue()) {
            return false;
        }
        column.setMinValue(value);
        return true;
    }

    private static boolean updateMax(PickerColumn column, int value) {
        if (value == column.getMaxValue()) {
            return false;
        }
        column.setMaxValue(value);
        return true;
    }

    private boolean parseDate(String date, Calendar outDate) {
        try {
            outDate.setTime(this.mDateFormat.parse(date));
            return true;
        } catch (ParseException e) {
            Log.w(LOG_TAG, "Date: " + date + " not in format: " + DATE_FORMAT);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public String getBestYearMonthDayPattern(String datePickerFormat) {
        String yearPattern;
        if (PickerUtility.SUPPORTS_BEST_DATE_TIME_PATTERN) {
            yearPattern = android.text.format.DateFormat.getBestDateTimePattern(this.mConstant.locale, datePickerFormat);
        } else {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
            if (dateFormat instanceof SimpleDateFormat) {
                yearPattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
            } else {
                yearPattern = DATE_FORMAT;
            }
        }
        return TextUtils.isEmpty(yearPattern) ? DATE_FORMAT : yearPattern;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<CharSequence> extractSeparators() {
        String hmaPattern = getBestYearMonthDayPattern(this.mDatePickerFormat);
        List<CharSequence> separators = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        char lastChar = 0;
        char[] dateFormats = {'Y', 'y', 'M', 'm', 'D', 'd'};
        boolean processingQuote = false;
        for (int i = 0; i < hmaPattern.length(); i++) {
            char c = hmaPattern.charAt(i);
            if (c != ' ') {
                if (c != '\'') {
                    if (processingQuote) {
                        sb.append(c);
                    } else if (!isAnyOf(c, dateFormats)) {
                        sb.append(c);
                    } else if (c != lastChar) {
                        separators.add(sb.toString());
                        sb.setLength(0);
                    }
                    lastChar = c;
                } else if (!processingQuote) {
                    sb.setLength(0);
                    processingQuote = true;
                } else {
                    processingQuote = false;
                }
            }
        }
        separators.add(sb.toString());
        return separators;
    }

    public String getDatePickerFormat() {
        return this.mDatePickerFormat;
    }

    public void setDatePickerFormat(String datePickerFormat) {
        if (TextUtils.isEmpty(datePickerFormat)) {
            datePickerFormat = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
        }
        if (!TextUtils.equals(this.mDatePickerFormat, datePickerFormat)) {
            this.mDatePickerFormat = datePickerFormat;
            List<CharSequence> separators = extractSeparators();
            if (separators.size() == datePickerFormat.length() + 1) {
                setSeparators(separators);
                this.mDayColumn = null;
                this.mMonthColumn = null;
                this.mYearColumn = null;
                this.mColMonthIndex = -1;
                this.mColDayIndex = -1;
                this.mColYearIndex = -1;
                String dateFieldsPattern = datePickerFormat.toUpperCase();
                ArrayList<PickerColumn> columns = new ArrayList<>(3);
                for (int i = 0; i < dateFieldsPattern.length(); i++) {
                    char charAt = dateFieldsPattern.charAt(i);
                    if (charAt != 'D') {
                        if (charAt != 'M') {
                            if (charAt != 'Y') {
                                throw new IllegalArgumentException("datePicker format error");
                            } else if (this.mYearColumn == null) {
                                PickerColumn pickerColumn = new PickerColumn();
                                this.mYearColumn = pickerColumn;
                                columns.add(pickerColumn);
                                this.mColYearIndex = i;
                                this.mYearColumn.setLabelFormat("%d");
                            } else {
                                throw new IllegalArgumentException("datePicker format error");
                            }
                        } else if (this.mMonthColumn == null) {
                            PickerColumn pickerColumn2 = new PickerColumn();
                            this.mMonthColumn = pickerColumn2;
                            columns.add(pickerColumn2);
                            this.mMonthColumn.setStaticLabels(this.mConstant.months);
                            this.mColMonthIndex = i;
                        } else {
                            throw new IllegalArgumentException("datePicker format error");
                        }
                    } else if (this.mDayColumn == null) {
                        PickerColumn pickerColumn3 = new PickerColumn();
                        this.mDayColumn = pickerColumn3;
                        columns.add(pickerColumn3);
                        this.mDayColumn.setLabelFormat("%02d");
                        this.mColDayIndex = i;
                    } else {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                }
                setColumns(columns);
                updateSpinners(false);
                return;
            }
            throw new IllegalStateException("Separators size: " + separators.size() + " must equal the size of datePickerFormat: " + datePickerFormat.length() + " + 1");
        }
    }

    private void updateCurrentLocale() {
        this.mConstant = PickerUtility.getDateConstantInstance(Locale.getDefault(), getContext().getResources());
        this.mTempDate = PickerUtility.getCalendarForLocale(this.mTempDate, this.mConstant.locale);
        this.mMinDate = PickerUtility.getCalendarForLocale(this.mMinDate, this.mConstant.locale);
        this.mMaxDate = PickerUtility.getCalendarForLocale(this.mMaxDate, this.mConstant.locale);
        this.mCurrentDate = PickerUtility.getCalendarForLocale(this.mCurrentDate, this.mConstant.locale);
        PickerColumn pickerColumn = this.mMonthColumn;
        if (pickerColumn != null) {
            pickerColumn.setStaticLabels(this.mConstant.months);
            setColumnAt(this.mColMonthIndex, this.mMonthColumn);
        }
    }

    public final void onColumnValueChanged(int column, int newVal) {
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        int oldVal = getColumnAt(column).getCurrentValue();
        if (column == this.mColDayIndex) {
            this.mTempDate.add(5, newVal - oldVal);
        } else if (column == this.mColMonthIndex) {
            this.mTempDate.add(2, newVal - oldVal);
        } else if (column == this.mColYearIndex) {
            this.mTempDate.add(1, newVal - oldVal);
        } else {
            throw new IllegalArgumentException();
        }
        setDate(this.mTempDate.get(1), this.mTempDate.get(2), this.mTempDate.get(5));
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public void setMinDate(long minDate) {
        this.mTempDate.setTimeInMillis(minDate);
        if (this.mTempDate.get(1) != this.mMinDate.get(1) || this.mTempDate.get(6) == this.mMinDate.get(6)) {
            this.mMinDate.setTimeInMillis(minDate);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public void setMaxDate(long maxDate) {
        this.mTempDate.setTimeInMillis(maxDate);
        if (this.mTempDate.get(1) != this.mMaxDate.get(1) || this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            this.mMaxDate.setTimeInMillis(maxDate);
            if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public long getDate() {
        return this.mCurrentDate.getTimeInMillis();
    }

    public void setDate(long timeInMilliseconds) {
        this.mTempDate.setTimeInMillis(timeInMilliseconds);
        setDate(this.mTempDate.get(1), this.mTempDate.get(2), this.mTempDate.get(5), false);
    }

    private void setDate(int year, int month, int dayOfMonth) {
        setDate(year, month, dayOfMonth, false);
    }

    public void setDate(int year, int month, int dayOfMonth, boolean animation) {
        if (isNewDate(year, month, dayOfMonth)) {
            this.mCurrentDate.set(year, month, dayOfMonth);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            } else if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            }
            updateSpinners(animation);
        }
    }

    private boolean isNewDate(int year, int month, int dayOfMonth) {
        if (this.mCurrentDate.get(1) == year && this.mCurrentDate.get(2) == dayOfMonth && this.mCurrentDate.get(5) == month) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void updateSpinnersImpl(boolean animation) {
        boolean dateFieldChanged;
        boolean dateFieldChanged2;
        int[] dateFieldIndices = {this.mColDayIndex, this.mColMonthIndex, this.mColYearIndex};
        boolean allLargerDateFieldsHaveBeenEqualToMinDate = true;
        boolean allLargerDateFieldsHaveBeenEqualToMaxDate = true;
        for (int i = DATE_FIELDS.length - 1; i >= 0; i--) {
            if (dateFieldIndices[i] >= 0) {
                int currField = DATE_FIELDS[i];
                PickerColumn currPickerColumn = getColumnAt(dateFieldIndices[i]);
                if (allLargerDateFieldsHaveBeenEqualToMinDate) {
                    dateFieldChanged = false | updateMin(currPickerColumn, this.mMinDate.get(currField));
                } else {
                    dateFieldChanged = false | updateMin(currPickerColumn, this.mCurrentDate.getActualMinimum(currField));
                }
                if (allLargerDateFieldsHaveBeenEqualToMaxDate) {
                    dateFieldChanged2 = dateFieldChanged | updateMax(currPickerColumn, this.mMaxDate.get(currField));
                } else {
                    dateFieldChanged2 = dateFieldChanged | updateMax(currPickerColumn, this.mCurrentDate.getActualMaximum(currField));
                }
                allLargerDateFieldsHaveBeenEqualToMinDate &= this.mCurrentDate.get(currField) == this.mMinDate.get(currField);
                allLargerDateFieldsHaveBeenEqualToMaxDate &= this.mCurrentDate.get(currField) == this.mMaxDate.get(currField);
                if (dateFieldChanged2) {
                    setColumnAt(dateFieldIndices[i], currPickerColumn);
                }
                setColumnValue(dateFieldIndices[i], this.mCurrentDate.get(currField), animation);
            }
        }
    }

    private void updateSpinners(final boolean animation) {
        post(new Runnable() {
            public void run() {
                DatePicker.this.updateSpinnersImpl(animation);
            }
        });
    }
}
