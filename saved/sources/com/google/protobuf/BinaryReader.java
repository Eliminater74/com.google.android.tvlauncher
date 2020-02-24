package com.google.protobuf;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

abstract class BinaryReader implements Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;

    public abstract int getTotalBytesRead();

    public static BinaryReader newInstance(ByteBuffer buffer, boolean bufferIsImmutable) {
        if (buffer.hasArray()) {
            return new SafeHeapReader(buffer, bufferIsImmutable);
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }

    private BinaryReader() {
    }

    public boolean shouldDiscardUnknownFields() {
        return false;
    }

    private static final class SafeHeapReader extends BinaryReader {
        private final byte[] buffer;
        private final boolean bufferIsImmutable;
        private int endGroupTag;
        private final int initialPos;
        private int limit;
        private int pos;
        private int tag;

        public SafeHeapReader(ByteBuffer bytebuf, boolean bufferIsImmutable2) {
            super();
            this.bufferIsImmutable = bufferIsImmutable2;
            this.buffer = bytebuf.array();
            int arrayOffset = bytebuf.arrayOffset() + bytebuf.position();
            this.pos = arrayOffset;
            this.initialPos = arrayOffset;
            this.limit = bytebuf.arrayOffset() + bytebuf.limit();
        }

        private boolean isAtEnd() {
            return this.pos == this.limit;
        }

        public int getTotalBytesRead() {
            return this.pos - this.initialPos;
        }

        public int getFieldNumber() throws IOException {
            if (isAtEnd()) {
                return Integer.MAX_VALUE;
            }
            this.tag = readVarint32();
            int i = this.tag;
            if (i == this.endGroupTag) {
                return Integer.MAX_VALUE;
            }
            return WireFormat.getTagFieldNumber(i);
        }

        public int getTag() {
            return this.tag;
        }

        public boolean skipField() throws IOException {
            int i;
            if (isAtEnd() || (i = this.tag) == this.endGroupTag) {
                return false;
            }
            int tagWireType = WireFormat.getTagWireType(i);
            if (tagWireType == 0) {
                skipVarint();
                return true;
            } else if (tagWireType == 1) {
                skipBytes(8);
                return true;
            } else if (tagWireType == 2) {
                skipBytes(readVarint32());
                return true;
            } else if (tagWireType == 3) {
                skipGroup();
                return true;
            } else if (tagWireType == 5) {
                skipBytes(4);
                return true;
            } else {
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        public double readDouble() throws IOException {
            requireWireType(1);
            return Double.longBitsToDouble(readLittleEndian64());
        }

        public float readFloat() throws IOException {
            requireWireType(5);
            return Float.intBitsToFloat(readLittleEndian32());
        }

        public long readUInt64() throws IOException {
            requireWireType(0);
            return readVarint64();
        }

        public long readInt64() throws IOException {
            requireWireType(0);
            return readVarint64();
        }

        public int readInt32() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        public long readFixed64() throws IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        public int readFixed32() throws IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        public boolean readBool() throws IOException {
            requireWireType(0);
            if (readVarint32() != 0) {
                return true;
            }
            return false;
        }

        public String readString() throws IOException {
            return readStringInternal(false);
        }

        public String readStringRequireUtf8() throws IOException {
            return readStringInternal(true);
        }

        public String readStringInternal(boolean requireUtf8) throws IOException {
            requireWireType(2);
            int size = readVarint32();
            if (size == 0) {
                return "";
            }
            requireBytes(size);
            if (requireUtf8) {
                byte[] bArr = this.buffer;
                int i = this.pos;
                if (!Utf8.isValidUtf8(bArr, i, i + size)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
            }
            String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
            this.pos += size;
            return result;
        }

        public <T> T readMessage(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
            requireWireType(2);
            return readMessage(Protobuf.getInstance().schemaFor((Class) clazz), extensionRegistry);
        }

        public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            requireWireType(2);
            return readMessage(schema, extensionRegistry);
        }

        private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            int size = readVarint32();
            requireBytes(size);
            int prevLimit = this.limit;
            int newLimit = this.pos + size;
            this.limit = newLimit;
            try {
                T message = schema.newInstance();
                schema.mergeFrom(message, this, extensionRegistry);
                schema.makeImmutable(message);
                if (this.pos == newLimit) {
                    return message;
                }
                throw InvalidProtocolBufferException.parseFailure();
            } finally {
                this.limit = prevLimit;
            }
        }

        public <T> T readGroup(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
            requireWireType(3);
            return readGroup(Protobuf.getInstance().schemaFor((Class) clazz), extensionRegistry);
        }

        public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            requireWireType(3);
            return readGroup(schema, extensionRegistry);
        }

        private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            int prevEndGroupTag = this.endGroupTag;
            this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
            try {
                T message = schema.newInstance();
                schema.mergeFrom(message, this, extensionRegistry);
                schema.makeImmutable(message);
                if (this.tag == this.endGroupTag) {
                    return message;
                }
                throw InvalidProtocolBufferException.parseFailure();
            } finally {
                this.endGroupTag = prevEndGroupTag;
            }
        }

        public ByteString readBytes() throws IOException {
            ByteString bytes;
            requireWireType(2);
            int size = readVarint32();
            if (size == 0) {
                return ByteString.EMPTY;
            }
            requireBytes(size);
            if (this.bufferIsImmutable) {
                bytes = ByteString.wrap(this.buffer, this.pos, size);
            } else {
                bytes = ByteString.copyFrom(this.buffer, this.pos, size);
            }
            this.pos += size;
            return bytes;
        }

        public int readUInt32() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        public int readEnum() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        public int readSFixed32() throws IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        public long readSFixed64() throws IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        public int readSInt32() throws IOException {
            requireWireType(0);
            return CodedInputStream.decodeZigZag32(readVarint32());
        }

        public long readSInt64() throws IOException {
            requireWireType(0);
            return CodedInputStream.decodeZigZag64(readVarint64());
        }

        public void readDoubleList(List<Double> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof DoubleArrayList) {
                DoubleArrayList plist = (DoubleArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        plist.addDouble(readDouble());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed64Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addDouble(Double.longBitsToDouble(readLittleEndian64_NoCheck()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 1) {
                    do {
                        target.add(Double.valueOf(readDouble()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed64Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Double.valueOf(Double.longBitsToDouble(readLittleEndian64_NoCheck())));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readFloatList(List<Float> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof FloatArrayList) {
                FloatArrayList plist = (FloatArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed32Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addFloat(Float.intBitsToFloat(readLittleEndian32_NoCheck()));
                    }
                } else if (tagWireType == 5) {
                    do {
                        plist.addFloat(readFloat());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed32Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Float.valueOf(Float.intBitsToFloat(readLittleEndian32_NoCheck())));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        target.add(Float.valueOf(readFloat()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readUInt64List(List<Long> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof LongArrayList) {
                LongArrayList plist = (LongArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addLong(readUInt64());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addLong(readVarint64());
                    }
                    requirePosition(fieldEndPos);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Long.valueOf(readUInt64()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Long.valueOf(readVarint64()));
                    }
                    requirePosition(fieldEndPos2);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readInt64List(List<Long> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof LongArrayList) {
                LongArrayList plist = (LongArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addLong(readInt64());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addLong(readVarint64());
                    }
                    requirePosition(fieldEndPos);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Long.valueOf(readInt64()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Long.valueOf(readVarint64()));
                    }
                    requirePosition(fieldEndPos2);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readInt32List(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addInt(readInt32());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addInt(readVarint32());
                    }
                    requirePosition(fieldEndPos);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Integer.valueOf(readInt32()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(readVarint32()));
                    }
                    requirePosition(fieldEndPos2);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readFixed64List(List<Long> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof LongArrayList) {
                LongArrayList plist = (LongArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        plist.addLong(readFixed64());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed64Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addLong(readLittleEndian64_NoCheck());
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 1) {
                    do {
                        target.add(Long.valueOf(readFixed64()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed64Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Long.valueOf(readLittleEndian64_NoCheck()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readFixed32List(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed32Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addInt(readLittleEndian32_NoCheck());
                    }
                } else if (tagWireType == 5) {
                    do {
                        plist.addInt(readFixed32());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed32Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        target.add(Integer.valueOf(readFixed32()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readBoolList(List<Boolean> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof BooleanArrayList) {
                BooleanArrayList plist = (BooleanArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addBoolean(readBool());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addBoolean(readVarint32() != 0);
                    }
                    requirePosition(fieldEndPos);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Boolean.valueOf(readBool()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Boolean.valueOf(readVarint32() != 0));
                    }
                    requirePosition(fieldEndPos2);
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readStringList(List<String> target) throws IOException {
            readStringListInternal(target, false);
        }

        public void readStringListRequireUtf8(List<String> target) throws IOException {
            readStringListInternal(target, true);
        }

        public void readStringListInternal(List<String> target, boolean requireUtf8) throws IOException {
            int prevPos;
            int prevPos2;
            if (WireFormat.getTagWireType(this.tag) != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            } else if (!(target instanceof LazyStringList) || requireUtf8) {
                do {
                    target.add(readStringInternal(requireUtf8));
                    if (!isAtEnd()) {
                        prevPos = this.pos;
                    } else {
                        return;
                    }
                } while (readVarint32() == this.tag);
                this.pos = prevPos;
            } else {
                LazyStringList lazyList = (LazyStringList) target;
                do {
                    lazyList.add(readBytes());
                    if (!isAtEnd()) {
                        prevPos2 = this.pos;
                    } else {
                        return;
                    }
                } while (readVarint32() == this.tag);
                this.pos = prevPos2;
            }
        }

        public <T> void readMessageList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
            readMessageList(target, Protobuf.getInstance().schemaFor((Class) targetType), extensionRegistry);
        }

        public <T> void readMessageList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            int prevPos;
            if (WireFormat.getTagWireType(this.tag) == 2) {
                int listTag = this.tag;
                do {
                    target.add(readMessage(schema, extensionRegistry));
                    if (!isAtEnd()) {
                        prevPos = this.pos;
                    } else {
                        return;
                    }
                } while (readVarint32() == listTag);
                this.pos = prevPos;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }

        public <T> void readGroupList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
            readGroupList(target, Protobuf.getInstance().schemaFor((Class) targetType), extensionRegistry);
        }

        public <T> void readGroupList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
            int prevPos;
            if (WireFormat.getTagWireType(this.tag) == 3) {
                int listTag = this.tag;
                do {
                    target.add(readGroup(schema, extensionRegistry));
                    if (!isAtEnd()) {
                        prevPos = this.pos;
                    } else {
                        return;
                    }
                } while (readVarint32() == listTag);
                this.pos = prevPos;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }

        public void readBytesList(List<ByteString> target) throws IOException {
            int prevPos;
            if (WireFormat.getTagWireType(this.tag) == 2) {
                do {
                    target.add(readBytes());
                    if (!isAtEnd()) {
                        prevPos = this.pos;
                    } else {
                        return;
                    }
                } while (readVarint32() == this.tag);
                this.pos = prevPos;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }

        public void readUInt32List(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addInt(readUInt32());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addInt(readVarint32());
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Integer.valueOf(readUInt32()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(readVarint32()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readEnumList(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addInt(readEnum());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addInt(readVarint32());
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Integer.valueOf(readEnum()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(readVarint32()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readSFixed32List(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed32Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addInt(readLittleEndian32_NoCheck());
                    }
                } else if (tagWireType == 5) {
                    do {
                        plist.addInt(readSFixed32());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed32Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        target.add(Integer.valueOf(readSFixed32()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readSFixed64List(List<Long> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof LongArrayList) {
                LongArrayList plist = (LongArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        plist.addLong(readSFixed64());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int bytes = readVarint32();
                    verifyPackedFixed64Length(bytes);
                    int fieldEndPos = this.pos + bytes;
                    while (this.pos < fieldEndPos) {
                        plist.addLong(readLittleEndian64_NoCheck());
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 1) {
                    do {
                        target.add(Long.valueOf(readSFixed64()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int bytes2 = readVarint32();
                    verifyPackedFixed64Length(bytes2);
                    int fieldEndPos2 = this.pos + bytes2;
                    while (this.pos < fieldEndPos2) {
                        target.add(Long.valueOf(readLittleEndian64_NoCheck()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readSInt32List(List<Integer> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof IntArrayList) {
                IntArrayList plist = (IntArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addInt(readSInt32());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addInt(CodedInputStream.decodeZigZag32(readVarint32()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Integer.valueOf(readSInt32()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Integer.valueOf(CodedInputStream.decodeZigZag32(readVarint32())));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public void readSInt64List(List<Long> target) throws IOException {
            int prevPos;
            int prevPos2;
            if (target instanceof LongArrayList) {
                LongArrayList plist = (LongArrayList) target;
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        plist.addLong(readSInt64());
                        if (!isAtEnd()) {
                            prevPos2 = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos2;
                } else if (tagWireType == 2) {
                    int fieldEndPos = this.pos + readVarint32();
                    while (this.pos < fieldEndPos) {
                        plist.addLong(CodedInputStream.decodeZigZag64(readVarint64()));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.tag);
                if (tagWireType2 == 0) {
                    do {
                        target.add(Long.valueOf(readSInt64()));
                        if (!isAtEnd()) {
                            prevPos = this.pos;
                        } else {
                            return;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = prevPos;
                } else if (tagWireType2 == 2) {
                    int fieldEndPos2 = this.pos + readVarint32();
                    while (this.pos < fieldEndPos2) {
                        target.add(Long.valueOf(CodedInputStream.decodeZigZag64(readVarint64())));
                    }
                } else {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }

        public <K, V> void readMap(Map<K, V> target, MapEntryLite.Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistry) throws IOException {
            requireWireType(2);
            int size = readVarint32();
            requireBytes(size);
            int prevLimit = this.limit;
            this.limit = this.pos + size;
            try {
                K key = metadata.defaultKey;
                V value = metadata.defaultValue;
                while (true) {
                    int number = getFieldNumber();
                    if (number == Integer.MAX_VALUE) {
                        target.put(key, value);
                        this.limit = prevLimit;
                        return;
                    } else if (number == 1) {
                        key = readField(metadata.keyType, null, null);
                    } else if (number == 2) {
                        value = readField(metadata.valueType, metadata.defaultValue.getClass(), extensionRegistry);
                    } else if (!skipField()) {
                        throw new InvalidProtocolBufferException("Unable to parse map entry.");
                    }
                }
            } catch (InvalidProtocolBufferException.InvalidWireTypeException e) {
                if (!skipField()) {
                    throw new InvalidProtocolBufferException("Unable to parse map entry.");
                }
            } catch (Throwable th) {
                this.limit = prevLimit;
                throw th;
            }
        }

        private Object readField(WireFormat.FieldType fieldType, Class<?> messageType, ExtensionRegistryLite extensionRegistry) throws IOException {
            switch (fieldType) {
                case BOOL:
                    return Boolean.valueOf(readBool());
                case BYTES:
                    return readBytes();
                case DOUBLE:
                    return Double.valueOf(readDouble());
                case ENUM:
                    return Integer.valueOf(readEnum());
                case FIXED32:
                    return Integer.valueOf(readFixed32());
                case FIXED64:
                    return Long.valueOf(readFixed64());
                case FLOAT:
                    return Float.valueOf(readFloat());
                case INT32:
                    return Integer.valueOf(readInt32());
                case INT64:
                    return Long.valueOf(readInt64());
                case MESSAGE:
                    return readMessage(messageType, extensionRegistry);
                case SFIXED32:
                    return Integer.valueOf(readSFixed32());
                case SFIXED64:
                    return Long.valueOf(readSFixed64());
                case SINT32:
                    return Integer.valueOf(readSInt32());
                case SINT64:
                    return Long.valueOf(readSInt64());
                case STRING:
                    return readStringRequireUtf8();
                case UINT32:
                    return Integer.valueOf(readUInt32());
                case UINT64:
                    return Long.valueOf(readUInt64());
                default:
                    throw new RuntimeException("unsupported field type.");
            }
        }

        /* JADX INFO: Multiple debug info for r1v7 'i'  int: [D('x' int), D('i' int)] */
        /* JADX INFO: Multiple debug info for r0v7 int: [D('x' int), D('i' int)] */
        /* JADX INFO: Multiple debug info for r1v11 'i'  int: [D('x' int), D('i' int)] */
        /* JADX INFO: Multiple debug info for r0v8 byte: [D('i' int), D('y' int)] */
        private int readVarint32() throws IOException {
            int x;
            int i;
            int i2 = this.pos;
            int i3 = this.limit;
            if (i3 != this.pos) {
                byte[] bArr = this.buffer;
                int i4 = i2 + 1;
                byte b = bArr[i2];
                int x2 = b;
                if (b >= 0) {
                    this.pos = i4;
                    return x2;
                } else if (i3 - i4 < 9) {
                    return (int) readVarint64SlowPath();
                } else {
                    int i5 = i4 + 1;
                    int i6 = (bArr[i4] << 7) ^ x2;
                    int x3 = i6;
                    if (i6 < 0) {
                        x = x3 ^ -128;
                        i = i5;
                    } else {
                        i = i5 + 1;
                        int i7 = (bArr[i5] << Ascii.f157SO) ^ x3;
                        int x4 = i7;
                        if (i7 >= 0) {
                            x = x4 ^ 16256;
                        } else {
                            int i8 = i + 1;
                            int i9 = (bArr[i] << Ascii.NAK) ^ x4;
                            int x5 = i9;
                            if (i9 < 0) {
                                x = -2080896 ^ x5;
                                i = i8;
                            } else {
                                i = i8 + 1;
                                byte b2 = bArr[i8];
                                x = (x5 ^ (b2 << Ascii.f150FS)) ^ 266354560;
                                if (b2 < 0) {
                                    int i10 = i + 1;
                                    if (bArr[i] < 0) {
                                        i = i10 + 1;
                                        if (bArr[i10] < 0) {
                                            i10 = i + 1;
                                            if (bArr[i] < 0) {
                                                i = i10 + 1;
                                                if (bArr[i10] < 0) {
                                                    i10 = i + 1;
                                                    if (bArr[i] < 0) {
                                                        throw InvalidProtocolBufferException.malformedVarint();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    i = i10;
                                }
                            }
                        }
                    }
                    this.pos = i;
                    return x;
                }
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public long readVarint64() throws IOException {
            long x;
            int i = this.pos;
            int i2 = this.limit;
            if (i2 != i) {
                byte[] buffer2 = this.buffer;
                int i3 = i + 1;
                byte b = buffer2[i];
                int y = b;
                if (b >= 0) {
                    this.pos = i3;
                    return (long) y;
                } else if (i2 - i3 < 9) {
                    return readVarint64SlowPath();
                } else {
                    int i4 = i3 + 1;
                    int i5 = (buffer2[i3] << 7) ^ y;
                    int y2 = i5;
                    if (i5 < 0) {
                        x = (long) (y2 ^ -128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = (buffer2[i4] << Ascii.f157SO) ^ y2;
                        int y3 = i7;
                        if (i7 >= 0) {
                            x = (long) (y3 ^ 16256);
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = (buffer2[i6] << Ascii.NAK) ^ y3;
                            int y4 = i8;
                            if (i8 < 0) {
                                x = (long) (-2080896 ^ y4);
                            } else {
                                int i9 = i4 + 1;
                                long j = ((long) y4) ^ (((long) buffer2[i4]) << 28);
                                long x2 = j;
                                if (j >= 0) {
                                    x = 266354560 ^ x2;
                                    i4 = i9;
                                } else {
                                    i4 = i9 + 1;
                                    long j2 = (((long) buffer2[i9]) << 35) ^ x2;
                                    long x3 = j2;
                                    if (j2 < 0) {
                                        x = -34093383808L ^ x3;
                                    } else {
                                        int i10 = i4 + 1;
                                        long j3 = (((long) buffer2[i4]) << 42) ^ x3;
                                        long x4 = j3;
                                        if (j3 >= 0) {
                                            x = 4363953127296L ^ x4;
                                            i4 = i10;
                                        } else {
                                            i4 = i10 + 1;
                                            long j4 = (((long) buffer2[i10]) << 49) ^ x4;
                                            long x5 = j4;
                                            if (j4 < 0) {
                                                x = -558586000294016L ^ x5;
                                            } else {
                                                int i11 = i4 + 1;
                                                x = ((((long) buffer2[i4]) << 56) ^ x5) ^ 71499008037633920L;
                                                if (x < 0) {
                                                    i4 = i11 + 1;
                                                    if (((long) buffer2[i11]) < 0) {
                                                        throw InvalidProtocolBufferException.malformedVarint();
                                                    }
                                                } else {
                                                    i4 = i11;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.pos = i4;
                    return x;
                }
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private long readVarint64SlowPath() throws IOException {
            long result = 0;
            for (int shift = 0; shift < 64; shift += 7) {
                byte b = readByte();
                result |= ((long) (b & Ascii.DEL)) << shift;
                if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private byte readByte() throws IOException {
            int i = this.pos;
            if (i != this.limit) {
                byte[] bArr = this.buffer;
                this.pos = i + 1;
                return bArr[i];
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        private int readLittleEndian32() throws IOException {
            requireBytes(4);
            return readLittleEndian32_NoCheck();
        }

        private long readLittleEndian64() throws IOException {
            requireBytes(8);
            return readLittleEndian64_NoCheck();
        }

        private int readLittleEndian32_NoCheck() {
            int p = this.pos;
            byte[] buffer2 = this.buffer;
            this.pos = p + 4;
            return (buffer2[p] & UnsignedBytes.MAX_VALUE) | ((buffer2[p + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((buffer2[p + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((buffer2[p + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        }

        private long readLittleEndian64_NoCheck() {
            int p = this.pos;
            byte[] buffer2 = this.buffer;
            this.pos = p + 8;
            return (((long) buffer2[p]) & 255) | ((((long) buffer2[p + 1]) & 255) << 8) | ((((long) buffer2[p + 2]) & 255) << 16) | ((((long) buffer2[p + 3]) & 255) << 24) | ((((long) buffer2[p + 4]) & 255) << 32) | ((((long) buffer2[p + 5]) & 255) << 40) | ((((long) buffer2[p + 6]) & 255) << 48) | ((255 & ((long) buffer2[p + 7])) << 56);
        }

        private void skipVarint() throws IOException {
            if (this.limit - this.pos >= 10) {
                byte[] buffer2 = this.buffer;
                int p = this.pos;
                int i = 0;
                while (i < 10) {
                    int p2 = p + 1;
                    if (buffer2[p] >= 0) {
                        this.pos = p2;
                        return;
                    } else {
                        i++;
                        p = p2;
                    }
                }
            }
            skipVarintSlowPath();
        }

        private void skipVarintSlowPath() throws IOException {
            int i = 0;
            while (i < 10) {
                if (readByte() < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipBytes(int size) throws IOException {
            requireBytes(size);
            this.pos += size;
        }

        /* JADX WARNING: Removed duplicated region for block: B:3:0x0018  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void skipGroup() throws java.io.IOException {
            /*
                r3 = this;
                int r0 = r3.endGroupTag
                int r1 = r3.tag
                int r1 = com.google.protobuf.WireFormat.getTagFieldNumber(r1)
                r2 = 4
                int r1 = com.google.protobuf.WireFormat.makeTag(r1, r2)
                r3.endGroupTag = r1
            L_0x000f:
                int r1 = r3.getFieldNumber()
                r2 = 2147483647(0x7fffffff, float:NaN)
                if (r1 == r2) goto L_0x001e
                boolean r1 = r3.skipField()
                if (r1 != 0) goto L_0x000f
            L_0x001e:
                int r1 = r3.tag
                int r2 = r3.endGroupTag
                if (r1 != r2) goto L_0x0027
                r3.endGroupTag = r0
                return
            L_0x0027:
                com.google.protobuf.InvalidProtocolBufferException r1 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
                throw r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.BinaryReader.SafeHeapReader.skipGroup():void");
        }

        private void requireBytes(int size) throws IOException {
            if (size < 0 || size > this.limit - this.pos) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void requireWireType(int requiredWireType) throws IOException {
            if (WireFormat.getTagWireType(this.tag) != requiredWireType) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        private void verifyPackedFixed64Length(int bytes) throws IOException {
            requireBytes(bytes);
            if ((bytes & 7) != 0) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        private void verifyPackedFixed32Length(int bytes) throws IOException {
            requireBytes(bytes);
            if ((bytes & 3) != 0) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        private void requirePosition(int expectedPosition) throws IOException {
            if (this.pos != expectedPosition) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
    }
}
