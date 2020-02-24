package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.LazyField;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

final class MessageSetSchema<T> implements Schema<T> {
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;

    private MessageSetSchema(UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MessageLite defaultInstance2) {
        this.unknownFieldSchema = unknownFieldSchema2;
        this.hasExtensions = extensionSchema2.hasExtensions(defaultInstance2);
        this.extensionSchema = extensionSchema2;
        this.defaultInstance = defaultInstance2;
    }

    static <T> MessageSetSchema<T> newSchema(UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MessageLite defaultInstance2) {
        return new MessageSetSchema<>(unknownFieldSchema2, extensionSchema2, defaultInstance2);
    }

    public T newInstance() {
        return this.defaultInstance.newBuilderForType().buildPartial();
    }

    public boolean equals(T message, T other) {
        if (!this.unknownFieldSchema.getFromMessage(message).equals(this.unknownFieldSchema.getFromMessage(other))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(message).equals(this.extensionSchema.getExtensions(other));
        }
        return true;
    }

    public int hashCode(T message) {
        int hashCode = this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions) {
            return (hashCode * 53) + this.extensionSchema.getExtensions(message).hashCode();
        }
        return hashCode;
    }

    public void mergeFrom(T message, T other) {
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
        }
    }

    public void writeTo(T message, Writer writer) throws IOException {
        Iterator<?> iterator = this.extensionSchema.getExtensions(message).iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> extension = iterator.next();
            FieldSet.FieldDescriptorLite<?> fd = (FieldSet.FieldDescriptorLite) extension.getKey();
            if (fd.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fd.isRepeated() || fd.isPacked()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (extension instanceof LazyField.LazyEntry) {
                writer.writeMessageSetItem(fd.getNumber(), ((LazyField.LazyEntry) extension).getField().toByteString());
            } else {
                writer.writeMessageSetItem(fd.getNumber(), extension.getValue());
            }
        }
        writeUnknownFieldsHelper(this.unknownFieldSchema, message, writer);
    }

    private <UT, UB> void writeUnknownFieldsHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema2, T message, Writer writer) throws IOException {
        unknownFieldSchema2.writeAsMessageSetTo(unknownFieldSchema2.getFromMessage(message), writer);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: com.google.protobuf.GeneratedMessageLite$GeneratedExtension} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mergeFrom(T r18, byte[] r19, int r20, int r21, com.google.protobuf.ArrayDecoders.Registers r22) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r7 = r19
            r8 = r21
            r9 = r22
            r1 = r18
            com.google.protobuf.GeneratedMessageLite r1 = (com.google.protobuf.GeneratedMessageLite) r1
            com.google.protobuf.UnknownFieldSetLite r1 = r1.unknownFields
            com.google.protobuf.UnknownFieldSetLite r2 = com.google.protobuf.UnknownFieldSetLite.getDefaultInstance()
            if (r1 != r2) goto L_0x0020
            com.google.protobuf.UnknownFieldSetLite r1 = com.google.protobuf.UnknownFieldSetLite.newInstance()
            r2 = r18
            com.google.protobuf.GeneratedMessageLite r2 = (com.google.protobuf.GeneratedMessageLite) r2
            r2.unknownFields = r1
            r10 = r1
            goto L_0x0021
        L_0x0020:
            r10 = r1
        L_0x0021:
            r1 = r18
            com.google.protobuf.GeneratedMessageLite$ExtendableMessage r1 = (com.google.protobuf.GeneratedMessageLite.ExtendableMessage) r1
            com.google.protobuf.FieldSet r11 = r1.ensureExtensionsAreMutable()
            r1 = 0
            r2 = r1
            r1 = r20
        L_0x002d:
            if (r1 >= r8) goto L_0x010c
            int r12 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r1, r9)
            int r13 = r9.int1
            int r1 = com.google.protobuf.WireFormat.MESSAGE_SET_ITEM_TAG
            r3 = 2
            if (r13 == r1) goto L_0x0085
            int r1 = com.google.protobuf.WireFormat.getTagWireType(r13)
            if (r1 != r3) goto L_0x0080
            com.google.protobuf.ExtensionSchema<?> r1 = r0.extensionSchema
            com.google.protobuf.ExtensionRegistryLite r3 = r9.extensionRegistry
            com.google.protobuf.MessageLite r4 = r0.defaultInstance
            int r5 = com.google.protobuf.WireFormat.getTagFieldNumber(r13)
            java.lang.Object r1 = r1.findExtensionByNumber(r3, r4, r5)
            r14 = r1
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r14 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r14
            if (r14 == 0) goto L_0x0070
            com.google.protobuf.Protobuf r1 = com.google.protobuf.Protobuf.getInstance()
            com.google.protobuf.MessageLite r2 = r14.getMessageDefaultInstance()
            java.lang.Class r2 = r2.getClass()
            com.google.protobuf.Schema r1 = r1.schemaFor(r2)
            int r1 = com.google.protobuf.ArrayDecoders.decodeMessageField(r1, r7, r12, r8, r9)
            com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor r2 = r14.descriptor
            java.lang.Object r3 = r9.object1
            r11.setField(r2, r3)
            r2 = r14
            goto L_0x002d
        L_0x0070:
            r1 = r13
            r2 = r19
            r3 = r12
            r4 = r21
            r5 = r10
            r6 = r22
            int r1 = com.google.protobuf.ArrayDecoders.decodeUnknownField(r1, r2, r3, r4, r5, r6)
            r2 = r14
            goto L_0x002d
        L_0x0080:
            int r1 = com.google.protobuf.ArrayDecoders.skipField(r13, r7, r12, r8, r9)
            goto L_0x002d
        L_0x0085:
            r1 = 0
            r4 = 0
        L_0x0087:
            if (r12 >= r8) goto L_0x00fa
            int r5 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r12, r9)
            int r6 = r9.int1
            int r12 = com.google.protobuf.WireFormat.getTagFieldNumber(r6)
            int r14 = com.google.protobuf.WireFormat.getTagWireType(r6)
            if (r12 == r3) goto L_0x00d1
            r15 = 3
            if (r12 == r15) goto L_0x009f
            r16 = r2
            goto L_0x00ec
        L_0x009f:
            if (r2 == 0) goto L_0x00bf
            com.google.protobuf.Protobuf r15 = com.google.protobuf.Protobuf.getInstance()
            com.google.protobuf.MessageLite r16 = r2.getMessageDefaultInstance()
            java.lang.Class r3 = r16.getClass()
            com.google.protobuf.Schema r3 = r15.schemaFor(r3)
            int r3 = com.google.protobuf.ArrayDecoders.decodeMessageField(r3, r7, r5, r8, r9)
            com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor r5 = r2.descriptor
            java.lang.Object r15 = r9.object1
            r11.setField(r5, r15)
            r12 = r3
            r3 = 2
            goto L_0x0087
        L_0x00bf:
            r3 = 2
            if (r14 != r3) goto L_0x00ce
            int r3 = com.google.protobuf.ArrayDecoders.decodeBytes(r7, r5, r9)
            java.lang.Object r5 = r9.object1
            r4 = r5
            com.google.protobuf.ByteString r4 = (com.google.protobuf.ByteString) r4
            r12 = r3
            r3 = 2
            goto L_0x0087
        L_0x00ce:
            r16 = r2
            goto L_0x00ec
        L_0x00d1:
            if (r14 != 0) goto L_0x00ea
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r5, r9)
            int r1 = r9.int1
            com.google.protobuf.ExtensionSchema<?> r5 = r0.extensionSchema
            com.google.protobuf.ExtensionRegistryLite r15 = r9.extensionRegistry
            r16 = r2
            com.google.protobuf.MessageLite r2 = r0.defaultInstance
            java.lang.Object r2 = r5.findExtensionByNumber(r15, r2, r1)
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r2 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r2
            r12 = r3
            r3 = 2
            goto L_0x0087
        L_0x00ea:
            r16 = r2
        L_0x00ec:
            int r2 = com.google.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG
            if (r6 != r2) goto L_0x00f2
            r12 = r5
            goto L_0x00fc
        L_0x00f2:
            int r12 = com.google.protobuf.ArrayDecoders.skipField(r6, r7, r5, r8, r9)
            r2 = r16
            r3 = 2
            goto L_0x0087
        L_0x00fa:
            r16 = r2
        L_0x00fc:
            if (r4 == 0) goto L_0x0107
            r2 = 2
            int r2 = com.google.protobuf.WireFormat.makeTag(r1, r2)
            r10.storeField(r2, r4)
        L_0x0107:
            r1 = r12
            r2 = r16
            goto L_0x002d
        L_0x010c:
            if (r1 != r8) goto L_0x010f
            return
        L_0x010f:
            com.google.protobuf.InvalidProtocolBufferException r3 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSetSchema.mergeFrom(java.lang.Object, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):void");
    }

    public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema2, ExtensionSchema<ET> extensionSchema2, T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        UB unknownFields = unknownFieldSchema2.getBuilderFromMessage(message);
        FieldSet<ET> extensions = extensionSchema2.getMutableExtensions(message);
        do {
            try {
                if (reader.getFieldNumber() == Integer.MAX_VALUE) {
                    unknownFieldSchema2.setBuilderToMessage(message, unknownFields);
                    return;
                }
            } finally {
                unknownFieldSchema2.setBuilderToMessage(message, unknownFields);
            }
        } while (parseMessageSetItemOrUnknownField(reader, extensionRegistry, extensionSchema2, extensions, unknownFieldSchema2, unknownFields));
    }

    public void makeImmutable(T message) {
        this.unknownFieldSchema.makeImmutable(message);
        this.extensionSchema.makeImmutable(message);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(Reader reader, ExtensionRegistryLite extensionRegistry, ExtensionSchema<ET> extensionSchema2, FieldSet<ET> extensions, UnknownFieldSchema<UT, UB> unknownFieldSchema2, UB unknownFields) throws IOException {
        int startTag = reader.getTag();
        if (startTag == WireFormat.MESSAGE_SET_ITEM_TAG) {
            int typeId = 0;
            ByteString rawBytes = null;
            Object extension = null;
            while (reader.getFieldNumber() != Integer.MAX_VALUE) {
                int tag = reader.getTag();
                if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = reader.readUInt32();
                    extension = extensionSchema2.findExtensionByNumber(extensionRegistry, this.defaultInstance, typeId);
                } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (extension != null) {
                        extensionSchema2.parseLengthPrefixedMessageSetItem(reader, extension, extensionRegistry, extensions);
                    } else {
                        rawBytes = reader.readBytes();
                    }
                } else if (!reader.skipField()) {
                    break;
                }
            }
            if (reader.getTag() == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                if (rawBytes != null) {
                    if (extension != null) {
                        extensionSchema2.parseMessageSetItem(rawBytes, extension, extensionRegistry, extensions);
                    } else {
                        unknownFieldSchema2.addLengthDelimited(unknownFields, typeId, rawBytes);
                    }
                }
                return true;
            }
            throw InvalidProtocolBufferException.invalidEndTag();
        } else if (WireFormat.getTagWireType(startTag) != 2) {
            return reader.skipField();
        } else {
            Object extension2 = extensionSchema2.findExtensionByNumber(extensionRegistry, this.defaultInstance, WireFormat.getTagFieldNumber(startTag));
            if (extension2 == null) {
                return unknownFieldSchema2.mergeOneFieldFrom(unknownFields, reader);
            }
            extensionSchema2.parseLengthPrefixedMessageSetItem(reader, extension2, extensionRegistry, extensions);
            return true;
        }
    }

    public final boolean isInitialized(T message) {
        return this.extensionSchema.getExtensions(message).isInitialized();
    }

    public int getSerializedSize(T message) {
        int size = 0 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
        if (this.hasExtensions) {
            return size + this.extensionSchema.getExtensions(message).getMessageSetSerializedSize();
        }
        return size;
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
        return schema.getSerializedSizeAsMessageSet(schema.getFromMessage(message));
    }
}
