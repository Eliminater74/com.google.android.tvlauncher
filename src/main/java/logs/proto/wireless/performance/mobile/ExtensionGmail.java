package logs.proto.wireless.performance.mobile;

import com.android.mail.perf.PrimesMetricExtensionEnums;
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

public final class ExtensionGmail {

    public interface ExchangeInfoOrBuilder extends MessageLiteOrBuilder {
        ExchangeInfo.DpcInfo getDpcInfo();

        ExchangeInfo.ExchangeAccountInfo getExchangeAccountInfo(int i);

        int getExchangeAccountInfoCount();

        List<ExchangeInfo.ExchangeAccountInfo> getExchangeAccountInfoList();

        long getExchangeInfoId();

        boolean hasDpcInfo();

        boolean hasExchangeInfoId();
    }

    public interface GmailExtensionOrBuilder extends MessageLiteOrBuilder {
        PrimesMetricExtensionEnums.AccountType getAccountType();

        @Deprecated
        int getActiveBteIds(int i);

        @Deprecated
        int getActiveBteIdsCount();

        @Deprecated
        List<Integer> getActiveBteIdsList();

        PrimesMetricExtensionEnums.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<PrimesMetricExtensionEnums.Annotation> getAnnotationList();

        PrimesMetricExtensionEnums.CancellationReason getCancellationReason();

        int getClassLoadLatency();

        PrimesMetricExtensionEnums.DataLayer getDataLayer();

        ExchangeInfo getExchangeInfo();

        PrimesMetricExtensionEnums.FolderType getFolderType();

        boolean getIsGooglerAccount();

        int getNumAccounts();

        OpenConversationAnnotations getOpenConversationAnnotations();

        @Deprecated
        boolean getRecyclerViewEnabled();

        String getWebviewVersion();

        ByteString getWebviewVersionBytes();

        boolean hasAccountType();

        boolean hasCancellationReason();

        boolean hasClassLoadLatency();

        boolean hasDataLayer();

        boolean hasExchangeInfo();

        boolean hasFolderType();

        boolean hasIsGooglerAccount();

        boolean hasNumAccounts();

        boolean hasOpenConversationAnnotations();

        @Deprecated
        boolean hasRecyclerViewEnabled();

        boolean hasWebviewVersion();
    }

    public interface OpenConversationAnnotationsOrBuilder extends MessageLiteOrBuilder {
        PrimesMetricExtensionEnums.ContentSource getContentSource();

        int getConversationIndex();

        boolean getDeferWebviewImageLoadUntilCvRevealed();

        boolean getHasInlineAttachments();

        boolean getHasLoadedDynamicMail();

        boolean getIsColdOpen();

        int getNumMessages();

        boolean getUseDomContentLoadedSignal();

        int getWebviewDumpHash();

        String getWebviewThreadDump();

        ByteString getWebviewThreadDumpBytes();

        boolean hasContentSource();

        boolean hasConversationIndex();

        boolean hasDeferWebviewImageLoadUntilCvRevealed();

        boolean hasHasInlineAttachments();

        boolean hasHasLoadedDynamicMail();

        boolean hasIsColdOpen();

        boolean hasNumMessages();

        boolean hasUseDomContentLoadedSignal();

        boolean hasWebviewDumpHash();

        boolean hasWebviewThreadDump();
    }

    private ExtensionGmail() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GmailExtension extends GeneratedMessageLite<GmailExtension, Builder> implements GmailExtensionOrBuilder {
        public static final int ACCOUNT_TYPE_FIELD_NUMBER = 1;
        public static final int ACTIVE_BTE_IDS_FIELD_NUMBER = 13;
        public static final int ANNOTATION_FIELD_NUMBER = 12;
        public static final int CANCELLATION_REASON_FIELD_NUMBER = 5;
        public static final int CLASS_LOAD_LATENCY_FIELD_NUMBER = 3;
        public static final int DATA_LAYER_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final GmailExtension DEFAULT_INSTANCE = new GmailExtension();
        public static final int EXCHANGE_INFO_FIELD_NUMBER = 7;
        public static final int FOLDER_TYPE_FIELD_NUMBER = 2;
        public static final int IS_GOOGLER_ACCOUNT_FIELD_NUMBER = 11;
        public static final int NUM_ACCOUNTS_FIELD_NUMBER = 10;
        public static final int OPEN_CONVERSATION_ANNOTATIONS_FIELD_NUMBER = 4;
        private static volatile Parser<GmailExtension> PARSER = null;
        public static final int RECYCLER_VIEW_ENABLED_FIELD_NUMBER = 9;
        public static final int WEBVIEW_VERSION_FIELD_NUMBER = 6;
        private static final Internal.ListAdapter.Converter<Integer, PrimesMetricExtensionEnums.Annotation> annotation_converter_ = new Internal.ListAdapter.Converter<Integer, PrimesMetricExtensionEnums.Annotation>() {
            public PrimesMetricExtensionEnums.Annotation convert(Integer from) {
                PrimesMetricExtensionEnums.Annotation result = PrimesMetricExtensionEnums.Annotation.forNumber(from.intValue());
                return result == null ? PrimesMetricExtensionEnums.Annotation.UNKNOWN_ANNOTATION : result;
            }
        };
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int accountType_;
        @ProtoField(fieldNumber = 13, type = FieldType.INT32_LIST)
        private Internal.IntList activeBteIds_ = emptyIntList();
        @ProtoField(fieldNumber = 12, type = FieldType.ENUM_LIST)
        private Internal.IntList annotation_ = emptyIntList();
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int cancellationReason_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int classLoadLatency_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int dataLayer_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private ExchangeInfo exchangeInfo_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int folderType_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean isGooglerAccount_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private int numAccounts_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private OpenConversationAnnotations openConversationAnnotations_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean recyclerViewEnabled_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String webviewVersion_ = "";

        private GmailExtension() {
        }

        public boolean hasAccountType() {
            return (this.bitField0_ & 1) != 0;
        }

        public PrimesMetricExtensionEnums.AccountType getAccountType() {
            PrimesMetricExtensionEnums.AccountType result = PrimesMetricExtensionEnums.AccountType.forNumber(this.accountType_);
            return result == null ? PrimesMetricExtensionEnums.AccountType.UNKNOWN_ACCOUNT_TYPE : result;
        }

        /* access modifiers changed from: private */
        public void setAccountType(PrimesMetricExtensionEnums.AccountType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.accountType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAccountType() {
            this.bitField0_ &= -2;
            this.accountType_ = 0;
        }

        public boolean hasFolderType() {
            return (this.bitField0_ & 2) != 0;
        }

        public PrimesMetricExtensionEnums.FolderType getFolderType() {
            PrimesMetricExtensionEnums.FolderType result = PrimesMetricExtensionEnums.FolderType.forNumber(this.folderType_);
            return result == null ? PrimesMetricExtensionEnums.FolderType.UNKNOWN_FOLDER_TYPE : result;
        }

        /* access modifiers changed from: private */
        public void setFolderType(PrimesMetricExtensionEnums.FolderType value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.folderType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearFolderType() {
            this.bitField0_ &= -3;
            this.folderType_ = 0;
        }

        public boolean hasClassLoadLatency() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getClassLoadLatency() {
            return this.classLoadLatency_;
        }

        /* access modifiers changed from: private */
        public void setClassLoadLatency(int value) {
            this.bitField0_ |= 4;
            this.classLoadLatency_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClassLoadLatency() {
            this.bitField0_ &= -5;
            this.classLoadLatency_ = 0;
        }

        public boolean hasOpenConversationAnnotations() {
            return (this.bitField0_ & 8) != 0;
        }

        public OpenConversationAnnotations getOpenConversationAnnotations() {
            OpenConversationAnnotations openConversationAnnotations = this.openConversationAnnotations_;
            return openConversationAnnotations == null ? OpenConversationAnnotations.getDefaultInstance() : openConversationAnnotations;
        }

        /* access modifiers changed from: private */
        public void setOpenConversationAnnotations(OpenConversationAnnotations value) {
            if (value != null) {
                this.openConversationAnnotations_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setOpenConversationAnnotations(OpenConversationAnnotations.Builder builderForValue) {
            this.openConversationAnnotations_ = (OpenConversationAnnotations) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeOpenConversationAnnotations(OpenConversationAnnotations value) {
            if (value != null) {
                OpenConversationAnnotations openConversationAnnotations = this.openConversationAnnotations_;
                if (openConversationAnnotations == null || openConversationAnnotations == OpenConversationAnnotations.getDefaultInstance()) {
                    this.openConversationAnnotations_ = value;
                } else {
                    this.openConversationAnnotations_ = (OpenConversationAnnotations) ((OpenConversationAnnotations.Builder) OpenConversationAnnotations.newBuilder(this.openConversationAnnotations_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearOpenConversationAnnotations() {
            this.openConversationAnnotations_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasCancellationReason() {
            return (this.bitField0_ & 16) != 0;
        }

        public PrimesMetricExtensionEnums.CancellationReason getCancellationReason() {
            PrimesMetricExtensionEnums.CancellationReason result = PrimesMetricExtensionEnums.CancellationReason.forNumber(this.cancellationReason_);
            return result == null ? PrimesMetricExtensionEnums.CancellationReason.NONE : result;
        }

        /* access modifiers changed from: private */
        public void setCancellationReason(PrimesMetricExtensionEnums.CancellationReason value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.cancellationReason_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCancellationReason() {
            this.bitField0_ &= -17;
            this.cancellationReason_ = 0;
        }

        public boolean hasWebviewVersion() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getWebviewVersion() {
            return this.webviewVersion_;
        }

        public ByteString getWebviewVersionBytes() {
            return ByteString.copyFromUtf8(this.webviewVersion_);
        }

        /* access modifiers changed from: private */
        public void setWebviewVersion(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.webviewVersion_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearWebviewVersion() {
            this.bitField0_ &= -33;
            this.webviewVersion_ = getDefaultInstance().getWebviewVersion();
        }

        /* access modifiers changed from: private */
        public void setWebviewVersionBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.webviewVersion_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasExchangeInfo() {
            return (this.bitField0_ & 64) != 0;
        }

        public ExchangeInfo getExchangeInfo() {
            ExchangeInfo exchangeInfo = this.exchangeInfo_;
            return exchangeInfo == null ? ExchangeInfo.getDefaultInstance() : exchangeInfo;
        }

        /* access modifiers changed from: private */
        public void setExchangeInfo(ExchangeInfo value) {
            if (value != null) {
                this.exchangeInfo_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setExchangeInfo(ExchangeInfo.Builder builderForValue) {
            this.exchangeInfo_ = (ExchangeInfo) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeExchangeInfo(ExchangeInfo value) {
            if (value != null) {
                ExchangeInfo exchangeInfo = this.exchangeInfo_;
                if (exchangeInfo == null || exchangeInfo == ExchangeInfo.getDefaultInstance()) {
                    this.exchangeInfo_ = value;
                } else {
                    this.exchangeInfo_ = (ExchangeInfo) ((ExchangeInfo.Builder) ExchangeInfo.newBuilder(this.exchangeInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearExchangeInfo() {
            this.exchangeInfo_ = null;
            this.bitField0_ &= -65;
        }

        public boolean hasDataLayer() {
            return (this.bitField0_ & 128) != 0;
        }

        public PrimesMetricExtensionEnums.DataLayer getDataLayer() {
            PrimesMetricExtensionEnums.DataLayer result = PrimesMetricExtensionEnums.DataLayer.forNumber(this.dataLayer_);
            return result == null ? PrimesMetricExtensionEnums.DataLayer.UNKNOWN_DATA_LAYER : result;
        }

        /* access modifiers changed from: private */
        public void setDataLayer(PrimesMetricExtensionEnums.DataLayer value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.dataLayer_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDataLayer() {
            this.bitField0_ &= -129;
            this.dataLayer_ = 0;
        }

        @Deprecated
        public boolean hasRecyclerViewEnabled() {
            return (this.bitField0_ & 256) != 0;
        }

        @Deprecated
        public boolean getRecyclerViewEnabled() {
            return this.recyclerViewEnabled_;
        }

        /* access modifiers changed from: private */
        public void setRecyclerViewEnabled(boolean value) {
            this.bitField0_ |= 256;
            this.recyclerViewEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRecyclerViewEnabled() {
            this.bitField0_ &= -257;
            this.recyclerViewEnabled_ = false;
        }

        public boolean hasNumAccounts() {
            return (this.bitField0_ & 512) != 0;
        }

        public int getNumAccounts() {
            return this.numAccounts_;
        }

        /* access modifiers changed from: private */
        public void setNumAccounts(int value) {
            this.bitField0_ |= 512;
            this.numAccounts_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumAccounts() {
            this.bitField0_ &= -513;
            this.numAccounts_ = 0;
        }

        public boolean hasIsGooglerAccount() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getIsGooglerAccount() {
            return this.isGooglerAccount_;
        }

        /* access modifiers changed from: private */
        public void setIsGooglerAccount(boolean value) {
            this.bitField0_ |= 1024;
            this.isGooglerAccount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsGooglerAccount() {
            this.bitField0_ &= -1025;
            this.isGooglerAccount_ = false;
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(GmailExtension.class, DEFAULT_INSTANCE);
        }

        public List<PrimesMetricExtensionEnums.Annotation> getAnnotationList() {
            return new Internal.ListAdapter(this.annotation_, annotation_converter_);
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public PrimesMetricExtensionEnums.Annotation getAnnotation(int index) {
            return annotation_converter_.convert(Integer.valueOf(this.annotation_.getInt(index)));
        }

        private void ensureAnnotationIsMutable() {
            if (!this.annotation_.isModifiable()) {
                this.annotation_ = GeneratedMessageLite.mutableCopy(this.annotation_);
            }
        }

        /* access modifiers changed from: private */
        public void setAnnotation(int index, PrimesMetricExtensionEnums.Annotation value) {
            if (value != null) {
                ensureAnnotationIsMutable();
                this.annotation_.setInt(index, value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAnnotation(PrimesMetricExtensionEnums.Annotation value) {
            if (value != null) {
                ensureAnnotationIsMutable();
                this.annotation_.addInt(value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAllAnnotation(Iterable<? extends PrimesMetricExtensionEnums.Annotation> values) {
            ensureAnnotationIsMutable();
            for (PrimesMetricExtensionEnums.Annotation value : values) {
                this.annotation_.addInt(value.getNumber());
            }
        }

        /* access modifiers changed from: private */
        public void clearAnnotation() {
            this.annotation_ = emptyIntList();
        }

        @Deprecated
        public List<Integer> getActiveBteIdsList() {
            return this.activeBteIds_;
        }

        @Deprecated
        public int getActiveBteIdsCount() {
            return this.activeBteIds_.size();
        }

        @Deprecated
        public int getActiveBteIds(int index) {
            return this.activeBteIds_.getInt(index);
        }

        private void ensureActiveBteIdsIsMutable() {
            if (!this.activeBteIds_.isModifiable()) {
                this.activeBteIds_ = GeneratedMessageLite.mutableCopy(this.activeBteIds_);
            }
        }

        /* access modifiers changed from: private */
        public void setActiveBteIds(int index, int value) {
            ensureActiveBteIdsIsMutable();
            this.activeBteIds_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addActiveBteIds(int value) {
            ensureActiveBteIdsIsMutable();
            this.activeBteIds_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllActiveBteIds(Iterable<? extends Integer> values) {
            ensureActiveBteIdsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.activeBteIds_);
        }

        /* access modifiers changed from: private */
        public void clearActiveBteIds() {
            this.activeBteIds_ = emptyIntList();
        }

        public static GmailExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmailExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmailExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmailExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmailExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmailExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmailExtension parseFrom(InputStream input) throws IOException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmailExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmailExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (GmailExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GmailExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmailExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmailExtension parseFrom(CodedInputStream input) throws IOException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmailExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmailExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GmailExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GmailExtension, Builder> implements GmailExtensionOrBuilder {
            private Builder() {
                super(GmailExtension.DEFAULT_INSTANCE);
            }

            public boolean hasAccountType() {
                return ((GmailExtension) this.instance).hasAccountType();
            }

            public PrimesMetricExtensionEnums.AccountType getAccountType() {
                return ((GmailExtension) this.instance).getAccountType();
            }

            public Builder setAccountType(PrimesMetricExtensionEnums.AccountType value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setAccountType(value);
                return this;
            }

            public Builder clearAccountType() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearAccountType();
                return this;
            }

            public boolean hasFolderType() {
                return ((GmailExtension) this.instance).hasFolderType();
            }

            public PrimesMetricExtensionEnums.FolderType getFolderType() {
                return ((GmailExtension) this.instance).getFolderType();
            }

            public Builder setFolderType(PrimesMetricExtensionEnums.FolderType value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setFolderType(value);
                return this;
            }

            public Builder clearFolderType() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearFolderType();
                return this;
            }

            public boolean hasClassLoadLatency() {
                return ((GmailExtension) this.instance).hasClassLoadLatency();
            }

            public int getClassLoadLatency() {
                return ((GmailExtension) this.instance).getClassLoadLatency();
            }

            public Builder setClassLoadLatency(int value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setClassLoadLatency(value);
                return this;
            }

            public Builder clearClassLoadLatency() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearClassLoadLatency();
                return this;
            }

            public boolean hasOpenConversationAnnotations() {
                return ((GmailExtension) this.instance).hasOpenConversationAnnotations();
            }

            public OpenConversationAnnotations getOpenConversationAnnotations() {
                return ((GmailExtension) this.instance).getOpenConversationAnnotations();
            }

            public Builder setOpenConversationAnnotations(OpenConversationAnnotations value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setOpenConversationAnnotations(value);
                return this;
            }

            public Builder setOpenConversationAnnotations(OpenConversationAnnotations.Builder builderForValue) {
                copyOnWrite();
                ((GmailExtension) this.instance).setOpenConversationAnnotations(builderForValue);
                return this;
            }

            public Builder mergeOpenConversationAnnotations(OpenConversationAnnotations value) {
                copyOnWrite();
                ((GmailExtension) this.instance).mergeOpenConversationAnnotations(value);
                return this;
            }

            public Builder clearOpenConversationAnnotations() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearOpenConversationAnnotations();
                return this;
            }

            public boolean hasCancellationReason() {
                return ((GmailExtension) this.instance).hasCancellationReason();
            }

            public PrimesMetricExtensionEnums.CancellationReason getCancellationReason() {
                return ((GmailExtension) this.instance).getCancellationReason();
            }

            public Builder setCancellationReason(PrimesMetricExtensionEnums.CancellationReason value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setCancellationReason(value);
                return this;
            }

            public Builder clearCancellationReason() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearCancellationReason();
                return this;
            }

            public boolean hasWebviewVersion() {
                return ((GmailExtension) this.instance).hasWebviewVersion();
            }

            public String getWebviewVersion() {
                return ((GmailExtension) this.instance).getWebviewVersion();
            }

            public ByteString getWebviewVersionBytes() {
                return ((GmailExtension) this.instance).getWebviewVersionBytes();
            }

            public Builder setWebviewVersion(String value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setWebviewVersion(value);
                return this;
            }

            public Builder clearWebviewVersion() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearWebviewVersion();
                return this;
            }

            public Builder setWebviewVersionBytes(ByteString value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setWebviewVersionBytes(value);
                return this;
            }

            public boolean hasExchangeInfo() {
                return ((GmailExtension) this.instance).hasExchangeInfo();
            }

            public ExchangeInfo getExchangeInfo() {
                return ((GmailExtension) this.instance).getExchangeInfo();
            }

            public Builder setExchangeInfo(ExchangeInfo value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setExchangeInfo(value);
                return this;
            }

            public Builder setExchangeInfo(ExchangeInfo.Builder builderForValue) {
                copyOnWrite();
                ((GmailExtension) this.instance).setExchangeInfo(builderForValue);
                return this;
            }

            public Builder mergeExchangeInfo(ExchangeInfo value) {
                copyOnWrite();
                ((GmailExtension) this.instance).mergeExchangeInfo(value);
                return this;
            }

            public Builder clearExchangeInfo() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearExchangeInfo();
                return this;
            }

            public boolean hasDataLayer() {
                return ((GmailExtension) this.instance).hasDataLayer();
            }

            public PrimesMetricExtensionEnums.DataLayer getDataLayer() {
                return ((GmailExtension) this.instance).getDataLayer();
            }

            public Builder setDataLayer(PrimesMetricExtensionEnums.DataLayer value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setDataLayer(value);
                return this;
            }

            public Builder clearDataLayer() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearDataLayer();
                return this;
            }

            @Deprecated
            public boolean hasRecyclerViewEnabled() {
                return ((GmailExtension) this.instance).hasRecyclerViewEnabled();
            }

            @Deprecated
            public boolean getRecyclerViewEnabled() {
                return ((GmailExtension) this.instance).getRecyclerViewEnabled();
            }

            @Deprecated
            public Builder setRecyclerViewEnabled(boolean value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setRecyclerViewEnabled(value);
                return this;
            }

            @Deprecated
            public Builder clearRecyclerViewEnabled() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearRecyclerViewEnabled();
                return this;
            }

            public boolean hasNumAccounts() {
                return ((GmailExtension) this.instance).hasNumAccounts();
            }

            public int getNumAccounts() {
                return ((GmailExtension) this.instance).getNumAccounts();
            }

            public Builder setNumAccounts(int value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setNumAccounts(value);
                return this;
            }

            public Builder clearNumAccounts() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearNumAccounts();
                return this;
            }

            public boolean hasIsGooglerAccount() {
                return ((GmailExtension) this.instance).hasIsGooglerAccount();
            }

            public boolean getIsGooglerAccount() {
                return ((GmailExtension) this.instance).getIsGooglerAccount();
            }

            public Builder setIsGooglerAccount(boolean value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setIsGooglerAccount(value);
                return this;
            }

            public Builder clearIsGooglerAccount() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearIsGooglerAccount();
                return this;
            }

            public List<PrimesMetricExtensionEnums.Annotation> getAnnotationList() {
                return ((GmailExtension) this.instance).getAnnotationList();
            }

            public int getAnnotationCount() {
                return ((GmailExtension) this.instance).getAnnotationCount();
            }

            public PrimesMetricExtensionEnums.Annotation getAnnotation(int index) {
                return ((GmailExtension) this.instance).getAnnotation(index);
            }

            public Builder setAnnotation(int index, PrimesMetricExtensionEnums.Annotation value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setAnnotation(index, value);
                return this;
            }

            public Builder addAnnotation(PrimesMetricExtensionEnums.Annotation value) {
                copyOnWrite();
                ((GmailExtension) this.instance).addAnnotation(value);
                return this;
            }

            public Builder addAllAnnotation(Iterable<? extends PrimesMetricExtensionEnums.Annotation> values) {
                copyOnWrite();
                ((GmailExtension) this.instance).addAllAnnotation(values);
                return this;
            }

            public Builder clearAnnotation() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearAnnotation();
                return this;
            }

            @Deprecated
            public List<Integer> getActiveBteIdsList() {
                return Collections.unmodifiableList(((GmailExtension) this.instance).getActiveBteIdsList());
            }

            @Deprecated
            public int getActiveBteIdsCount() {
                return ((GmailExtension) this.instance).getActiveBteIdsCount();
            }

            @Deprecated
            public int getActiveBteIds(int index) {
                return ((GmailExtension) this.instance).getActiveBteIds(index);
            }

            @Deprecated
            public Builder setActiveBteIds(int index, int value) {
                copyOnWrite();
                ((GmailExtension) this.instance).setActiveBteIds(index, value);
                return this;
            }

            @Deprecated
            public Builder addActiveBteIds(int value) {
                copyOnWrite();
                ((GmailExtension) this.instance).addActiveBteIds(value);
                return this;
            }

            @Deprecated
            public Builder addAllActiveBteIds(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((GmailExtension) this.instance).addAllActiveBteIds(values);
                return this;
            }

            @Deprecated
            public Builder clearActiveBteIds() {
                copyOnWrite();
                ((GmailExtension) this.instance).clearActiveBteIds();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GmailExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0002\u0000\u0001\f\u0000\u0002\f\u0001\u0003\u0004\u0002\u0004\t\u0003\u0005\f\u0004\u0006\b\u0005\u0007\t\u0006\b\f\u0007\t\u0007\b\n\u0004\t\u000b\u0007\n\f\u001e\r\u0016", new Object[]{"bitField0_", "accountType_", PrimesMetricExtensionEnums.AccountType.internalGetVerifier(), "folderType_", PrimesMetricExtensionEnums.FolderType.internalGetVerifier(), "classLoadLatency_", "openConversationAnnotations_", "cancellationReason_", PrimesMetricExtensionEnums.CancellationReason.internalGetVerifier(), "webviewVersion_", "exchangeInfo_", "dataLayer_", PrimesMetricExtensionEnums.DataLayer.internalGetVerifier(), "recyclerViewEnabled_", "numAccounts_", "isGooglerAccount_", "annotation_", PrimesMetricExtensionEnums.Annotation.internalGetVerifier(), "activeBteIds_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GmailExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (GmailExtension.class) {
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

        public static GmailExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GmailExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class OpenConversationAnnotations extends GeneratedMessageLite<OpenConversationAnnotations, Builder> implements OpenConversationAnnotationsOrBuilder {
        public static final int CONTENT_SOURCE_FIELD_NUMBER = 1;
        public static final int CONVERSATION_INDEX_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final OpenConversationAnnotations DEFAULT_INSTANCE = new OpenConversationAnnotations();
        public static final int DEFER_WEBVIEW_IMAGE_LOAD_UNTIL_CV_REVEALED_FIELD_NUMBER = 9;
        public static final int HAS_INLINE_ATTACHMENTS_FIELD_NUMBER = 3;
        public static final int HAS_LOADED_DYNAMIC_MAIL_FIELD_NUMBER = 10;
        public static final int IS_COLD_OPEN_FIELD_NUMBER = 4;
        public static final int NUM_MESSAGES_FIELD_NUMBER = 2;
        private static volatile Parser<OpenConversationAnnotations> PARSER = null;
        public static final int USE_DOM_CONTENT_LOADED_SIGNAL_FIELD_NUMBER = 6;
        public static final int WEBVIEW_DUMP_HASH_FIELD_NUMBER = 7;
        public static final int WEBVIEW_THREAD_DUMP_FIELD_NUMBER = 8;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int contentSource_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int conversationIndex_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean deferWebviewImageLoadUntilCvRevealed_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean hasInlineAttachments_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean hasLoadedDynamicMail_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean isColdOpen_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numMessages_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean useDomContentLoadedSignal_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int webviewDumpHash_;
        @ProtoField(fieldNumber = 8, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private String webviewThreadDump_ = "";

        private OpenConversationAnnotations() {
        }

        public boolean hasContentSource() {
            return (this.bitField0_ & 1) != 0;
        }

        public PrimesMetricExtensionEnums.ContentSource getContentSource() {
            PrimesMetricExtensionEnums.ContentSource result = PrimesMetricExtensionEnums.ContentSource.forNumber(this.contentSource_);
            return result == null ? PrimesMetricExtensionEnums.ContentSource.UNKNOWN_CONTENT_SOURCE : result;
        }

        /* access modifiers changed from: private */
        public void setContentSource(PrimesMetricExtensionEnums.ContentSource value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.contentSource_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearContentSource() {
            this.bitField0_ &= -2;
            this.contentSource_ = 0;
        }

        public boolean hasNumMessages() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumMessages() {
            return this.numMessages_;
        }

        /* access modifiers changed from: private */
        public void setNumMessages(int value) {
            this.bitField0_ |= 2;
            this.numMessages_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumMessages() {
            this.bitField0_ &= -3;
            this.numMessages_ = 0;
        }

        public boolean hasHasInlineAttachments() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getHasInlineAttachments() {
            return this.hasInlineAttachments_;
        }

        /* access modifiers changed from: private */
        public void setHasInlineAttachments(boolean value) {
            this.bitField0_ |= 4;
            this.hasInlineAttachments_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasInlineAttachments() {
            this.bitField0_ &= -5;
            this.hasInlineAttachments_ = false;
        }

        public boolean hasIsColdOpen() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getIsColdOpen() {
            return this.isColdOpen_;
        }

        /* access modifiers changed from: private */
        public void setIsColdOpen(boolean value) {
            this.bitField0_ |= 8;
            this.isColdOpen_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsColdOpen() {
            this.bitField0_ &= -9;
            this.isColdOpen_ = false;
        }

        public boolean hasConversationIndex() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getConversationIndex() {
            return this.conversationIndex_;
        }

        /* access modifiers changed from: private */
        public void setConversationIndex(int value) {
            this.bitField0_ |= 16;
            this.conversationIndex_ = value;
        }

        /* access modifiers changed from: private */
        public void clearConversationIndex() {
            this.bitField0_ &= -17;
            this.conversationIndex_ = 0;
        }

        public boolean hasUseDomContentLoadedSignal() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getUseDomContentLoadedSignal() {
            return this.useDomContentLoadedSignal_;
        }

        /* access modifiers changed from: private */
        public void setUseDomContentLoadedSignal(boolean value) {
            this.bitField0_ |= 32;
            this.useDomContentLoadedSignal_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUseDomContentLoadedSignal() {
            this.bitField0_ &= -33;
            this.useDomContentLoadedSignal_ = false;
        }

        public boolean hasWebviewDumpHash() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getWebviewDumpHash() {
            return this.webviewDumpHash_;
        }

        /* access modifiers changed from: private */
        public void setWebviewDumpHash(int value) {
            this.bitField0_ |= 64;
            this.webviewDumpHash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWebviewDumpHash() {
            this.bitField0_ &= -65;
            this.webviewDumpHash_ = 0;
        }

        public boolean hasWebviewThreadDump() {
            return (this.bitField0_ & 128) != 0;
        }

        public String getWebviewThreadDump() {
            return this.webviewThreadDump_;
        }

        public ByteString getWebviewThreadDumpBytes() {
            return ByteString.copyFromUtf8(this.webviewThreadDump_);
        }

        /* access modifiers changed from: private */
        public void setWebviewThreadDump(String value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.webviewThreadDump_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearWebviewThreadDump() {
            this.bitField0_ &= -129;
            this.webviewThreadDump_ = getDefaultInstance().getWebviewThreadDump();
        }

        /* access modifiers changed from: private */
        public void setWebviewThreadDumpBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.webviewThreadDump_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasDeferWebviewImageLoadUntilCvRevealed() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getDeferWebviewImageLoadUntilCvRevealed() {
            return this.deferWebviewImageLoadUntilCvRevealed_;
        }

        /* access modifiers changed from: private */
        public void setDeferWebviewImageLoadUntilCvRevealed(boolean value) {
            this.bitField0_ |= 256;
            this.deferWebviewImageLoadUntilCvRevealed_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeferWebviewImageLoadUntilCvRevealed() {
            this.bitField0_ &= -257;
            this.deferWebviewImageLoadUntilCvRevealed_ = false;
        }

        public boolean hasHasLoadedDynamicMail() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getHasLoadedDynamicMail() {
            return this.hasLoadedDynamicMail_;
        }

        /* access modifiers changed from: private */
        public void setHasLoadedDynamicMail(boolean value) {
            this.bitField0_ |= 512;
            this.hasLoadedDynamicMail_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasLoadedDynamicMail() {
            this.bitField0_ &= -513;
            this.hasLoadedDynamicMail_ = false;
        }

        public static OpenConversationAnnotations parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OpenConversationAnnotations parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OpenConversationAnnotations parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OpenConversationAnnotations parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OpenConversationAnnotations parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OpenConversationAnnotations parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OpenConversationAnnotations parseFrom(InputStream input) throws IOException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OpenConversationAnnotations parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OpenConversationAnnotations parseDelimitedFrom(InputStream input) throws IOException {
            return (OpenConversationAnnotations) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static OpenConversationAnnotations parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OpenConversationAnnotations) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OpenConversationAnnotations parseFrom(CodedInputStream input) throws IOException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OpenConversationAnnotations parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OpenConversationAnnotations) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(OpenConversationAnnotations prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<OpenConversationAnnotations, Builder> implements OpenConversationAnnotationsOrBuilder {
            private Builder() {
                super(OpenConversationAnnotations.DEFAULT_INSTANCE);
            }

            public boolean hasContentSource() {
                return ((OpenConversationAnnotations) this.instance).hasContentSource();
            }

            public PrimesMetricExtensionEnums.ContentSource getContentSource() {
                return ((OpenConversationAnnotations) this.instance).getContentSource();
            }

            public Builder setContentSource(PrimesMetricExtensionEnums.ContentSource value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setContentSource(value);
                return this;
            }

            public Builder clearContentSource() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearContentSource();
                return this;
            }

            public boolean hasNumMessages() {
                return ((OpenConversationAnnotations) this.instance).hasNumMessages();
            }

            public int getNumMessages() {
                return ((OpenConversationAnnotations) this.instance).getNumMessages();
            }

            public Builder setNumMessages(int value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setNumMessages(value);
                return this;
            }

            public Builder clearNumMessages() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearNumMessages();
                return this;
            }

            public boolean hasHasInlineAttachments() {
                return ((OpenConversationAnnotations) this.instance).hasHasInlineAttachments();
            }

            public boolean getHasInlineAttachments() {
                return ((OpenConversationAnnotations) this.instance).getHasInlineAttachments();
            }

            public Builder setHasInlineAttachments(boolean value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setHasInlineAttachments(value);
                return this;
            }

            public Builder clearHasInlineAttachments() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearHasInlineAttachments();
                return this;
            }

            public boolean hasIsColdOpen() {
                return ((OpenConversationAnnotations) this.instance).hasIsColdOpen();
            }

            public boolean getIsColdOpen() {
                return ((OpenConversationAnnotations) this.instance).getIsColdOpen();
            }

            public Builder setIsColdOpen(boolean value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setIsColdOpen(value);
                return this;
            }

            public Builder clearIsColdOpen() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearIsColdOpen();
                return this;
            }

            public boolean hasConversationIndex() {
                return ((OpenConversationAnnotations) this.instance).hasConversationIndex();
            }

            public int getConversationIndex() {
                return ((OpenConversationAnnotations) this.instance).getConversationIndex();
            }

            public Builder setConversationIndex(int value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setConversationIndex(value);
                return this;
            }

            public Builder clearConversationIndex() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearConversationIndex();
                return this;
            }

            public boolean hasUseDomContentLoadedSignal() {
                return ((OpenConversationAnnotations) this.instance).hasUseDomContentLoadedSignal();
            }

            public boolean getUseDomContentLoadedSignal() {
                return ((OpenConversationAnnotations) this.instance).getUseDomContentLoadedSignal();
            }

            public Builder setUseDomContentLoadedSignal(boolean value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setUseDomContentLoadedSignal(value);
                return this;
            }

            public Builder clearUseDomContentLoadedSignal() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearUseDomContentLoadedSignal();
                return this;
            }

            public boolean hasWebviewDumpHash() {
                return ((OpenConversationAnnotations) this.instance).hasWebviewDumpHash();
            }

            public int getWebviewDumpHash() {
                return ((OpenConversationAnnotations) this.instance).getWebviewDumpHash();
            }

            public Builder setWebviewDumpHash(int value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setWebviewDumpHash(value);
                return this;
            }

            public Builder clearWebviewDumpHash() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearWebviewDumpHash();
                return this;
            }

            public boolean hasWebviewThreadDump() {
                return ((OpenConversationAnnotations) this.instance).hasWebviewThreadDump();
            }

            public String getWebviewThreadDump() {
                return ((OpenConversationAnnotations) this.instance).getWebviewThreadDump();
            }

            public ByteString getWebviewThreadDumpBytes() {
                return ((OpenConversationAnnotations) this.instance).getWebviewThreadDumpBytes();
            }

            public Builder setWebviewThreadDump(String value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setWebviewThreadDump(value);
                return this;
            }

            public Builder clearWebviewThreadDump() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearWebviewThreadDump();
                return this;
            }

            public Builder setWebviewThreadDumpBytes(ByteString value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setWebviewThreadDumpBytes(value);
                return this;
            }

            public boolean hasDeferWebviewImageLoadUntilCvRevealed() {
                return ((OpenConversationAnnotations) this.instance).hasDeferWebviewImageLoadUntilCvRevealed();
            }

            public boolean getDeferWebviewImageLoadUntilCvRevealed() {
                return ((OpenConversationAnnotations) this.instance).getDeferWebviewImageLoadUntilCvRevealed();
            }

            public Builder setDeferWebviewImageLoadUntilCvRevealed(boolean value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setDeferWebviewImageLoadUntilCvRevealed(value);
                return this;
            }

            public Builder clearDeferWebviewImageLoadUntilCvRevealed() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearDeferWebviewImageLoadUntilCvRevealed();
                return this;
            }

            public boolean hasHasLoadedDynamicMail() {
                return ((OpenConversationAnnotations) this.instance).hasHasLoadedDynamicMail();
            }

            public boolean getHasLoadedDynamicMail() {
                return ((OpenConversationAnnotations) this.instance).getHasLoadedDynamicMail();
            }

            public Builder setHasLoadedDynamicMail(boolean value) {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).setHasLoadedDynamicMail(value);
                return this;
            }

            public Builder clearHasLoadedDynamicMail() {
                copyOnWrite();
                ((OpenConversationAnnotations) this.instance).clearHasLoadedDynamicMail();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new OpenConversationAnnotations();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0004\u0004\u0006\u0007\u0005\u0007\u0004\u0006\b\b\u0007\t\u0007\b\n\u0007\t", new Object[]{"bitField0_", "contentSource_", PrimesMetricExtensionEnums.ContentSource.internalGetVerifier(), "numMessages_", "hasInlineAttachments_", "isColdOpen_", "conversationIndex_", "useDomContentLoadedSignal_", "webviewDumpHash_", "webviewThreadDump_", "deferWebviewImageLoadUntilCvRevealed_", "hasLoadedDynamicMail_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<OpenConversationAnnotations> parser = PARSER;
                    if (parser == null) {
                        synchronized (OpenConversationAnnotations.class) {
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
            GeneratedMessageLite.registerDefaultInstance(OpenConversationAnnotations.class, DEFAULT_INSTANCE);
        }

        public static OpenConversationAnnotations getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OpenConversationAnnotations> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ExchangeInfo extends GeneratedMessageLite<ExchangeInfo, Builder> implements ExchangeInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final ExchangeInfo DEFAULT_INSTANCE = new ExchangeInfo();
        public static final int DPC_INFO_FIELD_NUMBER = 3;
        public static final int EXCHANGE_ACCOUNT_INFO_FIELD_NUMBER = 2;
        public static final int EXCHANGE_INFO_ID_FIELD_NUMBER = 1;
        private static volatile Parser<ExchangeInfo> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private DpcInfo dpcInfo_;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ExchangeAccountInfo> exchangeAccountInfo_ = emptyProtobufList();
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long exchangeInfoId_;

        public interface DpcInfoOrBuilder extends MessageLiteOrBuilder {
            String getDpcPackageName();

            ByteString getDpcPackageNameBytes();

            DpcInfo.ManagementMode getManagementMode();

            boolean hasDpcPackageName();

            boolean hasManagementMode();
        }

        public interface ExchangeAccountInfoOrBuilder extends MessageLiteOrBuilder {
            ExchangeAccountInfo.EasVersion getEasVersion();

            boolean getIsManaged();

            boolean hasEasVersion();

            boolean hasIsManaged();
        }

        private ExchangeInfo() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class ExchangeAccountInfo extends GeneratedMessageLite<ExchangeAccountInfo, Builder> implements ExchangeAccountInfoOrBuilder {
            /* access modifiers changed from: private */
            public static final ExchangeAccountInfo DEFAULT_INSTANCE = new ExchangeAccountInfo();
            public static final int EAS_VERSION_FIELD_NUMBER = 2;
            public static final int IS_MANAGED_FIELD_NUMBER = 1;
            private static volatile Parser<ExchangeAccountInfo> PARSER;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int easVersion_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private boolean isManaged_;

            private ExchangeAccountInfo() {
            }

            public enum EasVersion implements Internal.EnumLite {
                OTHER(0),
                V_2_5(1),
                V_12_0(2),
                V_12_1(3),
                V_14_0(4),
                V_14_1(5),
                V_16_0(6),
                V_16_1(7);
                
                public static final int OTHER_VALUE = 0;
                public static final int V_12_0_VALUE = 2;
                public static final int V_12_1_VALUE = 3;
                public static final int V_14_0_VALUE = 4;
                public static final int V_14_1_VALUE = 5;
                public static final int V_16_0_VALUE = 6;
                public static final int V_16_1_VALUE = 7;
                public static final int V_2_5_VALUE = 1;
                private static final Internal.EnumLiteMap<EasVersion> internalValueMap = new Internal.EnumLiteMap<EasVersion>() {
                    public EasVersion findValueByNumber(int number) {
                        return EasVersion.forNumber(number);
                    }
                };
                private final int value;

                public final int getNumber() {
                    return this.value;
                }

                public static EasVersion forNumber(int value2) {
                    switch (value2) {
                        case 0:
                            return OTHER;
                        case 1:
                            return V_2_5;
                        case 2:
                            return V_12_0;
                        case 3:
                            return V_12_1;
                        case 4:
                            return V_14_0;
                        case 5:
                            return V_14_1;
                        case 6:
                            return V_16_0;
                        case 7:
                            return V_16_1;
                        default:
                            return null;
                    }
                }

                public static Internal.EnumLiteMap<EasVersion> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return EasVersionVerifier.INSTANCE;
                }

                private static final class EasVersionVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new EasVersionVerifier();

                    private EasVersionVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return EasVersion.forNumber(number) != null;
                    }
                }

                private EasVersion(int value2) {
                    this.value = value2;
                }
            }

            public boolean hasIsManaged() {
                return (this.bitField0_ & 1) != 0;
            }

            public boolean getIsManaged() {
                return this.isManaged_;
            }

            /* access modifiers changed from: private */
            public void setIsManaged(boolean value) {
                this.bitField0_ |= 1;
                this.isManaged_ = value;
            }

            /* access modifiers changed from: private */
            public void clearIsManaged() {
                this.bitField0_ &= -2;
                this.isManaged_ = false;
            }

            public boolean hasEasVersion() {
                return (this.bitField0_ & 2) != 0;
            }

            public EasVersion getEasVersion() {
                EasVersion result = EasVersion.forNumber(this.easVersion_);
                return result == null ? EasVersion.OTHER : result;
            }

            /* access modifiers changed from: private */
            public void setEasVersion(EasVersion value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.easVersion_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearEasVersion() {
                this.bitField0_ &= -3;
                this.easVersion_ = 0;
            }

            public static ExchangeAccountInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExchangeAccountInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExchangeAccountInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExchangeAccountInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExchangeAccountInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExchangeAccountInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExchangeAccountInfo parseFrom(InputStream input) throws IOException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExchangeAccountInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExchangeAccountInfo parseDelimitedFrom(InputStream input) throws IOException {
                return (ExchangeAccountInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static ExchangeAccountInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExchangeAccountInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExchangeAccountInfo parseFrom(CodedInputStream input) throws IOException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExchangeAccountInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExchangeAccountInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(ExchangeAccountInfo prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<ExchangeAccountInfo, Builder> implements ExchangeAccountInfoOrBuilder {
                private Builder() {
                    super(ExchangeAccountInfo.DEFAULT_INSTANCE);
                }

                public boolean hasIsManaged() {
                    return ((ExchangeAccountInfo) this.instance).hasIsManaged();
                }

                public boolean getIsManaged() {
                    return ((ExchangeAccountInfo) this.instance).getIsManaged();
                }

                public Builder setIsManaged(boolean value) {
                    copyOnWrite();
                    ((ExchangeAccountInfo) this.instance).setIsManaged(value);
                    return this;
                }

                public Builder clearIsManaged() {
                    copyOnWrite();
                    ((ExchangeAccountInfo) this.instance).clearIsManaged();
                    return this;
                }

                public boolean hasEasVersion() {
                    return ((ExchangeAccountInfo) this.instance).hasEasVersion();
                }

                public EasVersion getEasVersion() {
                    return ((ExchangeAccountInfo) this.instance).getEasVersion();
                }

                public Builder setEasVersion(EasVersion value) {
                    copyOnWrite();
                    ((ExchangeAccountInfo) this.instance).setEasVersion(value);
                    return this;
                }

                public Builder clearEasVersion() {
                    copyOnWrite();
                    ((ExchangeAccountInfo) this.instance).clearEasVersion();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new ExchangeAccountInfo();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0007\u0000\u0002\f\u0001", new Object[]{"bitField0_", "isManaged_", "easVersion_", EasVersion.internalGetVerifier()});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<ExchangeAccountInfo> parser = PARSER;
                        if (parser == null) {
                            synchronized (ExchangeAccountInfo.class) {
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
                GeneratedMessageLite.registerDefaultInstance(ExchangeAccountInfo.class, DEFAULT_INSTANCE);
            }

            public static ExchangeAccountInfo getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ExchangeAccountInfo> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class DpcInfo extends GeneratedMessageLite<DpcInfo, Builder> implements DpcInfoOrBuilder {
            /* access modifiers changed from: private */
            public static final DpcInfo DEFAULT_INSTANCE = new DpcInfo();
            public static final int DPC_PACKAGE_NAME_FIELD_NUMBER = 2;
            public static final int MANAGEMENT_MODE_FIELD_NUMBER = 1;
            private static volatile Parser<DpcInfo> PARSER;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private String dpcPackageName_ = "";
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int managementMode_;

            private DpcInfo() {
            }

            public enum ManagementMode implements Internal.EnumLite {
                OTHER(0),
                DEVICE_OWNER(1),
                PROFILE_OWNER(2);
                
                public static final int DEVICE_OWNER_VALUE = 1;
                public static final int OTHER_VALUE = 0;
                public static final int PROFILE_OWNER_VALUE = 2;
                private static final Internal.EnumLiteMap<ManagementMode> internalValueMap = new Internal.EnumLiteMap<ManagementMode>() {
                    public ManagementMode findValueByNumber(int number) {
                        return ManagementMode.forNumber(number);
                    }
                };
                private final int value;

                public final int getNumber() {
                    return this.value;
                }

                public static ManagementMode forNumber(int value2) {
                    if (value2 == 0) {
                        return OTHER;
                    }
                    if (value2 == 1) {
                        return DEVICE_OWNER;
                    }
                    if (value2 != 2) {
                        return null;
                    }
                    return PROFILE_OWNER;
                }

                public static Internal.EnumLiteMap<ManagementMode> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return ManagementModeVerifier.INSTANCE;
                }

                private static final class ManagementModeVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new ManagementModeVerifier();

                    private ManagementModeVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return ManagementMode.forNumber(number) != null;
                    }
                }

                private ManagementMode(int value2) {
                    this.value = value2;
                }
            }

            public boolean hasManagementMode() {
                return (this.bitField0_ & 1) != 0;
            }

            public ManagementMode getManagementMode() {
                ManagementMode result = ManagementMode.forNumber(this.managementMode_);
                return result == null ? ManagementMode.OTHER : result;
            }

            /* access modifiers changed from: private */
            public void setManagementMode(ManagementMode value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.managementMode_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearManagementMode() {
                this.bitField0_ &= -2;
                this.managementMode_ = 0;
            }

            public boolean hasDpcPackageName() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getDpcPackageName() {
                return this.dpcPackageName_;
            }

            public ByteString getDpcPackageNameBytes() {
                return ByteString.copyFromUtf8(this.dpcPackageName_);
            }

            /* access modifiers changed from: private */
            public void setDpcPackageName(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.dpcPackageName_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearDpcPackageName() {
                this.bitField0_ &= -3;
                this.dpcPackageName_ = getDefaultInstance().getDpcPackageName();
            }

            /* access modifiers changed from: private */
            public void setDpcPackageNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.dpcPackageName_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public static DpcInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DpcInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DpcInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DpcInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DpcInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DpcInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DpcInfo parseFrom(InputStream input) throws IOException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static DpcInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static DpcInfo parseDelimitedFrom(InputStream input) throws IOException {
                return (DpcInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static DpcInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DpcInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static DpcInfo parseFrom(CodedInputStream input) throws IOException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static DpcInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DpcInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(DpcInfo prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<DpcInfo, Builder> implements DpcInfoOrBuilder {
                private Builder() {
                    super(DpcInfo.DEFAULT_INSTANCE);
                }

                public boolean hasManagementMode() {
                    return ((DpcInfo) this.instance).hasManagementMode();
                }

                public ManagementMode getManagementMode() {
                    return ((DpcInfo) this.instance).getManagementMode();
                }

                public Builder setManagementMode(ManagementMode value) {
                    copyOnWrite();
                    ((DpcInfo) this.instance).setManagementMode(value);
                    return this;
                }

                public Builder clearManagementMode() {
                    copyOnWrite();
                    ((DpcInfo) this.instance).clearManagementMode();
                    return this;
                }

                public boolean hasDpcPackageName() {
                    return ((DpcInfo) this.instance).hasDpcPackageName();
                }

                public String getDpcPackageName() {
                    return ((DpcInfo) this.instance).getDpcPackageName();
                }

                public ByteString getDpcPackageNameBytes() {
                    return ((DpcInfo) this.instance).getDpcPackageNameBytes();
                }

                public Builder setDpcPackageName(String value) {
                    copyOnWrite();
                    ((DpcInfo) this.instance).setDpcPackageName(value);
                    return this;
                }

                public Builder clearDpcPackageName() {
                    copyOnWrite();
                    ((DpcInfo) this.instance).clearDpcPackageName();
                    return this;
                }

                public Builder setDpcPackageNameBytes(ByteString value) {
                    copyOnWrite();
                    ((DpcInfo) this.instance).setDpcPackageNameBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new DpcInfo();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001", new Object[]{"bitField0_", "managementMode_", ManagementMode.internalGetVerifier(), "dpcPackageName_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<DpcInfo> parser = PARSER;
                        if (parser == null) {
                            synchronized (DpcInfo.class) {
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
                GeneratedMessageLite.registerDefaultInstance(DpcInfo.class, DEFAULT_INSTANCE);
            }

            public static DpcInfo getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<DpcInfo> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasExchangeInfoId() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getExchangeInfoId() {
            return this.exchangeInfoId_;
        }

        /* access modifiers changed from: private */
        public void setExchangeInfoId(long value) {
            this.bitField0_ |= 1;
            this.exchangeInfoId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExchangeInfoId() {
            this.bitField0_ &= -2;
            this.exchangeInfoId_ = 0;
        }

        public List<ExchangeAccountInfo> getExchangeAccountInfoList() {
            return this.exchangeAccountInfo_;
        }

        public List<? extends ExchangeAccountInfoOrBuilder> getExchangeAccountInfoOrBuilderList() {
            return this.exchangeAccountInfo_;
        }

        public int getExchangeAccountInfoCount() {
            return this.exchangeAccountInfo_.size();
        }

        public ExchangeAccountInfo getExchangeAccountInfo(int index) {
            return this.exchangeAccountInfo_.get(index);
        }

        public ExchangeAccountInfoOrBuilder getExchangeAccountInfoOrBuilder(int index) {
            return this.exchangeAccountInfo_.get(index);
        }

        private void ensureExchangeAccountInfoIsMutable() {
            if (!this.exchangeAccountInfo_.isModifiable()) {
                this.exchangeAccountInfo_ = GeneratedMessageLite.mutableCopy(this.exchangeAccountInfo_);
            }
        }

        /* access modifiers changed from: private */
        public void setExchangeAccountInfo(int index, ExchangeAccountInfo value) {
            if (value != null) {
                ensureExchangeAccountInfoIsMutable();
                this.exchangeAccountInfo_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setExchangeAccountInfo(int index, ExchangeAccountInfo.Builder builderForValue) {
            ensureExchangeAccountInfoIsMutable();
            this.exchangeAccountInfo_.set(index, (ExchangeAccountInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExchangeAccountInfo(ExchangeAccountInfo value) {
            if (value != null) {
                ensureExchangeAccountInfoIsMutable();
                this.exchangeAccountInfo_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExchangeAccountInfo(int index, ExchangeAccountInfo value) {
            if (value != null) {
                ensureExchangeAccountInfoIsMutable();
                this.exchangeAccountInfo_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addExchangeAccountInfo(ExchangeAccountInfo.Builder builderForValue) {
            ensureExchangeAccountInfoIsMutable();
            this.exchangeAccountInfo_.add((ExchangeAccountInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addExchangeAccountInfo(int index, ExchangeAccountInfo.Builder builderForValue) {
            ensureExchangeAccountInfoIsMutable();
            this.exchangeAccountInfo_.add(index, (ExchangeAccountInfo) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.ExtensionGmail$ExchangeInfo$ExchangeAccountInfo>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.ExtensionGmail$ExchangeInfo$ExchangeAccountInfo>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllExchangeAccountInfo(Iterable<? extends ExchangeAccountInfo> values) {
            ensureExchangeAccountInfoIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.exchangeAccountInfo_);
        }

        /* access modifiers changed from: private */
        public void clearExchangeAccountInfo() {
            this.exchangeAccountInfo_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExchangeAccountInfo(int index) {
            ensureExchangeAccountInfoIsMutable();
            this.exchangeAccountInfo_.remove(index);
        }

        public boolean hasDpcInfo() {
            return (this.bitField0_ & 2) != 0;
        }

        public DpcInfo getDpcInfo() {
            DpcInfo dpcInfo = this.dpcInfo_;
            return dpcInfo == null ? DpcInfo.getDefaultInstance() : dpcInfo;
        }

        /* access modifiers changed from: private */
        public void setDpcInfo(DpcInfo value) {
            if (value != null) {
                this.dpcInfo_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDpcInfo(DpcInfo.Builder builderForValue) {
            this.dpcInfo_ = (DpcInfo) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeDpcInfo(DpcInfo value) {
            if (value != null) {
                DpcInfo dpcInfo = this.dpcInfo_;
                if (dpcInfo == null || dpcInfo == DpcInfo.getDefaultInstance()) {
                    this.dpcInfo_ = value;
                } else {
                    this.dpcInfo_ = (DpcInfo) ((DpcInfo.Builder) DpcInfo.newBuilder(this.dpcInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDpcInfo() {
            this.dpcInfo_ = null;
            this.bitField0_ &= -3;
        }

        public static ExchangeInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExchangeInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExchangeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExchangeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExchangeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExchangeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExchangeInfo parseFrom(InputStream input) throws IOException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExchangeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExchangeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (ExchangeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExchangeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExchangeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExchangeInfo parseFrom(CodedInputStream input) throws IOException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExchangeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExchangeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExchangeInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ExchangeInfo, Builder> implements ExchangeInfoOrBuilder {
            private Builder() {
                super(ExchangeInfo.DEFAULT_INSTANCE);
            }

            public boolean hasExchangeInfoId() {
                return ((ExchangeInfo) this.instance).hasExchangeInfoId();
            }

            public long getExchangeInfoId() {
                return ((ExchangeInfo) this.instance).getExchangeInfoId();
            }

            public Builder setExchangeInfoId(long value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).setExchangeInfoId(value);
                return this;
            }

            public Builder clearExchangeInfoId() {
                copyOnWrite();
                ((ExchangeInfo) this.instance).clearExchangeInfoId();
                return this;
            }

            public List<ExchangeAccountInfo> getExchangeAccountInfoList() {
                return Collections.unmodifiableList(((ExchangeInfo) this.instance).getExchangeAccountInfoList());
            }

            public int getExchangeAccountInfoCount() {
                return ((ExchangeInfo) this.instance).getExchangeAccountInfoCount();
            }

            public ExchangeAccountInfo getExchangeAccountInfo(int index) {
                return ((ExchangeInfo) this.instance).getExchangeAccountInfo(index);
            }

            public Builder setExchangeAccountInfo(int index, ExchangeAccountInfo value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).setExchangeAccountInfo(index, value);
                return this;
            }

            public Builder setExchangeAccountInfo(int index, ExchangeAccountInfo.Builder builderForValue) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).setExchangeAccountInfo(index, builderForValue);
                return this;
            }

            public Builder addExchangeAccountInfo(ExchangeAccountInfo value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).addExchangeAccountInfo(value);
                return this;
            }

            public Builder addExchangeAccountInfo(int index, ExchangeAccountInfo value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).addExchangeAccountInfo(index, value);
                return this;
            }

            public Builder addExchangeAccountInfo(ExchangeAccountInfo.Builder builderForValue) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).addExchangeAccountInfo(builderForValue);
                return this;
            }

            public Builder addExchangeAccountInfo(int index, ExchangeAccountInfo.Builder builderForValue) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).addExchangeAccountInfo(index, builderForValue);
                return this;
            }

            public Builder addAllExchangeAccountInfo(Iterable<? extends ExchangeAccountInfo> values) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).addAllExchangeAccountInfo(values);
                return this;
            }

            public Builder clearExchangeAccountInfo() {
                copyOnWrite();
                ((ExchangeInfo) this.instance).clearExchangeAccountInfo();
                return this;
            }

            public Builder removeExchangeAccountInfo(int index) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).removeExchangeAccountInfo(index);
                return this;
            }

            public boolean hasDpcInfo() {
                return ((ExchangeInfo) this.instance).hasDpcInfo();
            }

            public DpcInfo getDpcInfo() {
                return ((ExchangeInfo) this.instance).getDpcInfo();
            }

            public Builder setDpcInfo(DpcInfo value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).setDpcInfo(value);
                return this;
            }

            public Builder setDpcInfo(DpcInfo.Builder builderForValue) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).setDpcInfo(builderForValue);
                return this;
            }

            public Builder mergeDpcInfo(DpcInfo value) {
                copyOnWrite();
                ((ExchangeInfo) this.instance).mergeDpcInfo(value);
                return this;
            }

            public Builder clearDpcInfo() {
                copyOnWrite();
                ((ExchangeInfo) this.instance).clearDpcInfo();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ExchangeInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u0005\u0000\u0002\u001b\u0003\t\u0001", new Object[]{"bitField0_", "exchangeInfoId_", "exchangeAccountInfo_", ExchangeAccountInfo.class, "dpcInfo_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ExchangeInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExchangeInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(ExchangeInfo.class, DEFAULT_INSTANCE);
        }

        public static ExchangeInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExchangeInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
