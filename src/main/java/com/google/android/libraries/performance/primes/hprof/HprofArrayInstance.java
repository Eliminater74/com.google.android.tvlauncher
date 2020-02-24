package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.stitch.util.Preconditions;

public final class HprofArrayInstance extends HprofObject {
    public final HprofClass clazz;

    HprofArrayInstance(int position, HprofClass clazz2) {
        super(position);
        this.clazz = clazz2;
    }

    public int getChildCount(ParseContext parseContext) {
        return parseContext.getBuffer().getInt(this.position + parseContext.getIdSize() + 4);
    }

    public int getChildValue(ParseContext parseContext, int index) {
        Preconditions.checkElementIndex(index, getChildCount(parseContext));
        return parseContext.readId((parseContext.getIdSize() * index) + this.position + parseContext.getIdSize() + 4 + 4 + parseContext.getIdSize());
    }

    public String getChildName(ParseContext parseContext, int index) {
        Preconditions.checkElementIndex(index, getChildCount(parseContext));
        StringBuilder sb = new StringBuilder(13);
        sb.append("[");
        sb.append(index);
        sb.append("]");
        return sb.toString();
    }

    public String buildLeakSegment(ParseContext parseContext, int fieldIndex) {
        int childCount = getChildCount(parseContext);
        StringBuilder sb = new StringBuilder(31);
        sb.append("Object[");
        sb.append(fieldIndex);
        sb.append("/");
        sb.append(childCount);
        sb.append("]");
        return sb.toString();
    }

    public int computeShallowSize(ParseContext parseContext) {
        return parseContext.getIdSize() * getChildCount(parseContext);
    }
}
