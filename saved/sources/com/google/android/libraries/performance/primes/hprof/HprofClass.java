package com.google.android.libraries.performance.primes.hprof;

import android.support.p001v4.internal.view.SupportMenu;
import com.google.android.libraries.performance.primes.hprof.collect.IntIntMap;
import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import com.google.android.libraries.stitch.util.Preconditions;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class HprofClass extends HprofObject {
    private static HprofClass javaLangClass;
    private final int classNamePosition;
    private int[] fieldNamePositions;
    private int fieldsCount = -1;
    private int instanceSize;
    private int[] offsets;
    private int[] staticFieldNamePositions;
    private int staticFieldsSize;
    private int[] staticValueIds;
    private HprofClass superClass;
    private int totalOffset;

    public static void setJavaLangClass(HprofClass javaLangClass2) {
        javaLangClass = javaLangClass2;
    }

    public HprofClass(int position, int classNamePosition2) {
        super(position);
        this.classNamePosition = classNamePosition2;
    }

    public int getFieldsCount() {
        return this.fieldsCount;
    }

    public int getInstanceSize() {
        return this.instanceSize;
    }

    public int getChildCount(ParseContext parseContext) {
        return this.staticValueIds.length;
    }

    public int getChildValue(ParseContext parseContext, int index) {
        return this.staticValueIds[index];
    }

    public String getChildName(ParseContext parseContext, int index) {
        return parseContext.readString(this.staticFieldNamePositions[index]);
    }

    public String buildLeakSegment(ParseContext parseContext, int fieldIndex) {
        if (fieldIndex < 0 || fieldIndex >= this.staticValueIds.length) {
            String valueOf = String.valueOf(getClassName(parseContext));
            return valueOf.length() != 0 ? "static ".concat(valueOf) : new String("static ");
        }
        String className = getClassName(parseContext);
        String childName = getChildName(parseContext, fieldIndex);
        StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 8 + String.valueOf(childName).length());
        sb.append("static ");
        sb.append(className);
        sb.append("#");
        sb.append(childName);
        return sb.toString();
    }

    public int computeShallowSize(ParseContext parseContext) {
        return this.staticFieldsSize + javaLangClass.instanceSize;
    }

    /* access modifiers changed from: package-private */
    public String getFieldName(ParseContext parseContext, int index) {
        Preconditions.checkElementIndex(index, this.fieldsCount);
        int[] iArr = this.fieldNamePositions;
        if (index < iArr.length) {
            return parseContext.readString(iArr[index]);
        }
        return this.superClass.getFieldName(parseContext, index - iArr.length);
    }

    /* access modifiers changed from: package-private */
    public HprofClass getDeclaringClassForField(int fieldIndex) {
        Preconditions.checkElementIndex(fieldIndex, this.fieldsCount);
        int[] iArr = this.fieldNamePositions;
        if (fieldIndex < iArr.length) {
            return this;
        }
        return this.superClass.getDeclaringClassForField(fieldIndex - iArr.length);
    }

    /* access modifiers changed from: package-private */
    public void resolveSuperClasses() {
        if (this.fieldsCount == -1) {
            this.fieldsCount = this.fieldNamePositions.length;
            HprofClass hprofClass = this.superClass;
            if (hprofClass != null) {
                hprofClass.resolveSuperClasses();
                int i = this.fieldsCount;
                HprofClass hprofClass2 = this.superClass;
                this.fieldsCount = i + hprofClass2.fieldsCount;
                if ((hprofClass2.flags & 2) != 0) {
                    this.flags |= 2;
                }
            }
        }
    }

    public HprofClass getSuperClass() {
        return this.superClass;
    }

    public String getClassName(ParseContext parseContext) {
        return parseContext.readString(this.classNamePosition);
    }

    public int getFieldValue(ParseContext parseContext, HprofClassInstance instance, int index) {
        Preconditions.checkElementIndex(index, this.fieldsCount);
        return getFieldValueInternal(parseContext, instance.position + parseContext.getIdSize() + 4 + parseContext.getIdSize() + 4, index);
    }

    private int getFieldValueInternal(ParseContext parseContext, int initialPosition, int index) {
        int[] iArr = this.fieldNamePositions;
        if (index < iArr.length) {
            return parseContext.readId(this.offsets[index] + initialPosition);
        }
        return this.superClass.getFieldValueInternal(parseContext, this.totalOffset + initialPosition, index - iArr.length);
    }

    public void parse(ParseContext parseContext, IntObjectMap<HprofClass> classes, IntIntMap stringPositions) {
        ByteBuffer buffer = parseContext.getBuffer();
        this.position = buffer.position() - parseContext.getIdSize();
        buffer.getInt();
        this.superClass = classes.get(parseContext.readId());
        parseContext.skipBytes(parseContext.getIdSize() * 5);
        this.instanceSize = buffer.getInt();
        skipClassConstants(parseContext);
        parseStaticFields(parseContext, stringPositions);
        parseFields(parseContext, stringPositions);
    }

    public void skip(ParseContext parseContext, IntObjectMap<HprofClass> classes) {
        ByteBuffer buffer = parseContext.getBuffer();
        this.position = buffer.position() - parseContext.getIdSize();
        buffer.getInt();
        this.superClass = classes.get(parseContext.readId());
        parseContext.skipBytes(parseContext.getIdSize() * 5);
        this.instanceSize = buffer.getInt();
        skipClassConstants(parseContext);
        skipStaticFields(parseContext);
        skipFields(parseContext);
    }

    private void skipClassConstants(ParseContext parseContext) {
        ByteBuffer buffer = parseContext.getBuffer();
        int constantsCount = buffer.getShort() & SupportMenu.USER_MASK;
        for (int i = 0; i < constantsCount; i++) {
            buffer.getShort();
            parseContext.skipBytes(parseContext.getTypeSize(buffer.get()));
        }
    }

    private void parseStaticFields(ParseContext parseContext, IntIntMap stringPositions) {
        int[] iArr;
        int[] iArr2;
        ByteBuffer buffer = parseContext.getBuffer();
        int staticFieldCount = buffer.getShort() & 65535;
        this.staticValueIds = new int[staticFieldCount];
        this.staticFieldNamePositions = new int[staticFieldCount];
        int validStaticFieldCount = 0;
        for (int i = 0; i < staticFieldCount; i++) {
            int nameId = parseContext.readId();
            int type = buffer.get();
            this.staticFieldsSize += parseContext.getTypeSize(type);
            if (parseContext.isObjectType(type)) {
                int valueId = parseContext.readId();
                if (valueId != 0) {
                    this.staticValueIds[validStaticFieldCount] = valueId;
                    this.staticFieldNamePositions[validStaticFieldCount] = stringPositions.get(nameId);
                    validStaticFieldCount++;
                }
            } else {
                parseContext.skipBytes(parseContext.getTypeSize(type));
            }
        }
        if (validStaticFieldCount == staticFieldCount) {
            iArr = this.staticValueIds;
        } else {
            iArr = Arrays.copyOf(this.staticValueIds, validStaticFieldCount);
        }
        this.staticValueIds = iArr;
        if (validStaticFieldCount == staticFieldCount) {
            iArr2 = this.staticFieldNamePositions;
        } else {
            iArr2 = Arrays.copyOf(this.staticFieldNamePositions, validStaticFieldCount);
        }
        this.staticFieldNamePositions = iArr2;
    }

    private void skipStaticFields(ParseContext parseContext) {
        ByteBuffer buffer = parseContext.getBuffer();
        int staticFieldCount = buffer.getShort() & SupportMenu.USER_MASK;
        for (int i = 0; i < staticFieldCount; i++) {
            parseContext.readId();
            parseContext.skipBytes(parseContext.getTypeSize(buffer.get()));
        }
        this.staticValueIds = new int[0];
        this.staticFieldNamePositions = new int[0];
    }

    private void parseFields(ParseContext parseContext, IntIntMap stringPositions) {
        int[] iArr;
        int[] iArr2;
        ByteBuffer buffer = parseContext.getBuffer();
        int totalFieldCount = buffer.getShort() & 65535;
        this.fieldNamePositions = new int[totalFieldCount];
        this.offsets = new int[totalFieldCount];
        int fieldsCount2 = 0;
        this.totalOffset = 0;
        for (int i = 0; i < totalFieldCount; i++) {
            int nameId = parseContext.readId();
            int type = buffer.get();
            if (parseContext.isObjectType(type)) {
                this.fieldNamePositions[fieldsCount2] = stringPositions.get(nameId);
                this.offsets[fieldsCount2] = this.totalOffset;
                fieldsCount2++;
            }
            this.totalOffset += parseContext.getTypeSize(type);
        }
        if (fieldsCount2 == totalFieldCount) {
            iArr = this.fieldNamePositions;
        } else {
            iArr = Arrays.copyOf(this.fieldNamePositions, fieldsCount2);
        }
        this.fieldNamePositions = iArr;
        if (fieldsCount2 == totalFieldCount) {
            iArr2 = this.offsets;
        } else {
            iArr2 = Arrays.copyOf(this.offsets, fieldsCount2);
        }
        this.offsets = iArr2;
    }

    private void skipFields(ParseContext parseContext) {
        ByteBuffer buffer = parseContext.getBuffer();
        int totalFieldCount = buffer.getShort() & SupportMenu.USER_MASK;
        this.totalOffset = 0;
        for (int i = 0; i < totalFieldCount; i++) {
            parseContext.readId();
            this.totalOffset += parseContext.getTypeSize(buffer.get());
        }
        this.fieldNamePositions = new int[0];
        this.offsets = new int[0];
    }
}
