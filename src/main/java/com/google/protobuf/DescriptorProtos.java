package com.google.protobuf;

import com.google.android.exoplayer2.C0841C;
import com.google.android.gsf.GoogleLoginServiceConstants;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class DescriptorProtos {

    public interface DescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        DescriptorProto.ExtensionRange getExtensionRange(int i);

        int getExtensionRangeCount();

        List<DescriptorProto.ExtensionRange> getExtensionRangeList();

        FieldDescriptorProto getField(int i);

        int getFieldCount();

        List<FieldDescriptorProto> getFieldList();

        String getName();

        ByteString getNameBytes();

        DescriptorProto getNestedType(int i);

        int getNestedTypeCount();

        List<DescriptorProto> getNestedTypeList();

        OneofDescriptorProto getOneofDecl(int i);

        int getOneofDeclCount();

        List<OneofDescriptorProto> getOneofDeclList();

        MessageOptions getOptions();

        String getReservedName(int i);

        ByteString getReservedNameBytes(int i);

        int getReservedNameCount();

        List<String> getReservedNameList();

        DescriptorProto.ReservedRange getReservedRange(int i);

        int getReservedRangeCount();

        List<DescriptorProto.ReservedRange> getReservedRangeList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        EnumOptions getOptions();

        String getReservedName(int i);

        ByteString getReservedNameBytes(int i);

        int getReservedNameCount();

        List<String> getReservedNameList();

        EnumDescriptorProto.EnumReservedRange getReservedRange(int i);

        int getReservedRangeCount();

        List<EnumDescriptorProto.EnumReservedRange> getReservedRangeList();

        EnumValueDescriptorProto getValue(int i);

        int getValueCount();

        List<EnumValueDescriptorProto> getValueList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<EnumOptions, EnumOptions.Builder> {
        boolean getAllowAlias();

        boolean getDeprecated();

        String getProto1Name();

        ByteString getProto1NameBytes();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasAllowAlias();

        boolean hasDeprecated();

        boolean hasProto1Name();
    }

    public interface EnumValueDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        int getNumber();

        EnumValueOptions getOptions();

        boolean hasName();

        boolean hasNumber();

        boolean hasOptions();
    }

    public interface EnumValueOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<EnumValueOptions, EnumValueOptions.Builder> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();
    }

    public interface ExtensionRangeOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<ExtensionRangeOptions, ExtensionRangeOptions.Builder> {
        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();
    }

    public interface FieldDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getDefaultValue();

        ByteString getDefaultValueBytes();

        String getExtendee();

        ByteString getExtendeeBytes();

        String getJsonName();

        ByteString getJsonNameBytes();

        FieldDescriptorProto.Label getLabel();

        String getName();

        ByteString getNameBytes();

        int getNumber();

        int getOneofIndex();

        FieldOptions getOptions();

        FieldDescriptorProto.Type getType();

        String getTypeName();

        ByteString getTypeNameBytes();

        boolean hasDefaultValue();

        boolean hasExtendee();

        boolean hasJsonName();

        boolean hasLabel();

        boolean hasName();

        boolean hasNumber();

        boolean hasOneofIndex();

        boolean hasOptions();

        boolean hasType();

        boolean hasTypeName();
    }

    public interface FieldOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<FieldOptions, FieldOptions.Builder> {
        boolean getCcOpenEnum();

        FieldOptions.CType getCtype();

        boolean getDeprecated();

        boolean getDeprecatedRawMessage();

        boolean getEnforceUtf8();

        FieldOptions.JSType getJstype();

        boolean getLazy();

        boolean getPacked();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        FieldOptions.UpgradedOption getUpgradedOption(int i);

        int getUpgradedOptionCount();

        List<FieldOptions.UpgradedOption> getUpgradedOptionList();

        boolean getWeak();

        boolean hasCcOpenEnum();

        boolean hasCtype();

        boolean hasDeprecated();

        boolean hasDeprecatedRawMessage();

        boolean hasEnforceUtf8();

        boolean hasJstype();

        boolean hasLazy();

        boolean hasPacked();

        boolean hasWeak();
    }

    public interface FileDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getDependency(int i);

        ByteString getDependencyBytes(int i);

        int getDependencyCount();

        List<String> getDependencyList();

        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        DescriptorProto getMessageType(int i);

        int getMessageTypeCount();

        List<DescriptorProto> getMessageTypeList();

        String getName();

        ByteString getNameBytes();

        FileOptions getOptions();

        String getPackage();

        ByteString getPackageBytes();

        int getPublicDependency(int i);

        int getPublicDependencyCount();

        List<Integer> getPublicDependencyList();

        ServiceDescriptorProto getService(int i);

        int getServiceCount();

        List<ServiceDescriptorProto> getServiceList();

        SourceCodeInfo getSourceCodeInfo();

        String getSyntax();

        ByteString getSyntaxBytes();

        int getWeakDependency(int i);

        int getWeakDependencyCount();

        List<Integer> getWeakDependencyList();

        boolean hasName();

        boolean hasOptions();

        boolean hasPackage();

        boolean hasSourceCodeInfo();

        boolean hasSyntax();
    }

    public interface FileDescriptorSetOrBuilder extends MessageLiteOrBuilder {
        FileDescriptorProto getFile(int i);

        int getFileCount();

        List<FileDescriptorProto> getFileList();
    }

    public interface FileOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<FileOptions, FileOptions.Builder> {
        int getCcApiVersion();

        boolean getCcEnableArenas();

        boolean getCcGenericServices();

        boolean getCcUtf8Verification();

        String getCsharpNamespace();

        ByteString getCsharpNamespaceBytes();

        boolean getDeprecated();

        String getGoPackage();

        ByteString getGoPackageBytes();

        String getJavaAltApiPackage();

        ByteString getJavaAltApiPackageBytes();

        int getJavaApiVersion();

        boolean getJavaEnableDualGenerateMutableApi();

        boolean getJavaGenericServices();

        boolean getJavaJava5Enums();

        boolean getJavaMultipleFiles();

        String getJavaMultipleFilesMutablePackage();

        ByteString getJavaMultipleFilesMutablePackageBytes();

        boolean getJavaMutableApi();

        String getJavaOuterClassname();

        ByteString getJavaOuterClassnameBytes();

        String getJavaPackage();

        ByteString getJavaPackageBytes();

        boolean getJavaStringCheckUtf8();

        boolean getJavaUseJavaproto2();

        String getJavascriptPackage();

        ByteString getJavascriptPackageBytes();

        String getObjcClassPrefix();

        ByteString getObjcClassPrefixBytes();

        FileOptions.OptimizeMode getOptimizeFor();

        String getPhpClassPrefix();

        ByteString getPhpClassPrefixBytes();

        boolean getPhpGenericServices();

        String getPhpMetadataNamespace();

        ByteString getPhpMetadataNamespaceBytes();

        String getPhpNamespace();

        ByteString getPhpNamespaceBytes();

        int getPyApiVersion();

        boolean getPyGenericServices();

        String getRubyPackage();

        ByteString getRubyPackageBytes();

        String getSwiftPrefix();

        ByteString getSwiftPrefixBytes();

        int getSzlApiVersion();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasCcApiVersion();

        boolean hasCcEnableArenas();

        boolean hasCcGenericServices();

        boolean hasCcUtf8Verification();

        boolean hasCsharpNamespace();

        boolean hasDeprecated();

        boolean hasGoPackage();

        boolean hasJavaAltApiPackage();

        boolean hasJavaApiVersion();

        boolean hasJavaEnableDualGenerateMutableApi();

        boolean hasJavaGenericServices();

        boolean hasJavaJava5Enums();

        boolean hasJavaMultipleFiles();

        boolean hasJavaMultipleFilesMutablePackage();

        boolean hasJavaMutableApi();

        boolean hasJavaOuterClassname();

        boolean hasJavaPackage();

        boolean hasJavaStringCheckUtf8();

        boolean hasJavaUseJavaproto2();

        boolean hasJavascriptPackage();

        boolean hasObjcClassPrefix();

        boolean hasOptimizeFor();

        boolean hasPhpClassPrefix();

        boolean hasPhpGenericServices();

        boolean hasPhpMetadataNamespace();

        boolean hasPhpNamespace();

        boolean hasPyApiVersion();

        boolean hasPyGenericServices();

        boolean hasRubyPackage();

        boolean hasSwiftPrefix();

        boolean hasSzlApiVersion();
    }

    public interface GeneratedCodeInfoOrBuilder extends MessageLiteOrBuilder {
        GeneratedCodeInfo.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<GeneratedCodeInfo.Annotation> getAnnotationList();
    }

    public interface MessageOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<MessageOptions, MessageOptions.Builder> {
        boolean getDeprecated();

        @Deprecated
        String getExperimentalJavaBuilderInterface(int i);

        @Deprecated
        ByteString getExperimentalJavaBuilderInterfaceBytes(int i);

        @Deprecated
        int getExperimentalJavaBuilderInterfaceCount();

        @Deprecated
        List<String> getExperimentalJavaBuilderInterfaceList();

        @Deprecated
        String getExperimentalJavaInterfaceExtends(int i);

        @Deprecated
        ByteString getExperimentalJavaInterfaceExtendsBytes(int i);

        @Deprecated
        int getExperimentalJavaInterfaceExtendsCount();

        @Deprecated
        List<String> getExperimentalJavaInterfaceExtendsList();

        @Deprecated
        String getExperimentalJavaMessageInterface(int i);

        @Deprecated
        ByteString getExperimentalJavaMessageInterfaceBytes(int i);

        @Deprecated
        int getExperimentalJavaMessageInterfaceCount();

        @Deprecated
        List<String> getExperimentalJavaMessageInterfaceList();

        boolean getMapEntry();

        boolean getMessageSetWireFormat();

        boolean getNoStandardDescriptorAccessor();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();

        boolean hasMapEntry();

        boolean hasMessageSetWireFormat();

        boolean hasNoStandardDescriptorAccessor();
    }

    public interface MethodDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        boolean getClientStreaming();

        String getInputType();

        ByteString getInputTypeBytes();

        String getName();

        ByteString getNameBytes();

        MethodOptions getOptions();

        String getOutputType();

        ByteString getOutputTypeBytes();

        boolean getServerStreaming();

        boolean hasClientStreaming();

        boolean hasInputType();

        boolean hasName();

        boolean hasOptions();

        boolean hasOutputType();

        boolean hasServerStreaming();
    }

    public interface MethodOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<MethodOptions, MethodOptions.Builder> {
        int getClientLogging();

        boolean getClientStreaming();

        double getDeadline();

        boolean getDeprecated();

        boolean getDuplicateSuppression();

        boolean getEndUserCredsRequested();

        boolean getFailFast();

        boolean getGoLegacyChannelApi();

        MethodOptions.IdempotencyLevel getIdempotencyLevel();

        long getLegacyClientInitialTokens();

        String getLegacyResultType();

        ByteString getLegacyResultTypeBytes();

        long getLegacyServerInitialTokens();

        String getLegacyStreamType();

        ByteString getLegacyStreamTypeBytes();

        MethodOptions.TokenUnit getLegacyTokenUnit();

        MethodOptions.LogLevel getLogLevel();

        MethodOptions.Protocol getProtocol();

        MethodOptions.Format getRequestFormat();

        MethodOptions.Format getResponseFormat();

        String getSecurityLabel();

        ByteString getSecurityLabelBytes();

        MethodOptions.SecurityLevel getSecurityLevel();

        int getServerLogging();

        boolean getServerStreaming();

        String getStreamType();

        ByteString getStreamTypeBytes();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasClientLogging();

        boolean hasClientStreaming();

        boolean hasDeadline();

        boolean hasDeprecated();

        boolean hasDuplicateSuppression();

        boolean hasEndUserCredsRequested();

        boolean hasFailFast();

        boolean hasGoLegacyChannelApi();

        boolean hasIdempotencyLevel();

        boolean hasLegacyClientInitialTokens();

        boolean hasLegacyResultType();

        boolean hasLegacyServerInitialTokens();

        boolean hasLegacyStreamType();

        boolean hasLegacyTokenUnit();

        boolean hasLogLevel();

        boolean hasProtocol();

        boolean hasRequestFormat();

        boolean hasResponseFormat();

        boolean hasSecurityLabel();

        boolean hasSecurityLevel();

        boolean hasServerLogging();

        boolean hasServerStreaming();

        boolean hasStreamType();
    }

    public interface OneofDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        OneofOptions getOptions();

        boolean hasName();

        boolean hasOptions();
    }

    public interface OneofOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<OneofOptions, OneofOptions.Builder> {
        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();
    }

    public interface ServiceDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        MethodDescriptorProto getMethod(int i);

        int getMethodCount();

        List<MethodDescriptorProto> getMethodList();

        String getName();

        ByteString getNameBytes();

        ServiceOptions getOptions();

        StreamDescriptorProto getStream(int i);

        int getStreamCount();

        List<StreamDescriptorProto> getStreamList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface ServiceOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<ServiceOptions, ServiceOptions.Builder> {
        boolean getDeprecated();

        double getFailureDetectionDelay();

        boolean getMulticastStub();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();

        boolean hasFailureDetectionDelay();

        boolean hasMulticastStub();
    }

    public interface SourceCodeInfoOrBuilder extends MessageLiteOrBuilder {
        SourceCodeInfo.Location getLocation(int i);

        int getLocationCount();

        List<SourceCodeInfo.Location> getLocationList();
    }

    public interface StreamDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getClientMessageType();

        ByteString getClientMessageTypeBytes();

        String getName();

        ByteString getNameBytes();

        StreamOptions getOptions();

        String getServerMessageType();

        ByteString getServerMessageTypeBytes();

        boolean hasClientMessageType();

        boolean hasName();

        boolean hasOptions();

        boolean hasServerMessageType();
    }

    public interface StreamOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<StreamOptions, StreamOptions.Builder> {
        long getClientInitialTokens();

        int getClientLogging();

        double getDeadline();

        boolean getDeprecated();

        boolean getEndUserCredsRequested();

        boolean getFailFast();

        MethodOptions.LogLevel getLogLevel();

        String getSecurityLabel();

        ByteString getSecurityLabelBytes();

        MethodOptions.SecurityLevel getSecurityLevel();

        long getServerInitialTokens();

        int getServerLogging();

        StreamOptions.TokenUnit getTokenUnit();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasClientInitialTokens();

        boolean hasClientLogging();

        boolean hasDeadline();

        boolean hasDeprecated();

        boolean hasEndUserCredsRequested();

        boolean hasFailFast();

        boolean hasLogLevel();

        boolean hasSecurityLabel();

        boolean hasSecurityLevel();

        boolean hasServerInitialTokens();

        boolean hasServerLogging();

        boolean hasTokenUnit();
    }

    public interface UninterpretedOptionOrBuilder extends MessageLiteOrBuilder {
        String getAggregateValue();

        ByteString getAggregateValueBytes();

        double getDoubleValue();

        String getIdentifierValue();

        ByteString getIdentifierValueBytes();

        UninterpretedOption.NamePart getName(int i);

        int getNameCount();

        List<UninterpretedOption.NamePart> getNameList();

        long getNegativeIntValue();

        long getPositiveIntValue();

        ByteString getStringValue();

        boolean hasAggregateValue();

        boolean hasDoubleValue();

        boolean hasIdentifierValue();

        boolean hasNegativeIntValue();

        boolean hasPositiveIntValue();

        boolean hasStringValue();
    }

    private DescriptorProtos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {1}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FileDescriptorSet extends GeneratedMessageLite<FileDescriptorSet, Builder> implements FileDescriptorSetOrBuilder {
        /* access modifiers changed from: private */
        public static final FileDescriptorSet DEFAULT_INSTANCE = new FileDescriptorSet();
        public static final int FILE_FIELD_NUMBER = 1;
        private static volatile Parser<FileDescriptorSet> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<FileDescriptorProto> file_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;

        private FileDescriptorSet() {
        }

        public List<FileDescriptorProto> getFileList() {
            return this.file_;
        }

        public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        public int getFileCount() {
            return this.file_.size();
        }

        public FileDescriptorProto getFile(int index) {
            return this.file_.get(index);
        }

        public FileDescriptorProtoOrBuilder getFileOrBuilder(int index) {
            return this.file_.get(index);
        }

        private void ensureFileIsMutable() {
            if (!this.file_.isModifiable()) {
                this.file_ = GeneratedMessageLite.mutableCopy(this.file_);
            }
        }

        /* access modifiers changed from: private */
        public void setFile(int index, FileDescriptorProto value) {
            if (value != null) {
                ensureFileIsMutable();
                this.file_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setFile(int index, FileDescriptorProto.Builder builderForValue) {
            ensureFileIsMutable();
            this.file_.set(index, (FileDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addFile(FileDescriptorProto value) {
            if (value != null) {
                ensureFileIsMutable();
                this.file_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addFile(int index, FileDescriptorProto value) {
            if (value != null) {
                ensureFileIsMutable();
                this.file_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addFile(FileDescriptorProto.Builder builderForValue) {
            ensureFileIsMutable();
            this.file_.add((FileDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addFile(int index, FileDescriptorProto.Builder builderForValue) {
            ensureFileIsMutable();
            this.file_.add(index, (FileDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$FileDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$FileDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllFile(Iterable<? extends FileDescriptorProto> values) {
            ensureFileIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.file_);
        }

        /* access modifiers changed from: private */
        public void clearFile() {
            this.file_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeFile(int index) {
            ensureFileIsMutable();
            this.file_.remove(index);
        }

        public static FileDescriptorSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileDescriptorSet prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FileDescriptorSet, Builder> implements FileDescriptorSetOrBuilder {
            private Builder() {
                super(FileDescriptorSet.DEFAULT_INSTANCE);
            }

            public List<FileDescriptorProto> getFileList() {
                return Collections.unmodifiableList(((FileDescriptorSet) this.instance).getFileList());
            }

            public int getFileCount() {
                return ((FileDescriptorSet) this.instance).getFileCount();
            }

            public FileDescriptorProto getFile(int index) {
                return ((FileDescriptorSet) this.instance).getFile(index);
            }

            public Builder setFile(int index, FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).setFile(index, value);
                return this;
            }

            public Builder setFile(int index, FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).setFile(index, builderForValue);
                return this;
            }

            public Builder addFile(FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(value);
                return this;
            }

            public Builder addFile(int index, FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(index, value);
                return this;
            }

            public Builder addFile(FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(builderForValue);
                return this;
            }

            public Builder addFile(int index, FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(index, builderForValue);
                return this;
            }

            public Builder addAllFile(Iterable<? extends FileDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addAllFile(values);
                return this;
            }

            public Builder clearFile() {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).clearFile();
                return this;
            }

            public Builder removeFile(int index) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).removeFile(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileDescriptorSet();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"file_", FileDescriptorProto.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileDescriptorSet> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileDescriptorSet.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FileDescriptorSet.class, DEFAULT_INSTANCE);
        }

        public static FileDescriptorSet getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileDescriptorSet> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {4, 5, 6, 7, 8}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FileDescriptorProto extends GeneratedMessageLite<FileDescriptorProto, Builder> implements FileDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final FileDescriptorProto DEFAULT_INSTANCE = new FileDescriptorProto();
        public static final int DEPENDENCY_FIELD_NUMBER = 3;
        public static final int ENUM_TYPE_FIELD_NUMBER = 5;
        public static final int EXTENSION_FIELD_NUMBER = 7;
        public static final int MESSAGE_TYPE_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static final int PACKAGE_FIELD_NUMBER = 2;
        private static volatile Parser<FileDescriptorProto> PARSER = null;
        public static final int PUBLIC_DEPENDENCY_FIELD_NUMBER = 10;
        public static final int SERVICE_FIELD_NUMBER = 6;
        public static final int SOURCE_CODE_INFO_FIELD_NUMBER = 9;
        public static final int SYNTAX_FIELD_NUMBER = 12;
        public static final int WEAK_DEPENDENCY_FIELD_NUMBER = 11;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> dependency_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 5, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<EnumDescriptorProto> enumType_ = emptyProtobufList();
        @ProtoField(fieldNumber = 7, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<FieldDescriptorProto> extension_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<DescriptorProto> messageType_ = emptyProtobufList();
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private FileOptions options_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String package_ = "";
        @ProtoField(fieldNumber = 10, type = FieldType.INT32_LIST)
        private Internal.IntList publicDependency_ = emptyIntList();
        @ProtoField(fieldNumber = 6, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ServiceDescriptorProto> service_ = emptyProtobufList();
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private SourceCodeInfo sourceCodeInfo_;
        @ProtoField(fieldNumber = 12, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String syntax_ = "";
        @ProtoField(fieldNumber = 11, type = FieldType.INT32_LIST)
        private Internal.IntList weakDependency_ = emptyIntList();

        private FileDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPackage() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getPackage() {
            return this.package_;
        }

        public ByteString getPackageBytes() {
            return ByteString.copyFromUtf8(this.package_);
        }

        /* access modifiers changed from: private */
        public void setPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.package_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackage() {
            this.bitField0_ &= -3;
            this.package_ = getDefaultInstance().getPackage();
        }

        /* access modifiers changed from: private */
        public void setPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.package_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<String> getDependencyList() {
            return this.dependency_;
        }

        public int getDependencyCount() {
            return this.dependency_.size();
        }

        public String getDependency(int index) {
            return this.dependency_.get(index);
        }

        public ByteString getDependencyBytes(int index) {
            return ByteString.copyFromUtf8(this.dependency_.get(index));
        }

        private void ensureDependencyIsMutable() {
            if (!this.dependency_.isModifiable()) {
                this.dependency_ = GeneratedMessageLite.mutableCopy(this.dependency_);
            }
        }

        /* access modifiers changed from: private */
        public void setDependency(int index, String value) {
            if (value != null) {
                ensureDependencyIsMutable();
                this.dependency_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addDependency(String value) {
            if (value != null) {
                ensureDependencyIsMutable();
                this.dependency_.add(value);
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
        public void addAllDependency(Iterable<String> values) {
            ensureDependencyIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.dependency_);
        }

        /* access modifiers changed from: private */
        public void clearDependency() {
            this.dependency_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addDependencyBytes(ByteString value) {
            if (value != null) {
                ensureDependencyIsMutable();
                this.dependency_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public List<Integer> getPublicDependencyList() {
            return this.publicDependency_;
        }

        public int getPublicDependencyCount() {
            return this.publicDependency_.size();
        }

        public int getPublicDependency(int index) {
            return this.publicDependency_.getInt(index);
        }

        private void ensurePublicDependencyIsMutable() {
            if (!this.publicDependency_.isModifiable()) {
                this.publicDependency_ = GeneratedMessageLite.mutableCopy(this.publicDependency_);
            }
        }

        /* access modifiers changed from: private */
        public void setPublicDependency(int index, int value) {
            ensurePublicDependencyIsMutable();
            this.publicDependency_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addPublicDependency(int value) {
            ensurePublicDependencyIsMutable();
            this.publicDependency_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPublicDependency(Iterable<? extends Integer> values) {
            ensurePublicDependencyIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.publicDependency_);
        }

        /* access modifiers changed from: private */
        public void clearPublicDependency() {
            this.publicDependency_ = emptyIntList();
        }

        public List<Integer> getWeakDependencyList() {
            return this.weakDependency_;
        }

        public int getWeakDependencyCount() {
            return this.weakDependency_.size();
        }

        public int getWeakDependency(int index) {
            return this.weakDependency_.getInt(index);
        }

        private void ensureWeakDependencyIsMutable() {
            if (!this.weakDependency_.isModifiable()) {
                this.weakDependency_ = GeneratedMessageLite.mutableCopy(this.weakDependency_);
            }
        }

        /* access modifiers changed from: private */
        public void setWeakDependency(int index, int value) {
            ensureWeakDependencyIsMutable();
            this.weakDependency_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addWeakDependency(int value) {
            ensureWeakDependencyIsMutable();
            this.weakDependency_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWeakDependency(Iterable<? extends Integer> values) {
            ensureWeakDependencyIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.weakDependency_);
        }

        /* access modifiers changed from: private */
        public void clearWeakDependency() {
            this.weakDependency_ = emptyIntList();
        }

        public List<DescriptorProto> getMessageTypeList() {
            return this.messageType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
            return this.messageType_;
        }

        public int getMessageTypeCount() {
            return this.messageType_.size();
        }

        public DescriptorProto getMessageType(int index) {
            return this.messageType_.get(index);
        }

        public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int index) {
            return this.messageType_.get(index);
        }

        private void ensureMessageTypeIsMutable() {
            if (!this.messageType_.isModifiable()) {
                this.messageType_ = GeneratedMessageLite.mutableCopy(this.messageType_);
            }
        }

        /* access modifiers changed from: private */
        public void setMessageType(int index, DescriptorProto value) {
            if (value != null) {
                ensureMessageTypeIsMutable();
                this.messageType_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMessageType(int index, DescriptorProto.Builder builderForValue) {
            ensureMessageTypeIsMutable();
            this.messageType_.set(index, (DescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMessageType(DescriptorProto value) {
            if (value != null) {
                ensureMessageTypeIsMutable();
                this.messageType_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMessageType(int index, DescriptorProto value) {
            if (value != null) {
                ensureMessageTypeIsMutable();
                this.messageType_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMessageType(DescriptorProto.Builder builderForValue) {
            ensureMessageTypeIsMutable();
            this.messageType_.add((DescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMessageType(int index, DescriptorProto.Builder builderForValue) {
            ensureMessageTypeIsMutable();
            this.messageType_.add(index, (DescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$DescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$DescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllMessageType(Iterable<? extends DescriptorProto> values) {
            ensureMessageTypeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.messageType_);
        }

        /* access modifiers changed from: private */
        public void clearMessageType() {
            this.messageType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeMessageType(int index) {
            ensureMessageTypeIsMutable();
            this.messageType_.remove(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return this.enumType_.get(index);
        }

        private void ensureEnumTypeIsMutable() {
            if (!this.enumType_.isModifiable()) {
                this.enumType_ = GeneratedMessageLite.mutableCopy(this.enumType_);
            }
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.set(index, (EnumDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.add((EnumDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.add(index, (EnumDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$EnumDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$EnumDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
            ensureEnumTypeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.enumType_);
        }

        /* access modifiers changed from: private */
        public void clearEnumType() {
            this.enumType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeEnumType(int index) {
            ensureEnumTypeIsMutable();
            this.enumType_.remove(index);
        }

        public List<ServiceDescriptorProto> getServiceList() {
            return this.service_;
        }

        public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
            return this.service_;
        }

        public int getServiceCount() {
            return this.service_.size();
        }

        public ServiceDescriptorProto getService(int index) {
            return this.service_.get(index);
        }

        public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int index) {
            return this.service_.get(index);
        }

        private void ensureServiceIsMutable() {
            if (!this.service_.isModifiable()) {
                this.service_ = GeneratedMessageLite.mutableCopy(this.service_);
            }
        }

        /* access modifiers changed from: private */
        public void setService(int index, ServiceDescriptorProto value) {
            if (value != null) {
                ensureServiceIsMutable();
                this.service_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setService(int index, ServiceDescriptorProto.Builder builderForValue) {
            ensureServiceIsMutable();
            this.service_.set(index, (ServiceDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addService(ServiceDescriptorProto value) {
            if (value != null) {
                ensureServiceIsMutable();
                this.service_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addService(int index, ServiceDescriptorProto value) {
            if (value != null) {
                ensureServiceIsMutable();
                this.service_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addService(ServiceDescriptorProto.Builder builderForValue) {
            ensureServiceIsMutable();
            this.service_.add((ServiceDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addService(int index, ServiceDescriptorProto.Builder builderForValue) {
            ensureServiceIsMutable();
            this.service_.add(index, (ServiceDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$ServiceDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$ServiceDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllService(Iterable<? extends ServiceDescriptorProto> values) {
            ensureServiceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.service_);
        }

        /* access modifiers changed from: private */
        public void clearService() {
            this.service_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeService(int index) {
            ensureServiceIsMutable();
            this.service_.remove(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return this.extension_.get(index);
        }

        private void ensureExtensionIsMutable() {
            if (!this.extension_.isModifiable()) {
                this.extension_ = GeneratedMessageLite.mutableCopy(this.extension_);
            }
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.set(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.add((FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.add(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$FieldDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$FieldDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
            ensureExtensionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.extension_);
        }

        /* access modifiers changed from: private */
        public void clearExtension() {
            this.extension_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtension(int index) {
            ensureExtensionIsMutable();
            this.extension_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) != 0;
        }

        public FileOptions getOptions() {
            FileOptions fileOptions = this.options_;
            return fileOptions == null ? FileOptions.getDefaultInstance() : fileOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(FileOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(FileOptions.Builder builderForValue) {
            this.options_ = (FileOptions) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(FileOptions value) {
            if (value != null) {
                FileOptions fileOptions = this.options_;
                if (fileOptions == null || fileOptions == FileOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (FileOptions) ((FileOptions.Builder) FileOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasSourceCodeInfo() {
            return (this.bitField0_ & 8) != 0;
        }

        public SourceCodeInfo getSourceCodeInfo() {
            SourceCodeInfo sourceCodeInfo = this.sourceCodeInfo_;
            return sourceCodeInfo == null ? SourceCodeInfo.getDefaultInstance() : sourceCodeInfo;
        }

        /* access modifiers changed from: private */
        public void setSourceCodeInfo(SourceCodeInfo value) {
            if (value != null) {
                this.sourceCodeInfo_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSourceCodeInfo(SourceCodeInfo.Builder builderForValue) {
            this.sourceCodeInfo_ = (SourceCodeInfo) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeSourceCodeInfo(SourceCodeInfo value) {
            if (value != null) {
                SourceCodeInfo sourceCodeInfo = this.sourceCodeInfo_;
                if (sourceCodeInfo == null || sourceCodeInfo == SourceCodeInfo.getDefaultInstance()) {
                    this.sourceCodeInfo_ = value;
                } else {
                    this.sourceCodeInfo_ = (SourceCodeInfo) ((SourceCodeInfo.Builder) SourceCodeInfo.newBuilder(this.sourceCodeInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSourceCodeInfo() {
            this.sourceCodeInfo_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasSyntax() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getSyntax() {
            return this.syntax_;
        }

        public ByteString getSyntaxBytes() {
            return ByteString.copyFromUtf8(this.syntax_);
        }

        /* access modifiers changed from: private */
        public void setSyntax(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.syntax_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSyntax() {
            this.bitField0_ &= -17;
            this.syntax_ = getDefaultInstance().getSyntax();
        }

        /* access modifiers changed from: private */
        public void setSyntaxBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.syntax_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static FileDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FileDescriptorProto, Builder> implements FileDescriptorProtoOrBuilder {
            private Builder() {
                super(FileDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((FileDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((FileDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((FileDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasPackage() {
                return ((FileDescriptorProto) this.instance).hasPackage();
            }

            public String getPackage() {
                return ((FileDescriptorProto) this.instance).getPackage();
            }

            public ByteString getPackageBytes() {
                return ((FileDescriptorProto) this.instance).getPackageBytes();
            }

            public Builder setPackage(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPackage(value);
                return this;
            }

            public Builder clearPackage() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearPackage();
                return this;
            }

            public Builder setPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPackageBytes(value);
                return this;
            }

            public List<String> getDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getDependencyList());
            }

            public int getDependencyCount() {
                return ((FileDescriptorProto) this.instance).getDependencyCount();
            }

            public String getDependency(int index) {
                return ((FileDescriptorProto) this.instance).getDependency(index);
            }

            public ByteString getDependencyBytes(int index) {
                return ((FileDescriptorProto) this.instance).getDependencyBytes(index);
            }

            public Builder setDependency(int index, String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setDependency(index, value);
                return this;
            }

            public Builder addDependency(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addDependency(value);
                return this;
            }

            public Builder addAllDependency(Iterable<String> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllDependency(values);
                return this;
            }

            public Builder clearDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearDependency();
                return this;
            }

            public Builder addDependencyBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addDependencyBytes(value);
                return this;
            }

            public List<Integer> getPublicDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getPublicDependencyList());
            }

            public int getPublicDependencyCount() {
                return ((FileDescriptorProto) this.instance).getPublicDependencyCount();
            }

            public int getPublicDependency(int index) {
                return ((FileDescriptorProto) this.instance).getPublicDependency(index);
            }

            public Builder setPublicDependency(int index, int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPublicDependency(index, value);
                return this;
            }

            public Builder addPublicDependency(int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addPublicDependency(value);
                return this;
            }

            public Builder addAllPublicDependency(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllPublicDependency(values);
                return this;
            }

            public Builder clearPublicDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearPublicDependency();
                return this;
            }

            public List<Integer> getWeakDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getWeakDependencyList());
            }

            public int getWeakDependencyCount() {
                return ((FileDescriptorProto) this.instance).getWeakDependencyCount();
            }

            public int getWeakDependency(int index) {
                return ((FileDescriptorProto) this.instance).getWeakDependency(index);
            }

            public Builder setWeakDependency(int index, int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setWeakDependency(index, value);
                return this;
            }

            public Builder addWeakDependency(int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addWeakDependency(value);
                return this;
            }

            public Builder addAllWeakDependency(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllWeakDependency(values);
                return this;
            }

            public Builder clearWeakDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearWeakDependency();
                return this;
            }

            public List<DescriptorProto> getMessageTypeList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getMessageTypeList());
            }

            public int getMessageTypeCount() {
                return ((FileDescriptorProto) this.instance).getMessageTypeCount();
            }

            public DescriptorProto getMessageType(int index) {
                return ((FileDescriptorProto) this.instance).getMessageType(index);
            }

            public Builder setMessageType(int index, DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setMessageType(index, value);
                return this;
            }

            public Builder setMessageType(int index, DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setMessageType(index, builderForValue);
                return this;
            }

            public Builder addMessageType(DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(value);
                return this;
            }

            public Builder addMessageType(int index, DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(index, value);
                return this;
            }

            public Builder addMessageType(DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(builderForValue);
                return this;
            }

            public Builder addMessageType(int index, DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(index, builderForValue);
                return this;
            }

            public Builder addAllMessageType(Iterable<? extends DescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllMessageType(values);
                return this;
            }

            public Builder clearMessageType() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearMessageType();
                return this;
            }

            public Builder removeMessageType(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeMessageType(index);
                return this;
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getEnumTypeList());
            }

            public int getEnumTypeCount() {
                return ((FileDescriptorProto) this.instance).getEnumTypeCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                return ((FileDescriptorProto) this.instance).getEnumType(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setEnumType(index, value);
                return this;
            }

            public Builder setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setEnumType(index, builderForValue);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(value);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(index, value);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(builderForValue);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(index, builderForValue);
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllEnumType(values);
                return this;
            }

            public Builder clearEnumType() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearEnumType();
                return this;
            }

            public Builder removeEnumType(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeEnumType(index);
                return this;
            }

            public List<ServiceDescriptorProto> getServiceList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getServiceList());
            }

            public int getServiceCount() {
                return ((FileDescriptorProto) this.instance).getServiceCount();
            }

            public ServiceDescriptorProto getService(int index) {
                return ((FileDescriptorProto) this.instance).getService(index);
            }

            public Builder setService(int index, ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setService(index, value);
                return this;
            }

            public Builder setService(int index, ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setService(index, builderForValue);
                return this;
            }

            public Builder addService(ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(value);
                return this;
            }

            public Builder addService(int index, ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(index, value);
                return this;
            }

            public Builder addService(ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(builderForValue);
                return this;
            }

            public Builder addService(int index, ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(index, builderForValue);
                return this;
            }

            public Builder addAllService(Iterable<? extends ServiceDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllService(values);
                return this;
            }

            public Builder clearService() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearService();
                return this;
            }

            public Builder removeService(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeService(index);
                return this;
            }

            public List<FieldDescriptorProto> getExtensionList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getExtensionList());
            }

            public int getExtensionCount() {
                return ((FileDescriptorProto) this.instance).getExtensionCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                return ((FileDescriptorProto) this.instance).getExtension(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setExtension(index, value);
                return this;
            }

            public Builder setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setExtension(index, builderForValue);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(value);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(index, value);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(builderForValue);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(index, builderForValue);
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllExtension(values);
                return this;
            }

            public Builder clearExtension() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearExtension();
                return this;
            }

            public Builder removeExtension(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeExtension(index);
                return this;
            }

            public boolean hasOptions() {
                return ((FileDescriptorProto) this.instance).hasOptions();
            }

            public FileOptions getOptions() {
                return ((FileDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(FileOptions value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(FileOptions.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(FileOptions value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public boolean hasSourceCodeInfo() {
                return ((FileDescriptorProto) this.instance).hasSourceCodeInfo();
            }

            public SourceCodeInfo getSourceCodeInfo() {
                return ((FileDescriptorProto) this.instance).getSourceCodeInfo();
            }

            public Builder setSourceCodeInfo(SourceCodeInfo value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSourceCodeInfo(value);
                return this;
            }

            public Builder setSourceCodeInfo(SourceCodeInfo.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSourceCodeInfo(builderForValue);
                return this;
            }

            public Builder mergeSourceCodeInfo(SourceCodeInfo value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).mergeSourceCodeInfo(value);
                return this;
            }

            public Builder clearSourceCodeInfo() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearSourceCodeInfo();
                return this;
            }

            public boolean hasSyntax() {
                return ((FileDescriptorProto) this.instance).hasSyntax();
            }

            public String getSyntax() {
                return ((FileDescriptorProto) this.instance).getSyntax();
            }

            public ByteString getSyntaxBytes() {
                return ((FileDescriptorProto) this.instance).getSyntaxBytes();
            }

            public Builder setSyntax(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSyntax(value);
                return this;
            }

            public Builder clearSyntax() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearSyntax();
                return this;
            }

            public Builder setSyntaxBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSyntaxBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0007\u0005\u0001\b\u0000\u0002\b\u0001\u0003\u001a\u0004Л\u0005Л\u0006Л\u0007Л\bЉ\u0002\t\t\u0003\n\u0016\u000b\u0016\f\b\u0004", new Object[]{"bitField0_", "name_", "package_", "dependency_", "messageType_", DescriptorProto.class, "enumType_", EnumDescriptorProto.class, GoogleLoginServiceConstants.FEATURE_SERVICE_PREFIX, ServiceDescriptorProto.class, "extension_", FieldDescriptorProto.class, "options_", "sourceCodeInfo_", "publicDependency_", "weakDependency_", "syntax_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FileDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static FileDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {2, 6, 3, 4, 5, 8, 7}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DescriptorProto extends GeneratedMessageLite<DescriptorProto, Builder> implements DescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final DescriptorProto DEFAULT_INSTANCE = new DescriptorProto();
        public static final int ENUM_TYPE_FIELD_NUMBER = 4;
        public static final int EXTENSION_FIELD_NUMBER = 6;
        public static final int EXTENSION_RANGE_FIELD_NUMBER = 5;
        public static final int FIELD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NESTED_TYPE_FIELD_NUMBER = 3;
        public static final int ONEOF_DECL_FIELD_NUMBER = 8;
        public static final int OPTIONS_FIELD_NUMBER = 7;
        private static volatile Parser<DescriptorProto> PARSER = null;
        public static final int RESERVED_NAME_FIELD_NUMBER = 10;
        public static final int RESERVED_RANGE_FIELD_NUMBER = 9;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<EnumDescriptorProto> enumType_ = emptyProtobufList();
        @ProtoField(fieldNumber = 5, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ExtensionRange> extensionRange_ = emptyProtobufList();
        @ProtoField(fieldNumber = 6, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<FieldDescriptorProto> extension_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<FieldDescriptorProto> field_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 3, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<DescriptorProto> nestedType_ = emptyProtobufList();
        @ProtoField(fieldNumber = 8, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<OneofDescriptorProto> oneofDecl_ = emptyProtobufList();
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private MessageOptions options_;
        @ProtoField(fieldNumber = 10, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> reservedName_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 9, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ReservedRange> reservedRange_ = emptyProtobufList();

        public interface ExtensionRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            ExtensionRangeOptions getOptions();

            int getStart();

            boolean hasEnd();

            boolean hasOptions();

            boolean hasStart();
        }

        public interface ReservedRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        private DescriptorProto() {
        }

        @ProtoMessage(checkInitialized = {3}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class ExtensionRange extends GeneratedMessageLite<ExtensionRange, Builder> implements ExtensionRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final ExtensionRange DEFAULT_INSTANCE = new ExtensionRange();
            public static final int END_FIELD_NUMBER = 2;
            public static final int OPTIONS_FIELD_NUMBER = 3;
            private static volatile Parser<ExtensionRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int end_;
            private byte memoizedIsInitialized = 2;
            @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private ExtensionRangeOptions options_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int start_;

            private ExtensionRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 4) != 0;
            }

            public ExtensionRangeOptions getOptions() {
                ExtensionRangeOptions extensionRangeOptions = this.options_;
                return extensionRangeOptions == null ? ExtensionRangeOptions.getDefaultInstance() : extensionRangeOptions;
            }

            /* access modifiers changed from: private */
            public void setOptions(ExtensionRangeOptions value) {
                if (value != null) {
                    this.options_ = value;
                    this.bitField0_ |= 4;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void setOptions(ExtensionRangeOptions.Builder builderForValue) {
                this.options_ = (ExtensionRangeOptions) builderForValue.build();
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void mergeOptions(ExtensionRangeOptions value) {
                if (value != null) {
                    ExtensionRangeOptions extensionRangeOptions = this.options_;
                    if (extensionRangeOptions == null || extensionRangeOptions == ExtensionRangeOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = (ExtensionRangeOptions) ((ExtensionRangeOptions.Builder) ExtensionRangeOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                    }
                    this.bitField0_ |= 4;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearOptions() {
                this.options_ = null;
                this.bitField0_ &= -5;
            }

            public static ExtensionRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(InputStream input) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input) throws IOException {
                return (ExtensionRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExtensionRange parseFrom(CodedInputStream input) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(ExtensionRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<ExtensionRange, Builder> implements ExtensionRangeOrBuilder {
                private Builder() {
                    super(ExtensionRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((ExtensionRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((ExtensionRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((ExtensionRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((ExtensionRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearEnd();
                    return this;
                }

                public boolean hasOptions() {
                    return ((ExtensionRange) this.instance).hasOptions();
                }

                public ExtensionRangeOptions getOptions() {
                    return ((ExtensionRange) this.instance).getOptions();
                }

                public Builder setOptions(ExtensionRangeOptions value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setOptions(value);
                    return this;
                }

                public Builder setOptions(ExtensionRangeOptions.Builder builderForValue) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setOptions(builderForValue);
                    return this;
                }

                public Builder mergeOptions(ExtensionRangeOptions value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).mergeOptions(value);
                    return this;
                }

                public Builder clearOptions() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearOptions();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                int i = 1;
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new ExtensionRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001\u0004\u0000\u0002\u0004\u0001\u0003Љ\u0002", new Object[]{"bitField0_", "start_", "end_", "options_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<ExtensionRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (ExtensionRange.class) {
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
                        if (arg0 == null) {
                            i = 0;
                        }
                        this.memoizedIsInitialized = (byte) i;
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(ExtensionRange.class, DEFAULT_INSTANCE);
            }

            public static ExtensionRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ExtensionRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class ReservedRange extends GeneratedMessageLite<ReservedRange, Builder> implements ReservedRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final ReservedRange DEFAULT_INSTANCE = new ReservedRange();
            public static final int END_FIELD_NUMBER = 2;
            private static volatile Parser<ReservedRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int end_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int start_;

            private ReservedRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public static ReservedRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(InputStream input) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ReservedRange parseDelimitedFrom(InputStream input) throws IOException {
                return (ReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ReservedRange parseFrom(CodedInputStream input) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(ReservedRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<ReservedRange, Builder> implements ReservedRangeOrBuilder {
                private Builder() {
                    super(ReservedRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((ReservedRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((ReservedRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((ReservedRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((ReservedRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((ReservedRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((ReservedRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((ReservedRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((ReservedRange) this.instance).clearEnd();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new ReservedRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "start_", "end_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<ReservedRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (ReservedRange.class) {
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
                GeneratedMessageLite.registerDefaultInstance(ReservedRange.class, DEFAULT_INSTANCE);
            }

            public static ReservedRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ReservedRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<FieldDescriptorProto> getFieldList() {
            return this.field_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
            return this.field_;
        }

        public int getFieldCount() {
            return this.field_.size();
        }

        public FieldDescriptorProto getField(int index) {
            return this.field_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int index) {
            return this.field_.get(index);
        }

        private void ensureFieldIsMutable() {
            if (!this.field_.isModifiable()) {
                this.field_ = GeneratedMessageLite.mutableCopy(this.field_);
            }
        }

        /* access modifiers changed from: private */
        public void setField(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureFieldIsMutable();
                this.field_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setField(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureFieldIsMutable();
            this.field_.set(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addField(FieldDescriptorProto value) {
            if (value != null) {
                ensureFieldIsMutable();
                this.field_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addField(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureFieldIsMutable();
                this.field_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addField(FieldDescriptorProto.Builder builderForValue) {
            ensureFieldIsMutable();
            this.field_.add((FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addField(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureFieldIsMutable();
            this.field_.add(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$FieldDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$FieldDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllField(Iterable<? extends FieldDescriptorProto> values) {
            ensureFieldIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.field_);
        }

        /* access modifiers changed from: private */
        public void clearField() {
            this.field_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeField(int index) {
            ensureFieldIsMutable();
            this.field_.remove(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return this.extension_.get(index);
        }

        private void ensureExtensionIsMutable() {
            if (!this.extension_.isModifiable()) {
                this.extension_ = GeneratedMessageLite.mutableCopy(this.extension_);
            }
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.set(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto value) {
            if (value != null) {
                ensureExtensionIsMutable();
                this.extension_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.add((FieldDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
            ensureExtensionIsMutable();
            this.extension_.add(index, (FieldDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$FieldDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$FieldDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
            ensureExtensionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.extension_);
        }

        /* access modifiers changed from: private */
        public void clearExtension() {
            this.extension_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtension(int index) {
            ensureExtensionIsMutable();
            this.extension_.remove(index);
        }

        public List<DescriptorProto> getNestedTypeList() {
            return this.nestedType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
            return this.nestedType_;
        }

        public int getNestedTypeCount() {
            return this.nestedType_.size();
        }

        public DescriptorProto getNestedType(int index) {
            return this.nestedType_.get(index);
        }

        public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int index) {
            return this.nestedType_.get(index);
        }

        private void ensureNestedTypeIsMutable() {
            if (!this.nestedType_.isModifiable()) {
                this.nestedType_ = GeneratedMessageLite.mutableCopy(this.nestedType_);
            }
        }

        /* access modifiers changed from: private */
        public void setNestedType(int index, DescriptorProto value) {
            if (value != null) {
                ensureNestedTypeIsMutable();
                this.nestedType_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNestedType(int index, Builder builderForValue) {
            ensureNestedTypeIsMutable();
            this.nestedType_.set(index, (DescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNestedType(DescriptorProto value) {
            if (value != null) {
                ensureNestedTypeIsMutable();
                this.nestedType_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNestedType(int index, DescriptorProto value) {
            if (value != null) {
                ensureNestedTypeIsMutable();
                this.nestedType_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNestedType(Builder builderForValue) {
            ensureNestedTypeIsMutable();
            this.nestedType_.add((DescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNestedType(int index, Builder builderForValue) {
            ensureNestedTypeIsMutable();
            this.nestedType_.add(index, (DescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$DescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$DescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllNestedType(Iterable<? extends DescriptorProto> values) {
            ensureNestedTypeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.nestedType_);
        }

        /* access modifiers changed from: private */
        public void clearNestedType() {
            this.nestedType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeNestedType(int index) {
            ensureNestedTypeIsMutable();
            this.nestedType_.remove(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return this.enumType_.get(index);
        }

        private void ensureEnumTypeIsMutable() {
            if (!this.enumType_.isModifiable()) {
                this.enumType_ = GeneratedMessageLite.mutableCopy(this.enumType_);
            }
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.set(index, (EnumDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto value) {
            if (value != null) {
                ensureEnumTypeIsMutable();
                this.enumType_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.add((EnumDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
            ensureEnumTypeIsMutable();
            this.enumType_.add(index, (EnumDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$EnumDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$EnumDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
            ensureEnumTypeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.enumType_);
        }

        /* access modifiers changed from: private */
        public void clearEnumType() {
            this.enumType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeEnumType(int index) {
            ensureEnumTypeIsMutable();
            this.enumType_.remove(index);
        }

        public List<ExtensionRange> getExtensionRangeList() {
            return this.extensionRange_;
        }

        public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
            return this.extensionRange_;
        }

        public int getExtensionRangeCount() {
            return this.extensionRange_.size();
        }

        public ExtensionRange getExtensionRange(int index) {
            return this.extensionRange_.get(index);
        }

        public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int index) {
            return this.extensionRange_.get(index);
        }

        private void ensureExtensionRangeIsMutable() {
            if (!this.extensionRange_.isModifiable()) {
                this.extensionRange_ = GeneratedMessageLite.mutableCopy(this.extensionRange_);
            }
        }

        /* access modifiers changed from: private */
        public void setExtensionRange(int index, ExtensionRange value) {
            if (value != null) {
                ensureExtensionRangeIsMutable();
                this.extensionRange_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setExtensionRange(int index, ExtensionRange.Builder builderForValue) {
            ensureExtensionRangeIsMutable();
            this.extensionRange_.set(index, (ExtensionRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(ExtensionRange value) {
            if (value != null) {
                ensureExtensionRangeIsMutable();
                this.extensionRange_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(int index, ExtensionRange value) {
            if (value != null) {
                ensureExtensionRangeIsMutable();
                this.extensionRange_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(ExtensionRange.Builder builderForValue) {
            ensureExtensionRangeIsMutable();
            this.extensionRange_.add((ExtensionRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(int index, ExtensionRange.Builder builderForValue) {
            ensureExtensionRangeIsMutable();
            this.extensionRange_.add(index, (ExtensionRange) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$DescriptorProto$ExtensionRange>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$DescriptorProto$ExtensionRange>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllExtensionRange(Iterable<? extends ExtensionRange> values) {
            ensureExtensionRangeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.extensionRange_);
        }

        /* access modifiers changed from: private */
        public void clearExtensionRange() {
            this.extensionRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtensionRange(int index) {
            ensureExtensionRangeIsMutable();
            this.extensionRange_.remove(index);
        }

        public List<OneofDescriptorProto> getOneofDeclList() {
            return this.oneofDecl_;
        }

        public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
            return this.oneofDecl_;
        }

        public int getOneofDeclCount() {
            return this.oneofDecl_.size();
        }

        public OneofDescriptorProto getOneofDecl(int index) {
            return this.oneofDecl_.get(index);
        }

        public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int index) {
            return this.oneofDecl_.get(index);
        }

        private void ensureOneofDeclIsMutable() {
            if (!this.oneofDecl_.isModifiable()) {
                this.oneofDecl_ = GeneratedMessageLite.mutableCopy(this.oneofDecl_);
            }
        }

        /* access modifiers changed from: private */
        public void setOneofDecl(int index, OneofDescriptorProto value) {
            if (value != null) {
                ensureOneofDeclIsMutable();
                this.oneofDecl_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
            ensureOneofDeclIsMutable();
            this.oneofDecl_.set(index, (OneofDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(OneofDescriptorProto value) {
            if (value != null) {
                ensureOneofDeclIsMutable();
                this.oneofDecl_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(int index, OneofDescriptorProto value) {
            if (value != null) {
                ensureOneofDeclIsMutable();
                this.oneofDecl_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(OneofDescriptorProto.Builder builderForValue) {
            ensureOneofDeclIsMutable();
            this.oneofDecl_.add((OneofDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
            ensureOneofDeclIsMutable();
            this.oneofDecl_.add(index, (OneofDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$OneofDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$OneofDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllOneofDecl(Iterable<? extends OneofDescriptorProto> values) {
            ensureOneofDeclIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.oneofDecl_);
        }

        /* access modifiers changed from: private */
        public void clearOneofDecl() {
            this.oneofDecl_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeOneofDecl(int index) {
            ensureOneofDeclIsMutable();
            this.oneofDecl_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public MessageOptions getOptions() {
            MessageOptions messageOptions = this.options_;
            return messageOptions == null ? MessageOptions.getDefaultInstance() : messageOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(MessageOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(MessageOptions.Builder builderForValue) {
            this.options_ = (MessageOptions) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(MessageOptions value) {
            if (value != null) {
                MessageOptions messageOptions = this.options_;
                if (messageOptions == null || messageOptions == MessageOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (MessageOptions) ((MessageOptions.Builder) MessageOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public List<ReservedRange> getReservedRangeList() {
            return this.reservedRange_;
        }

        public List<? extends ReservedRangeOrBuilder> getReservedRangeOrBuilderList() {
            return this.reservedRange_;
        }

        public int getReservedRangeCount() {
            return this.reservedRange_.size();
        }

        public ReservedRange getReservedRange(int index) {
            return this.reservedRange_.get(index);
        }

        public ReservedRangeOrBuilder getReservedRangeOrBuilder(int index) {
            return this.reservedRange_.get(index);
        }

        private void ensureReservedRangeIsMutable() {
            if (!this.reservedRange_.isModifiable()) {
                this.reservedRange_ = GeneratedMessageLite.mutableCopy(this.reservedRange_);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, ReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, ReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.set(index, (ReservedRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addReservedRange(ReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, ReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedRange(ReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.add((ReservedRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, ReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(index, (ReservedRange) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$DescriptorProto$ReservedRange>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$DescriptorProto$ReservedRange>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllReservedRange(Iterable<? extends ReservedRange> values) {
            ensureReservedRangeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.reservedRange_);
        }

        /* access modifiers changed from: private */
        public void clearReservedRange() {
            this.reservedRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeReservedRange(int index) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.remove(index);
        }

        public List<String> getReservedNameList() {
            return this.reservedName_;
        }

        public int getReservedNameCount() {
            return this.reservedName_.size();
        }

        public String getReservedName(int index) {
            return this.reservedName_.get(index);
        }

        public ByteString getReservedNameBytes(int index) {
            return ByteString.copyFromUtf8(this.reservedName_.get(index));
        }

        private void ensureReservedNameIsMutable() {
            if (!this.reservedName_.isModifiable()) {
                this.reservedName_ = GeneratedMessageLite.mutableCopy(this.reservedName_);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedName(int index, String value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedName(String value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.add(value);
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
        public void addAllReservedName(Iterable<String> values) {
            ensureReservedNameIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.reservedName_);
        }

        /* access modifiers changed from: private */
        public void clearReservedName() {
            this.reservedName_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addReservedNameBytes(ByteString value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public static DescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(InputStream input) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (DescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DescriptorProto, Builder> implements DescriptorProtoOrBuilder {
            private Builder() {
                super(DescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((DescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((DescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((DescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<FieldDescriptorProto> getFieldList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getFieldList());
            }

            public int getFieldCount() {
                return ((DescriptorProto) this.instance).getFieldCount();
            }

            public FieldDescriptorProto getField(int index) {
                return ((DescriptorProto) this.instance).getField(index);
            }

            public Builder setField(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setField(index, value);
                return this;
            }

            public Builder setField(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setField(index, builderForValue);
                return this;
            }

            public Builder addField(FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(value);
                return this;
            }

            public Builder addField(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(index, value);
                return this;
            }

            public Builder addField(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(builderForValue);
                return this;
            }

            public Builder addField(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(index, builderForValue);
                return this;
            }

            public Builder addAllField(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllField(values);
                return this;
            }

            public Builder clearField() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearField();
                return this;
            }

            public Builder removeField(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeField(index);
                return this;
            }

            public List<FieldDescriptorProto> getExtensionList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getExtensionList());
            }

            public int getExtensionCount() {
                return ((DescriptorProto) this.instance).getExtensionCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                return ((DescriptorProto) this.instance).getExtension(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtension(index, value);
                return this;
            }

            public Builder setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtension(index, builderForValue);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(value);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(index, value);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(builderForValue);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(index, builderForValue);
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllExtension(values);
                return this;
            }

            public Builder clearExtension() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearExtension();
                return this;
            }

            public Builder removeExtension(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeExtension(index);
                return this;
            }

            public List<DescriptorProto> getNestedTypeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getNestedTypeList());
            }

            public int getNestedTypeCount() {
                return ((DescriptorProto) this.instance).getNestedTypeCount();
            }

            public DescriptorProto getNestedType(int index) {
                return ((DescriptorProto) this.instance).getNestedType(index);
            }

            public Builder setNestedType(int index, DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNestedType(index, value);
                return this;
            }

            public Builder setNestedType(int index, Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNestedType(index, builderForValue);
                return this;
            }

            public Builder addNestedType(DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(value);
                return this;
            }

            public Builder addNestedType(int index, DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(index, value);
                return this;
            }

            public Builder addNestedType(Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(builderForValue);
                return this;
            }

            public Builder addNestedType(int index, Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(index, builderForValue);
                return this;
            }

            public Builder addAllNestedType(Iterable<? extends DescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllNestedType(values);
                return this;
            }

            public Builder clearNestedType() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearNestedType();
                return this;
            }

            public Builder removeNestedType(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeNestedType(index);
                return this;
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getEnumTypeList());
            }

            public int getEnumTypeCount() {
                return ((DescriptorProto) this.instance).getEnumTypeCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                return ((DescriptorProto) this.instance).getEnumType(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setEnumType(index, value);
                return this;
            }

            public Builder setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setEnumType(index, builderForValue);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(value);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(index, value);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(builderForValue);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(index, builderForValue);
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllEnumType(values);
                return this;
            }

            public Builder clearEnumType() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearEnumType();
                return this;
            }

            public Builder removeEnumType(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeEnumType(index);
                return this;
            }

            public List<ExtensionRange> getExtensionRangeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getExtensionRangeList());
            }

            public int getExtensionRangeCount() {
                return ((DescriptorProto) this.instance).getExtensionRangeCount();
            }

            public ExtensionRange getExtensionRange(int index) {
                return ((DescriptorProto) this.instance).getExtensionRange(index);
            }

            public Builder setExtensionRange(int index, ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtensionRange(index, value);
                return this;
            }

            public Builder setExtensionRange(int index, ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtensionRange(index, builderForValue);
                return this;
            }

            public Builder addExtensionRange(ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(value);
                return this;
            }

            public Builder addExtensionRange(int index, ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(index, value);
                return this;
            }

            public Builder addExtensionRange(ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(builderForValue);
                return this;
            }

            public Builder addExtensionRange(int index, ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(index, builderForValue);
                return this;
            }

            public Builder addAllExtensionRange(Iterable<? extends ExtensionRange> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllExtensionRange(values);
                return this;
            }

            public Builder clearExtensionRange() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearExtensionRange();
                return this;
            }

            public Builder removeExtensionRange(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeExtensionRange(index);
                return this;
            }

            public List<OneofDescriptorProto> getOneofDeclList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getOneofDeclList());
            }

            public int getOneofDeclCount() {
                return ((DescriptorProto) this.instance).getOneofDeclCount();
            }

            public OneofDescriptorProto getOneofDecl(int index) {
                return ((DescriptorProto) this.instance).getOneofDecl(index);
            }

            public Builder setOneofDecl(int index, OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOneofDecl(index, value);
                return this;
            }

            public Builder setOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOneofDecl(index, builderForValue);
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(value);
                return this;
            }

            public Builder addOneofDecl(int index, OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(index, value);
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(builderForValue);
                return this;
            }

            public Builder addOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(index, builderForValue);
                return this;
            }

            public Builder addAllOneofDecl(Iterable<? extends OneofDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllOneofDecl(values);
                return this;
            }

            public Builder clearOneofDecl() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearOneofDecl();
                return this;
            }

            public Builder removeOneofDecl(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeOneofDecl(index);
                return this;
            }

            public boolean hasOptions() {
                return ((DescriptorProto) this.instance).hasOptions();
            }

            public MessageOptions getOptions() {
                return ((DescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(MessageOptions value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(MessageOptions.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(MessageOptions value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearOptions();
                return this;
            }

            public List<ReservedRange> getReservedRangeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getReservedRangeList());
            }

            public int getReservedRangeCount() {
                return ((DescriptorProto) this.instance).getReservedRangeCount();
            }

            public ReservedRange getReservedRange(int index) {
                return ((DescriptorProto) this.instance).getReservedRange(index);
            }

            public Builder setReservedRange(int index, ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedRange(index, value);
                return this;
            }

            public Builder setReservedRange(int index, ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedRange(index, builderForValue);
                return this;
            }

            public Builder addReservedRange(ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(value);
                return this;
            }

            public Builder addReservedRange(int index, ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(index, value);
                return this;
            }

            public Builder addReservedRange(ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(builderForValue);
                return this;
            }

            public Builder addReservedRange(int index, ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(index, builderForValue);
                return this;
            }

            public Builder addAllReservedRange(Iterable<? extends ReservedRange> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllReservedRange(values);
                return this;
            }

            public Builder clearReservedRange() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearReservedRange();
                return this;
            }

            public Builder removeReservedRange(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeReservedRange(index);
                return this;
            }

            public List<String> getReservedNameList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getReservedNameList());
            }

            public int getReservedNameCount() {
                return ((DescriptorProto) this.instance).getReservedNameCount();
            }

            public String getReservedName(int index) {
                return ((DescriptorProto) this.instance).getReservedName(index);
            }

            public ByteString getReservedNameBytes(int index) {
                return ((DescriptorProto) this.instance).getReservedNameBytes(index);
            }

            public Builder setReservedName(int index, String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedName(index, value);
                return this;
            }

            public Builder addReservedName(String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedName(value);
                return this;
            }

            public Builder addAllReservedName(Iterable<String> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllReservedName(values);
                return this;
            }

            public Builder clearReservedName() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearReservedName();
                return this;
            }

            public Builder addReservedNameBytes(ByteString value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0000\u0001\u0001\n\n\u0000\b\u0007\u0001\b\u0000\u0002Л\u0003Л\u0004Л\u0005Л\u0006Л\u0007Љ\u0001\bЛ\t\u001b\n\u001a", new Object[]{"bitField0_", "name_", "field_", FieldDescriptorProto.class, "nestedType_", DescriptorProto.class, "enumType_", EnumDescriptorProto.class, "extensionRange_", ExtensionRange.class, "extension_", FieldDescriptorProto.class, "options_", "oneofDecl_", OneofDescriptorProto.class, "reservedRange_", ReservedRange.class, "reservedName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (DescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(DescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static DescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ExtensionRangeOptions extends GeneratedMessageLite.ExtendableMessage<ExtensionRangeOptions, Builder> implements ExtensionRangeOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final ExtensionRangeOptions DEFAULT_INSTANCE = new ExtensionRangeOptions();
        private static volatile Parser<ExtensionRangeOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private ExtensionRangeOptions() {
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static ExtensionRangeOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(InputStream input) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExtensionRangeOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (ExtensionRangeOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(CodedInputStream input) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExtensionRangeOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ExtensionRangeOptions, Builder> implements ExtensionRangeOptionsOrBuilder {
            private Builder() {
                super(ExtensionRangeOptions.DEFAULT_INSTANCE);
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((ExtensionRangeOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((ExtensionRangeOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((ExtensionRangeOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ExtensionRangeOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000ϧϧ\u0001\u0000\u0001\u0001ϧЛ", new Object[]{"uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ExtensionRangeOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExtensionRangeOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ExtensionRangeOptions.class, DEFAULT_INSTANCE);
        }

        public static ExtensionRangeOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExtensionRangeOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {8}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FieldDescriptorProto extends GeneratedMessageLite<FieldDescriptorProto, Builder> implements FieldDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final FieldDescriptorProto DEFAULT_INSTANCE = new FieldDescriptorProto();
        public static final int DEFAULT_VALUE_FIELD_NUMBER = 7;
        public static final int EXTENDEE_FIELD_NUMBER = 2;
        public static final int JSON_NAME_FIELD_NUMBER = 10;
        public static final int LABEL_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 3;
        public static final int ONEOF_INDEX_FIELD_NUMBER = 9;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        private static volatile Parser<FieldDescriptorProto> PARSER = null;
        public static final int TYPE_FIELD_NUMBER = 5;
        public static final int TYPE_NAME_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private String defaultValue_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String extendee_ = "";
        @ProtoField(fieldNumber = 10, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private String jsonName_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int label_ = 1;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int number_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int oneofIndex_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private FieldOptions options_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String typeName_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int type_ = 1;

        private FieldDescriptorProto() {
        }

        public enum Type implements Internal.EnumLite {
            TYPE_DOUBLE(1),
            TYPE_FLOAT(2),
            TYPE_INT64(3),
            TYPE_UINT64(4),
            TYPE_INT32(5),
            TYPE_FIXED64(6),
            TYPE_FIXED32(7),
            TYPE_BOOL(8),
            TYPE_STRING(9),
            TYPE_GROUP(10),
            TYPE_MESSAGE(11),
            TYPE_BYTES(12),
            TYPE_UINT32(13),
            TYPE_ENUM(14),
            TYPE_SFIXED32(15),
            TYPE_SFIXED64(16),
            TYPE_SINT32(17),
            TYPE_SINT64(18);
            
            public static final int TYPE_BOOL_VALUE = 8;
            public static final int TYPE_BYTES_VALUE = 12;
            public static final int TYPE_DOUBLE_VALUE = 1;
            public static final int TYPE_ENUM_VALUE = 14;
            public static final int TYPE_FIXED32_VALUE = 7;
            public static final int TYPE_FIXED64_VALUE = 6;
            public static final int TYPE_FLOAT_VALUE = 2;
            public static final int TYPE_GROUP_VALUE = 10;
            public static final int TYPE_INT32_VALUE = 5;
            public static final int TYPE_INT64_VALUE = 3;
            public static final int TYPE_MESSAGE_VALUE = 11;
            public static final int TYPE_SFIXED32_VALUE = 15;
            public static final int TYPE_SFIXED64_VALUE = 16;
            public static final int TYPE_SINT32_VALUE = 17;
            public static final int TYPE_SINT64_VALUE = 18;
            public static final int TYPE_STRING_VALUE = 9;
            public static final int TYPE_UINT32_VALUE = 13;
            public static final int TYPE_UINT64_VALUE = 4;
            private static final Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() {
                public Type findValueByNumber(int number) {
                    return Type.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Type forNumber(int value2) {
                switch (value2) {
                    case 1:
                        return TYPE_DOUBLE;
                    case 2:
                        return TYPE_FLOAT;
                    case 3:
                        return TYPE_INT64;
                    case 4:
                        return TYPE_UINT64;
                    case 5:
                        return TYPE_INT32;
                    case 6:
                        return TYPE_FIXED64;
                    case 7:
                        return TYPE_FIXED32;
                    case 8:
                        return TYPE_BOOL;
                    case 9:
                        return TYPE_STRING;
                    case 10:
                        return TYPE_GROUP;
                    case 11:
                        return TYPE_MESSAGE;
                    case 12:
                        return TYPE_BYTES;
                    case 13:
                        return TYPE_UINT32;
                    case 14:
                        return TYPE_ENUM;
                    case 15:
                        return TYPE_SFIXED32;
                    case 16:
                        return TYPE_SFIXED64;
                    case 17:
                        return TYPE_SINT32;
                    case 18:
                        return TYPE_SINT64;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TypeVerifier.INSTANCE;
            }

            private static final class TypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TypeVerifier();

                private TypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return Type.forNumber(number) != null;
                }
            }

            private Type(int value2) {
                this.value = value2;
            }
        }

        public enum Label implements Internal.EnumLite {
            LABEL_OPTIONAL(1),
            LABEL_REQUIRED(2),
            LABEL_REPEATED(3);
            
            public static final int LABEL_OPTIONAL_VALUE = 1;
            public static final int LABEL_REPEATED_VALUE = 3;
            public static final int LABEL_REQUIRED_VALUE = 2;
            private static final Internal.EnumLiteMap<Label> internalValueMap = new Internal.EnumLiteMap<Label>() {
                public Label findValueByNumber(int number) {
                    return Label.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Label forNumber(int value2) {
                if (value2 == 1) {
                    return LABEL_OPTIONAL;
                }
                if (value2 == 2) {
                    return LABEL_REQUIRED;
                }
                if (value2 != 3) {
                    return null;
                }
                return LABEL_REPEATED;
            }

            public static Internal.EnumLiteMap<Label> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return LabelVerifier.INSTANCE;
            }

            private static final class LabelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new LabelVerifier();

                private LabelVerifier() {
                }

                public boolean isInRange(int number) {
                    return Label.forNumber(number) != null;
                }
            }

            private Label(int value2) {
                this.value = value2;
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumber() {
            return this.number_;
        }

        /* access modifiers changed from: private */
        public void setNumber(int value) {
            this.bitField0_ |= 2;
            this.number_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumber() {
            this.bitField0_ &= -3;
            this.number_ = 0;
        }

        public boolean hasLabel() {
            return (this.bitField0_ & 4) != 0;
        }

        public Label getLabel() {
            Label result = Label.forNumber(this.label_);
            return result == null ? Label.LABEL_OPTIONAL : result;
        }

        /* access modifiers changed from: private */
        public void setLabel(Label value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.label_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLabel() {
            this.bitField0_ &= -5;
            this.label_ = 1;
        }

        public boolean hasType() {
            return (this.bitField0_ & 8) != 0;
        }

        public Type getType() {
            Type result = Type.forNumber(this.type_);
            return result == null ? Type.TYPE_DOUBLE : result;
        }

        /* access modifiers changed from: private */
        public void setType(Type value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.type_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -9;
            this.type_ = 1;
        }

        public boolean hasTypeName() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getTypeName() {
            return this.typeName_;
        }

        public ByteString getTypeNameBytes() {
            return ByteString.copyFromUtf8(this.typeName_);
        }

        /* access modifiers changed from: private */
        public void setTypeName(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.typeName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTypeName() {
            this.bitField0_ &= -17;
            this.typeName_ = getDefaultInstance().getTypeName();
        }

        /* access modifiers changed from: private */
        public void setTypeNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.typeName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasExtendee() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getExtendee() {
            return this.extendee_;
        }

        public ByteString getExtendeeBytes() {
            return ByteString.copyFromUtf8(this.extendee_);
        }

        /* access modifiers changed from: private */
        public void setExtendee(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.extendee_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearExtendee() {
            this.bitField0_ &= -33;
            this.extendee_ = getDefaultInstance().getExtendee();
        }

        /* access modifiers changed from: private */
        public void setExtendeeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.extendee_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasDefaultValue() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getDefaultValue() {
            return this.defaultValue_;
        }

        public ByteString getDefaultValueBytes() {
            return ByteString.copyFromUtf8(this.defaultValue_);
        }

        /* access modifiers changed from: private */
        public void setDefaultValue(String value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.defaultValue_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDefaultValue() {
            this.bitField0_ &= -65;
            this.defaultValue_ = getDefaultInstance().getDefaultValue();
        }

        /* access modifiers changed from: private */
        public void setDefaultValueBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.defaultValue_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOneofIndex() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getOneofIndex() {
            return this.oneofIndex_;
        }

        /* access modifiers changed from: private */
        public void setOneofIndex(int value) {
            this.bitField0_ |= 128;
            this.oneofIndex_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOneofIndex() {
            this.bitField0_ &= -129;
            this.oneofIndex_ = 0;
        }

        public boolean hasJsonName() {
            return (this.bitField0_ & 256) != 0;
        }

        public String getJsonName() {
            return this.jsonName_;
        }

        public ByteString getJsonNameBytes() {
            return ByteString.copyFromUtf8(this.jsonName_);
        }

        /* access modifiers changed from: private */
        public void setJsonName(String value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.jsonName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJsonName() {
            this.bitField0_ &= -257;
            this.jsonName_ = getDefaultInstance().getJsonName();
        }

        /* access modifiers changed from: private */
        public void setJsonNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.jsonName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 512) != 0;
        }

        public FieldOptions getOptions() {
            FieldOptions fieldOptions = this.options_;
            return fieldOptions == null ? FieldOptions.getDefaultInstance() : fieldOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(FieldOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(FieldOptions.Builder builderForValue) {
            this.options_ = (FieldOptions) builderForValue.build();
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(FieldOptions value) {
            if (value != null) {
                FieldOptions fieldOptions = this.options_;
                if (fieldOptions == null || fieldOptions == FieldOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (FieldOptions) ((FieldOptions.Builder) FieldOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -513;
        }

        public static FieldDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FieldDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FieldDescriptorProto, Builder> implements FieldDescriptorProtoOrBuilder {
            private Builder() {
                super(FieldDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((FieldDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((FieldDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((FieldDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasNumber() {
                return ((FieldDescriptorProto) this.instance).hasNumber();
            }

            public int getNumber() {
                return ((FieldDescriptorProto) this.instance).getNumber();
            }

            public Builder setNumber(int value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setNumber(value);
                return this;
            }

            public Builder clearNumber() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearNumber();
                return this;
            }

            public boolean hasLabel() {
                return ((FieldDescriptorProto) this.instance).hasLabel();
            }

            public Label getLabel() {
                return ((FieldDescriptorProto) this.instance).getLabel();
            }

            public Builder setLabel(Label value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setLabel(value);
                return this;
            }

            public Builder clearLabel() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearLabel();
                return this;
            }

            public boolean hasType() {
                return ((FieldDescriptorProto) this.instance).hasType();
            }

            public Type getType() {
                return ((FieldDescriptorProto) this.instance).getType();
            }

            public Builder setType(Type value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearType();
                return this;
            }

            public boolean hasTypeName() {
                return ((FieldDescriptorProto) this.instance).hasTypeName();
            }

            public String getTypeName() {
                return ((FieldDescriptorProto) this.instance).getTypeName();
            }

            public ByteString getTypeNameBytes() {
                return ((FieldDescriptorProto) this.instance).getTypeNameBytes();
            }

            public Builder setTypeName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setTypeName(value);
                return this;
            }

            public Builder clearTypeName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearTypeName();
                return this;
            }

            public Builder setTypeNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setTypeNameBytes(value);
                return this;
            }

            public boolean hasExtendee() {
                return ((FieldDescriptorProto) this.instance).hasExtendee();
            }

            public String getExtendee() {
                return ((FieldDescriptorProto) this.instance).getExtendee();
            }

            public ByteString getExtendeeBytes() {
                return ((FieldDescriptorProto) this.instance).getExtendeeBytes();
            }

            public Builder setExtendee(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setExtendee(value);
                return this;
            }

            public Builder clearExtendee() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearExtendee();
                return this;
            }

            public Builder setExtendeeBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setExtendeeBytes(value);
                return this;
            }

            public boolean hasDefaultValue() {
                return ((FieldDescriptorProto) this.instance).hasDefaultValue();
            }

            public String getDefaultValue() {
                return ((FieldDescriptorProto) this.instance).getDefaultValue();
            }

            public ByteString getDefaultValueBytes() {
                return ((FieldDescriptorProto) this.instance).getDefaultValueBytes();
            }

            public Builder setDefaultValue(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setDefaultValue(value);
                return this;
            }

            public Builder clearDefaultValue() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearDefaultValue();
                return this;
            }

            public Builder setDefaultValueBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setDefaultValueBytes(value);
                return this;
            }

            public boolean hasOneofIndex() {
                return ((FieldDescriptorProto) this.instance).hasOneofIndex();
            }

            public int getOneofIndex() {
                return ((FieldDescriptorProto) this.instance).getOneofIndex();
            }

            public Builder setOneofIndex(int value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOneofIndex(value);
                return this;
            }

            public Builder clearOneofIndex() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearOneofIndex();
                return this;
            }

            public boolean hasJsonName() {
                return ((FieldDescriptorProto) this.instance).hasJsonName();
            }

            public String getJsonName() {
                return ((FieldDescriptorProto) this.instance).getJsonName();
            }

            public ByteString getJsonNameBytes() {
                return ((FieldDescriptorProto) this.instance).getJsonNameBytes();
            }

            public Builder setJsonName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setJsonName(value);
                return this;
            }

            public Builder clearJsonName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearJsonName();
                return this;
            }

            public Builder setJsonNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setJsonNameBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((FieldDescriptorProto) this.instance).hasOptions();
            }

            public FieldOptions getOptions() {
                return ((FieldDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(FieldOptions value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(FieldOptions.Builder builderForValue) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(FieldOptions value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0001\u0001\b\u0000\u0002\b\u0005\u0003\u0004\u0001\u0004\f\u0002\u0005\f\u0003\u0006\b\u0004\u0007\b\u0006\bЉ\t\t\u0004\u0007\n\b\b", new Object[]{"bitField0_", "name_", "extendee_", "number_", "label_", Label.internalGetVerifier(), "type_", Type.internalGetVerifier(), "typeName_", "defaultValue_", "options_", "oneofIndex_", "jsonName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FieldDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (FieldDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FieldDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static FieldDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {2}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class OneofDescriptorProto extends GeneratedMessageLite<OneofDescriptorProto, Builder> implements OneofDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final OneofDescriptorProto DEFAULT_INSTANCE = new OneofDescriptorProto();
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 2;
        private static volatile Parser<OneofDescriptorProto> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private OneofOptions options_;

        private OneofDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public OneofOptions getOptions() {
            OneofOptions oneofOptions = this.options_;
            return oneofOptions == null ? OneofOptions.getDefaultInstance() : oneofOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(OneofOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(OneofOptions.Builder builderForValue) {
            this.options_ = (OneofOptions) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(OneofOptions value) {
            if (value != null) {
                OneofOptions oneofOptions = this.options_;
                if (oneofOptions == null || oneofOptions == OneofOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (OneofOptions) ((OneofOptions.Builder) OneofOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public static OneofDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(OneofDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<OneofDescriptorProto, Builder> implements OneofDescriptorProtoOrBuilder {
            private Builder() {
                super(OneofDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((OneofDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((OneofDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((OneofDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((OneofDescriptorProto) this.instance).hasOptions();
            }

            public OneofOptions getOptions() {
                return ((OneofDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(OneofOptions value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(OneofOptions.Builder builderForValue) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(OneofOptions value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new OneofDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0001\u0001\b\u0000\u0002Љ\u0001", new Object[]{"bitField0_", "name_", "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<OneofDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (OneofDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(OneofDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static OneofDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OneofDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {2, 3}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class EnumDescriptorProto extends GeneratedMessageLite<EnumDescriptorProto, Builder> implements EnumDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final EnumDescriptorProto DEFAULT_INSTANCE = new EnumDescriptorProto();
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<EnumDescriptorProto> PARSER = null;
        public static final int RESERVED_NAME_FIELD_NUMBER = 5;
        public static final int RESERVED_RANGE_FIELD_NUMBER = 4;
        public static final int VALUE_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private EnumOptions options_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> reservedName_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<EnumReservedRange> reservedRange_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<EnumValueDescriptorProto> value_ = emptyProtobufList();

        public interface EnumReservedRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        private EnumDescriptorProto() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class EnumReservedRange extends GeneratedMessageLite<EnumReservedRange, Builder> implements EnumReservedRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final EnumReservedRange DEFAULT_INSTANCE = new EnumReservedRange();
            public static final int END_FIELD_NUMBER = 2;
            private static volatile Parser<EnumReservedRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int end_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int start_;

            private EnumReservedRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public static EnumReservedRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(InputStream input) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EnumReservedRange parseDelimitedFrom(InputStream input) throws IOException {
                return (EnumReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(CodedInputStream input) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(EnumReservedRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<EnumReservedRange, Builder> implements EnumReservedRangeOrBuilder {
                private Builder() {
                    super(EnumReservedRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((EnumReservedRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((EnumReservedRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((EnumReservedRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((EnumReservedRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).clearEnd();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new EnumReservedRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "start_", "end_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<EnumReservedRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (EnumReservedRange.class) {
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
                GeneratedMessageLite.registerDefaultInstance(EnumReservedRange.class, DEFAULT_INSTANCE);
            }

            public static EnumReservedRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<EnumReservedRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<EnumValueDescriptorProto> getValueList() {
            return this.value_;
        }

        public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        public int getValueCount() {
            return this.value_.size();
        }

        public EnumValueDescriptorProto getValue(int index) {
            return this.value_.get(index);
        }

        public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        private void ensureValueIsMutable() {
            if (!this.value_.isModifiable()) {
                this.value_ = GeneratedMessageLite.mutableCopy(this.value_);
            }
        }

        /* access modifiers changed from: private */
        public void setValue(int index, EnumValueDescriptorProto value) {
            if (value != null) {
                ensureValueIsMutable();
                this.value_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
            ensureValueIsMutable();
            this.value_.set(index, (EnumValueDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addValue(EnumValueDescriptorProto value) {
            if (value != null) {
                ensureValueIsMutable();
                this.value_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addValue(int index, EnumValueDescriptorProto value) {
            if (value != null) {
                ensureValueIsMutable();
                this.value_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addValue(EnumValueDescriptorProto.Builder builderForValue) {
            ensureValueIsMutable();
            this.value_.add((EnumValueDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
            ensureValueIsMutable();
            this.value_.add(index, (EnumValueDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$EnumValueDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$EnumValueDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllValue(Iterable<? extends EnumValueDescriptorProto> values) {
            ensureValueIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.value_);
        }

        /* access modifiers changed from: private */
        public void clearValue() {
            this.value_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeValue(int index) {
            ensureValueIsMutable();
            this.value_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public EnumOptions getOptions() {
            EnumOptions enumOptions = this.options_;
            return enumOptions == null ? EnumOptions.getDefaultInstance() : enumOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumOptions.Builder builderForValue) {
            this.options_ = (EnumOptions) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(EnumOptions value) {
            if (value != null) {
                EnumOptions enumOptions = this.options_;
                if (enumOptions == null || enumOptions == EnumOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (EnumOptions) ((EnumOptions.Builder) EnumOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public List<EnumReservedRange> getReservedRangeList() {
            return this.reservedRange_;
        }

        public List<? extends EnumReservedRangeOrBuilder> getReservedRangeOrBuilderList() {
            return this.reservedRange_;
        }

        public int getReservedRangeCount() {
            return this.reservedRange_.size();
        }

        public EnumReservedRange getReservedRange(int index) {
            return this.reservedRange_.get(index);
        }

        public EnumReservedRangeOrBuilder getReservedRangeOrBuilder(int index) {
            return this.reservedRange_.get(index);
        }

        private void ensureReservedRangeIsMutable() {
            if (!this.reservedRange_.isModifiable()) {
                this.reservedRange_ = GeneratedMessageLite.mutableCopy(this.reservedRange_);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, EnumReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, EnumReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.set(index, (EnumReservedRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addReservedRange(EnumReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, EnumReservedRange value) {
            if (value != null) {
                ensureReservedRangeIsMutable();
                this.reservedRange_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedRange(EnumReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.add((EnumReservedRange) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, EnumReservedRange.Builder builderForValue) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(index, (EnumReservedRange) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$EnumDescriptorProto$EnumReservedRange>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$EnumDescriptorProto$EnumReservedRange>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllReservedRange(Iterable<? extends EnumReservedRange> values) {
            ensureReservedRangeIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.reservedRange_);
        }

        /* access modifiers changed from: private */
        public void clearReservedRange() {
            this.reservedRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeReservedRange(int index) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.remove(index);
        }

        public List<String> getReservedNameList() {
            return this.reservedName_;
        }

        public int getReservedNameCount() {
            return this.reservedName_.size();
        }

        public String getReservedName(int index) {
            return this.reservedName_.get(index);
        }

        public ByteString getReservedNameBytes(int index) {
            return ByteString.copyFromUtf8(this.reservedName_.get(index));
        }

        private void ensureReservedNameIsMutable() {
            if (!this.reservedName_.isModifiable()) {
                this.reservedName_ = GeneratedMessageLite.mutableCopy(this.reservedName_);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedName(int index, String value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addReservedName(String value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.add(value);
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
        public void addAllReservedName(Iterable<String> values) {
            ensureReservedNameIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.reservedName_);
        }

        /* access modifiers changed from: private */
        public void clearReservedName() {
            this.reservedName_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addReservedNameBytes(ByteString value) {
            if (value != null) {
                ensureReservedNameIsMutable();
                this.reservedName_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public static EnumDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<EnumDescriptorProto, Builder> implements EnumDescriptorProtoOrBuilder {
            private Builder() {
                super(EnumDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((EnumDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((EnumDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((EnumDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<EnumValueDescriptorProto> getValueList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getValueList());
            }

            public int getValueCount() {
                return ((EnumDescriptorProto) this.instance).getValueCount();
            }

            public EnumValueDescriptorProto getValue(int index) {
                return ((EnumDescriptorProto) this.instance).getValue(index);
            }

            public Builder setValue(int index, EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setValue(index, value);
                return this;
            }

            public Builder setValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setValue(index, builderForValue);
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(value);
                return this;
            }

            public Builder addValue(int index, EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(index, value);
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(builderForValue);
                return this;
            }

            public Builder addValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(index, builderForValue);
                return this;
            }

            public Builder addAllValue(Iterable<? extends EnumValueDescriptorProto> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllValue(values);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearValue();
                return this;
            }

            public Builder removeValue(int index) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).removeValue(index);
                return this;
            }

            public boolean hasOptions() {
                return ((EnumDescriptorProto) this.instance).hasOptions();
            }

            public EnumOptions getOptions() {
                return ((EnumDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(EnumOptions value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(EnumOptions.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(EnumOptions value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public List<EnumReservedRange> getReservedRangeList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getReservedRangeList());
            }

            public int getReservedRangeCount() {
                return ((EnumDescriptorProto) this.instance).getReservedRangeCount();
            }

            public EnumReservedRange getReservedRange(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedRange(index);
            }

            public Builder setReservedRange(int index, EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedRange(index, value);
                return this;
            }

            public Builder setReservedRange(int index, EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedRange(index, builderForValue);
                return this;
            }

            public Builder addReservedRange(EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(value);
                return this;
            }

            public Builder addReservedRange(int index, EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(index, value);
                return this;
            }

            public Builder addReservedRange(EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(builderForValue);
                return this;
            }

            public Builder addReservedRange(int index, EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(index, builderForValue);
                return this;
            }

            public Builder addAllReservedRange(Iterable<? extends EnumReservedRange> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllReservedRange(values);
                return this;
            }

            public Builder clearReservedRange() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearReservedRange();
                return this;
            }

            public Builder removeReservedRange(int index) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).removeReservedRange(index);
                return this;
            }

            public List<String> getReservedNameList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getReservedNameList());
            }

            public int getReservedNameCount() {
                return ((EnumDescriptorProto) this.instance).getReservedNameCount();
            }

            public String getReservedName(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedName(index);
            }

            public ByteString getReservedNameBytes(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedNameBytes(index);
            }

            public Builder setReservedName(int index, String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedName(index, value);
                return this;
            }

            public Builder addReservedName(String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedName(value);
                return this;
            }

            public Builder addAllReservedName(Iterable<String> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllReservedName(values);
                return this;
            }

            public Builder clearReservedName() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearReservedName();
                return this;
            }

            public Builder addReservedNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0002\u0001\b\u0000\u0002Л\u0003Љ\u0001\u0004\u001b\u0005\u001a", new Object[]{"bitField0_", "name_", "value_", EnumValueDescriptorProto.class, "options_", "reservedRange_", EnumReservedRange.class, "reservedName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(EnumDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static EnumDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {3}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class EnumValueDescriptorProto extends GeneratedMessageLite<EnumValueDescriptorProto, Builder> implements EnumValueDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final EnumValueDescriptorProto DEFAULT_INSTANCE = new EnumValueDescriptorProto();
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 2;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<EnumValueDescriptorProto> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int number_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private EnumValueOptions options_;

        private EnumValueDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumber() {
            return this.number_;
        }

        /* access modifiers changed from: private */
        public void setNumber(int value) {
            this.bitField0_ |= 2;
            this.number_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumber() {
            this.bitField0_ &= -3;
            this.number_ = 0;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) != 0;
        }

        public EnumValueOptions getOptions() {
            EnumValueOptions enumValueOptions = this.options_;
            return enumValueOptions == null ? EnumValueOptions.getDefaultInstance() : enumValueOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumValueOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumValueOptions.Builder builderForValue) {
            this.options_ = (EnumValueOptions) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(EnumValueOptions value) {
            if (value != null) {
                EnumValueOptions enumValueOptions = this.options_;
                if (enumValueOptions == null || enumValueOptions == EnumValueOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (EnumValueOptions) ((EnumValueOptions.Builder) EnumValueOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -5;
        }

        public static EnumValueDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumValueDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<EnumValueDescriptorProto, Builder> implements EnumValueDescriptorProtoOrBuilder {
            private Builder() {
                super(EnumValueDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((EnumValueDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((EnumValueDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((EnumValueDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasNumber() {
                return ((EnumValueDescriptorProto) this.instance).hasNumber();
            }

            public int getNumber() {
                return ((EnumValueDescriptorProto) this.instance).getNumber();
            }

            public Builder setNumber(int value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setNumber(value);
                return this;
            }

            public Builder clearNumber() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearNumber();
                return this;
            }

            public boolean hasOptions() {
                return ((EnumValueDescriptorProto) this.instance).hasOptions();
            }

            public EnumValueOptions getOptions() {
                return ((EnumValueDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(EnumValueOptions value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(EnumValueOptions.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(EnumValueOptions value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumValueDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001\b\u0000\u0002\u0004\u0001\u0003Љ\u0002", new Object[]{"bitField0_", "name_", "number_", "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumValueDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumValueDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(EnumValueDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static EnumValueDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumValueDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {2, 4, 3}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ServiceDescriptorProto extends GeneratedMessageLite<ServiceDescriptorProto, Builder> implements ServiceDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final ServiceDescriptorProto DEFAULT_INSTANCE = new ServiceDescriptorProto();
        public static final int METHOD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<ServiceDescriptorProto> PARSER = null;
        public static final int STREAM_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<MethodDescriptorProto> method_ = emptyProtobufList();
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private ServiceOptions options_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<StreamDescriptorProto> stream_ = emptyProtobufList();

        private ServiceDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<MethodDescriptorProto> getMethodList() {
            return this.method_;
        }

        public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
            return this.method_;
        }

        public int getMethodCount() {
            return this.method_.size();
        }

        public MethodDescriptorProto getMethod(int index) {
            return this.method_.get(index);
        }

        public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int index) {
            return this.method_.get(index);
        }

        private void ensureMethodIsMutable() {
            if (!this.method_.isModifiable()) {
                this.method_ = GeneratedMessageLite.mutableCopy(this.method_);
            }
        }

        /* access modifiers changed from: private */
        public void setMethod(int index, MethodDescriptorProto value) {
            if (value != null) {
                ensureMethodIsMutable();
                this.method_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMethod(int index, MethodDescriptorProto.Builder builderForValue) {
            ensureMethodIsMutable();
            this.method_.set(index, (MethodDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMethod(MethodDescriptorProto value) {
            if (value != null) {
                ensureMethodIsMutable();
                this.method_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMethod(int index, MethodDescriptorProto value) {
            if (value != null) {
                ensureMethodIsMutable();
                this.method_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMethod(MethodDescriptorProto.Builder builderForValue) {
            ensureMethodIsMutable();
            this.method_.add((MethodDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMethod(int index, MethodDescriptorProto.Builder builderForValue) {
            ensureMethodIsMutable();
            this.method_.add(index, (MethodDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$MethodDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$MethodDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllMethod(Iterable<? extends MethodDescriptorProto> values) {
            ensureMethodIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.method_);
        }

        /* access modifiers changed from: private */
        public void clearMethod() {
            this.method_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeMethod(int index) {
            ensureMethodIsMutable();
            this.method_.remove(index);
        }

        public List<StreamDescriptorProto> getStreamList() {
            return this.stream_;
        }

        public List<? extends StreamDescriptorProtoOrBuilder> getStreamOrBuilderList() {
            return this.stream_;
        }

        public int getStreamCount() {
            return this.stream_.size();
        }

        public StreamDescriptorProto getStream(int index) {
            return this.stream_.get(index);
        }

        public StreamDescriptorProtoOrBuilder getStreamOrBuilder(int index) {
            return this.stream_.get(index);
        }

        private void ensureStreamIsMutable() {
            if (!this.stream_.isModifiable()) {
                this.stream_ = GeneratedMessageLite.mutableCopy(this.stream_);
            }
        }

        /* access modifiers changed from: private */
        public void setStream(int index, StreamDescriptorProto value) {
            if (value != null) {
                ensureStreamIsMutable();
                this.stream_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStream(int index, StreamDescriptorProto.Builder builderForValue) {
            ensureStreamIsMutable();
            this.stream_.set(index, (StreamDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStream(StreamDescriptorProto value) {
            if (value != null) {
                ensureStreamIsMutable();
                this.stream_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStream(int index, StreamDescriptorProto value) {
            if (value != null) {
                ensureStreamIsMutable();
                this.stream_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStream(StreamDescriptorProto.Builder builderForValue) {
            ensureStreamIsMutable();
            this.stream_.add((StreamDescriptorProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStream(int index, StreamDescriptorProto.Builder builderForValue) {
            ensureStreamIsMutable();
            this.stream_.add(index, (StreamDescriptorProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$StreamDescriptorProto>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$StreamDescriptorProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStream(Iterable<? extends StreamDescriptorProto> values) {
            ensureStreamIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.stream_);
        }

        /* access modifiers changed from: private */
        public void clearStream() {
            this.stream_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStream(int index) {
            ensureStreamIsMutable();
            this.stream_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public ServiceOptions getOptions() {
            ServiceOptions serviceOptions = this.options_;
            return serviceOptions == null ? ServiceOptions.getDefaultInstance() : serviceOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(ServiceOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(ServiceOptions.Builder builderForValue) {
            this.options_ = (ServiceOptions) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(ServiceOptions value) {
            if (value != null) {
                ServiceOptions serviceOptions = this.options_;
                if (serviceOptions == null || serviceOptions == ServiceOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (ServiceOptions) ((ServiceOptions.Builder) ServiceOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public static ServiceDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServiceDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ServiceDescriptorProto, Builder> implements ServiceDescriptorProtoOrBuilder {
            private Builder() {
                super(ServiceDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((ServiceDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((ServiceDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((ServiceDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<MethodDescriptorProto> getMethodList() {
                return Collections.unmodifiableList(((ServiceDescriptorProto) this.instance).getMethodList());
            }

            public int getMethodCount() {
                return ((ServiceDescriptorProto) this.instance).getMethodCount();
            }

            public MethodDescriptorProto getMethod(int index) {
                return ((ServiceDescriptorProto) this.instance).getMethod(index);
            }

            public Builder setMethod(int index, MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setMethod(index, value);
                return this;
            }

            public Builder setMethod(int index, MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setMethod(index, builderForValue);
                return this;
            }

            public Builder addMethod(MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(value);
                return this;
            }

            public Builder addMethod(int index, MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(index, value);
                return this;
            }

            public Builder addMethod(MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(builderForValue);
                return this;
            }

            public Builder addMethod(int index, MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(index, builderForValue);
                return this;
            }

            public Builder addAllMethod(Iterable<? extends MethodDescriptorProto> values) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addAllMethod(values);
                return this;
            }

            public Builder clearMethod() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearMethod();
                return this;
            }

            public Builder removeMethod(int index) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).removeMethod(index);
                return this;
            }

            public List<StreamDescriptorProto> getStreamList() {
                return Collections.unmodifiableList(((ServiceDescriptorProto) this.instance).getStreamList());
            }

            public int getStreamCount() {
                return ((ServiceDescriptorProto) this.instance).getStreamCount();
            }

            public StreamDescriptorProto getStream(int index) {
                return ((ServiceDescriptorProto) this.instance).getStream(index);
            }

            public Builder setStream(int index, StreamDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setStream(index, value);
                return this;
            }

            public Builder setStream(int index, StreamDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setStream(index, builderForValue);
                return this;
            }

            public Builder addStream(StreamDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addStream(value);
                return this;
            }

            public Builder addStream(int index, StreamDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addStream(index, value);
                return this;
            }

            public Builder addStream(StreamDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addStream(builderForValue);
                return this;
            }

            public Builder addStream(int index, StreamDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addStream(index, builderForValue);
                return this;
            }

            public Builder addAllStream(Iterable<? extends StreamDescriptorProto> values) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addAllStream(values);
                return this;
            }

            public Builder clearStream() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearStream();
                return this;
            }

            public Builder removeStream(int index) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).removeStream(index);
                return this;
            }

            public boolean hasOptions() {
                return ((ServiceDescriptorProto) this.instance).hasOptions();
            }

            public ServiceOptions getOptions() {
                return ((ServiceDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(ServiceOptions value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(ServiceOptions.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(ServiceOptions value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0002\u0003\u0001\b\u0000\u0002Л\u0003Љ\u0001\u0004Л", new Object[]{"bitField0_", "name_", "method_", MethodDescriptorProto.class, "options_", "stream_", StreamDescriptorProto.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ServiceDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServiceDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ServiceDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static ServiceDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServiceDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {4}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MethodDescriptorProto extends GeneratedMessageLite<MethodDescriptorProto, Builder> implements MethodDescriptorProtoOrBuilder {
        public static final int CLIENT_STREAMING_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final MethodDescriptorProto DEFAULT_INSTANCE = new MethodDescriptorProto();
        public static final int INPUT_TYPE_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 4;
        public static final int OUTPUT_TYPE_FIELD_NUMBER = 3;
        private static volatile Parser<MethodDescriptorProto> PARSER = null;
        public static final int SERVER_STREAMING_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean clientStreaming_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String inputType_ = "";
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private MethodOptions options_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String outputType_ = "";
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean serverStreaming_;

        private MethodDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasInputType() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getInputType() {
            return this.inputType_;
        }

        public ByteString getInputTypeBytes() {
            return ByteString.copyFromUtf8(this.inputType_);
        }

        /* access modifiers changed from: private */
        public void setInputType(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.inputType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInputType() {
            this.bitField0_ &= -3;
            this.inputType_ = getDefaultInstance().getInputType();
        }

        /* access modifiers changed from: private */
        public void setInputTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.inputType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOutputType() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getOutputType() {
            return this.outputType_;
        }

        public ByteString getOutputTypeBytes() {
            return ByteString.copyFromUtf8(this.outputType_);
        }

        /* access modifiers changed from: private */
        public void setOutputType(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.outputType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOutputType() {
            this.bitField0_ &= -5;
            this.outputType_ = getDefaultInstance().getOutputType();
        }

        /* access modifiers changed from: private */
        public void setOutputTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.outputType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 8) != 0;
        }

        public MethodOptions getOptions() {
            MethodOptions methodOptions = this.options_;
            return methodOptions == null ? MethodOptions.getDefaultInstance() : methodOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(MethodOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(MethodOptions.Builder builderForValue) {
            this.options_ = (MethodOptions) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(MethodOptions value) {
            if (value != null) {
                MethodOptions methodOptions = this.options_;
                if (methodOptions == null || methodOptions == MethodOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (MethodOptions) ((MethodOptions.Builder) MethodOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasClientStreaming() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getClientStreaming() {
            return this.clientStreaming_;
        }

        /* access modifiers changed from: private */
        public void setClientStreaming(boolean value) {
            this.bitField0_ |= 16;
            this.clientStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientStreaming() {
            this.bitField0_ &= -17;
            this.clientStreaming_ = false;
        }

        public boolean hasServerStreaming() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getServerStreaming() {
            return this.serverStreaming_;
        }

        /* access modifiers changed from: private */
        public void setServerStreaming(boolean value) {
            this.bitField0_ |= 32;
            this.serverStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerStreaming() {
            this.bitField0_ &= -33;
            this.serverStreaming_ = false;
        }

        public static MethodDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MethodDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MethodDescriptorProto, Builder> implements MethodDescriptorProtoOrBuilder {
            private Builder() {
                super(MethodDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((MethodDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((MethodDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((MethodDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasInputType() {
                return ((MethodDescriptorProto) this.instance).hasInputType();
            }

            public String getInputType() {
                return ((MethodDescriptorProto) this.instance).getInputType();
            }

            public ByteString getInputTypeBytes() {
                return ((MethodDescriptorProto) this.instance).getInputTypeBytes();
            }

            public Builder setInputType(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setInputType(value);
                return this;
            }

            public Builder clearInputType() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearInputType();
                return this;
            }

            public Builder setInputTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setInputTypeBytes(value);
                return this;
            }

            public boolean hasOutputType() {
                return ((MethodDescriptorProto) this.instance).hasOutputType();
            }

            public String getOutputType() {
                return ((MethodDescriptorProto) this.instance).getOutputType();
            }

            public ByteString getOutputTypeBytes() {
                return ((MethodDescriptorProto) this.instance).getOutputTypeBytes();
            }

            public Builder setOutputType(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOutputType(value);
                return this;
            }

            public Builder clearOutputType() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearOutputType();
                return this;
            }

            public Builder setOutputTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOutputTypeBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((MethodDescriptorProto) this.instance).hasOptions();
            }

            public MethodOptions getOptions() {
                return ((MethodDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(MethodOptions value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(MethodOptions.Builder builderForValue) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(MethodOptions value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public boolean hasClientStreaming() {
                return ((MethodDescriptorProto) this.instance).hasClientStreaming();
            }

            public boolean getClientStreaming() {
                return ((MethodDescriptorProto) this.instance).getClientStreaming();
            }

            public Builder setClientStreaming(boolean value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setClientStreaming(value);
                return this;
            }

            public Builder clearClientStreaming() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearClientStreaming();
                return this;
            }

            public boolean hasServerStreaming() {
                return ((MethodDescriptorProto) this.instance).hasServerStreaming();
            }

            public boolean getServerStreaming() {
                return ((MethodDescriptorProto) this.instance).getServerStreaming();
            }

            public Builder setServerStreaming(boolean value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setServerStreaming(value);
                return this;
            }

            public Builder clearServerStreaming() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearServerStreaming();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MethodDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0001\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004Љ\u0003\u0005\u0007\u0004\u0006\u0007\u0005", new Object[]{"bitField0_", "name_", "inputType_", "outputType_", "options_", "clientStreaming_", "serverStreaming_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MethodDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (MethodDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MethodDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static MethodDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MethodDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {4}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class StreamDescriptorProto extends GeneratedMessageLite<StreamDescriptorProto, Builder> implements StreamDescriptorProtoOrBuilder {
        public static final int CLIENT_MESSAGE_TYPE_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final StreamDescriptorProto DEFAULT_INSTANCE = new StreamDescriptorProto();
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 4;
        private static volatile Parser<StreamDescriptorProto> PARSER = null;
        public static final int SERVER_MESSAGE_TYPE_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String clientMessageType_ = "";
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private StreamOptions options_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String serverMessageType_ = "";

        private StreamDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasClientMessageType() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getClientMessageType() {
            return this.clientMessageType_;
        }

        public ByteString getClientMessageTypeBytes() {
            return ByteString.copyFromUtf8(this.clientMessageType_);
        }

        /* access modifiers changed from: private */
        public void setClientMessageType(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.clientMessageType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClientMessageType() {
            this.bitField0_ &= -3;
            this.clientMessageType_ = getDefaultInstance().getClientMessageType();
        }

        /* access modifiers changed from: private */
        public void setClientMessageTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.clientMessageType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasServerMessageType() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getServerMessageType() {
            return this.serverMessageType_;
        }

        public ByteString getServerMessageTypeBytes() {
            return ByteString.copyFromUtf8(this.serverMessageType_);
        }

        /* access modifiers changed from: private */
        public void setServerMessageType(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.serverMessageType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearServerMessageType() {
            this.bitField0_ &= -5;
            this.serverMessageType_ = getDefaultInstance().getServerMessageType();
        }

        /* access modifiers changed from: private */
        public void setServerMessageTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.serverMessageType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 8) != 0;
        }

        public StreamOptions getOptions() {
            StreamOptions streamOptions = this.options_;
            return streamOptions == null ? StreamOptions.getDefaultInstance() : streamOptions;
        }

        /* access modifiers changed from: private */
        public void setOptions(StreamOptions value) {
            if (value != null) {
                this.options_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOptions(StreamOptions.Builder builderForValue) {
            this.options_ = (StreamOptions) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(StreamOptions value) {
            if (value != null) {
                StreamOptions streamOptions = this.options_;
                if (streamOptions == null || streamOptions == StreamOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (StreamOptions) ((StreamOptions.Builder) StreamOptions.newBuilder(this.options_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -9;
        }

        public static StreamDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamDescriptorProto parseFrom(InputStream input) throws IOException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StreamDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (StreamDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StreamDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(StreamDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<StreamDescriptorProto, Builder> implements StreamDescriptorProtoOrBuilder {
            private Builder() {
                super(StreamDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((StreamDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((StreamDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((StreamDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasClientMessageType() {
                return ((StreamDescriptorProto) this.instance).hasClientMessageType();
            }

            public String getClientMessageType() {
                return ((StreamDescriptorProto) this.instance).getClientMessageType();
            }

            public ByteString getClientMessageTypeBytes() {
                return ((StreamDescriptorProto) this.instance).getClientMessageTypeBytes();
            }

            public Builder setClientMessageType(String value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setClientMessageType(value);
                return this;
            }

            public Builder clearClientMessageType() {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).clearClientMessageType();
                return this;
            }

            public Builder setClientMessageTypeBytes(ByteString value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setClientMessageTypeBytes(value);
                return this;
            }

            public boolean hasServerMessageType() {
                return ((StreamDescriptorProto) this.instance).hasServerMessageType();
            }

            public String getServerMessageType() {
                return ((StreamDescriptorProto) this.instance).getServerMessageType();
            }

            public ByteString getServerMessageTypeBytes() {
                return ((StreamDescriptorProto) this.instance).getServerMessageTypeBytes();
            }

            public Builder setServerMessageType(String value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setServerMessageType(value);
                return this;
            }

            public Builder clearServerMessageType() {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).clearServerMessageType();
                return this;
            }

            public Builder setServerMessageTypeBytes(ByteString value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setServerMessageTypeBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((StreamDescriptorProto) this.instance).hasOptions();
            }

            public StreamOptions getOptions() {
                return ((StreamDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(StreamOptions value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(StreamOptions.Builder builderForValue) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).setOptions(builderForValue);
                return this;
            }

            public Builder mergeOptions(StreamOptions value) {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((StreamDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new StreamDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004Љ\u0003", new Object[]{"bitField0_", "name_", "clientMessageType_", "serverMessageType_", "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<StreamDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (StreamDescriptorProto.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(StreamDescriptorProto.class, DEFAULT_INSTANCE);
        }

        public static StreamDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StreamDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FileOptions extends GeneratedMessageLite.ExtendableMessage<FileOptions, Builder> implements FileOptionsOrBuilder {
        public static final int CC_API_VERSION_FIELD_NUMBER = 2;
        public static final int CC_ENABLE_ARENAS_FIELD_NUMBER = 31;
        public static final int CC_GENERIC_SERVICES_FIELD_NUMBER = 16;
        public static final int CC_UTF8_VERIFICATION_FIELD_NUMBER = 24;
        public static final int CSHARP_NAMESPACE_FIELD_NUMBER = 37;
        /* access modifiers changed from: private */
        public static final FileOptions DEFAULT_INSTANCE = new FileOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 23;
        public static final int GO_PACKAGE_FIELD_NUMBER = 11;
        public static final int JAVASCRIPT_PACKAGE_FIELD_NUMBER = 12;
        public static final int JAVA_ALT_API_PACKAGE_FIELD_NUMBER = 19;
        public static final int JAVA_API_VERSION_FIELD_NUMBER = 5;
        public static final int JAVA_ENABLE_DUAL_GENERATE_MUTABLE_API_FIELD_NUMBER = 26;
        public static final int JAVA_GENERIC_SERVICES_FIELD_NUMBER = 17;
        public static final int JAVA_JAVA5_ENUMS_FIELD_NUMBER = 7;
        public static final int JAVA_MULTIPLE_FILES_FIELD_NUMBER = 10;
        public static final int JAVA_MULTIPLE_FILES_MUTABLE_PACKAGE_FIELD_NUMBER = 29;
        public static final int JAVA_MUTABLE_API_FIELD_NUMBER = 28;
        public static final int JAVA_OUTER_CLASSNAME_FIELD_NUMBER = 8;
        public static final int JAVA_PACKAGE_FIELD_NUMBER = 1;
        public static final int JAVA_STRING_CHECK_UTF8_FIELD_NUMBER = 27;
        public static final int JAVA_USE_JAVAPROTO2_FIELD_NUMBER = 6;
        public static final int OBJC_CLASS_PREFIX_FIELD_NUMBER = 36;
        public static final int OPTIMIZE_FOR_FIELD_NUMBER = 9;
        private static volatile Parser<FileOptions> PARSER = null;
        public static final int PHP_CLASS_PREFIX_FIELD_NUMBER = 40;
        public static final int PHP_GENERIC_SERVICES_FIELD_NUMBER = 42;
        public static final int PHP_METADATA_NAMESPACE_FIELD_NUMBER = 44;
        public static final int PHP_NAMESPACE_FIELD_NUMBER = 41;
        public static final int PY_API_VERSION_FIELD_NUMBER = 4;
        public static final int PY_GENERIC_SERVICES_FIELD_NUMBER = 18;
        public static final int RUBY_PACKAGE_FIELD_NUMBER = 45;
        public static final int SWIFT_PREFIX_FIELD_NUMBER = 39;
        public static final int SZL_API_VERSION_FIELD_NUMBER = 14;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int ccApiVersion_ = 2;
        @ProtoField(fieldNumber = 31, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8388608, presenceBitsId = 0)
        private boolean ccEnableArenas_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private boolean ccGenericServices_;
        @ProtoField(fieldNumber = 24, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean ccUtf8Verification_ = true;
        @ProtoField(fieldNumber = 37, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 33554432, presenceBitsId = 0)
        private String csharpNamespace_ = "";
        @ProtoField(fieldNumber = 23, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4194304, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 11, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private String goPackage_ = "";
        @ProtoField(fieldNumber = 19, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private String javaAltApiPackage_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int javaApiVersion_ = 2;
        @ProtoField(fieldNumber = 26, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean javaEnableDualGenerateMutableApi_;
        @ProtoField(fieldNumber = 17, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 0)
        private boolean javaGenericServices_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private boolean javaJava5Enums_ = true;
        @ProtoField(fieldNumber = 29, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private String javaMultipleFilesMutablePackage_ = "";
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean javaMultipleFiles_;
        @ProtoField(fieldNumber = 28, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private boolean javaMutableApi_;
        @ProtoField(fieldNumber = 8, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private String javaOuterClassname_ = "";
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String javaPackage_ = "";
        @ProtoField(fieldNumber = 27, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private boolean javaStringCheckUtf8_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean javaUseJavaproto2_ = true;
        @ProtoField(fieldNumber = 12, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private String javascriptPackage_ = "";
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 36, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16777216, presenceBitsId = 0)
        private String objcClassPrefix_ = "";
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private int optimizeFor_ = 1;
        @ProtoField(fieldNumber = 40, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 134217728, presenceBitsId = 0)
        private String phpClassPrefix_ = "";
        @ProtoField(fieldNumber = 42, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2097152, presenceBitsId = 0)
        private boolean phpGenericServices_;
        @ProtoField(fieldNumber = 44, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 536870912, presenceBitsId = 0)
        private String phpMetadataNamespace_ = "";
        @ProtoField(fieldNumber = 41, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = C0841C.ENCODING_PCM_MU_LAW, presenceBitsId = 0)
        private String phpNamespace_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int pyApiVersion_ = 2;
        @ProtoField(fieldNumber = 18, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 0)
        private boolean pyGenericServices_;
        @ProtoField(fieldNumber = 45, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1073741824, presenceBitsId = 0)
        private String rubyPackage_ = "";
        @ProtoField(fieldNumber = 39, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 67108864, presenceBitsId = 0)
        private String swiftPrefix_ = "";
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private int szlApiVersion_ = 2;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private FileOptions() {
        }

        public enum OptimizeMode implements Internal.EnumLite {
            SPEED(1),
            CODE_SIZE(2),
            LITE_RUNTIME(3);
            
            public static final int CODE_SIZE_VALUE = 2;
            public static final int LITE_RUNTIME_VALUE = 3;
            public static final int SPEED_VALUE = 1;
            private static final Internal.EnumLiteMap<OptimizeMode> internalValueMap = new Internal.EnumLiteMap<OptimizeMode>() {
                public OptimizeMode findValueByNumber(int number) {
                    return OptimizeMode.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static OptimizeMode forNumber(int value2) {
                if (value2 == 1) {
                    return SPEED;
                }
                if (value2 == 2) {
                    return CODE_SIZE;
                }
                if (value2 != 3) {
                    return null;
                }
                return LITE_RUNTIME;
            }

            public static Internal.EnumLiteMap<OptimizeMode> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OptimizeModeVerifier.INSTANCE;
            }

            private static final class OptimizeModeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new OptimizeModeVerifier();

                private OptimizeModeVerifier() {
                }

                public boolean isInRange(int number) {
                    return OptimizeMode.forNumber(number) != null;
                }
            }

            private OptimizeMode(int value2) {
                this.value = value2;
            }
        }

        public boolean hasCcApiVersion() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCcApiVersion() {
            return this.ccApiVersion_;
        }

        /* access modifiers changed from: private */
        public void setCcApiVersion(int value) {
            this.bitField0_ |= 1;
            this.ccApiVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcApiVersion() {
            this.bitField0_ &= -2;
            this.ccApiVersion_ = 2;
        }

        public boolean hasCcUtf8Verification() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getCcUtf8Verification() {
            return this.ccUtf8Verification_;
        }

        /* access modifiers changed from: private */
        public void setCcUtf8Verification(boolean value) {
            this.bitField0_ |= 2;
            this.ccUtf8Verification_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcUtf8Verification() {
            this.bitField0_ &= -3;
            this.ccUtf8Verification_ = true;
        }

        public boolean hasJavaPackage() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getJavaPackage() {
            return this.javaPackage_;
        }

        public ByteString getJavaPackageBytes() {
            return ByteString.copyFromUtf8(this.javaPackage_);
        }

        /* access modifiers changed from: private */
        public void setJavaPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.javaPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJavaPackage() {
            this.bitField0_ &= -5;
            this.javaPackage_ = getDefaultInstance().getJavaPackage();
        }

        /* access modifiers changed from: private */
        public void setJavaPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.javaPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPyApiVersion() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getPyApiVersion() {
            return this.pyApiVersion_;
        }

        /* access modifiers changed from: private */
        public void setPyApiVersion(int value) {
            this.bitField0_ |= 8;
            this.pyApiVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPyApiVersion() {
            this.bitField0_ &= -9;
            this.pyApiVersion_ = 2;
        }

        public boolean hasJavaApiVersion() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getJavaApiVersion() {
            return this.javaApiVersion_;
        }

        /* access modifiers changed from: private */
        public void setJavaApiVersion(int value) {
            this.bitField0_ |= 16;
            this.javaApiVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaApiVersion() {
            this.bitField0_ &= -17;
            this.javaApiVersion_ = 2;
        }

        public boolean hasJavaUseJavaproto2() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getJavaUseJavaproto2() {
            return this.javaUseJavaproto2_;
        }

        /* access modifiers changed from: private */
        public void setJavaUseJavaproto2(boolean value) {
            this.bitField0_ |= 32;
            this.javaUseJavaproto2_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaUseJavaproto2() {
            this.bitField0_ &= -33;
            this.javaUseJavaproto2_ = true;
        }

        public boolean hasJavaJava5Enums() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getJavaJava5Enums() {
            return this.javaJava5Enums_;
        }

        /* access modifiers changed from: private */
        public void setJavaJava5Enums(boolean value) {
            this.bitField0_ |= 64;
            this.javaJava5Enums_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaJava5Enums() {
            this.bitField0_ &= -65;
            this.javaJava5Enums_ = true;
        }

        public boolean hasJavaAltApiPackage() {
            return (this.bitField0_ & 128) != 0;
        }

        public String getJavaAltApiPackage() {
            return this.javaAltApiPackage_;
        }

        public ByteString getJavaAltApiPackageBytes() {
            return ByteString.copyFromUtf8(this.javaAltApiPackage_);
        }

        /* access modifiers changed from: private */
        public void setJavaAltApiPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.javaAltApiPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJavaAltApiPackage() {
            this.bitField0_ &= -129;
            this.javaAltApiPackage_ = getDefaultInstance().getJavaAltApiPackage();
        }

        /* access modifiers changed from: private */
        public void setJavaAltApiPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.javaAltApiPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasJavaEnableDualGenerateMutableApi() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getJavaEnableDualGenerateMutableApi() {
            return this.javaEnableDualGenerateMutableApi_;
        }

        /* access modifiers changed from: private */
        public void setJavaEnableDualGenerateMutableApi(boolean value) {
            this.bitField0_ |= 256;
            this.javaEnableDualGenerateMutableApi_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaEnableDualGenerateMutableApi() {
            this.bitField0_ &= -257;
            this.javaEnableDualGenerateMutableApi_ = false;
        }

        public boolean hasJavaOuterClassname() {
            return (this.bitField0_ & 512) != 0;
        }

        public String getJavaOuterClassname() {
            return this.javaOuterClassname_;
        }

        public ByteString getJavaOuterClassnameBytes() {
            return ByteString.copyFromUtf8(this.javaOuterClassname_);
        }

        /* access modifiers changed from: private */
        public void setJavaOuterClassname(String value) {
            if (value != null) {
                this.bitField0_ |= 512;
                this.javaOuterClassname_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJavaOuterClassname() {
            this.bitField0_ &= -513;
            this.javaOuterClassname_ = getDefaultInstance().getJavaOuterClassname();
        }

        /* access modifiers changed from: private */
        public void setJavaOuterClassnameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 512;
                this.javaOuterClassname_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasJavaMultipleFiles() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getJavaMultipleFiles() {
            return this.javaMultipleFiles_;
        }

        /* access modifiers changed from: private */
        public void setJavaMultipleFiles(boolean value) {
            this.bitField0_ |= 1024;
            this.javaMultipleFiles_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaMultipleFiles() {
            this.bitField0_ &= -1025;
            this.javaMultipleFiles_ = false;
        }

        public boolean hasJavaStringCheckUtf8() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getJavaStringCheckUtf8() {
            return this.javaStringCheckUtf8_;
        }

        /* access modifiers changed from: private */
        public void setJavaStringCheckUtf8(boolean value) {
            this.bitField0_ |= 2048;
            this.javaStringCheckUtf8_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaStringCheckUtf8() {
            this.bitField0_ &= -2049;
            this.javaStringCheckUtf8_ = false;
        }

        public boolean hasJavaMutableApi() {
            return (this.bitField0_ & 4096) != 0;
        }

        public boolean getJavaMutableApi() {
            return this.javaMutableApi_;
        }

        /* access modifiers changed from: private */
        public void setJavaMutableApi(boolean value) {
            this.bitField0_ |= 4096;
            this.javaMutableApi_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaMutableApi() {
            this.bitField0_ &= -4097;
            this.javaMutableApi_ = false;
        }

        public boolean hasJavaMultipleFilesMutablePackage() {
            return (this.bitField0_ & 8192) != 0;
        }

        public String getJavaMultipleFilesMutablePackage() {
            return this.javaMultipleFilesMutablePackage_;
        }

        public ByteString getJavaMultipleFilesMutablePackageBytes() {
            return ByteString.copyFromUtf8(this.javaMultipleFilesMutablePackage_);
        }

        /* access modifiers changed from: private */
        public void setJavaMultipleFilesMutablePackage(String value) {
            if (value != null) {
                this.bitField0_ |= 8192;
                this.javaMultipleFilesMutablePackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJavaMultipleFilesMutablePackage() {
            this.bitField0_ &= -8193;
            this.javaMultipleFilesMutablePackage_ = getDefaultInstance().getJavaMultipleFilesMutablePackage();
        }

        /* access modifiers changed from: private */
        public void setJavaMultipleFilesMutablePackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8192;
                this.javaMultipleFilesMutablePackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasOptimizeFor() {
            return (this.bitField0_ & 16384) != 0;
        }

        public OptimizeMode getOptimizeFor() {
            OptimizeMode result = OptimizeMode.forNumber(this.optimizeFor_);
            return result == null ? OptimizeMode.SPEED : result;
        }

        /* access modifiers changed from: private */
        public void setOptimizeFor(OptimizeMode value) {
            if (value != null) {
                this.bitField0_ |= 16384;
                this.optimizeFor_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOptimizeFor() {
            this.bitField0_ &= -16385;
            this.optimizeFor_ = 1;
        }

        public boolean hasGoPackage() {
            return (this.bitField0_ & 32768) != 0;
        }

        public String getGoPackage() {
            return this.goPackage_;
        }

        public ByteString getGoPackageBytes() {
            return ByteString.copyFromUtf8(this.goPackage_);
        }

        /* access modifiers changed from: private */
        public void setGoPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 32768;
                this.goPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGoPackage() {
            this.bitField0_ &= -32769;
            this.goPackage_ = getDefaultInstance().getGoPackage();
        }

        /* access modifiers changed from: private */
        public void setGoPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32768;
                this.goPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasJavascriptPackage() {
            return (this.bitField0_ & 65536) != 0;
        }

        public String getJavascriptPackage() {
            return this.javascriptPackage_;
        }

        public ByteString getJavascriptPackageBytes() {
            return ByteString.copyFromUtf8(this.javascriptPackage_);
        }

        /* access modifiers changed from: private */
        public void setJavascriptPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 65536;
                this.javascriptPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJavascriptPackage() {
            this.bitField0_ &= -65537;
            this.javascriptPackage_ = getDefaultInstance().getJavascriptPackage();
        }

        /* access modifiers changed from: private */
        public void setJavascriptPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 65536;
                this.javascriptPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSzlApiVersion() {
            return (this.bitField0_ & 131072) != 0;
        }

        public int getSzlApiVersion() {
            return this.szlApiVersion_;
        }

        /* access modifiers changed from: private */
        public void setSzlApiVersion(int value) {
            this.bitField0_ |= 131072;
            this.szlApiVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSzlApiVersion() {
            this.bitField0_ &= -131073;
            this.szlApiVersion_ = 2;
        }

        public boolean hasCcGenericServices() {
            return (this.bitField0_ & 262144) != 0;
        }

        public boolean getCcGenericServices() {
            return this.ccGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setCcGenericServices(boolean value) {
            this.bitField0_ |= 262144;
            this.ccGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcGenericServices() {
            this.bitField0_ &= -262145;
            this.ccGenericServices_ = false;
        }

        public boolean hasJavaGenericServices() {
            return (this.bitField0_ & 524288) != 0;
        }

        public boolean getJavaGenericServices() {
            return this.javaGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setJavaGenericServices(boolean value) {
            this.bitField0_ |= 524288;
            this.javaGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaGenericServices() {
            this.bitField0_ &= -524289;
            this.javaGenericServices_ = false;
        }

        public boolean hasPyGenericServices() {
            return (this.bitField0_ & 1048576) != 0;
        }

        public boolean getPyGenericServices() {
            return this.pyGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setPyGenericServices(boolean value) {
            this.bitField0_ |= 1048576;
            this.pyGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPyGenericServices() {
            this.bitField0_ &= -1048577;
            this.pyGenericServices_ = false;
        }

        public boolean hasPhpGenericServices() {
            return (this.bitField0_ & 2097152) != 0;
        }

        public boolean getPhpGenericServices() {
            return this.phpGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setPhpGenericServices(boolean value) {
            this.bitField0_ |= 2097152;
            this.phpGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPhpGenericServices() {
            this.bitField0_ &= -2097153;
            this.phpGenericServices_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4194304) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 4194304;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -4194305;
            this.deprecated_ = false;
        }

        public boolean hasCcEnableArenas() {
            return (this.bitField0_ & 8388608) != 0;
        }

        public boolean getCcEnableArenas() {
            return this.ccEnableArenas_;
        }

        /* access modifiers changed from: private */
        public void setCcEnableArenas(boolean value) {
            this.bitField0_ |= 8388608;
            this.ccEnableArenas_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcEnableArenas() {
            this.bitField0_ &= -8388609;
            this.ccEnableArenas_ = false;
        }

        public boolean hasObjcClassPrefix() {
            return (this.bitField0_ & 16777216) != 0;
        }

        public String getObjcClassPrefix() {
            return this.objcClassPrefix_;
        }

        public ByteString getObjcClassPrefixBytes() {
            return ByteString.copyFromUtf8(this.objcClassPrefix_);
        }

        /* access modifiers changed from: private */
        public void setObjcClassPrefix(String value) {
            if (value != null) {
                this.bitField0_ |= 16777216;
                this.objcClassPrefix_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearObjcClassPrefix() {
            this.bitField0_ &= -16777217;
            this.objcClassPrefix_ = getDefaultInstance().getObjcClassPrefix();
        }

        /* access modifiers changed from: private */
        public void setObjcClassPrefixBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16777216;
                this.objcClassPrefix_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasCsharpNamespace() {
            return (this.bitField0_ & 33554432) != 0;
        }

        public String getCsharpNamespace() {
            return this.csharpNamespace_;
        }

        public ByteString getCsharpNamespaceBytes() {
            return ByteString.copyFromUtf8(this.csharpNamespace_);
        }

        /* access modifiers changed from: private */
        public void setCsharpNamespace(String value) {
            if (value != null) {
                this.bitField0_ |= 33554432;
                this.csharpNamespace_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCsharpNamespace() {
            this.bitField0_ &= -33554433;
            this.csharpNamespace_ = getDefaultInstance().getCsharpNamespace();
        }

        /* access modifiers changed from: private */
        public void setCsharpNamespaceBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 33554432;
                this.csharpNamespace_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSwiftPrefix() {
            return (this.bitField0_ & 67108864) != 0;
        }

        public String getSwiftPrefix() {
            return this.swiftPrefix_;
        }

        public ByteString getSwiftPrefixBytes() {
            return ByteString.copyFromUtf8(this.swiftPrefix_);
        }

        /* access modifiers changed from: private */
        public void setSwiftPrefix(String value) {
            if (value != null) {
                this.bitField0_ |= 67108864;
                this.swiftPrefix_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSwiftPrefix() {
            this.bitField0_ &= -67108865;
            this.swiftPrefix_ = getDefaultInstance().getSwiftPrefix();
        }

        /* access modifiers changed from: private */
        public void setSwiftPrefixBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 67108864;
                this.swiftPrefix_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPhpClassPrefix() {
            return (this.bitField0_ & 134217728) != 0;
        }

        public String getPhpClassPrefix() {
            return this.phpClassPrefix_;
        }

        public ByteString getPhpClassPrefixBytes() {
            return ByteString.copyFromUtf8(this.phpClassPrefix_);
        }

        /* access modifiers changed from: private */
        public void setPhpClassPrefix(String value) {
            if (value != null) {
                this.bitField0_ |= 134217728;
                this.phpClassPrefix_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPhpClassPrefix() {
            this.bitField0_ &= -134217729;
            this.phpClassPrefix_ = getDefaultInstance().getPhpClassPrefix();
        }

        /* access modifiers changed from: private */
        public void setPhpClassPrefixBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 134217728;
                this.phpClassPrefix_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPhpNamespace() {
            return (this.bitField0_ & C0841C.ENCODING_PCM_MU_LAW) != 0;
        }

        public String getPhpNamespace() {
            return this.phpNamespace_;
        }

        public ByteString getPhpNamespaceBytes() {
            return ByteString.copyFromUtf8(this.phpNamespace_);
        }

        /* access modifiers changed from: private */
        public void setPhpNamespace(String value) {
            if (value != null) {
                this.bitField0_ |= C0841C.ENCODING_PCM_MU_LAW;
                this.phpNamespace_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPhpNamespace() {
            this.bitField0_ &= -268435457;
            this.phpNamespace_ = getDefaultInstance().getPhpNamespace();
        }

        /* access modifiers changed from: private */
        public void setPhpNamespaceBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= C0841C.ENCODING_PCM_MU_LAW;
                this.phpNamespace_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPhpMetadataNamespace() {
            return (this.bitField0_ & 536870912) != 0;
        }

        public String getPhpMetadataNamespace() {
            return this.phpMetadataNamespace_;
        }

        public ByteString getPhpMetadataNamespaceBytes() {
            return ByteString.copyFromUtf8(this.phpMetadataNamespace_);
        }

        /* access modifiers changed from: private */
        public void setPhpMetadataNamespace(String value) {
            if (value != null) {
                this.bitField0_ |= 536870912;
                this.phpMetadataNamespace_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPhpMetadataNamespace() {
            this.bitField0_ &= -536870913;
            this.phpMetadataNamespace_ = getDefaultInstance().getPhpMetadataNamespace();
        }

        /* access modifiers changed from: private */
        public void setPhpMetadataNamespaceBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 536870912;
                this.phpMetadataNamespace_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasRubyPackage() {
            return (this.bitField0_ & 1073741824) != 0;
        }

        public String getRubyPackage() {
            return this.rubyPackage_;
        }

        public ByteString getRubyPackageBytes() {
            return ByteString.copyFromUtf8(this.rubyPackage_);
        }

        /* access modifiers changed from: private */
        public void setRubyPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 1073741824;
                this.rubyPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRubyPackage() {
            this.bitField0_ &= -1073741825;
            this.rubyPackage_ = getDefaultInstance().getRubyPackage();
        }

        /* access modifiers changed from: private */
        public void setRubyPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1073741824;
                this.rubyPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static FileOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(InputStream input) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FileOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileOptions parseFrom(CodedInputStream input) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<FileOptions, Builder> implements FileOptionsOrBuilder {
            private Builder() {
                super(FileOptions.DEFAULT_INSTANCE);
            }

            public boolean hasCcApiVersion() {
                return ((FileOptions) this.instance).hasCcApiVersion();
            }

            public int getCcApiVersion() {
                return ((FileOptions) this.instance).getCcApiVersion();
            }

            public Builder setCcApiVersion(int value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcApiVersion(value);
                return this;
            }

            public Builder clearCcApiVersion() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcApiVersion();
                return this;
            }

            public boolean hasCcUtf8Verification() {
                return ((FileOptions) this.instance).hasCcUtf8Verification();
            }

            public boolean getCcUtf8Verification() {
                return ((FileOptions) this.instance).getCcUtf8Verification();
            }

            public Builder setCcUtf8Verification(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcUtf8Verification(value);
                return this;
            }

            public Builder clearCcUtf8Verification() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcUtf8Verification();
                return this;
            }

            public boolean hasJavaPackage() {
                return ((FileOptions) this.instance).hasJavaPackage();
            }

            public String getJavaPackage() {
                return ((FileOptions) this.instance).getJavaPackage();
            }

            public ByteString getJavaPackageBytes() {
                return ((FileOptions) this.instance).getJavaPackageBytes();
            }

            public Builder setJavaPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaPackage(value);
                return this;
            }

            public Builder clearJavaPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaPackage();
                return this;
            }

            public Builder setJavaPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaPackageBytes(value);
                return this;
            }

            public boolean hasPyApiVersion() {
                return ((FileOptions) this.instance).hasPyApiVersion();
            }

            public int getPyApiVersion() {
                return ((FileOptions) this.instance).getPyApiVersion();
            }

            public Builder setPyApiVersion(int value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPyApiVersion(value);
                return this;
            }

            public Builder clearPyApiVersion() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPyApiVersion();
                return this;
            }

            public boolean hasJavaApiVersion() {
                return ((FileOptions) this.instance).hasJavaApiVersion();
            }

            public int getJavaApiVersion() {
                return ((FileOptions) this.instance).getJavaApiVersion();
            }

            public Builder setJavaApiVersion(int value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaApiVersion(value);
                return this;
            }

            public Builder clearJavaApiVersion() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaApiVersion();
                return this;
            }

            public boolean hasJavaUseJavaproto2() {
                return ((FileOptions) this.instance).hasJavaUseJavaproto2();
            }

            public boolean getJavaUseJavaproto2() {
                return ((FileOptions) this.instance).getJavaUseJavaproto2();
            }

            public Builder setJavaUseJavaproto2(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaUseJavaproto2(value);
                return this;
            }

            public Builder clearJavaUseJavaproto2() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaUseJavaproto2();
                return this;
            }

            public boolean hasJavaJava5Enums() {
                return ((FileOptions) this.instance).hasJavaJava5Enums();
            }

            public boolean getJavaJava5Enums() {
                return ((FileOptions) this.instance).getJavaJava5Enums();
            }

            public Builder setJavaJava5Enums(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaJava5Enums(value);
                return this;
            }

            public Builder clearJavaJava5Enums() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaJava5Enums();
                return this;
            }

            public boolean hasJavaAltApiPackage() {
                return ((FileOptions) this.instance).hasJavaAltApiPackage();
            }

            public String getJavaAltApiPackage() {
                return ((FileOptions) this.instance).getJavaAltApiPackage();
            }

            public ByteString getJavaAltApiPackageBytes() {
                return ((FileOptions) this.instance).getJavaAltApiPackageBytes();
            }

            public Builder setJavaAltApiPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaAltApiPackage(value);
                return this;
            }

            public Builder clearJavaAltApiPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaAltApiPackage();
                return this;
            }

            public Builder setJavaAltApiPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaAltApiPackageBytes(value);
                return this;
            }

            public boolean hasJavaEnableDualGenerateMutableApi() {
                return ((FileOptions) this.instance).hasJavaEnableDualGenerateMutableApi();
            }

            public boolean getJavaEnableDualGenerateMutableApi() {
                return ((FileOptions) this.instance).getJavaEnableDualGenerateMutableApi();
            }

            public Builder setJavaEnableDualGenerateMutableApi(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaEnableDualGenerateMutableApi(value);
                return this;
            }

            public Builder clearJavaEnableDualGenerateMutableApi() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaEnableDualGenerateMutableApi();
                return this;
            }

            public boolean hasJavaOuterClassname() {
                return ((FileOptions) this.instance).hasJavaOuterClassname();
            }

            public String getJavaOuterClassname() {
                return ((FileOptions) this.instance).getJavaOuterClassname();
            }

            public ByteString getJavaOuterClassnameBytes() {
                return ((FileOptions) this.instance).getJavaOuterClassnameBytes();
            }

            public Builder setJavaOuterClassname(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaOuterClassname(value);
                return this;
            }

            public Builder clearJavaOuterClassname() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaOuterClassname();
                return this;
            }

            public Builder setJavaOuterClassnameBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaOuterClassnameBytes(value);
                return this;
            }

            public boolean hasJavaMultipleFiles() {
                return ((FileOptions) this.instance).hasJavaMultipleFiles();
            }

            public boolean getJavaMultipleFiles() {
                return ((FileOptions) this.instance).getJavaMultipleFiles();
            }

            public Builder setJavaMultipleFiles(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaMultipleFiles(value);
                return this;
            }

            public Builder clearJavaMultipleFiles() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaMultipleFiles();
                return this;
            }

            public boolean hasJavaStringCheckUtf8() {
                return ((FileOptions) this.instance).hasJavaStringCheckUtf8();
            }

            public boolean getJavaStringCheckUtf8() {
                return ((FileOptions) this.instance).getJavaStringCheckUtf8();
            }

            public Builder setJavaStringCheckUtf8(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaStringCheckUtf8(value);
                return this;
            }

            public Builder clearJavaStringCheckUtf8() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaStringCheckUtf8();
                return this;
            }

            public boolean hasJavaMutableApi() {
                return ((FileOptions) this.instance).hasJavaMutableApi();
            }

            public boolean getJavaMutableApi() {
                return ((FileOptions) this.instance).getJavaMutableApi();
            }

            public Builder setJavaMutableApi(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaMutableApi(value);
                return this;
            }

            public Builder clearJavaMutableApi() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaMutableApi();
                return this;
            }

            public boolean hasJavaMultipleFilesMutablePackage() {
                return ((FileOptions) this.instance).hasJavaMultipleFilesMutablePackage();
            }

            public String getJavaMultipleFilesMutablePackage() {
                return ((FileOptions) this.instance).getJavaMultipleFilesMutablePackage();
            }

            public ByteString getJavaMultipleFilesMutablePackageBytes() {
                return ((FileOptions) this.instance).getJavaMultipleFilesMutablePackageBytes();
            }

            public Builder setJavaMultipleFilesMutablePackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaMultipleFilesMutablePackage(value);
                return this;
            }

            public Builder clearJavaMultipleFilesMutablePackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaMultipleFilesMutablePackage();
                return this;
            }

            public Builder setJavaMultipleFilesMutablePackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaMultipleFilesMutablePackageBytes(value);
                return this;
            }

            public boolean hasOptimizeFor() {
                return ((FileOptions) this.instance).hasOptimizeFor();
            }

            public OptimizeMode getOptimizeFor() {
                return ((FileOptions) this.instance).getOptimizeFor();
            }

            public Builder setOptimizeFor(OptimizeMode value) {
                copyOnWrite();
                ((FileOptions) this.instance).setOptimizeFor(value);
                return this;
            }

            public Builder clearOptimizeFor() {
                copyOnWrite();
                ((FileOptions) this.instance).clearOptimizeFor();
                return this;
            }

            public boolean hasGoPackage() {
                return ((FileOptions) this.instance).hasGoPackage();
            }

            public String getGoPackage() {
                return ((FileOptions) this.instance).getGoPackage();
            }

            public ByteString getGoPackageBytes() {
                return ((FileOptions) this.instance).getGoPackageBytes();
            }

            public Builder setGoPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setGoPackage(value);
                return this;
            }

            public Builder clearGoPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearGoPackage();
                return this;
            }

            public Builder setGoPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setGoPackageBytes(value);
                return this;
            }

            public boolean hasJavascriptPackage() {
                return ((FileOptions) this.instance).hasJavascriptPackage();
            }

            public String getJavascriptPackage() {
                return ((FileOptions) this.instance).getJavascriptPackage();
            }

            public ByteString getJavascriptPackageBytes() {
                return ((FileOptions) this.instance).getJavascriptPackageBytes();
            }

            public Builder setJavascriptPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavascriptPackage(value);
                return this;
            }

            public Builder clearJavascriptPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavascriptPackage();
                return this;
            }

            public Builder setJavascriptPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavascriptPackageBytes(value);
                return this;
            }

            public boolean hasSzlApiVersion() {
                return ((FileOptions) this.instance).hasSzlApiVersion();
            }

            public int getSzlApiVersion() {
                return ((FileOptions) this.instance).getSzlApiVersion();
            }

            public Builder setSzlApiVersion(int value) {
                copyOnWrite();
                ((FileOptions) this.instance).setSzlApiVersion(value);
                return this;
            }

            public Builder clearSzlApiVersion() {
                copyOnWrite();
                ((FileOptions) this.instance).clearSzlApiVersion();
                return this;
            }

            public boolean hasCcGenericServices() {
                return ((FileOptions) this.instance).hasCcGenericServices();
            }

            public boolean getCcGenericServices() {
                return ((FileOptions) this.instance).getCcGenericServices();
            }

            public Builder setCcGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcGenericServices(value);
                return this;
            }

            public Builder clearCcGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcGenericServices();
                return this;
            }

            public boolean hasJavaGenericServices() {
                return ((FileOptions) this.instance).hasJavaGenericServices();
            }

            public boolean getJavaGenericServices() {
                return ((FileOptions) this.instance).getJavaGenericServices();
            }

            public Builder setJavaGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaGenericServices(value);
                return this;
            }

            public Builder clearJavaGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaGenericServices();
                return this;
            }

            public boolean hasPyGenericServices() {
                return ((FileOptions) this.instance).hasPyGenericServices();
            }

            public boolean getPyGenericServices() {
                return ((FileOptions) this.instance).getPyGenericServices();
            }

            public Builder setPyGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPyGenericServices(value);
                return this;
            }

            public Builder clearPyGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPyGenericServices();
                return this;
            }

            public boolean hasPhpGenericServices() {
                return ((FileOptions) this.instance).hasPhpGenericServices();
            }

            public boolean getPhpGenericServices() {
                return ((FileOptions) this.instance).getPhpGenericServices();
            }

            public Builder setPhpGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpGenericServices(value);
                return this;
            }

            public Builder clearPhpGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpGenericServices();
                return this;
            }

            public boolean hasDeprecated() {
                return ((FileOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((FileOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((FileOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasCcEnableArenas() {
                return ((FileOptions) this.instance).hasCcEnableArenas();
            }

            public boolean getCcEnableArenas() {
                return ((FileOptions) this.instance).getCcEnableArenas();
            }

            public Builder setCcEnableArenas(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcEnableArenas(value);
                return this;
            }

            public Builder clearCcEnableArenas() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcEnableArenas();
                return this;
            }

            public boolean hasObjcClassPrefix() {
                return ((FileOptions) this.instance).hasObjcClassPrefix();
            }

            public String getObjcClassPrefix() {
                return ((FileOptions) this.instance).getObjcClassPrefix();
            }

            public ByteString getObjcClassPrefixBytes() {
                return ((FileOptions) this.instance).getObjcClassPrefixBytes();
            }

            public Builder setObjcClassPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setObjcClassPrefix(value);
                return this;
            }

            public Builder clearObjcClassPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearObjcClassPrefix();
                return this;
            }

            public Builder setObjcClassPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setObjcClassPrefixBytes(value);
                return this;
            }

            public boolean hasCsharpNamespace() {
                return ((FileOptions) this.instance).hasCsharpNamespace();
            }

            public String getCsharpNamespace() {
                return ((FileOptions) this.instance).getCsharpNamespace();
            }

            public ByteString getCsharpNamespaceBytes() {
                return ((FileOptions) this.instance).getCsharpNamespaceBytes();
            }

            public Builder setCsharpNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCsharpNamespace(value);
                return this;
            }

            public Builder clearCsharpNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCsharpNamespace();
                return this;
            }

            public Builder setCsharpNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCsharpNamespaceBytes(value);
                return this;
            }

            public boolean hasSwiftPrefix() {
                return ((FileOptions) this.instance).hasSwiftPrefix();
            }

            public String getSwiftPrefix() {
                return ((FileOptions) this.instance).getSwiftPrefix();
            }

            public ByteString getSwiftPrefixBytes() {
                return ((FileOptions) this.instance).getSwiftPrefixBytes();
            }

            public Builder setSwiftPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setSwiftPrefix(value);
                return this;
            }

            public Builder clearSwiftPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearSwiftPrefix();
                return this;
            }

            public Builder setSwiftPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setSwiftPrefixBytes(value);
                return this;
            }

            public boolean hasPhpClassPrefix() {
                return ((FileOptions) this.instance).hasPhpClassPrefix();
            }

            public String getPhpClassPrefix() {
                return ((FileOptions) this.instance).getPhpClassPrefix();
            }

            public ByteString getPhpClassPrefixBytes() {
                return ((FileOptions) this.instance).getPhpClassPrefixBytes();
            }

            public Builder setPhpClassPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpClassPrefix(value);
                return this;
            }

            public Builder clearPhpClassPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpClassPrefix();
                return this;
            }

            public Builder setPhpClassPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpClassPrefixBytes(value);
                return this;
            }

            public boolean hasPhpNamespace() {
                return ((FileOptions) this.instance).hasPhpNamespace();
            }

            public String getPhpNamespace() {
                return ((FileOptions) this.instance).getPhpNamespace();
            }

            public ByteString getPhpNamespaceBytes() {
                return ((FileOptions) this.instance).getPhpNamespaceBytes();
            }

            public Builder setPhpNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpNamespace(value);
                return this;
            }

            public Builder clearPhpNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpNamespace();
                return this;
            }

            public Builder setPhpNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpNamespaceBytes(value);
                return this;
            }

            public boolean hasPhpMetadataNamespace() {
                return ((FileOptions) this.instance).hasPhpMetadataNamespace();
            }

            public String getPhpMetadataNamespace() {
                return ((FileOptions) this.instance).getPhpMetadataNamespace();
            }

            public ByteString getPhpMetadataNamespaceBytes() {
                return ((FileOptions) this.instance).getPhpMetadataNamespaceBytes();
            }

            public Builder setPhpMetadataNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpMetadataNamespace(value);
                return this;
            }

            public Builder clearPhpMetadataNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpMetadataNamespace();
                return this;
            }

            public Builder setPhpMetadataNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpMetadataNamespaceBytes(value);
                return this;
            }

            public boolean hasRubyPackage() {
                return ((FileOptions) this.instance).hasRubyPackage();
            }

            public String getRubyPackage() {
                return ((FileOptions) this.instance).getRubyPackage();
            }

            public ByteString getRubyPackageBytes() {
                return ((FileOptions) this.instance).getRubyPackageBytes();
            }

            public Builder setRubyPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setRubyPackage(value);
                return this;
            }

            public Builder clearRubyPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearRubyPackage();
                return this;
            }

            public Builder setRubyPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setRubyPackageBytes(value);
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((FileOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((FileOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((FileOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((FileOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((FileOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((FileOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001 \u0000\u0001\u0001ϧ \u0000\u0001\u0001\u0001\b\u0002\u0002\u0004\u0000\u0004\u0004\u0003\u0005\u0004\u0004\u0006\u0007\u0005\u0007\u0007\u0006\b\b\t\t\f\u000e\n\u0007\n\u000b\b\u000f\f\b\u0010\u000e\u0004\u0011\u0010\u0007\u0012\u0011\u0007\u0013\u0012\u0007\u0014\u0013\b\u0007\u0017\u0007\u0016\u0018\u0007\u0001\u001a\u0007\b\u001b\u0007\u000b\u001c\u0007\f\u001d\b\r\u001f\u0007\u0017$\b\u0018%\b\u0019'\b\u001a(\b\u001b)\b\u001c*\u0007\u0015,\b\u001d-\b\u001eϧЛ", new Object[]{"bitField0_", "javaPackage_", "ccApiVersion_", "pyApiVersion_", "javaApiVersion_", "javaUseJavaproto2_", "javaJava5Enums_", "javaOuterClassname_", "optimizeFor_", OptimizeMode.internalGetVerifier(), "javaMultipleFiles_", "goPackage_", "javascriptPackage_", "szlApiVersion_", "ccGenericServices_", "javaGenericServices_", "pyGenericServices_", "javaAltApiPackage_", "deprecated_", "ccUtf8Verification_", "javaEnableDualGenerateMutableApi_", "javaStringCheckUtf8_", "javaMutableApi_", "javaMultipleFilesMutablePackage_", "ccEnableArenas_", "objcClassPrefix_", "csharpNamespace_", "swiftPrefix_", "phpClassPrefix_", "phpNamespace_", "phpGenericServices_", "phpMetadataNamespace_", "rubyPackage_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FileOptions.class, DEFAULT_INSTANCE);
        }

        public static FileOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MessageOptions extends GeneratedMessageLite.ExtendableMessage<MessageOptions, Builder> implements MessageOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final MessageOptions DEFAULT_INSTANCE = new MessageOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int EXPERIMENTAL_JAVA_BUILDER_INTERFACE_FIELD_NUMBER = 5;
        public static final int EXPERIMENTAL_JAVA_INTERFACE_EXTENDS_FIELD_NUMBER = 6;
        public static final int EXPERIMENTAL_JAVA_MESSAGE_INTERFACE_FIELD_NUMBER = 4;
        public static final int MAP_ENTRY_FIELD_NUMBER = 7;
        public static final int MESSAGE_SET_WIRE_FORMAT_FIELD_NUMBER = 1;
        public static final int NO_STANDARD_DESCRIPTOR_ACCESSOR_FIELD_NUMBER = 2;
        private static volatile Parser<MessageOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> experimentalJavaBuilderInterface_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> experimentalJavaInterfaceExtends_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> experimentalJavaMessageInterface_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean mapEntry_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean messageSetWireFormat_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean noStandardDescriptorAccessor_;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private MessageOptions() {
        }

        @Deprecated
        public List<String> getExperimentalJavaMessageInterfaceList() {
            return this.experimentalJavaMessageInterface_;
        }

        @Deprecated
        public int getExperimentalJavaMessageInterfaceCount() {
            return this.experimentalJavaMessageInterface_.size();
        }

        @Deprecated
        public String getExperimentalJavaMessageInterface(int index) {
            return this.experimentalJavaMessageInterface_.get(index);
        }

        @Deprecated
        public ByteString getExperimentalJavaMessageInterfaceBytes(int index) {
            return ByteString.copyFromUtf8(this.experimentalJavaMessageInterface_.get(index));
        }

        private void ensureExperimentalJavaMessageInterfaceIsMutable() {
            if (!this.experimentalJavaMessageInterface_.isModifiable()) {
                this.experimentalJavaMessageInterface_ = GeneratedMessageLite.mutableCopy(this.experimentalJavaMessageInterface_);
            }
        }

        /* access modifiers changed from: private */
        public void setExperimentalJavaMessageInterface(int index, String value) {
            if (value != null) {
                ensureExperimentalJavaMessageInterfaceIsMutable();
                this.experimentalJavaMessageInterface_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaMessageInterface(String value) {
            if (value != null) {
                ensureExperimentalJavaMessageInterfaceIsMutable();
                this.experimentalJavaMessageInterface_.add(value);
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
        public void addAllExperimentalJavaMessageInterface(Iterable<String> values) {
            ensureExperimentalJavaMessageInterfaceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.experimentalJavaMessageInterface_);
        }

        /* access modifiers changed from: private */
        public void clearExperimentalJavaMessageInterface() {
            this.experimentalJavaMessageInterface_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaMessageInterfaceBytes(ByteString value) {
            if (value != null) {
                ensureExperimentalJavaMessageInterfaceIsMutable();
                this.experimentalJavaMessageInterface_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public List<String> getExperimentalJavaBuilderInterfaceList() {
            return this.experimentalJavaBuilderInterface_;
        }

        @Deprecated
        public int getExperimentalJavaBuilderInterfaceCount() {
            return this.experimentalJavaBuilderInterface_.size();
        }

        @Deprecated
        public String getExperimentalJavaBuilderInterface(int index) {
            return this.experimentalJavaBuilderInterface_.get(index);
        }

        @Deprecated
        public ByteString getExperimentalJavaBuilderInterfaceBytes(int index) {
            return ByteString.copyFromUtf8(this.experimentalJavaBuilderInterface_.get(index));
        }

        private void ensureExperimentalJavaBuilderInterfaceIsMutable() {
            if (!this.experimentalJavaBuilderInterface_.isModifiable()) {
                this.experimentalJavaBuilderInterface_ = GeneratedMessageLite.mutableCopy(this.experimentalJavaBuilderInterface_);
            }
        }

        /* access modifiers changed from: private */
        public void setExperimentalJavaBuilderInterface(int index, String value) {
            if (value != null) {
                ensureExperimentalJavaBuilderInterfaceIsMutable();
                this.experimentalJavaBuilderInterface_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaBuilderInterface(String value) {
            if (value != null) {
                ensureExperimentalJavaBuilderInterfaceIsMutable();
                this.experimentalJavaBuilderInterface_.add(value);
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
        public void addAllExperimentalJavaBuilderInterface(Iterable<String> values) {
            ensureExperimentalJavaBuilderInterfaceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.experimentalJavaBuilderInterface_);
        }

        /* access modifiers changed from: private */
        public void clearExperimentalJavaBuilderInterface() {
            this.experimentalJavaBuilderInterface_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaBuilderInterfaceBytes(ByteString value) {
            if (value != null) {
                ensureExperimentalJavaBuilderInterfaceIsMutable();
                this.experimentalJavaBuilderInterface_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public List<String> getExperimentalJavaInterfaceExtendsList() {
            return this.experimentalJavaInterfaceExtends_;
        }

        @Deprecated
        public int getExperimentalJavaInterfaceExtendsCount() {
            return this.experimentalJavaInterfaceExtends_.size();
        }

        @Deprecated
        public String getExperimentalJavaInterfaceExtends(int index) {
            return this.experimentalJavaInterfaceExtends_.get(index);
        }

        @Deprecated
        public ByteString getExperimentalJavaInterfaceExtendsBytes(int index) {
            return ByteString.copyFromUtf8(this.experimentalJavaInterfaceExtends_.get(index));
        }

        private void ensureExperimentalJavaInterfaceExtendsIsMutable() {
            if (!this.experimentalJavaInterfaceExtends_.isModifiable()) {
                this.experimentalJavaInterfaceExtends_ = GeneratedMessageLite.mutableCopy(this.experimentalJavaInterfaceExtends_);
            }
        }

        /* access modifiers changed from: private */
        public void setExperimentalJavaInterfaceExtends(int index, String value) {
            if (value != null) {
                ensureExperimentalJavaInterfaceExtendsIsMutable();
                this.experimentalJavaInterfaceExtends_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaInterfaceExtends(String value) {
            if (value != null) {
                ensureExperimentalJavaInterfaceExtendsIsMutable();
                this.experimentalJavaInterfaceExtends_.add(value);
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
        public void addAllExperimentalJavaInterfaceExtends(Iterable<String> values) {
            ensureExperimentalJavaInterfaceExtendsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.experimentalJavaInterfaceExtends_);
        }

        /* access modifiers changed from: private */
        public void clearExperimentalJavaInterfaceExtends() {
            this.experimentalJavaInterfaceExtends_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addExperimentalJavaInterfaceExtendsBytes(ByteString value) {
            if (value != null) {
                ensureExperimentalJavaInterfaceExtendsIsMutable();
                this.experimentalJavaInterfaceExtends_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasMessageSetWireFormat() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getMessageSetWireFormat() {
            return this.messageSetWireFormat_;
        }

        /* access modifiers changed from: private */
        public void setMessageSetWireFormat(boolean value) {
            this.bitField0_ |= 1;
            this.messageSetWireFormat_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMessageSetWireFormat() {
            this.bitField0_ &= -2;
            this.messageSetWireFormat_ = false;
        }

        public boolean hasNoStandardDescriptorAccessor() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getNoStandardDescriptorAccessor() {
            return this.noStandardDescriptorAccessor_;
        }

        /* access modifiers changed from: private */
        public void setNoStandardDescriptorAccessor(boolean value) {
            this.bitField0_ |= 2;
            this.noStandardDescriptorAccessor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNoStandardDescriptorAccessor() {
            this.bitField0_ &= -3;
            this.noStandardDescriptorAccessor_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 4;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -5;
            this.deprecated_ = false;
        }

        public boolean hasMapEntry() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getMapEntry() {
            return this.mapEntry_;
        }

        /* access modifiers changed from: private */
        public void setMapEntry(boolean value) {
            this.bitField0_ |= 8;
            this.mapEntry_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMapEntry() {
            this.bitField0_ &= -9;
            this.mapEntry_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static MessageOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(InputStream input) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MessageOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageOptions parseFrom(CodedInputStream input) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MessageOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MessageOptions, Builder> implements MessageOptionsOrBuilder {
            private Builder() {
                super(MessageOptions.DEFAULT_INSTANCE);
            }

            @Deprecated
            public List<String> getExperimentalJavaMessageInterfaceList() {
                return Collections.unmodifiableList(((MessageOptions) this.instance).getExperimentalJavaMessageInterfaceList());
            }

            @Deprecated
            public int getExperimentalJavaMessageInterfaceCount() {
                return ((MessageOptions) this.instance).getExperimentalJavaMessageInterfaceCount();
            }

            @Deprecated
            public String getExperimentalJavaMessageInterface(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaMessageInterface(index);
            }

            @Deprecated
            public ByteString getExperimentalJavaMessageInterfaceBytes(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaMessageInterfaceBytes(index);
            }

            @Deprecated
            public Builder setExperimentalJavaMessageInterface(int index, String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setExperimentalJavaMessageInterface(index, value);
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaMessageInterface(String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaMessageInterface(value);
                return this;
            }

            @Deprecated
            public Builder addAllExperimentalJavaMessageInterface(Iterable<String> values) {
                copyOnWrite();
                ((MessageOptions) this.instance).addAllExperimentalJavaMessageInterface(values);
                return this;
            }

            @Deprecated
            public Builder clearExperimentalJavaMessageInterface() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearExperimentalJavaMessageInterface();
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaMessageInterfaceBytes(ByteString value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaMessageInterfaceBytes(value);
                return this;
            }

            @Deprecated
            public List<String> getExperimentalJavaBuilderInterfaceList() {
                return Collections.unmodifiableList(((MessageOptions) this.instance).getExperimentalJavaBuilderInterfaceList());
            }

            @Deprecated
            public int getExperimentalJavaBuilderInterfaceCount() {
                return ((MessageOptions) this.instance).getExperimentalJavaBuilderInterfaceCount();
            }

            @Deprecated
            public String getExperimentalJavaBuilderInterface(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaBuilderInterface(index);
            }

            @Deprecated
            public ByteString getExperimentalJavaBuilderInterfaceBytes(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaBuilderInterfaceBytes(index);
            }

            @Deprecated
            public Builder setExperimentalJavaBuilderInterface(int index, String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setExperimentalJavaBuilderInterface(index, value);
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaBuilderInterface(String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaBuilderInterface(value);
                return this;
            }

            @Deprecated
            public Builder addAllExperimentalJavaBuilderInterface(Iterable<String> values) {
                copyOnWrite();
                ((MessageOptions) this.instance).addAllExperimentalJavaBuilderInterface(values);
                return this;
            }

            @Deprecated
            public Builder clearExperimentalJavaBuilderInterface() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearExperimentalJavaBuilderInterface();
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaBuilderInterfaceBytes(ByteString value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaBuilderInterfaceBytes(value);
                return this;
            }

            @Deprecated
            public List<String> getExperimentalJavaInterfaceExtendsList() {
                return Collections.unmodifiableList(((MessageOptions) this.instance).getExperimentalJavaInterfaceExtendsList());
            }

            @Deprecated
            public int getExperimentalJavaInterfaceExtendsCount() {
                return ((MessageOptions) this.instance).getExperimentalJavaInterfaceExtendsCount();
            }

            @Deprecated
            public String getExperimentalJavaInterfaceExtends(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaInterfaceExtends(index);
            }

            @Deprecated
            public ByteString getExperimentalJavaInterfaceExtendsBytes(int index) {
                return ((MessageOptions) this.instance).getExperimentalJavaInterfaceExtendsBytes(index);
            }

            @Deprecated
            public Builder setExperimentalJavaInterfaceExtends(int index, String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setExperimentalJavaInterfaceExtends(index, value);
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaInterfaceExtends(String value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaInterfaceExtends(value);
                return this;
            }

            @Deprecated
            public Builder addAllExperimentalJavaInterfaceExtends(Iterable<String> values) {
                copyOnWrite();
                ((MessageOptions) this.instance).addAllExperimentalJavaInterfaceExtends(values);
                return this;
            }

            @Deprecated
            public Builder clearExperimentalJavaInterfaceExtends() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearExperimentalJavaInterfaceExtends();
                return this;
            }

            @Deprecated
            public Builder addExperimentalJavaInterfaceExtendsBytes(ByteString value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addExperimentalJavaInterfaceExtendsBytes(value);
                return this;
            }

            public boolean hasMessageSetWireFormat() {
                return ((MessageOptions) this.instance).hasMessageSetWireFormat();
            }

            public boolean getMessageSetWireFormat() {
                return ((MessageOptions) this.instance).getMessageSetWireFormat();
            }

            public Builder setMessageSetWireFormat(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setMessageSetWireFormat(value);
                return this;
            }

            public Builder clearMessageSetWireFormat() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearMessageSetWireFormat();
                return this;
            }

            public boolean hasNoStandardDescriptorAccessor() {
                return ((MessageOptions) this.instance).hasNoStandardDescriptorAccessor();
            }

            public boolean getNoStandardDescriptorAccessor() {
                return ((MessageOptions) this.instance).getNoStandardDescriptorAccessor();
            }

            public Builder setNoStandardDescriptorAccessor(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setNoStandardDescriptorAccessor(value);
                return this;
            }

            public Builder clearNoStandardDescriptorAccessor() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearNoStandardDescriptorAccessor();
                return this;
            }

            public boolean hasDeprecated() {
                return ((MessageOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((MessageOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasMapEntry() {
                return ((MessageOptions) this.instance).hasMapEntry();
            }

            public boolean getMapEntry() {
                return ((MessageOptions) this.instance).getMapEntry();
            }

            public Builder setMapEntry(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setMapEntry(value);
                return this;
            }

            public Builder clearMapEntry() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearMapEntry();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((MessageOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((MessageOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((MessageOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((MessageOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((MessageOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MessageOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\b\u0000\u0001\u0001ϧ\b\u0000\u0004\u0001\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u001a\u0005\u001a\u0006\u001a\u0007\u0007\u0003ϧЛ", new Object[]{"bitField0_", "messageSetWireFormat_", "noStandardDescriptorAccessor_", "deprecated_", "experimentalJavaMessageInterface_", "experimentalJavaBuilderInterface_", "experimentalJavaInterfaceExtends_", "mapEntry_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MessageOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (MessageOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MessageOptions.class, DEFAULT_INSTANCE);
        }

        public static MessageOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MessageOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FieldOptions extends GeneratedMessageLite.ExtendableMessage<FieldOptions, Builder> implements FieldOptionsOrBuilder {
        public static final int CC_OPEN_ENUM_FIELD_NUMBER = 14;
        public static final int CTYPE_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final FieldOptions DEFAULT_INSTANCE = new FieldOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int DEPRECATED_RAW_MESSAGE_FIELD_NUMBER = 12;
        public static final int ENFORCE_UTF8_FIELD_NUMBER = 13;
        public static final int JSTYPE_FIELD_NUMBER = 6;
        public static final int LAZY_FIELD_NUMBER = 5;
        public static final int PACKED_FIELD_NUMBER = 2;
        private static volatile Parser<FieldOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        public static final int UPGRADED_OPTION_FIELD_NUMBER = 11;
        public static final int WEAK_FIELD_NUMBER = 10;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean ccOpenEnum_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int ctype_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private boolean deprecatedRawMessage_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private boolean enforceUtf8_ = true;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int jstype_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean lazy_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean packed_;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();
        @ProtoField(fieldNumber = 11, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UpgradedOption> upgradedOption_ = emptyProtobufList();
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean weak_;

        public interface UpgradedOptionOrBuilder extends MessageLiteOrBuilder {
            String getName();

            ByteString getNameBytes();

            String getValue();

            ByteString getValueBytes();

            boolean hasName();

            boolean hasValue();
        }

        private FieldOptions() {
        }

        public enum CType implements Internal.EnumLite {
            STRING(0),
            CORD(1),
            STRING_PIECE(2);
            
            public static final int CORD_VALUE = 1;
            public static final int STRING_PIECE_VALUE = 2;
            public static final int STRING_VALUE = 0;
            private static final Internal.EnumLiteMap<CType> internalValueMap = new Internal.EnumLiteMap<CType>() {
                public CType findValueByNumber(int number) {
                    return CType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static CType forNumber(int value2) {
                if (value2 == 0) {
                    return STRING;
                }
                if (value2 == 1) {
                    return CORD;
                }
                if (value2 != 2) {
                    return null;
                }
                return STRING_PIECE;
            }

            public static Internal.EnumLiteMap<CType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return CTypeVerifier.INSTANCE;
            }

            private static final class CTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new CTypeVerifier();

                private CTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return CType.forNumber(number) != null;
                }
            }

            private CType(int value2) {
                this.value = value2;
            }
        }

        public enum JSType implements Internal.EnumLite {
            JS_NORMAL(0),
            JS_STRING(1),
            JS_NUMBER(2);
            
            public static final int JS_NORMAL_VALUE = 0;
            public static final int JS_NUMBER_VALUE = 2;
            public static final int JS_STRING_VALUE = 1;
            private static final Internal.EnumLiteMap<JSType> internalValueMap = new Internal.EnumLiteMap<JSType>() {
                public JSType findValueByNumber(int number) {
                    return JSType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static JSType forNumber(int value2) {
                if (value2 == 0) {
                    return JS_NORMAL;
                }
                if (value2 == 1) {
                    return JS_STRING;
                }
                if (value2 != 2) {
                    return null;
                }
                return JS_NUMBER;
            }

            public static Internal.EnumLiteMap<JSType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return JSTypeVerifier.INSTANCE;
            }

            private static final class JSTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new JSTypeVerifier();

                private JSTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return JSType.forNumber(number) != null;
                }
            }

            private JSType(int value2) {
                this.value = value2;
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class UpgradedOption extends GeneratedMessageLite<UpgradedOption, Builder> implements UpgradedOptionOrBuilder {
            /* access modifiers changed from: private */
            public static final UpgradedOption DEFAULT_INSTANCE = new UpgradedOption();
            public static final int NAME_FIELD_NUMBER = 1;
            private static volatile Parser<UpgradedOption> PARSER = null;
            public static final int VALUE_FIELD_NUMBER = 2;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String name_ = "";
            @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private String value_ = "";

            private UpgradedOption() {
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getName() {
                return this.name_;
            }

            public ByteString getNameBytes() {
                return ByteString.copyFromUtf8(this.name_);
            }

            /* access modifiers changed from: private */
            public void setName(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.name_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearName() {
                this.bitField0_ &= -2;
                this.name_ = getDefaultInstance().getName();
            }

            /* access modifiers changed from: private */
            public void setNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.name_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public boolean hasValue() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getValue() {
                return this.value_;
            }

            public ByteString getValueBytes() {
                return ByteString.copyFromUtf8(this.value_);
            }

            /* access modifiers changed from: private */
            public void setValue(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.value_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearValue() {
                this.bitField0_ &= -3;
                this.value_ = getDefaultInstance().getValue();
            }

            /* access modifiers changed from: private */
            public void setValueBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.value_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public static UpgradedOption parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static UpgradedOption parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static UpgradedOption parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static UpgradedOption parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static UpgradedOption parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static UpgradedOption parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static UpgradedOption parseFrom(InputStream input) throws IOException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static UpgradedOption parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static UpgradedOption parseDelimitedFrom(InputStream input) throws IOException {
                return (UpgradedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static UpgradedOption parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (UpgradedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static UpgradedOption parseFrom(CodedInputStream input) throws IOException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static UpgradedOption parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (UpgradedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(UpgradedOption prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<UpgradedOption, Builder> implements UpgradedOptionOrBuilder {
                private Builder() {
                    super(UpgradedOption.DEFAULT_INSTANCE);
                }

                public boolean hasName() {
                    return ((UpgradedOption) this.instance).hasName();
                }

                public String getName() {
                    return ((UpgradedOption) this.instance).getName();
                }

                public ByteString getNameBytes() {
                    return ((UpgradedOption) this.instance).getNameBytes();
                }

                public Builder setName(String value) {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).setName(value);
                    return this;
                }

                public Builder clearName() {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).clearName();
                    return this;
                }

                public Builder setNameBytes(ByteString value) {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).setNameBytes(value);
                    return this;
                }

                public boolean hasValue() {
                    return ((UpgradedOption) this.instance).hasValue();
                }

                public String getValue() {
                    return ((UpgradedOption) this.instance).getValue();
                }

                public ByteString getValueBytes() {
                    return ((UpgradedOption) this.instance).getValueBytes();
                }

                public Builder setValue(String value) {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).setValue(value);
                    return this;
                }

                public Builder clearValue() {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).clearValue();
                    return this;
                }

                public Builder setValueBytes(ByteString value) {
                    copyOnWrite();
                    ((UpgradedOption) this.instance).setValueBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new UpgradedOption();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001", new Object[]{"bitField0_", "name_", "value_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<UpgradedOption> parser = PARSER;
                        if (parser == null) {
                            synchronized (UpgradedOption.class) {
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
                GeneratedMessageLite.registerDefaultInstance(UpgradedOption.class, DEFAULT_INSTANCE);
            }

            public static UpgradedOption getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<UpgradedOption> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasCtype() {
            return (this.bitField0_ & 1) != 0;
        }

        public CType getCtype() {
            CType result = CType.forNumber(this.ctype_);
            return result == null ? CType.STRING : result;
        }

        /* access modifiers changed from: private */
        public void setCtype(CType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.ctype_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCtype() {
            this.bitField0_ &= -2;
            this.ctype_ = 0;
        }

        public boolean hasPacked() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getPacked() {
            return this.packed_;
        }

        /* access modifiers changed from: private */
        public void setPacked(boolean value) {
            this.bitField0_ |= 2;
            this.packed_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPacked() {
            this.bitField0_ &= -3;
            this.packed_ = false;
        }

        public boolean hasJstype() {
            return (this.bitField0_ & 4) != 0;
        }

        public JSType getJstype() {
            JSType result = JSType.forNumber(this.jstype_);
            return result == null ? JSType.JS_NORMAL : result;
        }

        /* access modifiers changed from: private */
        public void setJstype(JSType value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.jstype_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJstype() {
            this.bitField0_ &= -5;
            this.jstype_ = 0;
        }

        public boolean hasLazy() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getLazy() {
            return this.lazy_;
        }

        /* access modifiers changed from: private */
        public void setLazy(boolean value) {
            this.bitField0_ |= 8;
            this.lazy_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLazy() {
            this.bitField0_ &= -9;
            this.lazy_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 16;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -17;
            this.deprecated_ = false;
        }

        public boolean hasWeak() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getWeak() {
            return this.weak_;
        }

        /* access modifiers changed from: private */
        public void setWeak(boolean value) {
            this.bitField0_ |= 32;
            this.weak_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWeak() {
            this.bitField0_ &= -33;
            this.weak_ = false;
        }

        public List<UpgradedOption> getUpgradedOptionList() {
            return this.upgradedOption_;
        }

        public List<? extends UpgradedOptionOrBuilder> getUpgradedOptionOrBuilderList() {
            return this.upgradedOption_;
        }

        public int getUpgradedOptionCount() {
            return this.upgradedOption_.size();
        }

        public UpgradedOption getUpgradedOption(int index) {
            return this.upgradedOption_.get(index);
        }

        public UpgradedOptionOrBuilder getUpgradedOptionOrBuilder(int index) {
            return this.upgradedOption_.get(index);
        }

        private void ensureUpgradedOptionIsMutable() {
            if (!this.upgradedOption_.isModifiable()) {
                this.upgradedOption_ = GeneratedMessageLite.mutableCopy(this.upgradedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUpgradedOption(int index, UpgradedOption value) {
            if (value != null) {
                ensureUpgradedOptionIsMutable();
                this.upgradedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUpgradedOption(int index, UpgradedOption.Builder builderForValue) {
            ensureUpgradedOptionIsMutable();
            this.upgradedOption_.set(index, (UpgradedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUpgradedOption(UpgradedOption value) {
            if (value != null) {
                ensureUpgradedOptionIsMutable();
                this.upgradedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUpgradedOption(int index, UpgradedOption value) {
            if (value != null) {
                ensureUpgradedOptionIsMutable();
                this.upgradedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUpgradedOption(UpgradedOption.Builder builderForValue) {
            ensureUpgradedOptionIsMutable();
            this.upgradedOption_.add((UpgradedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUpgradedOption(int index, UpgradedOption.Builder builderForValue) {
            ensureUpgradedOptionIsMutable();
            this.upgradedOption_.add(index, (UpgradedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$FieldOptions$UpgradedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$FieldOptions$UpgradedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUpgradedOption(Iterable<? extends UpgradedOption> values) {
            ensureUpgradedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.upgradedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUpgradedOption() {
            this.upgradedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUpgradedOption(int index) {
            ensureUpgradedOptionIsMutable();
            this.upgradedOption_.remove(index);
        }

        public boolean hasDeprecatedRawMessage() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getDeprecatedRawMessage() {
            return this.deprecatedRawMessage_;
        }

        /* access modifiers changed from: private */
        public void setDeprecatedRawMessage(boolean value) {
            this.bitField0_ |= 64;
            this.deprecatedRawMessage_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecatedRawMessage() {
            this.bitField0_ &= -65;
            this.deprecatedRawMessage_ = false;
        }

        public boolean hasEnforceUtf8() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getEnforceUtf8() {
            return this.enforceUtf8_;
        }

        /* access modifiers changed from: private */
        public void setEnforceUtf8(boolean value) {
            this.bitField0_ |= 128;
            this.enforceUtf8_ = value;
        }

        /* access modifiers changed from: private */
        public void clearEnforceUtf8() {
            this.bitField0_ &= -129;
            this.enforceUtf8_ = true;
        }

        public boolean hasCcOpenEnum() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getCcOpenEnum() {
            return this.ccOpenEnum_;
        }

        /* access modifiers changed from: private */
        public void setCcOpenEnum(boolean value) {
            this.bitField0_ |= 256;
            this.ccOpenEnum_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcOpenEnum() {
            this.bitField0_ &= -257;
            this.ccOpenEnum_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static FieldOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(InputStream input) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldOptions parseFrom(CodedInputStream input) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FieldOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<FieldOptions, Builder> implements FieldOptionsOrBuilder {
            private Builder() {
                super(FieldOptions.DEFAULT_INSTANCE);
            }

            public boolean hasCtype() {
                return ((FieldOptions) this.instance).hasCtype();
            }

            public CType getCtype() {
                return ((FieldOptions) this.instance).getCtype();
            }

            public Builder setCtype(CType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setCtype(value);
                return this;
            }

            public Builder clearCtype() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearCtype();
                return this;
            }

            public boolean hasPacked() {
                return ((FieldOptions) this.instance).hasPacked();
            }

            public boolean getPacked() {
                return ((FieldOptions) this.instance).getPacked();
            }

            public Builder setPacked(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setPacked(value);
                return this;
            }

            public Builder clearPacked() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearPacked();
                return this;
            }

            public boolean hasJstype() {
                return ((FieldOptions) this.instance).hasJstype();
            }

            public JSType getJstype() {
                return ((FieldOptions) this.instance).getJstype();
            }

            public Builder setJstype(JSType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setJstype(value);
                return this;
            }

            public Builder clearJstype() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearJstype();
                return this;
            }

            public boolean hasLazy() {
                return ((FieldOptions) this.instance).hasLazy();
            }

            public boolean getLazy() {
                return ((FieldOptions) this.instance).getLazy();
            }

            public Builder setLazy(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setLazy(value);
                return this;
            }

            public Builder clearLazy() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearLazy();
                return this;
            }

            public boolean hasDeprecated() {
                return ((FieldOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((FieldOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasWeak() {
                return ((FieldOptions) this.instance).hasWeak();
            }

            public boolean getWeak() {
                return ((FieldOptions) this.instance).getWeak();
            }

            public Builder setWeak(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setWeak(value);
                return this;
            }

            public Builder clearWeak() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearWeak();
                return this;
            }

            public List<UpgradedOption> getUpgradedOptionList() {
                return Collections.unmodifiableList(((FieldOptions) this.instance).getUpgradedOptionList());
            }

            public int getUpgradedOptionCount() {
                return ((FieldOptions) this.instance).getUpgradedOptionCount();
            }

            public UpgradedOption getUpgradedOption(int index) {
                return ((FieldOptions) this.instance).getUpgradedOption(index);
            }

            public Builder setUpgradedOption(int index, UpgradedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUpgradedOption(index, value);
                return this;
            }

            public Builder setUpgradedOption(int index, UpgradedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUpgradedOption(index, builderForValue);
                return this;
            }

            public Builder addUpgradedOption(UpgradedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUpgradedOption(value);
                return this;
            }

            public Builder addUpgradedOption(int index, UpgradedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUpgradedOption(index, value);
                return this;
            }

            public Builder addUpgradedOption(UpgradedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUpgradedOption(builderForValue);
                return this;
            }

            public Builder addUpgradedOption(int index, UpgradedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUpgradedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUpgradedOption(Iterable<? extends UpgradedOption> values) {
                copyOnWrite();
                ((FieldOptions) this.instance).addAllUpgradedOption(values);
                return this;
            }

            public Builder clearUpgradedOption() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearUpgradedOption();
                return this;
            }

            public Builder removeUpgradedOption(int index) {
                copyOnWrite();
                ((FieldOptions) this.instance).removeUpgradedOption(index);
                return this;
            }

            public boolean hasDeprecatedRawMessage() {
                return ((FieldOptions) this.instance).hasDeprecatedRawMessage();
            }

            public boolean getDeprecatedRawMessage() {
                return ((FieldOptions) this.instance).getDeprecatedRawMessage();
            }

            public Builder setDeprecatedRawMessage(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setDeprecatedRawMessage(value);
                return this;
            }

            public Builder clearDeprecatedRawMessage() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearDeprecatedRawMessage();
                return this;
            }

            public boolean hasEnforceUtf8() {
                return ((FieldOptions) this.instance).hasEnforceUtf8();
            }

            public boolean getEnforceUtf8() {
                return ((FieldOptions) this.instance).getEnforceUtf8();
            }

            public Builder setEnforceUtf8(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setEnforceUtf8(value);
                return this;
            }

            public Builder clearEnforceUtf8() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearEnforceUtf8();
                return this;
            }

            public boolean hasCcOpenEnum() {
                return ((FieldOptions) this.instance).hasCcOpenEnum();
            }

            public boolean getCcOpenEnum() {
                return ((FieldOptions) this.instance).getCcOpenEnum();
            }

            public Builder setCcOpenEnum(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setCcOpenEnum(value);
                return this;
            }

            public Builder clearCcOpenEnum() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearCcOpenEnum();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((FieldOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((FieldOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((FieldOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((FieldOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((FieldOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000b\u0000\u0001\u0001ϧ\u000b\u0000\u0002\u0001\u0001\f\u0000\u0002\u0007\u0001\u0003\u0007\u0004\u0005\u0007\u0003\u0006\f\u0002\n\u0007\u0005\u000b\u001b\f\u0007\u0006\r\u0007\u0007\u000e\u0007\bϧЛ", new Object[]{"bitField0_", "ctype_", CType.internalGetVerifier(), "packed_", "deprecated_", "lazy_", "jstype_", JSType.internalGetVerifier(), "weak_", "upgradedOption_", UpgradedOption.class, "deprecatedRawMessage_", "enforceUtf8_", "ccOpenEnum_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FieldOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (FieldOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FieldOptions.class, DEFAULT_INSTANCE);
        }

        public static FieldOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class OneofOptions extends GeneratedMessageLite.ExtendableMessage<OneofOptions, Builder> implements OneofOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final OneofOptions DEFAULT_INSTANCE = new OneofOptions();
        private static volatile Parser<OneofOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private OneofOptions() {
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static OneofOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(InputStream input) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (OneofOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofOptions parseFrom(CodedInputStream input) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(OneofOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<OneofOptions, Builder> implements OneofOptionsOrBuilder {
            private Builder() {
                super(OneofOptions.DEFAULT_INSTANCE);
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((OneofOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((OneofOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((OneofOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((OneofOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((OneofOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((OneofOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new OneofOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000ϧϧ\u0001\u0000\u0001\u0001ϧЛ", new Object[]{"uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<OneofOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (OneofOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(OneofOptions.class, DEFAULT_INSTANCE);
        }

        public static OneofOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OneofOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class EnumOptions extends GeneratedMessageLite.ExtendableMessage<EnumOptions, Builder> implements EnumOptionsOrBuilder {
        public static final int ALLOW_ALIAS_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final EnumOptions DEFAULT_INSTANCE = new EnumOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        private static volatile Parser<EnumOptions> PARSER = null;
        public static final int PROTO1_NAME_FIELD_NUMBER = 1;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean allowAlias_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean deprecated_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String proto1Name_ = "";
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private EnumOptions() {
        }

        public boolean hasProto1Name() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getProto1Name() {
            return this.proto1Name_;
        }

        public ByteString getProto1NameBytes() {
            return ByteString.copyFromUtf8(this.proto1Name_);
        }

        /* access modifiers changed from: private */
        public void setProto1Name(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.proto1Name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProto1Name() {
            this.bitField0_ &= -2;
            this.proto1Name_ = getDefaultInstance().getProto1Name();
        }

        /* access modifiers changed from: private */
        public void setProto1NameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.proto1Name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasAllowAlias() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getAllowAlias() {
            return this.allowAlias_;
        }

        /* access modifiers changed from: private */
        public void setAllowAlias(boolean value) {
            this.bitField0_ |= 2;
            this.allowAlias_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAllowAlias() {
            this.bitField0_ &= -3;
            this.allowAlias_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 4;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -5;
            this.deprecated_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static EnumOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(InputStream input) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EnumOptions, Builder> implements EnumOptionsOrBuilder {
            private Builder() {
                super(EnumOptions.DEFAULT_INSTANCE);
            }

            public boolean hasProto1Name() {
                return ((EnumOptions) this.instance).hasProto1Name();
            }

            public String getProto1Name() {
                return ((EnumOptions) this.instance).getProto1Name();
            }

            public ByteString getProto1NameBytes() {
                return ((EnumOptions) this.instance).getProto1NameBytes();
            }

            public Builder setProto1Name(String value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setProto1Name(value);
                return this;
            }

            public Builder clearProto1Name() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearProto1Name();
                return this;
            }

            public Builder setProto1NameBytes(ByteString value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setProto1NameBytes(value);
                return this;
            }

            public boolean hasAllowAlias() {
                return ((EnumOptions) this.instance).hasAllowAlias();
            }

            public boolean getAllowAlias() {
                return ((EnumOptions) this.instance).getAllowAlias();
            }

            public Builder setAllowAlias(boolean value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setAllowAlias(value);
                return this;
            }

            public Builder clearAllowAlias() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearAllowAlias();
                return this;
            }

            public boolean hasDeprecated() {
                return ((EnumOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((EnumOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearDeprecated();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((EnumOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((EnumOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((EnumOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((EnumOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((EnumOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001ϧ\u0004\u0000\u0001\u0001\u0001\b\u0000\u0002\u0007\u0001\u0003\u0007\u0002ϧЛ", new Object[]{"bitField0_", "proto1Name_", "allowAlias_", "deprecated_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(EnumOptions.class, DEFAULT_INSTANCE);
        }

        public static EnumOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class EnumValueOptions extends GeneratedMessageLite.ExtendableMessage<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final EnumValueOptions DEFAULT_INSTANCE = new EnumValueOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 1;
        private static volatile Parser<EnumValueOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean deprecated_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private EnumValueOptions() {
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 1;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -2;
            this.deprecated_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static EnumValueOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(InputStream input) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumValueOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
            private Builder() {
                super(EnumValueOptions.DEFAULT_INSTANCE);
            }

            public boolean hasDeprecated() {
                return ((EnumValueOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((EnumValueOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearDeprecated();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((EnumValueOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((EnumValueOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((EnumValueOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumValueOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001ϧ\u0002\u0000\u0001\u0001\u0001\u0007\u0000ϧЛ", new Object[]{"bitField0_", "deprecated_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumValueOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumValueOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(EnumValueOptions.class, DEFAULT_INSTANCE);
        }

        public static EnumValueOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumValueOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ServiceOptions extends GeneratedMessageLite.ExtendableMessage<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final ServiceOptions DEFAULT_INSTANCE = new ServiceOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static final int FAILURE_DETECTION_DELAY_FIELD_NUMBER = 16;
        public static final int MULTICAST_STUB_FIELD_NUMBER = 20;
        private static volatile Parser<ServiceOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 33, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.DOUBLE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private double failureDetectionDelay_ = -1.0d;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 20, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean multicastStub_;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private ServiceOptions() {
        }

        public boolean hasMulticastStub() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getMulticastStub() {
            return this.multicastStub_;
        }

        /* access modifiers changed from: private */
        public void setMulticastStub(boolean value) {
            this.bitField0_ |= 1;
            this.multicastStub_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMulticastStub() {
            this.bitField0_ &= -2;
            this.multicastStub_ = false;
        }

        public boolean hasFailureDetectionDelay() {
            return (this.bitField0_ & 2) != 0;
        }

        public double getFailureDetectionDelay() {
            return this.failureDetectionDelay_;
        }

        /* access modifiers changed from: private */
        public void setFailureDetectionDelay(double value) {
            this.bitField0_ |= 2;
            this.failureDetectionDelay_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFailureDetectionDelay() {
            this.bitField0_ &= -3;
            this.failureDetectionDelay_ = -1.0d;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 4;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -5;
            this.deprecated_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static ServiceOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(InputStream input) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceOptions parseFrom(CodedInputStream input) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServiceOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
            private Builder() {
                super(ServiceOptions.DEFAULT_INSTANCE);
            }

            public boolean hasMulticastStub() {
                return ((ServiceOptions) this.instance).hasMulticastStub();
            }

            public boolean getMulticastStub() {
                return ((ServiceOptions) this.instance).getMulticastStub();
            }

            public Builder setMulticastStub(boolean value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setMulticastStub(value);
                return this;
            }

            public Builder clearMulticastStub() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearMulticastStub();
                return this;
            }

            public boolean hasFailureDetectionDelay() {
                return ((ServiceOptions) this.instance).hasFailureDetectionDelay();
            }

            public double getFailureDetectionDelay() {
                return ((ServiceOptions) this.instance).getFailureDetectionDelay();
            }

            public Builder setFailureDetectionDelay(double value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setFailureDetectionDelay(value);
                return this;
            }

            public Builder clearFailureDetectionDelay() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearFailureDetectionDelay();
                return this;
            }

            public boolean hasDeprecated() {
                return ((ServiceOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((ServiceOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearDeprecated();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((ServiceOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((ServiceOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((ServiceOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((ServiceOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0010ϧ\u0004\u0000\u0001\u0001\u0010\u0000\u0001\u0014\u0007\u0000!\u0007\u0002ϧЛ", new Object[]{"bitField0_", "failureDetectionDelay_", "multicastStub_", "deprecated_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ServiceOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServiceOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ServiceOptions.class, DEFAULT_INSTANCE);
        }

        public static ServiceOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServiceOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MethodOptions extends GeneratedMessageLite.ExtendableMessage<MethodOptions, Builder> implements MethodOptionsOrBuilder {
        public static final int CLIENT_LOGGING_FIELD_NUMBER = 11;
        public static final int CLIENT_STREAMING_FIELD_NUMBER = 20;
        public static final int DEADLINE_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final MethodOptions DEFAULT_INSTANCE = new MethodOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static final int DUPLICATE_SUPPRESSION_FIELD_NUMBER = 9;
        public static final int END_USER_CREDS_REQUESTED_FIELD_NUMBER = 26;
        public static final int FAIL_FAST_FIELD_NUMBER = 10;
        public static final int GO_LEGACY_CHANNEL_API_FIELD_NUMBER = 29;
        public static final int IDEMPOTENCY_LEVEL_FIELD_NUMBER = 34;
        public static final int LEGACY_CLIENT_INITIAL_TOKENS_FIELD_NUMBER = 24;
        public static final int LEGACY_RESULT_TYPE_FIELD_NUMBER = 23;
        public static final int LEGACY_SERVER_INITIAL_TOKENS_FIELD_NUMBER = 25;
        public static final int LEGACY_STREAM_TYPE_FIELD_NUMBER = 22;
        public static final int LEGACY_TOKEN_UNIT_FIELD_NUMBER = 28;
        public static final int LOG_LEVEL_FIELD_NUMBER = 27;
        private static volatile Parser<MethodOptions> PARSER = null;
        public static final int PROTOCOL_FIELD_NUMBER = 7;
        public static final int REQUEST_FORMAT_FIELD_NUMBER = 17;
        public static final int RESPONSE_FORMAT_FIELD_NUMBER = 15;
        public static final int SECURITY_LABEL_FIELD_NUMBER = 19;
        public static final int SECURITY_LEVEL_FIELD_NUMBER = 13;
        public static final int SERVER_LOGGING_FIELD_NUMBER = 12;
        public static final int SERVER_STREAMING_FIELD_NUMBER = 21;
        public static final int STREAM_TYPE_FIELD_NUMBER = 18;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.SINT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int clientLogging_ = 256;
        @ProtoField(fieldNumber = 20, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private boolean clientStreaming_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.DOUBLE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private double deadline_ = -1.0d;
        @ProtoField(fieldNumber = 33, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2097152, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean duplicateSuppression_;
        @ProtoField(fieldNumber = 26, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean endUserCredsRequested_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean failFast_;
        @ProtoField(fieldNumber = 29, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private boolean goLegacyChannelApi_;
        @ProtoField(fieldNumber = 34, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4194304, presenceBitsId = 0)
        private int idempotencyLevel_;
        @ProtoField(fieldNumber = 24, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private long legacyClientInitialTokens_ = -1;
        @ProtoField(fieldNumber = 23, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private String legacyResultType_ = "";
        @ProtoField(fieldNumber = 25, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private long legacyServerInitialTokens_ = -1;
        @ProtoField(fieldNumber = 22, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private String legacyStreamType_ = "";
        @ProtoField(fieldNumber = 28, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 0)
        private int legacyTokenUnit_ = 1;
        @ProtoField(fieldNumber = 27, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 0)
        private int logLevel_ = 2;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int protocol_;
        @ProtoField(fieldNumber = 17, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private int requestFormat_;
        @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private int responseFormat_;
        @ProtoField(fieldNumber = 19, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private String securityLabel_ = "";
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int securityLevel_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.SINT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int serverLogging_ = 256;
        @ProtoField(fieldNumber = 21, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private boolean serverStreaming_;
        @ProtoField(fieldNumber = 18, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private String streamType_ = "";
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private MethodOptions() {
        }

        public enum Protocol implements Internal.EnumLite {
            TCP(0),
            UDP(1);
            
            public static final int TCP_VALUE = 0;
            public static final int UDP_VALUE = 1;
            private static final Internal.EnumLiteMap<Protocol> internalValueMap = new Internal.EnumLiteMap<Protocol>() {
                public Protocol findValueByNumber(int number) {
                    return Protocol.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Protocol forNumber(int value2) {
                if (value2 == 0) {
                    return TCP;
                }
                if (value2 != 1) {
                    return null;
                }
                return UDP;
            }

            public static Internal.EnumLiteMap<Protocol> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ProtocolVerifier.INSTANCE;
            }

            private static final class ProtocolVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ProtocolVerifier();

                private ProtocolVerifier() {
                }

                public boolean isInRange(int number) {
                    return Protocol.forNumber(number) != null;
                }
            }

            private Protocol(int value2) {
                this.value = value2;
            }
        }

        public enum SecurityLevel implements Internal.EnumLite {
            NONE(0),
            INTEGRITY(1),
            PRIVACY_AND_INTEGRITY(2),
            STRONG_PRIVACY_AND_INTEGRITY(3);
            
            public static final int INTEGRITY_VALUE = 1;
            public static final int NONE_VALUE = 0;
            public static final int PRIVACY_AND_INTEGRITY_VALUE = 2;
            public static final int STRONG_PRIVACY_AND_INTEGRITY_VALUE = 3;
            private static final Internal.EnumLiteMap<SecurityLevel> internalValueMap = new Internal.EnumLiteMap<SecurityLevel>() {
                public SecurityLevel findValueByNumber(int number) {
                    return SecurityLevel.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static SecurityLevel forNumber(int value2) {
                if (value2 == 0) {
                    return NONE;
                }
                if (value2 == 1) {
                    return INTEGRITY;
                }
                if (value2 == 2) {
                    return PRIVACY_AND_INTEGRITY;
                }
                if (value2 != 3) {
                    return null;
                }
                return STRONG_PRIVACY_AND_INTEGRITY;
            }

            public static Internal.EnumLiteMap<SecurityLevel> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return SecurityLevelVerifier.INSTANCE;
            }

            private static final class SecurityLevelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new SecurityLevelVerifier();

                private SecurityLevelVerifier() {
                }

                public boolean isInRange(int number) {
                    return SecurityLevel.forNumber(number) != null;
                }
            }

            private SecurityLevel(int value2) {
                this.value = value2;
            }
        }

        public enum Format implements Internal.EnumLite {
            UNCOMPRESSED(0),
            ZIPPY_COMPRESSED(1);
            
            public static final int UNCOMPRESSED_VALUE = 0;
            public static final int ZIPPY_COMPRESSED_VALUE = 1;
            private static final Internal.EnumLiteMap<Format> internalValueMap = new Internal.EnumLiteMap<Format>() {
                public Format findValueByNumber(int number) {
                    return Format.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Format forNumber(int value2) {
                if (value2 == 0) {
                    return UNCOMPRESSED;
                }
                if (value2 != 1) {
                    return null;
                }
                return ZIPPY_COMPRESSED;
            }

            public static Internal.EnumLiteMap<Format> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return FormatVerifier.INSTANCE;
            }

            private static final class FormatVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new FormatVerifier();

                private FormatVerifier() {
                }

                public boolean isInRange(int number) {
                    return Format.forNumber(number) != null;
                }
            }

            private Format(int value2) {
                this.value = value2;
            }
        }

        public enum LogLevel implements Internal.EnumLite {
            LOG_NONE(0),
            LOG_HEADER_ONLY(1),
            LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL(2),
            LOG_HEADER_AND_FILTERED_PAYLOAD(3),
            LOG_HEADER_AND_PAYLOAD(4);
            
            public static final int LOG_HEADER_AND_FILTERED_PAYLOAD_VALUE = 3;
            public static final int LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL_VALUE = 2;
            public static final int LOG_HEADER_AND_PAYLOAD_VALUE = 4;
            public static final int LOG_HEADER_ONLY_VALUE = 1;
            public static final int LOG_NONE_VALUE = 0;
            private static final Internal.EnumLiteMap<LogLevel> internalValueMap = new Internal.EnumLiteMap<LogLevel>() {
                public LogLevel findValueByNumber(int number) {
                    return LogLevel.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static LogLevel forNumber(int value2) {
                if (value2 == 0) {
                    return LOG_NONE;
                }
                if (value2 == 1) {
                    return LOG_HEADER_ONLY;
                }
                if (value2 == 2) {
                    return LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL;
                }
                if (value2 == 3) {
                    return LOG_HEADER_AND_FILTERED_PAYLOAD;
                }
                if (value2 != 4) {
                    return null;
                }
                return LOG_HEADER_AND_PAYLOAD;
            }

            public static Internal.EnumLiteMap<LogLevel> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return LogLevelVerifier.INSTANCE;
            }

            private static final class LogLevelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new LogLevelVerifier();

                private LogLevelVerifier() {
                }

                public boolean isInRange(int number) {
                    return LogLevel.forNumber(number) != null;
                }
            }

            private LogLevel(int value2) {
                this.value = value2;
            }
        }

        public enum TokenUnit implements Internal.EnumLite {
            MESSAGE(0),
            BYTE(1);
            
            public static final int BYTE_VALUE = 1;
            public static final int MESSAGE_VALUE = 0;
            private static final Internal.EnumLiteMap<TokenUnit> internalValueMap = new Internal.EnumLiteMap<TokenUnit>() {
                public TokenUnit findValueByNumber(int number) {
                    return TokenUnit.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static TokenUnit forNumber(int value2) {
                if (value2 == 0) {
                    return MESSAGE;
                }
                if (value2 != 1) {
                    return null;
                }
                return BYTE;
            }

            public static Internal.EnumLiteMap<TokenUnit> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TokenUnitVerifier.INSTANCE;
            }

            private static final class TokenUnitVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TokenUnitVerifier();

                private TokenUnitVerifier() {
                }

                public boolean isInRange(int number) {
                    return TokenUnit.forNumber(number) != null;
                }
            }

            private TokenUnit(int value2) {
                this.value = value2;
            }
        }

        public enum IdempotencyLevel implements Internal.EnumLite {
            IDEMPOTENCY_UNKNOWN(0),
            NO_SIDE_EFFECTS(1),
            IDEMPOTENT(2);
            
            public static final int IDEMPOTENCY_UNKNOWN_VALUE = 0;
            public static final int IDEMPOTENT_VALUE = 2;
            public static final int NO_SIDE_EFFECTS_VALUE = 1;
            private static final Internal.EnumLiteMap<IdempotencyLevel> internalValueMap = new Internal.EnumLiteMap<IdempotencyLevel>() {
                public IdempotencyLevel findValueByNumber(int number) {
                    return IdempotencyLevel.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static IdempotencyLevel forNumber(int value2) {
                if (value2 == 0) {
                    return IDEMPOTENCY_UNKNOWN;
                }
                if (value2 == 1) {
                    return NO_SIDE_EFFECTS;
                }
                if (value2 != 2) {
                    return null;
                }
                return IDEMPOTENT;
            }

            public static Internal.EnumLiteMap<IdempotencyLevel> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return IdempotencyLevelVerifier.INSTANCE;
            }

            private static final class IdempotencyLevelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new IdempotencyLevelVerifier();

                private IdempotencyLevelVerifier() {
                }

                public boolean isInRange(int number) {
                    return IdempotencyLevel.forNumber(number) != null;
                }
            }

            private IdempotencyLevel(int value2) {
                this.value = value2;
            }
        }

        public boolean hasProtocol() {
            return (this.bitField0_ & 1) != 0;
        }

        public Protocol getProtocol() {
            Protocol result = Protocol.forNumber(this.protocol_);
            return result == null ? Protocol.TCP : result;
        }

        /* access modifiers changed from: private */
        public void setProtocol(Protocol value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.protocol_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProtocol() {
            this.bitField0_ &= -2;
            this.protocol_ = 0;
        }

        public boolean hasDeadline() {
            return (this.bitField0_ & 2) != 0;
        }

        public double getDeadline() {
            return this.deadline_;
        }

        /* access modifiers changed from: private */
        public void setDeadline(double value) {
            this.bitField0_ |= 2;
            this.deadline_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeadline() {
            this.bitField0_ &= -3;
            this.deadline_ = -1.0d;
        }

        public boolean hasDuplicateSuppression() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDuplicateSuppression() {
            return this.duplicateSuppression_;
        }

        /* access modifiers changed from: private */
        public void setDuplicateSuppression(boolean value) {
            this.bitField0_ |= 4;
            this.duplicateSuppression_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDuplicateSuppression() {
            this.bitField0_ &= -5;
            this.duplicateSuppression_ = false;
        }

        public boolean hasFailFast() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getFailFast() {
            return this.failFast_;
        }

        /* access modifiers changed from: private */
        public void setFailFast(boolean value) {
            this.bitField0_ |= 8;
            this.failFast_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFailFast() {
            this.bitField0_ &= -9;
            this.failFast_ = false;
        }

        public boolean hasEndUserCredsRequested() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getEndUserCredsRequested() {
            return this.endUserCredsRequested_;
        }

        /* access modifiers changed from: private */
        public void setEndUserCredsRequested(boolean value) {
            this.bitField0_ |= 16;
            this.endUserCredsRequested_ = value;
        }

        /* access modifiers changed from: private */
        public void clearEndUserCredsRequested() {
            this.bitField0_ &= -17;
            this.endUserCredsRequested_ = false;
        }

        public boolean hasClientLogging() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getClientLogging() {
            return this.clientLogging_;
        }

        /* access modifiers changed from: private */
        public void setClientLogging(int value) {
            this.bitField0_ |= 32;
            this.clientLogging_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientLogging() {
            this.bitField0_ &= -33;
            this.clientLogging_ = 256;
        }

        public boolean hasServerLogging() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getServerLogging() {
            return this.serverLogging_;
        }

        /* access modifiers changed from: private */
        public void setServerLogging(int value) {
            this.bitField0_ |= 64;
            this.serverLogging_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerLogging() {
            this.bitField0_ &= -65;
            this.serverLogging_ = 256;
        }

        public boolean hasSecurityLevel() {
            return (this.bitField0_ & 128) != 0;
        }

        public SecurityLevel getSecurityLevel() {
            SecurityLevel result = SecurityLevel.forNumber(this.securityLevel_);
            return result == null ? SecurityLevel.NONE : result;
        }

        /* access modifiers changed from: private */
        public void setSecurityLevel(SecurityLevel value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.securityLevel_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSecurityLevel() {
            this.bitField0_ &= -129;
            this.securityLevel_ = 0;
        }

        public boolean hasResponseFormat() {
            return (this.bitField0_ & 256) != 0;
        }

        public Format getResponseFormat() {
            Format result = Format.forNumber(this.responseFormat_);
            return result == null ? Format.UNCOMPRESSED : result;
        }

        /* access modifiers changed from: private */
        public void setResponseFormat(Format value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.responseFormat_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearResponseFormat() {
            this.bitField0_ &= -257;
            this.responseFormat_ = 0;
        }

        public boolean hasRequestFormat() {
            return (this.bitField0_ & 512) != 0;
        }

        public Format getRequestFormat() {
            Format result = Format.forNumber(this.requestFormat_);
            return result == null ? Format.UNCOMPRESSED : result;
        }

        /* access modifiers changed from: private */
        public void setRequestFormat(Format value) {
            if (value != null) {
                this.bitField0_ |= 512;
                this.requestFormat_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestFormat() {
            this.bitField0_ &= -513;
            this.requestFormat_ = 0;
        }

        public boolean hasStreamType() {
            return (this.bitField0_ & 1024) != 0;
        }

        public String getStreamType() {
            return this.streamType_;
        }

        public ByteString getStreamTypeBytes() {
            return ByteString.copyFromUtf8(this.streamType_);
        }

        /* access modifiers changed from: private */
        public void setStreamType(String value) {
            if (value != null) {
                this.bitField0_ |= 1024;
                this.streamType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStreamType() {
            this.bitField0_ &= -1025;
            this.streamType_ = getDefaultInstance().getStreamType();
        }

        /* access modifiers changed from: private */
        public void setStreamTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1024;
                this.streamType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSecurityLabel() {
            return (this.bitField0_ & 2048) != 0;
        }

        public String getSecurityLabel() {
            return this.securityLabel_;
        }

        public ByteString getSecurityLabelBytes() {
            return ByteString.copyFromUtf8(this.securityLabel_);
        }

        /* access modifiers changed from: private */
        public void setSecurityLabel(String value) {
            if (value != null) {
                this.bitField0_ |= 2048;
                this.securityLabel_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSecurityLabel() {
            this.bitField0_ &= -2049;
            this.securityLabel_ = getDefaultInstance().getSecurityLabel();
        }

        /* access modifiers changed from: private */
        public void setSecurityLabelBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2048;
                this.securityLabel_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasClientStreaming() {
            return (this.bitField0_ & 4096) != 0;
        }

        public boolean getClientStreaming() {
            return this.clientStreaming_;
        }

        /* access modifiers changed from: private */
        public void setClientStreaming(boolean value) {
            this.bitField0_ |= 4096;
            this.clientStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientStreaming() {
            this.bitField0_ &= -4097;
            this.clientStreaming_ = false;
        }

        public boolean hasServerStreaming() {
            return (this.bitField0_ & 8192) != 0;
        }

        public boolean getServerStreaming() {
            return this.serverStreaming_;
        }

        /* access modifiers changed from: private */
        public void setServerStreaming(boolean value) {
            this.bitField0_ |= 8192;
            this.serverStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerStreaming() {
            this.bitField0_ &= -8193;
            this.serverStreaming_ = false;
        }

        public boolean hasLegacyStreamType() {
            return (this.bitField0_ & 16384) != 0;
        }

        public String getLegacyStreamType() {
            return this.legacyStreamType_;
        }

        public ByteString getLegacyStreamTypeBytes() {
            return ByteString.copyFromUtf8(this.legacyStreamType_);
        }

        /* access modifiers changed from: private */
        public void setLegacyStreamType(String value) {
            if (value != null) {
                this.bitField0_ |= 16384;
                this.legacyStreamType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLegacyStreamType() {
            this.bitField0_ &= -16385;
            this.legacyStreamType_ = getDefaultInstance().getLegacyStreamType();
        }

        /* access modifiers changed from: private */
        public void setLegacyStreamTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16384;
                this.legacyStreamType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasLegacyResultType() {
            return (this.bitField0_ & 32768) != 0;
        }

        public String getLegacyResultType() {
            return this.legacyResultType_;
        }

        public ByteString getLegacyResultTypeBytes() {
            return ByteString.copyFromUtf8(this.legacyResultType_);
        }

        /* access modifiers changed from: private */
        public void setLegacyResultType(String value) {
            if (value != null) {
                this.bitField0_ |= 32768;
                this.legacyResultType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLegacyResultType() {
            this.bitField0_ &= -32769;
            this.legacyResultType_ = getDefaultInstance().getLegacyResultType();
        }

        /* access modifiers changed from: private */
        public void setLegacyResultTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32768;
                this.legacyResultType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasGoLegacyChannelApi() {
            return (this.bitField0_ & 65536) != 0;
        }

        public boolean getGoLegacyChannelApi() {
            return this.goLegacyChannelApi_;
        }

        /* access modifiers changed from: private */
        public void setGoLegacyChannelApi(boolean value) {
            this.bitField0_ |= 65536;
            this.goLegacyChannelApi_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGoLegacyChannelApi() {
            this.bitField0_ &= -65537;
            this.goLegacyChannelApi_ = false;
        }

        public boolean hasLegacyClientInitialTokens() {
            return (this.bitField0_ & 131072) != 0;
        }

        public long getLegacyClientInitialTokens() {
            return this.legacyClientInitialTokens_;
        }

        /* access modifiers changed from: private */
        public void setLegacyClientInitialTokens(long value) {
            this.bitField0_ |= 131072;
            this.legacyClientInitialTokens_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLegacyClientInitialTokens() {
            this.bitField0_ &= -131073;
            this.legacyClientInitialTokens_ = -1;
        }

        public boolean hasLegacyServerInitialTokens() {
            return (this.bitField0_ & 262144) != 0;
        }

        public long getLegacyServerInitialTokens() {
            return this.legacyServerInitialTokens_;
        }

        /* access modifiers changed from: private */
        public void setLegacyServerInitialTokens(long value) {
            this.bitField0_ |= 262144;
            this.legacyServerInitialTokens_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLegacyServerInitialTokens() {
            this.bitField0_ &= -262145;
            this.legacyServerInitialTokens_ = -1;
        }

        public boolean hasLegacyTokenUnit() {
            return (this.bitField0_ & 524288) != 0;
        }

        public TokenUnit getLegacyTokenUnit() {
            TokenUnit result = TokenUnit.forNumber(this.legacyTokenUnit_);
            return result == null ? TokenUnit.BYTE : result;
        }

        /* access modifiers changed from: private */
        public void setLegacyTokenUnit(TokenUnit value) {
            if (value != null) {
                this.bitField0_ |= 524288;
                this.legacyTokenUnit_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLegacyTokenUnit() {
            this.bitField0_ &= -524289;
            this.legacyTokenUnit_ = 1;
        }

        public boolean hasLogLevel() {
            return (this.bitField0_ & 1048576) != 0;
        }

        public LogLevel getLogLevel() {
            LogLevel result = LogLevel.forNumber(this.logLevel_);
            return result == null ? LogLevel.LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL : result;
        }

        /* access modifiers changed from: private */
        public void setLogLevel(LogLevel value) {
            if (value != null) {
                this.bitField0_ |= 1048576;
                this.logLevel_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLogLevel() {
            this.bitField0_ &= -1048577;
            this.logLevel_ = 2;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 2097152) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 2097152;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -2097153;
            this.deprecated_ = false;
        }

        public boolean hasIdempotencyLevel() {
            return (this.bitField0_ & 4194304) != 0;
        }

        public IdempotencyLevel getIdempotencyLevel() {
            IdempotencyLevel result = IdempotencyLevel.forNumber(this.idempotencyLevel_);
            return result == null ? IdempotencyLevel.IDEMPOTENCY_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setIdempotencyLevel(IdempotencyLevel value) {
            if (value != null) {
                this.bitField0_ |= 4194304;
                this.idempotencyLevel_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIdempotencyLevel() {
            this.bitField0_ &= -4194305;
            this.idempotencyLevel_ = 0;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static MethodOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(InputStream input) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodOptions parseFrom(CodedInputStream input) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MethodOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MethodOptions, Builder> implements MethodOptionsOrBuilder {
            private Builder() {
                super(MethodOptions.DEFAULT_INSTANCE);
            }

            public boolean hasProtocol() {
                return ((MethodOptions) this.instance).hasProtocol();
            }

            public Protocol getProtocol() {
                return ((MethodOptions) this.instance).getProtocol();
            }

            public Builder setProtocol(Protocol value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setProtocol(value);
                return this;
            }

            public Builder clearProtocol() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearProtocol();
                return this;
            }

            public boolean hasDeadline() {
                return ((MethodOptions) this.instance).hasDeadline();
            }

            public double getDeadline() {
                return ((MethodOptions) this.instance).getDeadline();
            }

            public Builder setDeadline(double value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setDeadline(value);
                return this;
            }

            public Builder clearDeadline() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearDeadline();
                return this;
            }

            public boolean hasDuplicateSuppression() {
                return ((MethodOptions) this.instance).hasDuplicateSuppression();
            }

            public boolean getDuplicateSuppression() {
                return ((MethodOptions) this.instance).getDuplicateSuppression();
            }

            public Builder setDuplicateSuppression(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setDuplicateSuppression(value);
                return this;
            }

            public Builder clearDuplicateSuppression() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearDuplicateSuppression();
                return this;
            }

            public boolean hasFailFast() {
                return ((MethodOptions) this.instance).hasFailFast();
            }

            public boolean getFailFast() {
                return ((MethodOptions) this.instance).getFailFast();
            }

            public Builder setFailFast(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setFailFast(value);
                return this;
            }

            public Builder clearFailFast() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearFailFast();
                return this;
            }

            public boolean hasEndUserCredsRequested() {
                return ((MethodOptions) this.instance).hasEndUserCredsRequested();
            }

            public boolean getEndUserCredsRequested() {
                return ((MethodOptions) this.instance).getEndUserCredsRequested();
            }

            public Builder setEndUserCredsRequested(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setEndUserCredsRequested(value);
                return this;
            }

            public Builder clearEndUserCredsRequested() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearEndUserCredsRequested();
                return this;
            }

            public boolean hasClientLogging() {
                return ((MethodOptions) this.instance).hasClientLogging();
            }

            public int getClientLogging() {
                return ((MethodOptions) this.instance).getClientLogging();
            }

            public Builder setClientLogging(int value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setClientLogging(value);
                return this;
            }

            public Builder clearClientLogging() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearClientLogging();
                return this;
            }

            public boolean hasServerLogging() {
                return ((MethodOptions) this.instance).hasServerLogging();
            }

            public int getServerLogging() {
                return ((MethodOptions) this.instance).getServerLogging();
            }

            public Builder setServerLogging(int value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setServerLogging(value);
                return this;
            }

            public Builder clearServerLogging() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearServerLogging();
                return this;
            }

            public boolean hasSecurityLevel() {
                return ((MethodOptions) this.instance).hasSecurityLevel();
            }

            public SecurityLevel getSecurityLevel() {
                return ((MethodOptions) this.instance).getSecurityLevel();
            }

            public Builder setSecurityLevel(SecurityLevel value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setSecurityLevel(value);
                return this;
            }

            public Builder clearSecurityLevel() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearSecurityLevel();
                return this;
            }

            public boolean hasResponseFormat() {
                return ((MethodOptions) this.instance).hasResponseFormat();
            }

            public Format getResponseFormat() {
                return ((MethodOptions) this.instance).getResponseFormat();
            }

            public Builder setResponseFormat(Format value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setResponseFormat(value);
                return this;
            }

            public Builder clearResponseFormat() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearResponseFormat();
                return this;
            }

            public boolean hasRequestFormat() {
                return ((MethodOptions) this.instance).hasRequestFormat();
            }

            public Format getRequestFormat() {
                return ((MethodOptions) this.instance).getRequestFormat();
            }

            public Builder setRequestFormat(Format value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setRequestFormat(value);
                return this;
            }

            public Builder clearRequestFormat() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearRequestFormat();
                return this;
            }

            public boolean hasStreamType() {
                return ((MethodOptions) this.instance).hasStreamType();
            }

            public String getStreamType() {
                return ((MethodOptions) this.instance).getStreamType();
            }

            public ByteString getStreamTypeBytes() {
                return ((MethodOptions) this.instance).getStreamTypeBytes();
            }

            public Builder setStreamType(String value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setStreamType(value);
                return this;
            }

            public Builder clearStreamType() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearStreamType();
                return this;
            }

            public Builder setStreamTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setStreamTypeBytes(value);
                return this;
            }

            public boolean hasSecurityLabel() {
                return ((MethodOptions) this.instance).hasSecurityLabel();
            }

            public String getSecurityLabel() {
                return ((MethodOptions) this.instance).getSecurityLabel();
            }

            public ByteString getSecurityLabelBytes() {
                return ((MethodOptions) this.instance).getSecurityLabelBytes();
            }

            public Builder setSecurityLabel(String value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setSecurityLabel(value);
                return this;
            }

            public Builder clearSecurityLabel() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearSecurityLabel();
                return this;
            }

            public Builder setSecurityLabelBytes(ByteString value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setSecurityLabelBytes(value);
                return this;
            }

            public boolean hasClientStreaming() {
                return ((MethodOptions) this.instance).hasClientStreaming();
            }

            public boolean getClientStreaming() {
                return ((MethodOptions) this.instance).getClientStreaming();
            }

            public Builder setClientStreaming(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setClientStreaming(value);
                return this;
            }

            public Builder clearClientStreaming() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearClientStreaming();
                return this;
            }

            public boolean hasServerStreaming() {
                return ((MethodOptions) this.instance).hasServerStreaming();
            }

            public boolean getServerStreaming() {
                return ((MethodOptions) this.instance).getServerStreaming();
            }

            public Builder setServerStreaming(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setServerStreaming(value);
                return this;
            }

            public Builder clearServerStreaming() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearServerStreaming();
                return this;
            }

            public boolean hasLegacyStreamType() {
                return ((MethodOptions) this.instance).hasLegacyStreamType();
            }

            public String getLegacyStreamType() {
                return ((MethodOptions) this.instance).getLegacyStreamType();
            }

            public ByteString getLegacyStreamTypeBytes() {
                return ((MethodOptions) this.instance).getLegacyStreamTypeBytes();
            }

            public Builder setLegacyStreamType(String value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyStreamType(value);
                return this;
            }

            public Builder clearLegacyStreamType() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLegacyStreamType();
                return this;
            }

            public Builder setLegacyStreamTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyStreamTypeBytes(value);
                return this;
            }

            public boolean hasLegacyResultType() {
                return ((MethodOptions) this.instance).hasLegacyResultType();
            }

            public String getLegacyResultType() {
                return ((MethodOptions) this.instance).getLegacyResultType();
            }

            public ByteString getLegacyResultTypeBytes() {
                return ((MethodOptions) this.instance).getLegacyResultTypeBytes();
            }

            public Builder setLegacyResultType(String value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyResultType(value);
                return this;
            }

            public Builder clearLegacyResultType() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLegacyResultType();
                return this;
            }

            public Builder setLegacyResultTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyResultTypeBytes(value);
                return this;
            }

            public boolean hasGoLegacyChannelApi() {
                return ((MethodOptions) this.instance).hasGoLegacyChannelApi();
            }

            public boolean getGoLegacyChannelApi() {
                return ((MethodOptions) this.instance).getGoLegacyChannelApi();
            }

            public Builder setGoLegacyChannelApi(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setGoLegacyChannelApi(value);
                return this;
            }

            public Builder clearGoLegacyChannelApi() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearGoLegacyChannelApi();
                return this;
            }

            public boolean hasLegacyClientInitialTokens() {
                return ((MethodOptions) this.instance).hasLegacyClientInitialTokens();
            }

            public long getLegacyClientInitialTokens() {
                return ((MethodOptions) this.instance).getLegacyClientInitialTokens();
            }

            public Builder setLegacyClientInitialTokens(long value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyClientInitialTokens(value);
                return this;
            }

            public Builder clearLegacyClientInitialTokens() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLegacyClientInitialTokens();
                return this;
            }

            public boolean hasLegacyServerInitialTokens() {
                return ((MethodOptions) this.instance).hasLegacyServerInitialTokens();
            }

            public long getLegacyServerInitialTokens() {
                return ((MethodOptions) this.instance).getLegacyServerInitialTokens();
            }

            public Builder setLegacyServerInitialTokens(long value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyServerInitialTokens(value);
                return this;
            }

            public Builder clearLegacyServerInitialTokens() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLegacyServerInitialTokens();
                return this;
            }

            public boolean hasLegacyTokenUnit() {
                return ((MethodOptions) this.instance).hasLegacyTokenUnit();
            }

            public TokenUnit getLegacyTokenUnit() {
                return ((MethodOptions) this.instance).getLegacyTokenUnit();
            }

            public Builder setLegacyTokenUnit(TokenUnit value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLegacyTokenUnit(value);
                return this;
            }

            public Builder clearLegacyTokenUnit() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLegacyTokenUnit();
                return this;
            }

            public boolean hasLogLevel() {
                return ((MethodOptions) this.instance).hasLogLevel();
            }

            public LogLevel getLogLevel() {
                return ((MethodOptions) this.instance).getLogLevel();
            }

            public Builder setLogLevel(LogLevel value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setLogLevel(value);
                return this;
            }

            public Builder clearLogLevel() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearLogLevel();
                return this;
            }

            public boolean hasDeprecated() {
                return ((MethodOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((MethodOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasIdempotencyLevel() {
                return ((MethodOptions) this.instance).hasIdempotencyLevel();
            }

            public IdempotencyLevel getIdempotencyLevel() {
                return ((MethodOptions) this.instance).getIdempotencyLevel();
            }

            public Builder setIdempotencyLevel(IdempotencyLevel value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setIdempotencyLevel(value);
                return this;
            }

            public Builder clearIdempotencyLevel() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearIdempotencyLevel();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((MethodOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((MethodOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((MethodOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((MethodOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((MethodOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MethodOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0018\u0000\u0001\u0007ϧ\u0018\u0000\u0001\u0001\u0007\f\u0000\b\u0000\u0001\t\u0007\u0002\n\u0007\u0003\u000b\u000f\u0005\f\u000f\u0006\r\f\u0007\u000f\f\b\u0011\f\t\u0012\b\n\u0013\b\u000b\u0014\u0007\f\u0015\u0007\r\u0016\b\u000e\u0017\b\u000f\u0018\u0002\u0011\u0019\u0002\u0012\u001a\u0007\u0004\u001b\f\u0014\u001c\f\u0013\u001d\u0007\u0010!\u0007\u0015\"\f\u0016ϧЛ", new Object[]{"bitField0_", "protocol_", Protocol.internalGetVerifier(), "deadline_", "duplicateSuppression_", "failFast_", "clientLogging_", "serverLogging_", "securityLevel_", SecurityLevel.internalGetVerifier(), "responseFormat_", Format.internalGetVerifier(), "requestFormat_", Format.internalGetVerifier(), "streamType_", "securityLabel_", "clientStreaming_", "serverStreaming_", "legacyStreamType_", "legacyResultType_", "legacyClientInitialTokens_", "legacyServerInitialTokens_", "endUserCredsRequested_", "logLevel_", LogLevel.internalGetVerifier(), "legacyTokenUnit_", TokenUnit.internalGetVerifier(), "goLegacyChannelApi_", "deprecated_", "idempotencyLevel_", IdempotencyLevel.internalGetVerifier(), "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MethodOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (MethodOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MethodOptions.class, DEFAULT_INSTANCE);
        }

        public static MethodOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MethodOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {999}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class StreamOptions extends GeneratedMessageLite.ExtendableMessage<StreamOptions, Builder> implements StreamOptionsOrBuilder {
        public static final int CLIENT_INITIAL_TOKENS_FIELD_NUMBER = 1;
        public static final int CLIENT_LOGGING_FIELD_NUMBER = 6;
        public static final int DEADLINE_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final StreamOptions DEFAULT_INSTANCE = new StreamOptions();
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static final int END_USER_CREDS_REQUESTED_FIELD_NUMBER = 10;
        public static final int FAIL_FAST_FIELD_NUMBER = 9;
        public static final int LOG_LEVEL_FIELD_NUMBER = 11;
        private static volatile Parser<StreamOptions> PARSER = null;
        public static final int SECURITY_LABEL_FIELD_NUMBER = 5;
        public static final int SECURITY_LEVEL_FIELD_NUMBER = 4;
        public static final int SERVER_INITIAL_TOKENS_FIELD_NUMBER = 2;
        public static final int SERVER_LOGGING_FIELD_NUMBER = 7;
        public static final int TOKEN_UNIT_FIELD_NUMBER = 3;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long clientInitialTokens_ = -1;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int clientLogging_ = 256;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.DOUBLE)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private double deadline_ = -1.0d;
        @ProtoField(fieldNumber = 33, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private boolean deprecated_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean endUserCredsRequested_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean failFast_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private int logLevel_ = 2;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String securityLabel_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int securityLevel_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long serverInitialTokens_ = -1;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int serverLogging_ = 256;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int tokenUnit_;
        @ProtoField(fieldNumber = 999, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private StreamOptions() {
        }

        public enum TokenUnit implements Internal.EnumLite {
            MESSAGE(0),
            BYTE(1);
            
            public static final int BYTE_VALUE = 1;
            public static final int MESSAGE_VALUE = 0;
            private static final Internal.EnumLiteMap<TokenUnit> internalValueMap = new Internal.EnumLiteMap<TokenUnit>() {
                public TokenUnit findValueByNumber(int number) {
                    return TokenUnit.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static TokenUnit forNumber(int value2) {
                if (value2 == 0) {
                    return MESSAGE;
                }
                if (value2 != 1) {
                    return null;
                }
                return BYTE;
            }

            public static Internal.EnumLiteMap<TokenUnit> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TokenUnitVerifier.INSTANCE;
            }

            private static final class TokenUnitVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TokenUnitVerifier();

                private TokenUnitVerifier() {
                }

                public boolean isInRange(int number) {
                    return TokenUnit.forNumber(number) != null;
                }
            }

            private TokenUnit(int value2) {
                this.value = value2;
            }
        }

        public boolean hasClientInitialTokens() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getClientInitialTokens() {
            return this.clientInitialTokens_;
        }

        /* access modifiers changed from: private */
        public void setClientInitialTokens(long value) {
            this.bitField0_ |= 1;
            this.clientInitialTokens_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientInitialTokens() {
            this.bitField0_ &= -2;
            this.clientInitialTokens_ = -1;
        }

        public boolean hasServerInitialTokens() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getServerInitialTokens() {
            return this.serverInitialTokens_;
        }

        /* access modifiers changed from: private */
        public void setServerInitialTokens(long value) {
            this.bitField0_ |= 2;
            this.serverInitialTokens_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerInitialTokens() {
            this.bitField0_ &= -3;
            this.serverInitialTokens_ = -1;
        }

        public boolean hasTokenUnit() {
            return (this.bitField0_ & 4) != 0;
        }

        public TokenUnit getTokenUnit() {
            TokenUnit result = TokenUnit.forNumber(this.tokenUnit_);
            return result == null ? TokenUnit.MESSAGE : result;
        }

        /* access modifiers changed from: private */
        public void setTokenUnit(TokenUnit value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.tokenUnit_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTokenUnit() {
            this.bitField0_ &= -5;
            this.tokenUnit_ = 0;
        }

        public boolean hasSecurityLevel() {
            return (this.bitField0_ & 8) != 0;
        }

        public MethodOptions.SecurityLevel getSecurityLevel() {
            MethodOptions.SecurityLevel result = MethodOptions.SecurityLevel.forNumber(this.securityLevel_);
            return result == null ? MethodOptions.SecurityLevel.NONE : result;
        }

        /* access modifiers changed from: private */
        public void setSecurityLevel(MethodOptions.SecurityLevel value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.securityLevel_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSecurityLevel() {
            this.bitField0_ &= -9;
            this.securityLevel_ = 0;
        }

        public boolean hasSecurityLabel() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getSecurityLabel() {
            return this.securityLabel_;
        }

        public ByteString getSecurityLabelBytes() {
            return ByteString.copyFromUtf8(this.securityLabel_);
        }

        /* access modifiers changed from: private */
        public void setSecurityLabel(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.securityLabel_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSecurityLabel() {
            this.bitField0_ &= -17;
            this.securityLabel_ = getDefaultInstance().getSecurityLabel();
        }

        /* access modifiers changed from: private */
        public void setSecurityLabelBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.securityLabel_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasClientLogging() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getClientLogging() {
            return this.clientLogging_;
        }

        /* access modifiers changed from: private */
        public void setClientLogging(int value) {
            this.bitField0_ |= 32;
            this.clientLogging_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientLogging() {
            this.bitField0_ &= -33;
            this.clientLogging_ = 256;
        }

        public boolean hasServerLogging() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getServerLogging() {
            return this.serverLogging_;
        }

        /* access modifiers changed from: private */
        public void setServerLogging(int value) {
            this.bitField0_ |= 64;
            this.serverLogging_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerLogging() {
            this.bitField0_ &= -65;
            this.serverLogging_ = 256;
        }

        public boolean hasDeadline() {
            return (this.bitField0_ & 128) != 0;
        }

        public double getDeadline() {
            return this.deadline_;
        }

        /* access modifiers changed from: private */
        public void setDeadline(double value) {
            this.bitField0_ |= 128;
            this.deadline_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeadline() {
            this.bitField0_ &= -129;
            this.deadline_ = -1.0d;
        }

        public boolean hasFailFast() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getFailFast() {
            return this.failFast_;
        }

        /* access modifiers changed from: private */
        public void setFailFast(boolean value) {
            this.bitField0_ |= 256;
            this.failFast_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFailFast() {
            this.bitField0_ &= -257;
            this.failFast_ = false;
        }

        public boolean hasEndUserCredsRequested() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getEndUserCredsRequested() {
            return this.endUserCredsRequested_;
        }

        /* access modifiers changed from: private */
        public void setEndUserCredsRequested(boolean value) {
            this.bitField0_ |= 512;
            this.endUserCredsRequested_ = value;
        }

        /* access modifiers changed from: private */
        public void clearEndUserCredsRequested() {
            this.bitField0_ &= -513;
            this.endUserCredsRequested_ = false;
        }

        public boolean hasLogLevel() {
            return (this.bitField0_ & 1024) != 0;
        }

        public MethodOptions.LogLevel getLogLevel() {
            MethodOptions.LogLevel result = MethodOptions.LogLevel.forNumber(this.logLevel_);
            return result == null ? MethodOptions.LogLevel.LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL : result;
        }

        /* access modifiers changed from: private */
        public void setLogLevel(MethodOptions.LogLevel value) {
            if (value != null) {
                this.bitField0_ |= 1024;
                this.logLevel_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLogLevel() {
            this.bitField0_ &= -1025;
            this.logLevel_ = 2;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 2048;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -2049;
            this.deprecated_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            if (!this.uninterpretedOption_.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(this.uninterpretedOption_);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, (UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            if (value != null) {
                ensureUninterpretedOptionIsMutable();
                this.uninterpretedOption_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add((UninterpretedOption) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, (UninterpretedOption) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static StreamOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StreamOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StreamOptions parseFrom(InputStream input) throws IOException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StreamOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (StreamOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StreamOptions parseFrom(CodedInputStream input) throws IOException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StreamOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StreamOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(StreamOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<StreamOptions, Builder> implements StreamOptionsOrBuilder {
            private Builder() {
                super(StreamOptions.DEFAULT_INSTANCE);
            }

            public boolean hasClientInitialTokens() {
                return ((StreamOptions) this.instance).hasClientInitialTokens();
            }

            public long getClientInitialTokens() {
                return ((StreamOptions) this.instance).getClientInitialTokens();
            }

            public Builder setClientInitialTokens(long value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setClientInitialTokens(value);
                return this;
            }

            public Builder clearClientInitialTokens() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearClientInitialTokens();
                return this;
            }

            public boolean hasServerInitialTokens() {
                return ((StreamOptions) this.instance).hasServerInitialTokens();
            }

            public long getServerInitialTokens() {
                return ((StreamOptions) this.instance).getServerInitialTokens();
            }

            public Builder setServerInitialTokens(long value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setServerInitialTokens(value);
                return this;
            }

            public Builder clearServerInitialTokens() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearServerInitialTokens();
                return this;
            }

            public boolean hasTokenUnit() {
                return ((StreamOptions) this.instance).hasTokenUnit();
            }

            public TokenUnit getTokenUnit() {
                return ((StreamOptions) this.instance).getTokenUnit();
            }

            public Builder setTokenUnit(TokenUnit value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setTokenUnit(value);
                return this;
            }

            public Builder clearTokenUnit() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearTokenUnit();
                return this;
            }

            public boolean hasSecurityLevel() {
                return ((StreamOptions) this.instance).hasSecurityLevel();
            }

            public MethodOptions.SecurityLevel getSecurityLevel() {
                return ((StreamOptions) this.instance).getSecurityLevel();
            }

            public Builder setSecurityLevel(MethodOptions.SecurityLevel value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setSecurityLevel(value);
                return this;
            }

            public Builder clearSecurityLevel() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearSecurityLevel();
                return this;
            }

            public boolean hasSecurityLabel() {
                return ((StreamOptions) this.instance).hasSecurityLabel();
            }

            public String getSecurityLabel() {
                return ((StreamOptions) this.instance).getSecurityLabel();
            }

            public ByteString getSecurityLabelBytes() {
                return ((StreamOptions) this.instance).getSecurityLabelBytes();
            }

            public Builder setSecurityLabel(String value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setSecurityLabel(value);
                return this;
            }

            public Builder clearSecurityLabel() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearSecurityLabel();
                return this;
            }

            public Builder setSecurityLabelBytes(ByteString value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setSecurityLabelBytes(value);
                return this;
            }

            public boolean hasClientLogging() {
                return ((StreamOptions) this.instance).hasClientLogging();
            }

            public int getClientLogging() {
                return ((StreamOptions) this.instance).getClientLogging();
            }

            public Builder setClientLogging(int value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setClientLogging(value);
                return this;
            }

            public Builder clearClientLogging() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearClientLogging();
                return this;
            }

            public boolean hasServerLogging() {
                return ((StreamOptions) this.instance).hasServerLogging();
            }

            public int getServerLogging() {
                return ((StreamOptions) this.instance).getServerLogging();
            }

            public Builder setServerLogging(int value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setServerLogging(value);
                return this;
            }

            public Builder clearServerLogging() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearServerLogging();
                return this;
            }

            public boolean hasDeadline() {
                return ((StreamOptions) this.instance).hasDeadline();
            }

            public double getDeadline() {
                return ((StreamOptions) this.instance).getDeadline();
            }

            public Builder setDeadline(double value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setDeadline(value);
                return this;
            }

            public Builder clearDeadline() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearDeadline();
                return this;
            }

            public boolean hasFailFast() {
                return ((StreamOptions) this.instance).hasFailFast();
            }

            public boolean getFailFast() {
                return ((StreamOptions) this.instance).getFailFast();
            }

            public Builder setFailFast(boolean value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setFailFast(value);
                return this;
            }

            public Builder clearFailFast() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearFailFast();
                return this;
            }

            public boolean hasEndUserCredsRequested() {
                return ((StreamOptions) this.instance).hasEndUserCredsRequested();
            }

            public boolean getEndUserCredsRequested() {
                return ((StreamOptions) this.instance).getEndUserCredsRequested();
            }

            public Builder setEndUserCredsRequested(boolean value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setEndUserCredsRequested(value);
                return this;
            }

            public Builder clearEndUserCredsRequested() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearEndUserCredsRequested();
                return this;
            }

            public boolean hasLogLevel() {
                return ((StreamOptions) this.instance).hasLogLevel();
            }

            public MethodOptions.LogLevel getLogLevel() {
                return ((StreamOptions) this.instance).getLogLevel();
            }

            public Builder setLogLevel(MethodOptions.LogLevel value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setLogLevel(value);
                return this;
            }

            public Builder clearLogLevel() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearLogLevel();
                return this;
            }

            public boolean hasDeprecated() {
                return ((StreamOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((StreamOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearDeprecated();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((StreamOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((StreamOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((StreamOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((StreamOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((StreamOptions) this.instance).setUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((StreamOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((StreamOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((StreamOptions) this.instance).addUninterpretedOption(builderForValue);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((StreamOptions) this.instance).addUninterpretedOption(index, builderForValue);
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((StreamOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((StreamOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((StreamOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new StreamOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\r\u0000\u0001\u0001ϧ\r\u0000\u0001\u0001\u0001\u0002\u0000\u0002\u0002\u0001\u0003\f\u0002\u0004\f\u0003\u0005\b\u0004\u0006\u0004\u0005\u0007\u0004\u0006\b\u0000\u0007\t\u0007\b\n\u0007\t\u000b\f\n!\u0007\u000bϧЛ", new Object[]{"bitField0_", "clientInitialTokens_", "serverInitialTokens_", "tokenUnit_", TokenUnit.internalGetVerifier(), "securityLevel_", MethodOptions.SecurityLevel.internalGetVerifier(), "securityLabel_", "clientLogging_", "serverLogging_", "deadline_", "failFast_", "endUserCredsRequested_", "logLevel_", MethodOptions.LogLevel.internalGetVerifier(), "deprecated_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<StreamOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (StreamOptions.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(StreamOptions.class, DEFAULT_INSTANCE);
        }

        public static StreamOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StreamOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {2}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class UninterpretedOption extends GeneratedMessageLite<UninterpretedOption, Builder> implements UninterpretedOptionOrBuilder {
        public static final int AGGREGATE_VALUE_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final UninterpretedOption DEFAULT_INSTANCE = new UninterpretedOption();
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 6;
        public static final int IDENTIFIER_VALUE_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 2;
        public static final int NEGATIVE_INT_VALUE_FIELD_NUMBER = 5;
        private static volatile Parser<UninterpretedOption> PARSER = null;
        public static final int POSITIVE_INT_VALUE_FIELD_NUMBER = 4;
        public static final int STRING_VALUE_FIELD_NUMBER = 7;
        @ProtoField(fieldNumber = 8, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String aggregateValue_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.DOUBLE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private double doubleValue_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String identifierValue_ = "";
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<NamePart> name_ = emptyProtobufList();
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long negativeIntValue_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.UINT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long positiveIntValue_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BYTES)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private ByteString stringValue_ = ByteString.EMPTY;

        public interface NamePartOrBuilder extends MessageLiteOrBuilder {
            boolean getIsExtension();

            String getNamePart();

            ByteString getNamePartBytes();

            boolean hasIsExtension();

            boolean hasNamePart();
        }

        private UninterpretedOption() {
        }

        @ProtoMessage(checkInitialized = {1, 2}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class NamePart extends GeneratedMessageLite<NamePart, Builder> implements NamePartOrBuilder {
            /* access modifiers changed from: private */
            public static final NamePart DEFAULT_INSTANCE = new NamePart();
            public static final int IS_EXTENSION_FIELD_NUMBER = 2;
            public static final int NAME_PART_FIELD_NUMBER = 1;
            private static volatile Parser<NamePart> PARSER;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = true, type = FieldType.BOOL)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private boolean isExtension_;
            private byte memoizedIsInitialized = 2;
            @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = true, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String namePart_ = "";

            private NamePart() {
            }

            public boolean hasNamePart() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getNamePart() {
                return this.namePart_;
            }

            public ByteString getNamePartBytes() {
                return ByteString.copyFromUtf8(this.namePart_);
            }

            /* access modifiers changed from: private */
            public void setNamePart(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.namePart_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearNamePart() {
                this.bitField0_ &= -2;
                this.namePart_ = getDefaultInstance().getNamePart();
            }

            /* access modifiers changed from: private */
            public void setNamePartBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.namePart_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public boolean hasIsExtension() {
                return (this.bitField0_ & 2) != 0;
            }

            public boolean getIsExtension() {
                return this.isExtension_;
            }

            /* access modifiers changed from: private */
            public void setIsExtension(boolean value) {
                this.bitField0_ |= 2;
                this.isExtension_ = value;
            }

            /* access modifiers changed from: private */
            public void clearIsExtension() {
                this.bitField0_ &= -3;
                this.isExtension_ = false;
            }

            public static NamePart parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(InputStream input) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static NamePart parseDelimitedFrom(InputStream input) throws IOException {
                return (NamePart) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static NamePart parseFrom(CodedInputStream input) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(NamePart prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<NamePart, Builder> implements NamePartOrBuilder {
                private Builder() {
                    super(NamePart.DEFAULT_INSTANCE);
                }

                public boolean hasNamePart() {
                    return ((NamePart) this.instance).hasNamePart();
                }

                public String getNamePart() {
                    return ((NamePart) this.instance).getNamePart();
                }

                public ByteString getNamePartBytes() {
                    return ((NamePart) this.instance).getNamePartBytes();
                }

                public Builder setNamePart(String value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setNamePart(value);
                    return this;
                }

                public Builder clearNamePart() {
                    copyOnWrite();
                    ((NamePart) this.instance).clearNamePart();
                    return this;
                }

                public Builder setNamePartBytes(ByteString value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setNamePartBytes(value);
                    return this;
                }

                public boolean hasIsExtension() {
                    return ((NamePart) this.instance).hasIsExtension();
                }

                public boolean getIsExtension() {
                    return ((NamePart) this.instance).getIsExtension();
                }

                public Builder setIsExtension(boolean value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setIsExtension(value);
                    return this;
                }

                public Builder clearIsExtension() {
                    copyOnWrite();
                    ((NamePart) this.instance).clearIsExtension();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                int i = 1;
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new NamePart();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001Ԉ\u0000\u0002ԇ\u0001", new Object[]{"bitField0_", "namePart_", "isExtension_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<NamePart> parser = PARSER;
                        if (parser == null) {
                            synchronized (NamePart.class) {
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
                        if (arg0 == null) {
                            i = 0;
                        }
                        this.memoizedIsInitialized = (byte) i;
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(NamePart.class, DEFAULT_INSTANCE);
            }

            public static NamePart getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<NamePart> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<NamePart> getNameList() {
            return this.name_;
        }

        public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
            return this.name_;
        }

        public int getNameCount() {
            return this.name_.size();
        }

        public NamePart getName(int index) {
            return this.name_.get(index);
        }

        public NamePartOrBuilder getNameOrBuilder(int index) {
            return this.name_.get(index);
        }

        private void ensureNameIsMutable() {
            if (!this.name_.isModifiable()) {
                this.name_ = GeneratedMessageLite.mutableCopy(this.name_);
            }
        }

        /* access modifiers changed from: private */
        public void setName(int index, NamePart value) {
            if (value != null) {
                ensureNameIsMutable();
                this.name_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(int index, NamePart.Builder builderForValue) {
            ensureNameIsMutable();
            this.name_.set(index, (NamePart) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addName(NamePart value) {
            if (value != null) {
                ensureNameIsMutable();
                this.name_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addName(int index, NamePart value) {
            if (value != null) {
                ensureNameIsMutable();
                this.name_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addName(NamePart.Builder builderForValue) {
            ensureNameIsMutable();
            this.name_.add((NamePart) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addName(int index, NamePart.Builder builderForValue) {
            ensureNameIsMutable();
            this.name_.add(index, (NamePart) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$UninterpretedOption$NamePart>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$UninterpretedOption$NamePart>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllName(Iterable<? extends NamePart> values) {
            ensureNameIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.name_);
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeName(int index) {
            ensureNameIsMutable();
            this.name_.remove(index);
        }

        public boolean hasIdentifierValue() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getIdentifierValue() {
            return this.identifierValue_;
        }

        public ByteString getIdentifierValueBytes() {
            return ByteString.copyFromUtf8(this.identifierValue_);
        }

        /* access modifiers changed from: private */
        public void setIdentifierValue(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.identifierValue_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIdentifierValue() {
            this.bitField0_ &= -2;
            this.identifierValue_ = getDefaultInstance().getIdentifierValue();
        }

        /* access modifiers changed from: private */
        public void setIdentifierValueBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.identifierValue_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasPositiveIntValue() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getPositiveIntValue() {
            return this.positiveIntValue_;
        }

        /* access modifiers changed from: private */
        public void setPositiveIntValue(long value) {
            this.bitField0_ |= 2;
            this.positiveIntValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPositiveIntValue() {
            this.bitField0_ &= -3;
            this.positiveIntValue_ = 0;
        }

        public boolean hasNegativeIntValue() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getNegativeIntValue() {
            return this.negativeIntValue_;
        }

        /* access modifiers changed from: private */
        public void setNegativeIntValue(long value) {
            this.bitField0_ |= 4;
            this.negativeIntValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNegativeIntValue() {
            this.bitField0_ &= -5;
            this.negativeIntValue_ = 0;
        }

        public boolean hasDoubleValue() {
            return (this.bitField0_ & 8) != 0;
        }

        public double getDoubleValue() {
            return this.doubleValue_;
        }

        /* access modifiers changed from: private */
        public void setDoubleValue(double value) {
            this.bitField0_ |= 8;
            this.doubleValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDoubleValue() {
            this.bitField0_ &= -9;
            this.doubleValue_ = 0.0d;
        }

        public boolean hasStringValue() {
            return (this.bitField0_ & 16) != 0;
        }

        public ByteString getStringValue() {
            return this.stringValue_;
        }

        /* access modifiers changed from: private */
        public void setStringValue(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.stringValue_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStringValue() {
            this.bitField0_ &= -17;
            this.stringValue_ = getDefaultInstance().getStringValue();
        }

        public boolean hasAggregateValue() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getAggregateValue() {
            return this.aggregateValue_;
        }

        public ByteString getAggregateValueBytes() {
            return ByteString.copyFromUtf8(this.aggregateValue_);
        }

        /* access modifiers changed from: private */
        public void setAggregateValue(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.aggregateValue_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAggregateValue() {
            this.bitField0_ &= -33;
            this.aggregateValue_ = getDefaultInstance().getAggregateValue();
        }

        /* access modifiers changed from: private */
        public void setAggregateValueBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.aggregateValue_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static UninterpretedOption parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(InputStream input) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input) throws IOException {
            return (UninterpretedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(UninterpretedOption prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<UninterpretedOption, Builder> implements UninterpretedOptionOrBuilder {
            private Builder() {
                super(UninterpretedOption.DEFAULT_INSTANCE);
            }

            public List<NamePart> getNameList() {
                return Collections.unmodifiableList(((UninterpretedOption) this.instance).getNameList());
            }

            public int getNameCount() {
                return ((UninterpretedOption) this.instance).getNameCount();
            }

            public NamePart getName(int index) {
                return ((UninterpretedOption) this.instance).getName(index);
            }

            public Builder setName(int index, NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setName(index, value);
                return this;
            }

            public Builder setName(int index, NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setName(index, builderForValue);
                return this;
            }

            public Builder addName(NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(value);
                return this;
            }

            public Builder addName(int index, NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(index, value);
                return this;
            }

            public Builder addName(NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(builderForValue);
                return this;
            }

            public Builder addName(int index, NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(index, builderForValue);
                return this;
            }

            public Builder addAllName(Iterable<? extends NamePart> values) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addAllName(values);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearName();
                return this;
            }

            public Builder removeName(int index) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).removeName(index);
                return this;
            }

            public boolean hasIdentifierValue() {
                return ((UninterpretedOption) this.instance).hasIdentifierValue();
            }

            public String getIdentifierValue() {
                return ((UninterpretedOption) this.instance).getIdentifierValue();
            }

            public ByteString getIdentifierValueBytes() {
                return ((UninterpretedOption) this.instance).getIdentifierValueBytes();
            }

            public Builder setIdentifierValue(String value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setIdentifierValue(value);
                return this;
            }

            public Builder clearIdentifierValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearIdentifierValue();
                return this;
            }

            public Builder setIdentifierValueBytes(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setIdentifierValueBytes(value);
                return this;
            }

            public boolean hasPositiveIntValue() {
                return ((UninterpretedOption) this.instance).hasPositiveIntValue();
            }

            public long getPositiveIntValue() {
                return ((UninterpretedOption) this.instance).getPositiveIntValue();
            }

            public Builder setPositiveIntValue(long value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setPositiveIntValue(value);
                return this;
            }

            public Builder clearPositiveIntValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearPositiveIntValue();
                return this;
            }

            public boolean hasNegativeIntValue() {
                return ((UninterpretedOption) this.instance).hasNegativeIntValue();
            }

            public long getNegativeIntValue() {
                return ((UninterpretedOption) this.instance).getNegativeIntValue();
            }

            public Builder setNegativeIntValue(long value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setNegativeIntValue(value);
                return this;
            }

            public Builder clearNegativeIntValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearNegativeIntValue();
                return this;
            }

            public boolean hasDoubleValue() {
                return ((UninterpretedOption) this.instance).hasDoubleValue();
            }

            public double getDoubleValue() {
                return ((UninterpretedOption) this.instance).getDoubleValue();
            }

            public Builder setDoubleValue(double value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setDoubleValue(value);
                return this;
            }

            public Builder clearDoubleValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearDoubleValue();
                return this;
            }

            public boolean hasStringValue() {
                return ((UninterpretedOption) this.instance).hasStringValue();
            }

            public ByteString getStringValue() {
                return ((UninterpretedOption) this.instance).getStringValue();
            }

            public Builder setStringValue(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setStringValue(value);
                return this;
            }

            public Builder clearStringValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearStringValue();
                return this;
            }

            public boolean hasAggregateValue() {
                return ((UninterpretedOption) this.instance).hasAggregateValue();
            }

            public String getAggregateValue() {
                return ((UninterpretedOption) this.instance).getAggregateValue();
            }

            public ByteString getAggregateValueBytes() {
                return ((UninterpretedOption) this.instance).getAggregateValueBytes();
            }

            public Builder setAggregateValue(String value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setAggregateValue(value);
                return this;
            }

            public Builder clearAggregateValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearAggregateValue();
                return this;
            }

            public Builder setAggregateValueBytes(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setAggregateValueBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new UninterpretedOption();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0002\b\u0007\u0000\u0001\u0001\u0002Л\u0003\b\u0000\u0004\u0003\u0001\u0005\u0002\u0002\u0006\u0000\u0003\u0007\n\u0004\b\b\u0005", new Object[]{"bitField0_", "name_", NamePart.class, "identifierValue_", "positiveIntValue_", "negativeIntValue_", "doubleValue_", "stringValue_", "aggregateValue_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<UninterpretedOption> parser = PARSER;
                    if (parser == null) {
                        synchronized (UninterpretedOption.class) {
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
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(UninterpretedOption.class, DEFAULT_INSTANCE);
        }

        public static UninterpretedOption getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UninterpretedOption> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SourceCodeInfo extends GeneratedMessageLite<SourceCodeInfo, Builder> implements SourceCodeInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final SourceCodeInfo DEFAULT_INSTANCE = new SourceCodeInfo();
        public static final int LOCATION_FIELD_NUMBER = 1;
        private static volatile Parser<SourceCodeInfo> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Location> location_ = emptyProtobufList();

        public interface LocationOrBuilder extends MessageLiteOrBuilder {
            String getLeadingComments();

            ByteString getLeadingCommentsBytes();

            String getLeadingDetachedComments(int i);

            ByteString getLeadingDetachedCommentsBytes(int i);

            int getLeadingDetachedCommentsCount();

            List<String> getLeadingDetachedCommentsList();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            int getSpan(int i);

            int getSpanCount();

            List<Integer> getSpanList();

            String getTrailingComments();

            ByteString getTrailingCommentsBytes();

            boolean hasLeadingComments();

            boolean hasTrailingComments();
        }

        private SourceCodeInfo() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Location extends GeneratedMessageLite<Location, Builder> implements LocationOrBuilder {
            /* access modifiers changed from: private */
            public static final Location DEFAULT_INSTANCE = new Location();
            public static final int LEADING_COMMENTS_FIELD_NUMBER = 3;
            public static final int LEADING_DETACHED_COMMENTS_FIELD_NUMBER = 6;
            private static volatile Parser<Location> PARSER = null;
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SPAN_FIELD_NUMBER = 2;
            public static final int TRAILING_COMMENTS_FIELD_NUMBER = 4;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String leadingComments_ = "";
            @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
            private Internal.ProtobufList<String> leadingDetachedComments_ = GeneratedMessageLite.emptyProtobufList();
            private int pathMemoizedSerializedSize = -1;
            @ProtoField(fieldNumber = 1, type = FieldType.INT32_LIST_PACKED)
            private Internal.IntList path_ = emptyIntList();
            private int spanMemoizedSerializedSize = -1;
            @ProtoField(fieldNumber = 2, type = FieldType.INT32_LIST_PACKED)
            private Internal.IntList span_ = emptyIntList();
            @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private String trailingComments_ = "";

            private Location() {
            }

            public List<Integer> getPathList() {
                return this.path_;
            }

            public int getPathCount() {
                return this.path_.size();
            }

            public int getPath(int index) {
                return this.path_.getInt(index);
            }

            private void ensurePathIsMutable() {
                if (!this.path_.isModifiable()) {
                    this.path_ = GeneratedMessageLite.mutableCopy(this.path_);
                }
            }

            /* access modifiers changed from: private */
            public void setPath(int index, int value) {
                ensurePathIsMutable();
                this.path_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addPath(int value) {
                ensurePathIsMutable();
                this.path_.addInt(value);
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
             arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
             candidates:
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
            /* access modifiers changed from: private */
            public void addAllPath(Iterable<? extends Integer> values) {
                ensurePathIsMutable();
                AbstractMessageLite.addAll((Iterable) values, (List) this.path_);
            }

            /* access modifiers changed from: private */
            public void clearPath() {
                this.path_ = emptyIntList();
            }

            public List<Integer> getSpanList() {
                return this.span_;
            }

            public int getSpanCount() {
                return this.span_.size();
            }

            public int getSpan(int index) {
                return this.span_.getInt(index);
            }

            private void ensureSpanIsMutable() {
                if (!this.span_.isModifiable()) {
                    this.span_ = GeneratedMessageLite.mutableCopy(this.span_);
                }
            }

            /* access modifiers changed from: private */
            public void setSpan(int index, int value) {
                ensureSpanIsMutable();
                this.span_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addSpan(int value) {
                ensureSpanIsMutable();
                this.span_.addInt(value);
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
             arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
             candidates:
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
            /* access modifiers changed from: private */
            public void addAllSpan(Iterable<? extends Integer> values) {
                ensureSpanIsMutable();
                AbstractMessageLite.addAll((Iterable) values, (List) this.span_);
            }

            /* access modifiers changed from: private */
            public void clearSpan() {
                this.span_ = emptyIntList();
            }

            public boolean hasLeadingComments() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getLeadingComments() {
                return this.leadingComments_;
            }

            public ByteString getLeadingCommentsBytes() {
                return ByteString.copyFromUtf8(this.leadingComments_);
            }

            /* access modifiers changed from: private */
            public void setLeadingComments(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.leadingComments_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearLeadingComments() {
                this.bitField0_ &= -2;
                this.leadingComments_ = getDefaultInstance().getLeadingComments();
            }

            /* access modifiers changed from: private */
            public void setLeadingCommentsBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.leadingComments_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public boolean hasTrailingComments() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getTrailingComments() {
                return this.trailingComments_;
            }

            public ByteString getTrailingCommentsBytes() {
                return ByteString.copyFromUtf8(this.trailingComments_);
            }

            /* access modifiers changed from: private */
            public void setTrailingComments(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.trailingComments_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearTrailingComments() {
                this.bitField0_ &= -3;
                this.trailingComments_ = getDefaultInstance().getTrailingComments();
            }

            /* access modifiers changed from: private */
            public void setTrailingCommentsBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.trailingComments_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public List<String> getLeadingDetachedCommentsList() {
                return this.leadingDetachedComments_;
            }

            public int getLeadingDetachedCommentsCount() {
                return this.leadingDetachedComments_.size();
            }

            public String getLeadingDetachedComments(int index) {
                return this.leadingDetachedComments_.get(index);
            }

            public ByteString getLeadingDetachedCommentsBytes(int index) {
                return ByteString.copyFromUtf8(this.leadingDetachedComments_.get(index));
            }

            private void ensureLeadingDetachedCommentsIsMutable() {
                if (!this.leadingDetachedComments_.isModifiable()) {
                    this.leadingDetachedComments_ = GeneratedMessageLite.mutableCopy(this.leadingDetachedComments_);
                }
            }

            /* access modifiers changed from: private */
            public void setLeadingDetachedComments(int index, String value) {
                if (value != null) {
                    ensureLeadingDetachedCommentsIsMutable();
                    this.leadingDetachedComments_.set(index, value);
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void addLeadingDetachedComments(String value) {
                if (value != null) {
                    ensureLeadingDetachedCommentsIsMutable();
                    this.leadingDetachedComments_.add(value);
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
            public void addAllLeadingDetachedComments(Iterable<String> values) {
                ensureLeadingDetachedCommentsIsMutable();
                AbstractMessageLite.addAll((Iterable) values, (List) this.leadingDetachedComments_);
            }

            /* access modifiers changed from: private */
            public void clearLeadingDetachedComments() {
                this.leadingDetachedComments_ = GeneratedMessageLite.emptyProtobufList();
            }

            /* access modifiers changed from: private */
            public void addLeadingDetachedCommentsBytes(ByteString value) {
                if (value != null) {
                    ensureLeadingDetachedCommentsIsMutable();
                    this.leadingDetachedComments_.add(value.toStringUtf8());
                    return;
                }
                throw new NullPointerException();
            }

            public static Location parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(InputStream input) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Location parseDelimitedFrom(InputStream input) throws IOException {
                return (Location) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Location parseFrom(CodedInputStream input) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Location prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Location, Builder> implements LocationOrBuilder {
                private Builder() {
                    super(Location.DEFAULT_INSTANCE);
                }

                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(((Location) this.instance).getPathList());
                }

                public int getPathCount() {
                    return ((Location) this.instance).getPathCount();
                }

                public int getPath(int index) {
                    return ((Location) this.instance).getPath(index);
                }

                public Builder setPath(int index, int value) {
                    copyOnWrite();
                    ((Location) this.instance).setPath(index, value);
                    return this;
                }

                public Builder addPath(int value) {
                    copyOnWrite();
                    ((Location) this.instance).addPath(value);
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllPath(values);
                    return this;
                }

                public Builder clearPath() {
                    copyOnWrite();
                    ((Location) this.instance).clearPath();
                    return this;
                }

                public List<Integer> getSpanList() {
                    return Collections.unmodifiableList(((Location) this.instance).getSpanList());
                }

                public int getSpanCount() {
                    return ((Location) this.instance).getSpanCount();
                }

                public int getSpan(int index) {
                    return ((Location) this.instance).getSpan(index);
                }

                public Builder setSpan(int index, int value) {
                    copyOnWrite();
                    ((Location) this.instance).setSpan(index, value);
                    return this;
                }

                public Builder addSpan(int value) {
                    copyOnWrite();
                    ((Location) this.instance).addSpan(value);
                    return this;
                }

                public Builder addAllSpan(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllSpan(values);
                    return this;
                }

                public Builder clearSpan() {
                    copyOnWrite();
                    ((Location) this.instance).clearSpan();
                    return this;
                }

                public boolean hasLeadingComments() {
                    return ((Location) this.instance).hasLeadingComments();
                }

                public String getLeadingComments() {
                    return ((Location) this.instance).getLeadingComments();
                }

                public ByteString getLeadingCommentsBytes() {
                    return ((Location) this.instance).getLeadingCommentsBytes();
                }

                public Builder setLeadingComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingComments(value);
                    return this;
                }

                public Builder clearLeadingComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearLeadingComments();
                    return this;
                }

                public Builder setLeadingCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingCommentsBytes(value);
                    return this;
                }

                public boolean hasTrailingComments() {
                    return ((Location) this.instance).hasTrailingComments();
                }

                public String getTrailingComments() {
                    return ((Location) this.instance).getTrailingComments();
                }

                public ByteString getTrailingCommentsBytes() {
                    return ((Location) this.instance).getTrailingCommentsBytes();
                }

                public Builder setTrailingComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).setTrailingComments(value);
                    return this;
                }

                public Builder clearTrailingComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearTrailingComments();
                    return this;
                }

                public Builder setTrailingCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).setTrailingCommentsBytes(value);
                    return this;
                }

                public List<String> getLeadingDetachedCommentsList() {
                    return Collections.unmodifiableList(((Location) this.instance).getLeadingDetachedCommentsList());
                }

                public int getLeadingDetachedCommentsCount() {
                    return ((Location) this.instance).getLeadingDetachedCommentsCount();
                }

                public String getLeadingDetachedComments(int index) {
                    return ((Location) this.instance).getLeadingDetachedComments(index);
                }

                public ByteString getLeadingDetachedCommentsBytes(int index) {
                    return ((Location) this.instance).getLeadingDetachedCommentsBytes(index);
                }

                public Builder setLeadingDetachedComments(int index, String value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingDetachedComments(index, value);
                    return this;
                }

                public Builder addLeadingDetachedComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).addLeadingDetachedComments(value);
                    return this;
                }

                public Builder addAllLeadingDetachedComments(Iterable<String> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllLeadingDetachedComments(values);
                    return this;
                }

                public Builder clearLeadingDetachedComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearLeadingDetachedComments();
                    return this;
                }

                public Builder addLeadingDetachedCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).addLeadingDetachedCommentsBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Location();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0003\u0000\u0001'\u0002'\u0003\b\u0000\u0004\b\u0001\u0006\u001a", new Object[]{"bitField0_", "path_", "span_", "leadingComments_", "trailingComments_", "leadingDetachedComments_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Location> parser = PARSER;
                        if (parser == null) {
                            synchronized (Location.class) {
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
                GeneratedMessageLite.registerDefaultInstance(Location.class, DEFAULT_INSTANCE);
            }

            public static Location getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Location> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<Location> getLocationList() {
            return this.location_;
        }

        public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
            return this.location_;
        }

        public int getLocationCount() {
            return this.location_.size();
        }

        public Location getLocation(int index) {
            return this.location_.get(index);
        }

        public LocationOrBuilder getLocationOrBuilder(int index) {
            return this.location_.get(index);
        }

        private void ensureLocationIsMutable() {
            if (!this.location_.isModifiable()) {
                this.location_ = GeneratedMessageLite.mutableCopy(this.location_);
            }
        }

        /* access modifiers changed from: private */
        public void setLocation(int index, Location value) {
            if (value != null) {
                ensureLocationIsMutable();
                this.location_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLocation(int index, Location.Builder builderForValue) {
            ensureLocationIsMutable();
            this.location_.set(index, (Location) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addLocation(Location value) {
            if (value != null) {
                ensureLocationIsMutable();
                this.location_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addLocation(int index, Location value) {
            if (value != null) {
                ensureLocationIsMutable();
                this.location_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addLocation(Location.Builder builderForValue) {
            ensureLocationIsMutable();
            this.location_.add((Location) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addLocation(int index, Location.Builder builderForValue) {
            ensureLocationIsMutable();
            this.location_.add(index, (Location) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$SourceCodeInfo$Location>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$SourceCodeInfo$Location>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllLocation(Iterable<? extends Location> values) {
            ensureLocationIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.location_);
        }

        /* access modifiers changed from: private */
        public void clearLocation() {
            this.location_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeLocation(int index) {
            ensureLocationIsMutable();
            this.location_.remove(index);
        }

        public static SourceCodeInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SourceCodeInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SourceCodeInfo, Builder> implements SourceCodeInfoOrBuilder {
            private Builder() {
                super(SourceCodeInfo.DEFAULT_INSTANCE);
            }

            public List<Location> getLocationList() {
                return Collections.unmodifiableList(((SourceCodeInfo) this.instance).getLocationList());
            }

            public int getLocationCount() {
                return ((SourceCodeInfo) this.instance).getLocationCount();
            }

            public Location getLocation(int index) {
                return ((SourceCodeInfo) this.instance).getLocation(index);
            }

            public Builder setLocation(int index, Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).setLocation(index, value);
                return this;
            }

            public Builder setLocation(int index, Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).setLocation(index, builderForValue);
                return this;
            }

            public Builder addLocation(Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(value);
                return this;
            }

            public Builder addLocation(int index, Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(index, value);
                return this;
            }

            public Builder addLocation(Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(builderForValue);
                return this;
            }

            public Builder addLocation(int index, Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(index, builderForValue);
                return this;
            }

            public Builder addAllLocation(Iterable<? extends Location> values) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addAllLocation(values);
                return this;
            }

            public Builder clearLocation() {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).clearLocation();
                return this;
            }

            public Builder removeLocation(int index) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).removeLocation(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SourceCodeInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"location_", Location.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SourceCodeInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (SourceCodeInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(SourceCodeInfo.class, DEFAULT_INSTANCE);
        }

        public static SourceCodeInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SourceCodeInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GeneratedCodeInfo extends GeneratedMessageLite<GeneratedCodeInfo, Builder> implements GeneratedCodeInfoOrBuilder {
        public static final int ANNOTATION_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final GeneratedCodeInfo DEFAULT_INSTANCE = new GeneratedCodeInfo();
        private static volatile Parser<GeneratedCodeInfo> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Annotation> annotation_ = emptyProtobufList();

        public interface AnnotationOrBuilder extends MessageLiteOrBuilder {
            int getBegin();

            int getEnd();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            String getSourceFile();

            ByteString getSourceFileBytes();

            boolean hasBegin();

            boolean hasEnd();

            boolean hasSourceFile();
        }

        private GeneratedCodeInfo() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Annotation extends GeneratedMessageLite<Annotation, Builder> implements AnnotationOrBuilder {
            public static final int BEGIN_FIELD_NUMBER = 3;
            /* access modifiers changed from: private */
            public static final Annotation DEFAULT_INSTANCE = new Annotation();
            public static final int END_FIELD_NUMBER = 4;
            private static volatile Parser<Annotation> PARSER = null;
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SOURCE_FILE_FIELD_NUMBER = 2;
            @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int begin_;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private int end_;
            private int pathMemoizedSerializedSize = -1;
            @ProtoField(fieldNumber = 1, type = FieldType.INT32_LIST_PACKED)
            private Internal.IntList path_ = emptyIntList();
            @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String sourceFile_ = "";

            private Annotation() {
            }

            public List<Integer> getPathList() {
                return this.path_;
            }

            public int getPathCount() {
                return this.path_.size();
            }

            public int getPath(int index) {
                return this.path_.getInt(index);
            }

            private void ensurePathIsMutable() {
                if (!this.path_.isModifiable()) {
                    this.path_ = GeneratedMessageLite.mutableCopy(this.path_);
                }
            }

            /* access modifiers changed from: private */
            public void setPath(int index, int value) {
                ensurePathIsMutable();
                this.path_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addPath(int value) {
                ensurePathIsMutable();
                this.path_.addInt(value);
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
             arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
             candidates:
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
            /* access modifiers changed from: private */
            public void addAllPath(Iterable<? extends Integer> values) {
                ensurePathIsMutable();
                AbstractMessageLite.addAll((Iterable) values, (List) this.path_);
            }

            /* access modifiers changed from: private */
            public void clearPath() {
                this.path_ = emptyIntList();
            }

            public boolean hasSourceFile() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getSourceFile() {
                return this.sourceFile_;
            }

            public ByteString getSourceFileBytes() {
                return ByteString.copyFromUtf8(this.sourceFile_);
            }

            /* access modifiers changed from: private */
            public void setSourceFile(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.sourceFile_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearSourceFile() {
                this.bitField0_ &= -2;
                this.sourceFile_ = getDefaultInstance().getSourceFile();
            }

            /* access modifiers changed from: private */
            public void setSourceFileBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.sourceFile_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public boolean hasBegin() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getBegin() {
                return this.begin_;
            }

            /* access modifiers changed from: private */
            public void setBegin(int value) {
                this.bitField0_ |= 2;
                this.begin_ = value;
            }

            /* access modifiers changed from: private */
            public void clearBegin() {
                this.bitField0_ &= -3;
                this.begin_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 4) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 4;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -5;
                this.end_ = 0;
            }

            public static Annotation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(InputStream input) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Annotation parseDelimitedFrom(InputStream input) throws IOException {
                return (Annotation) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Annotation parseFrom(CodedInputStream input) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Annotation prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Annotation, Builder> implements AnnotationOrBuilder {
                private Builder() {
                    super(Annotation.DEFAULT_INSTANCE);
                }

                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(((Annotation) this.instance).getPathList());
                }

                public int getPathCount() {
                    return ((Annotation) this.instance).getPathCount();
                }

                public int getPath(int index) {
                    return ((Annotation) this.instance).getPath(index);
                }

                public Builder setPath(int index, int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setPath(index, value);
                    return this;
                }

                public Builder addPath(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).addPath(value);
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Annotation) this.instance).addAllPath(values);
                    return this;
                }

                public Builder clearPath() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearPath();
                    return this;
                }

                public boolean hasSourceFile() {
                    return ((Annotation) this.instance).hasSourceFile();
                }

                public String getSourceFile() {
                    return ((Annotation) this.instance).getSourceFile();
                }

                public ByteString getSourceFileBytes() {
                    return ((Annotation) this.instance).getSourceFileBytes();
                }

                public Builder setSourceFile(String value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setSourceFile(value);
                    return this;
                }

                public Builder clearSourceFile() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearSourceFile();
                    return this;
                }

                public Builder setSourceFileBytes(ByteString value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setSourceFileBytes(value);
                    return this;
                }

                public boolean hasBegin() {
                    return ((Annotation) this.instance).hasBegin();
                }

                public int getBegin() {
                    return ((Annotation) this.instance).getBegin();
                }

                public Builder setBegin(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setBegin(value);
                    return this;
                }

                public Builder clearBegin() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearBegin();
                    return this;
                }

                public boolean hasEnd() {
                    return ((Annotation) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((Annotation) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearEnd();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Annotation();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001'\u0002\b\u0000\u0003\u0004\u0001\u0004\u0004\u0002", new Object[]{"bitField0_", "path_", "sourceFile_", "begin_", "end_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Annotation> parser = PARSER;
                        if (parser == null) {
                            synchronized (Annotation.class) {
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
                GeneratedMessageLite.registerDefaultInstance(Annotation.class, DEFAULT_INSTANCE);
            }

            public static Annotation getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Annotation> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public List<? extends AnnotationOrBuilder> getAnnotationOrBuilderList() {
            return this.annotation_;
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public Annotation getAnnotation(int index) {
            return this.annotation_.get(index);
        }

        public AnnotationOrBuilder getAnnotationOrBuilder(int index) {
            return this.annotation_.get(index);
        }

        private void ensureAnnotationIsMutable() {
            if (!this.annotation_.isModifiable()) {
                this.annotation_ = GeneratedMessageLite.mutableCopy(this.annotation_);
            }
        }

        /* access modifiers changed from: private */
        public void setAnnotation(int index, Annotation value) {
            if (value != null) {
                ensureAnnotationIsMutable();
                this.annotation_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAnnotation(int index, Annotation.Builder builderForValue) {
            ensureAnnotationIsMutable();
            this.annotation_.set(index, (Annotation) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addAnnotation(Annotation value) {
            if (value != null) {
                ensureAnnotationIsMutable();
                this.annotation_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAnnotation(int index, Annotation value) {
            if (value != null) {
                ensureAnnotationIsMutable();
                this.annotation_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAnnotation(Annotation.Builder builderForValue) {
            ensureAnnotationIsMutable();
            this.annotation_.add((Annotation) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addAnnotation(int index, Annotation.Builder builderForValue) {
            ensureAnnotationIsMutable();
            this.annotation_.add(index, (Annotation) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protobuf.DescriptorProtos$GeneratedCodeInfo$Annotation>, com.google.protobuf.Internal$ProtobufList<com.google.protobuf.DescriptorProtos$GeneratedCodeInfo$Annotation>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllAnnotation(Iterable<? extends Annotation> values) {
            ensureAnnotationIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.annotation_);
        }

        /* access modifiers changed from: private */
        public void clearAnnotation() {
            this.annotation_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeAnnotation(int index) {
            ensureAnnotationIsMutable();
            this.annotation_.remove(index);
        }

        public static GeneratedCodeInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(InputStream input) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GeneratedCodeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (GeneratedCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(CodedInputStream input) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GeneratedCodeInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GeneratedCodeInfo, Builder> implements GeneratedCodeInfoOrBuilder {
            private Builder() {
                super(GeneratedCodeInfo.DEFAULT_INSTANCE);
            }

            public List<Annotation> getAnnotationList() {
                return Collections.unmodifiableList(((GeneratedCodeInfo) this.instance).getAnnotationList());
            }

            public int getAnnotationCount() {
                return ((GeneratedCodeInfo) this.instance).getAnnotationCount();
            }

            public Annotation getAnnotation(int index) {
                return ((GeneratedCodeInfo) this.instance).getAnnotation(index);
            }

            public Builder setAnnotation(int index, Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).setAnnotation(index, value);
                return this;
            }

            public Builder setAnnotation(int index, Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).setAnnotation(index, builderForValue);
                return this;
            }

            public Builder addAnnotation(Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(value);
                return this;
            }

            public Builder addAnnotation(int index, Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(index, value);
                return this;
            }

            public Builder addAnnotation(Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(builderForValue);
                return this;
            }

            public Builder addAnnotation(int index, Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(index, builderForValue);
                return this;
            }

            public Builder addAllAnnotation(Iterable<? extends Annotation> values) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAllAnnotation(values);
                return this;
            }

            public Builder clearAnnotation() {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).clearAnnotation();
                return this;
            }

            public Builder removeAnnotation(int index) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).removeAnnotation(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GeneratedCodeInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"annotation_", Annotation.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GeneratedCodeInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (GeneratedCodeInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(GeneratedCodeInfo.class, DEFAULT_INSTANCE);
        }

        public static GeneratedCodeInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GeneratedCodeInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
