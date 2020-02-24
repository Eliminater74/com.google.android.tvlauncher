package com.google.protos.proto2.bridge;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = true, protoSyntax = ProtoSyntax.PROTO2)
public final class MessageSet extends GeneratedMessageLite.ExtendableMessage<MessageSet, Builder> implements MessageSetOrBuilder {
    /* access modifiers changed from: private */
    public static final MessageSet DEFAULT_INSTANCE = new MessageSet();
    private static volatile Parser<MessageSet> PARSER;
    private byte memoizedIsInitialized = 2;

    private MessageSet() {
    }

    public static MessageSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MessageSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MessageSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MessageSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MessageSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MessageSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MessageSet parseFrom(InputStream input) throws IOException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MessageSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MessageSet parseDelimitedFrom(InputStream input) throws IOException {
        return (MessageSet) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static MessageSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MessageSet) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MessageSet parseFrom(CodedInputStream input) throws IOException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MessageSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MessageSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(MessageSet prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MessageSet, Builder> implements MessageSetOrBuilder {
        private Builder() {
            super(MessageSet.DEFAULT_INSTANCE);
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new MessageSet();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0003\u0000", null);
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<MessageSet> parser = PARSER;
                if (parser == null) {
                    synchronized (MessageSet.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case GET_MEMOIZED_IS_INITIALIZED:
                return Byte.valueOf(this.memoizedIsInitialized);
            case SET_MEMOIZED_IS_INITIALIZED:
                this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        GeneratedMessageLite.registerDefaultInstance(MessageSet.class, DEFAULT_INSTANCE);
    }

    public static MessageSet getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MessageSet> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
