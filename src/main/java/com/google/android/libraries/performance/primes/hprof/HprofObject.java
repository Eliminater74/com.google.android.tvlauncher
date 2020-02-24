package com.google.android.libraries.performance.primes.hprof;

import com.android.ahat.dominators.DominatorsComputation;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class HprofObject implements DominatorsComputation.Node {
    public static final int FLAG_IS_REF = 2;
    public static final int FLAG_IS_ROOT = 1;
    public static final int NULL_OBJECT_ID = 0;
    int flags;
    String heapName;
    HprofObject immediateDominator;
    final List<HprofObject> immediatelyDominated = new ArrayList();
    Object intermediateDominatorsComputationState;
    HprofObject parent;
    Set<HprofObject> parents = new HashSet();
    protected int position;
    int retainedHeapSize = -1;
    int rootTag;
    boolean visited = false;

    @Nullable
    public abstract String buildLeakSegment(ParseContext parseContext, int i);

    public abstract int computeShallowSize(ParseContext parseContext);

    public abstract int getChildCount(ParseContext parseContext);

    @Nullable
    public abstract String getChildName(ParseContext parseContext, int i);

    public abstract int getChildValue(ParseContext parseContext, int i);

    public static boolean isRef(HprofObject object) {
        return (object instanceof HprofClassInstance) && (((HprofClassInstance) object).clazz.flags & 2) != 0;
    }

    public static boolean isRoot(HprofObject object) {
        return (object.flags & 1) != 0;
    }

    public static void computeRetainedSizes(HprofObject object, ParseContext parseContext) {
        Deque<HprofObject> queue = new ArrayDeque<>();
        queue.push(object);
        while (!queue.isEmpty()) {
            HprofObject object2 = (HprofObject) queue.pop();
            if (object2.isSizeInitialized()) {
                object2.retainedHeapSize = object2.computeShallowSize(parseContext);
                queue.push(object2);
                for (HprofObject dominated : object2.immediatelyDominated) {
                    queue.push(dominated);
                }
            } else {
                for (HprofObject dominated2 : object2.immediatelyDominated) {
                    object2.retainedHeapSize += dominated2.retainedHeapSize;
                }
            }
        }
    }

    protected HprofObject(int position2) {
        this.position = position2;
    }

    public int getId(ParseContext parseContext) {
        return parseContext.readId(this.position);
    }

    public int getChildValue(ParseContext parseContext, String childName) {
        Preconditions.checkNotNull(childName);
        int childCount = getChildCount(parseContext);
        for (int i = 0; i < childCount; i++) {
            if (childName.equals(getChildName(parseContext, i))) {
                return getChildValue(parseContext, i);
            }
        }
        return 0;
    }

    public int findChildIndex(ParseContext parseContext, int valueId) {
        int childCount = getChildCount(parseContext);
        for (int i = 0; i < childCount; i++) {
            if (valueId == getChildValue(parseContext, i)) {
                return i;
            }
        }
        return -1;
    }

    public void setDominatorsComputationState(Object state) {
        this.intermediateDominatorsComputationState = state;
    }

    public Object getDominatorsComputationState() {
        return this.intermediateDominatorsComputationState;
    }

    public void setDominator(DominatorsComputation.Node dominator) {
        this.immediateDominator = (HprofObject) dominator;
        this.immediateDominator.immediatelyDominated.add(this);
    }

    public Iterable<? extends DominatorsComputation.Node> getReferencesForDominators() {
        return Collections.emptyList();
    }

    private boolean isSizeInitialized() {
        return this.retainedHeapSize == -1;
    }
}
