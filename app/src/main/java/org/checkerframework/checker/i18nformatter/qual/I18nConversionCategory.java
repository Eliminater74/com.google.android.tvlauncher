package org.checkerframework.checker.i18nformatter.qual;

import com.google.android.gsf.TalkContract;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public enum I18nConversionCategory {
    UNUSED(null, null),
    GENERAL(null, null),
    DATE(new Class[]{Date.class, Number.class}, new String[]{TalkContract.MessageColumns.DATE, "time"}),
    NUMBER(new Class[]{Number.class}, new String[]{"number", "choice"});

    public final String[] strings;
    public final Class<? extends Object>[] types;

    private I18nConversionCategory(Class<? extends Object>[] types2, String[] strings2) {
        this.types = types2;
        this.strings = strings2;
    }

    public static I18nConversionCategory stringToI18nConversionCategory(String string) {
        String string2 = string.toLowerCase();
        for (I18nConversionCategory v : new I18nConversionCategory[]{DATE, NUMBER}) {
            for (String s : v.strings) {
                if (s.equals(string2)) {
                    return v;
                }
            }
        }
        throw new IllegalArgumentException("Invalid format type " + string2);
    }

    private static <E> Set<E> arrayToSet(E[] a) {
        return new HashSet(Arrays.asList(a));
    }

    public static boolean isSubsetOf(I18nConversionCategory a, I18nConversionCategory b) {
        return intersect(a, b) == a;
    }

    public static I18nConversionCategory intersect(I18nConversionCategory a, I18nConversionCategory b) {
        I18nConversionCategory i18nConversionCategory = UNUSED;
        if (a == i18nConversionCategory) {
            return b;
        }
        if (b == i18nConversionCategory) {
            return a;
        }
        I18nConversionCategory i18nConversionCategory2 = GENERAL;
        if (a == i18nConversionCategory2) {
            return b;
        }
        if (b == i18nConversionCategory2) {
            return a;
        }
        Set<Class<? extends Object>> as = arrayToSet(a.types);
        as.retainAll(arrayToSet(b.types));
        for (I18nConversionCategory v : new I18nConversionCategory[]{DATE, NUMBER}) {
            if (arrayToSet(v.types).equals(as)) {
                return v;
            }
        }
        throw new RuntimeException();
    }

    public static I18nConversionCategory union(I18nConversionCategory a, I18nConversionCategory b) {
        I18nConversionCategory i18nConversionCategory = UNUSED;
        if (a == i18nConversionCategory || b == i18nConversionCategory) {
            return UNUSED;
        }
        I18nConversionCategory i18nConversionCategory2 = GENERAL;
        if (a == i18nConversionCategory2 || b == i18nConversionCategory2) {
            return GENERAL;
        }
        I18nConversionCategory i18nConversionCategory3 = DATE;
        if (a == i18nConversionCategory3 || b == i18nConversionCategory3) {
            return DATE;
        }
        return NUMBER;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name());
        if (this.types == null) {
            sb.append(" conversion category (all types)");
        } else {
            sb.append(" conversion category (one of: ");
            boolean first = true;
            for (Class<? extends Object> cls : this.types) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(cls.getCanonicalName());
                first = false;
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
