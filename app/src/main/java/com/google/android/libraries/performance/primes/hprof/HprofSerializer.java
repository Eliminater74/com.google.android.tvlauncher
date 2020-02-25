package com.google.android.libraries.performance.primes.hprof;

import android.support.annotation.VisibleForTesting;

import com.android.ahat.dominators.Dominators;
import com.google.android.gtalkservice.GTalkServiceConstants;
import com.google.android.libraries.performance.primes.hprof.collect.IntIntMap;
import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import logs.proto.wireless.performance.mobile.PrimesHeapDumpProto;

public class HprofSerializer {
    private static final double INTERESTING_OBJECT_PERCENT_OF_PARENT_THRESHOLD = 0.75d;
    private static final int INTERESTING_OBJECT_RETAINED_SIZE_THRESHOLD = 1048576;
    private static final int INTERESTING_PRIMITIVE_ARRAY_SIZE_THRESHOLD = 10000;

    private static boolean isInstance(HprofObject object) {
        return !(object instanceof HprofClass);
    }

    @VisibleForTesting
    static ParseResult parseHeapDump(ParseContext parseContext) {
        return HprofParser.parseBuffer(parseContext, Collections.emptyList(), Arrays.asList("java.lang.Class"), Collections.emptyList());
    }

    public PrimesHeapDumpProto.PrimesHeapDump serialize(File hprofFile) throws IOException {
        ParseContext parseContext = ParseContext.prepareContext(hprofFile);
        ParseResult result = parseHeapDump(parseContext);
        trim(result, parseContext);
        return serialize(result, parseContext);
    }

    public PrimesHeapDumpProto.PrimesHeapDump serializeTopRooted(File hprofFile) throws IOException {
        ParseContext parseContext = ParseContext.prepareContext(hprofFile);
        ParseResult result = parseHeapDump(parseContext);
        trim(result, parseContext);
        HprofObject superRoot = new SuperRoot(result.getRoots());
        new Dominators<>(HprofGraph.newInstance(parseContext, result)).computeDominators(superRoot);
        HprofObject.computeRetainedSizes(superRoot, parseContext);
        return serialize(trimToTopRooted(result, parseContext, findNodesOfInterest(parseContext, result, HprofGraphAnalyzer.findTopNRooted(result, 10))), parseContext);
    }

    /* JADX INFO: Multiple debug info for r10v2 com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap$Enumerator<com.google.android.libraries.performance.primes.hprof.HprofClass>: [D('e' com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap$Enumerator<com.google.android.libraries.performance.primes.hprof.HprofClass>), D('heapDump' logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$PrimesHeapDump$Builder)] */
    /* JADX INFO: Multiple debug info for r10v4 com.google.android.libraries.performance.primes.hprof.HprofObject: [D('o' com.google.android.libraries.performance.primes.hprof.HprofObject), D('heapDump' logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$PrimesHeapDump$Builder)] */
    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public PrimesHeapDumpProto.PrimesHeapDump serialize(ParseResult result, ParseContext parseContext) throws IOException {
        int serializedId;
        ParseContext parseContext2 = parseContext;
        PrimesHeapDumpProto.PrimesHeapDump.Builder heapDump = PrimesHeapDumpProto.PrimesHeapDump.newBuilder();
        List<PrimesHeapDumpProto.ClassInfo> classInfos = new ArrayList<>(result.getClasses().size());
        IntIntMap idTranslator = new IntIntMap();
        IntObjectMap.Enumerator<HprofClass> e = result.getClasses().enumerator();
        while (e.next()) {
            HprofClass hprofClass = e.getValue();
            idTranslator.putIfAbsent(e.getKey(), classInfos.size());
            classInfos.add((PrimesHeapDumpProto.ClassInfo) PrimesHeapDumpProto.ClassInfo.newBuilder().setClassName(hprofClass.getClassName(parseContext2)).setInstanceSize(hprofClass.getInstanceSize()).build());
        }
        IntObjectMap.Enumerator<HprofClass> e2 = result.getClasses().enumerator();
        while (e2.next()) {
            HprofClass hprofClass2 = e2.getValue();
            int index = idTranslator.get(e2.getKey());
            PrimesHeapDumpProto.ClassInfo.Builder classInfo = (PrimesHeapDumpProto.ClassInfo.Builder) ((PrimesHeapDumpProto.ClassInfo) classInfos.get(index)).toBuilder();
            if (hprofClass2.getSuperClass() != null) {
                classInfo.setSuperClass(idTranslator.get(hprofClass2.getSuperClass().getId(parseContext2)) + 1);
            } else {
                classInfo.clearSuperClass();
            }
            classInfos.set(index, (PrimesHeapDumpProto.ClassInfo) classInfo.build());
        }
        List<PrimesHeapDumpProto.ClassInstance> classInstances = new ArrayList<>();
        List<PrimesHeapDumpProto.ArrayInstance> arrayInstances = new ArrayList<>();
        IntObjectMap.Enumerator<HprofObject> e3 = result.getClassInstances().enumerator();
        while (e3.next()) {
            if (e3.getValue() instanceof HprofClassInstance) {
                idTranslator.putIfAbsent(e3.getKey(), classInstances.size());
                classInstances.add((PrimesHeapDumpProto.ClassInstance) PrimesHeapDumpProto.ClassInstance.newBuilder().setClazz(idTranslator.get(((HprofClassInstance) e3.getValue()).clazz.getId(parseContext2)) + 1).build());
            } else if (e3.getValue() instanceof HprofArrayInstance) {
                idTranslator.putIfAbsent(e3.getKey(), arrayInstances.size());
                arrayInstances.add((PrimesHeapDumpProto.ArrayInstance) PrimesHeapDumpProto.ArrayInstance.newBuilder().setClazz(idTranslator.get(((HprofArrayInstance) e3.getValue()).clazz.getId(parseContext2)) + 1).build());
            } else if (e3.getValue() instanceof HprofPrimitiveArrayInstance) {
                idTranslator.putIfAbsent(e3.getKey(), heapDump.getPrimitiveArrayInstanceCount());
                HprofPrimitiveArrayInstance hprofPrimitiveArrayInstance = (HprofPrimitiveArrayInstance) e3.getValue();
                PrimesHeapDumpProto.PrimitiveArrayInstance.Builder primativeArrayInstance = PrimesHeapDumpProto.PrimitiveArrayInstance.newBuilder().setNumElements(hprofPrimitiveArrayInstance.getChildCount(parseContext2));
                PrimesHeapDumpProto.PrimitiveType primitiveType = PrimesHeapDumpProto.PrimitiveType.forNumber(hprofPrimitiveArrayInstance.getType(parseContext2));
                if (primitiveType != null) {
                    primativeArrayInstance.setType(primitiveType);
                }
                heapDump.addPrimitiveArrayInstance((PrimesHeapDumpProto.PrimitiveArrayInstance) primativeArrayInstance.build());
            }
        }
        IntObjectMap<HprofClass> classes = result.getClasses();
        IntObjectMap<HprofObject> instances = result.getClassInstances();
        IntObjectMap.Enumerator<HprofObject> e4 = instances.enumerator();
        while (e4.next()) {
            HprofObject o = e4.getValue();
            if (!(o instanceof HprofPrimitiveArrayInstance)) {
                PrimesHeapDumpProto.PrimesHeapDump.Builder heapDump2 = heapDump;
                HprofObject o2 = o;
                List<Integer> childrenIds = findChildrenIds(parseContext, idTranslator, instances, classes, e4.getValue(), classInfos.size(), classInstances.size(), arrayInstances.size());
                int index2 = idTranslator.get(e4.getKey());
                if (index2 >= 0) {
                    if (o2 instanceof HprofClassInstance) {
                        classInstances.set(index2, (PrimesHeapDumpProto.ClassInstance) ((PrimesHeapDumpProto.ClassInstance.Builder) ((PrimesHeapDumpProto.ClassInstance) classInstances.get(index2)).toBuilder()).clearValues().addAllValues(childrenIds).build());
                    } else if (o2 instanceof HprofArrayInstance) {
                        arrayInstances.set(index2, (PrimesHeapDumpProto.ArrayInstance) ((PrimesHeapDumpProto.ArrayInstance.Builder) ((PrimesHeapDumpProto.ArrayInstance) arrayInstances.get(index2)).toBuilder()).clearValues().addAllValues(childrenIds).build());
                    }
                }
                heapDump = heapDump2;
            }
        }
        PrimesHeapDumpProto.PrimesHeapDump.Builder heapDump3 = heapDump;
        IntObjectMap.Enumerator<HprofClass> e5 = classes.enumerator();
        while (e5.next()) {
            List<Integer> staticFieldValueIds = findChildrenIds(parseContext, idTranslator, instances, classes, e5.getValue(), classInfos.size(), classInstances.size(), arrayInstances.size());
            int index3 = idTranslator.get(e5.getKey());
            classInfos.set(index3, (PrimesHeapDumpProto.ClassInfo) ((PrimesHeapDumpProto.ClassInfo.Builder) ((PrimesHeapDumpProto.ClassInfo) classInfos.get(index3)).toBuilder()).clearValues().addAllValues(staticFieldValueIds).build());
        }
        IntObjectMap<List<Integer>> rootTagToRootIds = new IntObjectMap<>();
        for (HprofObject root : result.getRoots()) {
            int index4 = idTranslator.get(root.getId(parseContext2));
            if (root instanceof HprofClass) {
                serializedId = index4 + 1;
            } else if (root instanceof HprofClassInstance) {
                serializedId = classInfos.size() + 1 + index4;
            } else if (root instanceof HprofArrayInstance) {
                serializedId = classInfos.size() + 1 + classInstances.size() + index4;
            } else if (root instanceof HprofPrimitiveArrayInstance) {
                serializedId = classInfos.size() + 1 + classInstances.size() + arrayInstances.size() + index4;
            }
            if (!rootTagToRootIds.containsKey(root.rootTag)) {
                rootTagToRootIds.putIfAbsent(root.rootTag, new ArrayList());
            }
            ((List) rootTagToRootIds.get(root.rootTag)).add(Integer.valueOf(serializedId));
        }
        PrimesHeapDumpProto.PrimesHeapDump.Builder heapDump4 = heapDump3;
        heapDump4.addAllClassInfo(classInfos).addAllClassInstance(classInstances).addAllArrayInstance(arrayInstances);
        IntObjectMap.Enumerator<List<Integer>> rootEnumerator = rootTagToRootIds.enumerator();
        while (rootEnumerator.next()) {
            PrimesHeapDumpProto.Root.Builder root2 = PrimesHeapDumpProto.Root.newBuilder();
            PrimesHeapDumpProto.RootTag rootTag = PrimesHeapDumpProto.RootTag.forNumber(rootEnumerator.getKey());
            if (rootTag != null) {
                root2.setTag(rootTag);
            }
            for (int j = 0; j < ((List) rootEnumerator.getValue()).size(); j++) {
                root2.addNodes(((Integer) ((List) rootEnumerator.getValue()).get(j)).intValue());
            }
            heapDump4.addRoots(root2);
        }
        return (PrimesHeapDumpProto.PrimesHeapDump) heapDump4.build();
    }

    private List<Integer> findChildrenIds(ParseContext parseContext, IntIntMap idTranslator, IntObjectMap<HprofObject> instances, IntObjectMap<HprofClass> classes, HprofObject o, int numClasses, int numClassInstances, int numArrayInstances) {
        int childCount = o.getChildCount(parseContext);
        ArrayList<Integer> childrenIds = new ArrayList<>(childCount);
        for (int i = 0; i < childCount; i++) {
            int valueId = o.getChildValue(parseContext, i);
            if (valueId != 0) {
                HprofObject value = instances.get(valueId);
                HprofObject value2 = value != null ? value : classes.get(valueId);
                int index = idTranslator.get(valueId);
                if (!(value2 == null || index == -1)) {
                    if (value2 instanceof HprofClass) {
                        childrenIds.add(Integer.valueOf(index + 1));
                    } else if (value2 instanceof HprofClassInstance) {
                        childrenIds.add(Integer.valueOf(index + numClasses + 1));
                    } else if (value2 instanceof HprofArrayInstance) {
                        childrenIds.add(Integer.valueOf(index + numClasses + numClassInstances + 1));
                    } else if (value2 instanceof HprofPrimitiveArrayInstance) {
                        childrenIds.add(Integer.valueOf(index + numClasses + numClassInstances + numArrayInstances + 1));
                    }
                }
            }
        }
        childrenIds.trimToSize();
        return childrenIds;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void trim(ParseResult parseResult, ParseContext parseContext) {
        HprofTraverser.addShortestPathParent(parseContext, parseResult);
        IntObjectMap.Enumerator<HprofObject> e = parseResult.getClassInstances().enumerator();
        while (e.next()) {
            if (e.getValue().parent == null) {
                if ((e.getValue().flags & 1) == 0) {
                    parseResult.getClassInstances().remove(e.getKey());
                }
            } else if (e.getValue().heapName.equals(GTalkServiceConstants.EXTRA_APP) && !(e.getValue().parent instanceof HprofClass) && !e.getValue().parent.heapName.equals(GTalkServiceConstants.EXTRA_APP)) {
                e.getValue().rootTag = 255;
                e.getValue().flags |= 1;
                parseResult.getRoots().add(e.getValue());
            }
        }
        IntObjectMap.Enumerator<HprofObject> e2 = parseResult.getClassInstances().enumerator();
        while (e2.next()) {
            if (!e2.getValue().heapName.equals(GTalkServiceConstants.EXTRA_APP)) {
                parseResult.getClassInstances().remove(e2.getKey());
            }
        }
        Iterator<HprofObject> it = parseResult.getRoots().iterator();
        while (it.hasNext()) {
            HprofObject root = it.next();
            if (root.heapName != null && !root.heapName.equals(GTalkServiceConstants.EXTRA_APP)) {
                it.remove();
            }
        }
    }

    private ParseResult trimToTopRooted(ParseResult parseResult, ParseContext parseContext, List<HprofObject> interestingNodes) {
        IntObjectMap<HprofObject> instances = new IntObjectMap<>();
        for (HprofObject interestingNode : interestingNodes) {
            Deque<HprofObject> dominatedQueue = new ArrayDeque<>();
            dominatedQueue.addLast(interestingNode);
            interestingNode.visited = true;
            while (!dominatedQueue.isEmpty()) {
                HprofObject dominated = (HprofObject) dominatedQueue.removeFirst();
                if (isInstance(dominated)) {
                    instances.putIfAbsent(dominated.getId(parseContext), dominated);
                }
                for (HprofObject dominatedDescendant : dominated.immediatelyDominated) {
                    dominatedQueue.addLast(dominatedDescendant);
                }
            }
        }
        HprofTraverser.clearTraversal(parseResult.getClassInstances(), parseResult.getClasses());
        HprofTraverser.addAllParents(parseContext, parseResult);
        HprofTraverser.clearTraversal(parseResult.getClassInstances(), parseResult.getClasses());
        for (HprofObject interestingNode2 : interestingNodes) {
            Deque<HprofObject> parentQueue = new ArrayDeque<>();
            parentQueue.addLast(interestingNode2);
            interestingNode2.visited = true;
            while (!parentQueue.isEmpty()) {
                HprofObject parent = (HprofObject) parentQueue.removeFirst();
                if (isInstance(parent)) {
                    instances.putIfAbsent(parent.getId(parseContext), parent);
                }
                for (HprofObject grandParent : parent.parents) {
                    if (grandParent != null && !grandParent.visited) {
                        grandParent.visited = true;
                        parentQueue.addLast(grandParent);
                    }
                }
            }
        }
        List<HprofObject> roots = new ArrayList<>();
        for (HprofObject root : parseResult.getRoots()) {
            if (root instanceof HprofClass) {
                roots.add(root);
            }
        }
        IntObjectMap.Enumerator<HprofObject> e = instances.enumerator();
        while (e.next()) {
            if (HprofObject.isRoot((HprofObject) e.getValue())) {
                roots.add((HprofObject) e.getValue());
            }
        }
        return new ParseResult(parseResult.getClasses(), instances, roots, parseResult.getInstancesFound());
    }

    private List<HprofObject> findNodesOfInterest(ParseContext parseContext, ParseResult result, List<HprofObject> topNRooted) {
        Set<HprofObject> nodesOfInterest = new HashSet<>();
        IntObjectMap.Enumerator<HprofObject> e = result.getClassInstances().enumerator();
        while (e.next()) {
            if ((e.getValue() instanceof HprofPrimitiveArrayInstance) && e.getValue().getChildCount(parseContext) > 10000) {
                nodesOfInterest.add(e.getValue());
            }
        }
        Deque<HprofObject> queue = new ArrayDeque<>();
        for (HprofObject topRooted : topNRooted) {
            queue.addLast(topRooted);
        }
        while (!queue.isEmpty()) {
            HprofObject currentNode = (HprofObject) queue.removeFirst();
            int potentiallyInterestingImmediatelyDominatedCount = 0;
            for (HprofObject immediatelyDominated : currentNode.immediatelyDominated) {
                double d = (double) currentNode.retainedHeapSize;
                Double.isNaN(d);
                if (((double) immediatelyDominated.retainedHeapSize) >= d * INTERESTING_OBJECT_PERCENT_OF_PARENT_THRESHOLD || immediatelyDominated.retainedHeapSize >= 1048576) {
                    queue.addFirst(immediatelyDominated);
                    potentiallyInterestingImmediatelyDominatedCount++;
                }
            }
            if (potentiallyInterestingImmediatelyDominatedCount == 0) {
                nodesOfInterest.add(currentNode);
            }
        }
        return new ArrayList(nodesOfInterest);
    }
}
