package com.google.common.logging;

import com.google.common.logging.proto2api.UserActionEnum;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class AncestryVisualElement {

    public interface AncestryVisualElementProtoOrBuilder extends MessageLiteOrBuilder {
        UserActionEnum.CardinalDirection getCardinalDirection();

        int getElementId();

        int getElementIndex();

        int getPathToRootElementId(int i);

        int getPathToRootElementIdCount();

        List<Integer> getPathToRootElementIdList();

        UserActionEnum.UserAction getUserAction();

        boolean hasCardinalDirection();

        boolean hasElementId();

        boolean hasElementIndex();

        boolean hasUserAction();
    }

    private AncestryVisualElement() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AncestryVisualElementProto extends GeneratedMessageLite<AncestryVisualElementProto, Builder> implements AncestryVisualElementProtoOrBuilder {
        public static final int CARDINAL_DIRECTION_FIELD_NUMBER = 7;
        /* access modifiers changed from: private */
        public static final AncestryVisualElementProto DEFAULT_INSTANCE = new AncestryVisualElementProto();
        public static final int ELEMENT_ID_FIELD_NUMBER = 1;
        public static final int ELEMENT_INDEX_FIELD_NUMBER = 2;
        private static volatile Parser<AncestryVisualElementProto> PARSER = null;
        public static final int PATH_TO_ROOT_ELEMENT_ID_FIELD_NUMBER = 3;
        public static final int USER_ACTION_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int cardinalDirection_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int elementId_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int elementIndex_;
        @ProtoField(fieldNumber = 3, type = FieldType.INT32_LIST)
        private Internal.IntList pathToRootElementId_ = emptyIntList();
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int userAction_;

        private AncestryVisualElementProto() {
        }

        public boolean hasElementId() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getElementId() {
            return this.elementId_;
        }

        /* access modifiers changed from: private */
        public void setElementId(int value) {
            this.bitField0_ |= 1;
            this.elementId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearElementId() {
            this.bitField0_ &= -2;
            this.elementId_ = 0;
        }

        public boolean hasElementIndex() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getElementIndex() {
            return this.elementIndex_;
        }

        /* access modifiers changed from: private */
        public void setElementIndex(int value) {
            this.bitField0_ |= 2;
            this.elementIndex_ = value;
        }

        /* access modifiers changed from: private */
        public void clearElementIndex() {
            this.bitField0_ &= -3;
            this.elementIndex_ = 0;
        }

        public List<Integer> getPathToRootElementIdList() {
            return this.pathToRootElementId_;
        }

        public int getPathToRootElementIdCount() {
            return this.pathToRootElementId_.size();
        }

        public int getPathToRootElementId(int index) {
            return this.pathToRootElementId_.getInt(index);
        }

        private void ensurePathToRootElementIdIsMutable() {
            if (!this.pathToRootElementId_.isModifiable()) {
                this.pathToRootElementId_ = GeneratedMessageLite.mutableCopy(this.pathToRootElementId_);
            }
        }

        /* access modifiers changed from: private */
        public void setPathToRootElementId(int index, int value) {
            ensurePathToRootElementIdIsMutable();
            this.pathToRootElementId_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addPathToRootElementId(int value) {
            ensurePathToRootElementIdIsMutable();
            this.pathToRootElementId_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPathToRootElementId(Iterable<? extends Integer> values) {
            ensurePathToRootElementIdIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.pathToRootElementId_);
        }

        /* access modifiers changed from: private */
        public void clearPathToRootElementId() {
            this.pathToRootElementId_ = emptyIntList();
        }

        public boolean hasUserAction() {
            return (this.bitField0_ & 4) != 0;
        }

        public UserActionEnum.UserAction getUserAction() {
            UserActionEnum.UserAction result = UserActionEnum.UserAction.forNumber(this.userAction_);
            return result == null ? UserActionEnum.UserAction.UNASSIGNED_USER_ACTION_ID : result;
        }

        /* access modifiers changed from: private */
        public void setUserAction(UserActionEnum.UserAction value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.userAction_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearUserAction() {
            this.bitField0_ &= -5;
            this.userAction_ = 0;
        }

        public boolean hasCardinalDirection() {
            return (this.bitField0_ & 8) != 0;
        }

        public UserActionEnum.CardinalDirection getCardinalDirection() {
            UserActionEnum.CardinalDirection result = UserActionEnum.CardinalDirection.forNumber(this.cardinalDirection_);
            return result == null ? UserActionEnum.CardinalDirection.UNASSIGNED_DIRECTIONAL_MOVEMENT_ID : result;
        }

        /* access modifiers changed from: private */
        public void setCardinalDirection(UserActionEnum.CardinalDirection value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.cardinalDirection_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCardinalDirection() {
            this.bitField0_ &= -9;
            this.cardinalDirection_ = 0;
        }

        public static AncestryVisualElementProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AncestryVisualElementProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AncestryVisualElementProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AncestryVisualElementProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AncestryVisualElementProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AncestryVisualElementProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AncestryVisualElementProto parseFrom(InputStream input) throws IOException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AncestryVisualElementProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AncestryVisualElementProto parseDelimitedFrom(InputStream input) throws IOException {
            return (AncestryVisualElementProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AncestryVisualElementProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AncestryVisualElementProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AncestryVisualElementProto parseFrom(CodedInputStream input) throws IOException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AncestryVisualElementProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AncestryVisualElementProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AncestryVisualElementProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AncestryVisualElementProto, Builder> implements AncestryVisualElementProtoOrBuilder {
            private Builder() {
                super(AncestryVisualElementProto.DEFAULT_INSTANCE);
            }

            public boolean hasElementId() {
                return ((AncestryVisualElementProto) this.instance).hasElementId();
            }

            public int getElementId() {
                return ((AncestryVisualElementProto) this.instance).getElementId();
            }

            public Builder setElementId(int value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).setElementId(value);
                return this;
            }

            public Builder clearElementId() {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).clearElementId();
                return this;
            }

            public boolean hasElementIndex() {
                return ((AncestryVisualElementProto) this.instance).hasElementIndex();
            }

            public int getElementIndex() {
                return ((AncestryVisualElementProto) this.instance).getElementIndex();
            }

            public Builder setElementIndex(int value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).setElementIndex(value);
                return this;
            }

            public Builder clearElementIndex() {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).clearElementIndex();
                return this;
            }

            public List<Integer> getPathToRootElementIdList() {
                return Collections.unmodifiableList(((AncestryVisualElementProto) this.instance).getPathToRootElementIdList());
            }

            public int getPathToRootElementIdCount() {
                return ((AncestryVisualElementProto) this.instance).getPathToRootElementIdCount();
            }

            public int getPathToRootElementId(int index) {
                return ((AncestryVisualElementProto) this.instance).getPathToRootElementId(index);
            }

            public Builder setPathToRootElementId(int index, int value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).setPathToRootElementId(index, value);
                return this;
            }

            public Builder addPathToRootElementId(int value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).addPathToRootElementId(value);
                return this;
            }

            public Builder addAllPathToRootElementId(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).addAllPathToRootElementId(values);
                return this;
            }

            public Builder clearPathToRootElementId() {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).clearPathToRootElementId();
                return this;
            }

            public boolean hasUserAction() {
                return ((AncestryVisualElementProto) this.instance).hasUserAction();
            }

            public UserActionEnum.UserAction getUserAction() {
                return ((AncestryVisualElementProto) this.instance).getUserAction();
            }

            public Builder setUserAction(UserActionEnum.UserAction value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).setUserAction(value);
                return this;
            }

            public Builder clearUserAction() {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).clearUserAction();
                return this;
            }

            public boolean hasCardinalDirection() {
                return ((AncestryVisualElementProto) this.instance).hasCardinalDirection();
            }

            public UserActionEnum.CardinalDirection getCardinalDirection() {
                return ((AncestryVisualElementProto) this.instance).getCardinalDirection();
            }

            public Builder setCardinalDirection(UserActionEnum.CardinalDirection value) {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).setCardinalDirection(value);
                return this;
            }

            public Builder clearCardinalDirection() {
                copyOnWrite();
                ((AncestryVisualElementProto) this.instance).clearCardinalDirection();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AncestryVisualElementProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0007\u0005\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0016\u0004\f\u0002\u0007\f\u0003", new Object[]{"bitField0_", "elementId_", "elementIndex_", "pathToRootElementId_", "userAction_", UserActionEnum.UserAction.internalGetVerifier(), "cardinalDirection_", UserActionEnum.CardinalDirection.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AncestryVisualElementProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (AncestryVisualElementProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(AncestryVisualElementProto.class, DEFAULT_INSTANCE);
        }

        public static AncestryVisualElementProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AncestryVisualElementProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
