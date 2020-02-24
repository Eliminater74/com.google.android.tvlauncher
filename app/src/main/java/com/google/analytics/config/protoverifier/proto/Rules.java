package com.google.analytics.config.protoverifier.proto;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
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

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class Rules extends GeneratedMessageLite<Rules, Builder> implements RulesOrBuilder {
    public static final int CUSTOM_RULE_FIELD_NUMBER = 10;
    /* access modifiers changed from: private */
    public static final Rules DEFAULT_INSTANCE = new Rules();
    public static final int LANGUAGE_CODE_FIELD_NUMBER = 15;
    public static final int MATCH_REGEX_FIELD_NUMBER = 8;
    public static final int MAX_DOUBLE_FIELD_NUMBER = 7;
    public static final int MAX_FLOAT_FIELD_NUMBER = 6;
    public static final int MAX_INT_FIELD_NUMBER = 5;
    public static final int MAX_REPEATED_LENGTH_FIELD_NUMBER = 14;
    public static final int MAX_STRING_LENGTH_FIELD_NUMBER = 12;
    public static final int MIN_DOUBLE_FIELD_NUMBER = 4;
    public static final int MIN_FLOAT_FIELD_NUMBER = 3;
    public static final int MIN_INT_FIELD_NUMBER = 2;
    public static final int MIN_REPEATED_LENGTH_FIELD_NUMBER = 13;
    public static final int MIN_STRING_LENGTH_FIELD_NUMBER = 11;
    public static final int MUST_BE_SET_FIELD_NUMBER = 1;
    public static final int NOT_MATCH_REGEX_FIELD_NUMBER = 9;
    private static volatile Parser<Rules> PARSER;
    @ProtoPresenceBits(mo28548id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 10, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
    private Internal.ProtobufList<String> customRule_ = GeneratedMessageLite.emptyProtobufList();
    @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.BOOL)
    @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
    private boolean languageCode_;
    @ProtoField(fieldNumber = 8, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
    @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
    private String matchRegex_ = "";
    @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.DOUBLE)
    @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
    private double maxDouble_;
    @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.FLOAT)
    @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
    private float maxFloat_;
    @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
    private long maxInt_;
    @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
    private int maxRepeatedLength_;
    @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
    private int maxStringLength_;
    @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.DOUBLE)
    @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
    private double minDouble_;
    @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.FLOAT)
    @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
    private float minFloat_;
    @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
    private long minInt_;
    @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
    private int minRepeatedLength_;
    @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
    private int minStringLength_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private boolean mustBeSet_;
    @ProtoField(fieldNumber = 9, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
    @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
    private String notMatchRegex_ = "";

    private Rules() {
    }

    public boolean hasMustBeSet() {
        return (this.bitField0_ & 1) != 0;
    }

    public boolean getMustBeSet() {
        return this.mustBeSet_;
    }

    /* access modifiers changed from: private */
    public void setMustBeSet(boolean value) {
        this.bitField0_ |= 1;
        this.mustBeSet_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMustBeSet() {
        this.bitField0_ &= -2;
        this.mustBeSet_ = false;
    }

    public boolean hasMinInt() {
        return (this.bitField0_ & 2) != 0;
    }

    public long getMinInt() {
        return this.minInt_;
    }

    /* access modifiers changed from: private */
    public void setMinInt(long value) {
        this.bitField0_ |= 2;
        this.minInt_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMinInt() {
        this.bitField0_ &= -3;
        this.minInt_ = 0;
    }

    public boolean hasMinFloat() {
        return (this.bitField0_ & 4) != 0;
    }

    public float getMinFloat() {
        return this.minFloat_;
    }

    /* access modifiers changed from: private */
    public void setMinFloat(float value) {
        this.bitField0_ |= 4;
        this.minFloat_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMinFloat() {
        this.bitField0_ &= -5;
        this.minFloat_ = 0.0f;
    }

    public boolean hasMinDouble() {
        return (this.bitField0_ & 8) != 0;
    }

    public double getMinDouble() {
        return this.minDouble_;
    }

    /* access modifiers changed from: private */
    public void setMinDouble(double value) {
        this.bitField0_ |= 8;
        this.minDouble_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMinDouble() {
        this.bitField0_ &= -9;
        this.minDouble_ = 0.0d;
    }

    public boolean hasMaxInt() {
        return (this.bitField0_ & 16) != 0;
    }

    public long getMaxInt() {
        return this.maxInt_;
    }

    /* access modifiers changed from: private */
    public void setMaxInt(long value) {
        this.bitField0_ |= 16;
        this.maxInt_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMaxInt() {
        this.bitField0_ &= -17;
        this.maxInt_ = 0;
    }

    public boolean hasMaxFloat() {
        return (this.bitField0_ & 32) != 0;
    }

    public float getMaxFloat() {
        return this.maxFloat_;
    }

    /* access modifiers changed from: private */
    public void setMaxFloat(float value) {
        this.bitField0_ |= 32;
        this.maxFloat_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMaxFloat() {
        this.bitField0_ &= -33;
        this.maxFloat_ = 0.0f;
    }

    public boolean hasMaxDouble() {
        return (this.bitField0_ & 64) != 0;
    }

    public double getMaxDouble() {
        return this.maxDouble_;
    }

    /* access modifiers changed from: private */
    public void setMaxDouble(double value) {
        this.bitField0_ |= 64;
        this.maxDouble_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMaxDouble() {
        this.bitField0_ &= -65;
        this.maxDouble_ = 0.0d;
    }

    public boolean hasMatchRegex() {
        return (this.bitField0_ & 128) != 0;
    }

    public String getMatchRegex() {
        return this.matchRegex_;
    }

    public ByteString getMatchRegexBytes() {
        return ByteString.copyFromUtf8(this.matchRegex_);
    }

    /* access modifiers changed from: private */
    public void setMatchRegex(String value) {
        if (value != null) {
            this.bitField0_ |= 128;
            this.matchRegex_ = value;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearMatchRegex() {
        this.bitField0_ &= -129;
        this.matchRegex_ = getDefaultInstance().getMatchRegex();
    }

    /* access modifiers changed from: private */
    public void setMatchRegexBytes(ByteString value) {
        if (value != null) {
            this.bitField0_ |= 128;
            this.matchRegex_ = value.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public boolean hasNotMatchRegex() {
        return (this.bitField0_ & 256) != 0;
    }

    public String getNotMatchRegex() {
        return this.notMatchRegex_;
    }

    public ByteString getNotMatchRegexBytes() {
        return ByteString.copyFromUtf8(this.notMatchRegex_);
    }

    /* access modifiers changed from: private */
    public void setNotMatchRegex(String value) {
        if (value != null) {
            this.bitField0_ |= 256;
            this.notMatchRegex_ = value;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearNotMatchRegex() {
        this.bitField0_ &= -257;
        this.notMatchRegex_ = getDefaultInstance().getNotMatchRegex();
    }

    /* access modifiers changed from: private */
    public void setNotMatchRegexBytes(ByteString value) {
        if (value != null) {
            this.bitField0_ |= 256;
            this.notMatchRegex_ = value.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public List<String> getCustomRuleList() {
        return this.customRule_;
    }

    public int getCustomRuleCount() {
        return this.customRule_.size();
    }

    public String getCustomRule(int index) {
        return this.customRule_.get(index);
    }

    public ByteString getCustomRuleBytes(int index) {
        return ByteString.copyFromUtf8(this.customRule_.get(index));
    }

    private void ensureCustomRuleIsMutable() {
        if (!this.customRule_.isModifiable()) {
            this.customRule_ = GeneratedMessageLite.mutableCopy(this.customRule_);
        }
    }

    /* access modifiers changed from: private */
    public void setCustomRule(int index, String value) {
        if (value != null) {
            ensureCustomRuleIsMutable();
            this.customRule_.set(index, value);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void addCustomRule(String value) {
        if (value != null) {
            ensureCustomRuleIsMutable();
            this.customRule_.add(value);
            return;
        }
        throw new NullPointerException();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
     arg types: [java.lang.Iterable<java.lang.String>, com.google.protobuf.Internal$ProtobufList<java.lang.String>]
     candidates:
      com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
      com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
    /* access modifiers changed from: private */
    public void addAllCustomRule(Iterable<String> values) {
        ensureCustomRuleIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.customRule_);
    }

    /* access modifiers changed from: private */
    public void clearCustomRule() {
        this.customRule_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void addCustomRuleBytes(ByteString value) {
        if (value != null) {
            ensureCustomRuleIsMutable();
            this.customRule_.add(value.toStringUtf8());
            return;
        }
        throw new NullPointerException();
    }

    public boolean hasMinStringLength() {
        return (this.bitField0_ & 512) != 0;
    }

    public int getMinStringLength() {
        return this.minStringLength_;
    }

    /* access modifiers changed from: private */
    public void setMinStringLength(int value) {
        this.bitField0_ |= 512;
        this.minStringLength_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMinStringLength() {
        this.bitField0_ &= -513;
        this.minStringLength_ = 0;
    }

    public boolean hasMaxStringLength() {
        return (this.bitField0_ & 1024) != 0;
    }

    public int getMaxStringLength() {
        return this.maxStringLength_;
    }

    /* access modifiers changed from: private */
    public void setMaxStringLength(int value) {
        this.bitField0_ |= 1024;
        this.maxStringLength_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMaxStringLength() {
        this.bitField0_ &= -1025;
        this.maxStringLength_ = 0;
    }

    public boolean hasMinRepeatedLength() {
        return (this.bitField0_ & 2048) != 0;
    }

    public int getMinRepeatedLength() {
        return this.minRepeatedLength_;
    }

    /* access modifiers changed from: private */
    public void setMinRepeatedLength(int value) {
        this.bitField0_ |= 2048;
        this.minRepeatedLength_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMinRepeatedLength() {
        this.bitField0_ &= -2049;
        this.minRepeatedLength_ = 0;
    }

    public boolean hasMaxRepeatedLength() {
        return (this.bitField0_ & 4096) != 0;
    }

    public int getMaxRepeatedLength() {
        return this.maxRepeatedLength_;
    }

    /* access modifiers changed from: private */
    public void setMaxRepeatedLength(int value) {
        this.bitField0_ |= 4096;
        this.maxRepeatedLength_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMaxRepeatedLength() {
        this.bitField0_ &= -4097;
        this.maxRepeatedLength_ = 0;
    }

    public boolean hasLanguageCode() {
        return (this.bitField0_ & 8192) != 0;
    }

    public boolean getLanguageCode() {
        return this.languageCode_;
    }

    /* access modifiers changed from: private */
    public void setLanguageCode(boolean value) {
        this.bitField0_ |= 8192;
        this.languageCode_ = value;
    }

    /* access modifiers changed from: private */
    public void clearLanguageCode() {
        this.bitField0_ &= -8193;
        this.languageCode_ = false;
    }

    public static Rules parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Rules parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Rules parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Rules parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Rules parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Rules parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Rules parseFrom(InputStream input) throws IOException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Rules parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Rules parseDelimitedFrom(InputStream input) throws IOException {
        return (Rules) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Rules parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Rules) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Rules parseFrom(CodedInputStream input) throws IOException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Rules parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Rules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Rules prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Rules, Builder> implements RulesOrBuilder {
        private Builder() {
            super(Rules.DEFAULT_INSTANCE);
        }

        public boolean hasMustBeSet() {
            return ((Rules) this.instance).hasMustBeSet();
        }

        public boolean getMustBeSet() {
            return ((Rules) this.instance).getMustBeSet();
        }

        public Builder setMustBeSet(boolean value) {
            copyOnWrite();
            ((Rules) this.instance).setMustBeSet(value);
            return this;
        }

        public Builder clearMustBeSet() {
            copyOnWrite();
            ((Rules) this.instance).clearMustBeSet();
            return this;
        }

        public boolean hasMinInt() {
            return ((Rules) this.instance).hasMinInt();
        }

        public long getMinInt() {
            return ((Rules) this.instance).getMinInt();
        }

        public Builder setMinInt(long value) {
            copyOnWrite();
            ((Rules) this.instance).setMinInt(value);
            return this;
        }

        public Builder clearMinInt() {
            copyOnWrite();
            ((Rules) this.instance).clearMinInt();
            return this;
        }

        public boolean hasMinFloat() {
            return ((Rules) this.instance).hasMinFloat();
        }

        public float getMinFloat() {
            return ((Rules) this.instance).getMinFloat();
        }

        public Builder setMinFloat(float value) {
            copyOnWrite();
            ((Rules) this.instance).setMinFloat(value);
            return this;
        }

        public Builder clearMinFloat() {
            copyOnWrite();
            ((Rules) this.instance).clearMinFloat();
            return this;
        }

        public boolean hasMinDouble() {
            return ((Rules) this.instance).hasMinDouble();
        }

        public double getMinDouble() {
            return ((Rules) this.instance).getMinDouble();
        }

        public Builder setMinDouble(double value) {
            copyOnWrite();
            ((Rules) this.instance).setMinDouble(value);
            return this;
        }

        public Builder clearMinDouble() {
            copyOnWrite();
            ((Rules) this.instance).clearMinDouble();
            return this;
        }

        public boolean hasMaxInt() {
            return ((Rules) this.instance).hasMaxInt();
        }

        public long getMaxInt() {
            return ((Rules) this.instance).getMaxInt();
        }

        public Builder setMaxInt(long value) {
            copyOnWrite();
            ((Rules) this.instance).setMaxInt(value);
            return this;
        }

        public Builder clearMaxInt() {
            copyOnWrite();
            ((Rules) this.instance).clearMaxInt();
            return this;
        }

        public boolean hasMaxFloat() {
            return ((Rules) this.instance).hasMaxFloat();
        }

        public float getMaxFloat() {
            return ((Rules) this.instance).getMaxFloat();
        }

        public Builder setMaxFloat(float value) {
            copyOnWrite();
            ((Rules) this.instance).setMaxFloat(value);
            return this;
        }

        public Builder clearMaxFloat() {
            copyOnWrite();
            ((Rules) this.instance).clearMaxFloat();
            return this;
        }

        public boolean hasMaxDouble() {
            return ((Rules) this.instance).hasMaxDouble();
        }

        public double getMaxDouble() {
            return ((Rules) this.instance).getMaxDouble();
        }

        public Builder setMaxDouble(double value) {
            copyOnWrite();
            ((Rules) this.instance).setMaxDouble(value);
            return this;
        }

        public Builder clearMaxDouble() {
            copyOnWrite();
            ((Rules) this.instance).clearMaxDouble();
            return this;
        }

        public boolean hasMatchRegex() {
            return ((Rules) this.instance).hasMatchRegex();
        }

        public String getMatchRegex() {
            return ((Rules) this.instance).getMatchRegex();
        }

        public ByteString getMatchRegexBytes() {
            return ((Rules) this.instance).getMatchRegexBytes();
        }

        public Builder setMatchRegex(String value) {
            copyOnWrite();
            ((Rules) this.instance).setMatchRegex(value);
            return this;
        }

        public Builder clearMatchRegex() {
            copyOnWrite();
            ((Rules) this.instance).clearMatchRegex();
            return this;
        }

        public Builder setMatchRegexBytes(ByteString value) {
            copyOnWrite();
            ((Rules) this.instance).setMatchRegexBytes(value);
            return this;
        }

        public boolean hasNotMatchRegex() {
            return ((Rules) this.instance).hasNotMatchRegex();
        }

        public String getNotMatchRegex() {
            return ((Rules) this.instance).getNotMatchRegex();
        }

        public ByteString getNotMatchRegexBytes() {
            return ((Rules) this.instance).getNotMatchRegexBytes();
        }

        public Builder setNotMatchRegex(String value) {
            copyOnWrite();
            ((Rules) this.instance).setNotMatchRegex(value);
            return this;
        }

        public Builder clearNotMatchRegex() {
            copyOnWrite();
            ((Rules) this.instance).clearNotMatchRegex();
            return this;
        }

        public Builder setNotMatchRegexBytes(ByteString value) {
            copyOnWrite();
            ((Rules) this.instance).setNotMatchRegexBytes(value);
            return this;
        }

        public List<String> getCustomRuleList() {
            return Collections.unmodifiableList(((Rules) this.instance).getCustomRuleList());
        }

        public int getCustomRuleCount() {
            return ((Rules) this.instance).getCustomRuleCount();
        }

        public String getCustomRule(int index) {
            return ((Rules) this.instance).getCustomRule(index);
        }

        public ByteString getCustomRuleBytes(int index) {
            return ((Rules) this.instance).getCustomRuleBytes(index);
        }

        public Builder setCustomRule(int index, String value) {
            copyOnWrite();
            ((Rules) this.instance).setCustomRule(index, value);
            return this;
        }

        public Builder addCustomRule(String value) {
            copyOnWrite();
            ((Rules) this.instance).addCustomRule(value);
            return this;
        }

        public Builder addAllCustomRule(Iterable<String> values) {
            copyOnWrite();
            ((Rules) this.instance).addAllCustomRule(values);
            return this;
        }

        public Builder clearCustomRule() {
            copyOnWrite();
            ((Rules) this.instance).clearCustomRule();
            return this;
        }

        public Builder addCustomRuleBytes(ByteString value) {
            copyOnWrite();
            ((Rules) this.instance).addCustomRuleBytes(value);
            return this;
        }

        public boolean hasMinStringLength() {
            return ((Rules) this.instance).hasMinStringLength();
        }

        public int getMinStringLength() {
            return ((Rules) this.instance).getMinStringLength();
        }

        public Builder setMinStringLength(int value) {
            copyOnWrite();
            ((Rules) this.instance).setMinStringLength(value);
            return this;
        }

        public Builder clearMinStringLength() {
            copyOnWrite();
            ((Rules) this.instance).clearMinStringLength();
            return this;
        }

        public boolean hasMaxStringLength() {
            return ((Rules) this.instance).hasMaxStringLength();
        }

        public int getMaxStringLength() {
            return ((Rules) this.instance).getMaxStringLength();
        }

        public Builder setMaxStringLength(int value) {
            copyOnWrite();
            ((Rules) this.instance).setMaxStringLength(value);
            return this;
        }

        public Builder clearMaxStringLength() {
            copyOnWrite();
            ((Rules) this.instance).clearMaxStringLength();
            return this;
        }

        public boolean hasMinRepeatedLength() {
            return ((Rules) this.instance).hasMinRepeatedLength();
        }

        public int getMinRepeatedLength() {
            return ((Rules) this.instance).getMinRepeatedLength();
        }

        public Builder setMinRepeatedLength(int value) {
            copyOnWrite();
            ((Rules) this.instance).setMinRepeatedLength(value);
            return this;
        }

        public Builder clearMinRepeatedLength() {
            copyOnWrite();
            ((Rules) this.instance).clearMinRepeatedLength();
            return this;
        }

        public boolean hasMaxRepeatedLength() {
            return ((Rules) this.instance).hasMaxRepeatedLength();
        }

        public int getMaxRepeatedLength() {
            return ((Rules) this.instance).getMaxRepeatedLength();
        }

        public Builder setMaxRepeatedLength(int value) {
            copyOnWrite();
            ((Rules) this.instance).setMaxRepeatedLength(value);
            return this;
        }

        public Builder clearMaxRepeatedLength() {
            copyOnWrite();
            ((Rules) this.instance).clearMaxRepeatedLength();
            return this;
        }

        public boolean hasLanguageCode() {
            return ((Rules) this.instance).hasLanguageCode();
        }

        public boolean getLanguageCode() {
            return ((Rules) this.instance).getLanguageCode();
        }

        public Builder setLanguageCode(boolean value) {
            copyOnWrite();
            ((Rules) this.instance).setLanguageCode(value);
            return this;
        }

        public Builder clearLanguageCode() {
            copyOnWrite();
            ((Rules) this.instance).clearLanguageCode();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Rules();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000f\u0000\u0001\u0001\u000f\u000f\u0000\u0001\u0000\u0001\u0007\u0000\u0002\u0002\u0001\u0003\u0001\u0002\u0004\u0000\u0003\u0005\u0002\u0004\u0006\u0001\u0005\u0007\u0000\u0006\b\b\u0007\t\b\b\n\u001a\u000b\u0004\t\f\u0004\n\r\u0004\u000b\u000e\u0004\f\u000f\u0007\r", new Object[]{"bitField0_", "mustBeSet_", "minInt_", "minFloat_", "minDouble_", "maxInt_", "maxFloat_", "maxDouble_", "matchRegex_", "notMatchRegex_", "customRule_", "minStringLength_", "maxStringLength_", "minRepeatedLength_", "maxRepeatedLength_", "languageCode_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Rules> parser = PARSER;
                if (parser == null) {
                    synchronized (Rules.class) {
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
        GeneratedMessageLite.registerDefaultInstance(Rules.class, DEFAULT_INSTANCE);
    }

    public static Rules getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Rules> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
