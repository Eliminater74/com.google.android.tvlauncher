package com.google.android.libraries.performance.primes.hprof;

public final class HprofPrimitiveArrayInstance extends HprofObject {
    protected HprofPrimitiveArrayInstance(int position) {
        super(position);
    }

    public int getType(ParseContext parseContext) {
        return parseContext.readByte(this.position + parseContext.getIdSize() + 4 + 4);
    }

    public int getChildCount(ParseContext parseContext) {
        return parseContext.readInt(this.position + parseContext.getIdSize() + 4);
    }

    public int getChildValue(ParseContext parseContext, int index) {
        return 0;
    }

    public String getChildName(ParseContext parseContext, int index) {
        return "";
    }

    public String buildLeakSegment(ParseContext parseContext, int fieldIndex) {
        return "";
    }

    public int computeShallowSize(ParseContext parseContext) {
        return parseContext.getTypeSize(getType(parseContext)) * getChildCount(parseContext);
    }
}
