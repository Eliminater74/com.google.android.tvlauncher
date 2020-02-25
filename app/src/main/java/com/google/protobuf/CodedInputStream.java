package com.google.protobuf;

import androidx.tvprovider.media.p005tv.TvContractCompat;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class CodedInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;
    int recursionDepth;
    int recursionLimit;
    int sizeLimit;
    CodedInputStreamReader wrapper;
    private boolean shouldDiscardUnknownFields;

    private CodedInputStream() {
        this.recursionLimit = 100;
        this.sizeLimit = Integer.MAX_VALUE;
        this.shouldDiscardUnknownFields = false;
    }

    public static CodedInputStream newInstance(InputStream input) {
        return newInstance(input, 4096);
    }

    public static CodedInputStream newInstance(InputStream input, int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be > 0");
        } else if (input == null) {
            return newInstance(Internal.EMPTY_BYTE_ARRAY);
        } else {
            return new StreamDecoder(input, bufferSize);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.CodedInputStream.newInstance(java.lang.Iterable<java.nio.ByteBuffer>, boolean):com.google.protobuf.CodedInputStream
     arg types: [java.lang.Iterable<java.nio.ByteBuffer>, int]
     candidates:
      com.google.protobuf.CodedInputStream.newInstance(java.io.InputStream, int):com.google.protobuf.CodedInputStream
      com.google.protobuf.CodedInputStream.newInstance(java.nio.ByteBuffer, boolean):com.google.protobuf.CodedInputStream
      com.google.protobuf.CodedInputStream.newInstance(java.lang.Iterable<java.nio.ByteBuffer>, boolean):com.google.protobuf.CodedInputStream */
    public static CodedInputStream newInstance(Iterable<ByteBuffer> input) {
        if (!UnsafeDirectNioDecoder.isSupported()) {
            return newInstance(new IterableByteBufferInputStream(input));
        }
        return newInstance(input, false);
    }

    static CodedInputStream newInstance(Iterable<ByteBuffer> bufs, boolean bufferIsImmutable) {
        int flag = 0;
        int totalSize = 0;
        for (ByteBuffer buf : bufs) {
            totalSize += buf.remaining();
            if (buf.hasArray()) {
                flag |= 1;
            } else if (buf.isDirect()) {
                flag |= 2;
            } else {
                flag |= 4;
            }
        }
        if (flag == 2) {
            return new IterableDirectByteBufferDecoder(bufs, totalSize, bufferIsImmutable);
        }
        return newInstance(new IterableByteBufferInputStream(bufs));
    }

    public static CodedInputStream newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputStream newInstance(byte[] buf, int off, int len) {
        return newInstance(buf, off, len, false);
    }

    static CodedInputStream newInstance(byte[] buf, int off, int len, boolean bufferIsImmutable) {
        ArrayDecoder result = new ArrayDecoder(buf, off, len, bufferIsImmutable);
        try {
            result.pushLimit(len);
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.CodedInputStream.newInstance(java.nio.ByteBuffer, boolean):com.google.protobuf.CodedInputStream
     arg types: [java.nio.ByteBuffer, int]
     candidates:
      com.google.protobuf.CodedInputStream.newInstance(java.io.InputStream, int):com.google.protobuf.CodedInputStream
      com.google.protobuf.CodedInputStream.newInstance(java.lang.Iterable<java.nio.ByteBuffer>, boolean):com.google.protobuf.CodedInputStream
      com.google.protobuf.CodedInputStream.newInstance(java.nio.ByteBuffer, boolean):com.google.protobuf.CodedInputStream */
    public static CodedInputStream newInstance(ByteBuffer buf) {
        return newInstance(buf, false);
    }

    static CodedInputStream newInstance(ByteBuffer buf, boolean bufferIsImmutable) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), bufferIsImmutable);
        }
        if (buf.isDirect() && UnsafeDirectNioDecoder.isSupported()) {
            return new UnsafeDirectNioDecoder(buf, bufferIsImmutable);
        }
        byte[] buffer = new byte[buf.remaining()];
        buf.duplicate().get(buffer);
        return newInstance(buffer, 0, buffer.length, true);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        int offset = 7;
        while (offset < 32) {
            int b = input.read();
            if (b != -1) {
                result |= (b & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) << offset;
                if ((b & 128) == 0) {
                    return result;
                }
                offset += 7;
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        while (offset < 64) {
            int b2 = input.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if ((b2 & 128) == 0) {
                return result;
            } else {
                offset += 7;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream input) throws IOException {
        int firstByte = input.read();
        if (firstByte != -1) {
            return readRawVarint32(firstByte, input);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    public abstract void checkLastTagWas(int i) throws InvalidProtocolBufferException;

    public abstract void enableAliasing(boolean z);

    public abstract int getBytesUntilLimit();

    public abstract int getLastTag();

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd() throws IOException;

    public abstract void mergeToMessage(MutableMessageLite mutableMessageLite) throws IOException;

    public abstract void popLimit(int i);

    public abstract int pushLimit(int i) throws InvalidProtocolBufferException;

    public abstract boolean readBool() throws IOException;

    public abstract byte[] readByteArray() throws IOException;

    public abstract ByteBuffer readByteBuffer() throws IOException;

    public abstract ByteString readBytes() throws IOException;

    public abstract double readDouble() throws IOException;

    public abstract int readEnum() throws IOException;

    public abstract int readFixed32() throws IOException;

    public abstract long readFixed64() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract <T extends GeneratedMessageLite<T, ?>> T readGroup(int i, T t, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readGroup(int i, MutableMessageLite mutableMessageLite, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract int readInt32() throws IOException;

    public abstract long readInt64() throws IOException;

    public abstract <T extends GeneratedMessageLite<T, ?>> T readMessage(T t, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readMessage(MutableMessageLite mutableMessageLite, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract byte readRawByte() throws IOException;

    public abstract byte[] readRawBytes(int i) throws IOException;

    public abstract int readRawLittleEndian32() throws IOException;

    public abstract long readRawLittleEndian64() throws IOException;

    public abstract int readRawVarint32() throws IOException;

    public abstract long readRawVarint64() throws IOException;

    /* access modifiers changed from: package-private */
    public abstract long readRawVarint64SlowPath() throws IOException;

    public abstract int readSFixed32() throws IOException;

    public abstract long readSFixed64() throws IOException;

    public abstract int readSInt32() throws IOException;

    public abstract long readSInt64() throws IOException;

    public abstract String readString() throws IOException;

    public abstract String readStringRequireUtf8() throws IOException;

    public abstract int readTag() throws IOException;

    public abstract int readUInt32() throws IOException;

    public abstract long readUInt64() throws IOException;

    @Deprecated
    public abstract void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException;

    public abstract void resetSizeCounter();

    public abstract boolean skipField(int i) throws IOException;

    @Deprecated
    public abstract boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipMessage() throws IOException;

    public abstract void skipMessage(CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipRawBytes(int i) throws IOException;

    public final int setRecursionLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.recursionLimit;
            this.recursionLimit = limit;
            return oldLimit;
        }
        StringBuilder sb = new StringBuilder(47);
        sb.append("Recursion limit cannot be negative: ");
        sb.append(limit);
        throw new IllegalArgumentException(sb.toString());
    }

    public final int setSizeLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.sizeLimit;
            this.sizeLimit = limit;
            return oldLimit;
        }
        StringBuilder sb = new StringBuilder(42);
        sb.append("Size limit cannot be negative: ");
        sb.append(limit);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: package-private */
    public final void discardUnknownFields() {
        this.shouldDiscardUnknownFields = true;
    }

    /* access modifiers changed from: package-private */
    public final void unsetDiscardUnknownFields() {
        this.shouldDiscardUnknownFields = false;
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldDiscardUnknownFields() {
        return this.shouldDiscardUnknownFields;
    }

    private static final class ArrayDecoder extends CodedInputStream {
        private final byte[] buffer;
        private final boolean immutable;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private int lastTag;
        private int limit;
        private int pos;
        private int startPos;

        private ArrayDecoder(byte[] buffer2, int offset, int len, boolean immutable2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = buffer2;
            this.limit = offset + len;
            this.pos = offset;
            this.startPos = this.pos;
            this.immutable = immutable2;
        }

        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        public int getLastTag() {
            return this.lastTag;
        }

        public boolean skipField(int tag) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            } else if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            } else if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            } else if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    skipRawBytes(4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        /* JADX INFO: Multiple debug info for r0v1 com.google.protobuf.ByteString: [D('endtag' int), D('value' com.google.protobuf.ByteString)] */
        public boolean skipField(int tag, CodedOutputStream output) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            } else if (tagWireType == 1) {
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            } else if (tagWireType == 2) {
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            } else if (tagWireType == 3) {
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    int value4 = readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        public void skipMessage(CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, output));
        }

        public void mergeToMessage(MutableMessageLite message) throws IOException {
            int lastPos = this.pos;
            skipMessage();
            ByteBuffer data = ByteBuffer.wrap(this.buffer, lastPos, this.pos - lastPos);
            if (!message.mergeFrom(data.array(), data.position(), data.remaining())) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        public String readString() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (size <= i - i2) {
                    String result = new String(this.buffer, i2, size, Internal.UTF_8);
                    this.pos += size;
                    return result;
                }
            }
            if (size == 0) {
                return "";
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public String readStringRequireUtf8() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (size <= i - i2) {
                    String result = Utf8.decodeUtf8(this.buffer, i2, size);
                    this.pos += size;
                    return result;
                }
            }
            if (size == 0) {
                return "";
            }
            if (size <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readGroup(int fieldNumber, MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readGroup(int fieldNumber, T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Deprecated
        public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
            readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readMessage(MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readMessage(T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public ByteString readBytes() throws IOException {
            ByteString result;
            int size = readRawVarint32();
            if (size > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (size <= i - i2) {
                    if (!this.immutable || !this.enableAliasing) {
                        result = ByteString.copyFrom(this.buffer, this.pos, size);
                    } else {
                        result = ByteString.wrap(this.buffer, i2, size);
                    }
                    this.pos += size;
                    return result;
                }
            }
            if (size == 0) {
                return ByteString.EMPTY;
            }
            return ByteString.wrap(readRawBytes(size));
        }

        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        public ByteBuffer readByteBuffer() throws IOException {
            ByteBuffer result;
            int size = readRawVarint32();
            if (size > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (size <= i - i2) {
                    if (this.immutable || !this.enableAliasing) {
                        byte[] bArr = this.buffer;
                        int i3 = this.pos;
                        result = ByteBuffer.wrap(Arrays.copyOfRange(bArr, i3, i3 + size));
                    } else {
                        result = ByteBuffer.wrap(this.buffer, i2, size).slice();
                    }
                    this.pos += size;
                    return result;
                }
            }
            if (size == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX INFO: Multiple debug info for r1v7 'tempPos'  int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r0v8 int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r1v11 'tempPos'  int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r0v9 byte: [D('tempPos' int), D('y' int)] */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0071, code lost:
            if (r2[r1] < 0) goto L_0x0074;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.pos
                int r1 = r5.limit
                if (r1 != r0) goto L_0x0008
                goto L_0x0074
            L_0x0008:
                byte[] r2 = r5.buffer
                int r3 = r0 + 1
                byte r0 = r2[r0]
                r4 = r0
                if (r0 < 0) goto L_0x0014
                r5.pos = r3
                return r4
            L_0x0014:
                int r1 = r1 - r3
                r0 = 9
                if (r1 >= r0) goto L_0x001a
                goto L_0x0074
            L_0x001a:
                int r0 = r3 + 1
                byte r1 = r2[r3]
                int r1 = r1 << 7
                r1 = r1 ^ r4
                r3 = r1
                if (r1 >= 0) goto L_0x0029
                r1 = r3 ^ -128(0xffffffffffffff80, float:NaN)
                r3 = r1
                r1 = r0
                goto L_0x007b
            L_0x0029:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r0 = r0 << 14
                r0 = r0 ^ r3
                r3 = r0
                if (r0 < 0) goto L_0x0037
                r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
                r3 = r0
                goto L_0x007b
            L_0x0037:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 21
                r1 = r1 ^ r3
                r3 = r1
                if (r1 >= 0) goto L_0x0048
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r1 = r1 ^ r3
                r3 = r1
                r1 = r0
                goto L_0x007b
            L_0x0048:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r4 = r0 << 28
                r3 = r3 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r3 = r3 ^ r4
                if (r0 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
                int r1 = r4 + 1
                byte r4 = r2[r4]
                if (r4 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
                int r1 = r4 + 1
                byte r4 = r2[r4]
                if (r4 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
            L_0x0074:
                long r0 = r5.readRawVarint64SlowPath()
                int r1 = (int) r0
                return r1
            L_0x007a:
                r1 = r4
            L_0x007b:
                r5.pos = r1
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.ArrayDecoder.readRawVarint32():int");
        }

        private void skipRawVarint() throws IOException {
            if (this.limit - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            int i = 0;
            while (i < 10) {
                byte[] bArr = this.buffer;
                int i2 = this.pos;
                this.pos = i2 + 1;
                if (bArr[i2] < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            int i = 0;
            while (i < 10) {
                if (readRawByte() < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00bd, code lost:
            if (((long) r2[r1]) < 0) goto L_0x00c0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long readRawVarint64() throws java.io.IOException {
            /*
                r10 = this;
                int r0 = r10.pos
                int r1 = r10.limit
                if (r1 != r0) goto L_0x0008
                goto L_0x00c0
            L_0x0008:
                byte[] r2 = r10.buffer
                int r3 = r0 + 1
                byte r0 = r2[r0]
                r4 = r0
                if (r0 < 0) goto L_0x0015
                r10.pos = r3
                long r0 = (long) r4
                return r0
            L_0x0015:
                int r1 = r1 - r3
                r0 = 9
                if (r1 >= r0) goto L_0x001c
                goto L_0x00c0
            L_0x001c:
                int r0 = r3 + 1
                byte r1 = r2[r3]
                int r1 = r1 << 7
                r1 = r1 ^ r4
                r3 = r1
                if (r1 >= 0) goto L_0x002b
                r1 = r3 ^ -128(0xffffffffffffff80, float:NaN)
                long r4 = (long) r1
                goto L_0x00c6
            L_0x002b:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r0 = r0 << 14
                r0 = r0 ^ r3
                r3 = r0
                if (r0 < 0) goto L_0x003b
                r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
                long r4 = (long) r0
                r0 = r1
                goto L_0x00c6
            L_0x003b:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 21
                r1 = r1 ^ r3
                r3 = r1
                if (r1 >= 0) goto L_0x004c
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r1 = r1 ^ r3
                long r4 = (long) r1
                goto L_0x00c6
            L_0x004c:
                long r4 = (long) r3
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r6 = (long) r0
                r0 = 28
                long r6 = r6 << r0
                long r4 = r4 ^ r6
                r6 = r4
                r8 = 0
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 < 0) goto L_0x0063
                r4 = 266354560(0xfe03f80, double:1.315966377E-315)
                long r4 = r4 ^ r6
                r0 = r1
                goto L_0x00c6
            L_0x0063:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r4 = (long) r1
                r1 = 35
                long r4 = r4 << r1
                long r4 = r4 ^ r6
                r6 = r4
                int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x0078
                r4 = -34093383808(0xfffffff80fe03f80, double:NaN)
                long r4 = r4 ^ r6
                goto L_0x00c6
            L_0x0078:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r4 = (long) r0
                r0 = 42
                long r4 = r4 << r0
                long r4 = r4 ^ r6
                r6 = r4
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 < 0) goto L_0x008e
                r4 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
                long r4 = r4 ^ r6
                r0 = r1
                goto L_0x00c6
            L_0x008e:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r4 = (long) r1
                r1 = 49
                long r4 = r4 << r1
                long r4 = r4 ^ r6
                r6 = r4
                int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x00a3
                r4 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
                long r4 = r4 ^ r6
                goto L_0x00c6
            L_0x00a3:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r4 = (long) r0
                r0 = 56
                long r4 = r4 << r0
                long r4 = r4 ^ r6
                r6 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
                long r4 = r4 ^ r6
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 >= 0) goto L_0x00c5
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r6 = (long) r1
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x00c6
            L_0x00c0:
                long r0 = r10.readRawVarint64SlowPath()
                return r0
            L_0x00c5:
                r0 = r1
            L_0x00c6:
                r10.pos = r0
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.ArrayDecoder.readRawVarint64():long");
        }

        /* access modifiers changed from: package-private */
        public long readRawVarint64SlowPath() throws IOException {
            long result = 0;
            for (int shift = 0; shift < 64; shift += 7) {
                byte b = readRawByte();
                result |= ((long) (b & Ascii.DEL)) << shift;
                if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        public int readRawLittleEndian32() throws IOException {
            int tempPos = this.pos;
            if (this.limit - tempPos >= 4) {
                byte[] buffer2 = this.buffer;
                this.pos = tempPos + 4;
                return (buffer2[tempPos] & UnsignedBytes.MAX_VALUE) | ((buffer2[tempPos + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((buffer2[tempPos + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((buffer2[tempPos + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public long readRawLittleEndian64() throws IOException {
            int tempPos = this.pos;
            if (this.limit - tempPos >= 8) {
                byte[] buffer2 = this.buffer;
                this.pos = tempPos + 8;
                return (((long) buffer2[tempPos]) & 255) | ((((long) buffer2[tempPos + 1]) & 255) << 8) | ((((long) buffer2[tempPos + 2]) & 255) << 16) | ((((long) buffer2[tempPos + 3]) & 255) << 24) | ((((long) buffer2[tempPos + 4]) & 255) << 32) | ((((long) buffer2[tempPos + 5]) & 255) << 40) | ((((long) buffer2[tempPos + 6]) & 255) << 48) | ((255 & ((long) buffer2[tempPos + 7])) << 56);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public void enableAliasing(boolean enabled) {
            this.enableAliasing = enabled;
        }

        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit >= 0) {
                int byteLimit2 = byteLimit + getTotalBytesRead();
                int oldLimit = this.currentLimit;
                if (byteLimit2 <= oldLimit) {
                    this.currentLimit = byteLimit2;
                    recomputeBufferSizeAfterLimit();
                    return oldLimit;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private void recomputeBufferSizeAfterLimit() {
            this.limit += this.bufferSizeAfterLimit;
            int i = this.limit;
            int bufferEnd = i - this.startPos;
            int i2 = this.currentLimit;
            if (bufferEnd > i2) {
                this.bufferSizeAfterLimit = bufferEnd - i2;
                this.limit = i - this.bufferSizeAfterLimit;
                return;
            }
            this.bufferSizeAfterLimit = 0;
        }

        public void popLimit(int oldLimit) {
            this.currentLimit = oldLimit;
            recomputeBufferSizeAfterLimit();
        }

        public int getBytesUntilLimit() {
            int i = this.currentLimit;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        public int getTotalBytesRead() {
            return this.pos - this.startPos;
        }

        public byte readRawByte() throws IOException {
            int i = this.pos;
            if (i != this.limit) {
                byte[] bArr = this.buffer;
                this.pos = i + 1;
                return bArr[i];
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public byte[] readRawBytes(int length) throws IOException {
            if (length > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (length <= i - i2) {
                    int tempPos = this.pos;
                    this.pos = i2 + length;
                    return Arrays.copyOfRange(this.buffer, tempPos, this.pos);
                }
            }
            if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        public void skipRawBytes(int length) throws IOException {
            if (length >= 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (length <= i - i2) {
                    this.pos = i2 + length;
                    return;
                }
            }
            if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private static final class UnsafeDirectNioDecoder extends CodedInputStream {
        private final long address;
        private final ByteBuffer buffer;
        private final boolean immutable;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private int lastTag;
        private long limit;
        private long pos;
        private long startPos;

        private UnsafeDirectNioDecoder(ByteBuffer buffer2, boolean immutable2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = buffer2;
            this.address = UnsafeUtil.addressOffset(buffer2);
            this.limit = this.address + ((long) buffer2.limit());
            this.pos = this.address + ((long) buffer2.position());
            this.startPos = this.pos;
            this.immutable = immutable2;
        }

        static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        public int getLastTag() {
            return this.lastTag;
        }

        public boolean skipField(int tag) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            } else if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            } else if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            } else if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    skipRawBytes(4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        /* JADX INFO: Multiple debug info for r0v1 com.google.protobuf.ByteString: [D('endtag' int), D('value' com.google.protobuf.ByteString)] */
        public boolean skipField(int tag, CodedOutputStream output) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            } else if (tagWireType == 1) {
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            } else if (tagWireType == 2) {
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            } else if (tagWireType == 3) {
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    int value4 = readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        public void skipMessage(CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, output));
        }

        public void mergeToMessage(MutableMessageLite message) throws IOException {
            long lastPos = this.pos;
            skipMessage();
            if (!message.mergeFrom(slice(lastPos, this.pos))) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        public String readString() throws IOException {
            int size = readRawVarint32();
            if (size > 0 && size <= remaining()) {
                byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0, (long) size);
                String result = new String(bytes, Internal.UTF_8);
                this.pos += (long) size;
                return result;
            } else if (size == 0) {
                return "";
            } else {
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public String readStringRequireUtf8() throws IOException {
            int size = readRawVarint32();
            if (size > 0 && size <= remaining()) {
                String result = Utf8.decodeUtf8(this.buffer, bufferPos(this.pos), size);
                this.pos += (long) size;
                return result;
            } else if (size == 0) {
                return "";
            } else {
                if (size <= 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readGroup(int fieldNumber, MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readGroup(int fieldNumber, T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Deprecated
        public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
            readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readMessage(MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readMessage(T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        /* JADX INFO: Multiple debug info for r1v5 byte[]: [D('result' java.nio.ByteBuffer), D('bytes' byte[])] */
        public ByteString readBytes() throws IOException {
            int size = readRawVarint32();
            if (size <= 0 || size > remaining()) {
                if (size == 0) {
                    return ByteString.EMPTY;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (!this.immutable || !this.enableAliasing) {
                byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0, (long) size);
                this.pos += (long) size;
                return ByteString.wrap(bytes);
            } else {
                long j = this.pos;
                ByteBuffer result = slice(j, ((long) size) + j);
                this.pos += (long) size;
                return ByteString.wrap(result);
            }
        }

        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        /* JADX INFO: Multiple debug info for r1v5 byte[]: [D('result' java.nio.ByteBuffer), D('bytes' byte[])] */
        public ByteBuffer readByteBuffer() throws IOException {
            int size = readRawVarint32();
            if (size <= 0 || size > remaining()) {
                if (size == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (this.immutable || !this.enableAliasing) {
                byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0, (long) size);
                this.pos += (long) size;
                return ByteBuffer.wrap(bytes);
            } else {
                long j = this.pos;
                ByteBuffer result = slice(j, ((long) size) + j);
                this.pos += (long) size;
                return result;
            }
        }

        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:28:0x008c, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r4) < 0) goto L_0x008f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.pos
                long r2 = r10.limit
                int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r4 != 0) goto L_0x000a
                goto L_0x008f
            L_0x000a:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r0)
                r1 = r0
                if (r0 < 0) goto L_0x0018
                r10.pos = r4
                return r1
            L_0x0018:
                long r6 = r10.limit
                long r6 = r6 - r4
                r8 = 9
                int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r0 >= 0) goto L_0x0023
                goto L_0x008f
            L_0x0023:
                long r6 = r4 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r4)
                int r0 = r0 << 7
                r0 = r0 ^ r1
                r1 = r0
                if (r0 >= 0) goto L_0x0034
                r0 = r1 ^ -128(0xffffffffffffff80, float:NaN)
                r1 = r0
                goto L_0x009a
            L_0x0034:
                long r4 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r6)
                int r0 = r0 << 14
                r0 = r0 ^ r1
                r1 = r0
                if (r0 < 0) goto L_0x0045
                r0 = r1 ^ 16256(0x3f80, float:2.278E-41)
                r1 = r0
                r6 = r4
                goto L_0x009a
            L_0x0045:
                long r6 = r4 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r4)
                int r0 = r0 << 21
                r0 = r0 ^ r1
                r1 = r0
                if (r0 >= 0) goto L_0x0057
                r0 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                r1 = r0
                goto L_0x009a
            L_0x0057:
                long r4 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r6)
                int r6 = r0 << 28
                r1 = r1 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r1 = r1 ^ r6
                if (r0 >= 0) goto L_0x0099
                long r6 = r4 + r2
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r4 >= 0) goto L_0x009a
                long r4 = r6 + r2
                byte r6 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r6 >= 0) goto L_0x0097
                long r6 = r4 + r2
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r4 >= 0) goto L_0x009a
                long r4 = r6 + r2
                byte r6 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r6 >= 0) goto L_0x0095
                long r6 = r4 + r2
                byte r2 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r2 >= 0) goto L_0x009a
            L_0x008f:
                long r0 = r10.readRawVarint64SlowPath()
                int r1 = (int) r0
                return r1
            L_0x0095:
                r6 = r4
                goto L_0x009a
            L_0x0097:
                r6 = r4
                goto L_0x009a
            L_0x0099:
                r6 = r4
            L_0x009a:
                r10.pos = r6
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.UnsafeDirectNioDecoder.readRawVarint32():int");
        }

        private void skipRawVarint() throws IOException {
            if (remaining() >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            int i = 0;
            while (i < 10) {
                long j = this.pos;
                this.pos = 1 + j;
                if (UnsafeUtil.getByte(j) < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            int i = 0;
            while (i < 10) {
                if (readRawByte() < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        public long readRawVarint64() throws IOException {
            long x;
            long x2;
            long tempPos = this.pos;
            if (this.limit != tempPos) {
                long tempPos2 = tempPos + 1;
                int i = UnsafeUtil.getByte(tempPos);
                int y = i;
                if (i >= 0) {
                    this.pos = tempPos2;
                    return (long) y;
                } else if (this.limit - tempPos2 >= 9) {
                    long tempPos3 = tempPos2 + 1;
                    int i2 = (UnsafeUtil.getByte(tempPos2) << 7) ^ y;
                    int y2 = i2;
                    if (i2 < 0) {
                        x2 = (long) (y2 ^ -128);
                        x = tempPos3;
                    } else {
                        x = tempPos3 + 1;
                        int i3 = (UnsafeUtil.getByte(tempPos3) << Ascii.f157SO) ^ y2;
                        int y3 = i3;
                        if (i3 >= 0) {
                            x2 = (long) (y3 ^ 16256);
                        } else {
                            long tempPos4 = x + 1;
                            int i4 = (UnsafeUtil.getByte(x) << Ascii.NAK) ^ y3;
                            int y4 = i4;
                            if (i4 < 0) {
                                x2 = (long) (-2080896 ^ y4);
                                x = tempPos4;
                            } else {
                                long tempPos5 = tempPos4 + 1;
                                long j = ((long) y4) ^ (((long) UnsafeUtil.getByte(tempPos4)) << 28);
                                long x3 = j;
                                if (j >= 0) {
                                    x2 = 266354560 ^ x3;
                                    x = tempPos5;
                                } else {
                                    x = tempPos5 + 1;
                                    long j2 = (((long) UnsafeUtil.getByte(tempPos5)) << 35) ^ x3;
                                    long x4 = j2;
                                    if (j2 < 0) {
                                        x2 = -34093383808L ^ x4;
                                    } else {
                                        long tempPos6 = x + 1;
                                        long j3 = (((long) UnsafeUtil.getByte(x)) << 42) ^ x4;
                                        long x5 = j3;
                                        if (j3 >= 0) {
                                            x2 = 4363953127296L ^ x5;
                                            x = tempPos6;
                                        } else {
                                            x = tempPos6 + 1;
                                            long j4 = (((long) UnsafeUtil.getByte(tempPos6)) << 49) ^ x5;
                                            long x6 = j4;
                                            if (j4 < 0) {
                                                x2 = -558586000294016L ^ x6;
                                            } else {
                                                long tempPos7 = x + 1;
                                                long x7 = ((((long) UnsafeUtil.getByte(x)) << 56) ^ x6) ^ 71499008037633920L;
                                                if (x7 < 0) {
                                                    long tempPos8 = tempPos7 + 1;
                                                    if (((long) UnsafeUtil.getByte(tempPos7)) >= 0) {
                                                        x2 = x7;
                                                        x = tempPos8;
                                                    }
                                                } else {
                                                    x2 = x7;
                                                    x = tempPos7;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.pos = x;
                    return x2;
                }
            }
            return readRawVarint64SlowPath();
        }

        /* access modifiers changed from: package-private */
        public long readRawVarint64SlowPath() throws IOException {
            long result = 0;
            for (int shift = 0; shift < 64; shift += 7) {
                byte b = readRawByte();
                result |= ((long) (b & Ascii.DEL)) << shift;
                if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        public int readRawLittleEndian32() throws IOException {
            long tempPos = this.pos;
            if (this.limit - tempPos >= 4) {
                this.pos = 4 + tempPos;
                return (UnsafeUtil.getByte(tempPos) & UnsignedBytes.MAX_VALUE) | ((UnsafeUtil.getByte(1 + tempPos) & UnsignedBytes.MAX_VALUE) << 8) | ((UnsafeUtil.getByte(2 + tempPos) & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((UnsafeUtil.getByte(3 + tempPos) & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public long readRawLittleEndian64() throws IOException {
            long tempPos = this.pos;
            if (this.limit - tempPos >= 8) {
                this.pos = 8 + tempPos;
                return (((long) UnsafeUtil.getByte(tempPos)) & 255) | ((((long) UnsafeUtil.getByte(1 + tempPos)) & 255) << 8) | ((((long) UnsafeUtil.getByte(2 + tempPos)) & 255) << 16) | ((((long) UnsafeUtil.getByte(3 + tempPos)) & 255) << 24) | ((((long) UnsafeUtil.getByte(4 + tempPos)) & 255) << 32) | ((((long) UnsafeUtil.getByte(5 + tempPos)) & 255) << 40) | ((((long) UnsafeUtil.getByte(6 + tempPos)) & 255) << 48) | ((255 & ((long) UnsafeUtil.getByte(7 + tempPos))) << 56);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public void enableAliasing(boolean enabled) {
            this.enableAliasing = enabled;
        }

        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit >= 0) {
                int byteLimit2 = byteLimit + getTotalBytesRead();
                int oldLimit = this.currentLimit;
                if (byteLimit2 <= oldLimit) {
                    this.currentLimit = byteLimit2;
                    recomputeBufferSizeAfterLimit();
                    return oldLimit;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        public void popLimit(int oldLimit) {
            this.currentLimit = oldLimit;
            recomputeBufferSizeAfterLimit();
        }

        public int getBytesUntilLimit() {
            int i = this.currentLimit;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        public int getTotalBytesRead() {
            return (int) (this.pos - this.startPos);
        }

        public byte readRawByte() throws IOException {
            long j = this.pos;
            if (j != this.limit) {
                this.pos = 1 + j;
                return UnsafeUtil.getByte(j);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public byte[] readRawBytes(int length) throws IOException {
            if (length >= 0 && length <= remaining()) {
                byte[] bytes = new byte[length];
                long j = this.pos;
                slice(j, ((long) length) + j).get(bytes);
                this.pos += (long) length;
                return bytes;
            } else if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        public void skipRawBytes(int length) throws IOException {
            if (length >= 0 && length <= remaining()) {
                this.pos += (long) length;
            } else if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void recomputeBufferSizeAfterLimit() {
            this.limit += (long) this.bufferSizeAfterLimit;
            long j = this.limit;
            int bufferEnd = (int) (j - this.startPos);
            int i = this.currentLimit;
            if (bufferEnd > i) {
                this.bufferSizeAfterLimit = bufferEnd - i;
                this.limit = j - ((long) this.bufferSizeAfterLimit);
                return;
            }
            this.bufferSizeAfterLimit = 0;
        }

        private int remaining() {
            return (int) (this.limit - this.pos);
        }

        private int bufferPos(long pos2) {
            return (int) (pos2 - this.address);
        }

        private ByteBuffer slice(long begin, long end) throws IOException {
            int prevPos = this.buffer.position();
            int prevLimit = this.buffer.limit();
            try {
                this.buffer.position(bufferPos(begin));
                this.buffer.limit(bufferPos(end));
                ByteBuffer slice = this.buffer.slice();
                this.buffer.position(prevPos);
                this.buffer.limit(prevLimit);
                return slice;
            } catch (IllegalArgumentException e) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } catch (Throwable th) {
                this.buffer.position(prevPos);
                this.buffer.limit(prevLimit);
                throw th;
            }
        }
    }

    private static final class StreamDecoder extends CodedInputStream {
        /* access modifiers changed from: private */
        public final byte[] buffer;
        private final InputStream input;
        /* access modifiers changed from: private */
        public int pos;
        private int bufferSize;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private int lastTag;
        private RefillCallback refillCallback;
        private int totalBytesRetired;

        private StreamDecoder(InputStream input2, int bufferSize2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.refillCallback = null;
            Internal.checkNotNull(input2, TvContractCompat.PARAM_INPUT);
            this.input = input2;
            this.buffer = new byte[bufferSize2];
            this.bufferSize = 0;
            this.pos = 0;
            this.totalBytesRetired = 0;
        }

        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        public int getLastTag() {
            return this.lastTag;
        }

        public boolean skipField(int tag) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            } else if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            } else if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            } else if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    skipRawBytes(4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        /* JADX INFO: Multiple debug info for r0v1 com.google.protobuf.ByteString: [D('endtag' int), D('value' com.google.protobuf.ByteString)] */
        public boolean skipField(int tag, CodedOutputStream output) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            } else if (tagWireType == 1) {
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            } else if (tagWireType == 2) {
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            } else if (tagWireType == 3) {
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    int value4 = readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        public void skipMessage(CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, output));
        }

        public void mergeToMessage(MutableMessageLite message) throws IOException {
            SkippedDataSink dataSink = new SkippedDataSink();
            this.refillCallback = dataSink;
            skipMessage();
            this.refillCallback = null;
            ByteBuffer data = dataSink.getSkippedData();
            if (!message.mergeFrom(data.array(), data.arrayOffset() + data.position(), data.remaining())) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        public String readString() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                int i = this.bufferSize;
                int i2 = this.pos;
                if (size <= i - i2) {
                    String result = new String(this.buffer, i2, size, Internal.UTF_8);
                    this.pos += size;
                    return result;
                }
            }
            if (size == 0) {
                return "";
            }
            if (size > this.bufferSize) {
                return new String(readRawBytesSlowPath(size, false), Internal.UTF_8);
            }
            refillBuffer(size);
            String result2 = new String(this.buffer, this.pos, size, Internal.UTF_8);
            this.pos += size;
            return result2;
        }

        public String readStringRequireUtf8() throws IOException {
            int tempPos;
            byte[] bytes;
            int size = readRawVarint32();
            int oldPos = this.pos;
            if (size <= this.bufferSize - oldPos && size > 0) {
                bytes = this.buffer;
                this.pos = oldPos + size;
                tempPos = oldPos;
            } else if (size == 0) {
                return "";
            } else {
                if (size <= this.bufferSize) {
                    refillBuffer(size);
                    bytes = this.buffer;
                    tempPos = 0;
                    this.pos = 0 + size;
                } else {
                    bytes = readRawBytesSlowPath(size, false);
                    tempPos = 0;
                }
            }
            return Utf8.decodeUtf8(bytes, tempPos, size);
        }

        public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readGroup(int fieldNumber, MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readGroup(int fieldNumber, T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Deprecated
        public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
            readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readMessage(MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readMessage(T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public ByteString readBytes() throws IOException {
            int size = readRawVarint32();
            int i = this.bufferSize;
            int i2 = this.pos;
            if (size <= i - i2 && size > 0) {
                ByteString result = ByteString.copyFrom(this.buffer, i2, size);
                this.pos += size;
                return result;
            } else if (size == 0) {
                return ByteString.EMPTY;
            } else {
                return readBytesSlowPath(size);
            }
        }

        public byte[] readByteArray() throws IOException {
            int size = readRawVarint32();
            int i = this.bufferSize;
            int i2 = this.pos;
            if (size > i - i2 || size <= 0) {
                return readRawBytesSlowPath(size, false);
            }
            byte[] result = Arrays.copyOfRange(this.buffer, i2, i2 + size);
            this.pos += size;
            return result;
        }

        public ByteBuffer readByteBuffer() throws IOException {
            int size = readRawVarint32();
            int i = this.bufferSize;
            int i2 = this.pos;
            if (size <= i - i2 && size > 0) {
                ByteBuffer result = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, i2, i2 + size));
                this.pos += size;
                return result;
            } else if (size == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            } else {
                return ByteBuffer.wrap(readRawBytesSlowPath(size, true));
            }
        }

        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX INFO: Multiple debug info for r1v7 'tempPos'  int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r0v8 int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r1v11 'tempPos'  int: [D('x' int), D('tempPos' int)] */
        /* JADX INFO: Multiple debug info for r0v9 byte: [D('tempPos' int), D('y' int)] */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0071, code lost:
            if (r2[r1] < 0) goto L_0x0074;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.pos
                int r1 = r5.bufferSize
                if (r1 != r0) goto L_0x0008
                goto L_0x0074
            L_0x0008:
                byte[] r2 = r5.buffer
                int r3 = r0 + 1
                byte r0 = r2[r0]
                r4 = r0
                if (r0 < 0) goto L_0x0014
                r5.pos = r3
                return r4
            L_0x0014:
                int r1 = r1 - r3
                r0 = 9
                if (r1 >= r0) goto L_0x001a
                goto L_0x0074
            L_0x001a:
                int r0 = r3 + 1
                byte r1 = r2[r3]
                int r1 = r1 << 7
                r1 = r1 ^ r4
                r3 = r1
                if (r1 >= 0) goto L_0x0029
                r1 = r3 ^ -128(0xffffffffffffff80, float:NaN)
                r3 = r1
                r1 = r0
                goto L_0x007b
            L_0x0029:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r0 = r0 << 14
                r0 = r0 ^ r3
                r3 = r0
                if (r0 < 0) goto L_0x0037
                r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
                r3 = r0
                goto L_0x007b
            L_0x0037:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 21
                r1 = r1 ^ r3
                r3 = r1
                if (r1 >= 0) goto L_0x0048
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r1 = r1 ^ r3
                r3 = r1
                r1 = r0
                goto L_0x007b
            L_0x0048:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r4 = r0 << 28
                r3 = r3 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r3 = r3 ^ r4
                if (r0 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
                int r1 = r4 + 1
                byte r4 = r2[r4]
                if (r4 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
                int r1 = r4 + 1
                byte r4 = r2[r4]
                if (r4 >= 0) goto L_0x007b
                int r4 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x007a
            L_0x0074:
                long r0 = r5.readRawVarint64SlowPath()
                int r1 = (int) r0
                return r1
            L_0x007a:
                r1 = r4
            L_0x007b:
                r5.pos = r1
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.StreamDecoder.readRawVarint32():int");
        }

        private void skipRawVarint() throws IOException {
            if (this.bufferSize - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            int i = 0;
            while (i < 10) {
                byte[] bArr = this.buffer;
                int i2 = this.pos;
                this.pos = i2 + 1;
                if (bArr[i2] < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            int i = 0;
            while (i < 10) {
                if (readRawByte() < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00bd, code lost:
            if (((long) r2[r1]) < 0) goto L_0x00c0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long readRawVarint64() throws java.io.IOException {
            /*
                r10 = this;
                int r0 = r10.pos
                int r1 = r10.bufferSize
                if (r1 != r0) goto L_0x0008
                goto L_0x00c0
            L_0x0008:
                byte[] r2 = r10.buffer
                int r3 = r0 + 1
                byte r0 = r2[r0]
                r4 = r0
                if (r0 < 0) goto L_0x0015
                r10.pos = r3
                long r0 = (long) r4
                return r0
            L_0x0015:
                int r1 = r1 - r3
                r0 = 9
                if (r1 >= r0) goto L_0x001c
                goto L_0x00c0
            L_0x001c:
                int r0 = r3 + 1
                byte r1 = r2[r3]
                int r1 = r1 << 7
                r1 = r1 ^ r4
                r3 = r1
                if (r1 >= 0) goto L_0x002b
                r1 = r3 ^ -128(0xffffffffffffff80, float:NaN)
                long r4 = (long) r1
                goto L_0x00c6
            L_0x002b:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                int r0 = r0 << 14
                r0 = r0 ^ r3
                r3 = r0
                if (r0 < 0) goto L_0x003b
                r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
                long r4 = (long) r0
                r0 = r1
                goto L_0x00c6
            L_0x003b:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 21
                r1 = r1 ^ r3
                r3 = r1
                if (r1 >= 0) goto L_0x004c
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r1 = r1 ^ r3
                long r4 = (long) r1
                goto L_0x00c6
            L_0x004c:
                long r4 = (long) r3
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r6 = (long) r0
                r0 = 28
                long r6 = r6 << r0
                long r4 = r4 ^ r6
                r6 = r4
                r8 = 0
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 < 0) goto L_0x0063
                r4 = 266354560(0xfe03f80, double:1.315966377E-315)
                long r4 = r4 ^ r6
                r0 = r1
                goto L_0x00c6
            L_0x0063:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r4 = (long) r1
                r1 = 35
                long r4 = r4 << r1
                long r4 = r4 ^ r6
                r6 = r4
                int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x0078
                r4 = -34093383808(0xfffffff80fe03f80, double:NaN)
                long r4 = r4 ^ r6
                goto L_0x00c6
            L_0x0078:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r4 = (long) r0
                r0 = 42
                long r4 = r4 << r0
                long r4 = r4 ^ r6
                r6 = r4
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 < 0) goto L_0x008e
                r4 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
                long r4 = r4 ^ r6
                r0 = r1
                goto L_0x00c6
            L_0x008e:
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r4 = (long) r1
                r1 = 49
                long r4 = r4 << r1
                long r4 = r4 ^ r6
                r6 = r4
                int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x00a3
                r4 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
                long r4 = r4 ^ r6
                goto L_0x00c6
            L_0x00a3:
                int r1 = r0 + 1
                byte r0 = r2[r0]
                long r4 = (long) r0
                r0 = 56
                long r4 = r4 << r0
                long r4 = r4 ^ r6
                r6 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
                long r4 = r4 ^ r6
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 >= 0) goto L_0x00c5
                int r0 = r1 + 1
                byte r1 = r2[r1]
                long r6 = (long) r1
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x00c6
            L_0x00c0:
                long r0 = r10.readRawVarint64SlowPath()
                return r0
            L_0x00c5:
                r0 = r1
            L_0x00c6:
                r10.pos = r0
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.StreamDecoder.readRawVarint64():long");
        }

        /* access modifiers changed from: package-private */
        public long readRawVarint64SlowPath() throws IOException {
            long result = 0;
            for (int shift = 0; shift < 64; shift += 7) {
                byte b = readRawByte();
                result |= ((long) (b & Ascii.DEL)) << shift;
                if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        public int readRawLittleEndian32() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize - tempPos < 4) {
                refillBuffer(4);
                tempPos = this.pos;
            }
            byte[] buffer2 = this.buffer;
            this.pos = tempPos + 4;
            return (buffer2[tempPos] & UnsignedBytes.MAX_VALUE) | ((buffer2[tempPos + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((buffer2[tempPos + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((buffer2[tempPos + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        }

        public long readRawLittleEndian64() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize - tempPos < 8) {
                refillBuffer(8);
                tempPos = this.pos;
            }
            byte[] buffer2 = this.buffer;
            this.pos = tempPos + 8;
            return (((long) buffer2[tempPos]) & 255) | ((((long) buffer2[tempPos + 1]) & 255) << 8) | ((((long) buffer2[tempPos + 2]) & 255) << 16) | ((((long) buffer2[tempPos + 3]) & 255) << 24) | ((((long) buffer2[tempPos + 4]) & 255) << 32) | ((((long) buffer2[tempPos + 5]) & 255) << 40) | ((((long) buffer2[tempPos + 6]) & 255) << 48) | ((255 & ((long) buffer2[tempPos + 7])) << 56);
        }

        public void enableAliasing(boolean enabled) {
        }

        public void resetSizeCounter() {
            this.totalBytesRetired = -this.pos;
        }

        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit >= 0) {
                int byteLimit2 = byteLimit + this.totalBytesRetired + this.pos;
                int oldLimit = this.currentLimit;
                if (byteLimit2 <= oldLimit) {
                    this.currentLimit = byteLimit2;
                    recomputeBufferSizeAfterLimit();
                    return oldLimit;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private void recomputeBufferSizeAfterLimit() {
            this.bufferSize += this.bufferSizeAfterLimit;
            int i = this.totalBytesRetired;
            int i2 = this.bufferSize;
            int bufferEnd = i + i2;
            int i3 = this.currentLimit;
            if (bufferEnd > i3) {
                this.bufferSizeAfterLimit = bufferEnd - i3;
                this.bufferSize = i2 - this.bufferSizeAfterLimit;
                return;
            }
            this.bufferSizeAfterLimit = 0;
        }

        public void popLimit(int oldLimit) {
            this.currentLimit = oldLimit;
            recomputeBufferSizeAfterLimit();
        }

        public int getBytesUntilLimit() {
            int i = this.currentLimit;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - (this.totalBytesRetired + this.pos);
        }

        public boolean isAtEnd() throws IOException {
            return this.pos == this.bufferSize && !tryRefillBuffer(1);
        }

        public int getTotalBytesRead() {
            return this.totalBytesRetired + this.pos;
        }

        private void refillBuffer(int n) throws IOException {
            if (tryRefillBuffer(n)) {
                return;
            }
            if (n > (this.sizeLimit - this.totalBytesRetired) - this.pos) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        private boolean tryRefillBuffer(int n) throws IOException {
            if (this.pos + n > this.bufferSize) {
                int i = this.sizeLimit;
                int i2 = this.totalBytesRetired;
                int i3 = this.pos;
                if (n > (i - i2) - i3 || i2 + i3 + n > this.currentLimit) {
                    return false;
                }
                RefillCallback refillCallback2 = this.refillCallback;
                if (refillCallback2 != null) {
                    refillCallback2.onRefill();
                }
                int tempPos = this.pos;
                if (tempPos > 0) {
                    int i4 = this.bufferSize;
                    if (i4 > tempPos) {
                        byte[] bArr = this.buffer;
                        System.arraycopy(bArr, tempPos, bArr, 0, i4 - tempPos);
                    }
                    this.totalBytesRetired += tempPos;
                    this.bufferSize -= tempPos;
                    this.pos = 0;
                }
                InputStream inputStream = this.input;
                byte[] bArr2 = this.buffer;
                int i5 = this.bufferSize;
                int bytesRead = inputStream.read(bArr2, i5, Math.min(bArr2.length - i5, (this.sizeLimit - this.totalBytesRetired) - this.bufferSize));
                if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                    String valueOf = String.valueOf(this.input.getClass());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 91);
                    sb.append(valueOf);
                    sb.append("#read(byte[]) returned invalid result: ");
                    sb.append(bytesRead);
                    sb.append("\nThe InputStream implementation is buggy.");
                    throw new IllegalStateException(sb.toString());
                } else if (bytesRead <= 0) {
                    return false;
                } else {
                    this.bufferSize += bytesRead;
                    recomputeBufferSizeAfterLimit();
                    if (this.bufferSize >= n) {
                        return true;
                    }
                    return tryRefillBuffer(n);
                }
            } else {
                StringBuilder sb2 = new StringBuilder(77);
                sb2.append("refillBuffer() called when ");
                sb2.append(n);
                sb2.append(" bytes were already available in buffer");
                throw new IllegalStateException(sb2.toString());
            }
        }

        public byte readRawByte() throws IOException {
            if (this.pos == this.bufferSize) {
                refillBuffer(1);
            }
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i];
        }

        public byte[] readRawBytes(int size) throws IOException {
            int tempPos = this.pos;
            if (size > this.bufferSize - tempPos || size <= 0) {
                return readRawBytesSlowPath(size, false);
            }
            this.pos = tempPos + size;
            return Arrays.copyOfRange(this.buffer, tempPos, tempPos + size);
        }

        private byte[] readRawBytesSlowPath(int size, boolean ensureNoLeakedReferences) throws IOException {
            byte[] result = readRawBytesSlowPathOneChunk(size);
            if (result != null) {
                return ensureNoLeakedReferences ? (byte[]) result.clone() : result;
            }
            int originalBufferPos = this.pos;
            int i = this.bufferSize;
            int bufferedBytes = i - this.pos;
            this.totalBytesRetired += i;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> chunks = readRawBytesSlowPathRemainingChunks(size - bufferedBytes);
            byte[] bytes = new byte[size];
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
            int tempPos = bufferedBytes;
            for (byte[] chunk : chunks) {
                System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
                tempPos += chunk.length;
            }
            return bytes;
        }

        private byte[] readRawBytesSlowPathOneChunk(int size) throws IOException {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            if (size >= 0) {
                int currentMessageSize = this.totalBytesRetired + this.pos + size;
                if (currentMessageSize - this.sizeLimit <= 0) {
                    int i = this.currentLimit;
                    if (currentMessageSize <= i) {
                        int bufferedBytes = this.bufferSize - this.pos;
                        int sizeLeft = size - bufferedBytes;
                        if (sizeLeft >= 4096 && sizeLeft > this.input.available()) {
                            return null;
                        }
                        byte[] bytes = new byte[size];
                        System.arraycopy(this.buffer, this.pos, bytes, 0, bufferedBytes);
                        this.totalBytesRetired += this.bufferSize;
                        this.pos = 0;
                        this.bufferSize = 0;
                        int tempPos = bufferedBytes;
                        while (tempPos < bytes.length) {
                            int n = this.input.read(bytes, tempPos, size - tempPos);
                            if (n != -1) {
                                this.totalBytesRetired += n;
                                tempPos += n;
                            } else {
                                throw InvalidProtocolBufferException.truncatedMessage();
                            }
                        }
                        return bytes;
                    }
                    skipRawBytes((i - this.totalBytesRetired) - this.pos);
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private List<byte[]> readRawBytesSlowPathRemainingChunks(int sizeLeft) throws IOException {
            List<byte[]> chunks = new ArrayList<>();
            while (sizeLeft > 0) {
                byte[] chunk = new byte[Math.min(sizeLeft, 4096)];
                int tempPos = 0;
                while (tempPos < chunk.length) {
                    int n = this.input.read(chunk, tempPos, chunk.length - tempPos);
                    if (n != -1) {
                        this.totalBytesRetired += n;
                        tempPos += n;
                    } else {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                }
                sizeLeft -= chunk.length;
                chunks.add(chunk);
            }
            return chunks;
        }

        private ByteString readBytesSlowPath(int size) throws IOException {
            byte[] result = readRawBytesSlowPathOneChunk(size);
            if (result != null) {
                return ByteString.copyFrom(result);
            }
            int originalBufferPos = this.pos;
            int i = this.bufferSize;
            int bufferedBytes = i - this.pos;
            this.totalBytesRetired += i;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> chunks = readRawBytesSlowPathRemainingChunks(size - bufferedBytes);
            byte[] bytes = new byte[size];
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
            int tempPos = bufferedBytes;
            for (byte[] chunk : chunks) {
                System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
                tempPos += chunk.length;
            }
            return ByteString.wrap(bytes);
        }

        public void skipRawBytes(int size) throws IOException {
            int i = this.bufferSize;
            int i2 = this.pos;
            if (size > i - i2 || size < 0) {
                skipRawBytesSlowPath(size);
            } else {
                this.pos = i2 + size;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x006e, code lost:
            throw new java.lang.IllegalStateException(r7.toString());
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void skipRawBytesSlowPath(int r10) throws java.io.IOException {
            /*
                r9 = this;
                if (r10 < 0) goto L_0x00ab
                int r0 = r9.totalBytesRetired
                int r1 = r9.pos
                int r2 = r0 + r1
                int r2 = r2 + r10
                int r3 = r9.currentLimit
                if (r2 > r3) goto L_0x00a1
                r2 = 0
                com.google.protobuf.CodedInputStream$StreamDecoder$RefillCallback r3 = r9.refillCallback
                if (r3 != 0) goto L_0x0081
                int r0 = r0 + r1
                r9.totalBytesRetired = r0
                int r0 = r9.bufferSize
                int r0 = r0 - r1
                r1 = 0
                r9.bufferSize = r1
                r9.pos = r1
                r2 = r0
            L_0x001e:
                if (r2 >= r10) goto L_0x0079
                int r0 = r10 - r2
                java.io.InputStream r1 = r9.input     // Catch:{ all -> 0x006f }
                long r3 = (long) r0     // Catch:{ all -> 0x006f }
                long r3 = r1.skip(r3)     // Catch:{ all -> 0x006f }
                r5 = 0
                int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r1 < 0) goto L_0x003c
                long r7 = (long) r0     // Catch:{ all -> 0x006f }
                int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
                if (r1 > 0) goto L_0x003c
                int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r1 != 0) goto L_0x0039
                goto L_0x0079
            L_0x0039:
                int r1 = (int) r3     // Catch:{ all -> 0x006f }
                int r2 = r2 + r1
                goto L_0x001e
            L_0x003c:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x006f }
                java.io.InputStream r5 = r9.input     // Catch:{ all -> 0x006f }
                java.lang.Class r5 = r5.getClass()     // Catch:{ all -> 0x006f }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x006f }
                java.lang.String r6 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x006f }
                int r6 = r6.length()     // Catch:{ all -> 0x006f }
                int r6 = r6 + 92
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x006f }
                r7.<init>(r6)     // Catch:{ all -> 0x006f }
                r7.append(r5)     // Catch:{ all -> 0x006f }
                java.lang.String r5 = "#skip returned invalid result: "
                r7.append(r5)     // Catch:{ all -> 0x006f }
                r7.append(r3)     // Catch:{ all -> 0x006f }
                java.lang.String r5 = "\nThe InputStream implementation is buggy."
                r7.append(r5)     // Catch:{ all -> 0x006f }
                java.lang.String r5 = r7.toString()     // Catch:{ all -> 0x006f }
                r1.<init>(r5)     // Catch:{ all -> 0x006f }
                throw r1     // Catch:{ all -> 0x006f }
            L_0x006f:
                r0 = move-exception
                int r1 = r9.totalBytesRetired
                int r1 = r1 + r2
                r9.totalBytesRetired = r1
                r9.recomputeBufferSizeAfterLimit()
                throw r0
            L_0x0079:
                int r0 = r9.totalBytesRetired
                int r0 = r0 + r2
                r9.totalBytesRetired = r0
                r9.recomputeBufferSizeAfterLimit()
            L_0x0081:
                if (r2 >= r10) goto L_0x00a0
                int r0 = r9.bufferSize
                int r1 = r9.pos
                int r1 = r0 - r1
                r9.pos = r0
                r0 = 1
                r9.refillBuffer(r0)
            L_0x008f:
                int r3 = r10 - r1
                int r4 = r9.bufferSize
                if (r3 <= r4) goto L_0x009c
                int r1 = r1 + r4
                r9.pos = r4
                r9.refillBuffer(r0)
                goto L_0x008f
            L_0x009c:
                int r0 = r10 - r1
                r9.pos = r0
            L_0x00a0:
                return
            L_0x00a1:
                int r3 = r3 - r0
                int r3 = r3 - r1
                r9.skipRawBytes(r3)
                com.google.protobuf.InvalidProtocolBufferException r0 = com.google.protobuf.InvalidProtocolBufferException.truncatedMessage()
                throw r0
            L_0x00ab:
                com.google.protobuf.InvalidProtocolBufferException r0 = com.google.protobuf.InvalidProtocolBufferException.negativeSize()
                throw r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.StreamDecoder.skipRawBytesSlowPath(int):void");
        }

        private interface RefillCallback {
            void onRefill();
        }

        private class SkippedDataSink implements RefillCallback {
            private ByteArrayOutputStream byteArrayStream;
            private int lastPos;

            private SkippedDataSink() {
                this.lastPos = StreamDecoder.this.pos;
            }

            public void onRefill() {
                if (this.byteArrayStream == null) {
                    this.byteArrayStream = new ByteArrayOutputStream();
                }
                this.byteArrayStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                this.lastPos = 0;
            }

            /* access modifiers changed from: package-private */
            public ByteBuffer getSkippedData() {
                ByteArrayOutputStream byteArrayOutputStream = this.byteArrayStream;
                if (byteArrayOutputStream == null) {
                    return ByteBuffer.wrap(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                }
                byteArrayOutputStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos);
                return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
            }
        }
    }

    private static final class IterableDirectByteBufferDecoder extends CodedInputStream {
        private int bufferSizeAfterCurrentLimit;
        private long currentAddress;
        private ByteBuffer currentByteBuffer;
        private long currentByteBufferLimit;
        private long currentByteBufferPos;
        private long currentByteBufferStartPos;
        private int currentLimit;
        private boolean enableAliasing;
        private boolean immutable;
        private Iterable<ByteBuffer> input;
        private Iterator<ByteBuffer> iterator;
        private int lastTag;
        private int startOffset;
        private int totalBufferSize;
        private int totalBytesRead;

        private IterableDirectByteBufferDecoder(Iterable<ByteBuffer> inputBufs, int size, boolean immutableFlag) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.totalBufferSize = size;
            this.input = inputBufs;
            this.iterator = this.input.iterator();
            this.immutable = immutableFlag;
            this.totalBytesRead = 0;
            this.startOffset = 0;
            if (size == 0) {
                this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
                this.currentByteBufferPos = 0;
                this.currentByteBufferStartPos = 0;
                this.currentByteBufferLimit = 0;
                this.currentAddress = 0;
                return;
            }
            tryGetNextByteBuffer();
        }

        private void getNextByteBuffer() throws InvalidProtocolBufferException {
            if (this.iterator.hasNext()) {
                tryGetNextByteBuffer();
                return;
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        private void tryGetNextByteBuffer() {
            this.currentByteBuffer = this.iterator.next();
            this.totalBytesRead += (int) (this.currentByteBufferPos - this.currentByteBufferStartPos);
            this.currentByteBufferPos = (long) this.currentByteBuffer.position();
            this.currentByteBufferStartPos = this.currentByteBufferPos;
            this.currentByteBufferLimit = (long) this.currentByteBuffer.limit();
            this.currentAddress = UnsafeUtil.addressOffset(this.currentByteBuffer);
            long j = this.currentByteBufferPos;
            long j2 = this.currentAddress;
            this.currentByteBufferPos = j + j2;
            this.currentByteBufferStartPos += j2;
            this.currentByteBufferLimit += j2;
        }

        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        public int getLastTag() {
            return this.lastTag;
        }

        public boolean skipField(int tag) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            } else if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            } else if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            } else if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    skipRawBytes(4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        /* JADX INFO: Multiple debug info for r0v1 com.google.protobuf.ByteString: [D('endtag' int), D('value' com.google.protobuf.ByteString)] */
        public boolean skipField(int tag, CodedOutputStream output) throws IOException {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            } else if (tagWireType == 1) {
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            } else if (tagWireType == 2) {
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            } else if (tagWireType == 3) {
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            } else if (tagWireType == 4) {
                return false;
            } else {
                if (tagWireType == 5) {
                    int value4 = readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value4);
                    return true;
                }
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        public void skipMessage(CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, output));
        }

        public void mergeToMessage(MutableMessageLite message) throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            CodedOutputStream codedOutput = CodedOutputStream.newInstance(output);
            skipMessage(codedOutput);
            codedOutput.flush();
            if (!message.mergeFrom(output.toByteArray())) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        public String readString() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                long j = this.currentByteBufferLimit;
                long j2 = this.currentByteBufferPos;
                if (((long) size) <= j - j2) {
                    byte[] bytes = new byte[size];
                    UnsafeUtil.copyMemory(j2, bytes, 0, (long) size);
                    String result = new String(bytes, Internal.UTF_8);
                    this.currentByteBufferPos += (long) size;
                    return result;
                }
            }
            if (size > 0 && size <= remaining()) {
                byte[] bytes2 = new byte[size];
                readRawBytesTo(bytes2, 0, size);
                return new String(bytes2, Internal.UTF_8);
            } else if (size == 0) {
                return "";
            } else {
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public String readStringRequireUtf8() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                long j = this.currentByteBufferLimit;
                long j2 = this.currentByteBufferPos;
                if (((long) size) <= j - j2) {
                    String result = Utf8.decodeUtf8(this.currentByteBuffer, (int) (j2 - this.currentByteBufferStartPos), size);
                    this.currentByteBufferPos += (long) size;
                    return result;
                }
            }
            if (size >= 0 && size <= remaining()) {
                byte[] bytes = new byte[size];
                readRawBytesTo(bytes, 0, size);
                return Utf8.decodeUtf8(bytes, 0, size);
            } else if (size == 0) {
                return "";
            } else {
                if (size <= 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readGroup(int fieldNumber, MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readGroup(int fieldNumber, T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth < this.recursionLimit) {
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.recursionDepth--;
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Deprecated
        public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
            readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                builder.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public void readMessage(MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                message.mergeFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends GeneratedMessageLite<T, ?>> T readMessage(T instance, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = GeneratedMessageLite.parsePartialFrom(instance, this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
            int length = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int oldLimit = pushLimit(length);
                this.recursionDepth++;
                T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(oldLimit);
                return result;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        /* JADX INFO: Multiple debug info for r1v7 byte[]: [D('bytes' byte[]), D('idx' int)] */
        public ByteString readBytes() throws IOException {
            int size = readRawVarint32();
            if (size > 0) {
                long j = this.currentByteBufferLimit;
                long j2 = this.currentByteBufferPos;
                if (((long) size) <= j - j2) {
                    if (!this.immutable || !this.enableAliasing) {
                        byte[] bytes = new byte[size];
                        UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0, (long) size);
                        this.currentByteBufferPos += (long) size;
                        return ByteString.wrap(bytes);
                    }
                    int idx = (int) (j2 - this.currentAddress);
                    ByteString result = ByteString.wrap(slice(idx, idx + size));
                    this.currentByteBufferPos += (long) size;
                    return result;
                }
            }
            if (size > 0 && size <= remaining()) {
                byte[] temp = new byte[size];
                readRawBytesTo(temp, 0, size);
                return ByteString.wrap(temp);
            } else if (size == 0) {
                return ByteString.EMPTY;
            } else {
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        public ByteBuffer readByteBuffer() throws IOException {
            int size = readRawVarint32();
            if (size <= 0 || ((long) size) > currentRemaining()) {
                if (size > 0 && size <= remaining()) {
                    byte[] temp = new byte[size];
                    readRawBytesTo(temp, 0, size);
                    return ByteBuffer.wrap(temp);
                } else if (size == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                } else {
                    if (size < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } else if (this.immutable || !this.enableAliasing) {
                byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0, (long) size);
                this.currentByteBufferPos += (long) size;
                return ByteBuffer.wrap(bytes);
            } else {
                this.currentByteBufferPos += (long) size;
                long j = this.currentByteBufferPos;
                long j2 = this.currentAddress;
                return slice((int) ((j - j2) - ((long) size)), (int) (j - j2));
            }
        }

        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0093, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r4) < 0) goto L_0x0096;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.currentByteBufferPos
                long r2 = r10.currentByteBufferLimit
                long r4 = r10.currentByteBufferPos
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 != 0) goto L_0x000c
                goto L_0x0096
            L_0x000c:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r0)
                r1 = r0
                if (r0 < 0) goto L_0x001d
                long r6 = r10.currentByteBufferPos
                long r6 = r6 + r2
                r10.currentByteBufferPos = r6
                return r1
            L_0x001d:
                long r6 = r10.currentByteBufferLimit
                long r8 = r10.currentByteBufferPos
                long r6 = r6 - r8
                r8 = 10
                int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r0 >= 0) goto L_0x002a
                goto L_0x0096
            L_0x002a:
                long r6 = r4 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r4)
                int r0 = r0 << 7
                r0 = r0 ^ r1
                r1 = r0
                if (r0 >= 0) goto L_0x003b
                r0 = r1 ^ -128(0xffffffffffffff80, float:NaN)
                r1 = r0
                goto L_0x00a1
            L_0x003b:
                long r4 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r6)
                int r0 = r0 << 14
                r0 = r0 ^ r1
                r1 = r0
                if (r0 < 0) goto L_0x004c
                r0 = r1 ^ 16256(0x3f80, float:2.278E-41)
                r1 = r0
                r6 = r4
                goto L_0x00a1
            L_0x004c:
                long r6 = r4 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r4)
                int r0 = r0 << 21
                r0 = r0 ^ r1
                r1 = r0
                if (r0 >= 0) goto L_0x005e
                r0 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                r1 = r0
                goto L_0x00a1
            L_0x005e:
                long r4 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r6)
                int r6 = r0 << 28
                r1 = r1 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r1 = r1 ^ r6
                if (r0 >= 0) goto L_0x00a0
                long r6 = r4 + r2
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r4 >= 0) goto L_0x00a1
                long r4 = r6 + r2
                byte r6 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r6 >= 0) goto L_0x009e
                long r6 = r4 + r2
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r4 >= 0) goto L_0x00a1
                long r4 = r6 + r2
                byte r6 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r6 >= 0) goto L_0x009c
                long r6 = r4 + r2
                byte r2 = com.google.protobuf.UnsafeUtil.getByte(r4)
                if (r2 >= 0) goto L_0x00a1
            L_0x0096:
                long r0 = r10.readRawVarint64SlowPath()
                int r1 = (int) r0
                return r1
            L_0x009c:
                r6 = r4
                goto L_0x00a1
            L_0x009e:
                r6 = r4
                goto L_0x00a1
            L_0x00a0:
                r6 = r4
            L_0x00a1:
                r10.currentByteBufferPos = r6
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.IterableDirectByteBufferDecoder.readRawVarint32():int");
        }

        public long readRawVarint64() throws IOException {
            long x;
            long x2;
            long tempPos = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != this.currentByteBufferPos) {
                long tempPos2 = tempPos + 1;
                int i = UnsafeUtil.getByte(tempPos);
                int y = i;
                if (i >= 0) {
                    this.currentByteBufferPos++;
                    return (long) y;
                } else if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long tempPos3 = tempPos2 + 1;
                    int i2 = (UnsafeUtil.getByte(tempPos2) << 7) ^ y;
                    int y2 = i2;
                    if (i2 < 0) {
                        x2 = (long) (y2 ^ -128);
                        x = tempPos3;
                    } else {
                        x = tempPos3 + 1;
                        int i3 = (UnsafeUtil.getByte(tempPos3) << Ascii.f157SO) ^ y2;
                        int y3 = i3;
                        if (i3 >= 0) {
                            x2 = (long) (y3 ^ 16256);
                        } else {
                            long tempPos4 = x + 1;
                            int i4 = (UnsafeUtil.getByte(x) << Ascii.NAK) ^ y3;
                            int y4 = i4;
                            if (i4 < 0) {
                                x2 = (long) (-2080896 ^ y4);
                                x = tempPos4;
                            } else {
                                long tempPos5 = tempPos4 + 1;
                                long j = ((long) y4) ^ (((long) UnsafeUtil.getByte(tempPos4)) << 28);
                                long x3 = j;
                                if (j >= 0) {
                                    x2 = 266354560 ^ x3;
                                    x = tempPos5;
                                } else {
                                    x = tempPos5 + 1;
                                    long j2 = (((long) UnsafeUtil.getByte(tempPos5)) << 35) ^ x3;
                                    long x4 = j2;
                                    if (j2 < 0) {
                                        x2 = -34093383808L ^ x4;
                                    } else {
                                        long tempPos6 = x + 1;
                                        long j3 = (((long) UnsafeUtil.getByte(x)) << 42) ^ x4;
                                        long x5 = j3;
                                        if (j3 >= 0) {
                                            x2 = 4363953127296L ^ x5;
                                            x = tempPos6;
                                        } else {
                                            x = tempPos6 + 1;
                                            long j4 = (((long) UnsafeUtil.getByte(tempPos6)) << 49) ^ x5;
                                            long x6 = j4;
                                            if (j4 < 0) {
                                                x2 = -558586000294016L ^ x6;
                                            } else {
                                                long tempPos7 = x + 1;
                                                long x7 = ((((long) UnsafeUtil.getByte(x)) << 56) ^ x6) ^ 71499008037633920L;
                                                if (x7 < 0) {
                                                    long tempPos8 = tempPos7 + 1;
                                                    if (((long) UnsafeUtil.getByte(tempPos7)) >= 0) {
                                                        x2 = x7;
                                                        x = tempPos8;
                                                    }
                                                } else {
                                                    x2 = x7;
                                                    x = tempPos7;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.currentByteBufferPos = x;
                    return x2;
                }
            }
            return readRawVarint64SlowPath();
        }

        /* access modifiers changed from: package-private */
        public long readRawVarint64SlowPath() throws IOException {
            long result = 0;
            for (int shift = 0; shift < 64; shift += 7) {
                byte b = readRawByte();
                result |= ((long) (b & Ascii.DEL)) << shift;
                if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        public int readRawLittleEndian32() throws IOException {
            if (currentRemaining() < 4) {
                return (readRawByte() & UnsignedBytes.MAX_VALUE) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << 8) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
            }
            long tempPos = this.currentByteBufferPos;
            this.currentByteBufferPos += 4;
            return (UnsafeUtil.getByte(tempPos) & UnsignedBytes.MAX_VALUE) | ((UnsafeUtil.getByte(1 + tempPos) & UnsignedBytes.MAX_VALUE) << 8) | ((UnsafeUtil.getByte(2 + tempPos) & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((UnsafeUtil.getByte(3 + tempPos) & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        }

        public long readRawLittleEndian64() throws IOException {
            if (currentRemaining() < 8) {
                return (((long) readRawByte()) & 255) | ((((long) readRawByte()) & 255) << 8) | ((((long) readRawByte()) & 255) << 16) | ((((long) readRawByte()) & 255) << 24) | ((((long) readRawByte()) & 255) << 32) | ((((long) readRawByte()) & 255) << 40) | ((((long) readRawByte()) & 255) << 48) | ((((long) readRawByte()) & 255) << 56);
            }
            long tempPos = this.currentByteBufferPos;
            this.currentByteBufferPos += 8;
            return ((((long) UnsafeUtil.getByte(6 + tempPos)) & 255) << 48) | ((((long) UnsafeUtil.getByte(4 + tempPos)) & 255) << 32) | ((((long) UnsafeUtil.getByte(2 + tempPos)) & 255) << 16) | (((long) UnsafeUtil.getByte(tempPos)) & 255) | ((((long) UnsafeUtil.getByte(1 + tempPos)) & 255) << 8) | ((((long) UnsafeUtil.getByte(3 + tempPos)) & 255) << 24) | ((((long) UnsafeUtil.getByte(5 + tempPos)) & 255) << 40) | ((((long) UnsafeUtil.getByte(7 + tempPos)) & 255) << 56);
        }

        public void enableAliasing(boolean enabled) {
            this.enableAliasing = enabled;
        }

        public void resetSizeCounter() {
            this.startOffset = (int) ((((long) this.totalBytesRead) + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit >= 0) {
                int byteLimit2 = byteLimit + getTotalBytesRead();
                int oldLimit = this.currentLimit;
                if (byteLimit2 <= oldLimit) {
                    this.currentLimit = byteLimit2;
                    recomputeBufferSizeAfterLimit();
                    return oldLimit;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private void recomputeBufferSizeAfterLimit() {
            this.totalBufferSize += this.bufferSizeAfterCurrentLimit;
            int i = this.totalBufferSize;
            int bufferEnd = i - this.startOffset;
            int i2 = this.currentLimit;
            if (bufferEnd > i2) {
                this.bufferSizeAfterCurrentLimit = bufferEnd - i2;
                this.totalBufferSize = i - this.bufferSizeAfterCurrentLimit;
                return;
            }
            this.bufferSizeAfterCurrentLimit = 0;
        }

        public void popLimit(int oldLimit) {
            this.currentLimit = oldLimit;
            recomputeBufferSizeAfterLimit();
        }

        public int getBytesUntilLimit() {
            int i = this.currentLimit;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        public boolean isAtEnd() throws IOException {
            return (((long) this.totalBytesRead) + this.currentByteBufferPos) - this.currentByteBufferStartPos == ((long) this.totalBufferSize);
        }

        public int getTotalBytesRead() {
            return (int) ((((long) (this.totalBytesRead - this.startOffset)) + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        public byte readRawByte() throws IOException {
            if (currentRemaining() == 0) {
                getNextByteBuffer();
            }
            long j = this.currentByteBufferPos;
            this.currentByteBufferPos = 1 + j;
            return UnsafeUtil.getByte(j);
        }

        public byte[] readRawBytes(int length) throws IOException {
            if (length >= 0 && ((long) length) <= currentRemaining()) {
                byte[] bytes = new byte[length];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0, (long) length);
                this.currentByteBufferPos += (long) length;
                return bytes;
            } else if (length >= 0 && length <= remaining()) {
                byte[] bytes2 = new byte[length];
                readRawBytesTo(bytes2, 0, length);
                return bytes2;
            } else if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        private void readRawBytesTo(byte[] bytes, int offset, int length) throws IOException {
            if (length >= 0 && length <= remaining()) {
                int l = length;
                while (l > 0) {
                    if (currentRemaining() == 0) {
                        getNextByteBuffer();
                    }
                    int bytesToCopy = Math.min(l, (int) currentRemaining());
                    UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, (long) ((length - l) + offset), (long) bytesToCopy);
                    l -= bytesToCopy;
                    this.currentByteBufferPos += (long) bytesToCopy;
                }
            } else if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (length != 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        public void skipRawBytes(int length) throws IOException {
            if (length >= 0 && ((long) length) <= (((long) (this.totalBufferSize - this.totalBytesRead)) - this.currentByteBufferPos) + this.currentByteBufferStartPos) {
                int l = length;
                while (l > 0) {
                    if (currentRemaining() == 0) {
                        getNextByteBuffer();
                    }
                    int rl = Math.min(l, (int) currentRemaining());
                    l -= rl;
                    this.currentByteBufferPos += (long) rl;
                }
            } else if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void skipRawVarint() throws IOException {
            int i = 0;
            while (i < 10) {
                if (readRawByte() < 0) {
                    i++;
                } else {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private int remaining() {
            return (int) ((((long) (this.totalBufferSize - this.totalBytesRead)) - this.currentByteBufferPos) + this.currentByteBufferStartPos);
        }

        private long currentRemaining() {
            return this.currentByteBufferLimit - this.currentByteBufferPos;
        }

        private ByteBuffer slice(int begin, int end) throws IOException {
            int prevPos = this.currentByteBuffer.position();
            int prevLimit = this.currentByteBuffer.limit();
            try {
                this.currentByteBuffer.position(begin);
                this.currentByteBuffer.limit(end);
                ByteBuffer slice = this.currentByteBuffer.slice();
                this.currentByteBuffer.position(prevPos);
                this.currentByteBuffer.limit(prevLimit);
                return slice;
            } catch (IllegalArgumentException e) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } catch (Throwable th) {
                this.currentByteBuffer.position(prevPos);
                this.currentByteBuffer.limit(prevLimit);
                throw th;
            }
        }
    }
}
