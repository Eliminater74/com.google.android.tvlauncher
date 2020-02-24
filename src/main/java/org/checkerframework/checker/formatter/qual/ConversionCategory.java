package org.checkerframework.checker.formatter.qual;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.checkerframework.dataflow.qual.Pure;

public enum ConversionCategory {
    GENERAL(null, "bBhHsS"),
    CHAR(new Class[]{Character.class, Byte.class, Short.class, Integer.class}, "cC"),
    INT(new Class[]{Byte.class, Short.class, Integer.class, Long.class, BigInteger.class}, "doxX"),
    FLOAT(new Class[]{Float.class, Double.class, BigDecimal.class}, "eEfgGaA"),
    TIME(new Class[]{Long.class, Calendar.class, Date.class}, "tT"),
    CHAR_AND_INT(new Class[]{Byte.class, Short.class, Integer.class}, null),
    INT_AND_TIME(new Class[]{Long.class}, null),
    NULL(new Class[0], null),
    UNUSED(null, null);
    
    public final String chars;
    public final Class<? extends Object>[] types;

    private ConversionCategory(Class<? extends Object>[] types2, String chars2) {
        this.types = types2;
        this.chars = chars2;
    }

    public static ConversionCategory fromConversionChar(char c) {
        for (ConversionCategory v : new ConversionCategory[]{GENERAL, CHAR, INT, FLOAT, TIME}) {
            if (v.chars.contains(String.valueOf(c))) {
                return v;
            }
        }
        throw new IllegalArgumentException("Bad conversion character " + c);
    }

    private static <E> Set<E> arrayToSet(E[] a) {
        return new HashSet(Arrays.asList(a));
    }

    public static boolean isSubsetOf(ConversionCategory a, ConversionCategory b) {
        return intersect(a, b) == a;
    }

    public static ConversionCategory intersect(ConversionCategory a, ConversionCategory b) {
        ConversionCategory conversionCategory = UNUSED;
        if (a == conversionCategory) {
            return b;
        }
        if (b == conversionCategory) {
            return a;
        }
        ConversionCategory conversionCategory2 = GENERAL;
        if (a == conversionCategory2) {
            return b;
        }
        if (b == conversionCategory2) {
            return a;
        }
        Set<Class<? extends Object>> as = arrayToSet(a.types);
        as.retainAll(arrayToSet(b.types));
        for (ConversionCategory v : new ConversionCategory[]{CHAR, INT, FLOAT, TIME, CHAR_AND_INT, INT_AND_TIME, NULL}) {
            if (arrayToSet(v.types).equals(as)) {
                return v;
            }
        }
        throw new RuntimeException();
    }

    public static ConversionCategory union(ConversionCategory a, ConversionCategory b) {
        ConversionCategory conversionCategory = UNUSED;
        if (a == conversionCategory || b == conversionCategory) {
            return UNUSED;
        }
        ConversionCategory conversionCategory2 = GENERAL;
        if (a == conversionCategory2 || b == conversionCategory2) {
            return GENERAL;
        }
        if ((a == CHAR_AND_INT && b == INT_AND_TIME) || (a == INT_AND_TIME && b == CHAR_AND_INT)) {
            return INT;
        }
        Set<Class<? extends Object>> as = arrayToSet(a.types);
        as.addAll(arrayToSet(b.types));
        for (ConversionCategory v : new ConversionCategory[]{NULL, CHAR_AND_INT, INT_AND_TIME, CHAR, INT, FLOAT, TIME}) {
            if (arrayToSet(v.types).equals(as)) {
                return v;
            }
        }
        return GENERAL;
    }

    private String className(Class<?> cls) {
        if (cls == Boolean.class) {
            return "boolean";
        }
        if (cls == Character.class) {
            return "char";
        }
        if (cls == Byte.class) {
            return "byte";
        }
        if (cls == Short.class) {
            return "short";
        }
        if (cls == Integer.class) {
            return "int";
        }
        if (cls == Long.class) {
            return "long";
        }
        if (cls == Float.class) {
            return "float";
        }
        if (cls == Double.class) {
            return "double";
        }
        return cls.getSimpleName();
    }

    @Pure
    public String toString() {
        StringBuilder sb = new StringBuilder(name());
        sb.append(" conversion category (one of: ");
        boolean first = true;
        for (Class<? extends Object> cls : this.types) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(className(cls));
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }
}
