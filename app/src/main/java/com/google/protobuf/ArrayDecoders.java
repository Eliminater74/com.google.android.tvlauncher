package com.google.protobuf;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;

final class ArrayDecoders {
    ArrayDecoders() {
    }

    /* JADX INFO: Multiple debug info for r3v1 byte: [D('value' int), D('position' int)] */
    static int decodeVarint32(byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        byte b = data[position];
        if (b < 0) {
            return decodeVarint32(b, data, position2, registers);
        }
        registers.int1 = b;
        return position2;
    }

    /* JADX INFO: Multiple debug info for r8v1 byte: [D('b2' byte), D('position' int)] */
    /* JADX INFO: Multiple debug info for r1v1 byte: [D('b3' byte), D('position' int)] */
    /* JADX INFO: Multiple debug info for r2v3 byte: [D('position' int), D('b4' byte)] */
    /* JADX INFO: Multiple debug info for r3v3 byte: [D('position' int), D('b5' byte)] */
    static int decodeVarint32(int firstByte, byte[] data, int position, Registers registers) {
        int value = firstByte & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        int position2 = position + 1;
        byte b2 = data[position];
        if (b2 >= 0) {
            registers.int1 = (b2 << 7) | value;
            return position2;
        }
        int value2 = value | ((b2 & Ascii.DEL) << 7);
        int position3 = position2 + 1;
        byte b3 = data[position2];
        if (b3 >= 0) {
            registers.int1 = (b3 << Ascii.f157SO) | value2;
            return position3;
        }
        int value3 = value2 | ((b3 & Ascii.DEL) << Ascii.f157SO);
        int position4 = position3 + 1;
        byte b4 = data[position3];
        if (b4 >= 0) {
            registers.int1 = (b4 << Ascii.NAK) | value3;
            return position4;
        }
        int value4 = value3 | ((b4 & Ascii.DEL) << Ascii.NAK);
        int position5 = position4 + 1;
        byte b5 = data[position4];
        if (b5 >= 0) {
            registers.int1 = (b5 << Ascii.f150FS) | value4;
            return position5;
        }
        int value5 = value4 | ((b5 & Ascii.DEL) << Ascii.f150FS);
        while (true) {
            int position6 = position5 + 1;
            if (data[position5] < 0) {
                position5 = position6;
            } else {
                registers.int1 = value5;
                return position6;
            }
        }
    }

    static int decodeVarint64(byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        long value = (long) data[position];
        if (value < 0) {
            return decodeVarint64(value, data, position2, registers);
        }
        registers.long1 = value;
        return position2;
    }

    /* JADX INFO: Multiple debug info for r10v1 byte: [D('position' int), D('next' byte)] */
    static int decodeVarint64(long firstByte, byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        byte next = data[position];
        int shift = 7;
        long value = (127 & firstByte) | (((long) (next & Ascii.DEL)) << 7);
        while (next < 0) {
            next = data[position2];
            shift += 7;
            value |= ((long) (next & Ascii.DEL)) << shift;
            position2++;
        }
        registers.long1 = value;
        return position2;
    }

    static int decodeFixed32(byte[] data, int position) {
        return (data[position] & UnsignedBytes.MAX_VALUE) | ((data[position + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((data[position + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((data[position + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
    }

    static long decodeFixed64(byte[] data, int position) {
        return (((long) data[position]) & 255) | ((((long) data[position + 1]) & 255) << 8) | ((((long) data[position + 2]) & 255) << 16) | ((((long) data[position + 3]) & 255) << 24) | ((((long) data[position + 4]) & 255) << 32) | ((((long) data[position + 5]) & 255) << 40) | ((((long) data[position + 6]) & 255) << 48) | ((255 & ((long) data[position + 7])) << 56);
    }

    static double decodeDouble(byte[] data, int position) {
        return Double.longBitsToDouble(decodeFixed64(data, position));
    }

    static float decodeFloat(byte[] data, int position) {
        return Float.intBitsToFloat(decodeFixed32(data, position));
    }

    static int decodeString(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length == 0) {
            registers.object1 = "";
            return position2;
        } else {
            registers.object1 = new String(data, position2, length, Internal.UTF_8);
            return position2 + length;
        }
    }

    static int decodeStringRequireUtf8(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length == 0) {
            registers.object1 = "";
            return position2;
        } else {
            registers.object1 = Utf8.decodeUtf8(data, position2, length);
            return position2 + length;
        }
    }

    static int decodeBytes(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length > data.length - position2) {
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (length == 0) {
            registers.object1 = ByteString.EMPTY;
            return position2;
        } else {
            registers.object1 = ByteString.copyFrom(data, position2, length);
            return position2 + length;
        }
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX INFO: Multiple debug info for r10v1 byte: [D('length' int), D('position' int)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int decodeMessageField(com.google.protobuf.Schema r8, byte[] r9, int r10, int r11, com.google.protobuf.ArrayDecoders.Registers r12) throws java.io.IOException {
        /*
            int r0 = r10 + 1
            byte r10 = r9[r10]
            if (r10 >= 0) goto L_0x000e
            int r0 = decodeVarint32(r10, r9, r0, r12)
            int r10 = r12.int1
            r6 = r0
            goto L_0x000f
        L_0x000e:
            r6 = r0
        L_0x000f:
            if (r10 < 0) goto L_0x002b
            int r0 = r11 - r6
            if (r10 > r0) goto L_0x002b
            java.lang.Object r7 = r8.newInstance()
            int r4 = r6 + r10
            r0 = r8
            r1 = r7
            r2 = r9
            r3 = r6
            r5 = r12
            r0.mergeFrom(r1, r2, r3, r4, r5)
            r8.makeImmutable(r7)
            r12.object1 = r7
            int r0 = r6 + r10
            return r0
        L_0x002b:
            com.google.protobuf.InvalidProtocolBufferException r0 = com.google.protobuf.InvalidProtocolBufferException.truncatedMessage()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.ArrayDecoders.decodeMessageField(com.google.protobuf.Schema, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    static int decodeGroupField(Schema schema, byte[] data, int position, int limit, int endGroup, Registers registers) throws IOException {
        MessageSchema messageSchema = (MessageSchema) schema;
        Object result = messageSchema.newInstance();
        int endPosition = messageSchema.parseProto2Message(result, data, position, limit, endGroup, registers);
        messageSchema.makeImmutable(result);
        registers.object1 = result;
        return endPosition;
    }

    static int decodeVarint32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        output.addInt(registers.int1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint32(data, nextPosition, registers);
            output.addInt(registers.int1);
        }
        return position2;
    }

    static int decodeVarint64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addLong(registers.long1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addLong(registers.long1);
        }
        return position2;
    }

    static int decodeFixed32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        output.addInt(decodeFixed32(data, position));
        int position2 = position + 4;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addInt(decodeFixed32(data, nextPosition));
            position2 = nextPosition + 4;
        }
        return position2;
    }

    static int decodeFixed64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        output.addLong(decodeFixed64(data, position));
        int position2 = position + 8;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addLong(decodeFixed64(data, nextPosition));
            position2 = nextPosition + 8;
        }
        return position2;
    }

    static int decodeFloatList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        FloatArrayList output = (FloatArrayList) list;
        output.addFloat(decodeFloat(data, position));
        int position2 = position + 4;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addFloat(decodeFloat(data, nextPosition));
            position2 = nextPosition + 4;
        }
        return position2;
    }

    static int decodeDoubleList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        DoubleArrayList output = (DoubleArrayList) list;
        output.addDouble(decodeDouble(data, position));
        int position2 = position + 8;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addDouble(decodeDouble(data, nextPosition));
            position2 = nextPosition + 8;
        }
        return position2;
    }

    static int decodeBoolList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        BooleanArrayList output = (BooleanArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addBoolean(registers.long1 != 0);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addBoolean(registers.long1 != 0);
        }
        return position2;
    }

    static int decodeSInt32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint32(data, nextPosition, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        return position2;
    }

    static int decodeSInt64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        return position2;
    }

    static int decodePackedVarint32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint32(data, position2, registers);
            output.addInt(registers.int1);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedVarint64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addLong(registers.long1);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addInt(decodeFixed32(data, position2));
            position2 += 4;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addLong(decodeFixed64(data, position2));
            position2 += 8;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFloatList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        FloatArrayList output = (FloatArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addFloat(decodeFloat(data, position2));
            position2 += 4;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedDoubleList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        DoubleArrayList output = (DoubleArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addDouble(decodeDouble(data, position2));
            position2 += 8;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedBoolList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        BooleanArrayList output = (BooleanArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addBoolean(registers.long1 != 0);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint32(data, position2, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodeStringList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length >= 0) {
            if (length == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new String(data, position3, length, Internal.UTF_8));
                position3 += length;
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength == 0) {
                    protobufList.add("");
                } else {
                    protobufList.add(new String(data, position2, nextLength, Internal.UTF_8));
                    position2 += nextLength;
                }
            }
            return position2;
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    static int decodeStringListRequireUtf8(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length >= 0) {
            if (length == 0) {
                protobufList.add("");
            } else if (Utf8.isValidUtf8(data, position3, position3 + length)) {
                protobufList.add(new String(data, position3, length, Internal.UTF_8));
                position3 += length;
            } else {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength == 0) {
                    protobufList.add("");
                } else if (Utf8.isValidUtf8(data, position2, position2 + nextLength)) {
                    protobufList.add(new String(data, position2, nextLength, Internal.UTF_8));
                    position2 += nextLength;
                } else {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
            }
            return position2;
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    static int decodeBytesList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length <= data.length - position3) {
            if (length == 0) {
                protobufList.add(ByteString.EMPTY);
            } else {
                protobufList.add(ByteString.copyFrom(data, position3, length));
                position3 += length;
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength > data.length - position2) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                } else if (nextLength == 0) {
                    protobufList.add(ByteString.EMPTY);
                } else {
                    protobufList.add(ByteString.copyFrom(data, position2, nextLength));
                    position2 += nextLength;
                }
            }
            return position2;
        } else {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    static int decodeMessageList(Schema<?> schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        Internal.ProtobufList<Object> output = list;
        int position2 = decodeMessageField(schema, data, position, limit, registers);
        output.add(registers.object1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeMessageField(schema, data, nextPosition, limit, registers);
            output.add(registers.object1);
        }
        return position2;
    }

    static int decodeGroupList(Schema schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        Internal.ProtobufList<Object> output = list;
        int endgroup = (tag & -8) | 4;
        int position2 = decodeGroupField(schema, data, position, limit, endgroup, registers);
        output.add(registers.object1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeGroupField(schema, data, nextPosition, limit, endgroup, registers);
            output.add(registers.object1);
        }
        return position2;
    }

    static int decodeExtensionOrUnknownField(int tag, byte[] data, int position, int limit, Object message, MessageLite defaultInstance, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        GeneratedMessageLite.GeneratedExtension extension = registers.extensionRegistry.findLiteExtensionByNumber(defaultInstance, tag >>> 3);
        if (extension == null) {
            return decodeUnknownField(tag, data, position, limit, MessageSchema.getMutableUnknownFields(message), registers);
        }
        ((GeneratedMessageLite.ExtendableMessage) message).ensureExtensionsAreMutable();
        return decodeExtension(tag, data, position, limit, (GeneratedMessageLite.ExtendableMessage) message, extension, unknownFieldSchema, registers);
    }

    static int decodeExtension(int tag, byte[] data, int position, int limit, GeneratedMessageLite.ExtendableMessage<?, ?> message, GeneratedMessageLite.GeneratedExtension<?, ?> extension, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        int position2;
        Object oldValue;
        byte[] bArr = data;
        int i = position;
        GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage = message;
        GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension = extension;
        UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema2 = unknownFieldSchema;
        Registers registers2 = registers;
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = extendableMessage.extensions;
        int fieldNumber = tag >>> 3;
        if (!generatedExtension.descriptor.isRepeated() || !generatedExtension.descriptor.isPacked()) {
            Object value = null;
            if (extension.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (extension.getLiteType()) {
                    case DOUBLE:
                        value = Double.valueOf(decodeDouble(data, position));
                        position2 = i + 8;
                        break;
                    case FLOAT:
                        value = Float.valueOf(decodeFloat(data, position));
                        position2 = i + 4;
                        break;
                    case INT64:
                    case UINT64:
                        int position3 = decodeVarint64(bArr, i, registers2);
                        value = Long.valueOf(registers2.long1);
                        position2 = position3;
                        break;
                    case INT32:
                    case UINT32:
                        int position4 = decodeVarint32(bArr, i, registers2);
                        value = Integer.valueOf(registers2.int1);
                        position2 = position4;
                        break;
                    case FIXED64:
                    case SFIXED64:
                        value = Long.valueOf(decodeFixed64(data, position));
                        position2 = i + 8;
                        break;
                    case FIXED32:
                    case SFIXED32:
                        value = Integer.valueOf(decodeFixed32(data, position));
                        position2 = i + 4;
                        break;
                    case BOOL:
                        int position5 = decodeVarint64(bArr, i, registers2);
                        value = Boolean.valueOf(registers2.long1 != 0);
                        position2 = position5;
                        break;
                    case SINT32:
                        int position6 = decodeVarint32(bArr, i, registers2);
                        value = Integer.valueOf(CodedInputStream.decodeZigZag32(registers2.int1));
                        position2 = position6;
                        break;
                    case SINT64:
                        int position7 = decodeVarint64(bArr, i, registers2);
                        value = Long.valueOf(CodedInputStream.decodeZigZag64(registers2.long1));
                        position2 = position7;
                        break;
                    case ENUM:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case BYTES:
                        int position8 = decodeBytes(bArr, i, registers2);
                        value = registers2.object1;
                        position2 = position8;
                        break;
                    case STRING:
                        int position9 = decodeString(bArr, i, registers2);
                        value = registers2.object1;
                        position2 = position9;
                        break;
                    case GROUP:
                        int position10 = decodeGroupField(Protobuf.getInstance().schemaFor((Class) extension.getMessageDefaultInstance().getClass()), data, position, limit, (fieldNumber << 3) | 4, registers);
                        value = registers2.object1;
                        position2 = position10;
                        break;
                    case MESSAGE:
                        int position11 = decodeMessageField(Protobuf.getInstance().schemaFor((Class) extension.getMessageDefaultInstance().getClass()), bArr, i, limit, registers2);
                        value = registers2.object1;
                        position2 = position11;
                        break;
                    default:
                        position2 = i;
                        break;
                }
            } else {
                int position12 = decodeVarint32(bArr, i, registers2);
                if (generatedExtension.descriptor.getEnumType().findValueByNumber(registers2.int1) == null) {
                    UnknownFieldSetLite unknownFields = extendableMessage.unknownFields;
                    if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
                        unknownFields = UnknownFieldSetLite.newInstance();
                        extendableMessage.unknownFields = unknownFields;
                    }
                    SchemaUtil.storeUnknownEnum(fieldNumber, registers2.int1, unknownFields, unknownFieldSchema2);
                    return position12;
                }
                value = Integer.valueOf(registers2.int1);
                position2 = position12;
            }
            if (extension.isRepeated() != 0) {
                extensions.addRepeatedField(generatedExtension.descriptor, value);
                return position2;
            }
            int i2 = C18331.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extension.getLiteType().ordinal()];
            if ((i2 == 17 || i2 == 18) && (oldValue = extensions.getField(generatedExtension.descriptor)) != null) {
                value = Internal.mergeMessage(oldValue, value);
            }
            extensions.setField(generatedExtension.descriptor, value);
            return position2;
        }
        switch (extension.getLiteType()) {
            case DOUBLE:
                DoubleArrayList list = new DoubleArrayList();
                int position13 = decodePackedDoubleList(bArr, i, list, registers2);
                extensions.setField(generatedExtension.descriptor, list);
                return position13;
            case FLOAT:
                FloatArrayList list2 = new FloatArrayList();
                int position14 = decodePackedFloatList(bArr, i, list2, registers2);
                extensions.setField(generatedExtension.descriptor, list2);
                return position14;
            case INT64:
            case UINT64:
                LongArrayList list3 = new LongArrayList();
                int position15 = decodePackedVarint64List(bArr, i, list3, registers2);
                extensions.setField(generatedExtension.descriptor, list3);
                return position15;
            case INT32:
            case UINT32:
                IntArrayList list4 = new IntArrayList();
                int position16 = decodePackedVarint32List(bArr, i, list4, registers2);
                extensions.setField(generatedExtension.descriptor, list4);
                return position16;
            case FIXED64:
            case SFIXED64:
                LongArrayList list5 = new LongArrayList();
                int position17 = decodePackedFixed64List(bArr, i, list5, registers2);
                extensions.setField(generatedExtension.descriptor, list5);
                return position17;
            case FIXED32:
            case SFIXED32:
                IntArrayList list6 = new IntArrayList();
                int position18 = decodePackedFixed32List(bArr, i, list6, registers2);
                extensions.setField(generatedExtension.descriptor, list6);
                return position18;
            case BOOL:
                BooleanArrayList list7 = new BooleanArrayList();
                int position19 = decodePackedBoolList(bArr, i, list7, registers2);
                extensions.setField(generatedExtension.descriptor, list7);
                return position19;
            case SINT32:
                IntArrayList list8 = new IntArrayList();
                int position20 = decodePackedSInt32List(bArr, i, list8, registers2);
                extensions.setField(generatedExtension.descriptor, list8);
                return position20;
            case SINT64:
                LongArrayList list9 = new LongArrayList();
                int position21 = decodePackedSInt64List(bArr, i, list9, registers2);
                extensions.setField(generatedExtension.descriptor, list9);
                return position21;
            case ENUM:
                IntArrayList list10 = new IntArrayList();
                int position22 = decodePackedVarint32List(bArr, i, list10, registers2);
                UnknownFieldSetLite unknownFields2 = extendableMessage.unknownFields;
                if (unknownFields2 == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFields2 = null;
                }
                UnknownFieldSetLite unknownFields3 = (UnknownFieldSetLite) SchemaUtil.filterUnknownEnumList(fieldNumber, list10, generatedExtension.descriptor.getEnumType(), unknownFields2, unknownFieldSchema2);
                if (unknownFields3 != null) {
                    extendableMessage.unknownFields = unknownFields3;
                }
                extensions.setField(generatedExtension.descriptor, list10);
                return position22;
            default:
                String valueOf = String.valueOf(generatedExtension.descriptor.getLiteType());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
                sb.append("Type cannot be packed: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
        }
    }

    static int decodeUnknownField(int tag, byte[] data, int position, int limit, UnknownFieldSetLite unknownFields, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(tag) != 0) {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                int position2 = decodeVarint64(data, position, registers);
                unknownFields.storeField(tag, Long.valueOf(registers.long1));
                return position2;
            } else if (tagWireType == 1) {
                unknownFields.storeField(tag, Long.valueOf(decodeFixed64(data, position)));
                return position + 8;
            } else if (tagWireType == 2) {
                int position3 = decodeVarint32(data, position, registers);
                int length = registers.int1;
                if (length < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (length <= data.length - position3) {
                    if (length == 0) {
                        unknownFields.storeField(tag, ByteString.EMPTY);
                    } else {
                        unknownFields.storeField(tag, ByteString.copyFrom(data, position3, length));
                    }
                    return position3 + length;
                } else {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } else if (tagWireType == 3) {
                UnknownFieldSetLite child = UnknownFieldSetLite.newInstance();
                int endGroup = (tag & -8) | 4;
                int lastTag = 0;
                while (true) {
                    if (position >= limit) {
                        break;
                    }
                    position = decodeVarint32(data, position, registers);
                    int lastTag2 = registers.int1;
                    if (lastTag2 == endGroup) {
                        lastTag = lastTag2;
                        break;
                    }
                    lastTag = lastTag2;
                    position = decodeUnknownField(lastTag, data, position, limit, child, registers);
                }
                if (position > limit || lastTag != endGroup) {
                    throw InvalidProtocolBufferException.parseFailure();
                }
                unknownFields.storeField(tag, child);
                return position;
            } else if (tagWireType == 5) {
                unknownFields.storeField(tag, Integer.valueOf(decodeFixed32(data, position)));
                return position + 4;
            } else {
                throw InvalidProtocolBufferException.invalidTag();
            }
        } else {
            throw InvalidProtocolBufferException.invalidTag();
        }
    }

    static int skipField(int tag, byte[] data, int position, int limit, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(tag) != 0) {
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType == 0) {
                return decodeVarint64(data, position, registers);
            }
            if (tagWireType == 1) {
                return position + 8;
            }
            if (tagWireType == 2) {
                return registers.int1 + decodeVarint32(data, position, registers);
            } else if (tagWireType == 3) {
                int endGroup = (tag & -8) | 4;
                int lastTag = 0;
                while (position < limit) {
                    position = decodeVarint32(data, position, registers);
                    lastTag = registers.int1;
                    if (lastTag == endGroup) {
                        break;
                    }
                    position = skipField(lastTag, data, position, limit, registers);
                }
                if (position <= limit && lastTag == endGroup) {
                    return position;
                }
                throw InvalidProtocolBufferException.parseFailure();
            } else if (tagWireType == 5) {
                return position + 4;
            } else {
                throw InvalidProtocolBufferException.invalidTag();
            }
        } else {
            throw InvalidProtocolBufferException.invalidTag();
        }
    }

    static final class Registers {
        public final ExtensionRegistryLite extensionRegistry;
        public int int1;
        public long long1;
        public Object object1;

        Registers() {
            this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
        }

        Registers(ExtensionRegistryLite extensionRegistry2) {
            if (extensionRegistry2 != null) {
                this.extensionRegistry = extensionRegistry2;
                return;
            }
            throw new NullPointerException();
        }
    }
}
