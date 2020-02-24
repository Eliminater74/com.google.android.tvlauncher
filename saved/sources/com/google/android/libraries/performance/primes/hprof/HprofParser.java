package com.google.android.libraries.performance.primes.hprof;

import android.support.annotation.VisibleForTesting;
import android.support.p001v4.util.ArrayMap;
import com.google.android.libraries.performance.primes.hprof.collect.IntIntMap;
import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import com.google.android.libraries.performance.primes.hprof.collect.TrieMap;
import com.google.android.libraries.stitch.util.Preconditions;
import java.lang.ref.Reference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class HprofParser {
    private static final Map<String, Integer> PRIMITIVE_ARRAY_2_TYPES = new ArrayMap();
    private final TrieMap<ParseAction> actionsForClass = new TrieMap<>();
    private final ByteBuffer buffer;
    private final IntObjectMap<HprofClass> classes = new IntObjectMap<>();
    private String heapName = "";
    private final IntObjectMap<ParseAction> id2Actions = new IntObjectMap<>();
    private final IntObjectMap<HprofObject> instances = new IntObjectMap<>();
    private final Map<String, List<HprofObject>> instancesFound = new ArrayMap();
    private int objectClassId;
    private final ParseContext parseContext;
    private final IntIntMap rootIds = new IntIntMap();
    private final IntIntMap rootTagsToExclude = new IntIntMap();
    private final IntIntMap stringPositions = new IntIntMap();
    private final IntObjectMap<ParseAction> type2Actions = new IntObjectMap<>();

    private enum ParseAction {
        EXCLUDE_INSTANCE,
        FIND_INSTANCE,
        CLASSIFY_REF,
        IDENTIFY_OBJECT_CLASS,
        IDENTIFY_JAVA_LANG_CLASS
    }

    static {
        PRIMITIVE_ARRAY_2_TYPES.put("boolean[]", 4);
        PRIMITIVE_ARRAY_2_TYPES.put("char[]", 5);
        PRIMITIVE_ARRAY_2_TYPES.put("float[]", 6);
        PRIMITIVE_ARRAY_2_TYPES.put("double[]", 7);
        PRIMITIVE_ARRAY_2_TYPES.put("byte[]", 8);
        PRIMITIVE_ARRAY_2_TYPES.put("short[]", 9);
        PRIMITIVE_ARRAY_2_TYPES.put("int[]", 10);
        PRIMITIVE_ARRAY_2_TYPES.put("long[]", 11);
    }

    static ParseResult parseBuffer(ParseContext parseContext2, Iterable<Integer> rootTagsToExclude2, Iterable<String> instancesToExclude, Iterable<String> instancesToFind) {
        return new HprofParser(parseContext2, rootTagsToExclude2, instancesToExclude, instancesToFind).parse();
    }

    @VisibleForTesting
    HprofParser(ParseContext parseContext2, Iterable<Integer> rootTagsToExclude2, Iterable<String> instancesToExclude, Iterable<String> instancesToFind) {
        this.parseContext = parseContext2;
        this.buffer = parseContext2.getBuffer();
        this.actionsForClass.putIfAbsent(Reference.class.getName(), ParseAction.CLASSIFY_REF);
        this.actionsForClass.putIfAbsent(Object.class.getName(), ParseAction.IDENTIFY_OBJECT_CLASS);
        this.actionsForClass.putIfAbsent(Class.class.getName(), ParseAction.IDENTIFY_JAVA_LANG_CLASS);
        if (instancesToExclude != null) {
            for (String classToExclude : instancesToExclude) {
                this.actionsForClass.putIfAbsent(classToExclude, ParseAction.EXCLUDE_INSTANCE);
                if (PRIMITIVE_ARRAY_2_TYPES.containsKey(classToExclude)) {
                    this.type2Actions.putIfAbsent(PRIMITIVE_ARRAY_2_TYPES.get(classToExclude).intValue(), ParseAction.EXCLUDE_INSTANCE);
                }
            }
        }
        if (instancesToFind != null) {
            for (String instanceToFind : instancesToFind) {
                this.actionsForClass.putIfAbsent(instanceToFind, ParseAction.FIND_INSTANCE);
            }
        }
        if (rootTagsToExclude2 != null) {
            for (Integer intValue : rootTagsToExclude2) {
                this.rootTagsToExclude.putIfAbsent(intValue.intValue(), 0);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public ParseResult parse() {
        while (this.buffer.hasRemaining()) {
            int tag = this.buffer.get();
            this.buffer.getInt();
            ByteBuffer byteBuffer = this.buffer;
            if (byteBuffer.getInt(byteBuffer.position()) < 0) {
                throw new RuntimeException("Length too large to parse.");
            } else if (tag == 1) {
                parseString();
            } else if (tag == 2) {
                parseClassName();
            } else if (tag == 12 || tag == 28) {
                parseHeapDump();
            } else {
                this.parseContext.skipBytes(this.buffer.getInt());
            }
        }
        IntObjectMap.Enumerator<HprofClass> classEnumerator = this.classes.enumerator();
        while (classEnumerator.next()) {
            classEnumerator.getValue().resolveSuperClasses();
        }
        List<HprofObject> roots = new ArrayList<>();
        IntIntMap.Enumerator rootEnumerator = this.rootIds.enumerator();
        while (rootEnumerator.next()) {
            int rootId = rootEnumerator.getKey();
            HprofObject hprofObject = this.classes.get(rootId);
            HprofObject root = hprofObject;
            if (hprofObject == null) {
                HprofObject hprofObject2 = this.instances.get(rootId);
                root = hprofObject2;
                if (hprofObject2 == null) {
                }
            }
            root.flags |= 1;
            root.rootTag = rootEnumerator.getValue();
            roots.add(root);
        }
        this.stringPositions.clear();
        this.id2Actions.clear();
        return new ParseResult(this.classes, this.instances, roots, this.instancesFound);
    }

    private void parseString() {
        int position = this.buffer.position();
        int totalLength = this.buffer.getInt();
        this.stringPositions.putIfAbsent(this.parseContext.readId(), position);
        ParseContext parseContext2 = this.parseContext;
        parseContext2.skipBytes(totalLength - parseContext2.getIdSize());
    }

    private void parseClassName() {
        this.buffer.getInt();
        this.buffer.getInt();
        int classPosition = this.buffer.position();
        int id = this.parseContext.readId();
        this.buffer.getInt();
        int classNamePosition = this.stringPositions.get(this.parseContext.readId());
        HprofClass clazz = new HprofClass(classPosition, classNamePosition);
        this.classes.putIfAbsent(id, clazz);
        ParseAction parseAction = this.actionsForClass.get(this.buffer, this.parseContext.getStringBytesPos(classNamePosition), this.parseContext.getStringLength(classNamePosition));
        if (parseAction == ParseAction.IDENTIFY_OBJECT_CLASS) {
            this.objectClassId = id;
        } else if (parseAction == ParseAction.IDENTIFY_JAVA_LANG_CLASS) {
            HprofClass.setJavaLangClass(clazz);
        } else if (parseAction == ParseAction.CLASSIFY_REF) {
            clazz.flags |= 2;
        } else if (parseAction != null) {
            this.id2Actions.putIfAbsent(id, parseAction);
        }
    }

    private void parseHeapDump() {
        int end = this.buffer.position() + this.buffer.getInt();
        while (this.buffer.position() < end) {
            int tag = this.buffer.get() & 255;
            if (this.parseContext.isRootTag(tag)) {
                int size = this.parseContext.getRootTagSize(tag);
                if (this.rootTagsToExclude.containsKey(tag)) {
                    this.parseContext.skipBytes(size);
                } else {
                    this.rootIds.putIfAbsent(this.parseContext.readId(), tag);
                    ParseContext parseContext2 = this.parseContext;
                    parseContext2.skipBytes(size - parseContext2.getIdSize());
                }
            } else {
                if (tag != 195) {
                    if (tag != 254) {
                        switch (tag) {
                            case 32:
                                int classId = this.parseContext.readId();
                                if (classId != this.objectClassId) {
                                    this.classes.get(classId).parse(this.parseContext, this.classes, this.stringPositions);
                                    break;
                                } else {
                                    this.classes.get(classId).skip(this.parseContext, this.classes);
                                    break;
                                }
                            case 33:
                                parseClassInstance();
                                break;
                            case 34:
                                parseObjectArray();
                                break;
                            case 35:
                                break;
                            default:
                                StringBuilder sb = new StringBuilder(23);
                                sb.append("Unknown tag ");
                                sb.append(tag);
                                throw new IllegalArgumentException(sb.toString());
                        }
                    } else {
                        this.buffer.getInt();
                        this.heapName = this.parseContext.readString(this.stringPositions.get(this.parseContext.readId()));
                    }
                }
                parsePrimitiveArray();
            }
        }
        Preconditions.checkState(this.buffer.position() == end);
    }

    private void parseClassInstance() {
        int position = this.buffer.position();
        int id = this.parseContext.readId();
        this.buffer.getInt();
        int classId = this.parseContext.readId();
        int remaining = this.buffer.getInt();
        HprofClass clazz = this.classes.get(classId);
        ParseAction parseAction = this.id2Actions.get(classId);
        if (!(clazz == null || parseAction == ParseAction.EXCLUDE_INSTANCE)) {
            HprofClassInstance classInstance = new HprofClassInstance(position, clazz);
            classInstance.heapName = this.heapName;
            this.instances.putIfAbsent(id, classInstance);
            if (parseAction == ParseAction.FIND_INSTANCE) {
                String className = clazz.getClassName(this.parseContext);
                List<HprofObject> instances2 = this.instancesFound.get(className);
                if (instances2 == null) {
                    instances2 = new ArrayList<>();
                    this.instancesFound.put(className, instances2);
                }
                instances2.add(classInstance);
            }
        }
        this.parseContext.skipBytes(remaining);
    }

    private void parseObjectArray() {
        int position = this.buffer.position();
        int id = this.parseContext.readId();
        this.buffer.getInt();
        int numElements = this.buffer.getInt();
        int classId = this.parseContext.readId();
        ParseAction parseAction = this.id2Actions.get(classId);
        if (this.classes.containsKey(classId) && parseAction != ParseAction.EXCLUDE_INSTANCE) {
            HprofArrayInstance arrayInstance = new HprofArrayInstance(position, this.classes.get(classId));
            arrayInstance.heapName = this.heapName;
            this.instances.putIfAbsent(id, arrayInstance);
        }
        ParseContext parseContext2 = this.parseContext;
        parseContext2.skipBytes(parseContext2.getIdSize() * numElements);
    }

    private void parsePrimitiveArray() {
        int position = this.buffer.position();
        int id = this.parseContext.readId();
        this.buffer.getInt();
        int numElements = this.buffer.getInt();
        int type = this.buffer.get();
        ParseAction parseAction = this.type2Actions.get(type);
        ParseContext parseContext2 = this.parseContext;
        parseContext2.skipBytes(parseContext2.getTypeSize(type) * numElements);
        if (parseAction != ParseAction.EXCLUDE_INSTANCE) {
            HprofPrimitiveArrayInstance primitiveArrayInstance = new HprofPrimitiveArrayInstance(position);
            primitiveArrayInstance.heapName = this.heapName;
            this.instances.putIfAbsent(id, primitiveArrayInstance);
        }
    }
}
