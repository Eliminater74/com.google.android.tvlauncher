package com.google.protobuf;

import com.google.analytics.config.protoverifier.proto.RulesProto;
import com.google.apps.jspb.Jspb;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.nano.NanoDescriptor;
import com.google.protos.china.policy.ChinaAnnotations;
import com.google.protos.datapol.SemanticAnnotations;
import com.google.protos.logs_proto.LogsAnnotations;
import com.google.protos.protobuf.contrib.j2cl.options.JsEnum;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

/* renamed from: com.google.protobuf.java_com_google_android_apps_tv_tvhome__tvhome_fullrelease_6037b148GeneratedExtensionRegistryLite */
final class C1886x94510305 extends ExtensionRegistryLite {
    private static final String CONTAINING_TYPE_0 = "com.google.protobuf.DescriptorProtos$FieldOptions";
    private static final String CONTAINING_TYPE_1 = "com.google.protobuf.DescriptorProtos$MessageOptions";
    private static final String CONTAINING_TYPE_2 = "com.google.protobuf.DescriptorProtos$FileOptions";
    private static final String CONTAINING_TYPE_3 = "com.google.protos.proto2.bridge.MessageSet";
    private static final String CONTAINING_TYPE_4 = "com.google.protobuf.DescriptorProtos$EnumOptions";
    private static final String CONTAINING_TYPE_5 = "com.google.protobuf.DescriptorProtos$EnumValueOptions";
    private static final String CONTAINING_TYPE_6 = "com.google.protobuf.DescriptorProtos$MethodOptions";

    private C1886x94510305() {
        super(true);
    }

    /* renamed from: com.google.protobuf.java_com_google_android_apps_tv_tvhome__tvhome_fullrelease_6037b148GeneratedExtensionRegistryLite$Loader */
    public static class Loader extends GeneratedExtensionRegistryLoader {
        /* access modifiers changed from: protected */
        public final ExtensionRegistryLite getInstance() {
            return new C1886x94510305();
        }
    }

    public ExtensionRegistryLite getUnmodifiable() {
        return this;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public <CT extends MessageLite> GeneratedMessageLite.GeneratedExtension<CT, ?> findLiteExtensionByNumber(CT ct, int i) {
        char c;
        String name = ct.getClass().getName();
        switch (name.hashCode()) {
            case -922627218:
                if (name.equals(CONTAINING_TYPE_4)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -771329763:
                if (name.equals(CONTAINING_TYPE_5)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -769276105:
                if (name.equals(CONTAINING_TYPE_3)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 150746598:
                if (name.equals(CONTAINING_TYPE_1)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 587596435:
                if (name.equals(CONTAINING_TYPE_2)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1260860755:
                if (name.equals(CONTAINING_TYPE_0)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1968143182:
                if (name.equals(CONTAINING_TYPE_6)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                switch (i) {
                    case 17702:
                        return Jspb.ignore;
                    case Jspb.JSTYPE_FIELD_NUMBER:
                        return Jspb.jstype;
                    case Jspb.MAP_KEY_FIELD_NUMBER:
                        return Jspb.mapKey;
                    case Jspb.USE_BROKEN_JAVA_UNSIGNED_SERIALIZATION_BEHAVIOR_FIELD_NUMBER:
                        return Jspb.useBrokenJavaUnsignedSerializationBehavior;
                    case Jspb.ALLOW_BROKEN_JAVA_UNSIGNED_DESERIALIZATION_BEHAVIOR_FIELD_NUMBER:
                        return Jspb.allowBrokenJavaUnsignedDeserializationBehavior;
                    case 21596320:
                        return LogsAnnotations.notLoggedInSawmill;
                    case 21623477:
                        return LogsAnnotations.tempLogsOnly;
                    case 21713708:
                        return LogsAnnotations.idType;
                    case LogsAnnotations.IS_PRIVATE_LOG_FIELD_NUMBER /*23459630*/:
                        return LogsAnnotations.isPrivateLog;
                    case 26652850:
                        return LogsAnnotations.isEncrypted;
                    case RulesProto.RULES_FIELD_NUMBER:
                        return RulesProto.rules;
                    case RulesProto.MUTATION_RULES_FIELD_NUMBER:
                        return RulesProto.mutationRules;
                    case SemanticAnnotations.SEMANTIC_TYPE_FIELD_NUMBER /*40075780*/:
                        return SemanticAnnotations.semanticType;
                    case SemanticAnnotations.FIELD_DETAILS_FIELD_NUMBER /*40093572*/:
                        return SemanticAnnotations.fieldDetails;
                    case SemanticAnnotations.DATA_FORMAT_FIELD_NUMBER /*40221563*/:
                        return SemanticAnnotations.dataFormat;
                    case SemanticAnnotations.RETENTION_FIELD_NUMBER /*40223876*/:
                        return SemanticAnnotations.retention;
                    case SemanticAnnotations.QUALIFIER_FIELD_NUMBER /*40270992*/:
                        return SemanticAnnotations.qualifier;
                    case LogsAnnotations.MAX_RECURSION_DEPTH_FIELD_NUMBER /*53697879*/:
                        return LogsAnnotations.maxRecursionDepth;
                    case LogsAnnotations.SAWMILL_FILTER_OVERRIDE_APPROVED_BY_LOGS_ACCESS_FIELD_NUMBER /*56871503*/:
                        return LogsAnnotations.sawmillFilterOverrideApprovedByLogsAccess;
                    case ChinaAnnotations.SERIALIZED_DATA_FIELD_NUMBER /*61530914*/:
                        return ChinaAnnotations.serializedData;
                    case ChinaAnnotations.RESTRICTED_FIELD_NUMBER /*61533623*/:
                        return ChinaAnnotations.restricted;
                    case 69646961:
                        return SemanticAnnotations.locationQualifier;
                    case NanoDescriptor.GENERATE_AS_FIELD_NUMBER /*179701954*/:
                        return NanoDescriptor.generateAs;
                    default:
                        return null;
                }
            case 1:
                switch (i) {
                    case 17701:
                        return Jspb.messageId;
                    case 17702:
                        return Jspb.namespaceOnly;
                    case LogsAnnotations.MSG_DETAILS_FIELD_NUMBER /*21467048*/:
                        return LogsAnnotations.msgDetails;
                    case 21596320:
                        return LogsAnnotations.msgNotLoggedInSawmill;
                    case 21623477:
                        return LogsAnnotations.msgTempLogsOnly;
                    case 21713708:
                        return LogsAnnotations.msgIdType;
                    case Jspb.BUILDER_FIELD_NUMBER:
                        return Jspb.builder;
                    case 26652850:
                        return LogsAnnotations.fieldEncryptionKeyName;
                    case SemanticAnnotations.MSG_SEMANTIC_TYPE_FIELD_NUMBER /*41149386*/:
                        return SemanticAnnotations.msgSemanticType;
                    case SemanticAnnotations.MSG_QUALIFIER_FIELD_NUMBER /*41551199*/:
                        return SemanticAnnotations.msgQualifier;
                    case SemanticAnnotations.MSG_DETAILS_FIELD_NUMBER /*41744383*/:
                        return SemanticAnnotations.msgDetails;
                    case SemanticAnnotations.MSG_RETENTION_FIELD_NUMBER /*41909987*/:
                        return SemanticAnnotations.msgRetention;
                    case 69646961:
                        return SemanticAnnotations.msgLocationQualifier;
                    case Jspb.GENERATE_XID_FIELD_NUMBER:
                        return Jspb.generateXid;
                    case Jspb.GENERATE_FROM_OBJECT_FIELD_NUMBER:
                        return Jspb.generateFromObject;
                    case NanoDescriptor.LEGACY_ONEOF_FIELD_NUMBER /*147618788*/:
                        return NanoDescriptor.legacyOneof;
                    case NanoDescriptor.MESSAGE_AS_LITE_FIELD_NUMBER /*149418587*/:
                        return NanoDescriptor.messageAsLite;
                    case NanoDescriptor.MESSAGE_UNMUNGED_FILE_DESCRIPTOR_NAME_FIELD_NUMBER /*190288050*/:
                        return NanoDescriptor.messageUnmungedFileDescriptorName;
                    default:
                        return null;
                }
            case 2:
                switch (i) {
                    case 17701:
                        return Jspb.responseProto;
                    case 17702:
                        return Jspb.jsNamespace;
                    case 21596320:
                        return LogsAnnotations.fileNotUsedForLoggingExceptEnums;
                    case LogsAnnotations.FILE_VETTED_FOR_LOGS_ANNOTATIONS_FIELD_NUMBER /*28993747*/:
                        return LogsAnnotations.fileVettedForLogsAnnotations;
                    case SemanticAnnotations.FILE_VETTED_FOR_DATAPOL_ANNOTATIONS_FIELD_NUMBER /*43601160*/:
                        return SemanticAnnotations.fileVettedForDatapolAnnotations;
                    case SemanticAnnotations.FILE_VETTING_STATUS_FIELD_NUMBER /*71304954*/:
                        return SemanticAnnotations.fileVettingStatus;
                    case Jspb.USE_BROKEN_PROTO2_SEMANTICS_FIELD_NUMBER:
                        return Jspb.useBrokenProto2Semantics;
                    case NanoDescriptor.MUNGER_FIELD_NUMBER /*155465253*/:
                        return NanoDescriptor.munger;
                    case NanoDescriptor.ENCODED_MUNGEE_FIELD_NUMBER /*157245250*/:
                        return NanoDescriptor.encodedMungee;
                    case NanoDescriptor.FILE_AS_LITE_FIELD_NUMBER /*180648220*/:
                        return NanoDescriptor.fileAsLite;
                    default:
                        return null;
                }
            case 3:
                if (i != 66321687) {
                    return null;
                }
                return ClientAnalytics.ClientInfo.messageSetExtension;
            case 4:
                if (i == 13371) {
                    return JsEnum.generateJsEnum;
                }
                if (i == 149419467) {
                    return NanoDescriptor.enumAsLite;
                }
                if (i == 163526403) {
                    return NanoDescriptor.legacyEnum;
                }
                if (i != 190295313) {
                    return null;
                }
                return NanoDescriptor.enumUnmungedFileDescriptorName;
            case 5:
                switch (i) {
                    case NanoDescriptor.WATERMARK_COMPLIANT_FIELD_NUMBER /*162702653*/:
                        return NanoDescriptor.watermarkCompliant;
                    case NanoDescriptor.EMERITUS_FIELD_NUMBER /*163486533*/:
                        return NanoDescriptor.emeritus;
                    case NanoDescriptor.UNMUNGED_DEPS_COMPLIANT_FIELD_NUMBER /*170261731*/:
                        return NanoDescriptor.unmungedDepsCompliant;
                    case NanoDescriptor.WHITELISTED_FIELD_NUMBER /*179096040*/:
                        return NanoDescriptor.whitelisted;
                    default:
                        return null;
                }
            case 6:
                if (i != 211030419) {
                    return null;
                }
                return NanoDescriptor.methodAsLite;
            default:
                return null;
        }
    }
}
