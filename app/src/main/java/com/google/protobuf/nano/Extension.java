package com.google.protobuf.nano;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLite;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Extension<M extends ExtendableMessageNano<M>, T> {
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    public final int tag;
    protected final Class<T> clazz;
    protected final GeneratedMessageLite<?, ?> defaultInstance;
    protected final boolean repeated;
    protected final int type;

    private Extension(int type2, Class<T> clazz2, int tag2, boolean repeated2) {
        this(type2, clazz2, (GeneratedMessageLite<?, ?>) null, tag2, repeated2);
    }

    private Extension(int type2, Class<T> clazz2, GeneratedMessageLite<?, ?> defaultInstance2, int tag2, boolean repeated2) {
        this.type = type2;
        this.clazz = clazz2;
        this.tag = tag2;
        this.repeated = repeated2;
        this.defaultInstance = defaultInstance2;
    }

    @Deprecated
    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int type2, Class<T> clazz2, int tag2) {
        return new Extension<>(type2, clazz2, tag2, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int type2, Class<T> clazz2, long tag2) {
        return new Extension<>(type2, clazz2, (int) tag2, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, com.google.protobuf.GeneratedMessageLite<?, ?>, int, boolean):void
     arg types: [int, java.lang.Class<T>, T, int, int]
     candidates:
      com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, int, boolean, com.google.protobuf.nano.Extension$1):void
      com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, com.google.protobuf.GeneratedMessageLite<?, ?>, int, boolean):void */
    public static <M extends ExtendableMessageNano<M>, T extends GeneratedMessageLite<?, ?>> Extension<M, T> createMessageLiteTyped(int type2, Class<T> clazz2, T defaultInstance2, long tag2) {
        return new Extension(type2, (Class) clazz2, (GeneratedMessageLite<?, ?>) defaultInstance2, (int) tag2, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends Internal.EnumLite> Extension<M, T> createEnumLiteTyped(int type2, Class<T> clazz2, Internal.EnumLiteMap<T> enumLiteMap, long tag2) {
        return new EnumLiteExtension(type2, clazz2, enumLiteMap, (int) tag2, false, 0, 0);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T[]> createRepeatedMessageTyped(int type2, Class<T[]> clazz2, long tag2) {
        return new Extension<>(type2, clazz2, (int) tag2, true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, com.google.protobuf.GeneratedMessageLite<?, ?>, int, boolean):void
     arg types: [int, java.lang.Class<T[]>, T, int, int]
     candidates:
      com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, int, boolean, com.google.protobuf.nano.Extension$1):void
      com.google.protobuf.nano.Extension.<init>(int, java.lang.Class, com.google.protobuf.GeneratedMessageLite<?, ?>, int, boolean):void */
    public static <M extends ExtendableMessageNano<M>, T extends GeneratedMessageLite<?, ?>> Extension<M, T[]> createRepeatedMessageLiteTyped(int type2, Class<T[]> clazz2, T defaultInstance2, long tag2) {
        return new Extension(type2, (Class) clazz2, (GeneratedMessageLite<?, ?>) defaultInstance2, (int) tag2, true);
    }

    public static <M extends ExtendableMessageNano<M>, T extends Internal.EnumLite> Extension<M, T[]> createRepeatedEnumLiteTyped(int type2, Class<T[]> clazz2, Internal.EnumLiteMap<T> enumLiteMap, long tag2, long nonPackedTag, long packedTag) {
        return new EnumLiteExtension(type2, clazz2, enumLiteMap, (int) tag2, true, (int) nonPackedTag, (int) packedTag);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createPrimitiveTyped(int type2, Class<T> clazz2, long tag2) {
        return new PrimitiveExtension(type2, clazz2, (int) tag2, false, 0, 0);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createRepeatedPrimitiveTyped(int type2, Class<T> clazz2, long tag2, long nonPackedTag, long packedTag) {
        return new PrimitiveExtension(type2, clazz2, (int) tag2, true, (int) nonPackedTag, (int) packedTag);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Extension)) {
            return false;
        }
        Extension<?, ?> that = (Extension) other;
        if (this.type == that.type && this.clazz == that.clazz && this.tag == that.tag && this.repeated == that.repeated) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((37 * 31) + this.type) * 31) + this.clazz.hashCode()) * 31) + this.tag) * 31) + (this.repeated ? 1 : 0);
    }

    /* access modifiers changed from: package-private */
    public final T getValueFrom(List<UnknownFieldData> unknownFields) {
        if (unknownFields == null) {
            return null;
        }
        return this.repeated ? getRepeatedValueFrom(unknownFields) : getSingularValueFrom(unknownFields);
    }

    /* JADX INFO: Multiple debug info for r1v2 int: [D('resultSize' int), D('i' int)] */
    private T getRepeatedValueFrom(List<UnknownFieldData> unknownFields) {
        List<Object> resultList = new ArrayList<>();
        for (int i = 0; i < unknownFields.size(); i++) {
            UnknownFieldData data = unknownFields.get(i);
            if (data.bytes.length != 0) {
                readDataInto(data, resultList);
            }
        }
        int i2 = resultList.size();
        if (i2 == 0) {
            return null;
        }
        Class<T> cls = this.clazz;
        T result = cls.cast(Array.newInstance(cls.getComponentType(), i2));
        for (int i3 = 0; i3 < i2; i3++) {
            Array.set(result, i3, resultList.get(i3));
        }
        return result;
    }

    private T getSingularValueFrom(List<UnknownFieldData> unknownFields) {
        if (unknownFields.isEmpty()) {
            return null;
        }
        return this.clazz.cast(readData(CodedInputByteBufferNano.newInstance(unknownFields.get(unknownFields.size() - 1).bytes)));
    }

    /* access modifiers changed from: protected */
    public Object readData(CodedInputByteBufferNano input) {
        Class componentType = this.repeated ? this.clazz.getComponentType() : this.clazz;
        try {
            int i = this.type;
            if (i != 10) {
                if (i != 11) {
                    int i2 = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i2);
                    throw new IllegalArgumentException(sb.toString());
                } else if (this.defaultInstance != null) {
                    return input.readMessageLite(this.defaultInstance.getParserForType());
                } else {
                    MessageNano message = (MessageNano) componentType.newInstance();
                    input.readMessage(message);
                    return message;
                }
            } else if (this.defaultInstance != null) {
                return input.readGroupLite(this.defaultInstance.getParserForType(), WireFormatNano.getTagFieldNumber(this.tag));
            } else {
                MessageNano group = (MessageNano) componentType.newInstance();
                input.readGroup(group, WireFormatNano.getTagFieldNumber(this.tag));
                return group;
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    /* access modifiers changed from: protected */
    public void readDataInto(UnknownFieldData data, List<Object> resultList) {
        resultList.add(readData(CodedInputByteBufferNano.newInstance(data.bytes)));
    }

    /* access modifiers changed from: package-private */
    public void writeTo(Object value, CodedOutputByteBufferNano output) throws IOException {
        if (this.repeated) {
            writeRepeatedData(value, output);
        } else {
            writeSingularData(value, output);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeAsMessageSetTo(Object value, CodedOutputByteBufferNano output) throws IOException {
        if (this.repeated) {
            writeRepeatedDataAsMessageSet(value, output);
        } else {
            writeSingularDataAsMessageSet(value, output);
        }
    }

    /* access modifiers changed from: protected */
    public void writeSingularData(Object value, CodedOutputByteBufferNano out) {
        try {
            out.writeRawVarint32(this.tag);
            int i = this.type;
            if (i == 10) {
                int fieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
                if (this.defaultInstance == null) {
                    out.writeGroupNoTag((MessageNano) value);
                } else {
                    out.writeGroupNoTag((MessageLite) value);
                }
                out.writeTag(fieldNumber, 4);
            } else if (i != 11) {
                int i2 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.defaultInstance == null) {
                out.writeMessageNoTag((MessageNano) value);
            } else {
                out.writeMessageNoTag((MessageLite) value);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void writeSingularDataAsMessageSet(Object value, CodedOutputByteBufferNano out) throws IOException {
        out.writeMessageSetExtension(WireFormatNano.getTagFieldNumber(this.tag), (MessageNano) value);
    }

    /* access modifiers changed from: protected */
    public void writeRepeatedData(Object array, CodedOutputByteBufferNano output) {
        int arrayLength = Array.getLength(array);
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(array, i);
            if (element != null) {
                writeSingularData(element, output);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeRepeatedDataAsMessageSet(Object array, CodedOutputByteBufferNano output) throws IOException {
        int arrayLength = Array.getLength(array);
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(array, i);
            if (element != null) {
                writeSingularDataAsMessageSet(element, output);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSize(Object value) {
        if (this.repeated) {
            return computeRepeatedSerializedSize(value);
        }
        return computeSingularSerializedSize(value);
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSizeAsMessageSet(Object value) {
        if (this.repeated) {
            return computeRepeatedSerializedSizeAsMessageSet(value);
        }
        return computeSingularSerializedSizeAsMessageSet(value);
    }

    /* access modifiers changed from: protected */
    public int computeRepeatedSerializedSize(Object array) {
        int size = 0;
        int arrayLength = Array.getLength(array);
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(array, i);
            if (element != null) {
                size += computeSingularSerializedSize(element);
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public int computeSingularSerializedSize(Object value) {
        int fieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
        int i = this.type;
        if (i != 10) {
            if (i != 11) {
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.defaultInstance == null) {
                return CodedOutputByteBufferNano.computeMessageSize(fieldNumber, (MessageNano) value);
            } else {
                return CodedOutputStream.computeMessageSize(fieldNumber, (MessageLite) value);
            }
        } else if (this.defaultInstance == null) {
            return CodedOutputByteBufferNano.computeGroupSize(fieldNumber, (MessageNano) value);
        } else {
            return CodedOutputStream.computeGroupSize(fieldNumber, (MessageLite) value);
        }
    }

    /* access modifiers changed from: protected */
    public int computeRepeatedSerializedSizeAsMessageSet(Object array) {
        int size = 0;
        int arrayLength = Array.getLength(array);
        for (int i = 0; i < arrayLength; i++) {
            if (Array.get(array, i) != null) {
                size += computeSingularSerializedSizeAsMessageSet(Array.get(array, i));
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public int computeSingularSerializedSizeAsMessageSet(Object value) {
        return CodedOutputByteBufferNano.computeMessageSetExtensionSize(WireFormatNano.getTagFieldNumber(this.tag), (MessageNano) value);
    }

    private static class PrimitiveExtension<M extends ExtendableMessageNano<M>, T> extends Extension<M, T> {
        private final int nonPackedTag;
        private final int packedTag;

        public PrimitiveExtension(int type, Class<T> clazz, int tag, boolean repeated, int nonPackedTag2, int packedTag2) {
            super(type, clazz, tag, repeated);
            this.nonPackedTag = nonPackedTag2;
            this.packedTag = packedTag2;
        }

        /* access modifiers changed from: protected */
        public Object readData(CodedInputByteBufferNano input) {
            try {
                switch (this.type) {
                    case 1:
                        return Double.valueOf(input.readDouble());
                    case 2:
                        return Float.valueOf(input.readFloat());
                    case 3:
                        return Long.valueOf(input.readInt64());
                    case 4:
                        return Long.valueOf(input.readUInt64());
                    case 5:
                        return Integer.valueOf(input.readInt32());
                    case 6:
                        return Long.valueOf(input.readFixed64());
                    case 7:
                        return Integer.valueOf(input.readFixed32());
                    case 8:
                        return Boolean.valueOf(input.readBool());
                    case 9:
                        return input.readString();
                    case 10:
                    case 11:
                    default:
                        int i = this.type;
                        StringBuilder sb = new StringBuilder(24);
                        sb.append("Unknown type ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    case 12:
                        return input.readBytes();
                    case 13:
                        return Integer.valueOf(input.readUInt32());
                    case 14:
                        return Integer.valueOf(input.readEnum());
                    case 15:
                        return Integer.valueOf(input.readSFixed32());
                    case 16:
                        return Long.valueOf(input.readSFixed64());
                    case 17:
                        return Integer.valueOf(input.readSInt32());
                    case 18:
                        return Long.valueOf(input.readSInt64());
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Error reading extension field", e);
            }
        }

        /* access modifiers changed from: protected */
        public void readDataInto(UnknownFieldData data, List<Object> resultList) {
            if (data.tag == this.nonPackedTag) {
                resultList.add(readData(CodedInputByteBufferNano.newInstance(data.bytes)));
                return;
            }
            CodedInputByteBufferNano buffer = CodedInputByteBufferNano.newInstance(data.bytes);
            try {
                buffer.pushLimit(buffer.readRawVarint32());
                while (!buffer.isAtEnd()) {
                    resultList.add(readData(buffer));
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Error reading extension field", e);
            }
        }

        /* access modifiers changed from: protected */
        public void writeSingularData(Object value, CodedOutputByteBufferNano output) {
            try {
                output.writeRawVarint32(this.tag);
                switch (this.type) {
                    case 1:
                        output.writeDoubleNoTag(((Double) value).doubleValue());
                        return;
                    case 2:
                        output.writeFloatNoTag(((Float) value).floatValue());
                        return;
                    case 3:
                        output.writeInt64NoTag(((Long) value).longValue());
                        return;
                    case 4:
                        output.writeUInt64NoTag(((Long) value).longValue());
                        return;
                    case 5:
                        output.writeInt32NoTag(((Integer) value).intValue());
                        return;
                    case 6:
                        output.writeFixed64NoTag(((Long) value).longValue());
                        return;
                    case 7:
                        output.writeFixed32NoTag(((Integer) value).intValue());
                        return;
                    case 8:
                        output.writeBoolNoTag(((Boolean) value).booleanValue());
                        return;
                    case 9:
                        output.writeStringNoTag((String) value);
                        return;
                    case 10:
                    case 11:
                    default:
                        int i = this.type;
                        StringBuilder sb = new StringBuilder(24);
                        sb.append("Unknown type ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    case 12:
                        output.writeBytesNoTag((byte[]) value);
                        return;
                    case 13:
                        output.writeUInt32NoTag(((Integer) value).intValue());
                        return;
                    case 14:
                        output.writeEnumNoTag(((Integer) value).intValue());
                        return;
                    case 15:
                        output.writeSFixed32NoTag(((Integer) value).intValue());
                        return;
                    case 16:
                        output.writeSFixed64NoTag(((Long) value).longValue());
                        return;
                    case 17:
                        output.writeSInt32NoTag(((Integer) value).intValue());
                        return;
                    case 18:
                        output.writeSInt64NoTag(((Long) value).longValue());
                        return;
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        /* access modifiers changed from: protected */
        public void writeRepeatedData(Object array, CodedOutputByteBufferNano output) {
            if (this.tag == this.nonPackedTag) {
                Extension.super.writeRepeatedData(array, output);
            } else if (this.tag == this.packedTag) {
                int arrayLength = Array.getLength(array);
                int dataSize = computePackedDataSize(array);
                try {
                    output.writeRawVarint32(this.tag);
                    output.writeRawVarint32(dataSize);
                    switch (this.type) {
                        case 1:
                            for (int i = 0; i < arrayLength; i++) {
                                output.writeDoubleNoTag(Array.getDouble(array, i));
                            }
                            return;
                        case 2:
                            for (int i2 = 0; i2 < arrayLength; i2++) {
                                output.writeFloatNoTag(Array.getFloat(array, i2));
                            }
                            return;
                        case 3:
                            for (int i3 = 0; i3 < arrayLength; i3++) {
                                output.writeInt64NoTag(Array.getLong(array, i3));
                            }
                            return;
                        case 4:
                            for (int i4 = 0; i4 < arrayLength; i4++) {
                                output.writeUInt64NoTag(Array.getLong(array, i4));
                            }
                            return;
                        case 5:
                            for (int i5 = 0; i5 < arrayLength; i5++) {
                                output.writeInt32NoTag(Array.getInt(array, i5));
                            }
                            return;
                        case 6:
                            for (int i6 = 0; i6 < arrayLength; i6++) {
                                output.writeFixed64NoTag(Array.getLong(array, i6));
                            }
                            return;
                        case 7:
                            for (int i7 = 0; i7 < arrayLength; i7++) {
                                output.writeFixed32NoTag(Array.getInt(array, i7));
                            }
                            return;
                        case 8:
                            for (int i8 = 0; i8 < arrayLength; i8++) {
                                output.writeBoolNoTag(Array.getBoolean(array, i8));
                            }
                            return;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        default:
                            int i9 = this.type;
                            StringBuilder sb = new StringBuilder(27);
                            sb.append("Unpackable type ");
                            sb.append(i9);
                            throw new IllegalArgumentException(sb.toString());
                        case 13:
                            for (int i10 = 0; i10 < arrayLength; i10++) {
                                output.writeUInt32NoTag(Array.getInt(array, i10));
                            }
                            return;
                        case 14:
                            for (int i11 = 0; i11 < arrayLength; i11++) {
                                output.writeEnumNoTag(Array.getInt(array, i11));
                            }
                            return;
                        case 15:
                            for (int i12 = 0; i12 < arrayLength; i12++) {
                                output.writeSFixed32NoTag(Array.getInt(array, i12));
                            }
                            return;
                        case 16:
                            for (int i13 = 0; i13 < arrayLength; i13++) {
                                output.writeSFixed64NoTag(Array.getLong(array, i13));
                            }
                            return;
                        case 17:
                            for (int i14 = 0; i14 < arrayLength; i14++) {
                                output.writeSInt32NoTag(Array.getInt(array, i14));
                            }
                            return;
                        case 18:
                            for (int i15 = 0; i15 < arrayLength; i15++) {
                                output.writeSInt64NoTag(Array.getLong(array, i15));
                            }
                            return;
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                int i16 = this.tag;
                int i17 = this.nonPackedTag;
                int i18 = this.packedTag;
                StringBuilder sb2 = new StringBuilder(124);
                sb2.append("Unexpected repeated extension tag ");
                sb2.append(i16);
                sb2.append(", unequal to both non-packed variant ");
                sb2.append(i17);
                sb2.append(" and packed variant ");
                sb2.append(i18);
                throw new IllegalArgumentException(sb2.toString());
            }
        }

        private int computePackedDataSize(Object array) {
            int dataSize = 0;
            int arrayLength = Array.getLength(array);
            switch (this.type) {
                case 1:
                case 6:
                case 16:
                    return arrayLength * 8;
                case 2:
                case 7:
                case 15:
                    return arrayLength * 4;
                case 3:
                    for (int i = 0; i < arrayLength; i++) {
                        dataSize += CodedOutputByteBufferNano.computeInt64SizeNoTag(Array.getLong(array, i));
                    }
                    return dataSize;
                case 4:
                    for (int i2 = 0; i2 < arrayLength; i2++) {
                        dataSize += CodedOutputByteBufferNano.computeUInt64SizeNoTag(Array.getLong(array, i2));
                    }
                    return dataSize;
                case 5:
                    for (int i3 = 0; i3 < arrayLength; i3++) {
                        dataSize += CodedOutputByteBufferNano.computeInt32SizeNoTag(Array.getInt(array, i3));
                    }
                    return dataSize;
                case 8:
                    return arrayLength;
                case 9:
                case 10:
                case 11:
                case 12:
                default:
                    int i4 = this.type;
                    StringBuilder sb = new StringBuilder(40);
                    sb.append("Unexpected non-packable type ");
                    sb.append(i4);
                    throw new IllegalArgumentException(sb.toString());
                case 13:
                    for (int i5 = 0; i5 < arrayLength; i5++) {
                        dataSize += CodedOutputByteBufferNano.computeUInt32SizeNoTag(Array.getInt(array, i5));
                    }
                    return dataSize;
                case 14:
                    for (int i6 = 0; i6 < arrayLength; i6++) {
                        dataSize += CodedOutputByteBufferNano.computeEnumSizeNoTag(Array.getInt(array, i6));
                    }
                    return dataSize;
                case 17:
                    for (int i7 = 0; i7 < arrayLength; i7++) {
                        dataSize += CodedOutputByteBufferNano.computeSInt32SizeNoTag(Array.getInt(array, i7));
                    }
                    return dataSize;
                case 18:
                    for (int i8 = 0; i8 < arrayLength; i8++) {
                        dataSize += CodedOutputByteBufferNano.computeSInt64SizeNoTag(Array.getLong(array, i8));
                    }
                    return dataSize;
            }
        }

        /* access modifiers changed from: protected */
        public int computeRepeatedSerializedSize(Object array) {
            if (this.tag == this.nonPackedTag) {
                return Extension.super.computeRepeatedSerializedSize(array);
            }
            if (this.tag == this.packedTag) {
                int dataSize = computePackedDataSize(array);
                return CodedOutputByteBufferNano.computeRawVarint32Size(this.tag) + CodedOutputByteBufferNano.computeRawVarint32Size(dataSize) + dataSize;
            }
            int i = this.tag;
            int i2 = this.nonPackedTag;
            int i3 = this.packedTag;
            StringBuilder sb = new StringBuilder(124);
            sb.append("Unexpected repeated extension tag ");
            sb.append(i);
            sb.append(", unequal to both non-packed variant ");
            sb.append(i2);
            sb.append(" and packed variant ");
            sb.append(i3);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public int computeSingularSerializedSize(Object value) {
            Integer enumValue;
            int fieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
            switch (this.type) {
                case 1:
                    return CodedOutputByteBufferNano.computeDoubleSize(fieldNumber, ((Double) value).doubleValue());
                case 2:
                    return CodedOutputByteBufferNano.computeFloatSize(fieldNumber, ((Float) value).floatValue());
                case 3:
                    return CodedOutputByteBufferNano.computeInt64Size(fieldNumber, ((Long) value).longValue());
                case 4:
                    return CodedOutputByteBufferNano.computeUInt64Size(fieldNumber, ((Long) value).longValue());
                case 5:
                    return CodedOutputByteBufferNano.computeInt32Size(fieldNumber, ((Integer) value).intValue());
                case 6:
                    return CodedOutputByteBufferNano.computeFixed64Size(fieldNumber, ((Long) value).longValue());
                case 7:
                    return CodedOutputByteBufferNano.computeFixed32Size(fieldNumber, ((Integer) value).intValue());
                case 8:
                    return CodedOutputByteBufferNano.computeBoolSize(fieldNumber, ((Boolean) value).booleanValue());
                case 9:
                    return CodedOutputByteBufferNano.computeStringSize(fieldNumber, (String) value);
                case 10:
                case 11:
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                case 12:
                    return CodedOutputByteBufferNano.computeBytesSize(fieldNumber, (byte[]) value);
                case 13:
                    return CodedOutputByteBufferNano.computeUInt32Size(fieldNumber, ((Integer) value).intValue());
                case 14:
                    if (value instanceof Internal.EnumLite) {
                        enumValue = Integer.valueOf(((Internal.EnumLite) value).getNumber());
                    } else {
                        enumValue = (Integer) value;
                    }
                    return CodedOutputByteBufferNano.computeEnumSize(fieldNumber, enumValue.intValue());
                case 15:
                    return CodedOutputByteBufferNano.computeSFixed32Size(fieldNumber, ((Integer) value).intValue());
                case 16:
                    return CodedOutputByteBufferNano.computeSFixed64Size(fieldNumber, ((Long) value).longValue());
                case 17:
                    return CodedOutputByteBufferNano.computeSInt32Size(fieldNumber, ((Integer) value).intValue());
                case 18:
                    return CodedOutputByteBufferNano.computeSInt64Size(fieldNumber, ((Long) value).longValue());
            }
        }
    }

    private static final class EnumLiteExtension<M extends ExtendableMessageNano<M>, EnumT extends Internal.EnumLite, ExtensionT> extends PrimitiveExtension<M, ExtensionT> {
        private final Internal.EnumLiteMap<EnumT> enumLiteMap;

        private EnumLiteExtension(int type, Class<ExtensionT> clazz, Internal.EnumLiteMap<EnumT> enumLiteMap2, int tag, boolean repeated, int nonPackedTag, int packedTag) {
            super(type, clazz, tag, repeated, nonPackedTag, packedTag);
            this.enumLiteMap = enumLiteMap2;
        }

        /* access modifiers changed from: protected */
        public final Object readData(CodedInputByteBufferNano input) {
            return this.enumLiteMap.findValueByNumber(((Integer) super.readData(input)).intValue());
        }

        /* access modifiers changed from: protected */
        public final void writeSingularData(Object value, CodedOutputByteBufferNano output) {
            super.writeSingularData(Integer.valueOf(((Internal.EnumLite) value).getNumber()), output);
        }

        /* access modifiers changed from: protected */
        public final int computeSingularSerializedSize(Object value) {
            return super.computeSingularSerializedSize(Integer.valueOf(((Internal.EnumLite) value).getNumber()));
        }
    }
}
