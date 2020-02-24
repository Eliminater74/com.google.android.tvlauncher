package org.checkerframework.checker.i18nformatter;

import com.google.android.gsf.TalkContract;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.checkerframework.checker.i18nformatter.qual.I18nChecksFormat;
import org.checkerframework.checker.i18nformatter.qual.I18nConversionCategory;
import org.checkerframework.checker.i18nformatter.qual.I18nValidFormat;

public class I18nFormatUtil {
    public static void tryFormatSatisfiability(String format) throws IllegalFormatException {
        MessageFormat.format(format, null);
    }

    public static I18nConversionCategory[] formatParameterCategories(String format) throws IllegalFormatException {
        tryFormatSatisfiability(format);
        I18nConversion[] cs = MessageFormatParser.parse(format);
        int maxIndex = -1;
        Map<Integer, I18nConversionCategory> conv = new HashMap<>();
        for (I18nConversion c : cs) {
            int index = c.index;
            conv.put(Integer.valueOf(index), I18nConversionCategory.intersect(c.category, conv.containsKey(Integer.valueOf(index)) ? (I18nConversionCategory) conv.get(Integer.valueOf(index)) : I18nConversionCategory.UNUSED));
            maxIndex = Math.max(maxIndex, index);
        }
        I18nConversionCategory[] res = new I18nConversionCategory[(maxIndex + 1)];
        for (int i = 0; i <= maxIndex; i++) {
            res[i] = conv.containsKey(Integer.valueOf(i)) ? (I18nConversionCategory) conv.get(Integer.valueOf(i)) : I18nConversionCategory.UNUSED;
        }
        return res;
    }

    @I18nChecksFormat
    public static boolean hasFormat(String format, I18nConversionCategory... cc) {
        I18nConversionCategory[] fcc = formatParameterCategories(format);
        if (fcc.length != cc.length) {
            return false;
        }
        for (int i = 0; i < cc.length; i++) {
            if (!I18nConversionCategory.isSubsetOf(cc[i], fcc[i])) {
                return false;
            }
        }
        return true;
    }

    @I18nValidFormat
    public static boolean isFormat(String format) {
        try {
            formatParameterCategories(format);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static class I18nConversion {
        public I18nConversionCategory category;
        public int index;

        public I18nConversion(int index2, I18nConversionCategory category2) {
            this.index = index2;
            this.category = category2;
        }

        public String toString() {
            return this.category.toString() + "(index: " + this.index + ")";
        }
    }

    private static class MessageFormatParser {
        private static final String[] DATE_TIME_MODIFIER_KEYWORDS = {"", "short", TalkContract.AccountSettings.IMAGE_STABILIZATION_MEDIUM, "long", "full"};
        private static final int MODIFIER_CURRENCY = 1;
        private static final int MODIFIER_DEFAULT = 0;
        private static final int MODIFIER_INTEGER = 3;
        private static final int MODIFIER_PERCENT = 2;
        private static final String[] NUMBER_MODIFIER_KEYWORDS = {"", "currency", "percent", "integer"};
        private static final int SEG_INDEX = 1;
        private static final int SEG_MODIFIER = 3;
        private static final int SEG_RAW = 0;
        private static final int SEG_TYPE = 2;
        private static final int TYPE_CHOICE = 4;
        private static final int TYPE_DATE = 2;
        private static final String[] TYPE_KEYWORDS = {"", "number", TalkContract.MessageColumns.DATE, "time", "choice"};
        private static final int TYPE_NULL = 0;
        private static final int TYPE_NUMBER = 1;
        private static final int TYPE_TIME = 3;
        private static List<Integer> argumentIndices;
        private static List<I18nConversionCategory> categories;
        private static Locale locale;
        public static int maxOffset;
        private static int numFormat;

        private MessageFormatParser() {
        }

        public static I18nConversion[] parse(String pattern) {
            categories = new ArrayList();
            argumentIndices = new ArrayList();
            locale = Locale.getDefault(Locale.Category.FORMAT);
            applyPattern(pattern);
            I18nConversion[] ret = new I18nConversion[numFormat];
            for (int i = 0; i < numFormat; i++) {
                ret[i] = new I18nConversion(argumentIndices.get(i).intValue(), categories.get(i));
            }
            return ret;
        }

        private static void applyPattern(String pattern) {
            StringBuilder[] segments = new StringBuilder[4];
            segments[0] = new StringBuilder();
            int part = 0;
            numFormat = 0;
            boolean inQuote = false;
            int braceStack = 0;
            maxOffset = -1;
            int i = 0;
            while (i < pattern.length()) {
                char ch = pattern.charAt(i);
                if (part == 0) {
                    if (ch == '\'') {
                        if (i + 1 >= pattern.length() || pattern.charAt(i + 1) != '\'') {
                            inQuote = !inQuote;
                        } else {
                            segments[part].append(ch);
                            i++;
                        }
                    } else if (ch != '{' || inQuote) {
                        segments[part].append(ch);
                    } else {
                        part = 1;
                        if (segments[1] == null) {
                            segments[1] = new StringBuilder();
                        }
                    }
                } else if (inQuote) {
                    segments[part].append(ch);
                    if (ch == '\'') {
                        inQuote = false;
                    }
                } else if (ch != ' ') {
                    if (ch == '\'') {
                        inQuote = true;
                        segments[part].append(ch);
                    } else if (ch != ',') {
                        if (ch == '{') {
                            braceStack++;
                            segments[part].append(ch);
                        } else if (ch != '}') {
                            segments[part].append(ch);
                        } else if (braceStack == 0) {
                            part = 0;
                            makeFormat(i, numFormat, segments);
                            numFormat++;
                            segments[1] = null;
                            segments[2] = null;
                            segments[3] = null;
                        } else {
                            braceStack--;
                            segments[part].append(ch);
                        }
                    } else if (part < 3) {
                        part++;
                        if (segments[part] == null) {
                            segments[part] = new StringBuilder();
                        }
                    } else {
                        segments[part].append(ch);
                    }
                } else if (part != 2 || segments[2].length() > 0) {
                    segments[part].append(ch);
                }
                i++;
            }
            if (braceStack == 0 && part != 0) {
                maxOffset = -1;
                throw new IllegalArgumentException("Unmatched braces in the pattern");
            }
        }

        private static void makeFormat(int position, int offsetNumber, StringBuilder[] textSegments) {
            I18nConversionCategory category;
            String[] segments = new String[textSegments.length];
            for (int i = 0; i < textSegments.length; i++) {
                StringBuilder oneseg = textSegments[i];
                segments[i] = oneseg != null ? oneseg.toString() : "";
            }
            try {
                int argumentNumber = Integer.parseInt(segments[1]);
                if (argumentNumber >= 0) {
                    int oldMaxOffset = maxOffset;
                    maxOffset = offsetNumber;
                    argumentIndices.add(Integer.valueOf(argumentNumber));
                    if (segments[2].length() != 0) {
                        int type = findKeyword(segments[2], TYPE_KEYWORDS);
                        if (type == 0) {
                            category = I18nConversionCategory.GENERAL;
                        } else if (type == 1) {
                            int findKeyword = findKeyword(segments[3], NUMBER_MODIFIER_KEYWORDS);
                            if (!(findKeyword == 0 || findKeyword == 1 || findKeyword == 2 || findKeyword == 3)) {
                                try {
                                    new DecimalFormat(segments[3], DecimalFormatSymbols.getInstance(locale));
                                } catch (IllegalArgumentException e) {
                                    maxOffset = oldMaxOffset;
                                    throw e;
                                }
                            }
                            category = I18nConversionCategory.NUMBER;
                        } else if (type == 2 || type == 3) {
                            int mod = findKeyword(segments[3], DATE_TIME_MODIFIER_KEYWORDS);
                            if (mod < 0 || mod >= DATE_TIME_MODIFIER_KEYWORDS.length) {
                                try {
                                    new SimpleDateFormat(segments[3], locale);
                                } catch (IllegalArgumentException e2) {
                                    maxOffset = oldMaxOffset;
                                    throw e2;
                                }
                            }
                            category = I18nConversionCategory.DATE;
                        } else if (type != 4) {
                            maxOffset = oldMaxOffset;
                            throw new IllegalArgumentException("unknown format type: " + segments[2]);
                        } else if (segments[3].length() != 0) {
                            try {
                                new ChoiceFormat(segments[3]);
                                category = I18nConversionCategory.NUMBER;
                            } catch (Exception e3) {
                                maxOffset = oldMaxOffset;
                                throw new IllegalArgumentException("Choice Pattern incorrect: " + segments[3], e3);
                            }
                        } else {
                            throw new IllegalArgumentException("Choice Pattern requires Subformat Pattern: " + segments[3]);
                        }
                    } else {
                        category = I18nConversionCategory.GENERAL;
                    }
                    categories.add(category);
                    return;
                }
                throw new IllegalArgumentException("negative argument number: " + argumentNumber);
            } catch (NumberFormatException e4) {
                throw new IllegalArgumentException("can't parse argument number: " + segments[1], e4);
            }
        }

        private static final int findKeyword(String s, String[] list) {
            for (int i = 0; i < list.length; i++) {
                if (s.equals(list[i])) {
                    return i;
                }
            }
            String ls = s.trim().toLowerCase(Locale.ROOT);
            if (ls == s) {
                return -1;
            }
            for (int i2 = 0; i2 < list.length; i2++) {
                if (ls.equals(list[i2])) {
                    return i2;
                }
            }
            return -1;
        }
    }
}
