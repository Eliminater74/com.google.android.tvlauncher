package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class HprofTraverser {

    private interface BfsCallback {
        void edgeExplored(HprofObject hprofObject, HprofObject hprofObject2);
    }

    private HprofTraverser() {
    }

    /* JADX INFO: Multiple debug info for r0v1 com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap$Enumerator<com.google.android.libraries.performance.primes.hprof.HprofClass>: [D('e' com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap$Enumerator<com.google.android.libraries.performance.primes.hprof.HprofClass>), D('e' com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap$Enumerator<com.google.android.libraries.performance.primes.hprof.HprofObject>)] */
    static void clearTraversal(IntObjectMap<HprofObject> instances, IntObjectMap<HprofClass> classes) {
        IntObjectMap.Enumerator<HprofObject> e = instances.enumerator();
        while (e.next()) {
            e.getValue().visited = false;
        }
        IntObjectMap.Enumerator<HprofObject> e2 = classes.enumerator();
        while (e2.next()) {
            e2.getValue().visited = false;
        }
    }

    static void addShortestPathParent(ParseContext parseContext, ParseResult parseResult) {
        bfs(parseContext, parseResult.getClassInstances(), parseResult.getClasses(), getRootsQueue(parseResult.getRoots()), new BfsCallback() {
            public void edgeExplored(HprofObject parent, HprofObject child) {
                if (child.parent == null && !HprofObject.isRoot(child) && !HprofObject.isRef(child)) {
                    child.parent = parent;
                }
            }
        });
    }

    static void addAllParents(ParseContext parseContext, ParseResult parseResult) {
        bfs(parseContext, parseResult.getClassInstances(), parseResult.getClasses(), getRootsQueue(parseResult.getRoots()), new BfsCallback() {
            public void edgeExplored(HprofObject parent, HprofObject child) {
                child.parents.add(parent);
            }
        });
    }

    private static void bfs(ParseContext parseContext, IntObjectMap<HprofObject> instances, IntObjectMap<HprofClass> classes, Deque<HprofObject> queue, BfsCallback bfsCallback) {
        while (!queue.isEmpty()) {
            HprofObject object = queue.removeFirst();
            int childCount = object.getChildCount(parseContext);
            for (int i = 0; i < childCount; i++) {
                int valueId = object.getChildValue(parseContext, i);
                HprofObject child = instances.get(valueId);
                HprofObject child2 = child != null ? child : classes.get(valueId);
                if (child2 != null) {
                    if (!child2.visited && !HprofObject.isRoot(child2) && !HprofObject.isRef(child2)) {
                        queue.addLast(child2);
                    }
                    child2.visited = true;
                    bfsCallback.edgeExplored(object, child2);
                }
            }
        }
    }

    private static Deque<HprofObject> getRootsQueue(List<HprofObject> roots) {
        Deque<HprofObject> queue = new ArrayDeque<>();
        for (HprofObject root : roots) {
            if (!HprofObject.isRef(root)) {
                queue.addLast(root);
            }
        }
        return queue;
    }
}
