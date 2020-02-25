package com.android.ahat.dominators;

import com.android.ahat.progress.NullProgress;
import com.android.ahat.progress.Progress;

@Deprecated
public class DominatorsComputation {

    private DominatorsComputation() {
    }

    public static void computeDominators(Node root) {
        computeDominators(root, new NullProgress(), 0);
    }

    public static void computeDominators(Node root, Progress progress, long numNodes) {
        new Dominators(new Dominators.Graph<Node>() {
            public void setDominatorsComputationState(Node node, Object state) {
                node.setDominatorsComputationState(state);
            }

            public Object getDominatorsComputationState(Node node) {
                return node.getDominatorsComputationState();
            }

            public Iterable<? extends Node> getReferencesForDominators(Node node) {
                return node.getReferencesForDominators();
            }

            public void setDominator(Node node, Node dominator) {
                node.setDominator(dominator);
            }
        }).progress(progress, numNodes).computeDominators(root);
    }

    public interface Node {
        Object getDominatorsComputationState();

        void setDominatorsComputationState(Object obj);

        Iterable<? extends Node> getReferencesForDominators();

        void setDominator(Node node);
    }
}
