package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;

@GwtCompatible
public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.m72is('-'), "-") {
        /* access modifiers changed from: package-private */
        public String normalizeWord(String word) {
            return Ascii.toLowerCase(word);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
         arg types: [int, int]
         candidates:
          ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
          ClspMth{java.lang.String.replace(char, char):java.lang.String} */
        /* access modifiers changed from: package-private */
        public String convert(CaseFormat format, String s) {
            if (format == LOWER_UNDERSCORE) {
                return s.replace('-', '_');
            }
            if (format == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s.replace('-', '_'));
            }
            return CaseFormat.super.convert(format, s);
        }
    },
    LOWER_UNDERSCORE(CharMatcher.m72is('_'), "_") {
        /* access modifiers changed from: package-private */
        public String normalizeWord(String word) {
            return Ascii.toLowerCase(word);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
         arg types: [int, int]
         candidates:
          ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
          ClspMth{java.lang.String.replace(char, char):java.lang.String} */
        /* access modifiers changed from: package-private */
        public String convert(CaseFormat format, String s) {
            if (format == LOWER_HYPHEN) {
                return s.replace('_', '-');
            }
            if (format == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s);
            }
            return CaseFormat.super.convert(format, s);
        }
    },
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        /* access modifiers changed from: package-private */
        public String normalizeWord(String word) {
            return CaseFormat.access$100(word);
        }

        /* access modifiers changed from: package-private */
        public String normalizeFirstWord(String word) {
            return Ascii.toLowerCase(word);
        }
    },
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        /* access modifiers changed from: package-private */
        public String normalizeWord(String word) {
            return CaseFormat.access$100(word);
        }
    },
    UPPER_UNDERSCORE(CharMatcher.m72is('_'), "_") {
        /* access modifiers changed from: package-private */
        public String normalizeWord(String word) {
            return Ascii.toUpperCase(word);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
         arg types: [int, int]
         candidates:
          ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
          ClspMth{java.lang.String.replace(char, char):java.lang.String} */
        /* access modifiers changed from: package-private */
        public String convert(CaseFormat format, String s) {
            if (format == LOWER_HYPHEN) {
                return Ascii.toLowerCase(s.replace('_', '-'));
            }
            if (format == LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(s);
            }
            return CaseFormat.super.convert(format, s);
        }
    };

    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    private CaseFormat(CharMatcher wordBoundary2, String wordSeparator2) {
        this.wordBoundary = wordBoundary2;
        this.wordSeparator = wordSeparator2;
    }

    private static String firstCharOnlyToUpper(String word) {
        if (word.isEmpty()) {
            return word;
        }
        char upperCase = Ascii.toUpperCase(word.charAt(0));
        String lowerCase = Ascii.toLowerCase(word.substring(1));
        StringBuilder sb = new StringBuilder(String.valueOf(lowerCase).length() + 1);
        sb.append(upperCase);
        sb.append(lowerCase);
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public abstract String normalizeWord(String str);

    /* renamed from: to */
    public final String mo23000to(CaseFormat format, String str) {
        Preconditions.checkNotNull(format);
        Preconditions.checkNotNull(str);
        return format == this ? str : convert(format, str);
    }

    /* access modifiers changed from: package-private */
    public String convert(CaseFormat format, String s) {
        StringBuilder out = null;
        int i = 0;
        int j = -1;
        while (true) {
            int indexIn = this.wordBoundary.indexIn(s, j + 1);
            j = indexIn;
            if (indexIn == -1) {
                break;
            }
            if (i == 0) {
                out = new StringBuilder(s.length() + (this.wordSeparator.length() * 4));
                out.append(format.normalizeFirstWord(s.substring(i, j)));
            } else {
                out.append(format.normalizeWord(s.substring(i, j)));
            }
            out.append(format.wordSeparator);
            i = j + this.wordSeparator.length();
        }
        if (i == 0) {
            return format.normalizeFirstWord(s);
        }
        out.append(format.normalizeWord(s.substring(i)));
        return out.toString();
    }

    public Converter<String, String> converterTo(CaseFormat targetFormat) {
        return new StringConverter(this, targetFormat);
    }

    /* access modifiers changed from: package-private */
    public String normalizeFirstWord(String word) {
        return normalizeWord(word);
    }

    private static final class StringConverter extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;

        StringConverter(CaseFormat sourceFormat2, CaseFormat targetFormat2) {
            this.sourceFormat = (CaseFormat) Preconditions.checkNotNull(sourceFormat2);
            this.targetFormat = (CaseFormat) Preconditions.checkNotNull(targetFormat2);
        }

        /* access modifiers changed from: protected */
        public String doForward(String s) {
            return this.sourceFormat.mo23000to(this.targetFormat, s);
        }

        /* access modifiers changed from: protected */
        public String doBackward(String s) {
            return this.targetFormat.mo23000to(this.sourceFormat, s);
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof StringConverter)) {
                return false;
            }
            StringConverter that = (StringConverter) object;
            if (!this.sourceFormat.equals(that.sourceFormat) || !this.targetFormat.equals(that.targetFormat)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.sourceFormat);
            String valueOf2 = String.valueOf(this.targetFormat);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(".converterTo(");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }
}
