package logs.proto.wireless.performance.mobile;

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

public final class NetworkMetric {

    private NetworkMetric() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum RequestNegotiatedProtocol implements Internal.EnumLite {
        REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN(0),
        REQUEST_NEGOTIATED_PROTOCOL_HTTP11(1),
        REQUEST_NEGOTIATED_PROTOCOL_SPDY2(2),
        REQUEST_NEGOTIATED_PROTOCOL_SPDY3(3),
        REQUEST_NEGOTIATED_PROTOCOL_SPDY31(4),
        REQUEST_NEGOTIATED_PROTOCOL_SPDY4(5),
        REQUEST_NEGOTIATED_PROTOCOL_QUIC1_SPDY3(6);

        public static final int REQUEST_NEGOTIATED_PROTOCOL_HTTP11_VALUE = 1;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_QUIC1_SPDY3_VALUE = 6;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_SPDY2_VALUE = 2;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_SPDY31_VALUE = 4;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_SPDY3_VALUE = 3;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_SPDY4_VALUE = 5;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<RequestNegotiatedProtocol> internalValueMap = new Internal.EnumLiteMap<RequestNegotiatedProtocol>() {
            public RequestNegotiatedProtocol findValueByNumber(int number) {
                return RequestNegotiatedProtocol.forNumber(number);
            }
        };
        private final int value;

        private RequestNegotiatedProtocol(int value2) {
            this.value = value2;
        }

        public static RequestNegotiatedProtocol forNumber(int value2) {
            switch (value2) {
                case 0:
                    return REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN;
                case 1:
                    return REQUEST_NEGOTIATED_PROTOCOL_HTTP11;
                case 2:
                    return REQUEST_NEGOTIATED_PROTOCOL_SPDY2;
                case 3:
                    return REQUEST_NEGOTIATED_PROTOCOL_SPDY3;
                case 4:
                    return REQUEST_NEGOTIATED_PROTOCOL_SPDY31;
                case 5:
                    return REQUEST_NEGOTIATED_PROTOCOL_SPDY4;
                case 6:
                    return REQUEST_NEGOTIATED_PROTOCOL_QUIC1_SPDY3;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<RequestNegotiatedProtocol> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return RequestNegotiatedProtocolVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class RequestNegotiatedProtocolVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new RequestNegotiatedProtocolVerifier();

            private RequestNegotiatedProtocolVerifier() {
            }

            public boolean isInRange(int number) {
                return RequestNegotiatedProtocol.forNumber(number) != null;
            }
        }
    }

    public enum RequestStatus implements Internal.EnumLite {
        REQUEST_STATUS_UNSPECIFIED(0),
        SUCCEEDED(1),
        FAILED(2),
        CANCELED(3);

        public static final int CANCELED_VALUE = 3;
        public static final int FAILED_VALUE = 2;
        public static final int REQUEST_STATUS_UNSPECIFIED_VALUE = 0;
        public static final int SUCCEEDED_VALUE = 1;
        private static final Internal.EnumLiteMap<RequestStatus> internalValueMap = new Internal.EnumLiteMap<RequestStatus>() {
            public RequestStatus findValueByNumber(int number) {
                return RequestStatus.forNumber(number);
            }
        };
        private final int value;

        private RequestStatus(int value2) {
            this.value = value2;
        }

        public static RequestStatus forNumber(int value2) {
            if (value2 == 0) {
                return REQUEST_STATUS_UNSPECIFIED;
            }
            if (value2 == 1) {
                return SUCCEEDED;
            }
            if (value2 == 2) {
                return FAILED;
            }
            if (value2 != 3) {
                return null;
            }
            return CANCELED;
        }

        public static Internal.EnumLiteMap<RequestStatus> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return RequestStatusVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class RequestStatusVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new RequestStatusVerifier();

            private RequestStatusVerifier() {
            }

            public boolean isInRange(int number) {
                return RequestStatus.forNumber(number) != null;
            }
        }
    }

    public enum RequestFailedReason implements Internal.EnumLite {
        REQUEST_FAILED_REASON_UNSPECIFIED(0),
        LISTENER_EXCEPTION_THROWN(1),
        HOSTNAME_NOT_RESOLVED(2),
        INTERNET_DISCONNECTED(3),
        NETWORK_CHANGED(4),
        TIMED_OUT(5),
        CONNECTION_CLOSED(6),
        CONNECTION_TIMED_OUT(7),
        CONNECTION_REFUSED(8),
        CONNECTION_RESET(9),
        ADDRESS_UNREACHABLE(10),
        QUIC_PROTOCOL_FAILED(11),
        OTHER(12);

        public static final int ADDRESS_UNREACHABLE_VALUE = 10;
        public static final int CONNECTION_CLOSED_VALUE = 6;
        public static final int CONNECTION_REFUSED_VALUE = 8;
        public static final int CONNECTION_RESET_VALUE = 9;
        public static final int CONNECTION_TIMED_OUT_VALUE = 7;
        public static final int HOSTNAME_NOT_RESOLVED_VALUE = 2;
        public static final int INTERNET_DISCONNECTED_VALUE = 3;
        public static final int LISTENER_EXCEPTION_THROWN_VALUE = 1;
        public static final int NETWORK_CHANGED_VALUE = 4;
        public static final int OTHER_VALUE = 12;
        public static final int QUIC_PROTOCOL_FAILED_VALUE = 11;
        public static final int REQUEST_FAILED_REASON_UNSPECIFIED_VALUE = 0;
        public static final int TIMED_OUT_VALUE = 5;
        private static final Internal.EnumLiteMap<RequestFailedReason> internalValueMap = new Internal.EnumLiteMap<RequestFailedReason>() {
            public RequestFailedReason findValueByNumber(int number) {
                return RequestFailedReason.forNumber(number);
            }
        };
        private final int value;

        private RequestFailedReason(int value2) {
            this.value = value2;
        }

        public static RequestFailedReason forNumber(int value2) {
            switch (value2) {
                case 0:
                    return REQUEST_FAILED_REASON_UNSPECIFIED;
                case 1:
                    return LISTENER_EXCEPTION_THROWN;
                case 2:
                    return HOSTNAME_NOT_RESOLVED;
                case 3:
                    return INTERNET_DISCONNECTED;
                case 4:
                    return NETWORK_CHANGED;
                case 5:
                    return TIMED_OUT;
                case 6:
                    return CONNECTION_CLOSED;
                case 7:
                    return CONNECTION_TIMED_OUT;
                case 8:
                    return CONNECTION_REFUSED;
                case 9:
                    return CONNECTION_RESET;
                case 10:
                    return ADDRESS_UNREACHABLE;
                case 11:
                    return QUIC_PROTOCOL_FAILED;
                case 12:
                    return OTHER;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<RequestFailedReason> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return RequestFailedReasonVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class RequestFailedReasonVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new RequestFailedReasonVerifier();

            private RequestFailedReasonVerifier() {
            }

            public boolean isInRange(int number) {
                return RequestFailedReason.forNumber(number) != null;
            }
        }
    }

    public enum HttpMethod implements Internal.EnumLite {
        UNKNOWN_METHOD(0),
        GET(1),
        PUT(2),
        DELETE(3),
        POST(4),
        OPTIONS(5),
        HEAD(6),
        PATCH(7);

        public static final int DELETE_VALUE = 3;
        public static final int GET_VALUE = 1;
        public static final int HEAD_VALUE = 6;
        public static final int OPTIONS_VALUE = 5;
        public static final int PATCH_VALUE = 7;
        public static final int POST_VALUE = 4;
        public static final int PUT_VALUE = 2;
        public static final int UNKNOWN_METHOD_VALUE = 0;
        private static final Internal.EnumLiteMap<HttpMethod> internalValueMap = new Internal.EnumLiteMap<HttpMethod>() {
            public HttpMethod findValueByNumber(int number) {
                return HttpMethod.forNumber(number);
            }
        };
        private final int value;

        private HttpMethod(int value2) {
            this.value = value2;
        }

        public static HttpMethod forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNKNOWN_METHOD;
                case 1:
                    return GET;
                case 2:
                    return PUT;
                case 3:
                    return DELETE;
                case 4:
                    return POST;
                case 5:
                    return OPTIONS;
                case 6:
                    return HEAD;
                case 7:
                    return PATCH;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<HttpMethod> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return HttpMethodVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class HttpMethodVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new HttpMethodVerifier();

            private HttpMethodVerifier() {
            }

            public boolean isInRange(int number) {
                return HttpMethod.forNumber(number) != null;
            }
        }
    }

    public interface CacheStatsOrBuilder extends MessageLiteOrBuilder {
        int getHitCount();

        int getLookupCount();

        boolean hasHitCount();

        boolean hasLookupCount();
    }

    public interface NetworkConnectionInfoOrBuilder extends MessageLiteOrBuilder {
        NetworkConnectionInfo.NetworkType getNetworkType();

        boolean hasNetworkType();
    }

    public interface NetworkEventUsageOrBuilder extends MessageLiteOrBuilder {
        CacheStats getCacheStats();

        String getConstantRpcPath();

        ByteString getConstantRpcPathBytes();

        String getContentType();

        ByteString getContentTypeBytes();

        String getDomainPath();

        ByteString getDomainPathBytes();

        long getHashedRpcPath(int i);

        int getHashedRpcPathCount();

        List<Long> getHashedRpcPathList();

        HttpMethod getHttpMethod();

        int getHttpStatusCode();

        ExtensionMetric.MetricExtension getMetricExtension();

        NetworkConnectionInfo getNetworkConnectionInfo();

        NetworkEventUsage.NetworkingStack getNetworkingStack();

        PrimesScenarioProto.PrimesScenario getPrimesScenario(int i);

        int getPrimesScenarioCount();

        List<PrimesScenarioProto.PrimesScenario> getPrimesScenarioList();

        ProcessProto.AndroidProcessStats getProcessStats();

        int getQuicDetailedErrorCode();

        RequestFailedReason getRequestFailedReason();

        RequestNegotiatedProtocol getRequestNegotiatedProtocol();

        String getRequestPath();

        ByteString getRequestPathBytes();

        int getRequestSizeBytes();

        RequestStatus getRequestStatus();

        int getResponseSizeBytes();

        int getRetryCount();

        String getRpcPath();

        ByteString getRpcPathBytes();

        RpcStats getRpcStats();

        long getStartTimeMs();

        SubRequestData getSubRequest(int i);

        int getSubRequestCount();

        List<SubRequestData> getSubRequestList();

        int getTimeToResponseDataFinishMs();

        int getTimeToResponseHeaderMs();

        boolean hasCacheStats();

        boolean hasConstantRpcPath();

        boolean hasContentType();

        boolean hasDomainPath();

        boolean hasHttpMethod();

        boolean hasHttpStatusCode();

        boolean hasMetricExtension();

        boolean hasNetworkConnectionInfo();

        boolean hasNetworkingStack();

        boolean hasProcessStats();

        boolean hasQuicDetailedErrorCode();

        boolean hasRequestFailedReason();

        boolean hasRequestNegotiatedProtocol();

        boolean hasRequestPath();

        boolean hasRequestSizeBytes();

        boolean hasRequestStatus();

        boolean hasResponseSizeBytes();

        boolean hasRetryCount();

        boolean hasRpcPath();

        boolean hasRpcStats();

        boolean hasStartTimeMs();

        boolean hasTimeToResponseDataFinishMs();

        boolean hasTimeToResponseHeaderMs();
    }

    public interface NetworkUsageMetricOrBuilder extends MessageLiteOrBuilder {
        NetworkEventUsage getNetworkEventUsage(int i);

        int getNetworkEventUsageCount();

        List<NetworkEventUsage> getNetworkEventUsageList();

        @Deprecated
        ProcessProto.AndroidProcessStats getProcessStats();

        @Deprecated
        boolean hasProcessStats();
    }

    public interface RpcStatsOrBuilder extends MessageLiteOrBuilder {
        int getRpcStatusCode();

        boolean hasRpcStatusCode();
    }

    public interface SubRequestDataOrBuilder extends MessageLiteOrBuilder {
        String getRequestPath();

        ByteString getRequestPathBytes();

        boolean hasRequestPath();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class NetworkUsageMetric extends GeneratedMessageLite<NetworkUsageMetric, Builder> implements NetworkUsageMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final NetworkUsageMetric DEFAULT_INSTANCE = new NetworkUsageMetric();
        public static final int NETWORK_EVENT_USAGE_FIELD_NUMBER = 1;
        public static final int PROCESS_STATS_FIELD_NUMBER = 2;
        private static volatile Parser<NetworkUsageMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(NetworkUsageMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<NetworkEventUsage> networkEventUsage_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private ProcessProto.AndroidProcessStats processStats_;

        private NetworkUsageMetric() {
        }

        public static NetworkUsageMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkUsageMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkUsageMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkUsageMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkUsageMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkUsageMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkUsageMetric parseFrom(InputStream input) throws IOException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkUsageMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkUsageMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (NetworkUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkUsageMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkUsageMetric parseFrom(CodedInputStream input) throws IOException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkUsageMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NetworkUsageMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static NetworkUsageMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NetworkUsageMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public List<NetworkEventUsage> getNetworkEventUsageList() {
            return this.networkEventUsage_;
        }

        public List<? extends NetworkEventUsageOrBuilder> getNetworkEventUsageOrBuilderList() {
            return this.networkEventUsage_;
        }

        public int getNetworkEventUsageCount() {
            return this.networkEventUsage_.size();
        }

        public NetworkEventUsage getNetworkEventUsage(int index) {
            return this.networkEventUsage_.get(index);
        }

        public NetworkEventUsageOrBuilder getNetworkEventUsageOrBuilder(int index) {
            return this.networkEventUsage_.get(index);
        }

        private void ensureNetworkEventUsageIsMutable() {
            if (!this.networkEventUsage_.isModifiable()) {
                this.networkEventUsage_ = GeneratedMessageLite.mutableCopy(this.networkEventUsage_);
            }
        }

        /* access modifiers changed from: private */
        public void setNetworkEventUsage(int index, NetworkEventUsage value) {
            if (value != null) {
                ensureNetworkEventUsageIsMutable();
                this.networkEventUsage_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNetworkEventUsage(int index, NetworkEventUsage.Builder builderForValue) {
            ensureNetworkEventUsageIsMutable();
            this.networkEventUsage_.set(index, (NetworkEventUsage) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNetworkEventUsage(NetworkEventUsage value) {
            if (value != null) {
                ensureNetworkEventUsageIsMutable();
                this.networkEventUsage_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNetworkEventUsage(int index, NetworkEventUsage value) {
            if (value != null) {
                ensureNetworkEventUsageIsMutable();
                this.networkEventUsage_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNetworkEventUsage(NetworkEventUsage.Builder builderForValue) {
            ensureNetworkEventUsageIsMutable();
            this.networkEventUsage_.add((NetworkEventUsage) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNetworkEventUsage(int index, NetworkEventUsage.Builder builderForValue) {
            ensureNetworkEventUsageIsMutable();
            this.networkEventUsage_.add(index, (NetworkEventUsage) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.NetworkMetric$NetworkEventUsage>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.NetworkMetric$NetworkEventUsage>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllNetworkEventUsage(Iterable<? extends NetworkEventUsage> values) {
            ensureNetworkEventUsageIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.networkEventUsage_);
        }

        /* access modifiers changed from: private */
        public void clearNetworkEventUsage() {
            this.networkEventUsage_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeNetworkEventUsage(int index) {
            ensureNetworkEventUsageIsMutable();
            this.networkEventUsage_.remove(index);
        }

        @Deprecated
        public boolean hasProcessStats() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public ProcessProto.AndroidProcessStats getProcessStats() {
            ProcessProto.AndroidProcessStats androidProcessStats = this.processStats_;
            return androidProcessStats == null ? ProcessProto.AndroidProcessStats.getDefaultInstance() : androidProcessStats;
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.AndroidProcessStats value) {
            if (value != null) {
                this.processStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.AndroidProcessStats.Builder builderForValue) {
            this.processStats_ = (ProcessProto.AndroidProcessStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStats(ProcessProto.AndroidProcessStats value) {
            if (value != null) {
                ProcessProto.AndroidProcessStats androidProcessStats = this.processStats_;
                if (androidProcessStats == null || androidProcessStats == ProcessProto.AndroidProcessStats.getDefaultInstance()) {
                    this.processStats_ = value;
                } else {
                    this.processStats_ = (ProcessProto.AndroidProcessStats) ((ProcessProto.AndroidProcessStats.Builder) ProcessProto.AndroidProcessStats.newBuilder(this.processStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStats() {
            this.processStats_ = null;
            this.bitField0_ &= -2;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new NetworkUsageMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\t\u0000", new Object[]{"bitField0_", "networkEventUsage_", NetworkEventUsage.class, "processStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<NetworkUsageMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (NetworkUsageMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<NetworkUsageMetric, Builder> implements NetworkUsageMetricOrBuilder {
            private Builder() {
                super(NetworkUsageMetric.DEFAULT_INSTANCE);
            }

            public List<NetworkEventUsage> getNetworkEventUsageList() {
                return Collections.unmodifiableList(((NetworkUsageMetric) this.instance).getNetworkEventUsageList());
            }

            public int getNetworkEventUsageCount() {
                return ((NetworkUsageMetric) this.instance).getNetworkEventUsageCount();
            }

            public NetworkEventUsage getNetworkEventUsage(int index) {
                return ((NetworkUsageMetric) this.instance).getNetworkEventUsage(index);
            }

            public Builder setNetworkEventUsage(int index, NetworkEventUsage value) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).setNetworkEventUsage(index, value);
                return this;
            }

            public Builder setNetworkEventUsage(int index, NetworkEventUsage.Builder builderForValue) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).setNetworkEventUsage(index, builderForValue);
                return this;
            }

            public Builder addNetworkEventUsage(NetworkEventUsage value) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).addNetworkEventUsage(value);
                return this;
            }

            public Builder addNetworkEventUsage(int index, NetworkEventUsage value) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).addNetworkEventUsage(index, value);
                return this;
            }

            public Builder addNetworkEventUsage(NetworkEventUsage.Builder builderForValue) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).addNetworkEventUsage(builderForValue);
                return this;
            }

            public Builder addNetworkEventUsage(int index, NetworkEventUsage.Builder builderForValue) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).addNetworkEventUsage(index, builderForValue);
                return this;
            }

            public Builder addAllNetworkEventUsage(Iterable<? extends NetworkEventUsage> values) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).addAllNetworkEventUsage(values);
                return this;
            }

            public Builder clearNetworkEventUsage() {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).clearNetworkEventUsage();
                return this;
            }

            public Builder removeNetworkEventUsage(int index) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).removeNetworkEventUsage(index);
                return this;
            }

            @Deprecated
            public boolean hasProcessStats() {
                return ((NetworkUsageMetric) this.instance).hasProcessStats();
            }

            @Deprecated
            public ProcessProto.AndroidProcessStats getProcessStats() {
                return ((NetworkUsageMetric) this.instance).getProcessStats();
            }

            @Deprecated
            public Builder setProcessStats(ProcessProto.AndroidProcessStats value) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).setProcessStats(value);
                return this;
            }

            @Deprecated
            public Builder setProcessStats(ProcessProto.AndroidProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).setProcessStats(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeProcessStats(ProcessProto.AndroidProcessStats value) {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).mergeProcessStats(value);
                return this;
            }

            @Deprecated
            public Builder clearProcessStats() {
                copyOnWrite();
                ((NetworkUsageMetric) this.instance).clearProcessStats();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class NetworkEventUsage extends GeneratedMessageLite<NetworkEventUsage, Builder> implements NetworkEventUsageOrBuilder {
        public static final int CACHE_STATS_FIELD_NUMBER = 26;
        public static final int CONSTANT_RPC_PATH_FIELD_NUMBER = 25;
        public static final int CONTENT_TYPE_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final NetworkEventUsage DEFAULT_INSTANCE = new NetworkEventUsage();
        public static final int DOMAIN_PATH_FIELD_NUMBER = 20;
        public static final int HASHED_RPC_PATH_FIELD_NUMBER = 21;
        public static final int HTTP_METHOD_FIELD_NUMBER = 18;
        public static final int HTTP_STATUS_CODE_FIELD_NUMBER = 5;
        public static final int METRIC_EXTENSION_FIELD_NUMBER = 12;
        public static final int NETWORKING_STACK_FIELD_NUMBER = 24;
        public static final int NETWORK_CONNECTION_INFO_FIELD_NUMBER = 11;
        public static final int PRIMES_SCENARIO_FIELD_NUMBER = 23;
        public static final int PROCESS_STATS_FIELD_NUMBER = 10;
        public static final int QUIC_DETAILED_ERROR_CODE_FIELD_NUMBER = 19;
        public static final int REQUEST_FAILED_REASON_FIELD_NUMBER = 15;
        public static final int REQUEST_NEGOTIATED_PROTOCOL_FIELD_NUMBER = 8;
        public static final int REQUEST_PATH_FIELD_NUMBER = 2;
        public static final int REQUEST_SIZE_BYTES_FIELD_NUMBER = 7;
        public static final int REQUEST_STATUS_FIELD_NUMBER = 14;
        public static final int RESPONSE_SIZE_BYTES_FIELD_NUMBER = 6;
        public static final int RETRY_COUNT_FIELD_NUMBER = 16;
        public static final int RPC_PATH_FIELD_NUMBER = 17;
        public static final int RPC_STATS_FIELD_NUMBER = 22;
        public static final int START_TIME_MS_FIELD_NUMBER = 13;
        public static final int SUB_REQUEST_FIELD_NUMBER = 9;
        public static final int TIME_TO_RESPONSE_DATA_FINISH_MS_FIELD_NUMBER = 3;
        public static final int TIME_TO_RESPONSE_HEADER_MS_FIELD_NUMBER = 4;
        private static volatile Parser<NetworkEventUsage> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(NetworkEventUsage.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 26, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4194304, presenceBitsId = 0)
        private CacheStats cacheStats_;
        @ProtoField(fieldNumber = 25, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String constantRpcPath_ = "";
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String contentType_ = "";
        @ProtoField(fieldNumber = 20, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 0)
        private String domainPath_ = "";
        @ProtoField(fieldNumber = 21, type = FieldType.FIXED64_LIST)
        private Internal.LongList hashedRpcPath_ = emptyLongList();
        @ProtoField(fieldNumber = 18, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 0)
        private int httpMethod_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int httpStatusCode_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private ExtensionMetric.MetricExtension metricExtension_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private NetworkConnectionInfo networkConnectionInfo_;
        @ProtoField(fieldNumber = 24, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private int networkingStack_;
        @ProtoField(fieldNumber = 23, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<PrimesScenarioProto.PrimesScenario> primesScenario_ = emptyProtobufList();
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private ProcessProto.AndroidProcessStats processStats_;
        @ProtoField(fieldNumber = 19, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private int quicDetailedErrorCode_;
        @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private int requestFailedReason_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private int requestNegotiatedProtocol_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String requestPath_ = "";
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int requestSizeBytes_;
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private int requestStatus_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int responseSizeBytes_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private int retryCount_;
        @ProtoField(fieldNumber = 17, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private String rpcPath_ = "";
        @ProtoField(fieldNumber = 22, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2097152, presenceBitsId = 0)
        private RpcStats rpcStats_;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private long startTimeMs_;
        @ProtoField(fieldNumber = 9, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<SubRequestData> subRequest_ = emptyProtobufList();
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int timeToResponseDataFinishMs_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int timeToResponseHeaderMs_;

        private NetworkEventUsage() {
        }

        public static NetworkEventUsage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkEventUsage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkEventUsage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkEventUsage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkEventUsage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkEventUsage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkEventUsage parseFrom(InputStream input) throws IOException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkEventUsage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkEventUsage parseDelimitedFrom(InputStream input) throws IOException {
            return (NetworkEventUsage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkEventUsage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkEventUsage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkEventUsage parseFrom(CodedInputStream input) throws IOException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkEventUsage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkEventUsage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NetworkEventUsage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static NetworkEventUsage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NetworkEventUsage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasContentType() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getContentType() {
            return this.contentType_;
        }

        /* access modifiers changed from: private */
        public void setContentType(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.contentType_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getContentTypeBytes() {
            return ByteString.copyFromUtf8(this.contentType_);
        }

        /* access modifiers changed from: private */
        public void setContentTypeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.contentType_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearContentType() {
            this.bitField0_ &= -2;
            this.contentType_ = getDefaultInstance().getContentType();
        }

        public boolean hasRequestPath() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getRequestPath() {
            return this.requestPath_;
        }

        /* access modifiers changed from: private */
        public void setRequestPath(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.requestPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getRequestPathBytes() {
            return ByteString.copyFromUtf8(this.requestPath_);
        }

        /* access modifiers changed from: private */
        public void setRequestPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.requestPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestPath() {
            this.bitField0_ &= -3;
            this.requestPath_ = getDefaultInstance().getRequestPath();
        }

        public boolean hasConstantRpcPath() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getConstantRpcPath() {
            return this.constantRpcPath_;
        }

        /* access modifiers changed from: private */
        public void setConstantRpcPath(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.constantRpcPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getConstantRpcPathBytes() {
            return ByteString.copyFromUtf8(this.constantRpcPath_);
        }

        /* access modifiers changed from: private */
        public void setConstantRpcPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.constantRpcPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearConstantRpcPath() {
            this.bitField0_ &= -5;
            this.constantRpcPath_ = getDefaultInstance().getConstantRpcPath();
        }

        public boolean hasTimeToResponseDataFinishMs() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getTimeToResponseDataFinishMs() {
            return this.timeToResponseDataFinishMs_;
        }

        /* access modifiers changed from: private */
        public void setTimeToResponseDataFinishMs(int value) {
            this.bitField0_ |= 8;
            this.timeToResponseDataFinishMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimeToResponseDataFinishMs() {
            this.bitField0_ &= -9;
            this.timeToResponseDataFinishMs_ = 0;
        }

        public boolean hasTimeToResponseHeaderMs() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getTimeToResponseHeaderMs() {
            return this.timeToResponseHeaderMs_;
        }

        /* access modifiers changed from: private */
        public void setTimeToResponseHeaderMs(int value) {
            this.bitField0_ |= 16;
            this.timeToResponseHeaderMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimeToResponseHeaderMs() {
            this.bitField0_ &= -17;
            this.timeToResponseHeaderMs_ = 0;
        }

        public boolean hasHttpStatusCode() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getHttpStatusCode() {
            return this.httpStatusCode_;
        }

        /* access modifiers changed from: private */
        public void setHttpStatusCode(int value) {
            this.bitField0_ |= 32;
            this.httpStatusCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHttpStatusCode() {
            this.bitField0_ &= -33;
            this.httpStatusCode_ = 0;
        }

        public boolean hasResponseSizeBytes() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getResponseSizeBytes() {
            return this.responseSizeBytes_;
        }

        /* access modifiers changed from: private */
        public void setResponseSizeBytes(int value) {
            this.bitField0_ |= 64;
            this.responseSizeBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearResponseSizeBytes() {
            this.bitField0_ &= -65;
            this.responseSizeBytes_ = 0;
        }

        public boolean hasRequestSizeBytes() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getRequestSizeBytes() {
            return this.requestSizeBytes_;
        }

        /* access modifiers changed from: private */
        public void setRequestSizeBytes(int value) {
            this.bitField0_ |= 128;
            this.requestSizeBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRequestSizeBytes() {
            this.bitField0_ &= -129;
            this.requestSizeBytes_ = 0;
        }

        public boolean hasRequestNegotiatedProtocol() {
            return (this.bitField0_ & 256) != 0;
        }

        public RequestNegotiatedProtocol getRequestNegotiatedProtocol() {
            RequestNegotiatedProtocol result = RequestNegotiatedProtocol.forNumber(this.requestNegotiatedProtocol_);
            return result == null ? RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setRequestNegotiatedProtocol(RequestNegotiatedProtocol value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.requestNegotiatedProtocol_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestNegotiatedProtocol() {
            this.bitField0_ &= -257;
            this.requestNegotiatedProtocol_ = 0;
        }

        public List<SubRequestData> getSubRequestList() {
            return this.subRequest_;
        }

        public List<? extends SubRequestDataOrBuilder> getSubRequestOrBuilderList() {
            return this.subRequest_;
        }

        public int getSubRequestCount() {
            return this.subRequest_.size();
        }

        public SubRequestData getSubRequest(int index) {
            return this.subRequest_.get(index);
        }

        public SubRequestDataOrBuilder getSubRequestOrBuilder(int index) {
            return this.subRequest_.get(index);
        }

        private void ensureSubRequestIsMutable() {
            if (!this.subRequest_.isModifiable()) {
                this.subRequest_ = GeneratedMessageLite.mutableCopy(this.subRequest_);
            }
        }

        /* access modifiers changed from: private */
        public void setSubRequest(int index, SubRequestData value) {
            if (value != null) {
                ensureSubRequestIsMutable();
                this.subRequest_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSubRequest(int index, SubRequestData.Builder builderForValue) {
            ensureSubRequestIsMutable();
            this.subRequest_.set(index, (SubRequestData) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSubRequest(SubRequestData value) {
            if (value != null) {
                ensureSubRequestIsMutable();
                this.subRequest_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSubRequest(int index, SubRequestData value) {
            if (value != null) {
                ensureSubRequestIsMutable();
                this.subRequest_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSubRequest(SubRequestData.Builder builderForValue) {
            ensureSubRequestIsMutable();
            this.subRequest_.add((SubRequestData) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSubRequest(int index, SubRequestData.Builder builderForValue) {
            ensureSubRequestIsMutable();
            this.subRequest_.add(index, (SubRequestData) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.NetworkMetric$SubRequestData>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.NetworkMetric$SubRequestData>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSubRequest(Iterable<? extends SubRequestData> values) {
            ensureSubRequestIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.subRequest_);
        }

        /* access modifiers changed from: private */
        public void clearSubRequest() {
            this.subRequest_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSubRequest(int index) {
            ensureSubRequestIsMutable();
            this.subRequest_.remove(index);
        }

        public boolean hasProcessStats() {
            return (this.bitField0_ & 512) != 0;
        }

        public ProcessProto.AndroidProcessStats getProcessStats() {
            ProcessProto.AndroidProcessStats androidProcessStats = this.processStats_;
            return androidProcessStats == null ? ProcessProto.AndroidProcessStats.getDefaultInstance() : androidProcessStats;
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.AndroidProcessStats value) {
            if (value != null) {
                this.processStats_ = value;
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.AndroidProcessStats.Builder builderForValue) {
            this.processStats_ = (ProcessProto.AndroidProcessStats) builderForValue.build();
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStats(ProcessProto.AndroidProcessStats value) {
            if (value != null) {
                ProcessProto.AndroidProcessStats androidProcessStats = this.processStats_;
                if (androidProcessStats == null || androidProcessStats == ProcessProto.AndroidProcessStats.getDefaultInstance()) {
                    this.processStats_ = value;
                } else {
                    this.processStats_ = (ProcessProto.AndroidProcessStats) ((ProcessProto.AndroidProcessStats.Builder) ProcessProto.AndroidProcessStats.newBuilder(this.processStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStats() {
            this.processStats_ = null;
            this.bitField0_ &= -513;
        }

        public boolean hasNetworkingStack() {
            return (this.bitField0_ & 1024) != 0;
        }

        public NetworkingStack getNetworkingStack() {
            NetworkingStack result = NetworkingStack.forNumber(this.networkingStack_);
            return result == null ? NetworkingStack.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setNetworkingStack(NetworkingStack value) {
            if (value != null) {
                this.bitField0_ |= 1024;
                this.networkingStack_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkingStack() {
            this.bitField0_ &= -1025;
            this.networkingStack_ = 0;
        }

        public boolean hasNetworkConnectionInfo() {
            return (this.bitField0_ & 2048) != 0;
        }

        public NetworkConnectionInfo getNetworkConnectionInfo() {
            NetworkConnectionInfo networkConnectionInfo = this.networkConnectionInfo_;
            return networkConnectionInfo == null ? NetworkConnectionInfo.getDefaultInstance() : networkConnectionInfo;
        }

        /* access modifiers changed from: private */
        public void setNetworkConnectionInfo(NetworkConnectionInfo value) {
            if (value != null) {
                this.networkConnectionInfo_ = value;
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNetworkConnectionInfo(NetworkConnectionInfo.Builder builderForValue) {
            this.networkConnectionInfo_ = (NetworkConnectionInfo) builderForValue.build();
            this.bitField0_ |= 2048;
        }

        /* access modifiers changed from: private */
        public void mergeNetworkConnectionInfo(NetworkConnectionInfo value) {
            if (value != null) {
                NetworkConnectionInfo networkConnectionInfo = this.networkConnectionInfo_;
                if (networkConnectionInfo == null || networkConnectionInfo == NetworkConnectionInfo.getDefaultInstance()) {
                    this.networkConnectionInfo_ = value;
                } else {
                    this.networkConnectionInfo_ = (NetworkConnectionInfo) ((NetworkConnectionInfo.Builder) NetworkConnectionInfo.newBuilder(this.networkConnectionInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkConnectionInfo() {
            this.networkConnectionInfo_ = null;
            this.bitField0_ &= -2049;
        }

        public boolean hasMetricExtension() {
            return (this.bitField0_ & 4096) != 0;
        }

        public ExtensionMetric.MetricExtension getMetricExtension() {
            ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
            return metricExtension == null ? ExtensionMetric.MetricExtension.getDefaultInstance() : metricExtension;
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                this.metricExtension_ = value;
                this.bitField0_ |= 4096;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
            this.metricExtension_ = (ExtensionMetric.MetricExtension) builderForValue.build();
            this.bitField0_ |= 4096;
        }

        /* access modifiers changed from: private */
        public void mergeMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
                if (metricExtension == null || metricExtension == ExtensionMetric.MetricExtension.getDefaultInstance()) {
                    this.metricExtension_ = value;
                } else {
                    this.metricExtension_ = (ExtensionMetric.MetricExtension) ((ExtensionMetric.MetricExtension.Builder) ExtensionMetric.MetricExtension.newBuilder(this.metricExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4096;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMetricExtension() {
            this.metricExtension_ = null;
            this.bitField0_ &= -4097;
        }

        public boolean hasStartTimeMs() {
            return (this.bitField0_ & 8192) != 0;
        }

        public long getStartTimeMs() {
            return this.startTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setStartTimeMs(long value) {
            this.bitField0_ |= 8192;
            this.startTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartTimeMs() {
            this.bitField0_ &= -8193;
            this.startTimeMs_ = 0;
        }

        public boolean hasRequestStatus() {
            return (this.bitField0_ & 16384) != 0;
        }

        public RequestStatus getRequestStatus() {
            RequestStatus result = RequestStatus.forNumber(this.requestStatus_);
            return result == null ? RequestStatus.REQUEST_STATUS_UNSPECIFIED : result;
        }

        /* access modifiers changed from: private */
        public void setRequestStatus(RequestStatus value) {
            if (value != null) {
                this.bitField0_ |= 16384;
                this.requestStatus_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestStatus() {
            this.bitField0_ &= -16385;
            this.requestStatus_ = 0;
        }

        public boolean hasRequestFailedReason() {
            return (this.bitField0_ & 32768) != 0;
        }

        public RequestFailedReason getRequestFailedReason() {
            RequestFailedReason result = RequestFailedReason.forNumber(this.requestFailedReason_);
            return result == null ? RequestFailedReason.REQUEST_FAILED_REASON_UNSPECIFIED : result;
        }

        /* access modifiers changed from: private */
        public void setRequestFailedReason(RequestFailedReason value) {
            if (value != null) {
                this.bitField0_ |= 32768;
                this.requestFailedReason_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestFailedReason() {
            this.bitField0_ &= -32769;
            this.requestFailedReason_ = 0;
        }

        public boolean hasQuicDetailedErrorCode() {
            return (this.bitField0_ & 65536) != 0;
        }

        public int getQuicDetailedErrorCode() {
            return this.quicDetailedErrorCode_;
        }

        /* access modifiers changed from: private */
        public void setQuicDetailedErrorCode(int value) {
            this.bitField0_ |= 65536;
            this.quicDetailedErrorCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearQuicDetailedErrorCode() {
            this.bitField0_ &= -65537;
            this.quicDetailedErrorCode_ = 0;
        }

        public boolean hasRetryCount() {
            return (this.bitField0_ & 131072) != 0;
        }

        public int getRetryCount() {
            return this.retryCount_;
        }

        /* access modifiers changed from: private */
        public void setRetryCount(int value) {
            this.bitField0_ |= 131072;
            this.retryCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRetryCount() {
            this.bitField0_ &= -131073;
            this.retryCount_ = 0;
        }

        public boolean hasRpcPath() {
            return (this.bitField0_ & 262144) != 0;
        }

        public String getRpcPath() {
            return this.rpcPath_;
        }

        /* access modifiers changed from: private */
        public void setRpcPath(String value) {
            if (value != null) {
                this.bitField0_ |= 262144;
                this.rpcPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getRpcPathBytes() {
            return ByteString.copyFromUtf8(this.rpcPath_);
        }

        /* access modifiers changed from: private */
        public void setRpcPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 262144;
                this.rpcPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRpcPath() {
            this.bitField0_ &= -262145;
            this.rpcPath_ = getDefaultInstance().getRpcPath();
        }

        public List<Long> getHashedRpcPathList() {
            return this.hashedRpcPath_;
        }

        public int getHashedRpcPathCount() {
            return this.hashedRpcPath_.size();
        }

        public long getHashedRpcPath(int index) {
            return this.hashedRpcPath_.getLong(index);
        }

        private void ensureHashedRpcPathIsMutable() {
            if (!this.hashedRpcPath_.isModifiable()) {
                this.hashedRpcPath_ = GeneratedMessageLite.mutableCopy(this.hashedRpcPath_);
            }
        }

        /* access modifiers changed from: private */
        public void setHashedRpcPath(int index, long value) {
            ensureHashedRpcPathIsMutable();
            this.hashedRpcPath_.setLong(index, value);
        }

        /* access modifiers changed from: private */
        public void addHashedRpcPath(long value) {
            ensureHashedRpcPathIsMutable();
            this.hashedRpcPath_.addLong(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Long>, com.google.protobuf.Internal$LongList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllHashedRpcPath(Iterable<? extends Long> values) {
            ensureHashedRpcPathIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.hashedRpcPath_);
        }

        /* access modifiers changed from: private */
        public void clearHashedRpcPath() {
            this.hashedRpcPath_ = emptyLongList();
        }

        public boolean hasHttpMethod() {
            return (this.bitField0_ & 524288) != 0;
        }

        public HttpMethod getHttpMethod() {
            HttpMethod result = HttpMethod.forNumber(this.httpMethod_);
            return result == null ? HttpMethod.UNKNOWN_METHOD : result;
        }

        /* access modifiers changed from: private */
        public void setHttpMethod(HttpMethod value) {
            if (value != null) {
                this.bitField0_ |= 524288;
                this.httpMethod_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearHttpMethod() {
            this.bitField0_ &= -524289;
            this.httpMethod_ = 0;
        }

        public boolean hasDomainPath() {
            return (this.bitField0_ & 1048576) != 0;
        }

        public String getDomainPath() {
            return this.domainPath_;
        }

        /* access modifiers changed from: private */
        public void setDomainPath(String value) {
            if (value != null) {
                this.bitField0_ |= 1048576;
                this.domainPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getDomainPathBytes() {
            return ByteString.copyFromUtf8(this.domainPath_);
        }

        /* access modifiers changed from: private */
        public void setDomainPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1048576;
                this.domainPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDomainPath() {
            this.bitField0_ &= -1048577;
            this.domainPath_ = getDefaultInstance().getDomainPath();
        }

        public boolean hasRpcStats() {
            return (this.bitField0_ & 2097152) != 0;
        }

        public RpcStats getRpcStats() {
            RpcStats rpcStats = this.rpcStats_;
            return rpcStats == null ? RpcStats.getDefaultInstance() : rpcStats;
        }

        /* access modifiers changed from: private */
        public void setRpcStats(RpcStats value) {
            if (value != null) {
                this.rpcStats_ = value;
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setRpcStats(RpcStats.Builder builderForValue) {
            this.rpcStats_ = (RpcStats) builderForValue.build();
            this.bitField0_ |= 2097152;
        }

        /* access modifiers changed from: private */
        public void mergeRpcStats(RpcStats value) {
            if (value != null) {
                RpcStats rpcStats = this.rpcStats_;
                if (rpcStats == null || rpcStats == RpcStats.getDefaultInstance()) {
                    this.rpcStats_ = value;
                } else {
                    this.rpcStats_ = (RpcStats) ((RpcStats.Builder) RpcStats.newBuilder(this.rpcStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRpcStats() {
            this.rpcStats_ = null;
            this.bitField0_ &= -2097153;
        }

        public List<PrimesScenarioProto.PrimesScenario> getPrimesScenarioList() {
            return this.primesScenario_;
        }

        public List<? extends PrimesScenarioProto.PrimesScenarioOrBuilder> getPrimesScenarioOrBuilderList() {
            return this.primesScenario_;
        }

        public int getPrimesScenarioCount() {
            return this.primesScenario_.size();
        }

        public PrimesScenarioProto.PrimesScenario getPrimesScenario(int index) {
            return this.primesScenario_.get(index);
        }

        public PrimesScenarioProto.PrimesScenarioOrBuilder getPrimesScenarioOrBuilder(int index) {
            return this.primesScenario_.get(index);
        }

        private void ensurePrimesScenarioIsMutable() {
            if (!this.primesScenario_.isModifiable()) {
                this.primesScenario_ = GeneratedMessageLite.mutableCopy(this.primesScenario_);
            }
        }

        /* access modifiers changed from: private */
        public void setPrimesScenario(int index, PrimesScenarioProto.PrimesScenario value) {
            if (value != null) {
                ensurePrimesScenarioIsMutable();
                this.primesScenario_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesScenario(int index, PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
            ensurePrimesScenarioIsMutable();
            this.primesScenario_.set(index, (PrimesScenarioProto.PrimesScenario) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrimesScenario(PrimesScenarioProto.PrimesScenario value) {
            if (value != null) {
                ensurePrimesScenarioIsMutable();
                this.primesScenario_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrimesScenario(int index, PrimesScenarioProto.PrimesScenario value) {
            if (value != null) {
                ensurePrimesScenarioIsMutable();
                this.primesScenario_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrimesScenario(PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
            ensurePrimesScenarioIsMutable();
            this.primesScenario_.add((PrimesScenarioProto.PrimesScenario) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrimesScenario(int index, PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
            ensurePrimesScenarioIsMutable();
            this.primesScenario_.add(index, (PrimesScenarioProto.PrimesScenario) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesScenarioProto$PrimesScenario>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesScenarioProto$PrimesScenario>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPrimesScenario(Iterable<? extends PrimesScenarioProto.PrimesScenario> values) {
            ensurePrimesScenarioIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.primesScenario_);
        }

        /* access modifiers changed from: private */
        public void clearPrimesScenario() {
            this.primesScenario_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removePrimesScenario(int index) {
            ensurePrimesScenarioIsMutable();
            this.primesScenario_.remove(index);
        }

        public boolean hasCacheStats() {
            return (this.bitField0_ & 4194304) != 0;
        }

        public CacheStats getCacheStats() {
            CacheStats cacheStats = this.cacheStats_;
            return cacheStats == null ? CacheStats.getDefaultInstance() : cacheStats;
        }

        /* access modifiers changed from: private */
        public void setCacheStats(CacheStats value) {
            if (value != null) {
                this.cacheStats_ = value;
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCacheStats(CacheStats.Builder builderForValue) {
            this.cacheStats_ = (CacheStats) builderForValue.build();
            this.bitField0_ |= 4194304;
        }

        /* access modifiers changed from: private */
        public void mergeCacheStats(CacheStats value) {
            if (value != null) {
                CacheStats cacheStats = this.cacheStats_;
                if (cacheStats == null || cacheStats == CacheStats.getDefaultInstance()) {
                    this.cacheStats_ = value;
                } else {
                    this.cacheStats_ = (CacheStats) ((CacheStats.Builder) CacheStats.newBuilder(this.cacheStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCacheStats() {
            this.cacheStats_ = null;
            this.bitField0_ &= -4194305;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new NetworkEventUsage();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u001a\u0000\u0001\u0001\u001a\u001a\u0000\u0003\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0004\u0003\u0004\u0004\u0004\u0005\u0004\u0005\u0006\u000b\u0006\u0007\u000b\u0007\b\f\b\t\u001b\n\t\t\u000b\t\u000b\f\t\f\r\u0002\r\u000e\f\u000e\u000f\f\u000f\u0010\u0004\u0011\u0011\b\u0012\u0012\f\u0013\u0013\u0004\u0010\u0014\b\u0014\u0015\u0017\u0016\t\u0015\u0017\u001b\u0018\f\n\u0019\b\u0002\u001a\t\u0016", new Object[]{"bitField0_", "contentType_", "requestPath_", "timeToResponseDataFinishMs_", "timeToResponseHeaderMs_", "httpStatusCode_", "responseSizeBytes_", "requestSizeBytes_", "requestNegotiatedProtocol_", RequestNegotiatedProtocol.internalGetVerifier(), "subRequest_", SubRequestData.class, "processStats_", "networkConnectionInfo_", "metricExtension_", "startTimeMs_", "requestStatus_", RequestStatus.internalGetVerifier(), "requestFailedReason_", RequestFailedReason.internalGetVerifier(), "retryCount_", "rpcPath_", "httpMethod_", HttpMethod.internalGetVerifier(), "quicDetailedErrorCode_", "domainPath_", "hashedRpcPath_", "rpcStats_", "primesScenario_", PrimesScenarioProto.PrimesScenario.class, "networkingStack_", NetworkingStack.internalGetVerifier(), "constantRpcPath_", "cacheStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<NetworkEventUsage> parser = PARSER;
                    if (parser == null) {
                        synchronized (NetworkEventUsage.class) {
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

        public enum NetworkingStack implements Internal.EnumLite {
            UNKNOWN(0),
            CRONET(1);

            public static final int CRONET_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<NetworkingStack> internalValueMap = new Internal.EnumLiteMap<NetworkingStack>() {
                public NetworkingStack findValueByNumber(int number) {
                    return NetworkingStack.forNumber(number);
                }
            };
            private final int value;

            private NetworkingStack(int value2) {
                this.value = value2;
            }

            public static NetworkingStack forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 != 1) {
                    return null;
                }
                return CRONET;
            }

            public static Internal.EnumLiteMap<NetworkingStack> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return NetworkingStackVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class NetworkingStackVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new NetworkingStackVerifier();

                private NetworkingStackVerifier() {
                }

                public boolean isInRange(int number) {
                    return NetworkingStack.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NetworkEventUsage, Builder> implements NetworkEventUsageOrBuilder {
            private Builder() {
                super(NetworkEventUsage.DEFAULT_INSTANCE);
            }

            public boolean hasContentType() {
                return ((NetworkEventUsage) this.instance).hasContentType();
            }

            public String getContentType() {
                return ((NetworkEventUsage) this.instance).getContentType();
            }

            public Builder setContentType(String value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setContentType(value);
                return this;
            }

            public ByteString getContentTypeBytes() {
                return ((NetworkEventUsage) this.instance).getContentTypeBytes();
            }

            public Builder setContentTypeBytes(ByteString value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setContentTypeBytes(value);
                return this;
            }

            public Builder clearContentType() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearContentType();
                return this;
            }

            public boolean hasRequestPath() {
                return ((NetworkEventUsage) this.instance).hasRequestPath();
            }

            public String getRequestPath() {
                return ((NetworkEventUsage) this.instance).getRequestPath();
            }

            public Builder setRequestPath(String value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestPath(value);
                return this;
            }

            public ByteString getRequestPathBytes() {
                return ((NetworkEventUsage) this.instance).getRequestPathBytes();
            }

            public Builder setRequestPathBytes(ByteString value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestPathBytes(value);
                return this;
            }

            public Builder clearRequestPath() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRequestPath();
                return this;
            }

            public boolean hasConstantRpcPath() {
                return ((NetworkEventUsage) this.instance).hasConstantRpcPath();
            }

            public String getConstantRpcPath() {
                return ((NetworkEventUsage) this.instance).getConstantRpcPath();
            }

            public Builder setConstantRpcPath(String value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setConstantRpcPath(value);
                return this;
            }

            public ByteString getConstantRpcPathBytes() {
                return ((NetworkEventUsage) this.instance).getConstantRpcPathBytes();
            }

            public Builder setConstantRpcPathBytes(ByteString value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setConstantRpcPathBytes(value);
                return this;
            }

            public Builder clearConstantRpcPath() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearConstantRpcPath();
                return this;
            }

            public boolean hasTimeToResponseDataFinishMs() {
                return ((NetworkEventUsage) this.instance).hasTimeToResponseDataFinishMs();
            }

            public int getTimeToResponseDataFinishMs() {
                return ((NetworkEventUsage) this.instance).getTimeToResponseDataFinishMs();
            }

            public Builder setTimeToResponseDataFinishMs(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setTimeToResponseDataFinishMs(value);
                return this;
            }

            public Builder clearTimeToResponseDataFinishMs() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearTimeToResponseDataFinishMs();
                return this;
            }

            public boolean hasTimeToResponseHeaderMs() {
                return ((NetworkEventUsage) this.instance).hasTimeToResponseHeaderMs();
            }

            public int getTimeToResponseHeaderMs() {
                return ((NetworkEventUsage) this.instance).getTimeToResponseHeaderMs();
            }

            public Builder setTimeToResponseHeaderMs(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setTimeToResponseHeaderMs(value);
                return this;
            }

            public Builder clearTimeToResponseHeaderMs() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearTimeToResponseHeaderMs();
                return this;
            }

            public boolean hasHttpStatusCode() {
                return ((NetworkEventUsage) this.instance).hasHttpStatusCode();
            }

            public int getHttpStatusCode() {
                return ((NetworkEventUsage) this.instance).getHttpStatusCode();
            }

            public Builder setHttpStatusCode(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setHttpStatusCode(value);
                return this;
            }

            public Builder clearHttpStatusCode() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearHttpStatusCode();
                return this;
            }

            public boolean hasResponseSizeBytes() {
                return ((NetworkEventUsage) this.instance).hasResponseSizeBytes();
            }

            public int getResponseSizeBytes() {
                return ((NetworkEventUsage) this.instance).getResponseSizeBytes();
            }

            public Builder setResponseSizeBytes(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setResponseSizeBytes(value);
                return this;
            }

            public Builder clearResponseSizeBytes() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearResponseSizeBytes();
                return this;
            }

            public boolean hasRequestSizeBytes() {
                return ((NetworkEventUsage) this.instance).hasRequestSizeBytes();
            }

            public int getRequestSizeBytes() {
                return ((NetworkEventUsage) this.instance).getRequestSizeBytes();
            }

            public Builder setRequestSizeBytes(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestSizeBytes(value);
                return this;
            }

            public Builder clearRequestSizeBytes() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRequestSizeBytes();
                return this;
            }

            public boolean hasRequestNegotiatedProtocol() {
                return ((NetworkEventUsage) this.instance).hasRequestNegotiatedProtocol();
            }

            public RequestNegotiatedProtocol getRequestNegotiatedProtocol() {
                return ((NetworkEventUsage) this.instance).getRequestNegotiatedProtocol();
            }

            public Builder setRequestNegotiatedProtocol(RequestNegotiatedProtocol value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestNegotiatedProtocol(value);
                return this;
            }

            public Builder clearRequestNegotiatedProtocol() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRequestNegotiatedProtocol();
                return this;
            }

            public List<SubRequestData> getSubRequestList() {
                return Collections.unmodifiableList(((NetworkEventUsage) this.instance).getSubRequestList());
            }

            public int getSubRequestCount() {
                return ((NetworkEventUsage) this.instance).getSubRequestCount();
            }

            public SubRequestData getSubRequest(int index) {
                return ((NetworkEventUsage) this.instance).getSubRequest(index);
            }

            public Builder setSubRequest(int index, SubRequestData value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setSubRequest(index, value);
                return this;
            }

            public Builder setSubRequest(int index, SubRequestData.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setSubRequest(index, builderForValue);
                return this;
            }

            public Builder addSubRequest(SubRequestData value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addSubRequest(value);
                return this;
            }

            public Builder addSubRequest(int index, SubRequestData value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addSubRequest(index, value);
                return this;
            }

            public Builder addSubRequest(SubRequestData.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addSubRequest(builderForValue);
                return this;
            }

            public Builder addSubRequest(int index, SubRequestData.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addSubRequest(index, builderForValue);
                return this;
            }

            public Builder addAllSubRequest(Iterable<? extends SubRequestData> values) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addAllSubRequest(values);
                return this;
            }

            public Builder clearSubRequest() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearSubRequest();
                return this;
            }

            public Builder removeSubRequest(int index) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).removeSubRequest(index);
                return this;
            }

            public boolean hasProcessStats() {
                return ((NetworkEventUsage) this.instance).hasProcessStats();
            }

            public ProcessProto.AndroidProcessStats getProcessStats() {
                return ((NetworkEventUsage) this.instance).getProcessStats();
            }

            public Builder setProcessStats(ProcessProto.AndroidProcessStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setProcessStats(value);
                return this;
            }

            public Builder setProcessStats(ProcessProto.AndroidProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setProcessStats(builderForValue);
                return this;
            }

            public Builder mergeProcessStats(ProcessProto.AndroidProcessStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).mergeProcessStats(value);
                return this;
            }

            public Builder clearProcessStats() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearProcessStats();
                return this;
            }

            public boolean hasNetworkingStack() {
                return ((NetworkEventUsage) this.instance).hasNetworkingStack();
            }

            public NetworkingStack getNetworkingStack() {
                return ((NetworkEventUsage) this.instance).getNetworkingStack();
            }

            public Builder setNetworkingStack(NetworkingStack value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setNetworkingStack(value);
                return this;
            }

            public Builder clearNetworkingStack() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearNetworkingStack();
                return this;
            }

            public boolean hasNetworkConnectionInfo() {
                return ((NetworkEventUsage) this.instance).hasNetworkConnectionInfo();
            }

            public NetworkConnectionInfo getNetworkConnectionInfo() {
                return ((NetworkEventUsage) this.instance).getNetworkConnectionInfo();
            }

            public Builder setNetworkConnectionInfo(NetworkConnectionInfo value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setNetworkConnectionInfo(value);
                return this;
            }

            public Builder setNetworkConnectionInfo(NetworkConnectionInfo.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setNetworkConnectionInfo(builderForValue);
                return this;
            }

            public Builder mergeNetworkConnectionInfo(NetworkConnectionInfo value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).mergeNetworkConnectionInfo(value);
                return this;
            }

            public Builder clearNetworkConnectionInfo() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearNetworkConnectionInfo();
                return this;
            }

            public boolean hasMetricExtension() {
                return ((NetworkEventUsage) this.instance).hasMetricExtension();
            }

            public ExtensionMetric.MetricExtension getMetricExtension() {
                return ((NetworkEventUsage) this.instance).getMetricExtension();
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setMetricExtension(value);
                return this;
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).mergeMetricExtension(value);
                return this;
            }

            public Builder clearMetricExtension() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearMetricExtension();
                return this;
            }

            public boolean hasStartTimeMs() {
                return ((NetworkEventUsage) this.instance).hasStartTimeMs();
            }

            public long getStartTimeMs() {
                return ((NetworkEventUsage) this.instance).getStartTimeMs();
            }

            public Builder setStartTimeMs(long value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setStartTimeMs(value);
                return this;
            }

            public Builder clearStartTimeMs() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearStartTimeMs();
                return this;
            }

            public boolean hasRequestStatus() {
                return ((NetworkEventUsage) this.instance).hasRequestStatus();
            }

            public RequestStatus getRequestStatus() {
                return ((NetworkEventUsage) this.instance).getRequestStatus();
            }

            public Builder setRequestStatus(RequestStatus value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestStatus(value);
                return this;
            }

            public Builder clearRequestStatus() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRequestStatus();
                return this;
            }

            public boolean hasRequestFailedReason() {
                return ((NetworkEventUsage) this.instance).hasRequestFailedReason();
            }

            public RequestFailedReason getRequestFailedReason() {
                return ((NetworkEventUsage) this.instance).getRequestFailedReason();
            }

            public Builder setRequestFailedReason(RequestFailedReason value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRequestFailedReason(value);
                return this;
            }

            public Builder clearRequestFailedReason() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRequestFailedReason();
                return this;
            }

            public boolean hasQuicDetailedErrorCode() {
                return ((NetworkEventUsage) this.instance).hasQuicDetailedErrorCode();
            }

            public int getQuicDetailedErrorCode() {
                return ((NetworkEventUsage) this.instance).getQuicDetailedErrorCode();
            }

            public Builder setQuicDetailedErrorCode(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setQuicDetailedErrorCode(value);
                return this;
            }

            public Builder clearQuicDetailedErrorCode() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearQuicDetailedErrorCode();
                return this;
            }

            public boolean hasRetryCount() {
                return ((NetworkEventUsage) this.instance).hasRetryCount();
            }

            public int getRetryCount() {
                return ((NetworkEventUsage) this.instance).getRetryCount();
            }

            public Builder setRetryCount(int value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRetryCount(value);
                return this;
            }

            public Builder clearRetryCount() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRetryCount();
                return this;
            }

            public boolean hasRpcPath() {
                return ((NetworkEventUsage) this.instance).hasRpcPath();
            }

            public String getRpcPath() {
                return ((NetworkEventUsage) this.instance).getRpcPath();
            }

            public Builder setRpcPath(String value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRpcPath(value);
                return this;
            }

            public ByteString getRpcPathBytes() {
                return ((NetworkEventUsage) this.instance).getRpcPathBytes();
            }

            public Builder setRpcPathBytes(ByteString value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRpcPathBytes(value);
                return this;
            }

            public Builder clearRpcPath() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRpcPath();
                return this;
            }

            public List<Long> getHashedRpcPathList() {
                return Collections.unmodifiableList(((NetworkEventUsage) this.instance).getHashedRpcPathList());
            }

            public int getHashedRpcPathCount() {
                return ((NetworkEventUsage) this.instance).getHashedRpcPathCount();
            }

            public long getHashedRpcPath(int index) {
                return ((NetworkEventUsage) this.instance).getHashedRpcPath(index);
            }

            public Builder setHashedRpcPath(int index, long value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setHashedRpcPath(index, value);
                return this;
            }

            public Builder addHashedRpcPath(long value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addHashedRpcPath(value);
                return this;
            }

            public Builder addAllHashedRpcPath(Iterable<? extends Long> values) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addAllHashedRpcPath(values);
                return this;
            }

            public Builder clearHashedRpcPath() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearHashedRpcPath();
                return this;
            }

            public boolean hasHttpMethod() {
                return ((NetworkEventUsage) this.instance).hasHttpMethod();
            }

            public HttpMethod getHttpMethod() {
                return ((NetworkEventUsage) this.instance).getHttpMethod();
            }

            public Builder setHttpMethod(HttpMethod value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setHttpMethod(value);
                return this;
            }

            public Builder clearHttpMethod() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearHttpMethod();
                return this;
            }

            public boolean hasDomainPath() {
                return ((NetworkEventUsage) this.instance).hasDomainPath();
            }

            public String getDomainPath() {
                return ((NetworkEventUsage) this.instance).getDomainPath();
            }

            public Builder setDomainPath(String value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setDomainPath(value);
                return this;
            }

            public ByteString getDomainPathBytes() {
                return ((NetworkEventUsage) this.instance).getDomainPathBytes();
            }

            public Builder setDomainPathBytes(ByteString value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setDomainPathBytes(value);
                return this;
            }

            public Builder clearDomainPath() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearDomainPath();
                return this;
            }

            public boolean hasRpcStats() {
                return ((NetworkEventUsage) this.instance).hasRpcStats();
            }

            public RpcStats getRpcStats() {
                return ((NetworkEventUsage) this.instance).getRpcStats();
            }

            public Builder setRpcStats(RpcStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRpcStats(value);
                return this;
            }

            public Builder setRpcStats(RpcStats.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setRpcStats(builderForValue);
                return this;
            }

            public Builder mergeRpcStats(RpcStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).mergeRpcStats(value);
                return this;
            }

            public Builder clearRpcStats() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearRpcStats();
                return this;
            }

            public List<PrimesScenarioProto.PrimesScenario> getPrimesScenarioList() {
                return Collections.unmodifiableList(((NetworkEventUsage) this.instance).getPrimesScenarioList());
            }

            public int getPrimesScenarioCount() {
                return ((NetworkEventUsage) this.instance).getPrimesScenarioCount();
            }

            public PrimesScenarioProto.PrimesScenario getPrimesScenario(int index) {
                return ((NetworkEventUsage) this.instance).getPrimesScenario(index);
            }

            public Builder setPrimesScenario(int index, PrimesScenarioProto.PrimesScenario value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setPrimesScenario(index, value);
                return this;
            }

            public Builder setPrimesScenario(int index, PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setPrimesScenario(index, builderForValue);
                return this;
            }

            public Builder addPrimesScenario(PrimesScenarioProto.PrimesScenario value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addPrimesScenario(value);
                return this;
            }

            public Builder addPrimesScenario(int index, PrimesScenarioProto.PrimesScenario value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addPrimesScenario(index, value);
                return this;
            }

            public Builder addPrimesScenario(PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addPrimesScenario(builderForValue);
                return this;
            }

            public Builder addPrimesScenario(int index, PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addPrimesScenario(index, builderForValue);
                return this;
            }

            public Builder addAllPrimesScenario(Iterable<? extends PrimesScenarioProto.PrimesScenario> values) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).addAllPrimesScenario(values);
                return this;
            }

            public Builder clearPrimesScenario() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearPrimesScenario();
                return this;
            }

            public Builder removePrimesScenario(int index) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).removePrimesScenario(index);
                return this;
            }

            public boolean hasCacheStats() {
                return ((NetworkEventUsage) this.instance).hasCacheStats();
            }

            public CacheStats getCacheStats() {
                return ((NetworkEventUsage) this.instance).getCacheStats();
            }

            public Builder setCacheStats(CacheStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setCacheStats(value);
                return this;
            }

            public Builder setCacheStats(CacheStats.Builder builderForValue) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).setCacheStats(builderForValue);
                return this;
            }

            public Builder mergeCacheStats(CacheStats value) {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).mergeCacheStats(value);
                return this;
            }

            public Builder clearCacheStats() {
                copyOnWrite();
                ((NetworkEventUsage) this.instance).clearCacheStats();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SubRequestData extends GeneratedMessageLite<SubRequestData, Builder> implements SubRequestDataOrBuilder {
        /* access modifiers changed from: private */
        public static final SubRequestData DEFAULT_INSTANCE = new SubRequestData();
        public static final int REQUEST_PATH_FIELD_NUMBER = 1;
        private static volatile Parser<SubRequestData> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(SubRequestData.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String requestPath_ = "";

        private SubRequestData() {
        }

        public static SubRequestData parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SubRequestData parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SubRequestData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SubRequestData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SubRequestData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SubRequestData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SubRequestData parseFrom(InputStream input) throws IOException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SubRequestData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SubRequestData parseDelimitedFrom(InputStream input) throws IOException {
            return (SubRequestData) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SubRequestData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SubRequestData) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SubRequestData parseFrom(CodedInputStream input) throws IOException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SubRequestData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SubRequestData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SubRequestData prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static SubRequestData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SubRequestData> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasRequestPath() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getRequestPath() {
            return this.requestPath_;
        }

        /* access modifiers changed from: private */
        public void setRequestPath(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.requestPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getRequestPathBytes() {
            return ByteString.copyFromUtf8(this.requestPath_);
        }

        /* access modifiers changed from: private */
        public void setRequestPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.requestPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRequestPath() {
            this.bitField0_ &= -2;
            this.requestPath_ = getDefaultInstance().getRequestPath();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SubRequestData();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000", new Object[]{"bitField0_", "requestPath_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SubRequestData> parser = PARSER;
                    if (parser == null) {
                        synchronized (SubRequestData.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<SubRequestData, Builder> implements SubRequestDataOrBuilder {
            private Builder() {
                super(SubRequestData.DEFAULT_INSTANCE);
            }

            public boolean hasRequestPath() {
                return ((SubRequestData) this.instance).hasRequestPath();
            }

            public String getRequestPath() {
                return ((SubRequestData) this.instance).getRequestPath();
            }

            public Builder setRequestPath(String value) {
                copyOnWrite();
                ((SubRequestData) this.instance).setRequestPath(value);
                return this;
            }

            public ByteString getRequestPathBytes() {
                return ((SubRequestData) this.instance).getRequestPathBytes();
            }

            public Builder setRequestPathBytes(ByteString value) {
                copyOnWrite();
                ((SubRequestData) this.instance).setRequestPathBytes(value);
                return this;
            }

            public Builder clearRequestPath() {
                copyOnWrite();
                ((SubRequestData) this.instance).clearRequestPath();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class NetworkConnectionInfo extends GeneratedMessageLite<NetworkConnectionInfo, Builder> implements NetworkConnectionInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final NetworkConnectionInfo DEFAULT_INSTANCE = new NetworkConnectionInfo();
        public static final int NETWORK_TYPE_FIELD_NUMBER = 1;
        private static volatile Parser<NetworkConnectionInfo> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(NetworkConnectionInfo.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int networkType_ = -1;

        private NetworkConnectionInfo() {
        }

        public static NetworkConnectionInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkConnectionInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkConnectionInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkConnectionInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkConnectionInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NetworkConnectionInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NetworkConnectionInfo parseFrom(InputStream input) throws IOException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkConnectionInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkConnectionInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (NetworkConnectionInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkConnectionInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkConnectionInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NetworkConnectionInfo parseFrom(CodedInputStream input) throws IOException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NetworkConnectionInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NetworkConnectionInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NetworkConnectionInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static NetworkConnectionInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NetworkConnectionInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasNetworkType() {
            return (this.bitField0_ & 1) != 0;
        }

        public NetworkType getNetworkType() {
            NetworkType result = NetworkType.forNumber(this.networkType_);
            return result == null ? NetworkType.NONE : result;
        }

        /* access modifiers changed from: private */
        public void setNetworkType(NetworkType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.networkType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkType() {
            this.bitField0_ &= -2;
            this.networkType_ = -1;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new NetworkConnectionInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\f\u0000", new Object[]{"bitField0_", "networkType_", NetworkType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<NetworkConnectionInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (NetworkConnectionInfo.class) {
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

        public enum NetworkType implements Internal.EnumLite {
            NONE(-1),
            MOBILE(0),
            WIFI(1),
            MOBILE_MMS(2),
            MOBILE_SUPL(3),
            MOBILE_DUN(4),
            MOBILE_HIPRI(5),
            WIMAX(6),
            BLUETOOTH(7),
            DUMMY(8),
            ETHERNET(9),
            MOBILE_FOTA(10),
            MOBILE_IMS(11),
            MOBILE_CBS(12),
            WIFI_P2P(13),
            MOBILE_IA(14),
            MOBILE_EMERGENCY(15),
            PROXY(16),
            VPN(17);

            public static final int BLUETOOTH_VALUE = 7;
            public static final int DUMMY_VALUE = 8;
            public static final int ETHERNET_VALUE = 9;
            public static final int MOBILE_CBS_VALUE = 12;
            public static final int MOBILE_DUN_VALUE = 4;
            public static final int MOBILE_EMERGENCY_VALUE = 15;
            public static final int MOBILE_FOTA_VALUE = 10;
            public static final int MOBILE_HIPRI_VALUE = 5;
            public static final int MOBILE_IA_VALUE = 14;
            public static final int MOBILE_IMS_VALUE = 11;
            public static final int MOBILE_MMS_VALUE = 2;
            public static final int MOBILE_SUPL_VALUE = 3;
            public static final int MOBILE_VALUE = 0;
            public static final int NONE_VALUE = -1;
            public static final int PROXY_VALUE = 16;
            public static final int VPN_VALUE = 17;
            public static final int WIFI_P2P_VALUE = 13;
            public static final int WIFI_VALUE = 1;
            public static final int WIMAX_VALUE = 6;
            private static final Internal.EnumLiteMap<NetworkType> internalValueMap = new Internal.EnumLiteMap<NetworkType>() {
                public NetworkType findValueByNumber(int number) {
                    return NetworkType.forNumber(number);
                }
            };
            private final int value;

            private NetworkType(int value2) {
                this.value = value2;
            }

            public static NetworkType forNumber(int value2) {
                switch (value2) {
                    case -1:
                        return NONE;
                    case 0:
                        return MOBILE;
                    case 1:
                        return WIFI;
                    case 2:
                        return MOBILE_MMS;
                    case 3:
                        return MOBILE_SUPL;
                    case 4:
                        return MOBILE_DUN;
                    case 5:
                        return MOBILE_HIPRI;
                    case 6:
                        return WIMAX;
                    case 7:
                        return BLUETOOTH;
                    case 8:
                        return DUMMY;
                    case 9:
                        return ETHERNET;
                    case 10:
                        return MOBILE_FOTA;
                    case 11:
                        return MOBILE_IMS;
                    case 12:
                        return MOBILE_CBS;
                    case 13:
                        return WIFI_P2P;
                    case 14:
                        return MOBILE_IA;
                    case 15:
                        return MOBILE_EMERGENCY;
                    case 16:
                        return PROXY;
                    case 17:
                        return VPN;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<NetworkType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return NetworkTypeVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class NetworkTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new NetworkTypeVerifier();

                private NetworkTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return NetworkType.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NetworkConnectionInfo, Builder> implements NetworkConnectionInfoOrBuilder {
            private Builder() {
                super(NetworkConnectionInfo.DEFAULT_INSTANCE);
            }

            public boolean hasNetworkType() {
                return ((NetworkConnectionInfo) this.instance).hasNetworkType();
            }

            public NetworkType getNetworkType() {
                return ((NetworkConnectionInfo) this.instance).getNetworkType();
            }

            public Builder setNetworkType(NetworkType value) {
                copyOnWrite();
                ((NetworkConnectionInfo) this.instance).setNetworkType(value);
                return this;
            }

            public Builder clearNetworkType() {
                copyOnWrite();
                ((NetworkConnectionInfo) this.instance).clearNetworkType();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class RpcStats extends GeneratedMessageLite<RpcStats, Builder> implements RpcStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final RpcStats DEFAULT_INSTANCE = new RpcStats();
        public static final int RPC_STATUS_CODE_FIELD_NUMBER = 1;
        private static volatile Parser<RpcStats> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(RpcStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int rpcStatusCode_;

        private RpcStats() {
        }

        public static RpcStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RpcStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RpcStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RpcStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RpcStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RpcStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RpcStats parseFrom(InputStream input) throws IOException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RpcStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RpcStats parseDelimitedFrom(InputStream input) throws IOException {
            return (RpcStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static RpcStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RpcStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RpcStats parseFrom(CodedInputStream input) throws IOException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RpcStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RpcStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(RpcStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static RpcStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RpcStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasRpcStatusCode() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getRpcStatusCode() {
            return this.rpcStatusCode_;
        }

        /* access modifiers changed from: private */
        public void setRpcStatusCode(int value) {
            this.bitField0_ |= 1;
            this.rpcStatusCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRpcStatusCode() {
            this.bitField0_ &= -2;
            this.rpcStatusCode_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new RpcStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "rpcStatusCode_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<RpcStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (RpcStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<RpcStats, Builder> implements RpcStatsOrBuilder {
            private Builder() {
                super(RpcStats.DEFAULT_INSTANCE);
            }

            public boolean hasRpcStatusCode() {
                return ((RpcStats) this.instance).hasRpcStatusCode();
            }

            public int getRpcStatusCode() {
                return ((RpcStats) this.instance).getRpcStatusCode();
            }

            public Builder setRpcStatusCode(int value) {
                copyOnWrite();
                ((RpcStats) this.instance).setRpcStatusCode(value);
                return this;
            }

            public Builder clearRpcStatusCode() {
                copyOnWrite();
                ((RpcStats) this.instance).clearRpcStatusCode();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CacheStats extends GeneratedMessageLite<CacheStats, Builder> implements CacheStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final CacheStats DEFAULT_INSTANCE = new CacheStats();
        public static final int HIT_COUNT_FIELD_NUMBER = 2;
        public static final int LOOKUP_COUNT_FIELD_NUMBER = 1;
        private static volatile Parser<CacheStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(CacheStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int hitCount_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int lookupCount_;

        private CacheStats() {
        }

        public static CacheStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CacheStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CacheStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CacheStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CacheStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CacheStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CacheStats parseFrom(InputStream input) throws IOException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CacheStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CacheStats parseDelimitedFrom(InputStream input) throws IOException {
            return (CacheStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CacheStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CacheStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CacheStats parseFrom(CodedInputStream input) throws IOException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CacheStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CacheStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CacheStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static CacheStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CacheStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasLookupCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getLookupCount() {
            return this.lookupCount_;
        }

        /* access modifiers changed from: private */
        public void setLookupCount(int value) {
            this.bitField0_ |= 1;
            this.lookupCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLookupCount() {
            this.bitField0_ &= -2;
            this.lookupCount_ = 0;
        }

        public boolean hasHitCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getHitCount() {
            return this.hitCount_;
        }

        /* access modifiers changed from: private */
        public void setHitCount(int value) {
            this.bitField0_ |= 2;
            this.hitCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHitCount() {
            this.bitField0_ &= -3;
            this.hitCount_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CacheStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "lookupCount_", "hitCount_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CacheStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (CacheStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<CacheStats, Builder> implements CacheStatsOrBuilder {
            private Builder() {
                super(CacheStats.DEFAULT_INSTANCE);
            }

            public boolean hasLookupCount() {
                return ((CacheStats) this.instance).hasLookupCount();
            }

            public int getLookupCount() {
                return ((CacheStats) this.instance).getLookupCount();
            }

            public Builder setLookupCount(int value) {
                copyOnWrite();
                ((CacheStats) this.instance).setLookupCount(value);
                return this;
            }

            public Builder clearLookupCount() {
                copyOnWrite();
                ((CacheStats) this.instance).clearLookupCount();
                return this;
            }

            public boolean hasHitCount() {
                return ((CacheStats) this.instance).hasHitCount();
            }

            public int getHitCount() {
                return ((CacheStats) this.instance).getHitCount();
            }

            public Builder setHitCount(int value) {
                copyOnWrite();
                ((CacheStats) this.instance).setHitCount(value);
                return this;
            }

            public Builder clearHitCount() {
                copyOnWrite();
                ((CacheStats) this.instance).clearHitCount();
                return this;
            }
        }
    }
}
