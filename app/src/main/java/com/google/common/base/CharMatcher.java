package com.google.common.base;

import android.support.p001v4.internal.view.SupportMenu;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.BitSet;

@GwtCompatible(emulated = true)
public abstract class CharMatcher implements Predicate<Character> {
    private static final int DISTINCT_CHARS = 65536;

    protected CharMatcher() {
    }

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.INSTANCE;
    }

    public static CharMatcher ascii() {
        return Ascii.INSTANCE;
    }

    @Deprecated
    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.INSTANCE;
    }

    @Deprecated
    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }

    /* renamed from: is */
    public static CharMatcher m72is(char match) {
        return new C1482Is(match);
    }

    public static CharMatcher isNot(char match) {
        return new IsNot(match);
    }

    public static CharMatcher anyOf(CharSequence sequence) {
        int length = sequence.length();
        if (length == 0) {
            return none();
        }
        if (length == 1) {
            return m72is(sequence.charAt(0));
        }
        if (length != 2) {
            return new AnyOf(sequence);
        }
        return isEither(sequence.charAt(0), sequence.charAt(1));
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return anyOf(sequence).negate();
    }

    public static CharMatcher inRange(char startInclusive, char endInclusive) {
        return new InRange(startInclusive, endInclusive);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new ForPredicate(predicate);
    }

    @GwtIncompatible
    private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
        if (totalCharacters == 0) {
            return none();
        }
        if (totalCharacters == 1) {
            return m72is((char) table.nextSetBit(0));
        }
        if (totalCharacters == 2) {
            char c1 = (char) table.nextSetBit(0);
            return isEither(c1, (char) table.nextSetBit(c1 + 1));
        } else if (isSmall(totalCharacters, table.length())) {
            return SmallCharMatcher.from(table, description);
        } else {
            return new BitSetMatcher(table, description);
        }
    }

    @GwtIncompatible
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= 1023 && tableLength > (totalCharacters * 4) * 16;
    }

    /* access modifiers changed from: private */
    public static String showCharacter(char c) {
        char[] tmp = {'\\', 'u', 0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    private static IsEither isEither(char c1, char c2) {
        return new IsEither(c1, c2);
    }

    public abstract boolean matches(char c);

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher and(CharMatcher other) {
        return new And(this, other);
    }

    /* renamed from: or */
    public CharMatcher mo23021or(CharMatcher other) {
        return new C1483Or(this, other);
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public CharMatcher precomputedInternal() {
        String negatedDescription;
        BitSet table = new BitSet();
        setBits(table);
        int totalCharacters = table.cardinality();
        if (totalCharacters * 2 <= 65536) {
            return precomputedPositive(totalCharacters, table, toString());
        }
        table.flip(0, 65536);
        int negatedCharacters = 65536 - totalCharacters;
        final String description = toString();
        if (description.endsWith(".negate()")) {
            negatedDescription = description.substring(0, description.length() - ".negate()".length());
        } else {
            String valueOf = String.valueOf(description);
            String valueOf2 = String.valueOf(".negate()");
            negatedDescription = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        return new NegatedFastMatcher(this, precomputedPositive(negatedCharacters, table, negatedDescription)) {
            public String toString() {
                return description;
            }
        };
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public void setBits(BitSet table) {
        for (int c = SupportMenu.USER_MASK; c >= 0; c--) {
            if (matches((char) c)) {
                table.set(c);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence sequence) {
        return !matchesNoneOf(sequence);
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return indexIn(sequence) == -1;
    }

    public int indexIn(CharSequence sequence) {
        return indexIn(sequence, 0);
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public String removeFrom(CharSequence sequence) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        int spread = 1;
        while (true) {
            while (true) {
                pos++;
                if (pos == chars.length) {
                    return new String(chars, 0, pos - spread);
                }
                if (matches(chars[pos])) {
                    break;
                }
                chars[pos - spread] = chars[pos];
            }
            spread++;
        }
    }

    public String retainFrom(CharSequence sequence) {
        return negate().removeFrom(sequence);
    }

    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder}
     arg types: [java.lang.String, int, int]
     candidates:
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(char[], int, int):java.lang.StringBuilder}
      ClspMth{java.lang.Appendable.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder} */
    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(0));
        }
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        int len = string.length();
        StringBuilder buf = new StringBuilder(((len * 3) / 2) + 16);
        int oldpos = 0;
        do {
            buf.append((CharSequence) string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);
        buf.append((CharSequence) string, oldpos, len);
        return buf.toString();
    }

    public String trimFrom(CharSequence sequence) {
        int len = sequence.length();
        int first = 0;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        int last = len - 1;
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        return sequence.subSequence(first, last + 1).toString();
    }

    public String trimLeadingFrom(CharSequence sequence) {
        int len = sequence.length();
        for (int first = 0; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                return sequence.subSequence(first, len).toString();
            }
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence sequence) {
        for (int last = sequence.length() - 1; last >= 0; last--) {
            if (!matches(sequence.charAt(last))) {
                return sequence.subSequence(0, last + 1).toString();
            }
        }
        return "";
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder}
     arg types: [java.lang.CharSequence, int, int]
     candidates:
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(char[], int, int):java.lang.StringBuilder}
      ClspMth{java.lang.Appendable.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder} */
    public String collapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int i = 0;
        while (i < len) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c != replacement || (i != len - 1 && matches(sequence.charAt(i + 1)))) {
                    StringBuilder sb = new StringBuilder(len);
                    sb.append(sequence, 0, i);
                    return finishCollapseFrom(sequence, i + 1, len, replacement, sb.append(replacement), true);
                }
                i++;
            }
            i++;
        }
        return sequence.toString();
    }

    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int first = 0;
        int last = len - 1;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        if (first == 0 && last == len - 1) {
            return collapseFrom(sequence, replacement);
        }
        return finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder((last + 1) - first), false);
    }

    private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (!matches(c)) {
                builder.append(c);
                inMatchingGroup = false;
            } else if (!inMatchingGroup) {
                builder.append(replacement);
                inMatchingGroup = true;
            }
        }
        return builder.toString();
    }

    @Deprecated
    public boolean apply(Character character) {
        return matches(character.charValue());
    }

    public String toString() {
        return super.toString();
    }

    static abstract class FastMatcher extends CharMatcher {
        FastMatcher() {
        }

        public final CharMatcher precomputed() {
            return this;
        }

        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
    }

    static abstract class NamedFastMatcher extends FastMatcher {
        private final String description;

        NamedFastMatcher(String description2) {
            this.description = (String) Preconditions.checkNotNull(description2);
        }

        public final String toString() {
            return this.description;
        }
    }

    static class NegatedFastMatcher extends Negated {
        NegatedFastMatcher(CharMatcher original) {
            super(original);
        }

        public final CharMatcher precomputed() {
            return this;
        }
    }

    @GwtIncompatible
    private static final class BitSetMatcher extends NamedFastMatcher {
        private final BitSet table;

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.util.BitSet} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private BitSetMatcher(java.util.BitSet r3, java.lang.String r4) {
            /*
                r2 = this;
                r2.<init>(r4)
                int r0 = r3.length()
                int r0 = r0 + 64
                int r1 = r3.size()
                if (r0 >= r1) goto L_0x0016
                java.lang.Object r0 = r3.clone()
                r3 = r0
                java.util.BitSet r3 = (java.util.BitSet) r3
            L_0x0016:
                r2.table = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.CharMatcher.BitSetMatcher.<init>(java.util.BitSet, java.lang.String):void");
        }

        public boolean matches(char c) {
            return this.table.get(c);
        }

        /* access modifiers changed from: package-private */
        public void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }
    }

    private static final class Any extends NamedFastMatcher {
        static final Any INSTANCE = new Any();

        private Any() {
            super("CharMatcher.any()");
        }

        public boolean matches(char c) {
            return true;
        }

        public int indexIn(CharSequence sequence) {
            return sequence.length() == 0 ? -1 : 0;
        }

        public int indexIn(CharSequence sequence, int start) {
            int length = sequence.length();
            Preconditions.checkPositionIndex(start, length);
            if (start == length) {
                return -1;
            }
            return start;
        }

        public int lastIndexIn(CharSequence sequence) {
            return sequence.length() - 1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public String removeFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return "";
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            char[] array = new char[sequence.length()];
            Arrays.fill(array, replacement);
            return new String(array);
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            StringBuilder result = new StringBuilder(sequence.length() * replacement.length());
            for (int i = 0; i < sequence.length(); i++) {
                result.append(replacement);
            }
            return result.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.length() == 0 ? "" : String.valueOf(replacement);
        }

        public String trimFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return "";
        }

        public int countIn(CharSequence sequence) {
            return sequence.length();
        }

        public CharMatcher and(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        /* renamed from: or */
        public CharMatcher mo23021or(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        public CharMatcher negate() {
            return none();
        }
    }

    private static final class None extends NamedFastMatcher {
        static final None INSTANCE = new None();

        private None() {
            super("CharMatcher.none()");
        }

        public boolean matches(char c) {
            return false;
        }

        public int indexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public int indexIn(CharSequence sequence, int start) {
            Preconditions.checkPositionIndex(start, sequence.length());
            return -1;
        }

        public int lastIndexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public String removeFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            Preconditions.checkNotNull(replacement);
            return sequence.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String trimFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimLeadingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimTrailingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public int countIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return 0;
        }

        public CharMatcher and(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        /* renamed from: or */
        public CharMatcher mo23021or(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        public CharMatcher negate() {
            return any();
        }
    }

    @VisibleForTesting
    static final class Whitespace extends NamedFastMatcher {
        static final Whitespace INSTANCE = new Whitespace();
        static final int MULTIPLIER = 1682554634;
        static final String TABLE = " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　";
        static final int SHIFT = Integer.numberOfLeadingZeros(TABLE.length() - 1);

        Whitespace() {
            super("CharMatcher.whitespace()");
        }

        public boolean matches(char c) {
            return TABLE.charAt((MULTIPLIER * c) >>> SHIFT) == c;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            for (int i = 0; i < TABLE.length(); i++) {
                table.set(TABLE.charAt(i));
            }
        }
    }

    private static final class BreakingWhitespace extends CharMatcher {
        static final CharMatcher INSTANCE = new BreakingWhitespace();

        private BreakingWhitespace() {
        }

        public boolean matches(char c) {
            if (!(c == ' ' || c == 133 || c == 5760)) {
                if (c == 8199) {
                    return false;
                }
                if (!(c == 8287 || c == 12288 || c == 8232 || c == 8233)) {
                    switch (c) {
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            return c >= 8192 && c <= 8202;
                    }
                }
            }
            return true;
        }

        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }
    }

    private static final class Ascii extends NamedFastMatcher {
        static final Ascii INSTANCE = new Ascii();

        Ascii() {
            super("CharMatcher.ascii()");
        }

        public boolean matches(char c) {
            return c <= 127;
        }
    }

    private static class RangesMatcher extends CharMatcher {
        private final String description;
        private final char[] rangeEnds;
        private final char[] rangeStarts;

        RangesMatcher(String description2, char[] rangeStarts2, char[] rangeEnds2) {
            this.description = description2;
            this.rangeStarts = rangeStarts2;
            this.rangeEnds = rangeEnds2;
            Preconditions.checkArgument(rangeStarts2.length == rangeEnds2.length);
            for (int i = 0; i < rangeStarts2.length; i++) {
                Preconditions.checkArgument(rangeStarts2[i] <= rangeEnds2[i]);
                if (i + 1 < rangeStarts2.length) {
                    Preconditions.checkArgument(rangeEnds2[i] < rangeStarts2[i + 1]);
                }
            }
        }

        public boolean matches(char c) {
            int index = Arrays.binarySearch(this.rangeStarts, c);
            if (index >= 0) {
                return true;
            }
            int index2 = (index ^ -1) - 1;
            if (index2 < 0 || c > this.rangeEnds[index2]) {
                return false;
            }
            return true;
        }

        public String toString() {
            return this.description;
        }
    }

    private static final class Digit extends RangesMatcher {
        static final Digit INSTANCE = new Digit();
        private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０";

        private Digit() {
            super("CharMatcher.digit()", zeroes(), nines());
        }

        private static char[] zeroes() {
            return ZEROES.toCharArray();
        }

        private static char[] nines() {
            char[] nines = new char[ZEROES.length()];
            for (int i = 0; i < ZEROES.length(); i++) {
                nines[i] = (char) (ZEROES.charAt(i) + 9);
            }
            return nines;
        }
    }

    private static final class JavaDigit extends CharMatcher {
        static final JavaDigit INSTANCE = new JavaDigit();

        private JavaDigit() {
        }

        public boolean matches(char c) {
            return Character.isDigit(c);
        }

        public String toString() {
            return "CharMatcher.javaDigit()";
        }
    }

    private static final class JavaLetter extends CharMatcher {
        static final JavaLetter INSTANCE = new JavaLetter();

        private JavaLetter() {
        }

        public boolean matches(char c) {
            return Character.isLetter(c);
        }

        public String toString() {
            return "CharMatcher.javaLetter()";
        }
    }

    private static final class JavaLetterOrDigit extends CharMatcher {
        static final JavaLetterOrDigit INSTANCE = new JavaLetterOrDigit();

        private JavaLetterOrDigit() {
        }

        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }

        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
    }

    private static final class JavaUpperCase extends CharMatcher {
        static final JavaUpperCase INSTANCE = new JavaUpperCase();

        private JavaUpperCase() {
        }

        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }

        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
    }

    private static final class JavaLowerCase extends CharMatcher {
        static final JavaLowerCase INSTANCE = new JavaLowerCase();

        private JavaLowerCase() {
        }

        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }

        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
    }

    private static final class JavaIsoControl extends NamedFastMatcher {
        static final JavaIsoControl INSTANCE = new JavaIsoControl();

        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }

        public boolean matches(char c) {
            return c <= 31 || (c >= 127 && c <= 159);
        }
    }

    private static final class Invisible extends RangesMatcher {
        static final Invisible INSTANCE = new Invisible();
        private static final String RANGE_ENDS = "  ­؅؜۝܏࣢ ᠎‏ ⁤⁯　﻿￻";
        private static final String RANGE_STARTS = "\u0000­؀؜۝܏࣢ ᠎   ⁦　?﻿￹";

        private Invisible() {
            super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
        }
    }

    private static final class SingleWidth extends RangesMatcher {
        static final SingleWidth INSTANCE = new SingleWidth();

        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000־א׳؀ݐ฀Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ๿₯℺﷿﻿ￜ".toCharArray());
        }
    }

    private static class Negated extends CharMatcher {
        final CharMatcher original;

        Negated(CharMatcher original2) {
            this.original = (CharMatcher) Preconditions.checkNotNull(original2);
        }

        public boolean matches(char c) {
            return !this.original.matches(c);
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return this.original.matchesNoneOf(sequence);
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return this.original.matchesAllOf(sequence);
        }

        public int countIn(CharSequence sequence) {
            return sequence.length() - this.original.countIn(sequence);
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            BitSet tmp = new BitSet();
            this.original.setBits(tmp);
            tmp.flip(0, 65536);
            table.or(tmp);
        }

        public CharMatcher negate() {
            return this.original;
        }

        public String toString() {
            String valueOf = String.valueOf(this.original);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 9);
            sb.append(valueOf);
            sb.append(".negate()");
            return sb.toString();
        }
    }

    private static final class And extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        And(CharMatcher a, CharMatcher b) {
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        public boolean matches(char c) {
            return this.first.matches(c) && this.second.matches(c);
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            BitSet tmp1 = new BitSet();
            this.first.setBits(tmp1);
            BitSet tmp2 = new BitSet();
            this.second.setBits(tmp2);
            tmp1.and(tmp2);
            table.or(tmp1);
        }

        public String toString() {
            String valueOf = String.valueOf(this.first);
            String valueOf2 = String.valueOf(this.second);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19 + String.valueOf(valueOf2).length());
            sb.append("CharMatcher.and(");
            sb.append(valueOf);
            sb.append(", ");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    /* renamed from: com.google.common.base.CharMatcher$Or */
    private static final class C1483Or extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        C1483Or(CharMatcher a, CharMatcher b) {
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            this.first.setBits(table);
            this.second.setBits(table);
        }

        public boolean matches(char c) {
            return this.first.matches(c) || this.second.matches(c);
        }

        public String toString() {
            String valueOf = String.valueOf(this.first);
            String valueOf2 = String.valueOf(this.second);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
            sb.append("CharMatcher.or(");
            sb.append(valueOf);
            sb.append(", ");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    /* renamed from: com.google.common.base.CharMatcher$Is */
    private static final class C1482Is extends FastMatcher {
        private final char match;

        C1482Is(char match2) {
            this.match = match2;
        }

        public boolean matches(char c) {
            return c == this.match;
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString().replace(this.match, replacement);
        }

        public CharMatcher and(CharMatcher other) {
            return other.matches(this.match) ? this : none();
        }

        /* renamed from: or */
        public CharMatcher mo23021or(CharMatcher other) {
            return other.matches(this.match) ? other : super.mo23021or(other);
        }

        public CharMatcher negate() {
            return isNot(this.match);
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            table.set(this.match);
        }

        public String toString() {
            String access$100 = CharMatcher.showCharacter(this.match);
            StringBuilder sb = new StringBuilder(String.valueOf(access$100).length() + 18);
            sb.append("CharMatcher.is('");
            sb.append(access$100);
            sb.append("')");
            return sb.toString();
        }
    }

    private static final class IsNot extends FastMatcher {
        private final char match;

        IsNot(char match2) {
            this.match = match2;
        }

        public boolean matches(char c) {
            return c != this.match;
        }

        public CharMatcher and(CharMatcher other) {
            return other.matches(this.match) ? super.and(other) : other;
        }

        /* renamed from: or */
        public CharMatcher mo23021or(CharMatcher other) {
            return other.matches(this.match) ? any() : this;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            table.set(0, this.match);
            table.set(this.match + 1, 65536);
        }

        public CharMatcher negate() {
            return m72is(this.match);
        }

        public String toString() {
            String access$100 = CharMatcher.showCharacter(this.match);
            StringBuilder sb = new StringBuilder(String.valueOf(access$100).length() + 21);
            sb.append("CharMatcher.isNot('");
            sb.append(access$100);
            sb.append("')");
            return sb.toString();
        }
    }

    private static final class IsEither extends FastMatcher {
        private final char match1;
        private final char match2;

        IsEither(char match12, char match22) {
            this.match1 = match12;
            this.match2 = match22;
        }

        public boolean matches(char c) {
            return c == this.match1 || c == this.match2;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            table.set(this.match1);
            table.set(this.match2);
        }

        public String toString() {
            String access$100 = CharMatcher.showCharacter(this.match1);
            String access$1002 = CharMatcher.showCharacter(this.match2);
            StringBuilder sb = new StringBuilder(String.valueOf(access$100).length() + 21 + String.valueOf(access$1002).length());
            sb.append("CharMatcher.anyOf(\"");
            sb.append(access$100);
            sb.append(access$1002);
            sb.append("\")");
            return sb.toString();
        }
    }

    private static final class AnyOf extends CharMatcher {
        private final char[] chars;

        public AnyOf(CharSequence chars2) {
            this.chars = chars2.toString().toCharArray();
            Arrays.sort(this.chars);
        }

        public boolean matches(char c) {
            return Arrays.binarySearch(this.chars, c) >= 0;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            for (char c : this.chars) {
                table.set(c);
            }
        }

        public String toString() {
            StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c : this.chars) {
                description.append(CharMatcher.showCharacter(c));
            }
            description.append("\")");
            return description.toString();
        }
    }

    private static final class InRange extends FastMatcher {
        private final char endInclusive;
        private final char startInclusive;

        InRange(char startInclusive2, char endInclusive2) {
            Preconditions.checkArgument(endInclusive2 >= startInclusive2);
            this.startInclusive = startInclusive2;
            this.endInclusive = endInclusive2;
        }

        public boolean matches(char c) {
            return this.startInclusive <= c && c <= this.endInclusive;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public void setBits(BitSet table) {
            table.set(this.startInclusive, this.endInclusive + 1);
        }

        public String toString() {
            String access$100 = CharMatcher.showCharacter(this.startInclusive);
            String access$1002 = CharMatcher.showCharacter(this.endInclusive);
            StringBuilder sb = new StringBuilder(String.valueOf(access$100).length() + 27 + String.valueOf(access$1002).length());
            sb.append("CharMatcher.inRange('");
            sb.append(access$100);
            sb.append("', '");
            sb.append(access$1002);
            sb.append("')");
            return sb.toString();
        }
    }

    private static final class ForPredicate extends CharMatcher {
        private final Predicate<? super Character> predicate;

        ForPredicate(Predicate<? super Character> predicate2) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate2);
        }

        public boolean matches(char c) {
            return this.predicate.apply(Character.valueOf(c));
        }

        public boolean apply(Character character) {
            return this.predicate.apply(Preconditions.checkNotNull(character));
        }

        public String toString() {
            String valueOf = String.valueOf(this.predicate);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 26);
            sb.append("CharMatcher.forPredicate(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }
}
