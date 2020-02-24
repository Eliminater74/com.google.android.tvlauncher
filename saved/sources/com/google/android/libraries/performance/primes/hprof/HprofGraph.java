package com.google.android.libraries.performance.primes.hprof;

import com.android.ahat.dominators.Dominators;
import com.android.ahat.dominators.DominatorsComputation;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HprofGraph implements Dominators.Graph<DominatorsComputation.Node> {
    private final ParseContext parseContext;
    private final ParseResult parseResult;

    static HprofGraph newInstance(ParseContext parseContext2, ParseResult parseResult2) {
        return new HprofGraph(parseContext2, parseResult2);
    }

    private HprofGraph(ParseContext parseContext2, ParseResult parseResult2) {
        this.parseContext = (ParseContext) Preconditions.checkNotNull(parseContext2);
        this.parseResult = (ParseResult) Preconditions.checkNotNull(parseResult2);
    }

    public void setDominatorsComputationState(DominatorsComputation.Node node, Object state) {
        node.setDominatorsComputationState(state);
    }

    public Object getDominatorsComputationState(DominatorsComputation.Node node) {
        return node.getDominatorsComputationState();
    }

    public Iterable<? extends DominatorsComputation.Node> getReferencesForDominators(DominatorsComputation.Node node) {
        if (node instanceof SuperRoot) {
            return node.getReferencesForDominators();
        }
        if (!(node instanceof HprofObject)) {
            return Collections.emptyList();
        }
        HprofObject object = (HprofObject) node;
        int childCount = object.getChildCount(this.parseContext);
        List<DominatorsComputation.Node> children = new ArrayList<>(childCount);
        for (int i = 0; i < childCount; i++) {
            int childId = object.getChildValue(this.parseContext, i);
            HprofObject child = this.parseResult.getClassInstances().get(childId);
            if (child == null) {
                child = this.parseResult.getClasses().get(childId);
            }
            if (child != null && !HprofObject.isRef(object)) {
                children.add(child);
            }
        }
        return children;
    }

    public void setDominator(DominatorsComputation.Node node, DominatorsComputation.Node dominator) {
        node.setDominator(dominator);
    }
}
