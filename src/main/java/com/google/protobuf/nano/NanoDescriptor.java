package com.google.protobuf.nano;

import com.google.protobuf.ByteString;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;
import java.util.List;

public final class NanoDescriptor {
    public static final int EMERITUS_FIELD_NUMBER = 163486533;
    public static final int ENCODED_MUNGEE_FIELD_NUMBER = 157245250;
    public static final int ENUM_AS_LITE_FIELD_NUMBER = 149419467;
    public static final int ENUM_UNMUNGED_FILE_DESCRIPTOR_NAME_FIELD_NUMBER = 190295313;
    public static final int FILE_AS_LITE_FIELD_NUMBER = 180648220;
    public static final int GENERATE_AS_FIELD_NUMBER = 179701954;
    public static final int LEGACY_ENUM_FIELD_NUMBER = 163526403;
    public static final int LEGACY_ONEOF_FIELD_NUMBER = 147618788;
    public static final int MESSAGE_AS_LITE_FIELD_NUMBER = 149418587;
    public static final int MESSAGE_UNMUNGED_FILE_DESCRIPTOR_NAME_FIELD_NUMBER = 190288050;
    public static final int METHOD_AS_LITE_FIELD_NUMBER = 211030419;
    public static final int MUNGER_FIELD_NUMBER = 155465253;
    public static final int UNMUNGED_DEPS_COMPLIANT_FIELD_NUMBER = 170261731;
    public static final int WATERMARK_COMPLIANT_FIELD_NUMBER = 162702653;
    public static final int WHITELISTED_FIELD_NUMBER = 179096040;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumValueOptions, Boolean> emeritus = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumValueOptions.getDefaultInstance(), null, null, null, EMERITUS_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, ByteString> encodedMungee = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), ByteString.EMPTY, null, null, ENCODED_MUNGEE_FIELD_NUMBER, WireFormat.FieldType.BYTES, ByteString.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumOptions, Boolean> enumAsLite = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumOptions.getDefaultInstance(), 1, null, null, ENUM_AS_LITE_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumOptions, String> enumUnmungedFileDescriptorName = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumOptions.getDefaultInstance(), "", null, null, ENUM_UNMUNGED_FILE_DESCRIPTOR_NAME_FIELD_NUMBER, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> fileAsLite = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), 1, null, null, FILE_AS_LITE_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, GenerateAs> generateAs = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), GenerateAs.NANO, null, GenerateAs.internalGetValueMap(), GENERATE_AS_FIELD_NUMBER, WireFormat.FieldType.ENUM, GenerateAs.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumOptions, Boolean> legacyEnum = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumOptions.getDefaultInstance(), null, null, null, LEGACY_ENUM_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> legacyOneof = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, LEGACY_ONEOF_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> messageAsLite = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), 1, null, null, MESSAGE_AS_LITE_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, String> messageUnmungedFileDescriptorName = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), "", null, null, MESSAGE_UNMUNGED_FILE_DESCRIPTOR_NAME_FIELD_NUMBER, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MethodOptions, Boolean> methodAsLite = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MethodOptions.getDefaultInstance(), null, null, null, METHOD_AS_LITE_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Munger> munger = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), Munger.NONE, null, Munger.internalGetValueMap(), MUNGER_FIELD_NUMBER, WireFormat.FieldType.ENUM, Munger.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumValueOptions, Boolean> unmungedDepsCompliant = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumValueOptions.getDefaultInstance(), 1, null, null, UNMUNGED_DEPS_COMPLIANT_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumValueOptions, Boolean> watermarkCompliant = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumValueOptions.getDefaultInstance(), 1, null, null, WATERMARK_COMPLIANT_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumValueOptions, List<String>> whitelisted = GeneratedMessageLite.newRepeatedGeneratedExtension(DescriptorProtos.EnumValueOptions.getDefaultInstance(), null, null, WHITELISTED_FIELD_NUMBER, WireFormat.FieldType.STRING, false, String.class);

    private NanoDescriptor() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) legacyOneof);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) messageAsLite);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) messageUnmungedFileDescriptorName);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) enumAsLite);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) legacyEnum);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) enumUnmungedFileDescriptorName);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) generateAs);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) watermarkCompliant);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) emeritus);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) unmungedDepsCompliant);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) whitelisted);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) munger);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) encodedMungee);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fileAsLite);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) methodAsLite);
    }

    public enum GenerateAs implements Internal.EnumLite {
        INVALID(0),
        NANO(1),
        LITE(2),
        OMIT(3);
        
        public static final int INVALID_VALUE = 0;
        public static final int LITE_VALUE = 2;
        public static final int NANO_VALUE = 1;
        public static final int OMIT_VALUE = 3;
        private static final Internal.EnumLiteMap<GenerateAs> internalValueMap = new Internal.EnumLiteMap<GenerateAs>() {
            public GenerateAs findValueByNumber(int number) {
                return GenerateAs.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static GenerateAs forNumber(int value2) {
            if (value2 == 0) {
                return INVALID;
            }
            if (value2 == 1) {
                return NANO;
            }
            if (value2 == 2) {
                return LITE;
            }
            if (value2 != 3) {
                return null;
            }
            return OMIT;
        }

        public static Internal.EnumLiteMap<GenerateAs> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return GenerateAsVerifier.INSTANCE;
        }

        private static final class GenerateAsVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new GenerateAsVerifier();

            private GenerateAsVerifier() {
            }

            public boolean isInRange(int number) {
                return GenerateAs.forNumber(number) != null;
            }
        }

        private GenerateAs(int value2) {
            this.value = value2;
        }
    }

    public enum Munger implements Internal.EnumLite {
        NONE(0),
        REDUCED_NANO_PROTO(1),
        AGSA_PROTO(2),
        FIXED_CORRECT_PROTO_PLAY_ENTERTAINMENT(3),
        FIXED_CORRECT_PROTO_PLAY_STORE(4),
        WASP(5),
        COMMERCE_PAYMENTS_INSTORE_TOOLS_CONVERT_TO_NANO_PROTOS(6),
        ANDROID_UTIL_CONVERT_TO_NANO_PROTOS(7),
        SIMUX(8),
        KIDS_MANAGEMENT(9),
        ADS_EXPRESS_MOBILEAPP(10),
        INNERTUBE(11),
        HANGOUTS(12),
        TVSEARCH(13),
        FIXED_CORRECT_PROTO_PLAY_COMMON(14),
        CLOCKWORK_PROTO(15),
        GOGGLES_PROTO(16),
        S3_SPEECH_PROTO(17),
        DOTS(18),
        TESTING_NOT_WATERMARK_COMPLIANT(100),
        TESTING_WATERMARK_COMPLIANT(101);
        
        public static final int ADS_EXPRESS_MOBILEAPP_VALUE = 10;
        public static final int AGSA_PROTO_VALUE = 2;
        public static final int ANDROID_UTIL_CONVERT_TO_NANO_PROTOS_VALUE = 7;
        public static final int CLOCKWORK_PROTO_VALUE = 15;
        public static final int COMMERCE_PAYMENTS_INSTORE_TOOLS_CONVERT_TO_NANO_PROTOS_VALUE = 6;
        public static final int DOTS_VALUE = 18;
        public static final int FIXED_CORRECT_PROTO_PLAY_COMMON_VALUE = 14;
        public static final int FIXED_CORRECT_PROTO_PLAY_ENTERTAINMENT_VALUE = 3;
        public static final int FIXED_CORRECT_PROTO_PLAY_STORE_VALUE = 4;
        public static final int GOGGLES_PROTO_VALUE = 16;
        public static final int HANGOUTS_VALUE = 12;
        public static final int INNERTUBE_VALUE = 11;
        public static final int KIDS_MANAGEMENT_VALUE = 9;
        public static final int NONE_VALUE = 0;
        public static final int REDUCED_NANO_PROTO_VALUE = 1;
        public static final int S3_SPEECH_PROTO_VALUE = 17;
        public static final int SIMUX_VALUE = 8;
        public static final int TESTING_NOT_WATERMARK_COMPLIANT_VALUE = 100;
        public static final int TESTING_WATERMARK_COMPLIANT_VALUE = 101;
        public static final int TVSEARCH_VALUE = 13;
        public static final int WASP_VALUE = 5;
        private static final Internal.EnumLiteMap<Munger> internalValueMap = new Internal.EnumLiteMap<Munger>() {
            public Munger findValueByNumber(int number) {
                return Munger.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static Munger forNumber(int value2) {
            if (value2 == 100) {
                return TESTING_NOT_WATERMARK_COMPLIANT;
            }
            if (value2 == 101) {
                return TESTING_WATERMARK_COMPLIANT;
            }
            switch (value2) {
                case 0:
                    return NONE;
                case 1:
                    return REDUCED_NANO_PROTO;
                case 2:
                    return AGSA_PROTO;
                case 3:
                    return FIXED_CORRECT_PROTO_PLAY_ENTERTAINMENT;
                case 4:
                    return FIXED_CORRECT_PROTO_PLAY_STORE;
                case 5:
                    return WASP;
                case 6:
                    return COMMERCE_PAYMENTS_INSTORE_TOOLS_CONVERT_TO_NANO_PROTOS;
                case 7:
                    return ANDROID_UTIL_CONVERT_TO_NANO_PROTOS;
                case 8:
                    return SIMUX;
                case 9:
                    return KIDS_MANAGEMENT;
                case 10:
                    return ADS_EXPRESS_MOBILEAPP;
                case 11:
                    return INNERTUBE;
                case 12:
                    return HANGOUTS;
                case 13:
                    return TVSEARCH;
                case 14:
                    return FIXED_CORRECT_PROTO_PLAY_COMMON;
                case 15:
                    return CLOCKWORK_PROTO;
                case 16:
                    return GOGGLES_PROTO;
                case 17:
                    return S3_SPEECH_PROTO;
                case 18:
                    return DOTS;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Munger> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return MungerVerifier.INSTANCE;
        }

        private static final class MungerVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new MungerVerifier();

            private MungerVerifier() {
            }

            public boolean isInRange(int number) {
                return Munger.forNumber(number) != null;
            }
        }

        private Munger(int value2) {
            this.value = value2;
        }
    }
}
