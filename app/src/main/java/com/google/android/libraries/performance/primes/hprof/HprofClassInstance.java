package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.stitch.util.Preconditions;

public final class HprofClassInstance extends HprofObject {
    public final HprofClass clazz;

    protected HprofClassInstance(int position, HprofClass clazz2) {
        super(position);
        this.clazz = (HprofClass) Preconditions.checkNotNull(clazz2);
    }

    public int getChildCount(ParseContext parseContext) {
        return this.clazz.getFieldsCount();
    }

    public int getChildValue(ParseContext parseContext, int index) {
        return this.clazz.getFieldValue(parseContext, this, index);
    }

    public String getChildName(ParseContext parseContext, int index) {
        return this.clazz.getFieldName(parseContext, index);
    }

    public String buildLeakSegment(ParseContext parseContext, int fieldIndex) {
        if (fieldIndex < 0 || fieldIndex >= this.clazz.getFieldsCount()) {
            return this.clazz.getClassName(parseContext);
        }
        HprofClass declaringClass = this.clazz.getDeclaringClassForField(fieldIndex);
        HprofClass hprofClass = this.clazz;
        if (declaringClass == hprofClass) {
            String className = hprofClass.getClassName(parseContext);
            String childName = getChildName(parseContext, fieldIndex);
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 1 + String.valueOf(childName).length());
            sb.append(className);
            sb.append('#');
            sb.append(childName);
            return sb.toString();
        }
        String className2 = hprofClass.getClassName(parseContext);
        String className3 = declaringClass.getClassName(parseContext);
        String childName2 = getChildName(parseContext, fieldIndex);
        StringBuilder sb2 = new StringBuilder(String.valueOf(className2).length() + 2 + String.valueOf(className3).length() + String.valueOf(childName2).length());
        sb2.append(className2);
        sb2.append(':');
        sb2.append(className3);
        sb2.append('#');
        sb2.append(childName2);
        return sb2.toString();
    }

    public int computeShallowSize(ParseContext parseContext) {
        return this.clazz.getInstanceSize();
    }
}
