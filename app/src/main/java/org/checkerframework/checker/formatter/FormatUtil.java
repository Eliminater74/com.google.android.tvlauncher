package org.checkerframework.checker.formatter;

import org.checkerframework.checker.formatter.qual.ConversionCategory;
import org.checkerframework.checker.formatter.qual.ReturnsFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
    private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static Pattern fsPattern = Pattern.compile(formatSpecifier);

    @ReturnsFormat
    public static String asFormat(String format, ConversionCategory... cc) throws IllegalFormatException {
        ConversionCategory[] fcc = formatParameterCategories(format);
        if (fcc.length == cc.length) {
            int i = 0;
            while (i < cc.length) {
                if (cc[i] == fcc[i]) {
                    i++;
                } else {
                    throw new IllegalFormatConversionCategoryException(cc[i], fcc[i]);
                }
            }
            return format;
        }
        throw new ExcessiveOrMissingFormatArgumentException(cc.length, fcc.length);
    }

    public static void tryFormatSatisfiability(String format) throws IllegalFormatException {
        String format2 = String.format(format, null);
    }

    public static ConversionCategory[] formatParameterCategories(String format) throws IllegalFormatException {
        tryFormatSatisfiability(format);
        int last = -1;
        int lasto = -1;
        int maxindex = -1;
        Conversion[] cs = parse(format);
        Map<Integer, ConversionCategory> conv = new HashMap<>();
        for (Conversion c : cs) {
            int index = c.index();
            if (index != -1) {
                if (index != 0) {
                    last = index - 1;
                } else {
                    lasto++;
                    last = lasto;
                }
            }
            maxindex = Math.max(maxindex, last);
            conv.put(Integer.valueOf(last), ConversionCategory.intersect(conv.containsKey(Integer.valueOf(last)) ? (ConversionCategory) conv.get(Integer.valueOf(last)) : ConversionCategory.UNUSED, c.category()));
        }
        ConversionCategory[] res = new ConversionCategory[(maxindex + 1)];
        for (int i = 0; i <= maxindex; i++) {
            res[i] = conv.containsKey(Integer.valueOf(i)) ? (ConversionCategory) conv.get(Integer.valueOf(i)) : ConversionCategory.UNUSED;
        }
        return res;
    }

    private static int indexFromFormat(Matcher m) {
        String s = m.group(1);
        if (s != null) {
            return Integer.parseInt(s.substring(0, s.length() - 1));
        }
        if (m.group(2) == null || !m.group(2).contains(String.valueOf('<'))) {
            return 0;
        }
        return -1;
    }

    private static char conversionCharFromFormat(Matcher m) {
        String dt = m.group(5);
        if (dt == null) {
            return m.group(6).charAt(0);
        }
        return dt.charAt(0);
    }

    private static Conversion[] parse(String format) {
        ArrayList<Conversion> cs = new ArrayList<>();
        Matcher m = fsPattern.matcher(format);
        while (m.find()) {
            char c = conversionCharFromFormat(m);
            if (!(c == '%' || c == 'n')) {
                cs.add(new Conversion(c, indexFromFormat(m)));
            }
        }
        return (Conversion[]) cs.toArray(new Conversion[cs.size()]);
    }

    private static class Conversion {
        private final ConversionCategory cath;
        private final int index;

        public Conversion(char c, int index2) {
            this.index = index2;
            this.cath = ConversionCategory.fromConversionChar(c);
        }

        /* access modifiers changed from: package-private */
        public int index() {
            return this.index;
        }

        /* access modifiers changed from: package-private */
        public ConversionCategory category() {
            return this.cath;
        }
    }

    public static class ExcessiveOrMissingFormatArgumentException extends MissingFormatArgumentException {
        private static final long serialVersionUID = 17000126;
        private final int expected;
        private final int found;

        public ExcessiveOrMissingFormatArgumentException(int expected2, int found2) {
            super("-");
            this.expected = expected2;
            this.found = found2;
        }

        public int getExpected() {
            return this.expected;
        }

        public int getFound() {
            return this.found;
        }

        public String getMessage() {
            return String.format("Expected %d arguments but found %d.", Integer.valueOf(this.expected), Integer.valueOf(this.found));
        }
    }

    public static class IllegalFormatConversionCategoryException extends IllegalFormatConversionException {
        private static final long serialVersionUID = 17000126;
        private final ConversionCategory expected;
        private final ConversionCategory found;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public IllegalFormatConversionCategoryException(ConversionCategory expected2, ConversionCategory found2) {
            super(expected2.chars.length() == 0 ? '-' : expected2.chars.charAt(0), found2.types == null ? Object.class : found2.types[0]);
            this.expected = expected2;
            this.found = found2;
        }

        public ConversionCategory getExpected() {
            return this.expected;
        }

        public ConversionCategory getFound() {
            return this.found;
        }

        public String getMessage() {
            return String.format("Expected category %s but found %s.", this.expected, this.found);
        }
    }
}
