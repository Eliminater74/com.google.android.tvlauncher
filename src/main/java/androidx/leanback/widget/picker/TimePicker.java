package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import androidx.leanback.C0364R;
import androidx.leanback.widget.picker.PickerUtility;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimePicker extends Picker {
    private static final int AM_INDEX = 0;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int PM_INDEX = 1;
    static final String TAG = "TimePicker";
    PickerColumn mAmPmColumn;
    int mColAmPmIndex;
    int mColHourIndex;
    int mColMinuteIndex;
    private final PickerUtility.TimeConstant mConstant;
    private int mCurrentAmPmIndex;
    private int mCurrentHour;
    private int mCurrentMinute;
    PickerColumn mHourColumn;
    private boolean mIs24hFormat;
    PickerColumn mMinuteColumn;
    private String mTimePickerFormat;

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.timePickerStyle);
    }

    /* JADX INFO: finally extract failed */
    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mConstant = PickerUtility.getTimeConstantInstance(Locale.getDefault(), context.getResources());
        TypedArray attributesArray = context.obtainStyledAttributes(attrs, C0364R.styleable.lbTimePicker);
        try {
            this.mIs24hFormat = attributesArray.getBoolean(C0364R.styleable.lbTimePicker_is24HourFormat, DateFormat.is24HourFormat(context));
            boolean useCurrentTime = attributesArray.getBoolean(C0364R.styleable.lbTimePicker_useCurrentTime, true);
            attributesArray.recycle();
            updateColumns();
            updateColumnsRange();
            if (useCurrentTime) {
                Calendar currentDate = PickerUtility.getCalendarForLocale(null, this.mConstant.locale);
                setHour(currentDate.get(11));
                setMinute(currentDate.get(12));
                setAmPmValue();
            }
        } catch (Throwable th) {
            attributesArray.recycle();
            throw th;
        }
    }

    private static void updateMin(PickerColumn column, int value) {
        if (value != column.getMinValue()) {
            column.setMinValue(value);
        }
    }

    private static void updateMax(PickerColumn column, int value) {
        if (value != column.getMaxValue()) {
            column.setMaxValue(value);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
     arg types: [int, int]
     candidates:
      ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
      ClspMth{java.lang.String.replace(char, char):java.lang.String} */
    /* access modifiers changed from: package-private */
    public String getBestHourMinutePattern() {
        String hourPattern;
        String str;
        if (PickerUtility.SUPPORTS_BEST_DATE_TIME_PATTERN) {
            hourPattern = DateFormat.getBestDateTimePattern(this.mConstant.locale, this.mIs24hFormat ? "Hma" : "hma");
        } else {
            java.text.DateFormat dateFormat = SimpleDateFormat.getTimeInstance(3, this.mConstant.locale);
            if (dateFormat instanceof SimpleDateFormat) {
                String defaultPattern = ((SimpleDateFormat) dateFormat).toPattern().replace("s", "");
                if (this.mIs24hFormat) {
                    defaultPattern = defaultPattern.replace('h', 'H').replace("a", "");
                }
                hourPattern = defaultPattern;
            } else {
                if (this.mIs24hFormat) {
                    str = "H:mma";
                } else {
                    str = "h:mma";
                }
                hourPattern = str;
            }
        }
        if (TextUtils.isEmpty(hourPattern)) {
            return "h:mma";
        }
        return hourPattern;
    }

    /* access modifiers changed from: package-private */
    public List<CharSequence> extractSeparators() {
        String hmaPattern = getBestHourMinutePattern();
        List<CharSequence> separators = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        char lastChar = 0;
        char[] timeFormats = {'H', 'h', 'K', 'k', 'm', 'M', 'a'};
        boolean processingQuote = false;
        for (int i = 0; i < hmaPattern.length(); i++) {
            char c = hmaPattern.charAt(i);
            if (c != ' ') {
                if (c != '\'') {
                    if (processingQuote) {
                        sb.append(c);
                    } else if (!isAnyOf(c, timeFormats)) {
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

    private static boolean isAnyOf(char c, char[] any) {
        for (char c2 : any) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    private String extractTimeFields() {
        StringBuilder sb;
        String hmaPattern = getBestHourMinutePattern();
        boolean z = true;
        boolean isRTL = TextUtils.getLayoutDirectionFromLocale(this.mConstant.locale) == 1;
        if (hmaPattern.indexOf(97) >= 0 && hmaPattern.indexOf("a") <= hmaPattern.indexOf("m")) {
            z = false;
        }
        boolean isAmPmAtEnd = z;
        String timePickerFormat = isRTL ? "mh" : "hm";
        if (is24Hour()) {
            return timePickerFormat;
        }
        if (isAmPmAtEnd) {
            sb.append(timePickerFormat);
            sb.append("a");
        } else {
            sb = new StringBuilder();
            sb.append("a");
            sb.append(timePickerFormat);
        }
        return sb.toString();
    }

    private void updateColumns() {
        String timePickerFormat = getBestHourMinutePattern();
        if (!TextUtils.equals(timePickerFormat, this.mTimePickerFormat)) {
            this.mTimePickerFormat = timePickerFormat;
            String timeFieldsPattern = extractTimeFields();
            List<CharSequence> separators = extractSeparators();
            if (separators.size() == timeFieldsPattern.length() + 1) {
                setSeparators(separators);
                String timeFieldsPattern2 = timeFieldsPattern.toUpperCase();
                this.mAmPmColumn = null;
                this.mMinuteColumn = null;
                this.mHourColumn = null;
                this.mColAmPmIndex = -1;
                this.mColMinuteIndex = -1;
                this.mColHourIndex = -1;
                ArrayList<PickerColumn> columns = new ArrayList<>(3);
                for (int i = 0; i < timeFieldsPattern2.length(); i++) {
                    char charAt = timeFieldsPattern2.charAt(i);
                    if (charAt == 'A') {
                        PickerColumn pickerColumn = new PickerColumn();
                        this.mAmPmColumn = pickerColumn;
                        columns.add(pickerColumn);
                        this.mAmPmColumn.setStaticLabels(this.mConstant.ampm);
                        this.mColAmPmIndex = i;
                        updateMin(this.mAmPmColumn, 0);
                        updateMax(this.mAmPmColumn, 1);
                    } else if (charAt == 'H') {
                        PickerColumn pickerColumn2 = new PickerColumn();
                        this.mHourColumn = pickerColumn2;
                        columns.add(pickerColumn2);
                        this.mHourColumn.setStaticLabels(this.mConstant.hours24);
                        this.mColHourIndex = i;
                    } else if (charAt == 'M') {
                        PickerColumn pickerColumn3 = new PickerColumn();
                        this.mMinuteColumn = pickerColumn3;
                        columns.add(pickerColumn3);
                        this.mMinuteColumn.setStaticLabels(this.mConstant.minutes);
                        this.mColMinuteIndex = i;
                    } else {
                        throw new IllegalArgumentException("Invalid time picker format.");
                    }
                }
                setColumns(columns);
                return;
            }
            throw new IllegalStateException("Separators size: " + separators.size() + " must equal the size of timeFieldsPattern: " + timeFieldsPattern.length() + " + 1");
        }
    }

    private void updateColumnsRange() {
        updateMin(this.mHourColumn, this.mIs24hFormat ^ true ? 1 : 0);
        updateMax(this.mHourColumn, this.mIs24hFormat ? 23 : 12);
        updateMin(this.mMinuteColumn, 0);
        updateMax(this.mMinuteColumn, 59);
        PickerColumn pickerColumn = this.mAmPmColumn;
        if (pickerColumn != null) {
            updateMin(pickerColumn, 0);
            updateMax(this.mAmPmColumn, 1);
        }
    }

    private void setAmPmValue() {
        if (!is24Hour()) {
            setColumnValue(this.mColAmPmIndex, this.mCurrentAmPmIndex, false);
        }
    }

    public void setHour(@IntRange(from = 0, mo124to = 23) int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("hour: " + hour + " is not in [0-23] range in");
        }
        this.mCurrentHour = hour;
        if (!is24Hour()) {
            int i = this.mCurrentHour;
            if (i >= 12) {
                this.mCurrentAmPmIndex = 1;
                if (i > 12) {
                    this.mCurrentHour = i - 12;
                }
            } else {
                this.mCurrentAmPmIndex = 0;
                if (i == 0) {
                    this.mCurrentHour = 12;
                }
            }
            setAmPmValue();
        }
        setColumnValue(this.mColHourIndex, this.mCurrentHour, false);
    }

    public int getHour() {
        if (this.mIs24hFormat) {
            return this.mCurrentHour;
        }
        if (this.mCurrentAmPmIndex == 0) {
            return this.mCurrentHour % 12;
        }
        return (this.mCurrentHour % 12) + 12;
    }

    public void setMinute(@IntRange(from = 0, mo124to = 59) int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("minute: " + minute + " is not in [0-59] range.");
        }
        this.mCurrentMinute = minute;
        setColumnValue(this.mColMinuteIndex, this.mCurrentMinute, false);
    }

    public int getMinute() {
        return this.mCurrentMinute;
    }

    public void setIs24Hour(boolean is24Hour) {
        if (this.mIs24hFormat != is24Hour) {
            int currentHour = getHour();
            int currentMinute = getMinute();
            this.mIs24hFormat = is24Hour;
            updateColumns();
            updateColumnsRange();
            setHour(currentHour);
            setMinute(currentMinute);
            setAmPmValue();
        }
    }

    public boolean is24Hour() {
        return this.mIs24hFormat;
    }

    public boolean isPm() {
        return this.mCurrentAmPmIndex == 1;
    }

    public void onColumnValueChanged(int columnIndex, int newValue) {
        if (columnIndex == this.mColHourIndex) {
            this.mCurrentHour = newValue;
        } else if (columnIndex == this.mColMinuteIndex) {
            this.mCurrentMinute = newValue;
        } else if (columnIndex == this.mColAmPmIndex) {
            this.mCurrentAmPmIndex = newValue;
        } else {
            throw new IllegalArgumentException("Invalid column index.");
        }
    }
}
