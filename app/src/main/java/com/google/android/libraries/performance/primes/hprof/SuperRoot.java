package com.google.android.libraries.performance.primes.hprof;

import com.android.ahat.dominators.DominatorsComputation;
import java.util.List;

public final class SuperRoot extends HprofObject {
    private final List<HprofObject> roots;

    protected SuperRoot(List<HprofObject> roots2) {
        super(0);
        this.roots = roots2;
    }

    public int getChildCount(ParseContext parseContext) {
        return -1;
    }

    public int getChildValue(ParseContext parseContext, int index) {
        return -1;
    }

    public Iterable<? extends DominatorsComputation.Node> getReferencesForDominators() {
        return this.roots;
    }

    public String getChildName(ParseContext parseContext, int index) {
        return null;
    }

    public String buildLeakSegment(ParseContext parseContext, int fieldIndex) {
        return null;
    }

    public int computeShallowSize(ParseContext parseContext) {
        return 0;
    }
}
