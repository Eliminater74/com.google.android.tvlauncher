package com.google.protobuf;

import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ByteString;
import com.google.protobuf.Internal;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.google.protobuf.Writer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

final class MessageSchema<T> implements Schema<T> {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int INTS_PER_FIELD = 3;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final int REQUIRED_MASK = 268435456;
    private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();
    private final int[] buffer;
    private final int checkInitializedCount;
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final NewInstanceSchema newInstanceSchema;
    private final Object[] objects;
    private final boolean proto3;
    private final int repeatedFieldOffsetStart;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;

    private MessageSchema(int[] buffer2, Object[] objects2, int minFieldNumber2, int maxFieldNumber2, MessageLite defaultInstance2, boolean proto32, boolean useCachedSizeField2, int[] intArray2, int checkInitialized, int mapFieldPositions, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        MessageLite messageLite = defaultInstance2;
        ExtensionSchema<?> extensionSchema3 = extensionSchema2;
        this.buffer = buffer2;
        this.objects = objects2;
        this.minFieldNumber = minFieldNumber2;
        this.maxFieldNumber = maxFieldNumber2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.proto3 = proto32;
        this.hasExtensions = extensionSchema3 != null && extensionSchema3.hasExtensions(messageLite);
        this.useCachedSizeField = useCachedSizeField2;
        this.intArray = intArray2;
        this.checkInitializedCount = checkInitialized;
        this.repeatedFieldOffsetStart = mapFieldPositions;
        this.newInstanceSchema = newInstanceSchema2;
        this.listFieldSchema = listFieldSchema2;
        this.unknownFieldSchema = unknownFieldSchema2;
        this.extensionSchema = extensionSchema3;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema2;
    }

    static <T> MessageSchema<T> newSchema(Class<T> cls, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        if (messageInfo instanceof RawMessageInfo) {
            return newSchemaForRawMessageInfo((RawMessageInfo) messageInfo, newInstanceSchema2, listFieldSchema2, unknownFieldSchema2, extensionSchema2, mapFieldSchema2);
        }
        return newSchemaForMessageInfo((StructuralMessageInfo) messageInfo, newInstanceSchema2, listFieldSchema2, unknownFieldSchema2, extensionSchema2, mapFieldSchema2);
    }

    /* JADX INFO: Multiple debug info for r4v1 char: [D('i' int), D('next' int)] */
    /* JADX INFO: Multiple debug info for r14v0 sun.misc.Unsafe: [D('checkInitialized' int), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r7v7 int: [D('fieldNumber' int), D('result' int)] */
    /* JADX INFO: Multiple debug info for r4v9 int: [D('i' int), D('next' int)] */
    /* JADX INFO: Multiple debug info for r1v7 int: [D('length' int), D('objectsPosition' int)] */
    /* JADX INFO: Multiple debug info for r3v4 int: [D('i' int), D('hasBitsIndex' int)] */
    /* JADX INFO: Multiple debug info for r3v5 int: [D('presenceMaskShift' int), D('hasBitsIndex' int)] */
    /* JADX INFO: Multiple debug info for r9v24 int: [D('oneofIndex' int), D('i' int)] */
    /* JADX INFO: Multiple debug info for r1v24 ?: [D('length' int), D('o' java.lang.Object)] */
    /* JADX INFO: Multiple debug info for r7v14 int: [D('result' int), D('oneofCount' int)] */
    /* JADX INFO: Multiple debug info for r8v14 char: [D('i' int), D('hasBitsCount' int)] */
    /* JADX INFO: Multiple debug info for r5v15 int: [D('i' int), D('minFieldNumber' int)] */
    /* JADX INFO: Multiple debug info for r9v46 int: [D('i' int), D('maxFieldNumber' int)] */
    /* JADX INFO: Multiple debug info for r12v3 int: [D('result' int), D('numEntries' int)] */
    /* JADX INFO: Multiple debug info for r11v5 int: [D('mapFieldCount' int), D('result' int)] */
    /* JADX INFO: Multiple debug info for r13v5 char: [D('repeatedFieldCount' int), D('result' int)] */
    /* JADX INFO: Multiple debug info for r14v4 int: [D('checkInitialized' int), D('result' int)] */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> com.google.protobuf.MessageSchema<T> newSchemaForRawMessageInfo(com.google.protobuf.RawMessageInfo r43, com.google.protobuf.NewInstanceSchema r44, com.google.protobuf.ListFieldSchema r45, com.google.protobuf.UnknownFieldSchema<?, ?> r46, com.google.protobuf.ExtensionSchema<?> r47, com.google.protobuf.MapFieldSchema r48) {
        /*
            com.google.protobuf.ProtoSyntax r0 = r43.getSyntax()
            com.google.protobuf.ProtoSyntax r1 = com.google.protobuf.ProtoSyntax.PROTO3
            if (r0 != r1) goto L_0x000a
            r10 = 1
            goto L_0x000b
        L_0x000a:
            r10 = 0
        L_0x000b:
            java.lang.String r0 = r43.getStringInfo()
            int r1 = r0.length()
            r4 = 0
            int r5 = r4 + 1
            char r4 = r0.charAt(r4)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r4 < r6) goto L_0x0039
            r7 = r4 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x0023:
            int r9 = r5 + 1
            char r5 = r0.charAt(r5)
            r4 = r5
            if (r5 < r6) goto L_0x0034
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r8
            r7 = r7 | r5
            int r8 = r8 + 13
            r5 = r9
            goto L_0x0023
        L_0x0034:
            int r5 = r4 << r8
            r4 = r7 | r5
            r5 = r9
        L_0x0039:
            r20 = r4
            int r7 = r5 + 1
            char r4 = r0.charAt(r5)
            if (r4 < r6) goto L_0x005d
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x0047:
            int r9 = r7 + 1
            char r7 = r0.charAt(r7)
            r4 = r7
            if (r7 < r6) goto L_0x0058
            r7 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r8
            r5 = r5 | r7
            int r8 = r8 + 13
            r7 = r9
            goto L_0x0047
        L_0x0058:
            int r7 = r4 << r8
            r4 = r5 | r7
            r7 = r9
        L_0x005d:
            r21 = r4
            if (r21 != 0) goto L_0x0083
            r5 = 0
            r8 = 0
            r9 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            int[] r16 = com.google.protobuf.MessageSchema.EMPTY_INT_ARRAY
            r17 = 0
            r25 = r5
            r26 = r8
            r27 = r9
            r28 = r11
            r2 = r12
            r23 = r13
            r29 = r14
            r22 = r15
            r24 = r16
            r16 = r17
            r15 = r7
            goto L_0x01be
        L_0x0083:
            int r5 = r7 + 1
            char r4 = r0.charAt(r7)
            if (r4 < r6) goto L_0x00a5
            r7 = r4 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x008f:
            int r9 = r5 + 1
            char r5 = r0.charAt(r5)
            r4 = r5
            if (r5 < r6) goto L_0x00a0
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r8
            r7 = r7 | r5
            int r8 = r8 + 13
            r5 = r9
            goto L_0x008f
        L_0x00a0:
            int r5 = r4 << r8
            r4 = r7 | r5
            r5 = r9
        L_0x00a5:
            r7 = r4
            int r8 = r5 + 1
            char r4 = r0.charAt(r5)
            if (r4 < r6) goto L_0x00c8
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x00b2:
            int r11 = r8 + 1
            char r8 = r0.charAt(r8)
            r4 = r8
            if (r8 < r6) goto L_0x00c3
            r8 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r9
            r5 = r5 | r8
            int r9 = r9 + 13
            r8 = r11
            goto L_0x00b2
        L_0x00c3:
            int r8 = r4 << r9
            r4 = r5 | r8
            goto L_0x00c9
        L_0x00c8:
            r11 = r8
        L_0x00c9:
            r8 = r4
            int r5 = r11 + 1
            char r4 = r0.charAt(r11)
            if (r4 < r6) goto L_0x00ec
            r9 = r4 & 8191(0x1fff, float:1.1478E-41)
            r11 = 13
        L_0x00d6:
            int r12 = r5 + 1
            char r5 = r0.charAt(r5)
            r4 = r5
            if (r5 < r6) goto L_0x00e7
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r11
            r9 = r9 | r5
            int r11 = r11 + 13
            r5 = r12
            goto L_0x00d6
        L_0x00e7:
            int r5 = r4 << r11
            r4 = r9 | r5
            goto L_0x00ed
        L_0x00ec:
            r12 = r5
        L_0x00ed:
            r5 = r4
            int r9 = r12 + 1
            char r4 = r0.charAt(r12)
            if (r4 < r6) goto L_0x0110
            r11 = r4 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x00fa:
            int r13 = r9 + 1
            char r9 = r0.charAt(r9)
            r4 = r9
            if (r9 < r6) goto L_0x010b
            r9 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r12
            r11 = r11 | r9
            int r12 = r12 + 13
            r9 = r13
            goto L_0x00fa
        L_0x010b:
            int r9 = r4 << r12
            r4 = r11 | r9
            goto L_0x0111
        L_0x0110:
            r13 = r9
        L_0x0111:
            r9 = r4
            int r11 = r13 + 1
            char r4 = r0.charAt(r13)
            if (r4 < r6) goto L_0x0134
            r12 = r4 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x011e:
            int r14 = r11 + 1
            char r11 = r0.charAt(r11)
            r4 = r11
            if (r11 < r6) goto L_0x012f
            r11 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r11 = r11 << r13
            r12 = r12 | r11
            int r13 = r13 + 13
            r11 = r14
            goto L_0x011e
        L_0x012f:
            int r11 = r4 << r13
            r4 = r12 | r11
            r11 = r14
        L_0x0134:
            r12 = r4
            int r13 = r11 + 1
            char r4 = r0.charAt(r11)
            if (r4 < r6) goto L_0x0157
            r11 = r4 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x0141:
            int r15 = r13 + 1
            char r13 = r0.charAt(r13)
            r4 = r13
            if (r13 < r6) goto L_0x0152
            r13 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r11 = r11 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x0141
        L_0x0152:
            int r13 = r4 << r14
            r4 = r11 | r13
            r13 = r15
        L_0x0157:
            r11 = r4
            int r14 = r13 + 1
            char r4 = r0.charAt(r13)
            if (r4 < r6) goto L_0x017c
            r13 = r4 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x0164:
            int r16 = r14 + 1
            char r14 = r0.charAt(r14)
            r4 = r14
            if (r14 < r6) goto L_0x0176
            r14 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x0164
        L_0x0176:
            int r14 = r4 << r15
            r4 = r13 | r14
            r14 = r16
        L_0x017c:
            r13 = r4
            int r15 = r14 + 1
            char r4 = r0.charAt(r14)
            if (r4 < r6) goto L_0x01a2
            r14 = r4 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x0189:
            int r17 = r15 + 1
            char r15 = r0.charAt(r15)
            r4 = r15
            if (r15 < r6) goto L_0x019c
            r15 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r15 = r15 << r16
            r14 = r14 | r15
            int r16 = r16 + 13
            r15 = r17
            goto L_0x0189
        L_0x019c:
            int r15 = r4 << r16
            r4 = r14 | r15
            r15 = r17
        L_0x01a2:
            r14 = r4
            int r16 = r14 + r11
            int r2 = r16 + r13
            int[] r2 = new int[r2]
            int r16 = r7 * 2
            int r16 = r16 + r8
            r24 = r2
            r27 = r5
            r25 = r7
            r26 = r8
            r28 = r9
            r23 = r11
            r2 = r12
            r29 = r13
            r22 = r14
        L_0x01be:
            sun.misc.Unsafe r14 = com.google.protobuf.MessageSchema.UNSAFE
            java.lang.Object[] r30 = r43.getObjects()
            r5 = 0
            com.google.protobuf.MessageLite r7 = r43.getDefaultInstance()
            java.lang.Class r13 = r7.getClass()
            int r7 = r2 * 3
            int[] r12 = new int[r7]
            int r7 = r2 * 2
            java.lang.Object[] r11 = new java.lang.Object[r7]
            r7 = r22
            int r8 = r22 + r23
            r9 = 0
            r31 = r4
            r33 = r5
            r34 = r7
            r35 = r8
            r36 = r9
            r32 = r16
        L_0x01e6:
            if (r15 >= r1) goto L_0x0425
            int r4 = r15 + 1
            char r5 = r0.charAt(r15)
            if (r5 < r6) goto L_0x020a
            r7 = r5 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x01f4:
            int r9 = r4 + 1
            char r4 = r0.charAt(r4)
            r5 = r4
            if (r4 < r6) goto L_0x0205
            r4 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r4 = r4 << r8
            r7 = r7 | r4
            int r8 = r8 + 13
            r4 = r9
            goto L_0x01f4
        L_0x0205:
            int r4 = r5 << r8
            r5 = r7 | r4
            r4 = r9
        L_0x020a:
            r7 = r5
            int r8 = r4 + 1
            char r4 = r0.charAt(r4)
            if (r4 < r6) goto L_0x022d
            r5 = r4 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0217:
            int r15 = r8 + 1
            char r8 = r0.charAt(r8)
            r4 = r8
            if (r8 < r6) goto L_0x0228
            r8 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r9
            r5 = r5 | r8
            int r9 = r9 + 13
            r8 = r15
            goto L_0x0217
        L_0x0228:
            int r8 = r4 << r9
            r4 = r5 | r8
            goto L_0x022e
        L_0x022d:
            r15 = r8
        L_0x022e:
            r5 = r4
            r8 = r5 & 255(0xff, float:3.57E-43)
            r9 = r5 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L_0x023b
            int r9 = r33 + 1
            r24[r33] = r36
            r33 = r9
        L_0x023b:
            r9 = 51
            r3 = 17
            if (r8 < r9) goto L_0x02e2
            int r9 = r15 + 1
            char r4 = r0.charAt(r15)
            if (r4 < r6) goto L_0x0265
            r15 = r4 & 8191(0x1fff, float:1.1478E-41)
            r31 = 13
        L_0x024d:
            int r37 = r9 + 1
            char r9 = r0.charAt(r9)
            r4 = r9
            if (r9 < r6) goto L_0x0260
            r9 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r31
            r15 = r15 | r9
            int r31 = r31 + 13
            r9 = r37
            goto L_0x024d
        L_0x0260:
            int r9 = r4 << r31
            r4 = r15 | r9
            goto L_0x0267
        L_0x0265:
            r37 = r9
        L_0x0267:
            r9 = r4
            int r15 = r8 + -51
            r6 = 9
            if (r15 == r6) goto L_0x0289
            if (r15 != r3) goto L_0x0271
            goto L_0x0289
        L_0x0271:
            r3 = 12
            if (r15 != r3) goto L_0x0286
            r3 = r20 & 1
            r6 = 1
            if (r3 != r6) goto L_0x0286
            int r3 = r36 / 3
            int r3 = r3 * 2
            int r3 = r3 + r6
            int r6 = r32 + 1
            r18 = r30[r32]
            r11[r3] = r18
            goto L_0x0295
        L_0x0286:
            r6 = r32
            goto L_0x0295
        L_0x0289:
            int r3 = r36 / 3
            int r3 = r3 * 2
            r6 = 1
            int r3 = r3 + r6
            int r6 = r32 + 1
            r18 = r30[r32]
            r11[r3] = r18
        L_0x0295:
            int r3 = r9 * 2
            r39 = r1
            r1 = r30[r3]
            r40 = r2
            boolean r2 = r1 instanceof java.lang.reflect.Field
            if (r2 == 0) goto L_0x02a5
            r2 = r1
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2
            goto L_0x02ae
        L_0x02a5:
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.reflect.Field r2 = reflectField(r13, r2)
            r30[r3] = r2
        L_0x02ae:
            r18 = r9
            r41 = r10
            long r9 = r14.objectFieldOffset(r2)
            int r10 = (int) r9
            int r3 = r3 + 1
            r1 = r30[r3]
            boolean r9 = r1 instanceof java.lang.reflect.Field
            if (r9 == 0) goto L_0x02c3
            r9 = r1
            java.lang.reflect.Field r9 = (java.lang.reflect.Field) r9
            goto L_0x02cc
        L_0x02c3:
            r9 = r1
            java.lang.String r9 = (java.lang.String) r9
            java.lang.reflect.Field r9 = reflectField(r13, r9)
            r30[r3] = r9
        L_0x02cc:
            r31 = r1
            r19 = r2
            long r1 = r14.objectFieldOffset(r9)
            int r2 = (int) r1
            r1 = 0
            r31 = r4
            r32 = r6
            r9 = r7
            r7 = r10
            r15 = r37
            r37 = r0
            goto L_0x03f4
        L_0x02e2:
            r39 = r1
            r40 = r2
            r41 = r10
            int r1 = r32 + 1
            r2 = r30[r32]
            java.lang.String r2 = (java.lang.String) r2
            java.lang.reflect.Field r2 = reflectField(r13, r2)
            r6 = 49
            r9 = 9
            if (r8 == r9) goto L_0x0363
            if (r8 != r3) goto L_0x02fc
            goto L_0x0363
        L_0x02fc:
            r9 = 27
            if (r8 == r9) goto L_0x0354
            if (r8 != r6) goto L_0x0303
            goto L_0x0354
        L_0x0303:
            r9 = 12
            if (r8 == r9) goto L_0x033f
            r9 = 30
            if (r8 == r9) goto L_0x033f
            r9 = 44
            if (r8 != r9) goto L_0x0310
            goto L_0x033f
        L_0x0310:
            r9 = 50
            if (r8 != r9) goto L_0x033d
            int r9 = r34 + 1
            r24[r34] = r36
            int r10 = r36 / 3
            int r10 = r10 * 2
            int r18 = r1 + 1
            r1 = r30[r1]
            r11[r10] = r1
            r1 = r5 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L_0x0337
            int r1 = r36 / 3
            int r1 = r1 * 2
            r10 = 1
            int r1 = r1 + r10
            int r10 = r18 + 1
            r18 = r30[r18]
            r11[r1] = r18
            r34 = r9
            r1 = r10
            r10 = 1
            goto L_0x036f
        L_0x0337:
            r34 = r9
            r1 = r18
            r10 = 1
            goto L_0x036f
        L_0x033d:
            r10 = 1
            goto L_0x036f
        L_0x033f:
            r9 = r20 & 1
            r10 = 1
            if (r9 != r10) goto L_0x0352
            int r9 = r36 / 3
            int r9 = r9 * 2
            int r9 = r9 + r10
            int r10 = r1 + 1
            r1 = r30[r1]
            r11[r9] = r1
            r1 = r10
            r10 = 1
            goto L_0x036f
        L_0x0352:
            r10 = 1
            goto L_0x036f
        L_0x0354:
            int r9 = r36 / 3
            int r9 = r9 * 2
            r10 = 1
            int r9 = r9 + r10
            int r10 = r1 + 1
            r1 = r30[r1]
            r11[r9] = r1
            r1 = r10
            r10 = 1
            goto L_0x036f
        L_0x0363:
            int r9 = r36 / 3
            int r9 = r9 * 2
            r10 = 1
            int r9 = r9 + r10
            java.lang.Class r16 = r2.getType()
            r11[r9] = r16
        L_0x036f:
            r9 = r7
            long r6 = r14.objectFieldOffset(r2)
            int r7 = (int) r6
            r6 = r20 & 1
            if (r6 != r10) goto L_0x03d4
            if (r8 > r3) goto L_0x03d4
            int r3 = r15 + 1
            char r4 = r0.charAt(r15)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r4 < r6) goto L_0x03a7
            r6 = r4 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x038a:
            int r18 = r3 + 1
            char r3 = r0.charAt(r3)
            r4 = r3
            r10 = 55296(0xd800, float:7.7486E-41)
            if (r3 < r10) goto L_0x03a0
            r3 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r15
            r6 = r6 | r3
            int r15 = r15 + 13
            r3 = r18
            r10 = 1
            goto L_0x038a
        L_0x03a0:
            int r3 = r4 << r15
            r3 = r3 | r6
            r4 = r3
            r15 = r18
            goto L_0x03ab
        L_0x03a7:
            r10 = 55296(0xd800, float:7.7486E-41)
            r15 = r3
        L_0x03ab:
            r3 = r4
            int r6 = r25 * 2
            int r18 = r3 / 32
            int r6 = r6 + r18
            r10 = r30[r6]
            r37 = r0
            boolean r0 = r10 instanceof java.lang.reflect.Field
            if (r0 == 0) goto L_0x03be
            r0 = r10
            java.lang.reflect.Field r0 = (java.lang.reflect.Field) r0
            goto L_0x03c7
        L_0x03be:
            r0 = r10
            java.lang.String r0 = (java.lang.String) r0
            java.lang.reflect.Field r0 = reflectField(r13, r0)
            r30[r6] = r0
        L_0x03c7:
            r31 = r1
            r18 = r2
            long r1 = r14.objectFieldOffset(r0)
            int r2 = (int) r1
            int r3 = r3 % 32
            r1 = r3
            goto L_0x03dd
        L_0x03d4:
            r37 = r0
            r31 = r1
            r18 = r2
            r0 = 0
            r1 = 0
            r2 = r0
        L_0x03dd:
            r0 = 18
            if (r8 < r0) goto L_0x03f0
            r0 = 49
            if (r8 > r0) goto L_0x03f0
            int r0 = r35 + 1
            r24[r35] = r7
            r35 = r0
            r32 = r31
            r31 = r4
            goto L_0x03f4
        L_0x03f0:
            r32 = r31
            r31 = r4
        L_0x03f4:
            int r0 = r36 + 1
            r12[r36] = r9
            int r3 = r0 + 1
            r4 = r5 & 512(0x200, float:7.175E-43)
            if (r4 == 0) goto L_0x0401
            r4 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x0402
        L_0x0401:
            r4 = 0
        L_0x0402:
            r6 = r5 & 256(0x100, float:3.59E-43)
            if (r6 == 0) goto L_0x0409
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x040a
        L_0x0409:
            r6 = 0
        L_0x040a:
            r4 = r4 | r6
            int r6 = r8 << 20
            r4 = r4 | r6
            r4 = r4 | r7
            r12[r0] = r4
            int r36 = r3 + 1
            int r0 = r1 << 20
            r0 = r0 | r2
            r12[r3] = r0
            r0 = r37
            r1 = r39
            r2 = r40
            r10 = r41
            r6 = 55296(0xd800, float:7.7486E-41)
            goto L_0x01e6
        L_0x0425:
            r37 = r0
            r39 = r1
            r40 = r2
            com.google.protobuf.MessageSchema r0 = new com.google.protobuf.MessageSchema
            com.google.protobuf.MessageLite r9 = r43.getDefaultInstance()
            r1 = 0
            int r2 = r22 + r23
            r4 = r0
            r5 = r12
            r6 = r11
            r7 = r27
            r8 = r28
            r3 = r11
            r11 = r1
            r1 = r12
            r12 = r24
            r38 = r13
            r13 = r22
            r42 = r14
            r14 = r2
            r2 = r15
            r15 = r44
            r16 = r45
            r17 = r46
            r18 = r47
            r19 = r48
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.newSchemaForRawMessageInfo(com.google.protobuf.RawMessageInfo, com.google.protobuf.NewInstanceSchema, com.google.protobuf.ListFieldSchema, com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, com.google.protobuf.MapFieldSchema):com.google.protobuf.MessageSchema");
    }

    private static Field reflectField(Class<?> messageClass, String fieldName) {
        try {
            return messageClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Field[] fields = messageClass.getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            String name = messageClass.getName();
            String arrays = Arrays.toString(fields);
            StringBuilder sb = new StringBuilder(String.valueOf(fieldName).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(fieldName);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    static <T> MessageSchema<T> newSchemaForMessageInfo(StructuralMessageInfo messageInfo, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        int minFieldNumber2;
        int minFieldNumber3;
        int[] checkInitialized;
        int[] mapFieldPositions;
        int[] repeatedFieldOffsets;
        int checkInitializedIndex;
        boolean isProto3 = messageInfo.getSyntax() == ProtoSyntax.PROTO3;
        FieldInfo[] fis = messageInfo.getFields();
        if (fis.length == 0) {
            minFieldNumber2 = 0;
            minFieldNumber3 = 0;
        } else {
            int minFieldNumber4 = fis[0].getFieldNumber();
            minFieldNumber3 = fis[fis.length - 1].getFieldNumber();
            minFieldNumber2 = minFieldNumber4;
        }
        int numEntries = fis.length;
        int[] buffer2 = new int[(numEntries * 3)];
        Object[] objects2 = new Object[(numEntries * 2)];
        int repeatedFieldCount = 0;
        int mapFieldCount = 0;
        for (FieldInfo fi : fis) {
            if (fi.getType() == FieldType.MAP) {
                mapFieldCount++;
            } else if (fi.getType().mo28304id() >= 18 && fi.getType().mo28304id() <= 49) {
                repeatedFieldCount++;
            }
        }
        int[] repeatedFieldOffsets2 = null;
        int[] mapFieldPositions2 = mapFieldCount > 0 ? new int[mapFieldCount] : null;
        if (repeatedFieldCount > 0) {
            repeatedFieldOffsets2 = new int[repeatedFieldCount];
        }
        int[] checkInitialized2 = messageInfo.getCheckInitialized();
        if (checkInitialized2 == null) {
            checkInitialized = EMPTY_INT_ARRAY;
        } else {
            checkInitialized = checkInitialized2;
        }
        int checkInitializedIndex2 = 0;
        int fieldIndex = 0;
        int mapFieldCount2 = 0;
        int repeatedFieldCount2 = 0;
        int bufferIndex = 0;
        while (fieldIndex < fis.length) {
            FieldInfo fi2 = fis[fieldIndex];
            int fieldNumber = fi2.getFieldNumber();
            storeFieldData(fi2, buffer2, bufferIndex, isProto3, objects2);
            if (checkInitializedIndex2 < checkInitialized.length && checkInitialized[checkInitializedIndex2] == fieldNumber) {
                checkInitialized[checkInitializedIndex2] = bufferIndex;
                checkInitializedIndex2++;
            }
            if (fi2.getType() == FieldType.MAP) {
                mapFieldPositions2[mapFieldCount2] = bufferIndex;
                mapFieldCount2++;
                checkInitializedIndex = checkInitializedIndex2;
            } else if (fi2.getType().mo28304id() < 18 || fi2.getType().mo28304id() > 49) {
                checkInitializedIndex = checkInitializedIndex2;
            } else {
                checkInitializedIndex = checkInitializedIndex2;
                repeatedFieldOffsets2[repeatedFieldCount2] = (int) UnsafeUtil.objectFieldOffset(fi2.getField());
                repeatedFieldCount2++;
            }
            fieldIndex++;
            bufferIndex += 3;
            checkInitializedIndex2 = checkInitializedIndex;
        }
        if (mapFieldPositions2 == null) {
            mapFieldPositions = EMPTY_INT_ARRAY;
        } else {
            mapFieldPositions = mapFieldPositions2;
        }
        if (repeatedFieldOffsets2 == null) {
            repeatedFieldOffsets = EMPTY_INT_ARRAY;
        } else {
            repeatedFieldOffsets = repeatedFieldOffsets2;
        }
        int[] combined = new int[(checkInitialized.length + mapFieldPositions.length + repeatedFieldOffsets.length)];
        System.arraycopy(checkInitialized, 0, combined, 0, checkInitialized.length);
        System.arraycopy(mapFieldPositions, 0, combined, checkInitialized.length, mapFieldPositions.length);
        System.arraycopy(repeatedFieldOffsets, 0, combined, checkInitialized.length + mapFieldPositions.length, repeatedFieldOffsets.length);
        return new MessageSchema(buffer2, objects2, minFieldNumber2, minFieldNumber3, messageInfo.getDefaultInstance(), isProto3, true, combined, checkInitialized.length, checkInitialized.length + mapFieldPositions.length, newInstanceSchema2, listFieldSchema2, unknownFieldSchema2, extensionSchema2, mapFieldSchema2);
    }

    /* JADX INFO: Multiple debug info for r1v4 com.google.protobuf.FieldType: [D('type' com.google.protobuf.FieldType), D('typeId' int)] */
    private static void storeFieldData(FieldInfo fi, int[] buffer2, int bufferIndex, boolean proto32, Object[] objects2) {
        int presenceFieldOffset;
        int presenceMaskShift;
        int fieldOffset;
        int presenceFieldOffset2;
        OneofInfo oneof = fi.getOneof();
        if (oneof != null) {
            fieldOffset = (int) UnsafeUtil.objectFieldOffset(oneof.getValueField());
            presenceMaskShift = 0;
            presenceFieldOffset = (int) UnsafeUtil.objectFieldOffset(oneof.getCaseField());
            presenceFieldOffset2 = fi.getType().mo28304id() + 51;
        } else {
            FieldType type = fi.getType();
            fieldOffset = (int) UnsafeUtil.objectFieldOffset(fi.getField());
            presenceFieldOffset2 = type.mo28304id();
            if (!proto32 && !type.isList() && !type.isMap()) {
                presenceFieldOffset = (int) UnsafeUtil.objectFieldOffset(fi.getPresenceField());
                presenceMaskShift = Integer.numberOfTrailingZeros(fi.getPresenceMask());
            } else if (fi.getCachedSizeField() == null) {
                presenceFieldOffset = 0;
                presenceMaskShift = 0;
            } else {
                presenceFieldOffset = (int) UnsafeUtil.objectFieldOffset(fi.getCachedSizeField());
                presenceMaskShift = 0;
            }
        }
        buffer2[bufferIndex] = fi.getFieldNumber();
        int i = bufferIndex + 1;
        int i2 = 0;
        int i3 = fi.isEnforceUtf8() ? 536870912 : 0;
        if (fi.isRequired()) {
            i2 = 268435456;
        }
        buffer2[i] = i3 | i2 | (presenceFieldOffset2 << 20) | fieldOffset;
        buffer2[bufferIndex + 2] = (presenceMaskShift << 20) | presenceFieldOffset;
        Object messageFieldClass = fi.getMessageFieldClass();
        if (fi.getMapDefaultEntry() != null) {
            objects2[(bufferIndex / 3) * 2] = fi.getMapDefaultEntry();
            if (messageFieldClass != null) {
                objects2[((bufferIndex / 3) * 2) + 1] = messageFieldClass;
            } else if (fi.getEnumVerifier() != null) {
                objects2[((bufferIndex / 3) * 2) + 1] = fi.getEnumVerifier();
            }
        } else if (messageFieldClass != null) {
            objects2[((bufferIndex / 3) * 2) + 1] = messageFieldClass;
        } else if (fi.getEnumVerifier() != null) {
            objects2[((bufferIndex / 3) * 2) + 1] = fi.getEnumVerifier();
        }
    }

    public T newInstance() {
        return this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    public boolean equals(T message, T other) {
        int bufferLength = this.buffer.length;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            if (!equals(message, other, pos)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.getFromMessage(message).equals(this.unknownFieldSchema.getFromMessage(other))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(message).equals(this.extensionSchema.getExtensions(other));
        }
        return true;
    }

    private boolean equals(T message, T other, int pos) {
        int typeAndOffset = typeAndOffsetAt(pos);
        long offset = offset(typeAndOffset);
        switch (type(typeAndOffset)) {
            case 0:
                if (!arePresentForEquals(message, other, pos) || Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)) != Double.doubleToLongBits(UnsafeUtil.getDouble(other, offset))) {
                    return false;
                }
                return true;
            case 1:
                if (!arePresentForEquals(message, other, pos) || Float.floatToIntBits(UnsafeUtil.getFloat(message, offset)) != Float.floatToIntBits(UnsafeUtil.getFloat(other, offset))) {
                    return false;
                }
                return true;
            case 2:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong(message, offset) != UnsafeUtil.getLong(other, offset)) {
                    return false;
                }
                return true;
            case 3:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong(message, offset) != UnsafeUtil.getLong(other, offset)) {
                    return false;
                }
                return true;
            case 4:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 5:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong(message, offset) != UnsafeUtil.getLong(other, offset)) {
                    return false;
                }
                return true;
            case 6:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 7:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getBoolean(message, offset) != UnsafeUtil.getBoolean(other, offset)) {
                    return false;
                }
                return true;
            case 8:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset))) {
                    return false;
                }
                return true;
            case 9:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset))) {
                    return false;
                }
                return true;
            case 10:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset))) {
                    return false;
                }
                return true;
            case 11:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 12:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 13:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 14:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong(message, offset) != UnsafeUtil.getLong(other, offset)) {
                    return false;
                }
                return true;
            case 15:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt(message, offset) != UnsafeUtil.getInt(other, offset)) {
                    return false;
                }
                return true;
            case 16:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong(message, offset) != UnsafeUtil.getLong(other, offset)) {
                    return false;
                }
                return true;
            case 17:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset))) {
                    return false;
                }
                return true;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                return SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            case 50:
                return SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                if (!isOneofCaseEqual(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset))) {
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    public int hashCode(T message) {
        int hashCode = 0;
        int bufferLength = this.buffer.length;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            int typeAndOffset = typeAndOffsetAt(pos);
            int entryNumber = numberAt(pos);
            long offset = offset(typeAndOffset);
            switch (type(typeAndOffset)) {
                case 0:
                    hashCode = (hashCode * 53) + Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)));
                    break;
                case 1:
                    hashCode = (hashCode * 53) + Float.floatToIntBits(UnsafeUtil.getFloat(message, offset));
                    break;
                case 2:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                case 3:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                case 4:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 5:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                case 6:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 7:
                    hashCode = (hashCode * 53) + Internal.hashBoolean(UnsafeUtil.getBoolean(message, offset));
                    break;
                case 8:
                    hashCode = (hashCode * 53) + ((String) UnsafeUtil.getObject(message, offset)).hashCode();
                    break;
                case 9:
                    int protoHash = 37;
                    Object submessage = UnsafeUtil.getObject(message, offset);
                    if (submessage != null) {
                        protoHash = submessage.hashCode();
                    }
                    hashCode = (hashCode * 53) + protoHash;
                    break;
                case 10:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                case 11:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 12:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 13:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 14:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                case 15:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt(message, offset);
                    break;
                case 16:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                case 17:
                    int protoHash2 = 37;
                    Object submessage2 = UnsafeUtil.getObject(message, offset);
                    if (submessage2 != null) {
                        protoHash2 = submessage2.hashCode();
                    }
                    hashCode = (hashCode * 53) + protoHash2;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                case 50:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                case 51:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(message, offset)));
                        break;
                    }
                case 52:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Float.floatToIntBits(oneofFloatAt(message, offset));
                        break;
                    }
                case 53:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 54:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 55:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 56:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 57:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 58:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashBoolean(oneofBooleanAt(message, offset));
                        break;
                    }
                case 59:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + ((String) UnsafeUtil.getObject(message, offset)).hashCode();
                        break;
                    }
                case 60:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                        break;
                    }
                case 61:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                        break;
                    }
                case 62:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 63:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 64:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 65:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 66:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 67:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject(message, offset).hashCode();
                        break;
                    }
            }
        }
        int hashCode2 = (hashCode * 53) + this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions != 0) {
            return (hashCode2 * 53) + this.extensionSchema.getExtensions(message).hashCode();
        }
        return hashCode2;
    }

    public void mergeFrom(T message, T other) {
        if (other != null) {
            for (int i = 0; i < this.buffer.length; i += 3) {
                mergeSingleField(message, other, i);
            }
            if (this.proto3 == 0) {
                SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
                if (this.hasExtensions) {
                    SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private void mergeSingleField(T message, T other, int pos) {
        int typeAndOffset = typeAndOffsetAt(pos);
        long offset = offset(typeAndOffset);
        int number = numberAt(pos);
        switch (type(typeAndOffset)) {
            case 0:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putDouble(message, offset, UnsafeUtil.getDouble(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 1:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putFloat(message, offset, UnsafeUtil.getFloat(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 2:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 3:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 4:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 5:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 6:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 7:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putBoolean(message, offset, UnsafeUtil.getBoolean(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 8:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 9:
                mergeMessage(message, other, pos);
                return;
            case 10:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 11:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 12:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 13:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 14:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 15:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 16:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 17:
                mergeMessage(message, other, pos);
                return;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.listFieldSchema.mergeListsAt(message, other, offset);
                return;
            case 50:
                SchemaUtil.mergeMap(this.mapFieldSchema, message, other, offset);
                return;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    setOneofPresent(message, number, pos);
                    return;
                }
                return;
            case 60:
                mergeOneofMessage(message, other, pos);
                return;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    setOneofPresent(message, number, pos);
                    return;
                }
                return;
            case 68:
                mergeOneofMessage(message, other, pos);
                return;
            default:
                return;
        }
    }

    private void mergeMessage(T message, T other, int pos) {
        long offset = offset(typeAndOffsetAt(pos));
        if (isFieldPresent(other, pos)) {
            Object mine = UnsafeUtil.getObject(message, offset);
            Object theirs = UnsafeUtil.getObject(other, offset);
            if (mine != null && theirs != null) {
                UnsafeUtil.putObject(message, offset, Internal.mergeMessage(mine, theirs));
                setFieldPresent(message, pos);
            } else if (theirs != null) {
                UnsafeUtil.putObject(message, offset, theirs);
                setFieldPresent(message, pos);
            }
        }
    }

    private void mergeOneofMessage(T message, T other, int pos) {
        int typeAndOffset = typeAndOffsetAt(pos);
        int number = numberAt(pos);
        long offset = offset(typeAndOffset);
        if (isOneofPresent(other, number, pos)) {
            Object mine = UnsafeUtil.getObject(message, offset);
            Object theirs = UnsafeUtil.getObject(other, offset);
            if (mine != null && theirs != null) {
                UnsafeUtil.putObject(message, offset, Internal.mergeMessage(mine, theirs));
                setOneofPresent(message, number, pos);
            } else if (theirs != null) {
                UnsafeUtil.putObject(message, offset, theirs);
                setOneofPresent(message, number, pos);
            }
        }
    }

    public int getSerializedSize(T message) {
        return this.proto3 ? getSerializedSizeProto3(message) : getSerializedSizeProto2(message);
    }

    private int getSerializedSizeProto2(T message) {
        int currentPresenceFieldOffset;
        T t = message;
        int size = 0;
        Unsafe unsafe = UNSAFE;
        int currentPresenceFieldOffset2 = -1;
        int currentPresenceField = 0;
        int i = 0;
        while (i < this.buffer.length) {
            int typeAndOffset = typeAndOffsetAt(i);
            int number = numberAt(i);
            int fieldType = type(typeAndOffset);
            int presenceMaskAndOffset = 0;
            int presenceMask = 0;
            if (fieldType <= 17) {
                presenceMaskAndOffset = this.buffer[i + 2];
                int presenceFieldOffset = presenceMaskAndOffset & OFFSET_MASK;
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                if (presenceFieldOffset != currentPresenceFieldOffset2) {
                    currentPresenceFieldOffset2 = presenceFieldOffset;
                    currentPresenceField = unsafe.getInt(t, (long) presenceFieldOffset);
                }
            } else if (this.useCachedSizeField && fieldType >= FieldType.DOUBLE_LIST_PACKED.mo28304id() && fieldType <= FieldType.SINT64_LIST_PACKED.mo28304id()) {
                presenceMaskAndOffset = this.buffer[i + 2] & OFFSET_MASK;
            }
            long offset = offset(typeAndOffset);
            switch (fieldType) {
                case 0:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0d);
                        break;
                    }
                case 1:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                case 2:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt64Size(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 3:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt64Size(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 4:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt32Size(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 5:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed64Size(number, 0);
                        break;
                    }
                case 6:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                case 7:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                case 8:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        Object value = unsafe.getObject(t, offset);
                        if (!(value instanceof ByteString)) {
                            size += CodedOutputStream.computeStringSize(number, (String) value);
                            break;
                        } else {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString) value);
                            break;
                        }
                    }
                case 9:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += SchemaUtil.computeSizeMessage(number, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
                case 10:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString) unsafe.getObject(t, offset));
                        break;
                    }
                case 11:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt32Size(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 12:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeEnumSize(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 13:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                case 14:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed64Size(number, 0);
                        break;
                    }
                case 15:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt32Size(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 16:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt64Size(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 17:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite) unsafe.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
                case 18:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 19:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 20:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeInt64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 21:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeUInt64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 22:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeInt32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 23:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 24:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 25:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeBoolList(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 26:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeStringList(number, (List) unsafe.getObject(t, offset));
                    break;
                case 27:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeMessageList(number, (List) unsafe.getObject(t, offset), getMessageFieldSchema(i));
                    break;
                case 28:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeByteStringList(number, (List) unsafe.getObject(t, offset));
                    break;
                case 29:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeUInt32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 30:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeEnumList(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 31:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 32:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeFixed64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 33:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeSInt32List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 34:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeSInt64List(number, (List) unsafe.getObject(t, offset), false);
                    break;
                case 35:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize2 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize2);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize2) + fieldSize2;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize3 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize3 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize3);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize3) + fieldSize3;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize4 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize4);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize4) + fieldSize4;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize5 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize5 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize5);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize5) + fieldSize5;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize6 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize6 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize6);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize6) + fieldSize6;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize7 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize7 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize7);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize7) + fieldSize7;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize8 = SchemaUtil.computeSizeBoolListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize8 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize8);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize8) + fieldSize8;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize9 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize9 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize9);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize9) + fieldSize9;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize10 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize10 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize10);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize10) + fieldSize10;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize11 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize11 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize11);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize11) + fieldSize11;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize12 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize12 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize12);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize12) + fieldSize12;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize13 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize13 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize13);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize13) + fieldSize13;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    int fieldSize14 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize14 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) presenceMaskAndOffset, fieldSize14);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize14) + fieldSize14;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += SchemaUtil.computeSizeGroupList(number, (List) unsafe.getObject(t, offset), getMessageFieldSchema(i));
                    break;
                case 50:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    size += this.mapFieldSchema.getSerializedSize(number, unsafe.getObject(t, offset), getMapFieldDefaultEntry(i));
                    break;
                case 51:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0d);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 52:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 53:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeInt64Size(number, oneofLongAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 54:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt64Size(number, oneofLongAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 55:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeInt32Size(number, oneofIntAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 56:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed64Size(number, 0);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 57:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 58:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 59:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        Object value2 = unsafe.getObject(t, offset);
                        if (value2 instanceof ByteString) {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString) value2);
                        } else {
                            size += CodedOutputStream.computeStringSize(number, (String) value2);
                        }
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 60:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += SchemaUtil.computeSizeMessage(number, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 61:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString) unsafe.getObject(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 62:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt32Size(number, oneofIntAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 63:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeEnumSize(number, oneofIntAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 64:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 65:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed64Size(number, 0);
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 66:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt32Size(number, oneofIntAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 67:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt64Size(number, oneofLongAt(t, offset));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                case 68:
                    if (!isOneofPresent(t, number, i)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    } else {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite) unsafe.getObject(t, offset), getMessageFieldSchema(i));
                        currentPresenceFieldOffset = currentPresenceFieldOffset2;
                        break;
                    }
                default:
                    currentPresenceFieldOffset = currentPresenceFieldOffset2;
                    break;
            }
            i += 3;
            currentPresenceFieldOffset2 = currentPresenceFieldOffset;
        }
        int size2 = size + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t);
        if (this.hasExtensions) {
            return size2 + this.extensionSchema.getExtensions(t).getSerializedSize();
        }
        return size2;
    }

    private int getSerializedSizeProto3(T message) {
        int cachedSizeOffset;
        T t = message;
        Unsafe unsafe = UNSAFE;
        int size = 0;
        for (int i = 0; i < this.buffer.length; i += 3) {
            int typeAndOffset = typeAndOffsetAt(i);
            int fieldType = type(typeAndOffset);
            int number = numberAt(i);
            long offset = offset(typeAndOffset);
            if (fieldType < FieldType.DOUBLE_LIST_PACKED.mo28304id() || fieldType > FieldType.SINT64_LIST_PACKED.mo28304id()) {
                cachedSizeOffset = 0;
            } else {
                cachedSizeOffset = this.buffer[i + 2] & OFFSET_MASK;
            }
            switch (fieldType) {
                case 0:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0d);
                        break;
                    }
                case 1:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                case 2:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt64Size(number, UnsafeUtil.getLong(t, offset));
                        break;
                    }
                case 3:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt64Size(number, UnsafeUtil.getLong(t, offset));
                        break;
                    }
                case 4:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt32Size(number, UnsafeUtil.getInt(t, offset));
                        break;
                    }
                case 5:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed64Size(number, 0);
                        break;
                    }
                case 6:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                case 7:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                case 8:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        Object value = UnsafeUtil.getObject(t, offset);
                        if (!(value instanceof ByteString)) {
                            size += CodedOutputStream.computeStringSize(number, (String) value);
                            break;
                        } else {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString) value);
                            break;
                        }
                    }
                case 9:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += SchemaUtil.computeSizeMessage(number, UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
                case 10:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString) UnsafeUtil.getObject(t, offset));
                        break;
                    }
                case 11:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt32Size(number, UnsafeUtil.getInt(t, offset));
                        break;
                    }
                case 12:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeEnumSize(number, UnsafeUtil.getInt(t, offset));
                        break;
                    }
                case 13:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                case 14:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed64Size(number, 0);
                        break;
                    }
                case 15:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt32Size(number, UnsafeUtil.getInt(t, offset));
                        break;
                    }
                case 16:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt64Size(number, UnsafeUtil.getLong(t, offset));
                        break;
                    }
                case 17:
                    if (!isFieldPresent(t, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite) UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
                case 18:
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(t, offset), false);
                    break;
                case 19:
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(t, offset), false);
                    break;
                case 20:
                    size += SchemaUtil.computeSizeInt64List(number, listAt(t, offset), false);
                    break;
                case 21:
                    size += SchemaUtil.computeSizeUInt64List(number, listAt(t, offset), false);
                    break;
                case 22:
                    size += SchemaUtil.computeSizeInt32List(number, listAt(t, offset), false);
                    break;
                case 23:
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(t, offset), false);
                    break;
                case 24:
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(t, offset), false);
                    break;
                case 25:
                    size += SchemaUtil.computeSizeBoolList(number, listAt(t, offset), false);
                    break;
                case 26:
                    size += SchemaUtil.computeSizeStringList(number, listAt(t, offset));
                    break;
                case 27:
                    size += SchemaUtil.computeSizeMessageList(number, listAt(t, offset), getMessageFieldSchema(i));
                    break;
                case 28:
                    size += SchemaUtil.computeSizeByteStringList(number, listAt(t, offset));
                    break;
                case 29:
                    size += SchemaUtil.computeSizeUInt32List(number, listAt(t, offset), false);
                    break;
                case 30:
                    size += SchemaUtil.computeSizeEnumList(number, listAt(t, offset), false);
                    break;
                case 31:
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(t, offset), false);
                    break;
                case 32:
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(t, offset), false);
                    break;
                case 33:
                    size += SchemaUtil.computeSizeSInt32List(number, listAt(t, offset), false);
                    break;
                case 34:
                    size += SchemaUtil.computeSizeSInt64List(number, listAt(t, offset), false);
                    break;
                case 35:
                    int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    int fieldSize2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize2 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize2);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize2) + fieldSize2;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    int fieldSize3 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize3 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize3);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize3) + fieldSize3;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    int fieldSize4 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize4);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize4) + fieldSize4;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    int fieldSize5 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize5 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize5);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize5) + fieldSize5;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    int fieldSize6 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize6 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize6);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize6) + fieldSize6;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    int fieldSize7 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize7 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize7);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize7) + fieldSize7;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    int fieldSize8 = SchemaUtil.computeSizeBoolListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize8 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize8);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize8) + fieldSize8;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    int fieldSize9 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize9 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize9);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize9) + fieldSize9;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    int fieldSize10 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize10 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize10);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize10) + fieldSize10;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    int fieldSize11 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize11 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize11);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize11) + fieldSize11;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    int fieldSize12 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize12 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize12);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize12) + fieldSize12;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    int fieldSize13 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize13 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize13);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize13) + fieldSize13;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    int fieldSize14 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(t, offset));
                    if (fieldSize14 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, (long) cachedSizeOffset, fieldSize14);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize14) + fieldSize14;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    size += SchemaUtil.computeSizeGroupList(number, listAt(t, offset), getMessageFieldSchema(i));
                    break;
                case 50:
                    size += this.mapFieldSchema.getSerializedSize(number, UnsafeUtil.getObject(t, offset), getMapFieldDefaultEntry(i));
                    break;
                case 51:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0d);
                        break;
                    }
                case 52:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                case 53:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt64Size(number, oneofLongAt(t, offset));
                        break;
                    }
                case 54:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt64Size(number, oneofLongAt(t, offset));
                        break;
                    }
                case 55:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeInt32Size(number, oneofIntAt(t, offset));
                        break;
                    }
                case 56:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed64Size(number, 0);
                        break;
                    }
                case 57:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                case 58:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                case 59:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        Object value2 = UnsafeUtil.getObject(t, offset);
                        if (!(value2 instanceof ByteString)) {
                            size += CodedOutputStream.computeStringSize(number, (String) value2);
                            break;
                        } else {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString) value2);
                            break;
                        }
                    }
                case 60:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += SchemaUtil.computeSizeMessage(number, UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
                case 61:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString) UnsafeUtil.getObject(t, offset));
                        break;
                    }
                case 62:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeUInt32Size(number, oneofIntAt(t, offset));
                        break;
                    }
                case 63:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeEnumSize(number, oneofIntAt(t, offset));
                        break;
                    }
                case 64:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                case 65:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSFixed64Size(number, 0);
                        break;
                    }
                case 66:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt32Size(number, oneofIntAt(t, offset));
                        break;
                    }
                case 67:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeSInt64Size(number, oneofLongAt(t, offset));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(t, number, i)) {
                        break;
                    } else {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite) UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i));
                        break;
                    }
            }
        }
        return size + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t);
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
        return schema.getSerializedSize(schema.getFromMessage(message));
    }

    private static List<?> listAt(Object message, long offset) {
        return (List) UnsafeUtil.getObject(message, offset);
    }

    public void writeTo(T message, Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(message, writer);
        } else if (this.proto3) {
            writeFieldsInAscendingOrderProto3(message, writer);
        } else {
            writeFieldsInAscendingOrderProto2(message, writer);
        }
    }

    /* JADX INFO: Multiple debug info for r4v7 long: [D('offset' long), D('nextExtension' java.util.Map$Entry)] */
    private void writeFieldsInAscendingOrderProto2(T message, Writer writer) throws IOException {
        Map.Entry nextExtension;
        int currentPresenceFieldOffset;
        int currentPresenceFieldOffset2;
        T t = message;
        Writer writer2 = writer;
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension2 = null;
        if (this.hasExtensions) {
            FieldSet<?> extensions = this.extensionSchema.getExtensions(t);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.iterator();
                nextExtension2 = extensionIterator.next();
            }
        }
        int currentPresenceFieldOffset3 = -1;
        int currentPresenceField = 0;
        int bufferLength = this.buffer.length;
        Unsafe unsafe = UNSAFE;
        int pos = 0;
        while (pos < bufferLength) {
            int typeAndOffset = typeAndOffsetAt(pos);
            int number = numberAt(pos);
            int fieldType = type(typeAndOffset);
            int presenceMask = 0;
            Map.Entry nextExtension3 = nextExtension2;
            if (this.proto3 || fieldType > 17) {
                nextExtension = nextExtension3;
            } else {
                int presenceMaskAndOffset = this.buffer[pos + 2];
                int presenceFieldOffset = OFFSET_MASK & presenceMaskAndOffset;
                if (presenceFieldOffset != currentPresenceFieldOffset3) {
                    currentPresenceFieldOffset2 = presenceFieldOffset;
                    currentPresenceField = unsafe.getInt(t, (long) presenceFieldOffset);
                } else {
                    currentPresenceFieldOffset2 = currentPresenceFieldOffset3;
                }
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                nextExtension = nextExtension3;
                currentPresenceFieldOffset3 = currentPresenceFieldOffset2;
            }
            while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) <= number) {
                this.extensionSchema.serializeExtension(writer2, nextExtension);
                nextExtension = extensionIterator.hasNext() ? (Map.Entry) extensionIterator.next() : null;
            }
            Map.Entry nextExtension4 = nextExtension;
            int currentPresenceFieldOffset4 = currentPresenceFieldOffset3;
            long offset = offset(typeAndOffset);
            int bufferLength2 = bufferLength;
            switch (fieldType) {
                case 0:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeDouble(number, doubleAt(t, offset));
                        break;
                    }
                case 1:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeFloat(number, floatAt(t, offset));
                        break;
                    }
                case 2:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeInt64(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 3:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeUInt64(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 4:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeInt32(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 5:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeFixed64(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 6:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeFixed32(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 7:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeBool(number, booleanAt(t, offset));
                        break;
                    }
                case 8:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writeString(number, unsafe.getObject(t, offset), writer2);
                        break;
                    }
                case 9:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeMessage(number, unsafe.getObject(t, offset), getMessageFieldSchema(pos));
                        break;
                    }
                case 10:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeBytes(number, (ByteString) unsafe.getObject(t, offset));
                        break;
                    }
                case 11:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeUInt32(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 12:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeEnum(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 13:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeSFixed32(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 14:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeSFixed64(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 15:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeSInt32(number, unsafe.getInt(t, offset));
                        break;
                    }
                case 16:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeSInt64(number, unsafe.getLong(t, offset));
                        break;
                    }
                case 17:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if ((currentPresenceField & presenceMask) == 0) {
                        break;
                    } else {
                        writer2.writeGroup(number, unsafe.getObject(t, offset), getMessageFieldSchema(pos));
                        break;
                    }
                case 18:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeDoubleList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 19:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFloatList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 20:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 21:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeUInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 22:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 23:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFixed64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 24:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFixed32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 25:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeBoolList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 26:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeStringList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2);
                    break;
                case 27:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeMessageList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, getMessageFieldSchema(pos));
                    break;
                case 28:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeBytesList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2);
                    break;
                case 29:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeUInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 30:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeEnumList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 31:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSFixed32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 32:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSFixed64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 33:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 34:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, false);
                    break;
                case 35:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeDoubleList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 36:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFloatList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 37:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 38:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeUInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 39:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 40:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFixed64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 41:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeFixed32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 42:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeBoolList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 43:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeUInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 44:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeEnumList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 45:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSFixed32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 46:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSFixed64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 47:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSInt32List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 48:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeSInt64List(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, true);
                    break;
                case 49:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    SchemaUtil.writeGroupList(numberAt(pos), (List) unsafe.getObject(t, offset), writer2, getMessageFieldSchema(pos));
                    break;
                case 50:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    writeMapHelper(writer2, number, unsafe.getObject(t, offset), pos);
                    break;
                case 51:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeDouble(number, oneofDoubleAt(t, offset));
                        break;
                    }
                case 52:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeFloat(number, oneofFloatAt(t, offset));
                        break;
                    }
                case 53:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeInt64(number, oneofLongAt(t, offset));
                        break;
                    }
                case 54:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeUInt64(number, oneofLongAt(t, offset));
                        break;
                    }
                case 55:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeInt32(number, oneofIntAt(t, offset));
                        break;
                    }
                case 56:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeFixed64(number, oneofLongAt(t, offset));
                        break;
                    }
                case 57:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeFixed32(number, oneofIntAt(t, offset));
                        break;
                    }
                case 58:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeBool(number, oneofBooleanAt(t, offset));
                        break;
                    }
                case 59:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writeString(number, unsafe.getObject(t, offset), writer2);
                        break;
                    }
                case 60:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeMessage(number, unsafe.getObject(t, offset), getMessageFieldSchema(pos));
                        break;
                    }
                case 61:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeBytes(number, (ByteString) unsafe.getObject(t, offset));
                        break;
                    }
                case 62:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeUInt32(number, oneofIntAt(t, offset));
                        break;
                    }
                case 63:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeEnum(number, oneofIntAt(t, offset));
                        break;
                    }
                case 64:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeSFixed32(number, oneofIntAt(t, offset));
                        break;
                    }
                case 65:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeSFixed64(number, oneofLongAt(t, offset));
                        break;
                    }
                case 66:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeSInt32(number, oneofIntAt(t, offset));
                        break;
                    }
                case 67:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    if (!isOneofPresent(t, number, pos)) {
                        break;
                    } else {
                        writer2.writeSInt64(number, oneofLongAt(t, offset));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(t, number, pos)) {
                        currentPresenceFieldOffset = currentPresenceFieldOffset4;
                        break;
                    } else {
                        currentPresenceFieldOffset = currentPresenceFieldOffset4;
                        writer2.writeGroup(number, unsafe.getObject(t, offset), getMessageFieldSchema(pos));
                        break;
                    }
                default:
                    currentPresenceFieldOffset = currentPresenceFieldOffset4;
                    break;
            }
            pos += 3;
            currentPresenceFieldOffset3 = currentPresenceFieldOffset;
            nextExtension2 = nextExtension4;
            bufferLength = bufferLength2;
        }
        while (nextExtension2 != null) {
            this.extensionSchema.serializeExtension(writer2, nextExtension2);
            nextExtension2 = extensionIterator.hasNext() ? extensionIterator.next() : null;
        }
        writeUnknownInMessageTo(this.unknownFieldSchema, t, writer2);
    }

    private void writeFieldsInAscendingOrderProto3(T message, Writer writer) throws IOException {
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.iterator();
                nextExtension = extensionIterator.next();
            }
        }
        int bufferLength = this.buffer.length;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            int typeAndOffset = typeAndOffsetAt(pos);
            int number = numberAt(pos);
            while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) <= number) {
                this.extensionSchema.serializeExtension(writer, nextExtension);
                nextExtension = extensionIterator.hasNext() ? (Map.Entry) extensionIterator.next() : null;
            }
            switch (type(typeAndOffset)) {
                case 0:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 1:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 2:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 3:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 4:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 5:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 6:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 7:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 8:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                case 9:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeMessage(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                        break;
                    }
                case 10:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeBytes(number, (ByteString) UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                case 11:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 12:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 13:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 14:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 15:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 16:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 17:
                    if (!isFieldPresent(message, pos)) {
                        break;
                    } else {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                        break;
                    }
                case 18:
                    SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 19:
                    SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 20:
                    SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 21:
                    SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 22:
                    SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 23:
                    SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 24:
                    SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 25:
                    SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 26:
                    SchemaUtil.writeStringList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                case 27:
                    SchemaUtil.writeMessageList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                    break;
                case 28:
                    SchemaUtil.writeBytesList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                case 29:
                    SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 30:
                    SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 31:
                    SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 32:
                    SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 33:
                    SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 34:
                    SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                case 35:
                    SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 36:
                    SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 37:
                    SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 38:
                    SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 39:
                    SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 40:
                    SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 41:
                    SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 42:
                    SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 43:
                    SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 44:
                    SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 45:
                    SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 46:
                    SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 47:
                    SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 48:
                    SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                case 49:
                    SchemaUtil.writeGroupList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                    break;
                case 50:
                    writeMapHelper(writer, number, UnsafeUtil.getObject(message, offset(typeAndOffset)), pos);
                    break;
                case 51:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 52:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 53:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 54:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 55:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 56:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 57:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 58:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 59:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                case 60:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeMessage(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                        break;
                    }
                case 61:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeBytes(number, (ByteString) UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                case 62:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 63:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 64:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 65:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 66:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 67:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(message, number, pos)) {
                        break;
                    } else {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                        break;
                    }
            }
        }
        while (nextExtension != null) {
            this.extensionSchema.serializeExtension(writer, nextExtension);
            nextExtension = extensionIterator.hasNext() ? (Map.Entry) extensionIterator.next() : null;
        }
        writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
    }

    private void writeFieldsInDescendingOrder(T message, Writer writer) throws IOException {
        writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.descendingIterator();
                nextExtension = extensionIterator.next();
            }
        }
        int pos = this.buffer.length;
        while (true) {
            pos -= 3;
            if (pos >= 0) {
                int typeAndOffset = typeAndOffsetAt(pos);
                int number = numberAt(pos);
                while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) > number) {
                    this.extensionSchema.serializeExtension(writer, nextExtension);
                    nextExtension = extensionIterator.hasNext() ? (Map.Entry) extensionIterator.next() : null;
                }
                switch (type(typeAndOffset)) {
                    case 0:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 1:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 2:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 3:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 4:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 5:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 6:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 7:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 8:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                            break;
                        }
                    case 9:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeMessage(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case 10:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeBytes(number, (ByteString) UnsafeUtil.getObject(message, offset(typeAndOffset)));
                            break;
                        }
                    case 11:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 12:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 13:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 14:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 15:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 16:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 17:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case 18:
                        SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 19:
                        SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 20:
                        SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 21:
                        SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 22:
                        SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 23:
                        SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 24:
                        SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 25:
                        SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 26:
                        SchemaUtil.writeStringList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    case 27:
                        SchemaUtil.writeMessageList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                        break;
                    case 28:
                        SchemaUtil.writeBytesList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    case 29:
                        SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 30:
                        SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 31:
                        SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 32:
                        SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 33:
                        SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 34:
                        SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                        break;
                    case 35:
                        SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 36:
                        SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 37:
                        SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 38:
                        SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 39:
                        SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 40:
                        SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 41:
                        SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 42:
                        SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 43:
                        SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 44:
                        SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 45:
                        SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 46:
                        SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 47:
                        SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 48:
                        SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                        break;
                    case 49:
                        SchemaUtil.writeGroupList(numberAt(pos), (List) UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                        break;
                    case 50:
                        writeMapHelper(writer, number, UnsafeUtil.getObject(message, offset(typeAndOffset)), pos);
                        break;
                    case 51:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 52:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 53:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 54:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 55:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 56:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 57:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 58:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 59:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                            break;
                        }
                    case 60:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeMessage(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case 61:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeBytes(number, (ByteString) UnsafeUtil.getObject(message, offset(typeAndOffset)));
                            break;
                        }
                    case 62:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 63:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 64:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 65:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 66:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 67:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 68:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                }
            } else {
                while (nextExtension != null) {
                    this.extensionSchema.serializeExtension(writer, nextExtension);
                    nextExtension = extensionIterator.hasNext() ? (Map.Entry) extensionIterator.next() : null;
                }
                return;
            }
        }
    }

    private <K, V> void writeMapHelper(Writer writer, int number, Object mapField, int pos) throws IOException {
        if (mapField != null) {
            writer.writeMap(number, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos)), this.mapFieldSchema.forMapData(mapField));
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> schema, T message, Writer writer) throws IOException {
        schema.writeTo(schema.getFromMessage(message), writer);
    }

    public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (extensionRegistry != null) {
            mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
            return;
        }
        throw new NullPointerException();
    }

    /* JADX WARN: Failed to insert an additional move for type inference into block B:89:0x0290 */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:219:0x000e */
    /* JADX INFO: additional move instructions added (2) to help type inference */
    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:122:0x0472 */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:92:0x02a2 */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:218:0x000e */
    /* JADX INFO: Multiple debug info for r14v3 int: [D('pos' int), D('extensions' com.google.protobuf.FieldSet<ET>)] */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v28 */
    /* JADX WARN: Type inference failed for: r16v29 */
    /* JADX WARN: Type inference failed for: r16v34 */
    /* JADX WARN: Type inference failed for: r16v35 */
    /* JADX WARN: Type inference failed for: r16v36 */
    /* JADX WARN: Type inference failed for: r16v37 */
    /* JADX WARN: Type inference failed for: r16v38 */
    /* JADX WARN: Type inference failed for: r16v39 */
    /* JADX WARN: Type inference failed for: r16v40 */
    /* JADX WARN: Type inference failed for: r16v41 */
    /* JADX WARN: Type inference failed for: r16v42 */
    /* JADX WARN: Type inference failed for: r16v43 */
    /* JADX WARN: Type inference failed for: r16v44 */
    /* JADX WARN: Type inference failed for: r16v45 */
    /* JADX WARN: Type inference failed for: r16v46 */
    /* JADX WARN: Type inference failed for: r16v47 */
    /* JADX WARN: Type inference failed for: r16v48 */
    /* JADX WARN: Type inference failed for: r16v49 */
    /* JADX WARN: Type inference failed for: r16v50 */
    /* JADX WARN: Type inference failed for: r16v51 */
    /* JADX WARN: Type inference failed for: r16v52 */
    /* JADX WARN: Type inference failed for: r16v53 */
    /* JADX WARN: Type inference failed for: r16v54 */
    /* JADX WARN: Type inference failed for: r16v55 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x06ae A[Catch:{ all -> 0x06f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x06cb  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0700 A[LOOP:6: B:203:0x06fc->B:205:0x0700, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x070d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <UT, UB, ET extends com.google.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.google.protobuf.UnknownFieldSchema<UT, UB> r19, com.google.protobuf.ExtensionSchema<ET> r20, T r21, com.google.protobuf.Reader r22, com.google.protobuf.ExtensionRegistryLite r23) throws java.io.IOException {
        /*
            r18 = this;
            r8 = r18
            r9 = r19
            r10 = r21
            r11 = r22
            r12 = r23
            r1 = 0
            r2 = 0
            r13 = r1
            r14 = r2
        L_0x000e:
            int r1 = r22.getFieldNumber()     // Catch:{ all -> 0x06f6 }
            r15 = r1
            int r1 = r8.positionForFieldNumber(r15)     // Catch:{ all -> 0x06f6 }
            r7 = r1
            if (r7 >= 0) goto L_0x00aa
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r15 != r1) goto L_0x0036
            int r1 = r8.checkInitializedCount
        L_0x0021:
            int r2 = r8.repeatedFieldOffsetStart
            if (r1 >= r2) goto L_0x0030
            int[] r2 = r8.intArray
            r2 = r2[r1]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r2, r13, r9)
            int r1 = r1 + 1
            goto L_0x0021
        L_0x0030:
            if (r13 == 0) goto L_0x0035
            r9.setBuilderToMessage(r10, r13)
        L_0x0035:
            return
        L_0x0036:
            boolean r1 = r8.hasExtensions     // Catch:{ all -> 0x06f6 }
            if (r1 != 0) goto L_0x003e
            r1 = 0
            r6 = r20
            goto L_0x0046
        L_0x003e:
            com.google.protobuf.MessageLite r1 = r8.defaultInstance     // Catch:{ all -> 0x06f6 }
            r6 = r20
            java.lang.Object r1 = r6.findExtensionByNumber(r12, r1, r15)     // Catch:{ all -> 0x06f6 }
        L_0x0046:
            r16 = r1
            if (r16 == 0) goto L_0x0070
            if (r14 != 0) goto L_0x0058
            com.google.protobuf.FieldSet r1 = r20.getMutableExtensions(r21)     // Catch:{ all -> 0x0052 }
            r14 = r1
            goto L_0x0058
        L_0x0052:
            r0 = move-exception
            r1 = r0
            r17 = r14
            goto L_0x06fa
        L_0x0058:
            r1 = r20
            r2 = r22
            r3 = r16
            r4 = r23
            r5 = r14
            r6 = r13
            r17 = r14
            r14 = r7
            r7 = r19
            java.lang.Object r1 = r1.parseExtension(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x06f3 }
            r13 = r1
            r14 = r17
            goto L_0x000e
        L_0x0070:
            r17 = r14
            r14 = r7
            boolean r1 = r9.shouldDiscardUnknownFields(r11)     // Catch:{ all -> 0x06f3 }
            if (r1 == 0) goto L_0x0082
            boolean r1 = r22.skipField()     // Catch:{ all -> 0x06f3 }
            if (r1 == 0) goto L_0x0093
            r14 = r17
            goto L_0x000e
        L_0x0082:
            if (r13 != 0) goto L_0x0089
            java.lang.Object r1 = r9.getBuilderFromMessage(r10)     // Catch:{ all -> 0x06f3 }
            r13 = r1
        L_0x0089:
            boolean r1 = r9.mergeOneFieldFrom(r13, r11)     // Catch:{ all -> 0x06f3 }
            if (r1 == 0) goto L_0x0093
            r14 = r17
            goto L_0x000e
        L_0x0093:
            int r1 = r8.checkInitializedCount
        L_0x0095:
            int r2 = r8.repeatedFieldOffsetStart
            if (r1 >= r2) goto L_0x00a4
            int[] r2 = r8.intArray
            r2 = r2[r1]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r2, r13, r9)
            int r1 = r1 + 1
            goto L_0x0095
        L_0x00a4:
            if (r13 == 0) goto L_0x00a9
            r9.setBuilderToMessage(r10, r13)
        L_0x00a9:
            return
        L_0x00aa:
            r17 = r14
            r14 = r7
            int r1 = r8.typeAndOffsetAt(r14)     // Catch:{ all -> 0x06f3 }
            r7 = r1
            int r1 = type(r7)     // Catch:{ InvalidWireTypeException -> 0x06a5 }
            switch(r1) {
                case 0: goto L_0x0673;
                case 1: goto L_0x0663;
                case 2: goto L_0x0653;
                case 3: goto L_0x0643;
                case 4: goto L_0x0633;
                case 5: goto L_0x0622;
                case 6: goto L_0x0611;
                case 7: goto L_0x0600;
                case 8: goto L_0x05f7;
                case 9: goto L_0x05bd;
                case 10: goto L_0x05ac;
                case 11: goto L_0x059b;
                case 12: goto L_0x0575;
                case 13: goto L_0x0564;
                case 14: goto L_0x0553;
                case 15: goto L_0x0542;
                case 16: goto L_0x0531;
                case 17: goto L_0x04f7;
                case 18: goto L_0x04e7;
                case 19: goto L_0x04d7;
                case 20: goto L_0x04c7;
                case 21: goto L_0x04b7;
                case 22: goto L_0x04a7;
                case 23: goto L_0x0497;
                case 24: goto L_0x0487;
                case 25: goto L_0x0477;
                case 26: goto L_0x046e;
                case 27: goto L_0x0451;
                case 28: goto L_0x043e;
                case 29: goto L_0x042b;
                case 30: goto L_0x040e;
                case 31: goto L_0x03fb;
                case 32: goto L_0x03e8;
                case 33: goto L_0x03d5;
                case 34: goto L_0x03c2;
                case 35: goto L_0x03af;
                case 36: goto L_0x039c;
                case 37: goto L_0x0389;
                case 38: goto L_0x0376;
                case 39: goto L_0x0363;
                case 40: goto L_0x0350;
                case 41: goto L_0x033d;
                case 42: goto L_0x032a;
                case 43: goto L_0x0317;
                case 44: goto L_0x02fa;
                case 45: goto L_0x02e7;
                case 46: goto L_0x02d4;
                case 47: goto L_0x02c1;
                case 48: goto L_0x02ae;
                case 49: goto L_0x028f;
                case 50: goto L_0x0277;
                case 51: goto L_0x0261;
                case 52: goto L_0x024b;
                case 53: goto L_0x0235;
                case 54: goto L_0x021f;
                case 55: goto L_0x0209;
                case 56: goto L_0x01f3;
                case 57: goto L_0x01dd;
                case 58: goto L_0x01c7;
                case 59: goto L_0x01be;
                case 60: goto L_0x0182;
                case 61: goto L_0x0171;
                case 62: goto L_0x015b;
                case 63: goto L_0x0130;
                case 64: goto L_0x011a;
                case 65: goto L_0x0104;
                case 66: goto L_0x00ee;
                case 67: goto L_0x00d8;
                case 68: goto L_0x00c2;
                default: goto L_0x00b9;
            }
        L_0x00b9:
            r1 = r7
            if (r13 != 0) goto L_0x0687
            java.lang.Object r2 = r19.newBuilder()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x0686
        L_0x00c2:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.Schema r3 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Object r3 = r11.readGroupBySchemaWithCheck(r3, r12)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x00d8:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r3 = r22.readSInt64()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x00ee:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            int r3 = r22.readSInt32()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0104:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r3 = r22.readSFixed64()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x011a:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            int r3 = r22.readSFixed32()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0130:
            int r1 = r22.readEnum()     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.Internal$EnumVerifier r2 = r8.getEnumFieldVerifier(r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            if (r2 == 0) goto L_0x014a
            boolean r3 = r2.isInRange(r1)     // Catch:{ InvalidWireTypeException -> 0x028a }
            if (r3 == 0) goto L_0x0141
            goto L_0x014a
        L_0x0141:
            java.lang.Object r3 = com.google.protobuf.SchemaUtil.storeUnknownEnum(r15, r1, r13, r9)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r13 = r3
            r1 = r7
            goto L_0x06a4
        L_0x014a:
            long r3 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r3, r5)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x015b:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            int r3 = r22.readUInt32()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0171:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.ByteString r3 = r22.readBytes()     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0182:
            boolean r1 = r8.isOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            if (r1 == 0) goto L_0x01a5
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Object r1 = com.google.protobuf.UnsafeUtil.getObject(r10, r1)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.Schema r2 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Object r2 = r11.readMessageBySchemaWithCheck(r2, r12)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Object r1 = com.google.protobuf.Internal.mergeMessage(r1, r2)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r2 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r2, r1)     // Catch:{ InvalidWireTypeException -> 0x028a }
            goto L_0x01b8
        L_0x01a5:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.Schema r3 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Object r3 = r11.readMessageBySchemaWithCheck(r3, r12)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
        L_0x01b8:
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x01be:
            r8.readString(r10, r7, r11)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x01c7:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            boolean r3 = r22.readBool()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x01dd:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            int r3 = r22.readFixed32()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x01f3:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r3 = r22.readFixed64()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0209:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            int r3 = r22.readInt32()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x021f:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r3 = r22.readUInt64()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0235:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            long r3 = r22.readInt64()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x024b:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            float r3 = r22.readFloat()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Float r3 = java.lang.Float.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0261:
            long r1 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x028a }
            double r3 = r22.readDouble()     // Catch:{ InvalidWireTypeException -> 0x028a }
            java.lang.Double r3 = java.lang.Double.valueOf(r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            com.google.protobuf.UnsafeUtil.putObject(r10, r1, r3)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r8.setOneofPresent(r10, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x0277:
            java.lang.Object r4 = r8.getMapFieldDefaultEntry(r14)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r18
            r2 = r21
            r3 = r14
            r5 = r23
            r6 = r22
            r1.mergeMap(r2, r3, r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x028a }
            r1 = r7
            goto L_0x06a4
        L_0x028a:
            r0 = move-exception
            r2 = r0
            r1 = r7
            goto L_0x06a8
        L_0x028f:
            long r3 = offset(r7)     // Catch:{ InvalidWireTypeException -> 0x02a9 }
            com.google.protobuf.Schema r6 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x02a9 }
            r1 = r18
            r2 = r21
            r5 = r22
            r16 = r7
            r7 = r23
            r1.readGroupList(r2, r3, r5, r6, r7)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x02a9:
            r0 = move-exception
            r2 = r0
            r1 = r7
            goto L_0x06a8
        L_0x02ae:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSInt64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x02c1:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSInt32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x02d4:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSFixed64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x02e7:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSFixed32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x02fa:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readEnumList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            com.google.protobuf.Internal$EnumVerifier r2 = r8.getEnumFieldVerifier(r14)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.lang.Object r2 = com.google.protobuf.SchemaUtil.filterUnknownEnumList(r15, r1, r2, r13, r9)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r13 = r2
            r1 = r16
            goto L_0x06a4
        L_0x0317:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readUInt32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x032a:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readBoolList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x033d:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readFixed32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0350:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readFixed64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0363:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readInt32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0376:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readUInt64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0389:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readInt64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x039c:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readFloatList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x03af:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readDoubleList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x03c2:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSInt64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x03d5:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSInt32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x03e8:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSFixed64List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x03fb:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readSFixed32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x040e:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readEnumList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            com.google.protobuf.Internal$EnumVerifier r2 = r8.getEnumFieldVerifier(r14)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.lang.Object r2 = com.google.protobuf.SchemaUtil.filterUnknownEnumList(r15, r1, r2, r13, r9)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r13 = r2
            r1 = r16
            goto L_0x06a4
        L_0x042b:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readUInt32List(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x043e:
            r16 = r7
            com.google.protobuf.ListFieldSchema r1 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0468 }
            long r2 = offset(r16)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            java.util.List r1 = r1.mutableListAt(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r11.readBytesList(r1)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0451:
            r16 = r7
            com.google.protobuf.Schema r5 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r18
            r2 = r21
            r3 = r16
            r4 = r22
            r6 = r23
            r1.readMessageList(r2, r3, r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x0468 }
            r1 = r16
            goto L_0x06a4
        L_0x0468:
            r0 = move-exception
            r2 = r0
            r1 = r16
            goto L_0x06a8
        L_0x046e:
            r16 = r7
            r1 = r16
            r8.readStringList(r10, r1, r11)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0477:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readBoolList(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0487:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readFixed32List(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0497:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readFixed64List(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04a7:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readInt32List(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04b7:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readUInt64List(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04c7:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readInt64List(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04d7:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readFloatList(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04e7:
            r1 = r7
            com.google.protobuf.ListFieldSchema r2 = r8.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.util.List r2 = r2.mutableListAt(r10, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r11.readDoubleList(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x04f7:
            r1 = r7
            boolean r2 = r8.isFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            if (r2 == 0) goto L_0x051c
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r2 = com.google.protobuf.UnsafeUtil.getObject(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.Schema r3 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r3 = r11.readGroupBySchemaWithCheck(r3, r12)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r2 = com.google.protobuf.Internal.mergeMessage(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putObject(r10, r3, r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x051c:
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.Schema r4 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r4 = r11.readGroupBySchemaWithCheck(r4, r12)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putObject(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0531:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r4 = r22.readSInt64()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putLong(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0542:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            int r4 = r22.readSInt32()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0553:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r4 = r22.readSFixed64()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putLong(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0564:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            int r4 = r22.readSFixed32()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0575:
            r1 = r7
            int r2 = r22.readEnum()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.Internal$EnumVerifier r3 = r8.getEnumFieldVerifier(r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            if (r3 == 0) goto L_0x058f
            boolean r4 = r3.isInRange(r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            if (r4 == 0) goto L_0x0587
            goto L_0x058f
        L_0x0587:
            java.lang.Object r4 = com.google.protobuf.SchemaUtil.storeUnknownEnum(r15, r2, r13, r9)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r13 = r4
            goto L_0x06a4
        L_0x058f:
            long r4 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r4, r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x059b:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            int r4 = r22.readUInt32()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x05ac:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.ByteString r4 = r22.readBytes()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putObject(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x05bd:
            r1 = r7
            boolean r2 = r8.isFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            if (r2 == 0) goto L_0x05e2
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r2 = com.google.protobuf.UnsafeUtil.getObject(r10, r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.Schema r3 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r3 = r11.readMessageBySchemaWithCheck(r3, r12)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r2 = com.google.protobuf.Internal.mergeMessage(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r3 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putObject(r10, r3, r2)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x05e2:
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.Schema r4 = r8.getMessageFieldSchema(r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            java.lang.Object r4 = r11.readMessageBySchemaWithCheck(r4, r12)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putObject(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x05f7:
            r1 = r7
            r8.readString(r10, r1, r11)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0600:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            boolean r4 = r22.readBool()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putBoolean(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0611:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            int r4 = r22.readFixed32()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0622:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r4 = r22.readFixed64()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putLong(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0633:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            int r4 = r22.readInt32()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putInt(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0643:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r4 = r22.readUInt64()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putLong(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0653:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            long r4 = r22.readInt64()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putLong(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0663:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            float r4 = r22.readFloat()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putFloat(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0673:
            r1 = r7
            long r2 = offset(r1)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            double r4 = r22.readDouble()     // Catch:{ InvalidWireTypeException -> 0x0683 }
            com.google.protobuf.UnsafeUtil.putDouble(r10, r2, r4)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            r8.setFieldPresent(r10, r14)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            goto L_0x06a4
        L_0x0683:
            r0 = move-exception
            r2 = r0
            goto L_0x06a8
        L_0x0686:
            r13 = r2
        L_0x0687:
            boolean r2 = r9.mergeOneFieldFrom(r13, r11)     // Catch:{ InvalidWireTypeException -> 0x0683 }
            if (r2 != 0) goto L_0x06a4
            int r2 = r8.checkInitializedCount
        L_0x068f:
            int r3 = r8.repeatedFieldOffsetStart
            if (r2 >= r3) goto L_0x069e
            int[] r3 = r8.intArray
            r3 = r3[r2]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r3, r13, r9)
            int r2 = r2 + 1
            goto L_0x068f
        L_0x069e:
            if (r13 == 0) goto L_0x06a3
            r9.setBuilderToMessage(r10, r13)
        L_0x06a3:
            return
        L_0x06a4:
            goto L_0x06ef
        L_0x06a5:
            r0 = move-exception
            r1 = r7
            r2 = r0
        L_0x06a8:
            boolean r3 = r9.shouldDiscardUnknownFields(r11)     // Catch:{ all -> 0x06f3 }
            if (r3 == 0) goto L_0x06cb
            boolean r3 = r22.skipField()     // Catch:{ all -> 0x06f3 }
            if (r3 != 0) goto L_0x06ef
            int r3 = r8.checkInitializedCount
        L_0x06b6:
            int r4 = r8.repeatedFieldOffsetStart
            if (r3 >= r4) goto L_0x06c5
            int[] r4 = r8.intArray
            r4 = r4[r3]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r4, r13, r9)
            int r3 = r3 + 1
            goto L_0x06b6
        L_0x06c5:
            if (r13 == 0) goto L_0x06ca
            r9.setBuilderToMessage(r10, r13)
        L_0x06ca:
            return
        L_0x06cb:
            if (r13 != 0) goto L_0x06d2
            java.lang.Object r3 = r9.getBuilderFromMessage(r10)     // Catch:{ all -> 0x06f3 }
            r13 = r3
        L_0x06d2:
            boolean r3 = r9.mergeOneFieldFrom(r13, r11)     // Catch:{ all -> 0x06f3 }
            if (r3 != 0) goto L_0x06ef
            int r3 = r8.checkInitializedCount
        L_0x06da:
            int r4 = r8.repeatedFieldOffsetStart
            if (r3 >= r4) goto L_0x06e9
            int[] r4 = r8.intArray
            r4 = r4[r3]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r4, r13, r9)
            int r3 = r3 + 1
            goto L_0x06da
        L_0x06e9:
            if (r13 == 0) goto L_0x06ee
            r9.setBuilderToMessage(r10, r13)
        L_0x06ee:
            return
        L_0x06ef:
            r14 = r17
            goto L_0x000e
        L_0x06f3:
            r0 = move-exception
            r1 = r0
            goto L_0x06fa
        L_0x06f6:
            r0 = move-exception
            r17 = r14
            r1 = r0
        L_0x06fa:
            int r2 = r8.checkInitializedCount
        L_0x06fc:
            int r3 = r8.repeatedFieldOffsetStart
            if (r2 >= r3) goto L_0x070b
            int[] r3 = r8.intArray
            r3 = r3[r2]
            java.lang.Object r13 = r8.filterMapUnknownEnumValues(r10, r3, r13, r9)
            int r2 = r2 + 1
            goto L_0x06fc
        L_0x070b:
            if (r13 == 0) goto L_0x0710
            r9.setBuilderToMessage(r10, r13)
        L_0x0710:
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.mergeFromHelper(com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, java.lang.Object, com.google.protobuf.Reader, com.google.protobuf.ExtensionRegistryLite):void");
    }

    static UnknownFieldSetLite getMutableUnknownFields(Object message) {
        UnknownFieldSetLite unknownFields = ((GeneratedMessageLite) message).unknownFields;
        if (unknownFields != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFields;
        }
        UnknownFieldSetLite unknownFields2 = UnknownFieldSetLite.newInstance();
        ((GeneratedMessageLite) message).unknownFields = unknownFields2;
        return unknownFields2;
    }

    private int decodeMapEntryValue(byte[] data, int position, int limit, WireFormat.FieldType fieldType, Class<?> messageType, ArrayDecoders.Registers registers) throws IOException {
        switch (fieldType) {
            case BOOL:
                int position2 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return position2;
            case BYTES:
                return ArrayDecoders.decodeBytes(data, position, registers);
            case DOUBLE:
                registers.object1 = Double.valueOf(ArrayDecoders.decodeDouble(data, position));
                return position + 8;
            case FIXED32:
            case SFIXED32:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(data, position));
                return position + 4;
            case FIXED64:
            case SFIXED64:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(data, position));
                return position + 8;
            case FLOAT:
                registers.object1 = Float.valueOf(ArrayDecoders.decodeFloat(data, position));
                return position + 4;
            case ENUM:
            case INT32:
            case UINT32:
                int position3 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return position3;
            case INT64:
            case UINT64:
                int position4 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return position4;
            case MESSAGE:
                return ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor((Class) messageType), data, position, limit, registers);
            case SINT32:
                int position5 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return position5;
            case SINT64:
                int position6 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return position6;
            case STRING:
                return ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX INFO: Multiple debug info for r0v3 byte: [D('tag' int), D('position' int)] */
    /* JADX INFO: Multiple debug info for r11v4 int: [D('length' int), D('wireType' int)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <K, V> int decodeMapEntry(byte[] r19, int r20, int r21, com.google.protobuf.MapEntryLite.Metadata<K, V> r22, java.util.Map<K, V> r23, com.google.protobuf.ArrayDecoders.Registers r24) throws java.io.IOException {
        /*
            r18 = this;
            r7 = r19
            r8 = r21
            r9 = r22
            r10 = r24
            r0 = r20
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r0, r10)
            int r11 = r10.int1
            if (r11 < 0) goto L_0x00bc
            int r1 = r8 - r0
            if (r11 > r1) goto L_0x00bc
            int r12 = r0 + r11
            K r1 = r9.defaultKey
            V r2 = r9.defaultValue
            r13 = r1
            r14 = r2
        L_0x001e:
            if (r0 >= r12) goto L_0x00ab
            int r1 = r0 + 1
            byte r0 = r7[r0]
            if (r0 >= 0) goto L_0x002f
            int r1 = com.google.protobuf.ArrayDecoders.decodeVarint32(r0, r7, r1, r10)
            int r0 = r10.int1
            r15 = r0
            r6 = r1
            goto L_0x0031
        L_0x002f:
            r15 = r0
            r6 = r1
        L_0x0031:
            int r5 = r15 >>> 3
            r4 = r15 & 7
            r0 = 1
            if (r5 == r0) goto L_0x007b
            r0 = 2
            if (r5 == r0) goto L_0x0044
            r17 = r5
            r16 = r6
            r20 = r11
            r11 = r4
            goto L_0x00a1
        L_0x0044:
            com.google.protobuf.WireFormat$FieldType r0 = r9.valueType
            int r0 = r0.getWireType()
            if (r4 != r0) goto L_0x0073
            com.google.protobuf.WireFormat$FieldType r3 = r9.valueType
            V r0 = r9.defaultValue
            java.lang.Class r16 = r0.getClass()
            r0 = r18
            r1 = r19
            r2 = r6
            r17 = r3
            r3 = r21
            r20 = r11
            r11 = r4
            r4 = r17
            r17 = r5
            r5 = r16
            r16 = r6
            r6 = r24
            int r0 = r0.decodeMapEntryValue(r1, r2, r3, r4, r5, r6)
            java.lang.Object r14 = r10.object1
            r11 = r20
            goto L_0x001e
        L_0x0073:
            r17 = r5
            r16 = r6
            r20 = r11
            r11 = r4
            goto L_0x00a1
        L_0x007b:
            r17 = r5
            r16 = r6
            r20 = r11
            r11 = r4
            com.google.protobuf.WireFormat$FieldType r0 = r9.keyType
            int r0 = r0.getWireType()
            if (r11 != r0) goto L_0x00a1
            com.google.protobuf.WireFormat$FieldType r4 = r9.keyType
            r5 = 0
            r0 = r18
            r1 = r19
            r2 = r16
            r3 = r21
            r6 = r24
            int r0 = r0.decodeMapEntryValue(r1, r2, r3, r4, r5, r6)
            java.lang.Object r13 = r10.object1
            r11 = r20
            goto L_0x001e
        L_0x00a1:
            r1 = r16
            int r0 = com.google.protobuf.ArrayDecoders.skipField(r15, r7, r1, r8, r10)
            r11 = r20
            goto L_0x001e
        L_0x00ab:
            r20 = r11
            if (r0 != r12) goto L_0x00b5
            r1 = r23
            r1.put(r13, r14)
            return r12
        L_0x00b5:
            r1 = r23
            com.google.protobuf.InvalidProtocolBufferException r2 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r2
        L_0x00bc:
            r1 = r23
            r20 = r11
            com.google.protobuf.InvalidProtocolBufferException r2 = com.google.protobuf.InvalidProtocolBufferException.truncatedMessage()
            throw r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.decodeMapEntry(byte[], int, int, com.google.protobuf.MapEntryLite$Metadata, java.util.Map, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    private int parseRepeatedField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int bufferPosition, long typeAndOffset, int fieldType, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        Internal.ProtobufList<?> list;
        int position2;
        T t = message;
        byte[] bArr = data;
        int position3 = position;
        int i = wireType;
        int i2 = bufferPosition;
        long j = fieldOffset;
        ArrayDecoders.Registers registers2 = registers;
        Internal.ProtobufList<?> list2 = (Internal.ProtobufList) UNSAFE.getObject(t, j);
        if (!list2.isModifiable()) {
            int size = list2.size();
            Internal.ProtobufList<?> list3 = list2.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            UNSAFE.putObject(t, j, list3);
            list = list3;
        } else {
            list = list2;
        }
        switch (fieldType) {
            case 18:
            case 35:
                Internal.ProtobufList<?> list4 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedDoubleList(bArr, position3, list4, registers2);
                }
                if (i == 1) {
                    return ArrayDecoders.decodeDoubleList(tag, data, position, limit, list4, registers);
                }
                break;
            case 19:
            case 36:
                Internal.ProtobufList<?> list5 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedFloatList(bArr, position3, list5, registers2);
                }
                if (i == 5) {
                    return ArrayDecoders.decodeFloatList(tag, data, position, limit, list5, registers);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                Internal.ProtobufList<?> list6 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedVarint64List(bArr, position3, list6, registers2);
                }
                if (i == 0) {
                    return ArrayDecoders.decodeVarint64List(tag, data, position, limit, list6, registers);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                Internal.ProtobufList<?> list7 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedVarint32List(bArr, position3, list7, registers2);
                }
                if (i == 0) {
                    return ArrayDecoders.decodeVarint32List(tag, data, position, limit, list7, registers);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                Internal.ProtobufList<?> list8 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedFixed64List(bArr, position3, list8, registers2);
                }
                if (i == 1) {
                    return ArrayDecoders.decodeFixed64List(tag, data, position, limit, list8, registers);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                Internal.ProtobufList<?> list9 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedFixed32List(bArr, position3, list9, registers2);
                }
                if (i == 5) {
                    return ArrayDecoders.decodeFixed32List(tag, data, position, limit, list9, registers);
                }
                break;
            case 25:
            case 42:
                Internal.ProtobufList<?> list10 = list;
                if (i == 2) {
                    return ArrayDecoders.decodePackedBoolList(bArr, position3, list10, registers2);
                }
                if (i == 0) {
                    return ArrayDecoders.decodeBoolList(tag, data, position, limit, list10, registers);
                }
                break;
            case 26:
                Internal.ProtobufList<?> list11 = list;
                if (i == 2) {
                    if ((typeAndOffset & 536870912) == 0) {
                        return ArrayDecoders.decodeStringList(tag, data, position, limit, list11, registers);
                    }
                    return ArrayDecoders.decodeStringListRequireUtf8(tag, data, position, limit, list11, registers);
                }
                break;
            case 27:
                Internal.ProtobufList<?> list12 = list;
                if (i == 2) {
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i2), tag, data, position, limit, list12, registers);
                }
                break;
            case 28:
                Internal.ProtobufList<?> list13 = list;
                if (i == 2) {
                    return ArrayDecoders.decodeBytesList(tag, data, position, limit, list13, registers);
                }
                break;
            case 30:
            case 44:
                Internal.ProtobufList<?> list14 = list;
                if (i != 2) {
                    if (i != 0) {
                        break;
                    } else {
                        position2 = ArrayDecoders.decodeVarint32List(tag, data, position, limit, list14, registers);
                    }
                } else {
                    position2 = ArrayDecoders.decodePackedVarint32List(bArr, position3, list14, registers2);
                }
                UnknownFieldSetLite unknownFields = ((GeneratedMessageLite) t).unknownFields;
                if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFields = null;
                }
                UnknownFieldSetLite unknownFields2 = (UnknownFieldSetLite) SchemaUtil.filterUnknownEnumList(number, list14, getEnumFieldVerifier(i2), unknownFields, this.unknownFieldSchema);
                if (unknownFields2 == null) {
                    return position2;
                }
                ((GeneratedMessageLite) t).unknownFields = unknownFields2;
                return position2;
            case 33:
            case 47:
                Internal.ProtobufList<?> list15 = list;
                if (i != 2) {
                    if (i != 0) {
                        break;
                    } else {
                        return ArrayDecoders.decodeSInt32List(tag, data, position, limit, list15, registers);
                    }
                } else {
                    return ArrayDecoders.decodePackedSInt32List(bArr, position3, list15, registers2);
                }
            case 34:
            case 48:
                Internal.ProtobufList<?> list16 = list;
                if (i != 2) {
                    if (i != 0) {
                        break;
                    } else {
                        return ArrayDecoders.decodeSInt64List(tag, data, position, limit, list16, registers);
                    }
                } else {
                    return ArrayDecoders.decodePackedSInt64List(bArr, position3, list16, registers2);
                }
            case 49:
                if (i != 3) {
                    break;
                } else {
                    return ArrayDecoders.decodeGroupList(getMessageFieldSchema(i2), tag, data, position, limit, list, registers);
                }
        }
        return position3;
    }

    private <K, V> int parseMapField(T message, byte[] data, int position, int limit, int bufferPosition, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        Object mapField;
        T t = message;
        long j = fieldOffset;
        Unsafe unsafe = UNSAFE;
        Object mapDefaultEntry = getMapFieldDefaultEntry(bufferPosition);
        Object mapField2 = unsafe.getObject(t, j);
        if (this.mapFieldSchema.isImmutable(mapField2)) {
            Object oldMapField = mapField2;
            Object mapField3 = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField3, oldMapField);
            unsafe.putObject(t, j, mapField3);
            mapField = mapField3;
        } else {
            mapField = mapField2;
        }
        return decodeMapEntry(data, position, limit, this.mapFieldSchema.forMapMetadata(mapDefaultEntry), this.mapFieldSchema.forMutableMapData(mapField), registers);
    }

    /* JADX INFO: Multiple debug info for r4v1 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v1 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v2 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v2 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v3 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v3 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v4 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v4 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v5 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v5 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v6 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v6 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v7 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v7 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v8 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v8 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v9 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v9 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v10 sun.misc.Unsafe: [D('oneofCaseOffset' long), D('unsafe' sun.misc.Unsafe)] */
    /* JADX INFO: Multiple debug info for r6v10 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r6v11 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    /* JADX INFO: Multiple debug info for r6v12 long: [D('unsafe' sun.misc.Unsafe), D('oneofCaseOffset' long)] */
    private int parseOneofField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int typeAndOffset, int fieldType, long fieldOffset, int bufferPosition, ArrayDecoders.Registers registers) throws IOException {
        int position2;
        Unsafe unsafe;
        T t = message;
        byte[] bArr = data;
        int i = position;
        int i2 = tag;
        int i3 = number;
        int i4 = wireType;
        long j = fieldOffset;
        int i5 = bufferPosition;
        ArrayDecoders.Registers registers2 = registers;
        Unsafe unsafe2 = UNSAFE;
        long oneofCaseOffset = (long) (this.buffer[i5 + 2] & OFFSET_MASK);
        Object obj = null;
        switch (fieldType) {
            case 51:
                long j2 = oneofCaseOffset;
                Unsafe unsafe3 = unsafe2;
                position2 = i;
                long oneofCaseOffset2 = j2;
                if (i4 == 1) {
                    unsafe3.putObject(t, j, Double.valueOf(ArrayDecoders.decodeDouble(data, position)));
                    int position3 = position2 + 8;
                    unsafe3.putInt(t, oneofCaseOffset2, i3);
                    return position3;
                }
                break;
            case 52:
                long j3 = oneofCaseOffset;
                Unsafe unsafe4 = unsafe2;
                position2 = i;
                long oneofCaseOffset3 = j3;
                if (i4 == 5) {
                    unsafe4.putObject(t, j, Float.valueOf(ArrayDecoders.decodeFloat(data, position)));
                    int position4 = position2 + 4;
                    unsafe4.putInt(t, oneofCaseOffset3, i3);
                    return position4;
                }
                break;
            case 53:
            case 54:
                long j4 = oneofCaseOffset;
                Unsafe unsafe5 = unsafe2;
                position2 = i;
                byte[] bArr2 = bArr;
                ArrayDecoders.Registers registers3 = registers2;
                long oneofCaseOffset4 = j4;
                if (i4 == 0) {
                    int position5 = ArrayDecoders.decodeVarint64(bArr2, position2, registers3);
                    unsafe5.putObject(t, j, Long.valueOf(registers3.long1));
                    unsafe5.putInt(t, oneofCaseOffset4, i3);
                    return position5;
                }
                break;
            case 55:
            case 62:
                long j5 = oneofCaseOffset;
                Unsafe unsafe6 = unsafe2;
                position2 = i;
                byte[] bArr3 = bArr;
                ArrayDecoders.Registers registers4 = registers2;
                long oneofCaseOffset5 = j5;
                if (i4 == 0) {
                    int position6 = ArrayDecoders.decodeVarint32(bArr3, position2, registers4);
                    unsafe6.putObject(t, j, Integer.valueOf(registers4.int1));
                    unsafe6.putInt(t, oneofCaseOffset5, i3);
                    return position6;
                }
                break;
            case 56:
            case 65:
                long j6 = oneofCaseOffset;
                Unsafe unsafe7 = unsafe2;
                position2 = i;
                long oneofCaseOffset6 = j6;
                if (i4 == 1) {
                    unsafe7.putObject(t, j, Long.valueOf(ArrayDecoders.decodeFixed64(data, position)));
                    int position7 = position2 + 8;
                    unsafe7.putInt(t, oneofCaseOffset6, i3);
                    return position7;
                }
                break;
            case 57:
            case 64:
                long j7 = oneofCaseOffset;
                Unsafe unsafe8 = unsafe2;
                position2 = i;
                long oneofCaseOffset7 = j7;
                if (i4 == 5) {
                    unsafe8.putObject(t, j, Integer.valueOf(ArrayDecoders.decodeFixed32(data, position)));
                    int position8 = position2 + 4;
                    unsafe8.putInt(t, oneofCaseOffset7, i3);
                    return position8;
                }
                break;
            case 58:
                long j8 = oneofCaseOffset;
                Unsafe unsafe9 = unsafe2;
                position2 = i;
                byte[] bArr4 = bArr;
                ArrayDecoders.Registers registers5 = registers2;
                long oneofCaseOffset8 = j8;
                if (i4 == 0) {
                    int position9 = ArrayDecoders.decodeVarint64(bArr4, position2, registers5);
                    unsafe9.putObject(t, j, Boolean.valueOf(registers5.long1 != 0));
                    unsafe9.putInt(t, oneofCaseOffset8, i3);
                    return position9;
                }
                break;
            case 59:
                long j9 = oneofCaseOffset;
                Unsafe unsafe10 = unsafe2;
                position2 = i;
                byte[] bArr5 = bArr;
                ArrayDecoders.Registers registers6 = registers2;
                long oneofCaseOffset9 = j9;
                if (i4 == 2) {
                    int position10 = ArrayDecoders.decodeVarint32(bArr5, position2, registers6);
                    int length = registers6.int1;
                    if (length == 0) {
                        unsafe10.putObject(t, j, "");
                    } else if ((typeAndOffset & 536870912) == 0 || Utf8.isValidUtf8(bArr5, position10, position10 + length)) {
                        unsafe10.putObject(t, j, new String(bArr5, position10, length, Internal.UTF_8));
                        position10 += length;
                    } else {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    unsafe10.putInt(t, oneofCaseOffset9, i3);
                    return position10;
                }
                break;
            case 60:
                long j10 = oneofCaseOffset;
                Unsafe unsafe11 = unsafe2;
                position2 = i;
                byte[] bArr6 = bArr;
                ArrayDecoders.Registers registers7 = registers2;
                long oneofCaseOffset10 = j10;
                if (i4 != 2) {
                    break;
                } else {
                    int position11 = ArrayDecoders.decodeMessageField(getMessageFieldSchema(i5), bArr6, position2, limit, registers7);
                    if (unsafe11.getInt(t, oneofCaseOffset10) == i3) {
                        obj = unsafe11.getObject(t, j);
                    }
                    Object oldValue = obj;
                    if (oldValue == null) {
                        unsafe11.putObject(t, j, registers7.object1);
                    } else {
                        unsafe11.putObject(t, j, Internal.mergeMessage(oldValue, registers7.object1));
                    }
                    unsafe11.putInt(t, oneofCaseOffset10, i3);
                    return position11;
                }
            case 61:
                ArrayDecoders.Registers registers8 = registers2;
                long j11 = oneofCaseOffset;
                Unsafe unsafe12 = unsafe2;
                long oneofCaseOffset11 = j11;
                position2 = i;
                if (i4 != 2) {
                    break;
                } else {
                    int position12 = ArrayDecoders.decodeBytes(data, position2, registers8);
                    unsafe12.putObject(t, j, registers8.object1);
                    unsafe12.putInt(t, oneofCaseOffset11, i3);
                    return position12;
                }
            case 63:
                Unsafe unsafe13 = unsafe2;
                ArrayDecoders.Registers registers9 = registers2;
                long oneofCaseOffset12 = oneofCaseOffset;
                byte[] bArr7 = bArr;
                position2 = i;
                if (i4 != 0) {
                    break;
                } else {
                    int position13 = ArrayDecoders.decodeVarint32(bArr7, position2, registers9);
                    int enumValue = registers9.int1;
                    Internal.EnumVerifier enumVerifier = getEnumFieldVerifier(i5);
                    if (enumVerifier == null) {
                        unsafe = unsafe13;
                    } else if (enumVerifier.isInRange(enumValue)) {
                        unsafe = unsafe13;
                    } else {
                        unsafe = unsafe13;
                        getMutableUnknownFields(message).storeField(tag, Long.valueOf((long) enumValue));
                        return position13;
                    }
                    unsafe.putObject(t, j, Integer.valueOf(enumValue));
                    unsafe.putInt(t, oneofCaseOffset12, i3);
                    return position13;
                }
            case 66:
                Unsafe unsafe14 = unsafe2;
                ArrayDecoders.Registers registers10 = registers2;
                long oneofCaseOffset13 = oneofCaseOffset;
                byte[] bArr8 = bArr;
                position2 = i;
                if (i4 != 0) {
                    break;
                } else {
                    int position14 = ArrayDecoders.decodeVarint32(bArr8, position2, registers10);
                    unsafe14.putObject(t, j, Integer.valueOf(CodedInputStream.decodeZigZag32(registers10.int1)));
                    unsafe14.putInt(t, oneofCaseOffset13, i3);
                    return position14;
                }
            case 67:
                long oneofCaseOffset14 = oneofCaseOffset;
                Unsafe unsafe15 = unsafe2;
                ArrayDecoders.Registers registers11 = registers2;
                if (i4 != 0) {
                    position2 = position;
                    break;
                } else {
                    int position15 = ArrayDecoders.decodeVarint64(data, position, registers11);
                    unsafe15.putObject(t, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers11.long1)));
                    unsafe15.putInt(t, oneofCaseOffset14, i3);
                    return position15;
                }
            case 68:
                if (i4 != 3) {
                    position2 = position;
                    break;
                } else {
                    int endTag = (i2 & -8) | 4;
                    long oneofCaseOffset15 = oneofCaseOffset;
                    Unsafe unsafe16 = unsafe2;
                    ArrayDecoders.Registers registers12 = registers2;
                    int position16 = ArrayDecoders.decodeGroupField(getMessageFieldSchema(i5), data, position, limit, endTag, registers);
                    if (unsafe16.getInt(t, oneofCaseOffset15) == i3) {
                        obj = unsafe16.getObject(t, j);
                    }
                    Object oldValue2 = obj;
                    if (oldValue2 == null) {
                        unsafe16.putObject(t, j, registers12.object1);
                    } else {
                        unsafe16.putObject(t, j, Internal.mergeMessage(oldValue2, registers12.object1));
                    }
                    unsafe16.putInt(t, oneofCaseOffset15, i3);
                    return position16;
                }
            default:
                position2 = i;
                break;
        }
        return position2;
    }

    private Schema getMessageFieldSchema(int pos) {
        int index = (pos / 3) * 2;
        Schema schema = (Schema) this.objects[index];
        if (schema != null) {
            return schema;
        }
        Schema schema2 = Protobuf.getInstance().schemaFor((Class) this.objects[index + 1]);
        this.objects[index] = schema2;
        return schema2;
    }

    private Object getMapFieldDefaultEntry(int pos) {
        return this.objects[(pos / 3) * 2];
    }

    private Internal.EnumVerifier getEnumFieldVerifier(int pos) {
        return (Internal.EnumVerifier) this.objects[((pos / 3) * 2) + 1];
    }

    /* JADX INFO: Multiple debug info for r0v5 byte: [D('tag' int), D('position' int)] */
    /* JADX INFO: Multiple debug info for r1v4 int: [D('position' int), D('number' int)] */
    /* JADX INFO: Multiple debug info for r6v4 sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v7 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v5 int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r19v1 int: [D('unsafe' sun.misc.Unsafe), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r14v5 int: [D('fieldType' int), D('oldPosition' int)] */
    /* JADX INFO: Multiple debug info for r19v3 int: [D('unsafe' sun.misc.Unsafe), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r19v6 sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r6v11 'unsafe'  sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v13 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v20 'position'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r6v12 'unsafe'  sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v15 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v22 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r6v13 'unsafe'  sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v17 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v24 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r6v14 'unsafe'  sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v19 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v27 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r10v21 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v29 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r3v28 int: [D('tag' int), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r6v16 sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('pos' int)] */
    /* JADX INFO: Multiple debug info for r10v23 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v31 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r3v30 int: [D('tag' int), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r10v25 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v33 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r3v32 int: [D('tag' int), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r10v27 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v35 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r3v34 int: [D('tag' int), D('fieldType' int)] */
    /* JADX INFO: Multiple debug info for r10v29 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v37 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r10v31 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v39 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r10v33 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v41 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r10v36 long: [D('unsafe' sun.misc.Unsafe), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v45 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r8v47 'currentPresenceFieldOffset'  int: [D('position' int), D('currentPresenceFieldOffset' int)] */
    /* JADX INFO: Multiple debug info for r8v51 int: [D('fieldType' int), D('currentPresenceFieldOffset' int)] */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.lang.Object] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseProto2Message(T r33, byte[] r34, int r35, int r36, int r37, com.google.protobuf.ArrayDecoders.Registers r38) throws java.io.IOException {
        /*
            r32 = this;
            r15 = r32
            r14 = r33
            r12 = r34
            r13 = r36
            r11 = r37
            r9 = r38
            sun.misc.Unsafe r10 = com.google.protobuf.MessageSchema.UNSAFE
            r0 = -1
            r1 = 0
            r2 = 0
            r3 = -1
            r4 = 0
            r8 = r0
            r7 = r1
            r0 = r35
        L_0x0017:
            if (r0 >= r13) goto L_0x0612
            int r1 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0028
            int r1 = com.google.protobuf.ArrayDecoders.decodeVarint32(r0, r12, r1, r9)
            int r0 = r9.int1
            r5 = r0
            r2 = r1
            goto L_0x002a
        L_0x0028:
            r5 = r0
            r2 = r1
        L_0x002a:
            int r1 = r5 >>> 3
            r0 = r5 & 7
            if (r1 <= r3) goto L_0x0038
            int r6 = r4 / 3
            int r4 = r15.positionForFieldNumber(r1, r6)
            r6 = r4
            goto L_0x003d
        L_0x0038:
            int r4 = r15.positionForFieldNumber(r1)
            r6 = r4
        L_0x003d:
            r16 = r1
            r3 = -1
            if (r6 != r3) goto L_0x0052
            r3 = 0
            r15 = r0
            r24 = r1
            r26 = r2
            r21 = r3
            r35 = r5
            r23 = r7
            r29 = r10
            goto L_0x05b2
        L_0x0052:
            int[] r3 = r15.buffer
            int r4 = r6 + 1
            r4 = r3[r4]
            int r3 = type(r4)
            long r11 = offset(r4)
            r17 = r2
            r2 = 17
            r18 = r4
            if (r3 > r2) goto L_0x043e
            int[] r2 = r15.buffer
            int r19 = r6 + 2
            r19 = r2[r19]
            int r2 = r19 >>> 20
            r4 = 1
            int r21 = r4 << r2
            r2 = 1048575(0xfffff, float:1.469367E-39)
            r2 = r19 & r2
            if (r2 == r8) goto L_0x008f
            r4 = -1
            if (r8 == r4) goto L_0x0084
            r35 = r5
            long r4 = (long) r8
            r10.putInt(r14, r4, r7)
            goto L_0x0086
        L_0x0084:
            r35 = r5
        L_0x0086:
            r4 = r2
            r8 = r4
            long r4 = (long) r2
            int r4 = r10.getInt(r14, r4)
            r7 = r4
            goto L_0x0091
        L_0x008f:
            r35 = r5
        L_0x0091:
            r4 = 5
            switch(r3) {
                case 0: goto L_0x0403;
                case 1: goto L_0x03d6;
                case 2: goto L_0x03a4;
                case 3: goto L_0x03a4;
                case 4: goto L_0x0377;
                case 5: goto L_0x0332;
                case 6: goto L_0x02fd;
                case 7: goto L_0x02bb;
                case 8: goto L_0x027a;
                case 9: goto L_0x0227;
                case 10: goto L_0x01eb;
                case 11: goto L_0x0377;
                case 12: goto L_0x0189;
                case 13: goto L_0x02fd;
                case 14: goto L_0x0332;
                case 15: goto L_0x0151;
                case 16: goto L_0x010c;
                case 17: goto L_0x00a8;
                default: goto L_0x0095;
            }
        L_0x0095:
            r13 = r0
            r24 = r1
            r26 = r3
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            goto L_0x0431
        L_0x00a8:
            r4 = 3
            if (r0 != r4) goto L_0x00f9
            int r4 = r1 << 3
            r20 = r4 | 4
            com.google.protobuf.Schema r4 = r15.getMessageFieldSchema(r6)
            r5 = r0
            r0 = r4
            r24 = r1
            r1 = r34
            r4 = r17
            r17 = r2
            r2 = r4
            r25 = r8
            r8 = r3
            r3 = r36
            r26 = r8
            r8 = r4
            r4 = r20
            r13 = r5
            r5 = r38
            int r0 = com.google.protobuf.ArrayDecoders.decodeGroupField(r0, r1, r2, r3, r4, r5)
            r1 = r7 & r21
            if (r1 != 0) goto L_0x00da
            java.lang.Object r1 = r9.object1
            r10.putObject(r14, r11, r1)
            goto L_0x00e8
        L_0x00da:
            java.lang.Object r1 = r10.getObject(r14, r11)
            java.lang.Object r2 = r9.object1
            java.lang.Object r1 = com.google.protobuf.Internal.mergeMessage(r1, r2)
            r10.putObject(r14, r11, r1)
        L_0x00e8:
            r7 = r7 | r21
            r12 = r34
            r2 = r35
            r13 = r36
            r11 = r37
            r4 = r6
            r3 = r16
            r8 = r25
            goto L_0x0017
        L_0x00f9:
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r8 = r17
            r17 = r2
            r27 = r6
            r6 = r10
            r10 = r11
            r12 = r34
            goto L_0x0431
        L_0x010c:
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r8 = r17
            r17 = r2
            if (r13 != 0) goto L_0x0144
            r4 = r11
            r12 = r34
            int r8 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r8, r9)
            long r0 = r9.long1
            long r22 = com.google.protobuf.CodedInputStream.decodeZigZag64(r0)
            r0 = r10
            r1 = r33
            r2 = r4
            r27 = r10
            r10 = r4
            r4 = r22
            r0.putLong(r1, r2, r4)
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r4 = r6
            r0 = r8
            r3 = r16
            r8 = r25
            r10 = r27
            goto L_0x0017
        L_0x0144:
            r27 = r10
            r10 = r11
            r12 = r34
            r31 = r27
            r27 = r6
            r6 = r31
            goto L_0x0431
        L_0x0151:
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r27 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            if (r13 != 0) goto L_0x0182
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r8, r9)
            int r1 = r9.int1
            int r1 = com.google.protobuf.CodedInputStream.decodeZigZag32(r1)
            r5 = r27
            r5.putInt(r14, r10, r1)
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r5
            r4 = r6
            r3 = r16
            r8 = r25
            goto L_0x0017
        L_0x0182:
            r5 = r27
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x0189:
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            if (r13 != 0) goto L_0x01e2
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r8, r9)
            int r1 = r9.int1
            com.google.protobuf.Internal$EnumVerifier r2 = r15.getEnumFieldVerifier(r6)
            if (r2 == 0) goto L_0x01cc
            boolean r3 = r2.isInRange(r1)
            if (r3 == 0) goto L_0x01af
            r9 = r35
            goto L_0x01ce
        L_0x01af:
            com.google.protobuf.UnknownFieldSetLite r3 = getMutableUnknownFields(r33)
            long r8 = (long) r1
            java.lang.Long r4 = java.lang.Long.valueOf(r8)
            r9 = r35
            r3.storeField(r9, r4)
            r13 = r36
            r11 = r37
            r10 = r5
            r4 = r6
            r2 = r9
            r3 = r16
            r8 = r25
            r9 = r38
            goto L_0x0017
        L_0x01cc:
            r9 = r35
        L_0x01ce:
            r5.putInt(r14, r10, r1)
            r7 = r7 | r21
            r13 = r36
            r11 = r37
            r10 = r5
            r4 = r6
            r2 = r9
            r3 = r16
            r8 = r25
            r9 = r38
            goto L_0x0017
        L_0x01e2:
            r9 = r35
            r27 = r6
            r9 = r38
            r6 = r5
            goto L_0x0431
        L_0x01eb:
            r9 = r35
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            r0 = 2
            if (r13 != r0) goto L_0x021d
            r2 = r38
            int r0 = com.google.protobuf.ArrayDecoders.decodeBytes(r12, r8, r2)
            java.lang.Object r1 = r2.object1
            r5.putObject(r14, r10, r1)
            r7 = r7 | r21
            r13 = r36
            r11 = r37
            r10 = r5
            r4 = r6
            r3 = r16
            r8 = r25
            r31 = r9
            r9 = r2
            r2 = r31
            goto L_0x0017
        L_0x021d:
            r2 = r38
            r27 = r6
            r35 = r9
            r9 = r2
            r6 = r5
            goto L_0x0431
        L_0x0227:
            r13 = r0
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            r2 = r9
            r9 = r35
            r0 = 2
            if (r13 != r0) goto L_0x026f
            com.google.protobuf.Schema r0 = r15.getMessageFieldSchema(r6)
            r3 = r9
            r9 = r36
            int r0 = com.google.protobuf.ArrayDecoders.decodeMessageField(r0, r12, r8, r9, r2)
            r1 = r7 & r21
            if (r1 != 0) goto L_0x0252
            java.lang.Object r1 = r2.object1
            r5.putObject(r14, r10, r1)
            goto L_0x0260
        L_0x0252:
            java.lang.Object r1 = r5.getObject(r14, r10)
            java.lang.Object r4 = r2.object1
            java.lang.Object r1 = com.google.protobuf.Internal.mergeMessage(r1, r4)
            r5.putObject(r14, r10, r1)
        L_0x0260:
            r7 = r7 | r21
            r11 = r37
            r10 = r5
            r4 = r6
            r13 = r9
            r8 = r25
            r9 = r2
            r2 = r3
            r3 = r16
            goto L_0x0017
        L_0x026f:
            r3 = r9
            r9 = r36
            r9 = r2
            r35 = r3
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x027a:
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r3 = r35
            r17 = r2
            r2 = r9
            r9 = r13
            r13 = r0
            r0 = 2
            if (r13 != r0) goto L_0x02b3
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r18 & r0
            if (r0 != 0) goto L_0x029b
            int r0 = com.google.protobuf.ArrayDecoders.decodeString(r12, r8, r2)
            goto L_0x029f
        L_0x029b:
            int r0 = com.google.protobuf.ArrayDecoders.decodeStringRequireUtf8(r12, r8, r2)
        L_0x029f:
            java.lang.Object r1 = r2.object1
            r5.putObject(r14, r10, r1)
            r7 = r7 | r21
            r11 = r37
            r10 = r5
            r4 = r6
            r13 = r9
            r8 = r25
            r9 = r2
            r2 = r3
            r3 = r16
            goto L_0x0017
        L_0x02b3:
            r9 = r2
            r35 = r3
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x02bb:
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r3 = r35
            r17 = r2
            r2 = r9
            r9 = r13
            r13 = r0
            if (r13 != 0) goto L_0x02f5
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r8, r2)
            r35 = r0
            long r0 = r2.long1
            r27 = 0
            int r4 = (r0 > r27 ? 1 : (r0 == r27 ? 0 : -1))
            if (r4 == 0) goto L_0x02e0
            r4 = 1
            goto L_0x02e1
        L_0x02e0:
            r4 = 0
        L_0x02e1:
            com.google.protobuf.UnsafeUtil.putBoolean(r14, r10, r4)
            r7 = r7 | r21
            r0 = r35
            r11 = r37
            r10 = r5
            r4 = r6
            r13 = r9
            r8 = r25
            r9 = r2
            r2 = r3
            r3 = r16
            goto L_0x0017
        L_0x02f5:
            r9 = r2
            r35 = r3
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x02fd:
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r3 = r35
            r17 = r2
            r2 = r9
            r9 = r13
            r13 = r0
            if (r13 != r4) goto L_0x032a
            int r0 = com.google.protobuf.ArrayDecoders.decodeFixed32(r12, r8)
            r5.putInt(r14, r10, r0)
            int r0 = r8 + 4
            r7 = r7 | r21
            r11 = r37
            r10 = r5
            r4 = r6
            r13 = r9
            r8 = r25
            r9 = r2
            r2 = r3
            r3 = r16
            goto L_0x0017
        L_0x032a:
            r9 = r2
            r35 = r3
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x0332:
            r24 = r1
            r26 = r3
            r25 = r8
            r5 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r3 = r35
            r17 = r2
            r2 = r9
            r9 = r13
            r13 = r0
            r0 = 1
            if (r13 != r0) goto L_0x036f
            long r22 = com.google.protobuf.ArrayDecoders.decodeFixed64(r12, r8)
            r0 = r5
            r1 = r33
            r4 = r2
            r35 = r3
            r2 = r10
            r9 = r4
            r27 = r6
            r6 = r5
            r4 = r22
            r0.putLong(r1, r2, r4)
            int r0 = r8 + 8
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r6
            r3 = r16
            r8 = r25
            r4 = r27
            goto L_0x0017
        L_0x036f:
            r9 = r2
            r35 = r3
            r27 = r6
            r6 = r5
            goto L_0x0431
        L_0x0377:
            r13 = r0
            r24 = r1
            r26 = r3
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            if (r13 != 0) goto L_0x0431
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r8, r9)
            int r1 = r9.int1
            r6.putInt(r14, r10, r1)
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r6
            r3 = r16
            r8 = r25
            r4 = r27
            goto L_0x0017
        L_0x03a4:
            r13 = r0
            r24 = r1
            r26 = r3
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            if (r13 != 0) goto L_0x0431
            int r8 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r8, r9)
            long r4 = r9.long1
            r0 = r6
            r1 = r33
            r2 = r10
            r0.putLong(r1, r2, r4)
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r6
            r0 = r8
            r3 = r16
            r8 = r25
            r4 = r27
            goto L_0x0017
        L_0x03d6:
            r13 = r0
            r24 = r1
            r26 = r3
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            if (r13 != r4) goto L_0x0431
            float r0 = com.google.protobuf.ArrayDecoders.decodeFloat(r12, r8)
            com.google.protobuf.UnsafeUtil.putFloat(r14, r10, r0)
            int r0 = r8 + 4
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r6
            r3 = r16
            r8 = r25
            r4 = r27
            goto L_0x0017
        L_0x0403:
            r13 = r0
            r24 = r1
            r26 = r3
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r17 = r2
            r0 = 1
            if (r13 != r0) goto L_0x0431
            double r0 = com.google.protobuf.ArrayDecoders.decodeDouble(r12, r8)
            com.google.protobuf.UnsafeUtil.putDouble(r14, r10, r0)
            int r0 = r8 + 8
            r7 = r7 | r21
            r2 = r35
            r13 = r36
            r11 = r37
            r10 = r6
            r3 = r16
            r8 = r25
            r4 = r27
            goto L_0x0017
        L_0x0431:
            r29 = r6
            r23 = r7
            r26 = r8
            r15 = r13
            r8 = r25
            r21 = r27
            goto L_0x05b2
        L_0x043e:
            r13 = r0
            r24 = r1
            r26 = r3
            r35 = r5
            r27 = r6
            r25 = r8
            r6 = r10
            r10 = r11
            r8 = r17
            r12 = r34
            r0 = 27
            r5 = r26
            if (r5 != r0) goto L_0x04b9
            r0 = 2
            if (r13 != r0) goto L_0x04ab
            java.lang.Object r0 = r6.getObject(r14, r10)
            com.google.protobuf.Internal$ProtobufList r0 = (com.google.protobuf.Internal.ProtobufList) r0
            boolean r1 = r0.isModifiable()
            if (r1 != 0) goto L_0x047a
            int r1 = r0.size()
            if (r1 != 0) goto L_0x046e
            r2 = 10
            goto L_0x0470
        L_0x046e:
            int r2 = r1 * 2
        L_0x0470:
            com.google.protobuf.Internal$ProtobufList r0 = r0.mutableCopyWithCapacity(r2)
            r6.putObject(r14, r10, r0)
            r17 = r0
            goto L_0x047c
        L_0x047a:
            r17 = r0
        L_0x047c:
            r4 = r27
            com.google.protobuf.Schema r0 = r15.getMessageFieldSchema(r4)
            r1 = r35
            r2 = r34
            r3 = r8
            r19 = r4
            r4 = r36
            r9 = r5
            r5 = r17
            r21 = r19
            r19 = r6
            r6 = r38
            int r0 = com.google.protobuf.ArrayDecoders.decodeMessageList(r0, r1, r2, r3, r4, r5, r6)
            r2 = r35
            r13 = r36
            r11 = r37
            r9 = r38
            r3 = r16
            r10 = r19
            r4 = r21
            r8 = r25
            goto L_0x0017
        L_0x04ab:
            r9 = r5
            r19 = r6
            r21 = r27
            r23 = r7
            r26 = r8
            r15 = r13
            r29 = r19
            goto L_0x056b
        L_0x04b9:
            r9 = r5
            r19 = r6
            r21 = r27
            r0 = 49
            if (r9 > r0) goto L_0x051b
            r6 = r8
            r5 = r18
            long r3 = (long) r5
            r0 = r32
            r1 = r33
            r2 = r34
            r17 = r3
            r3 = r8
            r20 = -1
            r4 = r36
            r22 = r5
            r5 = r35
            r15 = r6
            r6 = r24
            r23 = r7
            r7 = r13
            r26 = r8
            r8 = r21
            r27 = r10
            r29 = r19
            r11 = r38
            r19 = r9
            r9 = r17
            r11 = r19
            r30 = r13
            r12 = r27
            r14 = r38
            int r0 = r0.parseRepeatedField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            if (r0 == r15) goto L_0x0513
            r15 = r32
            r14 = r33
            r12 = r34
            r2 = r35
            r13 = r36
            r11 = r37
            r9 = r38
            r3 = r16
            r4 = r21
            r7 = r23
            r8 = r25
            r10 = r29
            goto L_0x0017
        L_0x0513:
            r26 = r0
            r8 = r25
            r15 = r30
            goto L_0x05b2
        L_0x051b:
            r23 = r7
            r26 = r8
            r27 = r10
            r30 = r13
            r22 = r18
            r29 = r19
            r19 = r9
            r0 = 50
            r14 = r19
            if (r14 != r0) goto L_0x056e
            r15 = r30
            r0 = 2
            if (r15 != r0) goto L_0x056b
            r9 = r26
            r0 = r32
            r1 = r33
            r2 = r34
            r3 = r26
            r4 = r36
            r5 = r21
            r6 = r27
            r8 = r38
            int r0 = r0.parseMapField(r1, r2, r3, r4, r5, r6, r8)
            if (r0 == r9) goto L_0x0566
            r15 = r32
            r14 = r33
            r12 = r34
            r2 = r35
            r13 = r36
            r11 = r37
            r9 = r38
            r3 = r16
            r4 = r21
            r7 = r23
            r8 = r25
            r10 = r29
            goto L_0x0017
        L_0x0566:
            r26 = r0
            r8 = r25
            goto L_0x05b2
        L_0x056b:
            r8 = r25
            goto L_0x05b2
        L_0x056e:
            r15 = r30
            r13 = r26
            r0 = r32
            r1 = r33
            r2 = r34
            r3 = r26
            r4 = r36
            r5 = r35
            r6 = r24
            r7 = r15
            r8 = r22
            r9 = r14
            r10 = r27
            r12 = r21
            r19 = r14
            r14 = r13
            r13 = r38
            int r0 = r0.parseOneofField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == r14) goto L_0x05ae
            r15 = r32
            r14 = r33
            r12 = r34
            r2 = r35
            r13 = r36
            r11 = r37
            r9 = r38
            r3 = r16
            r4 = r21
            r7 = r23
            r8 = r25
            r10 = r29
            goto L_0x0017
        L_0x05ae:
            r26 = r0
            r8 = r25
        L_0x05b2:
            r10 = r35
            r9 = r37
            if (r10 != r9) goto L_0x05c6
            if (r9 == 0) goto L_0x05c6
            r11 = r32
            r12 = r38
            r4 = r21
            r0 = r23
            r1 = r26
            goto L_0x0621
        L_0x05c6:
            r11 = r32
            boolean r0 = r11.hasExtensions
            if (r0 == 0) goto L_0x05ea
            r12 = r38
            com.google.protobuf.ExtensionRegistryLite r0 = r12.extensionRegistry
            com.google.protobuf.ExtensionRegistryLite r1 = com.google.protobuf.ExtensionRegistryLite.getEmptyRegistry()
            if (r0 == r1) goto L_0x05ec
            com.google.protobuf.MessageLite r5 = r11.defaultInstance
            com.google.protobuf.UnknownFieldSchema<?, ?> r6 = r11.unknownFieldSchema
            r0 = r10
            r1 = r34
            r2 = r26
            r3 = r36
            r4 = r33
            r7 = r38
            int r0 = com.google.protobuf.ArrayDecoders.decodeExtensionOrUnknownField(r0, r1, r2, r3, r4, r5, r6, r7)
            goto L_0x05fe
        L_0x05ea:
            r12 = r38
        L_0x05ec:
            com.google.protobuf.UnknownFieldSetLite r4 = getMutableUnknownFields(r33)
            r0 = r10
            r1 = r34
            r2 = r26
            r3 = r36
            r5 = r38
            int r0 = com.google.protobuf.ArrayDecoders.decodeUnknownField(r0, r1, r2, r3, r4, r5)
        L_0x05fe:
            r14 = r33
            r13 = r36
            r2 = r10
            r15 = r11
            r3 = r16
            r4 = r21
            r7 = r23
            r10 = r29
            r11 = r9
            r9 = r12
            r12 = r34
            goto L_0x0017
        L_0x0612:
            r23 = r7
            r25 = r8
            r12 = r9
            r29 = r10
            r9 = r11
            r11 = r15
            r1 = r0
            r10 = r2
            r16 = r3
            r0 = r23
        L_0x0621:
            r2 = -1
            if (r8 == r2) goto L_0x062d
            long r2 = (long) r8
            r5 = r33
            r6 = r29
            r6.putInt(r5, r2, r0)
            goto L_0x0631
        L_0x062d:
            r5 = r33
            r6 = r29
        L_0x0631:
            r2 = 0
            int r3 = r11.checkInitializedCount
        L_0x0634:
            int r7 = r11.repeatedFieldOffsetStart
            if (r3 >= r7) goto L_0x0648
            int[] r7 = r11.intArray
            r7 = r7[r3]
            com.google.protobuf.UnknownFieldSchema<?, ?> r13 = r11.unknownFieldSchema
            java.lang.Object r7 = r11.filterMapUnknownEnumValues(r5, r7, r2, r13)
            r2 = r7
            com.google.protobuf.UnknownFieldSetLite r2 = (com.google.protobuf.UnknownFieldSetLite) r2
            int r3 = r3 + 1
            goto L_0x0634
        L_0x0648:
            if (r2 == 0) goto L_0x064f
            com.google.protobuf.UnknownFieldSchema<?, ?> r3 = r11.unknownFieldSchema
            r3.setBuilderToMessage(r5, r2)
        L_0x064f:
            if (r9 != 0) goto L_0x065b
            r3 = r36
            if (r1 != r3) goto L_0x0656
            goto L_0x0661
        L_0x0656:
            com.google.protobuf.InvalidProtocolBufferException r7 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r7
        L_0x065b:
            r3 = r36
            if (r1 > r3) goto L_0x0662
            if (r10 != r9) goto L_0x0662
        L_0x0661:
            return r1
        L_0x0662:
            com.google.protobuf.InvalidProtocolBufferException r7 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.parseProto2Message(java.lang.Object, byte[], int, int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    /* JADX INFO: Multiple debug info for r0v3 byte: [D('tag' int), D('position' int)] */
    /* JADX INFO: Multiple debug info for r8v1 long: [D('number' int), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r22v1 int: [D('number' int), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r14v6 int: [D('wireType' int), D('oldPosition' int)] */
    /* JADX INFO: Multiple debug info for r8v5 long: [D('number' int), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v6 long: [D('number' int), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r4v8 sun.misc.Unsafe: [D('unsafe' sun.misc.Unsafe), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r8v7 long: [D('number' int), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v8 long: [D('number' int), D('fieldOffset' long)] */
    /* JADX INFO: Multiple debug info for r8v9 int: [D('number' int), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v12 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v14 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v15 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r8v14 int: [D('number' int), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v16 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v17 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v18 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX INFO: Multiple debug info for r4v19 long: [D('fieldOffset' long), D('typeAndOffset' int)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int parseProto3Message(T r30, byte[] r31, int r32, int r33, com.google.protobuf.ArrayDecoders.Registers r34) throws java.io.IOException {
        /*
            r29 = this;
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            sun.misc.Unsafe r9 = com.google.protobuf.MessageSchema.UNSAFE
            r0 = 0
            r1 = -1
            r2 = 0
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r32
        L_0x0014:
            if (r0 >= r13) goto L_0x0413
            int r4 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0026
            int r1 = com.google.protobuf.ArrayDecoders.decodeVarint32(r0, r12, r4, r11)
            int r0 = r11.int1
            r16 = r0
            r10 = r1
            goto L_0x0029
        L_0x0026:
            r16 = r0
            r10 = r4
        L_0x0029:
            int r8 = r16 >>> 3
            r7 = r16 & 7
            if (r8 <= r2) goto L_0x0037
            int r0 = r3 / 3
            int r0 = r15.positionForFieldNumber(r8, r0)
            r6 = r0
            goto L_0x003c
        L_0x0037:
            int r0 = r15.positionForFieldNumber(r8)
            r6 = r0
        L_0x003c:
            r17 = r8
            r0 = -1
            if (r6 != r0) goto L_0x004c
            r0 = 0
            r19 = r0
            r32 = r7
            r18 = r8
            r23 = r9
            goto L_0x03ed
        L_0x004c:
            int[] r0 = r15.buffer
            int r1 = r6 + 1
            r4 = r0[r1]
            int r5 = type(r4)
            long r2 = offset(r4)
            r0 = 17
            r1 = 2
            if (r5 > r0) goto L_0x02b7
            r0 = 1
            switch(r5) {
                case 0: goto L_0x028f;
                case 1: goto L_0x0266;
                case 2: goto L_0x0233;
                case 3: goto L_0x0233;
                case 4: goto L_0x0208;
                case 5: goto L_0x01d3;
                case 6: goto L_0x01af;
                case 7: goto L_0x0183;
                case 8: goto L_0x0156;
                case 9: goto L_0x011d;
                case 10: goto L_0x00fa;
                case 11: goto L_0x0208;
                case 12: goto L_0x00d7;
                case 13: goto L_0x01af;
                case 14: goto L_0x01d3;
                case 15: goto L_0x00ab;
                case 16: goto L_0x0076;
                default: goto L_0x0063;
            }
        L_0x0063:
            r21 = r4
            r23 = r5
            r22 = r8
            r11 = r9
            r8 = r2
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r11
            r18 = r22
            goto L_0x03af
        L_0x0076:
            if (r7 != 0) goto L_0x0099
            int r10 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r10, r11)
            long r0 = r11.long1
            long r18 = com.google.protobuf.CodedInputStream.decodeZigZag64(r0)
            r0 = r9
            r1 = r30
            r20 = r2
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r18
            r0.putLong(r1, r2, r4)
            r3 = r6
            r0 = r10
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x0099:
            r20 = r2
            r23 = r5
            r22 = r8
            r8 = r4
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x00ab:
            r20 = r2
            r23 = r5
            r22 = r8
            r8 = r4
            if (r7 != 0) goto L_0x00ca
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r10, r11)
            int r1 = r11.int1
            int r1 = com.google.protobuf.CodedInputStream.decodeZigZag32(r1)
            r4 = r20
            r9.putInt(r14, r4, r1)
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x00ca:
            r4 = r20
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x00d7:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != 0) goto L_0x00ef
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r10, r11)
            int r1 = r11.int1
            r9.putInt(r14, r4, r1)
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x00ef:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x00fa:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != r1) goto L_0x0112
            int r0 = com.google.protobuf.ArrayDecoders.decodeBytes(r12, r10, r11)
            java.lang.Object r1 = r11.object1
            r9.putObject(r14, r4, r1)
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x0112:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x011d:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != r1) goto L_0x014b
            com.google.protobuf.Schema r0 = r15.getMessageFieldSchema(r6)
            int r0 = com.google.protobuf.ArrayDecoders.decodeMessageField(r0, r12, r10, r13, r11)
            java.lang.Object r1 = r9.getObject(r14, r4)
            if (r1 != 0) goto L_0x013a
            java.lang.Object r2 = r11.object1
            r9.putObject(r14, r4, r2)
            goto L_0x0144
        L_0x013a:
            java.lang.Object r2 = r11.object1
            java.lang.Object r2 = com.google.protobuf.Internal.mergeMessage(r1, r2)
            r9.putObject(r14, r4, r2)
        L_0x0144:
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x014b:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x0156:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != r1) goto L_0x0178
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r0 & r8
            if (r0 != 0) goto L_0x0168
            int r0 = com.google.protobuf.ArrayDecoders.decodeString(r12, r10, r11)
            goto L_0x016c
        L_0x0168:
            int r0 = com.google.protobuf.ArrayDecoders.decodeStringRequireUtf8(r12, r10, r11)
        L_0x016c:
            java.lang.Object r1 = r11.object1
            r9.putObject(r14, r4, r1)
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x0178:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x0183:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != 0) goto L_0x01a4
            int r1 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r10, r11)
            long r2 = r11.long1
            r18 = 0
            int r10 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r10 == 0) goto L_0x0198
            goto L_0x0199
        L_0x0198:
            r0 = 0
        L_0x0199:
            com.google.protobuf.UnsafeUtil.putBoolean(r14, r4, r0)
            r0 = r1
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x01a4:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x01af:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            r0 = 5
            if (r7 != r0) goto L_0x01c8
            int r0 = com.google.protobuf.ArrayDecoders.decodeFixed32(r12, r10)
            r9.putInt(r14, r4, r0)
            int r0 = r10 + 4
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x01c8:
            r19 = r6
            r14 = r7
            r23 = r9
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x01d3:
            r23 = r5
            r22 = r8
            r8 = r4
            r4 = r2
            if (r7 != r0) goto L_0x01f8
            long r18 = com.google.protobuf.ArrayDecoders.decodeFixed64(r12, r10)
            r0 = r9
            r1 = r30
            r2 = r4
            r21 = r8
            r20 = r9
            r8 = r4
            r4 = r18
            r0.putLong(r1, r2, r4)
            int r0 = r10 + 8
            r3 = r6
            r1 = r16
            r2 = r17
            r9 = r20
            goto L_0x0014
        L_0x01f8:
            r21 = r8
            r20 = r9
            r8 = r4
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r20
            r18 = r22
            goto L_0x03af
        L_0x0208:
            r21 = r4
            r23 = r5
            r22 = r8
            r20 = r9
            r8 = r2
            if (r7 != 0) goto L_0x0226
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r12, r10, r11)
            int r1 = r11.int1
            r4 = r20
            r4.putInt(r14, r8, r1)
            r9 = r4
            r3 = r6
            r1 = r16
            r2 = r17
            goto L_0x0014
        L_0x0226:
            r4 = r20
            r23 = r4
            r19 = r6
            r14 = r7
            r27 = r10
            r18 = r22
            goto L_0x03af
        L_0x0233:
            r21 = r4
            r23 = r5
            r22 = r8
            r4 = r9
            r8 = r2
            if (r7 != 0) goto L_0x025a
            int r10 = com.google.protobuf.ArrayDecoders.decodeVarint64(r12, r10, r11)
            long r2 = r11.long1
            r0 = r4
            r1 = r30
            r18 = r2
            r2 = r8
            r11 = r4
            r4 = r18
            r0.putLong(r1, r2, r4)
            r3 = r6
            r0 = r10
            r9 = r11
            r1 = r16
            r2 = r17
            r11 = r34
            goto L_0x0014
        L_0x025a:
            r11 = r4
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r11
            r18 = r22
            goto L_0x03af
        L_0x0266:
            r21 = r4
            r23 = r5
            r22 = r8
            r11 = r9
            r8 = r2
            r0 = 5
            if (r7 != r0) goto L_0x0284
            float r0 = com.google.protobuf.ArrayDecoders.decodeFloat(r12, r10)
            com.google.protobuf.UnsafeUtil.putFloat(r14, r8, r0)
            int r0 = r10 + 4
            r3 = r6
            r9 = r11
            r1 = r16
            r2 = r17
            r11 = r34
            goto L_0x0014
        L_0x0284:
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r11
            r18 = r22
            goto L_0x03af
        L_0x028f:
            r21 = r4
            r23 = r5
            r22 = r8
            r11 = r9
            r8 = r2
            if (r7 != r0) goto L_0x02ac
            double r0 = com.google.protobuf.ArrayDecoders.decodeDouble(r12, r10)
            com.google.protobuf.UnsafeUtil.putDouble(r14, r8, r0)
            int r0 = r10 + 8
            r3 = r6
            r9 = r11
            r1 = r16
            r2 = r17
            r11 = r34
            goto L_0x0014
        L_0x02ac:
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r11
            r18 = r22
            goto L_0x03af
        L_0x02b7:
            r21 = r4
            r23 = r5
            r22 = r8
            r11 = r9
            r8 = r2
            r0 = 27
            if (r5 != r0) goto L_0x0317
            if (r7 != r1) goto L_0x030b
            java.lang.Object r0 = r11.getObject(r14, r8)
            com.google.protobuf.Internal$ProtobufList r0 = (com.google.protobuf.Internal.ProtobufList) r0
            boolean r1 = r0.isModifiable()
            if (r1 != 0) goto L_0x02e7
            int r1 = r0.size()
            if (r1 != 0) goto L_0x02db
            r2 = 10
            goto L_0x02dd
        L_0x02db:
            int r2 = r1 * 2
        L_0x02dd:
            com.google.protobuf.Internal$ProtobufList r0 = r0.mutableCopyWithCapacity(r2)
            r11.putObject(r14, r8, r0)
            r18 = r0
            goto L_0x02e9
        L_0x02e7:
            r18 = r0
        L_0x02e9:
            com.google.protobuf.Schema r0 = r15.getMessageFieldSchema(r6)
            r1 = r16
            r2 = r31
            r3 = r10
            r4 = r33
            r15 = r5
            r5 = r18
            r19 = r6
            r6 = r34
            int r0 = com.google.protobuf.ArrayDecoders.decodeMessageList(r0, r1, r2, r3, r4, r5, r6)
            r15 = r29
            r9 = r11
            r2 = r17
            r3 = r19
            r11 = r34
            goto L_0x0014
        L_0x030b:
            r15 = r5
            r19 = r6
            r14 = r7
            r27 = r10
            r23 = r11
            r18 = r22
            goto L_0x03af
        L_0x0317:
            r15 = r5
            r19 = r6
            r0 = 49
            if (r15 > r0) goto L_0x036b
            r6 = r10
            r5 = r21
            long r3 = (long) r5
            r0 = r29
            r1 = r30
            r2 = r31
            r20 = r3
            r3 = r10
            r4 = r33
            r18 = r5
            r5 = r16
            r24 = r6
            r6 = r22
            r32 = r7
            r25 = r8
            r28 = r22
            r22 = r18
            r18 = r28
            r8 = r19
            r27 = r10
            r23 = r11
            r9 = r20
            r11 = r15
            r12 = r25
            r14 = r34
            int r0 = r0.parseRepeatedField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            r1 = r24
            if (r0 == r1) goto L_0x0368
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r16
            r2 = r17
            r3 = r19
            r9 = r23
            goto L_0x0014
        L_0x0368:
            r10 = r0
            goto L_0x03ed
        L_0x036b:
            r32 = r7
            r25 = r8
            r27 = r10
            r23 = r11
            r18 = r22
            r22 = r21
            r0 = 50
            if (r15 != r0) goto L_0x03b4
            r14 = r32
            if (r14 != r1) goto L_0x03af
            r9 = r27
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r27
            r4 = r33
            r5 = r19
            r6 = r25
            r8 = r34
            int r0 = r0.parseMapField(r1, r2, r3, r4, r5, r6, r8)
            if (r0 == r9) goto L_0x03ab
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r16
            r2 = r17
            r3 = r19
            r9 = r23
            goto L_0x0014
        L_0x03ab:
            r10 = r0
            r32 = r14
            goto L_0x03ed
        L_0x03af:
            r32 = r14
            r10 = r27
            goto L_0x03ed
        L_0x03b4:
            r14 = r32
            r13 = r27
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r27
            r4 = r33
            r5 = r16
            r6 = r18
            r7 = r14
            r8 = r22
            r9 = r15
            r10 = r25
            r12 = r19
            r14 = r13
            r13 = r34
            int r0 = r0.parseOneofField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == r14) goto L_0x03ec
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r16
            r2 = r17
            r3 = r19
            r9 = r23
            goto L_0x0014
        L_0x03ec:
            r10 = r0
        L_0x03ed:
            com.google.protobuf.UnknownFieldSetLite r4 = getMutableUnknownFields(r30)
            r0 = r16
            r1 = r31
            r2 = r10
            r3 = r33
            r5 = r34
            int r0 = com.google.protobuf.ArrayDecoders.decodeUnknownField(r0, r1, r2, r3, r4, r5)
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r16
            r2 = r17
            r3 = r19
            r9 = r23
            goto L_0x0014
        L_0x0413:
            r23 = r9
            r4 = r33
            if (r0 != r4) goto L_0x041a
            return r0
        L_0x041a:
            com.google.protobuf.InvalidProtocolBufferException r5 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.parseProto3Message(java.lang.Object, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    public void mergeFrom(T message, byte[] data, int position, int limit, ArrayDecoders.Registers registers) throws IOException {
        if (this.proto3) {
            parseProto3Message(message, data, position, limit, registers);
        } else {
            parseProto2Message(message, data, position, limit, 0, registers);
        }
    }

    public void makeImmutable(T message) {
        for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; i++) {
            long offset = offset(typeAndOffsetAt(this.intArray[i]));
            Object mapField = UnsafeUtil.getObject(message, offset);
            if (mapField != null) {
                UnsafeUtil.putObject(message, offset, this.mapFieldSchema.toImmutable(mapField));
            }
        }
        int length = this.intArray.length;
        for (int i2 = this.repeatedFieldOffsetStart; i2 < length; i2++) {
            this.listFieldSchema.makeImmutableListAt(message, (long) this.intArray[i2]);
        }
        this.unknownFieldSchema.makeImmutable(message);
        if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(message);
        }
    }

    private final <K, V> void mergeMap(Object message, int pos, Object mapDefaultEntry, ExtensionRegistryLite extensionRegistry, Reader reader) throws IOException {
        long offset = offset(typeAndOffsetAt(pos));
        Object mapField = UnsafeUtil.getObject(message, offset);
        if (mapField == null) {
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            UnsafeUtil.putObject(message, offset, mapField);
        } else if (this.mapFieldSchema.isImmutable(mapField)) {
            Object oldMapField = mapField;
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField, oldMapField);
            UnsafeUtil.putObject(message, offset, mapField);
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(mapField), this.mapFieldSchema.forMapMetadata(mapDefaultEntry), extensionRegistry);
    }

    private final <UT, UB> UB filterMapUnknownEnumValues(Object message, int pos, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema2) {
        Internal.EnumVerifier enumVerifier;
        int i = pos;
        int fieldNumber = numberAt(i);
        Object mapField = UnsafeUtil.getObject(message, offset(typeAndOffsetAt(i)));
        if (mapField == null || (enumVerifier = getEnumFieldVerifier(i)) == null) {
            return unknownFields;
        }
        return filterUnknownEnumMap(pos, fieldNumber, this.mapFieldSchema.forMutableMapData(mapField), enumVerifier, unknownFields, unknownFieldSchema2);
    }

    private final <K, V, UT, UB> UB filterUnknownEnumMap(int pos, int number, Map<K, V> mapData, Internal.EnumVerifier enumVerifier, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema2) {
        MapEntryLite.Metadata<?, ?> forMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos));
        Iterator<Map.Entry<K, V>> it = mapData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            if (!enumVerifier.isInRange(((Integer) entry.getValue()).intValue())) {
                if (unknownFields == null) {
                    unknownFields = unknownFieldSchema2.newBuilder();
                }
                ByteString.CodedBuilder codedBuilder = ByteString.newCodedBuilder(MapEntryLite.computeSerializedSize(forMapMetadata, entry.getKey(), entry.getValue()));
                try {
                    MapEntryLite.writeTo(codedBuilder.getCodedOutput(), forMapMetadata, entry.getKey(), entry.getValue());
                    unknownFieldSchema2.addLengthDelimited(unknownFields, number, codedBuilder.build());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return unknownFields;
    }

    public final boolean isInitialized(T message) {
        int currentPresenceFieldOffset = -1;
        int currentPresenceField = 0;
        for (int i = 0; i < this.checkInitializedCount; i++) {
            int pos = this.intArray[i];
            int number = numberAt(pos);
            int typeAndOffset = typeAndOffsetAt(pos);
            int presenceMask = 0;
            if (!this.proto3) {
                int presenceMaskAndOffset = this.buffer[pos + 2];
                int presenceFieldOffset = OFFSET_MASK & presenceMaskAndOffset;
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                if (presenceFieldOffset != currentPresenceFieldOffset) {
                    currentPresenceFieldOffset = presenceFieldOffset;
                    currentPresenceField = UNSAFE.getInt(message, (long) presenceFieldOffset);
                }
            }
            if (isRequired(typeAndOffset) && !isFieldPresent(message, pos, currentPresenceField, presenceMask)) {
                return false;
            }
            int type = type(typeAndOffset);
            if (type != 9 && type != 17) {
                if (type != 27) {
                    if (type == 60 || type == 68) {
                        if (isOneofPresent(message, number, pos) && !isInitialized(message, typeAndOffset, getMessageFieldSchema(pos))) {
                            return false;
                        }
                    } else if (type != 49) {
                        if (type == 50 && !isMapInitialized(message, typeAndOffset, pos)) {
                            return false;
                        }
                    }
                }
                if (!isListInitialized(message, typeAndOffset, pos)) {
                    return false;
                }
            } else if (isFieldPresent(message, pos, currentPresenceField, presenceMask) && !isInitialized(message, typeAndOffset, getMessageFieldSchema(pos))) {
                return false;
            }
        }
        return this.hasExtensions == 0 || this.extensionSchema.getExtensions(message).isInitialized();
    }

    private static boolean isInitialized(Object message, int typeAndOffset, Schema schema) {
        return schema.isInitialized(UnsafeUtil.getObject(message, offset(typeAndOffset)));
    }

    private <N> boolean isListInitialized(Object message, int typeAndOffset, int pos) {
        List<N> list = (List) UnsafeUtil.getObject(message, offset(typeAndOffset));
        if (list.isEmpty()) {
            return true;
        }
        Schema schema = getMessageFieldSchema(pos);
        for (int i = 0; i < list.size(); i++) {
            if (!schema.isInitialized(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isMapInitialized(T message, int typeAndOffset, int pos) {
        Map<?, ?> map = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(message, offset(typeAndOffset)));
        if (map.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos)).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        Schema schema = null;
        for (Object nested : map.values()) {
            if (schema == null) {
                schema = Protobuf.getInstance().schemaFor((Class) nested.getClass());
            }
            if (!schema.isInitialized(nested)) {
                return false;
            }
        }
        return true;
    }

    private void writeString(int fieldNumber, Object value, Writer writer) throws IOException {
        if (value instanceof String) {
            writer.writeString(fieldNumber, (String) value);
        } else {
            writer.writeBytes(fieldNumber, (ByteString) value);
        }
    }

    private void readString(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readString());
        } else {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
        }
    }

    private void readStringList(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        } else {
            reader.readStringList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        }
    }

    private <E> void readMessageList(Object message, int typeAndOffset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readMessageList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)), schema, extensionRegistry);
    }

    private <E> void readGroupList(Object message, long offset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(message, offset), schema, extensionRegistry);
    }

    private int numberAt(int pos) {
        return this.buffer[pos];
    }

    private int typeAndOffsetAt(int pos) {
        return this.buffer[pos + 1];
    }

    private int presenceMaskAndOffsetAt(int pos) {
        return this.buffer[pos + 2];
    }

    private static int type(int value) {
        return (FIELD_TYPE_MASK & value) >>> 20;
    }

    private static boolean isRequired(int value) {
        return (268435456 & value) != 0;
    }

    private static boolean isEnforceUtf8(int value) {
        return (536870912 & value) != 0;
    }

    private static long offset(int value) {
        return (long) (OFFSET_MASK & value);
    }

    private static <T> double doubleAt(T message, long offset) {
        return UnsafeUtil.getDouble(message, offset);
    }

    private static <T> float floatAt(T message, long offset) {
        return UnsafeUtil.getFloat(message, offset);
    }

    private static <T> int intAt(T message, long offset) {
        return UnsafeUtil.getInt(message, offset);
    }

    private static <T> long longAt(T message, long offset) {
        return UnsafeUtil.getLong(message, offset);
    }

    private static <T> boolean booleanAt(T message, long offset) {
        return UnsafeUtil.getBoolean(message, offset);
    }

    private static <T> double oneofDoubleAt(T message, long offset) {
        return ((Double) UnsafeUtil.getObject(message, offset)).doubleValue();
    }

    private static <T> float oneofFloatAt(T message, long offset) {
        return ((Float) UnsafeUtil.getObject(message, offset)).floatValue();
    }

    private static <T> int oneofIntAt(T message, long offset) {
        return ((Integer) UnsafeUtil.getObject(message, offset)).intValue();
    }

    private static <T> long oneofLongAt(T message, long offset) {
        return ((Long) UnsafeUtil.getObject(message, offset)).longValue();
    }

    private static <T> boolean oneofBooleanAt(T message, long offset) {
        return ((Boolean) UnsafeUtil.getObject(message, offset)).booleanValue();
    }

    private boolean arePresentForEquals(T message, T other, int pos) {
        return isFieldPresent(message, pos) == isFieldPresent(other, pos);
    }

    private boolean isFieldPresent(T message, int pos, int presenceField, int presenceMask) {
        if (this.proto3) {
            return isFieldPresent(message, pos);
        }
        return (presenceField & presenceMask) != 0;
    }

    /* JADX INFO: Multiple debug info for r0v1 int: [D('presenceMaskAndOffset' int), D('typeAndOffset' int)] */
    private boolean isFieldPresent(T message, int pos) {
        if (this.proto3) {
            int typeAndOffset = typeAndOffsetAt(pos);
            long offset = offset(typeAndOffset);
            switch (type(typeAndOffset)) {
                case 0:
                    if (UnsafeUtil.getDouble(message, offset) != 0.0d) {
                        return true;
                    }
                    return false;
                case 1:
                    if (UnsafeUtil.getFloat(message, offset) != 0.0f) {
                        return true;
                    }
                    return false;
                case 2:
                    if (UnsafeUtil.getLong(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 3:
                    if (UnsafeUtil.getLong(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 4:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 5:
                    if (UnsafeUtil.getLong(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 6:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 7:
                    return UnsafeUtil.getBoolean(message, offset);
                case 8:
                    Object value = UnsafeUtil.getObject(message, offset);
                    if (value instanceof String) {
                        return true ^ ((String) value).isEmpty();
                    }
                    if (value instanceof ByteString) {
                        return true ^ ByteString.EMPTY.equals(value);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    if (UnsafeUtil.getObject(message, offset) != null) {
                        return true;
                    }
                    return false;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.getObject(message, offset));
                case 11:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 12:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 13:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 14:
                    if (UnsafeUtil.getLong(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 15:
                    if (UnsafeUtil.getInt(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 16:
                    if (UnsafeUtil.getLong(message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 17:
                    if (UnsafeUtil.getObject(message, offset) != null) {
                        return true;
                    }
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            int typeAndOffset2 = presenceMaskAndOffsetAt(pos);
            if ((UnsafeUtil.getInt(message, (long) (OFFSET_MASK & typeAndOffset2)) & (1 << (typeAndOffset2 >>> 20))) != 0) {
                return true;
            }
            return false;
        }
    }

    private void setFieldPresent(T message, int pos) {
        if (!this.proto3) {
            int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
            long presenceFieldOffset = (long) (OFFSET_MASK & presenceMaskAndOffset);
            UnsafeUtil.putInt(message, presenceFieldOffset, UnsafeUtil.getInt(message, presenceFieldOffset) | (1 << (presenceMaskAndOffset >>> 20)));
        }
    }

    private boolean isOneofPresent(T message, int fieldNumber, int pos) {
        return UnsafeUtil.getInt(message, (long) (OFFSET_MASK & presenceMaskAndOffsetAt(pos))) == fieldNumber;
    }

    private boolean isOneofCaseEqual(T message, T other, int pos) {
        int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
        return UnsafeUtil.getInt(message, (long) (presenceMaskAndOffset & OFFSET_MASK)) == UnsafeUtil.getInt(other, (long) (OFFSET_MASK & presenceMaskAndOffset));
    }

    private void setOneofPresent(T message, int fieldNumber, int pos) {
        UnsafeUtil.putInt(message, (long) (OFFSET_MASK & presenceMaskAndOffsetAt(pos)), fieldNumber);
    }

    private int positionForFieldNumber(int number) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, 0);
    }

    private int positionForFieldNumber(int number, int min) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, min);
    }

    private int slowPositionForFieldNumber(int number, int min) {
        int max = (this.buffer.length / 3) - 1;
        while (min <= max) {
            int mid = (max + min) >>> 1;
            int pos = mid * 3;
            int midFieldNumber = numberAt(pos);
            if (number == midFieldNumber) {
                return pos;
            }
            if (number < midFieldNumber) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getSchemaSize() {
        return this.buffer.length * 3;
    }
}
