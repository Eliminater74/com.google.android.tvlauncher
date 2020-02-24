package com.google.protobuf;

import com.google.common.base.Ascii;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.nio.ByteBuffer;

final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Processor processor;

    static {
        Processor processor2;
        if (!UnsafeProcessor.isAvailable() || Android.isOnAndroidDevice()) {
            processor2 = new SafeProcessor();
        } else {
            processor2 = new UnsafeProcessor();
        }
        processor = processor2;
    }

    public static boolean isValidUtf8(byte[] bytes) {
        return processor.isValidUtf8(bytes, 0, bytes.length);
    }

    public static boolean isValidUtf8(byte[] bytes, int index, int limit) {
        return processor.isValidUtf8(bytes, index, limit);
    }

    public static int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
        return processor.partialIsValidUtf8(state, bytes, index, limit);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1) {
        if (byte1 > -12) {
            return -1;
        }
        return byte1;
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2) {
        if (byte1 > -12 || byte2 > -65) {
            return -1;
        }
        return (byte2 << 8) ^ byte1;
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2, int byte3) {
        if (byte1 > -12 || byte2 > -65 || byte3 > -65) {
            return -1;
        }
        return ((byte2 << 8) ^ byte1) ^ (byte3 << 16);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bytes, int index, int limit) {
        byte b = bytes[index - 1];
        int i = limit - index;
        if (i == 0) {
            return incompleteStateFor(b);
        }
        if (i == 1) {
            return incompleteStateFor(b, bytes[index]);
        }
        if (i == 2) {
            return incompleteStateFor(b, bytes[index], bytes[index + 1]);
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer buffer, int byte1, int index, int remaining) {
        if (remaining == 0) {
            return incompleteStateFor(byte1);
        }
        if (remaining == 1) {
            return incompleteStateFor(byte1, buffer.get(index));
        }
        if (remaining == 2) {
            return incompleteStateFor(byte1, buffer.get(index), buffer.get(index + 1));
        }
        throw new AssertionError();
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        UnpairedSurrogateException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 54
                r0.<init>(r1)
                java.lang.String r1 = "Unpaired surrogate at index "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = " of "
                r0.append(r1)
                r0.append(r4)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnpairedSurrogateException.<init>(int, int):void");
        }
    }

    static int encodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < 128) {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c >= 2048) {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
                utf8Length += (127 - c) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (utf8Length >= utf16Length) {
            return utf8Length;
        }
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(((long) utf8Length) + 4294967296L);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int i = start;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 2048) {
                utf8Length += (127 - c) >>> 31;
            } else {
                utf8Length += 2;
                if (55296 <= c && c <= 57343) {
                    if (Character.codePointAt(sequence, i) >= 65536) {
                        i++;
                    } else {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    }
                }
            }
            i++;
        }
        return utf8Length;
    }

    static int encode(CharSequence in, byte[] out, int offset, int length) {
        return processor.encodeUtf8(in, out, offset, length);
    }

    static boolean isValidUtf8(ByteBuffer buffer) {
        return processor.isValidUtf8(buffer, buffer.position(), buffer.remaining());
    }

    static int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
        return processor.partialIsValidUtf8(state, buffer, index, limit);
    }

    static String decodeUtf8(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
        return processor.decodeUtf8(buffer, index, size);
    }

    static String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
        return processor.decodeUtf8(bytes, index, size);
    }

    static void encodeUtf8(CharSequence in, ByteBuffer out) {
        processor.encodeUtf8(in, out);
    }

    /* access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer buffer, int index, int limit) {
        int i = index;
        int lim = limit - 7;
        while (i < lim && (buffer.getLong(i) & ASCII_MASK_LONG) == 0) {
            i += 8;
        }
        return i - index;
    }

    static abstract class Processor {
        /* access modifiers changed from: package-private */
        public abstract String decodeUtf8(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException;

        /* access modifiers changed from: package-private */
        public abstract String decodeUtf8Direct(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException;

        /* access modifiers changed from: package-private */
        public abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2);

        /* access modifiers changed from: package-private */
        public abstract void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer);

        /* access modifiers changed from: package-private */
        public abstract int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3);

        /* access modifiers changed from: package-private */
        public abstract int partialIsValidUtf8Direct(int i, ByteBuffer byteBuffer, int i2, int i3);

        Processor() {
        }

        /* access modifiers changed from: package-private */
        public final boolean isValidUtf8(byte[] bytes, int index, int limit) {
            return partialIsValidUtf8(0, bytes, index, limit) == 0;
        }

        /* access modifiers changed from: package-private */
        public final boolean isValidUtf8(ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8(0, buffer, index, limit) == 0;
        }

        /* access modifiers changed from: package-private */
        public final int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
            if (buffer.hasArray()) {
                int offset = buffer.arrayOffset();
                return partialIsValidUtf8(state, buffer.array(), offset + index, offset + limit);
            } else if (buffer.isDirect() != 0) {
                return partialIsValidUtf8Direct(state, buffer, index, limit);
            } else {
                return partialIsValidUtf8Default(state, buffer, index, limit);
            }
        }

        /* access modifiers changed from: package-private */
        public final int partialIsValidUtf8Default(int state, ByteBuffer buffer, int index, int limit) {
            int index2;
            if (state == 0) {
                index2 = index;
            } else if (index >= limit) {
                return state;
            } else {
                byte byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        index2 = index + 1;
                        if (buffer.get(index) > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    byte byte2 = (byte) ((state >> 8) ^ -1);
                    if (byte2 == 0) {
                        int index3 = index + 1;
                        byte2 = buffer.get(index);
                        if (index3 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte2);
                        }
                        index = index3;
                    }
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        index2 = index + 1;
                        if (buffer.get(index) > -65) {
                        }
                    }
                    return -1;
                } else {
                    byte byte22 = (byte) ((state >> 8) ^ -1);
                    byte byte3 = 0;
                    if (byte22 == 0) {
                        int index4 = index + 1;
                        byte22 = buffer.get(index);
                        if (index4 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22);
                        }
                        index = index4;
                    } else {
                        byte3 = (byte) (state >> 16);
                    }
                    if (byte3 == 0) {
                        int index5 = index + 1;
                        byte3 = buffer.get(index);
                        if (index5 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22, byte3);
                        }
                        index = index5;
                    }
                    if (byte22 <= -65 && (((byte1 << Ascii.f150FS) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        int index6 = index + 1;
                        if (buffer.get(index) <= -65) {
                            index2 = index6;
                        }
                    }
                    return -1;
                }
            }
            return partialIsValidUtf8(buffer, index2, limit);
        }

        /* JADX INFO: Multiple debug info for r0v3 byte: [D('byte2' int), D('index' int)] */
        /* JADX INFO: Multiple debug info for r0v4 byte: [D('index' int), D('byte2' byte)] */
        private static int partialIsValidUtf8(ByteBuffer buffer, int index, int limit) {
            int index2 = index + Utf8.estimateConsecutiveAscii(buffer, index, limit);
            while (index2 < limit) {
                int index3 = index2 + 1;
                int index4 = buffer.get(index2);
                int byte1 = index4;
                if (index4 >= 0) {
                    index2 = index3;
                } else if (byte1 < -32) {
                    if (index3 >= limit) {
                        return byte1;
                    }
                    if (byte1 < -62 || buffer.get(index3) > -65) {
                        return -1;
                    }
                    index2 = index3 + 1;
                } else if (byte1 < -16) {
                    if (index3 >= limit - 1) {
                        return Utf8.incompleteStateFor(buffer, byte1, index3, limit - index3);
                    }
                    int index5 = index3 + 1;
                    byte byte2 = buffer.get(index3);
                    if (byte2 > -65 || ((byte1 == -32 && byte2 < -96) || ((byte1 == -19 && byte2 >= -96) || buffer.get(index5) > -65))) {
                        return -1;
                    }
                    index2 = index5 + 1;
                } else if (index3 >= limit - 2) {
                    return Utf8.incompleteStateFor(buffer, byte1, index3, limit - index3);
                } else {
                    int index6 = index3 + 1;
                    int index7 = buffer.get(index3);
                    if (index7 <= -65 && (((byte1 << 28) + (index7 + 112)) >> 30) == 0) {
                        int index8 = index6 + 1;
                        if (buffer.get(index6) <= -65) {
                            index2 = index8 + 1;
                            if (buffer.get(index8) > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public final String decodeUtf8(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
            if (buffer.hasArray()) {
                return decodeUtf8(buffer.array(), buffer.arrayOffset() + index, size);
            } else if (buffer.isDirect() != 0) {
                return decodeUtf8Direct(buffer, index, size);
            } else {
                return decodeUtf8Default(buffer, index, size);
            }
        }

        /* JADX INFO: Multiple debug info for r2v6 byte: [D('offset' int), D('byte1' byte)] */
        /* access modifiers changed from: package-private */
        public final String decodeUtf8Default(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
            int offset;
            ByteBuffer byteBuffer = buffer;
            int i = size;
            if ((index | i | ((buffer.limit() - index) - i)) >= 0) {
                int offset2 = index;
                int limit = offset2 + i;
                char[] resultArr = new char[i];
                int resultPos = 0;
                while (offset < limit) {
                    byte b = byteBuffer.get(offset);
                    if (!DecodeUtil.isOneByte(b)) {
                        break;
                    }
                    offset2 = offset + 1;
                    DecodeUtil.handleOneByte(b, resultArr, resultPos);
                    resultPos++;
                }
                int resultPos2 = resultPos;
                while (offset < limit) {
                    int offset3 = offset + 1;
                    byte byte1 = byteBuffer.get(offset);
                    if (DecodeUtil.isOneByte(byte1)) {
                        int resultPos3 = resultPos2 + 1;
                        DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                        while (offset3 < limit) {
                            byte b2 = byteBuffer.get(offset3);
                            if (!DecodeUtil.isOneByte(b2)) {
                                break;
                            }
                            offset3++;
                            DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                            resultPos3++;
                        }
                        offset = offset3;
                        resultPos2 = resultPos3;
                    } else if (DecodeUtil.isTwoBytes(byte1) != 0) {
                        if (offset3 < limit) {
                            DecodeUtil.handleTwoBytes(byte1, byteBuffer.get(offset3), resultArr, resultPos2);
                            offset = offset3 + 1;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (DecodeUtil.isThreeBytes(byte1)) {
                        if (offset3 < limit - 1) {
                            int offset4 = offset3 + 1;
                            DecodeUtil.handleThreeBytes(byte1, byteBuffer.get(offset3), byteBuffer.get(offset4), resultArr, resultPos2);
                            offset = offset4 + 1;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (offset3 < limit - 2) {
                        int offset5 = offset3 + 1;
                        byte b3 = byteBuffer.get(offset3);
                        int offset6 = offset5 + 1;
                        DecodeUtil.handleFourBytes(byte1, b3, byteBuffer.get(offset5), byteBuffer.get(offset6), resultArr, resultPos2);
                        offset = offset6 + 1;
                        resultPos2 = resultPos2 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                }
                return new String(resultArr, 0, resultPos2);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(size)));
        }

        /* access modifiers changed from: package-private */
        public final void encodeUtf8(CharSequence in, ByteBuffer out) {
            if (out.hasArray()) {
                int offset = out.arrayOffset();
                out.position(Utf8.encode(in, out.array(), out.position() + offset, out.remaining()) - offset);
            } else if (out.isDirect()) {
                encodeUtf8Direct(in, out);
            } else {
                encodeUtf8Default(in, out);
            }
        }

        /* access modifiers changed from: package-private */
        public final void encodeUtf8Default(CharSequence in, ByteBuffer out) {
            int outIx;
            int inLength = in.length();
            int outIx2 = out.position();
            int inIx = 0;
            while (inIx < inLength) {
                try {
                    char charAt = in.charAt(inIx);
                    char c = charAt;
                    if (charAt >= 128) {
                        break;
                    }
                    out.put(outIx2 + inIx, (byte) c);
                    inIx++;
                } catch (IndexOutOfBoundsException e) {
                    char charAt2 = in.charAt(inIx);
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Failed writing ");
                    sb.append(charAt2);
                    sb.append(" at index ");
                    sb.append(out.position() + Math.max(inIx, (outIx2 - out.position()) + 1));
                    throw new ArrayIndexOutOfBoundsException(sb.toString());
                }
            }
            if (inIx == inLength) {
                out.position(outIx2 + inIx);
                return;
            }
            outIx2 += inIx;
            while (inIx < inLength) {
                char c2 = in.charAt(inIx);
                if (c2 < 128) {
                    out.put(outIx2, (byte) c2);
                } else if (c2 < 2048) {
                    outIx = outIx2 + 1;
                    try {
                        out.put(outIx2, (byte) ((c2 >>> 6) | 192));
                        out.put(outIx, (byte) ((c2 & '?') | 128));
                        outIx2 = outIx;
                    } catch (IndexOutOfBoundsException e2) {
                        outIx2 = outIx;
                        char charAt22 = in.charAt(inIx);
                        StringBuilder sb2 = new StringBuilder(37);
                        sb2.append("Failed writing ");
                        sb2.append(charAt22);
                        sb2.append(" at index ");
                        sb2.append(out.position() + Math.max(inIx, (outIx2 - out.position()) + 1));
                        throw new ArrayIndexOutOfBoundsException(sb2.toString());
                    }
                } else if (c2 < 55296 || 57343 < c2) {
                    outIx = outIx2 + 1;
                    out.put(outIx2, (byte) ((c2 >>> 12) | 224));
                    outIx2 = outIx + 1;
                    out.put(outIx, (byte) (((c2 >>> 6) & 63) | 128));
                    out.put(outIx2, (byte) ((c2 & '?') | 128));
                } else {
                    if (inIx + 1 != inLength) {
                        inIx++;
                        char charAt3 = in.charAt(inIx);
                        char low = charAt3;
                        if (Character.isSurrogatePair(c2, charAt3)) {
                            int codePoint = Character.toCodePoint(c2, low);
                            int outIx3 = outIx2 + 1;
                            try {
                                out.put(outIx2, (byte) ((codePoint >>> 18) | 240));
                                outIx2 = outIx3 + 1;
                                out.put(outIx3, (byte) (((codePoint >>> 12) & 63) | 128));
                                outIx3 = outIx2 + 1;
                                out.put(outIx2, (byte) (((codePoint >>> 6) & 63) | 128));
                                out.put(outIx3, (byte) ((codePoint & 63) | 128));
                                outIx2 = outIx3;
                            } catch (IndexOutOfBoundsException e3) {
                                outIx2 = outIx3;
                                char charAt222 = in.charAt(inIx);
                                StringBuilder sb22 = new StringBuilder(37);
                                sb22.append("Failed writing ");
                                sb22.append(charAt222);
                                sb22.append(" at index ");
                                sb22.append(out.position() + Math.max(inIx, (outIx2 - out.position()) + 1));
                                throw new ArrayIndexOutOfBoundsException(sb22.toString());
                            }
                        }
                    }
                    throw new UnpairedSurrogateException(inIx, inLength);
                }
                inIx++;
                outIx2++;
            }
            out.position(outIx2);
        }
    }

    static final class SafeProcessor extends Processor {
        SafeProcessor() {
        }

        /* access modifiers changed from: package-private */
        public int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
            int index2;
            if (state == 0) {
                index2 = index;
            } else if (index >= limit) {
                return state;
            } else {
                int byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        index2 = index + 1;
                        if (bytes[index] > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    byte b = (byte) ((state >> 8) ^ -1);
                    if (b == 0) {
                        int index3 = index + 1;
                        b = bytes[index];
                        if (index3 >= limit) {
                            return Utf8.incompleteStateFor(byte1, b);
                        }
                        index = index3;
                    }
                    if (b <= -65 && ((byte1 != -32 || b >= -96) && (byte1 != -19 || b < -96))) {
                        index2 = index + 1;
                        if (bytes[index] > -65) {
                        }
                    }
                    return -1;
                } else {
                    byte b2 = (byte) ((state >> 8) ^ -1);
                    byte b3 = 0;
                    if (b2 == 0) {
                        int index4 = index + 1;
                        b2 = bytes[index];
                        if (index4 >= limit) {
                            return Utf8.incompleteStateFor(byte1, b2);
                        }
                        index = index4;
                    } else {
                        b3 = (byte) (state >> 16);
                    }
                    if (b3 == 0) {
                        int index5 = index + 1;
                        b3 = bytes[index];
                        if (index5 >= limit) {
                            return Utf8.incompleteStateFor(byte1, b2, b3);
                        }
                        index = index5;
                    }
                    if (b2 <= -65 && (((byte1 << 28) + (b2 + 112)) >> 30) == 0 && b3 <= -65) {
                        int index6 = index + 1;
                        if (bytes[index] <= -65) {
                            index2 = index6;
                        }
                    }
                    return -1;
                }
            }
            return partialIsValidUtf8(bytes, index2, limit);
        }

        /* access modifiers changed from: package-private */
        public int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8Default(state, buffer, index, limit);
        }

        /* JADX INFO: Multiple debug info for r2v6 byte: [D('offset' int), D('byte1' byte)] */
        /* access modifiers changed from: package-private */
        public String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
            int offset;
            byte[] bArr = bytes;
            int i = size;
            if ((index | i | ((bArr.length - index) - i)) >= 0) {
                int offset2 = index;
                int limit = offset2 + i;
                char[] resultArr = new char[i];
                int resultPos = 0;
                while (offset < limit) {
                    byte b = bArr[offset];
                    if (!DecodeUtil.isOneByte(b)) {
                        break;
                    }
                    offset2 = offset + 1;
                    DecodeUtil.handleOneByte(b, resultArr, resultPos);
                    resultPos++;
                }
                int resultPos2 = resultPos;
                while (offset < limit) {
                    int offset3 = offset + 1;
                    byte byte1 = bArr[offset];
                    if (DecodeUtil.isOneByte(byte1)) {
                        int resultPos3 = resultPos2 + 1;
                        DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                        while (offset3 < limit) {
                            byte b2 = bArr[offset3];
                            if (!DecodeUtil.isOneByte(b2)) {
                                break;
                            }
                            offset3++;
                            DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                            resultPos3++;
                        }
                        offset = offset3;
                        resultPos2 = resultPos3;
                    } else if (DecodeUtil.isTwoBytes(byte1) != 0) {
                        if (offset3 < limit) {
                            DecodeUtil.handleTwoBytes(byte1, bArr[offset3], resultArr, resultPos2);
                            offset = offset3 + 1;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (DecodeUtil.isThreeBytes(byte1)) {
                        if (offset3 < limit - 1) {
                            int offset4 = offset3 + 1;
                            DecodeUtil.handleThreeBytes(byte1, bArr[offset3], bArr[offset4], resultArr, resultPos2);
                            offset = offset4 + 1;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (offset3 < limit - 2) {
                        int offset5 = offset3 + 1;
                        byte b3 = bArr[offset3];
                        int offset6 = offset5 + 1;
                        DecodeUtil.handleFourBytes(byte1, b3, bArr[offset5], bArr[offset6], resultArr, resultPos2);
                        offset = offset6 + 1;
                        resultPos2 = resultPos2 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                }
                return new String(resultArr, 0, resultPos2);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(index), Integer.valueOf(size)));
        }

        /* access modifiers changed from: package-private */
        public String decodeUtf8Direct(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
            return decodeUtf8Default(buffer, index, size);
        }

        /* access modifiers changed from: package-private */
        public int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            int utf16Length = in.length();
            int j = offset;
            int i = 0;
            int limit = offset + length;
            while (i < utf16Length && i + j < limit) {
                char charAt = in.charAt(i);
                char c = charAt;
                if (charAt >= 128) {
                    break;
                }
                out[j + i] = (byte) c;
                i++;
            }
            if (i == utf16Length) {
                return j + utf16Length;
            }
            int j2 = j + i;
            while (i < utf16Length) {
                char c2 = in.charAt(i);
                if (c2 < 128 && j2 < limit) {
                    out[j2] = (byte) c2;
                    j2++;
                } else if (c2 < 2048 && j2 <= limit - 2) {
                    int j3 = j2 + 1;
                    out[j2] = (byte) ((c2 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE);
                    j2 = j3 + 1;
                    out[j3] = (byte) ((c2 & '?') | 128);
                } else if ((c2 < 55296 || 57343 < c2) && j2 <= limit - 3) {
                    int j4 = j2 + 1;
                    out[j2] = (byte) ((c2 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE);
                    int j5 = j4 + 1;
                    out[j4] = (byte) (((c2 >>> 6) & 63) | 128);
                    out[j5] = (byte) ((c2 & '?') | 128);
                    j2 = j5 + 1;
                } else if (j2 <= limit - 4) {
                    if (i + 1 != in.length()) {
                        i++;
                        char charAt2 = in.charAt(i);
                        char low = charAt2;
                        if (Character.isSurrogatePair(c2, charAt2)) {
                            int codePoint = Character.toCodePoint(c2, low);
                            int j6 = j2 + 1;
                            out[j2] = (byte) ((codePoint >>> 18) | 240);
                            int j7 = j6 + 1;
                            out[j6] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int j8 = j7 + 1;
                            out[j7] = (byte) (((codePoint >>> 6) & 63) | 128);
                            j2 = j8 + 1;
                            out[j8] = (byte) ((codePoint & 63) | 128);
                        }
                    }
                    throw new UnpairedSurrogateException(i - 1, utf16Length);
                } else if (55296 > c2 || c2 > 57343 || (i + 1 != in.length() && Character.isSurrogatePair(c2, in.charAt(i + 1)))) {
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Failed writing ");
                    sb.append(c2);
                    sb.append(" at index ");
                    sb.append(j2);
                    throw new ArrayIndexOutOfBoundsException(sb.toString());
                } else {
                    throw new UnpairedSurrogateException(i, utf16Length);
                }
                i++;
            }
            return j2;
        }

        /* access modifiers changed from: package-private */
        public void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
            encodeUtf8Default(in, out);
        }

        private static int partialIsValidUtf8(byte[] bytes, int index, int limit) {
            while (index < limit && bytes[index] >= 0) {
                index++;
            }
            if (index >= limit) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bytes, index, limit);
        }

        private static int partialIsValidUtf8NonAscii(byte[] bytes, int index, int limit) {
            while (index < limit) {
                int index2 = index + 1;
                byte b = bytes[index];
                int byte1 = b;
                if (b >= 0) {
                    index = index2;
                } else if (byte1 < -32) {
                    if (index2 >= limit) {
                        return byte1;
                    }
                    if (byte1 >= -62) {
                        index = index2 + 1;
                        if (bytes[index2] > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    if (index2 >= limit - 1) {
                        return Utf8.incompleteStateFor(bytes, index2, limit);
                    }
                    int index3 = index2 + 1;
                    byte b2 = bytes[index2];
                    int byte2 = b2;
                    if (b2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        index = index3 + 1;
                        if (bytes[index3] > -65) {
                        }
                    }
                    return -1;
                } else if (index2 >= limit - 2) {
                    return Utf8.incompleteStateFor(bytes, index2, limit);
                } else {
                    int index4 = index2 + 1;
                    byte b3 = bytes[index2];
                    int byte22 = b3;
                    if (b3 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0) {
                        int index5 = index4 + 1;
                        if (bytes[index4] <= -65) {
                            index = index5 + 1;
                            if (bytes[index5] > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

    static final class UnsafeProcessor extends Processor {
        UnsafeProcessor() {
        }

        static boolean isAvailable() {
            return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r1, r4) > -65) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r1, r4) > -65) goto L_0x0062;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a3, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r1, r4) > -65) goto L_0x00a7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8(int r18, byte[] r19, int r20, int r21) {
            /*
                r17 = this;
                r0 = r18
                r1 = r19
                r2 = r20
                r3 = r21
                r4 = r2 | r3
                int r5 = r1.length
                int r5 = r5 - r3
                r4 = r4 | r5
                if (r4 < 0) goto L_0x00b1
                long r4 = (long) r2
                long r6 = (long) r3
                if (r0 == 0) goto L_0x00a8
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 < 0) goto L_0x0018
                return r0
            L_0x0018:
                byte r8 = (byte) r0
                r9 = -32
                r10 = -1
                r11 = -65
                r12 = 1
                if (r8 >= r9) goto L_0x0030
                r9 = -62
                if (r8 < r9) goto L_0x002e
                long r12 = r12 + r4
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                if (r4 <= r11) goto L_0x00a9
                goto L_0x002f
            L_0x002e:
                r12 = r4
            L_0x002f:
                return r10
            L_0x0030:
                r14 = -16
                if (r8 >= r14) goto L_0x0063
                int r14 = r0 >> 8
                r14 = r14 ^ r10
                byte r14 = (byte) r14
                if (r14 != 0) goto L_0x004a
                long r15 = r4 + r12
                byte r14 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                int r4 = (r15 > r6 ? 1 : (r15 == r6 ? 0 : -1))
                if (r4 < 0) goto L_0x0049
                int r4 = com.google.protobuf.Utf8.incompleteStateFor(r8, r14)
                return r4
            L_0x0049:
                r4 = r15
            L_0x004a:
                if (r14 > r11) goto L_0x0061
                r15 = -96
                if (r8 != r9) goto L_0x0052
                if (r14 < r15) goto L_0x0061
            L_0x0052:
                r9 = -19
                if (r8 != r9) goto L_0x0058
                if (r14 >= r15) goto L_0x0061
            L_0x0058:
                long r12 = r12 + r4
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                if (r4 <= r11) goto L_0x0060
                goto L_0x0062
            L_0x0060:
                goto L_0x00a9
            L_0x0061:
                r12 = r4
            L_0x0062:
                return r10
            L_0x0063:
                int r9 = r0 >> 8
                r9 = r9 ^ r10
                byte r9 = (byte) r9
                r14 = 0
                if (r9 != 0) goto L_0x007b
                long r15 = r4 + r12
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                int r4 = (r15 > r6 ? 1 : (r15 == r6 ? 0 : -1))
                if (r4 < 0) goto L_0x0079
                int r4 = com.google.protobuf.Utf8.incompleteStateFor(r8, r9)
                return r4
            L_0x0079:
                r4 = r15
                goto L_0x007e
            L_0x007b:
                int r15 = r0 >> 16
                byte r14 = (byte) r15
            L_0x007e:
                if (r14 != 0) goto L_0x0090
                long r15 = r4 + r12
                byte r14 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                int r4 = (r15 > r6 ? 1 : (r15 == r6 ? 0 : -1))
                if (r4 < 0) goto L_0x008f
                int r4 = com.google.protobuf.Utf8.incompleteStateFor(r8, r9, r14)
                return r4
            L_0x008f:
                r4 = r15
            L_0x0090:
                if (r9 > r11) goto L_0x00a6
                int r15 = r8 << 28
                int r16 = r9 + 112
                int r15 = r15 + r16
                int r15 = r15 >> 30
                if (r15 != 0) goto L_0x00a6
                if (r14 > r11) goto L_0x00a6
                long r12 = r12 + r4
                byte r4 = com.google.protobuf.UnsafeUtil.getByte(r1, r4)
                if (r4 <= r11) goto L_0x00a9
                goto L_0x00a7
            L_0x00a6:
                r12 = r4
            L_0x00a7:
                return r10
            L_0x00a8:
                r12 = r4
            L_0x00a9:
                long r4 = r6 - r12
                int r5 = (int) r4
                int r4 = partialIsValidUtf8(r1, r12, r5)
                return r4
            L_0x00b1:
                java.lang.ArrayIndexOutOfBoundsException r4 = new java.lang.ArrayIndexOutOfBoundsException
                r5 = 3
                java.lang.Object[] r5 = new java.lang.Object[r5]
                r6 = 0
                int r7 = r1.length
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
                r5[r6] = r7
                r6 = 1
                java.lang.Integer r7 = java.lang.Integer.valueOf(r20)
                r5[r6] = r7
                r6 = 2
                java.lang.Integer r7 = java.lang.Integer.valueOf(r21)
                r5[r6] = r7
                java.lang.String r6 = "Array length=%d, index=%d, limit=%d"
                java.lang.String r5 = java.lang.String.format(r6, r5)
                r4.<init>(r5)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* access modifiers changed from: package-private */
        public int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
            long address;
            long address2;
            int i = state;
            int i2 = index;
            if ((i2 | limit | (buffer.limit() - limit)) >= 0) {
                long address3 = UnsafeUtil.addressOffset(buffer) + ((long) i2);
                long addressLimit = ((long) (limit - i2)) + address3;
                if (i != 0) {
                    if (address3 >= addressLimit) {
                        return i;
                    }
                    int byte1 = (byte) i;
                    if (byte1 < -32) {
                        if (byte1 >= -62) {
                            long address4 = 1 + address3;
                            if (UnsafeUtil.getByte(address3) <= -65) {
                                address3 = address4;
                            }
                        }
                        return -1;
                    } else if (byte1 < -16) {
                        int byte2 = (byte) ((i >> 8) ^ -1);
                        if (byte2 == 0) {
                            address2 = address3 + 1;
                            byte2 = UnsafeUtil.getByte(address3);
                            if (address2 >= addressLimit) {
                                return Utf8.incompleteStateFor(byte1, byte2);
                            }
                        } else {
                            address2 = address3;
                        }
                        if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                            address3 = address2 + 1;
                            if (UnsafeUtil.getByte(address2) > -65) {
                            }
                        }
                        return -1;
                    } else {
                        int byte22 = (byte) ((i >> 8) ^ -1);
                        int byte3 = 0;
                        if (byte22 == 0) {
                            address = address3 + 1;
                            byte22 = UnsafeUtil.getByte(address3);
                            if (address >= addressLimit) {
                                return Utf8.incompleteStateFor(byte1, byte22);
                            }
                        } else {
                            byte3 = (byte) (i >> 16);
                            address = address3;
                        }
                        if (byte3 == 0) {
                            long address5 = address + 1;
                            byte3 = UnsafeUtil.getByte(address);
                            if (address5 >= addressLimit) {
                                return Utf8.incompleteStateFor(byte1, byte22, byte3);
                            }
                            address = address5;
                        }
                        if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                            address3 = address + 1;
                            if (UnsafeUtil.getByte(address) > -65) {
                            }
                        }
                        return -1;
                    }
                }
                return partialIsValidUtf8(address3, (int) (addressLimit - address3));
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(limit)));
        }

        /* access modifiers changed from: package-private */
        public String decodeUtf8(byte[] bytes, int index, int size) throws InvalidProtocolBufferException {
            int offset;
            byte[] bArr = bytes;
            int i = size;
            if ((index | i | ((bArr.length - index) - i)) >= 0) {
                int offset2 = index;
                int limit = offset2 + i;
                char[] resultArr = new char[i];
                int resultPos = 0;
                while (offset < limit) {
                    byte b = UnsafeUtil.getByte(bArr, (long) offset);
                    if (!DecodeUtil.isOneByte(b)) {
                        break;
                    }
                    offset2 = offset + 1;
                    DecodeUtil.handleOneByte(b, resultArr, resultPos);
                    resultPos++;
                }
                int resultPos2 = resultPos;
                while (offset < limit) {
                    int offset3 = offset + 1;
                    byte byte1 = UnsafeUtil.getByte(bArr, (long) offset);
                    if (DecodeUtil.isOneByte(byte1)) {
                        int resultPos3 = resultPos2 + 1;
                        DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                        while (offset3 < limit) {
                            byte b2 = UnsafeUtil.getByte(bArr, (long) offset3);
                            if (!DecodeUtil.isOneByte(b2)) {
                                break;
                            }
                            offset3++;
                            DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                            resultPos3++;
                        }
                        offset = offset3;
                        resultPos2 = resultPos3;
                    } else if (DecodeUtil.isTwoBytes(byte1) != 0) {
                        if (offset3 < limit) {
                            int offset4 = offset3 + 1;
                            DecodeUtil.handleTwoBytes(byte1, UnsafeUtil.getByte(bArr, (long) offset3), resultArr, resultPos2);
                            offset = offset4;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (DecodeUtil.isThreeBytes(byte1)) {
                        if (offset3 < limit - 1) {
                            int offset5 = offset3 + 1;
                            int offset6 = offset5 + 1;
                            DecodeUtil.handleThreeBytes(byte1, UnsafeUtil.getByte(bArr, (long) offset3), UnsafeUtil.getByte(bArr, (long) offset5), resultArr, resultPos2);
                            offset = offset6;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (offset3 < limit - 2) {
                        int offset7 = offset3 + 1;
                        byte b3 = UnsafeUtil.getByte(bArr, (long) offset3);
                        int offset8 = offset7 + 1;
                        DecodeUtil.handleFourBytes(byte1, b3, UnsafeUtil.getByte(bArr, (long) offset7), UnsafeUtil.getByte(bArr, (long) offset8), resultArr, resultPos2);
                        offset = offset8 + 1;
                        resultPos2 = resultPos2 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                }
                return new String(resultArr, 0, resultPos2);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(index), Integer.valueOf(size)));
        }

        /* JADX INFO: Multiple debug info for r5v6 byte: [D('address' long), D('byte1' byte)] */
        /* access modifiers changed from: package-private */
        public String decodeUtf8Direct(ByteBuffer buffer, int index, int size) throws InvalidProtocolBufferException {
            long address;
            int i = index;
            int i2 = size;
            if ((i | i2 | ((buffer.limit() - i) - i2)) >= 0) {
                long address2 = UnsafeUtil.addressOffset(buffer) + ((long) i);
                long addressLimit = ((long) i2) + address2;
                char[] resultArr = new char[i2];
                int resultPos = 0;
                while (address < addressLimit) {
                    byte b = UnsafeUtil.getByte(address);
                    if (!DecodeUtil.isOneByte(b)) {
                        break;
                    }
                    address2 = address + 1;
                    DecodeUtil.handleOneByte(b, resultArr, resultPos);
                    resultPos++;
                }
                int resultPos2 = resultPos;
                while (address < addressLimit) {
                    long address3 = address + 1;
                    byte byte1 = UnsafeUtil.getByte(address);
                    if (DecodeUtil.isOneByte(byte1)) {
                        int resultPos3 = resultPos2 + 1;
                        DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                        while (address3 < addressLimit) {
                            byte b2 = UnsafeUtil.getByte(address3);
                            if (!DecodeUtil.isOneByte(b2)) {
                                break;
                            }
                            address3++;
                            DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                            resultPos3++;
                        }
                        resultPos2 = resultPos3;
                        address = address3;
                    } else if (DecodeUtil.isTwoBytes(byte1) != 0) {
                        if (address3 < addressLimit) {
                            DecodeUtil.handleTwoBytes(byte1, UnsafeUtil.getByte(address3), resultArr, resultPos2);
                            resultPos2++;
                            address = address3 + 1;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (DecodeUtil.isThreeBytes(byte1)) {
                        if (address3 < addressLimit - 1) {
                            long address4 = address3 + 1;
                            byte b3 = UnsafeUtil.getByte(address3);
                            DecodeUtil.handleThreeBytes(byte1, b3, UnsafeUtil.getByte(address4), resultArr, resultPos2);
                            address = address4 + 1;
                            resultPos2++;
                        } else {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    } else if (address3 < addressLimit - 2) {
                        long address5 = address3 + 1;
                        long address6 = address5 + 1;
                        DecodeUtil.handleFourBytes(byte1, UnsafeUtil.getByte(address3), UnsafeUtil.getByte(address5), UnsafeUtil.getByte(address6), resultArr, resultPos2);
                        resultPos2 = resultPos2 + 1 + 1;
                        address = address6 + 1;
                    } else {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                }
                return new String(resultArr, 0, resultPos2);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(size)));
        }

        /* JADX INFO: Multiple debug info for r6v6 long: [D('outIx' long), D('outLimit' long)] */
        /* access modifiers changed from: package-private */
        public int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            long outIx;
            char c;
            long j;
            long outIx2;
            CharSequence charSequence = in;
            byte[] bArr = out;
            int i = offset;
            int i2 = length;
            long outIx3 = (long) i;
            long outLimit = ((long) i2) + outIx3;
            int inLimit = in.length();
            if (inLimit > i2 || bArr.length - i2 < i) {
                char charAt = charSequence.charAt(inLimit - 1);
                StringBuilder sb = new StringBuilder(37);
                sb.append("Failed writing ");
                sb.append(charAt);
                sb.append(" at index ");
                sb.append(offset + length);
                throw new ArrayIndexOutOfBoundsException(sb.toString());
            }
            int inIx = 0;
            while (true) {
                c = 128;
                j = 1;
                if (inIx >= inLimit) {
                    break;
                }
                char charAt2 = charSequence.charAt(inIx);
                char c2 = charAt2;
                if (charAt2 >= 128) {
                    break;
                }
                UnsafeUtil.putByte(bArr, outIx, (byte) c2);
                inIx++;
                outIx3 = 1 + outIx;
            }
            if (inIx == inLimit) {
                return (int) outIx;
            }
            while (inIx < inLimit) {
                char c3 = charSequence.charAt(inIx);
                if (c3 < c && outIx < outLimit) {
                    UnsafeUtil.putByte(bArr, outIx, (byte) c3);
                    outIx += j;
                    c = 128;
                    outIx2 = outLimit;
                } else if (c3 < 2048 && outIx <= outLimit - 2) {
                    long outIx4 = outIx + j;
                    UnsafeUtil.putByte(bArr, outIx, (byte) ((c3 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE));
                    outIx = outIx4 + j;
                    UnsafeUtil.putByte(bArr, outIx4, (byte) ((c3 & '?') | 128));
                    outIx2 = outLimit;
                    c = 128;
                } else if ((c3 < 55296 || 57343 < c3) && outIx <= outLimit - 3) {
                    long outIx5 = outIx + 1;
                    UnsafeUtil.putByte(bArr, outIx, (byte) ((c3 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE));
                    long outIx6 = outIx5 + 1;
                    UnsafeUtil.putByte(bArr, outIx5, (byte) (((c3 >>> 6) & 63) | 128));
                    UnsafeUtil.putByte(bArr, outIx6, (byte) ((c3 & '?') | 128));
                    outIx2 = outLimit;
                    outIx = outIx6 + 1;
                    c = 128;
                } else if (outIx <= outLimit - 4) {
                    if (inIx + 1 != inLimit) {
                        inIx++;
                        char charAt3 = charSequence.charAt(inIx);
                        char low = charAt3;
                        if (Character.isSurrogatePair(c3, charAt3)) {
                            int codePoint = Character.toCodePoint(c3, low);
                            outIx2 = outLimit;
                            long outLimit2 = outIx + 1;
                            UnsafeUtil.putByte(bArr, outIx, (byte) ((codePoint >>> 18) | 240));
                            long outIx7 = outLimit2 + 1;
                            c = 128;
                            UnsafeUtil.putByte(bArr, outLimit2, (byte) (((codePoint >>> 12) & 63) | 128));
                            long outIx8 = outIx7 + 1;
                            UnsafeUtil.putByte(bArr, outIx7, (byte) (((codePoint >>> 6) & 63) | 128));
                            outIx = outIx8 + 1;
                            UnsafeUtil.putByte(bArr, outIx8, (byte) ((codePoint & 63) | 128));
                        }
                    }
                    throw new UnpairedSurrogateException(inIx - 1, inLimit);
                } else if (55296 > c3 || c3 > 57343 || (inIx + 1 != inLimit && Character.isSurrogatePair(c3, charSequence.charAt(inIx + 1)))) {
                    StringBuilder sb2 = new StringBuilder(46);
                    sb2.append("Failed writing ");
                    sb2.append(c3);
                    sb2.append(" at index ");
                    sb2.append(outIx);
                    throw new ArrayIndexOutOfBoundsException(sb2.toString());
                } else {
                    throw new UnpairedSurrogateException(inIx, inLimit);
                }
                inIx++;
                outLimit = outIx2;
                j = 1;
            }
            return (int) outIx;
        }

        /* JADX INFO: Multiple debug info for r2v10 long: [D('outIx' long), D('low' char)] */
        /* access modifiers changed from: package-private */
        public void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
            long outIx;
            char c;
            long j;
            long j2;
            long outIx2;
            String str;
            CharSequence charSequence = in;
            ByteBuffer byteBuffer = out;
            long address = UnsafeUtil.addressOffset(out);
            long outIx3 = ((long) out.position()) + address;
            long outLimit = ((long) out.limit()) + address;
            int inLimit = in.length();
            String str2 = " at index ";
            if (((long) inLimit) <= outLimit - outIx3) {
                int inIx = 0;
                while (true) {
                    c = 128;
                    j = 1;
                    if (inIx >= inLimit) {
                        break;
                    }
                    char charAt = charSequence.charAt(inIx);
                    char c2 = charAt;
                    if (charAt >= 128) {
                        break;
                    }
                    UnsafeUtil.putByte(outIx, (byte) c2);
                    inIx++;
                    outIx3 = 1 + outIx;
                }
                if (inIx == inLimit) {
                    byteBuffer.position((int) (outIx - address));
                    return;
                }
                while (inIx < inLimit) {
                    char c3 = charSequence.charAt(inIx);
                    if (c3 < c && outIx < outLimit) {
                        long outIx4 = outIx + j;
                        UnsafeUtil.putByte(outIx, (byte) c3);
                        j2 = j;
                        str = str2;
                        outIx = outIx4;
                        outIx2 = address;
                    } else if (c3 >= 2048 || outIx > outLimit - 2) {
                        outIx2 = address;
                        if ((c3 < 55296 || 57343 < c3) && outIx <= outLimit - 3) {
                            long outIx5 = outIx + j;
                            UnsafeUtil.putByte(outIx, (byte) ((c3 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE));
                            long outIx6 = outIx5 + j;
                            UnsafeUtil.putByte(outIx5, (byte) (((c3 >>> 6) & 63) | 128));
                            UnsafeUtil.putByte(outIx6, (byte) ((c3 & '?') | 128));
                            outIx = outIx6 + j;
                            j2 = j;
                            str = str2;
                        } else if (outIx <= outLimit - 4) {
                            if (inIx + 1 != inLimit) {
                                inIx++;
                                char charAt2 = charSequence.charAt(inIx);
                                char low = charAt2;
                                if (Character.isSurrogatePair(c3, charAt2)) {
                                    int codePoint = Character.toCodePoint(c3, low);
                                    long outIx7 = outIx + j;
                                    UnsafeUtil.putByte(outIx, (byte) ((codePoint >>> 18) | 240));
                                    long outIx8 = outIx7 + 1;
                                    UnsafeUtil.putByte(outIx7, (byte) (((codePoint >>> 12) & 63) | 128));
                                    str = str2;
                                    long outIx9 = outIx8 + 1;
                                    UnsafeUtil.putByte(outIx8, (byte) (((codePoint >>> 6) & 63) | 128));
                                    j2 = 1;
                                    outIx = outIx9 + 1;
                                    UnsafeUtil.putByte(outIx9, (byte) ((codePoint & 63) | 128));
                                }
                            }
                            throw new UnpairedSurrogateException(inIx - 1, inLimit);
                        } else {
                            String str3 = str2;
                            if (55296 > c3 || c3 > 57343 || (inIx + 1 != inLimit && Character.isSurrogatePair(c3, charSequence.charAt(inIx + 1)))) {
                                StringBuilder sb = new StringBuilder(46);
                                sb.append("Failed writing ");
                                sb.append(c3);
                                sb.append(str3);
                                sb.append(outIx);
                                throw new ArrayIndexOutOfBoundsException(sb.toString());
                            }
                            throw new UnpairedSurrogateException(inIx, inLimit);
                        }
                    } else {
                        outIx2 = address;
                        long outIx10 = outIx + j;
                        UnsafeUtil.putByte(outIx, (byte) ((c3 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE));
                        UnsafeUtil.putByte(outIx10, (byte) ((c3 & '?') | 128));
                        outIx = outIx10 + j;
                        j2 = j;
                        str = str2;
                    }
                    inIx++;
                    str2 = str;
                    address = outIx2;
                    j = j2;
                    c = 128;
                }
                out.position((int) (outIx - address));
                return;
            }
            char charAt3 = charSequence.charAt(inLimit - 1);
            int limit = out.limit();
            StringBuilder sb2 = new StringBuilder(37);
            sb2.append("Failed writing ");
            sb2.append(charAt3);
            sb2.append(str2);
            sb2.append(limit);
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bytes, long offset, int maxChars) {
            if (maxChars < 16) {
                return 0;
            }
            int i = 0;
            while (i < maxChars) {
                long offset2 = 1 + offset;
                if (UnsafeUtil.getByte(bytes, offset) < 0) {
                    return i;
                }
                i++;
                offset = offset2;
            }
            return maxChars;
        }

        private static int unsafeEstimateConsecutiveAscii(long address, int maxChars) {
            int remaining = maxChars;
            if (remaining < 16) {
                return 0;
            }
            int unaligned = 8 - (((int) address) & 7);
            int j = unaligned;
            while (j > 0) {
                long address2 = 1 + address;
                if (UnsafeUtil.getByte(address) < 0) {
                    return unaligned - j;
                }
                j--;
                address = address2;
            }
            int remaining2 = remaining - unaligned;
            while (remaining2 >= 8 && (UnsafeUtil.getLong(address) & Utf8.ASCII_MASK_LONG) == 0) {
                address += 8;
                remaining2 -= 8;
            }
            return maxChars - remaining2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x006a, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(byte[] r11, long r12, int r14) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r11, r12, r14)
                int r14 = r14 - r0
                long r1 = (long) r0
                long r12 = r12 + r1
            L_0x0007:
                r1 = 0
            L_0x0008:
                r2 = 1
                if (r14 <= 0) goto L_0x001a
                long r4 = r12 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r12)
                r1 = r12
                if (r12 < 0) goto L_0x0019
                int r14 = r14 + -1
                r12 = r4
                goto L_0x0008
            L_0x0019:
                r12 = r4
            L_0x001a:
                if (r14 != 0) goto L_0x001e
                r2 = 0
                return r2
            L_0x001e:
                int r14 = r14 + -1
                r4 = -32
                r5 = -65
                r6 = -1
                if (r1 >= r4) goto L_0x003c
                if (r14 != 0) goto L_0x002a
                return r1
            L_0x002a:
                int r14 = r14 + -1
                r4 = -62
                if (r1 < r4) goto L_0x003b
                long r2 = r2 + r12
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r12)
                if (r12 <= r5) goto L_0x0039
                r12 = r2
                goto L_0x003b
            L_0x0039:
                r12 = r2
                goto L_0x0099
            L_0x003b:
                return r6
            L_0x003c:
                r7 = -16
                if (r1 >= r7) goto L_0x006b
                r7 = 2
                if (r14 >= r7) goto L_0x0048
                int r2 = unsafeIncompleteStateFor(r11, r1, r12, r14)
                return r2
            L_0x0048:
                int r14 = r14 + -2
                long r7 = r12 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r12)
                r13 = r12
                if (r12 > r5) goto L_0x0069
                r12 = -96
                if (r1 != r4) goto L_0x0059
                if (r13 < r12) goto L_0x0069
            L_0x0059:
                r4 = -19
                if (r1 != r4) goto L_0x005f
                if (r13 >= r12) goto L_0x0069
            L_0x005f:
                long r2 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r7)
                if (r12 <= r5) goto L_0x0067
                goto L_0x006a
            L_0x0067:
                r12 = r2
                goto L_0x0099
            L_0x0069:
                r2 = r7
            L_0x006a:
                return r6
            L_0x006b:
                r4 = 3
                if (r14 >= r4) goto L_0x0073
                int r2 = unsafeIncompleteStateFor(r11, r1, r12, r14)
                return r2
            L_0x0073:
                int r14 = r14 + -3
                long r7 = r12 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r12)
                r13 = r12
                if (r12 > r5) goto L_0x009c
                int r12 = r1 << 28
                int r4 = r13 + 112
                int r12 = r12 + r4
                int r12 = r12 >> 30
                if (r12 != 0) goto L_0x009c
                long r9 = r7 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r7)
                if (r12 > r5) goto L_0x009b
                long r7 = r9 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r11, r9)
                if (r12 <= r5) goto L_0x0098
                goto L_0x009c
            L_0x0098:
                r12 = r7
            L_0x0099:
                goto L_0x0007
            L_0x009b:
                r7 = r9
            L_0x009c:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(byte[], long, int):int");
        }

        /* JADX INFO: Multiple debug info for r11v6 byte: [D('byte2' byte), D('address' long)] */
        /* JADX INFO: Multiple debug info for r11v8 byte: [D('byte2' byte), D('address' long)] */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0069, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(long r11, int r13) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r11, r13)
                long r1 = (long) r0
                long r11 = r11 + r1
                int r13 = r13 - r0
            L_0x0007:
                r1 = 0
            L_0x0008:
                r2 = 1
                if (r13 <= 0) goto L_0x001a
                long r4 = r11 + r2
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r11)
                r1 = r11
                if (r11 < 0) goto L_0x0019
                int r13 = r13 + -1
                r11 = r4
                goto L_0x0008
            L_0x0019:
                r11 = r4
            L_0x001a:
                if (r13 != 0) goto L_0x001e
                r2 = 0
                return r2
            L_0x001e:
                int r13 = r13 + -1
                r4 = -32
                r5 = -65
                r6 = -1
                if (r1 >= r4) goto L_0x003c
                if (r13 != 0) goto L_0x002a
                return r1
            L_0x002a:
                int r13 = r13 + -1
                r4 = -62
                if (r1 < r4) goto L_0x003b
                long r2 = r2 + r11
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r11)
                if (r11 <= r5) goto L_0x0039
                r11 = r2
                goto L_0x003b
            L_0x0039:
                r11 = r2
                goto L_0x0097
            L_0x003b:
                return r6
            L_0x003c:
                r7 = -16
                if (r1 >= r7) goto L_0x006a
                r7 = 2
                if (r13 >= r7) goto L_0x0048
                int r2 = unsafeIncompleteStateFor(r11, r1, r13)
                return r2
            L_0x0048:
                int r13 = r13 + -2
                long r7 = r11 + r2
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r11)
                if (r11 > r5) goto L_0x0068
                r12 = -96
                if (r1 != r4) goto L_0x0058
                if (r11 < r12) goto L_0x0068
            L_0x0058:
                r4 = -19
                if (r1 != r4) goto L_0x005e
                if (r11 >= r12) goto L_0x0068
            L_0x005e:
                long r2 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r7)
                if (r12 <= r5) goto L_0x0066
                goto L_0x0069
            L_0x0066:
                r11 = r2
                goto L_0x0097
            L_0x0068:
                r2 = r7
            L_0x0069:
                return r6
            L_0x006a:
                r4 = 3
                if (r13 >= r4) goto L_0x0072
                int r2 = unsafeIncompleteStateFor(r11, r1, r13)
                return r2
            L_0x0072:
                int r13 = r13 + -3
                long r7 = r11 + r2
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r11)
                if (r11 > r5) goto L_0x009a
                int r12 = r1 << 28
                int r4 = r11 + 112
                int r12 = r12 + r4
                int r12 = r12 >> 30
                if (r12 != 0) goto L_0x009a
                long r9 = r7 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r7)
                if (r12 > r5) goto L_0x0099
                long r7 = r9 + r2
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r9)
                if (r12 <= r5) goto L_0x0096
                goto L_0x009a
            L_0x0096:
                r11 = r7
            L_0x0097:
                goto L_0x0007
            L_0x0099:
                r7 = r9
            L_0x009a:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(long, int):int");
        }

        private static int unsafeIncompleteStateFor(byte[] bytes, int byte1, long offset, int remaining) {
            if (remaining == 0) {
                return Utf8.incompleteStateFor(byte1);
            }
            if (remaining == 1) {
                return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(bytes, offset));
            }
            if (remaining == 2) {
                return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(bytes, offset), UnsafeUtil.getByte(bytes, 1 + offset));
            }
            throw new AssertionError();
        }

        private static int unsafeIncompleteStateFor(long address, int byte1, int remaining) {
            if (remaining == 0) {
                return Utf8.incompleteStateFor(byte1);
            }
            if (remaining == 1) {
                return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(address));
            }
            if (remaining == 2) {
                return Utf8.incompleteStateFor(byte1, UnsafeUtil.getByte(address), UnsafeUtil.getByte(1 + address));
            }
            throw new AssertionError();
        }
    }

    private static class DecodeUtil {
        private DecodeUtil() {
        }

        /* access modifiers changed from: private */
        public static boolean isOneByte(byte b) {
            return b >= 0;
        }

        /* access modifiers changed from: private */
        public static boolean isTwoBytes(byte b) {
            return b < -32;
        }

        /* access modifiers changed from: private */
        public static boolean isThreeBytes(byte b) {
            return b < -16;
        }

        /* access modifiers changed from: private */
        public static void handleOneByte(byte byte1, char[] resultArr, int resultPos) {
            resultArr[resultPos] = (char) byte1;
        }

        /* access modifiers changed from: private */
        public static void handleTwoBytes(byte byte1, byte byte2, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
            if (byte1 < -62 || isNotTrailingByte(byte2)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            resultArr[resultPos] = (char) (((byte1 & Ascii.f159US) << 6) | trailingByteValue(byte2));
        }

        /* access modifiers changed from: private */
        public static void handleThreeBytes(byte byte1, byte byte2, byte byte3, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(byte2) || ((byte1 == -32 && byte2 < -96) || ((byte1 == -19 && byte2 >= -96) || isNotTrailingByte(byte3)))) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            resultArr[resultPos] = (char) (((byte1 & Ascii.f156SI) << Ascii.f149FF) | (trailingByteValue(byte2) << 6) | trailingByteValue(byte3));
        }

        /* access modifiers changed from: private */
        public static void handleFourBytes(byte byte1, byte byte2, byte byte3, byte byte4, char[] resultArr, int resultPos) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(byte2) || (((byte1 << Ascii.f150FS) + (byte2 + 112)) >> 30) != 0 || isNotTrailingByte(byte3) || isNotTrailingByte(byte4)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            int codepoint = ((byte1 & 7) << Ascii.DC2) | (trailingByteValue(byte2) << 12) | (trailingByteValue(byte3) << 6) | trailingByteValue(byte4);
            resultArr[resultPos] = highSurrogate(codepoint);
            resultArr[resultPos + 1] = lowSurrogate(codepoint);
        }

        private static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }

        private static int trailingByteValue(byte b) {
            return b & 63;
        }

        private static char highSurrogate(int codePoint) {
            return (char) ((codePoint >>> 10) + 55232);
        }

        private static char lowSurrogate(int codePoint) {
            return (char) ((codePoint & ClientAnalytics.LogRequest.LogSource.G_SUITE_ADD_ONS_COUNTERS_VALUE) + 56320);
        }
    }

    private Utf8() {
    }
}
